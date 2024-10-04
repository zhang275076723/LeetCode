package com.zhang.java;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Date 2024/1/8 08:54
 * @Author zsy
 * @Description 乘法表中第k小的数 类比Problem2040 优先队列类比 二分查找类比Problem4、Problem287、Problem373、Problem378、Problem410、Problem441、Problem644、Problem658、Problem719、Problem786、Problem878、Problem1201、Problem1482、Problem1508、Problem1723、Problem2305、Problem2498、CutWood、FindMaxArrayMinAfterKMinus
 * 几乎每一个人都用 乘法表。但是你能在乘法表中快速找到第 k 小的数字吗？
 * 乘法表是大小为 m x n 的一个整数矩阵，其中 mat[i][j] == i * j（下标从 1 开始）。
 * 给你三个整数 m、n 和 k，请你在大小为 m x n 的乘法表中，找出并返回第 k 小的数字。
 * <p>
 * 输入：m = 3, n = 3, k = 5
 * 输出：3
 * 解释：第 5 小的数字是 3 。
 * <p>
 * 输入：m = 2, n = 3, k = 6
 * 输出：6
 * 解释：第 6 小的数字是 6 。
 * <p>
 * 1 <= m, n <= 3 * 10^4
 * 1 <= k <= m * n
 */
public class Problem668 {
    public static void main(String[] args) {
        Problem668 problem668 = new Problem668();
        int m = 2;
        int n = 3;
        int k = 6;
        System.out.println(problem668.findKthNumber(m, n, k));
        System.out.println(problem668.findKthNumber2(m, n, k));
    }

    /**
     * 优先队列，小根堆，多路归并排序 (超时)
     * 时间复杂度O(klogk)，空间复杂度O(k)
     *
     * @param m
     * @param n
     * @param k
     * @return
     */
    public int findKthNumber(int m, int n, int k) {
        //小根堆，arr[0]：1-m中的值，arr[1]：1-n中的值，arr[2]：arr[0]*arr[1]
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                //arr[2]由小到大排序
                return arr1[2] - arr2[2];
            }
        });

        //1-m每个值和1-n中第一个值1组成的数组加入小根堆
        for (int i = 1; i <= m; i++) {
            priorityQueue.offer(new int[]{i, 1, i * 1});
        }

        //小根堆移除k-1个元素，堆顶元素即为第k小的数
        for (int i = 0; i < k - 1; i++) {
            int[] arr = priorityQueue.poll();

            if (arr[1] + 1 <= n) {
                priorityQueue.offer(new int[]{arr[0], arr[1] + 1, arr[0] * (arr[1] + 1)});
            }
        }

        int[] arr = priorityQueue.poll();

        return arr[2];
    }

    /**
     * 二分查找
     * 对[left,right]进行二分查找，left为1，right为m*n，统计m*n乘法表中小于等于mid的个数count，
     * 如果count小于k，则第k小的数在mid右边，left=mid+1；
     * 如果count大于等于k，则第k小的数在mid或mid左边，right=mid
     * 时间复杂度O(m*log(mn))，空间复杂度O(1)
     *
     * @param m
     * @param n
     * @param k
     * @return
     */
    public int findKthNumber2(int m, int n, int k) {
        int left = 1;
        int right = m * n;
        int mid;

        while (left < right) {
            mid = left + ((right - left) >> 1);

            //m*n乘法表中小于等于mid的个数
            int count = 0;

            for (int i = 1; i <= m; i++) {
                //第i行的数都是i的倍数，并且第i行只有n个数
                count = count + Math.min(mid / i, n);
            }

            if (count < k) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }
}
