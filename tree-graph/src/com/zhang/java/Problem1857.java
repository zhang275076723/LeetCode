package com.zhang.java;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Date 2023/10/16 08:53
 * @Author zsy
 * @Description 有向图中最大颜色值 拓扑排序类比 图类比
 * 给你一个 有向图 ，它含有 n 个节点和 m 条边。
 * 节点编号从 0 到 n - 1 。
 * 给你一个字符串 colors ，其中 colors[i] 是小写英文字母，表示图中第 i 个节点的 颜色 （下标从 0 开始）。
 * 同时给你一个二维数组 edges ，其中 edges[j] = [aj, bj] 表示从节点 aj 到节点 bj 有一条 有向边 。
 * 图中一条有效 路径 是一个点序列 x1 -> x2 -> x3 -> ... -> xk ，对于所有 1 <= i < k ，从 xi 到 xi+1 在图中有一条有向边。
 * 路径的 颜色值 是路径中 出现次数最多 颜色的节点数目。
 * 请你返回给定图中有效路径里面的 最大颜色值 。
 * 如果图中含有环，请返回 -1 。
 * <p>
 * 输入：colors = "abaca", edges = [[0,1],[0,2],[2,3],[3,4]]
 * 输出：3
 * 解释：路径 0 -> 2 -> 3 -> 4 含有 3 个颜色为 "a" 的节点（上图中的红色节点）。
 * <p>
 * 输入：colors = "a", edges = [[0,0]]
 * 输出：-1
 * 解释：从 0 到 0 有一个环。
 * <p>
 * n == colors.length
 * m == edges.length
 * 1 <= n <= 10^5
 * 0 <= m <= 10^5
 * colors 只含有小写英文字母。
 * 0 <= aj, bj < n
 */
public class Problem1857 {
    public static void main(String[] args) {
        Problem1857 problem1857 = new Problem1857();
        String colors = "abaca";
        int[][] edges = {{0, 1}, {0, 2}, {2, 3}, {3, 4}};
        System.out.println(problem1857.largestPathValue(colors, edges));
    }

    /**
     * bfs拓扑排序+动态规划 (使用邻接矩阵空间溢出，要使用邻接表)
     * dp[i][j]：节点i作为结尾节点的路径中，颜色'a'+j出现的最大次数
     * dp[i][j] = max(dp[i][j],dp[u][j]+1) (存在节点u到节点i的边，并且节点i的颜色为j)
     * dp[i][j] = max(dp[i][j],dp[u][j])   (存在节点u到节点i的边，并且节点i的颜色不为j)
     * 图中入度为0的节点入队，队列中节点出队，存在节点u到节点v的边，则更新dp[v][j]，
     * 遍历结束判断是否能够遍历到所有的节点，如果能遍历到所有的节点，则存在拓扑排序，
     * 返回以某个节点作为结尾节点的路径中，出现次数最多的颜色出现的次数；否则不存在拓扑排序，即存在环，返回-1
     * 时间复杂度O(|Σ|*(m+n))=O(m+n)，空间复杂度O(m+n) (|Σ|=26，只包含小写英文字母)
     * (n=colors.length()，m=edges.length，n即为图中节点的数量，m即为图中边的数量) (如果使用邻接矩阵，空间复杂度O(m+n))
     *
     * @param colors
     * @param edges
     * @return
     */
    public int largestPathValue(String colors, int[][] edges) {
        //图中节点的个数
        int n = colors.length();
        //邻接表
        List<List<Integer>> graph = new ArrayList<>();
        //入度数组
        int[] inDegree = new int[n];

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];

            graph.get(u).add(v);
            inDegree[v]++;
        }

        //存放入度为0节点的队列
        Queue<Integer> queue = new LinkedList<>();
        //bfs能够遍历到的入度为0节点个数
        int count = 0;
        //只含有26个小写英文字母
        int[][] dp = new int[n][26];

        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
                //dp初始化，节点i作为结尾节点的路径中，颜色colors[i]出现的最大次数为1
                dp[i][colors.charAt(i) - 'a'] = 1;
            }
        }

        while (!queue.isEmpty()) {
            int u = queue.poll();
            count++;

            //遍历节点u的邻接节点v
            for (int v : graph.get(u)) {
                inDegree[v]--;

                if (inDegree[v] == 0) {
                    queue.offer(v);
                }

                //更新节点v作为结尾节点的路径中，颜色'a'+j出现的最大次数
                for (int j = 0; j < 26; j++) {
                    dp[v][j] = Math.max(dp[v][j], dp[u][j] + ((colors.charAt(v) - 'a') == j ? 1 : 0));
                }
            }
        }

        //以某个节点作为结尾节点的路径中，出现次数最多的颜色出现的次数
        int max = 1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 26; j++) {
                max = Math.max(max, dp[i][j]);
            }
        }

        //能遍历到所有的节点，则存在拓扑排序，返回max；否则不存在拓扑排序，即存在环，返回-1
        return count == n ? max : -1;
    }
}
