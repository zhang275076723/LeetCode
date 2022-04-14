package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2022/3/18 18:01
 * @Author zsy
 * @Description 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，
 * 使得所有奇数在数组的前半部分，所有偶数在数组的后半部分
 * <p>
 * 输入：nums = [1,2,3,4]
 * 输出：[1,3,2,4]
 * 注：[3,1,2,4] 也是正确的答案之一。
 */
public class Offer21 {
    public static void main(String[] args) {
        Offer21 offer21 = new Offer21();
        int[] nums = {2,1,1,1};
        System.out.println(Arrays.toString(offer21.exchange(nums)));
    }

    /**
     * 快排的一次划分，双指针思想，时间复杂度O(n)，空间复杂的O(1)
     *
     * @param nums
     * @return
     */
    public int[] exchange(int[] nums) {
        int i = 0;
        int j = nums.length - 1;
        int temp;
        while (i < j) {
            while (i < j && nums[j] % 2 == 0) {
                j--;
            }
            while (i < j && nums[i] % 2 == 1) {
                i++;
            }
            temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
        return nums;
    }
}
