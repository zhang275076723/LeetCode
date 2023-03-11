package com.zhang.java;


import java.util.Arrays;

/**
 * @Date 2021/11/27 19:31
 * @Author zsy
 * @Description 螺旋矩阵 II 类比Problem48、Problem54、Problem498、Offer29
 * 给你一个正整数n，生成一个包含1到n^2所有元素，且元素按顺时针顺序螺旋排列的n x n正方形矩阵matrix
 * <p>
 * 输入：n = 3
 * 输出：[
 * [1,2,3],
 * [8,9,4],
 * [7,6,5]
 * ]
 * <p>
 * 输入：n = 1
 * 输出：[[1]]
 * <p>
 * 1 <= n <= 20
 */
public class Problem59 {
    public static void main(String[] args) {
        Problem59 problem59 = new Problem59();
        int[][] matrix = problem59.generateMatrix(6);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println(Arrays.deepToString(matrix));
    }

    /**
     * 模拟
     * 使用四个指针，分别限定矩阵的上下左右，每次遍历完一行或一列之后，指针移动
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int[][] generateMatrix(int n) {
        int[][] result = new int[n][n];
        //结果数组中元素的值
        int value = 1;
        int left = 0;
        int right = n - 1;
        int top = 0;
        int bottom = n - 1;

        while (value <= n * n) {
            //先从左往右找
            for (int i = left; i <= right; i++) {
                result[top][i] = value;
                value++;
            }

            //top指针下移
            top++;

            //再从上往下找
            for (int i = top; i <= bottom; i++) {
                result[i][right] = value;
                value++;
            }

            //right指针左移
            right--;

            //接着从右往左找
            for (int i = right; i >= left; i--) {
                result[bottom][i] = value;
                value++;
            }

            //bottom指针上移
            bottom--;

            //最后从下往上找
            for (int i = bottom; i >= top; i--) {
                result[i][left] = value;
                value++;
            }

            //left指针右移
            left++;
        }

        return result;
    }
}
