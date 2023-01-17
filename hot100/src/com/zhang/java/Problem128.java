package com.zhang.java;

import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2022/5/3 10:06
 * @Author zsy
 * @Description 最长连续序列 字节面试题
 * 给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
 * 请你设计并实现时间复杂度为 O(n) 的算法解决此问题。
 * <p>
 * 输入：nums = [100,4,200,1,3,2]
 * 输出：4
 * 解释：最长数字连续序列是 [1, 2, 3, 4]。它的长度为 4。
 * <p>
 * 输入：nums = [0,3,7,2,5,8,4,6,0,1]
 * 输出：9
 * <p>
 * 0 <= nums.length <= 10^5
 * -10^9 <= nums[i] <= 10^9
 */
public class Problem128 {
    public static void main(String[] args) {
        Problem128 problem128 = new Problem128();
        int[] nums = {0, 3, 7, 2, 5, 8, 4, 6, 0, 1};
        System.out.println(problem128.longestConsecutive(nums));
        System.out.println(problem128.longestConsecutive2(nums));
    }

    /**
     * 排序+计数
     * 时间复杂度O(nlogn)，空间复杂度O(logn)
     *
     * @param nums
     * @return
     */
    public int longestConsecutive(int[] nums) {
        if (nums.length <= 1) {
            return nums.length;
        }

        quickSort(nums, 0, nums.length - 1);

        int maxLen = 1;
        int tempLen = 1;

        for (int i = 0; i < nums.length - 1; i++) {
            //两数相等的情况
            if (nums[i] == nums[i + 1]) {
                continue;
            }

            if (nums[i] + 1 == nums[i + 1]) {
                tempLen++;
                maxLen = Math.max(maxLen, tempLen);
            } else {
                tempLen = 1;
            }
        }

        return maxLen;
    }


    /**
     * 哈希表
     * 将数组中所有元素加入哈希表，遍历数组判断nums[i]的前一个元素nums[i]-1是否在哈希表中：
     * 1、如果在，则可以等到遍历到nums[i]-1时，才找最长连续序列
     * 2、如果不在，则从nums[i]开始在哈希表中往后找，循环判断nums[i]+1是否在哈希表中，并更新最长连续序列
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int longestConsecutive2(int[] nums) {
        if (nums.length <= 1) {
            return nums.length;
        }

        Set<Integer> set = new HashSet<>();

        for (int num : nums) {
            set.add(num);
        }

        int maxLen = 0;

        for (int i = 0; i < nums.length; i++) {
            //nums[i]的前一个元素nums[i]-1不在哈希表中，则说明nums[i]为连续元素的第一个元素，从nums[i]开始往后找
            if (!set.contains(nums[i] - 1)) {
                int tempLen = 1;
                int tempNum = nums[i];

                while (set.contains(tempNum + 1)) {
                    tempLen++;
                    tempNum++;
                }

                maxLen = Math.max(maxLen, tempLen);
            }
        }

        return maxLen;
    }

    private void quickSort(int[] nums, int left, int right) {
        if (left < right) {
            int pivot = partition(nums, left, right);
            quickSort(nums, left, pivot - 1);
            quickSort(nums, pivot + 1, right);
        }
    }

    private int partition(int[] nums, int left, int right) {
        int temp = nums[left];

        while (left < right) {
            while (left < right && nums[right] >= temp) {
                right--;
            }
            nums[left] = nums[right];

            while (left < right && nums[left] <= temp) {
                left++;
            }
            nums[right] = nums[left];
        }

        nums[left] = temp;

        return left;
    }
}
