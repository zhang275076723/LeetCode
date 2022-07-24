package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2022/3/18 18:01
 * @Author zsy
 * @Description 调整数组顺序使奇数位于偶数前面
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
    }

    /**
     * 双指针，快排的一次划分
     * 时间复杂度O(n)，空间复杂的O(1)
     *
     * @param nums
     * @return
     */
    public int[] exchange(int[] nums) {
        if (nums.length == 0 || nums.length == 1) {
            return nums;
        }

        int left = 0;
        int right = nums.length - 1;
        int temp;

        while (left < right) {
            //从右往左找奇数
            while (left < right && nums[right] % 2 == 0) {
                right--;
            }
            //从左往右找偶数
            while (left < right && nums[left] % 2 == 1) {
                left++;
            }

            temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
        }

        return nums;
    }
}
