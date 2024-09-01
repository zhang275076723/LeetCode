package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2022/11/10 11:32
 * @Author zsy
 * @Description 矩阵置零 标志位类比Problem130、Problem289、Problem1582
 * 给定一个 m x n 的矩阵，如果一个元素为 0 ，则将其所在行和列的所有元素都设为 0 。
 * 请使用 原地 算法。
 * <p>
 * 输入：matrix = [[1,1,1],[1,0,1],[1,1,1]]
 * 输出：[[1,0,1],[0,0,0],[1,0,1]]
 * <p>
 * 输入：matrix = [[0,1,2,0],[3,4,5,2],[1,3,1,5]]
 * 输出：[[0,0,0,0],[0,4,5,0],[0,3,1,0]]
 * <p>
 * m == matrix.length
 * n == matrix[0].length
 * 1 <= m, n <= 200
 * -2^31 <= matrix[i][j] <= 2^31 - 1
 */
public class Problem73 {
    public static void main(String[] args) {
        Problem73 problem73 = new Problem73();
        int[][] matrix = {
                {0, 1, 2, 1},
                {3, 4, 0, 2},
                {1, 3, 1, 5}
        };
//        problem73.setZeroes(matrix);
        problem73.setZeroes2(matrix);
        System.out.println(Arrays.deepToString(matrix));
    }

    /**
     * 使用额外数组
     * 复制一份一样的数组，对复制数组进行遍历，如果当前元素为0，则对原数组中当前行和当前列整体赋值0
     * 时间复杂度O(mn*(m+n))，空间复杂度O(mn)
     *
     * @param matrix
     */
    public void setZeroes(int[][] matrix) {
        int[][] tempArr = new int[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                tempArr[i][j] = matrix[i][j];
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                //当前元素为0，将所在行和列置为0
                if (tempArr[i][j] == 0) {
                    for (int m = 0; m < matrix.length; m++) {
                        matrix[m][j] = 0;
                    }

                    for (int n = 0; n < matrix[0].length; n++) {
                        matrix[i][n] = 0;
                    }
                }
            }
        }
    }

    /**
     * 标志位，原地修改
     * 使用两个标志位，分别记录第一行和第一列中是否有0，从第二行第二列开始遍历，
     * 如果当前元素为0，则在当前元素对应的首行和首列赋值为0，
     * 最后遍历首行首列元素，如果为0，则对应的行或列整体为0
     * 时间复杂度O(mn)，空间复杂度O(1)
     *
     * @param matrix
     */
    public void setZeroes2(int[][] matrix) {
        boolean rowFlag = false;
        boolean colFlag = false;

        //遍历首列，如果存在0，则将列标志位colFlag置为true，表示当前列最后都要置为0
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][0] == 0) {
                colFlag = true;
                break;
            }
        }

        //遍历首行，如果存在0，则将行标志位rowFlag置为true，表示当前行最后都要置为0
        for (int j = 0; j < matrix[0].length; j++) {
            if (matrix[0][j] == 0) {
                rowFlag = true;
                break;
            }
        }

        //遍历除首行和首列外的其他元素，如果当前元素为0，则将对应的首行和首列赋值为0
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[0].length; j++) {
                //当前元素为0，则对应的首行和首列赋值为0
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }

        //遍历首列除索引为0的元素之外的元素，如果当前元素值为0，将对应行元素都置为0
        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i][0] == 0) {
                for (int j = 1; j < matrix[0].length; j++) {
                    matrix[i][j] = 0;
                }
            }
        }

        //遍历首行除索引为0的元素之外的元素，如果当前元素值为0，将对应列元素都置为0
        for (int j = 1; j < matrix[0].length; j++) {
            if (matrix[0][j] == 0) {
                for (int i = 1; i < matrix.length; i++) {
                    matrix[i][j] = 0;
                }
            }
        }

        //根据rowFlag和colFlag，判断首行和首列是否需要赋值为0
        if (rowFlag) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[0][j] = 0;
            }
        }

        if (colFlag) {
            for (int i = 0; i < matrix.length; i++) {
                matrix[i][0] = 0;
            }
        }
    }
}
