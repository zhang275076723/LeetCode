package com.zhang.java;

import java.util.Stack;

/**
 * @Date 2023/8/11 08:42
 * @Author zsy
 * @Description 子数组最小乘积的最大值 字节面试题 单调栈类比Problem42、Problem84、Problem255、Problem316、Problem321、Problem402、Problem456、Problem496、Problem503、Problem654、Problem739、Problem907、Problem1019、Problem2104、Offer33、DoubleStackSort
 * 一个数组的 最小乘积 定义为这个数组中 最小值 乘以 数组的 和 。
 * 比方说，数组 [3,2,5] （最小值是 2）的最小乘积为 2 * (3+2+5) = 2 * 10 = 20 。
 * 给你一个正整数数组 nums ，请你返回 nums 任意 非空子数组 的最小乘积 的 最大值 。
 * 由于答案可能很大，请你返回答案对  10^9 + 7 取余 的结果。
 * 请注意，最小乘积的最大值考虑的是取余操作 之前 的结果。题目保证最小乘积的最大值在 不取余 的情况下可以用 64 位有符号整数 保存。
 * 子数组 定义为一个数组的 连续 部分。
 * <p>
 * 输入：nums = [1,2,3,2]
 * 输出：14
 * 解释：最小乘积的最大值由子数组 [2,3,2] （最小值是 2）得到。
 * 2 * (2+3+2) = 2 * 7 = 14 。
 * <p>
 * 输入：nums = [2,3,3,1,2]
 * 输出：18
 * 解释：最小乘积的最大值由子数组 [3,3] （最小值是 3）得到。
 * 3 * (3+3) = 3 * 6 = 18 。
 * <p>
 * 输入：nums = [3,1,5,6,4,2]
 * 输出：60
 * 解释：最小乘积的最大值由子数组 [5,6,4] （最小值是 4）得到。
 * 4 * (5+6+4) = 4 * 15 = 60 。
 * <p>
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i] <= 10^7
 */
public class Problem1856 {
    private final int MOD = (int) 1e9 + 7;

    public static void main(String[] args) {
        Problem1856 problem1856 = new Problem1856();
        int[] nums = {1, 2, 3, 2};
        System.out.println(problem1856.maxSumMinProduct(nums));
        System.out.println(problem1856.maxSumMinProduct2(nums));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int maxSumMinProduct(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        //区间最小值和区间和乘积的最大值
        //使用long，避免int溢出
        long result = 0;

        for (int i = 0; i < nums.length; i++) {
            //arr[i]-arr[j]子数组的最小值
            int min = nums[i];
            //arr[i]-arr[j]子数组元素之和
            //使用long，避免int溢出
            long sum = 0;

            for (int j = i; j < nums.length; j++) {
                min = Math.min(min, nums[j]);
                sum = sum + nums[j];
                result = Math.max(result, sum * min);
            }
        }

        return (int) (result % MOD);
    }

    /**
     * 前缀和+单调栈
     * 单调递增栈存放nums中区间的最小元素下标索引，前缀和用于求区间元素之和
     * 当前元素不满足单调递增栈，栈顶元素出栈，作为区间中最小元素的下标索引，
     * 对每一个最小元素，得到以当前元素作为区间最小元素的最大左右区间，
     * 因为求区间最小值和区间和乘积的最大值，所以每次只需要考虑以当前元素作为区间最小元素的最大区间
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int maxSumMinProduct2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        //前缀和数组，用于求区间元素之和
        //使用long，避免int溢出
        long[] preSum = new long[nums.length + 1];

        for (int i = 1; i <= nums.length; i++) {
            preSum[i] = preSum[i - 1] + nums[i - 1];
        }

        //区间最小值和区间和乘积的最大值
        //使用long，避免int溢出
        long result = 0;
        //单调递增栈，用于求区间的最小元素
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < nums.length; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] > nums[i]) {
                //区间[left,right]的最小元素下标索引
                int index = stack.pop();
                //区间[left,right]的最小元素
                int min = nums[index];
                //区间[left,right]左边界，通过preSum相减得到区间和
                int left;
                //区间[left,right]右边界，通过preSum相减得到区间和
                int right = i - 1;

                //栈为空，区间[left,right]左边界为0
                if (stack.isEmpty()) {
                    left = 0;
                } else {
                    //栈不为空，区间[left,right]左边界为栈顶元素下标索引加1
                    left = stack.peek() + 1;
                }

                //每次只需要考虑以当前元素作为区间最小元素的最大区间
                result = Math.max(result, min * (preSum[right + 1] - preSum[left]));
            }

            stack.push(i);
        }

        //栈中还剩有元素，栈中元素都满足单调递增栈，依次出栈，得到以每个元素作为区间最小元素的最大区间
        while (!stack.isEmpty()) {
            //区间[left,right]的最小元素下标索引
            int index = stack.pop();
            //区间[left,right]的最小元素
            int min = nums[index];
            //区间[left,right]左边界，通过preSum相减得到区间和
            int left;
            //区间[left,right]右边界，通过preSum相减得到区间和
            int right = nums.length - 1;

            //栈为空，区间[left,right]左边界为0
            if (stack.isEmpty()) {
                left = 0;
            } else {
                //栈不为空，区间[left,right]左边界为栈顶元素下标索引加1
                left = stack.peek() + 1;
            }

            //每次只需要考虑以当前元素作为区间最小元素的最大区间
            result = Math.max(result, min * (preSum[right + 1] - preSum[left]));
        }

        return (int) (result % MOD);
    }
}
