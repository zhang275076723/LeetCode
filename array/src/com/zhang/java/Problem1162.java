package com.zhang.java;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2023/2/14 08:28
 * @Author zsy
 * @Description 地图分析 多源bfs类比Problem286、Problem542、Problem994、Problem2812 dfs和bfs类比Problem79、Problem130、Problem200、Problem212、Problem463、Problem490、Problem499、Problem505、Problem529、Problem547、Problem694、Problem695、Problem711、Problem733、Problem827、Problem994、Problem1034、Problem1254、Problem1568、Problem1905、Offer12
 * 你现在手里有一份大小为 n x n 的 网格 grid，上面的每个 单元格 都用 0 和 1 标记好了。
 * 其中 0 代表海洋，1 代表陆地。
 * 请你找出一个海洋单元格，这个海洋单元格到离它最近的陆地单元格的距离是最大的，并返回该距离。
 * 如果网格上只有陆地或者海洋，请返回 -1。
 * 我们这里说的距离是「曼哈顿距离」(Manhattan Distance)：
 * (x0, y0) 和 (x1, y1)这两个单元格之间的距离是 |x0 - x1| + |y0 - y1|。
 * <p>
 * 输入：grid = [[1,0,1],[0,0,0],[1,0,1]]
 * 输出：2
 * 解释：
 * 海洋单元格 (1, 1) 和所有陆地单元格之间的距离都达到最大，最大距离为 2。
 * <p>
 * 输入：grid = [[1,0,0],[0,0,0],[0,0,0]]
 * 输出：4
 * 解释：
 * 海洋单元格 (2, 2) 和所有陆地单元格之间的距离都达到最大，最大距离为 4。
 * <p>
 * n == grid.length
 * n == grid[i].length
 * 1 <= n <= 100
 * grid[i][j] 不是 0 就是 1
 */
public class Problem1162 {
    public static void main(String[] args) {
        Problem1162 problem1162 = new Problem1162();
        int[][] grid = {
                {1, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        };
        System.out.println(problem1162.maxDistance(grid));
        System.out.println(problem1162.maxDistance2(grid));
    }

    /**
     * 暴力bfs
     * 对每个为0的节点bfs，得到为0的节点到最近为1的节点的距离，取所有遍历为0的节点到最近为1的节点的距离中的最大值
     * 时间复杂度O(n^4)，空间复杂度O(n^2)
     *
     * @param grid
     * @return
     */
    public int maxDistance(int[][] grid) {
        //赋初值-1，表示矩阵中全1或全0的特殊情况
        int maxDistance = -1;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                //对每个为0的节点进行bfs，得到当前节点和他最接近1的距离
                if (grid[i][j] == 0) {
                    maxDistance = Math.max(maxDistance, bfs(i, j, grid));
                }
            }
        }

        return maxDistance;
    }

    /**
     * 多源bfs+优化
     * 逆向思维，不对为0的节点bfs，而是对为1的节点bfs，每次于往外扩一层，将当前层中遍历到为0的节点加入队列，
     * 直至队列为空，得到为0的节点到最近为1的节点的距离中的最大距离
     * 时间复杂度O(n^2)，空间复杂度O(n^2)
     *
     * @param grid
     * @return
     */
    public int maxDistance2(int[][] grid) {
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[grid.length][grid[0].length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                //为1的节点加入队列，并设置为已访问
                if (grid[i][j] == 1) {
                    queue.offer(new int[]{i, j});
                    visited[i][j] = true;
                }
            }
        }

        //队列为空或队列大小等于矩阵大小，则矩阵全为0或全为1，则不存在为0的节点到最近为1的节点的距离中的最大距离，直接返回-1
        if (queue.isEmpty() || queue.size() == grid.length * grid[0].length) {
            return -1;
        }

        //为0的节点到最近为1的节点的距离中的最大距离
        int maxDistance = 0;
        int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        while (!queue.isEmpty()) {
            int size = queue.size();

            //遍历当前层中的节点，将下一层为0的节点加入队列
            for (int i = 0; i < size; i++) {
                int[] arr = queue.poll();

                //当前节点(arr[0],arr[1])上下左右未被访问的为0的节点(x,y)加入队列
                for (int j = 0; j < direction.length; j++) {
                    int x = arr[0] + direction[j][0];
                    int y = arr[1] + direction[j][1];

                    //节点(x,y)不在矩阵范围之内，或者节点(x,y)已被访问，或者节点(x,y)不是为0的节点，则直接进行下次循环
                    if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length ||
                            visited[x][y] || grid[x][y] != 0) {
                        continue;
                    }

                    //为0的节点(x,y)加入队列，并设置为已访问
                    queue.offer(new int[]{x, y});
                    visited[x][y] = true;
                }
            }

            //maxDistance加1，表示bfs每次往外扩一层
            maxDistance++;
        }

        //因为将最远的0加入队列，此时还需要往外扩一层，额外多遍历了一次，所以需要减1
        return maxDistance - 1;
    }

    private int bfs(int i, int j, int[][] grid) {
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        queue.offer(new int[]{i, j});
        //为0的节点(i,j)到最近为1的节点的距离
        int distance = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int k = 0; k < size; k++) {
                int[] arr = queue.poll();

                //当前节点不在矩阵范围之内，或者已经遍历过，直接进行下次循环
                if (arr[0] < 0 || arr[0] >= grid.length || arr[1] < 0 || arr[1] >= grid[0].length ||
                        visited[arr[0]][arr[1]]) {
                    continue;
                }

                //当前节点为1，则找到了为0的节点(i,j)到最近为1的节点(arr[0],arr[1])的距离，直接返回distance
                if (grid[arr[0]][arr[1]] == 1) {
                    return distance;
                }

                visited[arr[0]][arr[1]] = true;

                queue.offer(new int[]{arr[0] - 1, arr[1]});
                queue.offer(new int[]{arr[0] + 1, arr[1]});
                queue.offer(new int[]{arr[0], arr[1] - 1});
                queue.offer(new int[]{arr[0], arr[1] + 1});
            }

            //距离每次往外扩一层
            distance++;
        }

        //没有遍历到为1的节点，返回-1
        return -1;
    }
}
