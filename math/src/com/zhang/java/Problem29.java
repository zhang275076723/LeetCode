package com.zhang.java;

/**
 * @Date 2023/1/5 12:14
 * @Author zsy
 * @Description 两数相除 Facebook面试题 美团面试题 加减乘除类比Problem43、Problem166、Problem415、Add36Strings、BigNumberSubtract、Subtract36Strings 位运算类比Problem136、Problem137、Problem190、Problem191、Problem201、Problem231、Problem260、Problem271、Problem326、Problem342、Problem371、Problem389、Problem405、Problem461、Problem477、Problem645、Problem898、Problem1290、Offer15、Offer56、Offer56_2、Offer64、Offer65、IpToInt
 * 给你两个整数，被除数 dividend 和除数 divisor。
 * 将两数相除，要求 不使用 乘法、除法和取余运算。
 * 整数除法应该向零截断，也就是截去（truncate）其小数部分。
 * 例如，8.345 将被截断为 8 ，-2.7335 将被截断至 -2 。
 * 返回被除数 dividend 除以除数 divisor 得到的 商 。
 * 注意：假设我们的环境只能存储 32 位 有符号整数，其数值范围是 [−2^31,  2^31 − 1] 。
 * 本题中，如果商 严格大于 2^31 − 1 ，则返回 2^31 − 1 ；如果商 严格小于 -2^31 ，则返回 -2^31 。
 * <p>
 * 输入: dividend = 10, divisor = 3
 * 输出: 3
 * 解释: 10/3 = 3.33333.. ，向零截断后得到 3 。
 * <p>
 * 输入: dividend = 7, divisor = -3
 * 输出: -2
 * 解释: 7/-3 = -2.33333.. ，向零截断后得到 -2 。
 * <p>
 * -2^31 <= dividend, divisor <= 2^31 - 1
 * divisor != 0
 */
public class Problem29 {
    public static void main(String[] args) {
        Problem29 problem29 = new Problem29();
//        System.out.println(problem29.divide(60, 8));
//        System.out.println(problem29.divide(10, -3));
        System.out.println(problem29.divide(2147483647, 3));
    }

    /**
     * 快速乘思想
     * 不使用乘除和mod，则使用加减法，使用位运算左移，相当于乘2
     * dividend每次从减去2^0=1个divisor开始找能够减去的最大divisor个数，即减去2^bitCount个divisor，
     * dividend=dividend-divisor*2^bitCount，直至dividend小于divisor(dividend和divisor都是正数的情况)，
     * 最终得到的减去divisor的个数即为dividend/divisor的值
     * 注意：将dividend和divisor都转化为负数进行运算，得到结果之后再添加正负号，避免int正数运算越界问题
     * 时间复杂度O(log(dividend))=O(log(2^31))=O(1)，空间复杂度O(1) (int范围最多只有32位)
     * <p>
     * 例如：dividend=60，divisor=8
     * dividend=-60，divisor=-8，sign=-1
     * -60-(-8*2^2)=-60-(-8*4)=-28，即本次减去4个divisor，-28<=-8还能继续减
     * -28-(-8*2^1)=-28-(-8*2)=-12，即本次减去2个divisor，-12<=-8还能继续减
     * -12-(-8*2^0)=-12-(-8*1)=-4，即本次减去1个divisor，-4>-8，则找到了60/8的结果4+2+1=7
     *
     * @param dividend 被除数
     * @param divisor  除数
     * @return
     */
    public int divide(int dividend, int divisor) {
        //除数为0，则不合法，返回-1
        if (divisor == 0) {
            return -1;
        }

        //被除数为0，返回0
        if (dividend == 0) {
            return 0;
        }

        //特殊情况处理，被除数为int的最小值，除数为-1，则除法结果在int范围内溢出，返回int表示的最大值
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

        //dividend每次从减去2^0=1个divisor开始找能够减去的最大divisor个数，即减去2^bitCount个divisor
        //因为dividend和divisor都是负数，所以要保证dividend<=divisor
        while (dividend <= divisor) {
            //减去2^bitCount个divisor
            int bitCount = 0;

            //找能够减去的最大divisor个数
            //注意：divisor最多只能右移30位，因为32位int的最高位为符号位，不表示数字，
            //并且要保证divisor*(1<<(bitCount+1))为负数，即避免dividend减去2^bitCount个divisor在int范围内溢出
            while (bitCount + 1 <= 30 && divisor * (1 << (bitCount + 1)) < 0 && dividend <= divisor * (1 << (bitCount + 1))) {
                bitCount++;
            }

            //result加上2^bitCount，即减去2^bitCount个divisor
            result = result + (1 << bitCount);
            //更新dividend，减去2^bitCount个divisor
            dividend = dividend - divisor * (1 << bitCount);
        }

        return sign == 1 ? result : -result;
    }
}
