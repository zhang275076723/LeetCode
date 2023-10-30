package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/11/3 08:51
 * @Author zsy
 * @Description 找到最小生成树里的关键边和伪关键边 Tarjan类比Problem1192、Problem1568 最小生成树类比 并查集类比 图类比
 * 给你一个 n 个点的带权无向连通图，节点编号为 0 到 n-1 ，同时还有一个数组 edges ，
 * 其中 edges[i] = [fromi, toi, weighti] 表示在 fromi 和 toi 节点之间有一条带权无向边。
 * 最小生成树 (MST) 是给定图中边的一个子集，它连接了所有节点且没有环，而且这些边的权值和最小。
 * 请你找到给定图中最小生成树的所有关键边和伪关键边。如果从图中删去某条边，会导致最小生成树的权值和增加，
 * 那么我们就说它是一条关键边。伪关键边则是可能会出现在某些最小生成树中但不会出现在所有最小生成树中的边。
 * 请注意，你可以分别以任意顺序返回关键边的下标和伪关键边的下标。
 * <p>
 * 输入：n = 5, edges = [[0,1,1],[1,2,1],[2,3,2],[0,3,2],[0,4,3],[3,4,3],[1,4,6]]
 * 输出：[[0,1],[2,3,4,5]]
 * 解释：上图描述了给定图。
 * 下图是所有的最小生成树。
 * 注意到第 0 条边和第 1 条边出现在了所有最小生成树中，所以它们是关键边，我们将这两个下标作为输出的第一个列表。
 * 边 2，3，4 和 5 是所有 MST 的剩余边，所以它们是伪关键边。我们将它们作为输出的第二个列表。
 * <p>
 * 输入：n = 4, edges = [[0,1,1],[1,2,1],[2,3,1],[0,3,1]]
 * 输出：[[],[0,1,2,3]]
 * 解释：可以观察到 4 条边都有相同的权值，任选它们中的 3 条可以形成一棵 MST 。所以 4 条边都是伪关键边。
 * <p>
 * 2 <= n <= 100
 * 1 <= edges.length <= min(200, n * (n - 1) / 2)
 * edges[i].length == 3
 * 0 <= fromi < toi < n
 * 1 <= weighti <= 1000
 * 所有 (fromi, toi) 数对都是互不相同的。
 */
public class Problem1489 {
    /**
     * dfs过程中第一次访问当前节点的dfn和low的初始值
     * 注意：多次图求桥边，所以index需要多次初始化为0
     */
    private int index;

    public static void main(String[] args) {
        Problem1489 problem1489 = new Problem1489();
//        int n = 5;
//        int[][] edges = {{0, 1, 1}, {1, 2, 1}, {2, 3, 2}, {0, 3, 2}, {0, 4, 3}, {3, 4, 3}, {1, 4, 6}};
        int n = 14;
        int[][] edges = {{0, 1, 13}, {0, 2, 6}, {2, 3, 13}, {3, 4, 4}, {0, 5, 11}, {4, 6, 14},
                {4, 7, 8}, {2, 8, 6}, {4, 9, 6}, {7, 10, 4}, {5, 11, 3}, {6, 12, 7}, {12, 13, 9},
                {7, 13, 2}, {5, 13, 10}, {0, 6, 4}, {2, 7, 3}, {0, 7, 8}, {1, 12, 9}, {10, 12, 11},
                {1, 2, 7}, {1, 3, 10}, {3, 10, 6}, {6, 10, 4}, {4, 8, 5}, {1, 13, 4}, {11, 13, 8},
                {2, 12, 10}, {5, 8, 1}, {3, 7, 6}, {7, 12, 12}, {1, 7, 9}, {5, 9, 1}, {2, 13, 10},
                {10, 11, 4}, {3, 5, 10}, {6, 11, 14}, {5, 12, 3}, {0, 8, 13}, {8, 9, 1}, {3, 6, 8},
                {0, 3, 4}, {2, 9, 6}, {0, 11, 4}, {2, 5, 14}, {4, 11, 2}, {7, 11, 11}, {1, 11, 6},
                {2, 10, 12}, {0, 13, 4}, {3, 9, 9}, {4, 12, 3}, {6, 7, 10}, {6, 8, 13}, {9, 11, 3},
                {1, 6, 2}, {2, 4, 12}, {0, 10, 3}, {3, 12, 1}, {3, 8, 12}, {1, 8, 6}, {8, 13, 2},
                {10, 13, 12}, {9, 13, 11}, {2, 11, 14}, {5, 10, 9}, {5, 6, 10}, {2, 6, 9}, {4, 10, 7},
                {3, 13, 10}, {4, 13, 3}, {3, 11, 9}, {7, 9, 14}, {6, 9, 5}, {1, 5, 12}, {4, 5, 3},
                {11, 12, 3}, {0, 4, 8}, {5, 7, 8}, {9, 12, 13}, {8, 12, 12}, {1, 10, 6}, {1, 9, 9},
                {7, 8, 9}, {9, 10, 13}, {8, 11, 3}, {6, 13, 7}, {0, 12, 10}, {1, 4, 8}, {8, 10, 2}};
        System.out.println(problem1489.findCriticalAndPseudoCriticalEdges(n, edges));
        System.out.println(problem1489.findCriticalAndPseudoCriticalEdges2(n, edges));
    }

    /**
     * Kruskal求最小生成树
     * 图中边的权值由小到大排序，由小到大遍历排好序的边，当前边两个节点已经连通，即当前边作为最小生成树的边会成环，
     * 当前边不能作为最小生成树的边，直接进行下次循环；当前边两个节点不连通，则当前边能够作为最小生成树的边，
     * 当前边的两个节点相连，遍历结束，判断所有节点是否连通，即只有一个连通分量，则能得到最小生成树；否则不能得到最小生成树
     * 关键边：删除当前边，通过Kruskal无法得到最小生成树，或得到的最小生成树的权值大于原先图的最小生成树的权值
     * 伪关键边：当前边不是关键边，并且以当前边作为最小生成树的起始边，通过Kruskal得到最小生成树的权值等于原先图的最小生成树的权值
     * 注意：要先判断当前边是否是关键边，再判断当前边是否是伪关键边
     * 时间复杂度O(mlogm+m^2*α(n))=O(mlogm+m^2)=O(m^2)，空间复杂度O(m+n) (m=edges.length，即图中边的个数，n为图中节点的个数)
     * (存储边空间复杂度O(m)，并查集空间复杂度O(n)) (find()和union()的时间复杂度为O(α(n))，可视为常数O(1))
     *
     * @param n
     * @param edges
     * @return
     */
    public List<List<Integer>> findCriticalAndPseudoCriticalEdges(int n, int[][] edges) {
        //图中边的个数
        int m = edges.length;
        //newEdges[i][0]：当前边的节点u，newEdges[i][1]：当前边的节点v，newEdges[i][2]：当前边的权值，
        //newEdges[i][3]：当前边的下标索引，即当前边是edges中的第几条边
        int[][] newEdges = new int[m][4];

        for (int i = 0; i < m; i++) {
            newEdges[i] = new int[]{edges[i][0], edges[i][1], edges[i][2], i};
        }

        //图中边的权值由小到大排序
        heapSort(newEdges);

        UnionFind unionFind = new UnionFind(n);
        //图的最小生成树的权值
        int minWeight = 0;

        //通过Kruskal得到图的最小生成树的权值
        for (int i = 0; i < m; i++) {
            int u = newEdges[i][0];
            int v = newEdges[i][1];
            int weight = newEdges[i][2];

            if (unionFind.isConnected(u, v)) {
                continue;
            }

            unionFind.union(u, v);
            minWeight = minWeight + weight;
        }

        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            result.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            unionFind = new UnionFind(n);
            //删除当前边i，最小生成树的权值
            int curWeight1 = 0;

            for (int j = 0; j < m; j++) {
                int u = newEdges[j][0];
                int v = newEdges[j][1];
                int weight = newEdges[j][2];

                if (i == j || unionFind.isConnected(u, v)) {
                    continue;
                }

                unionFind.union(u, v);
                curWeight1 = curWeight1 + weight;
            }

            //通过Kruskal无法得到最小生成树，或得到的最小生成树的权值大于原先图的最小生成树的权值，则当前边i为关键边
            if (unionFind.count > 1 || (unionFind.count == 1 && curWeight1 > minWeight)) {
                //当前边i是edges中的第newEdges[i][3]条边
                result.get(0).add(newEdges[i][3]);
                continue;
            }

            unionFind = new UnionFind(n);
            //当前边i作为最小生成树的起始边
            unionFind.union(newEdges[i][0], newEdges[i][1]);
            //当前边i作为最小生成树的起始边，得到的最小生成树的权值
            int curWeight2 = newEdges[i][2];

            for (int j = 0; j < m; j++) {
                int u = newEdges[j][0];
                int v = newEdges[j][1];
                int weight = newEdges[j][2];

                if (i == j || unionFind.isConnected(u, v)) {
                    continue;
                }

                unionFind.union(u, v);
                curWeight2 = curWeight2 + weight;
            }

            //当前边不是关键边，并且以当前边作为最小生成树的起始边，通过Kruskal得到最小生成树的权值等于原先图的最小生成树的权值，
            //则当前边为伪关键边
            if (curWeight2 == minWeight) {
                //当前边i是edges中的第newEdges[i][3]条边
                result.get(1).add(newEdges[i][3]);
            }
        }

        return result;
    }

    /**
     * dfs，Tarjan(读作：ta young，https://www.cnblogs.com/nullzx/p/7968110.html)
     * 核心思想：通过Tarjan求图的桥边，桥边即为删除连通图中的某条边导致连通图不连通的边，即为关键边
     * dfn[u]：节点u第一次访问的时间戳，即节点u在dfs的访问顺序
     * low[u]：节点u不通过当前节点u到父节点的边能够访问到的祖先节点v的最小dfn[v]
     * 1、判断连通分量：当前节点u的邻接节点全部遍历完，如果dfn[u]==low[u]，则节点u是连通分量中时间戳最小的节点，
     * 即第一个访问的节点，栈内大于节点u的节点出栈，都是同一个连通分量中的节点
     * 2、判断桥边：当前节点u访问未访问的邻接节点v，更新当前节点u的low[u]=min(low[u],low[v])，
     * 如果low[v]>dfn[u]，则邻接节点v只能通过访问节点u访问节点u的祖先节点，则节点u和节点v的边是桥边
     * <p>
     * 图中边的权值由小到大排序，由小到大选择权值相等的边，这些权值相等的边对应的节点构成的图存在以下3种情况的边：
     * 1、桥边：删除连通图中的某条边导致连通图不连通的边，即flag为1的边是关键边
     * 2、非桥边：删除连通图中的某条边不会导致连通图不连通的边，即flag为0的边是伪关键边
     * 3、自环边：从一个节点指向本身节点的边，即flag为2的边既不是关键边也不是伪关键边
     * 本次权值相等的边的两个节点连接，作为同一个连通分量中的节点，下次循环选择权值更大的边时本次本次权值相等的边的节点作为一个节点
     * 时间复杂度O(mlogm+m*α(n)+n)，空间复杂度O(m+n) (m=edges.length，即图中边的个数，n为图中节点的个数)
     * (存储边空间复杂度O(m)，并查集空间复杂度O(n)) (find()和union()的时间复杂度为O(α(n))，可视为常数O(1))
     *
     * @param n
     * @param edges
     * @return
     */
    public List<List<Integer>> findCriticalAndPseudoCriticalEdges2(int n, int[][] edges) {
        //图中边的个数
        int m = edges.length;
        //newEdges[i][0]：当前边的节点u，newEdges[i][1]：当前边的节点v，newEdges[i][2]：当前边的权值，
        //newEdges[i][3]：当前边的下标索引，即当前边是edges中的第几条边
        int[][] newEdges = new int[m][4];

        for (int i = 0; i < m; i++) {
            newEdges[i] = new int[]{edges[i][0], edges[i][1], edges[i][2], i};
        }

        //图中边的权值由小到大排序
        heapSort(newEdges);

        //图中m条边的状态数组，flag[i]=0：edges中第i条边为非桥边，flag[i]=1：edges中第i条边为桥边，flag[i]=2：edges中第i条边为自环边
        int[] flag = new int[m];
        UnionFind unionFind = new UnionFind(n);

        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            result.add(new ArrayList<>());
        }

        //当前遍历到由小到大的边
        int i = 0;

        //由小到大选择权值相等的边，这些权值相等的边对应的节点构成的图
        while (i < m) {
            //和newEdges[i][2]权值相等的边的最后一条边，即第i条边到第j条边的权值相同
            int j = i;

            while (j + 1 < m && newEdges[i][2] == newEdges[j + 1][2]) {
                j++;
            }

            //这些权值相等的边对应的节点构成的图的节点个数
            int count = 0;
            //key：节点所在连通分量的根节点，value：图中节点的编号(同一个连通分量中的节点作为一个节点)
            Map<Integer, Integer> map = new HashMap<>();

            //第i条边到第j条边的权值相同，得到这些权值相等的边对应的节点
            for (int k = i; k <= j; k++) {
                //节点u的根节点
                int rootU = unionFind.find(newEdges[k][0]);
                //节点v的根节点
                int rootV = unionFind.find(newEdges[k][1]);

                //节点u和节点v在同一个连通分量中，即节点u和节点v是一个节点，是一条自环边，flag设置为2，
                //edges中第newEdges[k][3]条边为既不是关键边也不是伪关键边
                if (rootU == rootV) {
                    flag[newEdges[k][3]] = 2;
                } else {
                    //节点u和节点v不在同一个连通分量中，作为图中的节点，加入map
                    if (!map.containsKey(rootU)) {
                        map.put(rootU, count);
                        count++;
                    }
                    if (!map.containsKey(rootV)) {
                        map.put(rootV, count);
                        count++;
                    }
                }
            }

            //邻接表，arr[0]：节点的邻接节点，arr[1]：节点和邻接节点的边的下标索引，即当前边是edges中的第几条边
            List<List<int[]>> graph = new ArrayList<>();

            for (int k = 0; k < count; k++) {
                graph.add(new ArrayList<>());
            }

            //第i条边到第j条边的权值相同，这些权值相等的边对应的节点构成图
            for (int k = i; k <= j; k++) {
                //节点u的根节点
                int rootU = unionFind.find(newEdges[k][0]);
                //节点v的根节点
                int rootV = unionFind.find(newEdges[k][1]);

                //graph存储的是节点的邻接节点和边的权值，不存储自环边
                if (rootU != rootV) {
                    int u = map.get(rootU);
                    int v = map.get(rootV);

                    graph.get(u).add(new int[]{v, newEdges[k][3]});
                    graph.get(v).add(new int[]{u, newEdges[k][3]});
                }
            }

            //节点第一次访问的时间戳，即节点在dfs的访问顺序，同时也作为节点访问数组
            int[] dfn = new int[count];
            //节点不通过当前节点到父节点的边能够访问到的祖先节点的最小dfn
            int[] low = new int[count];
            //每次都得到新的图，所以每次dfn和low的初始值都要从0开始
            index = 0;

            //dfn、low初始化为-1，表示节点未访问
            for (int k = 0; k < count; k++) {
                dfn[k] = -1;
                low[k] = -1;
            }

            //Tarjan，得到图graph的桥边，保存到flag数组中
            for (int k = 0; k < count; k++) {
                if (dfn[k] == -1) {
                    dfs(k, -1, dfn, low, graph, flag);
                }
            }

            //本次权值相等的边的两个节点连接，作为同一个连通分量中的节点，下次循环选择权值更大的边时本次本次权值相等的边的节点作为一个节点
            for (int k = i; k <= j; k++) {
                //当前边的节点u
                int u = newEdges[k][0];
                //当前边的节点v
                int v = newEdges[k][1];
                //连接节点u和节点v
                unionFind.union(u, v);
            }

            //遍历完本次权值相等的边之后，i指向下一个权值更大的边的下标索引
            i = j + 1;
        }

        for (i = 0; i < m; i++) {
            //flag为0的边，即为伪关键边
            if (flag[i] == 0) {
                result.get(1).add(i);
            } else if (flag[i] == 1) {
                //flag为1的边，即为关键边
                result.get(0).add(i);
            }
        }

        return result;
    }

    /**
     * Tarjan求桥边
     * 注意：本题权值相等的边对应的节点构成图中存在重复的连接，即两个节点之间存在多条边，
     * 遍历当前节点时保存父节点到当前节点边是edges中的第几条边，即可避免重复遍历
     *
     * @param u         当前节点u
     * @param edgeIndex 节点u的父节点和节点u的边的下标索引，即当前边是edges中的第几条边
     * @param dfn       节点u第一次访问的时间戳，即节点在dfs的访问顺序，同时也作为节点访问数组
     * @param low       节点u不通过当前节点u到父节点的边能够访问到的祖先节点v的最小dfn[v]
     * @param graph     邻接表，arr[0]：节点的邻接节点，arr[1]：节点和邻接节点的边的下标索引，即当前边是edges中的第几条边
     * @param flag      图中m条边的状态数组
     */
    private void dfs(int u, int edgeIndex, int[] dfn, int[] low, List<List<int[]>> graph, int[] flag) {
        dfn[u] = index;
        low[u] = index;
        index++;

        //遍历节点u的邻接节点v
        for (int[] arr : graph.get(u)) {
            //邻接节点v
            int v = arr[0];
            //节点u和邻接节点v的边的下标索引，即当前边是edges中的第几条边
            int index = arr[1];

            //无向图中存在重复的连接，保存父节点到当前节点边是edges中的第几条边，即可避免重复遍历
            if (index == edgeIndex) {
                continue;
            }

            //邻接节点v未访问
            if (dfn[v] == -1) {
                dfs(v, index, dfn, low, graph, flag);
                //更新节点u不通过当前节点u到父节点的边能够访问到的祖先节点的最小dfn[u]，
                //因为节点v为节点u未访问的邻接节点，所以取low[u]和low[v]中的较小值
                low[u] = Math.min(low[u], low[v]);

                //邻接节点v只能通过节点u访问节点u的祖先节点，则节点u和节点v的边是桥边，flag设置为1，
                //edges中第index条边为关键边
                if (low[v] > dfn[u]) {
                    flag[index] = 1;
                }
            } else {
                //邻接节点v已访问

                //更新节点u不通过父节点parent能够访问到的祖先节点的最小dfn[u]，
                //注意：这里是取low[u]和dfn[v]中的较小值
                low[u] = Math.min(low[u], dfn[v]);
            }
        }
    }

    private void heapSort(int[][] arr) {
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }

        for (int i = arr.length - 1; i > 0; i--) {
            int[] temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, 0, i);
        }
    }

    private void heapify(int[][] arr, int i, int heapSize) {
        int index = i;
        int leftIndex = i * 2 + 1;
        int rightIndex = i * 2 + 2;

        if (leftIndex < heapSize && arr[leftIndex][2] > arr[index][2]) {
            index = leftIndex;
        }

        if (rightIndex < heapSize && arr[rightIndex][2] > arr[index][2]) {
            index = rightIndex;
        }

        if (index != i) {
            int[] temp = arr[i];
            arr[i] = arr[index];
            arr[index] = temp;

            heapify(arr, index, heapSize);
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

        public UnionFind(int n) {
            count = n;
            parent = new int[n];
            weight = new int[n];

            for (int i = 0; i < n; i++) {
                parent[i] = i;
                weight[i] = 1;
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
