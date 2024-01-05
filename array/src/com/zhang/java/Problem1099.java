package com.zhang.java;

import java.util.Random;

/**
 * @Date 2024/1/5 08:13
 * @Author zsy
 * @Description 小于 K 的两数之和 类比Problem1、Problem15、Problem16、Problem18、Problem167、Problem170、Problem454、Problem653、Offer57 双指针类比
 * 给你一个整数数组 nums 和整数 k ，返回最大和 sum ，满足存在 i < j 使得 nums[i] + nums[j] = sum 且 sum < k 。
 * 如果没有满足此等式的 i,j 存在，则返回 -1 。
 * <p>
 * 输入：nums = [34,23,1,24,75,33,54,8], k = 60
 * 输出：58
 * 解释：
 * 34 和 24 相加得到 58，58 小于 60，满足题意。
 * <p>
 * 输入：nums = [10,20,30], k = 15
 * 输出：-1
 * 解释：
 * 我们无法找到和小于 15 的两个元素。
 * <p>
 * 1 <= nums.length <= 100
 * 1 <= nums[i] <= 1000
 * 1 <= k <= 2000
 */
public class Problem1099 {
    public static void main(String[] args) {
        Problem1099 problem1099 = new Problem1099();
        int[] nums = {34, 23, 1, 24, 75, 33, 54, 8};
        int k = 60;
//        int[] nums = {10, 20, 30};
//        int k = 15;
        System.out.println(problem1099.twoSumLessThanK(nums, k));
    }

    /**
     * 排序+双指针
     * 时间复杂度O(nlogn)，空间复杂度O(logn)
     *
     * @param nums
     * @param k
     * @return
     */
    public int twoSumLessThanK(int[] nums, int k) {
        if (nums == null || nums.length < 2) {
            return -1;
        }

        //由小到大排序
        quickSort(nums, 0, nums.length - 1);

        int left = 0;
        int right = nums.length - 1;
        int sum = -1;

        while (left < right) {
            if (nums[left] + nums[right] < k) {
                sum = Math.max(sum, nums[left] + nums[right]);
                left++;
            } else {
                right--;
            }
        }

        return sum;
    }

    private void quickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }

        int pivot = partition(arr, left, right);
        quickSort(arr, left, pivot - 1);
        quickSort(arr, pivot + 1, right);
    }

    private int partition(int[] arr, int left, int right) {
        int randomIndex = new Random().nextInt(right - left + 1) + left;

        int value = arr[left];
        arr[left] = arr[randomIndex];
        arr[randomIndex] = value;

        int temp = arr[left];

        while (left < right) {
            while (left < right && arr[right] >= temp) {
                right--;
            }
            arr[left] = arr[right];

            while (left < right && arr[left] <= temp) {
                left++;
            }
            arr[right] = arr[left];
        }

        arr[left] = temp;

        return left;
    }
}
