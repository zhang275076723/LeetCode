package com.zhang.java;

/**
 * @Date 2022/4/23 11:12
 * @Author zsy
 * @Description 最小路径和 类比Problem62、Problem63、Offer47
 * 给定一个包含非负整数的 m x n 网格 grid ，
 * 请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
 * 说明：每次只能向下或者向右移动一步。
 * <p>
 * 输入：grid = [[1,3,1],[1,5,1],[4,2,1]]
 * 输出：7
 * 解释：因为路径 1→3→1→1→1 的总和最小。
 * <p>
 * 输入：grid = [[1,2,3],[4,5,6]]
 * 输出：12
 * <p>
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 200
 * 0 <= grid[i][j] <= 100
 */
public class Problem64 {
    /**
     * 回溯使用的最小路径和
     */
    private int minSum = Integer.MAX_VALUE;

    public static void main(String[] args) {
        Problem64 problem64 = new Problem64();
        int[][] grid = {{1, 3, 1}, {1, 5, 1}, {4, 2, 1}};
        System.out.println(problem64.minPathSum(grid));
//        System.out.println(problem64.minPathSum2(grid));
        System.out.println(problem64.minPathSum3(grid));
    }

    /**
     * 动态规划
     * dp[i][j]：grid[0][0]到grid[i][j]的最小路径和
     * dp[i][j] = min(dp[i][j-1], dp[i-1][j]) + grid[i][j]
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param grid
     * @return
     */
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];

        dp[0][0] = grid[0][0];

        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }

        for (int j = 1; j < n; j++) {
            dp[0][j] = dp[0][j - 1] + grid[0][j];
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }

        return dp[m - 1][n - 1];
    }

    /**
     * 动态规划优化，使用原数组作为dp数组
     * 每个位置的值只与它左边和上边的元素有关，直接使用原数组grid作为dp数组
     * 时间复杂度O(mn)，空间复杂度O(1)
     *
     * @param grid
     * @return
     */
    public int minPathSum2(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        for (int i = 1; i < m; i++) {
            grid[i][0] = grid[i - 1][0] + grid[i][0];
        }
        for (int j = 1; j < n; j++) {
            grid[0][j] = grid[0][j - 1] + grid[0][j];
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                grid[i][j] = Math.min(grid[i - 1][j], grid[i][j - 1]) + grid[i][j];
            }
        }

        return grid[m - 1][n - 1];
    }

    /**
     * 回溯+剪枝
     * 时间复杂度O((m+n)*C(m+n-2,m-1))，空间复杂度O(m+n)
     *
     * @param grid
     * @return
     */
    public int minPathSum3(int[][] grid) {
        backtrack(grid, 0, 0, 0);

        return minSum;
    }

    private void backtrack(int[][] grid, int i, int j, int sum) {
        //超过数组范围，剪枝
        if (i >= grid.length || j >= grid[0].length) {
            return;
        }

        sum = sum + grid[i][j];

        if (i == grid.length - 1 && j == grid[0].length - 1) {
            minSum = Math.min(minSum, sum);
            return;
        }

        backtrack(grid, i + 1, j, sum);
        backtrack(grid, i, j + 1, sum);
    }
}
