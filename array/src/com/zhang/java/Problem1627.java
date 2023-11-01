package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/9/17 08:35
 * @Author zsy
 * @Description 带阈值的图连通性 并查集类比Problem130、Problem200、Problem261、Problem305、Problem323、Problem399、Problem547、Problem684、Problem685、Problem695、Problem765、Problem785、Problem827、Problem886、Problem952、Problem1135、Problem1254、Problem1319、Problem1489、Problem1568、Problem1584、Problem1905、Problem1998、Problem2685
 * 有 n 座城市，编号从 1 到 n 。编号为 x 和 y 的两座城市直接连通的前提是：
 * x 和 y 的公因数中，至少有一个 严格大于 某个阈值 threshold 。
 * 更正式地说，如果存在整数 z ，且满足以下所有条件，则编号 x 和 y 的城市之间有一条道路：
 * x % z == 0
 * y % z == 0
 * z > threshold
 * 给你两个整数 n 和 threshold ，以及一个待查询数组，
 * 请你判断每个查询 queries[i] = [ai, bi] 指向的城市 ai 和 bi 是否连通（即，它们之间是否存在一条路径）。
 * 返回数组 answer ，其中answer.length == queries.length 。
 * 如果第 i 个查询中指向的城市 ai 和 bi 连通，则 answer[i] 为 true ；如果不连通，则 answer[i] 为 false 。
 * <p>
 * 输入：n = 6, threshold = 2, queries = [[1,4],[2,5],[3,6]]
 * 输出：[false,false,true]
 * 解释：每个数的因数如下：
 * 1:   1
 * 2:   1, 2
 * 3:   1, 3
 * 4:   1, 2, 4
 * 5:   1, 5
 * 6:   1, 2, 3, 6
 * 所有大于阈值的的因数已经加粗标识，只有城市 3 和 6 共享公约数 3 ，因此结果是：
 * [1,4]   1 与 4 不连通
 * [2,5]   2 与 5 不连通
 * [3,6]   3 与 6 连通，存在路径 3--6
 * <p>
 * 输入：n = 6, threshold = 0, queries = [[4,5],[3,4],[3,2],[2,6],[1,3]]
 * 输出：[true,true,true,true,true]
 * 解释：每个数的因数与上一个例子相同。但是，由于阈值为 0 ，所有的因数都大于阈值。因为所有的数字共享公因数 1 ，所以所有的城市都互相连通。
 * <p>
 * 输入：n = 5, threshold = 1, queries = [[4,5],[4,5],[3,2],[2,3],[3,4]]
 * 输出：[false,false,false,false,false]
 * 解释：只有城市 2 和 4 共享的公约数 2 严格大于阈值 1 ，所以只有这两座城市是连通的。
 * 注意，同一对节点 [x, y] 可以有多个查询，并且查询 [x，y] 等同于查询 [y，x] 。
 * <p>
 * 2 <= n <= 10^4
 * 0 <= threshold <= n
 * 1 <= queries.length <= 10^5
 * queries[i].length == 2
 * 1 <= ai, bi <= cities
 * ai != bi
 */
public class Problem1627 {
    public static void main(String[] args) {
        Problem1627 problem1627 = new Problem1627();
        int n = 6;
        int threshold = 2;
        int[][] queries = {{1, 4}, {2, 5}, {3, 6}};
        System.out.println(problem1627.areConnected(n, threshold, queries));
    }

    /**
     * 并查集+因子
     * [threshold+1,n/2]中每个因子i，都大于threshold，i和i*j属于同一个连通分量
     * 时间复杂度O(nlogn*α(n)+queries.length*α(n))，空间复杂度O(n) (find()和union()的时间复杂度为O(α(n))，可视为常数O(1))
     *
     * @param n
     * @param threshold
     * @param queries
     * @return
     */
    public List<Boolean> areConnected(int n, int threshold, int[][] queries) {
        //threshold为0，任意两个城市都至少有公因数1，大于0，则任意两个城市都连通
        if (threshold == 0) {
            List<Boolean> list = new ArrayList<>();

            for (int i = 0; i < queries.length; i++) {
                list.add(true);
            }

            return list;
        }

        List<Boolean> list = new ArrayList<>();
        //节点是从1开始，所以需要多加1
        UnionFind unionFind = new UnionFind(n + 1);

        //[threshold+1,n/2]中每个数i都作为大于threshold的因子，即i和i*j存在大于threshold的公因子
        for (int i = threshold + 1; i <= n / 2; i++) {
            for (int j = 2; i * j <= n; j++) {
                unionFind.union(i, i * j);
            }
        }

        for (int i = 0; i < queries.length; i++) {
            //queries[i][0]和queries[i][1]在同一个连通分量中，则两者连通
            if (unionFind.isConnected(queries[i][0], queries[i][1])) {
                list.add(true);
            } else {
                list.add(false);
            }
        }

        return list;
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
