package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2025/3/1 08:59
 * @Author zsy
 * @Description K 和数对的最大数目 哈希表类比Problem1
 * 给你一个整数数组 nums 和一个整数 k 。
 * 每一步操作中，你需要从数组中选出和为 k 的两个整数，并将它们移出数组。
 * 返回你可以对数组执行的最大操作数。
 * <p>
 * 输入：nums = [1,2,3,4], k = 5
 * 输出：2
 * 解释：开始时 nums = [1,2,3,4]：
 * - 移出 1 和 4 ，之后 nums = [2,3]
 * - 移出 2 和 3 ，之后 nums = []
 * 不再有和为 5 的数对，因此最多执行 2 次操作。
 * <p>
 * 输入：nums = [3,1,3,4,3], k = 6
 * 输出：1
 * 解释：开始时 nums = [3,1,3,4,3]：
 * - 移出前两个 3 ，之后nums = [1,4,3]
 * 不再有和为 6 的数对，因此最多执行 1 次操作。
 * <p>
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i] <= 10^9
 * 1 <= k <= 10^9
 */
public class Problem1679 {
    public static void main(String[] args) {
        Problem1679 problem1679 = new Problem1679();
        int[] nums = {1, 2, 3, 4};
        int k = 5;
        System.out.println(problem1679.maxOperations(nums, k));
    }

    /**
     * 哈希表
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @param k
     * @return
     */
    public int maxOperations(int[] nums, int k) {
        int count = 0;
        //key：nums[i]，value：nums[i]出现的次数
        //注意：不能使用set，因为nums中元素有可能重复
        Map<Integer, Integer> map = new HashMap<>();

        for (int num : nums) {
            if (map.containsKey(k - num)) {
                count++;
                map.put(k - num, map.get(k - num) - 1);

                if (map.get(k - num) == 0) {
                    map.remove(k - num);
                }
            } else {
                map.put(num, map.getOrDefault(num, 0) + 1);
            }
        }

        return count;
    }
}
