package com.zhang.java;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @Date 2022/9/12 8:32
 * @Author zsy
 * @Description 拼接最大数 字节面试题 类比Problem42、Problem84、Problem316、Problem402、Problem739
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
     * 时间复杂度O(k*(m+n+k^2))，空间复杂度O(n) (m=nums1.length, n=nums2.length) (临界结果数组需要O(k)，单调栈需要O(n))
     *
     * @param nums1
     * @param nums2
     * @param k
     * @return
     */
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int[] result = new int[k];

        //保证nums1和nums2取的最大数子序列长度不会越界
        for (int i = Math.max(0, k - nums2.length); i <= Math.min(k, nums1.length); i++) {
            int[] tempNums1 = getMaxKNumber(nums1, i);
            int[] tempNums2 = getMaxKNumber(nums2, k - i);
            //tempNums1和tempNums2合并，得到最大数
            int[] tempResult = merge(tempNums1, tempNums2);

            //如果最大数小于当前最大数，更新最大数
            if (compare(result, 0, tempResult, 0) < 0) {
                result = tempResult;
            }
        }

        return result;
    }

    /**
     * 得到nums中长度为k的最大数子序列
     * 单调栈，单调递减栈保证元素单调递减，即保证得到长度为k的最大数子序列
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @param k
     * @return
     */
    private int[] getMaxKNumber(int[] nums, int k) {
        if (k == 0) {
            return new int[0];
        }

        if (k == nums.length) {
            return Arrays.copyOf(nums, nums.length);
        }

        //单调递减栈
        Deque<Integer> stack = new ArrayDeque<>();
        //需要移除的元素个数
        int count = nums.length - k;

        for (int i = 0; i < nums.length; i++) {
            while (!stack.isEmpty() && count > 0 && stack.peekLast() < nums[i]) {
                stack.pollLast();
                count--;
            }

            stack.offerLast(nums[i]);
        }

        //移除的元素个数小于count，则继续移除
        while (count > 0) {
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
     * 时间复杂度O((m+n)^2)，空间复杂度O(1) (m=nums1.length, n=nums2.length)
     *
     * @param nums1
     * @param nums2
     */
    private int[] merge(int[] nums1, int[] nums2) {
        if (nums1.length == 0) {
            return nums2;
        }

        if (nums2.length == 0) {
            return nums1;
        }

        int[] result = new int[nums1.length + nums2.length];
        int i = 0;
        int j = 0;
        int k = 0;

        while (i < nums1.length && j < nums2.length) {
            if (compare(nums1, i, nums2, j) >= 0) {
                result[k] = nums1[i];
                i++;
                k++;
            } else {
                result[k] = nums2[j];
                j++;
                k++;
            }
        }

        while (i < nums1.length) {
            result[k] = nums1[i];
            i++;
            k++;
        }

        while (j < nums2.length) {
            result[k] = nums2[j];
            j++;
            k++;
        }

        return result;
    }

    /**
     * 比较两个数组中的数从起始位置i和j开始的大小
     * nums1[i]-nums1[nums1.length-1]大于nums2[i]-nums2[nums2.length-1]，返回正数
     * nums1[i]-nums1[nums1.length-1]小于nums2[i]-nums2[nums2.length-1]，返回负数
     * 时间复杂度O(m+n)，空间复杂度O(m+n) (m=nums1.length, n=nums2.length)
     *
     * @param nums1
     * @param i
     * @param nums2
     * @param j
     * @return
     */
    private int compare(int[] nums1, int i, int[] nums2, int j) {
        while (i < nums1.length && j < nums2.length) {
            //当前元素不相等，直接返回较大值
            if (nums1[i] != nums2[j]) {
                return nums1[i] - nums2[j];
            }

            i++;
            j++;
        }

        //i到达nums1末尾，nums2还有剩余，nums2大
        if (i == nums1.length) {
            return -1;
        } else {
            //j到达nums2末尾，nums1还有剩余，nums1大
            return 1;
        }
    }
}
