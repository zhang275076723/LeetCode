package com.zhang.java;

/**
 * @Date 2022/5/28 14:58
 * @Author zsy
 * @Description 背包问题 华为面试题 动态规划类比Problem198、Problem213、Problem256、Problem265、Problem279、Problem322、Problem338、Problem343、Problem377、Problem416、Problem474、Problem494、Problem518、Problem746、Problem983、Problem1340、Problem1388、Problem1444、Problem1473、Offer14、Offer14_2、Offer60、CircleBackToOrigin
 */
public class Knapsack {
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
        //
        System.out.println(knapsack.knapsack3(weights, values, bagWeight));
        System.out.println(knapsack.knapsack4(weights, values, bagWeight));
        System.out.println(knapsack.knapsack5(weights, bagWeight));
        System.out.println(knapsack.knapsack6(weights, bagWeight));
        System.out.println(knapsack.knapsack7(weights, bagWeight));
//        System.out.println(knapsack.knapsack5(weights, values, bagWeight));
//        System.out.println(knapsack.knapsack6(weights, values, bagWeight));

//        weights = new int[]{3, 4, 5};
//        values = new int[]{4, 5, 6};
//        bagWeight = 10;
//        //完全背包
//        //13
//        System.out.println(knapsack.knapsack4(weights, values, bagWeight));
//
//        weights = new int[]{2, 5, 4, 2, 3};
//        values = new int[]{6, 3, 5, 4, 6};
//        int[] counts = {2, 2, 5, 5, 4};
//        bagWeight = 10;
//        //多重背包
//        //24
//        System.out.println(knapsack.knapsack5(weights, values, counts, bagWeight));
    }

    /**
     * 01背包 容量正好为j的最大价值 (华为面试题)
     * dp[i][j]：前i件物品容量为j的最大价值
     * dp[i][j] = dp[i-1][j]                                            (weight[i-1] > j)
     * dp[i][j] = max(dp[i-1][j], dp[i-1][j-weight[i-1]] + values[i-1]) (weight[i-1] <= j)
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
     * dp[i][j] = dp[i-1][j]                                            (weight[i-1] > j)
     * dp[i][j] = min(dp[i-1][j], dp[i-1][j-weight[i-1]] + values[i-1]) (weight[i-1] <= j)
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
     * dp[i][j] = dp[i-1][j]                                            (weight[i-1] > j)
     * dp[i][j] = max(dp[i-1][j], dp[i-1][j-weight[i-1]] + values[i-1]) (weight[i-1] <= j)
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
     * 01背包 容量最少为j的最小价值(内层循环从大往小遍历)
     * dp[i][j]：前i件物品容量最少为j的最小价值
     * dp[i][j] = min(dp[i-1][j], dp[i-1][0] + values[i-1])             (weight[i-1] > j)
     * dp[i][j] = min(dp[i-1][j], dp[i-1][j-weight[i-1]] + values[i-1]) (weight[i-1] <= j)
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
            //注意：容量最少为j的最小价值的内层循环是从大往小遍历
            for (int j = bagWeight; j >= 0; j--) {
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
     * dp[i][j] = dp[i-1][j]                          (weight[i-1] > j)
     * dp[i][j] = dp[i-1][j] + dp[i-1][j-weight[i-1]] (weight[i-1] <= j)
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
     * dp[i][j] = dp[i-1][j]                          (weight[i-1] > j)
     * dp[i][j] = dp[i-1][j] + dp[i-1][j-weight[i-1]] (weight[i-1] <= j)
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
     * 01背包 容量最少为j的方案数(内层循环从大往小遍历)
     * dp[i][j]：前i件物品容量最少为j的方案数
     * dp[i][j] = dp[i-1][j] + dp[i-1][0]             (weight[i-1] > j)
     * dp[i][j] = dp[i-1][j] + dp[i-1][j-weight[i-1]] (weight[i-1] <= j)
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
            //注意：容量最少为j的方案数的内层循环是从大往小遍历
            for (int j = bagWeight; j >= 0; j--) {
                if (weights[i - 1] > j) {
                    dp[i][j] = dp[i - 1][j] + dp[i - 1][0];
                } else {
                    dp[i][j] = dp[i - 1][j] + dp[i - 1][j - weights[i - 1]];
                }
            }
        }

        return dp[weights.length][bagWeight];
    }

//    /**
//     * 完全背包 容量正好为j的最大价值
//     * dp[i][j]：前i件物品在容量为j的情况下的最大价值
//     * dp[i][j] = dp[i-1][j]                                                (weight[i-1] > j)
//     * dp[i][j] = max(dp[i-1][j], dp[i][j-weight[i-1]] + values[i-1])       (weight[i-1] <= j)
//     * 时间复杂度O(mn)，空间复杂度O(mn)，可以使用滚动数组优化空间复杂度为O(n) (m=weights.length, n=bagWeight)
//     *
//     * @param weights
//     * @param values
//     * @param bagWeight
//     * @return
//     */
//    public int knapsack4(int[] weights, int[] values, int bagWeight) {
//        int[][] dp = new int[weights.length + 1][bagWeight + 1];
//
//        for (int i = 1; i <= weights.length; i++) {
//            for (int j = 1; j <= bagWeight; j++) {
//                if (weights[i - 1] > j) {
//                    dp[i][j] = dp[i - 1][j];
//                } else {
//                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - weights[i - 1]] + values[i - 1]);
//                }
//            }
//        }
//
//        return dp[weights.length][bagWeight];
//    }
//
//    /**
//     * 多重背包 容量正好为j的最大价值
//     * 将每种物品的个数看成不同的物品，转换成01背包
//     * 使用二进制拆分，如果第i件物品有13个，13拆分为1+2+4+6，转换为4种不同的物品，变成01背包
//     * dp[i][j]：前i件物品在容量为j的情况下的最大价值
//     * dp[i][j] = dp[i-1][j]                                                             (listWeights.get(i-1) > j)
//     * dp[i][j] = max(dp[i-1][j], dp[i-1][j-listWeights.get(i-1)] + listValues.get(i-1)) (listWeights.get(i-1) <= j)
//     * 时间复杂度O(mn)，空间复杂度O(mn) (m=listWeights.size(), n=bagWeight)
//     *
//     * @param weights
//     * @param values
//     * @param count
//     * @param bagWeight
//     * @return
//     */
//    public int knapsack5(int[] weights, int[] values, int[] count, int bagWeight) {
//        List<Integer> listWeights = new ArrayList<>();
//        List<Integer> listValues = new ArrayList<>();
//
//        //二进制拆分，转换为01背包
//        for (int i = 0; i < count.length; i++) {
//            for (int j = 1; j <= count[i]; j = j << 1) {
//                listWeights.add(weights[i] * j);
//                listValues.add(values[i] * j);
//                count[i] = count[i] - j;
//            }
//            if (count[i] > 0) {
//                listWeights.add(weights[i] * count[i]);
//                listValues.add(values[i] * count[i]);
//            }
//        }
//
//        int[][] dp = new int[listWeights.size() + 1][bagWeight + 1];
//
//        for (int i = 1; i <= listWeights.size(); i++) {
//            for (int j = 1; j <= bagWeight; j++) {
//                if (listWeights.get(i - 1) > j) {
//                    dp[i][j] = dp[i - 1][j];
//                } else {
//                    dp[i][j] = Math.max(dp[i - 1][j],
//                            dp[i - 1][j - listWeights.get(i - 1)] + listValues.get(i - 1));
//                }
//            }
//        }
//
//        return dp[listWeights.size()][bagWeight];
//    }
}
