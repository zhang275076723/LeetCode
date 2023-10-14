package com.zhang.java;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * @Date 2023/9/19 08:33
 * @Author zsy
 * @Description 不同岛屿的数量 dfs和bfs类比Problem79、Problem130、Problem200、Problem212、Problem463、Problem547、Problem695、Problem711、Problem733、Problem827、Problem994、Problem1034、Problem1162、Problem1254、Problem1905、Offer12
 * 给定一个非空 01 二维数组表示的网格，一个岛屿由四连通（上、下、左、右四个方向）的 1 组成，你可以认为网格的四周被海水包围。
 * 请你计算这个网格中共有多少个形状不同的岛屿。
 * 两个岛屿被认为是相同的，当且仅当一个岛屿可以通过平移变换（不可以旋转、翻转）和另一个岛屿重合。
 * <p>
 * 输入: grid = [
 * [1,1,0,0,0],
 * [1,1,0,0,0],
 * [0,0,0,1,1],
 * [0,0,0,1,1]
 * ]
 * 输出: 1
 * 解释：其中有两个岛屿，但是左上角和右下角的岛屿形状相同，所以不同的岛屿共有一个，算法返回 1。
 * <p>
 * 输入: grid = [
 * [1,1,0,1,1],
 * [1,0,0,0,0],
 * [0,0,0,0,1],
 * [1,1,0,1,1]
 * ]
 * 输出: 3
 * 解释：其中有四个岛屿，但是左下角和右上角的岛屿形状相同，所以不同的岛屿共有三个，算法返回 3。
 * <p>
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 50
 * grid[i][j] 仅包含 0 或 1
 */
public class Problem694 {
    public static void main(String[] args) {
        Problem694 problem694 = new Problem694();
//        int[][] grid = {
//                {1, 1, 0, 0, 0},
//                {1, 1, 0, 0, 0},
//                {0, 0, 0, 1, 1},
//                {0, 0, 0, 1, 1}
//        };
        int[][] grid = {
                {1, 1, 0, 1, 1},
                {1, 0, 0, 0, 0},
                {0, 0, 0, 0, 1},
                {1, 1, 0, 1, 1}
        };
        System.out.println(problem694.numDistinctIslands(grid));
        System.out.println(problem694.numDistinctIslands2(grid));
    }

    /**
     * dfs
     * 核心思想：对每个岛屿需要得到当前岛屿的唯一识别
     * dfs遍历每个岛屿，保存遍历到的路径，并且回溯路径也保存，如果当前岛屿的路径在之前保存的路径中出现过，则存在相同岛屿
     * 从当前节点出发记为0，往上遍历记为1，往下遍历记为2，往左遍历记为3，往右遍历记为4，回溯记为5
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param grid
     * @return
     */
    public int numDistinctIslands(int[][] grid) {
        //保存不同岛屿路径的set集合，路径为从当前节点dfs遍历当前岛屿的过程
        Set<String> set = new HashSet<>();
        boolean[][] visited = new boolean[grid.length][grid[0].length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    //岛屿的唯一识别，当前岛屿遍历的路径(包含回溯路径)
                    StringBuilder path = new StringBuilder();

                    //当前节点出发记为0
                    dfs(i, j, 0, grid, visited, path);

                    set.add(path.toString());
                }
            }
        }

        return set.size();
    }

    /**
     * bfs
     * 核心思想：对每个岛屿需要得到当前岛屿的唯一识别
     * bfs遍历每个岛屿，当前岛屿中每个节点减去起始节点的横纵坐标节点，得到相对节点，作为当前岛屿的标记，
     * 如果当前岛屿的标记在之前保存的标记中出现过，则存在相同岛屿
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param grid
     * @return
     */
    public int numDistinctIslands2(int[][] grid) {
        //保存不同岛屿标记的set集合，当前岛屿中每个节点减去起始节点的横纵坐标节点，得到的相对节点作为当前岛屿的标记
        Set<String> set = new HashSet<>();
        boolean[][] visited = new boolean[grid.length][grid[0].length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    //岛屿的唯一识别，当前岛屿中每个节点减去起始节点的横纵坐标节点，得到相对节点
                    StringBuilder key = new StringBuilder();

                    bfs(i, j, i, j, grid, visited, key);

                    set.add(key.toString());
                }
            }
        }

        return set.size();
    }

    private void dfs(int i, int j, int direction, int[][] grid, boolean[][] visited, StringBuilder path) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] != 1 || visited[i][j]) {
            return;
        }

        visited[i][j] = true;
        path.append(direction);

        //往上遍历记为1
        dfs(i - 1, j, 1, grid, visited, path);
        //往下遍历记为2
        dfs(i + 1, j, 2, grid, visited, path);
        //往左遍历记为3
        dfs(i, j - 1, 3, grid, visited, path);
        //往右遍历记为4
        dfs(i, j + 1, 4, grid, visited, path);

        //回溯记为5
        path.append(5);
    }

    private void bfs(int startI, int startJ, int i, int j, int[][] grid, boolean[][] visited, StringBuilder key) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{i, j});

        while (!queue.isEmpty()) {
            int[] arr = queue.poll();

            if (arr[0] < 0 || arr[0] >= grid.length || arr[1] < 0 || arr[1] >= grid[0].length ||
                    grid[arr[0]][arr[1]] != 1 || visited[arr[0]][arr[1]]) {
                continue;
            }

            visited[arr[0]][arr[1]] = true;

            queue.offer(new int[]{arr[0] - 1, arr[1]});
            queue.offer(new int[]{arr[0] + 1, arr[1]});
            queue.offer(new int[]{arr[0], arr[1] - 1});
            queue.offer(new int[]{arr[0], arr[1] + 1});

            //拼接当前岛屿的标记，当前岛屿中每个节点减去起始节点的横纵坐标节点，得到相对节点
            key.append('(').append(arr[0] - startI).append(',').append(arr[1] - startJ).append(')');
        }
    }
}
