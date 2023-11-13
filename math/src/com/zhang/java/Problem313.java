package com.zhang.java;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * @Date 2023/9/8 08:41
 * @Author zsy
 * @Description 超级丑数 三指针类比Problem75、Problem264、Problem1201、Offer49 各种数类比Problem202、Problem204、Problem263、Problem264、Problem306、Problem507、Problem509、Problem728、Problem842、Problem878、Problem1175、Problem1201、Problem1291、Offer10、Offer49
 * 超级丑数 是一个正整数，并满足其所有质因数都出现在质数数组 primes 中。
 * 给你一个整数 n 和一个整数数组 primes ，返回第 n 个 超级丑数 。
 * 题目数据保证第 n 个 超级丑数 在 32-bit 带符号整数范围内。
 * <p>
 * 输入：n = 12, primes = [2,7,13,19]
 * 输出：32
 * 解释：给定长度为 4 的质数数组 primes = [2,7,13,19]，前 12 个超级丑数序列为：[1,2,4,7,8,13,14,16,19,26,28,32] 。
 * <p>
 * 输入：n = 1, primes = [2,3,5]
 * 输出：1
 * 解释：1 不含质因数，因此它的所有质因数都在质数数组 primes = [2,3,5] 中。
 * <p>
 * 1 <= n <= 10^5
 * 1 <= primes.length <= 100
 * 2 <= primes[i] <= 1000
 * 题目数据 保证 primes[i] 是一个质数
 * primes 中的所有值都 互不相同 ，且按 递增顺序 排列
 */
public class Problem313 {
    public static void main(String[] args) {
        Problem313 problem313 = new Problem313();
//        int n = 12;
//        int[] primes = {2, 7, 13, 19};
        int n = 5911;
        int[] primes = {2, 3, 5, 7};
        System.out.println(problem313.nthSuperUglyNumber(n, primes));
        System.out.println(problem313.nthSuperUglyNumber2(n, primes));
    }

    /**
     * 小根堆
     * 核心思想：一个超级丑数乘上primes[i]也是超级丑数
     * 堆顶超级丑数出堆，当前超级丑数乘以primes[i]表示的超级丑数入堆
     * 时间复杂度O(mnlog(mn))，空间复杂度O(mn)
     *
     * @param n
     * @param primes
     * @return
     */
    public int nthSuperUglyNumber(int n, int[] primes) {
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
        //访问集合，用于当前超级丑数乘以primes[i]表示的超级丑数入堆前去重，使用long，避免int溢出
        Set<Long> visitedSet = new HashSet<>();

        priorityQueue.offer(1L);
        visitedSet.add(1L);

        for (int i = 2; i <= n; i++) {
            long curSuperUglyNum = priorityQueue.poll();

            //当前超级丑数乘上primes[i]也是超级丑数
            for (int j = 0; j < primes.length; j++) {
                //当前超级丑数乘以primes[j]表示的下一个超级丑数，使用long，避免int溢出
                long nextSuperUglyNum = curSuperUglyNum * primes[j];

                //nextSuperUglyNum未访问，加入小根堆中，并设置已访问
                if (!visitedSet.contains(nextSuperUglyNum)) {
                    priorityQueue.offer(nextSuperUglyNum);
                    visitedSet.add(nextSuperUglyNum);
                }
            }
        }

        //Long自动拆箱为long，再强转为int返回
        long result = priorityQueue.poll();

        return (int) result;
    }

    /**
     * 动态规划，多指针
     * 核心思想：一个超级丑数乘上primes[i]也是超级丑数
     * dp[i]：第i+1个超级丑数
     * index[i]：primes[i]指向dp的下标索引，即通过dp[index[i]]*primes[i]得到下一个超级丑数
     * dp[i] = min(dp[index[j]]*primes[j]) (0 < j < primes.length)
     * 时间复杂度O(mn)，空间复杂度O(n+m) (m=primes.length)
     *
     * @param n
     * @param primes
     * @return
     */
    public int nthSuperUglyNumber2(int n, int[] primes) {
        if (n == 1) {
            return 1;
        }

        int[] dp = new int[n];
        //指向dp的下标索引数组，即通过dp[index[i]]*primes[i]得到下一个超级丑数
        int[] index = new int[primes.length];
        //dp初始化
        dp[0] = 1;

        for (int i = 1; i < n; i++) {
            //下一个超级丑数，初始化为long最大值，使用long，避免int相乘溢出
            long nextSuperUglyNum = Long.MAX_VALUE;

            //通过dp[index[i]]*primes[i]中的最小值得到下一个超级丑数
            for (int j = 0; j < primes.length; j++) {
                nextSuperUglyNum = Math.min(nextSuperUglyNum, (long) dp[index[j]] * primes[j]);
            }

            dp[i] = (int) nextSuperUglyNum;

            //index数组指针后移
            for (int j = 0; j < primes.length; j++) {
                if (nextSuperUglyNum == (long) dp[index[j]] * primes[j]) {
                    index[j]++;
                }
            }
        }

        return dp[n - 1];
    }
}
