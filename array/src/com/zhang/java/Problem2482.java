package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2024/12/20 08:02
 * @Author zsy
 * @Description 行和列中一和零的差值 类比Problem1582、Problem2133、Problem2661
 * 给你一个下标从 0 开始的 m x n 二进制矩阵 grid 。
 * 我们按照如下过程，定义一个下标从 0 开始的 m x n 差值矩阵 diff ：
 * 令第 i 行一的数目为 onesRowi 。
 * 令第 j 列一的数目为 onesColj 。
 * 令第 i 行零的数目为 zerosRowi 。
 * 令第 j 列零的数目为 zerosColj 。
 * diff[i][j] = onesRowi + onesColj - zerosRowi - zerosColj
 * 请你返回差值矩阵 diff 。
 * <p>
 * 输入：grid = [[0,1,1],[1,0,1],[0,0,1]]
 * 输出：[[0,0,4],[0,0,4],[-2,-2,2]]
 * 解释：
 * - diff[0][0] = onesRow0 + onesCol0 - zerosRow0 - zerosCol0 = 2 + 1 - 1 - 2 = 0
 * - diff[0][1] = onesRow0 + onesCol1 - zerosRow0 - zerosCol1 = 2 + 1 - 1 - 2 = 0
 * - diff[0][2] = onesRow0 + onesCol2 - zerosRow0 - zerosCol2 = 2 + 3 - 1 - 0 = 4
 * - diff[1][0] = onesRow1 + onesCol0 - zerosRow1 - zerosCol0 = 2 + 1 - 1 - 2 = 0
 * - diff[1][1] = onesRow1 + onesCol1 - zerosRow1 - zerosCol1 = 2 + 1 - 1 - 2 = 0
 * - diff[1][2] = onesRow1 + onesCol2 - zerosRow1 - zerosCol2 = 2 + 3 - 1 - 0 = 4
 * - diff[2][0] = onesRow2 + onesCol0 - zerosRow2 - zerosCol0 = 1 + 1 - 2 - 2 = -2
 * - diff[2][1] = onesRow2 + onesCol1 - zerosRow2 - zerosCol1 = 1 + 1 - 2 - 2 = -2
 * - diff[2][2] = onesRow2 + onesCol2 - zerosRow2 - zerosCol2 = 1 + 3 - 2 - 0 = 2
 * <p>
 * 输入：grid = [[1,1,1],[1,1,1]]
 * 输出：[[5,5,5],[5,5,5]]
 * 解释：
 * - diff[0][0] = onesRow0 + onesCol0 - zerosRow0 - zerosCol0 = 3 + 2 - 0 - 0 = 5
 * - diff[0][1] = onesRow0 + onesCol1 - zerosRow0 - zerosCol1 = 3 + 2 - 0 - 0 = 5
 * - diff[0][2] = onesRow0 + onesCol2 - zerosRow0 - zerosCol2 = 3 + 2 - 0 - 0 = 5
 * - diff[1][0] = onesRow1 + onesCol0 - zerosRow1 - zerosCol0 = 3 + 2 - 0 - 0 = 5
 * - diff[1][1] = onesRow1 + onesCol1 - zerosRow1 - zerosCol1 = 3 + 2 - 0 - 0 = 5
 * - diff[1][2] = onesRow1 + onesCol2 - zerosRow1 - zerosCol2 = 3 + 2 - 0 - 0 = 5
 * <p>
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 10^5
 * 1 <= m * n <= 10^5
 * grid[i][j] 要么是 0 ，要么是 1 。
 */
public class Problem2482 {
    public static void main(String[] args) {
        Problem2482 problem2482 = new Problem2482();
        int[][] grid = {
                {0, 1, 1},
                {1, 0, 1},
                {0, 0, 1}
        };
        System.out.println(Arrays.deepToString(problem2482.onesMinusZeros(grid)));
    }

    /**
     * 模拟
     * 时间复杂度O(mn)，空间复杂度O(m+n)
     *
     * @param grid
     * @return
     */
    public int[][] onesMinusZeros(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        //每行1的个数数组
        int[] rowArr = new int[m];
        //每列1的个数数组
        int[] colArr = new int[n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    rowArr[i]++;
                    colArr[j]++;
                }
            }
        }

        int[][] diff = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                diff[i][j] = rowArr[i] + colArr[j] - (m - rowArr[i]) - (n - colArr[j]);
            }
        }

        return diff;
    }
}
