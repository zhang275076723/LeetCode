package com.zhang.java;

/**
 * @Date 2022/7/5 21:18
 * @Author zsy
 * @Description 买卖股票的最佳时机 II 股票类比Problem121、Problem123、Problem188、Problem309、Problem714、Problem901、Problem2034、Problem2110、Problem2291、Offer63
 * 给你一个整数数组 prices ，其中 prices[i] 表示某支股票第 i 天的价格。
 * 在每一天，你可以决定是否购买和/或出售股票。
 * 你在任何时候 最多 只能持有 一股 股票。
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
    //最大值为int最大值除以2，避免相加在int范围内溢出
    private final int INF = Integer.MAX_VALUE / 2;

    public static void main(String[] args) {
        Problem122 problem122 = new Problem122();
        int[] prices = {7, 1, 5, 3, 6, 4};
        System.out.println(problem122.maxProfit(prices));
        System.out.println(problem122.maxProfit2(prices));
        System.out.println(problem122.maxProfit3(prices));
    }

    /**
     * 动态规划
     * dp[i][0]：第i天不持有股票的最大利润
     * dp[i][1]：第i天持有股票的最大利润
     * dp[i][0] = max(dp[i-1][0],dp[i-1][1]+prices[i-1])
     * dp[i][1] = max(dp[i-1][1],dp[i-1][0]-prices[i-1])
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        int[][] dp = new int[prices.length + 1][2];
        //dp初始化
        //第0天不持有股票的最大利润为0
        //第0天不存在持有股票的最大利润
        dp[0][0] = 0;
        dp[0][1] = -INF;

        for (int i = 1; i <= prices.length; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i - 1]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i - 1]);
        }

        return dp[prices.length][0];
    }

    /**
     * 动态规划优化，使用滚动数组
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param prices
     * @return
     */
    public int maxProfit2(int[] prices) {
        int[] dp = new int[2];
        //dp初始化
        //第0天不持有股票的最大利润为0
        //第0天不存在持有股票的最大利润
        dp[0] = 0;
        dp[1] = -INF;

        for (int i = 1; i <= prices.length; i++) {
            int[] temp = new int[2];

            temp[0] = Math.max(dp[0], dp[1] + prices[i - 1]);
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
     * @return
     */
    public int maxProfit3(int[] prices) {
        int[][] dp = new int[prices.length + 1][2];

        for (int i = 0; i <= prices.length; i++) {
            //初始化为int类型的最小值，表示当前dp未访问
            dp[i][0] = Integer.MIN_VALUE;
            dp[i][1] = Integer.MIN_VALUE;
        }

        //type为0：不持有股票的最大利润
        //type为1：持有股票的最大利润
        dfs(prices.length, 0, prices, dp);

        return dp[prices.length][0];
    }

    private int dfs(int i, int type, int[] prices, int[][] dp) {
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
            dp[i][type] = Math.max(dfs(i - 1, 0, prices, dp), dfs(i - 1, 1, prices, dp) + prices[i - 1]);
        } else {
            //type为1：持有股票的最大利润
            dp[i][type] = Math.max(dfs(i - 1, 1, prices, dp), dfs(i - 1, 0, prices, dp) - prices[i - 1]);
        }

        return dp[i][type];
    }
}
