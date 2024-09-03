package com.zhang.java;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2023/2/15 08:10
 * @Author zsy
 * @Description 腐烂的橘子 多源bfs类比Problem542、Problem1162、Problem2812 dfs和bfs类比Problem79、Problem130、Problem200、Problem212、Problem463、Problem490、Problem499、Problem505、Problem529、Problem547、Problem694、Problem695、Problem711、Problem733、Problem827、Problem1034、Problem1162、Problem1254、Problem1568、Problem1905、Offer12
 * 在给定的 m x n 网格 grid 中，每个单元格可以有以下三个值之一：
 * 值 0 代表空单元格；
 * 值 1 代表新鲜橘子；
 * 值 2 代表腐烂的橘子。
 * 每分钟，腐烂的橘子 周围 4 个方向上相邻 的新鲜橘子都会腐烂。
 * 返回 直到单元格中没有新鲜橘子为止所必须经过的最小分钟数。
 * 如果不可能，返回 -1 。
 * <p>
 * 输入：grid = [[2,1,1],[1,1,0],[0,1,1]]
 * 输出：4
 * <p>
 * 输入：grid = [[2,1,1],[0,1,1],[1,0,1]]
 * 输出：-1
 * 解释：左下角的橘子（第 2 行， 第 0 列）永远不会腐烂，因为腐烂只会发生在 4 个正向上。
 * <p>
 * 输入：grid = [[0,2]]
 * 输出：0
 * 解释：因为 0 分钟时已经没有新鲜橘子了，所以答案就是 0 。
 * <p>
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 10
 * grid[i][j] 仅为 0、1 或 2
 */
public class Problem994 {
    public static void main(String[] args) {
        Problem994 problem994 = new Problem994();
        int[][] grid = {
                {2, 1, 1},
                {1, 1, 0},
                {0, 1, 1}
        };
//        int[][] grid = {
//                {2, 1, 1},
//                {0, 1, 1},
//                {1, 0, 1}
//        };
        System.out.println(problem994.orangesRotting(grid));
    }

    /**
     * 多源bfs
     * 将为2的节点(腐烂的橘子)加入队列，作为bfs的起始元素，每次出队的元素个数为上次队列中元素的个数，每次相当于往外扩一层，
     * 遍历到为1的节点(新鲜的橘子)置为2(腐烂的橘子)，遍历完之后判断是否还存在为1的节点(新鲜的橘子)，
     * 如果不存在，返回需要的时间；如果存在，返回-1
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param grid
     * @return
     */
    public int orangesRotting(int[][] grid) {
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        //新鲜的橘子个数
        int freshOrangesCount = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    freshOrangesCount++;
                } else if (grid[i][j] == 2) {
                    queue.offer(new int[]{i, j});
                    visited[i][j] = true;
                }
            }
        }

        //矩阵中新鲜的橘子全部腐烂需要的时间
        int time = 0;
        int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        while (!queue.isEmpty()) {
            //当前已经不存在新鲜的橘子，则不需要继续bfs，直接返回time
            if (freshOrangesCount == 0) {
                return time;
            }

            int size = queue.size();

            //每次往外扩一层
            for (int i = 0; i < size; i++) {
                int[] arr = queue.poll();

                //当前节点(arr[0],arr[1])上下左右未被访问的新鲜的橘子节点(x,y)加入队列，表示为已腐烂
                for (int j = 0; j < direction.length; j++) {
                    int x = arr[0] + direction[j][0];
                    int y = arr[1] + direction[j][1];

                    //节点(x,y)不在矩阵范围之内，或者节点(x,y)已被访问，节点(x,y)不是新鲜的橘子，则直接进行下次循环
                    if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length ||
                            visited[x][y] || grid[x][y] != 1) {
                        continue;
                    }

                    //新鲜的橘子(x,y)加入队列，并置为已访问，表示当前橘子已腐烂
                    queue.offer(new int[]{x, y});
                    visited[x][y] = true;
                    //新鲜橘子个数减1
                    freshOrangesCount--;
                }
            }

            //time加1，表示bfs每次往外扩一层
            time++;
        }

        //bfs之后不存在新鲜的橘子，则返回time；否则，存在新鲜的橘子无法腐烂，返回-1
        return freshOrangesCount == 0 ? time : -1;
    }
}
