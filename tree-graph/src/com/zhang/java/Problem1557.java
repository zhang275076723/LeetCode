package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2025/1/22 08:11
 * @Author zsy
 * @Description 可以到达所有点的最少点数目 入度出度类比Problem331、Problem685、Problem1361
 * 给你一个 有向无环图 ， n 个节点编号为 0 到 n-1 ，以及一个边数组 edges ，
 * 其中 edges[i] = [fromi, toi] 表示一条从点  fromi 到点 toi 的有向边。
 * 找到最小的点集使得从这些点出发能到达图中所有点。题目保证解存在且唯一。
 * 你可以以任意顺序返回这些节点编号。
 * <p>
 * 输入：n = 6, edges = [[0,1],[0,2],[2,5],[3,4],[4,2]]
 * 输出：[0,3]
 * 解释：从单个节点出发无法到达所有节点。从 0 出发我们可以到达 [0,1,2,5] 。
 * 从 3 出发我们可以到达 [3,4,2,5] 。所以我们输出 [0,3] 。
 * <p>
 * 输入：n = 5, edges = [[0,1],[2,1],[3,1],[1,4],[2,4]]
 * 输出：[0,2,3]
 * 解释：注意到节点 0，3 和 2 无法从其他节点到达，所以我们必须将它们包含在结果点集中，这些点都能到达节点 1 和 4 。
 * <p>
 * 2 <= n <= 10^5
 * 1 <= edges.length <= min(10^5, n * (n - 1) / 2)
 * edges[i].length == 2
 * 0 <= fromi, toi < n
 * 所有点对 (fromi, toi) 互不相同。
 */
public class Problem1557 {
    public static void main(String[] args) {
        Problem1557 problem1557 = new Problem1557();
        int n = 6;
        List<List<Integer>> edges = new ArrayList<List<Integer>>() {{
            add(new ArrayList<Integer>() {{
                add(0);
                add(1);
            }});
            add(new ArrayList<Integer>() {{
                add(0);
                add(2);
            }});
            add(new ArrayList<Integer>() {{
                add(2);
                add(5);
            }});
            add(new ArrayList<Integer>() {{
                add(3);
                add(4);
            }});
            add(new ArrayList<Integer>() {{
                add(4);
                add(2);
            }});
        }};
        System.out.println(problem1557.findSmallestSetOfVertices(n, edges));
    }

    /**
     * 入度和出度
     * 入度为零的节点，才是最小的点集使得从这些点出发能到达图中所有点
     * 时间复杂度O(m+n)，空间复杂度O(n) (m=edges.length，即图中边的个数)
     *
     * @param n
     * @param edges
     * @return
     */
    public List<Integer> findSmallestSetOfVertices(int n, List<List<Integer>> edges) {
        //当前节点入度是否为零数组，不需要记录每个节点入度的个数，只需判断节点入度是否为零即可
        boolean[] inDegree = new boolean[n];

        //inDegree初始化，初始化为true，表示每个节点的入度都为零
        for (int i = 0; i < n; i++) {
            inDegree[i] = true;
        }

        for (List<Integer> edge : edges) {
            inDegree[edge.get(1)] = false;
        }

        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (inDegree[i]) {
                list.add(i);
            }
        }

        return list;
    }
}
