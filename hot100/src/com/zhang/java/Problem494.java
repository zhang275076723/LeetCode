package com.zhang.java;

/**
 * @Date 2022/6/10 10:30
 * @Author zsy
 * @Description 目标和 类比Problem279、Problem322、Problem416、Problem518
 * 给你一个整数数组 nums 和一个整数 target 。
 * 向数组中的每个整数前添加 '+' 或 '-' ，然后串联起所有整数，可以构造一个 表达式 ：
 * 例如，nums = [2, 1] ，可以在 2 之前添加 '+' ，在 1 之前添加 '-' ，然后串联起来得到表达式 "+2-1" 。
 * 返回可以通过上述方法构造的、运算结果等于 target 的不同 表达式 的数目。
 * <p>
 * 输入：nums = [1,1,1,1,1], target = 3
 * 输出：5
 * 解释：一共有 5 种方法让最终目标和为 3 。
 * -1 + 1 + 1 + 1 + 1 = 3
 * +1 - 1 + 1 + 1 + 1 = 3
 * +1 + 1 - 1 + 1 + 1 = 3
 * +1 + 1 + 1 - 1 + 1 = 3
 * +1 + 1 + 1 + 1 - 1 = 3
 * <p>
 * 输入：nums = [1], target = 1
 * 输出：1
 * <p>
 * 1 <= nums.length <= 20
 * 0 <= nums[i] <= 1000
 * 0 <= sum(nums[i]) <= 1000
 * -1000 <= target <= 1000
 */
public class Problem494 {
    private int count;

    public static void main(String[] args) {
        Problem494 problem494 = new Problem494();
        int[] nums = {1, 1, 1, 1, 1};
        int target = 3;
        System.out.println(problem494.findTargetSumWays(nums, target));
        System.out.println(problem494.findTargetSumWays2(nums, target));
        System.out.println(problem494.findTargetSumWays3(nums, target));
    }

    /**
     * 回溯
     * 时间复杂度O(2^n)，空间复杂度O(n)
     *
     * @param nums
     * @param target
     * @return
     */
    public int findTargetSumWays(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        backtrack(nums, 0, target, 0);
        return count;
    }

    /**
     * 动态规划，01背包
     * 设选择负号的元素之和为negative，选择正号的元素之和为positive，所有元素之和为sum，即sum=pos+neg
     * target=pos-neg=sum-2neg ===> neg=(sum-target)/2
     * dp[i][j]：前i个元素中选元素之和为j的方案数
     * dp[i][j] = dp[i-1][j]                        (nums[i-1] > j)
     * dp[i][j] = dp[i-1][j] + dp[i-1][j-nums[i-1]] (nums[i-1] <= j)
     * 结果为dp[nums.length-1][neg]
     * 时间复杂度O(n*(sum-target))，空间复杂度O(n*(sum-target))
     *
     * @param nums
     * @param target
     * @return
     */
    public int findTargetSumWays2(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int sum = 0;
        for (int num : nums) {
            sum = sum + num;
        }

        //如果sum小于target，或者sum-target不是偶数，则不符合条件，直接返回0
        if (sum - target < 0 || (sum - target) % 2 == 1) {
            return 0;
        }

        int neg = (sum - target) / 2;
        int[][] dp = new int[nums.length + 1][neg + 1];
        dp[0][0] = 1;

        for (int i = 1; i <= nums.length; i++) {
            for (int j = 0; j <= neg; j++) {
                if (nums[i - 1] > j) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j] + dp[i - 1][j - nums[i - 1]];
                }
            }
        }

        return dp[nums.length][neg];
    }

    /**
     * 动态规划优化，使用滚动数组
     * dp[j]：前i个元素中选元素之和为j的方案数
     * dp[j] = dp[j]                   (nums[i-1]>j)
     * dp[j] = dp[j] + dp[j-nums[i-1]] (nums[i-1]<=j)
     * 结果为dp[neg]
     * 时间复杂度O(n*(sum-target))，空间复杂度O(sum-target)
     *
     * @param nums
     * @param target
     * @return
     */
    public int findTargetSumWays3(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int sum = 0;
        for (int num : nums) {
            sum = sum + num;
        }

        //如果sum小于target，或者sum-target不是偶数，则不符合条件，直接返回0
        if (sum - target < 0 || (sum - target) % 2 == 1) {
            return 0;
        }

        int neg = (sum - target) / 2;
        int[] dp = new int[neg + 1];
        dp[0] = 1;

        for (int i = 1; i <= nums.length; i++) {
            //因为当前dp[j]会使用到前面的dp，所以逆序遍历
            for (int j = neg; j >= 0; j--) {
                if (nums[i - 1] <= j) {
                    dp[j] = dp[j] + dp[j - nums[i - 1]];
                }
            }
        }

        return dp[neg];
    }

    /**
     * @param nums   数组元素
     * @param t      前t个元素进行了计算
     * @param target 目标和
     * @param sum    前t个元素加正负符号之和
     */
    private void backtrack(int[] nums, int t, int target, int sum) {
        if (t == nums.length) {
            if (target == sum) {
                count++;
            }
            return;
        }

        backtrack(nums, t + 1, target, sum + nums[t]);
        backtrack(nums, t + 1, target, sum - nums[t]);
    }
}
