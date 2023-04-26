package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/4/26 09:47
 * @Author zsy
 * @Description 三角形最小路径和 字节面试题 微软面试题
 * 给定一个三角形 triangle ，找出自顶向下的最小路径和。
 * 每一步只能移动到下一行中相邻的结点上。
 * 相邻的结点 在这里指的是 下标 与 上一层结点下标 相同或者等于 上一层结点下标 + 1 的两个结点。
 * 也就是说，如果正位于当前行的下标 i ，那么下一步可以移动到下一行的下标 i 或 i + 1 。
 * <p>
 * 输入：triangle = [[2],[3,4],[6,5,7],[4,1,8,3]]
 * 输出：11
 * 解释：如下面简图所示：
 * <    2
 * <   3 4
 * <  6 5 7
 * < 4 1 8 3
 * 自顶向下的最小路径和为 11（即，2 + 3 + 5 + 1 = 11）。
 * <p>
 * 输入：triangle = [[-10]]
 * 输出：-10
 * <p>
 * 1 <= triangle.length <= 200
 * triangle[0].length == 1
 * triangle[i].length == triangle[i - 1].length + 1
 * -10^4 <= triangle[i][j] <= 10^4
 */
public class Problem120 {
    public static void main(String[] args) {
        Problem120 problem120 = new Problem120();
        List<List<Integer>> triangle = new ArrayList<>();
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        List<Integer> list3 = new ArrayList<>();
        List<Integer> list4 = new ArrayList<>();
        list1.add(2);
        list2.add(3);
        list2.add(4);
        list3.add(6);
        list3.add(5);
        list3.add(7);
        list4.add(4);
        list4.add(1);
        list4.add(8);
        list4.add(3);
        triangle.add(list1);
        triangle.add(list2);
        triangle.add(list3);
        triangle.add(list4);
        System.out.println(problem120.minimumTotal(triangle));
        System.out.println(problem120.minimumTotal2(triangle));
        System.out.println(problem120.minimumTotal3(triangle));
        System.out.println(problem120.minimumTotal4(triangle));
    }

    /**
     * 动态规划(从上往下)
     * dp[i][j]：从第0行第0列到第i行第j列的最小路径和
     * dp[i][j] = min(dp[i-1][j-1],dp[i-1][j]) + triangle.get(i).get(j)
     * 时间复杂度O(n^2)，空间复杂度O(n^2)
     *
     * @param triangle
     * @return
     */
    public int minimumTotal(List<List<Integer>> triangle) {
        int[][] dp = new int[triangle.size()][triangle.size()];
        //dp[0][0]初始化，从第0行第0列到第0行第0列的最小路径和
        dp[0][0] = triangle.get(0).get(0);

        for (int i = 1; i < triangle.size(); i++) {
            //当前第i行第0列只能由第i-1行第0列转换
            dp[i][0] = dp[i - 1][0] + triangle.get(i).get(0);

            //当前第i行非第0和第i列，可以由第i-1行第i-1列或者第i-1行第i列转换
            for (int j = 1; j < i; j++) {
                dp[i][j] = Math.min(dp[i - 1][j - 1], dp[i - 1][j]) + triangle.get(i).get(j);
            }

            //当前第i行第i列只能由第i-1行第i-1列转换
            dp[i][i] = dp[i - 1][i - 1] + triangle.get(i).get(i);
        }

        int result = dp[triangle.size() - 1][0];

        //最后一行中的最小值即为自顶向下的最小路径和
        for (int j = 0; j < triangle.size(); j++) {
            result = Math.min(result, dp[triangle.size() - 1][j]);
        }

        return result;
    }

    /**
     * 动态规划(从上往下)优化，使用滚动数组
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param triangle
     * @return
     */
    public int minimumTotal2(List<List<Integer>> triangle) {
        int[] dp = new int[triangle.size()];
        //dp[0][0]初始化，从第0行第0列到第0行第0列的最小路径和
        dp[0] = triangle.get(0).get(0);

        for (int i = 1; i < triangle.size(); i++) {
            //从后往前遍历，避免当前行dp影响上一行dp

            //当前第i行第i列只能由第i-1行第i-1列转换
            dp[i] = dp[i - 1] + triangle.get(i).get(i);

            //当前第i行非第0和第i列，可以由第i-1行第i-1列或者第i-1行第i列转换
            for (int j = i - 1; j > 0; j--) {
                dp[j] = Math.min(dp[j - 1], dp[j]) + triangle.get(i).get(j);
            }

            //当前第i行第0列只能由第i-1行第0列转换
            dp[0] = dp[0] + triangle.get(i).get(0);
        }

        int result = dp[0];

        //最后一行中的最小值即为自顶向下的最小路径和
        for (int i = 0; i < triangle.size(); i++) {
            result = Math.min(result, dp[i]);
        }

        return result;
    }

    /**
     * 动态规划(从下往上)
     * dp[i][j]：从最底层到第i行第j列的最小路径和
     * dp[i][j] = min(dp[i+1][j],dp[i+1][j+1]) + triangle.get(i).get(j)
     * 时间复杂度O(n^2)，空间复杂度O(n^2)
     *
     * @param triangle
     * @return
     */
    public int minimumTotal3(List<List<Integer>> triangle) {
        int[][] dp = new int[triangle.size()][triangle.size()];

        //最底层dp[triangle.size()-1][j]初始化
        for (int j = 0; j < triangle.size(); j++) {
            dp[triangle.size() - 1][j] = triangle.get(triangle.size() - 1).get(j);
        }

        for (int i = triangle.size() - 2; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                dp[i][j] = Math.min(dp[i + 1][j], dp[i + 1][j + 1]) + triangle.get(i).get(j);
            }
        }

        return dp[0][0];
    }

    /**
     * 动态规划(从下往上)优化，使用滚动数组
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param triangle
     * @return
     */
    public int minimumTotal4(List<List<Integer>> triangle) {
        int[] dp = new int[triangle.size()];

        //最底层dp[triangle.size()-1][j]初始化
        for (int i = 0; i < triangle.size(); i++) {
            dp[i] = triangle.get(triangle.size() - 1).get(i);
        }

        for (int i = triangle.size() - 2; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                dp[j] = Math.min(dp[j], dp[j + 1]) + triangle.get(i).get(j);
            }
        }

        return dp[0];
    }
}
