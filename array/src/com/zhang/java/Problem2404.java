package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2025/3/29 08:46
 * @Author zsy
 * @Description 出现最频繁的偶数元素 类比Problem169、Problem229、Offer39
 * 给你一个整数数组 nums ，返回出现最频繁的偶数元素。
 * 如果存在多个满足条件的元素，只需要返回 最小 的一个。
 * 如果不存在这样的元素，返回 -1 。
 * <p>
 * 输入：nums = [0,1,2,2,4,4,1]
 * 输出：2
 * 解释：
 * 数组中的偶数元素为 0、2 和 4 ，在这些元素中，2 和 4 出现次数最多。
 * 返回最小的那个，即返回 2 。
 * <p>
 * 输入：nums = [4,4,4,9,2,4]
 * 输出：4
 * 解释：4 是出现最频繁的偶数元素。
 * <p>
 * 输入：nums = [29,47,21,41,13,37,25,7]
 * 输出：-1
 * 解释：不存在偶数元素。
 * <p>
 * 1 <= nums.length <= 2000
 * 0 <= nums[i] <= 10^5
 */
public class Problem2404 {
    public static void main(String[] args) {
        Problem2404 problem2404 = new Problem2404();
        int[] nums = {0, 1, 2, 2, 4, 4, 1};
        System.out.println(problem2404.mostFrequentEven(nums));
    }

    /**
     * 哈希表
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int mostFrequentEven(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int num : nums) {
            if (num % 2 == 0) {
                map.put(num, map.getOrDefault(num, 0) + 1);
            }
        }

        int result = -1;

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            //注意：Integer对象之间比较不能使用==，只能使用equals()
            if (result == -1 || map.get(result) < entry.getValue() ||
                    (map.get(result).equals(entry.getValue()) && entry.getKey() < result)) {
                result = entry.getKey();
            }
        }

        return result;
    }
}
