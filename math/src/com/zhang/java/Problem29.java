package com.zhang.java;

/**
 * @Date 2023/1/5 12:14
 * @Author zsy
 * @Description 两数相除 Facebook面试题 美团面试题 加减乘除类比Problem43、Problem415、Add36Strings、BigNumberSubtract、Subtract36Strings 位运算类比Problem190、Problem191、Problem201、Problem271、Problem371、Problem405、Problem461、Problem477、Problem1290、Offer15、Offer64、Offer65、IpToInt
 * 给定两个整数，被除数 dividend 和除数 divisor。将两数相除，要求不使用乘法、除法和 mod 运算符。
 * 返回被除数 dividend 除以除数 divisor 得到的商。
 * 整数除法的结果应当截去（truncate）其小数部分，例如：truncate(8.345) = 8 以及 truncate(-2.7335) = -2
 * <p>
 * 输入: dividend = 10, divisor = 3
 * 输出: 3
 * 解释: 10/3 = truncate(3.33333..) = truncate(3) = 3
 * <p>
 * 输入: dividend = 7, divisor = -3
 * 输出: -2
 * 解释: 7/-3 = truncate(-2.33333..) = -2
 * <p>
 * 被除数和除数均为 32 位有符号整数。
 * 除数不为 0。
 * 假设我们的环境只能存储 32 位有符号整数，其数值范围是 [−2^31, 2^31 − 1]。
 * 本题中，如果除法结果溢出，则返回 2^31 − 1。
 */
public class Problem29 {
    public static void main(String[] args) {
        Problem29 problem29 = new Problem29();
        System.out.println(problem29.divide(60, 8));
        System.out.println(problem29.divide(10, -3));
        System.out.println(problem29.divide(2147483647, 3));
    }

    /**
     * 快速乘思想
     * 不使用乘除和mod，则使用加减法，使用位运算左移，相当于乘2
     * dividend每次从减去1个divisor开始找能够减去的最大divisor个数，即减去(1 << bitCount)个divisor，
     * 直至dividend小于divisor(dividend和divisor都是正数的情况)
     * 例如：60/8 ==> 60 = 8*4 + 8*2 + 8*1 + 4 ==> 60/8 = 7
     * 注意：将dividend和divisor都转化为负数进行运算，得到结果之后再添加正负号，避免int正数运算越界问题
     * 时间复杂度O(log(dividend))=O(32)=O(1)，空间复杂度O(1) (int范围最多只有32位)
     *
     * @param dividend 被除数
     * @param divisor  除数
     * @return
     */
    public int divide(int dividend, int divisor) {
        //除数不能为0，如果为0，返回-1
        if (divisor == 0) {
            return -1;
        }

        //被除数为0，除法结果为0，返回0
        if (dividend == 0) {
            return 0;
        }

        //特殊情况处理，被除数为int表示的最小值，除数为-1，除法结果在int范围内溢出，返回int表示的最大值
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }

        //除法结果标志位，1为正，-1为负
        int sign = 1;

        if ((dividend > 0 && divisor < 0) || (dividend < 0 && divisor > 0)) {
            sign = -1;
        }

        //除数和被除数都转为负数进行相除，避免int正数运算溢出
        if (dividend > 0) {
            dividend = -dividend;
        }

        if (divisor > 0) {
            divisor = -divisor;
        }

        //除法结果
        int result = 0;

        //dividend每次从减去1个divisor开始找能够减去的最大divisor个数，即减去(1 << bitCount)个divisor
        //因为dividend和divisor都是负数，所以要保证dividend始终小于等于divisor
        while (dividend <= divisor) {
            //减去divisor的个数，(1<<bitCount)个
            int bitCount = 0;

            //找能够减去的最大divisor个数
            //注意：divisor最多只能右移31位，并且始终保证divisor*(1<<(bitCount+1))为负数，避免int溢出
            while ((bitCount + 1) <= 31 && divisor * (1 << (bitCount + 1)) < 0 && dividend <= divisor * (1 << (bitCount + 1))) {
                bitCount++;
            }

            //result加上(1<<bitCount)，即减去(1<<bitCount)个divisor
            result = result + (1 << bitCount);
            //更新dividend，减去(1<<bitCount)个divisor
            dividend = dividend - divisor * (1 << bitCount);
        }

        return sign == 1 ? result : -result;
    }
}
