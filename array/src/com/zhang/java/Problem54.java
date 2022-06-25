package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/6/25 9:09
 * @Author zsy
 * @Description 螺旋矩阵 类比Problem59
 * 给你一个 m 行 n 列的矩阵 matrix ，请按照 顺时针螺旋顺序 ，返回矩阵中的所有元素。
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
 * m == matrix.length
 * n == matrix[i].length
 * 1 <= m, n <= 10
 * -100 <= matrix[i][j] <= 100
 */
public class Problem54 {
    public static void main(String[] args) {
        Problem54 problem54 = new Problem54();
        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
        System.out.println(problem54.spiralOrder(matrix));
    }

    /**
     * 先从左到右遍历，再上到下，接着右到左，最后下到上
     * 时间复杂度O(mn)，空间复杂度O(1)
     *
     * @param matrix
     * @return
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return new ArrayList<>();
        }

        List<Integer> list = new ArrayList<>();

        int left = 0;
        int right = matrix[0].length - 1;
        int top = 0;
        int bottom = matrix.length - 1;

        while (list.size() < matrix.length * matrix[0].length) {
            for (int i = left; i <= right; i++) {
                list.add(matrix[top][i]);
            }
            top++;

            if (list.size() == matrix.length * matrix[0].length){
                break;
            }

            for (int i = top; i <= bottom; i++) {
                list.add(matrix[i][right]);
            }
            right--;

            if (list.size() == matrix.length * matrix[0].length){
                break;
            }

            for (int i = right; i >= left; i--) {
                list.add(matrix[bottom][i]);
            }
            bottom--;

            if (list.size() == matrix.length * matrix[0].length){
                break;
            }

            for (int i = bottom; i >= top; i--) {
                list.add(matrix[i][left]);
            }
            left++;

            if (list.size() == matrix.length * matrix[0].length){
                break;
            }
        }

        return list;
    }
}
