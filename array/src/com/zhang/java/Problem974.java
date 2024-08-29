package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2023/7/30 08:54
 * @Author zsy
 * @Description 和可被 K 整除的子数组 类比Problem523、Problem1590、Problem2598 前缀和类比Problem209、Problem325、Problem327、Problem437、Problem523、Problem525、Problem560、Problem862、Problem1171、Problem1856、Problem1871、Offer57_2
 * 给定一个整数数组 nums 和一个整数 k ，返回其中元素之和可被 k 整除的（连续、非空） 子数组 的数目。
 * 子数组 是数组的 连续 部分。
 * <p>
 * 输入：nums = [4,5,0,-2,-3,1], k = 5
 * 输出：7
 * 解释：
 * 有 7 个子数组满足其元素之和可被 k = 5 整除：
 * [4, 5, 0, -2, -3, 1], [5], [5, 0], [5, 0, -2, -3], [0], [0, -2, -3], [-2, -3]
 * <p>
 * 输入: nums = [5], k = 9
 * 输出: 0
 * <p>
 * 1 <= nums.length <= 3 * 10^4
 * -10^4 <= nums[i] <= 10^4
 * 2 <= k <= 10^4
 */
public class Problem974 {
    public static void main(String[] args) {
        Problem974 problem974 = new Problem974();
        int[] nums = {4, 5, 0, -2, -3, 1};
        int k = 5;
        System.out.println(problem974.subarraysDivByK(nums, k));
        System.out.println(problem974.subarraysDivByK2(nums, k));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param nums
     * @param k
     * @return
     */
    public int subarraysDivByK(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int count = 0;

        for (int i = 0; i < nums.length; i++) {
            int sum = 0;

            for (int j = i; j < nums.length; j++) {
                sum = sum + nums[j];

                if (sum % k == 0) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * 前缀和+哈希表
     * preSum[j] mod k = preSum[i] mod k
     * 则nums[i]-nums[j-1]子数组元素之和能被k整除
     * 时间复杂度O(n)，空间复杂度O(min(n,k))
     *
     * @param nums
     * @param k
     * @return
     */
    public int subarraysDivByK2(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        //key：当前前缀和除以k的余数，value：当前前缀和除以k的余数出现的次数
        Map<Integer, Integer> map = new HashMap<>();
        //初始化，不添加任何元素的前缀和除以k的余数为0，前缀和除以k余数为0出现的次数为1
        map.put(0, 1);
        //当前前缀和除以k的余数
        int preSum = 0;
        int count = 0;

        for (int i = 0; i < nums.length; i++) {
            //注意：nums[i]有可能小于0，导致余数为负数，所以余数需要加上k再取余数
            preSum = ((preSum + nums[i]) % k + k) % k;

            //preSum[j] mod k = preSum[i] mod k
            //则nums[i]-nums[j-1]子数组元素之和能被k整除
            if (map.containsKey(preSum)) {
                count = count + map.get(preSum);
            }

            //当前前缀和除以k的余数加入map
            map.put(preSum, map.getOrDefault(preSum, 0) + 1);
        }

        return count;
    }
}
