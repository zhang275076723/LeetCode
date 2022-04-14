package com.zhang.java;

/**
 * @Date 2022/3/29 9:16
 * @Author zsy
 * @Description 在一个 m*n 的棋盘的每一格都放有一个礼物，每个礼物都有一定的价值（价值大于 0）。
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
 */
public class Offer47 {
    public static void main(String[] args) {
        Offer47 offer47 = new Offer47();
        int[][] grid = {{1, 3, 1}, {1, 5, 1}, {4, 2, 1}};
        System.out.println(offer47.maxValue(grid));
        System.out.println(offer47.maxValue2(grid));
        System.out.println(offer47.maxValue3(grid));
    }

    /**
     * 回溯，时间复杂度O(2^mn)，空间复杂度O(m+n)
     *
     * @param grid
     * @return
     */
    public int maxValue(int[][] grid) {
        return find(grid, 0, 0);
    }

    /**
     * 动态规划，时间复杂度O(mn)，空间复杂度O(mn)
     * dp[i][j]：从grid[i][j]到grid[grid.length-1][grid[0].length-1]的最大价值
     * dp[i][j] = grid[i][j] + dp[i+1][j] (dp[i+1][j] > dp[i][j+1])
     * dp[i][j] = grid[i][j] + dp[i][j+1] (dp[i+1][j] <= dp[i][j+1])
     *
     * @param grid
     * @return
     */
    public int maxValue2(int[][] grid) {
        int[][] dp = new int[grid.length + 1][grid[0].length + 1];

        for (int i = grid.length - 1; i >= 0; i--) {
            for (int j = grid[0].length - 1; j >= 0; j--) {
                if (dp[i + 1][j] > dp[i][j + 1]) {
                    dp[i][j] = grid[i][j] + dp[i + 1][j];
                } else {
                    dp[i][j] = grid[i][j] + dp[i][j + 1];
                }
            }
        }

        return dp[0][0];
    }

    /**
     * 动态规划优化，原数组作为dp数组，直接在原数组上修改，时间复杂度O(mn)，空间复杂度O(1)
     *
     * @param grid
     * @return
     */
    public int maxValue3(int[][] grid) {
        //修改最后一行和最后一列
        for (int i = grid.length - 2; i >= 0; i--) {
            grid[i][grid[0].length - 1] = grid[i][grid[0].length - 1] + grid[i + 1][grid[0].length - 1];
        }
        for (int j = grid[0].length - 2; j >= 0; j--) {
            grid[grid.length - 1][j] = grid[grid.length - 1][j] + grid[grid.length - 1][j + 1];
        }

        for (int i = grid.length - 2; i >= 0; i--) {
            for (int j = grid[0].length - 2; j >= 0; j--) {
                if (grid[i + 1][j] > grid[i][j + 1]) {
                    grid[i][j] = grid[i][j] + grid[i + 1][j];
                } else {
                    grid[i][j] = grid[i][j] + grid[i][j + 1];
                }
            }
        }

        return grid[0][0];
    }

    public int find(int[][] grid, int i, int j) {
        if (i >= grid.length || j >= grid[0].length) {
            return 0;
        }

        int downValue = find(grid, i + 1, j);
        int rightValue = find(grid, i, j + 1);

        if (downValue > rightValue) {
            return grid[i][j] + downValue;
        } else {
            return grid[i][j] + rightValue;
        }
    }
}
