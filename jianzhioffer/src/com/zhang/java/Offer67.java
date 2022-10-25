package com.zhang.java;

/**
 * @Date 2022/4/9 10:44
 * @Author zsy
 * @Description 把字符串转换成整数 类比Problem7 同Problem8
 * 写一个函数 StrToInt，实现把字符串转换成整数这个功能。
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
 * <p>
 * 说明：
 * 假设我们的环境只能存储 32 位大小的有符号整数，那么其数值范围为[−2^31, 2^31 − 1]。
 * 如果数值超过这个范围，请返回 INT_MAX (2^31 − 1) 或INT_MIN (−2^31) 。
 */
public class Offer67 {
    public static void main(String[] args) {
        Offer67 offer67 = new Offer67();
        String str = "    +0004193 with words";
        System.out.println(offer67.strToInt(str));
    }

    /**
     * 模拟
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param str
     * @return
     */
    public int strToInt(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        int index = 0;

        //去除前导空格
        while (index < str.length() && str.charAt(index) == ' ') {
            index++;
        }

        //都是空格的情况
        if (index == str.length()) {
            return 0;
        }

        //符号标志位，1：正，-1：负
        int flag = 1;

        if (str.charAt(index) == '+') {
            index++;
        } else if (str.charAt(index) == '-') {
            flag = -1;
            index++;
        }

        int num = 0;

        while (index < str.length()) {
            char c = str.charAt(index);

            if (c >= '0' && c <= '9') {
                //上溢出
                if (num > Integer.MAX_VALUE / 10 ||
                        (num == Integer.MAX_VALUE / 10 && (c - '0') >= Integer.MAX_VALUE % 10)) {
                    return Integer.MAX_VALUE;
                }

                //下溢出
                if (num < Integer.MIN_VALUE / 10 ||
                        (num == Integer.MIN_VALUE / 10 && (c - '0') >= -(Integer.MIN_VALUE % 10))) {
                    return Integer.MIN_VALUE;
                }

                num = num * 10 + (c - '0') * flag;

                index++;
            } else {
                //当前不是数字，则直接返回
                return num;
            }
        }

        //遍历到末尾，直接返回
        //不能写成return flag == 1 ? num : -num，因为int类型的最大值和最小值不一样，需要每次累加都判断是否溢出
        return num;
    }
}
