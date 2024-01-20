package com.zhang.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Date 2024/1/21 08:12
 * @Author zsy
 * @Description 范围内最接近的两个质数 质数类比Problem204、Problem952、Problem1175、Problem1998
 * 给你两个正整数 left 和 right ，请你找到两个整数 num1 和 num2 ，它们满足：
 * left <= nums1 < nums2 <= right  。
 * nums1 和 nums2 都是 质数 。
 * nums2 - nums1 是满足上述条件的质数对中的 最小值 。
 * 请你返回正整数数组 ans = [nums1, nums2] 。如果有多个整数对满足上述条件，请你返回 nums1 最小的质数对。
 * 如果不存在符合题意的质数对，请你返回 [-1, -1] 。
 * 如果一个整数大于 1 ，且只能被 1 和它自己整除，那么它是一个 质数。
 * <p>
 * 输入：left = 10, right = 19
 * 输出：[11,13]
 * 解释：10 到 19 之间的质数为 11 ，13 ，17 和 19 。
 * 质数对的最小差值是 2 ，[11,13] 和 [17,19] 都可以得到最小差值。
 * 由于 11 比 17 小，我们返回第一个质数对。
 * <p>
 * 输入：left = 4, right = 6
 * 输出：[-1,-1]
 * 解释：给定范围内只有一个质数，所以题目条件无法被满足。
 * <p>
 * 1 <= left <= right <= 10^6
 */
public class Problem2523 {
    public static void main(String[] args) {
        Problem2523 problem2523 = new Problem2523();
        int left = 84084;
        int right = 407043;
//        System.out.println(Arrays.toString(problem2523.closestPrimes(left, right)));
//        System.out.println(Arrays.toString(problem2523.closestPrimes2(left, right)));
        System.out.println(Arrays.toString(problem2523.closestPrimes3(left, right)));
        System.out.println(Arrays.toString(problem2523.closestPrimes4(left, right)));
    }

    /**
     * 模拟，暴力求质数 (超时)
     * 时间复杂度O(right^2)，空间复杂度O(right)
     *
     * @param left
     * @param right
     * @return
     */
    public int[] closestPrimes(int left, int right) {
        //质数集合
        List<Integer> primesList = new ArrayList<>();

        //求出小于等于left的所有质数
        for (int i = 2; i <= right; i++) {
            //当前数i是否为质数标志位
            boolean flag = true;

            for (int j = 2; j < i; j++) {
                if (i % j == 0) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                primesList.add(i);
            }
        }

        //[left,right]范围内最接近的两个质数
        int closestPrime1 = -1;
        int closestPrime2 = -1;

        for (int i = 0; i < primesList.size() - 1; i++) {
            int curPrime = primesList.get(i);
            int nextPrime = primesList.get(i + 1);

            if (curPrime < left) {
                continue;
            }

            if (closestPrime1 == -1 || nextPrime - curPrime < closestPrime2 - closestPrime1) {
                closestPrime1 = curPrime;
                closestPrime2 = nextPrime;
            }
        }

        return new int[]{closestPrime1, closestPrime2};
    }

    /**
     * 模拟，暴力求质数 (超时)
     * 时间复杂度O(right^(3/2))，空间复杂度O(right)
     *
     * @param left
     * @param right
     * @return
     */
    public int[] closestPrimes2(int left, int right) {
        //质数集合
        List<Integer> primesList = new ArrayList<>();

        for (int i = 2; i <= right; i++) {
            //当前数i是否为质数标志位
            boolean flag = true;

            for (int j = 2; j * j <= i; j++) {
                if (i % j == 0) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                primesList.add(i);
            }
        }

        //[left,right]范围内最接近的两个质数
        int closestPrime1 = -1;
        int closestPrime2 = -1;

        for (int i = 0; i < primesList.size() - 1; i++) {
            int curPrime = primesList.get(i);
            int nextPrime = primesList.get(i + 1);

            if (curPrime < left) {
                continue;
            }

            if (closestPrime1 == -1 || nextPrime - curPrime < closestPrime2 - closestPrime1) {
                closestPrime1 = curPrime;
                closestPrime2 = nextPrime;
            }
        }

        return new int[]{closestPrime1, closestPrime2};
    }

    /**
     * 模拟，埃氏筛求质数
     * 时间复杂度O(right*log(log(right)))，空间复杂度O(right)
     *
     * @param left
     * @param right
     * @return
     */
    public int[] closestPrimes3(int left, int right) {
        //质数集合
        List<Integer> primesList = new ArrayList<>();

        //dp[i]：数字i是否是质数
        boolean[] dp = new boolean[right + 1];

        //dp初始化，初始化[2,right]每个数都是质数
        for (int i = 2; i <= right; i++) {
            dp[i] = true;
        }

        for (int i = 2; i <= right; i++) {
            //如果i是质数，则质数i的正整数倍表示的数都不是质数
            if (dp[i]) {
                primesList.add(i);

                //从i*i开始遍历，因为i*2、i*3...在之前遍历2、3...时已经置为非质数false
                //使用long，避免int相乘溢出
                for (int j = i; (long) i * j <= right; j++) {
                    //质数i的j倍表示的数都不是质数
                    dp[i * j] = false;
                }
            }
        }

        //[left,right]范围内最接近的两个质数
        int closestPrime1 = -1;
        int closestPrime2 = -1;

        for (int i = 0; i < primesList.size() - 1; i++) {
            int curPrime = primesList.get(i);
            int nextPrime = primesList.get(i + 1);

            if (curPrime < left) {
                continue;
            }

            if (closestPrime1 == -1 || nextPrime - curPrime < closestPrime2 - closestPrime1) {
                closestPrime1 = curPrime;
                closestPrime2 = nextPrime;
            }
        }

        return new int[]{closestPrime1, closestPrime2};
    }

    /**
     * 模拟，线性筛(欧拉筛)求质数
     * 时间复杂度O(right)，空间复杂度O(right)
     *
     * @param left
     * @param right
     * @return
     */
    public int[] closestPrimes4(int left, int right) {
        //质数集合
        List<Integer> primesList = new ArrayList<>();

        //dp[i]：数字i是否是质数
        boolean[] dp = new boolean[right + 1];

        //dp初始化，初始化[2,right]每个数都是质数
        for (int i = 2; i <= right; i++) {
            dp[i] = true;
        }

        for (int i = 2; i <= right; i++) {
            //当前数i是质数，加入质数集合
            if (dp[i]) {
                primesList.add(i);
            }

            //i和primesList[j]相乘，得到合数
            //注意：每个合数只能由最小质因子得到，当i*primesList[j]的最小质因子不是primesList[j]时，直接跳出循环，避免重复标记合数
            for (int j = 0; j < primesList.size(); j++) {
                //primesList中的当前质数
                int curPrime = primesList.get(j);

                //超过范围right，直接跳出循环
                //使用long，避免int相乘溢出
                if ((long) i * curPrime > right) {
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

        //[left,right]范围内最接近的两个质数
        int closestPrime1 = -1;
        int closestPrime2 = -1;

        for (int i = 0; i < primesList.size() - 1; i++) {
            int curPrime = primesList.get(i);
            int nextPrime = primesList.get(i + 1);

            if (curPrime < left) {
                continue;
            }

            if (closestPrime1 == -1 || nextPrime - curPrime < closestPrime2 - closestPrime1) {
                closestPrime1 = curPrime;
                closestPrime2 = nextPrime;
            }
        }

        return new int[]{closestPrime1, closestPrime2};
    }
}
