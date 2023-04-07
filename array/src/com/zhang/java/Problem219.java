package com.zhang.java;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Date 2023/3/22 09:13
 * @Author zsy
 * @Description 存在重复元素 II 大疆机试题 类比Problem217、Problem220
 * 给你一个整数数组 nums 和一个整数 k ，判断数组中是否存在两个 不同的索引 i 和 j ，
 * 满足 nums[i] == nums[j] 且 abs(i - j) <= k 。
 * 如果存在，返回 true ；否则，返回 false 。
 * <p>
 * 输入：nums = [1,2,3,1], k = 3
 * 输出：true
 * <p>
 * 输入：nums = [1,0,1,1], k = 1
 * 输出：true
 * <p>
 * 输入：nums = [1,2,3,1,2,3], k = 2
 * 输出：false
 * <p>
 * 1 <= nums.length <= 10^5
 * -10^9 <= nums[i] <= 10^9
 * 0 <= k <= 10^5
 */
public class Problem219 {
    public static void main(String[] args) {
        Problem219 problem219 = new Problem219();
        int[] nums = {1, 2, 3, 1, 2, 3};
        int k = 0;
        System.out.println(problem219.containsNearbyDuplicate(nums, k));
        System.out.println(problem219.containsNearbyDuplicate2(nums, k));
    }

    /**
     * 哈希表
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @param k
     * @return
     */
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        if (nums == null || nums.length < 2) {
            return false;
        }

        //key：nums数组中元素，value：nums数组中元素最后一次出现的下标索引
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            //map中不存在nums[i]，将nums[i]下标索引加入map
            if (!map.containsKey(nums[i])) {
                map.put(nums[i], i);
            } else {
                //nums[i]和最后一次出现的下标索引不超过k，则返回true
                if (i - map.get(nums[i]) <= k) {
                    return true;
                }
                //将nums[i]下标索引加入map
                map.put(nums[i], i);
            }
        }

        return false;
    }

    /**
     * 滑动窗口
     * 时间复杂度O(n)，空间复杂度O(k)
     *
     * @param nums
     * @param k
     * @return
     */
    public boolean containsNearbyDuplicate2(int[] nums, int k) {
        if (nums == null || nums.length < 2) {
            return false;
        }

        Set<Integer> set = new HashSet<>();
        //滑动窗口的左右指针
        int left = 0;
        int right = 0;

        while (right < nums.length) {
            //滑动窗口中已经存在nums[right]，并且两个元素的索引下标小于等于k，返回true
            if (set.contains(nums[right])) {
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
