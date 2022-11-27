package com.zhang.java;

/**
 * @Date 2022/11/14 16:58
 * @Author zsy
 * @Description 最大连续 1 的个数 类比Problem487、Problem1004
 * 给定一个二进制数组 nums ， 计算其中最大连续 1 的个数。
 * <p>
 * 输入：nums = [1,1,0,1,1,1]
 * 输出：3
 * 解释：开头的两位和最后的三位都是连续 1 ，所以最大连续 1 的个数是 3.
 * <p>
 * 输入：nums = [1,0,1,1,0,1]
 * 输出：2
 * <p>
 * 1 <= nums.length <= 10^5
 * nums[i] 不是 0 就是 1.
 */
public class Problem485 {
    public static void main(String[] args) {
        Problem485 problem485 = new Problem485();
        int[] nums = {1, 0, 1, 1, 0, 1};
        System.out.println(problem485.findMaxConsecutiveOnes(nums));
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

        while (right < nums.length) {
            if (nums[right] == 1) {
                count = Math.max(count, right - left + 1);
                right++;
            } else {
                right++;
                left = right;
            }
        }

        return count;
    }
}