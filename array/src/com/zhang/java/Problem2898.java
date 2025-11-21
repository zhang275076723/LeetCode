package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2025/11/21 20:55
 * @Author zsy
 * @Description 最大线性股票得分 股票类比
 * 给定一个 1-indexed 整数数组 prices，其中 prices[i] 是第 i 天某只股票的价格。
 * 你的任务是 线性 地选择 prices 中的一些元素。
 * 一个选择 indexes，其中 indexes 是一个 1-indexed 整数数组，长度为 k，是数组 [1, 2, ..., n] 的子序列，
 * 如果以下条件成立，那么它是 线性 的：
 * 对于每个 1 < j <= k，prices[indexes[j]] - prices[indexes[j - 1]] == indexes[j] - indexes[j - 1]。
 * 数组的 子序列 是经由原数组删除一些元素（可能不删除）而产生的新数组，且删除不改变其余元素相对顺序。
 * 选择 indexes 的 得分 等于以下数组的总和：[prices[indexes[1]], prices[indexes[2]], ..., prices[indexes[k]]。
 * 返回 线性选择的 最大得分。
 * <p>
 * 输入： prices = [1,5,3,7,8]
 * 输出： 20
 * 解释： 我们可以选择索引[2,4,5]。我们可以证明我们的选择是线性的：
 * 对于j = 2，我们有：
 * indexes[2] - indexes[1] = 4 - 2 = 2。
 * prices[4] - prices[2] = 7 - 5 = 2。
 * 对于j = 3，我们有：
 * indexes[3] - indexes[2] = 5 - 4 = 1。
 * prices[5] - prices[4] = 8 - 7 = 1。
 * 元素的总和是：prices[2] + prices[4] + prices[5] = 20。
 * 可以证明线性选择的最大和是20。
 * <p>
 * 输入： prices = [5,6,7,8,9]
 * 输出： 35
 * 解释： 我们可以选择所有索引[1,2,3,4,5]。因为每个元素与前一个元素的差异恰好为1，所以我们的选择是线性的。
 * 所有元素的总和是35，这是每个选择的最大可能总和。
 * <p>
 * 1 <= prices.length <= 10^5
 * 1 <= prices[i] <= 10^9
 */
public class Problem2898 {
    public static void main(String[] args) {
        Problem2898 problem2898 = new Problem2898();
//        int[] prices = {1, 5, 3, 7, 8};
        int[] prices = {5, 6, 7, 8, 9};
        System.out.println(problem2898.maxScore(prices));
    }

    /**
     * 哈希表
     * 题目中indexes[i]、indexes[j]即为prices中元素构成的数组中从1开始的下标索引，
     * 题目由prices[indexes[j]]-prices[indexes[j-1]]==indexes[j]-indexes[j-1]，
     * 转化为prices[j]-prices[i]==j-i ==> prices[j]-j==prices[i]-i
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param prices
     * @return
     */
    public long maxScore(int[] prices) {
        //key：prices[i]-i，value：prices[i]-i相等的prices[i]之和
        Map<Integer, Long> map = new HashMap<>();

        for (int i = 0; i < prices.length; i++) {
            map.put(prices[i] - i, map.getOrDefault(prices[i] - i, 0L) + prices[i]);
        }

        long result = 0;

        for (long value : map.values()) {
            result = Math.max(result, value);
        }

        return result;
    }
}
