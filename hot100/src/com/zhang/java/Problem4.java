package com.zhang.java;

/**
 * @Date 2022/4/12 11:15
 * @Author zsy
 * @Description 寻找两个正序数组的中位数 字节面试题 类比Problem295、Problem480、Offer41 二分查找类比Problem287、Problem378、Problem410、Problem644、Problem658、Problem1201、Problem1482、Problem1723、Problem2305、Problem2498、CutWood、FindMaxArrayMinAfterKMinus 二分搜索树类比Problem230、Problem378、Problem440
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
     * 时间复杂度O(m+n)，空间复杂度O(1) (m=nums1.length, n=nums2.length)
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
            if (i < nums1.length && j < nums2.length) {
                if (nums1[i] < nums2[j]) {
                    num1 = num2;
                    num2 = nums1[i];
                    i++;
                } else {
                    num1 = num2;
                    num2 = nums2[j];
                    j++;
                }
            } else if (i < nums1.length) {
                num1 = num2;
                num2 = nums1[i];
                i++;
            } else {
                num1 = num2;
                num2 = nums2[j];
                j++;

            }
        }

        if ((nums1.length + nums2.length) % 2 == 0) {
            return (num1 + num2) / 2.0;
        } else {
            return num2;
        }
    }

    /**
     * 二分查找变形，看到有序数组，就要想到二分查找
     * 求第k小的数，则比较nums1中从起始位置开始第k/2个元素和nums2中从起始位置开始第k/2个元素，
     * 把较小的值和它之前的元素去掉，即去掉了较小的k/2个元素，因为这些数不可能是第k小的数，
     * 然后再求删除后的两个数组第(k-删除的元素个数)小的数，直至找到第k小的数
     * 时间复杂度O(log(m+n))，空间复杂度O(1) (m=nums1.length, n=nums2.length)
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

        //两个中位数中的前一个数
        int num1 = getMinKValue(nums1, nums2, (nums1.length + nums2.length) / 2);
        //两个中位数中的后一个数
        int num2 = getMinKValue(nums1, nums2, (nums1.length + nums2.length) / 2 + 1);

        if ((nums1.length + nums2.length) % 2 == 0) {
            return (num1 + num2) / 2.0;
        } else {
            return num2;
        }
    }

    /**
     * 获取有序数组nums1和nums2中的第k小元素的值 (k从1开始)
     * 时间复杂度O(log(m+n))，空间复杂度O(1) (m=nums1.length, n=nums2.length)
     *
     * @param nums1
     * @param nums2
     * @param k
     * @return
     */
    private int getMinKValue(int[] nums1, int[] nums2, int k) {
        //当前指向nums1的起始下标索引
        int i = 0;
        //当前指向nums2的起始下标索引
        int j = 0;
        //i下次跳到的下标索引的前一位，因为nums1[i]-nums1[nextI]和nums2[j]-nums2[nextJ]都是有序数组，
        //可以直接比较nums1[nextI]和nums2[nextJ]的大小，确定i指针下次跳到nextI+1,，或者j指针跳到nextJ+1
        int nextI = Math.min(nums1.length - 1, i + k / 2 - 1);
        //j下次跳到的下标索引的前一位，因为nums1[i]-nums1[nextI]和nums2[j]-nums2[nextJ]都是有序数组，
        //可以直接比较nums1[nextI]和nums2[nextJ]的大小，确定i指针下次跳到nextI+1,，或者j指针跳到nextJ+1
        int nextJ = Math.min(nums2.length - 1, j + k / 2 - 1);

        while (k != 1) {
            //nums1[nextI]小于nums2[nextJ]，说明nums1[i]-nums1[nextI]不是第k小元素，i指针后移
            if (nums1[nextI] < nums2[nextJ]) {
                k = k - (nextI - i + 1);
                i = nextI + 1;
            } else {
                //nums1[nextI]大于等于nums2[nextJ]，说明nums2[j]-nums2[nextJ]不是第k小元素，j指针后移
                k = k - (nextJ - j + 1);
                j = nextJ + 1;
            }

            //i已经遍历完，直接返回nums2中从j开始的第k个元素
            if (i == nums1.length) {
                return nums2[j + k - 1];
            }

            //j已经遍历完，直接返回nums1中从i开始的第k个元素
            if (j == nums2.length) {
                return nums1[i + k - 1];
            }

            //更新i、j的下一次要找的元素下标索引，每次往后找k/2个元素
            nextI = Math.min(nums1.length - 1, i + k / 2 - 1);
            nextJ = Math.min(nums2.length - 1, j + k / 2 - 1);
        }

        //第1小即为nums1[i]和nums2[j]中的较小值
        return Math.min(nums1[i], nums2[j]);
    }
}
