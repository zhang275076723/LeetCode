package com.zhang.java;

/**
 * @Date 2022/11/1 16:41
 * @Author zsy
 * @Description 搜索插入位置 类比Problem33、Problem34、Problem81、Problem153、Problem154、Problem162、Problem852
 * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
 * 请必须使用时间复杂度为 O(logn) 的算法。
 * <p>
 * 输入: nums = [1,3,5,6], target = 5
 * 输出: 2
 * <p>
 * 输入: nums = [1,3,5,6], target = 2
 * 输出: 1
 * <p>
 * 输入: nums = [1,3,5,6], target = 7
 * 输出: 4
 * <p>
 * 1 <= nums.length <= 10^4
 * -10^4 <= nums[i] <= 10^4
 * nums 为 无重复元素 的 升序 排列数组
 * -10^4 <= target <= 10^4
 */
public class Problem35 {
    public static void main(String[] args) {
        Problem35 problem35 = new Problem35();
        int[] nums = {1, 3, 5, 6};
        int target = -2;
        System.out.println(problem35.searchInsert(nums, target));
    }

    /**
     * 二分查找变形，看到有序数组，就要想到二分查找
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param nums
     * @param target
     * @return
     */
    public int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int mid;
        int index = -1;

        while (left <= right) {
            mid = left + ((right - left) >> 1);

            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                //nums[mid]大于target，往左找
                right = mid - 1;
                index = mid;
            } else {
                //nums[mid]小于target，往右找
                left = mid + 1;
                index = mid + 1;
            }
        }

        return index;
    }
}
