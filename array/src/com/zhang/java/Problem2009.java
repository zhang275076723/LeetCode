package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/8/2 08:29
 * @Author zsy
 * @Description 使数组连续的最少操作数 排序+滑动窗口类比Problem532、Problem632、Problem1838
 * 给你一个整数数组 nums 。
 * 每一次操作中，你可以将 nums 中 任意 一个元素替换成 任意 整数。
 * 如果 nums 满足以下条件，那么它是 连续的 ：
 * nums 中所有元素都是 互不相同 的。
 * nums 中 最大 元素与 最小 元素的差等于 nums.length - 1 。
 * 比方说，nums = [4, 2, 5, 3] 是 连续的 ，但是 nums = [1, 2, 3, 5, 6] 不是连续的 。
 * 请你返回使 nums 连续 的 最少 操作次数。
 * <p>
 * 输入：nums = [4,2,5,3]
 * 输出：0
 * 解释：nums 已经是连续的了。
 * <p>
 * 输入：nums = [1,2,3,5,6]
 * 输出：1
 * 解释：一个可能的解是将最后一个元素变为 4 。
 * 结果数组为 [1,2,3,5,4] ，是连续数组。
 * <p>
 * 输入：nums = [1,10,100,1000]
 * 输出：3
 * 解释：一个可能的解是：
 * - 将第二个元素变为 2 。
 * - 将第三个元素变为 3 。
 * - 将第四个元素变为 4 。
 * 结果数组为 [1,2,3,4] ，是连续数组。
 * <p>
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i] <= 10^9
 */
public class Problem2009 {
    public static void main(String[] args) {
        Problem2009 problem2009 = new Problem2009();
        int[] nums = {1, 2, 3, 5, 6};
        System.out.println(problem2009.minOperations(nums));
        System.out.println(problem2009.minOperations2(nums));
        System.out.println(problem2009.minOperations3(nums));
    }

    /**
     * 暴力
     * nums中元素去重之后，找在[list.get(i),list.get(i)+nums.length-1]范围内的元素个数，
     * 剩余不在范围内的元素个数即为需要修改的次数
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int minOperations(int[] nums) {
        Set<Integer> set = new HashSet<>();

        //去重
        for (int num : nums) {
            set.add(num);
        }

        List<Integer> list = new ArrayList<>(set);
        //使nums连续的最少修改次数
        //初始化为nums数组长度-1，即保留一个元素，其他元素都修改
        int min = nums.length - 1;

        //注意：遍历的是list，而不是nums
        for (int i = 0; i < list.size(); i++) {
            //在[list.get(i),list.get(i)+nums.length-1]范围内的元素个数
            int count = 0;

            for (int j = 0; j < list.size(); j++) {
                if (list.get(j) >= list.get(i) && list.get(j) <= list.get(i) + nums.length - 1) {
                    count++;
                }
            }

            min = Math.min(min, nums.length - count);
        }

        return min;
    }

    /**
     * 排序+二分查找
     * nums中元素去重之后，nums由小到大排序，往右二分查找找在[list.get(i),list.get(i)+nums.length-1]范围内的元素个数，
     * 剩余不在范围内的元素个数即为需要修改的次数
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int minOperations2(int[] nums) {
        Set<Integer> set = new HashSet<>();

        //去重
        for (int num : nums) {
            set.add(num);
        }

        List<Integer> list = new ArrayList<>(set);

        //由小到大排序
        list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return a - b;
            }
        });

        int min = nums.length - 1;

        //注意：遍历的是list，而不是nums
        for (int i = 0; i < list.size(); i++) {
            int left = i + 1;
            int right = list.size() - 1;
            int mid;
            //list中小于等于list.get(i)+nums.length-1的最大下标索引
            int result = -1;

            while (left <= right) {
                mid = left + ((right - left) >> 1);

                if (list.get(mid) <= list.get(i) + nums.length - 1) {
                    result = mid;
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }

            min = Math.min(min, nums.length - (result - i + 1));
        }

        return min;
    }

    /**
     * 排序+滑动窗口
     * nums中元素去重之后，nums由小到大排序，滑动窗口找在[list.get(left),list.get(left)+nums.length-1]范围内的元素个数，
     * 剩余不在范围内的元素个数即为需要修改的次数
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int minOperations3(int[] nums) {
        Set<Integer> set = new HashSet<>();

        //去重
        for (int num : nums) {
            set.add(num);
        }

        List<Integer> list = new ArrayList<>(set);

        //由小到大排序
        list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return a - b;
            }
        });

        //使nums连续的最少修改次数
        //初始化为nums数组长度-1，即保留一个元素，其他元素都修改
        int min = nums.length - 1;

        int left = 0;
        int right = 0;

        //注意：遍历的是list，而不是nums
        while (right < list.size()) {
            //list.get(left)不在范围内，左指针右移
            while (list.get(left) + nums.length - 1 < list.get(right)) {
                left++;
            }

            //更新min
            min = Math.min(min, nums.length - (right - left + 1));
            //右指针右移
            right++;
        }

        return min;
    }
}
