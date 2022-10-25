package com.zhang.java;

/**
 * @Date 2022/3/25 20:48
 * @Author zsy
 * @Description 连续子数组的最大和 同Problem53
 * 输入一个整型数组，数组中的一个或连续多个整数组成一个子数组。求所有子数组的和的最大值。
 * 要求时间复杂度为O(n)。
 * <p>
 * 输入: nums = [-2, 1, -3, 4, -1, 2, 1, -5, 4]
 * 输出: 6
 * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
 * <p>
 * 1 <= arr.length <= 10^5
 * -100 <= arr[i] <= 100
 */
public class Offer42 {
    public static void main(String[] args) {
        Offer42 offer42 = new Offer42();
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(offer42.maxSubArray(nums));
        System.out.println(offer42.maxSubArray2(nums));
        System.out.println(offer42.maxSubArray3(nums));
        System.out.println(offer42.maxSubArray4(nums));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < nums.length; i++) {
            int tempSum = 0;

            for (int j = i; j < nums.length; j++) {
                tempSum = tempSum + nums[j];

                if (tempSum > max) {
                    max = tempSum;
                }
            }
        }

        return max;
    }

    /**
     * 动态规划
     * dp[i]：以nums[i-1]元素结尾的最大子段和
     * dp[i] = dp[i-1] + nums[i-1] (dp[i-1] > 0)
     * dp[i] = nums[i]             (dp[i-1] <= 0)
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int maxSubArray2(int[] nums) {
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int max = dp[0];

        for (int i = 1; i < nums.length; i++) {
            if (dp[i - 1] > 0) {
                dp[i] = dp[i - 1] + nums[i];
            } else {
                dp[i] = nums[i];
            }

            max = Math.max(max, dp[i]);
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
    public int maxSubArray3(int[] nums) {
        int dp = nums[0];
        int max = dp;

        for (int i = 1; i < nums.length; i++) {
            if (dp > 0) {
                dp = dp + nums[i];
            } else {
                dp = nums[i];
            }
            max = Math.max(max, dp);
        }

        return max;
    }

    /**
     * 分治法
     * 最大子数组和：
     * 1、在当前位置左边
     * 2、在当前位置右边
     * 3、横跨当前位置，左右两边
     * 时间复杂度O(nlogn)，空间复杂度O(logn)
     *
     * @param nums
     * @return
     */
    public int maxSubArray4(int[] nums) {
        return maxSubArray4(nums, 0, nums.length - 1);
    }

    private int maxSubArray4(int[] nums, int left, int right) {
        if (left == right) {
            return nums[left];
        }

        int mid = left + ((right - left) >> 1);

        //左边最大子数组和
        int leftMax = maxSubArray4(nums, left, mid);

        //右边最大子数组和
        int rightMax = maxSubArray4(nums, mid + 1, right);

        //以mid为中心，左右两边求解最最大子数组和并相加，要包括mid
        int midMax = 0;
        int leftMidMax = Integer.MIN_VALUE;
        int rightMidMax = Integer.MIN_VALUE;
        int tempMidSum = 0;

        for (int i = mid; i >= left; i--) {
            tempMidSum = tempMidSum + nums[i];

            if (tempMidSum > leftMidMax) {
                leftMidMax = tempMidSum;
            }
        }

        tempMidSum = 0;

        for (int i = mid + 1; i <= right; i++) {
            tempMidSum = tempMidSum + nums[i];

            if (tempMidSum > rightMidMax) {
                rightMidMax = tempMidSum;
            }
        }

        midMax = leftMidMax + rightMidMax;

        //三种情况最大值即为整个数组的最大子数组和
        return Math.max(Math.max(leftMax, rightMax), midMax);
    }
}
