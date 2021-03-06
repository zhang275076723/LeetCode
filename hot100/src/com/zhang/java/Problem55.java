package com.zhang.java;

/**
 * @Date 2022/4/22 8:37
 * @Author zsy
 * @Description 给定一个非负整数数组 nums ，你最初位于数组的 第一个下标 。
 * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
 * 判断你是否能够到达最后一个下标。
 * <p>
 * 输入：nums = [2,3,1,1,4]
 * 输出：true
 * 解释：可以先跳 1 步，从下标 0 到达下标 1, 然后再从下标 1 跳 3 步到达最后一个下标。
 * <p>
 * 输入：nums = [3,2,1,0,4]
 * 输出：false
 * 解释：无论怎样，总会到达下标为 3 的位置。但该下标的最大跳跃长度是 0 ， 所以永远不可能到达最后一个下标。
 * <p>
 * 1 <= nums.length <= 3*10^4
 * 0 <= nums[i] <= 10^5
 */
public class Problem55 {
    public static void main(String[] args) {
        Problem55 problem55 = new Problem55();
        int[] nums = {1, 0, 1, 0};
        System.out.println(problem55.canJump(nums));
        System.out.println(problem55.canJump2(nums));
    }

    /**
     * 动态规划，时间复杂度O(n^2)，空间复杂度O(n)
     * dp[i]：nums[i]到是否能够到达结尾
     * dp[i] = dp[i+j] (0<=j<=nums[i]) (从nums[i]到nums[i+nums[i]]只要有一个能到达结尾就是true)
     *
     * @param nums
     * @return
     */
    public boolean canJump(int[] nums) {
        if (nums.length == 1){
            return true;
        }

        boolean[] dp = new boolean[nums.length];
        dp[nums.length - 1] = true;

        for (int i = nums.length - 2; i >= 0; i--) {
            for (int j = 0; j <= nums[i]; j++) {
                if (dp[i + j]) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[0];
    }

    /**
     * 贪心，每次找当前位置的最远可达位置，时间复杂度O(n)，空间复杂度O(1)
     * 如果当前位置 大于 最远可到达的位置，即当前位置不可达，返回false
     * 如果当前位置的最远可到达位置 大于 最远可到达的位置，则更新最远可到达的位置
     *
     * @param nums
     * @return
     */
    public boolean canJump2(int[] nums) {
        if (nums.length == 1){
            return true;
        }

        //最远可到达的位置
        int maxDistance = 0;
        for (int i = 0; i < nums.length; i++) {
            //当前位置 大于 最远可到达的位置，即当前位置不可达，返回false
            if (i > maxDistance) {
                return false;
            }
            //当前位置可达，且当前位置的最远可到达位置 大于 最远可到达的位置，则更新最远可到达的位置
            if (nums[i] + i > maxDistance) {
                maxDistance = nums[i] + i;
            }
            //最远可到达的位置 大于等于 结尾位置，返回true
            if (maxDistance >= nums.length - 1) {
                return true;
            }
        }
        return true;
    }
}
