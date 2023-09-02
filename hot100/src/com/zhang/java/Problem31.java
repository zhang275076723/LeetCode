package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2022/4/17 10:51
 * @Author zsy
 * @Description 下一个排列 字节面试题 类比Problem556、Problem670、Problem738
 * 整数数组的一个 排列 就是将其所有成员以序列或线性顺序排列。
 * 例如，arr = [1,2,3] ，以下这些都可以视作 arr 的排列：[1,2,3]、[1,3,2]、[3,1,2]、[2,3,1] 。
 * 整数数组的 下一个排列 是指其整数的下一个字典序更大的排列。
 * 更正式地，如果数组的所有排列根据其字典顺序从小到大排列在一个容器中，
 * 那么数组的 下一个排列 就是在这个有序容器中排在它后面的那个排列。
 * 如果不存在下一个更大的排列，那么这个数组必须重排为字典序最小的排列（即，其元素按升序排列）。
 * 例如，arr = [1,2,3] 的下一个排列是 [1,3,2] 。
 * 类似地，arr = [2,3,1] 的下一个排列是 [3,1,2] 。
 * 而 arr = [3,2,1] 的下一个排列是 [1,2,3] ，因为 [3,2,1] 不存在一个字典序更大的排列。
 * 给你一个整数数组 nums ，找出 nums 的下一个排列。
 * 必须 原地 修改，只允许使用额外常数空间。
 * <p>
 * 输入：nums = [1,2,3]
 * 输出：[1,3,2]
 * <p>
 * 输入：nums = [3,2,1]
 * 输出：[1,2,3]
 * <p>
 * 输入：nums = [1,1,5]
 * 输出：[1,5,1]
 * <p>
 * 1 <= nums.length <= 100
 * 0 <= nums[i] <= 100
 */
public class Problem31 {
    public static void main(String[] args) {
        Problem31 problem31 = new Problem31();
        int[] nums = {2, 6, 3, 5, 4, 1};
        problem31.nextPermutation(nums);
        System.out.println(Arrays.toString(nums));
    }

    /**
     * 模拟
     * 从后往前找到最长递减序列nums[i]-nums[nums.length-1]，将最长递减序列翻转，成为单调递增序列
     * num[i-1]和nums[i]-nums[nums.length-1]中第一个比num[i-1]大的元素交换，得到下一个排列
     * 时间复杂度O(n)，空间复杂度O(1)
     */
    public void nextPermutation(int[] nums) {
        if (nums == null || nums.length == 0 || nums.length == 1) {
            return;
        }

        //逆序的第一个下标索引
        int i = nums.length - 1;

        //从后往前找最长递减序列，必须是大于等于，不能是大于，需要包含两者相同的情况
        while (i > 0 && nums[i - 1] >= nums[i]) {
            i--;
        }

        //整个数组为逆序，即为最大值，下一个排列是最小值，反转整个数组，得到下一个排列
        if (i == 0) {
            reverse(nums, 0, nums.length - 1);
            return;
        }

        //翻转从后往前的最长递减数组，使之成为递增数组
        reverse(nums, i, nums.length - 1);

        //从后往前的最长递减数组的前一个元素下标索引
        int j = i - 1;

        //从前往后找第一个比nums[j]大的元素进行交换，即得到下个一排列
        for (int k = i; k < nums.length; k++) {
            if (nums[j] < nums[k]) {
                swap(nums, j, k);
                return;
            }
        }
    }

    /**
     * 反转数组nums[i]-nums[j]
     *
     * @param nums
     * @param i
     * @param j
     */
    private void reverse(int[] nums, int i, int j) {
        while (i < j) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;

            i++;
            j--;
        }
    }

    /**
     * 交换数组元素nums[i]和nums[j]
     *
     * @param nums
     * @param i
     * @param j
     */
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
