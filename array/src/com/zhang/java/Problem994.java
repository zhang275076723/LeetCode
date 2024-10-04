package com.zhang.java;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2023/2/15 08:10
 * @Author zsy
 * @Description 腐烂的橘子 多源bfs类比Problem286、Problem542、Problem1162、Problem1765、Problem2258、Problem2812 dfs和bfs类比Problem79、Problem130、Problem200、Problem212、Problem463、Problem490、Problem499、Problem505、Problem529、Problem547、Problem694、Problem695、Problem711、Problem733、Problem827、Problem1034、Problem1162、Problem1254、Problem1568、Problem1905、Offer12
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
     * 值为2的节点(腐烂的橘子)入列，bfs每次往外扩一层，相当于经过一分钟，得到将要变为腐烂橘子的新鲜橘子，
     * 统计bfs结束后新鲜橘子的个数，如果不存在新鲜的橘子，则返回需要的时间；如果存在新鲜的橘子，则返回-1
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param grid
     * @return
     */
    public int orangesRotting(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[m][n];
        //新鲜的橘子个数
        int freshOrangesCount = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    freshOrangesCount++;
                } else if (grid[i][j] == 2) {
                    //腐烂的橘子入队
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
                int x1 = arr[0];
                int y1 = arr[1];

                //遍历当前节点的邻接节点，未被访问的新鲜橘子邻接节点入队，表示为已腐烂
                for (int j = 0; j < direction.length; j++) {
                    int x2 = x1 + direction[j][0];
                    int y2 = y1 + direction[j][1];

                    //邻接节点越界，或者邻接节点已被访问，或者邻接节点不是新鲜的橘子，则直接进行下次循环
                    if (x2 < 0 || x2 >= m || y2 < 0 || y2 >= n || visited[x2][y2] || grid[x2][y2] != 1) {
                        continue;
                    }

                    //未被访问的新鲜橘子邻接节点入队，并置为已访问，表示当前橘子已腐烂
                    queue.offer(new int[]{x2, y2});
                    visited[x2][y2] = true;
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
