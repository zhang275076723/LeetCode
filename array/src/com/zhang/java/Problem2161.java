package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2024/8/28 08:29
 * @Author zsy
 * @Description 根据给定数字划分数组 类比Problem905、Problem922、Problem2149、Problem2164、Offer21
 * 给你一个下标从 0 开始的整数数组 nums 和一个整数 pivot 。
 * 请你将 nums 重新排列，使得以下条件均成立：
 * 所有小于 pivot 的元素都出现在所有大于 pivot 的元素 之前 。
 * 所有等于 pivot 的元素都出现在小于和大于 pivot 的元素 中间 。
 * 小于 pivot 的元素之间和大于 pivot 的元素之间的 相对顺序 不发生改变。
 * 更正式的，考虑每一对 pi，pj ，pi 是初始时位置 i 元素的新位置，pj 是初始时位置 j 元素的新位置。
 * 对于小于 pivot 的元素，如果 i < j 且 nums[i] < pivot 和 nums[j] < pivot 都成立，那么 pi < pj 也成立。
 * 类似的，对于大于 pivot 的元素，如果 i < j 且 nums[i] > pivot 和 nums[j] > pivot 都成立，那么 pi < pj 。
 * 请你返回重新排列 nums 数组后的结果数组。
 * <p>
 * 输入：nums = [9,12,5,10,14,3,10], pivot = 10
 * 输出：[9,5,3,10,10,12,14]
 * 解释：
 * 元素 9 ，5 和 3 小于 pivot ，所以它们在数组的最左边。
 * 元素 12 和 14 大于 pivot ，所以它们在数组的最右边。
 * 小于 pivot 的元素的相对位置和大于 pivot 的元素的相对位置分别为 [9, 5, 3] 和 [12, 14] ，它们在结果数组中的相对顺序需要保留。
 * <p>
 * 输入：nums = [-3,4,3,2], pivot = 2
 * 输出：[-3,2,4,3]
 * 解释：
 * 元素 -3 小于 pivot ，所以在数组的最左边。
 * 元素 4 和 3 大于 pivot ，所以它们在数组的最右边。
 * 小于 pivot 的元素的相对位置和大于 pivot 的元素的相对位置分别为 [-3] 和 [4, 3] ，它们在结果数组中的相对顺序需要保留。
 * <p>
 * 1 <= nums.length <= 10^5
 * -10^6 <= nums[i] <= 10^6
 * pivot 等于 nums 中的一个元素。
 */
public class Problem2161 {
    public static void main(String[] args) {
        Problem2161 problem2161 = new Problem2161();
        int[] nums = {9, 12, 5, 10, 14, 3, 10};
        int pivot = 10;
        System.out.println(Arrays.toString(problem2161.pivotArray(nums, pivot)));
    }

    /**
     * 双指针
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @param pivot
     * @return
     */
    public int[] pivotArray(int[] nums, int pivot) {
        int[] result = new int[nums.length];
        //result中小于pivot的元素下标索引
        int left = 0;
        //result中大于pivot的元素下标索引
        int right = nums.length - 1;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < pivot) {
                result[left] = nums[i];
                left++;
            } else if (nums[i] > pivot) {
                result[right] = nums[i];
                right--;
            }
        }

        for (int i = left; i <= right; i++) {
            result[i] = pivot;
        }

        //反转result[right+1]-result[result.length-1]，保证大于pivot的元素在nums中的相对位置
        int l = right + 1;
        int r = result.length - 1;

        while (l < r) {
            int temp = result[l];
            result[l] = result[r];
            result[r] = temp;

            l++;
            r--;
        }

        return result;
    }
}
