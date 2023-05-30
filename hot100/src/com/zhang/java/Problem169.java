package com.zhang.java;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Date 2022/5/11 9:29
 * @Author zsy
 * @Description 多数元素 同Offer39
 * 给定一个大小为 n 的数组nums ，返回其中的多数元素。
 * 多数元素是指在数组中出现次数 大于⌊n/2⌋的元素。
 * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
 * <p>
 * 输入：nums = [3,2,3]
 * 输出：3
 * <p>
 * 输入：nums = [2,2,1,1,1,2,2]
 * 输出：2
 * <p>
 * n == nums.length
 * 1 <= n <= 5 * 10^4
 * -10^9 <= nums[i] <= 10^9
 */
public class Problem169 {
    public static void main(String[] args) {
        Problem169 problem169 = new Problem169();
        int[] nums = {2, 2, 1, 1, 1, 2, 2};
        System.out.println(problem169.majorityElement(nums));
        System.out.println(problem169.majorityElement2(nums));
        System.out.println(problem169.majorityElement3(nums));
        System.out.println(problem169.majorityElement4(nums));
    }

    /**
     * 哈希表
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int majorityElement(int[] nums) {
        if (nums == null || nums.length == 0) {
            return Integer.MIN_VALUE;
        }

        Map<Integer, Integer> map = new HashMap<>();

        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
            if (map.get(num) > nums.length / 2) {
                return num;
            }
        }

        return Integer.MIN_VALUE;
    }

    /**
     * 随机取值
     * 期望时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int majorityElement2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return Integer.MIN_VALUE;
        }

        while (true) {
            int index = new Random().nextInt(nums.length);
            int count = 0;

            for (int num : nums) {
                if (num == nums[index]) {
                    count++;
                }

                if (count > nums.length / 2) {
                    return num;
                }
            }
        }
    }

    /**
     * 对数组排序，取中间值
     * 时间复杂度O(nlogn)，空间复杂度O(logn)
     *
     * @param nums
     * @return
     */
    public int majorityElement3(int[] nums) {
        if (nums == null || nums.length == 0) {
            return Integer.MIN_VALUE;
        }

        quickSort(nums, 0, nums.length - 1);

        return nums[nums.length / 2];
    }

    /**
     * 摩尔投票，我愿称之为同归于尽法
     * 取两个数，不同则抵消，占半数以上的数字必然留到最后
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int majorityElement4(int[] nums) {
        if (nums == null || nums.length == 0) {
            return Integer.MIN_VALUE;
        }

        //摩尔投票
        int vote = 0;
        int result = -1;

        for (int num : nums) {
            if (vote == 0) {
                result = num;
            }

            if (result == num) {
                vote++;
            } else {
                vote--;
            }
        }

        return result;
    }

    private void quickSort(int[] nums, int left, int right) {
        if (left < right) {
            int pivot = partition(nums, left, right);
            quickSort(nums, left, pivot - 1);
            quickSort(nums, pivot + 1, right);
        }
    }

    /**
     * 快排划分的另一种形式
     *
     * @param nums
     * @param left
     * @param right
     * @return
     */
    private int partition(int[] nums, int left, int right) {
        int temp = nums[left];
        int i = left;

        for (int j = left + 1; j <= right; j++) {
            if (nums[j] <= temp) {
                i++;
                //交换nums[i]和nums[j]
                int t = nums[i];
                nums[i] = nums[j];
                nums[j] = t;
            }
        }

        //交换nums[left]和nums[i]
        int t = nums[left];
        nums[left] = nums[i];
        nums[i] = t;

        return i;
    }
}