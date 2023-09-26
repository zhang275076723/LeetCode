package com.zhang.java;

/**
 * @Date 2022/5/23 11:27
 * @Author zsy
 * @Description 完全平方数 动态规划类比Problem198、Problem213、Problem256、Problem265、Problem322、Problem338、Problem343、Problem377、Problem416、Problem474、Problem494、Problem518、Problem746、Problem983、Problem1340、Problem1388、Problem1444、Problem1473、Offer14、Offer14_2、Offer60、CircleBackToOrigin、Knapsack
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
    /**
     * 回溯+剪枝使用的和为n需要完全平方数的最少数量
     */
    private int minCount = Integer.MAX_VALUE;

    public static void main(String[] args) {
        Problem279 problem279 = new Problem279();
        System.out.println(problem279.numSquares(13));
        System.out.println(problem279.numSquares2(13));
        System.out.println(problem279.numSquares3(13));
    }

    /**
     * 动态规划
     * dp[i]：和为i需要的完全平方数的最少数量
     * dp[i] = min(dp[i-j^2] + 1) (1 <= j <= i^1/2)
     * 时间复杂度O(n^(3/2))，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public int numSquares(int n) {
        if (n == 1) {
            return 1;
        }

        //dp[0]=0，用于初始化
        int[] dp = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            //初始化当前数字i最少由i个1平方之和
            dp[i] = i;

            for (int j = 1; j * j <= i; j++) {
                dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
            }
        }

        return dp[n];
    }

    /**
     * 四平方和定理：任意一个正整数都可以表示为至多四个正整数的平方和
     * 如果n为完全平方数，则n可以拆分为1个正整数平方和
     * 如果n=4^k*(8m+7)，则n可以拆分为4个正整数平方和
     * 如果n=a^2+b^2，则n可以拆分为2个正整数平方和
     * 如果n不能拆分为1、2、4个正整数平方和，则n肯定能拆分为3个正整数平方和
     * 时间复杂度O(n^1/2)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int numSquares2(int n) {
        if (n == 1) {
            return 1;
        }

        //n能否拆分为1个正整数平方和
        int sqrt = (int) Math.sqrt(n);

        if (sqrt * sqrt == n) {
            return 1;
        }

        //n能否表示成n=4^k*(8m+7)，拆分为4个正整数平方和
        int x = n;

        while (x % 4 == 0) {
            x = x / 4;
        }

        if (x % 8 == 7) {
            return 4;
        }

        //n是否能表示为n=a^2+b^2，拆分为2个正整数平方和
        for (int i = 1; i * i < n; i++) {
            int j = n - i * i;
            int sqrt2 = (int) Math.sqrt(j);

            //j开方为整数，说明i^2+j^2==n
            if (sqrt2 * sqrt2 == j) {
                return 2;
            }
        }

        //n不能拆分为1、2、4个正整数平方和，则n肯定能拆分为3个正整数平方和
        return 3;
    }

    /**
     * 回溯+剪枝
     *
     * @param n
     * @return
     */
    public int numSquares3(int n) {
        if (n == 1) {
            return 1;
        }

        backtrack(n, 0);

        return minCount;
    }

    private void backtrack(int n, int count) {
        //最少所需完全平方数的数量小于等于当前所需的数量，则直接剪枝
        if (minCount <= count) {
            return;
        }

        if (n == 0) {
            minCount = Math.min(minCount, count);
            return;
        }

        //由大到小找，可以加快查找速度
        for (int i = (int) Math.sqrt(n); i > 0; i--) {
            backtrack(n - i * i, count + 1);
        }
    }
}
