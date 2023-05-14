package com.zhang.java;


/**
 * @Date 2022/3/31 17:17
 * @Author zsy
 * @Description 数组中的逆序对 归并排序类比Problem23、Problem148、Problem315、Problem327、Problem493、CalculateSmallSum
 * 在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。
 * 输入一个数组，求出这个数组中的逆序对的总数。
 * <p>
 * 输入: [7,5,6,4]
 * 输出: 5
 * <p>
 * 0 <= 数组长度 <= 50000
 */
public class Offer51 {
    public static void main(String[] args) {
        Offer51 offer51 = new Offer51();
        int[] nums = {7, 5, 6, 4};
        System.out.println(offer51.reversePairs(nums));
        System.out.println(offer51.reversePairs2(nums));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int reversePairs(int[] nums) {
        if (nums.length < 2) {
            return 0;
        }

        int result = 0;

        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] > nums[j]) {
                    result++;
                }
            }
        }

        return result;
    }

    /**
     * 归并排序
     * 在合并时，如果左边数组的当前元素大于右边数组的当前元素，
     * 说明左边数组的所有元素都大于右边数组当前元素，即形成了逆序对
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

    public int mergeSort(int[] nums, int left, int right, int[] tempArr) {
        if (left < right) {
            int count = 0;
            int mid = left + ((right - left) >> 1);
            count = count + mergeSort(nums, left, mid, tempArr);
            count = count + mergeSort(nums, mid + 1, right, tempArr);
            count = count + merge(nums, left, mid, right, tempArr);
            return count;
        }

        return 0;
    }

    public int merge(int[] nums, int left, int mid, int right, int[] tempArr) {
        int count = 0;
        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            //注意是小于等于，不能是小于，因为要保证nums[i]>nums[j]的时候，nums[i]-nums[mid]都要大于nums[j]，才能构成逆序对
            if (nums[i] <= nums[j]) {
                tempArr[k] = nums[i];
                i++;
                k++;
            } else {
                tempArr[k] = nums[j];
                j++;
                k++;

                //nums[i]-nums[mid]都大于nums[j]，都构成了逆序对
                count = count + mid - i + 1;
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
