package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2022/6/19 9:00
 * @Author zsy
 * @Description 合并两个有序数组 贝壳面试题
 * 给你两个按 非递减顺序 排列的整数数组 nums1 和 nums2，另有两个整数 m 和 n ，分别表示 nums1 和 nums2 中的元素数目。
 * 请你 合并 nums2 到 nums1 中，使合并后的数组同样按 非递减顺序 排列。
 * 注意：最终，合并后数组不应由函数返回，而是存储在数组 nums1 中。
 * 为了应对这种情况，nums1 的初始长度为 m + n，其中前 m 个元素表示应合并的元素，
 * 后 n 个元素为 0 ，应忽略。nums2 的长度为 n 。
 * <p>
 * 输入：nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
 * 输出：[1,2,2,3,5,6]
 * 解释：需要合并 [1,2,3] 和 [2,5,6] 。
 * 合并结果是 [1,2,2,3,5,6] ，其中斜体加粗标注的为 nums1 中的元素。
 * <p>
 * 输入：nums1 = [1], m = 1, nums2 = [], n = 0
 * 输出：[1]
 * 解释：需要合并 [1] 和 [] 。
 * 合并结果是 [1] 。
 * <p>
 * 输入：nums1 = [0], m = 0, nums2 = [1], n = 1
 * 输出：[1]
 * 解释：需要合并的数组是 [] 和 [1] 。
 * 合并结果是 [1] 。
 * 注意，因为 m = 0 ，所以 nums1 中没有元素。nums1 中仅存的 0 仅仅是为了确保合并结果可以顺利存放到 nums1 中。
 * <p>
 * nums1.length == m + n
 * nums2.length == n
 * 0 <= m, n <= 200
 * 1 <= m + n <= 200
 * -10^9 <= nums1[i], nums2[j] <= 10^9
 */
public class Problem88 {
    public static void main(String[] args) {
        Problem88 problem88 = new Problem88();
        int[] nums1 = {1, 2, 3, 0, 0, 0};
        int[] nums2 = {2, 5, 6};
        int m = 3;
        int n = 3;
//        problem88.merge(nums1, m, nums2, n);
        problem88.merge2(nums1, m, nums2, n);
        System.out.println(Arrays.toString(nums1));
    }

    /**
     * 双指针
     * 将两个数组中元素放到新数组中，再重新放回nums1
     * 时间复杂度O(m+n)，空间复杂度O(m+n)
     *
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        if (m == 0) {
            for (int i = 0; i < n; i++) {
                nums1[i] = nums2[i];
            }
            return;
        }

        if (n == 0) {
            return;
        }

        int[] result = new int[m + n];
        int index = 0;
        int left1 = 0;
        int left2 = 0;

        while (left1 < m && left2 < n) {
            if (nums1[left1] < nums2[left2]) {
                result[index] = nums1[left1];
                left1++;
            } else {
                result[index] = nums2[left2];
                left2++;
            }
            index++;
        }

        while (left1 < m) {
            result[index] = nums1[left1];
            left1++;
            index++;
        }
        while (left2 < n) {
            result[index] = nums2[left2];
            left2++;
            index++;
        }

        for (int i = 0; i < m + n; i++) {
            nums1[i] = result[i];
        }
    }

    /**
     * 逆向双指针
     * 从后往前遍历，选择最大的元素放到num1尾部
     * 时间复杂度O(m+n)，空间复杂度O(1)
     *
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public void merge2(int[] nums1, int m, int[] nums2, int n) {
        if (m == 0) {
            for (int i = 0; i < n; i++) {
                nums1[i] = nums2[i];
            }
            return;
        }

        if (n == 0) {
            return;
        }

        int index = m + n - 1;
        int right1 = m - 1;
        int right2 = n - 1;

        while (right1 >= 0 && right2 >= 0) {
            if (nums1[right1] > nums2[right2]) {
                nums1[index] = nums1[right1];
                right1--;
            } else {
                nums1[index] = nums2[right2];
                right2--;
            }
            index--;
        }

        if (right1 >= 0) {
            return;
        }

        while (right2 >= 0) {
            nums1[index] = nums2[right2];
            right2--;
            index--;
        }
    }
}
