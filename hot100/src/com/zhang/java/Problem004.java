package com.zhang.java;

/**
 * @Date 2022/4/12 11:15
 * @Author zsy
 * @Description 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。
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
public class Problem004 {
    public static void main(String[] args) {
        Problem004 problem004 = new Problem004();
        int[] num1 = {1, 2};
        int[] num2 = {3, 4};
        System.out.println(problem004.findMedianSortedArrays(num1, num2));
        System.out.println(problem004.findMedianSortedArrays2(num1, num2));
    }

    /**
     * 暴力，时间复杂度O(m+n)，空间复杂度O(1)
     * 两个指针分别指向nums1和nums2，找到两个数组的中位数
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1.length == 0) {
            return nums2.length % 2 == 1 ?
                    nums2[nums2.length / 2] :
                    (nums2[nums2.length / 2 - 1] + nums2[nums2.length / 2]) / 2.0;
        }
        if (nums2.length == 0) {
            return nums1.length % 2 == 1 ?
                    nums1[nums1.length / 2] :
                    (nums1[nums1.length / 2 - 1] + nums1[nums1.length / 2]) / 2.0;
        }

        //nums1指针
        int i = 0;
        //nums2指针
        int j = 0;
        //中位数的两个值
        int num1 = Integer.MIN_VALUE;
        int num2 = Integer.MIN_VALUE;
        int m = nums1.length;
        int n = nums2.length;

        //找到中位数的两个值
        for (int k = 0; k <= (m + n) / 2; k++) {
            num1 = num2;
            if (j >= n || (i < m && nums1[i] < nums2[j])) {
                num2 = nums1[i];
                i++;
            } else {
                num2 = nums2[j];
                j++;
            }
        }

        if ((m + n) % 2 == 1) {
            return num2;
        } else {
            return (num1 + num2) / 2.0;
        }
    }

    /**
     * 有序就要往二分查找考虑
     * 二分查找变形，时间复杂度O(log(m+n))，空间复杂度O(1)
     * 求第k小的数，比较nums1中第k/2个元素和nums2中第k/2个元素，
     * 把较小的值和它之前的元素去掉，因为这些数不可能是第k小的数，
     * 然后再求删除后的两个数组第(k-删除的元素个数)小的数，直至找到第k小的数
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArrays2(int[] nums1, int[] nums2) {
        if (nums1.length == 0) {
            return nums2.length % 2 == 1 ?
                    nums2[nums2.length / 2] :
                    (nums2[nums2.length / 2 - 1] + nums2[nums2.length / 2]) / 2.0;
        }
        if (nums2.length == 0) {
            return nums1.length % 2 == 1 ?
                    nums1[nums1.length / 2] :
                    (nums1[nums1.length / 2 - 1] + nums1[nums1.length / 2]) / 2.0;
        }

        int m = nums1.length;
        int n = nums2.length;
        //第k小元素
        int k = (m + n) / 2 + 1;
        if ((m + n) % 2 == 1) {
            return findMinK(nums1, nums2, k);
        } else {
            return (findMinK(nums1, nums2, k - 1) + findMinK(nums1, nums2, k)) / 2.0;
        }
    }

    public int findMinK(int[] nums1, int[] nums2, int k) {
        //nums1数组的起始索引指针
        int i = 0;
        //nums2数组的起始索引指针
        int j = 0;
        //nums1数组的起始索引指针i的下一个位置索引指针
        int nextI;
        //nums2数组的起始索引指针j的下一个位置索引指针
        int nextJ;
        //第k/2小元素
        int halfK;

        while (true) {
            //i、j指针超过数组长度时，直接返回另一个数组当前的第k个元素
            if (i >= nums1.length) {
                return nums2[j + k - 1];
            }
            if (j >= nums2.length) {
                return nums1[i + k - 1];
            }

            //当k为1时，直接返回两个数组当前的第1个元素的较小值
            if (k == 1) {
                return Math.min(nums1[i], nums2[j]);
            }

            halfK = k / 2;
            nextI = Math.min(i + halfK - 1, nums1.length - 1);
            nextJ = Math.min(j + halfK - 1, nums2.length - 1);

            if (nums1[nextI] < nums2[nextJ]) {
                //更新第k小元素
                k = k - (nextI - i + 1);
                i = nextI + 1;
            } else {
                //更新第k小元素
                k = k - (nextJ - j + 1);
                j = nextJ + 1;
            }
        }
    }
}
