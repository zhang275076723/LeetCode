package com.zhang.java;

/**
 * @Date 2022/4/9 9:15
 * @Author zsy
 * @Description 不用加减乘除做加法 类比Problem136、Problem137、Problem260、Problem389、Offer56、Offer56_2 位运算类比Problem29、Problem190、Problem191、Problem201、Problem271、Problem371、Problem405、Problem461、Problem477、Problem1290、Offer15、Offer64、IpToInt 同Problem371
 * 写一个函数，求两个整数之和，要求在函数体内不得使用 “+”、“-”、“*”、“/” 四则运算符号。
 * <p>
 * 输入: a = 1, b = 1
 * 输出: 2
 * <p>
 * a, b 均可能是负数或 0
 * 结果不会溢出 32 位整数
 */
public class Offer65 {
    public static void main(String[] args) {
        Offer65 offer65 = new Offer65();
        System.out.println(offer65.add(13, 15));
    }

    /**
     * 不使用加减乘除，则使用位运算
     * a和b相加，二进制表示的每一位的非进位和为a ^ b，二进制表示的每一位的进位值为(a & b) << 1
     * 当前位的值和进位的值循环相加，直至进位的值为0，得到a+b的值
     * 例如：13+15=28
     * 13=1101，15=1111
     * 非进位和a^b：0010(2)
     * 进位值(a&b)<<1：11010(26)
     * 2+26=28
     * 非进位和a^b：11000(24)
     * 进位值(a&b)<<1：100(4)
     * 24+4=28
     * 非进位和a^b：11100(28)
     * 进位值(a&b)<<1：0(0)
     * 当前进位值为0，则a^b值28为a+b的结果
     * 时间复杂度O(1)，空间复杂度O(1) (int的二进制表示最长为32位)
     *
     * @param a
     * @param b
     * @return
     */
    public int add(int a, int b) {
        //当进位为0时，当前位即为a+b结果
        while (b != 0) {
            //当前位
            int temp1 = a ^ b;
            //进位
            int temp2 = (a & b) << 1;

            a = temp1;
            b = temp2;
        }

        return a;
    }
}
