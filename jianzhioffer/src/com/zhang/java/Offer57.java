package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/4/4 16:05
 * @Author zsy
 * @Description 和为s的两个数字 类比Offer57_2
 * 输入一个 递增排序 的数组和一个数字s，在数组中查找两个数，使得它们的和正好是s。
 * 如果有多对数字的和等于s，则输出任意一对即可。
 * <p>
 * 输入：nums = [2,7,11,15], target = 9
 * 输出：[2,7] 或者 [7,2]
 * <p>
 * 输入：nums = [10,26,30,31,47,60], target = 40
 * 输出：[10,30] 或者 [30,10]
 * <p>
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i] <= 10^6
 */
public class Offer57 {
    public static void main(String[] args) {
        Offer57 offer57 = new Offer57();
        int[] nums = {10, 26, 30, 31, 47, 60};
        System.out.println(Arrays.toString(offer57.twoSum(nums, 40)));
        System.out.println(Arrays.toString(offer57.twoSum2(nums, 40)));
        System.out.println(Arrays.toString(offer57.twoSum3(nums, 40)));
        System.out.println(Arrays.toString(offer57.twoSum4(nums, 40)));
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
        if (nums.length < 2) {
            return null;
        }

        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] >= target) {
                return null;
            }

            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] >= target) {
                    break;
                }
                if (nums[i] + nums[j] == target) {
                    return new int[]{nums[i], nums[j]};
                }
            }
        }

        return null;
    }

    /**
     * 二分查找变形，看到有序数组，就要想到二分查找
     * 时间复杂度(nlogn)，空间复杂度O(1)
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum2(int[] nums, int target) {
        if (nums.length < 2) {
            return null;
        }

        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] >= target) {
                return null;
            }

            int left = i + 1;
            int right = nums.length - 1;
            int mid;

            while (left <= right) {
                mid = left + ((right - left) >> 1);

                if (nums[i] + nums[mid] == target) {
                    return new int[]{nums[i], nums[mid]};
                } else if (nums[i] + nums[mid] < target) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }

        return null;
    }

    /**
     * 哈希表
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum3(int[] nums, int target) {
        if (nums.length < 2) {
            return null;
        }

        Set<Integer> set = new HashSet<>();

        for (int num : nums) {
            //set中存在target-num，则找到
            if (set.contains(target - num)) {
                return new int[]{num, target - num};
            }
            set.add(num);
        }

        return null;
    }

    /**
     * 滑动窗口，双指针
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum4(int[] nums, int target) {
        if (nums.length < 2) {
            return null;
        }

        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int sum = nums[left] + nums[right];

            if (sum == target) {
                return new int[]{nums[left], nums[right]};
            } else if (sum < target) {
                //指针所指元素之和小于target，说明当前最大值加最小值仍然小于target，需要最小值右移
                left++;
            } else {
                //指针所指元素之和大于target，说明当前最大值加最小值仍然大于target，需要最大值左移
                right--;
            }
        }

        return null;
    }
}