package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/6/13 11:38
 * @Author zsy
 * @Description 和为 K 的子数组 类比problem209、Problem437、Offer57_2
 * 给你一个整数数组 nums 和一个整数 k ，请你统计并返回 该数组中和为 k 的子数组的个数 。
 * 注意：子数组中的元素在原数组中必须连续
 * <p>
 * 输入：nums = [1,1,1], k = 2
 * 输出：2
 * <p>
 * 输入：nums = [1,2,3], k = 3
 * 输出：2
 * <p>
 * 1 <= nums.length <= 2 * 10^4
 * -1000 <= nums[i] <= 1000
 * -10^7 <= k <= 10^7
 * <p>
 * 注意：因为数组中元素存在负数所以不能使用滑动窗口，如果都是正数，则可以使用滑动窗口，所以只能使用前缀和
 */
public class Problem560 {
    public static void main(String[] args) {
        Problem560 problem560 = new Problem560();
        int[] nums = {1, 2, 3};
        int k = 3;
        System.out.println(problem560.subarraySum(nums, k));
        System.out.println(problem560.subarraySum2(nums, k));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param nums
     * @param k
     * @return
     */
    public int subarraySum(int[] nums, int k) {
        int count = 0;

        for (int i = 0; i < nums.length; i++) {
            int sum = 0;

            for (int j = i; j < nums.length; j++) {
                sum = sum + nums[j];

                if (sum == k) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * 前缀和，当存在负数时，只能使用前缀和，不能使用滑动窗口
     * 看到连续子数组，想到滑动窗口和前缀和(适合有负数的情况)
     * pre[i]：nums[0]-nums[i]元素之和
     * 和为k的子数组nums[i]-nums[j]：pre[j] - pre[i-1] == k
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @param k
     * @return
     */
    public int subarraySum2(int[] nums, int k) {
        //map中key为前缀和，value为前缀和出现的次数
        Map<Integer, Integer> map = new HashMap<>();
        //将(0,1)放入map，保证只有nums[0]满足为k的情况
        map.put(0, 1);

        int count = 0;
        int pre = 0;

        for (int i = 0; i < nums.length; i++) {
            pre = pre + nums[i];

            //map中存在key为pre - k的前缀和，说明有满足子数组之和为k的情况
            if (map.containsKey(pre - k)) {
                count = count + map.get(pre - k);
            }

            //将当前前缀和放入map
            map.put(pre, map.getOrDefault(pre, 0) + 1);
        }

        return count;
    }
}
