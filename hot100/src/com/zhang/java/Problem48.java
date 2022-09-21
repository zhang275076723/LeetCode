package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2022/4/20 9:13
 * @Author zsy
 * @Description 旋转图像 类比Problem54、Problem59、Offer29
 * 给定一个 n × n 的二维矩阵 matrix 表示一个图像。请你将图像顺时针旋转 90 度。
 * 你必须在 原地 旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要 使用另一个矩阵来旋转图像。
 * <p>
 * 输入：matrix = [
 * [1,2,3],
 * [4,5,6],
 * [7,8,9]
 * ]
 * 输出：[
 * [7,4,1],
 * [8,5,2],
 * [9,6,3]
 * ]
 * <p>
 * 输入：matrix = [
 * [5,1,9,11],
 * [2,4,8,10],
 * [13,3,6,7],
 * [15,14,12,16]
 * ]
 * 输出：[
 * [15,13,2,5],
 * [14,3,4,1],
 * [12,6,8,9],
 * [16,7,10,11]
 * ]
 * <p>
 * n == matrix.length == matrix[i].length
 * 1 <= n <= 20
 * -1000 <= matrix[i][j] <= 1000
 */
public class Problem48 {
    public static void main(String[] args) {
        Problem48 problem48 = new Problem48();
        int[][] matrix = {{5, 1, 9, 11}, {2, 4, 8, 10}, {13, 3, 6, 7}, {15, 14, 12, 16}};
        problem48.rotate(matrix);
        System.out.println(Arrays.deepToString(matrix));
    }

    /**
     * 每四个元素是一组，由外到内按照一圈一圈调整每一组的四个元素，不管怎么旋转，只是调整了这四个元素的位置
     * 矩阵四个角的元素：matrix[start][start]、matrix[start][end]、matrix[end][end]、matrix[end][start]
     * 要调整每组四个元素：matrix[start][start + j]、matrix[start + j][end]、matrix[end][end - j]、matrix[end - j][start]
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param matrix
     */
    public void rotate(int[][] matrix) {
        if (matrix.length == 1) {
            return;
        }

        //由外到内一圈一圈调整几轮，每组的四个元素
        for (int i = 0; i < matrix.length / 2; i++) {
            //每组要调整的四个元素的起始元素
            int start = i;
            //每组要调整的四个元素的末尾元素
            int end = matrix.length - i - 1;

            //每轮需要调整的每四个元素的组数
            for (int j = 0; j < end - start; j++) {
                //调整这四个元素的位置
                int temp = matrix[start][start + j];
                matrix[start][start + j] = matrix[end - j][start];
                matrix[end - j][start] = matrix[end][end - j];
                matrix[end][end - j] = matrix[start + j][end];
                matrix[start + j][end] = temp;
            }
        }
    }
}
