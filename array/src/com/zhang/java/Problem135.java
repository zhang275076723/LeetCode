package com.zhang.java;

/**
 * @Date 2023/3/13 10:28
 * @Author zsy
 * @Description 分发糖果 字节面试题 两次遍历类比Problem32、Problem152、Problem581 数组中的动态规划类比Problem53、Problem152、Problem238、Problem724、Problem768、Problem769、Problem845、Problem1749、Offer42、Offer66、FindLeftBiggerRightLessIndex
 * n 个孩子站成一排。给你一个整数数组 ratings 表示每个孩子的评分。
 * 你需要按照以下要求，给这些孩子分发糖果：
 * 每个孩子至少分配到 1 个糖果。
 * 相邻两个孩子评分更高的孩子会获得更多的糖果。
 * 请你给每个孩子分发糖果，计算并返回需要准备的 最少糖果数目 。
 * <p>
 * 输入：ratings = [1,0,2]
 * 输出：5
 * 解释：你可以分别给第一个、第二个、第三个孩子分发 2、1、2 颗糖果。
 * <p>
 * 输入：ratings = [1,2,2]
 * 输出：4
 * 解释：你可以分别给第一个、第二个、第三个孩子分发 1、2、1 颗糖果。
 * 第三个孩子只得到 1 颗糖果，这满足题面中的两个条件。
 * <p>
 * n == ratings.length
 * 1 <= n <= 2 * 10^4
 * 0 <= ratings[i] <= 2 * 10^4
 */
public class Problem135 {
    public static void main(String[] args) {
        Problem135 problem135 = new Problem135();
        int[] ratings = {1, 3, 5, 3, 2, 1};
        System.out.println(problem135.candy(ratings));
    }

    /**
     * 动态规划，两次遍历
     * dp1[i]：从左往右遍历过程中，当前孩子i分发糖果的最少个数
     * dp2[i]：从右往左遍历过程中，当前孩子i分发糖果的最少个数
     * dp1[i] = dp1[i-1]+1 (ratings[i]>ratings[i-1])
     * dp1[i] = 1          (ratings[i]<=ratings[i-1])
     * dp2[i] = dp2[i+1]+1 (ratings[i]>ratings[i+1])
     * dp2[i] = 1          (ratings[i]<=ratings[i+1])
     * 最少分发糖果的总数量=sum(max(dp1[i],dp2[i]))
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param ratings
     * @return
     */
    public int candy(int[] ratings) {
        int[] dp1 = new int[ratings.length];
        int[] dp2 = new int[ratings.length];
        //dp1和dp2初始化，从左往右遍历时，第一个孩子分一个糖果，从右往左遍历时，最后一个孩子分一个糖果
        dp1[0] = 1;
        dp2[ratings.length - 1] = 1;

        //从左往右遍历
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i - 1]) {
                dp1[i] = dp1[i - 1] + 1;
            } else {
                dp1[i] = 1;
            }
        }

        //从右往左遍历
        for (int i = ratings.length - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                dp2[i] = dp2[i + 1] + 1;
            } else {
                dp2[i] = 1;
            }
        }

        //最少分发糖果的总数量
        int count = 0;

        for (int i = 0; i < ratings.length; i++) {
            count = count + Math.max(dp1[i], dp2[i]);
        }

        return count;
    }
}
