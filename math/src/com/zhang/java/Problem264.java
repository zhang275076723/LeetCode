package com.zhang.java;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * @Date 2022/8/27 11:37
 * @Author zsy
 * @Description 丑数 II 三指针类比Problem75、Problem313、Problem1201、Offer49 各种数类比Problem202、Problem204、Problem263、Problem306、Problem313、Problem507、Problem509、Problem728、Problem842、Problem878、Problem1175、Problem1201、Problem1291、Offer10、Offer49 同Offer49
 * 给你一个整数 n ，请你找出并返回第 n 个 丑数 。
 * 丑数 就是只包含质因数 2、3 和/或 5 的正整数。
 * <p>
 * 输入：n = 10
 * 输出：12
 * 解释：[1, 2, 3, 4, 5, 6, 8, 9, 10, 12] 是由前 10 个丑数组成的序列。
 * <p>
 * 输入：n = 1
 * 输出：1
 * 解释：1 通常被视为丑数。
 * <p>
 * 1 <= n <= 1690
 */
public class Problem264 {
    public static void main(String[] args) {
        Problem264 problem264 = new Problem264();
        int n = 11;
        System.out.println(problem264.nthUglyNumber(n));
        System.out.println(problem264.nthUglyNumber2(n));
    }

    /**
     * 小根堆
     * 核心思想：一个丑数乘上2、3、5也是丑数
     * 堆顶丑数出堆，当前丑数乘以2、3、5表示的丑数入堆
     * 时间复杂度O(nlogn)，空间复杂度O(n)
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

        //小根堆，使用long，避免int溢出
        PriorityQueue<Long> priorityQueue = new PriorityQueue<>(new Comparator<Long>() {
            @Override
            public int compare(Long a, Long b) {
                //不能写成return (int) (a - b);，因为long相减再转为int有可能在int范围溢出
                return Long.compare(a, b);
            }
        });
        //访问集合，用于当前丑数乘以2、3、5表示的丑数入堆前去重，使用long，避免int溢出
        Set<Long> visitedSet = new HashSet<>();

        //小根堆初始化，1入堆
        priorityQueue.offer(1L);
        visitedSet.add(1L);

        for (int i = 2; i <= n; i++) {
            //当前丑数，使用long，避免int溢出
            long curUglyNum = priorityQueue.poll();
            //当前丑数乘以2表示的下一个丑数，使用long，避免int溢出
            long nextUglyNum1 = curUglyNum * 2;
            //当前丑数乘以3表示的下一个丑数，使用long，避免int溢出
            long nextUglyNum2 = curUglyNum * 3;
            //当前丑数乘以5表示的下一个丑数，使用long，避免int溢出
            long nextUglyNum3 = curUglyNum * 5;

            //nextUglyNum1未访问，加入小根堆中，并设置已访问
            if (!visitedSet.contains(nextUglyNum1)) {
                priorityQueue.offer(nextUglyNum1);
                visitedSet.add(nextUglyNum1);
            }

            //nextUglyNum2未访问，加入小根堆中，并设置已访问
            if (!visitedSet.contains(nextUglyNum2)) {
                priorityQueue.offer(nextUglyNum2);
                visitedSet.add(nextUglyNum2);
            }

            //nextUglyNum3未访问，加入小根堆中，并设置已访问
            if (!visitedSet.contains(nextUglyNum3)) {
                priorityQueue.offer(nextUglyNum3);
                visitedSet.add(nextUglyNum3);
            }
        }

        //Long自动拆箱为long，再强转为int返回
        long result = priorityQueue.poll();

        return (int) result;
    }

    /**
     * 动态规划，三指针
     * 核心思想：一个丑数乘上2、3、5也是丑数
     * dp[i]：第i+1个丑数
     * dp[m] = min(dp[i]*2,dp[j]*3,dp[k]*5) (i,j,k < m)
     * 例如：1, 2, 3, 4, 5, 6, 8, 9, 10, 12是前10个丑数，
     * i=6、j=4、k=2，dp[i]*2=16、dp[j]*3=15、dp[k]*5=15，取三者中的最小值15，即为第11个丑数
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public int nthUglyNumber2(int n) {
        if (n <= 0) {
            return -1;
        }

        if (n == 1) {
            return 1;
        }

        int[] dp = new int[n];
        //dp初始化
        dp[0] = 1;

        //i、j、k分别指向dp数组的下标索引，每次取dp[i]*2，dp[j]*3，dp[k]*5，三者中最小值作为当前丑数
        int i = 0;
        int j = 0;
        int k = 0;

        for (int m = 1; m < n; m++) {
            //dp[i]*2，dp[j]*3，dp[k]*5，三者中最小值作为当前丑数
            dp[m] = Math.min(dp[i] * 2, Math.min(dp[j] * 3, dp[k] * 5));

            //i指针后移
            if (dp[m] == dp[i] * 2) {
                i++;
            }

            //j指针后移
            if (dp[m] == dp[j] * 3) {
                j++;
            }

            //k指针后移
            if (dp[m] == dp[k] * 5) {
                k++;
            }
        }

        return dp[n - 1];
    }
}
