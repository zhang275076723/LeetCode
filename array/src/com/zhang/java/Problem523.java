package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2023/7/27 08:07
 * @Author zsy
 * @Description 连续的子数组和 前缀和类比Problem209、Problem325、Problem327、Problem437、Problem525、Problem560、Problem862、Problem1171、Problem1871、Offer57_2
 * 给你一个整数数组 nums 和一个整数 k ，编写一个函数来判断该数组是否含有同时满足下述条件的连续子数组：
 * 子数组大小 至少为 2 ，且子数组元素总和为 k 的倍数。
 * 如果存在，返回 true ；否则，返回 false 。
 * 如果存在一个整数 n ，令整数 x 符合 x = n * k ，则称 x 是 k 的一个倍数。0 始终视为 k 的一个倍数。
 * <p>
 * 输入：nums = [23,2,4,6,7], k = 6
 * 输出：true
 * 解释：[2,4] 是一个大小为 2 的子数组，并且和为 6 。
 * <p>
 * 输入：nums = [23,2,6,4,7], k = 6
 * 输出：true
 * 解释：[23, 2, 6, 4, 7] 是大小为 5 的子数组，并且和为 42 。
 * 42 是 6 的倍数，因为 42 = 7 * 6 且 7 是一个整数。
 * <p>
 * 输入：nums = [23,2,6,4,7], k = 13
 * 输出：false
 * <p>
 * 1 <= nums.length <= 10^5
 * 0 <= nums[i] <= 10^9
 * 0 <= sum(nums[i]) <= 2^31 - 1
 * 1 <= k <= 2^31 - 1
 */
public class Problem523 {
    public static void main(String[] args) {
        Problem523 problem523 = new Problem523();
        int[] nums = {23, 2, 6, 4, 7};
        int k = 6;
        System.out.println(problem523.checkSubarraySum(nums, k));
        System.out.println(problem523.checkSubarraySum2(nums, k));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param nums
     * @param k
     * @return
     */
    public boolean checkSubarraySum(int[] nums, int k) {
        if (nums == null || nums.length < 2) {
            return false;
        }

        for (int i = 0; i < nums.length - 1; i++) {
            //nums[i]-nums[j]元素之和
            int sum = 0;

            for (int j = i; j < nums.length; j++) {
                sum = sum + nums[j];
                //子数组长度超过2，并且子数组元素之和是k的倍数，则返回true
                if (i < j && sum % k == 0) {
                    return true;
                }
            }
        }

        //遍历结束都没有找到满足要求的子数组，则返回false
        return false;
    }

    /**
     * 前缀和+哈希表
     * 哈希表中存在当前前缀和除以k的余数，则存在和为k倍数的子数组
     * 时间复杂度O(n)，空间复杂度O(min(n,k))
     *
     * @param nums
     * @param k
     * @return
     */
    public boolean checkSubarraySum2(int[] nums, int k) {
        if (nums == null || nums.length < 2) {
            return false;
        }

        //key：当前前缀和除以k的余数，value：当前前缀和除以k的余数第一次出现的下标索引
        Map<Integer, Integer> map = new HashMap<>();
        //初始化，不添加任何元素的前缀和除以k的余数为0，不添加任何元素的前缀和除以k第一次出现的下标索引为-1
        map.put(0, -1);
        //当前前缀和除以k的余数
        int preSum = 0;

        for (int i = 0; i < nums.length; i++) {
            preSum = (preSum + nums[i]) % k;

            //map中存在preSum，即之前存在相同余数，存在和为k倍数的子数组
            if (map.containsKey(preSum)) {
                int index = map.get(preSum);
                //判断和为k倍数的子数组长度是否超过2
                if (i - index >= 2) {
                    return true;
                }
            } else {
                map.put(preSum, i);
            }
        }

        return false;
    }
}
