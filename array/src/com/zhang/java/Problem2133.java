package com.zhang.java;

import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2024/12/18 08:54
 * @Author zsy
 * @Description 检查是否每一行每一列都包含全部整数 类比Problem36 类比Problem1582、Problem2482、Problem2661
 * 对一个大小为 n x n 的矩阵而言，如果其每一行和每一列都包含从 1 到 n 的 全部 整数（含 1 和 n），则认为该矩阵是一个 有效 矩阵。
 * 给你一个大小为 n x n 的整数矩阵 matrix ，请你判断矩阵是否为一个有效矩阵：如果是，返回 true ；否则，返回 false 。
 * <p>
 * 输入：matrix = [[1,2,3],[3,1,2],[2,3,1]]
 * 输出：true
 * 解释：在此例中，n = 3 ，每一行和每一列都包含数字 1、2、3 。
 * 因此，返回 true 。
 * <p>
 * 输入：matrix = [[1,1,1],[1,2,3],[1,2,3]]
 * 输出：false
 * 解释：在此例中，n = 3 ，但第一行和第一列不包含数字 2 和 3 。
 * 因此，返回 false 。
 * <p>
 * n == matrix.length == matrix[i].length
 * 1 <= n <= 100
 * 1 <= matrix[i][j] <= n
 */
public class Problem2133 {
    public static void main(String[] args) {
        Problem2133 problem2133 = new Problem2133();
        int[][] matrix = {
                {1, 1, 1},
                {1, 2, 3},
                {1, 2, 3}
        };
        System.out.println(problem2133.checkValid(matrix));
    }

    /**
     * 模拟+哈希表
     * 存储每行、每列中包含的数字，如果存在相同数字，则不是有效矩阵
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param matrix
     * @return
     */
    public boolean checkValid(int[][] matrix) {
        int n = matrix.length;
        //先存储每行中包含的数字，再存储每列中包含的数字集合
        Set<Integer> set = new HashSet<>();

        //判断每行中是否包含相同的数字
        for (int i = 0; i < n; i++) {
            set.clear();

            for (int j = 0; j < n; j++) {
                //当前行存在相同数字，则不是有效矩阵，返回false
                if (set.contains(matrix[i][j])) {
                    return false;
                }

                set.add(matrix[i][j]);
            }
        }

        //判断每列中是否包含相同的数字
        for (int j = 0; j < n; j++) {
            set.clear();

            for (int i = 0; i < n; i++) {
                //当前列存在相同数字，则不是有效矩阵，返回false
                if (set.contains(matrix[i][j])) {
                    return false;
                }

                set.add(matrix[i][j]);
            }
        }

        return true;
    }
}
