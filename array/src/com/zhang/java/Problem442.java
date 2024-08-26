package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/4/10 10:21
 * @Author zsy
 * @Description 数组中重复的数据 原地哈希类比Problem41、Problem268、Problem287、Problem448、Problem645、Problem1528、Offer3
 * 给你一个长度为 n 的整数数组 nums ，其中 nums 的所有整数都在范围 [1, n] 内，且每个整数出现 一次 或 两次 。
 * 请你找出所有出现 两次 的整数，并以数组形式返回。
 * 你必须设计并实现一个时间复杂度为 O(n) 且仅使用常量额外空间的算法解决此问题。
 * <p>
 * 输入：nums = [4,3,2,7,8,2,3,1]
 * 输出：[2,3]
 * <p>
 * 输入：nums = [1,1,2]
 * 输出：[1]
 * <p>
 * 输入：nums = [1]
 * 输出：[]
 * <p>
 * n == nums.length
 * 1 <= n <= 10^5
 * 1 <= nums[i] <= n
 * nums 中的每个元素出现 一次 或 两次
 */
public class Problem442 {
    public static void main(String[] args) {
        Problem442 problem442 = new Problem442();
        int[] nums = {4, 3, 2, 7, 8, 2, 3, 1};
//        System.out.println(problem442.findDuplicates(nums));
        System.out.println(problem442.findDuplicates2(nums));
    }

    /**
     * 原地哈希，原数组作为哈希表，下标索引i处放置的nums[i]等于i+1
     * 从原地哈希中找出nums[i]和i+1不相同的元素nums[i]，即为出现两次的元素
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public List<Integer> findDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        for (int i = 0; i < nums.length; i++) {
            //nums[i]和nums[nums[i]-1]不相等时，nums[i]和nums[nums[i]-1]进行交换
            while (nums[i] != nums[nums[i] - 1]) {
                swap(nums, i, nums[i] - 1);
            }
        }

        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            //nums[i]和i+1不相等时，则nums[i]为出现两次的元素，加入结果集合list中
            if (nums[i] != i + 1) {
                list.add(nums[i]);
            }
        }

        return list;
    }

    /**
     * 原地哈希+标志位
     * nums[abs(nums[i])-1]为正数，则数字abs(nums[i])第一次访问，设置nums[abs(nums[i])-1]为负数；
     * nums[abs(nums[i])-1]为负数，则数字abs(nums[i])之前已经访问过，是第二次访问，加入结果集合list中
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public List<Integer> findDuplicates2(int[] nums) {
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            //nums[abs(nums[i])-1]为正数，则数字abs(nums[i])第一次访问，设置nums[abs(nums[i])-1]为负数
            if (nums[Math.abs(nums[i]) - 1] > 0) {
                nums[Math.abs(nums[i]) - 1] = -nums[Math.abs(nums[i]) - 1];
            } else {
                //nums[abs(nums[i])-1]为负数，则数字abs(nums[i])之前已经访问过，是第二次访问，加入结果集合list中
                list.add(Math.abs(nums[i]));
            }
        }

        return list;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
