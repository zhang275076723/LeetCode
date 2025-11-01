package com.zhang.java;

/**
 * @Date 2025/11/1 17:15
 * @Author zsy
 * @Description 排序矩阵查找 类比Problem74、Problem240、Problem378、Offer4 同Problem240、Offer4
 * 给定 M×N 矩阵，每一行、每一列都按升序排列，请编写代码找出某元素。
 * <p>
 * 示例：
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
 */
public class Interview_10_09 {
    public static void main(String[] args) {
        Interview_10_09 interview_10_09 = new Interview_10_09();
        int[][] matrix = {
                {1, 4, 7, 11, 15},
                {2, 5, 8, 12, 19},
                {3, 6, 9, 16, 22},
                {10, 13, 14, 17, 24},
                {18, 21, 23, 26, 30}
        };
        int target = 5;
        System.out.println(interview_10_09.searchMatrix(matrix, target));
    }

    /**
     * 二分查找
     * 类比二分查找树，从右上角开始遍历，target比当前元素大，则往下一行遍历；target比当前元素小，则往左一列遍历
     * 注意：也可以从左下角往右上角遍历
     * 时间复杂度O(m+n)，空间复杂度O(1)
     *
     * @param matrix
     * @param target
     * @return
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }

        int i = 0;
        int j = matrix[0].length - 1;

        while (i < matrix.length && j >= 0) {
            if (matrix[i][j] == target) {
                return true;
            } else if (matrix[i][j] < target) {
                i++;
            } else {
                j--;
            }
        }

        return false;
    }
}
