package com.zhang.java;

/**
 * @Date 2022/5/12 9:03
 * @Author zsy
 * @Description 打家劫舍 类比Problem213、Problem337
 * 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，
 * 影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
 * 给定一个代表每个房屋存放金额的非负整数数组，计算你 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。
 * 输入：[1,2,3,1]
 * 输出：4
 * 解释：偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
 * 偷窃到的最高金额 = 1 + 3 = 4 。
 * <p>
 * 输入：[2,7,9,3,1]
 * 输出：12
 * 解释：偷窃 1 号房屋 (金额 = 2), 偷窃 3 号房屋 (金额 = 9)，接着偷窃 5 号房屋 (金额 = 1)。
 * 偷窃到的最高金额 = 2 + 9 + 1 = 12 。
 * <p>
 * 1 <= nums.length <= 100
 * 0 <= nums[i] <= 400
 */
public class Problem198 {
    public static void main(String[] args) {
        Problem198 problem198 = new Problem198();
        int[] nums = {2, 7, 9, 3, 1};
        System.out.println(problem198.rob(nums));
        System.out.println(problem198.rob2(nums));
    }

    /**
     * 动态规划
     * dp[i]：偷窃到第i个房屋，能够偷窃到的最高金额
     * dp[i] = Math(dp[i-2] + nums[i], dp[i-1])
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        if (nums.length == 1) {
            return nums[0];
        }

        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);

        for (int i = 2; i < nums.length; i++) {
            dp[i] = Math.max(dp[i - 2] + nums[i], dp[i - 1]);
        }

        return dp[nums.length - 1];
    }

    /**
     * 动态规划优化
     * dp[i]只与dp[i-1]和dp[i-2]有关，所有只用两个变量p、q即可
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int rob2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        if (nums.length == 1) {
            return nums[0];
        }

        //dp[i-2]
        int p = nums[0];
        //dp[i-1]
        int q = Math.max(nums[0], nums[1]);

        for (int i = 2; i < nums.length; i++) {
            int temp = Math.max(p + nums[i], q);
            p = q;
            q = temp;
        }

        return q;
    }
}
