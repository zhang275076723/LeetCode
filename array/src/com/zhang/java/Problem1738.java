package com.zhang.java;

/**
 * @Date 2024/7/18 08:36
 * @Author zsy
 * @Description 找出第 K 大的异或坐标值 二维前缀和类比Problem304、Problem363、Problem1292、Problem1444
 * 给你一个二维矩阵 matrix 和一个整数 k ，矩阵大小为 m x n 由非负整数组成。
 * 矩阵中坐标 (a, b) 的 目标值 可以通过对所有元素 matrix[i][j] 执行异或运算得到，
 * 其中 i 和 j 满足 0 <= i <= a < m 且 0 <= j <= b < n（下标从 0 开始计数）。
 * 请你找出 matrix 的所有坐标中第 k 大的目标值（k 的值从 1 开始计数）。
 * <p>
 * 输入：matrix = [[5,2],[1,6]], k = 1
 * 输出：7
 * 解释：坐标 (0,1) 的目标值是 5 XOR 2 = 7 ，为最大的目标值。
 * <p>
 * 输入：matrix = [[5,2],[1,6]], k = 2
 * 输出：5
 * 解释：坐标 (0,0) 的目标值是 5 = 5 ，为第 2 大的目标值。
 * <p>
 * 输入：matrix = [[5,2],[1,6]], k = 3
 * 输出：4
 * 解释：坐标 (1,0) 的目标值是 5 XOR 1 = 4 ，为第 3 大的目标值。
 * <p>
 * 输入：matrix = [[5,2],[1,6]], k = 4
 * 输出：0
 * 解释：坐标 (1,1) 的目标值是 5 XOR 2 XOR 1 XOR 6 = 0 ，为第 4 大的目标值。
 * <p>
 * m == matrix.length
 * n == matrix[i].length
 * 1 <= m, n <= 1000
 * 0 <= matrix[i][j] <= 10^6
 * 1 <= k <= m * n
 */
public class Problem1738 {
    public static void main(String[] args) {
        Problem1738 problem1738 = new Problem1738();
        int[][] matrix = {{5, 2}, {1, 6}};
        int k = 2;
        System.out.println(problem1738.kthLargestValue(matrix, k));
    }

    /**
     * 二维前缀和+排序
     * preSum[i][j]：matrix[0][0]-matrix[i-1][j-1]异或运算结果
     * preSum[i][j] = preSum[i-1][j-1] ^ preSum[i][j-1] ^ preSum[i-1][j] ^ matrix[i-1][j-1]
     * 时间复杂度O(mnlog(mn))，空间复杂度O(mn)
     *
     * @param matrix
     * @param k
     * @return
     */
    public int kthLargestValue(int[][] matrix, int k) {
        int[][] preSum = new int[matrix.length + 1][matrix[0].length + 1];
        //二维前缀和排序数组
        int[] arr = new int[matrix.length * matrix[0].length];
        int index = 0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                preSum[i + 1][j + 1] = preSum[i][j] ^ preSum[i + 1][j] ^ preSum[i][j + 1] ^ matrix[i][j];
                arr[index] = preSum[i + 1][j + 1];
                index++;
            }
        }

        //二维前缀和由小到大排序
        heapSort(arr);

        //返回arr中第k大异或值
        return arr[arr.length - k];
    }

    private void heapSort(int[] arr) {
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }

        for (int i = arr.length - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, 0, i);
        }
    }

    private void heapify(int[] arr, int i, int heapSize) {
        int index = i;
        int leftIndex = 2 * i + 1;
        int rightIndex = 2 * i + 2;

        if (leftIndex < heapSize && arr[index] < arr[leftIndex]) {
            index = leftIndex;
        }

        if (rightIndex < heapSize && arr[index] < arr[rightIndex]) {
            index = rightIndex;
        }

        if (index != i) {
            int temp = arr[index];
            arr[index] = arr[i];
            arr[i] = temp;

            heapify(arr, index, heapSize);
        }
    }
}
