package com.zhang.java;

/**
 * @Date 2022/5/2 11:35
 * @Author zsy
 * @Description 买卖股票的最佳时机 类比Problem309、Problem122、Offer63
 * 给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
 * 你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
 * 返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。
 * <p>
 * 输入：[7,1,5,3,6,4]
 * 输出：5
 * 解释：在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
 * 注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票。
 * <p>
 * 输入：prices = [7,6,4,3,1]
 * 输出：0
 * 解释：在这种情况下, 没有交易完成, 所以最大利润为 0。
 * <p>
 * 1 <= prices.length <= 105
 * 0 <= prices[i] <= 10^4
 */
public class Problem121 {
    public static void main(String[] args) {
        Problem121 problem121 = new Problem121();
        int[] prices = {7, 1, 5, 3, 6, 4};
        System.out.println(problem121.maxProfit(prices));
        System.out.println(problem121.maxProfit2(prices));
    }

    /**
     * 动态规划
     * dp[i]：到第i+1天的最大利润
     * dp[i] = dp[i-1]                          (prices[i] <= minPrice)
     * dp[i] = max(dp[i-1], prices[i]-minPrice) (prices[i] > minPrice)
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }

        int[] dp = new int[prices.length];
        int minPrice = prices[0];

        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > minPrice) {
                dp[i] = Math.max(dp[i - 1], prices[i] - minPrice);
            } else {
                dp[i] = dp[i - 1];
                minPrice = prices[i];
            }
        }

        return dp[prices.length - 1];
    }

    /**
     * 动态规划优化，滚动数组
     * dp：当前天的最大最大利润
     * dp = dp                          (prices[i] <= minPrice)
     * dp = max(dp, prices[i]-minPrice) (prices[i] > minPrice)
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param prices
     * @return
     */
    public int maxProfit2(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }

        int dp = 0;
        int minPrice = prices[0];

        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > minPrice) {
                dp = Math.max(dp, prices[i] - minPrice);
            } else {
                minPrice = prices[i];
            }
        }

        return dp;
    }
}
