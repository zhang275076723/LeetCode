package com.zhang.java;


/**
 * @Date 2023/12/13 08:04
 * @Author zsy
 * @Description 优美的排列 类比Problem667 状态压缩类比Problem187、Problem294、Problem464、Problem473、Problem638、Problem698、Problem847、Problem1908 记忆化搜索类比 动态规划类比
 * 假设有从 1 到 n 的 n 个整数。用这些整数构造一个数组 perm（下标从 1 开始），
 * 只要满足下述条件 之一 ，该数组就是一个 优美的排列 ：
 * 1、perm[i] 能够被 i 整除
 * 2、i 能够被 perm[i] 整除
 * 给你一个整数 n ，返回可以构造的 优美排列 的 数量 。
 * <p>
 * 输入：n = 2
 * 输出：2
 * 解释：
 * 第 1 个优美的排列是 [1,2]：
 * - perm[1] = 1 能被 i = 1 整除
 * - perm[2] = 2 能被 i = 2 整除
 * 第 2 个优美的排列是 [2,1]:
 * - perm[1] = 2 能被 i = 1 整除
 * - i = 2 能被 perm[2] = 1 整除
 * <p>
 * 输入：n = 1
 * 输出：1
 * <p>
 * 1 <= n <= 15
 */
public class Problem526 {
    public static void main(String[] args) {
        Problem526 problem526 = new Problem526();
        int n = 4;
        //1234、1432、2134、2431、3214、3412、4132、4231
        System.out.println(problem526.countArrangement(n));
        System.out.println(problem526.countArrangement2(n));
        System.out.println(problem526.countArrangement3(n));
    }

    /**
     * 回溯+剪枝
     * 时间复杂度O(n!)，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public int countArrangement(int n) {
        return backtrack(1, n, new boolean[n + 1]);
    }

    /**
     * 记忆化搜索+二进制状态压缩
     * dp[i]：二进制访问状态i的情况下，优美排列的数量
     * 二进制访问状态第i位(低位到高位，最低位为第0位)为1，则表示数字i+1已访问；第i位为0，则表示数字i+1未访问
     * 例如：当前二进制访问状态为00010，则优美排列的第一个数字为2，如果00010下一个二进制访问状态为10010，则优美排列的第二个数字5
     * 时间复杂度O(n*2^n)，空间复杂度O(2^n) (共2^n种状态，每种状态需要O(n)计算得到)
     *
     * @param n
     * @return
     */
    public int countArrangement2(int n) {
        //注意：记忆化搜索和动态规划都使用了dp，但记忆化最后返回的是dp[0]，动态规划返回的是dp[(1<<n)-1]
        return dfs(1, n, 0, new int[1 << n]);
    }

    /**
     * 动态规划+二进制状态压缩
     * dp[i]：二进制访问状态i的情况下，优美排列的数量
     * dp[i] = sum(dp[j]) (二进制访问状态j的某一位未访问，置为1之后的二进制访问状态为i)
     * 例如：当前二进制访问状态为00010，则优美排列的第一个数字为2，如果00010下一个二进制访问状态为10010，则优美排列的第二个数字5
     * 时间复杂度O(n*2^n)，空间复杂度O(2^n) (共2^n种状态，每种状态需要O(n)计算得到)
     *
     * @param n
     * @return
     */
    public int countArrangement3(int n) {
        int[] dp = new int[1 << n];
        //dp初始化，二进制访问状态0的情况下，优美排列的数量为1
        dp[0] = 1;

        //二进制访问状态i，第k位为1，则数字k+1已访问
        for (int i = 0; i < (1 << n); i++) {
            //二进制访问状态i不可达，直接进行下次循环
            if (dp[i] == 0) {
                continue;
            }

            //二进制访问状态i中1的个数，即已访问的数字个数
            int bitCount = 0;
            //当前数字i
            int temp = i;

            //统计temp表示的二进制数中1的个数
            while (temp != 0) {
                //将temp表示的二进制数中最低位的1置为0
                temp = temp & (temp - 1);
                bitCount++;
            }

            //当前数字j，即第j-1位
            for (int j = 1; j <= n; j++) {
                //二进制访问状态i中第j-1位为1，即数字j已访问，直接进行下次循环
                if (((i >>> (j - 1)) & 1) == 1) {
                    continue;
                }

                //二进制访问状态i^(1<<(j-1))中1的个数为bitCount+1，即二进制访问状态i中1的个数加1
                if ((bitCount + 1) % j == 0 || j % (bitCount + 1) == 0) {
                    //i^(1<<(j-1))：二进制访问状态i的第j-1位由0置为1的二进制访问状态
                    dp[i ^ (1 << (j - 1))] = dp[i ^ (1 << (j - 1))] + dp[i];
                }
            }
        }

        //注意：记忆化搜索和动态规划都使用了dp，但记忆化最后返回的是dp[0]，动态规划返回的是dp[(1<<n)-1]
        return dp[(1 << n) - 1];
    }

    private int backtrack(int t, int n, boolean[] visited) {
        if (t == n + 1) {
            return 1;
        }

        int count = 0;

        for (int i = 1; i <= n; i++) {
            if (visited[i]) {
                continue;
            }

            if (i % t == 0 || t % i == 0) {
                visited[i] = true;
                count = count + backtrack(t + 1, n, visited);
                visited[i] = false;
            }
        }

        return count;
    }

    private int dfs(int t, int n, int state, int[] dp) {
        if (t == n + 1) {
            dp[state] = 1;
            return 1;
        }

        if (dp[state] != 0) {
            return dp[state];
        }

        for (int i = 1; i <= n; i++) {
            //二进制访问状态state的第i-1位为1，则数字i已访问，直接进行下次循环
            if (((state >>> (i - 1)) & 1) == 1) {
                continue;
            }

            if (i % t == 0 || t % i == 0) {
                //state^(1<<(i-1))：将二进制访问状态state中未访问的第i-1位由0置为1，得到第i-1位置为1的二进制访问状态
                dp[state] = dp[state] + dfs(t + 1, n, state ^ (1 << (i - 1)), dp);
            }
        }

        return dp[state];
    }
}
