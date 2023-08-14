package com.zhang.java;

import java.util.Stack;

/**
 * @Date 2023/8/11 09:11
 * @Author zsy
 * @Description 子数组范围和 单调栈类比Problem42、Problem84、Problem255、Problem316、Problem321、Problem402、Problem456、Problem496、Problem503、Problem654、Problem739、Problem907、Problem1019、Problem1856、Problem2487、Offer33、DoubleStackSort
 * 给你一个整数数组 nums 。nums 中，子数组的 范围 是子数组中最大元素和最小元素的差值。
 * 返回 nums 中 所有 子数组范围的 和 。
 * 子数组是数组中一个连续 非空 的元素序列。
 * <p>
 * 输入：nums = [1,2,3]
 * 输出：4
 * 解释：nums 的 6 个子数组如下所示：
 * [1]，范围 = 最大 - 最小 = 1 - 1 = 0
 * [2]，范围 = 2 - 2 = 0
 * [3]，范围 = 3 - 3 = 0
 * [1,2]，范围 = 2 - 1 = 1
 * [2,3]，范围 = 3 - 2 = 1
 * [1,2,3]，范围 = 3 - 1 = 2
 * 所有范围的和是 0 + 0 + 0 + 1 + 1 + 2 = 4
 * <p>
 * 输入：nums = [1,3,3]
 * 输出：4
 * 解释：nums 的 6 个子数组如下所示：
 * [1]，范围 = 最大 - 最小 = 1 - 1 = 0
 * [3]，范围 = 3 - 3 = 0
 * [3]，范围 = 3 - 3 = 0
 * [1,3]，范围 = 3 - 1 = 2
 * [3,3]，范围 = 3 - 3 = 0
 * [1,3,3]，范围 = 3 - 1 = 2
 * 所有范围的和是 0 + 0 + 0 + 2 + 0 + 2 = 4
 * <p>
 * 输入：nums = [4,-2,-3,4,1]
 * 输出：59
 * 解释：nums 中所有子数组范围的和是 59
 * <p>
 * 1 <= nums.length <= 1000
 * -10^9 <= nums[i] <= 10^9
 */
public class Problem2104 {
    public static void main(String[] args) {
        Problem2104 problem2104 = new Problem2104();
        int[] nums = {1, 2, 3};
        System.out.println(problem2104.subArrayRanges(nums));
        System.out.println(problem2104.subArrayRanges2(nums));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public long subArrayRanges(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        //nums数组中所有子数组的最大值和最小值差值之和
        long result = 0;

        for (int i = 0; i < nums.length; i++) {
            //nums[i]-nums[j]子数组的最大值
            int max = Integer.MIN_VALUE;
            //nums[i]-nums[j]子数组的最小值
            int min = Integer.MAX_VALUE;

            for (int j = i; j < nums.length; j++) {
                max = Math.max(max, nums[j]);
                min = Math.min(min, nums[j]);
                result = result + max - min;
            }
        }

        return result;
    }

    /**
     * 单调栈
     * 数组中所有子数组中最大元素和最小元素的差值 = 数组中所有子数组中最大值之和 - 数组中所有子数组中最小值之和
     * 单调递减栈存放nums中区间最大元素的下标索引，单调递增栈存放nums中区间最小元素的下标索引
     * 对每一个最大或最小元素，得到以当前元素作为区间最大或最小元素的最大左右边界，加上以当前元素作为最大或最小元素的区间个数
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public long subArrayRanges2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        //nums数组中所有子数组的最大值之和
        long sumMax = 0;
        //nums数组中所有子数组的最小值之和
        long sumMin = 0;
        //单调递减栈，用于求nums数组所有子数组中最大值
        Stack<Integer> stack1 = new Stack<>();
        //单调递增栈，用于求nums数组所有子数组中最小值
        Stack<Integer> stack2 = new Stack<>();

        //求nums数组中所有子数组的最大值之和sumMax
        for (int i = 0; i < nums.length; i++) {
            while (!stack1.isEmpty() && nums[stack1.peek()] < nums[i]) {
                //区间的最大元素下标索引
                int index = stack1.pop();
                //区间的最大元素
                int max = nums[index];
                //以max作为最大元素的最大左边界下标索引
                int left;
                //以max作为最大元素的最大右边界下标索引
                int right = i - 1;

                if (stack1.isEmpty()) {
                    left = 0;
                } else {
                    left = stack1.peek() + 1;
                }

                //加上以max作为最大元素的区间个数，左边界有(index-left+1)种情况，右边界有(right-index+1)种情况，
                //两者相乘得到有多少个区间以max作为最大元素
                sumMax = sumMax + (long) max * (index - left + 1) * (right - index + 1);
            }

            stack1.push(i);
        }

        while (!stack1.isEmpty()) {
            //区间的最大元素下标索引
            int index = stack1.pop();
            //区间的最大元素
            int max = nums[index];
            //以max作为最大元素的最大左边界下标索引
            int left;
            //以max作为最大元素的最大右边界下标索引
            int right = nums.length - 1;

            if (stack1.isEmpty()) {
                left = 0;
            } else {
                left = stack1.peek() + 1;
            }

            //加上以max作为最大元素的区间个数，左边界有(index-left+1)种情况，右边界有(right-index+1)种情况，
            //两者相乘得到有多少个区间以max作为最大元素
            sumMax = sumMax + (long) max * (index - left + 1) * (right - index + 1);
        }

        //求nums数组中所有子数组的最小值之和sumMin
        for (int i = 0; i < nums.length; i++) {
            while (!stack2.isEmpty() && nums[stack2.peek()] > nums[i]) {
                //区间的最小元素下标索引
                int index = stack2.pop();
                //区间的最小元素
                int min = nums[index];
                //以min作为最小元素的最大左边界下标索引
                int left;
                //以min作为最小元素的最大右边界下标索引
                int right = i - 1;

                if (stack2.isEmpty()) {
                    left = 0;
                } else {
                    left = stack2.peek() + 1;
                }

                //加上以min作为最小元素的区间个数，左边界有(index-left+1)种情况，右边界有(right-index+1)种情况，
                //两者相乘得到有多少个区间以min作为最小元素
                sumMin = sumMin + (long) min * (index - left + 1) * (right - index + 1);
            }

            stack2.push(i);
        }

        while (!stack2.isEmpty()) {
            //区间的最小元素下标索引
            int index = stack2.pop();
            //区间的最小元素
            int min = nums[index];
            //以min作为最小元素的最大左边界下标索引
            int left;
            //以min作为最小元素的最大右边界下标索引
            int right = nums.length - 1;

            if (stack2.isEmpty()) {
                left = 0;
            } else {
                left = stack2.peek() + 1;
            }

            //加上以min作为最小元素的区间个数，左边界有(index-left+1)种情况，右边界有(right-index+1)种情况，
            //两者相乘得到有多少个区间以min作为最小元素
            sumMin = sumMin + (long) min * (index - left + 1) * (right - index + 1);
        }

        return sumMax - sumMin;
    }
}
