package com.zhang.java;

import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * @Date 2022/4/7 12:14
 * @Author zsy
 * @Description n个骰子的点数 动态规划类比Problem198、Problem213、Problem279、Problem322、Problem343、Problem377、Problem416、Problem474、Problem494、Problem518、Problem746、Problem983、Problem1340、Problem1388、Problem1444、Offer14、Offer14_2、CircleBackToOrigin、Knapsack
 * 把n个骰子扔在地上，所有骰子朝上一面的点数之和为s。
 * 输入n，打印出s的所有可能的值出现的概率。
 * 你需要用一个浮点数数组返回答案，其中第 i 个元素代表这 n 个骰子所能掷出的点数集合中第 i 小的那个的概率。
 * <p>
 * 输入: 1
 * 输出: [0.16667,0.16667,0.16667,0.16667,0.16667,0.16667]
 * <p>
 * 输入: 2
 * 输出: [0.02778,0.05556,0.08333,0.11111,0.13889,0.16667,0.13889,0.11111,0.08333,0.05556,0.02778]
 * <p>
 * * 1 <= n <= 11
 */
public class Offer60 {
    public static void main(String[] args) {
        Offer60 offer60 = new Offer60();
        System.out.println(Arrays.toString(offer60.dicesProbability(2)));
        System.out.println(Arrays.toString(offer60.dicesProbability2(2)));
        System.out.println(Arrays.toString(offer60.dicesProbability3(2)));
    }

    /**
     * 回溯
     * 时间复杂度O(6^n)，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public double[] dicesProbability(int n) {
        //结果概率数组
        double[] result = new double[5 * n + 1];
        //掷n个骰子点数之和的情况个数
        int[] count = new int[5 * n + 1];
        //每次掷n个骰子掷出的点数
        int[] nPoints = new int[n];
        //掷n个骰子的情况总数
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
     * 动态规划
     * dp[i][j]：掷i个骰子点数之和为j的数量
     * dp[i][j] = sum(dp[i-1][j-k]) (1<=k<=6)
     * 时间复杂度O(n^2)，空间复杂度O(n^2)
     *
     * @param n
     * @return
     */
    public double[] dicesProbability2(int n) {
        //掷i个骰子点数之和为j的数量
        int[][] dp = new int[n + 1][6 * n + 1];

        //dp初始化
        for (int j = 1; j <= 6; j++) {
            dp[1][j] = 1;
        }

        for (int i = 2; i <= n; i++) {
            for (int j = i; j <= 6 * i; j++) {
                for (int k = 1; k <= 6; k++) {
                    //掷i-1个骰子能掷出点数之和为j-k，必须大于等于i-1
                    if (i - 1 <= j - k) {
                        dp[i][j] = dp[i][j] + dp[i - 1][j - k];
                    }
                }
            }
        }

        //掷n个骰子的总样本数量
        int count = (int) Math.pow(6, n);
        double[] result = new double[5 * n + 1];

        for (int i = 0; i < result.length; i++) {
            result[i] = (double) dp[n][n + i] / count;
        }

        return result;
    }

    /**
     * 动态规划优化，使用滚动数组 (一般动态规划的空间优化：1、滚动数组；2、原数组作为dp数组)
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public double[] dicesProbability3(int n) {
        //掷i个骰子点数之和为j的数量
        int[] dp = new int[6 * n + 1];

        for (int i = 1; i <= 6; i++) {
            dp[i] = 1;
        }

        for (int i = 2; i <= n; i++) {
            //使用滚动数组dp[j]依赖dp[j-k]，所以必须从后往前遍历
            for (int j = 6 * i; j >= i; j--) {
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

        //掷n个骰子的总样本数量
        int total = (int) Math.pow(6, n);
        double[] result = new double[5 * n + 1];

        for (int i = 0; i < result.length; i++) {
            result[i] = (double) dp[i + n] / total;
        }

        return result;
    }

    /**
     * @param t       掷第t+1个骰子
     * @param n       总骰子数
     * @param count   掷n个骰子点数之和的情况个数，count[0] = 2，表示掷n个骰子点数之和为0+n的情况有2种
     * @param nPoints 每个骰子所掷出的点数，nPoints[t] = i，表示第t个骰子的点数为i
     */
    public void backtrack(int t, int n, int[] count, int[] nPoints) {
        if (t == n) {
            //n个骰子所掷出的点数之和
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
