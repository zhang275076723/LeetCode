package com.zhang.java;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @Date 2023/2/3 11:29
 * @Author zsy
 * @Description 跳跃游戏 VI 超参数面试题 字节面试题 跳跃问题类比Problem45、Problem55、Problem403、Problem975、Problem1306、Problem1340、Problem1345、Problem1377、Problem1654、Problem1871、Problem2297、Problem2498、Problem2770、LCP09 单调队列类比Problem209、Problem239、Problem862、Offer59、Offer59_2
 * 给你一个下标从 0 开始的整数数组 nums 和一个整数 k 。
 * 一开始你在下标 0 处。每一步，你最多可以往前跳 k 步，但你不能跳出数组的边界。
 * 也就是说，你可以从下标 i 跳到 [i + 1， min(n - 1, i + k)] 包含 两个端点的任意位置。
 * 你的目标是到达数组最后一个位置（下标为 n - 1 ），你的 得分 为经过的所有数字之和。
 * 请你返回你能得到的 最大得分 。
 * <p>
 * 输入：nums = [1,-1,-2,4,-7,3], k = 2
 * 输出：7
 * 解释：你可以选择子序列 [1,-1,4,3] （上面加粗的数字），和为 7 。
 * <p>
 * 输入：nums = [10,-5,-2,4,0,3], k = 3
 * 输出：17
 * 解释：你可以选择子序列 [10,4,3] （上面加粗数字），和为 17 。
 * <p>
 * 输入：nums = [1,-5,-20,4,-1,3,-6,-3], k = 2
 * 输出：0
 * <p>
 * 1 <= nums.length, k <= 10^5
 * -10^4 <= nums[i] <= 10^4
 */
public class Problem1696 {
    public static void main(String[] args) {
        Problem1696 problem1696 = new Problem1696();
        int[] nums = {1, -1, -2, 4, -7, 3};
        int k = 2;
        System.out.println(problem1696.maxResult(nums, k));
        System.out.println(problem1696.maxResult2(nums, k));
    }

    /**
     * 动态规划
     * dp[i]：跳跃到nums[i]得到的最大得分
     * dp[i] = max(dp[j] + nums[j]) (i-k < j < i)
     * 时间复杂度O(nk)，空间复杂度O(n)
     *
     * @param nums
     * @param k
     * @return
     */
    public int maxResult(int[] nums, int k) {
        int[] dp = new int[nums.length];
        //dp初始化，开始能够跳跃到nums[0]
        dp[0] = nums[0];

        for (int i = 1; i < nums.length; i++) {
            //初始化nums[i]为int最小值，因为nums数组中元素有负数，不能初始化为0
            dp[i] = Integer.MIN_VALUE;

            for (int j = Math.max(0, i - k); j < i; j++) {
                dp[i] = Math.max(dp[i], dp[j] + nums[i]);
            }
        }

        return dp[nums.length - 1];
    }

    /**
     * 动态规划+单调队列
     * dp[i]：跳跃到nums[i]得到的最大得分
     * 单调递减队列存放dp数组中元素的下标索引
     * 1、i减去队首元素dp[j]中的j大于k，则dp[j]不在当前dp[i]所要查询的dp[i-k]-dp[i-1]范围之内，队首元素出队
     * 2、当前dp[i]不满足单调递减队列，队尾元素出队，当前dp[i]入队
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @param k
     * @return
     */
    public int maxResult2(int[] nums, int k) {
        //单调递减队列
        Deque<Integer> queue = new ArrayDeque<>();
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        queue.offerLast(0);

        for (int i = 1; i < nums.length; i++) {
            //i减去队首元素dp[j]中的j大于k，则dp[j]不在当前dp[i]所要查询的dp[i-k]-dp[i-1]范围之内，队首元素出队
            while (!queue.isEmpty() && i - queue.peekFirst() > k) {
                queue.pollFirst();
            }

            //dp[i]为队首元素dp加上nums[i]
            dp[i] = dp[queue.peekFirst()] + nums[i];

            //当前dp[i]不满足单调递减队列，队尾元素出队
            while (!queue.isEmpty() && dp[queue.peekLast()] < dp[i]) {
                queue.pollLast();
            }

            //dp[i]入队
            queue.offerLast(i);
        }

        return dp[nums.length - 1];
    }
}
