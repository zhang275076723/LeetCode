package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2025/12/11 10:37
 * @Author zsy
 * @Description 跳跃游戏 IX 类比Problem975 数组中的动态规划类比FindLeftBiggerRightLessIndex 跳跃问题类比
 * 给你一个整数数组 nums。
 * 从任意下标 i 出发，你可以根据以下规则跳跃到另一个下标 j：
 * 仅当 nums[j] < nums[i] 时，才允许跳跃到下标 j，其中 j > i。
 * 仅当 nums[j] > nums[i] 时，才允许跳跃到下标 j，其中 j < i。
 * 对于每个下标 i，找出从 i 出发且可以跳跃 任意 次，能够到达 nums 中的 最大值 是多少。
 * 返回一个数组 ans，其中 ans[i] 是从下标 i 出发可以到达的最大值。
 * <p>
 * 输入: nums = [2,1,3]
 * 输出: [2,2,3]
 * 解释:
 * 对于 i = 0：没有跳跃方案可以获得更大的值。
 * 对于 i = 1：跳到 j = 0，因为 nums[j] = 2 大于 nums[i]。
 * 对于 i = 2：由于 nums[2] = 3 是 nums 中的最大值，没有跳跃方案可以获得更大的值。
 * 因此，ans = [2, 2, 3]。
 * <p>
 * 输入: nums = [2,3,1]
 * 输出: [3,3,3]
 * 解释:
 * 对于 i = 0：向后跳到 j = 2，因为 nums[j] = 1 小于 nums[i] = 2，然后从 i = 2 跳到 j = 1，因为 nums[j] = 3 大于 nums[2]。
 * 对于 i = 1：由于 nums[1] = 3 是 nums 中的最大值，没有跳跃方案可以获得更大的值。
 * 对于 i = 2：跳到 j = 1，因为 nums[j] = 3 大于 nums[2] = 1。
 * 因此，ans = [3, 3, 3]。
 * <p>
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i] <= 10^9
 */
public class Problem3660 {
    public static void main(String[] args) {
        Problem3660 problem3660 = new Problem3660();
        int[] nums = {2, 3, 1};
        System.out.println(Arrays.toString(problem3660.maxValue(nums)));
    }

    /**
     * 动态规划
     * leftMax[i]：nums[0]-nums[i]的最大值
     * rightMin[i]：nums[i]-nums[n-1]的最小值
     * leftMax[i] = max(leftMax[i-1],nums[i])
     * rightMin[i] = min(rightMin[i+1],nums[i])
     * 从后往前遍历nums中元素：
     * 1、leftMax[i]>rightMin[i+1]，假设[0,i]中最大值下标索引为j，[i+1,n-1]中最小值下标索引为k，则可以从i跳到j，再跳到k，最后跳到i+1，
     * 即result[i]可以从result[i+1]中得到
     * 2、leftMax[i]<=rightMin[i+1]，则从i不能往[i+1,n-1]跳跃，只能往[0,n-1]跳跃，即result[i]为[0,i]中最大值leftMax[i]
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int[] maxValue(int[] nums) {
        int[] leftMax = new int[nums.length];
        int[] rightMin = new int[nums.length];

        //leftMax、rightMin初始化
        leftMax[0] = nums[0];
        rightMin[nums.length - 1] = nums[nums.length - 1];

        for (int i = 1; i < nums.length; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], nums[i]);
        }

        for (int i = nums.length - 2; i >= 0; i--) {
            rightMin[i] = Math.min(rightMin[i + 1], nums[i]);
        }

        int[] result = new int[nums.length];
        //result初始化
        result[nums.length - 1] = leftMax[nums.length - 1];

        //注意：是从后往前遍历
        for (int i = nums.length - 2; i >= 0; i--) {
            //假设[0,i]中最大值下标索引为j，[i+1,n-1]中最小值下标索引为k，则可以从i跳到j，再跳到k，最后跳到i+1，
            //即result[i]可以从result[i+1]中得到
            if (leftMax[i] > rightMin[i + 1]) {
                result[i] = result[i + 1];
            } else {
                //从i不能往[i+1,n-1]跳跃，只能往[0,n-1]跳跃，即result[i]为[0,i]中最大值leftMax[i]
                result[i] = leftMax[i];
            }
        }

        return result;
    }
}
