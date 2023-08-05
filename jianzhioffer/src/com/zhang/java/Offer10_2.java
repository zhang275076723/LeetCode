package com.zhang.java;

/**
 * @Date 2022/3/14 15:12
 * @Author zsy
 * @Description 青蛙跳台阶问题 类比Problem509、Problem746、Problem1137、Offer10、Offer46 记忆化搜索类比Problem62、Problem63、Problem64、Problem70、Problem329、Problem509、Problem1340、Offer10 同Problem70
 * 一只青蛙一次可以跳上1级台阶，也可以跳上2级台阶。求该青蛙跳上一个 n 级的台阶总共有多少种跳法。
 * 总跳法 = 1次跳上1级台阶和剩余n-1级台阶的跳法 + 1次跳上2级台阶和剩余n-2级台阶的跳法
 * <p>
 * 输入：n = 2
 * 输出：2
 * <p>
 * 输入：n = 7
 * 输出：21
 * <p>
 * 输入：n = 0
 * 输出：1
 * <p>
 * 0 <= n <= 100
 */
public class Offer10_2 {
    private final int MOD = 1000000007;

    public static void main(String[] args) {
        Offer10_2 offer10_2 = new Offer10_2();
        System.out.println(offer10_2.numWays(88));
        System.out.println(offer10_2.numWays2(88));
        System.out.println(offer10_2.numWays3(88));
        System.out.println(offer10_2.numWays4(88));
    }

    /**
     * 递归+记忆化搜索
     * 时间复杂度O(n)，空间复杂的O(n)
     *
     * @param n
     * @return
     */
    public int numWays(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }

        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;

        dfs(n, dp);

        return dp[n];
    }

    /**
     * 动态规划
     * 时间复杂度O(n)，空间复杂的O(n）
     *
     * @param n
     * @return
     */
    public int numWays2(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }

        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            dp[i] = (dp[i - 1] + dp[i - 2]) % MOD;
        }

        return dp[n];
    }

    /**
     * 动态规划优化
     * 时间复杂度O(n)，空间复杂的O(1）
     *
     * @param n
     * @return
     */
    public int numWays3(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }

        int p = 1;
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
     * [ f(n) ]         [1 1] ^ (n-1)         [f(1)]
     * [f(n-1)]    =    [1 0]           *     [f(0)]
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int numWays4(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }

        int[][] a = {{1, 1}, {1, 0}};

        int[][] result = quickPow(a, n - 1);

        result = multiply(result, new int[][]{{1}, {1}});

        return result[0][0];
    }

    private void dfs(int n, int[] dp) {
        if (dp[n] != 0) {
            return;
        }

        dfs(n - 1, dp);
        dfs(n - 2, dp);

        dp[n] = (dp[n - 1] + dp[n - 2]) % MOD;
    }

    private int[][] quickPow(int[][] a, int n) {
        int[][] result = new int[a.length][a.length];

        //初始化为单位矩阵
        for (int i = 0; i < a.length; i++) {
            result[i][i] = 1;
        }

        while (n > 0) {
            if ((n & 1) != 0) {
                result = multiply(result, a);
            }

            a = multiply(a, a);
            n = n >> 1;
        }

        return result;
    }

    private int[][] multiply(int[][] a, int[][] b) {
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
