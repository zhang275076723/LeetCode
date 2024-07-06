package com.zhang.java;

/**
 * @Date 2024/8/16 08:30
 * @Author zsy
 * @Description 检查按位或是否存在尾随零 类比Problem2710
 * 给你一个 正整数 数组 nums 。
 * 你需要检查是否可以从数组中选出 两个或更多 元素，满足这些元素的按位或运算（ OR）结果的二进制表示中 至少 存在一个尾随零。
 * 例如，数字 5 的二进制表示是 "101"，不存在尾随零，而数字 4 的二进制表示是 "100"，存在两个尾随零。
 * 如果可以选择两个或更多元素，其按位或运算结果存在尾随零，返回 true；否则，返回 false 。
 * <p>
 * 输入：nums = [1,2,3,4,5]
 * 输出：true
 * 解释：如果选择元素 2 和 4，按位或运算结果是 6，二进制表示为 "110" ，存在一个尾随零。
 * <p>
 * 输入：nums = [2,4,8,16]
 * 输出：true
 * 解释：如果选择元素 2 和 4，按位或运算结果是 6，二进制表示为 "110"，存在一个尾随零。
 * 其他按位或运算结果存在尾随零的可能选择方案包括：(2, 8), (2, 16), (4, 8), (4, 16), (8, 16), (2, 4, 8),
 * (2, 4, 16), (2, 8, 16), (4, 8, 16), 以及 (2, 4, 8, 16) 。
 * <p>
 * 输入：nums = [1,3,5,7,9]
 * 输出：false
 * 解释：不存在按位或运算结果存在尾随零的选择方案。
 * <p>
 * 2 <= nums.length <= 100
 * 1 <= nums[i] <= 100
 */
public class Problem2980 {
    public static void main(String[] args) {
        Problem2980 problem2980 = new Problem2980();
        int[] nums = {1, 2, 3, 4, 5};
        System.out.println(problem2980.hasTrailingZeros(nums));
    }

    /**
     * 模拟
     * 数组中至少存在2个元素最低位为0，则这2个元素或运算存在尾随零
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public boolean hasTrailingZeros(int[] nums) {
        int count = 0;

        for (int num : nums) {
            if ((num & 1) == 0) {
                count++;
            }

            if (count >= 2) {
                return true;
            }
        }

        //遍历结束没有找到至少存在2个元素最低位为0，则返回false
        return false;
    }
}
