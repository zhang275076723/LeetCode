package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/5/21 7:51
 * @Author zsy
 * @Description 滑动窗口最大值 同Offer59
 * 给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。
 * 你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
 * <p>
 * 返回 滑动窗口中的最大值 。
 * <p>
 * 输入：nums = [1,3,-1,-3,5,3,6,7], k = 3
 * 输出：[3,3,5,5,6,7]
 * 解释：
 * 滑动窗口的位置                 最大值
 * ---------------               -----
 * [1  3  -1] -3  5  3  6  7       3
 * 1 [3  -1  -3] 5  3  6  7        3
 * 1  3 [-1  -3  5] 3  6  7        5
 * 1  3  -1 [-3  5  3] 6  7        5
 * 1  3  -1  -3 [5  3  6] 7        6
 * 1  3  -1  -3  5 [3  6  7]       7
 * <p>
 * 输入：nums = [1], k = 1
 * 输出：[1]
 * <p>
 * 1 <= nums.length <= 10^5
 * -10^4 <= nums[i] <= 10^4
 * 1 <= k <= nums.length
 */
public class Problem239 {
    public static void main(String[] args) {
        Problem239 problem239 = new Problem239();
        int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
        int k = 3;
        System.out.println(Arrays.toString(problem239.maxSlidingWindow(nums, k)));
        System.out.println(Arrays.toString(problem239.maxSlidingWindow2(nums, k)));
        System.out.println(Arrays.toString(problem239.maxSlidingWindow3(nums, k)));
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
        if (nums == null || k > nums.length || nums.length == 0) {
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
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow2(int[] nums, int k) {
        if (nums == null || k > nums.length || nums.length == 0) {
            return new int[0];
        }

        //优先队列存放二元组(num, index)
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] pair1, int[] pair2) {
                //两个节点值不一样，按由大到小排序
                if (pair1[0] != pair2[0]) {
                    return pair2[0] - pair1[0];
                }
                //两个节点值一样，按索引由大到小排序
                return pair2[1] - pair1[1];
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
            while (priorityQueue.peek()[1] <= i - k) {
                priorityQueue.poll();
            }

            //当前优先队列最大值为滑动窗口的最大值
            result[i - k + 1] = priorityQueue.peek()[0];
        }

        return result;
    }

    /**
     * 单调递减队列，存放由大到小元素对应的索引下标
     * 时间复杂度O(n)，空间复杂度O(k)
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow3(int[] nums, int k) {
        if (nums == null || k > nums.length || nums.length == 0) {
            return new int[0];
        }

        //单调递减队列，存放由大到小元素对应的索引下标
        Deque<Integer> queue = new LinkedList<>();

        for (int i = 0; i < k; i++) {
            //当前元素不满足单调递减队列要求，则队尾元素出队
            while (!queue.isEmpty() && nums[i] > nums[queue.peekLast()]) {
                queue.pollLast();
            }

            //当前元素入队
            queue.offerLast(i);
        }

        int[] result = new int[nums.length - k + 1];
        result[0] = nums[queue.peekFirst()];

        for (int i = k; i < nums.length; i++) {
            //单调递减队列队首元素不在滑动窗口范围内，则队首元素出队
            if (queue.peekFirst() <= i - k) {
                queue.pollFirst();
            }

            //当前元素不满足单调递减队列要求，则队尾元素出队
            while (!queue.isEmpty() && nums[i] > nums[queue.peekLast()]) {
                queue.pollLast();
            }

            //当前元素入队
            queue.offerLast(i);

            //滑动窗口的最大值为单调递减队列的队首元素索引对应的值
            result[i - k + 1] = nums[queue.peekFirst()];
        }

        return result;
    }
}
