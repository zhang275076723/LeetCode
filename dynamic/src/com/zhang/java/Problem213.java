package com.zhang.java;

/**
 * @Date 2022/5/31 10:57
 * @Author zsy
 * @Description 打家劫舍 II 类比Problem198、Problem337
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
        int[] nums = {2, 3, 2};
        System.out.println(problem213.rob(nums));
    }

    /**
     * 动态规划
     * 相当于把原数组分成两个数组，一个是从[0,n-2]，另一个是从[1,n-1]，
     * 如果遍历第一个房屋，则最后一个房屋不能遍历，即[0,n-2]；如果遍最后个房屋，则第一个房屋不能遍历，即[1,n-1]
     * dp[i]：以nums[i]结尾的房屋偷窃的最高金额
     * dp[i] = max(dp[i-1], dp[i-2] + nums[i])
     * 时间复杂度O(n)，空间复杂度O(n)/空间复杂度O(1)
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

        if (nums.length == 2) {
            return Math.max(nums[0], nums[1]);
        }

//        return Math.max(robInRange(nums, 0, nums.length - 2),
//                robInRange(nums, 1, nums.length - 1));

        return Math.max(robInRange2(nums, 0, nums.length - 2),
                robInRange2(nums, 1, nums.length - 1));
    }

    /**
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @param start
     * @param end
     * @return
     */
    private int robInRange(int[] nums, int start, int end) {
        int[] dp = new int[end - start + 1];
        dp[0] = nums[start];

        for (int i = start + 1; i <= end; i++) {
            if (i - start >= 2) {
                dp[i - start] = Math.max(dp[i - start - 1], dp[i - start - 2] + nums[i]);
            } else {
                dp[i - start] = Math.max(dp[i - start - 1], nums[i]);
            }
        }

        return dp[end - start];
    }

    /**
     * 使用滚动数组
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @param start
     * @param end
     * @return
     */
    private int robInRange2(int[] nums, int start, int end) {
        //dp[i-2]
        int p = 0;
        //dp[i-1]
        int q = nums[start];

        for (int i = start + 1; i <= end; i++) {
            int temp = q;
            q = Math.max(q, p+nums[i]);
            p = temp;
        }

        return q;
    }

}
