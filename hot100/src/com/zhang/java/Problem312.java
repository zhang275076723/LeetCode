package com.zhang.java;

/**
 * @Date 2022/5/30 12:06
 * @Author zsy
 * @Description 戳气球
 * 有 n 个气球，编号为0 到 n - 1，每个气球上都标有一个数字，这些数字存在数组 nums 中。
 * 现在要求你戳破所有的气球。戳破第 i 个气球，你可以获得 nums[i - 1] * nums[i] * nums[i + 1] 枚硬币。
 * 这里的 i - 1 和 i + 1 代表和 i 相邻的两个气球的序号。
 * 如果 i - 1或 i + 1 超出了数组的边界，那么就当它是一个数字为 1 的气球。
 * 求所能获得硬币的最大数量。
 * <p>
 * 输入：nums = [3,1,5,8]
 * 输出：167
 * 解释：
 * nums = [3,1,5,8] --> [3,5,8] --> [3,8] --> [8] --> []
 * coins =  3*1*5    +   3*5*8   +  1*3*8  + 1*8*1 = 167
 * <p>
 * 输入：nums = [1,5]
 * 输出：10
 * <p>
 * n == nums.length
 * 1 <= n <= 300
 * 0 <= nums[i] <= 100
 */
public class Problem312 {
    public static void main(String[] args) {
        Problem312 problem312 = new Problem312();
        int[] nums = new int[]{3, 1, 5, 8};
        System.out.println(problem312.maxCoins(nums));
    }

    /**
     * 动态规划
     * dp[i][j]：将(i,j)(即第i个气球和第j个气球)之内的气球全部戳完能得到的最多硬币
     * dp[i][j] = max(dp[i][k] + dp[k][j] + nums[i] * nums[k] * nums[j]) (i<k<j)
     * 时间复杂度O(n^3)，空间复杂度O(n^2)
     *
     * @param nums
     * @return
     */
    public int maxCoins(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        //dp[i][j]：将(i,j)(即第i个气球和第j个气球)之内的气球全部戳完能得到的最多硬币
        int[][] dp = new int[nums.length + 2][nums.length + 2];

        //辅助数组，在首尾各添加1，方便处理边界情况，
        //因为如果要戳的气球是第一个元素或最后一个元素，则当前气球的前或后超出边界，设置为1
        int[] temp = new int[nums.length + 2];
        temp[0] = 1;
        temp[nums.length + 1] = 1;
        for (int i = 0; i < nums.length; i++) {
            temp[i + 1] = nums[i];
        }

        //区间长度i
        for (int i = 3; i <= nums.length + 2; i++) {
            //区间起始位置j
            for (int j = 0; j <= nums.length + 2 - i; j++) {
                //长度为i的区间之内要戳的气球k
                for (int k = j + 1; k <= i + j - 2; k++) {
                    dp[j][i + j - 1] = Math.max(dp[j][i + j - 1],
                            dp[j][k] + dp[k][i + j - 1] + temp[j] * temp[k] * temp[i + j - 1]);
                }
            }
        }

        return dp[0][nums.length + 1];
    }
}
