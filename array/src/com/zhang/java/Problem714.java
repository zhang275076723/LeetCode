package com.zhang.java;

/**
 * @Date 2022/11/15 20:23
 * @Author zsy
 * @Description 买卖股票的最佳时机含手续费 股票类比Problem121、Problem122、Problem123、Problem188、Problem309、Problem901、Problem2034、Problem2110、Problem2291、Problem2898、Problem3562、Problem3573、Problem3652、Offer63
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
    //最大值为int最大值除以2，避免相加在int范围内溢出
    private final int INF = Integer.MAX_VALUE / 2;

    public static void main(String[] args) {
        Problem714 problem714 = new Problem714();
        int[] prices = {1, 3, 2, 8, 4, 9};
        int fee = 2;
        System.out.println(problem714.maxProfit(prices, fee));
        System.out.println(problem714.maxProfit2(prices, fee));
        System.out.println(problem714.maxProfit3(prices, fee));
    }

    /**
     * 动态规划
     * dp[i][0]：第i天不持有股票的最大利润
     * dp[i][1]：第i天持有股票的最大利润
     * dp[i][0] = max(dp[i-1][0],dp[i-1][1]+prices[i]-fee)
     * dp[i][1] = max(dp[i-1][1],dp[i-1][0]-prices[i])
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param prices
     * @param fee
     * @return
     */
    public int maxProfit(int[] prices, int fee) {
        int[][] dp = new int[prices.length + 1][2];
        //dp初始化
        //第0天不持有股票的最大利润为0
        //第0天不存在持有股票的最大利润
        dp[0][0] = 0;
        dp[0][1] = -INF;

        for (int i = 1; i <= prices.length; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i - 1] - fee);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i - 1]);
        }

        return dp[prices.length][0];
    }

    /**
     * 动态规划优化，使用滚动数组
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param prices
     * @param fee
     * @return
     */
    public int maxProfit2(int[] prices, int fee) {
        int[] dp = new int[2];
        //dp初始化
        //第0天不持有股票的最大利润为0
        //第0天不存在持有股票的最大利润
        dp[0] = 0;
        dp[1] = -INF;

        for (int i = 1; i <= prices.length; i++) {
            int[] temp = new int[2];

            temp[0] = Math.max(dp[0], dp[1] + prices[i - 1] - fee);
            temp[1] = Math.max(dp[1], dp[0] - prices[i - 1]);

            dp = temp;
        }

        return dp[0];
    }

    /**
     * 递归+记忆化搜索
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param prices
     * @param fee
     * @return
     */
    public int maxProfit3(int[] prices, int fee) {
        int[][] dp = new int[prices.length + 1][2];

        for (int i = 0; i <= prices.length; i++) {
            //初始化为int类型的最小值，表示当前dp未访问
            dp[i][0] = Integer.MIN_VALUE;
            dp[i][1] = Integer.MIN_VALUE;
        }

        //type为0：不持有股票的最大利润
        //type为1：持有股票的最大利润
        dfs(prices.length, 0, prices, fee, dp);

        return dp[prices.length][0];
    }

    public int dfs(int i, int type, int[] prices, int fee, int[][] dp) {
        if (i == 0) {
            //第0天不持有股票的最大利润为0
            if (type == 0) {
                dp[i][type] = 0;
            } else {
                //第0天不存在持有股票的最大利润
                dp[i][type] = -INF;
            }

            return dp[i][type];
        }

        //当前dp已访问，直接返回当前dp
        if (dp[i][type] != Integer.MIN_VALUE) {
            return dp[i][type];
        }

        //type为0：不持有股票的最大利润
        if (type == 0) {
            dp[i][type] = Math.max(dfs(i - 1, 0, prices, fee, dp), dfs(i - 1, 1, prices, fee, dp) + prices[i - 1] - fee);
        } else {
            //type为1：持有股票的最大利润
            dp[i][type] = Math.max(dfs(i - 1, 1, prices, fee, dp), dfs(i - 1, 0, prices, fee, dp) - prices[i - 1]);
        }

        return dp[i][type];
    }
}
