package com.zhang.java;

/**
 * @Date 2022/3/29 9:16
 * @Author zsy
 * @Description 礼物的最大价值 类比Problem64
 * 在一个 m*n 的棋盘的每一格都放有一个礼物，每个礼物都有一定的价值（价值大于 0）。
 * 你可以从棋盘的左上角开始拿格子里的礼物，并每次向右或者向下移动一格、直到到达棋盘的右下角。
 * 给定一个棋盘及其上面的礼物的价值，请计算你最多能拿到多少价值的礼物？
 * <p>
 * 输入:
 * [
 * [1,3,1],
 * [1,5,1],
 * [4,2,1]
 * ]
 * 输出: 12
 * 解释: 路径 1→3→5→2→1 可以拿到最多价值的礼物
 * <p>
 * 0 < grid.length <= 200
 * 0 < grid[0].length <= 200
 */
public class Offer47 {
    /**
     * 回溯使用的最大价值
     */
    private int maxValue = 0;

    public static void main(String[] args) {
        Offer47 offer47 = new Offer47();
        int[][] grid = {{1, 3, 1}, {1, 5, 1}, {4, 2, 1}};
        System.out.println(offer47.maxValue(grid));
        System.out.println(offer47.maxValue2(grid));
        System.out.println(offer47.maxValue3(grid));
    }

    /**
     * 回溯
     * 时间复杂度O(2^mn)，空间复杂度O(m+n)
     *
     * @param grid
     * @return
     */
    public int maxValue(int[][] grid) {
        backtrack(grid, 0, 0, 0);

        return maxValue;
    }

    /**
     * 动态规划
     * dp[i][j]：从dp[0][0]到dp[i][j]的最大价值
     * dp[i][j] = max(dp[i-1][j], dp[i][j-1]) + grid[i][j]
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param grid
     * @return
     */
    public int maxValue2(int[][] grid) {
        if (grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int[][] dp = new int[grid.length][grid[0].length];

        dp[0][0] = grid[0][0];

        for (int i = 1; i < grid.length; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }

        for (int j = 1; j < grid[0].length; j++) {
            dp[0][j] = dp[0][j - 1] + grid[0][j];
        }

        for (int i = 1; i < grid.length; i++) {
            for (int j = 1; j < grid[0].length; j++) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }

        return dp[grid.length - 1][grid[0].length - 1];
    }

    /**
     * 动态规划优化
     * 原数组作为dp数组，直接在原数组上修改
     * 时间复杂度O(mn)，空间复杂度O(1)
     *
     * @param grid
     * @return
     */
    public int maxValue3(int[][] grid) {
        if (grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        for (int i = 1; i < grid.length; i++) {
            grid[i][0] = grid[i - 1][0] + grid[i][0];
        }

        for (int j = 1; j < grid[0].length; j++) {
            grid[0][j] = grid[0][j - 1] + grid[0][j];
        }

        for (int i = 1; i < grid.length; i++) {
            for (int j = 1; j < grid[0].length; j++) {
                grid[i][j] = Math.max(grid[i - 1][j], grid[i][j - 1]) + grid[i][j];
            }
        }

        return grid[grid.length - 1][grid[0].length - 1];
    }

    private void backtrack(int[][] grid, int i, int j, int value) {
        if (i == grid.length || j == grid[0].length) {
            if (value > maxValue) {
                maxValue = value;
            }
            return;
        }

        backtrack(grid, i + 1, j, value + grid[i][j]);
        backtrack(grid, i, j + 1, value + grid[i][j]);
    }
}
