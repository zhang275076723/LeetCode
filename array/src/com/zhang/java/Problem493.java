package com.zhang.java;

/**
 * @Date 2022/9/24 9:31
 * @Author zsy
 * @Description 翻转对 华为机试题 归并排序类比Problem23、Problem148、Problem315、Problem327、Offer51、CalculateSmallSum
 * 给定一个数组 nums ，如果 i < j 且 nums[i] > 2*nums[j] 我们就将 (i, j) 称作一个重要翻转对。
 * 你需要返回给定数组中的重要翻转对的数量。
 * <p>
 * 输入: [1,3,2,3,1]
 * 输出: 2
 * <p>
 * 输入: [2,4,3,5,1]
 * 输出: 3
 * <p>
 * 给定数组的长度不会超过50000。
 * 输入数组中的所有数字都在32位整数的表示范围内。
 */
public class Problem493 {
    public static void main(String[] args) {
        Problem493 problem493 = new Problem493();
        int[] nums = {1, 3, 2, 3, 1};
        System.out.println(problem493.reversePairs(nums));
        System.out.println(problem493.reversePairs2(nums));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int reversePairs(int[] nums) {
        int count = 0;

        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                //long避免int溢出
                if (nums[i] > (long) nums[j] * 2) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * 归并排序
     * 在合并时，如果左边数组的当前元素大于右边数组的当前元素2倍时，
     * 即左边数组的当前元素到左边数组最后一个元素都大于右边数组的当前元素2倍
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int reversePairs2(int[] nums) {
        if (nums.length < 2) {
            return 0;
        }

        return mergeSort(nums, 0, nums.length - 1, new int[nums.length]);
    }

    private int mergeSort(int[] nums, int left, int right, int[] tempArr) {
        if (left >= right) {
            return 0;
        }

        int count = 0;
        int mid = left + ((right - left) >> 1);

        count = count + mergeSort(nums, left, mid, tempArr);
        count = count + mergeSort(nums, mid + 1, right, tempArr);
        count = count + merge(nums, left, mid, right, tempArr);

        return count;
    }

    private int merge(int[] nums, int left, int mid, int right, int[] tempArr) {
        int count = 0;
        int i = left;
        int j = mid + 1;
        int k = left;

        //统计翻转对，在合并之前统计
        while (i <= mid && j <= right) {
            //nums[i]-nums[mid]都大于2倍nums[j]，构成翻转对
            //使用long，避免nums[j]*2超过int范围
            if (nums[i] > (long) nums[j] * 2) {
                count = count + mid - i + 1;
                j++;
            } else {
                i++;
            }
        }

        //i、j重新赋值，进行合并
        i = left;
        j = mid + 1;

        while (i <= mid && j <= right) {
            if (nums[i] <= nums[j]) {
                tempArr[k] = nums[i];
                i++;
                k++;
            } else {
                tempArr[k] = nums[j];
                j++;
                k++;
            }
        }

        while (i <= mid) {
            tempArr[k] = nums[i];
            i++;
            k++;
        }

        while (j <= right) {
            tempArr[k] = nums[j];
            j++;
            k++;
        }

        for (k = left; k <= right; k++) {
            nums[k] = tempArr[k];
        }

        return count;
    }
}
