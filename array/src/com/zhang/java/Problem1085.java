package com.zhang.java;

/**
 * @Date 2023/10/9 08:57
 * @Author zsy
 * @Description 最小元素各数位之和
 * 给你一个正整数的数组 A。
 * 然后计算 S，使其等于数组 A 当中最小的那个元素各个数位上数字之和。
 * 最后，假如 S 所得计算结果是 奇数 ，返回 0 ；否则请返回 1。
 * <p>
 * 输入：[34,23,1,24,75,33,54,8]
 * 输出：0
 * 解释：
 * 最小元素为 1 ，该元素各个数位上的数字之和 S = 1 ，是奇数所以答案为 0 。
 * <p>
 * 输入：[99,77,33,66,55]
 * 输出：1
 * 解释：
 * 最小元素为 33 ，该元素各个数位上的数字之和 S = 3 + 3 = 6 ，是偶数所以答案为 1 。
 * <p>
 * 1 <= A.length <= 100
 * 1 <= A[i] <= 100
 */
public class Problem1085 {
    public static void main(String[] args) {
        Problem1085 problem1085 = new Problem1085();
        int[] nums = {34, 23, 1, 24, 75, 33, 54, 8};
        System.out.println(problem1085.sumOfDigits(nums));
    }

    /**
     * 模拟
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int sumOfDigits(int[] nums) {
        int min = nums[0];

        for (int num : nums) {
            min = Math.min(min, num);
        }

        //min每一位之和
        int result = 0;

        while (min != 0) {
            result = result + min % 10;
            min = min / 10;
        }

        return result % 2 == 1 ? 0 : 1;
    }
}
