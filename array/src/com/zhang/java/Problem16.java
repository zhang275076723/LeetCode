package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2022/8/29 9:53
 * @Author zsy
 * @Description 最接近的三数之和 类比Problem1、Problem15、Problem18、Problem167、Problem454、Offer57
 * 给你一个长度为 n 的整数数组 nums 和 一个目标值 target。
 * 请你从 nums 中选出三个整数，使它们的和与 target 最接近。
 * 返回这三个数的和。
 * 假定每组输入只存在恰好一个解。
 * <p>
 * 输入：nums = [-1,2,1,-4], target = 1
 * 输出：2
 * 解释：与 target 最接近的和是 2 (-1 + 2 + 1 = 2) 。
 * <p>
 * 输入：nums = [0,0,0], target = 1
 * 输出：0
 * <p>
 * 3 <= nums.length <= 1000
 * -1000 <= nums[i] <= 1000
 * -10^4 <= target <= 10^4
 */
public class Problem16 {
    public static void main(String[] args) {
        Problem16 problem16 = new Problem16();
//        int[] nums = {-1, 2, 1, -4};
        int[] nums = {-1000, -5, -5, -5, -5, -5, -5, -1, -1, -1};
//        int target = 1;
        int target = -14;
        System.out.println(problem16.threeSumClosest(nums, target));
    }

    /**
     * 排序+双指针
     * 先排序，确定第一个元素，左右指针分别指向剩下的两个元素
     * 时间复杂度O(n^2)，空间复杂度O(logn) (快排的空间复杂度为O(logn))
     *
     * @param nums
     * @param target
     * @return
     */
    public int threeSumClosest(int[] nums, int target) {
        if (nums == null || nums.length < 3) {
            return 0;
        }

        quickSort(nums, 0, nums.length - 1);

        //最接近target的数组中三个元素之和
        int result = nums[0] + nums[1] + nums[2];

        for (int i = 0; i < nums.length - 2; i++) {
            //nums[i]去重
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            int left = i + 1;
            int right = nums.length - 1;

            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];

                //三数之和sum等于target，直接返回target
                if (sum == target) {
                    return target;
                } else if (sum < target) {
                    //三数之和sum小于target，left右移
                    left++;
                } else {
                    //三数之和sum大于target，right左移
                    right--;
                }

                //nums[left]去重
                while (left < right && left > i + 1 && nums[left] == nums[left - 1]) {
                    left++;
                }

                //nums[right]去重
                while (left < right && right < nums.length - 1 && nums[right] == nums[right + 1]) {
                    right--;
                }

                //sum比result更接近target，更新result
                if (Math.abs(sum - target) < Math.abs(result - target)) {
                    result = sum;
                }
            }
        }

        return result;
    }

    private void quickSort(int[] nums, int left, int right) {
        if (left < right) {
            int pivot = partition(nums, left, right);
            quickSort(nums, left, pivot - 1);
            quickSort(nums, pivot + 1, right);
        }
    }

    private int partition(int[] nums, int left, int right) {
        int temp = nums[left];

        while (left < right) {
            while (left < right && nums[right] >= temp) {
                right--;
            }
            nums[left] = nums[right];

            while (left < right && nums[left] <= temp) {
                left++;
            }
            nums[right] = nums[left];
        }

        nums[left] = temp;

        return left;
    }
}
