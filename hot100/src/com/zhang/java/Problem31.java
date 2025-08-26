package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2022/4/17 10:51
 * @Author zsy
 * @Description 下一个排列 字节面试题 类比Problem556、Problem670、Problem738、Problem1323、Problem1328、Problem1842、Problem1850、Problem2231
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
        int[] nums = {1, 3, 3, 2};
        problem31.nextPermutation(nums);
        System.out.println(Arrays.toString(nums));
    }

    /**
     * 模拟
     * 从后往前找最长递减数组nums[i]-nums[nums.length-1]，将当前最长递减数组翻转，成为单调递增数组，
     * 然后从前往后找nums[i]-nums[nums.length-1]中第一个大于num[i-1]的元素交换，得到下一个排列
     * 时间复杂度O(n)，空间复杂度O(1)
     */
    public void nextPermutation(int[] nums) {
        //最长递减数组的下标索引
        int i = nums.length - 1;

        //从后往前找最长递减数组
        //注意：最长递减数组包含相邻元素相等的情况，即为大于等于
        while (i > 0 && nums[i - 1] >= nums[i]) {
            i--;
        }

        //递减数组nums[i]-nums[nums.length-1]反转，变为递增数组
        reverse(nums, i, nums.length - 1);

        //nums整体为递减数组，则不存在比nums大的下一个排列，反转整个数组得到递增数组为最小排列，直接返回
        if (i == 0) {
            return;
        }

        int j = i - 1;

        //从前往后找第一个大于nums[j]的元素nums[i]，两者进行交换，得到下个一排列
        while (i < nums.length && nums[j] >= nums[i]) {
            i++;
        }

        swap(nums, i, j);
    }

    private void reverse(int[] nums, int i, int j) {
        while (i < j) {
            swap(nums, i, j);
            i++;
            j--;
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
