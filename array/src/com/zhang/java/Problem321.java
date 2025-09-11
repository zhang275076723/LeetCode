package com.zhang.java;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * @Date 2022/9/12 8:32
 * @Author zsy
 * @Description 拼接最大数 字节面试题 单调栈类比Problem42、Problem84、Problem255、Problem316、Problem402、Problem456、Problem496、Problem503、Problem654、Problem739、Problem795、Problem907、Problem1019、Problem1856、Problem2104、Problem2454、Problem2487、Offer33、DoubleStackSort
 * 给定长度分别为 m 和 n 的两个数组，其元素由 0-9 构成，表示两个自然数各位上的数字。
 * 现在从这两个数组中选出 k (k <= m + n) 个数字拼接成一个新的数，要求从同一个数组中取出的数字保持其在原数组中的相对顺序。
 * 求满足该条件的最大数。结果返回一个表示该最大数的长度为 k 的数组。
 * 说明: 请尽可能地优化你算法的时间和空间复杂度。
 * <p>
 * 输入:
 * nums1 = [3, 4, 6, 5]
 * nums2 = [9, 1, 2, 5, 8, 3]
 * k = 5
 * 输出:
 * [9, 8, 6, 5, 3]
 * <p>
 * 输入:
 * nums1 = [6, 7]
 * nums2 = [6, 0, 4]
 * k = 5
 * 输出:
 * [6, 7, 6, 0, 4]
 * <p>
 * 输入:
 * nums1 = [3, 9]
 * nums2 = [8, 9]
 * k = 3
 * 输出:
 * [9, 8, 9]
 */
public class Problem321 {
    public static void main(String[] args) {
        Problem321 problem321 = new Problem321();
        int[] nums1 = {3, 4, 6, 5};
        int[] nums2 = {9, 1, 2, 5, 8, 3};
        int k = 5;
        System.out.println(Arrays.toString(problem321.maxNumber(nums1, nums2, k)));
    }

    /**
     * 单调栈
     * nums1中取出长度为x的最大数子序列，nums2中取出长度为k-x的最大数子序列，两个子序列合并，成为长度为k的最大数
     * 时间复杂度O(k*(m+n+k^2))，空间复杂度O(k) (m=nums1.length, n=nums2.length) (临时最大数数组需要O(k))
     * (nums1中取i个，nums2中取k-i个，则有k种情况，即O(k)，得到nums1和nums2最大数组需要O(m+n)，合并两个最大数组需要O(k^2))
     *
     * @param nums1
     * @param nums2
     * @param k
     * @return
     */
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int m = nums1.length;
        int n = nums2.length;
        int[] result = new int[0];

        //保证nums1和nums2取的最大数子序列长度不会越界
        for (int i = Math.max(0, k - n); i <= Math.min(k, m); i++) {
            //nums1取长度为i的最大值
            int[] temp1 = getMaxKArr(nums1, i);
            //nums2取长度为k-i的最大值
            int[] temp2 = getMaxKArr(nums2, k - i);
            //result1和result2合并，得到最大数
            int[] tempArr = merge(temp1, temp2);

            if (compare(tempArr, result, 0, 0) > 0) {
                result = tempArr;
            }
        }

        return result;
    }

    /**
     * 得到nums中长度为k的最大数子序列 (求当前元素之后，比当前元素大或小的元素，就要想到单调栈)
     * 单调栈，单调递减栈保证元素单调递减，即保证得到长度为k的最大数子序列
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @param k
     * @return
     */
    private int[] getMaxKArr(int[] nums, int k) {
        if (k == 0) {
            return new int[0];
        }

        if (k == nums.length) {
            return nums;
        }

        //单调递减栈
        Deque<Integer> stack = new ArrayDeque<>();
        //需要移除的元素个数
        int count = nums.length - k;

        for (int i = 0; i < nums.length; i++) {
            //栈不为空，要移除的元素个数count大于0，栈顶元素小于当前元素，则栈顶元素出栈，count减1
            while (!stack.isEmpty() && count > 0 && stack.peekLast() < nums[i]) {
                stack.pollLast();
                count--;
            }

            stack.offerLast(nums[i]);
        }

        //移除的元素个数小于count，则继续移除
        while (!stack.isEmpty() && count > 0) {
            stack.pollLast();
            count--;
        }

        int[] result = new int[k];

        for (int i = 0; i < k; i++) {
            result[i] = stack.pollFirst();
        }

        return result;
    }

    /**
     * 合并两个数组得到最大值数组，保证两个数组中元素在原数组中相对顺序不变
     * 时间复杂度O((m+n)*min(m,n))，空间复杂度O(1) (m=nums1.length, n=nums2.length)
     *
     * @param nums1
     * @param nums2
     */
    private int[] merge(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int[] result = new int[m + n];
        int i = 0;
        int j = 0;
        int k = 0;

        while (i < m && j < n) {
            //注意：不能直接比较nums1[i]和nums[j]的大小来拼接最大数组，还需要考虑nums1和nums2相等位之后元素的大小关系
            //例如：nums1=[3,0],nums2=[3,8]
            //第一位相等，如果先拼接nums1[i]，最终结果为[3,3,8,0]，如果先拼接nums2[j]，最终结果为[3,8,3,0]
            if (compare(nums1, nums2, i, j) >= 0) {
                result[k] = nums1[i];
                i++;
                k++;
            } else {
                result[k] = nums2[j];
                j++;
                k++;
            }
        }

        while (i <m) {
            result[k] = nums1[i];
            i++;
            k++;
        }

        while (j < n) {
            result[k] = nums2[j];
            j++;
            k++;
        }

        return result;
    }

    /**
     * 比较从nums1[i]起始的数和从nums2[j]起始的数的大小
     * nums1[i]和nums2[j]不相等时，返回两数之差，根据正负值判断nums1[i]和nums2[j]的大小关系，nums1[i]大，返回正数，nums2[j]大，返回负数
     * nums1[i]和nums2[j]相等时，继续往后判断
     * 时间复杂度O(min(m,n))，空间复杂度O(1) (m=nums1.length, n=nums2.length)
     *
     * @param nums1
     * @param nums2
     * @param i
     * @param j
     * @return
     */
    private int compare(int[] nums1, int[] nums2, int i, int j) {
        int m = nums1.length;
        int n = nums2.length;

        while (i < m && j < n) {
            //nums1[i]和nums2[j]不相等，直接返回两数之差
            if (nums1[i] != nums2[j]) {
                return nums1[i] - nums2[j];
            }

            i++;
            j++;
        }

        //从nums1[i]起始的数和从nums2[j]起始的数相等，返回0
        if (i == m && j == n) {
            return 0;
        } else if (i == m) {
            //从nums1[i]起始的数比从nums2[j]起始的数小，返回-1
            return -1;
        } else {
            //从nums1[i]起始的数比从nums2[j]起始的数大，返回1
            return 1;
        }
    }
}
