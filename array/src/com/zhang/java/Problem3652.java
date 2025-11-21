package com.zhang.java;

/**
 * @Date 2025/11/20 21:35
 * @Author zsy
 * @Description 按策略买卖股票的最佳时机 类比Problem834、Problem1838、ElevatorSchedule 前缀和类比 滑动窗口类比 股票类比
 * 给你两个整数数组 prices 和 strategy，其中：
 * prices[i] 表示第 i 天某股票的价格。
 * strategy[i] 表示第 i 天的交易策略，其中：
 * -1 表示买入一单位股票。
 * 0 表示持有股票。
 * 1 表示卖出一单位股票。
 * 同时给你一个 偶数 整数 k，你可以对 strategy 进行 最多一次 修改。
 * 一次修改包括：
 * 选择 strategy 中恰好 k 个 连续 元素。
 * 将前 k / 2 个元素设为 0（持有）。
 * 将后 k / 2 个元素设为 1（卖出）。
 * 利润 定义为所有天数中 strategy[i] * prices[i] 的 总和 。
 * 返回你可以获得的 最大 可能利润。
 * 注意： 没有预算或股票持有数量的限制，因此所有买入和卖出操作均可行，无需考虑过去的操作。
 * <p>
 * 输入： prices = [4,2,8], strategy = [-1,0,1], k = 2
 * 输出： 10
 * 解释：
 * 修改	策略	利润计算	利润
 * 原始	[-1, 0, 1]	(-1 × 4) + (0 × 2) + (1 × 8) = -4 + 0 + 8	4
 * 修改 [0, 1]	[0, 1, 1]	(0 × 4) + (1 × 2) + (1 × 8) = 0 + 2 + 8	10
 * 修改 [1, 2]	[-1, 0, 1]	(-1 × 4) + (0 × 2) + (1 × 8) = -4 + 0 + 8	4
 * 因此，最大可能利润是 10，通过修改子数组 [0, 1] 实现。
 * <p>
 * 输入： prices = [5,4,3], strategy = [1,1,0], k = 2
 * 输出： 9
 * 解释：
 * 修改	策略	利润计算	利润
 * 原始	[1, 1, 0]	(1 × 5) + (1 × 4) + (0 × 3) = 5 + 4 + 0	9
 * 修改 [0, 1]	[0, 1, 0]	(0 × 5) + (1 × 4) + (0 × 3) = 0 + 4 + 0	4
 * 修改 [1, 2]	[1, 0, 1]	(1 × 5) + (0 × 4) + (1 × 3) = 5 + 0 + 3	8
 * 因此，最大可能利润是 9，无需任何修改即可达成。
 * <p>
 * 2 <= prices.length == strategy.length <= 10^5
 * 1 <= prices[i] <= 10^5
 * -1 <= strategy[i] <= 1
 * 2 <= k <= prices.length
 * k 是偶数
 */
public class Problem3652 {
    public static void main(String[] args) {
        Problem3652 problem3652 = new Problem3652();
        int[] prices = {4, 2, 8};
        int[] strategy = {-1, 0, 1};
        int k = 2;
        System.out.println(problem3652.maxProfit(prices, strategy, k));
        System.out.println(problem3652.maxProfit2(prices, strategy, k));
    }

    /**
     * 前缀和
     * 对每个要修改的strategy[i]-strategy[i+k-1]通过前缀和计算利润
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param prices
     * @param strategy
     * @param k
     * @return
     */
    public long maxProfit(int[] prices, int[] strategy, int k) {
        int n = prices.length;
        //股票价格prices[i]前缀和数组
        long[] pricesPreSum = new long[n + 1];
        //股票利润prices[i]*strategy[i]前缀和数组
        long[] profitPreSum = new long[n + 1];

        for (int i = 1; i <= n; i++) {
            pricesPreSum[i] = pricesPreSum[i - 1] + prices[i - 1];
            profitPreSum[i] = profitPreSum[i - 1] + (long) prices[i - 1] * strategy[i - 1];
        }

        long maxProfit = profitPreSum[n] - profitPreSum[0];

        //strategy[i]-strategy[i+k-1]进行修改
        for (int i = 0; i <= n - k; i++) {
            //prices[0]-prices[i-1]的利润
            long profit1 = profitPreSum[i] - profitPreSum[0];
            //prices[i]-prices[i+k-1]的利润
            //strategy[i]-strategy[i+k/2-1]修改为0，strategy[i+k/2]-strategy[i+k-1]修改为1
            long profit2 = pricesPreSum[i + k] - pricesPreSum[i + k / 2];
            //prices[i+k]-prices[n-1]的利润
            long profit3 = profitPreSum[n] - profitPreSum[i + k];

            maxProfit = Math.max(maxProfit, profit1 + profit2 + profit3);
        }

        return maxProfit;
    }

    /**
     * 滑动窗口
     * 通过滑动窗口计算从上一个滑动窗口strategy[i-1]-strategy[i+k-2]到当前滑动窗口strategy[i]-strategy[i+k-1]利润的变化值change，
     * curProfit+change得到当前窗口的利润
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param prices
     * @param strategy
     * @param k
     * @return
     */
    public long maxProfit2(int[] prices, int[] strategy, int k) {
        int n = prices.length;
        //原数组未修改之前的利润
        long curProfit = 0;

        for (int i = 0; i < n; i++) {
            curProfit = curProfit + (long) prices[i] * strategy[i];
        }

        long maxProfit = curProfit;
        //从上一个滑动窗口strategy[i-1]-strategy[i+k-2]到当前滑动窗口strategy[i]-strategy[i+k-1]利润的变化值
        long change = 0;

        //滑动窗口strategy[0]-strategy[k-1]进行修改
        for (int i = 0; i < k; i++) {
            //strategy[0]-strategy[k/2-1]修改为0，即change减去之前利润prices[i]*strategy[i]
            if (i < k / 2) {
                change = change - (long) prices[i] * strategy[i];
            } else {
                //strategy[k/2]-strategy[i+k-1]修改为1，即change加上新增的利润prices[i]-prices[i]*strategy[i]
                change = change + prices[i] - (long) prices[i] * strategy[i];
            }
        }

        //curProfit+change得到当前窗口的利润
        maxProfit = Math.max(maxProfit, curProfit + change);

        //滑动窗口strategy[i]-strategy[i+k-1]进行修改
        for (int i = 1; i <= n - k; i++) {
            //strategy[i-1]由0恢复为strategy[i-1]，即change加上之前的利润prices[i-1]*strategy[i-1]
            change = change + (long) prices[i - 1] * strategy[i - 1];
            //strategy[i+k/2-1]由1修改为0，即change减去之前利润prices[i+k/2-1]
            change = change - prices[i + k / 2 - 1];
            //strategy[i+k-1]修改为1，即change加上新增的利润prices[i+k-1]-prices[i+k-1]*strategy[i+k-1]
            change = change + prices[i + k - 1] - (long) prices[i + k - 1] * strategy[i + k - 1];

            //curProfit+change得到当前窗口的利润
            maxProfit = Math.max(maxProfit, curProfit + change);
        }

        return maxProfit;
    }
}
