package com.zhang.java;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Date 2025/3/25 08:05
 * @Author zsy
 * @Description 最大子序列的分数 类比Problem857、Problem1383 优先队列类比
 * 给你两个下标从 0 开始的整数数组 nums1 和 nums2 ，两者长度都是 n ，再给你一个正整数 k 。
 * 你必须从 nums1 中选一个长度为 k 的 子序列 对应的下标。
 * 对于选择的下标 i0 ，i1 ，...， ik - 1 ，你的 分数 定义如下：
 * nums1 中下标对应元素求和，乘以 nums2 中下标对应元素的 最小值 。
 * 用公式表示： (nums1[i0] + nums1[i1] +...+ nums1[ik - 1]) * min(nums2[i0] , nums2[i1], ... ,nums2[ik - 1]) 。
 * 请你返回 最大 可能的分数。
 * 一个数组的 子序列 下标是集合 {0, 1, ..., n-1} 中删除若干元素得到的剩余集合，也可以不删除任何元素。
 * <p>
 * 输入：nums1 = [1,3,3,2], nums2 = [2,1,3,4], k = 3
 * 输出：12
 * 解释：
 * 四个可能的子序列分数为：
 * - 选择下标 0 ，1 和 2 ，得到分数 (1+3+3) * min(2,1,3) = 7 。
 * - 选择下标 0 ，1 和 3 ，得到分数 (1+3+2) * min(2,1,4) = 6 。
 * - 选择下标 0 ，2 和 3 ，得到分数 (1+3+2) * min(2,3,4) = 12 。
 * - 选择下标 1 ，2 和 3 ，得到分数 (3+3+2) * min(1,3,4) = 8 。
 * 所以最大分数为 12 。
 * <p>
 * 输入：nums1 = [4,2,3,1,1], nums2 = [7,5,10,9,6], k = 1
 * 输出：30
 * 解释：
 * 选择下标 2 最优：nums1[2] * nums2[2] = 3 * 10 = 30 是最大可能分数。
 * <p>
 * n == nums1.length == nums2.length
 * 1 <= n <= 10^5
 * 0 <= nums1[i], nums2[j] <= 10^5
 * 1 <= k <= n
 */
public class Problem2542 {
    public static void main(String[] args) {
        Problem2542 problem2542 = new Problem2542();
//        int[] nums1 = {1, 3, 3, 2};
//        int[] nums2 = {2, 1, 3, 4};
//        int k = 3;
        int[] nums1 = {2, 1, 14, 12};
        int[] nums2 = {11, 7, 13, 6};
        int k = 3;
        System.out.println(problem2542.maxScore(nums1, nums2, k));
    }

    /**
     * 排序+优先队列，小根堆
     * nums1[i]和nums2[i]构成二维数组，按照nums2[i]由大到小排序，小根堆中保存arr排序后长度为k的子序列的nums1[i]，
     * 当前arr[i][1]作为arr排序后长度为k的子序列的nums2[i]的最小值min，则通过小根堆得到arr排序后长度为k的子序列的nums1[i]之和sum，得到长度为k的子序列的分数
     * 时间复杂度O(nlogn+nlogk)，空间复杂度O(n+k) (n=nums1.length=nums2.length)
     *
     * @param nums1
     * @param nums2
     * @param k
     * @return
     */
    public long maxScore(int[] nums1, int[] nums2, int k) {
        //arr[i][0]：nums1中元素，arr[i][1]：nums1中元素对应的nums2中元素
        int[][] arr = new int[nums1.length][2];

        for (int i = 0; i < nums1.length; i++) {
            arr[i][0] = nums1[i];
            arr[i][1] = nums2[i];
        }

        //按照nums2[i]由大到小排序
        //当前arr[i][1]作为arr排序后长度为k的子序列的nums2[i]的最小值min，则通过小根堆得到arr排序后长度为k的子序列的nums1[i]之和sum，得到长度为k的子序列的分数
        mergeSort(arr, 0, arr.length - 1, new int[arr.length][2]);

        //优先队列，小根堆，保存arr排序后长度为k的子序列的nums1[i]
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return a - b;
            }
        });

        //长度为k的子序列的分数，即长度为k的子序列的sum(nums1[i])与min(nums2[i])乘积的最大值
        long result = 0;
        //arr排序后长度为k的子序列的nums1[i]之和，即小根堆中元素之和
        long sum = 0;
        //arr排序后长度为k的子序列的nums2[i]的最小值
        long min;

        for (int i = 0; i < k - 1; i++) {
            priorityQueue.offer(arr[i][0]);
            sum = sum + arr[i][0];
        }

        for (int i = k - 1; i < arr.length; i++) {
            priorityQueue.offer(arr[i][0]);
            sum = sum + arr[i][0];
            //当前arr[i][1]作为arr排序后长度为k的子序列的nums2[i]的最小值min
            min = arr[i][1];

            result = Math.max(result, sum * min);

            //小根堆堆顶元素出堆，保证下次得到长度为k的子序列的nums1[i]之和最大
            int num1 = priorityQueue.poll();
            sum = sum - num1;
        }

        return result;
    }

    private void mergeSort(int[][] arr, int left, int right, int[][] tempArr) {
        if (left >= right) {
            return;
        }

        int mid = left + ((right - left) >> 1);

        mergeSort(arr, left, mid, tempArr);
        mergeSort(arr, mid + 1, right, tempArr);
        merge(arr, left, mid, right, tempArr);
    }

    private void merge(int[][] arr, int left, int mid, int right, int[][] tempArr) {
        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (arr[i][1] >= arr[j][1]) {
                tempArr[k] = arr[i];
                i++;
                k++;
            } else {
                tempArr[k] = arr[j];
                j++;
                k++;
            }
        }

        while (i <= mid) {
            tempArr[k] = arr[i];
            i++;
            k++;
        }

        while (j <= right) {
            tempArr[k] = arr[j];
            j++;
            k++;
        }

        for (k = left; k <= right; k++) {
            arr[k] = tempArr[k];
        }
    }
}
