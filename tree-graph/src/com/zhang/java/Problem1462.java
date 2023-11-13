package com.zhang.java;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Date 2023/10/20 08:10
 * @Author zsy
 * @Description 课程表 IV 课程表类比Problem207、Problem210、Problem630 图中最短路径类比Problem399、Problem743、Problem787、Problem882、Problem1334、Problem1368、Problem1514、Problem1786、Problem1928、Problem1976、Problem2093、Dijkstra 拓扑排序类比 图类比
 * 你总共需要上 numCourses 门课，课程编号依次为 0 到 numCourses-1 。
 * 你会得到一个数组 prerequisite ，其中 prerequisites[i] = [ai, bi] 表示如果你想选 bi 课程，你 必须 先选 ai 课程。
 * 有的课会有直接的先修课程，比如如果想上课程 1 ，你必须先上课程 0 ，那么会以 [0,1] 数对的形式给出先修课程数对。
 * 先决条件也可以是 间接 的。如果课程 a 是课程 b 的先决条件，课程 b 是课程 c 的先决条件，那么课程 a 就是课程 c 的先决条件。
 * 你也得到一个数组 queries ，其中 queries[j] = [uj, vj]。
 * 对于第 j 个查询，您应该回答课程 uj 是否是课程 vj 的先决条件。
 * 返回一个布尔数组 answer ，其中 answer[j] 是第 j 个查询的答案。
 * <p>
 * 输入：numCourses = 2, prerequisites = [[1,0]], queries = [[0,1],[1,0]]
 * 输出：[false,true]
 * 解释：课程 0 不是课程 1 的先修课程，但课程 1 是课程 0 的先修课程。
 * <p>
 * 输入：numCourses = 2, prerequisites = [], queries = [[1,0],[0,1]]
 * 输出：[false,false]
 * 解释：没有先修课程对，所以每门课程之间是独立的。
 * <p>
 * 输入：numCourses = 3, prerequisites = [[1,2],[1,0],[2,0]], queries = [[1,0],[1,2]]
 * 输出：[true,true]
 * <p>
 * 2 <= numCourses <= 100
 * 0 <= prerequisites.length <= (numCourses * (numCourses - 1) / 2)
 * prerequisites[i].length == 2
 * 0 <= ai, bi <= n - 1
 * ai != bi
 * 每一对 [ai, bi] 都 不同
 * 先修课程图中没有环。
 * 1 <= queries.length <= 10^4
 * 0 <= ui, vi <= n - 1
 * ui != vi
 */
public class Problem1462 {
    public static void main(String[] args) {
        Problem1462 problem1462 = new Problem1462();
        int numCourses = 6;
        int[][] prerequisites = {{0, 3}, {1, 3}, {1, 4}, {2, 4}, {3, 5}, {4, 5}};
        int[][] queries = {{0, 5}, {1, 5}, {2, 5}, {5, 0}};
        System.out.println(problem1462.checkIfPrerequisite(numCourses, prerequisites, queries));
        System.out.println(problem1462.checkIfPrerequisite2(numCourses, prerequisites, queries));
        System.out.println(problem1462.checkIfPrerequisite3(numCourses, prerequisites, queries));
    }

    /**
     * dfs拓扑排序+动态规划
     * dp[i][j]：节点i到节点j是否可达
     * dp[i][j] = dp[i][j] || dp[k][j] (dfs拓扑排序中，存在节点i到节点k的边)
     * dfs过程中，存在节点u到节点v的边，则dp[u][v]为true，节点u通过节点v作为中间节点还能到达哪些节点i，
     * 来更新dp[u][i]，即u->v->i
     * 时间复杂度O(numCourses^2+m+n)，空间复杂度O(numCourses^2+n) (n=prerequisites.length，m=queries.length)
     * (dp数组的空间复杂度O(numCourses^2)，邻接表的空间复杂度O(n)，dfs栈的深度为O(numCourses))
     *
     * @param numCourses
     * @param prerequisites
     * @param queries
     * @return
     */
    public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites, int[][] queries) {
        //邻接表
        List<List<Integer>> edges = new ArrayList<>();
        //访问数组，因为图中不存在环，所以不需要使用int类型的访问数组在dfs过程中判断是否存在环
        boolean[] visited = new boolean[numCourses];
        //节点u到节点v是否可达数组
        boolean[][] dp = new boolean[numCourses][numCourses];

        for (int i = 0; i < numCourses; i++) {
            edges.add(new ArrayList<>());
        }

        for (int i = 0; i < prerequisites.length; i++) {
            edges.get(prerequisites[i][0]).add(prerequisites[i][1]);
        }

        for (int i = 0; i < numCourses; i++) {
            if (!visited[i]) {
                dfs(i, dp, edges, visited);
            }
        }

        List<Boolean> list = new ArrayList<>();

        for (int i = 0; i < queries.length; i++) {
            list.add(dp[queries[i][0]][queries[i][1]]);
        }

        return list;
    }

    /**
     * bfs拓扑排序+动态规划
     * dp[i][j]：节点i到节点j是否可达
     * dp[i][j] = dp[i][j] || dp[i][k] (bfs拓扑排序中，存在节点k到节点j的边)
     * 图中入度为0的节点入队，队列中节点出队，存在节点u到节点v的边，则dp[u][v]为true，哪些节点i通过节点u作为中间节点还能到达节点v，
     * 来更新dp[i][v]，即i->u->v
     * 时间复杂度O(numCourses^3+m+n)，空间复杂度O(numCourses^2+n) (n=prerequisites.length，m=queries.length)
     * (dp数组的空间复杂度O(numCourses^2)，邻接表的空间复杂度O(n))
     *
     * @param numCourses
     * @param prerequisites
     * @param queries
     * @return
     */
    public List<Boolean> checkIfPrerequisite2(int numCourses, int[][] prerequisites, int[][] queries) {
        //邻接表
        List<List<Integer>> edges = new ArrayList<>();
        //节点u到节点v是否可达数组
        boolean[][] dp = new boolean[numCourses][numCourses];
        //入度数组
        int[] inDegree = new int[numCourses];

        for (int i = 0; i < numCourses; i++) {
            edges.add(new ArrayList<>());
        }

        for (int i = 0; i < prerequisites.length; i++) {
            edges.get(prerequisites[i][0]).add(prerequisites[i][1]);
            inDegree[prerequisites[i][1]]++;
        }

        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            int u = queue.poll();

            //节点u的邻接节点v
            for (int v : edges.get(u)) {
                inDegree[v]--;

                if (inDegree[v] == 0) {
                    queue.offer(v);
                }

                //当前节点u到邻接节点v可达
                dp[u][v] = true;

                //哪些节点i通过节点u作为中间节点还能到达节点v
                for (int i = 0; i < numCourses; i++) {
                    dp[i][v] = dp[i][v] || dp[i][u];
                }
            }
        }

        List<Boolean> list = new ArrayList<>();

        for (int i = 0; i < queries.length; i++) {
            list.add(dp[queries[i][0]][queries[i][1]]);
        }

        return list;
    }

    /**
     * Floyd，计算任意两个节点之间的最短路径
     * 注意：Floyd可以处理带负权值的图，而Dijkstra不能处理带负权值的图
     * 时间复杂度O(numCourses^3)，空间复杂度O(numCourses^2)
     *
     * @param numCourses
     * @param prerequisites
     * @param queries
     * @return
     */
    public List<Boolean> checkIfPrerequisite3(int numCourses, int[][] prerequisites, int[][] queries) {
        //Floyd数组，dp[u][v]：节点u到节点v是否可达数组
        boolean[][] dp = new boolean[numCourses][numCourses];

        //dp数组初始化
        for (int i = 0; i < prerequisites.length; i++) {
            dp[prerequisites[i][0]][prerequisites[i][1]] = true;
        }

        for (int k = 0; k < numCourses; k++) {
            for (int i = 0; i < numCourses; i++) {
                for (int j = 0; j < numCourses; j++) {
                    dp[i][j] = dp[i][j] || (dp[i][k] && dp[k][j]);
                }
            }
        }

        List<Boolean> list = new ArrayList<>();

        for (int i = 0; i < queries.length; i++) {
            list.add(dp[queries[i][0]][queries[i][1]]);
        }

        return list;
    }

    private void dfs(int u, boolean[][] dp, List<List<Integer>> edges, boolean[] visited) {
        if (visited[u]) {
            return;
        }

        visited[u] = true;

        //遍历节点u的邻接节点v
        for (int v : edges.get(u)) {
            //当前节点u到邻接节点v可达
            dp[u][v] = true;

            dfs(v, dp, edges, visited);

            //节点u通过节点v作为中间节点还能到达哪些节点i
            for (int i = 0; i < dp.length; i++) {
                dp[u][i] = dp[u][i] || dp[v][i];
            }
        }
    }
}
