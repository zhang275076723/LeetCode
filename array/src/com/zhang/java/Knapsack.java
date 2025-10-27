package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/5/28 14:58
 * @Author zsy
 * @Description 背包问题 华为面试题 动态规划类比Problem198、Problem213、Problem256、Problem265、Problem279、Problem322、Problem338、Problem343、Problem377、Problem416、Problem474、Problem494、Problem518、Problem746、Problem983、Problem1340、Problem1388、Problem1444、Problem1473、Offer14、Offer14_2、Offer60、CircleBackToOrigin
 */
public class Knapsack {
    //最大值为int最大值除以2，避免相加在int范围内溢出
    private final int INF = Integer.MAX_VALUE / 2;

    public static void main(String[] args) {
        Knapsack knapsack = new Knapsack();

        int[] weights = {5, 4, 7, 2, 6};
        int[] values = {12, 3, 10, 3, 6};
        int bagWeight = 12;

        //01背包
        //22
        System.out.println(knapsack.knapsack(weights, values, bagWeight));
        //12
        System.out.println(knapsack.knapsack2(weights, values, bagWeight));
        //22
        System.out.println(knapsack.knapsack3(weights, values, bagWeight));
        //12
        System.out.println(knapsack.knapsack4(weights, values, bagWeight));
        //2
        System.out.println(knapsack.knapsack5(weights, bagWeight));
        //15
        System.out.println(knapsack.knapsack6(weights, bagWeight));
        //17
        System.out.println(knapsack.knapsack7(weights, bagWeight));

        weights = new int[]{3, 4, 5};
        values = new int[]{4, 5, 6};
        bagWeight = 10;
        //完全背包
        //13
        System.out.println(knapsack.knapsack8(weights, values, bagWeight));

        weights = new int[]{2, 5, 4, 2, 3};
        values = new int[]{6, 3, 5, 4, 6};
        int[] counts = {2, 2, 5, 5, 4};
        bagWeight = 10;
        //多重背包
        //24
        System.out.println(knapsack.knapsack9(weights, values, counts, bagWeight));
        //24
        System.out.println(knapsack.knapsack10(weights, values, counts, bagWeight));
    }

    /**
     * 01背包 容量正好为j的最大价值 (华为面试题)
     * dp[i][j]：前i件物品容量为j的最大价值
     * dp[i][j] = dp[i-1][j]                                             (weights[i-1] > j)
     * dp[i][j] = max(dp[i-1][j], dp[i-1][j-weights[i-1]] + values[i-1]) (weights[i-1] <= j)
     * 时间复杂度O(mn)，空间复杂度O(mn) (m=weights.length, n=bagWeight) (使用滚动数组优化空间复杂度为O(n))
     *
     * @param weights
     * @param values
     * @param bagWeight
     * @return
     */
    public int knapsack(int[] weights, int[] values, int bagWeight) {
        int[][] dp = new int[weights.length + 1][bagWeight + 1];

        //dp初始化，前0件物品容量为0的最大价值为0
        dp[0][0] = 0;

        //dp初始化，前0件物品不存在容量为1-bagWeight的最大价值
        for (int j = 1; j <= bagWeight; j++) {
            dp[0][j] = -INF;
        }

        for (int i = 1; i <= weights.length; i++) {
            for (int j = 0; j <= bagWeight; j++) {
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
     * 01背包 容量正好为j的最小价值
     * dp[i][j]：前i件物品容量为j的最小价值
     * dp[i][j] = dp[i-1][j]                                             (weights[i-1] > j)
     * dp[i][j] = min(dp[i-1][j], dp[i-1][j-weights[i-1]] + values[i-1]) (weights[i-1] <= j)
     * 时间复杂度O(mn)，空间复杂度O(mn) (m=weights.length, n=bagWeight) (使用滚动数组优化空间复杂度为O(n))
     *
     * @param weights
     * @param values
     * @param bagWeight
     * @return
     */
    public int knapsack2(int[] weights, int[] values, int bagWeight) {
        int[][] dp = new int[weights.length + 1][bagWeight + 1];

        //dp初始化，前0件物品容量为0的最小价值为0
        dp[0][0] = 0;

        //dp初始化，前0件物品不存在容量为1-bagWeight的最小价值
        for (int j = 1; j <= bagWeight; j++) {
            dp[0][j] = INF;
        }

        for (int i = 1; i <= weights.length; i++) {
            for (int j = 0; j <= bagWeight; j++) {
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
     * 01背包 容量最多为j的最大价值
     * dp[i][j]：前i件物品容量最多为j的最大价值
     * dp[i][j] = dp[i-1][j]                                             (weights[i-1] > j)
     * dp[i][j] = max(dp[i-1][j], dp[i-1][j-weights[i-1]] + values[i-1]) (weights[i-1] <= j)
     * 时间复杂度O(mn)，空间复杂度O(mn) (m=weights.length, n=bagWeight) (使用滚动数组优化空间复杂度为O(n))
     *
     * @param weights
     * @param values
     * @param bagWeight
     * @return
     */
    public int knapsack3(int[] weights, int[] values, int bagWeight) {
        int[][] dp = new int[weights.length + 1][bagWeight + 1];

        //dp初始化，前0件物品容量最多为0-bagWeight的最大价值为0
        for (int j = 0; j <= bagWeight; j++) {
            dp[0][j] = 0;
        }

        for (int i = 1; i <= weights.length; i++) {
            for (int j = 0; j <= bagWeight; j++) {
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
     * 01背包 容量最少为j的最小价值
     * dp[i][j]：前i件物品容量最少为j的最小价值
     * dp[i][j] = min(dp[i-1][j], dp[i-1][0] + values[i-1])              (weights[i-1] > j)
     * dp[i][j] = min(dp[i-1][j], dp[i-1][j-weights[i-1]] + values[i-1]) (weights[i-1] <= j)
     * 时间复杂度O(mn)，空间复杂度O(mn) (m=weights.length, n=bagWeight) (使用滚动数组优化空间复杂度为O(n))
     *
     * @param weights
     * @param values
     * @param bagWeight
     * @return
     */
    public int knapsack4(int[] weights, int[] values, int bagWeight) {
        int[][] dp = new int[weights.length + 1][bagWeight + 1];

        //dp初始化，前0件物品容量最少为0的最小价值为0
        dp[0][0] = 0;

        //dp初始化，前0件物品不存在容量最少为1-bagWeight的最小价值
        for (int j = 1; j <= bagWeight; j++) {
            dp[0][j] = INF;
        }

        for (int i = 1; i <= weights.length; i++) {
            for (int j = 0; j <= bagWeight; j++) {
                //注意：weights[i-1]>j时，dp[i][j]还需要考虑到dp[i-1][0]，
                //即前i-1件物品容量最少为0的最小价值加上第i件物品的价值values[i-1]构成的最小价值
                if (weights[i - 1] > j) {
                    dp[i][j] = Math.min(dp[i - 1][j], dp[i - 1][0] + values[i - 1]);
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j], dp[i - 1][j - weights[i - 1]] + values[i - 1]);
                }
            }
        }

        return dp[weights.length][bagWeight];
    }

    /**
     * 01背包 容量正好为j的方案数
     * dp[i][j]：前i件物品容量为j的方案数
     * dp[i][j] = dp[i-1][j]                           (weights[i-1] > j)
     * dp[i][j] = dp[i-1][j] + dp[i-1][j-weights[i-1]] (weights[i-1] <= j)
     * 时间复杂度O(mn)，空间复杂度O(mn) (m=weights.length, n=bagWeight) (使用滚动数组优化空间复杂度为O(n))
     *
     * @param weights
     * @param bagWeight
     * @return
     */
    public int knapsack5(int[] weights, int bagWeight) {
        int[][] dp = new int[weights.length + 1][bagWeight + 1];

        //dp初始化，前0件物品容量为0的方案数为1
        dp[0][0] = 1;

        //dp初始化，前0件物品容量为1-bagWeight的方案数为0
        for (int j = 1; j <= bagWeight; j++) {
            dp[0][j] = 0;
        }

        for (int i = 1; i <= weights.length; i++) {
            for (int j = 0; j <= bagWeight; j++) {
                if (weights[i - 1] > j) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j] + dp[i - 1][j - weights[i - 1]];
                }
            }
        }

        return dp[weights.length][bagWeight];
    }

    /**
     * 01背包 容量最多为j的方案数
     * dp[i][j]：前i件物品容量最多为j的方案数
     * dp[i][j] = dp[i-1][j]                           (weights[i-1] > j)
     * dp[i][j] = dp[i-1][j] + dp[i-1][j-weights[i-1]] (weights[i-1] <= j)
     * 时间复杂度O(mn)，空间复杂度O(mn) (m=weights.length, n=bagWeight) (使用滚动数组优化空间复杂度为O(n))
     *
     * @param weights
     * @param bagWeight
     * @return
     */
    public int knapsack6(int[] weights, int bagWeight) {
        int[][] dp = new int[weights.length + 1][bagWeight + 1];

        //dp初始化，前0件物品容量最多为0-bagWeight的方案数为1
        for (int j = 1; j <= bagWeight; j++) {
            dp[0][j] = 1;
        }

        for (int i = 1; i <= weights.length; i++) {
            for (int j = 0; j <= bagWeight; j++) {
                if (weights[i - 1] > j) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j] + dp[i - 1][j - weights[i - 1]];
                }
            }
        }

        return dp[weights.length][bagWeight];
    }

    /**
     * 01背包 容量最少为j的方案数
     * dp[i][j]：前i件物品容量最少为j的方案数
     * dp[i][j] = dp[i-1][j] + dp[i-1][0]              (weights[i-1] > j)
     * dp[i][j] = dp[i-1][j] + dp[i-1][j-weights[i-1]] (weights[i-1] <= j)
     * 时间复杂度O(mn)，空间复杂度O(mn) (m=weights.length, n=bagWeight) (使用滚动数组优化空间复杂度为O(n))
     *
     * @param weights
     * @param bagWeight
     * @return
     */
    public int knapsack7(int[] weights, int bagWeight) {
        int[][] dp = new int[weights.length + 1][bagWeight + 1];

        //dp初始化，前0件物品容量最少为0的方案数为1
        dp[0][0] = 1;

        //dp初始化，前0件物品容量最少为1-bagWeight的方案数为0
        for (int j = 1; j <= bagWeight; j++) {
            dp[0][j] = 0;
        }

        for (int i = 1; i <= weights.length; i++) {
            for (int j = 0; j <= bagWeight; j++) {
                //注意：weights[i-1]>j时，dp[i][j]还需要考虑到dp[i-1][0]，
                //即前i-1件物品容量最少为0的方案数加上第i件物品构成的方案数
                if (weights[i - 1] > j) {
                    dp[i][j] = dp[i - 1][j] + dp[i - 1][0];
                } else {
                    dp[i][j] = dp[i - 1][j] + dp[i - 1][j - weights[i - 1]];
                }
            }
        }

        return dp[weights.length][bagWeight];
    }

    /**
     * 完全背包 容量正好为j的最大价值
     * dp[i][j]：前i件物品容量为j的最大价值
     * dp[i][j] = dp[i-1][j]                                           (weights[i-1] > j)
     * dp[i][j] = max(dp[i-1][j], dp[i][j-weights[i-1]] + values[i-1]) (weights[i-1] <= j)
     * 时间复杂度O(mn)，空间复杂度O(mn) (m=weights.length, n=bagWeight) (使用滚动数组优化空间复杂度为O(n))
     *
     * @param weights
     * @param values
     * @param bagWeight
     * @return
     */
    public int knapsack8(int[] weights, int[] values, int bagWeight) {
        int[][] dp = new int[weights.length + 1][bagWeight + 1];

        //dp初始化，前0件物品容量为0的最大价值为0
        dp[0][0] = 0;

        //dp初始化，前0件物品不存在容量为1-bagWeight的最大价值
        for (int j = 1; j <= bagWeight; j++) {
            dp[0][j] = -INF;
        }

        for (int i = 1; i <= weights.length; i++) {
            for (int j = 0; j <= bagWeight; j++) {
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
     * 多重背包(朴素解法) 容量正好为j的最大价值
     * dp[i][j]：前i件物品在容量为j的情况下的最大价值
     * dp[i][j] = max(dp[i-1][j], dp[i-1][j-weights[i-1]*k] + values[i-1]*k) (weights[i-1]*k <= j) (0 <= k <= count[i-1])
     * 时间复杂度O(mnl)，空间复杂度O(mn) (m=weights.length, n=bagWeight，l=max(count[i])) (使用滚动数组优化空间复杂度为O(n))
     *
     * @param weights
     * @param values
     * @param count
     * @param bagWeight
     * @return
     */
    public int knapsack9(int[] weights, int[] values, int[] count, int bagWeight) {
        int[][] dp = new int[weights.length + 1][bagWeight + 1];

        //dp初始化，前0件物品容量为0的最大价值为0
        dp[0][0] = 0;

        //dp初始化，前0件物品不存在容量为1-bagWeight的最大价值
        for (int j = 1; j <= bagWeight; j++) {
            dp[0][j] = -INF;
        }

        for (int i = 1; i <= weights.length; i++) {
            for (int j = 0; j <= bagWeight; j++) {
                //dp初始化，前i件物品容量为j的最大价值为前i-1件物品容量为j的最大价值
                dp[i][j] = dp[i - 1][j];

                for (int k = 0; k <= count[i - 1]; k++) {
                    if (weights[i - 1] * k <= j) {
                        dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - weights[i - 1] * k] + values[i - 1] * k);
                    }
                }
            }
        }

        return dp[weights.length][bagWeight];
    }

    /**
     * 多重背包(二进制拆分) 容量正好为j的最大价值
     * 物品count[i]拆分为1+2+4+...+2^(k-1)+count[i]-2^k+1作为不同的物品，拆分后的物品可以表示1-count[i]中任意的值，即转换为01背包，
     * 如果第i件物品有13个，13拆分为1+2+4+6，转换为4种不同的物品
     * dp[i][j]：前i件物品在容量为j的情况下的最大价值
     * dp[i][j] = dp[i-1][j]                                                             (weightsList.get(i-1) > j)
     * dp[i][j] = max(dp[i-1][j], dp[i-1][j-weightsList.get(i-1)] + valuesList.get(i-1)) (weightsList.get(i-1) <= j)
     * 时间复杂度O(mn)，空间复杂度O(mn) (m=weightsList.size(), n=bagWeight) (使用滚动数组优化空间复杂度为O(n))
     *
     * @param weights
     * @param values
     * @param count
     * @param bagWeight
     * @return
     */
    public int knapsack10(int[] weights, int[] values, int[] count, int bagWeight) {
        //转换为01背包后的物品重量集合
        List<Integer> weightsList = new ArrayList<>();
        //转换为01背包后的物品价值集合
        List<Integer> valuesList = new ArrayList<>();

        //二进制拆分，转换为01背包
        for (int i = 0; i < count.length; i++) {
            int temp = 1;

            //物品count[i]拆分为1+2+4+...+2^(k-1)+count[i]-2^k+1作为不同的物品，
            //拆分后的物品可以表示1-count[i]中任意的值，即转换为01背包
            while (count[i] > temp) {
                weightsList.add(weights[i] * temp);
                valuesList.add(values[i] * temp);
                count[i] = count[i] - temp;
                temp = temp << 1;
            }

            //count[i]都拆分为2^k之后还有剩余部分，剩余部分也作为一个新的物品
            if (count[i] > 0) {
                weightsList.add(weights[i] * count[i]);
                valuesList.add(values[i] * count[i]);
            }
        }

        int[][] dp = new int[weightsList.size() + 1][bagWeight + 1];

        //dp初始化，前0件物品容量为0的最大价值为0
        dp[0][0] = 0;

        //dp初始化，前0件物品不存在容量为1-bagWeight的最大价值
        for (int j = 1; j <= bagWeight; j++) {
            dp[0][j] = -INF;
        }

        for (int i = 1; i <= weightsList.size(); i++) {
            for (int j = 0; j <= bagWeight; j++) {
                if (weightsList.get(i - 1) > j) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weightsList.get(i - 1)] + valuesList.get(i - 1));
                }
            }
        }

        return dp[weightsList.size()][bagWeight];
    }
}
