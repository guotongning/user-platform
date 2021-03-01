package com.ning.commons.utils;

/**
 * @Author: nicholas
 * @Date: 2021/3/1 21:29
 * @Descreption:
 */
public class StringUtils {
    public static boolean isEmpty(String string) {
        return string == null || "".equals(string);
    }
}
