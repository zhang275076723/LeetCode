package com.zhang.java;

import java.util.*;

/**
 * @Date 2025/4/1 08:24
 * @Author zsy
 * @Description 多个数组求交集 类比Problem349、Problem350、Problem2215
 * 给你一个二维整数数组 nums ，其中 nums[i] 是由 不同 正整数组成的一个非空数组，
 * 按 升序排列 返回一个数组，数组中的每个元素在 nums 所有数组 中都出现过。
 * <p>
 * 输入：nums = [[3,1,2,4,5],[1,2,3,4],[3,4,5,6]]
 * 输出：[3,4]
 * 解释：
 * nums[0] = [3,1,2,4,5]，nums[1] = [1,2,3,4]，nums[2] = [3,4,5,6]，在 nums 中每个数组中都出现的数字是 3 和 4 ，所以返回 [3,4] 。
 * <p>
 * 输入：nums = [[1,2,3],[4,5,6]]
 * 输出：[]
 * 解释：
 * 不存在同时出现在 nums[0] 和 nums[1] 的整数，所以返回一个空列表 [] 。
 * <p>
 * 1 <= nums.length <= 1000
 * 1 <= sum(nums[i].length) <= 1000
 * 1 <= nums[i][j] <= 1000
 * nums[i] 中的所有值 互不相同
 */
public class Problem2248 {
    public static void main(String[] args) {
        Problem2248 problem2248 = new Problem2248();
        int[][] nums = {{3, 1, 2, 4, 5}, {1, 2, 3, 4}, {3, 4, 5, 6}};
        System.out.println(problem2248.intersection(nums));
        System.out.println(problem2248.intersection2(nums));
    }

    /**
     * 哈希表
     * 每两两个nums[i]求交集，最后得到整个数组的交集
     * 时间复杂度O(n+min(nums[i].length)*log(min(nums[i].length)))，空间复杂度O(max(nums[i].length)) (n=nums中元素的个数)
     *
     * @param nums
     * @return
     */
    public List<Integer> intersection(int[][] nums) {
        Set<Integer> set = new HashSet<>();

        for (int num : nums[0]) {
            set.add(num);
        }

        for (int i = 1; i < nums.length; i++) {
            Set<Integer> tempSet = new HashSet<>();

            for (int num : nums[i]) {
                if(set.contains(num)){
                    tempSet.add(num);
                }
            }

            set = tempSet;
        }

        List<Integer> list = new ArrayList<>(set);

        //由小到大排序
        list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return a - b;
            }
        });

        return list;
    }

    /**
     * 哈希表
     * nums中元素互不相同，统计nums中元素出现的次数，如果当前元素出现次数等于nums.length，则当前元素为数组的交集
     * 时间复杂度O(n+min(nums[i].length)*log(min(nums[i].length)))，空间复杂度O(max(nums[i].length)) (n=nums中元素的个数)
     *
     * @param nums
     * @return
     */
    public List<Integer> intersection2(int[][] nums) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int[] arr : nums) {
            for (int num : arr) {
                map.put(num, map.getOrDefault(num, 0) + 1);
            }
        }

        List<Integer> list = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            //nums中元素互不相同，出现次数等于nums.length，则当前元素为数组的交集
            if (entry.getValue() == nums.length) {
                list.add(entry.getKey());
            }
        }

        //由小到大排序
        list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return a - b;
            }
        });

        return list;
    }
}
