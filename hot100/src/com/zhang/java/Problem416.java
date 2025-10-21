package com.zhang.java;

/**
 * @Date 2022/6/6 8:44
 * @Author zsy
 * @Description 分割等和子集 01背包类比Problem474、Problem494、Problem2291 集合划分类比Problem473、Problem698、Problem1723、Problem2305 动态规划类比Problem198、Problem213、Problem256、Problem265、Problem279、Problem322、Problem338、Problem343、Problem377、Problem474、Problem494、Problem518、Problem746、Problem983、Problem1340、Problem1388、Problem1444、Problem1473、Offer14、Offer14_2、Offer60、CircleBackToOrigin、Knapsack
 * 给你一个 只包含正整数 的 非空 数组 nums 。请你判断是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
 * <p>
 * 输入：nums = [1,5,11,5]
 * 输出：true
 * 解释：数组可以分割成 [1, 5, 5] 和 [11] 。
 * <p>
 * 输入：nums = [1,2,3,5]
 * 输出：false
 * 解释：数组不能分割成两个元素和相等的子集。
 * <p>
 * 1 <= nums.length <= 200
 * 1 <= nums[i] <= 100
 */
public class Problem416 {
    public static void main(String[] args) {
        Problem416 problem416 = new Problem416();
//        int[] nums = {1, 5, 11, 5};
        int[] nums = {1, 1};
        System.out.println(problem416.canPartition(nums));
        System.out.println(problem416.canPartition2(nums));
        System.out.println(problem416.canPartition3(nums));
    }

    /**
     * 动态规划 01背包
     * dp[i][j]：nums[0]-nums[i-1]是否存在和为j的方案
     * dp[i][j] = dp[i-1][j]                           (nums[i-1] > j)
     * dp[i][j] = dp[i-1][j] || dp[i-1][j-nums[i-1]]   (nums[i-1] <= j)
     * 时间复杂度O(n*sum)，空间复杂度O(n*sum) (n=nums.length，sum=sum(nums[i]))
     *
     * @param nums
     * @return
     */
    public boolean canPartition(int[] nums) {
        int sum = 0;

        for (int num : nums) {
            sum = sum + num;
        }

        //数组元素之和不能被2整除，则不能分割为两个元素和相等的子集
        if (sum % 2 != 0) {
            return false;
        }

        boolean[][] dp = new boolean[nums.length + 1][sum / 2 + 1];

        //dp初始化，前0个元素存在和为0的方案
        dp[0][0] = true;

        for (int i = 1; i <= nums.length; i++) {
            for (int j = 0; j <= sum / 2; j++) {
                if (nums[i - 1] > j) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums[i - 1]];
                }
            }
        }

        return dp[nums.length][sum / 2];
    }

    /**
     * 动态规划优化，使用滚动数组
     * dp[j]：nums[0]-nums[i-1]是否存在和为j的方案
     * dp[j] = dp[j]                    (nums[i-1] > j)
     * dp[j] = dp[j] || [j-nums[i-1]]   (nums[i-1] <= j)
     * 时间复杂度O(n*sum)，空间复杂度O(sum) (n=nums.length，sum=sum(nums[i]))
     *
     * @param nums
     * @return
     */
    public boolean canPartition2(int[] nums) {
        int sum = 0;

        for (int num : nums) {
            sum = sum + num;
        }

        //数组元素之和不能被2整除，则不能分割为两个元素和相等的子集
        if (sum % 2 != 0) {
            return false;
        }

        boolean[] dp = new boolean[sum / 2 + 1];
        //dp初始化，前0个元素存在和为0的方案
        dp[0] = true;

        for (int i = 1; i <= nums.length; i++) {
            //注意：当前dp[j]会使用到前面的dp，所以逆序遍历
            for (int j = sum / 2; j >= 0; j--) {
                if (nums[i - 1] <= j) {
                    dp[j] = dp[j] || dp[j - nums[i - 1]];
                }
            }
        }

        return dp[sum / 2];
    }

    /**
     * 递归+记忆化搜索
     * 时间复杂度O(n*sum)，空间复杂度O(n*sum) (n=nums.length，sum=sum(nums[i]))
     *
     * @param nums
     * @return
     */
    public boolean canPartition3(int[] nums) {
        int sum = 0;

        for (int num : nums) {
            sum = sum + num;
        }

        //数组元素之和不能被2整除，则不能分割为两个元素和相等的子集
        if (sum % 2 != 0) {
            return false;
        }

        //注意：和上面dp不同，当前dp为int类型，
        //-1表示未访问，0表示当前dp无法得到对应和，1表示当前dp可以得到对应和
        int[][] dp = new int[nums.length + 1][sum / 2 + 1];

        for (int i = 0; i <= nums.length; i++) {
            for (int j = 0; j <= sum / 2; j++) {
                //初始化为-1，表示当前dp未访问
                dp[i][j] = -1;
            }
        }

        dfs(nums.length, sum / 2, nums, dp);

        return dp[nums.length][sum / 2] == 1;
    }

    private int dfs(int i, int sum, int[] nums, int[][] dp) {
        if (i == 0) {
            //前0个元素存在和为0的方案
            if (sum == 0) {
                dp[i][sum] = 1;
                return dp[i][sum];
            } else {
                //前0个元素不存在和大于0的方案
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

        dp[i][sum] = dfs(i - 1, sum, nums, dp) | dfs(i - 1, sum - nums[i - 1], nums, dp);
        return dp[i][sum];
    }
}
