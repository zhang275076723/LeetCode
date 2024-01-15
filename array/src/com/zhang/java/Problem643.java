package com.zhang.java;

/**
 * @Date 2023/8/31 08:06
 * @Author zsy
 * @Description 子数组最大平均数 类比Problem644 滑动窗口类比Problem3、Problem30、Problem76、Problem209、Problem219、Problem220、Problem239、Problem340、Problem438、Problem485、Problem487、Problem567、Problem632、Problem713、Problem1004、Offer48、Offer57_2、Offer59
 * 给你一个由 n 个元素组成的整数数组 nums 和一个整数 k 。
 * 请你找出平均数最大且 长度为 k 的连续子数组，并输出该最大平均数。
 * 任何误差小于 10^-5 的答案都将被视为正确答案。
 * <p>
 * 输入：nums = [1,12,-5,-6,50,3], k = 4
 * 输出：12.75
 * 解释：最大平均数 (12-5-6+50)/4 = 51/4 = 12.75
 * <p>
 * 输入：nums = [5], k = 1
 * 输出：5.00000
 * <p>
 * n == nums.length
 * 1 <= k <= n <= 10^5
 * -10^4 <= nums[i] <= 10^4
 */
public class Problem643 {
    public static void main(String[] args) {
        Problem643 problem643 = new Problem643();
//        int[] nums = {1, 12, -5, -6, 50, 3};
//        int k = 4;
        int[] nums = {-1};
        int k = 1;
        System.out.println(problem643.findMaxAverage(nums, k));
        System.out.println(problem643.findMaxAverage2(nums, k));
    }

    /**
     * 前缀和
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @param k
     * @return
     */
    public double findMaxAverage(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int[] preSum = new int[nums.length + 1];

        for (int i = 1; i <= nums.length; i++) {
            preSum[i] = preSum[i - 1] + nums[i - 1];
        }

        //长度为k的子数组之和的最大值，初始化为第一个长度为k的子数组之和
        int maxSum = preSum[k] - preSum[0];

        for (int i = 0; i <= nums.length - k; i++) {
            maxSum = Math.max(maxSum, preSum[i + k] - preSum[i]);
        }

        return (double) maxSum / k;
    }

    /**
     * 滑动窗口
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @param k
     * @return
     */
    public double findMaxAverage2(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        //长度为k的子数组之和的最大值
        int maxSum = 0;
        int sum = 0;
        int left = 0;
        int right = 0;

        //初始化maxSum为第一个长度为k的子数组之和
        for (int i = 0; i < k; i++) {
            maxSum = maxSum + nums[i];
        }

        while (right < nums.length) {
            sum = sum + nums[right];

            if (right - left + 1 == k) {
                maxSum = Math.max(maxSum, sum);
                sum = sum - nums[left];
                left++;
            }

            right++;
        }

        return (double) maxSum / k;
    }
}
