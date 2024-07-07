package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2024/8/26 09:17
 * @Author zsy
 * @Description 按奇偶排序数组 II 类比Problem905、Problem2164、Offer21
 * 给定一个非负整数数组 nums，  nums 中一半整数是 奇数 ，一半整数是 偶数 。
 * 对数组进行排序，以便当 nums[i] 为奇数时，i 也是 奇数 ；当 nums[i] 为偶数时， i 也是 偶数 。
 * 你可以返回 任何满足上述条件的数组作为答案 。
 * <p>
 * 输入：nums = [4,2,5,7]
 * 输出：[4,5,2,7]
 * 解释：[4,7,2,5]，[2,5,4,7]，[2,7,4,5] 也会被接受。
 * <p>
 * 输入：nums = [2,3]
 * 输出：[2,3]
 * <p>
 * 2 <= nums.length <= 2 * 10^4
 * nums.length 是偶数
 * nums 中一半是偶数
 * 0 <= nums[i] <= 1000
 */
public class Problem922 {
    public static void main(String[] args) {
        Problem922 problem922 = new Problem922();
        int[] nums = {4, 2, 5, 7};
        System.out.println(Arrays.toString(problem922.sortArrayByParityII(nums)));
    }

    /**
     * 双指针
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int[] sortArrayByParityII(int[] nums) {
        //nums中偶数下标索引元素不为偶数的指针
        int left = 0;
        //nums中奇数下标索引元素不为奇数的指针
        int right = 1;

        while (left < nums.length && right < nums.length) {
            //找偶数下标索引元素不为偶数的下标索引
            while (left < nums.length && nums[left] % 2 == 0) {
                left = left + 2;
            }

            //找奇数下标索引元素不为奇数的下标索引
            while (right < nums.length && nums[right] % 2 == 1) {
                right = right + 2;
            }

            if (left < nums.length && right < nums.length) {
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;

                left = left + 2;
                right = right + 2;
            }
        }

        return nums;
    }
}
