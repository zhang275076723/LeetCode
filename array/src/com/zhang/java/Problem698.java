package com.zhang.java;

/**
 * @Date 2022/9/14 8:37
 * @Author zsy
 * @Description 划分为k个相等的子集 网易机试题 类比Problem416
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
    /**
     * 回溯+剪枝中判断是否可以分割
     */
    private boolean flag = false;

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
     * 回溯+剪枝
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

        if (sum % k != 0) {
            return false;
        }

        //排序，便于分割等和子集和剪枝
        mergeSort(nums, 0, nums.length - 1, new int[nums.length]);

        int target = sum / k;

        //最大的元素大于target，则不存在等k个相等的子集，返回false
        if (nums[nums.length - 1] > target) {
            return false;
        }

        //从后往前遍历，先选择大的元素遍历回溯和剪枝
        backtrack(nums.length - 1, new int[k], nums, target);

        return flag;
    }

    private void backtrack(int t, int[] bucket, int[] nums, int target) {
        //nums已经遍历完，此时arr中每个元素都为target，存在k个相等的子集，返回true
        if (t < 0) {
            flag = true;
            return;
        }

        for (int i = 0; i < bucket.length; i++) {
            if (flag) {
                return;
            }

            //当前bucket[i]加上nums[t]正好等于target，或者bucket[i]加上nums[t]和nums[0]小于等于target，才遍历前一个nums[t-1]
            //bucket[i] + nums[t] + nums[0] <= target相当于剪枝，因为nums已经排序，加上最小的nums[0]超过target，则不存在合法分割
            if (bucket[i] + nums[t] == target || (t > 0 && bucket[i] + nums[t] + nums[0] <= target)) {
                bucket[i] = bucket[i] + nums[t];

                //当前情况可以找到k个相等的子集，返回true
                backtrack(t - 1, bucket, nums, target);

                bucket[i] = bucket[i] - nums[t];
            }
        }
    }

    private void mergeSort(int[] arr, int left, int right, int[] tempArr) {
        if (left < right) {
            int mid = left + ((right - left) >> 1);
            mergeSort(arr, left, mid, tempArr);
            mergeSort(arr, mid + 1, right, tempArr);
            merge(arr, left, mid, right, tempArr);
        }
    }

    private void merge(int[] arr, int left, int mid, int right, int[] tempArr) {
        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (arr[i] < arr[j]) {
                tempArr[k] = arr[i];
                i++;
            } else {
                tempArr[k] = arr[j];
                j++;
            }
            k++;
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
