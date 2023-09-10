package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/5/28 14:58
 * @Author zsy
 * @Description 01背包、完全背包、多重背包 华为面试题 动态规划类比Problem198、Problem213、Problem279、Problem322、Problem343、Problem377、Problem416、Problem474、Problem494、Problem518、Problem746、Problem983、Problem1340、Problem1388、Problem1444、Offer14、Offer14_2、CircleBackToOrigin
 */
public class Knapsack {
    public static void main(String[] args) {
        Knapsack knapsack = new Knapsack();

        int[] weights = {5, 4, 7, 2, 6};
        int[] values = {12, 3, 10, 3, 6};
        int bagWeight = 12;

        //01背包
        System.out.println(knapsack.knapsack(weights, values, bagWeight));
        System.out.println(knapsack.knapsack2(weights, values, bagWeight));
        System.out.println(knapsack.knapsack3(weights, values, bagWeight));

        weights = new int[]{3, 4, 5};
        values = new int[]{4, 5, 6};
        bagWeight = 10;
        //完全背包
        System.out.println(knapsack.knapsack4(weights, values, bagWeight));

        weights = new int[]{2, 5, 4, 2, 3};
        values = new int[]{6, 3, 5, 4, 6};
        int[] counts = {2, 2, 5, 5, 4};
        bagWeight = 10;
        //多重背包
        System.out.println(knapsack.knapsack5(weights, values, counts, bagWeight));
    }

    /**
     * 01背包最大价值，不要求背包正好装满
     * dp[i][j]：前i件物品在容量为j的最大价值
     * dp[i][j] = dp[i-1][j]                                            (weight[i-1] > j)
     * dp[i][j] = max(dp[i-1][j], dp[i-1][j-weight[i-1]] + values[i-1]) (weight[i-1] <= j)
     * 时间复杂度O(mn)，空间复杂度O(mn) (m=weights.length, n=bagWeight)
     * (使用滚动数组优化空间复杂度为O(n)，注意是从右往左遍历)
     *
     * @param weights
     * @param values
     * @param bagWeight
     * @return
     */
    public int knapsack(int[] weights, int[] values, int bagWeight) {
        int[][] dp = new int[weights.length + 1][bagWeight + 1];

        for (int i = 1; i <= weights.length; i++) {
            for (int j = 1; j <= bagWeight; j++) {
                if (weights[i - 1] > j) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weights[i - 1]] + values[i - 1]);
                }
            }
        }

        return dp[weights.length][bagWeight];
    }

    /**
     * 01背包正好装满的最大价值 (华为面试题)
     * dp[i][j]：前i件物品在容量为j正好装满的最大价值
     * dp[i][0]初始化为0，表示正好装满，dp[0][j]初始化为Integer.MIN_VALUE，表示无法装满
     * dp[i][j] = dp[i-1][j]                                            (weight[i-1] > j)
     * dp[i][j] = max(dp[i-1][j], dp[i-1][j-weight[i-1]] + values[i-1]) (weight[i-1] <= j)
     * 时间复杂度O(mn)，空间复杂度O(mn) (m=weights.length, n=bagWeight)
     * (使用滚动数组优化空间复杂度为O(n)，注意是从右往左遍历)
     *
     * @param weights
     * @param values
     * @param bagWeight
     * @return
     */
    public int knapsack2(int[] weights, int[] values, int bagWeight) {
        int[][] dp = new int[weights.length + 1][bagWeight + 1];

        for (int j = 1; j <= bagWeight; j++) {
            //初始化Integer.MIN_VALUE，表示无法装满
            dp[0][j] = Integer.MIN_VALUE;
        }

        for (int i = 1; i <= weights.length; i++) {
            for (int j = 1; j <= bagWeight; j++) {
                if (weights[i - 1] > j) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weights[i - 1]] + values[i - 1]);
                }
            }
        }

        return dp[weights.length][bagWeight];
    }

    /**
     * 01背包正好装满的最小价值
     * dp[i][j]：前i件物品在容量为j正好装满的最小价值
     * dp[i][0]初始化为0，表示正好装满，dp[0][j]初始化为一个较大的数，表示无法装满，
     * 不能赋值为Integer.MAX_VALUE，避免int相加溢出结果为负数
     * dp[i][j] = dp[i-1][j]                                            (weight[i-1] > j)
     * dp[i][j] = min(dp[i-1][j], dp[i-1][j-weight[i-1]] + values[i-1]) (weight[i-1] <= j)
     * 时间复杂度O(mn)，空间复杂度O(mn)，可以使用滚动数组优化空间复杂度为O(n) (m=weights.length, n=bagWeight)
     *
     * @param weights
     * @param values
     * @param bagWeight
     * @return
     */
    public int knapsack3(int[] weights, int[] values, int bagWeight) {
        int[][] dp = new int[weights.length + 1][bagWeight + 1];

        for (int j = 1; j <= bagWeight; j++) {
            //初始化较大的整数，表示无法装满，不能赋值为Integer.MAX_VALUE，避免int相加溢出结果为负数
            dp[0][j] = 10000;
        }

        for (int i = 1; i <= weights.length; i++) {
            //注意：最小价值是从后往前遍历，不能从前往后遍历
            for (int j = bagWeight; j >= 1; j--) {
                if (weights[i - 1] > j) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j], dp[i - 1][j - weights[i - 1]] + values[i - 1]);
                }
            }
        }

        return dp[weights.length][bagWeight];
    }

    /**
     * 完全背包最大价值，不要求背包正好装满
     * dp[i][j]：前i件物品在容量为j的情况下的最大价值
     * dp[i][j] = dp[i-1][j]                                                (weight[i-1] > j)
     * dp[i][j] = max(dp[i-1][j], dp[i][j-weight[i-1]] + values[i-1])       (weight[i-1] <= j)
     * 时间复杂度O(mn)，空间复杂度O(mn)，可以使用滚动数组优化空间复杂度为O(n) (m=weights.length, n=bagWeight)
     *
     * @param weights
     * @param values
     * @param bagWeight
     * @return
     */
    public int knapsack4(int[] weights, int[] values, int bagWeight) {
        int[][] dp = new int[weights.length + 1][bagWeight + 1];

        for (int i = 1; i <= weights.length; i++) {
            for (int j = 1; j <= bagWeight; j++) {
                if (weights[i - 1] > j) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - weights[i - 1]] + values[i - 1]);
                }
            }
        }

        return dp[weights.length][bagWeight];
    }

    /**
     * 多重背包最大价值，不要求背包正好装满
     * 将每种物品的个数看成不同的物品，转换成01背包
     * 使用二进制拆分，如果第i件物品有13个，13拆分为1+2+4+6，转换为4种不同的物品，变成01背包
     * dp[i][j]：前i件物品在容量为j的情况下的最大价值
     * dp[i][j] = dp[i-1][j]                                                             (listWeights.get(i-1) > j)
     * dp[i][j] = max(dp[i-1][j], dp[i-1][j-listWeights.get(i-1)] + listValues.get(i-1)) (listWeights.get(i-1) <= j)
     * 时间复杂度O(mn)，空间复杂度O(mn) (m=listWeights.size(), n=bagWeight)
     *
     * @param weights
     * @param values
     * @param count
     * @param bagWeight
     * @return
     */
    public int knapsack5(int[] weights, int[] values, int[] count, int bagWeight) {
        List<Integer> listWeights = new ArrayList<>();
        List<Integer> listValues = new ArrayList<>();

        //二进制拆分，转换为01背包
        for (int i = 0; i < count.length; i++) {
            for (int j = 1; j <= count[i]; j = j << 1) {
                listWeights.add(weights[i] * j);
                listValues.add(values[i] * j);
                count[i] = count[i] - j;
            }
            if (count[i] > 0) {
                listWeights.add(weights[i] * count[i]);
                listValues.add(values[i] * count[i]);
            }
        }

        int[][] dp = new int[listWeights.size() + 1][bagWeight + 1];

        for (int i = 1; i <= listWeights.size(); i++) {
            for (int j = 1; j <= bagWeight; j++) {
                if (listWeights.get(i - 1) > j) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j],
                            dp[i - 1][j - listWeights.get(i - 1)] + listValues.get(i - 1));
                }
            }
        }

        return dp[listWeights.size()][bagWeight];
    }
}
