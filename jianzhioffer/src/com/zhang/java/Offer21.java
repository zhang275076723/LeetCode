package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2022/3/18 18:01
 * @Author zsy
 * @Description 调整数组顺序使奇数位于偶数前面 类比Problem905、Problem922、Problem2149、Problem2164 双指针类比
 * 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，
 * 使得所有奇数在数组的前半部分，所有偶数在数组的后半部分
 * <p>
 * 输入：nums = [1,2,3,4]
 * 输出：[1,3,2,4]
 * 注：[3,1,2,4] 也是正确的答案之一。
 * <p>
 * 0 <= nums.length <= 50000
 * 0 <= nums[i] <= 10000
 */
public class Offer21 {
    public static void main(String[] args) {
        Offer21 offer21 = new Offer21();
        int[] nums = {2, 1, 1, 1};
        System.out.println(Arrays.toString(offer21.exchange(nums)));
        System.out.println(Arrays.toString(offer21.exchange2(nums)));
    }

    /**
     * 双指针，新建数组
     * 遍历当前数组，如果是奇数放在新数组前面，如果是偶数，放在新数组后面
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int[] exchange(int[] nums) {
        if (nums.length == 0 || nums.length == 1) {
            return nums;
        }

        int[] result = new int[nums.length];
        int left = 0;
        int right = nums.length - 1;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] % 2 == 1) {
                result[left] = nums[i];
                left++;
            } else {
                result[right] = nums[i];
                right--;
            }
        }

        return result;
    }

    /**
     * 双指针，原数组交换，类比快排划分
     * 从前往后找第一个偶数，从后往前找第一个奇数，进行交换
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int[] exchange2(int[] nums) {
        if (nums.length == 0 || nums.length == 1) {
            return nums;
        }

        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            //从前往后找第一个偶数
            while (left < right && nums[left] % 2 == 1) {
                left++;
            }

            //从后往前找第一个奇数
            while (left < right && nums[right] % 2 == 0) {
                right--;
            }

            if (left < right) {
                //交换
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;

                left++;
                right--;
            }
        }

        return nums;
    }
}
