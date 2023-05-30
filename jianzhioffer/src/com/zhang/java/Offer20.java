package com.zhang.java;

/**
 * @Date 2022/3/18 16:37
 * @Author zsy
 * @Description 表示数值的字符串
 * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）
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
 * <p>
 * 1 <= s.length <= 20
 * s 仅含英文字母（大写和小写），数字（0-9），加号 '+' ，减号 '-' ，空格 ' ' 或者点 '.' 。
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
     * 时间复杂度O(n)，空间复杂度O(1)
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

        //数字标志位
        boolean numFlag = false;
        //'.'标志位
        boolean dotFlag = false;
        //'e'/'E'标志位
        boolean eFlag = false;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c >= '0' && c <= '9') {
                numFlag = true;
            } else if (c == '.') {
                //出现'.'时，如果之前出现过'.'或'e'/'E'，则不合法
                if (dotFlag || eFlag) {
                    return false;
                }

                dotFlag = true;
            } else if (c == 'e' || c == 'E') {
                //出现'e'/'E'时，如果之前出现过'e'/'E'或没有出现数字，则不合法
                if (eFlag || !numFlag) {
                    return false;
                }

                eFlag = true;
                //出现'e'/'E'之后，将numFlag置位false，因为'e'/'E'之后必须出现数字
                numFlag = false;
            } else if ((c == '+' || c == '-') && (i == 0 || s.charAt(i - 1) == 'e' || s.charAt(i - 1) == 'E')) {
                //出现'+'/'-'，则要么出现在第一位，要么出现在'e'/'E'的后一位
            } else {
                //其余的都是不合法的情况，直接返回false
                return false;
            }
        }

        //判断是否为数字，则必须包含数字
        return numFlag;
    }
}
