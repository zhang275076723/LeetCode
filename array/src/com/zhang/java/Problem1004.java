package com.zhang.java;

/**
 * @Date 2022/11/14 17:33
 * @Author zsy
 * @Description 最大连续1的个数 III b站机试题 类比Problem485、Problem487
 * 给定一个二进制数组 nums 和一个整数 k，如果可以翻转最多 k 个 0 ，则返回 数组中连续 1 的最大个数 。
 * <p>
 * 输入：nums = [1,1,1,0,0,0,1,1,1,1,0], K = 2
 * 输出：6
 * 解释：[1,1,1,0,0,1,1,1,1,1,1]
 * 粗体数字从 0 翻转到 1，最长的子数组长度为 6。
 * <p>
 * 输入：nums = [0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1], K = 3
 * 输出：10
 * 解释：[0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1]
 * 粗体数字从 0 翻转到 1，最长的子数组长度为 10。
 * <p>
 * 1 <= nums.length <= 10^5
 * nums[i] 不是 0 就是 1
 * 0 <= k <= nums.length
 */
public class Problem1004 {
    public static void main(String[] args) {
        Problem1004 problem1004 = new Problem1004();
        int[] nums = {1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0};
        int k = 2;
        System.out.println(problem1004.longestOnes(nums, k));
    }

    /**
     * 滑动窗口，双指针
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @param k
     * @return
     */
    public int longestOnes(int[] nums, int k) {
        int left = 0;
        int right = 0;
        int count = 0;

        while (right < nums.length) {
            //右指针所指元素为0时，将可修改0的个数减1
            if (nums[right] == 0) {
                k--;
            }

            //当可修改0的个数小于0时，左指针右移
            while (k < 0) {
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
