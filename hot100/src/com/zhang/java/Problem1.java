package com.zhang.java;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/4/11 7:36
 * @Author zsy
 * @Description 两数之和 类比Problem15、Problem16、Problem18、Problem167、Problem170、Problem454、Problem653、Problem1099、Offer57 哈希表类比Problem128、Problem166、Problem187、Problem205、Problem242、Problem290、Problem291、Problem383、Problem387、Problem389、Problem454、Problem532、Problem535、Problem554、Problem609、Problem763、Problem1500、Problem1640、Offer50
 * 给定一个整数数组 nums 和一个整数目标值 target，
 * 请你在该数组中找出 和为目标值 target 的那两个整数，并返回它们的数组下标。
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
 * 你可以按任意顺序返回答案。
 * <p>
 * 输入：nums = [2,7,11,15], target = 9
 * 输出：[0,1]
 * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
 * <p>
 * 输入：nums = [3,2,4], target = 6
 * 输出：[1,2]
 * <p>
 * 输入：nums = [3,3], target = 6
 * 输出：[0,1]
 * <p>
 * 2 <= nums.length <= 10^4
 * -10^9 <= nums[i] <= 10^9
 * -10^9 <= target <= 10^9
 * 只会存在一个有效答案
 */
public class Problem1 {
    public static void main(String[] args) {
        Problem1 problem1 = new Problem1();
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        System.out.println(Arrays.toString(problem1.twoSum(nums, target)));
        System.out.println(Arrays.toString(problem1.twoSum2(nums, target)));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return new int[0];
        }

        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }

        return new int[0];
    }

    /**
     * 哈希表
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum2(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return new int[0];
        }

        //key：nums中元素；value：nums中元素的下标索引
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{i, map.get(target - nums[i])};
            }

            map.put(nums[i], i);
        }

        return new int[0];
    }
}
