package com.zhang.java;

/**
 * @Date 2023/10/5 08:30
 * @Author zsy
 * @Description 统计各位数字都不同的数字个数 类比Problem386、Offer17 回溯+剪枝类比 动态规划类比
 * 给你一个整数 n ，统计并返回各位数字都不同的数字 x 的个数，其中 0 <= x < 10^n 。
 * <p>
 * 输入：n = 2
 * 输出：91
 * 解释：答案应为除去 11、22、33、44、55、66、77、88、99 外，在 0 ≤ x < 100 范围内的所有数字。
 * <p>
 * 输入：n = 0
 * 输出：1
 * <p>
 * 0 <= n <= 8
 */
public class Problem357 {
    public static void main(String[] args) {
        Problem357 problem357 = new Problem357();
        int n = 2;
        System.out.println(problem357.countNumbersWithUniqueDigits(n));
        System.out.println(problem357.countNumbersWithUniqueDigits2(n));
    }

    /**
     * 回溯+剪枝
     * 时间复杂度O(10^n)，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public int countNumbersWithUniqueDigits(int n) {
        if (n == 0) {
            return 1;
        }

        int count = 0;

        for (int i = 0; i <= 9; i++) {
            if (i == 0) {
                //len：当前数字i的长度，这里的长度考虑到前驱0，保证回溯的跳出条件
                count = count + backtrack(i, 1, n, new boolean[10]);
            } else {
                boolean[] visited = new boolean[10];
                //从i开始遍历，置i已访问
                visited[i] = true;
                //len：当前数字i的长度，这里的长度考虑到前驱0，保证回溯的跳出条件
                count = count + backtrack(i, 1, n, visited);
                visited[i] = false;
            }
        }

        return count;
    }

    /**
     * 动态规划
     * dp[i]：[0,10^i)中各位数字都不同的数字的个数
     * dp[i] = dp[i-1] + (dp[i-1]-dp[i-2])*(10-i+1)
     * dp[i]包括[0,10^(i-1))中各位数字都不同的数字的个数，即dp[i-1]，和[10^(i-1),10^i)中各位数字都不同的数字的个数，
     * 即[10^(i-2),10^(i-1))中各位数字都不同的数字的个数，即dp[i-1]-dp[i-2]，乘以(10-i+1)，
     * 因为[10^(i-2),10^(i-1))中各位数字都不同的数字占0-9中10位数字的i-1位，还剩(10-i+1)位不同的数字可以选择
     * 时间复杂度O(n)，空间复杂度O(n)
     * <p>
     * 例如：n=3，dp[3]包括[0,100)中各位数字都不同的数字的个数dp[2]，和[100,1000)中各位数字都不同的数字的个数组成，
     * [100,1000)中各位数字都不同的数字的个数由[10,100)中各位数字都不同的数字拼接上和[10,100)中各位数字都不同的数字组成，
     * [10,100)中各位数字都不同的数字的个数dp[2]-dp[1]，和[10,100)中各位数字都不同的数字的个数10-3+1，
     * 即为(dp[2]-dp[1])*(10-3+1)，dp[3]=dp[2]+(dp[2]-dp[1])*(10-3+1)
     *
     * @param n
     * @return
     */
    public int countNumbersWithUniqueDigits2(int n) {
        if (n == 0) {
            return 1;
        }

        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 10;

        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + (dp[i - 1] - dp[i - 2]) * (10 - i + 1);
        }

        return dp[n];
    }

    private int backtrack(int t, int len, int n, boolean[] visited) {
        if (len == n) {
            return 1;
        }

        int count = 0;

        for (int i = 0; i <= 9; i++) {
            //i和数字t中某一位有重复，则直接进行下次循环
            if (visited[i]) {
                continue;
            }

            if (i == 0 && t == 0) {
                count = count + backtrack(t, len + 1, n, visited);
            } else {
                visited[i] = true;
                count = count + backtrack(t * 10 + i, len + 1, n, visited);
                visited[i] = false;
            }
        }

        return count;
    }
}
