package com.zhang.java;

/**
 * @Date 2022/3/13 21:31
 * @Author zsy
 * @Description 写一个函数，输入 n ，求斐波那契（Fibonacci）数列的第 n 项（即 F(N)）。
 * 斐波那契数列的定义如下：
 * F(0) = 0,   F(1) = 1
 * F(N) = F(N - 1) + F(N - 2), 其中 N > 1.
 * 答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。
 */
public class Offer10 {
    public static void main(String[] args) {
        Offer10 offer10 = new Offer10();
        System.out.println(offer10.fib(48));
        System.out.println(offer10.fib2(48));
        System.out.println(offer10.fib3(48));
        System.out.println(offer10.fib4(48));
    }

    /**
     * 递归，有大量重复计算，需要优化
     *
     * @param n
     * @return
     */
    public int fib(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        return (fib(n - 1) + fib(n - 2)) % 1000000007;
    }

    /**
     * 动态规划，时间复杂度O(n)，空间复杂的O(n）
     *
     * @param n
     * @return
     */
    public int fib2(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }

        int[] dp = new int[n + 1];
        final int MOD = 1000000007;
        dp[0] = 0;
        dp[1] = dp[2] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = (dp[i - 1] + dp[i - 2]) % MOD;
        }
        return dp[n];
    }

    /**
     * 动态规划优化，时间复杂度O(n)，空间复杂的O(1）
     *
     * @param n
     * @return
     */
    public int fib3(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }

        int p = 1;
        int q = 1;
        int result = 0;
        final int MOD = 1000000007;
        for (int i = 2; i < n; i++) {
            result = (p + q) % MOD;
            p = q;
            q = result;
        }
        return result;
    }

    /**
     * 矩阵快速幂，时间复杂度O(logn)，空间复杂的O(1）
     * [ F(n) ]          [1  1] ^ (n-1)       [F(1)]
     * [F(n-1)]    =    [1  0]          *    [F(0)]
     *
     * @param n
     * @return
     */
    public int fib4(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }

        int[][] a = new int[][]{{1, 1}, {1, 0}};
        int[][] fib = quickPow2D(a, n - 1);
        return fib[0][0];
    }

    /**
     * 递归快速幂
     *
     * @param a
     * @param n
     * @return
     */
    public int quickPow(int a, int n) {
        if (n == 0) {
            return 1;
        }

        if (n % 2 == 0) {
            int temp = quickPow(a, n / 2) % 1000000007;
            return temp * temp % 1000000007;
        } else {
            return quickPow(a, n - 1) * a % 1000000007;
        }
    }

    /**
     * 非递归快速幂
     *
     * @param a
     * @param n
     * @return
     */
    public int quickPow2(int a, int n) {
        int result = 1;
        final int MOD = 1000000007;
        while (n > 0) {
            //如果末位为1
            if ((n & 1) == 1) {
                result = result * a % MOD;
            }
            a = a * a;
            n = n >> 1;
        }
        return result;
    }

    /**
     * 二维递归快速幂
     *
     * @param a
     * @param n
     * @return
     */
    public int[][] quickPow2D(int[][] a, int n) {
        if (n == 0) {
            return new int[][]{{1, 0}, {0, 1}};
        }

        if (n % 2 == 0) {
            int[][] temp = quickPow2D(a, n / 2);
            return multiply2D(temp, temp);
        } else {
            return multiply2D(quickPow2D(a, n - 1), a);
        }
    }

    /**
     * 二维非递归快速幂
     *
     * @param a
     * @param n
     * @return
     */
    public int[][] quickPow2D2(int[][] a, int n) {
        int[][] result = new int[][]{{1, 0}, {0, 1}};
        while (n > 0) {
            if ((n & 1) == 1) {
                result = multiply2D(result, a);
            }
            a = multiply2D(a, a);
            n = n >> 1;
        }
        return result;
    }

    /**
     * 2x2矩阵乘法
     *
     * @param a
     * @param b
     * @return
     */
    public int[][] multiply2D(int[][] a, int[][] b) {
        int[][] result = new int[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                //在相乘时必须转为long，否则有可能溢出
                result[i][j] = (int) (((long) a[i][0] * b[0][j] + (long) a[i][1] * b[1][j]) % 1000000007);
            }
        }
        return result;
    }
}
