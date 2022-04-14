package com.zhang.java;

/**
 * @Date 2022/4/2 14:48
 * @Author zsy
 * @Description 统计一个数字在排序数组中出现的次数。
 * <p>
 * 输入: nums = [5,7,7,8,8,10], target = 8
 * 输出: 2
 * <p>
 * 输入: nums = [5,7,7,8,8,10], target = 6
 * 输出: 0
 */
public class Offer53 {
    public static void main(String[] args) {
        Offer53 offer53 = new Offer53();
        int[] nums = {5, 7, 7, 8, 8, 10};
        System.out.println(offer53.search(nums, 8));
    }

    /**
     * 两次二分查找，时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int leftIndex;
        int rightIndex;

        //找比第一个target小的元素索引
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (target <= nums[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        leftIndex = right;

        //如果leftIndex是最后一个元素索引，或leftIndex的下一个索引元素不为target，则说明未找到
        if (leftIndex == nums.length - 1 || nums[leftIndex + 1] != target) {
            return 0;
        }

        //找比最后一个target大的元素索引
        left = 0;
        right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] <= target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        rightIndex = left;

        return rightIndex - leftIndex - 1;
    }
}
