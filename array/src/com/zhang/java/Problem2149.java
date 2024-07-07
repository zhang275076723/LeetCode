package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2024/8/27 08:41
 * @Author zsy
 * @Description 按符号重排数组 类比Problem905、Problem922、Problem2161、Problem2164、Offer21
 * 给你一个下标从 0 开始的整数数组 nums ，数组长度为 偶数 ，由数目 相等 的正整数和负整数组成。
 * 你需要返回满足下述条件的数组 nums：
 * 任意 连续 的两个整数 符号相反
 * 对于符号相同的所有整数，保留 它们在 nums 中的 顺序 。
 * 重排后数组以正整数开头。
 * 重排元素满足上述条件后，返回修改后的数组。
 * <p>
 * 输入：nums = [3,1,-2,-5,2,-4]
 * 输出：[3,-2,1,-5,2,-4]
 * 解释：
 * nums 中的正整数是 [3,1,2] ，负整数是 [-2,-5,-4] 。
 * 重排的唯一可行方案是 [3,-2,1,-5,2,-4]，能满足所有条件。
 * 像 [1,-2,2,-5,3,-4]、[3,1,2,-2,-5,-4]、[-2,3,-5,1,-4,2] 这样的其他方案是不正确的，因为不满足一个或者多个条件。
 * <p>
 * 输入：nums = [-1,1]
 * 输出：[1,-1]
 * 解释：
 * 1 是 nums 中唯一一个正整数，-1 是 nums 中唯一一个负整数。
 * 所以 nums 重排为 [1,-1] 。
 * <p>
 * 2 <= nums.length <= 2 * 10^5
 * nums.length 是 偶数
 * 1 <= |nums[i]| <= 10^5
 * nums 由 相等 数量的正整数和负整数组成
 */
public class Problem2149 {
    public static void main(String[] args) {
        Problem2149 problem2149 = new Problem2149();
        int[] nums = {3, 1, -2, -5, 2, -4};
        System.out.println(Arrays.toString(problem2149.rearrangeArray(nums)));
    }

    /**
     * 模拟
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int[] rearrangeArray(int[] nums) {
        int[] result = new int[nums.length];
        //存储nums中正整数元素的下标索引
        int index1 = 0;
        //存储nums中负整数元素的下标索引
        int index2 = 1;

        for (int num : nums) {
            if (num > 0) {
                result[index1] = num;
                index1 = index1 + 2;
            } else {
                result[index2] = num;
                index2 = index2 + 2;
            }
        }

        return result;
    }
}
