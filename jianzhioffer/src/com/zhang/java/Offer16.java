package com.zhang.java;

/**
 * @Date 2022/3/17 19:14
 * @Author zsy
 * @Description 实现 pow(x, n) ，即计算 x 的 n 次幂函数（即，x^n）。不得使用库函数，同时不需要考虑大数问题。
 * <p>
 * 输入：x = 2.00000, n = 10
 * 输出：1024.00000
 * <p>
 * 输入：x = 2.10000, n = 3
 * 输出：9.26100
 * <p>
 * 输入：x = 2.00000, n = -2
 * 输出：0.25000
 * 解释：2^(-2) = 1/(2^2) = 1/4 = 0.25
 */
public class Offer16 {
    public static void main(String[] args) {
        Offer16 offer16 = new Offer16();
        System.out.println(offer16.myPow(-2, -3));
    }

    /**
     * 非递归快速幂，要注意n溢出即取最小值的情况
     *
     * @param x
     * @param n
     * @return
     */
    public double myPow(double x, int n) {
        if (n == 0 || x == 1) {
            return 1;
        }

        //使用long避免n取得int类型的最小值-(2^31)时，对n取反导致溢出
        long a = n;
        double result = 1;
        //如果a小于0，则需要进行调整
        if (a < 0) {
            x = 1 / x;
            a = -a;
        }
        while (a != 0) {
            if ((a & 1) == 1) {
                result = result * x;
            }
            x = x * x;
            a = a >> 1;
        }

        return result;
    }
}
