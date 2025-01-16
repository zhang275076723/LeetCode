package com.zhang.java;

/**
 * @Date 2023/5/2 08:44
 * @Author zsy
 * @Description 最长递增子序列的个数 类比Problem300、Problem354、Problem491、Problem674、Problem1143、Problem2407、Problem2771 子序列和子数组类比Problem53、Problem115、Problem152、Problem209、Problem300、Problem325、Problem392、Problem491、Problem516、Problem525、Problem560、Problem581、Problem659、Problem674、Problem718、Problem862、Problem1143、Offer42、Offer57_2
 * 给定一个未排序的整数数组 nums ，返回最长递增子序列的个数 。
 * 注意 这个数列必须是 严格 递增的。
 * <p>
 * 输入: [1,3,5,4,7]
 * 输出: 2
 * 解释: 有两个最长递增子序列，分别是 [1, 3, 4, 7] 和[1, 3, 5, 7]。
 * <p>
 * 输入: [2,2,2,2,2]
 * 输出: 5
 * 解释: 最长递增子序列的长度是1，并且存在5个子序列的长度为1，因此输出5。
 * <p>
 * 1 <= nums.length <= 2000
 * -10^6 <= nums[i] <= 10^6
 */
public class Problem673 {
    public static void main(String[] args) {
        Problem673 problem673 = new Problem673();
        int[] nums = {1, 3, 5, 4, 7};
        System.out.println(problem673.findNumberOfLIS(nums));
    }

    /**
     * 动态规划
     * dp1[i]：以nums[i]结尾的最长递增子序列的长度
     * dp2[i]：以nums[i]结尾的最长递增子序列的个数
     * dp1[i] = max(dp1[j] + 1) (0 <= j < i，且nums[j] < nums[i])
     * dp2[i] = sum(dp2[j]) (0 <= j < i，且nums[j] < nums[i]，且dp1[j] + 1 == dp1[i])
     * dp2[i] = 1               (dp2[i] == 0，即以nums[i]结尾的最长递增子序列的个数至少为1)
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int findNumberOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        //最长递增子序列的长度
        int maxLen = 1;
        int[] dp1 = new int[nums.length];
        int[] dp2 = new int[nums.length];
        //初始化，以nums[0]结尾的最长递增子序列的长度为1
        dp1[0] = 1;
        //初始化，以nums[0]结尾的最长递增子序列的个数为1
        dp2[0] = 1;

        for (int i = 1; i < nums.length; i++) {
            //初始化，以nums[i]结尾的最长递增子序列的长度为1
            dp1[i] = 1;

            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    //更新以nums[i]结尾的最长递增子序列的长度
                    dp1[i] = Math.max(dp1[i], dp1[j] + 1);
                }
            }

            //更新最长递增子序列长度
            maxLen = Math.max(maxLen, dp1[i]);

            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i] && dp1[j] + 1 == dp1[i]) {
                    //更新以nums[i]结尾的最长递增子序列的个数
                    dp2[i] = dp2[i] + dp2[j];
                }
            }

            //以nums[i]结尾的最长递增子序列的个数至少为1
            if (dp2[i] == 0) {
                dp2[i] = 1;
            }
        }

        //最长递增子序列的个数
        int count = 0;

        for (int i = 0; i < nums.length; i++) {
            //以nums[i]结尾的最长递增子序列是最长递增子序列，则count加上以nums[i]结尾的最长递增子序列的个数
            if (dp1[i] == maxLen) {
                count = count + dp2[i];
            }
        }

        return count;
    }
}
