package com.zhang.java;

/**
 * @Date 2022/3/30 9:41
 * @Author zsy
 * @Description 丑数 类比Problem26、Problem75 同Problem264
 * 我们把只包含质因子 2、3 和 5 的数称作丑数（Ugly Number）。
 * 求按从小到大的顺序的第 n 个丑数。
 * <p>
 * 输入: n = 10
 * 输出: 12
 * 解释: 1, 2, 3, 4, 5, 6, 8, 9, 10, 12 是前 10 个丑数。
 * <p>
 * 1 是丑数。
 * n 不超过1690。
 */
public class Offer49 {
    public static void main(String[] args) {
        Offer49 offer49 = new Offer49();
        System.out.println(offer49.nthUglyNumber(10));
    }

    /**
     * 动态规划，三指针
     * 要点：一个丑数乘上2、3、5也是丑数
     * dp[i]：第i+1个丑数
     * dp[l] = min(dp[i]*2,dp[j]*3,dp[k]*5) (i,j,k < l)
     * 当前丑数数组乘上2、3、5也是丑数，所以根据前面的丑数可以得到当前的丑数
     * 三个指针分别指向当前丑数数组的下标索引，分别乘上2、3、5，取最小值，即为当前丑数
     * 1, 2, 3, 4, 5, 6, 8, 9, 10, 12是前10个丑数
     * i=6、j=4、k=2，分为乘上2、3、5，取最小值15，即为第11个丑数
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public int nthUglyNumber(int n) {
        if (n <= 0) {
            return -1;
        }

        if (n == 1) {
            return 1;
        }

        int[] ugly = new int[n];
        ugly[0] = 1;

        //i、j、k分别指向当前丑数数组的下标索引
        int i = 0;
        int j = 0;
        int k = 0;

        for (int l = 1; l < n; l++) {
            //三者最小值即为当前丑数
            int curUgly = Math.min(ugly[i] * 2, Math.min(ugly[j] * 3, ugly[k] * 5));
            ugly[l] = curUgly;

            //指针后移
            if (curUgly == ugly[i] * 2) {
                i++;
            }

            if (curUgly == ugly[j] * 3) {
                j++;
            }

            if (curUgly == ugly[k] * 5) {
                k++;
            }
        }

        return ugly[n - 1];
    }
}
