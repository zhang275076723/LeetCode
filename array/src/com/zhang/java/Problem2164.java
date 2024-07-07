package com.zhang.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @Date 2024/8/25 08:55
 * @Author zsy
 * @Description 对奇偶下标分别排序 类比Problem905、Problem922、Problem2149、Offer21
 * 给你一个下标从 0 开始的整数数组 nums 。
 * 根据下述规则重排 nums 中的值：
 * 按 非递增 顺序排列 nums 奇数下标 上的所有值。
 * 举个例子，如果排序前 nums = [4,1,2,3] ，对奇数下标的值排序后变为 [4,3,2,1] 。奇数下标 1 和 3 的值按照非递增顺序重排。
 * 按 非递减 顺序排列 nums 偶数下标 上的所有值。
 * 举个例子，如果排序前 nums = [4,1,2,3] ，对偶数下标的值排序后变为 [2,1,4,3] 。偶数下标 0 和 2 的值按照非递减顺序重排。
 * 返回重排 nums 的值之后形成的数组。
 * <p>
 * 输入：nums = [4,1,2,3]
 * 输出：[2,3,4,1]
 * 解释：
 * 首先，按非递增顺序重排奇数下标（1 和 3）的值。
 * 所以，nums 从 [4,1,2,3] 变为 [4,3,2,1] 。
 * 然后，按非递减顺序重排偶数下标（0 和 2）的值。
 * 所以，nums 从 [4,1,2,3] 变为 [2,3,4,1] 。
 * 因此，重排之后形成的数组是 [2,3,4,1] 。
 * <p>
 * 输入：nums = [2,1]
 * 输出：[2,1]
 * 解释：
 * 由于只有一个奇数下标和一个偶数下标，所以不会发生重排。
 * 形成的结果数组是 [2,1] ，和初始数组一样。
 * <p>
 * 1 <= nums.length <= 100
 * 1 <= nums[i] <= 100
 */
public class Problem2164 {
    public static void main(String[] args) {
        Problem2164 problem2164 = new Problem2164();
        int[] nums = {4, 1, 2, 3};
        System.out.println(Arrays.toString(problem2164.sortEvenOdd(nums)));
    }

    /**
     * 模拟
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int[] sortEvenOdd(int[] nums) {
        //存储nums中奇数下标索引元素
        List<Integer> list1 = new ArrayList<>();
        //存储nums中偶数下标索引元素
        List<Integer> list2 = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            if (i % 2 == 1) {
                list1.add(nums[i]);
            } else {
                list2.add(nums[i]);
            }
        }

        //nums中奇数下标索引元素由大到小排序
        list1.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return b - a;
            }
        });

        //nums中偶数下标索引元素由小到大排序
        list2.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return a - b;
            }
        });

        for (int i = 0; i < nums.length; i = i + 2) {
            nums[i] = list2.get(i / 2);
        }

        for (int i = 1; i < nums.length; i = i + 2) {
            nums[i] = list1.get((i - 1) / 2);
        }

        return nums;
    }
}
