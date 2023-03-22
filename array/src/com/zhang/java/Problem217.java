package com.zhang.java;

import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2022/11/5 16:45
 * @Author zsy
 * @Description 存在重复元素 类比Problem219、Problem220
 * 给你一个整数数组 nums 。如果任一值在数组中出现 至少两次 ，返回 true ；如果数组中每个元素互不相同，返回 false 。
 * <p>
 * 输入：nums = [1,2,3,1]
 * 输出：true
 * <p>
 * 输入：nums = [1,2,3,4]
 * 输出：false
 * <p>
 * 输入：nums = [1,1,1,3,3,4,3,2,4,2]
 * 输出：true
 * <p>
 * 1 <= nums.length <= 10^5
 * -10^9 <= nums[i] <= 10^9
 */
public class Problem217 {
    public static void main(String[] args) {
        Problem217 problem217 = new Problem217();
        int[] nums = {1, 1, 1, 3, 3, 4, 3, 2, 4, 2};
        System.out.println(problem217.containsDuplicate(nums));
    }

    /**
     * 哈希表
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public boolean containsDuplicate(int[] nums) {
        if (nums == null || nums.length < 2) {
            return false;
        }

        Set<Integer> set = new HashSet<>();

        for (int num : nums) {
            if (set.contains(num)) {
                return true;
            }
            set.add(num);
        }

        return false;
    }
}
