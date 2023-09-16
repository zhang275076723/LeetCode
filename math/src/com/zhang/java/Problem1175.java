package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/9/16 08:40
 * @Author zsy
 * @Description 质数排列 类比Problem60、Problem1492 各种数类比Problem202、Problem204、Problem263、Problem264、Problem306、Problem313、Problem507、Problem509、Problem728、Problem842、Problem878、Problem1201、Problem1291、Offer10、Offer49
 * 请你帮忙给从 1 到 n 的数设计排列方案，使得所有的「质数」都应该被放在「质数索引」（索引从 1 开始）上；你需要返回可能的方案总数。
 * 让我们一起来回顾一下「质数」：质数一定是大于 1 的，并且不能用两个小于它的正整数的乘积来表示。
 * 由于答案可能会很大，所以请你返回答案 模 mod 10^9 + 7 之后的结果即可。
 * <p>
 * 输入：n = 5
 * 输出：12
 * 解释：举个例子，[1,2,5,4,3] 是一个有效的排列，但 [5,2,3,4,1] 不是，因为在第二种情况里质数 5 被错误地放在索引为 1 的位置上。
 * 1、[1,2,3,4,5]
 * 2、[1,2,5,4,3]
 * 3、[1,3,2,4,5]
 * 4、[1,3,5,4,2]
 * 5、[1,5,2,4,3]
 * 6、[1,5,3,4,2]
 * 7、[4,2,3,1,5]
 * 8、[4,2,5,1,3]
 * 9、[4,3,2,1,5]
 * 10、[4,3,5,1,2]
 * 11、[4,5,2,1,3]
 * 12、[4,5,3,1,2]
 * <p>
 * 输入：n = 100
 * 输出：682289015
 * <p>
 * 1 <= n <= 100
 */
public class Problem1175 {
    private final int MOD = (int) 1e9 + 7;

    public static void main(String[] args) {
        Problem1175 problem1175 = new Problem1175();
        int n = 100;
        System.out.println(problem1175.numPrimeArrangements(n));
        System.out.println(problem1175.numPrimeArrangements2(n));
        System.out.println(problem1175.numPrimeArrangements3(n));
        System.out.println(problem1175.numPrimeArrangements4(n));
    }

    /**
     * 模拟，暴力求质数
     * 找出1-n中质数的个数，质数个数的全排列乘以剩余数个数的全排列即为所求的排列个数
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int numPrimeArrangements(int n) {
        //质数的个数
        int count = 0;

        for (int i = 2; i <= n; i++) {
            //当前数i是否为质数标志位
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

        return (int) (getFactorial(count) * getFactorial(n - count) % MOD);
    }

    /**
     * 模拟，暴力优化求质数
     * 找出1-n中质数的个数，质数个数的全排列乘以剩余数个数的全排列即为所求的排列个数
     * 时间复杂度O(n^(3/2))，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int numPrimeArrangements2(int n) {
        //质数的个数
        int count = 0;

        for (int i = 2; i <= n; i++) {
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

        return (int) (getFactorial(count) * getFactorial(n - count) % MOD);
    }

    /**
     * 模拟，埃氏筛求质数
     * 找出1-n中质数的个数，质数个数的全排列乘以剩余数个数的全排列即为所求的排列个数
     * 时间复杂度O(nlog(logn))，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public int numPrimeArrangements3(int n) {
        //质数的个数
        int count = 0;
        //dp[i]：数字i是否是质数
        boolean[] dp = new boolean[n + 1];

        //dp初始化，初始化[2,n]每个数都是质数
        for (int i = 2; i <= n; i++) {
            dp[i] = true;
        }

        for (int i = 2; i <= n; i++) {
            //如果i是质数，则质数i的正整数倍表示的数都不是质数
            if (dp[i]) {
                count++;

                //从i*i开始遍历，因为i*2、i*3...在之前遍历2、3...时已经置为非质数false，使用long，避免int相乘溢出
                for (int j = i; (long) i * j <= n; j++) {
                    //质数i的j倍表示的数都不是质数
                    dp[i * j] = false;
                }
            }
        }

        return (int) (getFactorial(count) * getFactorial(n - count) % MOD);
    }

    /**
     * 模拟，线性筛(欧拉筛)求质数
     * 找出1-n中质数的个数，质数个数的全排列乘以剩余数个数的全排列即为所求的排列个数
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public int numPrimeArrangements4(int n) {
        //质数的个数
        int count = 0;
        //dp[i]：数字i是否是质数
        boolean[] dp = new boolean[n + 1];
        List<Integer> primesList = new ArrayList<>();

        //dp初始化，初始化[2,n]每个数都是质数
        for (int i = 2; i <= n; i++) {
            dp[i] = true;
        }

        for (int i = 2; i <= n; i++) {
            //当前数i是质数，加入质数集合
            if (dp[i]) {
                primesList.add(i);
                count++;
            }

            //i和质数集合中数相乘，得到合数，注意：每个合数只能由最小质因子得到，避免了重复计算标记合数
            for (int j = 0; j < primesList.size(); j++) {
                //primesList中的当前质数
                int curPrime = primesList.get(j);

                //超过范围n，直接跳出循环
                if (i * curPrime > n) {
                    break;
                }

                //数i的curPrime倍表示的数不是质数
                dp[i * curPrime] = false;

                //curPrimes为i的最小质因子，则curPrime之后的质数都不是最小质因子，和i相乘得到的会得到重复的合数，直接跳出循环
                if (i % curPrime == 0) {
                    break;
                }
            }
        }

        return (int) (getFactorial(count) * getFactorial(n - count) % MOD);
    }

    /**
     * 得到n的阶乘
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @return
     */
    private long getFactorial(int n) {
        //使用long，避免int相乘溢出
        long result = 1;

        for (int i = 1; i <= n; i++) {
            result = result * i % MOD;
        }

        return result;
    }
}
