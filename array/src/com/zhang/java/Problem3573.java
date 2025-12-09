package com.zhang.java;

/**
 * @Date 2025/11/3 20:56
 * @Author zsy
 * @Description 买卖股票的最佳时机 V 股票类比Problem121、Problem122、Problem123、Problem188、Problem309、Problem714、Problem901、Problem2034、Problem2110、Problem2291、Problem2898、Problem3562、Problem3652、Offer63
 * 给你一个整数数组 prices，其中 prices[i] 是第 i 天股票的价格（美元），以及一个整数 k。
 * 你最多可以进行 k 笔交易，每笔交易可以是以下任一类型：
 * 普通交易：在第 i 天买入，然后在之后的第 j 天卖出，其中 i < j。你的利润是 prices[j] - prices[i]。
 * 做空交易：在第 i 天卖出，然后在之后的第 j 天买回，其中 i < j。你的利润是 prices[i] - prices[j]。
 * 注意：你必须在开始下一笔交易之前完成当前交易。此外，你不能在已经进行买入或卖出操作的同一天再次进行买入或卖出操作。
 * 通过进行 最多 k 笔交易，返回你可以获得的最大总利润。
 * <p>
 * 输入: prices = [1,7,9,8,2], k = 2
 * 输出: 14
 * 解释:
 * 我们可以通过 2 笔交易获得 14 美元的利润：
 * 一笔普通交易：第 0 天以 1 美元买入，第 2 天以 9 美元卖出。
 * 一笔做空交易：第 3 天以 8 美元卖出，第 4 天以 2 美元买回。
 * <p>
 * 输入: prices = [12,16,19,19,8,1,19,13,9], k = 3
 * 输出: 36
 * 解释:
 * 我们可以通过 3 笔交易获得 36 美元的利润：
 * 一笔普通交易：第 0 天以 12 美元买入，第 2 天以 19 美元卖出。
 * 一笔做空交易：第 3 天以 19 美元卖出，第 4 天以 8 美元买回。
 * 一笔普通交易：第 5 天以 1 美元买入，第 6 天以 19 美元卖出。
 * <p>
 * 2 <= prices.length <= 10^3
 * 1 <= prices[i] <= 10^9
 * 1 <= k <= prices.length / 2
 */
public class Problem3573 {
    //最大值为long最大值除以2，避免相加在long范围内溢出
    private final long INF = Long.MAX_VALUE / 2;

    public static void main(String[] args) {
        Problem3573 problem3573 = new Problem3573();
//        int[] prices = {1, 7, 9, 8, 2};
//        int k = 2;
        int[] prices = {12, 16, 19, 19, 8, 1, 19, 13, 9};
        int k = 3;
        System.out.println(problem3573.maximumProfit(prices, k));
        System.out.println(problem3573.maximumProfit2(prices, k));
        System.out.println(problem3573.maximumProfit3(prices, k));
    }

    /**
     * 动态规划
     * dp[i][j][0]：第i天最多交易j次，不持有股票的最大利润
     * dp[i][j][1]：第i天最多交易j次，普通交易持有股票的最大利润
     * dp[i][j][2]：第i天最多交易j次，做空交易持有股票的最大利润
     * dp[i][j][0] = max(dp[i-1][j][0],dp[i-1][j][1]+prices[i-1],dp[i-1][j][2]-prices[i-1])
     * dp[i][j][1] = max(dp[i-1][j][1],dp[i-1][j-1][0]-prices[i-1])
     * dp[i][j][2] = max(dp[i-1][j][2],dp[i-1][j-1][0]+prices[i-1])
     * 时间复杂度O(nk)，空间复杂度O(nk)
     *
     * @param prices
     * @param k
     * @return
     */
    public long maximumProfit(int[] prices, int k) {
        long[][][] dp = new long[prices.length + 1][k + 1][3];

        //dp初始化
        //第0天最多交易j次，不持有股票的最大利润为0
        //第0天最多交易j次，不存在普通交易持有股票的最大利润
        //第0天最多交易j次，不存在做空交易持有股票的最大利润
        for (int j = 0; j <= k; j++) {
            dp[0][j][0] = 0;
            dp[0][j][1] = -INF;
            dp[0][j][2] = -INF;
        }

        //dp初始化
        //第i天最多交易0次，不持有股票的最大利润为0
        //第i天最多交易0次，不存在普通交易持有股票的最大利润
        //第i天最多交易0次，不存在做空交易持有股票的最大利润
        for (int i = 1; i <= prices.length; i++) {
            dp[i][0][0] = 0;
            dp[i][0][1] = -INF;
            dp[i][0][2] = -INF;
        }

        for (int i = 1; i <= prices.length; i++) {
            for (int j = 1; j <= k; j++) {
                dp[i][j][0] = Math.max(dp[i - 1][j][0],
                        Math.max(dp[i - 1][j][1] + prices[i - 1], dp[i - 1][j][2] - prices[i - 1]));
                dp[i][j][1] = Math.max(dp[i - 1][j][1], dp[i - 1][j - 1][0] - prices[i - 1]);
                dp[i][j][2] = Math.max(dp[i - 1][j][2], dp[i - 1][j - 1][0] + prices[i - 1]);
            }
        }

        return dp[prices.length][k][0];
    }

    /**
     * 动态规划优化，使用滚动数组
     * 时间复杂度O(nk)，空间复杂度O(k)
     *
     * @param prices
     * @param k
     * @return
     */
    public long maximumProfit2(int[] prices, int k) {
        long[][] dp = new long[k + 1][3];

        //dp初始化
        //第0天最多交易j次，不持有股票的最大利润为0
        //第0天最多交易j次，不存在普通交易持有股票的最大利润
        //第0天最多交易j次，不存在做空交易持有股票的最大利润
        for (int j = 0; j <= k; j++) {
            dp[j][0] = 0;
            dp[j][1] = -INF;
            dp[j][2] = -INF;
        }

        for (int i = 1; i <= prices.length; i++) {
            long[][] temp = new long[k + 1][3];

            for (int j = 1; j <= k; j++) {
                temp[j][0] = Math.max(dp[j][0],
                        Math.max(dp[j][1] + prices[i - 1], dp[j][2] - prices[i - 1]));
                temp[j][1] = Math.max(dp[j][1], dp[j - 1][0] - prices[i - 1]);
                temp[j][2] = Math.max(dp[j][2], dp[j - 1][0] + prices[i - 1]);
            }

            dp = temp;
        }

        return dp[k][0];
    }

    /**
     * 递归+记忆化搜索
     * 时间复杂度O(nk)，空间复杂度O(nk)
     *
     * @param prices
     * @param k
     * @return
     */
    public long maximumProfit3(int[] prices, int k) {
        long[][][] dp = new long[prices.length + 1][k + 1][3];

        for (int i = 0; i <= prices.length; i++) {
            for (int j = 0; j <= k; j++) {
                //初始化为long类型的最小值，表示当前dp未访问
                dp[i][j][0] = Long.MIN_VALUE;
                dp[i][j][1] = Long.MIN_VALUE;
                dp[i][j][2] = Long.MIN_VALUE;
            }
        }

        //type为0：不持有股票的最大利润
        //type为1：普通交易持有股票的最大利润
        //type为2：做空交易持有股票的最大利润
        dfs(prices.length, k, 0, prices, dp);

        return dp[prices.length][k][0];
    }

    private long dfs(int i, int j, int type, int[] prices, long[][][] dp) {
        if (i == 0 || j == 0) {
            //第0天最多交易j次，或者第i天最多交易0次，不持有股票的最大利润为0
            if (type == 0) {
                dp[i][j][type] = 0;
            }  else {
                //第0天最多交易j次，或者第i天最多交易0次，不存在普通交易持有股票的最大利润
                //第0天最多交易j次，或者第i天最多交易0次，不存在做空交易持有股票的最大利润
                dp[i][j][type] = -INF;
            }

            return dp[i][j][type];
        }

        //当前dp已访问，直接返回当前dp
        if (dp[i][j][type] != Long.MIN_VALUE) {
            return dp[i][j][type];
        }

        //type为0：不持有股票的最大利润
        if (type == 0) {
            dp[i][j][type] = Math.max(dfs(i - 1, j, 0, prices, dp),
                    Math.max(dfs(i - 1, j, 1, prices, dp) + prices[i - 1], dfs(i - 1, j, 2, prices, dp) - prices[i - 1]));
        } else if (type == 1) {
            //type为1：普通交易持有股票的最大利润
            dp[i][j][type] = Math.max(dfs(i - 1, j, 1, prices, dp), dfs(i - 1, j - 1, 0, prices, dp) - prices[i - 1]);
        } else {
            //type为2：做空交易持有股票的最大利润
            dp[i][j][type] = Math.max(dfs(i - 1, j, 2, prices, dp), dfs(i - 1, j - 1, 0, prices, dp) + prices[i - 1]);
        }

        return dp[i][j][type];
    }
}
