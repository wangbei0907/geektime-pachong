package com.example.springtest.jksj.detail;

import com.example.springtest.comm.ICollector;
import com.example.springtest.comm.WebMagicUtil;
import com.example.springtest.jksj.util.CollectorUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * <b>description</b>：采集文章详情 <br>
 * 如页面：<a href="https://time.geekbang.org/column/article/41440">点击查看</a> <br>
 * 返回结果见：{@link ArticleCollectorResponse} <br>
 * <b>time</b>：2019-4-21 19:41 <br>
 * <b>author</b>： 微信公众号：路人甲Java，专注于java技术分享（带你玩转 爬虫、分布式事务、异步消息服务、任务调度、分库分表、大数据等），喜欢请关注！
 */
@Slf4j
public class ArticleCollector implements ICollector<ArticleCollectorRequest, ArticleCollectorResponse> {

    @Override
    public ArticleCollectorResponse collect(ArticleCollectorRequest request) {
        CollectorPageProcessor pageProcessor = new CollectorPageProcessor(request);
        pageProcessor.collect();
        return pageProcessor.response;
    }

    static class CollectorPageProcessor implements PageProcessor {
        private ArticleCollectorRequest request;
        private ArticleCollectorResponse response;
        private Site site = Site.me().setRetryTimes(3).setSleepTime(100).setTimeOut(10000);
        private String url;

        public CollectorPageProcessor(ArticleCollectorRequest request) {
            this.request = request;
        }

        public void collect() {
            String url = String.format("https://time.geekbang.org/serv/v1/article");
            Request request = new Request(url);
            request.getHeaders().putAll(CollectorUtil.headerMap());
            request.setMethod("POST");
            String json = String.format("{\"id\":\"%s\"}", this.request.getId());
            request.setRequestBody(HttpRequestBody.json(json, "UTF-8"));
            Spider.create(this).thread(1).addRequest(request).run();
        }

        @Override
        public void process(Page page) {
            //判断采集是否成功
            if (!WebMagicUtil.isSuccess(page)) {
                return;
            }
            String rawText = page.getRawText();
            log.info("{}", rawText);
            //将结果转换为ArticleCollectorResponse
            JSONObject jsonObject = JSON.parseObject(rawText);
            this.response = jsonObject.getObject("data", ArticleCollectorResponse.class);
            if (this.response != null) {
                this.response.setSite_source_url(String.format("https://time.geekbang.org/column/article/%s", this.response.getId()));
            }
        }

        @Override
        public Site getSite() {
            return this.site;
        }
    }
}
