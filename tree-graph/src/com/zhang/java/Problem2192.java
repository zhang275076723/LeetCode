package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/10/17 08:29
 * @Author zsy
 * @Description 有向无环图中一个节点的所有祖先 拓扑排序类比 图类比
 * 给你一个正整数 n ，它表示一个 有向无环图 中节点的数目，节点编号为 0 到 n - 1 （包括两者）。
 * 给你一个二维整数数组 edges ，其中 edges[i] = [fromi, toi] 表示图中一条从 fromi 到 toi 的单向边。
 * 请你返回一个数组 answer，其中 answer[i]是第 i 个节点的所有 祖先 ，这些祖先节点 升序 排序。
 * 如果 u 通过一系列边，能够到达 v ，那么我们称节点 u 是节点 v 的 祖先 节点。
 * <p>
 * 输入：n = 8, edgeList = [[0,3],[0,4],[1,3],[2,4],[2,7],[3,5],[3,6],[3,7],[4,6]]
 * 输出：[[],[],[],[0,1],[0,2],[0,1,3],[0,1,2,3,4],[0,1,2,3]]
 * 解释：
 * 上图为输入所对应的图。
 * - 节点 0 ，1 和 2 没有任何祖先。
 * - 节点 3 有 2 个祖先 0 和 1 。
 * - 节点 4 有 2 个祖先 0 和 2 。
 * - 节点 5 有 3 个祖先 0 ，1 和 3 。
 * - 节点 6 有 5 个祖先 0 ，1 ，2 ，3 和 4 。
 * - 节点 7 有 4 个祖先 0 ，1 ，2 和 3 。
 * <p>
 * 输入：n = 5, edgeList = [[0,1],[0,2],[0,3],[0,4],[1,2],[1,3],[1,4],[2,3],[2,4],[3,4]]
 * 输出：[[],[0],[0,1],[0,1,2],[0,1,2,3]]
 * 解释：
 * 上图为输入所对应的图。
 * - 节点 0 没有任何祖先。
 * - 节点 1 有 1 个祖先 0 。
 * - 节点 2 有 2 个祖先 0 和 1 。
 * - 节点 3 有 3 个祖先 0 ，1 和 2 。
 * - 节点 4 有 4 个祖先 0 ，1 ，2 和 3 。
 * <p>
 * 1 <= n <= 1000
 * 0 <= edges.length <= min(2000, n * (n - 1) / 2)
 * edges[i].length == 2
 * 0 <= fromi, toi <= n - 1
 * fromi != toi
 * 图中不会有重边。
 * 图是 有向 且 无环 的。
 */
public class Problem2192 {
    public static void main(String[] args) {
        Problem2192 problem2192 = new Problem2192();
        int n = 8;
        int[][] edges = {{0, 3}, {0, 4}, {1, 3}, {2, 4}, {2, 7}, {3, 5}, {3, 6}, {3, 7}, {4, 6}};
        System.out.println(problem2192.getAncestors(n, edges));
    }

    /**
     * bfs拓扑排序
     * 核心思想：拓扑排序能够保证如果存在节点u到节点v的边，则节点u在拓扑排序中出现在节点v之前，即当前节点所有的祖先节点先于当前节点被访问
     * 图中入度为0的节点入队，队列中节点出队，当前节点的邻接节点的入度减1，邻接节点入度为0的节点入队，
     * 当前节点即为邻接节点的祖先节点，则邻接节点的祖先节点为当前节点和当前节点的祖先节点
     * 时间复杂度O(m+n+n^2*logn)，空间复杂度O(n) (m：图中边的数量)
     * (n个节点排序O(nlogn)，排序n次，需要O(n^2*logn)) (如果使用邻接矩阵，空间复杂度O(n^2))
     *
     * @param n
     * @param edges
     * @return
     */
    public List<List<Integer>> getAncestors(int n, int[][] edges) {
        //存放每个节点的祖先节点，使用set去重
        List<Set<Integer>> setList = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            setList.add(new HashSet<>());
        }

        //邻接表
        List<List<Integer>> graph = new ArrayList<>();
        //入度数组
        int[] inDegree = new int[n];

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < edges.length; i++) {
            graph.get(edges[i][0]).add(edges[i][1]);
            inDegree[edges[i][1]]++;
        }

        //存放入度为0节点的队列
        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            //当前节点u
            int u = queue.poll();

            //邻接节点v
            for (int v : graph.get(u)) {
                //v的祖先节点为u和u的祖先节点
                setList.get(v).add(u);
                setList.get(v).addAll(new ArrayList<>(setList.get(u)));

                inDegree[v]--;

                if (inDegree[v] == 0) {
                    queue.offer(v);
                }
            }
        }

        List<List<Integer>> result = new ArrayList<>();

        //setList中每个节点的祖先节点由小到大排序
        for (int i = 0; i < n; i++) {
            List<Integer> list = new ArrayList<>(setList.get(i));
            //由小到大排序
            list.sort(new Comparator<Integer>() {
                @Override
                public int compare(Integer a, Integer b) {
                    return a - b;
                }
            });
            result.add(list);
        }

        return result;
    }
}
