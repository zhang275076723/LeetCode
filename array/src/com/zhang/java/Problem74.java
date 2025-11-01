package com.zhang.java;

/**
 * @Date 2022/8/6 11:22
 * @Author zsy
 * @Description 搜索二维矩阵 类比Problem240、Problem378、Offer4、Interview_10_09
 * 编写一个高效的算法来判断 m x n 矩阵中，是否存在一个目标值。
 * 该矩阵具有如下特性：
 * 每行中的整数从左到右按升序排列。
 * 每行的第一个整数大于前一行的最后一个整数。
 * <p>
 * 输入：matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3
 * 输出：true
 * <p>
 * 输入：matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 13
 * 输出：false
 * <p>
 * m == matrix.length
 * n == matrix[i].length
 * 1 <= m, n <= 100
 * -10^4 <= matrix[i][j], target <= 10^4
 */
public class Problem74 {
    public static void main(String[] args) {
        Problem74 problem74 = new Problem74();
        int[][] matrix = {
                {1, 3, 5, 7},
                {10, 11, 16, 20},
                {23, 30, 34, 60}
        };
        int target = 13;
        System.out.println(problem74.searchMatrix(matrix, target));
    }

    /**
     * 类比二分查找
     * 将二维数组看成一维从小到大的数组，按照二分查找，找target
     * 时间复杂度O(log(mn))，空间复杂度O(1)
     *
     * @param matrix
     * @param target
     * @return
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        int left = 0;
        int right = matrix.length * matrix[0].length - 1;
        int mid;

        while (left <= right) {
            mid = left + ((right - left) >> 1);
            //mid在矩阵中的索引下标
            int i = mid / matrix[0].length;
            int j = mid % matrix[0].length;

            if (matrix[i][j] == target) {
                return true;
            } else if (matrix[i][j] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return false;
    }
}
