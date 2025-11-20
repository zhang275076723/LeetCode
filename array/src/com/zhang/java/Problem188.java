package com.zhang.java;

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
    //最大值为int最大值除以2，避免相加在int范围内溢出
    private final int INF = Integer.MAX_VALUE / 2;

    public static void main(String[] args) {
        Problem188 problem188 = new Problem188();
        int k = 2;
        int[] prices = {3, 2, 6, 5, 0, 3};
        System.out.println(problem188.maxProfit(k, prices));
        System.out.println(problem188.maxProfit2(k, prices));
        System.out.println(problem188.maxProfit3(k, prices));
    }

    /**
     * 动态规划
     * dp[i][j][0]：第i天最多交易j次，不持有股票的最大利润
     * dp[i][j][1]：第i天最多交易j次，持有股票的最大利润
     * dp[i][j][0] = max(dp[i-1][j][0],dp[i-1][j][1]+prices[i-1])
     * dp[i][j][1] = max(dp[i-1][j][1],dp[i-1][j-1][0]-prices[i-1])
     * 时间复杂度O(nk)，空间复杂度O(nk)
     *
     * @param k
     * @param prices
     * @return
     */
    public int maxProfit(int k, int[] prices) {
        int[][][] dp = new int[prices.length + 1][k + 1][2];

        //dp初始化
        //第0天最多交易j次，不持有股票的最大利润为0
        //第0天最多交易j次，不存在持有股票的最大利润
        for (int j = 0; j <= k; j++) {
            dp[0][j][0] = 0;
            dp[0][j][1] = -INF;
        }

        //dp初始化
        //第i天最多交易0次，不持有股票的最大利润为0
        //第i天最多交易0次，不存在持有股票的最大利润
        for (int i = 1; i <= prices.length; i++) {
            dp[i][0][0] = 0;
            dp[i][0][1] = -INF;
        }

        for (int i = 1; i <= prices.length; i++) {
            for (int j = 1; j <= k; j++) {
                dp[i][j][0] = Math.max(dp[i - 1][j][0], dp[i - 1][j][1] + prices[i - 1]);
                dp[i][j][1] = Math.max(dp[i - 1][j][1], dp[i - 1][j - 1][0] - prices[i - 1]);
            }
        }

        return dp[prices.length][k][0];
    }

    /**
     * 动态规划优化，使用滚动数组
     * 时间复杂度O(nk)，空间复杂度O(k)
     *
     * @param k
     * @param prices
     * @return
     */
    public int maxProfit2(int k, int[] prices) {
        int[][] dp = new int[k + 1][2];

        //dp初始化
        //第0天最多交易j次，不持有股票的最大利润为0
        //第0天最多交易j次，不存在持有股票的最大利润
        for (int j = 0; j <= k; j++) {
            dp[j][0] = 0;
            dp[j][1] = -INF;
        }

        for (int i = 1; i <= prices.length; i++) {
            int[][] temp = new int[k + 1][2];

            for (int j = 1; j <= k; j++) {
                temp[j][0] = Math.max(dp[j][0], dp[j][1] + prices[i - 1]);
                temp[j][1] = Math.max(dp[j][1], dp[j - 1][0] - prices[i - 1]);
            }

            dp = temp;
        }

        return dp[k][0];
    }

    /**
     * 递归+记忆化搜索
     * 时间复杂度O(nk)，空间复杂度O(nk)
     *
     * @param k
     * @param prices
     * @return
     */
    public int maxProfit3(int k, int[] prices) {
        int[][][] dp = new int[prices.length + 1][k + 1][2];

        for (int i = 0; i <= prices.length; i++) {
            for (int j = 0; j <= k; j++) {
                //初始化为int类型的最小值，表示当前dp未访问
                dp[i][j][0] = Integer.MIN_VALUE;
                dp[i][j][1] = Integer.MIN_VALUE;
            }
        }

        //type为0：不持有股票的最大利润
        //type为1：持有股票的最大利润
        dfs(prices.length, k, 0, prices, dp);

        return dp[prices.length][k][0];
    }

    private int dfs(int i, int j, int type, int[] prices, int[][][] dp) {
        if (i == 0 || j == 0) {
            //第0天最多交易j次，或者第i天最多交易0次，不持有股票的最大利润为0
            if (type == 0) {
                dp[i][j][type] = 0;
            } else {
                //第0天最多交易j次，或者第i天最多交易0次，不存在持有股票的最大利润
                dp[i][j][type] = -INF;
            }

            return dp[i][j][type];
        }

        //当前dp已访问，直接返回当前dp
        if (dp[i][j][type] != Integer.MIN_VALUE) {
            return dp[i][j][type];
        }

        //type为0：不持有股票的最大利润
        if (type == 0) {
            dp[i][j][type] = Math.max(dfs(i - 1, j, 0, prices, dp), dfs(i - 1, j, 1, prices, dp) + prices[i - 1]);
        } else {
            //type为1：持有股票的最大利润
            dp[i][j][type] = Math.max(dfs(i - 1, j, 1, prices, dp), dfs(i - 1, j - 1, 0, prices, dp) - prices[i - 1]);
        }

        return dp[i][j][type];
    }
}
