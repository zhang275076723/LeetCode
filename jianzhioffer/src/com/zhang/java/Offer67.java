package com.zhang.java;

/**
 * @Date 2022/4/9 10:44
 * @Author zsy
 * @Description 写一个函数 StrToInt，实现把字符串转换成整数这个功能。
 * 不能使用 atoi 或者其他类似的库函数。
 * 首先，该函数会根据需要丢弃无用的开头空格字符，直到寻找到第一个非空格的字符为止。
 * 当我们寻找到的第一个非空字符为正或者负号时，则将该符号与之后面尽可能多的连续数字组合起来，
 * 作为该整数的正负号；
 * 假如第一个非空字符是数字，则直接将其与之后连续的数字字符组合起来，形成整数。
 * <p>
 * 该字符串除了有效的整数部分之后也可能会存在多余的字符，这些字符可以被忽略，它们对于函数不应该造成影响。
 * 注意：假如该字符串中的第一个非空格字符不是一个有效整数字符、字符串为空或字符串仅包含空白字符时，
 * 则你的函数不需要进行转换。
 * 在任何情况下，若函数不能进行有效的转换时，请返回 0。
 * <p>
 * 说明：
 * 假设我们的环境只能存储 32 位大小的有符号整数，那么其数值范围为[−2^31, 2^31 − 1]。
 * 如果数值超过这个范围，请返回 INT_MAX (2^31 − 1) 或INT_MIN (−2^31) 。
 * <p>
 * 输入: "42"
 * 输出: 42
 * <p>
 * 输入: "   -42"
 * 输出: -42
 * 解释: 第一个非空白字符为 '-', 它是一个负号。
 * 我们尽可能将负号与后面所有连续出现的数字组合起来，最后得到 -42 。
 * <p>
 * 输入: "4193 with words"
 * 输出: 4193
 * 解释: 转换截止于数字 '3' ，因为它的下一个字符不为数字。
 * <p>
 * 输入: "words and 987"
 * 输出: 0
 * 解释: 第一个非空字符是 'w', 但它不是数字或正、负号。因此无法执行有效的转换。
 * <p>
 * 输入: "-91283472332"
 * 输出: -2147483648
 * 解释: 数字 "-91283472332" 超过 32 位有符号整数范围。因此返回 INT_MIN (−2^31) 。
 */
public class Offer67 {
    public static void main(String[] args) {
        Offer67 offer67 = new Offer67();
        String str = "  -4193 with words ";
        System.out.println(offer67.strToInt(str));
    }

    /**
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param str
     * @return
     */
    public int strToInt(String str) {
        //字符串为空，或长度为0时，返回0
        if ("".equals(str) || str.length() == 0) {
            return 0;
        }

        //去除前后的空格 trim()
        int start = 0;
        int end = str.length() - 1;
        while (start <= end && str.charAt(start) == ' ') {
            start++;
        }
        while (start <= end && str.charAt(end) == ' ') {
            end--;
        }
        //只包含空白字符
        if (start > end) {
            return 0;
        }

        char c = str.charAt(start);
        //不以数字或+-开头
        if ((c < '0' || c > '9') && c != '+' && c != '-') {
            return 0;
        }

        int result = 0;
        //正负号标志
        boolean isNegative = false;
        //当前遍历索引
        int index = start;
        //溢出判断 overflow = 2147483647 / 10 = 214748364
        int overflow = Integer.MAX_VALUE / 10;

        //如果第一位是符号的处理
        if (c == '+') {
            index++;
        } else if (c == '-') {
            isNegative = true;
            index++;
        }

        //找到数字的结尾索引
        while (index <= end) {
            c = str.charAt(index);
            if (c < '0' || c > '9') {
                break;
            }

            //溢出判断 max = 2147483647  min = -2147483648  overflow = 214748364
            if (result > overflow || (result == overflow && c > '7')){
                return isNegative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }

            result = 10 * result + c - '0';
            index++;
        }

        //正负号处理
        if(isNegative){
            result = -result;
        }

        return result;
    }
}
