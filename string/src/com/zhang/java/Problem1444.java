package com.zhang.java;

/**
 * @Date 2023/8/20 08:41
 * @Author zsy
 * @Description 切披萨的方案数 二维前缀和类比Problem304 动态规划类比Problem198、Problem213、Problem279、Problem322、Problem343、Problem377、Problem416、Problem474、Problem494、Problem518、Problem983、Problem1340、Problem1388、Offer14、Offer14_2、CircleBackToOrigin、Knapsack 记忆化搜索类比Problem62、Problem63、Problem64、Problem70、Problem329、Problem509、Problem1340、Problem1388、Offer10、Offer10_2
 * 给你一个 rows x cols 大小的矩形披萨和一个整数 k ，矩形包含两种字符： 'A' （表示苹果）和 '.' （表示空白格子）。
 * 你需要切披萨 k-1 次，得到 k 块披萨并送给别人。
 * 切披萨的每一刀，先要选择是向垂直还是水平方向切，再在矩形的边界上选一个切的位置，将披萨一分为二。
 * 如果垂直地切披萨，那么需要把左边的部分送给一个人，如果水平地切，那么需要把上面的部分送给一个人。
 * 在切完最后一·后，需要把剩下来的一块送给最后一个人。
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
        String[] pizza = {"A..", "AAA", "..."};
        int k = 3;
        System.out.println(problem1444.ways(pizza, k));
        System.out.println(problem1444.ways2(pizza, k));
    }

    /**
     * 动态规划+二维前缀和
     * dp[i][j][k]：左上角(i,j)和右下角(pizza.length-1,pizza[0].length()-1)围成矩形表示的披萨，分成k个pizza的方案数
     * preSum[i][j]：左上角(0,0)和右下角(i-1,j-1)围成矩形表示的披萨中的苹果数量
     * dp[i][j][k] = sum(dp[m][j][k-1]) + sum(dp[i][n][k-1]) (i < m < pizza.length，j < n < pizza[0].length())
     * (左上角(i,j)和右下角(m-1,pizza[0].length()-1)围成矩形表示的披萨中的苹果数量大于等于1，
     * 左上角(m,j)和右下角(pizza.length-1,pizza[0].length()-1)围成矩形表示的披萨中的苹果数量大于等于k-1，才能水平切；
     * 左上角(i,j)和右下角(pizza.length-1,n-1)围成矩形表示的披萨中的苹果数量大于等于1，
     * 左上角(i,n)和右下角(pizza.length-1,pizza[0].length()-1)围成矩形表示的披萨中的苹果数量大于等于k-1，才能垂直切)
     * preSum[i][j] = preSum[i][j-1] + preSum[i-1][j] - preSum[i-1][j-1] + (pizza[i-1].charAt(j-1) == 'A')
     * 时间复杂度O(kmn(m+n))，空间复杂度O(kmn)
     *
     * @param pizza
     * @param k
     * @return
     */
    public int ways(String[] pizza, int k) {
        int[][][] dp = new int[pizza.length][pizza[0].length()][k + 1];
        int[][] preSum = new int[pizza.length + 1][pizza[0].length() + 1];

        //得到preSum
        for (int i = 1; i <= pizza.length; i++) {
            for (int j = 1; j <= pizza[0].length(); j++) {
                preSum[i][j] = preSum[i - 1][j] + preSum[i][j - 1] - preSum[i - 1][j - 1] +
                        (pizza[i - 1].charAt(j - 1) == 'A' ? 1 : 0);
            }
        }

        //dp初始化
        for (int i = 0; i < pizza.length; i++) {
            for (int j = 0; j < pizza[0].length(); j++) {
                //左上角(i,j)和右下角(pizza.length-1,pizza[0].length()-1)围成矩形表示的披萨中的苹果数量大于等于1，
                //则当前围成矩形表示的披萨，分成1个pizza的方案数为1
                if (getPizzaAppleCount(i, j, pizza.length - 1, pizza[0].length() - 1, preSum) >= 1) {
                    dp[i][j][1] = 1;
                }
            }
        }

        for (int i = pizza.length - 1; i >= 0; i--) {
            for (int j = pizza[0].length() - 1; j >= 0; j--) {
                //左上角(i,j)和右下角(pizza.length-1,pizza[0].length()-1)围成矩形表示的披萨，分成m个pizza的方案数
                for (int m = 2; m <= k; m++) {
                    //上边披萨中苹果数量大于等于1，即左上角(i,j)和右下角(n-1,pizza[0].length()-1)围成矩形表示的披萨中的苹果数量大于等于1，
                    //下边披萨中苹果数量大于等于m-1，即左上角(n,j)和右下角(pizza.length-1,pizza[0].length()-1)围成矩形表示的披萨中的苹果数量大于等于m-1，
                    //才能往下水平切
                    for (int n = i + 1; n < pizza.length; n++) {
                        if (getPizzaAppleCount(i, j, n - 1, pizza[0].length() - 1, preSum) >= 1 &&
                                getPizzaAppleCount(n, j, pizza.length - 1, pizza[0].length() - 1, preSum) >= m - 1) {
                            dp[i][j][m] = (dp[i][j][m] + dp[n][j][m - 1]) % MOD;
                        }
                    }

                    //左边披萨中苹果数量大于等于1，即左上角(i,j)和右下角(pizza.length-1,n-1)围成矩形表示的披萨中的苹果数量大于等于1，
                    //右边披萨中苹果数量大于等于m-1，即左上角(i,n)和右下角(pizza.length-1,pizza[0].length()-1)围成矩形表示的披萨中的苹果数量大于等于m-1，
                    //才能往右垂直切
                    for (int n = j + 1; n < pizza[0].length(); n++) {
                        if (getPizzaAppleCount(i, j, pizza.length - 1, n - 1, preSum) >= 1 &&
                                getPizzaAppleCount(i, n, pizza.length - 1, pizza[0].length() - 1, preSum) >= m - 1) {
                            dp[i][j][m] = (dp[i][j][m] + dp[i][n][m - 1]) % MOD;
                        }
                    }
                }
            }
        }

        return dp[0][0][k];
    }

    /**
     * 递归+记忆化搜索+二维前缀和
     * dp[i][j][k]：左上角(i,j)和右下角(pizza.length-1,pizza[0].length()-1)围成矩形表示的披萨，分成k个pizza的方案数
     * preSum[i][j]：左上角(0,0)和右下角(i-1,j-1)围成矩形表示的披萨中的苹果数量
     * 时间复杂度O(kmn(m+n))，空间复杂度O(kmn)
     *
     * @param pizza
     * @param k
     * @return
     */
    public int ways2(String[] pizza, int k) {
        int[][][] dp = new int[pizza.length][pizza[0].length()][k + 1];
        int[][] preSum = new int[pizza.length + 1][pizza[0].length() + 1];

        //得到preSum
        for (int i = 1; i <= pizza.length; i++) {
            for (int j = 1; j <= pizza[0].length(); j++) {
                preSum[i][j] = preSum[i - 1][j] + preSum[i][j - 1] - preSum[i - 1][j - 1] +
                        (pizza[i - 1].charAt(j - 1) == 'A' ? 1 : 0);
            }
        }

        //dp初始化
        for (int i = 0; i < pizza.length; i++) {
            for (int j = 0; j < pizza[0].length(); j++) {
                //左上角(i,j)和右下角(pizza.length-1,pizza[0].length()-1)围成矩形表示的披萨中的苹果数量大于等于1，
                //则当前围成矩形表示的披萨，分成1个pizza的方案数为1
                if (getPizzaAppleCount(i, j, pizza.length - 1, pizza[0].length() - 1, preSum) >= 1) {
                    dp[i][j][1] = 1;
                }
            }
        }

        dfs(0, 0, pizza, k, dp, preSum);

        return dp[0][0][k];
    }

    /**
     * 左上角(i,j)和右下角(pizza.length-1,pizza[0].length()-1)围成矩形表示的披萨，分成k个pizza的方案数
     *
     * @param i
     * @param j
     * @param pizza
     * @param k
     * @param dp
     * @param preSum
     */
    private void dfs(int i, int j, String[] pizza, int k, int[][][] dp, int[][] preSum) {
        //i、j越界，或者分成1个pizza，或者已经得到了左上角(i,j)分成k个pizza的方案数，直接返回
        if (i < 0 || i >= pizza.length || j < 0 || j >= pizza[0].length() ||
                k == 1 || dp[i][j][k] != 0) {
            return;
        }

        //上边披萨中苹果数量大于等于1，即左上角(i,j)和右下角(m-1,pizza[0].length()-1)围成矩形表示的披萨中的苹果数量大于等于1，
        //下边披萨中苹果数量大于等于k-1，即左上角(m,j)和右下角(pizza.length-1,pizza[0].length()-1)围成矩形表示的披萨中的苹果数量大于等于k-1，
        //才能往下水平切
        for (int m = i + 1; m < pizza.length; m++) {
            if (getPizzaAppleCount(i, j, m - 1, pizza[0].length() - 1, preSum) >= 1 &&
                    getPizzaAppleCount(m, j, pizza.length - 1, pizza[0].length() - 1, preSum) >= k - 1) {
                dfs(m, j, pizza, k - 1, dp, preSum);
                dp[i][j][k] = (dp[i][j][k] + dp[m][j][k - 1]) % MOD;
            }
        }

        //左边披萨中苹果数量大于等于1，即左上角(i,j)和右下角(pizza.length-1,n-1)围成矩形表示的披萨中的苹果数量大于等于1，
        //右边披萨中苹果数量大于等于k-1，即左上角(i,n)和右下角(pizza.length-1,pizza[0].length()-1)围成矩形表示的披萨中的苹果数量大于等于k-1，
        //才能往右垂直切
        for (int n = j + 1; n < pizza[0].length(); n++) {
            if (getPizzaAppleCount(i, j, pizza.length - 1, n - 1, preSum) >= 1 &&
                    getPizzaAppleCount(i, n, pizza.length - 1, pizza[0].length() - 1, preSum) >= k - 1) {
                dfs(i, n, pizza, k - 1, dp, preSum);
                dp[i][j][k] = (dp[i][j][k] + dp[i][n][k - 1]) % MOD;
            }
        }
    }

    /**
     * 获取左上角(row1,col1)和右下角(row2,col2)围成矩形表示的披萨中的苹果数量
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @param row1
     * @param col1
     * @param row2
     * @param col2
     * @param preSum
     * @return
     */
    private int getPizzaAppleCount(int row1, int col1, int row2, int col2, int[][] preSum) {
        return preSum[row2 + 1][col2 + 1] - preSum[row2 + 1][col1] - preSum[row1][col2 + 1] + preSum[row1][col1];
    }
}
