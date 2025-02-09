package com.zhang.java;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Date 2025/3/31 08:08
 * @Author zsy
 * @Description 找出两数组的不同 类比Problem349、Problem350、Problem2248
 * 给你两个下标从 0 开始的整数数组 nums1 和 nums2 ，请你返回一个长度为 2 的列表 answer ，其中：
 * answer[0] 是 nums1 中所有 不 存在于 nums2 中的 不同 整数组成的列表。
 * answer[1] 是 nums2 中所有 不 存在于 nums1 中的 不同 整数组成的列表。
 * 注意：列表中的整数可以按 任意 顺序返回。
 * <p>
 * 输入：nums1 = [1,2,3], nums2 = [2,4,6]
 * 输出：[[1,3],[4,6]]
 * 解释：
 * 对于 nums1 ，nums1[1] = 2 出现在 nums2 中下标 0 处，然而 nums1[0] = 1 和 nums1[2] = 3 没有出现在 nums2 中。
 * 因此，answer[0] = [1,3]。
 * 对于 nums2 ，nums2[0] = 2 出现在 nums1 中下标 1 处，然而 nums2[1] = 4 和 nums2[2] = 6 没有出现在 nums2 中。
 * 因此，answer[1] = [4,6]。
 * <p>
 * 输入：nums1 = [1,2,3,3], nums2 = [1,1,2,2]
 * 输出：[[3],[]]
 * 解释：
 * 对于 nums1 ，nums1[2] 和 nums1[3] 没有出现在 nums2 中。
 * 由于 nums1[2] == nums1[3] ，二者的值只需要在 answer[0] 中出现一次，故 answer[0] = [3]。
 * nums2 中的每个整数都在 nums1 中出现，因此，answer[1] = [] 。
 * <p>
 * 1 <= nums1.length, nums2.length <= 1000
 * -1000 <= nums1[i], nums2[i] <= 1000
 */
public class Problem2215 {
    public static void main(String[] args) {
        Problem2215 problem2215 = new Problem2215();
        int[] nums1 = {1, 2, 3, 3};
        int[] nums2 = {1, 1, 2, 2};
        System.out.println(problem2215.findDifference(nums1, nums2));
    }

    /**
     * 哈希表
     * 时间复杂度O(m+n)，空间复杂度O(m+n)
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public List<List<Integer>> findDifference(int[] nums1, int[] nums2) {
        Set<Integer> set1 = new HashSet<>();
        Set<Integer> set2 = new HashSet<>();

        for (int num : nums1) {
            set1.add(num);
        }

        for (int num : nums2) {
            set2.add(num);
        }

        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            result.add(new ArrayList<>());
        }

        //注意：不能遍历nums1，避免元素重复加入result
        for (int num : set1) {
            if (!set2.contains(num)) {
                result.get(0).add(num);
            }
        }

        //注意：不能遍历nums2，避免元素重复加入result
        for (int num : set2) {
            if (!set1.contains(num)) {
                result.get(1).add(num);
            }
        }

        return result;
    }
}
