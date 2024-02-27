package com.zhang.java;

/**
 * @Date 2022/3/13 21:31
 * @Author zsy
 * @Description 斐波那契数列 字节面试题 类比Problem70、Problem509、Problem746、Problem1137、Offer10_2、Offer46 各种数类比Problem202、Problem204、Problem263、Problem264、Problem306、Problem313、Problem507、Problem509、Problem728、Problem842、Problem878、Problem1175、Problem1201、Problem1291、Offer49 记忆化搜索类比Problem62、Problem63、Problem64、Problem70、Problem329、Problem509、Problem1340、Problem1388、Problem1444、Offer10_2 同Problem509
 * 写一个函数，输入 n ，求斐波那契（Fibonacci）数列的第 n 项（即 F(N)）。
 * 斐波那契数列的定义如下：
 * F(0) = 0,   F(1) = 1
 * F(N) = F(N - 1) + F(N - 2), 其中 N > 1.
 * 斐波那契数列由 0 和 1 开始，之后的斐波那契数就是由之前的两数相加而得出。
 * 答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。
 * <p>
 * 输入：n = 2
 * 输出：1
 * <p>
 * 输入：n = 5
 * 输出：5
 * <p>
 * 0 <= n <= 100
 */
public class Offer10 {
    private final int MOD = 1000000007;

    public static void main(String[] args) {
        Offer10 offer10 = new Offer10();
        System.out.println(offer10.fib(88));
        System.out.println(offer10.fib2(88));
        System.out.println(offer10.fib3(88));
        System.out.println(offer10.fib4(88));
    }

    /**
     * 递归+记忆化搜索
     * 时间复杂度O(n)，空间复杂的O(n)
     *
     * @param n
     * @return
     */
    public int fib(int n) {
        if (n == 0 || n == 1) {
            return n;
        }

        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;

        dfs(n, dp);

        return dp[n];
    }

    /**
     * 动态规划
     * 时间复杂度O(n)，空间复杂的O(n)
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

        dp[0] = 0;
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            dp[i] = (dp[i - 1] + dp[i - 2]) % MOD;
        }

        return dp[n];
    }

    /**
     * 动态规划优化，使用滚动数组
     * 时间复杂度O(n)，空间复杂的O(1)
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

        int p = 0;
        int q = 1;

        for (int i = 2; i <= n; i++) {
            int temp = (p + q) % MOD;
            p = q;
            q = temp;
        }

        return q;
    }

    /**
     * 矩阵快速幂
     * [ F(n) ]         [1 1] ^ (n-1)       [F(1)]
     * [F(n-1)]    =    [1 0]          *    [F(0)]
     * 时间复杂度O(logn)，空间复杂的O(1)
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

        int[][] result = quickPow2D(a, n - 1);

        result = multiply2D(result, new int[][]{{1}, {0}});

        return result[0][0];
    }

    private void dfs(int n, int[] dp) {
        if (n == 0 || dp[n] != 0) {
            return;
        }

        dfs(n - 1, dp);
        dfs(n - 2, dp);

        dp[n] = (dp[n - 1] + dp[n - 2]) % MOD;
    }

    /**
     * 递归快速幂，类比快速乘
     * 时间复杂度O(logn)，空间复杂度O(logn)
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
            int temp = quickPow(a, n / 2);
            return temp * temp;
        } else {
            return quickPow(a, n - 1) * a;
        }
    }

    /**
     * 非递归快速幂，类比快速乘
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param a
     * @param n
     * @return
     */
    public int quickPow2(int a, int n) {
        if (n == 0) {
            return 1;
        }

        int result = 1;

        while (n != 0) {
            if ((n & 1) == 1) {
                result = result * a;
            }

            a = a * a;
            n = n >>> 1;
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
            int[][] result = new int[a.length][a.length];

            //初始化为单位矩阵
            for (int i = 0; i < a.length; i++) {
                result[i][i] = 1;
            }

            return result;
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
        int[][] result = new int[a.length][a.length];

        //初始化为单位矩阵
        for (int i = 0; i < a.length; i++) {
            result[i][i] = 1;
        }

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
     * 矩阵乘法
     *
     * @param a
     * @param b
     * @return
     */
    private int[][] multiply2D(int[][] a, int[][] b) {
        int[][] result = new int[a.length][b[0].length];

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                for (int k = 0; k < a[0].length; k++) {
                    //使用long，避免int相乘溢出
                    result[i][j] = (int) ((result[i][j] + (long) a[i][k] * b[k][j]) % MOD);
                }
            }
        }

        return result;
    }
}
