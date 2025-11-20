package com.zhang.java;

/**
 * @Date 2024/11/19 08:38
 * @Author zsy
 * @Description 最大股票收益 01背包类比Problem416、Problem474、Problem494、Problem2742 股票类比Problem121、Problem122、Problem123、Problem188、Problem309、Problem714、Problem901、Problem2034、Problem2110、Offer63 动态规划类比
 * 给你两个下标从 0 开始的数组 present 和 future ，present[i] 和 future[i] 分别代表第 i 支股票现在和将来的价格。
 * 每支股票你最多购买 一次 ，你的预算为 budget 。
 * 求最大的收益。
 * <p>
 * 输入：present = [5,4,6,2,3], future = [8,5,4,3,5], budget = 10
 * 输出：6
 * 解释：你可以选择购买第 0,3,4 支股票获得最大收益：6 。总开销为：5 + 2 + 3 = 10 , 总收益是: 8 + 3 + 5 - 10 = 6 。
 * <p>
 * 输入：present = [2,2,5], future = [3,4,10], budget = 6
 * 输出：5
 * 解释：你可以选择购买第 2 支股票获得最大收益：5 。总开销为：5 , 总收益是: 10 - 5 = 5 。
 * <p>
 * 输入：present = [3,3,12], future = [0,3,15], budget = 10
 * 输出：0
 * 解释：你无法购买唯一一支正收益股票 2 ，因此你的收益是 0 。
 * <p>
 * n == present.length == future.length
 * 1 <= n <= 1000
 * 0 <= present[i], future[i] <= 100
 * 0 <= budget <= 1000
 */
public class Problem2291 {
    public static void main(String[] args) {
        Problem2291 problem2291 = new Problem2291();
        int[] present = {5, 4, 6, 2, 3};
        int[] future = {8, 5, 4, 3, 5};
        int budget = 10;
        System.out.println(problem2291.maximumProfit(present, future, budget));
        System.out.println(problem2291.maximumProfit2(present, future, budget));
        System.out.println(problem2291.maximumProfit3(present, future, budget));
    }

    /**
     * 动态规划 01背包
     * dp[i][j]：购买前i-1支股票预算最多为j的最大收益
     * dp[i][j] = dp[i-1][j]                                                       (present[i-1]>j)
     * dp[i][j] = max(dp[i-1][j],dp[i-1][j-present[i-1]]+future[i-1]-present[i-1]) (present[i-1]<=j)
     * 时间复杂度O(n*budget)，空间复杂度O(n*budget)
     *
     * @param present
     * @param future
     * @param budget
     * @return
     */
    public int maximumProfit(int[] present, int[] future, int budget) {
        int[][] dp = new int[present.length + 1][budget + 1];

        //dp初始化，购买前0支股票预算最多为0-budget的最大收益为0
        for (int j = 0; j <= budget; j++) {
            dp[0][j] = 0;
        }

        for (int i = 1; i <= present.length; i++) {
            for (int j = 0; j <= budget; j++) {
                if (present[i - 1] > j) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - present[i - 1]] + future[i - 1] - present[i - 1]);
                }
            }
        }

        return dp[present.length][budget];
    }

    /**
     * 动态规划优化，使用滚动数组
     * dp[j]：购买前i-1支股票预算最多为j的最大收益
     * dp[j] = dp[j]                                                  (present[i-1]>j)
     * dp[j] = max(dp[j],dp[j-present[i-1]]+future[i-1]-present[i-1]) (present[i-1]<=j)
     * 时间复杂度O(n*budget)，空间复杂度O(budget)
     *
     * @param present
     * @param future
     * @param budget
     * @return
     */
    public int maximumProfit2(int[] present, int[] future, int budget) {
        int[] dp = new int[budget + 1];

        //dp初始化，购买前0支股票预算最多为0-budget的最大收益为0
        for (int j = 0; j <= budget; j++) {
            dp[j] = 0;
        }

        for (int i = 0; i < present.length; i++) {
            //注意：当前dp[j]会使用到前面的dp，所以逆序遍历
            for (int j = budget; j >= 0; j--) {
                if (present[i] <= j) {
                    dp[j] = Math.max(dp[j], dp[j - present[i]] + future[i] - present[i]);
                }
            }
        }

        return dp[budget];
    }

    /**
     * 递归+记忆化搜索
     * 时间复杂度O(n*budget)，空间复杂度O(n*budget)
     *
     * @param present
     * @param future
     * @param budget
     * @return
     */
    public int maximumProfit3(int[] present, int[] future, int budget) {
        int[][] dp = new int[present.length + 1][budget + 1];

        for (int i = 0; i <= present.length; i++) {
            for (int j = 0; j <= budget; j++) {
                //初始化为-1，表示当前dp未访问
                dp[i][j] = -1;
            }
        }

        dfs(present.length, budget, present, future, dp);

        return dp[present.length][budget];
    }

    private int dfs(int i, int budget, int[] present, int[] future, int[][] dp) {
        //购买前0支股票预算最多为budget的最大收益为0
        if (i == 0) {
            dp[i][budget] = 0;
            return dp[i][budget];
        }

        //当前dp已访问，直接返回当前dp
        if (dp[i][budget] != -1) {
            return dp[i][budget];
        }

        if (present[i - 1] > budget) {
            dp[i][budget] = dfs(i - 1, budget, present, future, dp);
            return dp[i][budget];
        }

        dp[i][budget] = Math.max(dfs(i - 1, budget, present, future, dp),
                dfs(i - 1, budget - present[i - 1], present, future, dp) + future[i - 1] - present[i - 1]);

        return dp[i][budget];
    }
}
