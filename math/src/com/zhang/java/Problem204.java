package com.zhang.java;

/**
 * @Date 2022/12/6 11:57
 * @Author zsy
 * @Description 计数质数 类比Problem263、Problem264、Offer49
 * 给定整数 n ，返回 所有小于非负整数 n 的质数的数量。
 * <p>
 * 输入：n = 10
 * 输出：4
 * 解释：小于 10 的质数一共有 4 个, 它们是 2, 3, 5, 7 。
 * <p>
 * 输入：n = 0
 * 输出：0
 * <p>
 * 输入：n = 1
 * 输出：0
 * <p>
 * 0 <= n <= 5 * 10^6
 */
public class Problem204 {
    public static void main(String[] args) {
        Problem204 problem204 = new Problem204();
        System.out.println(problem204.countPrimes(10));
        System.out.println(problem204.countPrimes2(10));
        System.out.println(problem204.countPrimes3(10));
    }

    /**
     * 暴力
     * 质数：在大于 1 的自然数中，除了 1 和该数自身外，无法被其他自然数整除的数
     * 遍历2到n-1中每一个数是否是质数，在判断i是否为质数时，从2到i-1都进行相除，如果都无法整除，说明是质数
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int countPrimes(int n) {
        if (n == 0 || n == 1) {
            return 0;
        }

        int count = 0;

        for (int i = 2; i < n; i++) {
            //当前数i是否为质数
            boolean flag = true;

            for (int j = 2; j < i; j++) {
                if (i % j == 0) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                count++;
            }
        }

        return count;
    }

    /**
     * 暴力优化
     * 质数：在大于 1 的自然数中，除了 1 和该数自身外，无法被其他自然数整除的数
     * 遍历2到n-1中每一个数是否是质数，在判断i是否为质数时，从2到i^(1/2)都进行相除，如果都无法整除，说明是质数
     * 时间复杂度O(n^(3/2))，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int countPrimes2(int n) {
        if (n == 0 || n == 1) {
            return 0;
        }

        int count = 0;

        for (int i = 2; i < n; i++) {
            //当前数i是否为质数
            boolean flag = true;

            for (int j = 2; j * j <= i; j++) {
                if (i % j == 0) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                count++;
            }
        }

        return count;
    }

    /**
     * 动态规划，埃氏筛
     * 质数：大于1的自然数中，除了1和该数本身外，无法被其他自然数整除的数
     * dp[i]：数字i是否是质数
     * 如果dp[i] = true，则dp[i*2] = dp[i*3] = ... = dp[i*n] = false (如果i是质数，则i的倍数表示的数都不是质数)
     * 时间复杂度O(nloglogn)，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public int countPrimes3(int n) {
        if (n == 0 || n == 1) {
            return 0;
        }

        //dp[i]：数字i是否是质数
        boolean[] dp = new boolean[n];

        //dp初始化，每个数都是质数
        for (int i = 2; i < n; i++) {
            dp[i] = true;
        }

        int count = 0;

        for (int i = 2; i < n; i++) {
            //i是质数，则i的倍数表示的数都不是质数
            if (dp[i]) {
                count++;

                //从i*i开始遍历，因为i*2、i*3...在之前遍历2、3...时已经置为非质数false
                for (int j = i; (long) i * j < n; j++) {
                    //质数i的j倍表示的数都不是质数
                    dp[i * j] = false;
                }
            }
        }

        return count;
    }
}
