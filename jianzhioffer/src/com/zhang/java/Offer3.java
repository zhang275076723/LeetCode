package com.zhang.java;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2022/3/13 10:27
 * @Author zsy
 * @Description 数组中重复的数字 类比Problem287、Problem448、Offer3
 * 在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。数组中某些数字是重复的，
 * 但不知道有几个数字重复了，也不知道每个数字重复了几次。请找出数组中任意一个重复的数字
 * <p>
 * 输入：[2, 3, 1, 0, 2, 5, 3]
 * 输出：2 或 3
 */
public class Offer3 {
    public static void main(String[] args) {
        Offer3 offer3 = new Offer3();
        int[] nums = new int[]{2, 3, 1, 0, 2, 5, 3};
        System.out.println(offer3.findRepeatNumber(nums));
        System.out.println(offer3.findRepeatNumber2(nums));
        System.out.println(offer3.findRepeatNumber3(nums));

        offer3.quickSort(nums, 0, nums.length - 1);
        System.out.println(Arrays.toString(nums));
    }

    /**
     * 计数排序，找数组中元素大于1的下标索引
     * 时间复杂度O(n)，空间复杂度O(n)
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

    /**
     * 哈希表，时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int findRepeatNumber2(int[] nums) {
        Set<Integer> set = new HashSet<>(nums.length);

        for (int num : nums) {
            if (set.contains(num)) {
                return num;
            }
            set.add(num);
        }

        return -1;
    }

    /**
     * 原地哈希，原数组作为哈希表，时间复杂度O(n)，空间复杂度O(1)
     * 数组nums[i]和i对应，如果不相等，则进行交换；如果相等，且i!=nums[i]，说明不是第一次出现，则返回nums[i]
     * 例如：[(2), 3, (1), 0, 2, 5, 3]
     * 第一次交换：[(1), (3), 2, 0, 2, 5, 3]
     * 第二次交换：[(3), 1, 2, (0), 2, 5, 3]
     * 第三次交换：[0, 1, (2), 3, (2), 5, 3]，找到重复的数字
     *
     * @param nums
     * @return
     */
    public int findRepeatNumber3(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] != nums[nums[i]]) {
                int temp = nums[nums[i]];
                nums[nums[i]] = nums[i];
                nums[i] = temp;
            }
            //当前元素值nums[i]不在索引位置i时，说明有重复元素
            if (i != nums[i]) {
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
