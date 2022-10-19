package com.zhang.java;

/**
 * @Date 2022/3/17 19:14
 * @Author zsy
 * @Description 数值的整数次方 同Problem50
 * 实现 pow(x, n) ，即计算 x 的 n 次幂函数（即，x^n）。不得使用库函数，同时不需要考虑大数问题。
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
 * <p>
 * -100.0 < x < 100.0
 * -2^31 <= n <= 2^31-1
 * -10^4 <= x^n <= 10^4
 */
public class Offer16 {
    public static void main(String[] args) {
        Offer16 offer16 = new Offer16();
        System.out.println(offer16.myPow(-2, -3));
        System.out.println(offer16.myPow2(-2, -3));
    }

    /**
     * 递归快速幂，类比快速乘
     * 注意n溢出，即取最小值的情况
     * 时间复杂度O(logn)，空间复杂度O(logn)
     *
     * @param x
     * @param n
     * @return
     */
    public double myPow(double x, int n) {
        if (n == 0) {
            return 1.0;
        }

        //使用long避免n取得int类型的最小值-(2^31)时，对n取反导致溢出
        long a = n;

        if (n > 0) {
            return quickPow(x, a);
        } else {
            return 1.0 / quickPow(x, -a);
        }
    }

    /**
     * 非递归快速幂，类比快速乘
     * 注意n溢出，即取最小值的情况
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param x
     * @param n
     * @return
     */
    public double myPow2(double x, int n) {
        if (n == 0 || x == 1) {
            return 1.0;
        }

        //使用long避免n取得int类型的最小值-(2^31)时，对n取反导致溢出
        long a = n;
        double result = 1;

        //如果a小于0，则需要进行调整
        if (a < 0) {
            a = -a;
        }

        while (a != 0) {
            if ((a & 1) == 1) {
                result = result * x;
            }

            x = x * x;
            a = a >> 1;
        }

        if (n > 0) {
            return result;
        } else {
            return 1.0 / result;
        }
    }

    /**
     * 使用long，避免int取最小值的情况下溢出
     *
     * @param x
     * @param n
     * @return
     */
    private double quickPow(double x, long n) {
        if (n == 0) {
            return 1.0;
        }

        if (n % 2 == 0) {
            double temp = quickPow(x, n / 2);
            return temp * temp;
        } else {
            return quickPow(x, n - 1) * x;
        }
    }
}
