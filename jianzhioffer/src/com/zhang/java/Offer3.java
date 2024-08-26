package com.zhang.java;

import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2022/3/13 10:27
 * @Author zsy
 * @Description 数组中重复的数字 原地哈希类比Problem41、Problem268、Problem287、Problem442、Problem448、Problem645、Problem1528 二分查找类比
 * 在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。
 * 数组中某些数字是重复的，但不知道有几个数字重复了，也不知道每个数字重复了几次。
 * 请找出数组中任意一个重复的数字
 * <p>
 * 输入：[2, 3, 1, 0, 2, 5, 3]
 * 输出：2 或 3
 * <p>
 * 2 <= n <= 100000
 */
public class Offer3 {
    public static void main(String[] args) {
        Offer3 offer3 = new Offer3();
        int[] nums = new int[]{2, 3, 1, 0, 2, 5, 3};
        System.out.println(offer3.findRepeatNumber(nums));
        System.out.println(offer3.findRepeatNumber2(nums));
    }

    /**
     * 哈希表
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int findRepeatNumber(int[] nums) {
        Set<Integer> set = new HashSet<>(nums.length);

        for (int num : nums) {
            if (set.contains(num)) {
                return num;
            }
            set.add(num);
        }

        return -1;
    }

    /**
     * 原地哈希，原数组作为哈希表，下标索引i处放置的nums[i]等于i
     * 时间复杂度O(n)，空间复杂度O(1)
     * <p>
     * 例如：[(2), 3, (1), 0, 2, 5, 3]
     * 第一次交换：[(1), (3), 2, 0, 2, 5, 3]
     * 第二次交换：[(3), 1, 2, (0), 2, 5, 3]
     * 第三次交换：[0, 1, (2), 3, (2), 5, 3]，找到重复的数字
     *
     * @param nums
     * @return
     */
    public int findRepeatNumber2(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            //nums[i]和nums[nums[i]]不相等时，nums[i]和nums[nums[i]]进行交换
            while (nums[i] != nums[nums[i]]) {
                swap(nums, i, nums[i]);
            }

            //nums[i]不等于i+1，则nums[i]为重复的元素
            if (nums[i] != i) {
                return nums[i];
            }
        }

        return -1;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
