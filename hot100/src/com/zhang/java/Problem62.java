package com.zhang.java;

/**
 * @Date 2022/4/23 10:01
 * @Author zsy
 * @Description 不同路径 类比Offer13
 * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。
 * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish” ）。
 * 问总共有多少条不同的路径？
 * <p>
 * 输入：m = 3, n = 7
 * 输出：28
 * <p>
 * 输入：m = 3, n = 2
 * 输出：3
 * 解释：
 * 从左上角开始，总共有 3 条路径可以到达右下角。
 * 1. 向右 -> 向下 -> 向下
 * 2. 向下 -> 向下 -> 向右
 * 3. 向下 -> 向右 -> 向下
 * <p>
 * 输入：m = 7, n = 3
 * 输出：28
 * <p>
 * 输入：m = 3, n = 3
 * 输出：6
 * <p>
 * 1 <= m, n <= 100
 * 题目数据保证答案小于等于 2 * 10^9
 */
public class Problem62 {
    public static void main(String[] args) {
        Problem62 problem62 = new Problem62();
        System.out.println(problem62.uniquePaths(7, 3));
        System.out.println(problem62.uniquePaths2(7, 3));
        System.out.println(problem62.uniquePaths3(7, 3));
        System.out.println(problem62.uniquePaths4(7, 3));
    }

    /**
     * 动态规划
     * dp[i][j]：从(1,1)到(i,j)的不同路径数量
     * dp[i][j] = dp[i-1][j] + dp[i][j-1]
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param m
     * @param n
     * @return
     */
    public int uniquePaths(int m, int n) {
        if (m == 1 || n == 1) {
            return 1;
        }

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            dp[i][1] = 1;
        }
        for (int j = 1; j <= n; j++) {
            dp[1][j] = 1;
        }

        for (int i = 2; i <= m; i++) {
            for (int j = 2; j <= n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }

        return dp[m][n];
    }

    /**
     * 动态规划优化，使用滚动数组
     * 时间复杂度O(mn)，O(min(m,n)) (交换行列的值，保证取到m、n最小值)
     *
     * @param m
     * @param n
     * @return
     */
    public int uniquePaths2(int m, int n) {
        if (m == 1 || n == 1) {
            return 1;
        }

        int[] dp = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            dp[i] = 1;
        }

        for (int i = 2; i <= m; i++) {
            for (int j = 2; j <= n; j++) {
                dp[j] = dp[j - 1] + dp[j];
            }
        }

        return dp[n];
    }

    /**
     * 递归+记忆数组，相当于自下而上的动态规划
     *
     * @param m
     * @param n
     * @return
     */
    public int uniquePaths3(int m, int n) {
        if (m == 1 || n == 1) {
            return 1;
        }

        //保存从(1,1)到(m,n)的不同路径数量
        int[][] memory = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            memory[i][1] = 1;
        }
        for (int j = 1; j <= n; j++) {
            memory[1][j] = 1;
        }

        return dfs(m, n, memory);
    }

    /**
     * 组合数学，移动m+n-2次，向下m-1次，向右n-1次，从m+n-2中选出m-1个向下移动的方案
     * 时间复杂度O(min(m,n)) (交换行列的值，保证取到m、n最小值)，空间复杂度O(1)
     *
     * @param m
     * @param n
     * @return
     */
    public int uniquePaths4(int m, int n) {
        //有可能溢出，所以使用long，最后再转换成int
        long result = 1;

        for (int i = 1; i <= m - 1; i++) {
            //先乘再除，保证每次运算都是整数
            result = result * (n - 1 + i) / i;
        }

        return (int) result;
    }

    private int dfs(int m, int n, int[][] memory) {
        if (memory[m][n] == 0) {
            memory[m][n] = dfs(m - 1, n, memory) + dfs(m, n - 1, memory);
        }

        return memory[m][n];
    }
}
