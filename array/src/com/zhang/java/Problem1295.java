package com.zhang.java;

/**
 * @Date 2024/8/11 08:25
 * @Author zsy
 * @Description 统计位数为偶数的数字
 * 给你一个整数数组 nums，请你返回其中位数为 偶数 的数字的个数。
 * <p>
 * 输入：nums = [12,345,2,6,7896]
 * 输出：2
 * 解释：
 * 12 是 2 位数字（位数为偶数）
 * 345 是 3 位数字（位数为奇数）
 * 2 是 1 位数字（位数为奇数）
 * 6 是 1 位数字 位数为奇数）
 * 7896 是 4 位数字（位数为偶数）
 * 因此只有 12 和 7896 是位数为偶数的数字
 * <p>
 * 输入：nums = [555,901,482,1771]
 * 输出：1
 * 解释：
 * 只有 1771 是位数为偶数的数字。
 * <p>
 * 1 <= nums.length <= 500
 * 1 <= nums[i] <= 10^5
 */
public class Problem1295 {
    public static void main(String[] args) {
        Problem1295 problem1295 = new Problem1295();
        int[] nums = {12, 345, 2, 6, 7896};
        System.out.println(problem1295.findNumbers(nums));
    }

    /**
     * 模拟
     * 时间复杂度O(nlogC)=O(n)，空间复杂度O(1) (C=max(nums[i])=10^5)
     *
     * @param nums
     * @return
     */
    public int findNumbers(int[] nums) {
        int count = 0;

        for (int num : nums) {
            //num的位数
            int length = 0;

            while (num != 0) {
                num = num / 10;
                length++;
            }

            if (length % 2 == 0) {
                count++;
            }
        }

        return count;
    }
}
