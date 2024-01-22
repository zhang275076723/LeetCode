package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2022/3/20 16:06
 * @Author zsy
 * @Description 顺时针打印矩阵 类比Problem48、Problem54、Problem59、Problem885、Problem2326 同Problem54
 * 输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字。
 * <p>
 * 输入：matrix = [
 * [1,2,3],
 * [4,5,6],
 * [7,8,9]
 * ]
 * 输出：[1,2,3,6,9,8,7,4,5]
 * <p>
 * 输入：matrix = [
 * [1,2,3,4],
 * [5,6,7,8],
 * [9,10,11,12]
 * ]
 * 输出：[1,2,3,4,8,12,11,10,9,5,6,7]
 * <p>
 * 0 <= matrix.length <= 100
 * 0 <= matrix[i].length <= 100
 */
public class Offer29 {
    public static void main(String[] args) {
        Offer29 offer29 = new Offer29();
        int[][] matrix = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12}
        };
        System.out.println(Arrays.toString(offer29.spiralOrder(matrix)));
    }

    /**
     * 模拟
     * 使用四个指针，分别限定矩阵的上下左右边界，每次遍历完一行或一列之后，指针移动
     * 时间复杂度O(mn)，空间复杂度O(1)
     *
     * @param matrix
     * @return
     */
    public int[] spiralOrder(int[][] matrix) {
        if (matrix == null) {
            return null;
        }

        if (matrix.length == 0 || matrix[0].length == 0) {
            return new int[0];
        }

        int[] result = new int[matrix.length * matrix[0].length];
        int index = 0;
        //上下左右四个指针，限定矩阵的上下左右边界
        int left = 0;
        int right = matrix[0].length - 1;
        int top = 0;
        int bottom = matrix.length - 1;

        while (index < result.length) {
            //先从左往右遍历
            for (int i = left; i <= right; i++) {
                result[index] = matrix[top][i];
                index++;
            }

            //top指针下移
            top++;

            //矩阵已经遍历结束，直接返回
            if (index == result.length) {
                return result;
            }

            //再从上往下遍历
            for (int i = top; i <= bottom; i++) {
                result[index] = matrix[i][right];
                index++;
            }

            //right指针左移
            right--;

            //矩阵已经遍历结束，直接返回
            if (index == result.length) {
                return result;
            }

            //接着从右往左遍历
            for (int i = right; i >= left; i--) {
                result[index] = matrix[bottom][i];
                index++;
            }

            //bottom指针上移
            bottom--;

            //矩阵已经遍历结束，直接返回
            if (index == result.length) {
                return result;
            }

            //最后从下往上遍历
            for (int i = bottom; i >= top; i--) {
                result[index] = matrix[i][left];
                index++;
            }

            //left指针右移
            left++;
        }

        return result;
    }
}
