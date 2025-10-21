package com.zhang.java;


/**
 * @Date 2023/3/23 09:04
 * @Author zsy
 * @Description 组合总和 IV 类比Problem39、Problem40、Problem216 完全背包类比Problem279、Problem322、Problem518 回溯+剪枝类比Problem17、Problem22、Problem39、Problem40、Problem46、Problem47、Problem77、Problem78、Problem89、Problem90、Problem97、Problem216、Problem301、Problem491、Problem679、Problem698、Offer17、Offer38 动态规划类比Problem198、Problem213、Problem256、Problem265、Problem279、Problem322、Problem338、Problem343、Problem416、Problem474、Problem494、Problem518、Problem746、Problem983、Problem1340、Problem1388、Problem1444、Problem1473、Offer14、Offer14_2、Offer60、CircleBackToOrigin、Knapsack
 * 给你一个由 不同 整数组成的数组 nums ，和一个目标整数 target 。
 * 请你从 nums 中找出并返回总和为 target 的元素组合的个数。
 * 题目数据保证答案符合 32 位整数范围。
 * 进阶：如果给定的数组中含有负数会发生什么？问题会产生何种变化？如果允许负数出现，需要向题目中添加哪些限制条件？
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
        System.out.println(problem377.combinationSum4_3(nums, target));
        System.out.println(problem377.combinationSum4_4(nums, target));
    }

    /**
     * 回溯+剪枝
     * 时间复杂度O(n^n)，空间复杂度O(n)
     * <p>
     * 注意：如果给定的数组元素有负数，则由于正数与负数可以抵消，因此会导致出现无限长度的序列；
     * 如果允许负数出现，则必须限制序列的最大长度，避免出现无限长度的序列
     *
     * @param nums
     * @param target
     * @return
     */
    public int combinationSum4(int[] nums, int target) {
        return backtrack(0, nums, target);
    }

    /**
     * 动态规划 完全背包
     * 注意：当前组合要考虑不同顺序，实际为排列
     * dp[i][j]：nums[0]-nums[i-1]总和为j的组合个数
     * dp[i][j] = sum(dp[i][j-nums[k]]) (0 <= k < i && j - nums[k] >= 0)
     * 时间复杂度O(n^2*target)，空间复杂度O(n*target)
     *
     * @param nums
     * @param target
     * @return
     */
    public int combinationSum4_2(int[] nums, int target) {
        int[][] dp = new int[nums.length + 1][target + 1];

        //dp初始化，前i个nums中元素总和为0的组合个数为1
        for (int i = 0; i <= nums.length; i++) {
            dp[i][0] = 1;
        }

        for (int i = 1; i <= nums.length; i++) {
            for (int j = 1; j <= target; j++) {
                //注意：当前组合要考虑不同顺序，实际为排列，则需要考虑nums[i-1]之前的元素nums[k]作为组合末尾元素的情况
                for (int k = 0; k < i; k++) {
                    if (j - nums[k] >= 0) {
                        dp[i][j] = dp[i][j] + dp[i][j - nums[k]];
                    }
                }
            }
        }

        return dp[nums.length][target];
    }

    /**
     * 递归+记忆化搜索
     * 时间复杂度O(n*target)，空间复杂度O(n*target)
     *
     * @param nums
     * @param target
     * @return
     */
    public int combinationSum4_3(int[] nums, int target) {
        int[][] dp = new int[nums.length + 1][target + 1];

        for (int i = 0; i <= nums.length; i++) {
            for (int j = 0; j <= target; j++) {
                //初始化为-1，表示当前dp未访问
                dp[i][j] = -1;
            }
        }

        dfs(nums.length, target, nums, dp);

        return dp[nums.length][target];
    }

    /**
     * 动态规划2 (本质为上面动态规划的空间优化)
     * 注意：当前组合要考虑不同顺序，实际为排列
     * dp[i]：总和为i的组合个数
     * dp[i] = sum(dp[i-nums[j]]) (0 <= j < nums.length && i - nums[j] >= 0)
     * 时间复杂度O(n*target)，空间复杂度O(target)
     *
     * @param nums
     * @param target
     * @return
     */
    public int combinationSum4_4(int[] nums, int target) {
        int[] dp = new int[target + 1];
        //dp初始化，总和为0的组合个数为1
        dp[0] = 1;

        for (int i = 1; i <= target; i++) {
            for (int j = 0; j < nums.length; j++) {
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

    private int dfs(int i, int target, int[] nums, int[][] dp) {
        //前i个nums中元素总和为0的组合个数为1
        if (target == 0) {
            dp[i][target] = 1;
            return dp[i][target];
        }

        //当前dp已访问，直接返回当前dp
        if (dp[i][target] != -1) {
            return dp[i][target];
        }

        //未访问dp未-1，需要初始化当前dp为0
        dp[i][target] = 0;

        for (int j = 0; j < i; j++) {
            if (nums[j] <= target) {
                //注意：当前组合要考虑不同顺序，实际为排列，则需要考虑nums[i-1]之前的元素nums[j]作为组合末尾元素的情况
                dp[i][target] = dp[i][target] + dfs(i, target - nums[j], nums, dp);
            }
        }

        return dp[i][target];
    }
}
