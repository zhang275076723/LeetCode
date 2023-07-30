package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2023/5/28 08:00
 * @Author zsy
 * @Description 和等于 k 的最长子数组长度 前缀和类比Problem209、Problem327、Problem437、Problem523、Problem525、Problem560、Problem862、Problem974、Problem1171、Problem1871、Offer57_2 子序列和子数组类比Problem53、Problem115、Problem152、Problem209、Problem300、Problem392、Problem491、Problem516、Problem525、Problem560、Problem581、Problem659、Problem673、Problem674、Problem718、Problem862、Problem1143、Offer42、Offer57_2
 * 给定一个数组 nums 和一个目标值 k，找到和等于 k 的最长连续子数组长度。
 * 如果不存在任意一个符合要求的子数组，则返回 0。
 * <p>
 * 输入: nums = [1,-1,5,-2,3], k = 3
 * 输出: 4
 * 解释: 子数组 [1, -1, 5, -2] 和等于 3，且长度最长。
 * <p>
 * 输入: nums = [-2,-1,2,1], k = 1
 * 输出: 2
 * 解释: 子数组 [-1, 2] 和等于 1，且长度最长。
 * <p>
 * 1 <= nums.length <= 2 * 10^5
 * -10^4 <= nums[i] <= 10^4
 * -10^9 <= k <= 10^9
 */
public class Problem325 {
    public static void main(String[] args) {
        Problem325 problem325 = new Problem325();
        int[] nums = {1, -1, 5, -2, 3};
        int k = 3;
        System.out.println(problem325.maxSubArrayLen(nums, k));
        System.out.println(problem325.maxSubArrayLen2(nums, k));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param nums
     * @param k
     * @return
     */
    public int maxSubArrayLen(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        //和为k的子数组的最大长度
        int maxLen = 0;

        for (int i = 0; i < nums.length; i++) {
            int sum = 0;

            for (int j = i; j < nums.length; j++) {
                sum = sum + nums[j];

                if (sum == k) {
                    maxLen = Math.max(maxLen, j - i + 1);
                }
            }
        }

        return maxLen;
    }

    /**
     * 前缀和+哈希表
     * 看到连续子数组，就要想到滑动窗口和前缀和
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @param k
     * @return
     */
    public int maxSubArrayLen2(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        //和为k的子数组的最大长度
        int maxLen = 0;
        //key：当前前缀和，value：当前前缀和的末尾元素下标索引
        Map<Integer, Integer> map = new HashMap<>();
        //初始化，不添加任何元素的前缀和为0，不添加任何元素的前缀和的末尾元素的下标索引为-1
        map.put(0, -1);
        int preSum = 0;

        for (int i = 0; i < nums.length; i++) {
            preSum = preSum + nums[i];

            //map中存在key为preSum-k的前缀和，则更新maxLen
            if (map.containsKey(preSum - k)) {
                maxLen = Math.max(maxLen, i - map.get(preSum - k));
            }

            //因为要找和为k的子数组的最大长度，所以map中不存在当前前缀和，preSum才能加入map
            if (!map.containsKey(preSum)) {
                map.put(preSum, i);
            }
        }

        return maxLen;
    }
}
