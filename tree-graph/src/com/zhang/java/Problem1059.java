package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/10/29 08:48
 * @Author zsy
 * @Description 从始点到终点的所有路径 拓扑排序类比 图类比
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
    public static void main(String[] args) {
        Problem1059 problem1059 = new Problem1059();
//        int n = 3;
//        int[][] edges = {{0, 1}, {0, 2}};
//        int source = 0;
//        int destination = 2;
//        int n = 4;
//        int[][] edges = {{0, 1}, {0, 3}, {1, 2}, {2, 1}};
//        int source = 0;
//        int destination = 3;
//        int n = 4;
//        int[][] edges = {{0, 1}, {0, 2}, {1, 3}, {2, 3}};
//        int source = 0;
//        int destination = 3;
        int n = 5;
        int[][] edges = {{0, 1}, {0, 2}, {1, 3}, {2, 3}, {3, 4}};
        int source = 0;
        int destination = 4;
        System.out.println(problem1059.leadsToDestination(n, edges, source, destination));
    }

    /**
     * dfs拓扑排序
     * 从节点source开始dfs，未访问的节点的访问标志位为0，从当前节点出发所有的路径终点不都为节点destination的访问标志位为1，
     * 从当前节点出发所有的路径终点都为节点destination的访问标志位为2
     * 时间复杂度O(m+n)，空间复杂度O(m+n) (m=edges.length，即图中边的数量)
     *
     * @param n
     * @param edges
     * @param source
     * @param destination
     * @return
     */
    public boolean leadsToDestination(int n, int[][] edges, int source, int destination) {
        //邻接表，有向图，当前图为稀疏图，不能使用邻接矩阵
        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < edges.length; i++) {
            graph.get(edges[i][0]).add(edges[i][1]);
        }

        //访问数组，未访问的节点的访问标志位为0，从当前节点出发所有的路径终点不都为节点destination的访问标志位为1，
        //从当前节点出发所有的路径终点都为节点destination的访问标志位为2
        int[] visited = new int[n];

        return dfs(source, destination, graph, visited);
    }

    private boolean dfs(int u, int destination, List<List<Integer>> graph, int[] visited) {
        //从当前节点出发所有的路径终点不都为节点destination，返回false
        if (visited[u] == 1) {
            return false;
        }

        //从当前节点出发所有的路径终点都为节点destination，返回true
        if (visited[u] == 2) {
            return true;
        }

        //当前节点没有邻接节点，即为终点，判断终点是否为节点destination
        if (graph.get(u).size() == 0) {
            return u == destination;
        }

        //设置从当前节点出发所有的路径终点不都为节点destination的访问标志位为1
        visited[u] = 1;

        //遍历节点u的邻接节点v
        for (int v : graph.get(u)) {
            //从邻接节点v出发所有的路径终点不都为节点destination，返回false
            if (!dfs(v, destination, graph, visited)) {
                return false;
            }
        }

        //设置从当前节点出发所有的路径终点都为节点destination的访问标志位为2
        visited[u] = 2;
        //遍历结束，则从当前节点出发所有的路径终点都为节点destination，返回true
        return true;
    }
}
