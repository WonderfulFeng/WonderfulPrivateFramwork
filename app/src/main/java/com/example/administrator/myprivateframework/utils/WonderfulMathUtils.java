package com.example.administrator.myprivateframework.utils;

/**
 * Created by Administrator on 2017/8/30.
 */

public class WonderfulMathUtils {
    /**
     * 按四舍五入保留小数位
     *
     * @param n 保留位数
     */
    public static double getRundNumber(double number, int n) {
        n = (int) Math.pow(10, n);
        number = (Math.round(number * n)) / (n * 1.0);
        return number;
    }
}
