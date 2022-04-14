package com.zhang.java;

/**
 * @Date 2022/4/8 16:06
 * @Author zsy
 * @Description 求 1+2+...+n ，
 * 要求不能使用乘除法、for、while、if、else、switch、case等关键字及条件判断语句（A?B:C）。
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
     * 时间复杂度O(n)，空间复杂的O(n）
     * 不使用乘除法，则使用加减法和位运算
     * 不使用for、while，则使用递归
     * 不使用if、else、switch、case及条件判断语句（A?B:C），则使用短路与
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
     * 使用位运算乘法，快速乘法，因为 n <= 10000，所以n最多为14位，时间复杂度O(logn)，空间复杂的O(1）
     * 1 + 2 + .. + n = n(n+1)/2
     *
     * @param n
     * @return
     */
    public int sumNums2(int n) {
        int result = 0;
        int a = n;
        int b = n + 1;
        boolean flag;

        //1
        flag = (b & 1) == 1 && (result = result + a) > 0;
        b = b >> 1;
        a = a << 1;

        //2
        flag = (b & 1) == 1 && (result = result + a) > 0;
        b = b >> 1;
        a = a << 1;

        //3
        flag = (b & 1) == 1 && (result = result + a) > 0;
        b = b >> 1;
        a = a << 1;

        //4
        flag = (b & 1) == 1 && (result = result + a) > 0;
        b = b >> 1;
        a = a << 1;

        //5
        flag = (b & 1) == 1 && (result = result + a) > 0;
        b = b >> 1;
        a = a << 1;

        //6
        flag = (b & 1) == 1 && (result = result + a) > 0;
        b = b >> 1;
        a = a << 1;

        //7
        flag = (b & 1) == 1 && (result = result + a) > 0;
        b = b >> 1;
        a = a << 1;

        //8
        flag = (b & 1) == 1 && (result = result + a) > 0;
        b = b >> 1;
        a = a << 1;

        //9
        flag = (b & 1) == 1 && (result = result + a) > 0;
        b = b >> 1;
        a = a << 1;

        //10
        flag = (b & 1) == 1 && (result = result + a) > 0;
        b = b >> 1;
        a = a << 1;

        //11
        flag = (b & 1) == 1 && (result = result + a) > 0;
        b = b >> 1;
        a = a << 1;

        //12
        flag = (b & 1) == 1 && (result = result + a) > 0;
        b = b >> 1;
        a = a << 1;

        //13
        flag = (b & 1) == 1 && (result = result + a) > 0;
        b = b >> 1;
        a = a << 1;

        //14
        flag = (b & 1) == 1 && (result = result + a) > 0;
        b = b >> 1;
        a = a << 1;

        result = result >> 1;

        return result;
    }

    /**
     * 快速乘法，不使用乘法实现ab相乘，时间复杂度O(logn)，空间复杂的O(1）
     * a * b：如果b的二进制第i位为1，则对结果的贡献为a * (1<<i)，即a<<i，将所有贡献相加即为ab相乘结果
     *
     * @param a
     * @param b
     * @return
     */
    public int multiply(int a, int b) {
        int result = 0;

        while (b > 0) {
            if ((b & 1) == 1) {
                result = result + a;
            }
            b = b >> 1;
            a = a << 1;
        }

        return result;
    }
}
