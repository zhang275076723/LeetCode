package com.zhang.java;

/**
 * @Date 2022/11/3 08:23
 * @Author zsy
 * @Description 跳跃游戏 II 华为机试题 跳跃问题类比problem55、Problem403、Problem1306、Problem1340、Problem1345、Problem1696、Problem1871
 * 给你一个非负整数数组 nums ，你最初位于数组的第一个位置。
 * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
 * 你的目标是使用最少的跳跃次数到达数组的最后一个位置。
 * 假设你总是可以到达数组的最后一个位置。
 * <p>
 * 输入: nums = [2,3,1,1,4]
 * 输出: 2
 * 解释: 跳到最后一个位置的最小跳跃数是 2。从下标为 0 跳到下标为 1 的位置，跳 1 步，然后跳 3 步到达数组的最后一个位置。
 * <p>
 * 输入: nums = [2,3,0,1,4]
 * 输出: 2
 * <p>
 * 1 <= nums.length <= 10^4
 * 0 <= nums[i] <= 1000
 */
public class Problem45 {
    public static void main(String[] args) {
        Problem45 problem45 = new Problem45();
        int[] nums = {2, 3, 1, 1, 4};
        System.out.println(problem45.jump(nums));
        System.out.println(problem45.jump2(nums));
    }

    /**
     * 动态规划
     * dp[i]：跳跃到nums[i]所需的最少次数
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int jump(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int[] dp = new int[nums.length];

        for (int i = 1; i < nums.length; i++) {
            //初始化nums[i]不可达
            dp[i] = Integer.MAX_VALUE;

            for (int j = 0; j < i; j++) {
                //nums[j]可达，并且nums[j]可以跳到nums[i]，才更新dp[i]
                if (dp[j] != Integer.MAX_VALUE && j + nums[j] >= i) {
                    dp[i] = Math.min(dp[i], dp[j] + 1);
                }
            }
        }

        return dp[nums.length - 1];
    }

    /**
     * 贪心
     * 使用两个变量，一个变量记录当前可以跳跃到的最远距离，另一个变量记录下次可以跳跃到的最远距离，
     * 当前跳跃到的距离和当前能够跳跃的最远距离相等时，更新当前能够跳跃到的最远距离，最少跳跃次数加1，
     * 当前能够跳跃的最远距离能够跳跃到最后一个位置时，返回所需的最少跳跃次数
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int jump2(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return 0;
        }

        //跳跃到最后一个位置所需的最少跳跃次数
        int count = 0;
        //当前能够跳跃到的最远距离
        int curJumpDistance = 0;
        //下次能够跳跃到的最远距离
        int nextJumpDistance = 0;

        for (int i = 0; i < nums.length; i++) {
            //当前能够跳跃到的最远距离能够跳跃到最后一个位置，返回count
            if (curJumpDistance >= nums.length - 1) {
                return count;
            }

            //更新下次能够跳跃到的最远距离
            nextJumpDistance = Math.max(nextJumpDistance, i + nums[i]);

            //当前跳跃到的距离和当前能够跳跃的最远距离相等时，更新当前能够跳跃的最远距离，并更新最少跳跃次数
            if (i == curJumpDistance) {
                curJumpDistance = nextJumpDistance;
                count++;
            }
        }

        return count;
    }
}
