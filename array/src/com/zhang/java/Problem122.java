package com.zhang.java;

/**
 * @Date 2022/7/5 21:18
 * @Author zsy
 * @Description 买卖股票的最佳时机 II 类比Problem121、Problem309、Offer63
 * 给你一个整数数组 prices ，其中 prices[i] 表示某支股票第 i 天的价格。
 * 在每一天，你可以决定是否购买和/或出售股票。你在任何时候 最多 只能持有 一股 股票。
 * 你也可以先购买，然后在 同一天 出售。
 * 返回 你能获得的 最大 利润 。
 * <p>
 * 输入：prices = [7,1,5,3,6,4]
 * 输出：7
 * 解释：在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5 - 1 = 4 。
 * 随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6 - 3 = 3 。
 * 总利润为 4 + 3 = 7 。
 * <p>
 * 输入：prices = [1,2,3,4,5]
 * 输出：4
 * 解释：在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5 - 1 = 4 。
 * 总利润为 4 。
 * <p>
 * 输入：prices = [7,6,4,3,1]
 * 输出：0
 * 解释：在这种情况下, 交易无法获得正利润，所以不参与交易可以获得最大利润，最大利润为 0 。
 * <p>
 * 1 <= prices.length <= 3 * 10^4
 * 0 <= prices[i] <= 10^4
 */
public class Problem122 {
    public static void main(String[] args) {
        Problem122 problem122 = new Problem122();
        int[] prices = {7, 1, 5, 3, 6, 4};
        System.out.println(problem122.maxProfit(prices));
        System.out.println(problem122.maxProfit2(prices));
        System.out.println(problem122.maxProfit3(prices));
    }

    /**
     * 动态规划
     * dp[i][0]：到price[i]那天，持有一只股票的最大利润
     * dp[i][1]：到price[i]那天，不持有股票的最大利润
     * dp[i][0] = max(dp[i-1][0], dp[i-1][1] - prices[i])
     * dp[i][1] = max(dp[i-1][1], dp[i-1][0] + prices[i])
     * 最大利润 = dp[prices.length-1][1]
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }

        //dp[][0]：持有股票
        //dp[][1]：不持有股票
        int[][] dp = new int[prices.length][2];
        dp[0][0] = -prices[0];

        for (int i = 1; i < prices.length; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] - prices[i]);
            dp[i][1] = Math.max(dp[i - 1][0] + prices[i], dp[i - 1][1]);
        }

        return dp[prices.length - 1][1];
    }

    /**
     * 动态规划优化，滚动数组
     * dp1：到price[i]那天，持有一只股票的最大利润
     * dp2：到price[i]那天，不持有股票的最大利润
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param prices
     * @return
     */
    public int maxProfit2(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }

        int dp1 = -prices[0];
        int dp2 = 0;

        for (int i = 1; i < prices.length; i++) {
            //因为上一次的状态会在本次中变化，所以要使用临时变量
            int temp1 = dp1;
            int temp2 = dp2;

            dp1 = Math.max(temp1, temp2 - prices[i]);
            dp2 = Math.max(temp2, temp1 + prices[i]);
        }

        return dp2;
    }

    /**
     * 贪心
     * 如果第i天股票价格大于第i-1天股票价格，则卖出
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param prices
     * @return
     */
    public int maxProfit3(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }

        int result = 0;

        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                result = result + prices[i] - prices[i - 1];
            }
        }

        return result;
    }
}
