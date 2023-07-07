package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/3/24 17:20
 * @Author zsy
 * @Description 数组中出现次数超过一半的数字 类比Problem229 同Problem169
 * 数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。
 * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
 * <p>
 * 输入: [1, 2, 3, 2, 2, 2, 5, 4, 2]
 * 输出: 2
 * <p>
 * 1 <= 数组长度 <= 50000
 */
public class Offer39 {
    public static void main(String[] args) {
        Offer39 offer39 = new Offer39();
        int[] nums = {1, 2, 3, 2, 2, 2, 5, 4, 2};
        System.out.println(offer39.majorityElement(nums));
        System.out.println(offer39.majorityElement2(nums));
        System.out.println(offer39.majorityElement3(nums));
        System.out.println(offer39.majorityElement4(nums));
    }

    /**
     * 摩尔投票，我愿称之为同归于尽法
     * 取两个数，不同则抵消，占半数以上的数字必然留到最后
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int majorityElement(int[] nums) {
        //摩尔投票，统计result抵消之后出现的次数
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

    /**
     * 哈希表
     * 存储每个元素和元素出现的次数
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int majorityElement2(int[] nums) {
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
    public int majorityElement3(int[] nums) {
        while (true) {
            int index = (int) (Math.random() * nums.length);
            int count = 0;

            for (int num : nums) {
                if (num == nums[index]) {
                    count++;
                }

                if (count > nums.length / 2) {
                    return nums[index];
                }
            }
        }
    }

    /**
     * 快排，取中间值
     * 时间复杂度O(nlogn)，空间复杂度O(logn)
     *
     * @param nums
     * @return
     */
    public int majorityElement4(int[] nums) {
        quickSort(nums, 0, nums.length - 1);

        return nums[nums.length / 2];
    }

    public void quickSort(int[] nums, int left, int right) {
        if (left < right) {
            int pivot = partition(nums, left, right);
            quickSort(nums, left, pivot - 1);
            quickSort(nums, pivot + 1, right);
        }
    }

    public int partition(int[] nums, int left, int right) {
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
