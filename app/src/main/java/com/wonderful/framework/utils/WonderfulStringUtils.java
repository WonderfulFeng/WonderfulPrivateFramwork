package com.wonderful.framework.utils;

/**
 * Created by Administrator on 2017/8/30.
 */

public class WonderfulStringUtils {
    /**
     * 判断一个文本是否为空为null
     */
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str) || "null".equals(str.toLowerCase())) {
            return true;
        }
        return false;
    }
}
