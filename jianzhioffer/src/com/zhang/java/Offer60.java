package com.zhang.java;

import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * @Date 2022/4/7 12:14
 * @Author zsy
 * @Description 把n个骰子扔在地上，所有骰子朝上一面的点数之和为s。
 * 输入n，打印出s的所有可能的值出现的概率。
 * 你需要用一个浮点数数组返回答案，其中第 i 个元素代表这 n 个骰子所能掷出的点数集合中第 i 小的那个的概率。
 * 1 <= n <= 11
 * <p>
 * 输入: 1
 * 输出: [0.16667,0.16667,0.16667,0.16667,0.16667,0.16667]
 * <p>
 * 输入: 2
 * 输出: [0.02778,0.05556,0.08333,0.11111,0.13889,0.16667,0.13889,0.11111,0.08333,0.05556,0.02778]
 */
public class Offer60 {
    public static void main(String[] args) {
        Offer60 offer60 = new Offer60();
        System.out.println(Arrays.toString(offer60.dicesProbability(2)));
        System.out.println(Arrays.toString(offer60.dicesProbability2(2)));
        System.out.println(Arrays.toString(offer60.dicesProbability3(2)));
    }

    /**
     * 暴力，时间复杂度O(6^n)，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public double[] dicesProbability(int n) {
        //结果概率数组
        double[] result = new double[5 * n + 1];
        //掷n个骰子点数之和的计数数组
        int[] count = new int[5 * n + 1];
        //掷n个骰子点数数组
        int[] nPoints = new int[n];
        //掷n个骰子的总数量
        int total = (int) Math.pow(6, n);

        backtrack(0, n, count, nPoints);

        //保留5位小数
        DecimalFormat df = new DecimalFormat("#.00000");
        for (int i = 0; i < result.length; i++) {
            result[i] = Double.parseDouble(df.format((double) count[i] / total));
        }

        return result;
    }

    /**
     * 动态规划，时间复杂度O(n^2)，空间复杂度O(n^2)
     * dp[i][j]：掷i个骰子点数之和为j的数量
     * dp[i][j] = dp[i-1][j-1] + dp[i-1][j-2] + dp[i-1][j-3] + dp[i-1][j-4] + dp[i-1][j-5] + dp[i-1][j-6]
     *
     * @param n
     * @return
     */
    public double[] dicesProbability2(int n) {
        double[] result = new double[5 * n + 1];
        //掷i个骰子点数之和为j的数量
        int[][] dp = new int[n + 1][6 * n + 1];
        //掷n个骰子的总样本数量
        int total = (int) Math.pow(6, n);

        //dp初始化
        for (int i = 1; i <= 6; i++) {
            dp[1][i] = 1;
        }

        for (int i = 2; i <= n; i++) {
            for (int j = i; j <= 6 * n; j++) {
                for (int k = 1; k <= 6; k++) {
                    //当掷前i-1个骰子点数之和大于等于i-1的情况下，才有效
                    if (j - k >= i - 1) {
                        dp[i][j] = dp[i][j] + dp[i - 1][j - k];
                    }
                }
            }
        }

        for (int i = 0; i < result.length; i++) {
            result[i] = (double) dp[n][i + n] / total;
        }

        return result;
    }

    /**
     * 动态规划优化，时间复杂度O(n^2)，空间复杂度O(n)
     * dp[i]
     *
     * @param n
     * @return
     */
    public double[] dicesProbability3(int n) {
        double[] result = new double[5 * n + 1];
        //掷i个骰子点数之和为j的数量
        int[] dp = new int[6 * n + 1];
        //掷n个骰子的总样本数量
        int total = (int) Math.pow(6, n);

        for (int i = 1; i <= 6; i++) {
            dp[i] = 1;
        }

        for (int i = 2; i <= n; i++) {
            for (int j = 6 * n; j >= i; j--) {
                //将掷前i-1个骰子点数之和为j的数量置为0，否则会受上次的影响
                dp[j] = 0;
                for (int k = 1; k <= 6; k++) {
                    //当掷前i-1个骰子点数之和大于等于i-1的情况下，才有效
                    if (j - k >= i - 1) {
                        dp[j] = dp[j] + dp[j - k];
                    }
                }
            }
        }

        for (int i = 0; i < result.length; i++) {
            result[i] = (double) dp[i + n] / total;
        }

        return result;
    }

    /**
     * 回溯
     *
     * @param t       掷第t+1个骰子
     * @param n       总骰子数
     * @param count   掷n个骰子点数之和的计数数组
     * @param nPoints n个骰子点数数组，nPoints[t] = i，表示第t个骰子的点数为i
     */
    public void backtrack(int t, int n, int[] count, int[] nPoints) {
        if (t == n) {
            int sum = 0;
            for (int i = 0; i < nPoints.length; i++) {
                sum = sum + nPoints[i];
            }
            count[sum - n]++;
            return;
        }

        for (int i = 1; i <= 6; i++) {
            nPoints[t] = i;
            backtrack(t + 1, n, count, nPoints);
        }
    }
}
