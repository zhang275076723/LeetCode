package com.zhang.java;

/**
 * @Date 2022/8/18 9:07
 * @Author zsy
 * @Description 矩阵中的最长递增路径 类比Problem131、Problem139、Problem140
 * 给定一个 m x n 整数矩阵 matrix ，找出其中 最长递增路径 的长度。
 * 对于每个单元格，你可以往上，下，左，右四个方向移动。
 * 你 不能 在 对角线 方向上移动或移动到 边界外（即不允许环绕）。
 * <p>
 * 输入：matrix = [[9,9,4],[6,6,8],[2,1,1]]
 * 输出：4
 * 解释：最长递增路径为 [1, 2, 6, 9]。
 * <p>
 * 输入：matrix = [[3,4,5],[3,2,6],[2,2,1]]
 * 输出：4
 * 解释：最长递增路径是 [3, 4, 5, 6]。注意不允许在对角线方向上移动。
 * <p>
 * 输入：matrix = [[1]]
 * 输出：1
 * <p>
 * m == matrix.length
 * n == matrix[i].length
 * 1 <= m, n <= 200
 * 0 <= matrix[i][j] <= 2^31 - 1
 */
public class Problem329 {
    public static void main(String[] args) {
        Problem329 problem329 = new Problem329();
        int[][] matrix = {{9, 9, 4}, {6, 6, 8}, {2, 1, 1}};
        System.out.println(problem329.longestIncreasingPath(matrix));
    }

    /**
     * 回溯+剪枝+动态规划预处理matrix
     * dp[i][j]：以matrix[i][j]起始的最长递增路径长度
     * dp[i][j] = max(dp[i-1][j],dp[i+1][j],dp[i][j-1],dp[i][j+1]) + 1 (dp[i][j] < dp[i-1][j],dp[i+1][j],dp[i][j-1],dp[i][j+1])
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param matrix
     * @return
     */
    public int longestIncreasingPath(int[][] matrix) {
        int maxLen = 0;
        int[][] dp = new int[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                maxLen = Math.max(maxLen, backtrack(i, j, matrix, dp));
            }
        }

        return maxLen;
    }

    private int backtrack(int i, int j, int[][] matrix, int[][] dp) {
        //已经找到以matrix[i][j]起始的最长递增路径长度，直接返回
        if (dp[i][j] != 0) {
            return dp[i][j];
        }

        int upLen = 0;
        int downLen = 0;
        int leftLen = 0;
        int rightLen = 0;

        //往上找
        if (i - 1 >= 0 && matrix[i][j] < matrix[i - 1][j]) {
            upLen = backtrack(i - 1, j, matrix, dp);
        }

        //往下找
        if (i + 1 < matrix.length && matrix[i][j] < matrix[i + 1][j]) {
            downLen = backtrack(i + 1, j, matrix, dp);
        }

        //往左找
        if (j - 1 >= 0 && matrix[i][j] < matrix[i][j - 1]) {
            leftLen = backtrack(i, j - 1, matrix, dp);
        }

        //往右找
        if (j + 1 < matrix[0].length && matrix[i][j] < matrix[i][j + 1]) {
            rightLen = backtrack(i, j + 1, matrix, dp);
        }

        dp[i][j] = Math.max(Math.max(upLen, downLen), Math.max(leftLen, rightLen)) + 1;

        return dp[i][j];
    }
}
