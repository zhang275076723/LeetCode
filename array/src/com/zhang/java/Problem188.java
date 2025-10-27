package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2022/11/15 16:56
 * @Author zsy
 * @Description 买卖股票的最佳时机 IV 股票类比Problem121、Problem122、Problem123、Problem309、Problem714、Problem901、Problem2034、Problem2110、Problem2291、Offer63
 * 给定一个整数数组 prices ，它的第 i 个元素 prices[i] 是一支给定的股票在第 i 天的价格。
 * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 k 笔交易。
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 * <p>
 * 输入：k = 2, prices = [2,4,1]
 * 输出：2
 * 解释：在第 1 天 (股票价格 = 2) 的时候买入，在第 2 天 (股票价格 = 4) 的时候卖出，这笔交易所能获得利润 = 4-2 = 2 。
 * <p>
 * 输入：k = 2, prices = [3,2,6,5,0,3]
 * 输出：7
 * 解释：在第 2 天 (股票价格 = 2) 的时候买入，在第 3 天 (股票价格 = 6) 的时候卖出, 这笔交易所能获得利润 = 6-2 = 4 。
 * 随后，在第 5 天 (股票价格 = 0) 的时候买入，在第 6 天 (股票价格 = 3) 的时候卖出, 这笔交易所能获得利润 = 3-0 = 3 。
 * <p>
 * 0 <= k <= 100
 * 0 <= prices.length <= 1000
 * 0 <= prices[i] <= 1000
 */
public class Problem188 {
    public static void main(String[] args) {
        Problem188 problem188 = new Problem188();
        int k = 2;
//        int[] prices = {3, 2, 6, 5, 0, 3};
        int[] prices = {1, 2, 3, 4, 5};
        System.out.println(problem188.maxProfit(k, prices));
        System.out.println(problem188.maxProfit2(k, prices));
    }

    /**
     * 动态规划
     * dp[i][j][0]：到prices[i]那天，第j+1次买入，持有一只股票的最大利润
     * dp[i][j][1]：到prices[i]那天，第j+1次卖出，不持有股票的最大利润
     * dp[i][j][0] = max(dp[i-1][j][0], -prices[i])                (j == 0)
     * dp[i][j][0] = max(dp[i-1][j][0], dp[i-1][j-1][1]-prices[i]) (j > 0)
     * dp[i][j][1] = max(dp[i-1][j][1], dp[i-1][j][0]+prices[i])
     * 时间复杂度O(nk)，空间复杂度O(nk)
     *
     * @param k
     * @param prices
     * @return
     */
    public int maxProfit(int k, int[] prices) {
        if (prices == null || prices.length == 0 || k == 0) {
            return 0;
        }

        //dp[][][0]：持有股票
        //dp[][][1]：不持有股票
        int[][][] dp = new int[prices.length][k][2];

        //初始化，prices[0]那天，第1、2...k次买入，持有一只股票的最大利润为-prices[0]
        for (int j = 0; j < k; j++) {
            dp[0][j][0] = -prices[0];
        }

        for (int i = 1; i < prices.length; i++) {
            for (int j = 0; j < k; j++) {
                //对于第1次买入，需要特殊处理
                if (j == 0) {
                    dp[i][j][0] = Math.max(dp[i - 1][j][0], -prices[i]);
                    dp[i][j][1] = Math.max(dp[i - 1][j][1], dp[i - 1][j][0] + prices[i]);
                } else {
                    dp[i][j][0] = Math.max(dp[i - 1][j][0], dp[i - 1][j - 1][1] - prices[i]);
                    dp[i][j][1] = Math.max(dp[i - 1][j][1], dp[i - 1][j][0] + prices[i]);
                }
            }
        }

        //最后一天，第k次卖出，不持有股票的最大利润，即为所获取的最大利润
        return dp[prices.length - 1][k - 1][1];
    }

    /**
     * 动态规划优化，滚动数组
     * dp[j][0]：到prices[i]那天，第j+1次买入，持有一只股票的最大利润
     * dp[j][1]：到prices[i]那天，第j+1次卖出，不持有股票的最大利润
     * 时间复杂度O(nk)，空间复杂度O(k)
     *
     * @param k
     * @param prices
     * @return
     */
    public int maxProfit2(int k, int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }

        int[][] dp = new int[k][2];

        //初始化，prices[0]那天，第1、2...k次买入，持有一只股票的最大利润为-prices[0]
        for (int j = 0; j < k; j++) {
            dp[j][0] = -prices[0];
        }

        for (int i = 1; i < prices.length; i++) {
            //保存上次dp，用于更新本次dp
            int[][] temp = Arrays.copyOf(dp, dp.length);

            //注意：二维数组通过Arrays.copyOf必须逐行复制，不能整体复制，避免浅拷贝

            for (int j = 0; j < k; j++) {
                if (j == 0) {
                    dp[j][0] = Math.max(temp[j][0], -prices[i]);
                    dp[j][1] = Math.max(temp[j][1], temp[j][0] + prices[i]);
                } else {
                    dp[j][0] = Math.max(temp[j][0], temp[j - 1][1] - prices[i]);
                    dp[j][1] = Math.max(temp[j][1], temp[j][0] + prices[i]);
                }
            }
        }

        return dp[k - 1][1];
    }
}
