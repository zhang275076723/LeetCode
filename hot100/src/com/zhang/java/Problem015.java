package com.zhang.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Date 2022/4/14 15:17
 * @Author zsy
 * @Description 给你一个包含 n 个整数的数组 nums，
 * 判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有和为 0 且不重复的三元组。
 * 注意：答案中不可以包含重复的三元组。
 * <p>
 * 输入：nums = [-1,0,1,2,-1,-4]
 * 输出：[[-1,-1,2],[-1,0,1]]
 * <p>
 * 输入：nums = []
 * 输出：[]
 * <p>
 * 输入：nums = [0]
 * 输出：[]
 * <p>
 * 0 <= nums.length <= 3000
 * -10^5 <= nums[i] <= 10^5
 */
public class Problem015 {
    public static void main(String[] args) {
        Problem015 problem015 = new Problem015();
        int[] nums = {-1, 0, 1, 2, -1, -4};
        System.out.println(problem015.threeSum(nums));
    }

    /**
     * 暴力，时间复杂度O(n^3)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        if (nums == null || nums.length < 3) {
            return new ArrayList<>();
        }

        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < nums.length - 2; i++) {
            for (int j = i + 1; j < nums.length - 1; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        ArrayList<Integer> list = new ArrayList<>();
                        list.add(nums[i]);
                        list.add(nums[j]);
                        list.add(nums[k]);

                        //去重


                        result.add(list);
                    }
                }
            }
        }

        return result;
    }

    public void heapSort(int[] nums) {
        //建堆
        for (int i = nums.length / 2 - 1; i >= 0; i++) {
            heapify(nums, i, nums.length);
        }


    }

    public void heapify(int[] nums, int index, int heapSize) {
        int maxIndex = index;

        //比较左子节点
        if (heapSize > 2 * index + 1 && nums[2 * index + 1] > nums[maxIndex]) {
            maxIndex = 2 * index + 1;
        }
        //比较右子节点
        if (heapSize > 2 * index + 1 && nums[2 * index + 2] > nums[maxIndex]) {
            maxIndex = 2 * index + 2;
        }

        if (maxIndex != index) {
            int temp = nums[index];
            nums[index] = nums[maxIndex];
            nums[maxIndex] = temp;

            //继续向下整堆
            heapify(nums, maxIndex, heapSize);
        }
    }
}
