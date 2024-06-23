package com.zhang.java;

/**
 * @Date 2024/6/29 08:09
 * @Author zsy
 * @Description 使数组异或和等于 K 的最少操作次数 类比Problem1318、Problem2220 位运算类比
 * 给你一个下标从 0 开始的整数数组 nums 和一个正整数 k 。
 * 你可以对数组执行以下操作 任意次 ：
 * 选择数组里的 任意 一个元素，并将它的 二进制 表示 翻转 一个数位，翻转数位表示将 0 变成 1 或者将 1 变成 0 。
 * 你的目标是让数组里 所有 元素的按位异或和得到 k ，请你返回达成这一目标的 最少 操作次数。
 * 注意，你也可以将一个数的前导 0 翻转。比方说，数字 (101)2 翻转第四个数位，得到 (1101)2 。
 * <p>
 * 输入：nums = [2,1,3,4], k = 1
 * 输出：2
 * 解释：我们可以执行以下操作：
 * - 选择下标为 2 的元素，也就是 3 == (011)2 ，我们翻转第一个数位得到 (010)2 == 2 。数组变为 [2,1,2,4] 。
 * - 选择下标为 0 的元素，也就是 2 == (010)2 ，我们翻转第三个数位得到 (110)2 == 6 。数组变为 [6,1,2,4] 。
 * 最终数组的所有元素异或和为 (6 XOR 1 XOR 2 XOR 4) == 1 == k 。
 * 无法用少于 2 次操作得到异或和等于 k 。
 * <p>
 * 输入：nums = [2,0,2,0], k = 0
 * 输出：0
 * 解释：数组所有元素的异或和为 (2 XOR 0 XOR 2 XOR 0) == 0 == k 。所以不需要进行任何操作。
 * <p>
 * 1 <= nums.length <= 10^5
 * 0 <= nums[i] <= 10^6
 * 0 <= k <= 10^6
 */
public class Problem2997 {
    public static void main(String[] args) {
        Problem2997 problem2997 = new Problem2997();
        int[] nums = {2, 1, 3, 4};
        int k = 1;
        System.out.println(problem2997.minOperations(nums, k));
    }

    /**
     * 位运算 (bitCount()源码)
     * nums中元素和k异或运算结果中二进制中1的个数即为需要翻转的次数
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @param nums
     * @param k
     * @return
     */
    public int minOperations(int[] nums, int k) {
        int xor = k;

        for (int num : nums) {
            xor = xor ^ num;
        }

        //bitCount()源码求xor中1的个数
        xor = ((xor & 0xaaaaaaaa) >>> 1) + (xor & 0x55555555);
        xor = ((xor & 0xcccccccc) >>> 2) + (xor & 0x33333333);
        xor = ((xor & 0xf0f0f0f0) >>> 4) + (xor & 0x0f0f0f0f);
        xor = ((xor & 0xff00ff00) >>> 8) + (xor & 0x00ff00ff);
        xor = ((xor & 0xffff0000) >>> 16) + (xor & 0x0000ffff);

        return xor;
    }
}
