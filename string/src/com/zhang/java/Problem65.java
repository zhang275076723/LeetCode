package com.zhang.java;

/**
 * @Date 2023/6/17 10:05
 * @Author zsy
 * @Description 有效数字 类比Problem520 同Offer20
 * 有效数字（按顺序）可以分成以下几个部分：
 * 一个 小数 或者 整数
 * （可选）一个 'e' 或 'E' ，后面跟着一个 整数
 * 小数（按顺序）可以分成以下几个部分：
 * （可选）一个符号字符（'+' 或 '-'）
 * 下述格式之一：
 * 至少一位数字，后面跟着一个点 '.'
 * 至少一位数字，后面跟着一个点 '.' ，后面再跟着至少一位数字
 * 一个点 '.' ，后面跟着至少一位数字
 * 整数（按顺序）可以分成以下几个部分：
 * （可选）一个符号字符（'+' 或 '-'）
 * 至少一位数字
 * 部分有效数字列举如下：["2", "0089", "-0.1", "+3.14", "4.", "-.9", "2e10", "-90E3", "3e+7",
 * "+6e-1", "53.5e93", "-123.456e789"]
 * 部分无效数字列举如下：["abc", "1a", "1e", "e3", "99e2.5", "--6", "-+3", "95a54e53"]
 * 给你一个字符串 s ，如果 s 是一个 有效数字 ，请返回 true 。
 * <p>
 * 输入：s = "0"
 * 输出：true
 * <p>
 * 输入：s = "e"
 * 输出：false
 * <p>
 * 输入：s = "."
 * 输出：false
 * <p>
 * 1 <= s.length <= 20
 * s 仅含英文字母（大写和小写），数字（0-9），加号 '+' ，减号 '-' ，或者点 '.' 。
 */
public class Problem65 {
    public static void main(String[] args) {
        Problem65 problem65 = new Problem65();
        String s = "1.";
        System.out.println(problem65.isNumber(s));
    }

    /**
     * 模拟
     * 使用三个标志位
     * '.'正确出现的情况：在e和E之前，且只能出现一次
     * 'e'/'E'正确出现的情况：e和E之前有数字，之后有整数，且只能出现一次
     * '+'/'-'正确出现的情况：出现在开头，或出现在e/E的后一位
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
                //出现'e'/'E'时，如果之前出现过'e'/'E'或之前没有出现数字，则不合法
                if (eFlag || !numFlag) {
                    return false;
                }

                eFlag = true;
                //出现'e'/'E'之后，将numFlag置为false，因为'e'/'E'之后必须出现数字
                numFlag = false;
            } else if ((c == '+' || c == '-') &&
                    (i == 0 || s.charAt(i - 1) == 'e' || s.charAt(i - 1) == 'E')) {
                //出现'+'/'-'，则要么出现在第一位，要么出现在'e'/'E'的后一位
            } else {
                //其余的都是不合法的情况，直接返回false
                return false;
            }
        }

        //遍历完成之后判断是否有数字
        return numFlag;
    }
}
