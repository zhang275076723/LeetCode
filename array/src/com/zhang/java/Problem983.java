package com.zhang.java;

/**
 * @Date 2023/1/21 10:29
 * @Author zsy
 * @Description 最低票价 动态规划类比Problem279、Problem322、Problem343、Problem377、Problem416、Problem494、Problem518、Offer14、Offer14_2、CircleBackToOrigin、Knapsack
 * 在一个火车旅行很受欢迎的国度，你提前一年计划了一些火车旅行。
 * 在接下来的一年里，你要旅行的日子将以一个名为 days 的数组给出。每一项是一个从 1 到 365 的整数。
 * 火车票有 三种不同的销售方式 ：
 * 一张 为期一天 的通行证售价为 costs[0] 美元；
 * 一张 为期七天 的通行证售价为 costs[1] 美元；
 * 一张 为期三十天 的通行证售价为 costs[2] 美元。
 * 通行证允许数天无限制的旅行。
 * 例如，如果我们在第 2 天获得一张 为期 7 天 的通行证，
 * 那么我们可以连着旅行 7 天：第 2 天、第 3 天、第 4 天、第 5 天、第 6 天、第 7 天和第 8 天。
 * 返回 你想要完成在给定的列表 days 中列出的每一天的旅行所需要的最低消费。
 * <p>
 * 输入：days = [1,4,6,7,8,20], costs = [2,7,15]
 * 输出：11
 * 解释：
 * 例如，这里有一种购买通行证的方法，可以让你完成你的旅行计划：
 * 在第 1 天，你花了 costs[0] = $2 买了一张为期 1 天的通行证，它将在第 1 天生效。
 * 在第 3 天，你花了 costs[1] = $7 买了一张为期 7 天的通行证，它将在第 3, 4, ..., 9 天生效。
 * 在第 20 天，你花了 costs[0] = $2 买了一张为期 1 天的通行证，它将在第 20 天生效。
 * 你总共花了 $11，并完成了你计划的每一天旅行。
 * <p>
 * 输入：days = [1,2,3,4,5,6,7,8,9,10,30,31], costs = [2,7,15]
 * 输出：17
 * 解释：
 * 例如，这里有一种购买通行证的方法，可以让你完成你的旅行计划：
 * 在第 1 天，你花了 costs[2] = $15 买了一张为期 30 天的通行证，它将在第 1, 2, ..., 30 天生效。
 * 在第 31 天，你花了 costs[0] = $2 买了一张为期 1 天的通行证，它将在第 31 天生效。
 * 你总共花了 $17，并完成了你计划的每一天旅行。
 * <p>
 * 1 <= days.length <= 365
 * 1 <= days[i] <= 365
 * days 按顺序严格递增
 * costs.length == 3
 * 1 <= costs[i] <= 1000
 */
public class Problem983 {
    public static void main(String[] args) {
        Problem983 problem983 = new Problem983();
        int[] days = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 30, 31};
        int[] costs = {2, 7, 15};
        System.out.println(problem983.mincostTickets(days, costs));
    }

    /**
     * 动态规划
     * dp[i]：到days[i]那天，旅行需要的最低消费
     * dp[i] = min(dp[i-1]+costs[0], dp[i-7]+costs[1], dp[i-30]+costs[2])
     * 时间复杂度O(lastDay)，空间复杂度O(lastDay) (lastDay=days[days.length-1])
     *
     * @param days
     * @param costs
     * @return
     */
    public int mincostTickets(int[] days, int[] costs) {
        if (days == null || days.length == 0 || costs == null || costs.length == 0) {
            return 0;
        }

        //days数组中最后一天
        int lastDay = days[days.length - 1];
        int[] dp = new int[lastDay + 1];
        //days数组索引
        int index = 0;

        //从第1天遍历到lastDay那天，判断哪一天要旅行，则dp[i]是dp[i-1]+cost[0]、dp[i-7]+cost[1]、dp[i-30]+cost[2]三者中的最小值
        for (int i = 1; i <= lastDay; i++) {
            //第i天要旅行
            if (i == days[index]) {
                //从第i-1天买1天通行证到第i天旅行需要的最低消费
                int temp1 = dp[i - 1] + costs[0];
                //从第i-7天买7天通行证到第i天旅行需要的最低消费
                int temp2 = i - 7 >= 0 ? dp[i - 7] + costs[1] : costs[1];
                //从第i-30天买30天通行证到第i天旅行需要的最低消费
                int temp3 = i - 30 >= 0 ? dp[i - 30] + costs[2] : costs[2];

                //temp1、temp2、temp3三者中的最小值，即为第i天旅行需要的最低消费
                dp[i] = Math.min(temp1, Math.min(temp2, temp3));

                //days数组索引后移
                index++;
            } else {
                //第i天不旅行
                dp[i] = dp[i - 1];
            }
        }

        return dp[lastDay];
    }
}
