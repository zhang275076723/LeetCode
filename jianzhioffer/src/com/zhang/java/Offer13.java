package com.zhang.java;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2022/3/16 16:06
 * @Author zsy
 * @Description 机器人的运动范围 类比Problem62、Problem63、Problem64、Problem980
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

    /**
     * dfs
     * 注意：机器人只能一步一步走，中间不能跳跃，有可能(i,j)满足行坐标和列坐标的数位之和小于等于k，
     * 但(i,j)相邻的四个位置(i-1,j)、(i+1,j)、(i,j-1)、(i,j+1)却无法到达，所以(i,j)无法到达
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

        return dfs(0, 0, m, n, k, new boolean[m][n]);
    }

    /**
     * bfs
     * 注意：机器人只能一步一步走，中间不能跳跃，有可能(i,j)满足行坐标和列坐标的数位之和小于等于k，
     * 但(i,j)相邻的四个位置(i-1,j)、(i+1,j)、(i,j-1)、(i,j+1)却无法到达，所以(i,j)无法到达
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

        int count = 0;
        //队列中存放当前位置的行列索引下标
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[m][n];
        queue.offer(new int[]{0, 0});

        while (!queue.isEmpty()) {
            int[] arr = queue.poll();

            //当前节点超过矩阵范围，或者当前节点已被访问，或者当前节点行列坐标的数位之和大于k，则不合法，直接进行下次循环
            if (arr[0] < 0 || arr[0] >= m || arr[1] < 0 || arr[1] >= n || visited[arr[0]][arr[1]] ||
                    getNumSum(arr[0], arr[1]) > k) {
                continue;
            }

            visited[arr[0]][arr[1]] = true;
            count++;

            //因为从(0,0)开始往右下角走，所以只需要考虑向下和向右这两种情况
            queue.offer(new int[]{arr[0] + 1, arr[1]});
            queue.offer(new int[]{arr[0], arr[1] + 1});
        }

        return count;
    }

    /**
     * @param i       机器人当前所在行
     * @param j       机器人当前所在列
     * @param m       方格行数
     * @param n       方格列数
     * @param k       行列坐标位数之和约束
     * @param visited 方格访问数组
     * @return
     */
    private int dfs(int i, int j, int m, int n, int k, boolean[][] visited) {
        //当前节点超过矩阵范围，或者当前节点已被访问，或者当前节点行列坐标的数位之和大于k，则不合法，直接返回0
        if (i < 0 || i >= m || j < 0 || j >= n || visited[i][j] || getNumSum(i, j) > k) {
            return 0;
        }

        int count = 1;
        visited[i][j] = true;

        //因为从(0,0)开始往右下角走，所以只需要考虑向下和向右这两种情况
        count = count + dfs(i + 1, j, m, n, k, visited);
        count = count + dfs(i, j + 1, m, n, k, visited);

        return count;
    }

    /**
     * 计算i、j两数的每位之和
     *
     * @param i
     * @param j
     * @return
     */
    private int getNumSum(int i, int j) {
        int sum = 0;

        while (i != 0) {
            sum = sum + i % 10;
            i = i / 10;
        }

        while (j != 0) {
            sum = sum + j % 10;
            j = j / 10;
        }

        return sum;
    }
}
