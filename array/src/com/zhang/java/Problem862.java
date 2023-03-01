package com.zhang.java;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @Date 2023/2/3 08:30
 * @Author zsy
 * @Description 和至少为 K 的最短子数组 前缀和类比Problem209、Problem327、Problem437、Problem560、Problem1871、Offer57_2 单调队列类比Problem209、Problem239、Problem1696、Offer59
 * 给你一个整数数组 nums 和一个整数 k ，找出 nums 中和至少为 k 的 最短非空子数组 ，并返回该子数组的长度。
 * 如果不存在这样的 子数组 ，返回 -1 。
 * 子数组 是数组中 连续 的一部分。
 * <p>
 * 输入：nums = [1], k = 1
 * 输出：1
 * <p>
 * 输入：nums = [1,2], k = 4
 * 输出：-1
 * <p>
 * 输入：nums = [2,-1,2], k = 3
 * 输出：3
 * <p>
 * 1 <= nums.length <= 10^5
 * -10^5 <= nums[i] <= 10^5
 * 1 <= k <= 10^9
 */
public class Problem862 {
    public static void main(String[] args) {
        Problem862 problem862 = new Problem862();
//        int[] nums = {2, 1, -2, 2, 3, -2, 1, -2};
//        int k = 3;
        int[] nums = {2, -1, 2};
        int k = 3;
        System.out.println(problem862.shortestSubarray(nums, k));
        System.out.println(problem862.shortestSubarray2(nums, k));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param nums
     * @param k
     * @return
     */
    public int shortestSubarray(int[] nums, int k) {
        int minLength = Integer.MAX_VALUE;

        for (int i = 0; i < nums.length; i++) {
            //使用long，避免int溢出
            long sum = 0;

            for (int j = i; j < nums.length; j++) {
                sum = sum + nums[j];

                if (sum >= k) {
                    minLength = Math.min(minLength, j - i + 1);
                    //当找到大于等于k的子数组之后，再往后找，长度只会更大，则不需要往后继续找，直接break，跳出循环
                    break;
                }
            }
        }

        return minLength == Integer.MAX_VALUE ? -1 : minLength;
    }

    /**
     * 前缀和+单调队列
     * 单调递增队列存放前缀和数组中元素的下标索引
     * 1、当前元素preSum[i]和队首元素preSum[j]之差大于等于k，即[j+1,i]满足子数组长度大于等于k，
     * 则队首元素出队，更新子数组长度
     * 2、当前元素preSum[i]不满足单调递增队列，队尾元素出队，当前元素preSum[i]入队
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @param k
     * @return
     */
    public int shortestSubarray2(int[] nums, int k) {
        //前缀和数组，preSum[i]：nums[0]-nums[i-1]之和
        //使用long避免int溢出
        long[] preSum = new long[nums.length + 1];

        for (int i = 0; i < nums.length; i++) {
            preSum[i + 1] = preSum[i] + nums[i];
        }

        //单调递增队列
        Deque<Integer> queue = new ArrayDeque<>();
        int minLength = Integer.MAX_VALUE;

        for (int i = 0; i < preSum.length; i++) {
            //preSum[i]和队首元素preSum[j]之差大于等于k，即[j+1,i]满足子数组长度大于等于k，
            //则队首元素出队，更新子数组长度
            while (!queue.isEmpty() && preSum[i] - preSum[queue.peekFirst()] >= k) {
                int j = queue.pollFirst();
                minLength = Math.min(minLength, i - j);
            }

            //preSum[i]不满足单调递增队列，队尾元素出队
            while (!queue.isEmpty() && preSum[queue.peekLast()] > preSum[i]) {
                queue.pollLast();
            }

            //preSum[i]入队
            queue.offerLast(i);
        }

        return minLength == Integer.MAX_VALUE ? -1 : minLength;
    }
}
