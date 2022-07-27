package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2021/11/25 9:34
 * @Author zsy
 * @Description 有序数组的平方 类比Problem88、DifferentSquareCount
 * 给你一个按 非递减顺序 排序的整数数组 nums，返回 每个数字的平方 组成的新数组，要求也按 非递减顺序 排序。
 * <p>
 * 输入：nums = [-4,-1,0,3,10]
 * 输出：[0,1,9,16,100]
 * 解释：平方后，数组变为 [16,1,0,9,100]
 * 排序后，数组变为 [0,1,9,16,100]
 * <p>
 * 输入：nums = [-7,-3,2,3,11]
 * 输出：[4,9,9,49,121]
 * <p>
 * 1 <= nums.length <= 104
 * -104 <= nums[i] <= 104
 * nums 已按 非递减顺序 排序
 */
public class Problem977 {
    public static void main(String[] args) {
        Problem977 p = new Problem977();
        int[] nums = {-4, -1, 0, 3, 10};
        System.out.println(Arrays.toString(p.sortedSquares2(nums)));
    }

    /**
     * 暴力
     * 时间复杂度O(nlogn)，空间复杂度O(logn)
     *
     * @param nums
     * @return
     */
    public int[] sortedSquares(int[] nums) {
        int[] result = new int[nums.length];

        for (int i = 0; i < nums.length; i++) {
            result[i] = nums[i] * nums[i];
        }

        Arrays.sort(result);

        return result;
    }

    /**
     * 双指针
     * 每次从左右指针中找到最大平方数放到数组末尾
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int[] sortedSquares2(int[] nums) {
        int[] result = new int[nums.length];
        int i = 0;
        int j = nums.length - 1;
        int index = nums.length - 1;

        while (i <= j) {
            if (nums[i] * nums[i] < nums[j] * nums[j]) {
                result[index--] = nums[j] * nums[j];
                j--;
            } else {
                result[index--] = nums[i] * nums[i];
                i++;
            }
        }

        return result;
    }
}
