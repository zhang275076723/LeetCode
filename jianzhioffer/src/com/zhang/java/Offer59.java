package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/4/6 10:00
 * @Author zsy
 * @Description 滑动窗口的最大值 类比Offer59_2 同Problem239
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
 * <p>
 * 你可以假设 k 总是有效的，在输入数组不为空的情况下，1 ≤ k ≤ 输入数组的大小。
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
     * 暴力
     * 时间复杂度O(nk)，空间复杂度O(1)
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
     * 优先队列，每个节点存放当前元素和索引下标
     * 时间复杂度O(nlogn)，空间复杂度O(n) (最差情况下，数组单调递增，没有元素从优先队列中移除，往优先队列中添加元素为O(logn))
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow2(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k > nums.length) {
            return new int[0];
        }

        //优先队列存放二元组(num, index)
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                //两个节点值不一样，按由大到小排序
                if (arr1[0] != arr2[0]) {
                    return arr2[0] - arr1[0];
                } else {
                    //两个节点值一样，按索引由小到大排序
                    return arr1[1] - arr2[1];
                }
            }
        });

        for (int i = 0; i < k; i++) {
            priorityQueue.offer(new int[]{nums[i], i});
        }

        int[] result = new int[nums.length - k + 1];
        result[0] = priorityQueue.peek()[0];

        for (int i = k; i < nums.length; i++) {
            priorityQueue.offer(new int[]{nums[i], i});

            //当前优先队列最大值，不在滑动窗口的范围内
            while (!priorityQueue.isEmpty() && priorityQueue.peek()[1] <= i - k) {
                priorityQueue.poll();
            }

            //当前优先队列最大值为滑动窗口的最大值
            result[i - k + 1] = priorityQueue.peek()[0];
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

        //单调递减队列
        Deque<Integer> queue = new LinkedList<>();

        for (int i = 0; i < k; i++) {
            //不满足单调递减队列，则出队
            while (!queue.isEmpty() && nums[i] > nums[queue.peekLast()]) {
                queue.pollLast();
            }

            queue.offerLast(i);
        }

        int[] result = new int[nums.length - k + 1];
        result[0] = nums[queue.peekFirst()];

        for (int i = k; i < nums.length; i++) {
            //当前单调递减队列队首元素对应索引下标超出滑动窗口范围，则出队
            if (queue.peekFirst() < i - k + 1) {
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
