package com.zhang.java;

/**
 * @Date 2024/1/24 08:18
 * @Author zsy
 * @Description 矩阵对角线元素的和 对角线类比Problem51、Problem52、Problem498、Problem1001、Problem1329、Problem1424、Problem2614、Problem2711、Problem3000
 * 给你一个正方形矩阵 mat，请你返回矩阵对角线元素的和。
 * 请你返回在矩阵主对角线上的元素和副对角线上且不在主对角线上元素的和。
 * <p>
 * 输入：mat = [
 * [1,2,3],
 * [4,5,6],
 * [7,8,9]
 * ]
 * 输出：25
 * 解释：对角线的和为：1 + 5 + 9 + 3 + 7 = 25
 * 请注意，元素 mat[1][1] = 5 只会被计算一次。
 * <p>
 * 输入：mat = [
 * [1,1,1,1],
 * [1,1,1,1],
 * [1,1,1,1],
 * [1,1,1,1]
 * ]
 * 输出：8
 * <p>
 * n == mat.length == mat[i].length
 * 1 <= n <= 100
 * 1 <= mat[i][j] <= 100
 */
public class Problem1572 {
    public static void main(String[] args) {
        Problem1572 problem1572 = new Problem1572();
        int[][] mat = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        System.out.println(problem1572.diagonalSum(mat));
    }

    /**
     * 模拟
     * 左上到右下对角线上的元素的下标索引j-i相等，左下到右上对角线上的元素下标索引i+j相等
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param mat
     * @return
     */
    public int diagonalSum(int[][] mat) {
        //两个对角线上元素之和
        int sum = 0;
        int n = mat.length;
        int i = n - 1;
        int j = 0;

        //左下到右上对角线元素之和
        while (i >= 0 && j < n) {
            sum = sum + mat[i][j];
            i--;
            j++;
        }

        i = 0;
        j = 0;

        //左上到右下对角线元素之和
        while (i < n && j < n) {
            sum = sum + mat[i][j];
            i++;
            j++;
        }

        //n为偶数，两个对角线上元素不相交，直接返回sum
        if (n % 2 == 0) {
            return sum;
        } else {
            //n为奇数，两个对角线上元素不相交，即mat[n/2][n/2]多加了一次，直接返回sum-mat[n/2][n/2]
            return sum - mat[n / 2][n / 2];
        }
    }
}
