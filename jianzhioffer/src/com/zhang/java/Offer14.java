package com.zhang.java;

/**
 * @Date 2022/3/16 17:48
 * @Author zsy
 * @Description 剪绳子 动态规划类比Problem198、Problem213、Problem279、Problem322、Problem343、Problem377、Problem416、Problem474、Problem494、Problem518、Problem746、Problem983、Problem1340、Problem1388、Problem1444、Offer14_2、Offer60、CircleBackToOrigin、Knapsack
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
        System.out.println(offer14.cuttingRope(10));
        System.out.println(offer14.cuttingRope2(10));
    }

    /**
     * 动态规划
     * dp[i]：长度为i的绳子，至少剪成2段，所有子段长度的最大乘积
     * dp[i] = max(j*dp[i-j], j*(i-j)) (1 < j < i)
     * 先减掉长度为j的子段，剩余部分不剪，乘积为j*(i-j)；剩余部分要剪，乘积为j*dp[i-j]
     * 时间复杂度O(n^2)，空间复杂的O(n)
     *
     * @param n
     * @return
     */
    public int cuttingRope(int n) {
        int[] dp = new int[n + 1];
        //初始化，长度为1的绳子，最大乘积为1
        dp[1] = 1;

        //长度为i的绳子
        for (int i = 2; i <= n; i++) {
            //减掉长度为j的一段
            for (int j = 1; j < i; j++) {
                //j * dp[i - j]：绳子剪为长度为j和之和为(i-j)的最大乘积的字段，至少为3段，
                //j * (i - j)：绳子剪为长度为j和(i-j)的2段
                dp[i] = Math.max(dp[i], Math.max(j * dp[i - j], j * (i - j)));
            }
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
     * 时间复杂度O(1)，空间复杂的O(1)
     *
     * @param n
     * @return
     */
    public int cuttingRope2(int n) {
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
