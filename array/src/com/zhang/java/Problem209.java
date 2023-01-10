package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2021/11/25 10:05
 * @Author zsy
 * @Description 长度最小的子数组 滑动窗口类比Problem3、Problem76、Problem438、Problem567、Offer48、Offer57_2、Offer59 前缀和类比Problem437、Problem560、Offer57_2
 * 给定一个含有n个正整数的数组和一个正整数target
 * 找出该数组中满足其和 ≥ target的长度最小的连续子数组，并返回其长度。如果不存在符合条件的子数组，返回0
 * 注意：涉及连续子数组问题，一：考虑前缀和；二：考虑滑动窗口
 * <p>
 * 输入：target = 7, nums = [2,3,1,2,4,3]
 * 输出：2
 * 解释：子数组 [4,3] 是该条件下的长度最小的子数组。
 * <p>
 * 输入：target = 4, nums = [1,4,4]
 * 输出：1
 * <p>
 * 输入：target = 11, nums = [1,1,1,1,1,1,1,1]
 * 输出：0
 * <p>
 * 1 <= target <= 10^9
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i] <= 10^5
 */
public class Problem209 {
    public static void main(String[] args) {
        Problem209 p = new Problem209();
        int[] nums = {2, 3, 1, 2, 4, 3};
        System.out.println(p.minSubArrayLen(7, nums));
        System.out.println(p.minSubArrayLen2(7, nums));
        System.out.println(p.minSubArrayLen3(7, nums));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param target
     * @param nums
     * @return
     */
    public int minSubArrayLen(int target, int[] nums) {
        int length = Integer.MAX_VALUE;

        for (int i = 0; i < nums.length; i++) {
            int sum = 0;

            for (int j = i; j < nums.length; j++) {
                sum = sum + nums[j];

                if (sum >= target) {
                    length = Integer.min(length, j - i + 1);
                    break;
                }
            }
        }

        return length == Integer.MAX_VALUE ? 0 : length;
    }

    /**
     * 前缀和 + 二分查找，前缀和在数组中元素存在负数的情况仍有效，但滑动窗口在数组中存在负数的时候失效
     * 看到连续子数组，想到滑动窗口和前缀和(适合有负数的情况)
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param target
     * @param nums
     * @return
     */
    public int minSubArrayLen2(int target, int[] nums) {
        //前缀和数组，preSum[i]：表示前i个元素nums[0..i-1]的和
        int[] preSum = new int[nums.length + 1];
        preSum[0] = 0;

        for (int i = 1; i < preSum.length; i++) {
            preSum[i] = preSum[i - 1] + nums[i - 1];
        }

        int minLength = Integer.MAX_VALUE;

        for (int i = 0; i < nums.length; i++) {
            int tempSum = preSum[i] + target;
            //二分查找，找到返回下标索引，没找到返回(-(该值在数组中应该插入的位置索引+1))
            int index = Arrays.binarySearch(preSum, tempSum);

            //不在数组中，返回值为负数
            if (index < 0) {
                index = -index - 1;
            }

            //更新length
            if (index <= nums.length) {
                minLength = Integer.min(minLength, index - i);
            }
        }

        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }

    /**
     * 滑动窗口，双指针
     * 看到连续子数组，想到滑动窗口和前缀和(适合有负数的情况)
     * 因为数组中元素都是正数，所以可以使用滑动窗口，如果存在负数，则只能使用前缀和
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param target
     * @param nums
     * @return
     */
    public int minSubArrayLen3(int target, int[] nums) {
        int minLength = Integer.MAX_VALUE;
        int left = 0;
        int right = 0;
        int sum = 0;

        while (right < nums.length) {
            sum = sum + nums[right];

            while (sum >= target) {
                //更新满足要求的最小子数组长度
                if (right - left + 1 < minLength) {
                    minLength = right - left + 1;
                }

                sum = sum - nums[left];
                left++;
            }

            right++;
        }

        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }

}
