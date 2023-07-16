package com.zhang.java;


/**
 * @Date 2023/3/23 09:04
 * @Author zsy
 * @Description 组合总和 IV 回溯+剪枝类比Problem17、Problem39、Problem40、Problem46、Problem47、Problem77、Problem78、Problem89、Problem90、Problem97、Problem216、Problem301、Problem491、Problem679、Problem698、Offer17、Offer38 动态规划类比Problem279、Problem322、Problem343、Problem416、Problem494、Problem518、Problem983、Offer14、Offer14_2、Knapsack
 * 给你一个由 不同 整数组成的数组 nums ，和一个目标整数 target 。
 * 请你从 nums 中找出并返回总和为 target 的元素组合的个数。
 * 题目数据保证答案符合 32 位整数范围。
 * <p>
 * 输入：nums = [1,2,3], target = 4
 * 输出：7
 * 解释：
 * 所有可能的组合为：
 * (1, 1, 1, 1)
 * (1, 1, 2)
 * (1, 2, 1)
 * (1, 3)
 * (2, 1, 1)
 * (2, 2)
 * (3, 1)
 * 请注意，顺序不同的序列被视作不同的组合。
 * <p>
 * 输入：nums = [9], target = 3
 * 输出：0
 * <p>
 * 1 <= nums.length <= 200
 * 1 <= nums[i] <= 1000
 * nums 中的所有元素 互不相同
 * 1 <= target <= 1000
 */
public class Problem377 {
    public static void main(String[] args) {
        Problem377 problem377 = new Problem377();
        int[] nums = {1, 2, 3};
        int target = 4;
        System.out.println(problem377.combinationSum4(nums, target));
        System.out.println(problem377.combinationSum4_2(nums, target));
    }

    /**
     * 回溯+剪枝
     * 时间复杂度O(n^n)，空间复杂度O(n)
     *
     * @param nums
     * @param target
     * @return
     */
    public int combinationSum4(int[] nums, int target) {
        return backtrack(0, nums, target);
    }

    /**
     * 动态规划
     * dp[i]：总和为i的组合个数
     * dp[i] = sum(dp[i-nums[j]]) (0 <= j < nums.length)
     * 时间复杂度O(n*target)，空间复杂度O(target)
     *
     * @param nums
     * @param target
     * @return
     */
    public int combinationSum4_2(int[] nums, int target) {
        int[] dp = new int[target + 1];
        //dp[0]=0，用于初始化，表示总和为0的组合个数为1个
        dp[0] = 1;

        for (int i = 1; i <= target; i++) {
            for (int j = 0; j < nums.length; j++) {
                //总和为i-nums[j]大于等于0，则总和为i-nums[j]的组合个数可以考虑
                if (i - nums[j] >= 0) {
                    dp[i] = dp[i] + dp[i - nums[j]];
                }
            }
        }

        return dp[target];
    }

    private int backtrack(int sum, int[] nums, int target) {
        if (sum > target) {
            return 0;
        }

        if (sum == target) {
            return 1;
        }

        int count = 0;

        for (int i = 0; i < nums.length; i++) {
            count = count + backtrack(sum + nums[i], nums, target);
        }

        return count;
    }
}
