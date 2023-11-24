package com.zhang.java;

/**
 * @Date 2023/12/11 08:36
 * @Author zsy
 * @Description 预测赢家 类比Problem337 类比Problem292、Problem293、Problem294、Problem464、Problem1908 动态规划类比 记忆化搜索类比
 * 给你一个整数数组 nums 。玩家 1 和玩家 2 基于这个数组设计了一个游戏。
 * 玩家 1 和玩家 2 轮流进行自己的回合，玩家 1 先手。开始时，两个玩家的初始分值都是 0 。
 * 每一回合，玩家从数组的任意一端取一个数字（即，nums[0] 或 nums[nums.length - 1]），取到的数字将会从数组中移除（数组长度减 1 ）。
 * 玩家选中的数字将会加到他的得分上。当数组中没有剩余数字可取时，游戏结束。
 * 如果玩家 1 能成为赢家，返回 true 。如果两个玩家得分相等，同样认为玩家 1 是游戏的赢家，也返回 true 。
 * 你可以假设每个玩家的玩法都会使他的分数最大化。
 * <p>
 * 输入：nums = [1,5,2]
 * 输出：false
 * 解释：一开始，玩家 1 可以从 1 和 2 中进行选择。
 * 如果他选择 2（或者 1 ），那么玩家 2 可以从 1（或者 2 ）和 5 中进行选择。
 * 如果玩家 2 选择了 5 ，那么玩家 1 则只剩下 1（或者 2 ）可选。
 * 所以，玩家 1 的最终分数为 1 + 2 = 3，而玩家 2 为 5 。
 * 因此，玩家 1 永远不会成为赢家，返回 false 。
 * <p>
 * 输入：nums = [1,5,233,7]
 * 输出：true
 * 解释：玩家 1 一开始选择 1 。然后玩家 2 必须从 5 和 7 中进行选择。无论玩家 2 选择了哪个，玩家 1 都可以选择 233 。
 * 最终，玩家 1（234 分）比玩家 2（12 分）获得更多的分数，所以返回 true，表示玩家 1 可以成为赢家。
 * <p>
 * 1 <= nums.length <= 20
 * 0 <= nums[i] <= 10^7
 */
public class Problem486 {
    public static void main(String[] args) {
        Problem486 problem486 = new Problem486();
        int[] nums = {0, 0, 7, 6, 5, 6, 1};
        System.out.println(problem486.predictTheWinner(nums));
        System.out.println(problem486.predictTheWinner2(nums));
        System.out.println(problem486.predictTheWinner3(nums));
    }

    /**
     * dfs
     * 时间复杂度O(2^n)，空间复杂度O(n) (共O(2^n)种状态，dfs栈的深度O(n))
     *
     * @param nums
     * @return
     */
    public boolean predictTheWinner(int[] nums) {
        //当前玩家先手得到的分数减去对手得到的分数的最大值大于等于0，则当前玩家胜利
        return dfs(nums, 0, nums.length - 1) >= 0;
    }

    /**
     * 递归+记忆化搜索
     * dp[i][j]：在nums[i]-nums[j]范围内，当前玩家先手得到的分数减去对手得到的分数的最大值
     * 时间复杂度O(n^2)，空间复杂度O(n^2)
     *
     * @param nums
     * @return
     */
    public boolean predictTheWinner2(int[] nums) {
        int[][] dp = new int[nums.length][nums.length];

        //dp初始化
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                dp[i][j] = Integer.MAX_VALUE;
            }
        }

        //当前玩家先手得到的分数减去对手得到的分数的最大值大于等于0，则当前玩家胜利
        return dfs(nums, 0, nums.length - 1, dp) >= 0;
    }

    /**
     * 动态规划
     * dp[i][j]：在nums[i]-nums[j]范围内，当前玩家先手得到的分数减去对手得到的分数的最大值
     * dp[i][j] = max(nums[i]-dp[i+1][j],nums[j]-dp[i][j+1])
     * 时间复杂度O(n^2)，空间复杂度O(n^2)
     *
     * @param nums
     * @return
     */
    public boolean predictTheWinner3(int[] nums) {
        int[][] dp = new int[nums.length][nums.length];

        //dp初始化，nums[i]-nums[i]得到的分数为nums[i]
        for (int i = 0; i < nums.length; i++) {
            dp[i][i] = nums[i];
        }

        //当前数组长度i
        for (int i = 2; i <= nums.length; i++) {
            //长度i情况下的起始下标索引j，即当前数组nums[j]-nums[j+i-1]
            for (int j = 0; j <= nums.length - i; j++) {
                //当前玩家先手取nums[j]得到的分数减去对手得到的分数的最大值
                int max1 = nums[j] - dp[j + 1][j + i - 1];
                //当前玩家先手取nums[j+i-1]得到的分数减去对手得到的分数的最大值
                int max2 = nums[j + i - 1] - dp[j][j + i - 2];

                dp[j][j + i - 1] = Math.max(max1, max2);
            }
        }

        //当前玩家先手得到的分数减去对手得到的分数的最大值大于等于0，则当前玩家胜利
        return dp[0][nums.length - 1] >= 0;
    }

    /**
     * 在nums[start]-nums[end]范围内，当前玩家先手得到的分数减去对手得到的分数的最大值
     *
     * @param nums
     * @param start
     * @param end
     * @return
     */
    private int dfs(int[] nums, int start, int end) {
        if (start > end) {
            return 0;
        }

        if (start == end) {
            return nums[start];
        }

        //当前玩家先手取nums[start]得到的分数减去对手得到的分数的最大值
        int max1 = nums[start] - dfs(nums, start + 1, end);
        //当前玩家先手取nums[end]得到的分数减去对手得到的分数的最大值
        int max2 = nums[end] - dfs(nums, start, end - 1);

        return Math.max(max1, max2);
    }

    /**
     * 记忆化搜索，在nums[start]-nums[end]范围内，当前玩家先手得到的分数减去对手得到的分数的最大值
     *
     * @param nums
     * @param start
     * @param end
     * @param dp
     * @return
     */
    private int dfs(int[] nums, int start, int end, int[][] dp) {
        if (start > end) {
            return 0;
        }

        if (start == end) {
            dp[start][end] = nums[start];
            return dp[start][end];
        }

        if (dp[start][end] != Integer.MAX_VALUE) {
            return dp[start][end];
        }

        //当前玩家先手取nums[start]得到的分数减去对手得到的分数的最大值
        int max1 = nums[start] - dfs(nums, start + 1, end, dp);
        //当前玩家先手取nums[end]得到的分数减去对手得到的分数的最大值
        int max2 = nums[end] - dfs(nums, start, end - 1, dp);

        dp[start][end] = Math.max(max1, max2);

        return dp[start][end];
    }
}
