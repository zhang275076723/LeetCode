package com.zhang.java;

/**
 * @Date 2021/11/18 21:21
 * @Author zsy
 * @Description 给定一个n个元素有序的（升序）整型数组nums和一个目标值target，
 * 写一个函数搜索nums中的target，如果目标值存在返回下标，否则返回-1
 * <p>
 * 输入: nums = [-1,0,3,5,9,12], target = 9
 * 输出: 4
 * 解释: 9 出现在 nums 中并且下标为 4
 * <p>
 * 输入: nums = [-1,0,3,5,9,12], target = 2
 * 输出: -1
 * 解释: 2 不存在 nums 中因此返回 -1
 */
public class Problem704 {
    public static void main(String[] args) {
        Problem704 p = new Problem704();
        int[] nums = {-1, 0, 3, 5, 9, 12};
        System.out.println(p.search(nums, 9));
    }

    /**
     * [a,b]左闭右闭情况：循环判断条件是<=，如果nums[mid] > target，则right = mid-1
     *
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {
        //如果target小于第一个元素或者大于最后一个元素，直接返回-1，提升效率
        if (target < nums[0] || target > nums[nums.length - 1]) {
            return -1;
        }

        int mid;
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            mid = (left + right) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    /**
     * [a,b)左闭右开情况：循环判断条件是<，如果nums[mid] > target，right = mid
     *
     * @param nums
     * @param target
     * @return
     */
    public int search2(int[] nums, int target) {
        //如果target小于第一个元素或者大于最后一个元素，直接返回-1，提升效率
        if (target < nums[0] || target > nums[nums.length - 1]) {
            return -1;
        }

        int mid;
        int left = 0;
        int right = nums.length;
        while (left < right) {
            mid = (left + right) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return -1;
    }
}
