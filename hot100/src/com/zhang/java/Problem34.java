package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2022/4/18 12:31
 * @Author zsy
 * @Description 在排序数组中查找元素的第一个和最后一个位置 类比Problem33、Problem35、Problem81、Problem153、Problem154、Problem162、Problem275、Problem540、Problem852、Problem1095、Offer11、Offer53_2、Interview_10_03、Interview_10_05 同Offer53
 * 在排序数组中查找元素的第一个和最后一个位置
 * 给定一个按照升序排列的整数数组 nums，和一个目标值 target。
 * 找出给定目标值在数组中的开始位置和结束位置。
 * 如果数组中不存在目标值 target，返回 [-1, -1]。
 * <p>
 * 输入：nums = [5,7,7,8,8,10], target = 8
 * 输出：[3,4]
 * <p>
 * 输入：nums = [5,7,7,8,8,10], target = 6
 * 输出：[-1,-1]
 * <p>
 * 输入：nums = [], target = 0
 * 输出：[-1,-1]
 * <p>
 * 0 <= nums.length <= 10^5
 * -10^9 <= nums[i] <= 10^9
 * nums 是一个非递减数组
 * -10^9 <= target <= 10^9
 */
public class Problem34 {
    public static void main(String[] args) {
        Problem34 problem34 = new Problem34();
        int[] nums = {5, 7, 7, 8, 8, 10};
        int target = 6;
        System.out.println(Arrays.toString(problem34.searchRange(nums, target)));
    }

    /**
     * 二分查找变形，看到有序数组，就要想到二分查找
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] searchRange(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return new int[]{-1, -1};
        }

        //数组中第一个值为target的索引下标
        int first = -1;
        //数组中最后一个值为target的索引下标
        int last = -1;
        int left = 0;
        int right = nums.length - 1;
        int mid;

        //找第一个值为target的索引下标first
        while (left <= right) {
            mid = left + ((right - left) >> 1);

            if (nums[mid] == target) {
                first = mid;
                right = mid - 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        //target不在数组中，直接返回
        if (first == -1) {
            return new int[]{-1, -1};
        }

        last = first;
        left = first + 1;
        right = nums.length - 1;

        //找最后一个值为target的索引下标last
        while (left <= right) {
            mid = left + ((right - left) >> 1);

            if (nums[mid] == target) {
                last = mid;
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return new int[]{first, last};
    }
}
