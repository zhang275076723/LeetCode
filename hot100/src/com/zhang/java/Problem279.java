package com.zhang.java;

/**
 * @Date 2022/5/23 11:27
 * @Author zsy
 * @Description 完全平方数 完全背包类比Problem322、Problem377、Problem518 动态规划类比Problem198、Problem213、Problem256、Problem265、Problem322、Problem338、Problem343、Problem377、Problem416、Problem474、Problem494、Problem518、Problem746、Problem983、Problem1340、Problem1388、Problem1444、Problem1473、Offer14、Offer14_2、Offer60、CircleBackToOrigin、Knapsack
 * 给你一个整数 n ，返回 和为 n 的完全平方数的最少数量 。
 * 完全平方数 是一个整数，其值等于另一个整数的平方；换句话说，其值等于一个整数自乘的积。
 * 例如，1、4、9 和 16 都是完全平方数，而 3 和 11 不是。
 * <p>
 * 输入：n = 12
 * 输出：3
 * 解释：12 = 4 + 4 + 4
 * <p>
 * 输入：n = 13
 * 输出：2
 * 解释：13 = 4 + 9
 * <p>
 * 1 <= n <= 10^4
 */
public class Problem279 {
    //最大值为int最大值除以2，避免相加在int范围内溢出
    private final int INF = Integer.MAX_VALUE / 2;

    public static void main(String[] args) {
        Problem279 problem279 = new Problem279();
        int n = 13;
        System.out.println(problem279.numSquares(n));
        System.out.println(problem279.numSquares2(n));
        System.out.println(problem279.numSquares3(n));
        System.out.println(problem279.numSquares4(n));
    }

    /**
     * 动态规划
     * dp[i]：构成数字i需要的最少完全平方数的个数
     * dp[i] = min(dp[i-j^2] + 1) (j*j <= i)
     * 时间复杂度O(n^(3/2))，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public int numSquares(int n) {
        int[] dp = new int[n + 1];

        //dp初始化，数字0由0个完全平方数构成
        dp[0] = 0;

        //dp初始化，数字i最多由i个完全平方数1构成
        for (int i = 1; i <= n; i++) {
            dp[i] = i;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j * j <= i; j++) {
                dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
            }
        }

        return dp[n];
    }

    /**
     * 数学
     * 四平方和定理：任意一个正整数都可以表示为至多4个完全平方数之和
     * 如果n开根为整数，则n可以拆分为1个完全平方数
     * 如果n=4^k*(8m+7)，则n可以拆分为4个完全平方数
     * 如果n=a^2+b^2，则n可以拆分为2个完全平方数
     * 如果n不能拆分为1、2、4个完全平方数，则n只能拆分为3个完全平方数
     * 时间复杂度O(n^1/2)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int numSquares2(int n) {
        //n能否拆分为1个完全平方数
        int sqrt = (int) Math.sqrt(n);

        if (sqrt * sqrt == n) {
            return 1;
        }

        //n能否表示成n=4^k*(8m+7)，拆分为4个完全平方数
        int temp = n;

        while (temp % 4 == 0) {
            temp = temp / 4;
        }

        if (temp % 8 == 7) {
            return 4;
        }

        //n是否能表示为n=a^2+b^2，拆分为2个完全平方数
        for (int i = 1; i * i < n; i++) {
            int temp2 = n - i * i;
            int j = (int) Math.sqrt(temp2);

            //temp2开根为整数，则说明i^2+j^2==n
            if (j * j == temp2) {
                return 2;
            }
        }

        //n不能拆分为1、2、4个完全平方数，则n只能拆分为3个完全平方数
        return 3;
    }

    /**
     * 动态规划 完全背包
     * dp[i][j]：前i个完全平方数构成数字j需要的最少完全平方数的个数
     * dp[i][j] = dp[i-1][j]                     (i*i > j)
     * dp[i][j] = min(dp[i-1][j],dp[i][j-i*i]+1) (i*i <= j)
     * 时间复杂度O(n^(3/2))，空间复杂度O(n^(3/2))
     *
     * @param n
     * @return
     */
    public int numSquares3(int n) {
        int[][] dp = new int[(int) Math.sqrt(n) + 1][n + 1];

        //dp初始化，前0个完全平方数构成数字0需要的最少完全平方数的个数为0
        dp[0][0] = 0;

        //dp初始化，前0个完全平方数无法构成数字j
        for (int j = 1; j <= n; j++) {
            dp[0][j] = INF;
        }

        for (int i = 1; i <= (int) Math.sqrt(n); i++) {
            for (int j = 0; j <= n; j++) {
                if (i * i > j) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - i * i] + 1);
                }
            }
        }

        return dp[(int) Math.sqrt(n)][n];
    }

    /**
     * 递归+记忆化搜索
     * 时间复杂度O(n^(3/2))，空间复杂度O(n^(3/2))
     *
     * @param n
     * @return
     */
    public int numSquares4(int n) {
        int[][] dp = new int[(int) Math.sqrt(n) + 1][n + 1];

        for (int i = 0; i <= Math.sqrt(n); i++) {
            for (int j = 0; j <= n; j++) {
                //初始化为-1，表示当前dp未访问
                dp[i][j] = -1;
            }
        }

        dfs((int) Math.sqrt(n), n, dp);

        return dp[(int) Math.sqrt(n)][n];
    }

    private int dfs(int i, int n, int[][] dp) {
        if (i == 0) {
            //前0个完全平方数构成数字0需要的最少完全平方数的个数为0
            if (n == 0) {
                dp[i][n] = 0;
                return dp[i][n];
            } else {
                //前0个完全平方数无法构成大于0的数字n
                dp[i][n] = INF;
                return dp[i][n];
            }
        }

        //当前dp已访问，直接返回当前dp
        if (dp[i][n] != -1) {
            return dp[i][n];
        }

        if (i * i > n) {
            dp[i][n] = dfs(i - 1, n, dp);
            return dp[i][n];
        }

        dp[i][n] = Math.min(dfs(i - 1, n, dp), dfs(i, n - i * i, dp) + 1);
        return dp[i][n];
    }
}
