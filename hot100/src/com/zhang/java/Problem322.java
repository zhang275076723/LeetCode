package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2022/5/24 8:42
 * @Author zsy
 * @Description 零钱兑换 腾讯面试题 动态规划类比Problem279、Problem343、Problem377、Problem416、Problem474、Problem494、Problem518、Problem983、Offer14、Offer14_2、CircleBackToOrigin、Knapsack
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
        int[] coins = {1, 2, 5};
        int amount = 11;
//        int[] coins = {411, 412, 413, 414, 415, 416, 417, 418, 419, 420, 421, 422};
//        int amount = 9864;
        System.out.println(problem322.coinChange(coins, amount));
        System.out.println(problem322.coinChange2(coins, amount));
        System.out.println(problem322.coinChange3(coins, amount));
        System.out.println(problem322.coinChange4(coins, amount));
    }

    /**
     * 动态规划 完全背包
     * dp[i][j]：coins[0]-coins[i-1]凑成金额j所需的最少的硬币个数
     * dp[i][j] = dp[i-1][j]                                          (coins[i-1] > j)
     * dp[i][j] = min(dp[i-1][j], dp[i][j-coins[i-1]] + 1)            (coins[i-1] <= j)
     * 时间复杂度O(n*amount)，空间复杂度O(n*amount) (n=coins.length)
     *
     * @param coins
     * @param amount
     * @return
     */
    public int coinChange(int[] coins, int amount) {
        int[][] dp = new int[coins.length + 1][amount + 1];

        //dp初始化，前0个硬币无法凑成金额1-amount
        for (int j = 1; j <= amount; j++) {
            dp[0][j] = Integer.MAX_VALUE;
        }

        for (int i = 1; i <= coins.length; i++) {
            for (int j = 1; j <= amount; j++) {
                if (coins[i - 1] > j) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    //避免dp[i][j-coins[i-1]]+1在int范围内溢出
                    if (dp[i][j - coins[i - 1]] == Integer.MAX_VALUE) {
                        dp[i][j] = dp[i - 1][j];
                    } else {
                        dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - coins[i - 1]] + 1);
                    }
                }
            }
        }

        return dp[coins.length][amount] == Integer.MAX_VALUE ? -1 : dp[coins.length][amount];
    }

    /**
     * 动态规划优化，使用滚动数组
     * dp[j]：coins[0]-coins[i-1]凑成金额j所需的最少的硬币个数
     * dp[j] = dp[j]                                       (coins[i-1] > j)
     * dp[j] = min(dp[j], dp[j-coins[i-1]] + 1)            (coins[i-1] <= j)
     * 时间复杂度O(n*amount)，空间复杂度O(amount) (n=coins.length)
     *
     * @param coins
     * @param amount
     * @return
     */
    public int coinChange2(int[] coins, int amount) {
        int[] dp = new int[amount + 1];

        //dp初始化，前0个硬币无法凑成金额1-amount
        for (int j = 1; j <= amount; j++) {
            dp[j] = Integer.MAX_VALUE;
        }

        for (int i = 1; i <= coins.length; i++) {
            for (int j = 1; j <= amount; j++) {
                if (coins[i - 1] <= j && dp[j - coins[i - 1]] != Integer.MAX_VALUE) {
                    dp[j] = Math.min(dp[j], dp[j - coins[i - 1]] + 1);
                }
            }
        }

        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }

    /**
     * 动态规划
     * dp[i]：凑成金额i所需的最少的硬币个数
     * dp[i] = min(dp[i-coins[j]] + 1) (0 <= j < coins.length)
     * 时间复杂度O(n*amount)，空间复杂度O(amount) (n=coins.length)
     *
     * @param coins
     * @param amount
     * @return
     */
    public int coinChange3(int[] coins, int amount) {
        //dp[0]=0，用于初始化
        int[] dp = new int[amount + 1];

        for (int i = 1; i <= amount; i++) {
            //初始化为Integer.MAX_VALUE，表示当前金额i不能用硬币凑成
            dp[i] = Integer.MAX_VALUE;

            for (int j = 0; j < coins.length; j++) {
                //当前金额i减去coins[j]大于等于0，并且金额i-coins[j]能用硬币凑成
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
    public int coinChange4(int[] coins, int amount) {
        if (amount < 0) {
            return -1;
        }

        //按照硬币面值由小到大排序
        Arrays.sort(coins);

        //从最大的硬币开始搜索，能够提升效率
        backtrack(coins.length - 1, 0, coins, amount);

        return minCount == Integer.MAX_VALUE ? -1 : minCount;
    }

    /**
     * @param index  硬币种类索引下标coins[index]
     * @param count  当前使用的硬币数量
     * @param coins  不同面额的硬币数组
     * @param amount 当前所要凑的总金额
     */
    private void backtrack(int index, int count, int[] coins, int amount) {
        //硬币种类已经遍历完，或者当前所需使用的硬币数量大于等于使用的最少硬币数量，则剪枝
        if (index < 0 || count >= minCount) {
            return;
        }

        //当前硬币coins[index]正好能凑成amount，更新使用的最少硬币数量
        if (amount % coins[index] == 0) {
            minCount = Math.min(minCount, count + amount / coins[index]);
            return;
        }

        //coins[index]硬币取amount/coins[index]个到0个
        for (int i = amount / coins[index]; i >= 0; i--) {
            backtrack(index - 1, count + i, coins, amount - i * coins[index]);
        }
    }
}
