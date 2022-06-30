package com.zhang.java;

/**
 * @Date 2022/4/21 16:38
 * @Author zsy
 * @Description 最大子数组和 类比Problem152、Problem416
 * 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 * 子数组 是数组中的一个连续部分。
 * <p>
 * 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
 * 输出：6
 * 解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
 * <p>
 * 输入：nums = [1]
 * 输出：1
 * <p>
 * 输入：nums = [5,4,-1,7,8]
 * 输出：23
 * <p>
 * 1 <= nums.length <= 10^5
 * -10^4 <= nums[i] <= 10^4
 */
public class Problem53 {
    public static void main(String[] args) {
        Problem53 problem53 = new Problem53();
//        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        int[] nums = {-84, -87, -78, -16, -94, -36, -87, -93, -50, -22, -63, -28, -91, -60, -64, -27, -41, -27, -73, -37, -12, -69, -68, -30, -83, -31, -63, -24, -68, -36, -30, -3, -23, -59, -70, -68, -94, -57, -12, -43, -30, -74, -22, -20, -85, -38, -99, -25, -16, -71, -14, -27, -92, -81, -57, -74, -63, -71, -97, -82, -6, -26, -85, -28, -37, -6, -47, -30, -14, -58, -25, -96, -83, -46, -15, -68, -35, -65, -44, -51, -88, -9, -77, -79, -89, -85, -4, -52, -55, -100, -33, -61, -77, -69, -40, -13, -27, -87, -95, -40};
//        System.out.println(problem53.maxSubArray(nums));
        System.out.println(problem53.maxSubArray2(nums));
        System.out.println(problem53.maxSubArray3(nums));
        System.out.println(problem53.maxSubArray4(nums));
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
     * dp[i]：以nums[i-1]结尾的子数组的最大和
     * dp[i] = nums[i-1]           (dp[i-1] <= 0)
     * dp[i] = dp[i-1] + nums[i-1] (dp[i-1] > 0)
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int maxSubArray2(int[] nums) {
        int[] dp = new int[nums.length + 1];
        int max = Integer.MIN_VALUE;

        for (int i = 1; i < dp.length; i++) {
            if (dp[i - 1] <= 0) {
                dp[i] = nums[i - 1];
            } else {
                dp[i] = dp[i - 1] + nums[i - 1];
            }
            max = Math.max(max, dp[i]);
        }

        return max;
    }

    /**
     * 动态规划优化，使用滚动元素
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int maxSubArray3(int[] nums) {
        int dp = 0;
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < nums.length; i++) {
            if (dp <= 0) {
                dp = nums[i];
            } else {
                dp = dp + nums[i];
            }
            max = Math.max(max, dp);
        }

        return max;
    }

    /**
     * 分治
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

    public int maxSubArray4(int[] nums, int left, int right) {
        if (left == right) {
            return nums[left];
        }

        int mid = left + ((right - left) >> 1);

        //情况一：递归解左边最大子数组和
        int leftSum = maxSubArray4(nums, left, mid);

        //情况二：递归解右边最大子数组和
        int rightSum = maxSubArray4(nums, mid + 1, right);

        //情况三：以mid为中心，左右两边求解最最大子数组和并相加，要包括mid
        int midSum;
        int leftMidSum = Integer.MIN_VALUE;
        int rightMidSum = Integer.MIN_VALUE;
        int tempSum = 0;

        for (int i = mid; i >= left; i--) {
            tempSum = tempSum + nums[i];
            if (tempSum > leftMidSum) {
                leftMidSum = tempSum;
            }
        }

        tempSum = 0;

        for (int i = mid + 1; i <= right; i++) {
            tempSum = tempSum + nums[i];
            if (tempSum > rightMidSum) {
                rightMidSum = tempSum;
            }
        }

        midSum = leftMidSum + rightMidSum;

        //三种情况最大值即为整个数组的最大子数组和
        return Math.max(Math.max(leftSum, rightSum), midSum);
    }
}
