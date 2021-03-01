package com.ning.web.mvc.utils;

/**
 * @Author: nicholas
 * @Date: 2021/2/28 17:19
 * @Descreption:
 */
public class RequestMappingUtils {

    /**
     * 给requestMapping拼上开头的“/”
     *
     * @param requestMapping 请求路径
     * @return 正确的请求路径
     */
    public static String handleRequestMapping(String requestMapping) {
        if (requestMapping == null || "".equals(requestMapping)) {
            return "";
        }
        if (!requestMapping.startsWith("/")) {
            return "/" + requestMapping;
        }
        return requestMapping;
    }
}
