package com.zhang.java;

/**
 * @Date 2023/9/24 08:20
 * @Author zsy
 * @Description 构成特定和需要添加的最少元素
 * 给你一个整数数组 nums ，和两个整数 limit 与 goal 。
 * 数组 nums 有一条重要属性：abs(nums[i]) <= limit 。
 * 返回使数组元素总和等于 goal 所需要向数组中添加的 最少元素数量 ，添加元素 不应改变 数组中 abs(nums[i]) <= limit 这一属性。
 * 注意，如果 x >= 0 ，那么 abs(x) 等于 x ；否则，等于 -x 。
 * <p>
 * 输入：nums = [1,-1,1], limit = 3, goal = -4
 * 输出：2
 * 解释：可以将 -2 和 -3 添加到数组中，数组的元素总和变为 1 - 1 + 1 - 2 - 3 = -4 。
 * <p>
 * 输入：nums = [1,-10,9,1], limit = 100, goal = 0
 * 输出：1
 * <p>
 * 1 <= nums.length <= 10^5
 * 1 <= limit <= 10^6
 * -limit <= nums[i] <= limit
 * -10^9 <= goal <= 10^9
 */
public class Problem1785 {
    public static void main(String[] args) {
        Problem1785 problem1785 = new Problem1785();
        int[] nums = {1, -1, 1};
        int limit = 3;
        int goal = -4;
        System.out.println(problem1785.minElements(nums, limit, goal));
    }

    /**
     * 模拟
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @param limit
     * @param goal
     * @return
     */
    public int minElements(int[] nums, int limit, int goal) {
        //使用long，避免int相加溢出
        long sum = 0;

        for (int num : nums) {
            sum = sum + num;
        }

        //使nums元素之和等于goal所需要添加的元素之和绝对值
        long abs = Math.abs(sum - goal);

        //abs能倍limit整除，不需要额外加1
        if (abs % limit == 0) {
            return (int) (abs / limit);
        } else {
            //不能整除，需要额外加1
            return (int) (abs / limit) + 1;
        }
    }
}
