package com.zhang.java;

import java.util.Random;

/**
 * @Date 2024/8/22 08:26
 * @Author zsy
 * @Description 至少是其他数字两倍的最大数 类比Problem628、Problem1131、Problem1330、Problem1509 类比Problem128、Problem506、Problem539、Problem561、Problem628、Problem1200、Problem1509、Problem2144
 * 给你一个整数数组 nums ，其中总是存在 唯一的 一个最大整数 。
 * 请你找出数组中的最大元素并检查它是否 至少是数组中每个其他数字的两倍 。
 * 如果是，则返回 最大元素的下标 ，否则返回 -1 。
 * <p>
 * 输入：nums = [3,6,1,0]
 * 输出：1
 * 解释：6 是最大的整数，对于数组中的其他整数，6 至少是数组中其他元素的两倍。6 的下标是 1 ，所以返回 1 。
 * <p>
 * 输入：nums = [1,2,3,4]
 * 输出：-1
 * 解释：4 没有超过 3 的两倍大，所以返回 -1 。
 * <p>
 * 2 <= nums.length <= 50
 * 0 <= nums[i] <= 100
 * nums 中的最大元素是唯一的
 */
public class Problem747 {
    public static void main(String[] args) {
        Problem747 problem747 = new Problem747();
        int[] nums = {3, 6, 1, 0};
        System.out.println(problem747.dominantIndex(nums));
        System.out.println(problem747.dominantIndex2(nums));
    }

    /**
     * 排序
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int dominantIndex(int[] nums) {
        int[][] arr = new int[nums.length][2];

        for (int i = 0; i < nums.length; i++) {
            arr[i] = new int[]{nums[i], i};
        }

        //按照arr[i][0]由小到大排序
        quickSort(arr, 0, arr.length - 1);

        if (arr[arr.length - 1][0] >= 2 * arr[arr.length - 2][0]) {
            return arr[arr.length - 1][1];
        } else {
            return -1;
        }
    }

    /**
     * 一次遍历
     * 遍历过程中得到nums中的最大元素和第二大元素
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int dominantIndex2(int[] nums) {
        //nums中最大值
        int max1 = Integer.MIN_VALUE;
        //nums中第二大的值
        int max2 = Integer.MIN_VALUE;
        //nums中最大值的下标索引
        int index = -1;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > max1) {
                max2 = max1;
                max1 = nums[i];
                index = i;
            } else if (nums[i] > max2) {
                max2 = nums[i];
            }
        }

        if (max1 >= 2 * max2) {
            return index;
        } else {
            return -1;
        }
    }

    private void quickSort(int[][] arr, int left, int right) {
        if (left >= right) {
            return;
        }

        int pivot = partition(arr, left, right);
        quickSort(arr, left, pivot - 1);
        quickSort(arr, pivot + 1, right);
    }

    private int partition(int[][] arr, int left, int right) {
        int randomIndex = new Random().nextInt(right - left + 1) + left;

        int[] value = arr[left];
        arr[left] = arr[randomIndex];
        arr[randomIndex] = value;

        int[] temp = arr[left];

        while (left < right) {
            while (left < right && arr[right][0] >= temp[0]) {
                right--;
            }

            arr[left] = arr[right];

            while (left < right && arr[left][0] <= temp[0]) {
                left++;
            }

            arr[right] = arr[left];
        }

        arr[left] = temp;

        return left;
    }
}
