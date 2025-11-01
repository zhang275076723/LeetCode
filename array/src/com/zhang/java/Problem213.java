package com.zhang.java;

/**
 * @Date 2022/5/31 10:57
 * @Author zsy
 * @Description 打家劫舍 II 类比Problem1388 类比Problem198、Problem337、Problem2560 动态规划类比Problem198、Problem256、Problem265、Problem279、Problem322、Problem338、Problem343、Problem377、Problem416、Problem474、Problem494、Problem518、Problem746、Problem983、Problem1340、Problem1388、Problem1444、Problem1473、Offer14、Offer14_2、Offer60、CircleBackToOrigin、Knapsack
 * 你是一个专业的小偷，计划偷窃沿街的房屋，每间房内都藏有一定的现金。
 * 这个地方所有的房屋都 围成一圈 ，这意味着第一个房屋和最后一个房屋是紧挨着的。
 * 同时，相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警 。
 * 给定一个代表每个房屋存放金额的非负整数数组，计算你 在不触动警报装置的情况下 ，今晚能够偷窃到的最高金额。
 * <p>
 * 输入：nums = [2,3,2]
 * 输出：3
 * 解释：你不能先偷窃 1 号房屋（金额 = 2），然后偷窃 3 号房屋（金额 = 2）, 因为他们是相邻的。
 * <p>
 * 输入：nums = [1,2,3,1]
 * 输出：4
 * 解释：你可以先偷窃 1 号房屋（金额 = 1），然后偷窃 3 号房屋（金额 = 3）。偷窃到的最高金额 = 1 + 3 = 4 。
 * <p>
 * 输入：nums = [1,2,3]
 * 输出：3
 * <p>
 * 1 <= nums.length <= 100
 * 0 <= nums[i] <= 1000
 */
public class Problem213 {
    public static void main(String[] args) {
        Problem213 problem213 = new Problem213();
        int[] nums = {1, 2, 3, 1};
        System.out.println(problem213.rob(nums));
        System.out.println(problem213.rob2(nums));
    }

    /**
     * 动态规划
     * 原数组分成两个数组，一个是从[0,n-2]，另一个是从[1,n-1]，
     * 如果选择nums[0]，则不能选择nums[n-1]；如果选择nums[n-1]，则不能选择nums[0]
     * dp[i]：nums[0]-nums[i]偷窃到的最高金额
     * dp[i] = max(dp[i-1], dp[i-2] + nums[i])
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int rob(int[] nums) {
        int max1 = robInRange(nums, 0, nums.length - 2);
        int max2 = robInRange(nums, 1, nums.length - 1);

        return Math.max(max1, max2);
    }

    /**
     * 动态规划优化，使用滚动数组
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int rob2(int[] nums) {
        int max1 = robInRange2(nums, 0, nums.length - 2);
        int max2 = robInRange2(nums, 1, nums.length - 1);

        return Math.max(max1, max2);
    }

    /**
     * 时间复杂度O(right-left)，空间复杂度O(right-left)
     *
     * @param nums
     * @param left
     * @param right
     * @return
     */
    private int robInRange(int[] nums, int left, int right) {
        if (left > right) {
            return -1;
        }

        if (left == right) {
            return nums[left];
        }

        int[] dp = new int[right - left + 1];
        //dp初始化
        dp[0] = nums[left];
        dp[1] = Math.max(nums[left], nums[left + 1]);

        for (int i = 2; i <= right - left; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[left + i]);
        }

        return dp[right - left];
    }

    /**
     * 时间复杂度O(right-left)，空间复杂度O(1)
     *
     * @param nums
     * @param left
     * @param right
     * @return
     */
    private int robInRange2(int[] nums, int left, int right) {
        if (left > right) {
            return -1;
        }

        if (left == right) {
            return nums[left];
        }

        //dp初始化，dp[i-2]
        int p = nums[left];
        //dp初始化，dp[i-1]
        int q = nums[left + 1];

        for (int i = 2; i <= right - left; i++) {
            int temp = Math.max(q, p + nums[left + i]);
            p = q;
            q = temp;
        }

        return q;
    }
}
