package com.zhang.java;

/**
 * @Date 2022/3/13 11:26
 * @Author zsy
 * @Description 二维数组中的查找 类比Problem74、Problem378 同Problem240
 * 在一个 n * m 的二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。
 * 请完成一个高效的函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数
 * <p>
 * 现有矩阵 matrix 如下：
 * [
 * [1,   4,  7, 11, 15],
 * [2,   5,  8, 12, 19],
 * [3,   6,  9, 16, 22],
 * [10, 13, 14, 17, 24],
 * [18, 21, 23, 26, 30]
 * ]
 * 给定 target = 5，返回 true。
 * 给定 target = 20，返回 false。
 * <p>
 * 0 <= n <= 1000
 * 0 <= m <= 1000
 */
public class Offer4 {
    public static void main(String[] args) {
        Offer4 offer4 = new Offer4();
        int[][] matrix = {
                {1, 4, 7, 11, 15},
                {2, 5, 8, 12, 19},
                {3, 6, 9, 16, 22},
                {10, 13, 14, 17, 24},
                {8, 21, 23, 26, 30}
        };
        System.out.println(offer4.findNumberIn2DArray(matrix, 20));
    }

    /**
     * 类比二分查找树
     * 从右上角开始比较，比它大就往下数一行，比它小就往左数一列
     * 注意：也可以从左下角往右上角遍历
     * 时间复杂度O(m+n)，空间复杂度O(1)
     *
     * @param matrix
     * @param target
     * @return
     */
    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0) {
            return false;
        }

        int i = 0;
        int j = matrix[0].length - 1;

        while (i < matrix.length && j >= 0) {
            if (matrix[i][j] == target) {
                return true;
            } else if (matrix[i][j] > target) {
                j--;
            } else {
                i++;
            }
        }

        return false;
    }
}