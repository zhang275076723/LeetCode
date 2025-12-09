package com.zhang.java;

/**
 * @Date 2022/5/29 10:11
 * @Author zsy
 * @Description 最佳买卖股票时机含冷冻期 股票类比Problem121、Problem122、Problem123、Problem188、Problem714、Problem901、Problem2034、Problem2110、Problem2291、Problem2898、Problem3562、Problem3573、Problem3652、Offer63
 * 给定一个整数数组prices，其中第 prices[i] 表示第 i 天的股票价格 。
 * 设计一个算法计算出最大利润。
 * 在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）:
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
    //最大值为int最大值除以2，避免相加在int范围内溢出
    private final int INF = Integer.MAX_VALUE / 2;

    public static void main(String[] args) {
        Problem309 problem309 = new Problem309();
        int[] prices = {1, 2, 3, 0, 2};
        System.out.println(problem309.maxProfit(prices));
        System.out.println(problem309.maxProfit2(prices));
        System.out.println(problem309.maxProfit3(prices));
    }

    /**
     * 动态规划
     * dp[i][0]：第i天不持有股票，并且不处于冷冻期的最大利润
     * dp[i][1]：第i天不持有股票，并且处于冷冻期的最大利润
     * dp[i][2]：第i天持有股票的最大利润
     * dp[i][0] = max(dp[i-1][0],dp[i-1][1])
     * dp[i][1] = dp[i-1][2]+prices[i-1]
     * dp[i][2] = max(dp[i-1][2],dp[i-1][0]-prices[i-1])
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        int[][] dp = new int[prices.length + 1][3];
        //dp初始化
        //第0天不持有股票，并且不处于冷冻期的最大利润为0
        dp[0][0] = 0;
        //不存在第0天不持有股票，并且处于冷冻期的最大利润
        dp[0][1] = -INF;
        //不存在第0天持有股票的最大利润
        dp[0][2] = -INF;

        for (int i = 1; i <= prices.length; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1]);
            dp[i][1] = dp[i - 1][2] + prices[i - 1];
            dp[i][2] = Math.max(dp[i - 1][2], dp[i - 1][0] - prices[i - 1]);
        }

        //第prices.length天不持有股票，不处于冷冻期或者处于冷冻期的最大利润中的较大值，即为所获取的最大利润
        return Math.max(dp[prices.length][0], dp[prices.length][1]);
    }

    /**
     * 动态规划优化，使用滚动数组
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param prices
     * @return
     */
    public int maxProfit2(int[] prices) {
        int[] dp = new int[3];
        //dp初始化
        //第0天不持有股票，并且不处于冷冻期的最大利润为0
        dp[0] = 0;
        //不存在第0天不持有股票，并且处于冷冻期的最大利润
        dp[1] = -INF;
        //不存在第0天持有股票的最大利润
        dp[2] = -INF;

        for (int i = 1; i <= prices.length; i++) {
            int[] temp = new int[3];

            temp[0] = Math.max(dp[0], dp[1]);
            temp[1] = dp[2] + prices[i - 1];
            temp[2] = Math.max(dp[2], dp[0] - prices[i - 1]);

            dp = temp;
        }

        //第prices.length天不持有股票，不处于冷冻期或者处于冷冻期的最大利润中的较大值，即为所获取的最大利润
        return Math.max(dp[0], dp[1]);
    }

    /**
     * 递归+记忆化搜索
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param prices
     * @return
     */
    public int maxProfit3(int[] prices) {
        int[][] dp = new int[prices.length + 1][3];

        for (int i = 0; i <= prices.length; i++) {
            //初始化为int类型的最小值，表示当前dp未访问
            dp[i][0] = Integer.MIN_VALUE;
            dp[i][1] = Integer.MIN_VALUE;
            dp[i][2] = Integer.MIN_VALUE;
        }

        //type为0：不持有股票，并且不处于冷冻期的最大利润
        //type为1：不持有股票，并且处于冷冻期的最大利润
        //type为2：持有股票的最大利润
        dfs(prices.length, 0, prices, dp);
        dfs(prices.length, 1, prices, dp);

        //第prices.length天不持有股票，不处于冷冻期或者处于冷冻期的最大利润中的较大值，即为所获取的最大利润
        return Math.max(dp[prices.length][0], dp[prices.length][1]);
    }

    private int dfs(int i, int type, int[] prices, int[][] dp) {
        if (i == 0) {
            //第0天不持有股票，并且不处于冷冻期的最大利润为0
            if (type == 0) {
                dp[i][type] = 0;
            } else {
                //不存在第0天不持有股票，并且处于冷冻期的最大利润
                //不存在第0天持有股票的最大利润
                dp[i][type] = -INF;
            }

            return dp[i][type];
        }

        //当前dp已访问，直接返回当前dp
        if (dp[i][type] != Integer.MIN_VALUE) {
            return dp[i][type];
        }

        //type为0：不持有股票，并且不处于冷冻期的最大利润
        if (type == 0) {
            dp[i][type] = Math.max(dfs(i - 1, 0, prices, dp), dfs(i - 1, 1, prices, dp));
        } else if (type == 1) {
            //type为1：不持有股票，并且处于冷冻期的最大利润
            dp[i][type] = dfs(i - 1, 2, prices, dp) + prices[i - 1];
        } else {
            //type为2：持有股票的最大利润
            dp[i][type] = Math.max(dfs(i - 1, 2, prices, dp), dfs(i - 1, 0, prices, dp) - prices[i - 1]);
        }

        return dp[i][type];
    }
}
