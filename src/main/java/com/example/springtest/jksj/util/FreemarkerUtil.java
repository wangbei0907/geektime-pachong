package com.example.springtest.jksj.util;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

import java.io.StringWriter;

/**
 * <b>description</b>：freemarker工具类 <br>
 * <b>time</b>：2019-04-21 10:37:09 <br>
 * <b>author</b>： 微信公众号：路人甲Java，专注于java技术分享（带你玩转 爬虫、分布式事务、异步消息服务、任务调度、分库分表、大数据等），喜欢请关注！
 */
public class FreemarkerUtil {

    private static Configuration cfg = null;

    public static Configuration getCfg() throws TemplateException {
        if (cfg == null) {
            synchronized (FreemarkerUtil.class) {
                if (cfg == null) {
                    cfg = new Configuration();
                    cfg.setDefaultEncoding("UTF-8");
                    cfg.setClassicCompatible(true);
                    cfg.setTemplateUpdateDelay(10);
                    // 设置模板文件所在的目录
                    cfg.setClassForTemplateLoading(FreemarkerUtil.class, "/com/ady01/demo4/jksj/ftl/");
                }
            }
        }
        return cfg;
    }

    /**
     * 根据freemarker文件获取内容
     *
     * @param ftlFileName
     * @param rootMap
     * @return
     * @throws Exception
     */
    public static String getFtlToString(String ftlFileName, Object rootMap)
            throws Exception {
        StringWriter sw = new StringWriter();
        getCfg().getTemplate(ftlFileName + ".ftl").process(rootMap, sw);
        return sw.toString();
    }

}
