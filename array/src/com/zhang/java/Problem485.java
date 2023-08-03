package com.zhang.java;

/**
 * @Date 2022/11/14 16:58
 * @Author zsy
 * @Description 最大连续1的个数 滑动窗口类比Problem3、Problem30、Problem76、Problem209、Problem219、Problem220、Problem239、Problem340、Problem438、Problem487、Problem567、Problem1004、Offer48、Offer57_2、Offer59
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
        int result = 0;
        int left = 0;
        int right = 0;

        while (right < nums.length) {
            if (nums[right] == 0) {
                right++;
                left = right;
            } else {
                //当前元素为1，则更新最大连续1的个数
                result = Math.max(result, right - left + 1);
                right++;
            }
        }

        return result;
    }
}
