package com.zhang.java;

/**
 * @Date 2022/6/6 8:44
 * @Author zsy
 * @Description 分割等和子集 类比Problem53、Problem152
 * 给你一个 只包含正整数 的 非空 数组 nums 。请你判断是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
 * <p>
 * 输入：nums = [1,5,11,5]
 * 输出：true
 * 解释：数组可以分割成 [1, 5, 5] 和 [11] 。
 * <p>
 * 输入：nums = [1,2,3,5]
 * 输出：false
 * 解释：数组不能分割成两个元素和相等的子集。
 * <p>
 * 1 <= nums.length <= 200
 * 1 <= nums[i] <= 100
 */
public class Problem416 {
    public static void main(String[] args) {
        Problem416 problem416 = new Problem416();
//        int[] nums = {1, 5, 11, 5};
        int[] nums = {1, 1};
        System.out.println(problem416.canPartition(nums));
        System.out.println(problem416.canPartition2(nums));
    }

    /**
     * 动态规划 01背包
     * dp[i][j]：num[0]-nums[i-1]中是否存在和为j的方案
     * dp[i][j] = dp[i-1][j]                           (nums[i-1] >= j)
     * dp[i][j] = dp[i-1][j] || dp[i-1][j-nums[i-1]]   (nums[i-1] < j)
     * 时间复杂度O(nums.length*(sum/2))，空间复杂度O(nums.length*(sum/2))
     *
     * @param nums
     * @return
     */
    public boolean canPartition(int[] nums) {
        if (nums == null || nums.length < 2) {
            return false;
        }

        int sum = 0;

        for (int num : nums) {
            sum = sum + num;
        }

        //数组元素之和为奇数，则不能分割为两个元素和相等的子集
        if (sum % 2 == 1) {
            return false;
        }

        boolean[][] dp = new boolean[nums.length + 1][sum / 2 + 1];

        //dp初始化
        for (int i = 0; i <= nums.length; i++) {
            dp[i][0] = true;
        }

        for (int i = 1; i <= nums.length; i++) {
            for (int j = 1; j <= sum / 2; j++) {
                if (nums[i - 1] > j) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums[i - 1]];
                }
            }
        }

        return dp[nums.length][sum / 2];
    }

    /**
     * 动态规划优化，使用滚动数组
     * dp[j]：nums[0]-nums[i-1]中是否存在和为j的方案
     * dp[j] = dp[j]                  (nums[i-1] >= j)
     * dp[j] = dp[j] || [j-nums[i-1]]   (nums[i-1] < j)
     * 时间复杂度O(nums.length*(sum/2))，空间复杂度O(sum/2)
     *
     * @param nums
     * @return
     */
    public boolean canPartition2(int[] nums) {
        if (nums == null || nums.length < 2) {
            return false;
        }

        int sum = 0;

        for (int num : nums) {
            sum = sum + num;
        }

        //数组元素之和为奇数，则不能分割为两个元素和相等的子集
        if (sum % 2 == 1) {
            return false;
        }

        boolean[] dp = new boolean[sum / 2 + 1];

        dp[0] = true;

        for (int i = 1; i <= nums.length; i++) {
            //使用滚动数组需要从后往前遍历，因为当前元素会使用到前面的元素，
            //如果前面的元素修改之后，会导致后面的元素无法正确计算
            for (int j = sum / 2; j >= 1; j--) {
                if (nums[i - 1] <= j) {
                    dp[j] = dp[j] || dp[j - nums[i - 1]];
                }
            }
        }

        return dp[sum / 2];
    }
}
