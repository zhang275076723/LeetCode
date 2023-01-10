package com.zhang.java;

/**
 * @Date 2022/11/14 17:15
 * @Author zsy
 * @Description 最大连续1的个数 II 滑动窗口类比Problem485、Problem1004
 * 给定一个二进制数组，你可以最多将 1 个 0 翻转为 1，找出其中最大连续 1 的个数。
 * <p>
 * 输入：[1,0,1,1,0]
 * 输出：4
 * 解释：翻转第一个 0 可以得到最长的连续 1。当翻转以后，最大连续 1 的个数为 4。
 * <p>
 * 输入数组只包含 0 和 1.
 * 输入数组的长度为正整数，且不超过 10,000
 */
public class Problem487 {
    public static void main(String[] args) {
        Problem487 problem487 = new Problem487();
        int[] nums = {1, 0, 1, 1, 0};
        System.out.println(problem487.findMaxConsecutiveOnes(nums));
    }

    /**
     * 滑动窗口，双指针
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int findMaxConsecutiveOnes(int[] nums) {
        int left = 0;
        int right = 0;
        int count = 0;
        //能将0转化为1的个数
        int k = 1;

        while (right < nums.length) {
            //右指针所指元素为0时，将可修改0的个数减1，即将当前元素0假设为1
            if (nums[right] == 0) {
                k--;
            }

            //当可修改0的个数小于0时，左指针右移
            while (k < 0) {
                //左指针所指元素为0时，即之前把当前元素0假设为1，k要++
                if (nums[left] == 0) {
                    k++;
                }

                left++;
            }

            count = Math.max(count, right - left + 1);
            right++;
        }

        return count;
    }
}
