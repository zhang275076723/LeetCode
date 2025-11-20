package com.zhang.java;

/**
 * @Date 2022/11/15 11:34
 * @Author zsy
 * @Description 买卖股票的最佳时机 III 股票类比Problem121、Problem122、Problem188、Problem309、Problem714、Problem901、Problem2034、Problem2110、Problem2291、Offer63
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
    //最大值为int最大值除以2，避免相加在int范围内溢出
    private final int INF = Integer.MAX_VALUE / 2;

    public static void main(String[] args) {
        Problem123 problem123 = new Problem123();
//        int[] prices = {3, 3, 5, 0, 0, 3, 1, 4};
        int[] prices = {1, 2, 3, 4, 5};
        System.out.println(problem123.maxProfit(prices));
        System.out.println(problem123.maxProfit2(prices));
        System.out.println(problem123.maxProfit3(prices));
    }

    /**
     * 动态规划
     * dp[i][j][0]：第i天最多交易j次，不持有股票的最大利润
     * dp[i][j][1]：第i天最多交易j次，持有股票的最大利润
     * dp[i][j][0] = max(dp[i-1][j][0],dp[i-1][j][1]+prices[i-1])
     * dp[i][j][1] = max(dp[i-1][j][1],dp[i-1][j-1][0]-prices[i-1])
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        int[][][] dp = new int[prices.length + 1][3][2];

        //dp初始化
        //第0天最多交易j次，不持有股票的最大利润为0
        //第0天最多交易j次，不存在持有股票的最大利润
        for (int j = 0; j <= 2; j++) {
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
            for (int j = 1; j <= 2; j++) {
                dp[i][j][0] = Math.max(dp[i - 1][j][0], dp[i - 1][j][1] + prices[i - 1]);
                dp[i][j][1] = Math.max(dp[i - 1][j][1], dp[i - 1][j - 1][0] - prices[i - 1]);
            }
        }

        return dp[prices.length][2][0];
    }

    /**
     * 动态规划优化，使用滚动数组
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param prices
     * @return
     */
    public int maxProfit2(int[] prices) {
        int[][] dp = new int[3][2];

        //dp初始化
        //第0天最多交易j次，不持有股票的最大利润为0
        //第0天最多交易j次，不存在持有股票的最大利润
        for (int j = 0; j <= 2; j++) {
            dp[j][0] = 0;
            dp[j][1] = -INF;
        }

        for (int i = 1; i <= prices.length; i++) {
            int[][] temp = new int[3][2];

            for (int j = 1; j <= 2; j++) {
                temp[j][0] = Math.max(dp[j][0], dp[j][1] + prices[i - 1]);
                temp[j][1] = Math.max(dp[j][1], dp[j - 1][0] - prices[i - 1]);
            }

            dp = temp;
        }

        return dp[2][0];
    }

    /**
     * 递归+记忆化搜索
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param prices
     * @return
     */
    public int maxProfit3(int[] prices) {
        int[][][] dp = new int[prices.length + 1][3][2];

        for (int i = 0; i <= prices.length; i++) {
            for (int j = 0; j <= 2; j++) {
                //初始化为int类型的最小值，表示当前dp未访问
                dp[i][j][0] = Integer.MIN_VALUE;
                dp[i][j][1] = Integer.MIN_VALUE;
            }
        }

        //type为0：不持有股票的最大利润
        //type为1：持有股票的最大利润
        dfs(prices.length, 2, 0, prices, dp);

        return dp[prices.length][2][0];
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
