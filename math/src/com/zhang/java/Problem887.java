package com.zhang.java;

/**
 * @Date 2022/11/5 17:19
 * @Author zsy
 * @Description 鸡蛋掉落 Google面试题 字节面试题 腾讯面试题 vivo机试题 类比Problem375、Offer62
 * 给你 k 枚相同的鸡蛋，并可以使用一栋从第 1 层到第 n 层共有 n 层楼的建筑。
 * 已知存在楼层 f ，满足 0 <= f <= n ，任何从 高于 f 的楼层落下的鸡蛋都会碎，从 f 楼层或比它低的楼层落下的鸡蛋都不会破。
 * 每次操作，你可以取一枚没有碎的鸡蛋并把它从任一楼层 x 扔下（满足 1 <= x <= n）。
 * 如果鸡蛋碎了，你就不能再次使用它。如果某枚鸡蛋扔下后没有摔碎，则可以在之后的操作中 重复使用 这枚鸡蛋。
 * 请你计算并返回要确定 f 确切的值 的 最小操作次数 是多少？
 * <p>
 * 输入：k = 1, n = 2
 * 输出：2
 * 解释：
 * 鸡蛋从 1 楼掉落。如果它碎了，肯定能得出 f = 0 。
 * 否则，鸡蛋从 2 楼掉落。如果它碎了，肯定能得出 f = 1 。
 * 如果它没碎，那么肯定能得出 f = 2 。
 * 因此，在最坏的情况下我们需要移动 2 次以确定 f 是多少。
 * <p>
 * 输入：k = 2, n = 6
 * 输出：3
 * <p>
 * 输入：k = 3, n = 14
 * 输出：4
 * <p>
 * 1 <= k <= 100
 * 1 <= n <= 10^4
 */
public class Problem887 {
    public static void main(String[] args) {
        Problem887 problem887 = new Problem887();
        int k = 2;
        int n = 100;
        System.out.println(problem887.superEggDrop(k, n));
        System.out.println(problem887.superEggDrop2(k, n));
    }

    /**
     * 动态规划
     * dp[i][j]：i层楼j个鸡蛋找到临界楼层最少需要的次数
     * dp[i][j] = max(dp[m-1][j-1], dp[i-m][j]) + 1 (1 <= m <= i)
     * 假设在第m层扔鸡蛋，如果鸡蛋碎了，则需要在[1,m-1]，m-1层楼j-1个鸡蛋找临界楼层，即dp[m-1][j-1]；
     * 如果鸡蛋没碎，则需要在[m+1,i]，i-m层楼j个鸡蛋找临界楼层，即dp[i-m][j]
     * 时间复杂度O(n^2*k)，空间复杂度O(nk)
     *
     * @param k
     * @param n
     * @return
     */
    public int superEggDrop(int k, int n) {
        //1层楼，则不需要管有几个鸡蛋，只需要在1楼扔1次
        if (n == 1) {
            return 1;
        }

        //鸡蛋只有一个，则只能由低到高，从1楼开始一层一层往上扔
        if (k == 1) {
            return n;
        }

        int[][] dp = new int[n + 1][k + 1];

        for (int i = 1; i <= n; i++) {
            dp[i][1] = i;
        }

        for (int j = 1; j <= k; j++) {
            dp[1][j] = 1;
        }

        //楼层从2楼开始往上递增
        for (int i = 2; i <= n; i++) {
            for (int j = 2; j <= k; j++) {
                //赋初值为最大值，则可以取到扔鸡蛋的最佳楼层m
                dp[i][j] = Integer.MAX_VALUE;

                //当前从第m层楼扔鸡蛋
                for (int m = 1; m <= i; m++) {
                    dp[i][j] = Math.min(dp[i][j], Math.max(dp[m - 1][j - 1], dp[i - m][j]) + 1);
                }
            }
        }

        return dp[n][k];
    }

    /**
     * 动态规划
     * dp[i][j]：扔i次j个鸡蛋所能找到临界楼层的最高楼层
     * dp[i][j] = dp[i-1][j-1] + dp[i-1][j] + 1
     * 无论在哪层扔鸡蛋，如果鸡蛋碎了，则剩扔i-1次j-1个鸡蛋，在扔鸡蛋楼层之下找，即dp[i-1][j-1]；
     * 如果鸡蛋没碎，则剩扔i-1次j个鸡蛋，在扔鸡蛋楼层之上找，即dp[i-1][j]
     * 时间复杂度O(nk)，空间复杂度O(nk)
     *
     * @param k
     * @param n
     * @return
     */
    public int superEggDrop2(int k, int n) {
        //1层楼，则不需要管有几个鸡蛋，只需要在1楼扔1次
        if (n == 1) {
            return 1;
        }

        //鸡蛋只有一个，则只能由低到高，从1楼一层一层扔
        if (k == 1) {
            return n;
        }

        //最多不会扔超过n次
        int[][] dp = new int[n + 1][k + 1];

        for (int i = 1; i <= n; i++) {
            dp[i][1] = i;
        }

        for (int j = 1; j <= k; j++) {
            dp[1][j] = 1;
        }

        for (int i = 2; i <= n; i++) {
            for (int j = 2; j <= k; j++) {
                dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j] + 1;

                //当扔i次j个鸡蛋所能找到临界楼层的最高楼层超过n时，即找到最少次数i
                if (dp[i][j] >= n) {
                    return i;
                }
            }
        }

        return -1;
    }
}
