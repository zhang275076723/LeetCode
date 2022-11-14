package com.zhang.java;

/**
 * @Date 2022/10/19 09:31
 * @Author zsy
 * @Description Pow(x, n) 同Offer16
 * 实现 pow(x, n) ，即计算 x 的整数 n 次幂函数（即，x^n ）。
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
public class Problem50 {
    public static void main(String[] args) {
        Problem50 problem50 = new Problem50();
        double x = 2.0;
        int n = -2;
        System.out.println(problem50.myPow(x, n));
        System.out.println(problem50.myPow2(x, n));
    }

    /**
     * 非递归快速幂
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param x
     * @param n
     * @return
     */
    public double myPow(double x, int n) {
        if (n == 0) {
            return 1.0;
        }

        double result = 1.0;
        //使用long避免n取int最小值时，取反溢出
        long a = n;

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
     * 递归快速幂
     * 时间复杂度O(logn)，空间复杂度O(logn)
     *
     * @param x
     * @param n
     * @return
     */
    public double myPow2(double x, int n) {
        if (n == 0) {
            return 1.0;
        }

        //使用long避免n取int最小值时，取反溢出
        long a = n;

        if (n > 0) {
            return quickPow(x, a);
        } else {
            return 1.0 / quickPow(x, -a);
        }
    }

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
