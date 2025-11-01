package com.zhang.java;

/**
 * @Date 2023/8/20 08:41
 * @Author zsy
 * @Description 切披萨的方案数 燧原机试题 快手面试题 二维前缀和类比Problem304、Problem363、Problem1292、Problem1738 动态规划类比Problem198、Problem213、Problem256、Problem265、Problem279、Problem322、Problem338、Problem343、Problem377、Problem416、Problem474、Problem494、Problem518、Problem746、Problem983、Problem1340、Problem1388、Problem1473、Offer14、Offer14_2、Offer60、CircleBackToOrigin、Knapsack 记忆化搜索类比Problem62、Problem63、Problem64、Problem70、Problem329、Problem509、Problem1340、Problem1388、Offer10、Offer10_2
 * 给你一个 rows x cols 大小的矩形披萨和一个整数 k ，矩形包含两种字符： 'A' （表示苹果）和 '.' （表示空白格子）。
 * 你需要切披萨 k-1 次，得到 k 块披萨并送给别人。
 * 切披萨的每一刀，先要选择是向垂直还是水平方向切，再在矩形的边界上选一个切的位置，将披萨一分为二。
 * 如果垂直地切披萨，那么需要把左边的部分送给一个人，如果水平地切，那么需要把上面的部分送给一个人。
 * 在切完最后一刀后，需要把剩下来的一块送给最后一个人。
 * 请你返回确保每一块披萨包含 至少 一个苹果的切披萨方案数。
 * 由于答案可能是个很大的数字，请你返回它对 10^9 + 7 取余的结果。
 * <p>
 * 输入：pizza = ["A..","AAA","..."], k = 3
 * 输出：3
 * 解释：上图展示了三种切披萨的方案。注意每一块披萨都至少包含一个苹果。
 * <p>
 * 输入：pizza = ["A..","AA.","..."], k = 3
 * 输出：1
 * <p>
 * 输入：pizza = ["A..","A..","..."], k = 1
 * 输出：1
 * <p>
 * 1 <= rows, cols <= 50
 * rows == pizza.length
 * cols == pizza[i].length
 * 1 <= k <= 10
 * pizza 只包含字符 'A' 和 '.' 。
 */
public class Problem1444 {
    private final int MOD = (int) 1e9 + 7;

    public static void main(String[] args) {
        Problem1444 problem1444 = new Problem1444();
//        String[] pizza = {"A..", "AAA", "..."};
//        int k = 3;
        String[] pizza = {".A..A", "A.A..", "A.AA.", "AAAA.", "A.AA."};
        int k = 5;
        System.out.println(problem1444.ways(pizza, k));
        System.out.println(problem1444.ways2(pizza, k));
        System.out.println(problem1444.ways3(pizza, k));
    }

    /**
     * 动态规划+二维前缀和
     * dp[i][j][k]：左上角(i,j)和右下角(m-1,n-1)构成的矩形得到k个披萨的方案数
     * dp[i][j][k] = sum(dp[i2+1][j][k-1]) + sum(dp[i][j2+1][k-1]) (i <= i2 < m-1) (j <= j2 < n-1)
     * (左上角(i,j)和右下角(i2,n-1)构成的矩形中苹果数量大于等于1，左上角(i2+1,j)和右下角(m-1,n-1)构成的矩形中苹果数量大于等于k-1，才能水平切)
     * (左上角(i,j)和右下角(m-1,j2)构成的矩形中苹果数量大于等于1，左上角(i,j2+1)和右下角(m-1,n-1)构成的矩形中苹果数量大于等于k-1，才能垂直切)
     * 注意：dp不能定义为左上角(0,0)和右下角(i,j)构成的矩形得到k个披萨的方案数，
     * 因为水平切把上半矩形分给1个人，而不能分给多个人；垂直切把左半矩形分给1个人，而不能分给多个人
     * 时间复杂度O(mnk(m+n))，空间复杂度O(mnk)
     *
     * @param pizza
     * @param k
     * @return
     */
    public int ways(String[] pizza, int k) {
        int m = pizza.length;
        int n = pizza[0].length();
        int[][][] dp = new int[m][n][k + 1];
        int[][] preSum = new int[m + 1][n + 1];

        //得到preSum
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                preSum[i][j] = preSum[i - 1][j] + preSum[i][j - 1] - preSum[i - 1][j - 1] +
                        (pizza[i - 1].charAt(j - 1) == 'A' ? 1 : 0);
            }
        }

        //dp初始化，左上角(i,j)和右下角(m-1,n-1)构成的矩形中的苹果数量大于等于1，则得到1个披萨的方案数为1
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (getAppleCount(i, j, m - 1, n - 1, preSum) >= 1) {
                    dp[i][j][1] = 1;
                }
            }
        }

        //左上角(i,j)和右下角(m-1,n-1)构成的矩形得到count个披萨的方案数
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                for (int count = 2; count <= k; count++) {
                    //左上角(i,j)和右下角(i2,n-1)构成的矩形中苹果数量大于等于1，
                    //左上角(i2+1,j)和右下角(m-1,n-1)构成的矩形中苹果数量大于等于count-1，才能水平切
                    for (int i2 = i; i2 < m - 1; i2++) {
                        if (getAppleCount(i, j, i2, n - 1, preSum) >= 1 &&
                                getAppleCount(i2 + 1, j, m - 1, n - 1, preSum) >= count - 1) {
                            dp[i][j][count] = (dp[i][j][count] + dp[i2 + 1][j][count - 1]) % MOD;
                        }
                    }

                    //左上角(i,j)和右下角(m-1,j2)构成的矩形中苹果数量大于等于1，
                    //左上角(i,j2+1)和右下角(m-1,n-1)构成的矩形中苹果数量大于等于count-1，才能垂直切
                    for (int j2 = j; j2 < n - 1; j2++) {
                        if (getAppleCount(i, j, m - 1, j2, preSum) >= 1 &&
                                getAppleCount(i, j2 + 1, m - 1, n - 1, preSum) >= count - 1) {
                            dp[i][j][count] = (dp[i][j][count] + dp[i][j2 + 1][count - 1]) % MOD;
                        }
                    }
                }
            }
        }

        return dp[0][0][k];
    }

    /**
     * 动态规划优化，使用滚动数组
     * 时间复杂度O(mnk(m+n))，空间复杂度O(mn)
     *
     * @param pizza
     * @param k
     * @return
     */
    public int ways2(String[] pizza, int k) {
        int m = pizza.length;
        int n = pizza[0].length();
        int[][] dp = new int[m][n];
        int[][] preSum = new int[m + 1][n + 1];

        //得到preSum
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                preSum[i][j] = preSum[i - 1][j] + preSum[i][j - 1] - preSum[i - 1][j - 1] +
                        (pizza[i - 1].charAt(j - 1) == 'A' ? 1 : 0);
            }
        }

        //dp初始化，左上角(i,j)和右下角(m-1,n-1)构成的矩形中的苹果数量大于等于1，则得到1个披萨的方案数为1
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (getAppleCount(i, j, m - 1, n - 1, preSum) >= 1) {
                    dp[i][j] = 1;
                }
            }
        }

        //左上角(i,j)和右下角(m-1,n-1)构成的矩形得到count个披萨的方案数
        //注意：滚动数组优化，最外层要先遍历count
        for (int count = 2; count <= k; count++) {
            int[][] temp = new int[m][n];

            for (int i = m - 1; i >= 0; i--) {
                for (int j = n - 1; j >= 0; j--) {
                    //左上角(i,j)和右下角(i2,n-1)构成的矩形中苹果数量大于等于1，
                    //左上角(i2+1,j)和右下角(m-1,n-1)构成的矩形中苹果数量大于等于count-1，才能水平切
                    for (int i2 = i; i2 < m - 1; i2++) {
                        if (getAppleCount(i, j, i2, n - 1, preSum) >= 1 &&
                                getAppleCount(i2 + 1, j, m - 1, n - 1, preSum) >= count - 1) {
                            temp[i][j] = (temp[i][j] + dp[i2 + 1][j]) % MOD;
                        }
                    }

                    //左上角(i,j)和右下角(m-1,j2)构成的矩形中苹果数量大于等于1，
                    //左上角(i,j2+1)和右下角(m-1,n-1)构成的矩形中苹果数量大于等于count-1，才能垂直切
                    for (int j2 = j; j2 < n - 1; j2++) {
                        if (getAppleCount(i, j, m - 1, j2, preSum) >= 1 &&
                                getAppleCount(i, j2 + 1, m - 1, n - 1, preSum) >= count - 1) {
                            temp[i][j] = (temp[i][j] + dp[i][j2 + 1]) % MOD;
                        }
                    }
                }
            }

            dp = temp;
        }

        return dp[0][0];
    }

    /**
     * 递归+记忆化搜索+二维前缀和
     * 时间复杂度O(mnk(m+n))，空间复杂度O(mnk)
     *
     * @param pizza
     * @param k
     * @return
     */
    public int ways3(String[] pizza, int k) {
        int m = pizza.length;
        int n = pizza[0].length();
        int[][][] dp = new int[m][n][k + 1];
        int[][] preSum = new int[m + 1][n + 1];

        //得到preSum
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                preSum[i][j] = preSum[i - 1][j] + preSum[i][j - 1] - preSum[i - 1][j - 1] +
                        (pizza[i - 1].charAt(j - 1) == 'A' ? 1 : 0);
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int count = 0; count <= k; count++) {
                    //初始化为-1，表示当前dp未访问
                    dp[i][j][count] = -1;
                }
            }
        }

        dfs(0, 0, k, m, n, dp, preSum);

        return dp[0][0][k];
    }

    /**
     * 左上角(i,j)和右下角(m-1,n-1)构成的矩形得到k个披萨的方案数
     *
     * @param i
     * @param j
     * @param k
     * @param m
     * @param n
     * @param dp
     * @param preSum
     * @return
     */
    private int dfs(int i, int j, int k, int m, int n, int[][][] dp, int[][] preSum) {
        //左上角(i,j)和右下角(m-1,n-1)构成的矩形中的苹果数量大于等于1，则得到1个披萨的方案数为1
        if (k == 1) {
            dp[i][j][k] = getAppleCount(i, j, m - 1, n - 1, preSum) >= 1 ? 1 : 0;
            return dp[i][j][k];
        }

        //当前dp已访问，直接返回当前dp
        if (dp[i][j][k] != -1) {
            return dp[i][j][k];
        }

        //dp初始化为-1表示未访问，求当前dp要重新初始化为0
        dp[i][j][k] = 0;

        //左上角(i,j)和右下角(i2,n-1)构成的矩形中苹果数量大于等于1，
        //左上角(i2+1,j)和右下角(m-1,n-1)构成的矩形中苹果数量大于等于count-1，才能水平切
        for (int i2 = i; i2 < m - 1; i2++) {
            if (getAppleCount(i, j, i2, n - 1, preSum) >= 1 &&
                    getAppleCount(i2 + 1, j, m - 1, n - 1, preSum) >= k - 1) {
                dp[i][j][k] = (dp[i][j][k] + dfs(i2 + 1, j, k - 1, m, n, dp, preSum)) % MOD;
            }
        }

        //左上角(i,j)和右下角(m-1,j2)构成的矩形中苹果数量大于等于1，
        //左上角(i,j2+1)和右下角(m-1,n-1)构成的矩形中苹果数量大于等于k-1，才能垂直切
        for (int j2 = j; j2 < n - 1; j2++) {
            if (getAppleCount(i, j, m - 1, j2, preSum) >= 1 &&
                    getAppleCount(i, j2 + 1, m - 1, n - 1, preSum) >= k - 1) {
                dp[i][j][k] = (dp[i][j][k] + dfs(i, j2 + 1, k - 1, m, n, dp, preSum)) % MOD;
            }
        }

        return dp[i][j][k];
    }

    /**
     * 左上角(row1,col1)和右下角(row2,col2)构成的矩形中的苹果数量
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @param row1
     * @param col1
     * @param row2
     * @param col2
     * @param preSum
     * @return
     */
    private int getAppleCount(int row1, int col1, int row2, int col2, int[][] preSum) {
        return preSum[row2 + 1][col2 + 1] - preSum[row2 + 1][col1] - preSum[row1][col2 + 1] + preSum[row1][col1];
    }
}
