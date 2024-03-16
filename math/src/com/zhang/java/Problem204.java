package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/12/6 11:57
 * @Author zsy
 * @Description 计数质数 质数类比Problem866、Problem952、Problem1175、Problem1998、Problem2523、Problem2614 各种数类比Problem202、Problem263、Problem264、Problem306、Problem313、Problem507、Problem509、Problem728、Problem842、Problem878、Problem1175、Problem1201、Problem1291、Offer10、Offer49
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
        System.out.println(problem204.countPrimes(100));
        System.out.println(problem204.countPrimes2(10));
        System.out.println(problem204.countPrimes3(10));
        System.out.println(problem204.countPrimes4(15));
    }

    /**
     * 暴力 (超时)
     * 质数：大于1的自然数中，除了1和该数自身外，无法被其他自然数整除的数
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

        return count;
    }

    /**
     * 暴力优化 (超时)
     * 质数：大于1的自然数中，除了1和该数自身外，无法被其他自然数整除的数
     * i从2到n-1判断每一个i是否是质数，判断i能否能整除j(2 <= j <= i^(1/2))，来判断i是否为质数，如果i都不能整除j，则说明i是质数
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
            //当前数i是否为质数标志位
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
     * 核心思想：一个数的i倍表示的数都不是质数 (i>=2，且i为整数)
     * 质数：大于1的自然数中，除了1和该数自身外，无法被其他自然数整除的数
     * dp[i]：数字i是否是质数
     * 如果dp[i] = true，则dp[i*2] = dp[i*3] = ... = dp[i*n] = false (如果i是质数，则i的倍数表示的数都不是质数)
     * 时间复杂度O(nlog(logn))，空间复杂度O(n)
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

        //dp初始化，初始化[2,n-1]每个数都是质数
        for (int i = 2; i < n; i++) {
            dp[i] = true;
        }

        int count = 0;

        for (int i = 2; i < n; i++) {
            //如果i是质数，则质数i的正整数倍表示的数都不是质数
            if (dp[i]) {
                count++;

                //从i*i开始遍历，因为i*2、i*3...在之前遍历2、3...时已经置为非质数false
                //使用long，避免int相乘溢出
                for (int j = i; (long) i * j < n; j++) {
                    //质数i的j倍表示的数都不是质数
                    dp[i * j] = false;
                }
            }
        }

        return count;
    }

    /**
     * 动态规划，线性筛(欧拉筛)
     * 核心思想：一个数的i倍表示的数都不是质数 (i>=2，且i为整数)
     * 核心思想：任意一个合数都可以表示为质数乘积的形式，并且每个合数只能由最小质因子筛选出来
     * 当前遍历到的数i，i和质数集合相乘(质数集合中只有小于等于i的质数)，标记得到的合数，
     * 如果i能整除当前质数primesList[j]，则得到的合数i*primesList[j]的最小质因子为primesList[j]，
     * primesList[j]之后的质数primesList[j+1]和i相乘得到的合数i*primesList[j+1]的最小质因子不是primesList[j+1]，
     * 当前合数i*primesList[j+1]应该由之后遍历到的某个数乘以最小质因子筛选，避免重复标记合数
     * 例如：当i=4时，质数集合包含2、3，4能整除质数2，此时遍历完质数2之后应该直接跳出循环，
     * 因为2的下一个质数3和i=4相乘，12的最小质因子不是质数3，而是质数2，即12应该由数i=6乘以最小质因子2来标记
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public int countPrimes4(int n) {
        if (n == 0 || n == 1) {
            return 0;
        }

        //dp[i]：数字i是否是质数
        boolean[] dp = new boolean[n];
        //存放当前遍历到的质数集合，此时质数集合中的质数都小于等于当前遍历到的数i
        List<Integer> primesList = new ArrayList<>();

        //dp初始化，初始化[2,n-1]每个数都是质数
        for (int i = 2; i < n; i++) {
            dp[i] = true;
        }

        int count = 0;

        for (int i = 2; i < n; i++) {
            //当前数i是质数，加入质数集合
            if (dp[i]) {
                count++;
                primesList.add(i);
            }

            //i和primesList[j]相乘，得到合数
            //注意：每个合数只能由最小质因子得到，当i*primesList[j]的最小质因子不是primesList[j]时，直接跳出循环，避免重复标记合数
            for (int j = 0; j < primesList.size(); j++) {
                //primesList中的当前质数
                int curPrime = primesList.get(j);

                //超过范围n，直接跳出循环
                //使用long，避免int相乘溢出
                if ((long) i * curPrime >= n) {
                    break;
                }

                //数i的curPrime倍表示的数不是质数
                dp[i * curPrime] = false;

                //i能整除curPrime，则得到的合数i*curPrime的最小质因子为curPrime，
                //curPrime之后的质数primesList[j+1]和i相乘得到的合数i*primesList[j+1]的最小质因子不是primesList[j+1]，
                //直接跳出循环，避免重复标记合数
                if (i % curPrime == 0) {
                    break;
                }
            }
        }

        //等价于return primesList.size();
        return count;
    }
}
