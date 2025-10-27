package com.zhang.java;

/**
 * @Date 2022/5/24 8:42
 * @Author zsy
 * @Description 零钱兑换 腾讯面试题 字节面试题 拼多多面试题 完全背包类比Problem279、Problem377、Problem518 动态规划类比Problem198、Problem213、Problem256、Problem265、Problem279、Problem338、Problem343、Problem377、Problem416、Problem474、Problem494、Problem518、Problem746、Problem983、Problem1340、Problem1388、Problem1444、Problem1473、Offer14、Offer14_2、Offer60、CircleBackToOrigin、Knapsack
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
    //最大值为int最大值除以2，避免相加在int范围内溢出
    private final int INF = Integer.MAX_VALUE / 2;

    public static void main(String[] args) {
        Problem322 problem322 = new Problem322();
        int[] coins = {1, 2, 5};
        int amount = 11;
//        int[] coins = {411, 412, 413, 414, 415, 416, 417, 418, 419, 420, 421, 422};
//        int amount = 9864;
        System.out.println(problem322.coinChange(coins, amount));
        System.out.println(problem322.coinChange2(coins, amount));
        System.out.println(problem322.coinChange3(coins, amount));
    }

    /**
     * 动态规划 完全背包
     * dp[i][j]：coins[0]-coins[i-1]凑成金额j所需的最少的硬币个数
     * dp[i][j] = dp[i-1][j]                               (coins[i-1] > j)
     * dp[i][j] = min(dp[i-1][j], dp[i][j-coins[i-1]] + 1) (coins[i-1] <= j)
     * 时间复杂度O(n*amount)，空间复杂度O(n*amount)
     *
     * @param coins
     * @param amount
     * @return
     */
    public int coinChange(int[] coins, int amount) {
        int[][] dp = new int[coins.length + 1][amount + 1];

        //dp初始化，前0个硬币凑成金额0的最少的硬币个数为0
        dp[0][0] = 0;

        //dp初始化，前0个硬币无法凑成金额1-amount
        for (int j = 1; j <= amount; j++) {
            dp[0][j] = INF;
        }

        for (int i = 1; i <= coins.length; i++) {
            for (int j = 0; j <= amount; j++) {
                if (coins[i - 1] > j) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - coins[i - 1]] + 1);
                }
            }
        }

        return dp[coins.length][amount] == INF ? -1 : dp[coins.length][amount];
    }

    /**
     * 动态规划优化，使用滚动数组
     * dp[j]：coins[0]-coins[i-1]凑成金额j所需的最少的硬币个数
     * dp[j] = dp[j]                            (coins[i-1] > j)
     * dp[j] = min(dp[j], dp[j-coins[i-1]] + 1) (coins[i-1] <= j)
     * 时间复杂度O(n*amount)，空间复杂度O(amount)
     *
     * @param coins
     * @param amount
     * @return
     */
    public int coinChange2(int[] coins, int amount) {
        int[] dp = new int[amount + 1];

        //dp初始化，前0个硬币凑成金额0的最少的硬币个数为0
        dp[0] = 0;

        //dp初始化，前0个硬币无法凑成金额1-amount
        for (int j = 1; j <= amount; j++) {
            dp[j] = INF;
        }

        for (int i = 1; i <= coins.length; i++) {
            for (int j = 0; j <= amount; j++) {
                if (coins[i - 1] <= j) {
                    dp[j] = Math.min(dp[j], dp[j - coins[i - 1]] + 1);
                }
            }
        }

        return dp[amount] == INF ? -1 : dp[amount];
    }

    /**
     * 递归+记忆化搜索
     * 时间复杂度O(n*amount)，空间复杂度O(n*amount)
     *
     * @param coins
     * @param amount
     * @return
     */
    public int coinChange3(int[] coins, int amount) {
        int[][] dp = new int[coins.length + 1][amount + 1];

        for (int i = 0; i <= coins.length; i++) {
            for (int j = 0; j <= amount; j++) {
                //初始化为-1，表示当前dp未访问
                dp[i][j] = -1;
            }
        }

        dfs(coins.length, amount, coins, dp);

        return dp[coins.length][amount];
    }

    private int dfs(int i, int amount, int[] coins, int[][] dp) {
        if (i == 0) {
            //前0个硬币凑成金额0的最少的硬币个数为0
            if (amount == 0) {
                dp[i][amount] = 0;
                return dp[i][amount];
            } else {
                //前0个硬币无法凑成大于0的金额amount
                dp[i][amount] = INF;
                return dp[i][amount];
            }
        }

        //当前dp已访问，直接返回当前dp
        if (dp[i][amount] != -1) {
            return dp[i][amount];
        }

        if (coins[i - 1] > amount) {
            dp[i][amount] = dfs(i - 1, amount, coins, dp);
            return dp[i][amount];
        }

        dp[i][amount] = Math.min(dfs(i - 1, amount, coins, dp), dfs(i, amount - coins[i - 1], coins, dp) + 1);
        return dp[i][amount];
    }
}
