package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2025/1/29 08:16
 * @Author zsy
 * @Description 选择边来最大化树的得分 类比Problem337、Problem968、Problem979、Problem1373 dfs类比Problem104、Problem110、Problem111、Problem124、Problem298、Problem337、Problem543、Problem687、Problem968、Problem979、Problem1245、Problem1373、Problem2246
 * 给定一个 加权 树，由 n 个节点组成，从 0 到 n - 1。
 * 该树以节点 0 为 根，用大小为 n 的二维数组 edges 表示，
 * 其中 edges[i] = [pari, weighti] 表示节点 pari 是节点 i 的 父 节点，它们之间的边的权重等于 weighti。
 * 因为根结点 没有 父结点，所以有 edges[0] = [-1, -1]。
 * 从树中选择一些边，使所选的两条边都不 相邻，所选边的权值之 和 最大。
 * 返回所选边的 最大 和。
 * 注意:
 * 你可以 不选择 树中的任何边，在这种情况下权值和将为 0。
 * 如果树中的两条边 Edge1 和 Edge2 有一个 公共 节点，它们就是 相邻 的。
 * 换句话说，如果 Edge1连接节点 a 和 b, Edge2 连接节点 b 和 c，它们是相邻的。
 * <p>
 * 输入: edges = [[-1,-1],[0,5],[0,10],[2,6],[2,4]]
 * 输出: 11
 * 解释: 上面的图表显示了我们必须选择红色的边。
 * 总分是 5 + 6 = 11.
 * 可以看出，没有更好的分数可以获得。
 * <p>
 * 输入: edges = [[-1,-1],[0,5],[0,-6],[0,7]]
 * 输出: 7
 * 解释: 我们选择权值为 7 的边。
 * 注意，我们不能选择一条以上的边，因为所有的边都是彼此相邻的。
 * <p>
 * n == edges.length
 * 1 <= n <= 10^5
 * edges[i].length == 2
 * par0 == weight0 == -1
 * i >= 1 时 0 <= pari <= n - 1 。
 * pari != i
 * i >= 1 时 -10^6 <= weighti <= 10^6 。
 * edges 表示有效的树。
 */
public class Problem2378 {
    public static void main(String[] args) {
        Problem2378 problem2378 = new Problem2378();
        int[][] edges = {{-1, -1}, {0, 5}, {0, 10}, {2, 6}, {2, 4}};
        System.out.println(problem2378.maxScore(edges));
    }

    /**
     * dfs
     * 得到根节点所选的两条边都不相邻的最大权值和数组，不选根节点和父节点的边的最大权值和，即为树的所选的两条边都不相邻的最大权值和
     * arr[0]：不选当前节点和父节点的边，使所选的两条边都不相邻的最大权值和
     * arr[1]：选当前节点和父节点的边，使所选的两条边都不相邻的最大权值和
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param edges
     * @return
     */
    public long maxScore(int[][] edges) {
        //图中节点的个数
        int n = edges.length;
        //邻接表，有向图
        List<List<int[]>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        //i从1开始遍历，不需要考虑节点0的父节点
        for (int i = 1; i < n; i++) {
            int parent = edges[i][0];
            int weight = edges[i][1];
            graph.get(parent).add(new int[]{i, weight});
        }

        //使用long，避免int溢出
        long[] arr = dfs(0, graph);

        //根节点没有父节点，所以不选根节点和父节点的边的最大权值和，即为树的所选的两条边都不相邻的最大权值和
        return arr[0];
    }

    private long[] dfs(int u, List<List<int[]>> graph) {
        //不选当前节点u和父节点的边，使所选的两条边都不相邻的最大权值和
        long notSelected = 0;
        //选当前节点u和父节点的边，使所选的两条边都不相邻的最大权值和
        long selected = 0;
        //用于notSelected，不选当前节点u和父节点的边，则选择当前节点u和子节点的边中最大的vArr[1]
        long value = 0;

        for (int[] arr : graph.get(u)) {
            int v = arr[0];
            int weight = arr[1];

            long[] vArr = dfs(v, graph);

            //不选当前节点u和父节点的边，则选择当前节点u和子节点的边中最大的vArr[1]，选择当前节点u和剩余子节点的边的vArr[0]
            notSelected = notSelected + vArr[0];
            //选当前节点u和父节点的边，则当前节点u和子节点的边都不能选择，选择当前节点u和子节点的边的vArr[0]
            selected = selected + vArr[0];
            //通过value得到选择当前节点u和子节点的边中最大的vArr[1]
            value = Math.max(value, vArr[1] - vArr[0] + weight);
        }

        //notSelected中多加了一次vArr[0]，所以value中减去vArr[0]
        notSelected = notSelected + value;

        return new long[]{notSelected, selected};
    }
}