package com.zhang.java;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * @Date 2023/9/21 08:54
 * @Author zsy
 * @Description 统计子岛屿 dfs和bfs类比Problem79、Problem130、Problem200、Problem212、Problem463、Problem490、Problem499、Problem505、Problem529、Problem547、Problem694、Problem695、Problem711、Problem733、Problem827、Problem994、Problem1034、Problem1162、Problem1254、Problem1568、Offer12 并查集类比Problem130、Problem200、Problem261、Problem305、Problem323、Problem399、Problem547、Problem684、Problem685、Problem695、Problem765、Problem785、Problem827、Problem886、Problem952、Problem1135、Problem1254、Problem1319、Problem1361、Problem1489、Problem1568、Problem1584、Problem1627、Problem1998、Problem2685
 * 给你两个 m x n 的二进制矩阵 grid1 和 grid2 ，它们只包含 0 （表示水域）和 1 （表示陆地）。
 * 一个 岛屿 是由 四个方向 （水平或者竖直）上相邻的 1 组成的区域。
 * 任何矩阵以外的区域都视为水域。
 * 如果 grid2 的一个岛屿，被 grid1 的一个岛屿 完全 包含，也就是说 grid2 中该岛屿的每一个格子都被 grid1 中同一个岛屿完全包含，
 * 那么我们称 grid2 中的这个岛屿为 子岛屿 。
 * 请你返回 grid2 中 子岛屿 的 数目 。
 * <p>
 * 输入：grid1 = [
 * [1,1,1,0,0],
 * [0,1,1,1,1],
 * [0,0,0,0,0],
 * [1,0,0,0,0],
 * [1,1,0,1,1]
 * ],
 * grid2 = [
 * [1,1,1,0,0],
 * [0,0,1,1,1],
 * [0,1,0,0,0],
 * [1,0,1,1,0],
 * [0,1,0,1,0]
 * ]
 * 输出：3
 * 解释：如上图所示，左边为 grid1 ，右边为 grid2 。
 * grid2 中标红的 1 区域是子岛屿，总共有 3 个子岛屿。
 * <p>
 * 输入：grid1 = [
 * [1,0,1,0,1],
 * [1,1,1,1,1],
 * [0,0,0,0,0],
 * [1,1,1,1,1],
 * [1,0,1,0,1]
 * ],
 * grid2 = [
 * [0,0,0,0,0],
 * [1,1,1,1,1],
 * [0,1,0,1,0],
 * [0,1,0,1,0],
 * [1,0,0,0,1]
 * ]
 * 输出：2
 * 解释：如上图所示，左边为 grid1 ，右边为 grid2 。
 * grid2 中标红的 1 区域是子岛屿，总共有 2 个子岛屿。
 * <p>
 * m == grid1.length == grid2.length
 * n == grid1[i].length == grid2[i].length
 * 1 <= m, n <= 500
 * grid1[i][j] 和 grid2[i][j] 都要么是 0 要么是 1 。
 */
public class Problem1905 {
    public static void main(String[] args) {
        Problem1905 problem1905 = new Problem1905();
        int[][] grid1 = {
                {1, 1, 1, 0, 0},
                {0, 1, 1, 1, 1},
                {0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0},
                {1, 1, 0, 1, 1}
        };
        int[][] grid2 = {
                {1, 1, 1, 0, 0},
                {0, 0, 1, 1, 1},
                {0, 1, 0, 0, 0},
                {1, 0, 1, 1, 0},
                {0, 1, 0, 1, 0}
        };
//        int[][] grid1 = {
//                {0, 0, 0, 0, 0},
//                {0, 0, 1, 1, 0},
//                {0, 0, 0, 1, 1}
//        };
//        int[][] grid2 = {
//                {0, 0, 1, 0, 0},
//                {0, 0, 1, 1, 0},
//                {0, 0, 0, 1, 0}
//        };
        System.out.println(problem1905.countSubIslands(grid1, grid2));
        System.out.println(problem1905.countSubIslands2(grid1, grid2));
        System.out.println(problem1905.countSubIslands3(grid1, grid2));
    }

    /**
     * dfs
     * 对grid2为1的节点进行dfs，如果grid2中当前岛屿所有为1的节点都在grid1中出现，则grid2中当前岛屿是grid1的子岛屿
     * 注意：dfs过程中要保证grid2中当前岛屿所有节点被充分遍历，避免grid2中岛屿被多次统计的情况
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param grid1
     * @param grid2
     * @return
     */
    public int countSubIslands(int[][] grid1, int[][] grid2) {
        int count = 0;
        boolean[][] visited1 = new boolean[grid1.length][grid1[0].length];
        boolean[][] visited2 = new boolean[grid2.length][grid2[0].length];

        for (int i = 0; i < grid2.length; i++) {
            for (int j = 0; j < grid2[0].length; j++) {
                if (grid2[i][j] == 1 && !visited2[i][j]) {
                    if (dfs(i, j, grid1, grid2, visited1, visited2)) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    /**
     * bfs
     * 对grid2为1的节点进行bfs，如果grid2中当前岛屿所有为1的节点都在grid1中出现，则grid2中当前岛屿是grid1的子岛屿
     * 注意：bfs过程中要保证grid2中当前岛屿所有节点被充分遍历，避免grid2中岛屿被多次统计的情况
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param grid1
     * @param grid2
     * @return
     */
    public int countSubIslands2(int[][] grid1, int[][] grid2) {
        int count = 0;
        boolean[][] visited1 = new boolean[grid1.length][grid1[0].length];
        boolean[][] visited2 = new boolean[grid2.length][grid2[0].length];

        for (int i = 0; i < grid2.length; i++) {
            for (int j = 0; j < grid2[0].length; j++) {
                if (grid2[i][j] == 1 && !visited2[i][j]) {
                    if (bfs(i, j, grid1, grid2, visited1, visited2)) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    /**
     * 并查集
     * grid2中为1的相邻节点连接，作为同一个连通分量，
     * 使用2个set，set1记录grid2当前岛屿是grid1子岛屿连通分量的根节点，set2记录grid2当前岛屿不是grid1子岛屿连通分量的根节点，
     * 如果grid1、grid2中当前节点为1，并且grid2中当前节点所在岛屿没有在set2中被标记为不是grid1子岛屿，
     * 则到目前为止grid2当前节点所在岛屿仍是grid1子岛屿，grid2当前节点所在岛屿连通分量的根节点加入set1；
     * 否则，grid2当前节点所在岛屿不是grid1子岛屿，grid2当前节点所在岛屿连通分量的根节点从set1中移除，加入set2
     * 时间复杂度O(mn*α(mn))=O(mn)，空间复杂度O(mn) (find()和union()的时间复杂度为O(α(mn))，可视为常数O(1))
     *
     * @param grid1
     * @param grid2
     * @return
     */
    public int countSubIslands3(int[][] grid1, int[][] grid2) {
        //当前节点的上下左右四个位置
        int[][] direction = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        UnionFind unionFind2 = new UnionFind(grid2);

        for (int i = 0; i < grid2.length; i++) {
            for (int j = 0; j < grid2[0].length; j++) {
                if (grid2[i][j] == 1) {
                    for (int k = 0; k < direction.length; k++) {
                        int x = i + direction[k][0];
                        int y = j + direction[k][1];

                        if (x < 0 || x >= grid2.length || y < 0 || y >= grid2[0].length || grid2[x][y] != 1) {
                            continue;
                        }

                        unionFind2.union(i * grid2[0].length + j, x * grid2[0].length + y);
                    }
                }
            }
        }

        //grid2当前岛屿是grid1子岛屿连通分量的根节点的set集合
        Set<Integer> set1 = new HashSet<>();
        //grid2当前岛屿不是grid1子岛屿连通分量的根节点的set集合
        //注意：必须使用set2，不能只使用一个set，如果grid2当前节点所在岛屿仍是grid1子岛屿，
        //但遍历到下一个节点时，grid2当前节点所在岛屿不是grid1子岛屿，只有一个set的话，
        //则grid2当前节点所在岛屿连通分量的根节点从set中移除，如果grid2下一个节点还是该岛屿中节点的话，
        //仍会将grid2中岛屿不是grid1子岛屿连通分量的根节点加入set中
        Set<Integer> set2 = new HashSet<>();

        for (int i = 0; i < grid2.length; i++) {
            for (int j = 0; j < grid2[0].length; j++) {
                if (grid2[i][j] == 1) {
                    //grid2当前节点所在岛屿连通分量的根节点
                    int rootIndex = unionFind2.find(i * grid2[0].length + j);

                    //gird1、grid2中当前节点为1，并且grid2中当前节点所在岛屿没有在set2中被标记为不是grid1子岛屿，
                    //则到目前为止grid2当前节点所在岛屿仍是grid1子岛屿，grid2当前节点所在岛屿连通分量的根节点加入set1
                    if (grid1[i][j] == 1 && !set2.contains(rootIndex)) {
                        set1.add(rootIndex);
                    } else {
                        //grid2当前节点所在岛屿不是grid1子岛屿，grid2当前节点所在岛屿连通分量的根节点从set1中移除，加入set2
                        set1.remove(rootIndex);
                        set2.add(rootIndex);
                    }
                }
            }
        }

        //set1中的元素个数即为grid2中岛屿是grid1子岛屿的个数
        return set1.size();
    }

    private boolean dfs(int i, int j, int[][] grid1, int[][] grid2, boolean[][] visited1, boolean[][] visited2) {
        //当前节点不在矩阵返回内，或者当前节点在grid2中不为1，或者当前节点在grid2中已被访问，
        //则返回true，表示grid2当前岛屿遍历到的节点仍然是grid1的子岛屿
        if (i < 0 || i >= grid2.length || j < 0 || j >= grid2[0].length || grid2[i][j] != 1 || visited2[i][j]) {
            return true;
        }

        //当前节点在grid1中不为1，或者当前节点在grid1中已被访问，则返回false，表示grid2当前岛屿不是grid1的子岛屿
        if (grid1[i][j] != 1 || visited1[i][j]) {
            return false;
        }

        visited2[i][j] = true;
        visited1[i][j] = true;

//        //注意：不能这样写，因为当一个dfs为false的时候，后面的dfs都不会执行，导致grid2中当前岛屿未被充分遍历，
//        //有可能grid2中当前岛屿的部分岛屿是grid1的子岛屿，导致当前grid2中岛屿被多次统计
//        return dfs(i - 1, j, grid1, grid2, visited1, visited2) &&
//                dfs(i + 1, j, grid1, grid2, visited1, visited2) &&
//                dfs(i, j - 1, grid1, grid2, visited1, visited2) &&
//                dfs(i, j + 1, grid1, grid2, visited1, visited2);

        //dfs分开遍历，保证grid2中当前岛屿充分遍历，不会出现grid2中当前岛屿的部分岛屿是grid1的子岛屿，导致当前grid2中岛屿被多次统计
        boolean flag1 = dfs(i - 1, j, grid1, grid2, visited1, visited2);
        boolean flag2 = dfs(i + 1, j, grid1, grid2, visited1, visited2);
        boolean flag3 = dfs(i, j - 1, grid1, grid2, visited1, visited2);
        boolean flag4 = dfs(i, j + 1, grid1, grid2, visited1, visited2);

        return flag1 && flag2 && flag3 && flag4;
    }

    private boolean bfs(int i, int j, int[][] grid1, int[][] grid2, boolean[][] visited1, boolean[][] visited2) {
        //grid2当前岛屿是否是grid1的子岛屿的标志位
        //注意：必须使用标志位，而不能在发现不是子岛屿的时候立刻返回false，会导致grid2中当前岛屿未被充分遍历，
        //有可能grid2中当前岛屿的部分岛屿是grid1的子岛屿，导致当前grid2中岛屿被多次统计
        boolean flag = true;
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{i, j});

        while (!queue.isEmpty()) {
            int[] arr = queue.poll();

            //当前节点不在矩阵返回内，或者当前节点在grid2中不为1，或者当前节点在grid2中已被访问，
            //则直接进行下次循环，表示grid2当前岛屿遍历到的节点仍然是grid1的子岛屿
            if (arr[0] < 0 || arr[0] >= grid2.length || arr[1] < 0 || arr[1] >= grid2[0].length ||
                    grid2[arr[0]][arr[1]] != 1 || visited2[arr[0]][arr[1]]) {
                continue;
            }

            //当前节点在grid1中不为1，或者当前节点在grid1中已被访问，则flag置为false，表示grid2当前岛屿不是grid1的子岛屿
            //注意：不能直接返回false，如果直接返回false，导致grid2中当前岛屿未被充分遍历，
            //有可能grid2中当前岛屿的部分岛屿是grid1的子岛屿，导致当前grid2中岛屿被多次统计
            if (grid1[arr[0]][arr[1]] != 1 || visited1[arr[0]][arr[1]]) {
                flag = false;
            }

            visited2[arr[0]][arr[1]] = true;
            visited1[arr[0]][arr[1]] = true;

            queue.offer(new int[]{arr[0] - 1, arr[1]});
            queue.offer(new int[]{arr[0] + 1, arr[1]});
            queue.offer(new int[]{arr[0], arr[1] - 1});
            queue.offer(new int[]{arr[0], arr[1] + 1});
        }

        return flag;
    }

    /**
     * 并查集(不相交数据集)类
     * 用数组的形式表示图
     */
    private static class UnionFind {
        //连通分量的个数
        private int count;
        //节点的父节点数组
        private final int[] parent;
        //节点的权值数组
        private final int[] weight;

        public UnionFind(int[][] grid) {
            count = 0;
            parent = new int[grid.length * grid[0].length];
            weight = new int[grid.length * grid[0].length];

            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[i][j] == 1) {
                        count++;
                        parent[i * grid[0].length + j] = i * grid[0].length + j;
                        weight[i * grid[0].length + j] = 1;
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
                } else if (weight[rootI] > weight[rootJ]) {
                    parent[rootJ] = rootI;
                } else {
                    parent[rootJ] = rootI;
                    weight[rootI]++;
                }

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
