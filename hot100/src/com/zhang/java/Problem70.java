package com.zhang.java;

/**
 * @Date 2022/4/23 11:45
 * @Author zsy
 * @Description 爬楼梯 类比Problem509、Problem746、Problem1137、Offer10、Offer46、CircleBackToOrigin 记忆化搜索类比Problem62、Problem63、Problem64、Problem329、Problem509、Problem1340、Offer10、Offer10_2 同Offer10_2
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 * <p>
 * 输入：n = 2
 * 输出：2
 * 解释：有两种方法可以爬到楼顶。
 * 1. 1 阶 + 1 阶
 * 2. 2 阶
 * <p>
 * 输入：n = 3
 * 输出：3
 * 解释：有三种方法可以爬到楼顶。
 * 1. 1 阶 + 1 阶 + 1 阶
 * 2. 1 阶 + 2 阶
 * 3. 2 阶 + 1 阶
 * <p>
 * 1 <= n <= 45
 */
public class Problem70 {
    public static void main(String[] args) {
        Problem70 problem70 = new Problem70();
        System.out.println(problem70.climbStairs(45));
        System.out.println(problem70.climbStairs2(45));
        System.out.println(problem70.climbStairs3(45));
        System.out.println(problem70.climbStairs4(45));
    }

    /**
     * 动态规划
     * dp[i]：从第1阶爬到第n阶台阶的不同方法数
     * dp[i] = dp[i-1] + dp[i-2]
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public int climbStairs(int n) {
        if (n == 1 || n == 2) {
            return n;
        }

        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }

    /**
     * 动态规划优化
     * 每个位置的值只与它前面两个元素有关，直接使用三个变量即可
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int climbStairs2(int n) {
        if (n == 1 || n == 2) {
            return n;
        }

        int p = 1;
        int q = 1;

        for (int i = 2; i <= n; i++) {
            int temp = q;
            q = p + q;
            p = temp;
        }

        return q;
    }

    /**
     * 递归+记忆化搜索
     * dp[i]：从第1阶爬到第n阶台阶的不同方法数
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public int climbStairs3(int n) {
        if (n == 1 || n == 2) {
            return n;
        }

        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;

        dfs(n, dp);

        return dp[n];
    }

    /**
     * 矩阵快速幂
     * [ f(n) ]         [1 1] ^ (n-1)       [f(1)]
     * [f(n-1)]    =    [1 0]          *    [f(0)]
     * 时间复杂度O(logn)，空间复杂的O(1）
     *
     * @param n
     * @return
     */
    public int climbStairs4(int n) {
        if (n == 1 || n == 2) {
            return n;
        }

        int[][] result = {{1, 1}, {1, 0}};

        result = quickPow(result, n - 1);

        result = multiply(result, new int[][]{{1}, {1}});

        return result[0][0];
    }

    private void dfs(int n, int[] dp) {
        if (dp[n] != 0) {
            return;
        }

        dfs(n - 1, dp);
        dfs(n - 2, dp);

        dp[n] = dp[n - 1] + dp[n - 2];
    }

    private int[][] quickPow(int[][] a, int n) {
        int[][] result = new int[a.length][a.length];

        //初始化为单位矩阵
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
                    //使用long，避免int相乘溢出
                    result[i][j] = (int) (result[i][j] + (long) a[i][k] * b[k][j]);
                }
            }
        }

        return result;
    }
}
