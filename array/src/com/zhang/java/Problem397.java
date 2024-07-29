package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2024/6/15 09:04
 * @Author zsy
 * @Description 整数替换 类比Problem756 类比Problem338、Problem1342、Problem1404、Problem2139、Problem2571 记忆化搜索类比
 * 给定一个正整数 n ，你可以做如下操作：
 * 如果 n 是偶数，则用 n / 2替换 n 。
 * 如果 n 是奇数，则可以用 n + 1或n - 1替换 n 。
 * 返回 n 变为 1 所需的 最小替换次数 。
 * <p>
 * 输入：n = 8
 * 输出：3
 * 解释：8 -> 4 -> 2 -> 1
 * <p>
 * 输入：n = 7
 * 输出：4
 * 解释：7 -> 8 -> 4 -> 2 -> 1
 * 或 7 -> 6 -> 3 -> 2 -> 1
 * <p>
 * 输入：n = 4
 * 输出：2
 * <p>
 * 1 <= n <= 2^31 - 1
 */
public class Problem397 {
    public static void main(String[] args) {
        Problem397 problem397 = new Problem397();
        int n = 8;
        System.out.println(problem397.integerReplacement(n));
        System.out.println(problem397.integerReplacement2(n));
    }

    /**
     * 动态规划 (超时)
     * dp[i]：i变为1需要的最少次数
     * dp[i] = dp[i/2]+1                    (i%2==0)
     * dp[i] = min(dp[i-1]+1,dp[(i+1)/2]+2) (i%2==1)
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public int integerReplacement(int n) {
        int[] dp = new int[n + 1];

        for (int i = 2; i <= n; i++) {
            if (i % 2 == 0) {
                dp[i] = dp[i / 2] + 1;
            } else {
                //i为奇数，则变为1需要的最少次数为偶数i-1变为1需要的最少次数+1，偶数(i+1)/2变为1需要的最少次数+2，两者中的较小值
                dp[i] = Math.min(dp[i - 1] + 1, dp[(i + 1) / 2] + 2);
            }
        }

        return dp[n];
    }

    /**
     * 记忆化搜索
     * 时间复杂度O(logn)，空间复杂度O(logn)
     *
     * @param n
     * @return
     */
    public int integerReplacement2(int n) {
        //key：当前需要变为1的正整数，value：key变为1需要的最少次数
        //使用long，避免相加int溢出
        Map<Long, Integer> map = new HashMap<>();

        return dfs(n, map);
    }

    private int dfs(long n, Map<Long, Integer> map) {
        if (n == 1) {
            map.put(1L, 0);
            return 0;
        }

        if (map.containsKey(n)) {
            return map.get(n);
        }

        int count;

        if (n % 2 == 0) {
            count = dfs(n / 2, map) + 1;
        } else {
            count = Math.min(dfs(n - 1, map), dfs(n + 1, map)) + 1;
        }

        map.put(n, count);
        return count;
    }
}
