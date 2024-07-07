package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2024/8/24 08:48
 * @Author zsy
 * @Description 按奇偶排序数组 类比Problem922、Problem2164、Offer21 双指针类比
 * 给你一个整数数组 nums，将 nums 中的的所有偶数元素移动到数组的前面，后跟所有奇数元素。
 * 返回满足此条件的 任一数组 作为答案。
 * <p>
 * 输入：nums = [3,1,2,4]
 * 输出：[2,4,3,1]
 * 解释：[4,2,3,1]、[2,4,1,3] 和 [4,2,1,3] 也会被视作正确答案。
 * <p>
 * 输入：nums = [0]
 * 输出：[0]
 * <p>
 * 1 <= nums.length <= 5000
 * 0 <= nums[i] <= 5000
 */
public class Problem905 {
    public static void main(String[] args) {
        Problem905 problem905 = new Problem905();
        int[] nums = {3, 1, 2, 4};
        System.out.println(Arrays.toString(problem905.sortArrayByParity(nums)));
    }

    /**
     * 双指针
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int[] sortArrayByParity(int[] nums) {
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            //从前往后找第一个奇数
            while (left < right && nums[left] % 2 == 0) {
                left++;
            }

            //从后往前找第一个偶数
            while (left < right && nums[right] % 2 == 1) {
                right--;
            }

            if (left < right) {
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
