package com.zhang.java;

/**
 * @Date 2023/8/28 08:42
 * @Author zsy
 * @Description 任意子数组和的绝对值的最大值 数组类比Problem53、Problem135、Problem152、Problem238、Problem581、Problem628、Problem845、Offer42、Offer66、FindLeftBiggerRightLessIndex
 * 给你一个整数数组 nums 。一个子数组 [numsl, numsl+1, ..., numsr-1, numsr] 的 和的绝对值 为
 * abs(numsl + numsl+1 + ... + numsr-1 + numsr) 。
 * 请你找出 nums 中 和的绝对值 最大的任意子数组（可能为空），并返回该 最大值 。
 * abs(x) 定义如下：
 * 如果 x 是负整数，那么 abs(x) = -x 。
 * 如果 x 是非负整数，那么 abs(x) = x 。
 * <p>
 * 输入：nums = [1,-3,2,3,-4]
 * 输出：5
 * 解释：子数组 [2,3] 和的绝对值最大，为 abs(2+3) = abs(5) = 5 。
 * <p>
 * 输入：nums = [2,-5,1,-4,3,-2]
 * 输出：8
 * 解释：子数组 [-5,1,-4] 和的绝对值最大，为 abs(-5+1-4) = abs(-8) = 8 。
 * <p>
 * 1 <= nums.length <= 10^5
 * -10^4 <= nums[i] <= 10^4
 */
public class Problem1749 {
    public static void main(String[] args) {
        Problem1749 problem1749 = new Problem1749();
//        int[] nums = {1, -3, 2, 3, -4};
//        int[] nums = {2, -5, 1, -4, 3, -2};
        int[] nums = {-3, -5, -3, -2, -6, 3, 10, -10, -8, -3, 0, 10, 3, -5, 8, 7, -9, -9, 5, -8};
        System.out.println(problem1749.maxAbsoluteSum(nums));
        System.out.println(problem1749.maxAbsoluteSum2(nums));
        System.out.println(problem1749.maxAbsoluteSum3(nums));
    }

    /**
     * 动态规划
     * dpMax[i]：以nums[i]结尾的连续子数组的最大和
     * dpMin[i]：以nums[i]结尾的连续子数组的最小和
     * dpMax[i] = max(dpMax[i-1]+nums[i],nums[i])
     * dpMin[i] = min(dpMax[i-1]+nums[i],nums[i])
     * max = max(max,abs(dpMax[i]),abs(dpMin[i]))
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int maxAbsoluteSum(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int[] dpMax = new int[nums.length];
        int[] dpMin = new int[nums.length];
        //dp初始化
        dpMax[0] = nums[0];
        dpMin[0] = nums[0];

        //初始化子数组和的绝对值的最大值为nums[0]的绝对值
        int max = Math.abs(nums[0]);

        for (int i = 1; i < nums.length; i++) {
            dpMax[i] = Math.max(dpMax[i - 1] + nums[i], nums[i]);
            dpMin[i] = Math.min(dpMin[i - 1] + nums[i], nums[i]);

            //子数组和的绝对值的最大值为最大子数组和和最小子数组和两者中的较大值
            max = Math.max(max, Math.max(Math.abs(dpMax[i]), Math.abs(dpMin[i])));
        }

        return max;
    }

    /**
     * 动态规划优化，使用滚动数组
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int maxAbsoluteSum2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int dpMax = nums[0];
        int dpMin = nums[0];

        //初始化子数组和的绝对值的最大值为nums[0]的绝对值
        int max = Math.abs(nums[0]);

        for (int i = 1; i < nums.length; i++) {
            //保存前一个dp状态，用于更新下一个dp
            int preDpMax = dpMax;
            int preDpMin = dpMin;

            dpMax = Math.max(preDpMax + nums[i], nums[i]);
            dpMin = Math.min(preDpMin + nums[i], nums[i]);

            //子数组和的绝对值的最大值为最大子数组和和最小子数组和两者中的较大值
            max = Math.max(max, Math.max(Math.abs(dpMax), Math.abs(dpMin)));
        }

        return max;
    }

    /**
     * 前缀和+贪心
     * 前缀和可以O(1)获取任意子数组和，子数组和的绝对值的最大值为前缀和数组中的最大值和最小值之差
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int maxAbsoluteSum3(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int[] preSum = new int[nums.length + 1];

        for (int i = 1; i <= nums.length; i++) {
            preSum[i] = preSum[i - 1] + nums[i - 1];
        }

        //前缀和数组中的最大值
        int max = 0;
        //前缀和数组中的最小值
        int min = 0;

        for (int i = 1; i <= nums.length; i++) {
            max = Math.max(max, preSum[i]);
            min = Math.min(min, preSum[i]);
        }

        //子数组和的绝对值的最大值为前缀和数组中的最大值和最小值之差
        return max - min;
    }
}
