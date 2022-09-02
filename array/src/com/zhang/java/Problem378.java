package com.zhang.java;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @Date 2022/8/17 8:07
 * @Author zsy
 * @Description 有序矩阵中第 K 小的元素 类比Problem23、Problem74、Problem240、Offer4
 * 给你一个 n x n 矩阵 matrix ，其中每行和每列元素均按升序排序，找到矩阵中第 k 小的元素。
 * 请注意，它是 排序后 的第 k 小元素，而不是第 k 个 不同 的元素。
 * <p>
 * 输入：matrix = [[1,5,9],[10,11,13],[12,13,15]], k = 8
 * 输出：13
 * 解释：矩阵中的元素为 [1,5,9,10,11,12,13,13,15]，第 8 小元素是 13
 * <p>
 * 输入：matrix = [[-5]], k = 1
 * 输出：-5
 * <p>
 * n == matrix.length
 * n == matrix[i].length
 * 1 <= n <= 300
 * -10^9 <= matrix[i][j] <= 10^9
 * 题目数据 保证 matrix 中的所有行和列都按 非递减顺序 排列
 * 1 <= k <= n^2
 */
public class Problem378 {
    public static void main(String[] args) {
        Problem378 problem378 = new Problem378();
        int[][] matrix = new int[][]{
                {1, 5, 9},
                {10, 11, 13},
                {12, 13, 15}
        };
        int k = 8;
        System.out.println(problem378.kthSmallest(matrix, k));
        System.out.println(problem378.kthSmallest2(matrix, k));
        System.out.println(problem378.kthSmallest3(matrix, k));
    }

    /**
     * 小根堆，优先队列，k路归并排序
     * 时间复杂度O(nlogn+klogn)，空间复杂度O(n) (n=matrix.length)
     *
     * @param matrix
     * @param k
     * @return
     */
    public int kthSmallest(int[][] matrix, int k) {
        Queue<int[]> priorityQueue = new PriorityQueue<>(matrix.length, new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                return arr1[0] - arr2[0];
            }
        });

        for (int i = 0; i < matrix.length; i++) {
            //int[0]：当前元素，int[1]：当前元素的行索引，int[2]：当前元素的列索引
            priorityQueue.offer(new int[]{matrix[i][0], i, 0});
        }

        for (int i = 0; i < k - 1; i++) {
            int[] arr = priorityQueue.poll();

            //当前元素没有到该行末尾时，该行下一个元素入队
            if (arr[2] < matrix[0].length - 1) {
                priorityQueue.offer(new int[]{matrix[arr[1]][arr[2] + 1], arr[1], arr[2] + 1});
            }
        }

        return priorityQueue.poll()[0];
    }

    /**
     * 手动实现小根堆
     * 时间复杂度O(nlogn+klogn)，空间复杂度O(n) (n=matrix.length)
     *
     * @param matrix
     * @param k
     * @return
     */
    public int kthSmallest2(int[][] matrix, int k) {
        //小根堆数组，arr[i][0]：当前元素，arr[i][1]：当前元素的行索引，arr[i][2]：当前元素的列索引
        int[][] arr = new int[matrix.length][3];
        //小根堆大小
        int heapSize = matrix.length;

        for (int i = 0; i < matrix.length; i++) {
            arr[i] = new int[]{matrix[i][0], i, 0};
        }

        //建堆
        for (int i = arr.length / 2 - 1; i > 0; i--) {
            heapify(arr, i, heapSize);
        }

        for (int i = 0; i < k - 1; i++) {
            int[] temp = arr[0];

            //当前元素没有到该行末尾时，该行下一个元素入堆
            if (temp[2] < matrix[0].length - 1) {
                arr[0] = new int[]{matrix[temp[1]][temp[2] + 1], temp[1], temp[2] + 1};
                heapify(arr, 0, heapSize);
            } else {
                //当前元素到该行末尾时，堆尾元素替代堆首元素，再整堆
                heapSize--;
                arr[0] = arr[heapSize];
                heapify(arr, 0, heapSize);
            }
        }

        return arr[0][0];
    }

    /**
     * 二分查找变形
     * 从左下往右上移动，可以以一个数将二维数组分为左右两部分，左边都小于等于该数，右边都大于等于该数
     * 如果二维数组左边小于等于当前中值的数量小于k，说明中值小于第k小元素，左指针=mid+1
     * 如果二维数组左边小于等于当前中值的数量大于等于k，说明中值大于等于第k小元素，右指针=mid
     * 每次循环都保证了第k小元素在左指针-右指针之间，当左指针等于右指针时，即找到第k小元素，等于左指针，也等于右指针
     * 时间复杂度O(nlog(right-left))，空间复杂度O(1) (n=matrix.length, left=matrix[0][0], right=matrix[matrix.length-1][matrix[0].length-1])
     *
     * @param matrix
     * @param k
     * @return
     */
    public int kthSmallest3(int[][] matrix, int k) {
        //当前最小元素
        int left = matrix[0][0];
        //当前最大元素
        int right = matrix[matrix.length - 1][matrix[0].length - 1];
        int mid;

        while (left < right) {
            mid = left + ((right - left) >> 1);

            //小于等于mid的元素个数小于k，说明前k小元素在右边
            if (notBiggerThanMidCount(matrix, mid) < k) {
                left = mid + 1;
            } else {
                //小于等于mid的元素个数大于等于k，说明前k小元素在左边或mid处
                right = mid;
            }
        }

        return left;
    }

    private int notBiggerThanMidCount(int[][] matrix, int mid) {
        int count = 0;
        int i = matrix.length - 1;
        int j = 0;

        while (i >= 0 && j < matrix[0].length) {
            if (matrix[i][j] <= mid) {
                j++;
                //每次统计一列小于等于mid的个数
                count = count + i + 1;
            } else {
                i--;
            }
        }

        return count;
    }

    private void heapify(int[][] arr, int i, int heapSize) {
        int index = i;
        int leftIndex = 2 * i + 1;
        int rightIndex = 2 * i + 2;

        if (leftIndex < heapSize && arr[leftIndex][0] < arr[index][0]) {
            index = leftIndex;
        }

        if (rightIndex < heapSize && arr[rightIndex][0] < arr[index][0]) {
            index = rightIndex;
        }

        if (index != i) {
            int[] temp = arr[i];
            arr[i] = arr[index];
            arr[index] = temp;

            heapify(arr, index, heapSize);
        }
    }
}
