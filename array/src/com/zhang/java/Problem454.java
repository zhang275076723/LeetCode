package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/8/29 11:09
 * @Author zsy
 * @Description 四数相加 II 类比Problem1、Problem15、Problem16、Problem18、Problem167、Problem170、Problem653、Offer57 哈希表类比Problem1、Problem187、Problem205、Problem242、Problem290、Problem291、Problem383、Problem387、Problem389、Problem554、Problem763、Problem1640、Offer50
 * 给你四个整数数组 nums1、nums2、nums3 和 nums4 ，数组长度都是 n ，
 * 请你计算有多少个元组 (i, j, k, l) 能满足：
 * 0 <= i, j, k, l < n
 * nums1[i] + nums2[j] + nums3[k] + nums4[l] == 0
 * <p>
 * 输入：nums1 = [1,2], nums2 = [-2,-1], nums3 = [-1,2], nums4 = [0,2]
 * 输出：2
 * 解释：
 * 两个元组如下：
 * 1. (0, 0, 0, 1) -> nums1[0] + nums2[0] + nums3[0] + nums4[1] = 1 + (-2) + (-1) + 2 = 0
 * 2. (1, 1, 0, 0) -> nums1[1] + nums2[1] + nums3[0] + nums4[0] = 2 + (-1) + (-1) + 0 = 0
 * <p>
 * 输入：nums1 = [0], nums2 = [0], nums3 = [0], nums4 = [0]
 * 输出：1
 * <p>
 * n == nums1.length
 * n == nums2.length
 * n == nums3.length
 * n == nums4.length
 * 1 <= n <= 200
 * -2^28 <= nums1[i], nums2[i], nums3[i], nums4[i] <= 2^28
 */
public class Problem454 {
    public static void main(String[] args) {
        Problem454 problem454 = new Problem454();
        int[] nums1 = {1, 2};
        int[] nums2 = {-2, -1};
        int[] nums3 = {-1, 2};
        int[] nums4 = {0, 2};
        System.out.println(problem454.fourSumCount(nums1, nums2, nums3, nums4));
        System.out.println(problem454.fourSumCount2(nums1, nums2, nums3, nums4));
    }

    /**
     * 暴力
     * 时间复杂度O(n^4)，空间复杂度O(1)
     *
     * @param nums1
     * @param nums2
     * @param nums3
     * @param nums4
     * @return
     */
    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        if (nums1 == null || nums1.length == 0) {
            return 0;
        }

        int n = nums1.length;
        int count = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    for (int l = 0; l < n; l++) {
                        if (nums1[i] + nums2[j] + nums3[k] + nums4[l] == 0) {
                            count++;
                        }
                    }
                }
            }
        }

        return count;
    }

    /**
     * 哈希表
     * 先在前两个数组中计算num1+num2和对应次数的map映射，然后在后两个数组中计算-(num3+num4)是否在map存在
     * 时间复杂度O(n^2)，空间复杂度O(n^2)
     *
     * @param nums1
     * @param nums2
     * @param nums3
     * @param nums4
     * @return
     */
    public int fourSumCount2(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        if (nums1 == null || nums1.length == 0) {
            return 0;
        }

        //key：num1+num2之和，value：出现次数
        Map<Integer, Integer> map = new HashMap<>();

        for (int num1 : nums1) {
            for (int num2 : nums2) {
                map.put(num1 + num2, map.getOrDefault(num1 + num2, 0) + 1);
            }
        }

        int count = 0;

        //从map集合中找num3和num4数组之和为-(num3 + num4)的元素
        for (int num3 : nums3) {
            for (int num4 : nums4) {
                if (map.containsKey(-(num3 + num4))) {
                    count = count + map.get(-(num3 + num4));
                }
            }
        }

        return count;
    }
}
