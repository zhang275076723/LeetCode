package com.zhang.java;

/**
 * @Date 2022/11/27 13:41
 * @Author zsy
 * @Description 斐波那契数 矩阵快速幂类比Problem70、Problem1137、Problem1641、Offer10、Offer10_2 各种数类比Problem202、Problem204、Problem263、Problem264、Problem306、Problem313、Problem507、Problem728、Problem842、Problem878、Problem1175、Problem1201、Problem1291、Offer10、Offer49 记忆化搜索类比Problem62、Problem63、Problem64、Problem70、Problem329、Problem1340、Problem1388、Problem1444、Offer10、Offer10_2 同Offer10
 * 斐波那契数 （通常用 F(n) 表示）形成的序列称为 斐波那契数列 。
 * 该数列由 0 和 1 开始，后面的每一项数字都是前面两项数字的和。也就是：
 * F(0) = 0，F(1) = 1
 * F(n) = F(n - 1) + F(n - 2)，其中 n > 1
 * 给定 n ，请计算 F(n) 。
 * <p>
 * 输入：n = 2
 * 输出：1
 * 解释：F(2) = F(1) + F(0) = 1 + 0 = 1
 * <p>
 * 输入：n = 3
 * 输出：2
 * 解释：F(3) = F(2) + F(1) = 1 + 1 = 2
 * <p>
 * 输入：n = 4
 * 输出：3
 * 解释：F(4) = F(3) + F(2) = 2 + 1 = 3
 * <p>
 * 0 <= n <= 30
 */
public class Problem509 {
    public static void main(String[] args) {
        Problem509 problem509 = new Problem509();
        System.out.println(problem509.fib(5));
        System.out.println(problem509.fib2(5));
        System.out.println(problem509.fib3(5));
        System.out.println(problem509.fib4(5));
    }

    /**
     * 递归+记忆化搜索
     * dp[i]：F(i)所表示的斐波那契数
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public int fib(int n) {
        if (n == 0 || n == 1) {
            return n;
        }

        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;

        dfs(n, dp);

        return dp[n];
    }

    /**
     * 动态规划
     * dp[i]：F(i)所表示的斐波那契数
     * dp[i] = dp[i-1] + dp[i-2]
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public int fib2(int n) {
        if (n == 0 || n == 1) {
            return n;
        }

        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }

    /**
     * 动态规划优化，使用滚动数组
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int fib3(int n) {
        if (n == 0 || n == 1) {
            return n;
        }

        int p = 0;
        int q = 1;

        for (int i = 2; i <= n; i++) {
            int temp = p + q;
            p = q;
            q = temp;
        }

        return q;
    }

    /**
     * 矩阵快速幂
     * [ f(n) ]         [1 1] ^ (n-1)       [f(1)]
     * [f(n-1)]    =    [1 0]          *    [f(0)]
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int fib4(int n) {
        if (n == 0 || n == 1) {
            return n;
        }

        int[][] result = {{1, 1}, {1, 0}};
        result = quickPow(result, n - 1);
        result = multiply(result, new int[][]{{1}, {0}});

        return result[0][0];
    }

    private void dfs(int n, int[] dp) {
        if (n == 0 || dp[n] != 0) {
            return;
        }

        dfs(n - 1, dp);
        dfs(n - 2, dp);

        dp[n] = dp[n - 1] + dp[n - 2];
    }

    private int[][] quickPow(int[][] a, int n) {
        int[][] result = new int[a.length][a.length];

        for (int i = 0; i < a.length; i++) {
            result[i][i] = 1;
        }

        while (n != 0) {
            if ((n & 1) == 1) {
                result = multiply(result, a);
            }

            a = multiply(a, a);
            n = n >>> 1;
        }

        return result;
    }

    private int[][] multiply(int[][] a, int[][] b) {
        int[][] result = new int[a.length][b[0].length];

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                for (int k = 0; k < a[0].length; k++) {
                    result[i][j] = result[i][j] + a[i][k] * b[k][j];
                }
            }
        }

        return result;
    }
}
