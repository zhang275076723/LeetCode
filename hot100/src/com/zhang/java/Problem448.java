package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/6/9 20:49
 * @Author zsy
 * @Description 找到所有数组中消失的数字 原地哈希类比Problem41、Problem268、Problem287、Problem442、Problem645、Problem1528、Offer3
 * 给你一个含 n 个整数的数组 nums ，其中 nums[i] 在区间 [1, n] 内。
 * 请你找出所有在 [1, n] 范围内但没有出现在 nums 中的数字，并以数组的形式返回结果。
 * <p>
 * 输入：nums = [4,3,2,7,8,2,3,1]
 * 输出：[5,6]
 * <p>
 * 输入：nums = [1,1]
 * 输出：[2]
 * <p>
 * n == nums.length
 * 1 <= n <= 10^5
 * 1 <= nums[i] <= n
 */
public class Problem448 {
    public static void main(String[] args) {
        Problem448 problem448 = new Problem448();
        int[] nums = {4, 3, 2, 7, 8, 2, 3, 1};
        System.out.println(problem448.findDisappearedNumbers(nums));
    }

    /**
     * 原地哈希，原数组作为哈希表，下标索引i处放置的nums[i]等于i+1
     * 从原地哈希中找出nums[i]和i+1不相同的元素i+1，即为1-n中缺失的元素
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public List<Integer> findDisappearedNumbers(int[] nums) {
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
            //nums[i]和i+1不相等时，则i+1为1-n中缺失的元素
            if (nums[i] != i + 1) {
                list.add(i + 1);
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
