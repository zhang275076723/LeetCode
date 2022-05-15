package com.zhang.java;

/**
 * @Date 2022/5/15 9:47
 * @Author zsy
 * @Description 最大正方形 类比84题
 * 在一个由 '0' 和 '1' 组成的二维矩阵内，找到只包含 '1' 的最大正方形，并返回其面积。
 * <p>
 * 输入：matrix = [
 * ["1","0","1","0","0"],
 * ["1","0","1","1","1"],
 * ["1","1","1","1","1"],
 * ["1","0","0","1","0"]
 * ]
 * 输出：4
 * <p>
 * 输入：matrix = [
 * ["0","1"],
 * ["1","0"]
 * ]
 * 输出：1
 * <p>
 * 输入：matrix = [["0"]]
 * 输出：0
 * <p>
 * m == matrix.length
 * n == matrix[i].length
 * 1 <= m, n <= 300
 * matrix[i][j] 为 '0' 或 '1'
 */
public class Problem221 {
    public static void main(String[] args) {
        Problem221 problem221 = new Problem221();
        char[][] matrix = {
                {'1', '0', '1', '0', '0'},
                {'1', '0', '1', '1', '1'},
                {'1', '1', '1', '1', '1'},
                {'1', '0', '0', '1', '0'}
        };
        System.out.println(problem221.maximalSquare(matrix));
    }

    /**
     * 动态规划
     * 时间复杂度O(mn)，空间复杂度O(mn)，m为matrix的行，n为matrix的列
     * dp[i][j]：以matrix[i][j]为右下角构成的最大正方形的边长
     * dp[i][j] = 1 + min(dp[i-1][j-1], dp[i-1][j], dp[i][j-1]) (matrix[i][j] == 1)
     * dp[i][j] = 0                                             (matrix[i][j] == 0)
     *
     * @param matrix
     * @return
     */
    public int maximalSquare(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        int m = matrix.length;
        int n = matrix[0].length;
        int[][] dp = new int[m][n];
        //当前正方形的最大边长
        int maxLen = 0;

        //初始化dp
        for (int i = 0; i < m; i++) {
            dp[i][0] = matrix[i][0] - '0';
            //更新maxLen
            if (dp[i][0] == 1) {
                maxLen = 1;
            }
        }
        for (int j = 0; j < n; j++) {
            dp[0][j] = matrix[0][j] - '0';
            //更新maxLen
            if (dp[0][j] == 1) {
                maxLen = 1;
            }
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == '1') {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));
                    maxLen = Math.max(maxLen, dp[i][j]);
                }
            }
        }

        return maxLen * maxLen;
    }
}
