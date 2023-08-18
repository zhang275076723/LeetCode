package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/6/13 11:38
 * @Author zsy
 * @Description 和为 K 的子数组 前缀和类比Problem209、Problem325、Problem327、Problem437、Problem523、Problem525、Problem862、Problem974、Problem1171、Problem1856、Problem1871、Offer57_2 子序列和子数组类比Problem53、Problem115、Problem152、Problem209、Problem300、Problem325、Problem392、Problem491、Problem516、Problem525、Problem581、Problem659、Problem673、Problem674、Problem718、Problem862、Problem1143、Offer42、Offer57_2
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
     * 前缀和+哈希表 (数组中存在负数元素，所以不能使用滑动窗口，只能使用前缀和)
     * 看到连续子数组，就要想到滑动窗口和前缀和
     * pre[i]：nums[0]-nums[i]元素之和
     * 和为k的子数组nums[i]-nums[j]：pre[j] - pre[i-1] == k
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @param k
     * @return
     */
    public int subarraySum2(int[] nums, int k) {
        //key：当前前缀和，value：当前前缀和出现的次数
        Map<Integer, Integer> map = new HashMap<>();
        //初始化，不添加任何元素的前缀和为0，前缀和为0的出现次数为1
        map.put(0, 1);

        int count = 0;
        int preSum = 0;

        for (int i = 0; i < nums.length; i++) {
            preSum = preSum + nums[i];

            //map中存在key为pre-k的前缀和，即存在子数组之和为k的情况，count累加
            if (map.containsKey(preSum - k)) {
                count = count + map.get(preSum - k);
            }

            //将当前前缀和以及当前前缀和出现次数加入map
            map.put(preSum, map.getOrDefault(preSum, 0) + 1);
        }

        return count;
    }
}
