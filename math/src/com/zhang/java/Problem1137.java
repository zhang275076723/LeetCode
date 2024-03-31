package com.zhang.java;

/**
 * @Date 2022/11/27 15:28
 * @Author zsy
 * @Description 第 N 个泰波那契数 矩阵快速幂类比Problem70、Problem509、Problem1641、Offer10、Offer10_2
 * 泰波那契序列 Tn 定义如下：
 * T0 = 0, T1 = 1, T2 = 1, 且在 n >= 0 的条件下 Tn+3 = Tn + Tn+1 + Tn+2
 * 给你整数 n，请返回第 n 个泰波那契数 Tn 的值。
 * <p>
 * 输入：n = 4
 * 输出：4
 * 解释：
 * T_3 = 0 + 1 + 1 = 2
 * T_4 = 1 + 1 + 2 = 4
 * <p>
 * 输入：n = 25
 * 输出：1389537
 * <p>
 * 0 <= n <= 37
 * 答案保证是一个 32 位整数，即 answer <= 2^31 - 1。
 */
public class Problem1137 {
    public static void main(String[] args) {
        Problem1137 problem1137 = new Problem1137();
        System.out.println(problem1137.tribonacci(25));
        System.out.println(problem1137.tribonacci2(25));
        System.out.println(problem1137.tribonacci3(25));
    }

    /**
     * 动态规划
     * dp[i]：Ti所表示的泰波那契数
     * dp[i] = dp[i-1] + dp[i-2] + dp[i-3]
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int tribonacci(int n) {
        if (n == 0) {
            return 0;
        }

        if (n == 1 || n == 2) {
            return 1;
        }

        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 1;

        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2] + dp[i - 3];
        }

        return dp[n];
    }

    /**
     * 动态规划优化，使用滚动数组
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int tribonacci2(int n) {
        if (n == 0) {
            return 0;
        }

        if (n == 1 || n == 2) {
            return 1;
        }

        int p = 0;
        int q = 1;
        int r = 1;

        for (int i = 3; i <= n; i++) {
            int temp = p + q + r;
            p = q;
            q = r;
            r = temp;
        }

        return r;
    }

    /**
     * 矩阵快速幂
     * [ T(n) ]         [1 1 1] ^ (n-2)       [T(2)]
     * [T(n-1)]    =    [1 0 0]          *    [T(1)]
     * [T(n-2)]         [0 1 0]               [T(0)]
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int tribonacci3(int n) {
        if (n == 0) {
            return 0;
        }

        if (n == 1 || n == 2) {
            return 1;
        }

        int[][] result = {{1, 1, 1}, {1, 0, 0}, {0, 1, 0}};
        result = quickPow(result, n - 2);
        result = multiply(result, new int[][]{{1}, {1}, {0}});

        return result[0][0];
    }

    /**
     * 二维矩阵快速幂
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param a
     * @param n
     * @return
     */
    private int[][] quickPow(int[][] a, int n) {
        int[][] result = new int[a.length][a.length];

        for (int i = 0; i < a.length; i++) {
            result[i][i] = 1;
        }

        while (n != 0) {
            if ((n & 1) == 1) {
                result = multiply(result, a);
            }

            a = multiply(a, a);
            n = n >>> 1;
        }

        return result;
    }

    private int[][] multiply(int[][] a, int[][] b) {
        int[][] result = new int[a.length][b[0].length];

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                for (int k = 0; k < a[0].length; k++) {
                    result[i][j] = result[i][j] + a[i][k] * b[k][j];
                }
            }
        }

        return result;
    }
}
