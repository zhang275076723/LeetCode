package com.zhang.java;

/**
 * @Date 2022/9/25 8:34
 * @Author zsy
 * @Description 区间和的个数 类比Problem315、Problem493、Offer51
 * 给你一个整数数组 nums 以及两个整数 lower 和 upper 。
 * 求数组中，值位于范围 [lower, upper] （包含 lower 和 upper）之内的 区间和的个数 。
 * 区间和 S(i, j) 表示在 nums 中，位置从 i 到 j 的元素之和，包含 i 和 j (i ≤ j)。
 * <p>
 * 输入：nums = [-2,5,-1], lower = -2, upper = 2
 * 输出：3
 * 解释：存在三个区间：[0,0]、[2,2] 和 [0,2] ，对应的区间和分别是：-2 、-1 、2 。
 * <p>
 * 输入：nums = [0], lower = 0, upper = 0
 * 输出：1
 * <p>
 * 1 <= nums.length <= 10^5
 * -2^31 <= nums[i] <= 231 - 1
 * -10^5 <= lower <= upper <= 10^5
 * 题目数据保证答案是一个 32 位 的整数
 */
public class Problem327 {
    /**
     * 归并排序求满足要求的区间和个数
     */
    private int count = 0;

    public static void main(String[] args) {
        Problem327 problem327 = new Problem327();
//        int[] nums = {-1, 1, 7, 2, -11, 7, 2, 1};
//        int lower = 0;
//        int upper = 4;
        int[] nums = {-2, 5, -1};
        int lower = -2;
        int upper = 2;
        System.out.println(problem327.countRangeSum(nums, lower, upper));
        System.out.println(problem327.countRangeSum2(nums, lower, upper));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param nums
     * @param lower
     * @param upper
     * @return
     */
    public int countRangeSum(int[] nums, int lower, int upper) {
        int count = 0;

        for (int i = 0; i < nums.length; i++) {
            //使用long，避免溢出
            long sum = 0;

            for (int j = i; j < nums.length; j++) {
                sum = sum + nums[j];

                if (sum >= lower && sum <= upper) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * 归并排序+前缀和
     * 对前缀和数组进行归并排序，
     * 在合并时，如果左边前缀和数组的当前元素preSum[i]，右边前缀和数组左指针元素preSum[l]，右指针元素preSum[r]，
     * 满足preSum[l]-preSum[i]>=lower && preSum[r]-preSum[i]<=upper，
     * 则[i,left]到[i,right]都满足区间和在[lower,upper]之内
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param nums
     * @param lower
     * @param upper
     * @return
     */
    public int countRangeSum2(int[] nums, int lower, int upper) {
        //前缀和数组使用long，避免溢出
        long[] preSum = new long[nums.length + 1];

        for (int i = 1; i < preSum.length; i++) {
            preSum[i] = preSum[i - 1] + nums[i - 1];
        }

        mergeSort(preSum, 0, preSum.length - 1, new long[preSum.length], lower, upper);

        return count;
    }

    private void mergeSort(long[] preSum, int left, int right, long[] tempArr, int lower, int upper) {
        if (left < right) {
            int mid = left + ((right - left) >> 1);
            mergeSort(preSum, left, mid, tempArr, lower, upper);
            mergeSort(preSum, mid + 1, right, tempArr, lower, upper);
            merge(preSum, left, mid, right, tempArr, lower, upper);
        }
    }

    private void merge(long[] preSum, int left, int mid, int right, long[] tempArr, int lower, int upper) {
        int i = left;
        int j = mid + 1;
        int k = left;
        int l = mid + 1;
        int r = mid + 1;

        //查找满足要求的区间，遍历左半部分数组，查找每个preSum[i]满足要求的preSum[l]和preSum[j]，
        //即[l,r)都满足区间和在[lower,upper]之间
        while (i <= mid) {
            //找右半部分的左区间l
            while (l <= right && preSum[l] - preSum[i] < lower) {
                l++;
            }

            //找右半部分的右区间r
            while (r <= right && preSum[r] - preSum[i] <= upper) {
                r++;
            }

            //统计preSum[i]满足要求的preSum[l]和preSum[j]，即[l,r)都满足区间和在[lower,upper]之间
            count = count + r - l;
            i++;
        }

        i = left;

        while (i <= mid && j <= right) {
            if (preSum[i] <= preSum[j]) {
                tempArr[k] = preSum[i];
                i++;
                k++;
            } else {
                tempArr[k] = preSum[j];
                j++;
                k++;
            }
        }

        while (i <= mid) {
            tempArr[k] = preSum[i];
            i++;
            k++;
        }

        while (j <= right) {
            tempArr[k] = preSum[j];
            j++;
            k++;
        }

        for (k = left; k <= right; k++) {
            preSum[k] = tempArr[k];
        }
    }
}
