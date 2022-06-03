package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2021/11/25 9:34
 * @Author zsy
 * @Description 给你一个按非递减顺序排序的整数数组nums，返回每个数字的平方组成的新数组，要求按非递减顺序排序
 * 输入：nums = [-4,-1,0,3,10]
 * 输出：[0,1,9,16,100]
 * 解释：平方后，数组变为 [16,1,0,9,100]
 * 排序后，数组变为 [0,1,9,16,100]
 * <p>
 * 输入：nums = [-7,-3,2,3,11]
 * 输出：[4,9,9,49,121]
 */
public class Problem977 {
    public static void main(String[] args) {
        Problem977 p = new Problem977();
        int[] nums = {-4, -1, 0, 3, 10};
        System.out.println(Arrays.toString(p.sortedSquares2(nums)));
    }

    /**
     * 暴力，先平方，在排序
     * 时间复杂度T(n) = O(nlogn)，空间复杂度S(n) = O(n)
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
     * 使用双指针，因为最大的平方在数组两端
     * 时间复杂度T(n) = O(n)，空间复杂度S(n) = O(n)
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
