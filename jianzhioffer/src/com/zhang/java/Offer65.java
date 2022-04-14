package com.zhang.java;

/**
 * @Date 2022/4/9 9:15
 * @Author zsy
 * @Description 写一个函数，求两个整数之和，要求在函数体内不得使用 “+”、“-”、“*”、“/” 四则运算符号。
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
        System.out.println(offer65.add(-3, 5));
    }

    /**
     * 时间复杂度O(1)(最多循环int的长度32位)，空间复杂的O(1）
     * 不使用加减乘除，则使用位运算
     * a和b相加，当前位的值为a ^ b，进位的值为(a & b) << 1
     * 当前位的值和进位的值循环相加，直至进位的值为0，得到a+b的值
     *
     * @param a
     * @param b
     * @return
     */
    public int add(int a, int b) {
        int carry;

        //当进位的值为0时，跳出循环
        while (b != 0) {
            //进位的值
            carry = (a & b) << 1;
            //当前位的值
            a = a ^ b;
            b = carry;
        }

        return a;
    }
}
