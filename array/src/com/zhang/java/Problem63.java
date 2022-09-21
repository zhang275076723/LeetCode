package com.zhang.java;

/**
 * @Date 2022/8/22 9:11
 * @Author zsy
 * @Description 不同路径 II 类比Problem62、Problem64、Offer13
 * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。
 * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish”）。
 * 现在考虑网格中有障碍物。那么从左上角到右下角将会有多少条不同的路径？
 * 网格中的障碍物和空位置分别用 1 和 0 来表示。
 * <p>
 * 输入：obstacleGrid = [[0,0,0],[0,1,0],[0,0,0]]
 * 输出：2
 * 解释：3x3 网格的正中间有一个障碍物。
 * 从左上角到右下角一共有 2 条不同的路径：
 * 1. 向右 -> 向右 -> 向下 -> 向下
 * 2. 向下 -> 向下 -> 向右 -> 向右
 * <p>
 * 输入：obstacleGrid = [[0,1],[0,0]]
 * 输出：1
 * <p>
 * m == obstacleGrid.length
 * n == obstacleGrid[i].length
 * 1 <= m, n <= 100
 * obstacleGrid[i][j] 为 0 或 1
 */
public class Problem63 {
    public static void main(String[] args) {
        Problem63 problem63 = new Problem63();
        int[][] obstacleGrid = {{0, 0, 0}, {0, 1, 0}, {0, 0, 0}};
        System.out.println(problem63.uniquePathsWithObstacles(obstacleGrid));
        System.out.println(problem63.uniquePathsWithObstacles2(obstacleGrid));
    }

    /**
     * 动态规划
     * dp[i][j]：从(0,0)到(i,j)不同的路径数量
     * dp[i][j] = dp[i-1][j] + dp[i][j-1] (obstacleGrid[i][j] != 0)
     * dp[i][j] = 0                       (obstacleGrid[i][j] == 0)
     * 时间复杂度O(mn)，空间复杂度O(mn) (m = obstacleGrid.length, n = obstacleGrid[0].length)
     *
     * @param obstacleGrid
     * @return
     */
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int[][] dp = new int[obstacleGrid.length][obstacleGrid[0].length];

        for (int i = 0; i < obstacleGrid.length; i++) {
            if (obstacleGrid[i][0] == 0) {
                dp[i][0] = 1;
            } else {
                break;
            }
        }

        for (int j = 0; j < obstacleGrid[0].length; j++) {
            if (obstacleGrid[0][j] == 0) {
                dp[0][j] = 1;
            } else {
                break;
            }
        }

        for (int i = 1; i < obstacleGrid.length; i++) {
            for (int j = 1; j < obstacleGrid[0].length; j++) {
                if (obstacleGrid[i][j] == 0) {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }

        return dp[obstacleGrid.length - 1][obstacleGrid[0].length - 1];
    }

    /**
     * 动态规划优化，使用滚动数组
     * 时间复杂度O(mn)，空间复杂度O(n) (m = obstacleGrid.length, n = obstacleGrid[0].length)
     *
     * @param obstacleGrid
     * @return
     */
    public int uniquePathsWithObstacles2(int[][] obstacleGrid) {
        int[] dp = new int[obstacleGrid[0].length];

        for (int j = 0; j < obstacleGrid[0].length; j++) {
            if (obstacleGrid[0][j] == 0) {
                dp[j] = 1;
            } else {
                break;
            }
        }

        for (int i = 1; i < obstacleGrid.length; i++) {
            for (int j = 0; j < obstacleGrid[0].length; j++) {
                //每行第一列元素处理
                if (j == 0) {
                    if (obstacleGrid[i][j] == 1) {
                        dp[j] = 0;
                    }

                    continue;
                }

                if (obstacleGrid[i][j] == 0) {
                    dp[j] = dp[j - 1] + dp[j];
                } else {
                    dp[j] = 0;
                }
            }
        }

        return dp[obstacleGrid[0].length - 1];
    }
}