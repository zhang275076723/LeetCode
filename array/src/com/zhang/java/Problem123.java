package com.zhang.java;

/**
 * @Date 2022/11/15 11:34
 * @Author zsy
 * @Description 买卖股票的最佳时机 III 类比Problem121、Problem122、Problem188、Problem309、Problem714、Offer63
 * 给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
 * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 两笔 交易。
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 * <p>
 * 输入：prices = [3,3,5,0,0,3,1,4]
 * 输出：6
 * 解释：在第 4 天（股票价格 = 0）的时候买入，在第 6 天（股票价格 = 3）的时候卖出，这笔交易所能获得利润 = 3-0 = 3 。
 * 随后，在第 7 天（股票价格 = 1）的时候买入，在第 8 天 （股票价格 = 4）的时候卖出，这笔交易所能获得利润 = 4-1 = 3 。
 * <p>
 * 输入：prices = [1,2,3,4,5]
 * 输出：4
 * 解释：在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
 * 注意你不能在第 1 天和第 2 天接连购买股票，之后再将它们卖出。
 * 因为这样属于同时参与了多笔交易，你必须在再次购买前出售掉之前的股票。
 * <p>
 * 输入：prices = [7,6,4,3,1]
 * 输出：0
 * 解释：在这个情况下, 没有交易完成, 所以最大利润为 0。
 * <p>
 * 输入：prices = [1]
 * 输出：0
 * <p>
 * 1 <= prices.length <= 10^5
 * 0 <= prices[i] <= 10^5
 */
public class Problem123 {
    public static void main(String[] args) {
        Problem123 problem123 = new Problem123();
//        int[] prices = {3, 3, 5, 0, 0, 3, 1, 4};
        int[] prices = {1, 2, 3, 4, 5};
        System.out.println(problem123.maxProfit(prices));
        System.out.println(problem123.maxProfit2(prices));
    }

    /**
     * 动态规划
     * dp[i][0][0]：到prices[i]那天，第一次买入，持有一只股票的最大利润
     * dp[i][0][1]：到prices[i]那天，第一次卖出，不持有股票的最大利润
     * dp[i][1][0]：到prices[i]那天，第二次买入，持有一只股票的最大利润
     * dp[i][1][1]：到prices[i]那天，第二次卖出，不持有股票的最大利润
     * dp[i][0][0] = max(dp[i-1][0][0], -prices[i])
     * dp[i][0][1] = max(dp[i-1][0][1], dp[i-1][0][0]+prices[i])
     * dp[i][1][0] = max(dp[i-1][1][0], dp[i-1][0][1]-prices[i])
     * dp[i][1][1] = max(dp[i-1][1][1], dp[i-1][1][0]+prices[i])
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }

        //dp[][][0]：持有股票
        //dp[][][1]：不持有股票
        int[][][] dp = new int[prices.length][2][2];
        dp[0][0][0] = -prices[0];
        dp[0][1][0] = -prices[0];

        for (int i = 1; i < prices.length; i++) {
            dp[i][0][0] = Math.max(dp[i - 1][0][0], -prices[i]);
            dp[i][0][1] = Math.max(dp[i - 1][0][1], dp[i - 1][0][0] + prices[i]);
            dp[i][1][0] = Math.max(dp[i - 1][1][0], dp[i - 1][0][1] - prices[i]);
            dp[i][1][1] = Math.max(dp[i - 1][1][1], dp[i - 1][1][0] + prices[i]);
        }

        //最后一天，第2次卖出，不持有股票的最大利润，即为所获取的最大利润
        return dp[prices.length - 1][1][1];
    }

    /**
     * 动态规划优化，滚动数组
     * dp1：到prices[i]那天，第一次买入，持有一只股票的最大利润
     * dp2：到prices[i]那天，第一次卖出，不持有股票的最大利润
     * dp3：到prices[i]那天，第二次买入，持有一只股票的最大利润
     * dp4：到prices[i]那天，第二次卖出，不持有股票的最大利润
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
        int dp3 = -prices[0];
        int dp4 = 0;

        for (int i = 1; i < prices.length; i++) {
            //保存上次状态，因为上次状态会在本次发生变化
            int temp1 = dp1;
            int temp2 = dp2;
            int temp3 = dp3;
            int temp4 = dp4;

            dp1 = Math.max(temp1, -prices[i]);
            dp2 = Math.max(temp2, temp1 + prices[i]);
            dp3 = Math.max(temp3, temp2 - prices[i]);
            dp4 = Math.max(temp4, temp3 + prices[i]);
        }

        return dp4;
    }
}
