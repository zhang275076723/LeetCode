package com.zhang.java;

import java.util.Stack;

/**
 * @Date 2024/11/10 08:45
 * @Author zsy
 * @Description 跳跃游戏 VIII 跳跃问题类比Problem45、Problem55、Problem403、Problem975、Problem1306、Problem1340、Problem1345、Problem1377、Problem1654、Problem1696、Problem1871、Problem2498、Problem2770、LCP09 单调栈类比
 * 给定一个长度为 n 的下标从 0 开始的整数数组 nums。
 * 初始位置为下标 0。
 * 当 i < j 时，你可以从下标 i 跳转到下标 j:
 * 对于在 i < k < j 范围内的所有下标 k 有 nums[i] <= nums[j] 和 nums[k] < nums[i] , 或者
 * 对于在 i < k < j 范围内的所有下标 k 有 nums[i] > nums[j] 和 nums[k] >= nums[i] 。
 * 你还得到了一个长度为 n 的整数数组 costs，其中 costs[i] 表示跳转到下标 i 的代价。
 * 返回跳转到下标 n - 1 的最小代价。
 * <p>
 * 输入: nums = [3,2,4,4,1], costs = [3,7,6,4,2]
 * 输出: 8
 * 解释: 从下标 0 开始。
 * - 以 costs[2]= 6 的代价跳转到下标 2。
 * - 以 costs[4]= 2 的代价跳转到下标 4。
 * 总代价是 8。可以证明，8 是所需的最小代价。
 * 另外两个可能的路径是:下标 0 -> 1 -> 4 和下标 0 -> 2 -> 3 -> 4。
 * 它们的总代价分别为9和12。
 * <p>
 * 输入: nums = [0,1,2], costs = [1,1,1]
 * 输出: 2
 * 解释: 从下标 0 开始。
 * - 以 costs[1] = 1 的代价跳转到下标 1。
 * - 以 costs[2] = 1 的代价跳转到下标 2。
 * 总代价是 2。注意您不能直接从下标 0 跳转到下标 2，因为 nums[0] <= nums[1]。
 * <p>
 * n == nums.length == costs.length
 * 1 <= n <= 10^5
 * 0 <= nums[i], costs[i] <= 10^5
 */
public class Problem2297 {
    public static void main(String[] args) {
        Problem2297 problem2297 = new Problem2297();
        int[] nums = {3, 2, 4, 4, 1};
        int[] costs = {3, 7, 6, 4, 2};
//        int[] nums = {0, 1, 2};
//        int[] costs = {1, 1, 1};
        System.out.println(problem2297.minCost(nums, costs));
    }

    /**
     * 单调栈+动态规划
     * dp[i]：跳跃到nums[i]得到的最小代价
     * 1、对于在 i < k < j 范围内的所有下标 k 有 nums[i] <= nums[j] 和 nums[k] < nums[i]，
     * 则i右侧大于等于nums[i]的第一个nums[j]满足要求，因为满足i<k<j的k，nums[k]都小于nums[i]，即使用单调递减栈
     * 2、对于在 i < k < j 范围内的所有下标 k 有 nums[i] > nums[j] 和 nums[k] >= nums[i]，
     * 则i右侧小于nums[i]的第一个nums[j]满足要求，因为满足i<k<j的k，nums[k]都大于等于nums[i]，即使用单调递增栈
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @param costs
     * @return
     */
    public long minCost(int[] nums, int[] costs) {
        int[] dp = new int[nums.length];
        dp[0] = 0;

        //dp初始化
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Integer.MAX_VALUE;
        }

        //单调递减栈，用于情况1
        Stack<Integer> stack1 = new Stack<>();
        //单调递增栈，用于情况2
        Stack<Integer> stack2 = new Stack<>();

        for (int i = 0; i < nums.length; i++) {
            //单调递减栈栈顶元素小于等于nums[i]，则单调递减栈栈顶下标索引到i满足情况1，更新dp[i]
            //注意：是小于等于，而不是小于
            while (!stack1.isEmpty() && nums[stack1.peek()] <= nums[i]) {
                int j = stack1.pop();
                dp[i] = Math.min(dp[i], dp[j] + costs[i]);
            }

            stack1.push(i);

            //单调递增栈栈顶元素大于nums[i]，则单调递增栈栈顶下标索引到i满足情况1，更新dp[i]
            //注意：是大于，而不是大于等于
            while (!stack2.isEmpty() && nums[stack2.peek()] > nums[i]) {
                int j = stack2.pop();
                dp[i] = Math.min(dp[i], dp[j] + costs[i]);
            }

            stack2.push(i);
        }

        return dp[nums.length - 1];
    }
}
