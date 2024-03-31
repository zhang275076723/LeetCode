package com.zhang.java;

/**
 * @Date 2022/4/8 16:06
 * @Author zsy
 * @Description 求1+2+…+n 阿里面试题 位运算类比Problem29、Problem136、Problem137、Problem190、Problem191、Problem201、Problem231、Problem260、Problem271、Problem326、Problem342、Problem371、Problem389、Problem405、Problem461、Problem477、Problem645、Problem898、Problem1290、Offer15、Offer56、Offer56_2、Offer65、IpToInt
 * 求 1+2+...+n ，要求不能使用乘除法、for、while、if、else、switch、case等关键字及条件判断语句（A?B:C）。
 * <p>
 * 输入: n = 3
 * 输出: 6
 * <p>
 * 输入: n = 9
 * 输出: 45
 * <p>
 * 1 <= n <= 10000
 */
public class Offer64 {
    public static void main(String[] args) {
        Offer64 offer64 = new Offer64();
        System.out.println(offer64.sumNums(9));
        System.out.println(offer64.sumNums2(9));
    }

    /**
     * 不使用乘除法，则使用加减法和位运算
     * 不使用for、while循环，则使用递归
     * 不使用if、else、switch、case及条件判断语句（A?B:C），则使用逻辑运算短路与&&
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public int sumNums(int n) {
        int result = 0;

        //短路与，加减法，递归
        boolean flag = n > 0 && (result = n + sumNums(n - 1)) > 0;

        return result;
    }

    /**
     * 快速乘，位运算乘法，不使用乘号，类比快速幂
     * 因为 n <= 10000，所以n最多为14位，因为2^13-1<10000，2^14-1>10000
     * 1 + 2 + .. + n = n(n+1)/2
     * 时间复杂度O(logn)，空间复杂度O(1）
     *
     * @param n
     * @return
     */
    public int sumNums2(int n) {
        int result = 0;
        int a = n;
        int b = n + 1;
        boolean flag;

        //从右往左第1位
        flag = ((b & 1) == 1) && (result = result + a) > 0;
        b = b >>> 1;
        a = a << 1;

        //从右往左第2位
        flag = ((b & 1) == 1) && (result = result + a) > 0;
        b = b >>> 1;
        a = a << 1;

        //从右往左第3位
        flag = ((b & 1) == 1) && (result = result + a) > 0;
        b = b >>> 1;
        a = a << 1;

        //从右往左第4位
        flag = ((b & 1) == 1) && (result = result + a) > 0;
        b = b >>> 1;
        a = a << 1;

        //从右往左第5位
        flag = ((b & 1) == 1) && (result = result + a) > 0;
        b = b >>> 1;
        a = a << 1;

        //从右往左第6位
        flag = ((b & 1) == 1) && (result = result + a) > 0;
        b = b >>> 1;
        a = a << 1;

        //从右往左第7位
        flag = ((b & 1) == 1) && (result = result + a) > 0;
        b = b >>> 1;
        a = a << 1;

        //从右往左第8位
        flag = ((b & 1) == 1) && (result = result + a) > 0;
        b = b >>> 1;
        a = a << 1;

        //从右往左第9位
        flag = ((b & 1) == 1) && (result = result + a) > 0;
        b = b >>> 1;
        a = a << 1;

        //从右往左第10位
        flag = ((b & 1) == 1) && (result = result + a) > 0;
        b = b >>> 1;
        a = a << 1;

        //从右往左第11位
        flag = ((b & 1) == 1) && (result = result + a) > 0;
        b = b >>> 1;
        a = a << 1;

        //从右往左第12位
        flag = ((b & 1) == 1) && (result = result + a) > 0;
        b = b >>> 1;
        a = a << 1;

        //从右往左第13位
        flag = ((b & 1) == 1) && (result = result + a) > 0;
        b = b >>> 1;
        a = a << 1;

        //从右往左第14位
        flag = ((b & 1) == 1) && (result = result + a) > 0;
        b = b >>> 1;
        a = a << 1;

        result = result >> 1;

        return result;
    }

    /**
     * 快速乘，位运算乘法，不使用乘号，使用位运算实现a、b相乘 类比快速幂
     * a * b：如果b的二进制第i位为1，则对结果的贡献为a * (1<<i)，即a<<i，将所有贡献相加即为a、b相乘结果
     * 例如：3 * 7 = 21
     * 7=111
     * 3*1=3  (7的二进制表示的第三位)
     * 3*2=6  (7的二进制表示的第二位)
     * 3*4=12 (7的二进制表示的第一位)
     * 3*7=3+6+12=21
     * 时间复杂度O(logn)，空间复杂度O(1) (n=b)
     *
     * @param a
     * @param b
     * @return
     */
    public int quickMultiply(int a, int b) {
        int result = 0;

        while (b != 0) {
            if ((b & 1) == 1) {
                result = result + a;
            }

            a = a << 1;
            b = b >>> 1;
        }

        return result;
    }
}
