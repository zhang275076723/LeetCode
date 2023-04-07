package com.zhang.java;

import java.util.TreeSet;

/**
 * @Date 2023/3/22 10:23
 * @Author zsy
 * @Description 存在重复元素 III 类比Problem217、Problem219
 * 给你一个整数数组 nums 和两个整数 k 和 t 。
 * 请你判断是否存在 两个不同下标 i 和 j，使得 abs(nums[i] - nums[j]) <= t ，同时又满足 abs(i - j) <= k 。
 * 如果存在则返回 true，不存在返回 false。
 * <p>
 * 输入：nums = [1,2,3,1], k = 3, t = 0
 * 输出：true
 * <p>
 * 输入：nums = [1,0,1,1], k = 1, t = 2
 * 输出：true
 * <p>
 * 输入：nums = [1,5,9,1,5,9], k = 2, t = 3
 * 输出：false
 * <p>
 * 0 <= nums.length <= 2 * 10^4
 * -2^31 <= nums[i] <= 2^31 - 1
 * 0 <= k <= 10^4
 * 0 <= t <= 2^31 - 1
 */
public class Problem220 {
    public static void main(String[] args) {
        Problem220 problem220 = new Problem220();
        int[] nums = {1, 0, 1, 1};
        int k = 1;
        int t = 2;
        System.out.println(problem220.containsNearbyAlmostDuplicate(nums, k, t));
    }

    /**
     * 滑动窗口+有序集合
     * 滑动窗口保证数组中两个元素下标索引不超过k，有序集合保证O(logk)得到大于nums[i]-t的最小元素
     * 时间复杂度O(nlogk)，空间复杂度O(k)
     *
     * @param nums
     * @param k
     * @param t
     * @return
     */
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        //TreeSet中才有ceiling()，TreeSet添加、删除、查找的时间复杂度都为O(logn)
        TreeSet<Integer> set = new TreeSet<>();
        int left = 0;
        int right = 0;

        while (right < nums.length) {
            //通过TreeSet在O(logn)快速找到，set中大于等于nums[right]-t的最小元素
            Integer num = set.ceiling(nums[right] - t);

            //num存在，并且nums[right]和num差的绝对值小于等于t，返回true
            if (num != null && Math.abs(nums[right] - num) <= t) {
                return true;
            }

            //nums[right]加入set，右指针右移
            set.add(nums[right]);
            right++;

            //始终保持滑动窗口大小不超过k，当滑动窗口大小大于k时，nums[left]从set中移除，左指针右移
            if (right - left > k) {
                set.remove(nums[left]);
                left++;
            }
        }

        return false;
    }
}
