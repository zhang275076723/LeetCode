package com.zhang.java;

/**
 * @Date 2022/4/12 11:15
 * @Author zsy
 * @Description 寻找两个正序数组的中位数 字节面试题
 * 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。
 * 请你找出并返回这两个正序数组的 中位数 。
 * 算法的时间复杂度应该为 O(log (m+n)) 。
 * <p>
 * 输入：nums1 = [1,3], nums2 = [2]
 * 输出：2.00000
 * 解释：合并数组 = [1,2,3] ，中位数 2
 * <p>
 * 输入：nums1 = [1,2], nums2 = [3,4]
 * 输出：2.50000
 * 解释：合并数组 = [1,2,3,4] ，中位数 (2 + 3) / 2 = 2.5
 * <p>
 * nums1.length == m
 * nums2.length == n
 * 0 <= m <= 1000
 * 0 <= n <= 1000
 * 1 <= m + n <= 2000
 * -10^6 <= nums1[i], nums2[i] <= 10^6
 */
public class Problem4 {
    public static void main(String[] args) {
        Problem4 problem4 = new Problem4();
        int[] num1 = {1, 2};
        int[] num2 = {3, 4};
        System.out.println(problem4.findMedianSortedArrays(num1, num2));
        System.out.println(problem4.findMedianSortedArrays2(num1, num2));
    }

    /**
     * 暴力
     * 两个指针分别指向nums1和nums2，找到两个数组的中位数
     * 时间复杂度O(m+n)，空间复杂度O(1)
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1.length == 0) {
            if (nums2.length % 2 == 1) {
                return nums2[nums2.length / 2];
            } else {
                return (nums2[nums2.length / 2 - 1] + nums2[nums2.length / 2]) / 2.0;
            }
        }

        if (nums2.length == 0) {
            if (nums1.length % 2 == 1) {
                return nums1[nums1.length / 2];
            } else {
                return (nums1[nums1.length / 2 - 1] + nums1[nums1.length / 2]) / 2.0;
            }
        }

        //nums1指针
        int i = 0;
        //nums2指针
        int j = 0;
        //两个中位数中的前一个数
        int num1 = Integer.MIN_VALUE;
        //两个中位数中的后一个数
        int num2 = Integer.MIN_VALUE;

        //找到中位数的两个值
        for (int k = 0; k <= (nums1.length + nums2.length) / 2; k++) {
            num1 = num2;

            if ((i < nums1.length && j < nums2.length && nums1[i] < nums2[j]) || j >= nums2.length) {
                num2 = nums1[i];
                i++;
            } else {
                num2 = nums2[j];
                j++;
            }
        }

        if ((nums1.length + nums2.length) % 2 == 1) {
            return num2;
        } else {
            return (num1 + num2) / 2.0;
        }
    }

    /**
     * 二分查找变形，看到有序数组，就要想到二分查找
     * 求第k小的数，则比较nums1中第k/2个元素和nums2中第k/2个元素，
     * 把较小的值和它之前的元素去掉，即去掉了较小的k/2个元素，因为这些数不可能是第k小的数，
     * 然后再求删除后的两个数组第(k-删除的元素个数)小的数，直至找到第k小的数
     * 时间复杂度O(log(m+n))，空间复杂度O(1)
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArrays2(int[] nums1, int[] nums2) {
        if (nums1.length == 0) {
            if (nums2.length % 2 == 1) {
                return nums2[nums2.length / 2];
            } else {
                return (nums2[nums2.length / 2 - 1] + nums2[nums2.length / 2]) / 2.0;
            }
        }

        if (nums2.length == 0) {
            if (nums1.length % 2 == 1) {
                return nums1[nums1.length / 2];
            } else {
                return (nums1[nums1.length / 2 - 1] + nums1[nums1.length / 2]) / 2.0;
            }
        }

        //第k小元素
        int k = (nums1.length + nums2.length) / 2 + 1;

        if ((nums1.length + nums2.length) % 2 == 1) {
            return findMinK(nums1, nums2, k);
        } else {
            return (findMinK(nums1, nums2, k - 1) + findMinK(nums1, nums2, k)) / 2.0;
        }
    }

    private int findMinK(int[] nums1, int[] nums2, int k) {
        //当前指向nums1的起始下标索引
        int i = 0;
        //当前指向nums2的起始下标索引
        int j = 0;
        //i的下一个下标索引
        int nextI;
        //j的下一个下标索引
        int nextJ;

        while (true) {
            //i、j指针超过数组长度时，直接返回另一个数组当前的第k个元素
            if (i == nums1.length) {
                return nums2[j + k - 1];
            }

            if (j == nums2.length) {
                return nums1[i + k - 1];
            }

            //当k为1时，直接返回两个数组当前的第1个元素的较小值
            if (k == 1) {
                return Math.min(nums1[i], nums2[j]);
            }

            //每次找当前数组的之后k/2个元素
            nextI = Math.min(i + k / 2 - 1, nums1.length - 1);
            nextJ = Math.min(j + k / 2 - 1, nums2.length - 1);

            if (nums1[nextI] < nums2[nextJ]) {
                //更新k、i
                k = k - (nextI - i + 1);
                i = nextI + 1;
            } else {
                //更新k、j
                k = k - (nextJ - j + 1);
                j = nextJ + 1;
            }
        }
    }
}
