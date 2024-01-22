package com.zhang.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @Date 2024/1/24 08:30
 * @Author zsy
 * @Description 将矩阵按对角线排序 对角线类比Problem51、Problem52、Problem498、Problem1001、Problem1424、Problem1572
 * 矩阵对角线 是一条从矩阵最上面行或者最左侧列中的某个元素开始的对角线，沿右下方向一直到矩阵末尾的元素。
 * 例如，矩阵 mat 有 6 行 3 列，从 mat[2][0] 开始的 矩阵对角线 将会经过 mat[2][0]、mat[3][1] 和 mat[4][2] 。
 * 给你一个 m * n 的整数矩阵 mat ，请你将同一条 矩阵对角线 上的元素按升序排序后，返回排好序的矩阵。
 * <p>
 * 输入：mat = [
 * [3,3,1,1],
 * [2,2,1,2],
 * [1,1,1,2]
 * ]
 * 输出：[
 * [1,1,1,1],
 * [1,2,2,2],
 * [1,2,3,3]
 * ]
 * <p>
 * 输入：mat = [
 * [11,25,66,1,69,7],
 * [23,55,17,45,15,52],
 * [75,31,36,44,58,8],
 * [22,27,33,25,68,4],
 * [84,28,14,11,5,50]
 * ]
 * 输出：[
 * [5,17,4,1,52,7],
 * [11,11,25,45,8,69],
 * [14,23,25,44,58,15],
 * [22,27,31,36,50,66],
 * [84,28,75,33,55,68]
 * ]
 * <p>
 * m == mat.length
 * n == mat[i].length
 * 1 <= m, n <= 100
 * 1 <= mat[i][j] <= 100
 */
public class Problem1329 {
    public static void main(String[] args) {
        Problem1329 problem1329 = new Problem1329();
        int[][] mat = {
                {11, 25, 66, 1, 69, 7},
                {23, 55, 17, 45, 15, 52},
                {75, 31, 36, 44, 58, 8},
                {22, 27, 33, 25, 68, 4},
                {84, 28, 14, 11, 5, 50}};
        System.out.println(Arrays.deepToString(problem1329.diagonalSort(mat)));
    }

    /**
     * 模拟
     * 左上到右下对角线上的元素的下标索引j-i相等
     * 时间复杂度O((m+n)*min(m,n)*log(m,n))，空间复杂度O(mn) (m=mat.length，n=mat[0].length)
     * (对角线中最多只有min(m.n)个元素，min(m.n)个元素排序的时间复杂度O(min(m,n)*log(m,n)))
     *
     * @param mat
     * @return
     */
    public int[][] diagonalSort(int[][] mat) {
        //行
        int m = mat.length;
        //列
        int n = mat[0].length;
        //lists.get(k)：第k条对角线上的元素的集合，下标索引j-i+m-1=k
        List<List<Integer>> lists = new ArrayList<>();

        //对角线初始化，共m+n-1条对角线
        for (int i = 0; i < m + n - 1; i++) {
            lists.add(new ArrayList<>());
        }

        //m+n-1条对角线上的元素加入lists中
        for (int i = 0; i < m + n - 1; i++) {
            //起始列为i和最大列n-1中的最小值
            int y = Math.min(i, n - 1);
            //第i条对角线满足y-x+m-1=i，即x=y+m-1-i
            int x = y + m - 1 - i;

            while (x >= 0 && y >= 0) {
                lists.get(i).add(mat[x][y]);
                x--;
                y--;
            }
        }

        //m+n-1条对角线上的元素由小到大排序
        for (int i = 0; i < m + n - 1; i++) {
            lists.get(i).sort(new Comparator<Integer>() {
                @Override
                public int compare(Integer a, Integer b) {
                    return a - b;
                }
            });
        }

        int[][] result = new int[m][n];

        //m+n-1条对角线上的元素按顺序加入result
        for (int i = 0; i < m + n - 1; i++) {
            int x = Math.max(m - 1 - i, 0);
            int y = x + i - m + 1;
            //lists.get(i)当前遍历到的下标索引
            int index = 0;

            while (x < m && y < n) {
                result[x][y] = lists.get(i).get(index);
                x++;
                y++;
                index++;
            }
        }

        return result;
    }
}
