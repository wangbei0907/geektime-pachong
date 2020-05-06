package com.example.springtest.jksj.detail;

import com.example.springtest.comm.ICollectorResponse;
import lombok.*;

/**
 * <b>description</b>：响应信息 <br>
 * <b>time</b>：2019-4-21 19:41 <br>
 * <b>author</b>： 微信公众号：路人甲Java，专注于java技术分享（带你玩转 爬虫、分布式事务、异步消息服务、任务调度、分库分表、大数据等），喜欢请关注！
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ArticleCollectorResponse implements ICollectorResponse {
    //课程id
    private Long id;
    //课程系列id
    private Long column_id;
    //文章标题
    private String article_title;
    //文章内容
    private String article_content;
    //音频地址
    private String audio_download_url;
    //采集页面来源
    private String site_source_url;
}
