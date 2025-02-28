package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/4/11 10:55
 * @Author zsy
 * @Description 两个数组的交集 II 类比Problem349、Problem2215、Problem2248
 * 给你两个整数数组 nums1 和 nums2 ，请你以数组形式返回两数组的交集。
 * 返回结果中每个元素出现的次数，应与元素在两个数组中都出现的次数一致（如果出现次数不一致，则考虑取较小值）。
 * 可以不考虑输出结果的顺序。
 * 进阶：
 * 如果给定的数组已经排好序呢？你将如何优化你的算法？
 * 如果 nums1 的大小比 nums2 小，哪种方法更优？
 * 如果 nums2 的元素存储在磁盘上，内存是有限的，并且你不能一次加载所有的元素到内存中，你该怎么办？
 * <p>
 * 输入：nums1 = [1,2,2,1], nums2 = [2,2]
 * 输出：[2,2]
 * <p>
 * 输入：nums1 = [4,9,5], nums2 = [9,4,9,8,4]
 * 输出：[4,9]
 * <p>
 * 1 <= nums1.length, nums2.length <= 1000
 * 0 <= nums1[i], nums2[i] <= 1000
 */
public class Problem350 {
    public static void main(String[] args) {
        Problem350 problem350 = new Problem350();
        int[] nums1 = {4, 9, 5};
        int[] nums2 = {9, 4, 9, 8, 4};
        System.out.println(Arrays.toString(problem350.intersect(nums1, nums2)));
        System.out.println(Arrays.toString(problem350.intersect2(nums1, nums2)));
    }

    /**
     * 哈希表
     * 进阶问题3：只能使用哈希表，而不能排序
     * 时间复杂度O(m+n)，空间复杂度O(min(m,n))
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] intersect(int[] nums1, int[] nums2) {
        //存放nums1中元素出现的次数map
        Map<Integer, Integer> map = new HashMap<>();
        //存放重叠元素的list集合
        List<Integer> list = new ArrayList<>();

        for (int num : nums1) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        for (int num : nums2) {
            //num存在于map中，并且出现次数大于0，则num是重叠元素，加入list集合，并且map中num次数减1
            if (map.getOrDefault(num, 0) > 0) {
                list.add(num);
                map.put(num, map.get(num) - 1);

                if (map.get(num) == 0) {
                    map.remove(num);
                }
            }
        }

        int[] arr = new int[list.size()];

        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }

        return arr;
    }

    /**
     * 双指针
     * 先对两个数组排序，如果nums1[i] < nums2[j]，则i++；如果nums1[i] > nums2[j]，则j++；
     * 如果nums1[i] == nums2[j]，则nums1[i]加入结果数组，i++、j++
     * 时间复杂度O(mlogm+nlogn)，空间复杂度O(logm+logn+min(m,n))
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] intersect2(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);

        //存放重叠元素的list集合
        List<Integer> list = new ArrayList<>();
        int i = 0;
        int j = 0;

        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] < nums2[j]) {
                i++;
            } else if (nums1[i] > nums2[j]) {
                j++;
            } else {
                //找到重叠元素，则加入list中
                list.add(nums1[i]);
                i++;
                j++;
            }
        }

        int[] arr = new int[list.size()];

        for (int k = 0; k < list.size(); k++) {
            arr[k] = list.get(k);
        }

        return arr;
    }
}
