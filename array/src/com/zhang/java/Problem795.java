package com.zhang.java;

import java.util.Stack;

/**
 * @Date 2023/8/29 08:11
 * @Author zsy
 * @Description 区间子数组个数 单调栈类比Problem42、Problem84、Problem255、Problem316、Problem321、Problem402、Problem456、Problem496、Problem503、Problem654、Problem739、Problem907、Problem1019、Problem1856、Problem2104、Problem2454、Problem2487、Offer33、DoubleStackSort
 * 给你一个整数数组 nums 和两个整数：left 及 right 。
 * 找出 nums 中连续、非空且其中最大元素在范围 [left, right] 内的子数组，并返回满足条件的子数组的个数。
 * 生成的测试用例保证结果符合 32-bit 整数范围。
 * <p>
 * 输入：nums = [2,1,4,3], left = 2, right = 3
 * 输出：3
 * 解释：满足条件的三个子数组：[2], [2, 1], [3]
 * <p>
 * 输入：nums = [2,9,2,5,6], left = 2, right = 8
 * 输出：7
 * <p>
 * 1 <= nums.length <= 10^5
 * 0 <= nums[i] <= 10^9
 * 0 <= left <= right <= 10^9
 */
public class Problem795 {
    public static void main(String[] args) {
        Problem795 problem795 = new Problem795();
        int[] nums = {2, 9, 2, 5, 6};
        int left = 2;
        int right = 8;
        System.out.println(problem795.numSubarrayBoundedMax(nums, left, right));
        System.out.println(problem795.numSubarrayBoundedMax2(nums, left, right));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param nums
     * @param left
     * @param right
     * @return
     */
    public int numSubarrayBoundedMax(int[] nums, int left, int right) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int count = 0;

        for (int i = 0; i < nums.length; i++) {
            int max = nums[i];

            for (int j = i; j < nums.length; j++) {
                max = Math.max(max, nums[j]);

                if (left <= max && max <= right) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * 单调栈
     * 当前元素不满足单调递减栈，栈顶元素出栈，作为区间中最大元素的下标索引，
     * 对每一个最大元素，得到以当前元素作为区间最大元素的最大左右边界，得到每个元素作为区间最大元素的区间个数
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @param left
     * @param right
     * @return
     */
    public int numSubarrayBoundedMax2(int[] nums, int left, int right) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        //单调递减栈，用于求区间的最大元素
        Stack<Integer> stack = new Stack<>();
        //arr[i]：nums[i]作为区间最大元素的区间个数
        int[] arr = new int[nums.length];

        for (int i = 0; i < nums.length; i++) {
            //不满足单调递减栈，则栈顶元素出栈，作为区间中最大元素的下标索引
            while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
                //区间的最大元素下标索引
                int index = stack.pop();
                //以nums[index]作为最大元素的最大左边界下标索引
                int leftBound;
                //以nums[index]作为最大元素的最大右边界下标索引
                int rightBound = i - 1;

                if (stack.isEmpty()) {
                    leftBound = 0;
                } else {
                    leftBound = stack.peek() + 1;
                }

                //左边界有(index-leftBound+1)种情况，右边界有(rightBound-index+1)种情况，
                //两者相乘得到有多少个区间以nums[index]作为最大元素
                arr[index] = (index - leftBound + 1) * (rightBound - index + 1);
            }

            stack.push(i);
        }

        while (!stack.isEmpty()) {
            //区间的最大元素下标索引
            int index = stack.pop();
            //以nums[index]作为最大元素的最大左边界下标索引
            int leftBound;
            //以nums[index]作为最大元素的最大右边界下标索引
            int rightBound = nums.length - 1;

            if (stack.isEmpty()) {
                leftBound = 0;
            } else {
                leftBound = stack.peek() + 1;
            }

            //左边界有(index-leftBound+1)种情况，右边界有(rightBound-index+1)种情况，
            //两者相乘得到有多少个区间以nums[index]作为最大元素
            arr[index] = (index - leftBound + 1) * (rightBound - index + 1);
        }

        int count = 0;

        for (int i = 0; i < nums.length; i++) {
            //以nums[i]作为区间最大元素在[left,right]范围内，则累加nums[i]作为区间最大元素的区间个数
            if (left <= nums[i] && nums[i] <= right) {
                count = count + arr[i];
            }
        }

        return count;
    }
}
