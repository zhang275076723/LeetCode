package com.zhang.java;

/**
 * @Date 2022/6/30 10:26
 * @Author zsy
 * @Description 寻找峰值 Google面试题 类比Problem33、Problem34、Problem35、Problem81、Problem153、Problem154、Problem852、Problem1095、Offer11、Offer53、Offer53_2、Interview_10_03、Interview_10_05
 * 峰值元素是指其值严格大于左右相邻值的元素。
 * 给你一个整数数组 nums，找到峰值元素并返回其索引。
 * 数组可能包含多个峰值，在这种情况下，返回 任何一个峰值 所在位置即可。
 * 你可以假设 nums[-1] = nums[n] = -∞ 。
 * 你必须实现时间复杂度为 O(log n) 的算法来解决此问题。
 * <p>
 * 输入：nums = [1,2,3,1]
 * 输出：2
 * 解释：3 是峰值元素，你的函数应该返回其索引 2。
 * <p>
 * 输入：nums = [1,2,1,3,5,6,4]
 * 输出：1 或 5
 * 解释：你的函数可以返回索引 1，其峰值元素为 2；或者返回索引 5， 其峰值元素为 6。
 * <p>
 * 1 <= nums.length <= 1000
 * -2^31 <= nums[i] <= 2^31 - 1
 * 对于所有有效的 i 都有 nums[i] != nums[i + 1]
 */
public class Problem162 {
    public static void main(String[] args) {
        Problem162 problem162 = new Problem162();
        int[] nums = {1, 2, 1, 3, 5, 6, 4};
        System.out.println(problem162.findPeakElement(nums));
        System.out.println(problem162.findPeakElement2(nums));
    }

    /**
     * 顺序遍历
     * 当前元素大于下一个元素时，即为峰值
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int findPeakElement(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        if (nums.length == 1) {
            return 0;
        }

        for (int i = 0; i < nums.length - 1; i++) {
            //当前元素大于下一个元素时，即为峰值
            if (nums[i] > nums[i + 1]) {
                return i;
            }
        }

        //未找到，则说明之前元素递增，峰值为最后一个元素
        return nums.length - 1;
    }

    /**
     * 二分查找变形
     * 当前元素大于下一个元素时，峰值在当前元素或当前元素左边；当前元素小于等于下一个元素时，峰值在当前元素右边
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int findPeakElement2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        if (nums.length == 1) {
            return 0;
        }

        int left = 0;
        int right = nums.length - 1;
        int mid;

        while (left < right) {
            mid = left + ((right - left) >> 1);

            //nums[mid]小于nums[mid+1]，峰值在nums[mid+1]-nums[right]
            if (nums[mid] < nums[mid + 1]) {
                left = mid + 1;
            } else {
                //nums[mid]大于nums[mid+1]，峰值在nums[left]-nums[mid]
                right = mid;
            }
        }

        return left;
    }
}
