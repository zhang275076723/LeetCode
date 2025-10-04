package com.zhang.java;

/**
 * @Date 2022/3/16 17:48
 * @Author zsy
 * @Description 剪绳子 类比Problem343、Offer14_2 动态规划类比Problem198、Problem213、Problem256、Problem265、Problem279、Problem322、Problem338、Problem343、Problem377、Problem416、Problem474、Problem494、Problem518、Problem746、Problem983、Problem1340、Problem1388、Problem1444、Problem1473、Offer14_2、Offer60、CircleBackToOrigin、Knapsack
 * 给你一根长度为 n 的绳子，请把绳子剪成整数长度的 m 段（m、n都是整数，n>1并且m>1），
 * 每段绳子的长度记为 k[0],k[1]...k[m-1] 。
 * 请问 k[0]*k[1]*...*k[m-1] 可能的最大乘积是多少？
 * 例如，当绳子的长度是8时，我们把它剪成长度分别为2、3、3的三段，此时得到的最大乘积是18。
 * <p>
 * 输入: 2
 * 输出: 1
 * 解释: 2 = 1 + 1, 1 × 1 = 1
 * <p>
 * 输入: 10
 * 输出: 36
 * 解释: 10 = 3 + 3 + 4, 3 × 3 × 4 = 36
 * <p>
 * 2 <= n <= 58
 */
public class Offer14 {
    public static void main(String[] args) {
        Offer14 offer14 = new Offer14();
        int n = 10;
        System.out.println(offer14.cuttingRope(n));
        System.out.println(offer14.cuttingRope2(n));
        System.out.println(offer14.cuttingRope3(n));
    }

    /**
     * 动态规划
     * dp[i]：长度为i的绳子至少剪成2段，所有子段绳子长度的最大乘积
     * dp[i] = max(j*(i-j),j*dp[i-j],dp[j]*dp[i-j]) (1 <= j < i)
     * j*(i-j)：拆分成2段，j*dp[i-j]：至少拆分成3段，dp[j]*dp[i-j]：至少拆分成4段
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public int cuttingRope(int n) {
        int[] dp = new int[n + 1];
        //初始化，dp[0]=dp[1]=0

        //长度为i的绳子
        for (int i = 2; i <= n; i++) {
            //减掉长度为j的一段绳子
            for (int j = 1; j < i; j++) {
                //j*(i-j)：拆分成2段，j*dp[i-j]：至少拆分成3段，dp[j]*dp[i-j]：至少拆分成4段
                dp[i] = Math.max(dp[i], Math.max(j * (i - j), Math.max(j * dp[i - j], dp[j] * dp[i - j])));
            }
        }

        return dp[n];
    }

    /**
     * 动态规划优化
     * dp[i]：长度为i的绳子至少剪成2段，所有子段绳子长度的最大乘积
     * dp[i] = max(2*(i-2),2*dp[i-2],3*(i-3),3*dp[i-3])
     * j*(i-j)：拆分成2段，j*dp[i-j]：至少拆分成3段
     * 最优拆分为3，次优拆分为2，所以只需要考虑每段拆分为2和3的情况
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public int cuttingRope2(int n) {
        if (n <= 3) {
            return n - 1;
        }

        int[] dp = new int[n + 1];
        //初始化，dp[0]=dp[1]=0
        dp[2] = 1;

        //注意i从3开始遍历
        for (int i = 3; i <= n; i++) {
            //j*(i-j)：拆分成2段，j*dp[i-j]：至少拆分成3段
            dp[i] = Math.max(Math.max(2 * (i - 2), 2 * dp[i - 2]), Math.max(3 * (i - 3), 3 * dp[i - 3]));
        }

        return dp[n];
    }

    /**
     * 贪心，尽可能把绳子分为每段长度为3的子段
     * 最优：3
     * 次优：2
     * 最差：1
     * 当n>3，n%3 == 0时，都可以剪成长度为3的子段，返回3^(n/3)；
     * 当n%3 == 1时，剪成2个长度为2的子段，其余都是长度为3的子段，返回(3^(n/3-1))*2*2；
     * 当n%3 == 2时，剪成1个长度为2的子段，其余都是长度为3的子段，返回3^(n/3)*2
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int cuttingRope3(int n) {
        //至少剪成2段
        if (n <= 3) {
            return n - 1;
        }

        //尽可能剪成长度为3的子段

        //商
        int a = n / 3;
        //余数
        int b = n % 3;

        //余数为0
        if (b == 0) {
            return quickPow(3, a);
        } else if (b == 1) {
            //余数为1，需要考虑最后一段和倒数第二段
            return quickPow(3, a - 1) * 2 * 2;
        } else {
            //余数为2
            return quickPow(3, a) * 2;
        }
    }

    private int quickPow(int a, int n) {
        if (n == 0) {
            return 1;
        }

        if (a % 2 == 0) {
            int temp = quickPow(a, n / 2);
            return temp * temp;
        } else {
            return a * quickPow(a, n - 1);
        }
    }
}
