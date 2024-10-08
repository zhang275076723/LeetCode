package com.zhang.java;

/**
 * @Date 2022/4/22 8:37
 * @Author zsy
 * @Description 跳跃游戏 跳跃问题类比Problem45、Problem403、Problem975、Problem1306、Problem1340、Problem1345、Problem1377、Problem1654、Problem1696、Problem1871、Problem2297、Problem2498、Problem2770、LCP09
 * 给定一个非负整数数组 nums ，你最初位于数组的 第一个下标 。
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
        int[] nums = {2, 3, 1, 1, 4};
        System.out.println(problem55.canJump(nums));
        System.out.println(problem55.canJump2(nums));
    }

    /**
     * 动态规划
     * dp[i]：能否跳跃到nums[i]
     * dp[i] = true (dp[j] && j+nums[j] >= i)
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public boolean canJump(int[] nums) {
        if (nums.length == 1) {
            return true;
        }

        boolean[] dp = new boolean[nums.length];
        dp[0] = true;

        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && j + nums[j] >= i) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[nums.length - 1];
    }

    /**
     * 贪心
     * 每次找当前位置可跳到的最远位置
     * 如果当前位置 > 最远可到达的位置，即当前位置不可达，返回false
     * 如果当前位置的最远可到达位置 大于 最远可到达的位置，则更新最远可到达的位置
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public boolean canJump2(int[] nums) {
        if (nums.length == 1) {
            return true;
        }

        //当前可以跳到的最远下标索引
        int maxIndex = 0;

        for (int i = 0; i < nums.length; i++) {
            //可以跳到的最远位置小于当前位置，当前位置不可达，返回false
            if (i > maxIndex) {
                return false;
            }

            //更新可跳的最远下标索引
            maxIndex = Math.max(maxIndex, i + nums[i]);

            //可以跳到最后一个元素下标索引，返回true
            if (maxIndex >= nums.length - 1) {
                return true;
            }
        }

        return false;
    }
}
