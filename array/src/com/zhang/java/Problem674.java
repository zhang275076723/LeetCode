package com.zhang.java;

/**
 * @Date 2023/5/2 08:57
 * @Author zsy
 * @Description 最长连续递增序列 子序列和子数组类比Problem53、Problem115、Problem152、Problem209、Problem300、Problem325、Problem392、Problem491、Problem516、Problem525、Problem560、Problem581、Problem659、Problem673、Problem718、Problem862、Problem1143、Offer42、Offer57_2
 * 给定一个未经排序的整数数组，找到最长且 连续递增的子序列，并返回该序列的长度。
 * 连续递增的子序列 可以由两个下标 l 和 r（l < r）确定，如果对于每个 l <= i < r，
 * 都有 nums[i] < nums[i + 1] ，那么子序列 [nums[l], nums[l + 1], ..., nums[r - 1], nums[r]] 就是连续递增子序列。
 * <p>
 * 输入：nums = [1,3,5,4,7]
 * 输出：3
 * 解释：最长连续递增序列是 [1,3,5], 长度为3。
 * 尽管 [1,3,5,7] 也是升序的子序列, 但它不是连续的，因为 5 和 7 在原数组里被 4 隔开。
 * <p>
 * 输入：nums = [2,2,2,2,2]
 * 输出：1
 * 解释：最长连续递增序列是 [2], 长度为1。
 * <p>
 * 1 <= nums.length <= 10^4
 * -10^9 <= nums[i] <= 10^9
 */
public class Problem674 {
    public static void main(String[] args) {
        Problem674 problem674 = new Problem674();
        int[] nums = {1, 3, 5, 4, 7};
        System.out.println(problem674.findLengthOfLCIS(nums));
        System.out.println(problem674.findLengthOfLCIS2(nums));
    }

    /**
     * 动态规划
     * dp[i]：以nums[i]结尾的最长连续递增序列的长度，连续递增序列即为递增子数组
     * dp[i] = dp[i-1] + 1 (nums[i-1] < nums[i])
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int findLengthOfLCIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        //最长连续递增序列的长度，连续递增序列即为递增子数组
        int maxLen = 1;
        int[] dp = new int[nums.length];
        //dp初始化
        dp[0] = 1;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] < nums[i]) {
                dp[i] = dp[i - 1] + 1;
            } else {
                dp[i] = 1;
            }
            maxLen = Math.max(maxLen, dp[i]);
        }

        return maxLen;
    }

    /**
     * 动态规划优化，使用滚动数组
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int findLengthOfLCIS2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        //最长连续递增序列的长度，连续递增序列即为递增子数组
        int maxLen = 1;
        //dp初始化
        int dp = 1;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] < nums[i]) {
                dp = dp + 1;
            } else {
                dp = 1;
            }
            maxLen = Math.max(maxLen, dp);
        }

        return maxLen;
    }
}
