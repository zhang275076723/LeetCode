package com.zhang.java;

/**
 * @Date 2022/4/8 15:26
 * @Author zsy
 * @Description 假设把某股票的价格按照时间先后顺序存储在数组中，请问买卖该股票一次可能获得的最大利润是多少？
 * <p>
 * 输入: [7,1,5,3,6,4]
 * 输出: 5
 * 解释: 在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
 * 注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格。
 * <p>
 * 输入: [7,6,4,3,1]
 * 输出: 0
 * 解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。
 * <p>
 * 0 <= 数组长度 <= 10^5
 */
public class Offer63 {
    public static void main(String[] args) {
        Offer63 offer63 = new Offer63();
        int[] prices = {7, 1, 5, 3, 6, 4};
        System.out.println(offer63.maxProfit(prices));
        System.out.println(offer63.maxProfit2(prices));
    }

    /**
     * 动态规划，时间复杂度(n)，空间复杂度O(n)
     * dp[i]：到price[i]那天的最大利润
     * dp[i] = dp[i-1] (prices[i] < minPrice)
     * dp[i] = max(dp[i-1], price[i]-minPrice) (prices[i] >= minPrice)
     *
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }

        int[] dp = new int[prices.length];
        int minPrice = prices[0];

        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < minPrice) {
                minPrice = prices[i];
                dp[i] = dp[i - 1];
            } else {
                dp[i] = Math.max(dp[i - 1], prices[i] - minPrice);
            }
        }

        return dp[prices.length - 1];
    }

    /**
     * 动态规划优化，时间复杂度(n)，空间复杂度O(1)
     *
     * @param prices
     * @return
     */
    public int maxProfit2(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }

        int dp = 0;
        int minPrice = prices[0];

        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < minPrice) {
                minPrice = prices[i];
            } else {
                dp = Math.max(dp, prices[i] - minPrice);
            }
        }

        return dp;
    }
}
