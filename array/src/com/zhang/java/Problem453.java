package com.zhang.java;

/**
 * @Date 2024/4/29 08:35
 * @Author zsy
 * @Description 最小操作次数使数组元素相等 深信服机试题 类比Problem462
 * 给你一个长度为 n 的整数数组，每次操作将会使 n - 1 个元素增加 1 。
 * 返回让数组所有元素相等的最小操作次数。
 * <p>
 * 输入：nums = [1,2,3]
 * 输出：3
 * 解释：
 * 只需要3次操作（注意每次操作会增加两个元素的值）：
 * [1,2,3]  =>  [2,3,3]  =>  [3,4,3]  =>  [4,4,4]
 * <p>
 * 输入：nums = [1,1,1]
 * 输出：0
 * <p>
 * n == nums.length
 * 1 <= nums.length <= 10^5
 * -10^9 <= nums[i] <= 10^9
 * 答案保证符合 32-bit 整数
 */
public class Problem453 {
    public static void main(String[] args) {
        Problem453 problem453 = new Problem453();
        int[] nums = {1, 2, 3};
        System.out.println(problem453.minMoves(nums));
    }

    /**
     * 模拟
     * 数组中n-1个元素加1相当于数组中1个元素减1，则数组中元素都相等的最小操作次数即为数组中元素都减少为数组中最小元素的操作次数
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int minMoves(int[] nums) {
        int min = nums[0];

        for (int num : nums) {
            min = Math.min(min, num);
        }

        int count = 0;

        for (int num : nums) {
            count = count + (num - min);
        }

        return count;
    }
}
