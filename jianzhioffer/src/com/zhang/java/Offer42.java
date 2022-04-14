package com.zhang.java;

/**
 * @Date 2022/3/25 20:48
 * @Author zsy
 * @Description 输入一个整型数组，数组中的一个或连续多个整数组成一个子数组。求所有子数组的和的最大值。
 * 要求时间复杂度为O(n)。
 * <p>
 * 输入: nums = [-2, 1, -3, 4, -1, 2, 1, -5, 4]
 * 输出: 6
 * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
 */
public class Offer42 {
    public static void main(String[] args) {
        Offer42 offer42 = new Offer42();
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(offer42.maxSubArray(nums));
        System.out.println(offer42.maxSubArray2(nums));
        System.out.println(offer42.maxSubArray3(nums));
    }

    /**
     * 暴力，时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < nums.length; i++) {
            int tempMax = Integer.MIN_VALUE;
            int tempSum = 0;

            for (int j = i; j < nums.length; j++) {
                tempSum = tempSum + nums[j];
                if (tempSum > tempMax) {
                    tempMax = tempSum;
                }
            }

            if (tempMax > max) {
                max = tempMax;
            }
        }

        return max;
    }

    /**
     * 动态规划，时间复杂度O(n)，空间复杂度O(n)
     * dp[i]：以nums[i-1]元素结尾的最大子段和
     * dp[i] = dp[i-1] + nums[i-1] (dp[i-1] > 0)
     * dp[i] = nums[i] (dp[i-1] <= 0)
     *
     * @param nums
     * @return
     */
    public int maxSubArray2(int[] nums) {
        int sum = Integer.MIN_VALUE;
        int[] dp = new int[nums.length + 1];

        for (int i = 0; i < nums.length; i++) {
            if (dp[i] > 0) {
                dp[i + 1] = dp[i] + nums[i];
            } else {
                dp[i + 1] = nums[i];
            }

            if (dp[i + 1] > sum) {
                sum = dp[i + 1];
            }
        }

        return sum;
    }

    /**
     * 动态规划优化，时间复杂度O(n)，空间复杂度O(1)
     * dp：以某个元素结尾的最大子段和
     *
     * @param nums
     * @return
     */
    public int maxSubArray3(int[] nums) {
        int sum = Integer.MIN_VALUE;
        int dp = 0;

        for (int i = 0; i < nums.length; i++) {
            if (dp > 0) {
                dp = dp + nums[i];
            } else {
                dp = nums[i];
            }

            if (dp > sum) {
                sum = dp;
            }
        }

        return sum;
    }

}
