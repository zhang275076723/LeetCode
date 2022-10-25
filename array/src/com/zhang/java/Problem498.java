package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2022/7/10 8:23
 * @Author zsy
 * @Description 对角线遍历 美团面试题 小红书面试题 类比Problem48、Problem54、Problem59、Offer29
 * 给你一个大小为 m x n 的矩阵 mat ，请以对角线遍历的顺序，用一个数组返回这个矩阵中的所有元素。
 * <p>
 * 输入：mat = [
 * [1,2,3],
 * [4,5,6],
 * [7,8,9]
 * ]
 * 输出：[1,2,4,7,5,3,6,8,9]
 * <p>
 * 输入：mat = [
 * [1,2],
 * [3,4]
 * ]
 * 输出：[1,2,3,4]
 * <p>
 * m == mat.length
 * n == mat[i].length
 * 1 <= m, n <= 10^4
 * 1 <= m * n <= 10^4
 * -10^5 <= mat[i][j] <= 10^5
 */
public class Problem498 {
    public static void main(String[] args) {
        Problem498 problem498 = new Problem498();
        int[][] mat = {{1, 2, 3}, {4, 5, 6}};
        System.out.println(Arrays.toString(problem498.findDiagonalOrder(mat)));
    }

    /**
     * 模拟
     * mat[i][j]中i+j和相同的在一条对角线上；
     * i+j和为偶数的是从左下到右上的对角线;
     * i+j和为奇数的是从右上到左下的对角线
     * 时间复杂度O(mn)，空间复杂度O(1)
     *
     * @param mat
     * @return
     */
    public int[] findDiagonalOrder(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        int[] result = new int[m * n];
        int index = 0;

        //共m+n-1个对角线，每行对角线行列之和
        for (int i = 0; i < m + n - 1; i++) {
            //从左下到右上遍历
            if (i % 2 == 0) {
                int x = i >= m ? m - 1 : i;
                int y = i - x;

                while (x >= 0 && y < n) {
                    result[index] = mat[x][y];
                    index++;
                    x--;
                    y++;
                }
            } else {
                //从右上到左下遍历
                int y = i >= n ? n - 1 : i;
                int x = i - y;

                while (x < m && y >= 0) {
                    result[index] = mat[x][y];
                    index++;
                    x++;
                    y--;
                }
            }
        }

        return result;
    }
}
