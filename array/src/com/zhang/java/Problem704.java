package com.zhang.java;

/**
 * @Date 2021/11/18 21:21
 * @Author zsy
 * @Description 二分查找
 * 给定一个 n 个元素有序的（升序）整型数组 nums 和一个目标值 target ，
 * 写一个函数搜索 nums 中的 target，如果目标值存在返回下标，否则返回 -1。
 * <p>
 * 输入: nums = [-1,0,3,5,9,12], target = 9
 * 输出: 4
 * 解释: 9 出现在 nums 中并且下标为 4
 * <p>
 * 输入: nums = [-1,0,3,5,9,12], target = 2
 * 输出: -1
 * 解释: 2 不存在 nums 中因此返回 -1
 * <p>
 * 你可以假设 nums 中的所有元素是不重复的。
 * n 将在 [1, 10000]之间。
 * nums 的每个元素都将在 [-9999, 9999]之间。
 */
public class Problem704 {
    public static void main(String[] args) {
        Problem704 problem704 = new Problem704();
        int[] nums = {-1, 0, 3, 5, 9, 12};
        System.out.println(problem704.search(nums, 9));
        System.out.println(problem704.search2(nums, 9));
    }

    /**
     * 二分查找
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0 || target < nums[0] || target > nums[nums.length - 1]) {
            return -1;
        }

        int left = 0;
        int right = nums.length - 1;
        int mid;

        while (left <= right) {
            mid = left + ((right - left) >> 1);

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
     * 递归二分查找
     * 时间复杂度O(logn)，空间复杂度O(logn)
     *
     * @param nums
     * @param target
     * @return
     */
    public int search2(int[] nums, int target) {
        if (nums == null || nums.length == 0 || target < nums[0] || target > nums[nums.length - 1]) {
            return -1;
        }

        return search2(nums, target, 0, nums.length - 1);
    }

    private int search2(int[] nums, int target, int left, int right) {
        if (left > right) {
            return -1;
        }

        if (left == right) {
            if (nums[left] == target) {
                return left;
            }

            return -1;
        }

        int mid = left + ((right - left) >> 1);

        if (nums[mid] == target) {
            return mid;
        } else if (nums[mid] > target) {
            return search2(nums, target, left, mid - 1);
        } else {
            return search2(nums, target, mid + 1, right);
        }
    }
}
