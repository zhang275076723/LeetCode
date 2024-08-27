package com.zhang.java;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @Date 2024/11/16 08:36
 * @Author zsy
 * @Description 水资源分配优化 最小生成树类比Problem778、Problem1135、Problem1489、Problem1584、Problem1631、Prim
 * 村里面一共有 n 栋房子。
 * 我们希望通过建造水井和铺设管道来为所有房子供水。
 * 对于每个房子 i，我们有两种可选的供水方案：一种是直接在房子内建造水井，成本为 wells[i - 1] （注意 -1 ，因为 索引从0开始 ）；
 * 另一种是从另一口井铺设管道引水，数组 pipes 给出了在房子间铺设管道的成本，
 * 其中每个 pipes[j] = [house1j, house2j, costj] 代表用管道将 house1j 和 house2j连接在一起的成本。
 * 连接是双向的。
 * 请返回 为所有房子都供水的最低总成本 。
 * <p>
 * 输入：n = 3, wells = [1,2,2], pipes = [[1,2,1],[2,3,1]]
 * 输出：3
 * 解释：
 * 上图展示了铺设管道连接房屋的成本。
 * 最好的策略是在第一个房子里建造水井（成本为 1），然后将其他房子铺设管道连起来（成本为 2），所以总成本为 3。
 * <p>
 * 输入：n = 2, wells = [1,1], pipes = [[1,2,1]]
 * 输出：2
 * 解释：我们可以用以下三种方法中的一种来提供低成本的水:
 * 选项1:
 * 在1号房子里面建一口井，成本为1
 * 在房子2内建造井，成本为1
 * 总成本是2。
 * 选项2:
 * 在1号房子里面建一口井，成本为1
 * -花费1连接房子2和房子1。
 * 总成本是2。
 * 选项3:
 * 在房子2内建造井，成本为1
 * -花费1连接房子1和房子2。
 * 总成本是2。
 * 注意，我们可以用cost 1或cost 2连接房子1和房子2，但我们总是选择最便宜的选项。
 * <p>
 * 2 <= n <= 10^4
 * wells.length == n
 * 0 <= wells[i] <= 10^5
 * 1 <= pipes.length <= 10^4
 * pipes[j].length == 3
 * 1 <= house1j, house2j <= n
 * 0 <= costj <= 10^5
 * house1j != house2j
 */
public class Problem1168 {
    public static void main(String[] args) {
        Problem1168 problem1168 = new Problem1168();
        int n = 3;
        int[] wells = {1, 2, 2};
        int[][] pipes = {{1, 2, 1}, {2, 3, 1}};
//        int n = 2;
//        int[] wells = {1, 2};
//        int[][] pipes = {{1, 2, 1}};
        System.out.println(problem1168.minCostToSupplyWater(n, wells, pipes));
    }

    /**
     * Kruskal求最小生成树
     * 核心思想：如果在节点u建造水井，则假设节点0和节点u之间存在权值为wells[u]的边，所有节点(包括节点0)的最小生成树的权值即为最小成本
     * 图中边的权值由小到大排序，由小到大遍历排好序的边，当前边两个节点已经连通，即当前边作为最小生成树的边会成环，
     * 当前边不能作为最小生成树的边，直接进行下次循环；当前边两个节点不连通，则当前边能够作为最小生成树的边，当前边的两个节点相连，
     * 遍历结束，判断所有节点是否连通，即只有一个连通分量，则能得到最小生成树；否则不能得到最小生成树
     * 时间复杂度O(m+n+(m+n)*log(m+n)+(m+n)*α(m+n))=O((m+n)*log(m+n))，空间复杂度O(m+n) (n=wells.length，m=pipes.length)
     * (find()和union()的时间复杂度为O(α(m+n))，可视为常数O(1))
     *
     * @param n
     * @param wells
     * @param pipes
     * @return
     */
    public int minCostToSupplyWater(int n, int[] wells, int[][] pipes) {
        //arr[0]：当前边的节点u，arr[1]：当前边的节点v，arr[1]：当前边的权值，即联通节点u和节点v的成本
        List<int[]> list = new ArrayList<>();

        //假设虚拟节点0，如果在节点i建造水井，则虚拟节点0和节点i之间存在权值为wells[i-1]的边
        for (int i = 1; i <= n; i++) {
            list.add(new int[]{0, i, wells[i - 1]});
        }

        for (int[] arr : pipes) {
            list.add(arr);
        }

        //图中边的权值由小到大排序
        list.sort(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                return arr1[2] - arr2[2];
            }
        });

        //多申请1个长度，考虑假设的虚拟节点0
        UnionFind unionFind = new UnionFind(n + 1);
        //所有节点连通的最小成本
        int result = 0;

        for (int i = 0; i < list.size(); i++) {
            int[] arr = list.get(i);
            int u = arr[0];
            int v = arr[1];
            int weight = arr[2];

            if (unionFind.isConnected(u, v)) {
                continue;
            }

            unionFind.union(u, v);
            result = result + weight;
        }

        //图中所有节点连通，即只有一个连通分量，则能得到最小生成树，返回最小生成树的权值；否则不能得到最小生成树，返回-1
        return unionFind.count == 1 ? result : -1;
    }

    /**
     * 并查集
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
