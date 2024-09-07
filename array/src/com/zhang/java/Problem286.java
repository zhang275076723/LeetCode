package com.zhang.java;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2024/12/24 08:39
 * @Author zsy
 * @Description 墙与门 多源bfs类比Problem542、Problem994、Problem1162、Problem1765、Problem2812
 * 你被给定一个 m × n 的二维网格 rooms ，网格中有以下三种可能的初始化值：
 * -1 表示墙或是障碍物
 * 0 表示一扇门
 * INF 无限表示一个空的房间。
 * 然后，我们用 2^31 - 1 = 2147483647 代表 INF。
 * 你可以认为通往门的距离总是小于 2147483647 的。
 * 你要给每个空房间位上填上该房间到 最近门的距离 ，如果无法到达门，则填 INF 即可。
 * <p>
 * 输入：rooms = [[2147483647,-1,0,2147483647],[2147483647,2147483647,2147483647,-1],[2147483647,-1,2147483647,-1],[0,-1,2147483647,2147483647]]
 * 输出：[[3,-1,0,1],[2,2,1,-1],[1,-1,2,-1],[0,-1,3,4]]
 * <p>
 * 输入：rooms = [[-1]]
 * 输出：[[-1]]
 * <p>
 * 输入：rooms = [[2147483647]]
 * 输出：[[2147483647]]
 * <p>
 * 输入：rooms = [[0]]
 * 输出：[[0]]
 * <p>
 * m == rooms.length
 * n == rooms[i].length
 * 1 <= m, n <= 250
 * rooms[i][j] 是 -1、0 或 2^31 - 1
 */
public class Problem286 {
    public static void main(String[] args) {
        Problem286 problem286 = new Problem286();
        int[][] rooms = {
                {2147483647, -1, 0, 2147483647},
                {2147483647, 2147483647, 2147483647, -1},
                {2147483647, -1, 2147483647, -1},
                {0, -1, 2147483647, 2147483647}
        };
        problem286.wallsAndGates(rooms);
        System.out.println(Arrays.deepToString(rooms));
    }

    /**
     * 多源bfs
     * 值为0的节点入队，bfs每次往外扩一层，得到当前空房间到门的最小距离
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param rooms
     */
    public void wallsAndGates(int[][] rooms) {
        int m = rooms.length;
        int n = rooms[0].length;

        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[m][n];
        int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        //空房间
        int INF = Integer.MAX_VALUE;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                //值为0的节点入队
                if (rooms[i][j] == 0) {
                    queue.offer(new int[]{i, j});
                    visited[i][j] = true;
                }
            }
        }

        //bfs遍历过程中，当前为INF的节点到最近门节点的最小距离
        int distance = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                int[] arr = queue.poll();
                int x1 = arr[0];
                int y1 = arr[1];
                rooms[x1][y1] = distance;

                for (int j = 0; j < direction.length; j++) {
                    int x2 = x1 + direction[j][0];
                    int y2 = y1 + direction[j][1];

                    //邻接节点越界，或者邻接节点已访问，直接进行下次循环
                    if (x2 < 0 || x2 >= m || y2 < 0 || y2 >= n || visited[x2][y2]) {
                        continue;
                    }

                    if (rooms[x2][y2] == INF) {
                        queue.offer(new int[]{x2, y2});
                        visited[x2][y2] = true;
                    }
                }
            }

            //bfs每次往外扩一层
            distance++;
        }
    }
}
