package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/4/6 10:00
 * @Author zsy
 * @Description 滑动窗口的最大值 同Problem239
 * 给定一个数组 nums 和滑动窗口的大小 k，请找出所有滑动窗口里的最大值。
 * 你可以假设 k 总是有效的，在输入数组不为空的情况下，1 ≤ k ≤ 输入数组的大小。
 * <p>
 * 输入: nums = [1,3,-1,-3,5,3,6,7], 和 k = 3
 * 输出: [3,3,5,5,6,7]
 * 解释:
 * <p>
 * 滑动窗口的位置                 最大值
 * --------------------------    -----
 * [1  3  -1] -3  5  3  6  7       3
 * 1 [3  -1  -3] 5  3  6  7        3
 * 1  3 [-1  -3  5] 3  6  7        5
 * 1  3  -1 [-3  5  3] 6  7        5
 * 1  3  -1  -3 [5  3  6] 7        6
 * 1  3  -1  -3  5 [3  6  7]       7
 */
public class Offer59 {
    public static void main(String[] args) {
        Offer59 offer59 = new Offer59();
        int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
        System.out.println(Arrays.toString(offer59.maxSlidingWindow(nums, 3)));
        System.out.println(Arrays.toString(offer59.maxSlidingWindow2(nums, 3)));
        System.out.println(Arrays.toString(offer59.maxSlidingWindow3(nums, 3)));
    }

    /**
     * 暴力，时间复杂度O(nk)，空间复杂度O(1)
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return new int[0];
        }

        int[] result = new int[nums.length - k + 1];
        int tempMax;

        for (int i = 0; i < result.length; i++) {
            tempMax = Integer.MIN_VALUE;
            for (int j = i; j < i + k; j++) {
                if (nums[j] > tempMax) {
                    tempMax = nums[j];
                }
            }
            result[i] = tempMax;
        }

        return result;
    }

    /**
     * 优先队列，大根堆，堆顶存放的就是当前滑动窗口的最大值，时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow2(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return new int[0];
        }

        int[] result = new int[nums.length - k + 1];
        //优先队列存放二元组(num, index)
        Queue<int[]> queue = new PriorityQueue<>((pair1, pair2) -> {
            //如果num值不相等，按num值从大到小排序
            if (pair1[0] != pair2[0]) {
                return pair2[0] - pair1[0];
            }
            //如果num值相等，按索引从大到小排序
            return pair2[1] - pair1[1];
        });

        for (int i = 0; i < k; i++) {
            queue.offer(new int[]{nums[i], i});
        }
        //当前堆顶元素为滑动窗口最大值
        result[0] = queue.peek()[0];

        for (int i = k; i < nums.length; i++) {
            queue.offer(new int[]{nums[i], i});
            //如果堆顶元素不在滑动窗口中，移除
            while (queue.peek()[1] <= i - k) {
                queue.poll();
            }
            //当前堆顶元素为滑动窗口最大值
            result[i - k + 1] = queue.peek()[0];
        }

        return result;
    }

    /**
     * 单调队列，存放从大到小num值对应的下标索引
     * 时间复杂度O(n)，空间复杂度O(k)
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow3(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return new int[0];
        }

        int[] result = new int[nums.length - k + 1];
        //单调队列
        Deque<Integer> queue = new LinkedList<>();

        for (int i = 0; i < k; i++) {
            //不满足单调递减队列，则出队
            while (!queue.isEmpty() && nums[i] > nums[queue.peekLast()]) {
                queue.pollLast();
            }
            queue.offerLast(i);
        }
        result[0] = nums[queue.peekFirst()];

        for (int i = k; i < nums.length; i++) {
            //当前单调递减队列队首元素对应索引下标超出滑动窗口范围，则出队
            if (queue.peekFirst() <= i - k) {
                queue.pollFirst();
            }
            while (!queue.isEmpty() && nums[i] > nums[queue.peekLast()]) {
                queue.pollLast();
            }
            queue.offerLast(i);
            result[i - k + 1] = nums[queue.peekFirst()];
        }

        return result;
    }
}
