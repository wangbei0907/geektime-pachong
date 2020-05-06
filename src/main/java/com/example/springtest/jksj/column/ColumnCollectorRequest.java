package com.example.springtest.jksj.column;

import com.example.springtest.comm.ICollectorRequest;
import lombok.*;

/**
 * <b>description</b>：请求信息 <br>
 * <b>time</b>：2019-4-21 19:41 <br>
 * <b>author</b>： 微信公众号：路人甲Java，专注于java技术分享（带你玩转 爬虫、分布式事务、异步消息服务、任务调度、分库分表、大数据等），喜欢请关注！
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ColumnCollectorRequest implements ICollectorRequest {
    private String cid = "126";
    private int size = 20;
}
