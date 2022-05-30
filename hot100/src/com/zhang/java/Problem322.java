package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2022/5/24 8:42
 * @Author zsy
 * @Description 零钱兑换 腾讯面试题
 * 给你一个整数数组 coins ，表示不同面额的硬币；以及一个整数 amount ，表示总金额。
 * 计算并返回可以凑成总金额所需的 最少的硬币个数 。如果没有任何一种硬币组合能组成总金额，返回-1 。
 * 你可以认为每种硬币的数量是无限的。
 * <p>
 * 输入：coins = [1, 2, 5], amount = 11
 * 输出：3
 * 解释：11 = 5 + 5 + 1
 * <p>
 * 输入：coins = [2], amount = 3
 * 输出：-1
 * <p>
 * 输入：coins = [1], amount = 0
 * 输出：0
 * <p>
 * 1 <= coins.length <= 12
 * 1 <= coins[i] <= 2^31 - 1
 * 0 <= amount <= 10^4
 */
public class Problem322 {
    /**
     * 回溯+剪枝金额使用的最少硬币数量
     */
    private int minCount = Integer.MAX_VALUE;

    public static void main(String[] args) {
        Problem322 problem322 = new Problem322();
        int[] coins = {411, 412, 413, 414, 415, 416, 417, 418, 419, 420, 421, 422};
        int amount = 9864;
        System.out.println(problem322.coinChange(coins, amount));
        System.out.println(problem322.coinChange2(coins, amount));
    }

    /**
     * 动态规划
     * dp[i]：金额i所需的最少的硬币个数
     * dp[i] = min(dp[i - coins[j]] + 1) (j为硬币种类)
     * 时间复杂度O(coins.length*amount)，空间复杂度O(amount)
     *
     * @param coins
     * @param amount
     * @return
     */
    public int coinChange(int[] coins, int amount) {
        if (amount < 0) {
            return -1;
        }

        //dp[0]=0，用于初始化
        int[] dp = new int[amount + 1];

        for (int i = 1; i <= amount; i++) {
            //初始化为Integer.MAX_VALUE表示当前无法用硬币组成总金额
            dp[i] = Integer.MAX_VALUE;
            for (int j = 0; j < coins.length; j++) {
                //dp[i - coins[j]] != Integer.MAX_VALUE，避免dp[i - coins[j]] + 1溢出
                if (i - coins[j] >= 0 && dp[i - coins[j]] != Integer.MAX_VALUE) {
                    dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
                }
            }
        }

        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }

    /**
     * 回溯+剪枝
     *
     * @param coins
     * @param amount
     * @return
     */
    public int coinChange2(int[] coins, int amount) {
        if (amount < 0) {
            return -1;
        }

        //按照硬币面值由小到大排序
        Arrays.sort(coins);
        //从最大的硬币开始搜索，能够提升效率
        backtrack(coins, amount, coins.length - 1, 0);

        return minCount == Integer.MAX_VALUE ? -1 : minCount;
    }

    /**
     * @param coins  不同面额硬币数组
     * @param amount 当前所需的总金额
     * @param index  硬币种类索引下标coins[index]
     * @param count  当前使用的硬币数量
     */
    private void backtrack(int[] coins, int amount, int index, int count) {
        //硬币种类已经遍历完，或者当前所需使用的硬币数量大于等于使用的最少硬币数量，则剪枝
        if (index < 0 || count + amount / coins[index] >= minCount) {
            return;
        }

        //更新使用的最少硬币数量
        if (amount % coins[index] == 0) {
            minCount = Math.min(minCount, count + amount / coins[index]);
            return;
        }

        //coins[index]硬币取amount/coins[index到0个
        for (int i = amount / coins[index]; i >= 0; i--) {
            backtrack(coins, amount - i * coins[index], index - 1, count + i);
        }
    }
}
