package com.zhang.java;

/**
 * @Date 2022/3/17 16:51
 * @Author zsy
 * @Description 剪绳子 II
 * 给你一根长度为 n 的绳子，请把绳子剪成整数长度的 m 段（m、n都是整数，n>1并且m>1），
 * 每段绳子的长度记为 k[0],k[1]...k[m - 1] 。
 * 请问 k[0]*k[1]*...*k[m - 1] 可能的最大乘积是多少？
 * 例如，当绳子的长度是8时，我们把它剪成长度分别为2、3、3的三段，此时得到的最大乘积是18。
 * 答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。
 * <p>
 * 输入: 2
 * 输出: 1
 * 解释: 2 = 1 + 1, 1 × 1 = 1
 * <p>
 * 输入: 10
 * 输出: 36
 * 解释: 10 = 3 + 3 + 4, 3 × 3 × 4 = 36
 * <p>
 * 2 <= n <= 1000
 */
public class Offer14_2 {
    private final int MOD = 1000000007;

    public static void main(String[] args) {
        Offer14_2 offer14_2 = new Offer14_2();
        //953271190
        System.out.println(offer14_2.cuttingRope(120));
    }

    /**
     * 贪心，尽可能把绳子分为每段长度为3的子段
     * 与上题不同的是，需要考虑子段乘积有可能会溢出，所以不能使用动态规划
     * 子段相乘有可能会int范围，所以每次乘3都要取模
     * 最优：3
     * 次优：2
     * 最差：1
     * 当n>3，n%3 == 0时，都可以剪成长度为3的子段，返回3^(n/3)；
     * 当n%3 == 1时，剪成2个长度为2的子段，其余都是长度为3的子段，返回(3^(n/3-1))*2*2；
     * 当n%3 == 2时，剪成1个长度为2的子段，其余都是长度为3的子段，返回3^(n/3)*2
     * 时间复杂度O(n)，空间复杂的O(1)
     *
     * @param n
     * @return
     */
    public int cuttingRope(int n) {
        //至少剪成2段
        if (n <= 3) {
            return n - 1;
        }

        //尽可能剪成长度为3的子段
        int a = n / 3;
        int b = n % 3;
        //result * 3 可能在求余之前超出int范围，所以用long
        long result = 1;

        //3的a-1次幂
        for (int i = 0; i < a - 1; i++) {
            result = result * 3 % MOD;
        }

        //需要考虑最后一段和倒数第二段
        if (b == 0) {
            return (int) (result * 3 % MOD);
        } else if (b == 1) {
            return (int) (result * 2 * 2 % MOD);
        } else {
            return (int) (result * 3 * 2 % MOD);
        }
    }
}
