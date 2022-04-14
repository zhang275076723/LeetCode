package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2022/3/13 10:27
 * @Author zsy
 * @Description 在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。数组中某些数字是重复的，
 * 但不知道有几个数字重复了，也不知道每个数字重复了几次。请找出数组中任意一个重复的数字
 * <p>
 * 输入：[2, 3, 1, 0, 2, 5, 3]
 * 输出：2 或 3
 */
public class Offer03 {
    public static void main(String[] args) {
        Offer03 offer03 = new Offer03();
        int[] nums = new int[]{2, 3, 1, 0, 2, 5, 3};
        System.out.println(offer03.findRepeatNumber(nums));

        offer03.quickSort(nums, 0, nums.length - 1);
        System.out.println(Arrays.toString(nums));

    }

    /**
     * 计数排序，找数组中元素大于1的下标索引
     *
     * @param nums
     * @return
     */
    public int findRepeatNumber(int[] nums) {
        int[] a = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            a[nums[i]]++;
            if (a[nums[i]] > 1) {
                return nums[i];
            }
        }
        return -1;
    }

    public void quickSort(int[] nums, int left, int right) {
        if (left < right) {
            int partition = partition(nums, left, right);
            quickSort(nums, left, partition - 1);
            quickSort(nums, partition + 1, right);
        }
    }

    public int partition(int[] nums, int left, int right) {
        int temp = nums[left];
        while (left < right) {
            while (left < right && temp <= nums[right]) {
                right--;
            }
            nums[left] = nums[right];
            while (left < right && temp >= nums[left]) {
                left++;
            }
            nums[right] = nums[left];
        }
        nums[left] = temp;
        return left;
    }

}
