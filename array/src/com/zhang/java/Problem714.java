package com.zhang.java;

/**
 * @Date 2022/11/15 20:23
 * @Author zsy
 * @Description 买卖股票的最佳时机含手续费 股票类比Problem121、Problem122、Problem123、Problem188、Problem309、Problem901、Problem2034、Problem2110、Problem2291、Offer63
 * 给定一个整数数组 prices，其中 prices[i]表示第 i 天的股票价格 ；整数 fee 代表了交易股票的手续费用。
 * 你可以无限次地完成交易，但是你每笔交易都需要付手续费。如果你已经购买了一个股票，在卖出它之前你就不能再继续购买股票了。
 * 返回获得利润的最大值。
 * 注意：这里的一笔交易指买入持有并卖出股票的整个过程，每笔交易你只需要为支付一次手续费。
 * <p>
 * 输入：prices = [1, 3, 2, 8, 4, 9], fee = 2
 * 输出：8
 * 解释：能够达到的最大利润:
 * 在此处买入 prices[0] = 1
 * 在此处卖出 prices[3] = 8
 * 在此处买入 prices[4] = 4
 * 在此处卖出 prices[5] = 9
 * 总利润: ((8 - 1) - 2) + ((9 - 4) - 2) = 8
 * <p>
 * 输入：prices = [1,3,7,5,10,3], fee = 3
 * 输出：6
 * <p>
 * 1 <= prices.length <= 5 * 10^4
 * 1 <= prices[i] < 5 * 10^4
 * 0 <= fee < 5 * 10^4
 */
public class Problem714 {
    public static void main(String[] args) {
        Problem714 problem714 = new Problem714();
        int[] prices = {1, 3, 2, 8, 4, 9};
        int fee = 2;
        System.out.println(problem714.maxProfit(prices, fee));
        System.out.println(problem714.maxProfit2(prices, fee));
    }

    /**
     * 动态规划
     * dp[i][0]：到prices[i]那天，持有一只股票的最大利润
     * dp[i][1]：到prices[i]那天，不持有股票的最大利润
     * dp[i][0] = max(dp[i-1][0], dp[i-1][1]-prices[i])
     * dp[i][1] = max(dp[i-1][1], dp[i-1][0]+prices[i]-fee)
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param prices
     * @param fee
     * @return
     */
    public int maxProfit(int[] prices, int fee) {
        if (prices == null || prices.length == 0) {
            return 0;
        }

        //dp[][0]：持有股票
        //dp[][1]：不持有股票
        int[][] dp = new int[prices.length][2];
        dp[0][0] = -prices[0];

        for (int i = 1; i < prices.length; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] - prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] + prices[i] - fee);
        }

        return dp[prices.length - 1][1];
    }

    /**
     * 动态规划优化，滚动数组
     * dp1：到prices[i]那天，持有一只股票的最大利润
     * dp2：到prices[i]那天，不持有股票的最大利润
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param prices
     * @param fee
     * @return
     */
    public int maxProfit2(int[] prices, int fee) {
        if (prices == null || prices.length == 0) {
            return 0;
        }

        int dp1 = -prices[0];
        int dp2 = 0;

        for (int i = 1; i < prices.length; i++) {
            //保存上次状态，因为上次状态会在本次发生变化
            int temp1 = dp1;
            int temp2 = dp2;

            dp1 = Math.max(temp1, temp2 - prices[i]);
            dp2 = Math.max(temp2, temp1 + prices[i] - fee);
        }

        return dp2;
    }
}
