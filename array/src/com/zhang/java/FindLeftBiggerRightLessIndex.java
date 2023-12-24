package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/5/11 08:14
 * @Author zsy
 * @Description 比左边元素都大同时比右边元素都小的元素下标索引 字节面试题 数组中的动态规划类比Problem53、Problem135、Problem152、Problem238、Problem724、Problem768、Problem769、Problem845、Problem1749、Offer42、Offer66
 * 求数组中比左边元素都大同时比右边元素都小的元素，返回这些元素的下标索引。
 * <p>
 * 输入：nums = [2, 3, 1, 8, 9, 20, 12]
 * 输出：3, 4
 * 解释：数组中 8, 9 满足题目要求，他们的索引分别是 3、4
 */
public class FindLeftBiggerRightLessIndex {
    public static void main(String[] args) {
        FindLeftBiggerRightLessIndex findLeftBiggerRightLessIndex = new FindLeftBiggerRightLessIndex();
        int[] nums = {2, 3, 1, 8, 9, 20, 12};
        System.out.println(findLeftBiggerRightLessIndex.find(nums));
    }

    /**
     * 动态规划
     * left[i]：nums[i]左边，即nums[0]-nums[i-1]的最大值
     * right[i]：nums[i]右边，即nums[i+1]-nums[nums.length-1]的最小值
     * left[i] = max(left[i-1],nums[i-1])
     * right[i] = min(right[i+1],nums[i+1])
     * 时间复杂度O(n)，空间复杂的O(n)
     *
     * @param nums
     * @return
     */
    public List<Integer> find(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new ArrayList<>();
        }

        int[] left = new int[nums.length];
        int[] right = new int[nums.length];
        //left和right数组初始化
        left[0] = Integer.MIN_VALUE;
        right[nums.length - 1] = Integer.MAX_VALUE;

        for (int i = 1; i < nums.length; i++) {
            left[i] = Math.max(left[i - 1], nums[i - 1]);
            right[nums.length - 1 - i] = Math.min(right[nums.length - i], nums[nums.length - i]);
        }

        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > left[i] && nums[i] < right[i]) {
                list.add(i);
            }
        }

        return list;
    }
}
