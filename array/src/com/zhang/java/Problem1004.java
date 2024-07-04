package com.zhang.java;

/**
 * @Date 2022/11/14 17:33
 * @Author zsy
 * @Description 最大连续1的个数 III b站机试题 类比Problem424、Problem1493 滑动窗口类比Problem3、Problem30、Problem76、Problem209、Problem219、Problem220、Problem239、Problem340、Problem438、Problem485、Problem487、Problem567、Problem632、Problem643、Problem713、Problem1456、Problem1839、Problem2062、Offer48、Offer57_2、Offer59
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
        int result = 0;

        while (right < nums.length) {
            //右指针所指元素为0时，将可修改0为1的个数k减1，即将当前元素0假设置为1
            if (nums[right] == 0) {
                k--;
            }

            //当可修改0为1的个数小于0时，左指针右移
            while (k < 0) {
                //左指针所指元素为0时，即把之前假设为1的元素0恢复为0，可修改0为1的个数k加1
                if (nums[left] == 0) {
                    k++;
                }

                //左指针右移
                left++;
            }

            //更新最大值
            result = Math.max(result, right - left + 1);
            //右指针右移
            right++;
        }

        return result;
    }
}
