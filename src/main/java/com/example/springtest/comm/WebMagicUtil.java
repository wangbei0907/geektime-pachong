package com.example.springtest.comm;

import org.apache.http.HttpStatus;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;

import java.util.Objects;

/**
 * <b>description</b>：webmagic工具类 <br>
 * <b>time</b>：2018-09-03 11:18 <br>
 * <b>author</b>： 微信公众号：路人甲Java，专注于java技术分享（带你玩转 爬虫、分布式事务、异步消息服务、任务调度、分库分表、大数据等），喜欢请关注！
 */
public class WebMagicUtil {

    public static final String REQUEST_PAGE_TYPE_KEY = "REQUEST_PAGE_TYPE_KEY";

    /**
     * 判断page是否成功
     *
     * @param page
     * @return
     */
    public static boolean isSuccess(Page page) {
        return page != null && page.isDownloadSuccess() && page.getStatusCode() != HttpStatus.SC_NOT_FOUND;
    }

    /**
     * 设置页面类型
     *
     * @param request
     * @param type
     * @return
     */
    public static Request setRequestPageType(Request request, String type) {
        return request.putExtra(REQUEST_PAGE_TYPE_KEY, type);
    }

    /**
     * 获取页面类型
     *
     * @param request
     * @return
     */
    public static String getRequestPageType(Request request) {
        Object obj = request.getExtra(REQUEST_PAGE_TYPE_KEY);
        return Objects.isNull(obj) ? null : obj.toString();
    }

    /**
     * 是否匹配requestPageType
     *
     * @param request
     * @param type
     * @return
     */
    public static boolean isRequestPageType(Request request, String type) {
        String requestPageType = getRequestPageType(request);
        return type == requestPageType || (Objects.nonNull(type) && type.equals(requestPageType));
    }
}
