package com.zhang.java;

/**
 * @Date 2025/3/18 08:09
 * @Author zsy
 * @Description 多米诺和托米诺平铺 矩阵快速幂类比Problem70、Problem509、Problem1137、Problem1220、Problem1641、Offer10、Offer10_2
 * 有两种形状的瓷砖：一种是 2 x 1 的多米诺形，另一种是形如 "L" 的托米诺形。两种形状都可以旋转。
 * 给定整数 n ，返回可以平铺 2 x n 的面板的方法的数量。返回对 10^9 + 7 取模 的值。
 * 平铺指的是每个正方形都必须有瓷砖覆盖。
 * 两个平铺不同，当且仅当面板上有四个方向上的相邻单元中的两个，使得恰好有一个平铺有一个瓷砖占据两个正方形。
 * <p>
 * 输入: n = 3
 * 输出: 5
 * 解释: 五种不同的方法如上所示。
 * <p>
 * 输入: n = 1
 * 输出: 1
 * <p>
 * 1 <= n <= 1000
 */
public class Problem790 {
    private final int MOD = (int) 1e9 + 7;

    public static void main(String[] args) {
        Problem790 problem790 = new Problem790();
        int n = 3;
        System.out.println(problem790.numTilings(n));
        System.out.println(problem790.numTilings2(n));
        System.out.println(problem790.numTilings3(n));
    }

    /**
     * 动态规划
     * dp[i][j]：前i-1列平铺，第i列是第j种情况的方法数量
     * dp[i][0] = dp[i-1][3]
     * dp[i][1] = dp[i-1][0] + dp[i-1][2]
     * dp[i][2] = dp[i-1][0] + dp[i-1][1]
     * dp[i][3] = dp[i-1][0] + dp[i-1][1] + dp[i-1][2] + dp[i-1][3]
     * 情况0：第i列的两个正方形都不存在
     * 情况1：第i列的上面正方形存在，下面正方形不存在
     * 情况2：第i列的上面正方形不存在，下面正方形存在
     * 情况3：第i列的两个正方形都存在
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public int numTilings(int n) {
        int[][] dp = new int[n + 1][4];

        //dp初始化
        //前0列平铺，第i列的两个正方形都不存在的方法数量为1
        dp[1][0] = 1;
        //前0列平铺，第i列的上面正方形存在，下面正方形不存在的方法数量为0
        dp[1][1] = 0;
        //前0列平铺，第i列的上面正方形不存在，下面正方形存在的方法数量为0
        dp[1][2] = 0;
        //前0列平铺，第i列的两个正方形都存在的方法数量为1
        dp[1][3] = 1;

        for (int i = 2; i <= n; i++) {
            dp[i][0] = dp[i - 1][3];
            dp[i][1] = (dp[i - 1][0] + dp[i - 1][2]) % MOD;
            dp[i][2] = (dp[i - 1][0] + dp[i - 1][1]) % MOD;
            dp[i][3] = (((dp[i - 1][0] + dp[i - 1][1]) % MOD + dp[i - 1][2]) % MOD + dp[i - 1][3]) % MOD;
        }

        return dp[n][3];
    }

    /**
     * 动态规划优化，使用滚动数组
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int numTilings2(int n) {
        int[] dp = new int[4];

        //dp初始化
        //前0列平铺，第i列的两个正方形都不存在的方法数量为1
        dp[0] = 1;
        //前0列平铺，第i列的上面正方形存在，下面正方形不存在的方法数量为0
        dp[1] = 0;
        //前0列平铺，第i列的上面正方形不存在，下面正方形存在的方法数量为0
        dp[2] = 0;
        //前0列平铺，第i列的两个正方形都存在的方法数量为1
        dp[3] = 1;

        for (int i = 2; i <= n; i++) {
            //临时数组，用于更新dp
            int[] temp = new int[4];

            temp[0] = dp[3];
            temp[1] = (dp[0] + dp[2]) % MOD;
            temp[2] = (dp[0] + dp[1]) % MOD;
            temp[3] = (((dp[0] + dp[1]) % MOD + dp[2]) % MOD + dp[3]) % MOD;

            dp = temp;
        }

        return dp[3];
    }

    /**
     * 矩阵快速幂
     * dp[i][j]：前i-1列平铺，第i列是第j种情况的方法数量
     * dp[i][0] = dp[i-1][3]
     * dp[i][1] = dp[i-1][0] + dp[i-1][2]
     * dp[i][2] = dp[i-1][0] + dp[i-1][1]
     * dp[i][3] = dp[i-1][0] + dp[i-1][1] + dp[i-1][2] + dp[i-1][3]
     * 情况0：第i列的两个正方形都不存在
     * 情况1：第i列的上面正方形存在，下面正方形不存在
     * 情况2：第i列的上面正方形不存在，下面正方形存在
     * 情况3：第i列的两个正方形都存在
     * [dp[n][0]]         [0 0 0 1] ^ (n-1)         [dp[1][0]]
     * [dp[n][1]]         [1 0 1 0]                 [dp[1][1]]
     * [dp[n][2]]    =    [1 1 0 0]        *        [dp[1][2]]
     * [dp[n][3]]         [1 1 1 1]                 [dp[1][3]]
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int numTilings3(int n) {
        int[][] result = {
                {0, 0, 0, 1},
                {1, 0, 1, 0},
                {1, 1, 0, 0},
                {1, 1, 1, 1}
        };

        result = quickPow(result, n - 1);
        //dp[1][0]=1，dp[1][1]=0，dp[1][2]=0，dp[1][3]=1
        result = multiply(result, new int[][]{{1}, {0}, {0}, {1}});

        //dp[n][3]
        return result[3][0];
    }

    private int[][] quickPow(int[][] a, int n) {
        int[][] result = new int[a.length][a.length];

        for (int i = 0; i < a.length; i++) {
            result[i][i] = 1;
        }

        while (n != 0) {
            if ((n & 1) == 1) {
                result = multiply(result, a);
            }

            a = multiply(a, a);
            n = n >>> 1;
        }

        return result;
    }

    private int[][] multiply(int[][] a, int[][] b) {
        int[][] result = new int[a.length][b[0].length];

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                for (int k = 0; k < a[0].length; k++) {
                    result[i][j] = (int) ((result[i][j] + (long) a[i][k] * b[k][j]) % MOD);
                }
            }
        }

        return result;
    }
}
