package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/2/9 12:40
 * @Author zsy
 * @Description 最大人工岛 华为机试题 dfs和bfs类比Problem79、Problem130、Problem200、Problem212、Problem463、Problem547、Problem694、Problem695、Problem711、Problem733、Problem994、Problem1034、Problem1162、Problem1254、Problem1568、Problem1905、Offer12 并查集类比Problem130、Problem200、Problem261、Problem305、Problem323、Problem399、Problem547、Problem684、Problem685、Problem695、Problem765、Problem785、Problem886、Problem952、Problem1135、Problem1254、Problem1319、Problem1489、Problem1568、Problem1584、Problem1627、Problem1905、Problem1998、Problem2685
 * 给你一个大小为 n x n 二进制矩阵 grid 。最多 只能将一格 0 变成 1 。
 * 返回执行此操作后，grid 中最大的岛屿面积是多少？
 * 岛屿 由一组上、下、左、右四个方向相连的 1 形成。
 * <p>
 * 输入: grid = [[1, 0], [0, 1]]
 * 输出: 3
 * 解释: 将一格0变成1，最终连通两个小岛得到面积为 3 的岛屿。
 * <p>
 * 输入: grid = [[1, 1], [1, 0]]
 * 输出: 4
 * 解释: 将一格0变成1，岛屿的面积扩大为 4。
 * <p>
 * 输入: grid = [[1, 1], [1, 1]]
 * 输出: 4
 * 解释: 没有0可以让我们变成1，面积依然为 4。
 * <p>
 * n == grid.length
 * n == grid[i].length
 * 1 <= n <= 500
 * grid[i][j] 为 0 或 1
 */
public class Problem827 {
    public static void main(String[] args) {
        Problem827 problem827 = new Problem827();
        int[][] grid = {
                {1, 0},
                {0, 1}
        };
//        int[][] grid = {
//                {0, 0, 0, 0, 0, 0, 0},
//                {0, 1, 1, 1, 1, 0, 0},
//                {0, 1, 0, 0, 1, 0, 0},
//                {1, 0, 1, 0, 1, 0, 0},
//                {0, 1, 0, 0, 1, 0, 0},
//                {0, 1, 0, 0, 1, 0, 0},
//                {0, 1, 1, 1, 1, 0, 0}
//        };
        System.out.println(problem827.largestIsland(grid));
        System.out.println(problem827.largestIsland2(grid));
        System.out.println(problem827.largestIsland3(grid));
    }

    /**
     * dfs
     * dfs得到每个岛屿的面积，map存储岛屿的面积，岛屿最左上元素(i*grid[0].length+j+1)作为该岛屿的key，岛屿面积作为value，
     * 遍历为0的节点，判断当前节点由0变1之后，是否连通两个岛屿，得到连通的最大岛屿面积
     * 时间复杂度O(n^2)，空间复杂度O(n^2)
     *
     * @param grid
     * @return
     */
    public int largestIsland(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        //初始化最大面积为最大岛屿面积，避免没有0可以合并的情况
        int maxArea = 0;
        //key：当前岛屿的唯一标识，岛屿的最左上元素(i*grid[0].length+j+1)，value：岛屿面积
        Map<Integer, Integer> map = new HashMap<>();
        //访问数组，0：未访问，大于0的数：已访问，当前节点属于哪个岛屿，即岛屿的唯一标识
        int[][] visited = new int[grid.length][grid[0].length];

        //从左往右、从上往下遍历，保证先遍历到岛屿的最左上元素，保证map中key是岛屿的最左上元素
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                //对未被访问为1的节点进行dfs，得到每个岛屿面积，放入map中
                if (grid[i][j] == 1 && visited[i][j] == 0) {
                    //(i,j)所在岛屿的标记，必须要额外加1，避免左上角元素dfs无法跳出循环
                    int key = i * grid[0].length + j + 1;
                    int area = dfs(i, j, key, grid, visited);
                    map.put(key, area);
                    //更新最大面积
                    maxArea = Math.max(maxArea, area);
                }
            }
        }

        //当前节点的上下左右四个位置
        int[][] direction = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                //遍历为0的节点，判断当前节点由0变1之后，是否连通两个岛屿，得到连通的最大岛屿面积
                if (grid[i][j] == 0) {
                    //当前节点(i,j)由0变为1之后，面积为1
                    int area = 1;
                    //存储岛屿的唯一标识set，避免当前为0的节点两个方向上可以连通的岛屿是同一个岛屿
                    Set<Integer> set = new HashSet<>();

                    for (int k = 0; k < direction.length; k++) {
                        int x = i + direction[k][0];
                        int y = j + direction[k][1];

                        //节点(x,y)不在矩阵范围之内，或节点(x,y)不是1，进行下次循环
                        if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length || grid[x][y] != 1) {
                            continue;
                        }

                        //(x,y)所在岛屿的标记
                        int key = visited[x][y];

                        //(x,y)所在岛屿没有添加到set中，岛屿面积累加到area中
                        if (!set.contains(key)) {
                            area = area + map.get(key);
                            //(x,y)所在岛屿的标记加入set集合，避免重复累加
                            set.add(key);
                        }
                    }

                    //更新一个0变1之后的最大岛屿面积
                    maxArea = Math.max(maxArea, area);
                }
            }
        }

        return maxArea;
    }

    /**
     * bfs
     * bfs得到每个岛屿的面积，map存储岛屿的面积，岛屿最左上元素(i*grid[0].length+j+1)作为该岛屿的key，岛屿面积作为value，
     * 遍历为0的节点，判断当前节点由0变1之后，是否连通两个岛屿，得到连通的最大岛屿面积
     * 时间复杂度O(n^2)，空间复杂度O(n^2)
     *
     * @param grid
     * @return
     */
    public int largestIsland2(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        //初始化最大面积为最大岛屿面积，避免没有0可以合并的情况
        int maxArea = 0;
        //key：当前岛屿的唯一标识，岛屿的最左上元素(i*grid[0].length+j+1)，value：岛屿面积
        Map<Integer, Integer> map = new HashMap<>();
        //访问数组，0：未访问，大于0的数：已访问，当前节点属于哪个岛屿，即岛屿的唯一标识
        int[][] visited = new int[grid.length][grid[0].length];

        //从左往右、从上往下遍历，保证先遍历到岛屿的最左上元素，保证map中key是岛屿的最左上元素
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                //对未被访问为1的节点进行bfs，得到每个岛屿面积，放入map中
                if (grid[i][j] == 1 && visited[i][j] == 0) {
                    int key = i * grid[0].length + j + 1;
                    int area = bfs(i, j, key, grid, visited);
                    map.put(key, area);
                    //更新最大面积
                    maxArea = Math.max(maxArea, area);
                }
            }
        }

        //当前节点的上下左右四个位置
        int[][] direction = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                //遍历为0的节点，判断当前节点由0变1之后，是否连通两个岛屿，得到连通的最大岛屿面积
                if (grid[i][j] == 0) {
                    //当前节点(i,j)由0变为1之后，面积为1
                    int area = 1;
                    //存储岛屿的唯一标识set，避免当前为0的节点两个方向上可以连通的岛屿是同一个岛屿
                    Set<Integer> set = new HashSet<>();

                    for (int k = 0; k < direction.length; k++) {
                        int x = i + direction[k][0];
                        int y = j + direction[k][1];

                        //节点(x,y)不在矩阵范围之内，或节点(x,y)不是1，进行下次循环
                        if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length || grid[x][y] != 1) {
                            continue;
                        }

                        //(x,y)所在岛屿的标记
                        int key = visited[x][y];

                        //(x,y)所在岛屿没有添加到set中，岛屿面积累加到area中
                        if (!set.contains(key)) {
                            area = area + map.get(key);
                            //(x,y)所在岛屿的标记加入set集合，避免重复累加
                            set.add(key);
                        }
                    }

                    //更新一个0变1之后的最大岛屿面积
                    maxArea = Math.max(maxArea, area);
                }
            }
        }

        return maxArea;
    }

    /**
     * 并查集
     * 通过并查集遍历grid得到每个岛屿的面积，
     * 遍历为0的节点，判断当前节点由0变1之后，是否连通两个岛屿，得到连通的最大岛屿面积
     * 间复杂度O(n^2*α(n^2))=O(n^2)，空间复杂度O(n^2) (find()和union()的时间复杂度为O(α(n^2))，可视为常数O(1))
     *
     * @param grid
     * @return
     */
    public int largestIsland3(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        UnionFind unionFind = new UnionFind(grid);
        //当前节点的上下左右四个位置
        int[][] direction = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                //当前节点为1时，和当前位置的上下左右1进行合并，成为一个连通分量，得到当前连通分量的面积
                if (grid[i][j] == 1) {
                    for (int k = 0; k < direction.length; k++) {
                        int x = i + direction[k][0];
                        int y = j + direction[k][1];

                        //节点(x,y)不在矩阵范围之内，或节点(x,y)不是1，进行下次循环
                        if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length || grid[x][y] != 1) {
                            continue;
                        }

                        //(i,j)和(x,y)合并为一个连通分量
                        unionFind.union(i * grid[0].length + j, x * grid[0].length + y);
                    }
                }
            }
        }

        //初始化最大面积为最大岛屿面积，避免没有0可以合并的情况
        int maxArea = 0;

        //并查集中连通分量的根节点面积中的最大值，即为岛屿的最大面积
        for (int area : unionFind.area) {
            maxArea = Math.max(maxArea, area);
        }

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                //遍历为0的节点，判断当前节点由0变1之后，是否连通两个岛屿，得到连通的最大岛屿面积
                if (grid[i][j] == 0) {
                    //当前节点(i,j)由0变为1之后，面积为1
                    int area = 1;
                    //存储岛屿的唯一标识set，避免当前为0的节点两个方向上可以连通的岛屿是同一个岛屿
                    Set<Integer> set = new HashSet<>();

                    for (int k = 0; k < direction.length; k++) {
                        int x = i + direction[k][0];
                        int y = j + direction[k][1];

                        //节点(x,y)不在矩阵范围之内，或节点(x,y)不是1，进行下次循环
                        if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length || grid[x][y] != 1) {
                            continue;
                        }

                        //当前节点(x,y)根节点的下标索引
                        //注意：必须使用unionFind.find找(x,y)的根节点，不能使用unionFind.parent找(x,y)的根节点，
                        //因为有可能(x,y)没有进行路径压缩，此时unionFind.parent并没有指向当前连通分量的根节点
                        int rootIndex = unionFind.find(x * grid[0].length + y);

                        //(x,y)所在岛屿没有添加到set中，岛屿面积累加到area中
                        if (!set.contains(rootIndex)) {
                            area = area + unionFind.area[rootIndex];
                            //(x,y)根节点下标索引加入set集合，避免重复累加
                            set.add(rootIndex);
                        }
                    }

                    //更新一个0变1之后的最大岛屿面积
                    maxArea = Math.max(maxArea, area);
                }
            }
        }

        return maxArea;
    }

    private int dfs(int i, int j, int key, int[][] grid, int[][] visited) {
        //当前节点(i,j)不在矩阵范围之内，或当前节点(i,j)已被访问，或当前节点(i,j)不是1，直接返回0
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || visited[i][j] != 0 || grid[i][j] != 1) {
            return 0;
        }

        //当前位置已被访问，key为当前节点属于的岛屿标记
        visited[i][j] = key;
        int area = 1;

        //往上下左右找
        area = area + dfs(i - 1, j, key, grid, visited);
        area = area + dfs(i + 1, j, key, grid, visited);
        area = area + dfs(i, j - 1, key, grid, visited);
        area = area + dfs(i, j + 1, key, grid, visited);

        return area;
    }

    private int bfs(int i, int j, int key, int[][] grid, int[][] visited) {
        int area = 0;
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{i, j});

        while (!queue.isEmpty()) {
            int[] arr = queue.poll();

            //当前节点(arr[0],arr[1])不在矩阵范围之内，或当前节点(arr[0],arr[1])已被访问，或当前节点(arr[0],arr[1])不是1，进行下次循环
            if (arr[0] < 0 || arr[0] >= grid.length || arr[1] < 0 || arr[1] >= grid[0].length ||
                    visited[arr[0]][arr[1]] != 0 || grid[arr[0]][arr[1]] != 1) {
                continue;
            }

            //当前位置已被访问，key为当前节点属于的岛屿标记
            visited[arr[0]][arr[1]] = key;
            //面积加1
            area++;

            //当前位置的上下左右加入队列
            queue.offer(new int[]{arr[0] - 1, arr[1]});
            queue.offer(new int[]{arr[0] + 1, arr[1]});
            queue.offer(new int[]{arr[0], arr[1] - 1});
            queue.offer(new int[]{arr[0], arr[1] + 1});
        }

        return area;
    }

    /**
     * 并查集(不相交数据集)类
     * 用数组的形式表示图
     */
    private static class UnionFind {
        //并查集中连通分量的个数
        private int count;
        //节点的父节点索引下标数组，二维数组按照由左到右由上到下的顺序，从0开始存储
        private final int[] parent;
        //节点的权值数组(节点的高度)，只有一个节点的权值为1
        private final int[] weight;
        //连通分量中节点个数数组，用于求面积
        private final int[] area;

        public UnionFind(int[][] grid) {
            count = 0;
            parent = new int[grid.length * grid[0].length];
            weight = new int[grid.length * grid[0].length];
            area = new int[grid.length * grid[0].length];

            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    //当前节点为'1'时，才加入并查集中
                    if (grid[i][j] == 1) {
                        parent[i * grid[0].length + j] = i * grid[0].length + j;
                        weight[i * grid[0].length + j] = 1;
                        area[i * grid[0].length + j] = 1;
                        count++;
                    }
                }
            }
        }

        public void union(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);

            if (rootI != rootJ) {
                if (weight[rootI] < weight[rootJ]) {
                    parent[rootI] = rootJ;
                    //更新根节点的面积
                    area[rootJ] = area[rootI] + area[rootJ];
                } else if (weight[rootI] > weight[rootJ]) {
                    parent[rootJ] = rootI;
                    //更新根节点的面积
                    area[rootI] = area[rootI] + area[rootJ];
                } else {
                    parent[rootJ] = rootI;
                    weight[rootI]++;
                    //更新根节点的面积
                    area[rootI] = area[rootI] + area[rootJ];
                }

                //i、j两个连通分量合并，并查集中连通分量的个数减1
                count--;
            }
        }

        public int find(int i) {
            if (parent[i] != i) {
                parent[i] = find(parent[i]);
            }

            return parent[i];
        }

        public boolean isConnected(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);

            return rootI == rootJ;
        }
    }
}
