package com.zhang.java;

/**
 * @Date 2022/6/10 10:30
 * @Author zsy
 * @Description 目标和 01背包类比Problem416、Problem474、Problem2291、Problem2742 动态规划类比Problem198、Problem213、Problem256、Problem265、Problem279、Problem322、Problem338、Problem343、Problem377、Problem416、Problem474、Problem518、Problem746、Problem983、Problem1340、Problem1388、Problem1444、Problem1473、Offer14、Offer14_2、Offer60、CircleBackToOrigin、Knapsack 回溯+剪枝类比
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
    public static void main(String[] args) {
        Problem494 problem494 = new Problem494();
//        int[] nums = {1, 1, 1, 1, 1};
//        int target = 3;
        int[] nums = {1, 0};
        int target = 1;
        System.out.println(problem494.findTargetSumWays(nums, target));
        System.out.println(problem494.findTargetSumWays2(nums, target));
        System.out.println(problem494.findTargetSumWays3(nums, target));
        System.out.println(problem494.findTargetSumWays4(nums, target));
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
        return backtrack(0, 0, nums, target);
    }

    /**
     * 动态规划 01背包
     * 设选择负号的元素之和为neg，选择正号的元素之和为pos，所有元素之和为sum，即sum=pos+neg
     * target=pos-neg=sum-2neg ===> neg=(sum-target)/2
     * dp[i][j]：nums[i]-nums[i-1]和为j的方案数
     * dp[i][j] = dp[i-1][j]                        (nums[i-1] > j)
     * dp[i][j] = dp[i-1][j] + dp[i-1][j-nums[i-1]] (nums[i-1] <= j)
     * 时间复杂度O(n*(sum-target))，空间复杂度O(n*(sum-target)) (n=nums.length，sum=sum(nums[i]))
     *
     * @param nums
     * @param target
     * @return
     */
    public int findTargetSumWays2(int[] nums, int target) {
        int sum = 0;

        for (int num : nums) {
            sum = sum + num;
        }

        //sum-target小于0，或者sum-target不能被2整除，则不能得到neg=(sum-target)/2，直接返回0
        if (sum - target < 0 || (sum - target) % 2 != 0) {
            return 0;
        }

        //数组中负数之和
        int neg = (sum - target) / 2;
        //注意：dp定义也可以为[nums.length+1][pos+1]，只不过neg小于pos，使用neg的二维常数时空复杂度小于pos
        int[][] dp = new int[nums.length + 1][neg + 1];
        //dp初始化，前0个元素和为0的方案数为1
        dp[0][0] = 1;

        for (int i = 1; i <= nums.length; i++) {
            //注意从0开始遍历，因为nums[i-1]可能为0
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
     * dp[j]：nums[0]-nums[i-1]和为j的方案数
     * dp[j] = dp[j]                   (nums[i-1] > j)
     * dp[j] = dp[j] + dp[j-nums[i-1]] (nums[i-1] <= j)
     * 时间复杂度O(n*(sum-target))，空间复杂度O(sum-target) (n=nums.length，sum=sum(nums[i]))
     *
     * @param nums
     * @param target
     * @return
     */
    public int findTargetSumWays3(int[] nums, int target) {
        int sum = 0;

        for (int num : nums) {
            sum = sum + num;
        }

        //sum-target小于0，或者sum-target不能被2整除，则不能得到neg=(sum-target)/2，直接返回0
        if (sum - target < 0 || (sum - target) % 2 != 0) {
            return 0;
        }

        //数组中负数之和
        int neg = (sum - target) / 2;
        int[] dp = new int[neg + 1];
        //dp初始化，前0个元素和为0的方案数为1
        dp[0] = 1;

        for (int i = 1; i <= nums.length; i++) {
            //注意：当前dp[j]会使用到前面的dp，所以逆序遍历
            for (int j = neg; j >= 0; j--) {
                if (nums[i - 1] <= j) {
                    dp[j] = dp[j] + dp[j - nums[i - 1]];
                }
            }
        }

        return dp[neg];
    }

    /**
     * 递归+记忆化搜索
     * 时间复杂度O(n*(sum-target))，空间复杂度O(n*(sum-target)) (n=nums.length，sum=sum(nums[i]))
     *
     * @param nums
     * @param target
     * @return
     */
    public int findTargetSumWays4(int[] nums, int target) {
        int sum = 0;

        for (int num : nums) {
            sum = sum + num;
        }

        //sum-target小于0，或者sum-target不能被2整除，则不能得到neg=(sum-target)/2，直接返回0
        if (sum - target < 0 || (sum - target) % 2 != 0) {
            return 0;
        }

        //数组中负数之和
        int neg = (sum - target) / 2;
        int[][] dp = new int[nums.length + 1][neg + 1];

        for (int i = 0; i <= nums.length; i++) {
            for (int j = 0; j <= neg; j++) {
                //初始化为-1，表示当前dp未访问
                dp[i][j] = -1;
            }
        }

        dfs(nums.length, neg, nums, dp);

        return dp[nums.length][neg];
    }

    private int backtrack(int t, int sum, int[] nums, int target) {
        if (t == nums.length) {
            if (sum == target) {
                return 1;
            } else {
                return 0;
            }
        }

        int count = 0;

        //加上nums[t]
        count = count + backtrack(t + 1, sum + nums[t], nums, target);

        //减去nums[t]
        count = count + backtrack(t + 1, sum - nums[t], nums, target);

        return count;
    }

    private int dfs(int i, int sum, int[] nums, int[][] dp) {
        if (i == 0) {
            //前0个元素和为0的方案为1
            if (sum == 0) {
                dp[i][sum] = 1;
                return dp[i][sum];
            } else {
                //前0个元素和大于0的方案为0
                dp[i][sum] = 0;
                return dp[i][sum];
            }
        }

        //当前dp已访问，直接返回当前dp
        if (dp[i][sum] != -1) {
            return dp[i][sum];
        }

        if (nums[i - 1] > sum) {
            dp[i][sum] = dfs(i - 1, sum, nums, dp);
            return dp[i][sum];
        }

        dp[i][sum] = dfs(i - 1, sum, nums, dp) + dfs(i - 1, sum - nums[i - 1], nums, dp);
        return dp[i][sum];
    }
}
