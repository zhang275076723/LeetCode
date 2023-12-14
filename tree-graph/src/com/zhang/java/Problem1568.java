package com.zhang.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date 2023/11/6 08:19
 * @Author zsy
 * @Description 使陆地分离的最少天数 Tarjan类比Problem1192、Problem1489、Problem2685 dfs和bfs类比Problem79、Problem130、Problem200、Problem212、Problem463、Problem490、Problem499、Problem505、Problem529、Problem547、Problem694、Problem695、Problem711、Problem733、Problem827、Problem994、Problem1034、Problem1162、Problem1254、Problem1905、Offer12 并查集类比Problem130、Problem200、Problem261、Problem305、Problem323、Problem399、Problem547、Problem684、Problem685、Problem695、Problem765、Problem785、Problem827、Problem886、Problem952、Problem1135、Problem1254、Problem1319、Problem1489、Problem1584、Problem1627、Problem1905、Problem1998、Problem2685
 * 给你一个大小为 m x n ，由若干 0 和 1 组成的二维网格 grid ，其中 1 表示陆地， 0 表示水。
 * 岛屿 由水平方向或竖直方向上相邻的 1 （陆地）连接形成。
 * 如果 恰好只有一座岛屿 ，则认为陆地是 连通的 ；否则，陆地就是 分离的 。
 * 一天内，可以将 任何单个 陆地单元（1）更改为水单元（0）。
 * 返回使陆地分离的最少天数。
 * <p>
 * 输入：grid = [[0,1,1,0],[0,1,1,0],[0,0,0,0]]
 * 输出：2
 * 解释：至少需要 2 天才能得到分离的陆地。
 * 将陆地 grid[1][1] 和 grid[0][2] 更改为水，得到两个分离的岛屿。
 * <p>
 * 输入：grid = [[1,1]]
 * 输出：2
 * 解释：如果网格中都是水，也认为是分离的 ([[1,1]] -> [[0,0]])，0 岛屿。
 * <p>
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 30
 * grid[i][j] 为 0 或 1
 */
public class Problem1568 {
    /**
     * dfs过程中第一次访问当前节点的dfn和low的初始值
     */
    private int index = 0;

    public static void main(String[] args) {
        Problem1568 problem1568 = new Problem1568();
        int[][] grid = {{0, 1, 1, 0}, {0, 1, 1, 0}, {0, 0, 0, 0}};
        System.out.println(problem1568.minDays(grid));
        //因为minDays()会修改grid，所以grid需要重新赋值
        grid = new int[][]{{0, 1, 1, 0}, {0, 1, 1, 0}, {0, 0, 0, 0}};
        System.out.println(problem1568.minDays2(grid));
    }

    /**
     * dfs
     * dfs求岛屿数量，岛屿数量大于等于2或等于0，则不需要分离，陆地分离的最少天数为0；
     * 岛屿数量等于1，将其中一个陆地单元'1'修改为水单元'0'，dfs求此时岛屿数量，如果岛屿数量大于等于2或等于0，
     * 则陆地分离的最少天数为1；否则，陆地分离的最少天数为2
     * 时间复杂度O((mn)^2)，空间复杂度O(mn) (m=grid.length，n=grid[0].length)
     *
     * @param grid
     * @return
     */
    public int minDays(int[][] grid) {
        //岛屿数量
        int count = 0;
        //节点访问数组
        boolean[][] visited = new boolean[grid.length][grid[0].length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    dfs(i, j, grid, visited);
                    count++;
                }
            }
        }

        //岛屿数量大于等于2或等于0，则不需要分离，陆地分离的最少天数为0
        if (count >= 2 || count == 0) {
            return 0;
        }

        //岛屿数量等于1，将其中一个陆地单元'1'修改为水单元'0'，dfs求此时岛屿数量
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    //将陆地单元'1'修改为水单元'0'
                    grid[i][j] = 0;

                    //将其中一个陆地单元'1'修改为水单元'0'之后，此时岛屿数量
                    count = 0;
                    visited = new boolean[grid.length][grid[0].length];

                    for (int m = 0; m < grid.length; m++) {
                        for (int n = 0; n < grid[0].length; n++) {
                            if (grid[m][n] == 1 && !visited[m][n]) {
                                dfs(m, n, grid, visited);
                                count++;
                            }
                        }
                    }

                    //将其中一个陆地单元'1'修改为水单元'0'之后，岛屿数量大于等于2或等于0，则陆地分离的最少天数为1
                    if (count >= 2 || count == 0) {
                        return 1;
                    }

                    //恢复修改为水单元'0'的陆地单元'1'
                    grid[i][j] = 1;
                }
            }
        }

        //陆地分离的最少天数只能为0、1、2
        return 2;
    }

    /**
     * 并查集+dfs，Tarjan(读作：ta young，https://www.cnblogs.com/nullzx/p/7968110.html)
     * 核心思想：通过Tarjan求图的割点，割点即为删除该节点和该节点所有相邻的边中导致图中连通分量的个数增加的节点
     * dfn[u]：节点u第一次访问的时间戳，即节点u在dfs的访问顺序
     * low[u]：节点u不通过当前节点u到父节点的边能够访问到的祖先节点v的最小dfn[v]
     * 1、求桥边：当前节点u访问未访问的邻接节点v，更新当前节点u的low[u]=min(low[u],low[v])，
     * 如果low[v]>dfn[u]，则邻接节点v只能通过节点u访问节点u的祖先节点，则节点u和节点v的边是桥边
     * 2、求割点：当前节点u访问未访问的邻接节点v，更新当前节点u的low[u]=min(low[u],low[v])，
     * 如果low[v]>=dfn[u]，并且节点u不是第一个访问到的节点(即不是图的根节点)，则邻接节点v只能通过节点u访问节点u的祖先节点，
     * 则节点u是割点；如果节点u是第一个访问到的节点(即图的根节点)，并且节点u能够访问到2个或2个以上子节点，则节点u是割点
     * 3、求强连通分量：遍历到当前节点u，当前节点u入栈，当前节点u的邻接节点dfs结束，如果dfn[u]==low[u]，
     * 则节点u为当前强连通分量dfs过程中的第一个遍历到的节点，栈中不等于节点u的节点出栈，是同一个强连通分量中的节点
     * <p>
     * 并查集求岛屿数量，岛屿数量大于等于2或等于0，则不需要分离，陆地分离的最少天数为0；
     * 岛屿数量等于1，通过Tarjan求图中割点，存在割点，将割点陆地单元'1'修改为水单元'0'，则可以使陆地分离，最少天数为1；
     * 不存在割点，将图中某个角的2个相邻节点陆地单元'1'修改为水单元'0'，则可以使陆地分离，最少天数为2
     * 注意：考虑只有一个陆地'1'，并且岛屿数量为1的特殊情况，这种情况图中不存在割点，但陆地分离的最少天数为1
     * 时间复杂度O(mn*α(mn)+mn)=O(mn)，空间复杂度O(mn) (m=grid.length，n=grid[0].length)
     * (Tarjan时间复杂度O(mn)，即图中节点的个数加上边的个数)
     * (find()和union()的时间复杂度为O(α(mn))，可视为常数O(1))
     *
     * @param grid
     * @return
     */
    public int minDays2(int[][] grid) {
        UnionFind unionFind = new UnionFind(grid);
        //当前节点的上下左右四个位置
        int[][] direction = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        //key：grid中节点的下标索引，value：图中节点的下标索引
        Map<Integer, Integer> map = new HashMap<>();
        //陆地'1'的个数，即无向图中节点的个数
        int count = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    map.put(i * grid[0].length + j, count);
                    count++;
                }
            }
        }

        //邻接表，无向图
        List<List<Integer>> edges = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            edges.add(new ArrayList<>());
        }

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    for (int k = 0; k < direction.length; k++) {
                        int x = i + direction[k][0];
                        int y = j + direction[k][1];

                        if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length || grid[x][y] != 1) {
                            continue;
                        }

                        //(i,j)和(x,y)合并为一个连通分量
                        unionFind.union(i * grid[0].length + j, x * grid[0].length + y);

                        //图中节点u
                        int u = map.get(i * grid[0].length + j);
                        //图中节点v
                        int v = map.get(x * grid[0].length + y);
                        //建图，u和v之间存在边
                        edges.get(u).add(v);
                    }
                }
            }
        }

        //岛屿数量大于等于2或等于0，则不需要分离，陆地分离的最少天数为0
        if (unionFind.count >= 2 || unionFind.count == 0) {
            return 0;
        }

        //注意：考虑只有一个陆地'1'，并且岛屿数量为1的特殊情况，这种情况图中不存在割点，但陆地分离的最少天数为1
        if (count == 1) {
            return 1;
        }

        //节点第一次访问的时间戳，即节点在dfs的访问顺序，同时也作为节点访问数组
        int[] dfn = new int[count];
        //节点不通过当前节点到父节点的边能够访问到的祖先节点的最小dfn
        int[] low = new int[count];

        //dfn、low初始化为-1，表示节点未访问
        for (int i = 0; i < count; i++) {
            dfn[i] = -1;
            low[i] = -1;
        }

        //存储图中割点
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            if (dfn[i] == -1) {
                //初始化节点i的父节点为-1，表示节点i不存在父节点
                dfs(i, -1, dfn, low, edges, list);
            }
        }

        //存在割点，将割点陆地单元'1'修改为水单元'0'，则可以使陆地分离，最少天数为1；
        //不存在割点，将图中某个角的2个相邻节点陆地单元'1'修改为水单元'0'，则可以使陆地分离，最少天数为2
        return list.isEmpty() ? 2 : 1;
    }

    private void dfs(int i, int j, int[][] grid, boolean[][] visited) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || visited[i][j] || grid[i][j] == 0) {
            return;
        }

        visited[i][j] = true;

        dfs(i + 1, j, grid, visited);
        dfs(i - 1, j, grid, visited);
        dfs(i, j + 1, grid, visited);
        dfs(i, j - 1, grid, visited);
    }

    /**
     * Tarjan求割点
     * 注意：本题无向图中两个节点之间最多只存在一条边，则遍历当前节点时保存父节点parent，避免重复遍历
     *
     * @param u      当前节点u
     * @param parent 节点u的父节点，即节点u是从哪个节点遍历到的，本题无向图中两个节点之间最多只存在一条边，保存父节点，避免重复遍历
     * @param dfn    节点u第一次访问的时间戳，即节点在dfs的访问顺序，同时也作为节点访问数组
     * @param low    节点u不通过当前节点u到父节点的边能够访问到的祖先节点v的最小dfn[v]
     * @param edges  邻接表，无向图
     * @param list   存储割点的集合
     */
    private void dfs(int u, int parent, int[] dfn, int[] low, List<List<Integer>> edges, List<Integer> list) {
        dfn[u] = index;
        low[u] = index;
        index++;

        //节点u能够访问到的子节点个数，而不是节点u的子节点个数，注意两者的区间
        int children = 0;

        //遍历节点u的邻接节点v
        for (int v : edges.get(u)) {
            //本题无向图中两个节点之间最多只存在一条边，保存父节点，避免重复遍历
            if (v == parent) {
                continue;
            }

            //邻接节点v未访问
            if (dfn[v] == -1) {
                children++;
                dfs(v, u, dfn, low, edges, list);
                //更新节点u不通过父节点parent能够访问到的祖先节点的最小dfn[u]，
                //因为节点v为节点u未访问的邻接节点，所以取low[u]和low[v]中的较小值
                low[u] = Math.min(low[u], low[v]);

                //节点u是第一个访问到的节点(即图的根节点)，并且节点u能够访问到2个或2个以上子节点，则节点u是割点
                if (parent == -1 && children >= 2) {
                    list.add(u);
                } else if (parent != -1 && low[v] >= dfn[u]) {
                    //邻接节点v只能通过节点u访问节点u的祖先节点，则节点u是割点
                    list.add(u);
                }
            } else {
                //邻接节点v已访问

                //更新节点u不通过父节点parent能够访问到的祖先节点的最小dfn[u]，
                //注意：这里是取low[u]和dfn[v]中的较小值
                low[u] = Math.min(low[u], dfn[v]);
            }
        }
    }

    /**
     * 并查集(不相交数据集)类
     * 用数组的形式表示图
     */
    private static class UnionFind {
        private int count;
        private final int[] parent;
        private final int[] weight;

        public UnionFind(int[][] grid) {
            count = 0;
            parent = new int[grid.length * grid[0].length];
            weight = new int[grid.length * grid[0].length];

            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    //当前节点为'1'时，才加入并查集中
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
