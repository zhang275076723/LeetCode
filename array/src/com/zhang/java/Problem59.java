package com.zhang.java;


/**
 * @Date 2021/11/27 19:31
 * @Author zsy
 * @Description 给你一个正整数n，生成一个包含1到n^2所有元素，且元素按顺时针顺序螺旋排列的n x n正方形矩阵matrix
 * <p>
 * 输入：n = 3
 * 输出：[[1,2,3],[8,9,4],[7,6,5]]
 * <p>
 * 输入：n = 1
 * 输出：[[1]]
 */
public class Problem59 {
    public static void main(String[] args) {
        Problem59 p = new Problem59();
        int[][] matrix = p.generateMatrix(6);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * 先左到右，再上到下，接着右到左，最后下到上
     * 时间复杂度T(n) = O(n^2)，空间复杂度S(n) = O(n^2)
     *
     * @param n
     * @return
     */
    public int[][] generateMatrix(int n) {
        int[][] matrix = new int[n][n];
        int count = 1;
        int left = 0;
        int right = n - 1;
        int top = 0;
        int bottom = n - 1;

        while (count <= n * n) {
            for (int i = left; i <= right; i++) {
                matrix[top][i] = count++;
            }
            top++;

            for (int i = top; i <= bottom; i++) {
                matrix[i][right] = count++;
            }
            right--;

            for (int i = right; i >= left; i--) {
                matrix[bottom][i] = count++;
            }
            bottom--;

            for (int i = bottom; i >= top; i--) {
                matrix[i][left] = count++;
            }
            left++;
        }

        return matrix;
    }
}
