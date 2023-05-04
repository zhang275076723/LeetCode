package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2023/3/26 08:48
 * @Author zsy
 * @Description 轮转数组 旋转问题类比Problem61、Problem186、Problem459、Problem686、Problem796、Offer58_2
 * 给定一个整数数组 nums，将数组中的元素向右轮转 k 个位置，其中 k 是非负数。
 * <p>
 * 输入: nums = [1,2,3,4,5,6,7], k = 3
 * 输出: [5,6,7,1,2,3,4]
 * 解释:
 * 向右轮转 1 步: [7,1,2,3,4,5,6]
 * 向右轮转 2 步: [6,7,1,2,3,4,5]
 * 向右轮转 3 步: [5,6,7,1,2,3,4]
 * <p>
 * 输入：nums = [-1,-100,3,99], k = 2
 * 输出：[3,99,-1,-100]
 * 解释:
 * 向右轮转 1 步: [99,-1,-100,3]
 * 向右轮转 2 步: [3,99,-1,-100]
 * <p>
 * 1 <= nums.length <= 10^5
 * -2^31 <= nums[i] <= 2^31 - 1
 * 0 <= k <= 10^5
 */
public class Problem189 {
    public static void main(String[] args) {
        Problem189 problem189 = new Problem189();
        int[] nums = {1, 2, 3, 4, 5, 6, 7};
        int k = 3;
        problem189.rotate(nums, k);
        System.out.println(Arrays.toString(nums));
    }

    /**
     * 三次反转
     * [0,nums.length-k-1]和[nums.length-k,nums.length-1]进行反转，最后数组nums整体进行反转
     * 注意：k取模，保证k在数组nums范围之内
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @param k
     */
    public void rotate(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k == 0) {
            return;
        }

        //k取模，保证k在数组nums范围之内
        k = k % nums.length;

        if (k == 0) {
            return;
        }

        //[0,nums.length-k-1]进行反转
        reverse(nums, 0, nums.length - k - 1);
        //[nums.length-k,nums.length-1]进行反转
        reverse(nums, nums.length - k, nums.length - 1);
        //nums数组整体进行反转
        reverse(nums, 0, nums.length - 1);
    }

    private void reverse(int[] nums, int left, int right) {
        while (left < right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;

            left++;
            right--;
        }
    }
}
