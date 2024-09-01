package com.zhang.java;

/**
 * @Date 2024/12/21 09:00
 * @Author zsy
 * @Description 二进制矩阵中的特殊位置 标志位类比Problem73、Problem130、Problem289 类比Problem2133、Problem2482、Problem2661
 * 给定一个 m x n 的二进制矩阵 mat，返回矩阵 mat 中特殊位置的数量。
 * 如果位置 (i, j) 满足 mat[i][j] == 1 并且行 i 与列 j 中的所有其他元素都是 0（行和列的下标从 0 开始计数），
 * 那么它被称为 特殊 位置。
 * <p>
 * 输入：mat = [[1,0,0],[0,0,1],[1,0,0]]
 * 输出：1
 * 解释：位置 (1, 2) 是一个特殊位置，因为 mat[1][2] == 1 且第 1 行和第 2 列的其他所有元素都是 0。
 * <p>
 * 输入：mat = [[1,0,0],[0,1,0],[0,0,1]]
 * 输出：3
 * 解释：位置 (0, 0)，(1, 1) 和 (2, 2) 都是特殊位置。
 * <p>
 * m == mat.length
 * n == mat[i].length
 * 1 <= m, n <= 100
 * mat[i][j] 是 0 或 1。
 */
public class Problem1582 {
    public static void main(String[] args) {
        Problem1582 problem1582 = new Problem1582();
        int[][] mat = {
                {1, 0, 0},
                {0, 0, 1},
                {1, 0, 0}
        };
        System.out.println(problem1582.numSpecial(mat));
        System.out.println(problem1582.numSpecial2(mat));
    }

    /**
     * 模拟
     * 时间复杂度O(mn)，空间复杂度O(m+n)
     *
     * @param mat
     * @return
     */
    public int numSpecial(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;

        //每行1的个数数组
        int[] rowArr = new int[m];
        //每列1的个数数组
        int[] colArr = new int[n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 1) {
                    rowArr[i]++;
                    colArr[j]++;
                }
            }
        }

        int count = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 1 && rowArr[i] == 1 && colArr[j] == 1) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * 标志位，原地修改
     * 遍历每行中1的个数count，如果当前行对应列元素为1，则count累加到第0行中对应列中，
     * 遍历结束，如果第0行当前列为1，则当前列存在1个特殊位置
     * 时间复杂度O(mn)，空间复杂度O(1)
     *
     * @param mat
     * @return
     */
    public int numSpecial2(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;

        for (int i = 0; i < m; i++) {
            //第i行中1的个数
            int count = 0;

            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 1) {
                    count++;
                }
            }

            //当前行为第0行，则count减1，避免累加第0行中1的个数出错
            //例如：第0行[1,1,0]，count=2，如果count不减1，则累加后错误的第0行[3,3,0]，count减1，才得到正确的第0行[2,2,0]
            if (i == 0) {
                count--;
            }

            for (int j = 0; j < n; j++) {
                //当前行对应列元素mat[i][j]为1，则count累加到第0行中对应列j中
                if (mat[i][j] == 1) {
                    mat[0][j] = mat[0][j] + count;
                }
            }
        }

        int count = 0;

        for (int j = 0; j < n; j++) {
            //第0行当前列为1，则当前列存在1个特殊位置
            if (mat[0][j] == 1) {
                count++;
            }
        }

        return count;
    }
}
