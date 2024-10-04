package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/4/6 10:00
 * @Author zsy
 * @Description 滑动窗口的最大值 类比Problem4、Problem480 单调队列类比Problem209、Problem239、Problem862、Problem1696、Offer59_2 滑动窗口类比Problem3、Problem30、Problem76、Problem209、Problem219、Problem220、Problem239、Problem340、Problem438、Problem485、Problem487、Problem567、Problem632、Problem643、Problem713、Problem1004、Offer48、Offer57_2 类比Offer59_2 同Problem239
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
        int k = 3;
        System.out.println(Arrays.toString(offer59.maxSlidingWindow(nums, k)));
        System.out.println(Arrays.toString(offer59.maxSlidingWindow2(nums, k)));
        System.out.println(Arrays.toString(offer59.maxSlidingWindow3(nums, k)));
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
        if (nums == null || nums.length == 0 || k > nums.length) {
            return new int[0];
        }

        int[] result = new int[nums.length - k + 1];

        for (int i = 0; i < result.length; i++) {
            int tempMax = Integer.MIN_VALUE;

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
     * 优先队列，大根堆
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     * (最差情况下，数组单调递增，当前元素入大根堆整堆之后，堆顶元素不需要移除，大根堆最终有n个元素)
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow2(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k > nums.length) {
            return new int[0];
        }

        //大根堆，arr[0]：当前元素，arr[1]：当前元素的下标索引
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                //两个元素值不同，按由元素值由大到小排序
                if (arr1[0] != arr2[0]) {
                    return arr2[0] - arr1[0];
                } else {
                    //两个元素值不同，按元素下标索引由小到大排序
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

            //大根堆堆顶元素，不在滑动窗口的范围内
            while (!priorityQueue.isEmpty() && priorityQueue.peek()[1] <= i - k) {
                priorityQueue.poll();
            }

            //大根堆堆顶元素为滑动窗口的最大值
            result[i - k + 1] = priorityQueue.peek()[0];
        }

        return result;
    }

    /**
     * 单调队列
     * 单调递减队列存放nums数组中由大到小元素的下标索引
     * 时间复杂度O(n)，空间复杂度O(k)
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow3(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k > nums.length) {
            return new int[0];
        }

        //单调递减队列
        Deque<Integer> queue = new LinkedList<>();

        for (int i = 0; i < k; i++) {
            //队尾元素在数组中表示的值小于当前元素，队尾元素出队
            while (!queue.isEmpty() && nums[queue.peekLast()] < nums[i]) {
                queue.pollLast();
            }

            queue.offerLast(i);
        }

        int[] result = new int[nums.length - k + 1];
        result[0] = nums[queue.peekFirst()];

        for (int i = k; i < nums.length; i++) {
            //列首元素索引下标超出当前滑动窗口范围，则出队
            while (!queue.isEmpty() && queue.peekFirst() <= i - k) {
                queue.pollFirst();
            }

            //队尾元素在数组中表示的值小于当前元素，队尾元素出队
            while (!queue.isEmpty() && nums[queue.peekLast()] < nums[i]) {
                queue.pollLast();
            }

            queue.offerLast(i);
            result[i - k + 1] = nums[queue.peekFirst()];
        }

        return result;
    }
}
