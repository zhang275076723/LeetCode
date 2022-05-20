package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2022/4/17 10:51
 * @Author zsy
 * @Description 下一个排列
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
     * 从后往前找到最长的逆序nums[i]-nums[nums.length-1]，按升序排序，
     * num[i-1]和nums[i]-nums[nums.length-1]中第一个比num[i-1]大的元素交换
     * 时间复杂度O(n)，空间复杂度O(1)
     */
    public void nextPermutation(int[] nums) {
        if (nums == null || nums.length == 0 || nums.length == 1) {
            return;
        }

        //逆序数组之前的一个元素
        int i;
        //逆序
        int j = nums.length - 1;

        while (j > 0 && nums[j - 1] >= nums[j]) {
            j--;
        }

        //当前数组为逆序，返回升序数组
        if (j == 0) {
            reverse(nums, 0, nums.length - 1);
            return;
        }

        i = j - 1;
        reverse(nums, j, nums.length - 1);
        for (int k = j; k < nums.length; k++) {
            //交换第一个比nums[i]大的元素
            if (nums[i] < nums[k]) {
                swap(nums, i, k);
                return;
            }
        }
    }

    /**
     * 反转数组
     *
     * @param nums
     * @param i
     * @param j
     */
    public void reverse(int[] nums, int i, int j) {
        while (i < j) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
            i++;
            j--;
        }
    }

    /**
     * 交换数组元素
     *
     * @param nums
     * @param i
     * @param j
     */
    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
