package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2022/5/25 10:01
 * @Author zsy
 * @Description 移动零
 * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
 * 请注意，必须在不复制数组的情况下原地对数组进行操作。
 * <p>
 * 输入: nums = [0,1,0,3,12]
 * 输出: [1,3,12,0,0]
 * <p>
 * 输入: nums = [0]
 * 输出: [0]
 * <p>
 * 1 <= nums.length <= 10^4
 * -2^31 <= nums[i] <= 2^31 - 1
 */
public class Problem283 {
    public static void main(String[] args) {
        Problem283 problem283 = new Problem283();
        int[] nums = {0, 1, 0, 3, 12};
        problem283.moveZeroes(nums);
        System.out.println(Arrays.toString(nums));
    }

    /**
     * 双指针，一个指针指向当前要插入元素的位置，另一个指针指向当前遍历的位置
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     */
    public void moveZeroes(int[] nums) {
        if (nums.length == 1) {
            return;
        }

        int left = 0;
        int right = 0;
        while (right < nums.length) {
            if (nums[right] != 0) {
                swap(nums, left, right);
                left++;
            }
            right++;
        }
    }

    public void swap(int[] nums, int left, int right) {
        int temp = nums[left];
        nums[left] = nums[right];
        nums[right] = temp;
    }
}
