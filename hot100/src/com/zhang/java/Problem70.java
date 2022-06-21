package com.zhang.java;

/**
 * @Date 2022/4/23 11:45
 * @Author zsy
 * @Description 爬楼梯
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
        System.out.println(problem70.climbStairs(7));
        System.out.println(problem70.climbStairs2(7));
        System.out.println(problem70.climbStairs3(7));
        System.out.println(problem70.climbStairs4(7));
    }

    /**
     * 动态规划
     * dp[i]：爬n阶台阶的不同方法数量
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
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i <= n; i++) {
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

        //前2阶台阶
        int p;
        //前1阶台阶
        int q = 1;
        //当前台阶
        int r = 2;
        for (int i = 3; i <= n; i++) {
            p = q;
            q = r;
            r = p + q;
        }
        return r;
    }

    /**
     * 递归+记忆数组
     *
     * @param n
     * @return
     */
    public int climbStairs3(int n) {
        if (n == 1 || n == 2) {
            return n;
        }

        int[] memory = new int[n + 1];
        memory[1] = 1;
        memory[2] = 2;
        return dfs(n, memory);
    }

    /**
     * 矩阵快速幂
     * [ f(n) ]    =   [1 1] ^ (n-2)       [f(2)]
     * [f(n-1)]        [1 0]          *    [f(1)]
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
        result = quickPow(result, n - 2);
        result = multiply(result, new int[][]{{2,0}, {1,0}});
        return result[0][0];
    }

    private int dfs(int n, int[] memory) {
        if (memory[n] == 0) {
            memory[n] = dfs(n - 1, memory) + dfs(n - 2, memory);
        }
        return memory[n];
    }

    private int[][] quickPow(int[][] a, int n) {
        int[][] result = {{1, 0}, {0, 1}};
        while (n > 0) {
            if ((n & 1) == 1) {
                result = multiply(result, a);
            }
            a = multiply(a, a);
            n = n >> 1;
        }
        return result;
    }

    private int[][] multiply(int[][] a, int[][] b) {
        int[][] result = new int[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                result[i][j] = a[i][0] * b[0][j] + a[i][1] * b[1][j];
            }
        }
        return result;
    }
}
