package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2022/4/18 12:31
 * @Author zsy
 * @Description 在排序数组中查找元素的第一个和最后一个位置 类比Problem33、Problem153、Problem154、Problem162、Offer11、Offer53_2 同Offer53
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

        int left = 0;
        int right = nums.length - 1;
        int mid;
        int start = -1;
        int end = -1;

        //寻找start，第一个target
        while (left <= right) {
            mid = left + ((right - left) >> 1);

            if (nums[mid] == target) {
                start = mid;
                right = mid - 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        if (start == -1) {
            return new int[]{-1, -1};
        }

        //寻找end，最后一个target
        left = start;
        right = nums.length - 1;

        while (left <= right) {
            mid = left + ((right - left) >> 1);

            if (nums[mid] == target) {
                end = mid;
                left = mid + 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return new int[]{start, end};
    }
}
