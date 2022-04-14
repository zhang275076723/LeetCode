package com.zhang.java;

/**
 * @Date 2022/3/18 16:37
 * @Author zsy
 * @Description 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）
 * 字符串 s 仅含英文字母（大写和小写），数字（0-9），加号 '+' ，减号 '-' ，空格 ' ' 或者点 '.'
 * <p>
 * 数值（按顺序）可以分成以下几个部分：
 * 1、若干空格
 * 2、一个 小数 或者 整数
 * 3、（可选）一个 'e' 或 'E' ，后面跟着一个 整数
 * 4、若干空格
 * <p>
 * 小数（按顺序）可以分成以下几个部分：
 * 1、（可选）一个符号字符（'+' 或 '-'）
 * 2、下述格式之一：
 * 2.1至少一位数字，后面跟着一个点 '.'
 * 2.2至少一位数字，后面跟着一个点 '.' ，后面再跟着至少一位数字
 * 2.3一个点 '.' ，后面跟着至少一位数字
 * <p>
 * 整数（按顺序）可以分成以下几个部分：
 * 1、（可选）一个符号字符（'+' 或 '-'）
 * 2、至少一位数字
 * <p>
 * 部分数值列举：返回true
 * ["+100", "5e2", "-123", "3.1416", "-1E-16", "0123"]
 * <p>
 * 部分非数值列举：返回false
 * ["12e", "1a3.14", "1.2.3", "+-5", "12e+5.4"]
 */
public class Offer20 {
    public static void main(String[] args) {
        Offer20 offer20 = new Offer20();
        System.out.println(offer20.isNumber(" +.3e-5  "));
    }

    /**
     * '.'正确出现的情况：在e和E之前，且只能出现一次
     * 'e'/'E'正确出现的情况：e和E之前有数字，之后有整数，且只能出现一次
     * '+'/'-'正确出现的情况：在开头或e和E的后一位
     *
     * @param s
     * @return
     */
    public boolean isNumber(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }

        //去掉首尾空格
        s = s.trim();
        boolean numFlag = false;
        boolean dotFlag = false;
        boolean eFlag = false;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                numFlag = true;
            } else if (s.charAt(i) == '.' && !dotFlag && !eFlag) {
                //如果为'.'，则需要之前没有'.'，且'.'在e和E之前
                dotFlag = true;
            } else if ((s.charAt(i) == 'e' || s.charAt(i) == 'E') && !eFlag && numFlag) {
                //如果为'e'/'E'，则需要之前没出现过'e'/'E'，且之前有数字
                eFlag = true;
                //出现'e'/'E'之后，将numFlag置位false，因为后面需要判断是否是整数
                numFlag = false;
            } else if ((s.charAt(i) == '+' || s.charAt(i) == '-') &&
                    (i == 0 || s.charAt(i - 1) == 'e' || s.charAt(i - 1) == 'E')) {
                //如果为'+'/'-'，则要么出现在第一位，要么在'e'/'E'的后一位
            } else {
                return false;
            }
        }

        return numFlag;
    }
}
