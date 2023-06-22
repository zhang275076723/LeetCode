package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2022/9/14 8:37
 * @Author zsy
 * @Description 划分为k个相等的子集 网易机试题 划分子集类比类比Problem416 分割类比Problem659、Problem725 回溯+剪枝类比Problem17、Problem39、Problem40、Problem46、Problem47、Problem77、Problem78、Problem89、Problem90、Problem97、Problem216、Problem377、Problem491、Problem679、Offer17、Offer38
 * 给定一个整数数组  nums 和一个正整数 k，找出是否有可能把这个数组分成 k 个非空子集，其总和都相等。
 * <p>
 * 输入： nums = [4, 3, 2, 3, 5, 2, 1], k = 4
 * 输出： True
 * 说明： 有可能将其分成 4 个子集（5），（1,4），（2,3），（2,3）等于总和。
 * <p>
 * 输入: nums = [1,2,3,4], k = 3
 * 输出: false
 * <p>
 * 1 <= k <= len(nums) <= 16
 * 0 < nums[i] < 10000
 * 每个元素的频率在 [1,4] 范围内
 */
public class Problem698 {
    public static void main(String[] args) {
        Problem698 problem698 = new Problem698();
        //(1,4,9) (4,4,6) (2,2,4,6)
//        int[] nums = {4, 4, 4, 6, 1, 2, 2, 9, 4, 6};
//        int k = 3;
        int[] nums = {4, 3, 2, 3, 5, 2, 1};
        int k = 4;
        System.out.println(problem698.canPartitionKSubsets(nums, k));
    }

    /**
     * 回溯+剪枝，难点在于由大到小排序后剪枝
     * 数组中元素由大到小排序，判断当前元素能否放到其中一个桶中，如果可以，则继续判断下一个元素
     * 时间复杂度O(k^n)，空间复杂度O(k+n) (排序空间需要O(n)，递归栈深度O(k))
     *
     * @param nums
     * @param k
     * @return
     */
    public boolean canPartitionKSubsets(int[] nums, int k) {
        if (k == 1) {
            return true;
        }

        int sum = 0;

        for (int num : nums) {
            sum = sum + num;
        }

        //元素之和不能被k整除，则不能划分为k个子集，直接返回false
        if (sum % k != 0) {
            return false;
        }

        //由大到小排序，先将大的元素放入桶中
        mergeSort(nums, 0, nums.length - 1, new int[nums.length]);

        int target = sum / k;

        //元素都大于0，最大的元素大于target，则不存在k个相等的子集，返回false
        if (nums[0] > target) {
            return false;
        }

        //由大到小排序后从前往后遍历，先将大的元素放入桶中，便于回溯和剪枝
        return backtrack(0, new int[k], nums, target);
    }

    private boolean backtrack(int t, int[] bucket, int[] nums, int target) {
        //nums已经遍历完，此时bucket桶中每个元素都为target，存在k个相等的子集，返回true
        if (t == nums.length) {
            return true;
        }

        for (int i = 0; i < bucket.length; i++) {
            //当前桶bucket[i]加上nums[t]大于target，则当前桶无法加上nums[t]，剪枝，直接进行下次循环
            if (bucket[i] + nums[t] > target) {
                continue;
            }

            //当前桶bucket[i]和前一个桶bucket[i-1]相等，说明前一个桶已经考虑过nums[t]，则当前桶不需要再考虑nums[t]，
            //剪枝，直接进行下次循环
            if (i > 0 && bucket[i] == bucket[i - 1]) {
                continue;
            }

            bucket[i] = bucket[i] + nums[t];

            //已经找到k个相等的子集，则返回true
            if (backtrack(t + 1, bucket, nums, target)) {
                return true;
            }

            bucket[i] = bucket[i] - nums[t];
        }

        //遍历结束也没有找到k个相等的子集，则返回false
        return false;
    }

    /**
     * 由大到小排序
     *
     * @param arr
     * @param left
     * @param right
     * @param tempArr
     */
    private void mergeSort(int[] arr, int left, int right, int[] tempArr) {
        if (left >= right) {
            return;
        }

        int mid = left + ((right - left) >> 1);
        mergeSort(arr, left, mid, tempArr);
        mergeSort(arr, mid + 1, right, tempArr);
        merge(arr, left, mid, right, tempArr);
    }

    private void merge(int[] arr, int left, int mid, int right, int[] tempArr) {
        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (arr[i] > arr[j]) {
                tempArr[k] = arr[i];
                i++;
                k++;
            } else {
                tempArr[k] = arr[j];
                j++;
                k++;
            }
        }

        while (i <= mid) {
            tempArr[k] = arr[i];
            i++;
            k++;
        }

        while (j <= right) {
            tempArr[k] = arr[j];
            j++;
            k++;
        }

        for (k = left; k <= right; k++) {
            arr[k] = tempArr[k];
        }
    }
}
