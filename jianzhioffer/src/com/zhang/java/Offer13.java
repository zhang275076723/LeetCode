package com.zhang.java;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2022/3/16 16:06
 * @Author zsy
 * @Description 机器人的运动范围 类比Problem62
 * 地上有一个m行n列的方格，从坐标 [0,0] 到坐标 [m-1,n-1] 。
 * 一个机器人从坐标 [0, 0] 的格子开始移动，它每次可以向左、右、上、下移动一格（不能移动到方格外），
 * 也不能进入行坐标和列坐标的数位之和大于k的格子。
 * 例如，当k为18时，机器人能够进入方格 [35, 37] ，因为3+5+3+7=18。
 * 但它不能进入方格 [35, 38]，因为3+5+3+8=19。请问该机器人能够到达多少个格子？
 * <p>
 * 输入：m = 2, n = 3, k = 1
 * 输出：3
 * <p>
 * 输入：m = 3, n = 1, k = 0
 * 输出：1
 * <p>
 * 1 <= n,m <= 100
 * 0 <= k <= 20
 */
public class Offer13 {
    public static void main(String[] args) {
        Offer13 offer13 = new Offer13();
        System.out.println(offer13.movingCount(16, 8, 4));
        System.out.println(offer13.movingCount2(16, 8, 4));
    }


    //错误的，机器人只能一步一步走，中间不能跳跃，有可能(i,j)能达到，但(i-1,j)和(i,j-1)无法到达，所以(i,j)无法到达
//    public int movingCount(int m, int n, int k) {
//        int count = 0;
//
//        for (int i = 0; i < m; i++) {
//            for (int j = 0; j < n; j++) {
//                if (getNumSum(i, j) <= k) {
//                    count++;
//                }
//            }
//        }
//
//        return count;
//    }

    /**
     * dfs
     * 时间复杂度O(mn)，空间复杂的O(mn)
     *
     * @param m
     * @param n
     * @param k
     * @return
     */
    public int movingCount(int m, int n, int k) {
        if (k == 0) {
            return 1;
        }

        boolean[][] visited = new boolean[m][n];

        return dfs(m, n, k, 0, 0, visited);
    }

    /**
     * bfs
     * 时间复杂度O(mn)，空间复杂的O(mn)
     *
     * @param m
     * @param n
     * @param k
     * @return
     */
    public int movingCount2(int m, int n, int k) {
        if (k == 0) {
            return 1;
        }

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0});
        boolean[][] visited = new boolean[m][n];
        visited[0][0] = true;
        int count = 1;

        while (!queue.isEmpty()) {
            int[] xy = queue.poll();
            int x = xy[0];
            int y = xy[1];

            //因为从(0,0)开始，所以只需要考虑向下和向右的情况
            if (x + 1 < m && y < n && !visited[x + 1][y] && getNumSum(x + 1, y) <= k) {
                queue.add(new int[]{x + 1, y});
                visited[x + 1][y] = true;
                count++;
            }
            if (x < m && y + 1 < n && !visited[x][y + 1] && getNumSum(x, y + 1) <= k) {
                queue.add(new int[]{x, y + 1});
                visited[x][y + 1] = true;
                count++;
            }
        }

        return count;
    }

    /**
     * @param m       方格行数
     * @param n       方格列数
     * @param k       行列坐标位数之和约束
     * @param i       机器人当前所在行
     * @param j       机器人当前所在列
     * @param visited 方格访问数组
     * @return
     */
    public int dfs(int m, int n, int k, int i, int j, boolean[][] visited) {
        if (i < 0 || i >= m || j < 0 || j >= n ||
                visited[i][j] || getNumSum(i, j) > k) {
            return 0;
        }

        visited[i][j] = true;

        //因为从(0,0)开始，所以只需要考虑向下和向右的情况
        return 1 + dfs(m, n, k, i + 1, j, visited) + dfs(m, n, k, i, j + 1, visited);
    }

    /**
     * 计算两个数的每位之和
     *
     * @param m
     * @param n
     * @return
     */
    public int getNumSum(int m, int n) {
        int sum = 0;

        while (m != 0) {
            sum = sum + m % 10;
            m = m / 10;
        }

        while (n != 0) {
            sum = sum + n % 10;
            n = n / 10;
        }

        return sum;
    }
}
