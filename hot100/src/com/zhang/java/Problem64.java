package com.zhang.java;

/**
 * @Date 2022/4/23 11:12
 * @Author zsy
 * @Description 最小路径和 类比Problem62、Problem63、Problem980、Offer47 记忆化搜索类比Problem62、Problem63、Problem70、Problem329、Problem509、Problem1340、Problem1388、Problem1444、Offer10、Offer10_2
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
        int[][] dp = new int[grid.length][grid[0].length];
        //dp[0][0]初始化
        dp[0][0] = grid[0][0];

        //dp初始化
        for (int i = 1; i < grid.length; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }

        //dp初始化
        for (int j = 1; j < grid[0].length; j++) {
            dp[0][j] = dp[0][j - 1] + grid[0][j];
        }

        for (int i = 1; i < grid.length; i++) {
            for (int j = 1; j < grid[0].length; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }

        return dp[grid.length - 1][grid[0].length - 1];
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
        //dp初始化
        for (int i = 1; i < grid.length; i++) {
            grid[i][0] = grid[i - 1][0] + grid[i][0];
        }

        //dp初始化
        for (int j = 1; j < grid[0].length; j++) {
            grid[0][j] = grid[0][j - 1] + grid[0][j];
        }

        for (int i = 1; i < grid.length; i++) {
            for (int j = 1; j < grid[0].length; j++) {
                grid[i][j] = Math.min(grid[i - 1][j], grid[i][j - 1]) + grid[i][j];
            }
        }

        return grid[grid.length - 1][grid[0].length - 1];
    }

    /**
     * 递归+记忆化搜索(相当于自下而上的动态规划)
     * dp[i][j]：grid[0][0]到grid[i][j]的最小路径和
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param grid
     * @return
     */
    public int minPathSum3(int[][] grid) {
        int[][] dp = new int[grid.length][grid[0].length];
        //dp[0][0]初始化
        dp[0][0] = grid[0][0];

        //dp初始化
        for (int i = 1; i < grid.length; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }

        //dp初始化
        for (int j = 1; j < grid[0].length; j++) {
            dp[0][j] = dp[0][j - 1] + grid[0][j];
        }

        //dp初始化，未访问的dp初始化为-1
        for (int i = 1; i < grid.length; i++) {
            for (int j = 1; j < grid[0].length; j++) {
                dp[i][j] = -1;
            }
        }

        dfs(grid.length - 1, grid[0].length - 1, grid, dp);

        return dp[grid.length - 1][grid[0].length - 1];
    }

    private void dfs(int i, int j, int[][] grid, int[][] dp) {
        //当前dp不等于-1，则说明grid[0][0]到grid[i][j]的最小路径和已经得到，直接返回，可以直接使用
        if (dp[i][j] != -1) {
            return;
        }

        dfs(i - 1, j, grid, dp);
        dfs(i, j - 1, grid, dp);

        dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
    }
}
