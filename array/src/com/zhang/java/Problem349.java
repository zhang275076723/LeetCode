package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/4/11 10:44
 * @Author zsy
 * @Description 两个数组的交集 类比Problem350
 * 给定两个数组 nums1 和 nums2 ，返回 它们的交集 。
 * 输出结果中的每个元素一定是 唯一 的。我们可以 不考虑输出结果的顺序 。
 * <p>
 * 输入：nums1 = [1,2,2,1], nums2 = [2,2]
 * 输出：[2]
 * <p>
 * 输入：nums1 = [4,9,5], nums2 = [9,4,9,8,4]
 * 输出：[9,4]
 * 解释：[4,9] 也是可通过的
 * <p>
 * 1 <= nums1.length, nums2.length <= 1000
 * 0 <= nums1[i], nums2[i] <= 1000
 */
public class Problem349 {
    public static void main(String[] args) {
        Problem349 problem349 = new Problem349();
        int[] nums1 = {1, 2, 2, 1};
        int[] nums2 = {2, 2};
        System.out.println(Arrays.toString(problem349.intersection(nums1, nums2)));
        System.out.println(Arrays.toString(problem349.intersection2(nums1, nums2)));
    }

    /**
     * 哈希表
     * 时间复杂度O(m+n)，空间复杂度O(min(m,n))
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] intersection(int[] nums1, int[] nums2) {
        //存放nums1中元素的set集合
        Set<Integer> set = new HashSet<>();
        //存放重叠元素的set集合
        Set<Integer> resultSet = new HashSet<>();

        for (int num1 : nums1) {
            set.add(num1);
        }

        //如果nums2中包含有nums1中元素，则是交集元素，加入resultSet中
        for (int num2 : nums2) {
            if (set.contains(num2)) {
                resultSet.add(num2);
            }
        }

        //nums1和nums2交集的结果集合
        int[] arr = new int[resultSet.size()];
        int index = 0;

        for (int num : resultSet) {
            arr[index] = num;
            index++;
        }

        return arr;
    }

    /**
     * 双指针
     * 先对两个数组排序，如果nums1[i] < nums2[j]，则i++；如果nums1[i] > nums2[j]，则j++；
     * 如果nums1[i] == nums2[j]，则判断结果数组中是否存在nums1[i]，如果存在i++、j++，如果不存在，nums1[i]加入结果数组
     * 时间复杂度O(mlogm+nlogn)，空间复杂度O(logm+logn+min(m,n)) (排序使用的额外空间)
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] intersection2(int[] nums1, int[] nums2) {
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
                //找到重叠元素，并且list集合中没有当前重叠元素，因为重叠元素不能重复，则加入list中
                if (list.isEmpty() || list.get(list.size() - 1) != nums1[i]) {
                    list.add(nums1[i]);
                }
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
