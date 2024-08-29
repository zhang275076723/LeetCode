package com.zhang.java;

import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2024/12/10 08:36
 * @Author zsy
 * @Description 大于等于顺序前缀和的最小缺失整数 类比Problem128
 * 给你一个下标从 0 开始的整数数组 nums 。
 * 如果一个前缀 nums[0..i] 满足对于 1 <= j <= i 的所有元素都有 nums[j] = nums[j - 1] + 1 ，
 * 那么我们称这个前缀是一个 顺序前缀 。
 * 特殊情况是，只包含 nums[0] 的前缀也是一个 顺序前缀 。
 * 请你返回 nums 中没有出现过的 最小 整数 x ，满足 x 大于等于 最长 顺序前缀的和。
 * <p>
 * 输入：nums = [1,2,3,2,5]
 * 输出：6
 * 解释：nums 的最长顺序前缀是 [1,2,3] ，和为 6 ，6 不在数组中，所以 6 是大于等于最长顺序前缀和的最小整数。
 * <p>
 * 输入：nums = [3,4,5,1,12,14,13]
 * 输出：15
 * 解释：nums 的最长顺序前缀是 [3,4,5] ，和为 12 ，12、13 和 14 都在数组中，但 15 不在，
 * 所以 15 是大于等于最长顺序前缀和的最小整数。
 * <p>
 * 1 <= nums.length <= 50
 * 1 <= nums[i] <= 50
 */
public class Problem2996 {
    public static void main(String[] args) {
        Problem2996 problem2996 = new Problem2996();
//        int[] nums = {1, 2, 3, 2, 5};
        int[] nums = {37, 1, 2, 9, 5, 8, 5, 2, 9, 4};
        System.out.println(problem2996.missingInteger(nums));
    }

    /**
     * 模拟+哈希表
     * 注意：求从nums[0]开始的最长顺序前缀，而不是求数组中的最长顺序前缀
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int missingInteger(int[] nums) {
        //从nums[0]开始的最长顺序前缀之和
        int sum = nums[0];
        int index = 0;

        while (index + 1 < nums.length && nums[index] + 1 == nums[index + 1]) {
            sum = sum + nums[index + 1];
            index++;
        }

        Set<Integer> set = new HashSet<>();

        for (int num : nums) {
            set.add(num);
        }

        while (set.contains(sum)) {
            sum++;
        }

        return sum;
    }
}
