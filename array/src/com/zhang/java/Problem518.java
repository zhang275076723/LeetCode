package com.zhang.java;

/**
 * @Date 2022/7/12 8:42
 * @Author zsy
 * @Description 零钱兑换 II 动态规划类比Problem279、Problem322、Problem343、Problem377、Problem416、Problem494、Problem983、Offer14、Offer14_2、CircleBackToOrigin、Knapsack
 * 给你一个整数数组 coins 表示不同面额的硬币，另给一个整数 amount 表示总金额。
 * 请你计算并返回可以凑成总金额的硬币组合数。如果任何硬币组合都无法凑出总金额，返回 0 。
 * 假设每一种面额的硬币有无限个。
 * 题目数据保证结果符合 32 位带符号整数。
 * <p>
 * 输入：amount = 5, coins = [1, 2, 5]
 * 输出：4
 * 解释：有四种方式可以凑成总金额：
 * 5=5
 * 5=2+2+1
 * 5=2+1+1+1
 * 5=1+1+1+1+1
 * <p>
 * 输入：amount = 3, coins = [2]
 * 输出：0
 * 解释：只用面额 2 的硬币不能凑成总金额 3 。
 * <p>
 * 输入：amount = 10, coins = [10]
 * 输出：1
 * <p>
 * 1 <= coins.length <= 300
 * 1 <= coins[i] <= 5000
 * coins 中的所有值 互不相同
 * 0 <= amount <= 5000
 */
public class Problem518 {
    /**
     * 回溯，凑成金额amount的硬币组合数量
     */
    private int count = 0;

    public static void main(String[] args) {
        Problem518 problem518 = new Problem518();
        int amount = 5;
        int[] coins = {1, 2, 5};
//        int amount = 500;
//        int[] coins = {3, 5, 7, 8, 9, 10, 11};
        System.out.println(problem518.change(amount, coins));
        System.out.println(problem518.change2(amount, coins));
        System.out.println(problem518.change3(amount, coins));
    }

    /**
     * 动态规划 完全背包
     * dp[i][j]：coin[0]-coin[i-1]凑成金额j的组合数量
     * dp[i][j] = dp[i-1][j]                       (coins[i-1] > j)
     * dp[i][j] = dp[i-1][j] + dp[i][j-coins[i-1]] (coins[i-1] <= j)
     * 时间复杂度O(n*amount)，空间复杂度O(n*amount) (n=coins.length)
     *
     * @param amount
     * @param coins
     * @return
     */
    public int change(int amount, int[] coins) {
        if (amount == 0) {
            return 1;
        }

        int[][] dp = new int[coins.length + 1][amount + 1];

        //dp初始化，前i种硬币，凑成金额0的数量为1种
        for (int i = 0; i <= coins.length; i++) {
            dp[i][0] = 1;
        }

        for (int i = 1; i <= coins.length; i++) {
            for (int j = 1; j <= amount; j++) {
                if (coins[i - 1] > j) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - coins[i - 1]];
                }
            }
        }

        return dp[coins.length][amount];
    }

    /**
     * 动态规划优化，使用滚动数组
     * 时间复杂度O(n*amount)，空间复杂度O(amount) (n=coins.length)
     *
     * @param amount
     * @param coins
     * @return
     */
    public int change2(int amount, int[] coins) {
        if (amount == 0) {
            return 1;
        }

        int[] dp = new int[amount + 1];
        //初始化，凑成金额为0的组合数量为1
        dp[0] = 1;

        for (int i = 0; i < coins.length; i++) {
            for (int j = 1; j <= amount; j++) {
                if (j >= coins[i]) {
                    dp[j] = dp[j] + dp[j - coins[i]];
                }
            }
        }

        return dp[amount];
    }

    /**
     * 回溯+剪枝
     *
     * @param amount
     * @param coins
     * @return
     */
    public int change3(int amount, int[] coins) {
        if (amount == 0) {
            return 1;
        }

        backtrack(0, amount, coins);

        return count;
    }

    /**
     * @param t  当前遍历到的硬币索引下标
     * @param amount 当前要凑成的金额
     * @param coins  不同面额的硬币数组
     */
    private void backtrack(int t, int amount, int[] coins) {
        //找到凑成amount的硬币组合，直接返回
        if (amount == 0) {
            count++;
            return;
        }

        //硬币种类已经全部遍历结束
        if (t == coins.length) {
            return;
        }

        //coin[index]硬币数量取0-amount/coins[t]个
        for (int i = 0; i <= amount / coins[t]; i++) {
            backtrack(t + 1, amount - i * coins[t], coins);
        }
    }
}
