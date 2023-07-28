package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2023/5/28 08:41
 * @Author zsy
 * @Description 连续数组 前缀和类比Problem209、Problem325、Problem327、Problem437、Problem523、Problem560、Problem862、Problem1171、Problem1871、Offer57_2 子序列和子数组类比Problem53、Problem115、Problem152、Problem209、Problem300、Problem325、Problem392、Problem491、Problem516、Problem560、Problem581、Problem659、Problem673、Problem674、Problem718、Problem862、Problem1143、Offer42、Offer57_2
 * 给定一个二进制数组 nums , 找到含有相同数量的 0 和 1 的最长连续子数组，并返回该子数组的长度。
 * <p>
 * 输入: nums = [0,1]
 * 输出: 2
 * 说明: [0, 1] 是具有相同数量 0 和 1 的最长连续子数组。
 * <p>
 * 输入: nums = [0,1,0]
 * 输出: 2
 * 说明: [0, 1] (或 [1, 0]) 是具有相同数量0和1的最长连续子数组。
 * <p>
 * 1 <= nums.length <= 10^5
 * nums[i] 不是 0 就是 1
 */
public class Problem525 {
    public static void main(String[] args) {
        Problem525 problem525 = new Problem525();
        int[] nums = {1, 1, 0, 0, 1};
        System.out.println(problem525.findMaxLength(nums));
        System.out.println(problem525.findMaxLength2(nums));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int findMaxLength(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        //含有相同数量的0和1的子数组的最大长度
        int maxLen = 0;

        for (int i = 0; i < nums.length; i++) {
            //从下标索引i开始的子数组中1的个数减去0的个数之差
            int sum = 0;

            for (int j = i; j < nums.length; j++) {
                //遇到1，sum加1，遇到0，sum减1
                if (nums[j] == 1) {
                    sum++;
                } else if (nums[j] == 0) {
                    sum--;
                }

                if (sum == 0) {
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
     * @return
     */
    public int findMaxLength2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        //含有相同数量的0和1的子数组的最大长度
        int maxLen = 0;
        //key：当前区间和(遇到1，前缀和加1，遇到0，前缀和减1)，value：首次出现当前区间和的最后一个元素的下标索引
        Map<Integer, Integer> map = new HashMap<>();
        //初始化，不添加任何元素的前缀和为0，最后一个元素的下标索引为-1
        map.put(0, -1);
        int preSum = 0;

        for (int i = 0; i < nums.length; i++) {
            //遇到1，preSum加1，遇到0，preSum减1
            if (nums[i] == 1) {
                preSum++;
            } else if (nums[i] == 0) {
                preSum--;
            }

            //map中存在当前区间和preSum，更新maxLen，此时不能更新map
            if (map.containsKey(preSum)) {
                maxLen = Math.max(maxLen, i - map.get(preSum));
            } else {
                //因为要找含有相同数量的0和1的子数组的最大长度，所以map中不存在当前区间和preSum，preSum才能加入map
                map.put(preSum, i);
            }
        }

        return maxLen;
    }
}
