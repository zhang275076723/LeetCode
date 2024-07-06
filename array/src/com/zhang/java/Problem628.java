package com.zhang.java;

import java.util.Random;

/**
 * @Date 2023/8/3 08:45
 * @Author zsy
 * @Description 三个数的最大乘积 类比Problem581 类比Problem747 类比Problem128、Problem506、Problem539、Problem561、Problem747
 * 给你一个整型数组 nums ，在数组中找出由三个数组成的最大乘积，并输出这个乘积。
 * <p>
 * 输入：nums = [1,2,3]
 * 输出：6
 * <p>
 * 输入：nums = [1,2,3,4]
 * 输出：24
 * <p>
 * 输入：nums = [-1,-2,-3]
 * 输出：-6
 * <p>
 * 3 <= nums.length <= 10^4
 * -1000 <= nums[i] <= 1000
 */
public class Problem628 {
    public static void main(String[] args) {
        Problem628 problem628 = new Problem628();
        int[] nums = {1, 2, 3, 4};
        System.out.println(problem628.maximumProduct(nums));
        System.out.println(problem628.maximumProduct2(nums));
    }

    /**
     * 排序
     * 最大乘积的三个数要么是最大的三个数相乘，要么是最小的两个数和最大的数相乘
     * 时间复杂度O(nlogn)，空间复杂度O(logn) (快排平均的空间复杂度O(logn))
     *
     * @param nums
     * @return
     */
    public int maximumProduct(int[] nums) {
        if (nums == null || nums.length < 3) {
            return -1;
        }

        quickSort(nums, 0, nums.length - 1);

        //最大乘积的三个数要么是最大的三个数相乘，要么是最小的两个数和最大的数相乘
        return Math.max(nums[nums.length - 1] * nums[nums.length - 2] * nums[nums.length - 3],
                nums[0] * nums[1] * nums[nums.length - 1]);
    }

    /**
     * 一次遍历
     * 最大乘积的三个数要么是最大的三个数相乘，要么是最小的两个数和最大的数相乘
     * 遍历过程中得到nums中最大的三个数和最小的两个数
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int maximumProduct2(int[] nums) {
        if (nums == null || nums.length < 3) {
            return -1;
        }

        //数组中最大的三个数，max1最大，max3第三大
        int max1 = Integer.MIN_VALUE;
        int max2 = Integer.MIN_VALUE;
        int max3 = Integer.MIN_VALUE;
        //数组中最小的两个数，min1最小，min2第二小
        int min1 = Integer.MAX_VALUE;
        int min2 = Integer.MAX_VALUE;

        for (int num : nums) {
            //num大于max1，更新max1、max2、max3
            if (num > max1) {
                max3 = max2;
                max2 = max1;
                max1 = num;
            } else if (num > max2) {
                //num大于max2，更新max2、max3
                max3 = max2;
                max2 = num;
            } else if (num > max3) {
                //num大于max3，更新max3
                max3 = num;
            }

            //num小于min1，更新min1、min2
            if (num < min1) {
                min2 = min1;
                min1 = num;
            } else if (num < min2) {
                //num小于min2，更新min2
                min2 = num;
            }
        }

        //最大乘积的三个数要么是最大的三个数相乘，要么是最小的两个数和最大的数相乘
        return Math.max(max1 * max2 * max3, min1 * min2 * max1);
    }

    private void quickSort(int[] nums, int left, int right) {
        if (left >= right) {
            return;
        }

        int pivot = partition(nums, left, right);
        quickSort(nums, left, pivot - 1);
        quickSort(nums, pivot + 1, right);
    }

    private int partition(int[] nums, int left, int right) {
        int randomIndex = new Random().nextInt(right - left + 1) + left;

        int value = nums[left];
        nums[left] = nums[randomIndex];
        nums[randomIndex] = value;

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
