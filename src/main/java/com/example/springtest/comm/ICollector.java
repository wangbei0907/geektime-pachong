package com.example.springtest.comm;

/**
 * <b>description</b>：采集器接口 <br>
 * <b>time</b>：2019-4-21 19:40 <br>
 * <b>author</b>： 微信公众号：路人甲Java，专注于java技术分享（带你玩转 爬虫、分布式事务、异步消息服务、任务调度、分库分表、大数据等），喜欢请关注！
 */
public interface ICollector<R extends ICollectorRequest, O extends ICollectorResponse> {
    O collect(R request);
}
