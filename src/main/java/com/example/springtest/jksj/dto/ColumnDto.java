package com.example.springtest.jksj.dto;

import com.example.springtest.jksj.column.ColumnCollectorResponse;
import com.example.springtest.jksj.detail.ArticleCollectorResponse;
import lombok.*;

import java.util.List;

/**
 * <b>description</b>：一个完整课程信息封装 <br>
 * <b>time</b>：2019-04-21 16:40 <br>
 * <b>author</b>： 微信公众号：路人甲Java，专注于java技术分享（带你玩转 爬虫、分布式事务、异步消息服务、任务调度、分库分表、大数据等），喜欢请关注！
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ColumnDto {
    //一个系列课程信息
    private ColumnCollectorResponse columnCollectorResponse;
    //系列中文章列表信息系
    private List<ArticleCollectorResponse> articleCollectorResponseList;
}
