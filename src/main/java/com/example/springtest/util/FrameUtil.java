package com.example.springtest.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>description</b>：工具类 <br>
 * <b>time</b>：2019/4/21 13:59 <br>
 * <b>author</b>：微信公众号：路人甲Java，专注于java技术分享（爬虫、分布式事务、异步消息服务、任务调度、分库分表、大数据等），喜欢请关注！
 */
public class FrameUtil {
    /**
     * 将对象转换为json字符串
     *
     * @param object 需转换的目标对象
     * @return
     */
    public static String json(Object object) {
        return json(object, false);
    }

    /**
     * 将对象转换为json字符串，可以指定是否格式化
     *
     * @param object 需转换的目标对象
     * @param format 是否格式化
     * @return
     */
    public static String json(Object object, boolean format) {
        if (format) {
            return JSON.toJSONString(object, SerializerFeature.WriteNonStringKeyAsString, SerializerFeature.WriteNullStringAsEmpty,
                    SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue, SerializerFeature.PrettyFormat);
        } else {
            return JSON.toJSONString(object, SerializerFeature.WriteNonStringKeyAsString, SerializerFeature.WriteNullStringAsEmpty,
                    SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue);
        }
    }

    /**
     * 创建hashMap
     *
     * @param args
     * @return
     */
    public static <K, V> Map<K, V> newHashMap(Object... args) {
        HashMap paramMap = new HashMap();
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                paramMap.put(args[i], args[++i]);
            }
        }
        return paramMap;
    }

    /**
     * 创建arraylist
     *
     * @param args
     * @return
     */
    public static <V> List<V> newArrayList(Object... args) {
        ArrayList list = new ArrayList();
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                list.add(args[i]);
            }
        }
        return list;
    }

    /**
     * 生成固定长度的字符，不足指定长度，用addPre进行补偿
     *
     * @param target 原字符
     * @param length 长度
     * @param addPre 需要补偿的字符
     * @param isLeft 指定是否在左边补偿，如果为false表示在右边补偿
     * @return
     */
    public static String generateCode(String target, int length, String addPre, boolean isLeft) {
        if (StringUtils.isNotEmpty(target) && target.length() < length) {
            int plus = length - target.length();
            StringBuilder pre = new StringBuilder();
            for (int i = 0; i < plus; i++) {
                pre.append(addPre);
            }
            return isLeft ? (pre.append(target).toString()) : (target + pre.toString());
        }
        return target;
    }

}
