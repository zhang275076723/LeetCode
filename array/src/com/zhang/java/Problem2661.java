package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2024/12/19 08:49
 * @Author zsy
 * @Description 找出叠涂元素 类比Problem1582、Problem2133、Problem2482
 * 给你一个下标从 0 开始的整数数组 arr 和一个 m x n 的整数 矩阵 mat 。
 * arr 和 mat 都包含范围 [1，m * n] 内的 所有 整数。
 * 从下标 0 开始遍历 arr 中的每个下标 i ，并将包含整数 arr[i] 的 mat 单元格涂色。
 * 请你找出 arr 中第一个使得 mat 的某一行或某一列都被涂色的元素，并返回其下标 i 。
 * <p>
 * 输入：arr = [1,3,4,2], mat = [[1,4],[2,3]]
 * 输出：2
 * 解释：遍历如上图所示，arr[2] 在矩阵中的第一行或第二列上都被涂色。
 * <p>
 * image explanation for example 2
 * 输入：arr = [2,8,7,4,1,3,5,6,9], mat = [[3,2,5],[1,4,6],[8,7,9]]
 * 输出：3
 * 解释：遍历如上图所示，arr[3] 在矩阵中的第二列上都被涂色。
 * <p>
 * m == mat.length
 * n = mat[i].length
 * arr.length == m * n
 * 1 <= m, n <= 10^5
 * 1 <= m * n <= 10^5
 * 1 <= arr[i], mat[r][c] <= m * n
 * arr 中的所有整数 互不相同
 * mat 中的所有整数 互不相同
 */
public class Problem2661 {
    public static void main(String[] args) {
        Problem2661 problem2661 = new Problem2661();
        int[] arr = {2, 8, 7, 4, 1, 3, 5, 6, 9};
        int[][] mat = {{3, 2, 5},
                {1, 4, 6},
                {8, 7, 9}
        };
        System.out.println(problem2661.firstCompleteIndex(arr, mat));
    }

    /**
     * 模拟+哈希表
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param arr
     * @param mat
     * @return
     */
    public int firstCompleteIndex(int[] arr, int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;

        //key：mat中元素，value：mat中元素的行列下标索引
        Map<Integer, int[]> map = new HashMap<>();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                map.put(mat[i][j], new int[]{i, j});
            }
        }

        //每行涂色的元素个数数组
        int[] rowArr = new int[m];
        //每列涂色的元素个数数组
        int[] colArr = new int[n];

        for (int i = 0; i < arr.length; i++) {
            int[] arr2 = map.get(arr[i]);
            //arr[i]在mat中的行
            int x = arr2[0];
            //arr[i]在mat中的列
            int y = arr2[1];

            rowArr[x]++;

            //第x行元素个数为n，则第x行都被涂色，返回i
            if (rowArr[x] == n) {
                return i;
            }

            colArr[y]++;

            //第y列元素个数为m，则第y列都被涂色，返回i
            if (colArr[y] == m) {
                return i;
            }
        }

        return -1;
    }
}
