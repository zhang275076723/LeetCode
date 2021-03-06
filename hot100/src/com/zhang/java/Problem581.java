package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2022/6/14 8:50
 * @Author zsy
 * @Description 最短无序连续子数组
 * 给你一个整数数组 nums ，你需要找出一个 连续子数组 ，如果对这个子数组进行升序排序，那么整个数组都会变为升序排序。
 * 请你找出符合题意的 最短 子数组，并输出它的长度。
 * <p>
 * 输入：nums = [2,6,4,8,10,9,15]
 * 输出：5
 * 解释：你只需要对 [6, 4, 8, 10, 9] 进行升序排序，那么整个表都会变为升序排序。
 * <p>
 * 输入：nums = [1,2,3,4]
 * 输出：0
 * <p>
 * 输入：nums = [1]
 * 输出：0
 * <p>
 * 1 <= nums.length <= 10^4
 * -10^5 <= nums[i] <= 10^5
 */
public class Problem581 {
    public static void main(String[] args) {
        Problem581 problem581 = new Problem581();
        int[] nums = {2, 6, 4, 8, 10, 9, 15};
//        System.out.println(problem581.findUnsortedSubarray(nums));
        System.out.println(problem581.findUnsortedSubarray2(nums));
    }

    /**
     * 先排序，再比较
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int findUnsortedSubarray(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return 0;
        }

        int[] newNums = Arrays.copyOf(nums, nums.length);
//        mergeSort(nums, 0, nums.length - 1, new int[nums.length]);
        heapSort(nums);

        int left = 0;
        int right = nums.length - 1;

        while (left <= right && nums[left] == newNums[left]) {
            left++;
        }
        while (left <= right && nums[right] == newNums[right]) {
            right--;
        }

        return right - left + 1;
    }

    /**
     * 将数组分为三部分，左边部分和右边部分都是升序，中间部分的最小值大于左边部分的最大值，最大值小于右边部分的最小值
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int findUnsortedSubarray2(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return 0;
        }

        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        int left = -1;
        int right = -1;

        for (int i = 0; i < nums.length; i++) {
            //从左往右遍历，找中间部分的右边界
            if (nums[i] < max) {
                right = i;
            }
            max = Math.max(max, nums[i]);

            //从右往左遍历，找中间部分的左边界
            if (nums[nums.length - i - 1] > min) {
                left = nums.length - i - 1;
            }
            min = Math.min(min, nums[nums.length - i - 1]);
        }

        return left == right ? 0 : right - left + 1;
    }

    private void heapSort(int[] nums) {
        for (int i = nums.length / 2 - 1; i >= 0; i--) {
            heapify(nums, i, nums.length);
        }

        for (int i = nums.length - 1; i > 0; i--) {
            int temp = nums[i];
            nums[i] = nums[0];
            nums[0] = temp;
            heapify(nums, 0, i);
        }
    }

    private void heapify(int[] nums, int i, int heapSize) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int max = i;

        if (left < heapSize && nums[max] < nums[left]) {
            max = left;
        }
        if (right < heapSize && nums[max] < nums[right]) {
            max = right;
        }

        if (max != i) {
            int temp = nums[i];
            nums[i] = nums[max];
            nums[max] = temp;
            heapify(nums, max, heapSize);
        }
    }

    private void mergeSort(int[] nums, int left, int right, int[] tempArr) {
        if (left < right) {
            int mid = left + ((right - left) >> 1);
            mergeSort(nums, left, mid, tempArr);
            mergeSort(nums, mid + 1, right, tempArr);
            merge(nums, left, mid, right, tempArr);
        }
    }

    private void merge(int[] nums, int left, int mid, int right, int[] tempArr) {
        int i = left;
        int j = mid + 1;
        int index = i;

        while (i <= mid && j <= right) {
            if (nums[i] < nums[j]) {
                tempArr[index] = nums[i];
                i++;
            } else {
                tempArr[index] = nums[j];
                j++;
            }
            index++;
        }

        while (i <= mid) {
            tempArr[index] = nums[i];
            i++;
            index++;
        }
        while (j <= right) {
            tempArr[index] = nums[j];
            j++;
            index++;
        }

        for (int k = left; k <= right; k++) {
            nums[k] = tempArr[k];
        }
    }
}
