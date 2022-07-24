package com.zhang.java;

/**
 * @Date 2022/5/29 10:11
 * @Author zsy
 * @Description 最佳买卖股票时机含冷冻期 类比Problem121、Problem122、Offer63
 * 给定一个整数数组prices，其中第 prices[i] 表示第 i 天的股票价格 。
 * 设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）:
 * 卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 * <p>
 * 输入: prices = [1,2,3,0,2]
 * 输出: 3
 * 解释: 对应的交易状态为: [买入, 卖出, 冷冻期, 买入, 卖出]
 * <p>
 * 输入: prices = [1]
 * 输出: 0
 * <p>
 * 1 <= prices.length <= 5000
 * 0 <= prices[i] <= 1000
 */
public class Problem309 {
    public static void main(String[] args) {
        Problem309 problem309 = new Problem309();
        int[] prices = {1, 2, 3, 0, 2};
        System.out.println(problem309.maxProfit(prices));
        System.out.println(problem309.maxProfit2(prices));
    }

    /**
     * 动态规划
     * dp[i]：到price[i]那天的最大利润
     * dp[i][0]：到price[i]那天，持有一只股票的最大利润
     * dp[i][1]：到price[i]那天，不持有股票，且处于冷冻期的最大利润
     * dp[i][2]：到price[i]那天，不持有股票，且不处于冷冻期的最大利润
     * dp[i][0] = max(dp[i-1][0], dp[i-1][2] - prices[i])
     * dp[i][1] = dp[i-1][0] + prices[i]
     * dp[i][2] = max(dp[i-1][1], dp[i-1][2])
     * dp[i] = max(dp[i][0], dp[i][1], dp[i][2])
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }

        int[][] dp = new int[prices.length][3];
        dp[0][0] = -prices[0];

        for (int i = 1; i < prices.length; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][2] - prices[i]);
            dp[i][1] = dp[i - 1][0] + prices[i];
            dp[i][2] = Math.max(dp[i - 1][1], dp[i - 1][2]);
        }

        return Math.max(dp[prices.length - 1][0],
                Math.max(dp[prices.length - 1][1], dp[prices.length - 1][2]));
    }

    /**
     * 动态规划优化，因为本次状态只与上次状态有关，所以使用滚动数组替代二维数组
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param prices
     * @return
     */
    public int maxProfit2(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }

        int dp0 = -prices[0];
        int dp1 = 0;
        int dp2 = 0;

        for (int i = 1; i < prices.length; i++) {
            //因为上一次的状态会在本次中变化，所以要使用临时变量
            int temp0 = dp0;
            int temp1 = dp1;
            int temp2 = dp2;

            dp0 = Math.max(temp0, temp2 - prices[i]);
            dp1 = temp0 + prices[i];
            dp2 = Math.max(temp1, temp2);
        }

        return Math.max(dp0, Math.max(dp1, dp2));
    }
}
