package com.zhang.java;

import java.util.Comparator;
import java.util.TreeSet;

/**
 * @Date 2024/2/23 08:20
 * @Author zsy
 * @Description 矩形区域不超过 K 的最大数值和 类比Problem53 二维前缀和类比Problem304、Problem1292、Problem1444、Problem1738 有序集合类比Problem220、Problem352、Problem855、Problem1348 前缀和类比
 * 给你一个 m x n 的矩阵 matrix 和一个整数 k ，找出并返回矩阵内部矩形区域的不超过 k 的最大数值和。
 * 题目数据保证总会存在一个数值和不超过 k 的矩形区域。
 * <p>
 * 输入：matrix = [[1,0,1],[0,-2,3]], k = 2
 * 输出：2
 * 解释：蓝色边框圈出来的矩形区域 [[0, 1], [-2, 3]] 的数值和是 2，且 2 是不超过 k 的最大数字（k = 2）。
 * <p>
 * 输入：matrix = [[2,2,-1]], k = 3
 * 输出：3
 * <p>
 * m == matrix.length
 * n == matrix[i].length
 * 1 <= m, n <= 100
 * -100 <= matrix[i][j] <= 100
 * -10^5 <= k <= 10^5
 */
public class Problem363 {
    public static void main(String[] args) {
        Problem363 problem363 = new Problem363();
        int[][] matrix = {{1, 0, 1}, {0, -2, 3}};
        int k = 2;
        System.out.println(problem363.maxSumSubmatrix(matrix, k));
        System.out.println(problem363.maxSumSubmatrix2(matrix, k));
        System.out.println(problem363.maxSumSubmatrix3(matrix, k));
    }

    /**
     * 暴力+二维前缀和
     * 时间复杂度O(m^2*n^2)，空间复杂度O(mn) (m=matrix.length，n=matrix[0].length)
     *
     * @param matrix
     * @param k
     * @return
     */
    public int maxSumSubmatrix(int[][] matrix, int k) {
        int m = matrix.length;
        int n = matrix[0].length;

        int[][] preSum = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                preSum[i][j] = preSum[i - 1][j] + preSum[i][j - 1] - preSum[i - 1][j - 1] + matrix[i - 1][j - 1];
            }
        }

        //矩形元素之和不超过k的最大值
        int result = Integer.MIN_VALUE;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int p = i; p < m; p++) {
                    for (int q = j; q < n; q++) {
                        //左上角(i,j)和右下角(p,q)围成矩形的元素之和
                        int sum = preSum[p + 1][q + 1] - preSum[p + 1][j] - preSum[i][q + 1] + preSum[i][j];

                        //更新矩形元素之和不超过k的最大值
                        if (sum <= k) {
                            result = Math.max(result, sum);
                        }
                    }
                }
            }
        }

        return result;
    }

    /**
     * 二维前缀和+有序集合
     * 固定二维数组的上下边界，通过有序集合找当前右边界对应左边界中矩形元素之和不超过k的最大值
     * 时间复杂度O(m^2*nlogn)，空间复杂度O(n) (m=matrix.length，n=matrix[0].length) (适用于n远远大于m的情况)
     *
     * @param matrix
     * @param k
     * @return
     */
    public int maxSumSubmatrix2(int[][] matrix, int k) {
        int m = matrix.length;
        int n = matrix[0].length;

        int[][] preSum = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                preSum[i][j] = preSum[i - 1][j] + preSum[i][j - 1] - preSum[i - 1][j - 1] + matrix[i - 1][j - 1];
            }
        }

        //矩形元素之和不超过k的最大值
        int result = Integer.MIN_VALUE;

        //固定二维数组的上边界i
        for (int i = 0; i < m; i++) {
            //固定二维数组的下边界j
            for (int j = i; j < m; j++) {
                //有序集合，由小到大存储遍历过的右边界对应矩形元素之和
                TreeSet<Integer> set = new TreeSet<>(new Comparator<Integer>() {
                    @Override
                    public int compare(Integer a, Integer b) {
                        return a - b;
                    }
                });

                //初始化，空矩形元素之和为0
                set.add(0);

                //遍历右边界p，通过有序集合找当前右边界对应左边界中矩形元素之和不超过k的最大值
                for (int p = 0; p < n; p++) {
                    //左上角(i,0)和右下角(j,p)围成矩形的元素之和
                    int sum = preSum[j + 1][p + 1] - preSum[j + 1][0] - preSum[i][p + 1] + preSum[i][0];
                    //有序集合中找遍历过的右边界对应矩形元素之和大于等于sum-k的最小值，如果不存在返回null
                    Integer sum2 = set.ceiling(sum - k);

                    //sum2存在，sum-sum2即为当前右边界对应左边界中矩形元素之和不超过k的最大值
                    if (sum2 != null) {
                        result = Math.max(result, sum - sum2);
                    }

                    //当前矩形元素之和sum加入set，用于下一个右边界查找
                    set.add(sum);
                }
            }
        }

        return result;
    }

    /**
     * 二维前缀和+有序集合
     * 固定二维数组的左右边界，通过有序集合找当前下边界对应上边界中矩形元素之和不超过k的最大值
     * 时间复杂度O(n^2*mlogm)，空间复杂度O(m) (m=matrix.length，n=matrix[0].length) (适用于m远远大于n的情况)
     *
     * @param matrix
     * @param k
     * @return
     */
    public int maxSumSubmatrix3(int[][] matrix, int k) {
        int m = matrix.length;
        int n = matrix[0].length;

        int[][] preSum = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                preSum[i][j] = preSum[i - 1][j] + preSum[i][j - 1] - preSum[i - 1][j - 1] + matrix[i - 1][j - 1];
            }
        }

        //矩形元素之和不超过k的最大值
        int result = Integer.MIN_VALUE;

        //固定二维数组的左边界i
        for (int i = 0; i < n; i++) {
            //固定二维数组的右边界j
            for (int j = i; j < n; j++) {
                //有序集合，由小到大存储遍历过的下边界对应矩形元素之和
                TreeSet<Integer> set = new TreeSet<>(new Comparator<Integer>() {
                    @Override
                    public int compare(Integer a, Integer b) {
                        return a - b;
                    }
                });

                //初始化，空矩形元素之和为0
                set.add(0);

                //遍历下边界p，通过有序集合找当前下边界对应上边界中矩形元素之和不超过k的最大值
                for (int p = 0; p < m; p++) {
                    //左上角(0,i)和右下角(p,j)围成矩形的元素之和
                    int sum = preSum[p + 1][j + 1] - preSum[p + 1][i] - preSum[0][j + 1] + preSum[0][i];
                    //有序集合中找遍历过的下边界对应矩形元素之和大于等于sum-k的最小值，如果不存在返回null
                    Integer sum2 = set.ceiling(sum - k);

                    //sum2存在，sum-sum2即为当前下边界对应上边界中矩形元素之和不超过k的最大值
                    if (sum2 != null) {
                        result = Math.max(result, sum - sum2);
                    }

                    //当前矩形元素之和sum加入set，用于下一个下边界查找
                    set.add(sum);
                }
            }
        }

        return result;
    }
}