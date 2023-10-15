package com.zhang.java;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Date 2023/10/29 08:48
 * @Author zsy
 * @Description 从始点到终点的所有路径 图类比
 * 给定有向图的边 edges，以及该图的始点 source 和目标终点 destination，
 * 确定从始点 source 出发的所有路径是否最终结束于目标终点 destination，即：
 * 从始点 source 到目标终点 destination 存在至少一条路径
 * 如果存在从始点 source 到没有出边的节点的路径，则该节点就是路径终点。
 * 从始点source到目标终点 destination 可能路径数是有限数字
 * 当从始点 source 出发的所有路径都可以到达目标终点 destination 时返回 true，否则返回 false。
 * <p>
 * 输入：n = 3, edges = [[0,1],[0,2]], source = 0, destination = 2
 * 输出：false
 * 说明：节点 1 和节点 2 都可以到达，但也会卡在那里。
 * <p>
 * 输入：n = 4, edges = [[0,1],[0,3],[1,2],[2,1]], source = 0, destination = 3
 * 输出：false
 * 说明：有两种可能：在节点 3 处结束，或是在节点 1 和节点 2 之间无限循环。
 * <p>
 * 输入：n = 4, edges = [[0,1],[0,2],[1,3],[2,3]], source = 0, destination = 3
 * 输出：true
 * <p>
 * 1 <= n <= 10^4
 * 0 <= edges.length <= 10^4
 * edges.length == 2
 * 0 <= ai, bi <= n - 1
 * 0 <= source <= n - 1
 * 0 <= destination <= n - 1
 * 给定的图中可能带有自环和平行边。
 */
public class Problem1059 {
    /**
     * dfs中从节点source出发所有的路径终点是否都为节点destination
     */
    private boolean flag = true;

    public static void main(String[] args) {
        Problem1059 problem1059 = new Problem1059();
//        int n = 3;
//        int[][] edges = {{0, 1}, {0, 2}};
//        int source = 0;
//        int destination = 2;
        int n = 4;
//        int[][] edges = {{0, 1}, {0, 3}, {1, 2}, {2, 1}};
        int[][] edges = {{0, 1}, {0, 2}, {1, 3}, {2, 3}};
        int source = 0;
        int destination = 3;
        System.out.println(problem1059.leadsToDestination(n, edges, source, destination));
        System.out.println(problem1059.leadsToDestination2(n, edges, source, destination));
    }

    /**
     * dfs
     * 时间复杂度O(m+n)，空间复杂度O(m+n) (m=edges.length，即图中边的数量，如果使用邻接矩阵，空间复杂度O(n^2))
     *
     * @param n
     * @param edges
     * @param source
     * @param destination
     * @return
     */
    public boolean leadsToDestination(int n, int[][] edges, int source, int destination) {
        //邻接表
        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < edges.length; i++) {
            graph.get(edges[i][0]).add(edges[i][1]);
        }

        dfs(source, destination, graph, new boolean[n]);

        return flag;
    }

    /**
     * bfs
     * 时间复杂度O(m+n)，空间复杂度O(m+n) (m=edges.length，即图中边的数量，如果使用邻接矩阵，空间复杂度O(n^2))
     *
     * @param n
     * @param edges
     * @param source
     * @param destination
     * @return
     */
    public boolean leadsToDestination2(int n, int[][] edges, int source, int destination) {
        //邻接表
        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < edges.length; i++) {
            graph.get(edges[i][0]).add(edges[i][1]);
        }

        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[n];
        queue.offer(source);

        while (!queue.isEmpty()) {
            //当前节点u
            int u = queue.poll();

            //节点u已经访问过，则存在环，不存在路径终点为destination的情况，flag置为false，直接返回
            if (visited[u]) {
                return false;
            }

            if (u == destination) {
                //节点destination不是路径终点，返回false
                if (graph.get(u).size() != 0) {
                    return false;
                } else {
                    //节点destination是路径终点，直接进行下次循环
                    continue;
                }
            } else {
                //节点u是路径终点，并且节点u不是节点destination，返回false
                if (graph.get(u).size() == 0) {
                    return false;
                }
            }

            visited[u] = true;

            //节点u的邻接节点v
            for (int v : graph.get(u)) {
                queue.offer(v);
            }
        }

        return true;
    }

    private void dfs(int u, int destination, List<List<Integer>> graph, boolean[] visited) {
        //已经存在从节点source出发的路径终点不是节点destination的情况，直接返回
        if (!flag) {
            return;
        }

        //节点u已经访问过，则存在环，不存在路径终点为destination的情况，flag置为false，直接返回
        if (visited[u]) {
            flag = false;
            return;
        }

        if (u == destination) {
            //节点destination不是路径终点，flag置为false，直接返回
            if (graph.get(u).size() != 0) {
                flag = false;
            }
            return;
        } else {
            //节点u是路径终点，并且节点u不是节点destination，flag置为false，直接返回
            if (graph.get(u).size() == 0) {
                flag = false;
                return;
            }
        }

        visited[u] = true;

        //节点u的邻接节点v
        for (int v : graph.get(u)) {
            dfs(v, destination, graph, visited);

            if (!flag) {
                return;
            }
        }
    }
}
