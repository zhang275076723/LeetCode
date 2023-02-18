package com.zhang.java;

/**
 * @Date 2023/1/5 12:14
 * @Author zsy
 * @Description 两数相除 Facebook面试题 美团面试题 加减乘除类比Problem43、Problem415 类比Offer64、Offer65
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
     * 不使用乘除和mod，则使用加减法，使用位运算左移，相当于等2
     * 每次从bit为1开始找最大的能够减去divisor*bit的bit，dividend = dividend - (bit * divisor)，
     * 直至dividend小于divisor(dividend和divisor都是正数的情况)
     * 例如：60/8 ==> 60 = 8*4 + 8*2 + 8*1 + 4 ==> 60/8 = 7
     * 注意：将dividend和divisor都转化为负数进行运算，得到结果之后再添加正负号
     * 时间复杂度O(log(dividend))=O(1)，空间复杂度O(1)
     *
     * @param dividend 被除数
     * @param divisor  除数
     * @return
     */
    public int divide(int dividend, int divisor) {
        //除数不能为0
        if (divisor == 0) {
            return -1;
        }

        //被除数为0，除法结果为0
        if (dividend == 0) {
            return 0;
        }

        //特殊情况处理，除法结果溢出，返回int表示的最大值
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }

        //特殊情况处理，被除数为int表示的最小值，除数为1，返回int表示的最小值
        if (dividend == Integer.MIN_VALUE && divisor == 1) {
            return Integer.MIN_VALUE;
        }

        //除法结果标志位，1为正，-1为负
        int sign = 1;

        if ((dividend > 0 && divisor < 0) || (dividend < 0 && divisor > 0)) {
            sign = -1;
        }

        //除数和被除数都转为负数进行相除，避免溢出
        if (dividend > 0) {
            dividend = -dividend;
        }

        if (divisor > 0) {
            divisor = -divisor;
        }

        //除法结果
        int result = 0;

        //dividend每次从bit为1开始找最大的能够减去divisor*bit的bit，即dividend=bit*divisor+余数
        //因为dividend和divisor都是负数，所以要保证dividend小于等于divisor
        while (dividend <= divisor) {
            //从1开始，每次左移一位，表示乘上2
            int bit = 1;

            //判断bit*divisor是否大于等于dividend(因为dividend和divisor都是负数)，
            //如果大于等于，则result加上bit，dividend减去bit*divisor
            //(bit << 1) * divisor < 0，避免相乘时int溢出
            while ((bit << 1) * divisor < 0 && dividend <= (bit << 1) * divisor) {
                bit = bit << 1;

                //bit在int表示的范围之内最大值为2^30，如果再往左移一位，则溢出变为负数
                if (bit == (1 << 30)) {
                    break;
                }
            }

            //dividend减去bit*divisor
            dividend = dividend - (bit * divisor);
            //除法结果加上bit
            result = result + bit;
        }

        return sign == 1 ? result : -result;
    }
}
