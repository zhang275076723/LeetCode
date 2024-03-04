package com.zhang.java;

/**
 * @Date 2022/4/21 16:38
 * @Author zsy
 * @Description 最大子数组和 美团面试题 类比Problem363 数组中的动态规划类比Problem135、Problem152、Problem238、Problem724、Problem768、Problem769、Problem845、Problem1749、Offer42、Offer66、FindLeftBiggerRightLessIndex 子序列和子数组类比Problem115、Problem152、Problem209、Problem300、Problem325、Problem392、Problem491、Problem516、Problem525、Problem560、Problem581、Problem659、Problem673、Problem674、Problem718、Problem862、Problem1143、Offer42、Offer57_2 同Offer42
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
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(problem53.maxSubArray(nums));
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
                max = Math.max(max, tempSum);
            }
        }

        return max;
    }

    /**
     * 动态规划
     * dp[i]：以nums[i]结尾的子数组的最大和
     * dp[i] = max(dp[i-1] + nums[i], nums[i])
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int maxSubArray2(int[] nums) {
        int[] dp = new int[nums.length];
        //dp初始化
        dp[0] = nums[0];
        //子数组和的最大值
        int max = dp[0];

        for (int i = 1; i < nums.length; i++) {
            dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
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
            dp = Math.max(dp + nums[i], nums[i]);
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
