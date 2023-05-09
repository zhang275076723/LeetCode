package com.zhang.java;

/**
 * @Date 2022/4/2 14:48
 * @Author zsy
 * @Description 在排序数组中查找数字 I 类比Problem33、Problem35、Problem81、Problem153、Problem154、Problem162、Problem852、Offer11、Offer53_2、Interview_10_03 同Problem34
 * 统计一个数字在排序数组中出现的次数。
 * <p>
 * 输入: nums = [5,7,7,8,8,10], target = 8
 * 输出: 2
 * <p>
 * 输入: nums = [5,7,7,8,8,10], target = 6
 * 输出: 0
 * <p>
 * 0 <= nums.length <= 10^5
 * -10^9 <= nums[i] <= 10^9
 * nums是一个非递减数组
 * -10^9 <= target <= 10^9
 */
public class Offer53 {
    public static void main(String[] args) {
        Offer53 offer53 = new Offer53();
        int[] nums = {5, 7, 7, 8, 8, 10};
        System.out.println(offer53.search(nums, 8));
    }

    /**
     * 二分查找变形，看到有序数组，就要想到二分查找
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
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
            return 0;
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

        return last - first + 1;
    }
}
