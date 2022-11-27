package com.zhang.java;

/**
 * @Date 2022/11/27 15:58
 * @Author zsy
 * @Description 使用最小花费爬楼梯 类比Problem70、Problem509、Problem1137、Offer10、Offer10_2、Offer46
 * 给你一个整数数组 cost ，其中 cost[i] 是从楼梯第 i 个台阶向上爬需要支付的费用。
 * 一旦你支付此费用，即可选择向上爬一个或者两个台阶。
 * 你可以选择从下标为 0 或下标为 1 的台阶开始爬楼梯。
 * 请你计算并返回达到楼梯顶部的最低花费。
 * <p>
 * 输入：cost = [10,15,20]
 * 输出：15
 * 解释：你将从下标为 1 的台阶开始。
 * - 支付 15 ，向上爬两个台阶，到达楼梯顶部。
 * 总花费为 15 。
 * <p>
 * 输入：cost = [1,100,1,1,1,100,1,1,100,1]
 * 输出：6
 * 解释：你将从下标为 0 的台阶开始。
 * - 支付 1 ，向上爬两个台阶，到达下标为 2 的台阶。
 * - 支付 1 ，向上爬两个台阶，到达下标为 4 的台阶。
 * - 支付 1 ，向上爬两个台阶，到达下标为 6 的台阶。
 * - 支付 1 ，向上爬一个台阶，到达下标为 7 的台阶。
 * - 支付 1 ，向上爬两个台阶，到达下标为 9 的台阶。
 * - 支付 1 ，向上爬一个台阶，到达楼梯顶部。
 * 总花费为 6 。
 * <p>
 * 2 <= cost.length <= 1000
 * 0 <= cost[i] <= 999
 */
public class Problem746 {
    public static void main(String[] args) {
        Problem746 problem746 = new Problem746();
        int[] cost = {1, 100, 1, 1, 1, 100, 1, 1, 100, 1};
        System.out.println(problem746.minCostClimbingStairs(cost));
    }

    /**
     * 动态规划
     * dp[i]：到达cost[i]对应台阶所需的费用
     * dp[i] = mid(dp[i-1]+cost[i-1],dp[i-2]+cost[i-2])
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param cost
     * @return
     */
    public int minCostClimbingStairs(int[] cost) {
        int[] dp = new int[cost.length];
        dp[0] = 0;
        dp[1] = 0;

        for (int i = 2; i < cost.length; i++) {
            dp[i] = Math.min(dp[i - 1] + cost[i - 1], dp[i - 2] + cost[i - 2]);
        }

        return Math.min(dp[cost.length - 1] + cost[cost.length - 1], dp[cost.length - 2] + cost[cost.length - 2]);
    }

    /**
     * 动态规划优化，使用滚动数组
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param cost
     * @return
     */
    public int minCostClimbingStairs2(int[] cost) {
        int p = 0;
        int q = 0;

        for (int i = 2; i < cost.length; i++) {
            int temp = Math.min(p + cost[i - 2], q + cost[i - 1]);
            p = q;
            q = temp;
        }

        return Math.min(p + cost[cost.length - 2], q + cost[cost.length - 1]);
    }
}
