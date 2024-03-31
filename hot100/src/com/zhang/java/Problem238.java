package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2022/5/19 15:38
 * @Author zsy
 * @Description 除自身以外数组的乘积 数组中的动态规划类比Problem53、Problem135、Problem152、Problem724、Problem768、Problem769、Problem845、Problem1749、Offer42、Offer66、FindLeftBiggerRightLessIndex 同Offer66
 * 给你一个整数数组 nums，返回 数组 answer ，其中 answer[i] 等于 nums 中除 nums[i] 之外其余各元素的乘积 。
 * 题目数据 保证 数组 nums之中任意元素的全部前缀元素和后缀的乘积都在 32 位 整数范围内。
 * 请不要使用除法，且在 O(n) 时间复杂度内完成此题。
 * <p>
 * 输入: nums = [1,2,3,4]
 * 输出: [24,12,8,6]
 * <p>
 * 输入: nums = [-1,1,0,-3,3]
 * 输出: [0,0,9,0,0]
 * <p>
 * 2 <= nums.length <= 10^5
 * -30 <= nums[i] <= 30
 * 保证 数组 nums之中任意元素的全部前缀元素和后缀的乘积都在  32 位 整数范围内
 */
public class Problem238 {
    public static void main(String[] args) {
        Problem238 problem238 = new Problem238();
        int[] nums = {1, 2, 3, 4};
        System.out.println(Arrays.toString(problem238.productExceptSelf(nums)));
        System.out.println(Arrays.toString(problem238.productExceptSelf2(nums)));
    }

    /**
     * 动态规划
     * left[i]：nums[0]-nums[i-1]所有元素乘积
     * right[i]：nums[i+1]-nums[nums.length-1]所有元素乘积
     * left[i] = left[i-1] * nums[i-1]
     * right[i] = right[i+1] * nums[i+1]
     * result[i] = left[i] * right[i]
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int[] productExceptSelf(int[] nums) {
        if (nums == null || nums.length < 2) {
            return nums;
        }

        int[] left = new int[nums.length];
        int[] right = new int[nums.length];
        //left和right数组初始化
        left[0] = 1;
        right[nums.length - 1] = 1;

        for (int i = 1; i < nums.length; i++) {
            left[i] = left[i - 1] * nums[i - 1];
            right[nums.length - 1 - i] = right[nums.length - i] * nums[nums.length - i];
        }

        int[] result = new int[nums.length];

        for (int i = 0; i < nums.length; i++) {
            result[i] = left[i] * right[i];
        }

        return result;
    }

    /**
     * 前缀积
     * 使用两个前缀值，一个表示从左边到当前位置的乘积，另一个表示从右边到当前位置的乘积
     * 结果数组是两个前缀数组的乘积
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int[] productExceptSelf2(int[] nums) {
        if (nums == null || nums.length < 2) {
            return nums;
        }

        int[] result = new int[nums.length];
        result[0] = 1;

        //左前缀乘积
        for (int i = 1; i < nums.length; i++) {
            result[i] = result[i - 1] * nums[i - 1];
        }

        int right = nums[nums.length - 1];

        //右前缀乘积
        for (int i = nums.length - 2; i >= 0; i--) {
            result[i] = result[i] * right;
            right = right * nums[i];
        }

        return result;
    }
}
