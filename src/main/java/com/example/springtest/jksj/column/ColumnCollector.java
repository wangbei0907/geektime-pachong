package com.example.springtest.jksj.column;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.springtest.comm.ICollector;
import com.example.springtest.comm.WebMagicUtil;
import com.example.springtest.jksj.util.CollectorUtil;
import com.example.springtest.util.FrameUtil;
import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * <b>description</b>：采集课程列表 <br>
 * 如页面：<a href="https://time.geekbang.org/column/126">点击查看</a> <br>
 * 返回结果见：{@link ColumnCollectorResponse} <br>
 * <b>time</b>：2019-4-21 19:41 <br>
 * <b>author</b>： 微信公众号：路人甲Java，专注于java技术分享（带你玩转 爬虫、分布式事务、异步消息服务、任务调度、分库分表、大数据等），喜欢请关注！
 */
@Slf4j
public class ColumnCollector implements ICollector<ColumnCollectorRequest, ColumnCollectorResponse> {

    @Override
    public ColumnCollectorResponse collect(ColumnCollectorRequest request) {
        CollectorPageProcessor pageProcessor = new CollectorPageProcessor(request);
        pageProcessor.collect();
        return pageProcessor.response;
    }

    static class CollectorPageProcessor implements PageProcessor {
        private ColumnCollectorRequest request;
        private ColumnCollectorResponse response;
        private Site site = Site.me().setRetryTimes(3).setSleepTime(100).setTimeOut(10000);

        public CollectorPageProcessor(ColumnCollectorRequest request) {
            this.request = request;
        }

        public void collect() {
            Spider spider = Spider.create(this).thread(1);
            {
                String url = String.format("https://time.geekbang.org/serv/v1/column/intro");
                Request request = new Request(url);
                request.setExtras(FrameUtil.newHashMap("page_type", "1"));
                request.getHeaders().putAll(CollectorUtil.headerMap());
                request.setMethod("POST");
                String json = String.format("{\"cid\":\"%s\"}", this.request.getCid());
                request.setRequestBody(HttpRequestBody.json(json, "UTF-8"));
                spider.addRequest(request);
            }
            {
                String url = String.format("https://time.geekbang.org/serv/v1/column/articles");
                Request request = new Request(url);
                request.setExtras(FrameUtil.newHashMap("page_type", "2"));
                request.getHeaders().putAll(CollectorUtil.headerMap());
                request.setMethod("POST");
                String json = String.format("{\"cid\":\"%s\",\"size\":%s,\"prev\":0,\"order\":\"earliest\",\"sample\":false}", this.request.getCid(), this.request.getSize());
                request.setRequestBody(HttpRequestBody.json(json, "UTF-8"));
                spider.addRequest(request);
            }
            this.response = ColumnCollectorResponse.builder().build();
            spider.run();
        }

        @Override
        public void process(Page page) {
            if (!WebMagicUtil.isSuccess(page)) {
                return;
            }
            String rawText = page.getRawText();
            log.info("{}", rawText);
            JSONObject jsonObject = JSON.parseObject(rawText);
            JSONObject data = jsonObject.getJSONObject("data");
            Object page_type = page.getRequest().getExtra("page_type");
            if (page_type.equals("1")) {
                this.response.setColumn_title(data.getString("column_title"));
            } else if (page_type.equals("2")) {
                List<ColumnCollectorResponse.Article> articleList = null;
                if (data != null) {
                    JSONArray list = data.getJSONArray("list");
                    if (list != null && list.size() >= 1) {
                        articleList = FrameUtil.newArrayList();
                        for (Object o : list) {
                            if (o instanceof JSONObject) {
                                JSONObject item = (JSONObject) o;
                                String article_title = item.getString("article_title");
                                Long id = item.getLong("id");
                                articleList.add(ColumnCollectorResponse.Article.builder().id(id).article_title(article_title).article_url("https://time.geekbang.org/column/article/"+id).build());
                            }
                        }
                    }
                }
                this.response.setArticleList(articleList);
            }
        }

        @Override
        public Site getSite() {
            return this.site;
        }
    }
}
