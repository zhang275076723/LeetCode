package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/11/4 08:33
 * @Author zsy
 * @Description 查找集群内的关键连接 Tarjan类比Problem1489、Problem1568
 * 力扣数据中心有 n 台服务器，分别按从 0 到 n-1 的方式进行了编号。
 * 它们之间以 服务器到服务器 的形式相互连接组成了一个内部集群，连接是无向的。
 * 用  connections 表示集群网络，connections[i] = [a, b] 表示服务器 a 和 b 之间形成连接。
 * 任何服务器都可以直接或者间接地通过网络到达任何其他服务器。
 * 关键连接 是在该集群中的重要连接，假如我们将它移除，便会导致某些服务器无法访问其他服务器。
 * 请你以任意顺序返回该集群内的所有 关键连接 。
 * <p>
 * 输入：n = 4, connections = [[0,1],[1,2],[2,0],[1,3]]
 * 输出：[[1,3]]
 * 解释：[[3,1]] 也是正确的。
 * <p>
 * 输入：n = 2, connections = [[0,1]]
 * 输出：[[0,1]]
 * <p>
 * 2 <= n <= 10^5
 * n - 1 <= connections.length <= 10^5
 * 0 <= ai, bi <= n - 1
 * ai != bi
 * 不存在重复的连接
 */
public class Problem1192 {
    /**
     * dfs过程中第一次访问当前节点的dfn和low的初始值
     */
    private int index = 0;

    public static void main(String[] args) {
        Problem1192 problem1192 = new Problem1192();
        int n = 4;
        List<List<Integer>> connections = new ArrayList<List<Integer>>() {{
            add(new ArrayList<Integer>() {{
                add(0);
                add(1);
            }});
            add(new ArrayList<Integer>() {{
                add(1);
                add(2);
            }});
            add(new ArrayList<Integer>() {{
                add(2);
                add(0);
            }});
            add(new ArrayList<Integer>() {{
                add(1);
                add(3);
            }});
        }};
        System.out.println(problem1192.criticalConnections(n, connections));
    }

    /**
     * dfs，Tarjan(读作：ta young，https://www.cnblogs.com/nullzx/p/7968110.html)
     * 核心思想：通过Tarjan求图的桥边，桥边即为删除连通图中的某条边导致连通图不连通的边，即为关键连接
     * dfn[u]：节点u第一次访问的时间戳，即节点u在dfs的访问顺序
     * low[u]：节点u不通过当前节点u到父节点的边能够访问到的祖先节点v的最小dfn[v]
     * 1、判断连通分量：当前节点u的邻接节点全部遍历完，如果dfn[u]==low[u]，则节点u是连通分量中时间戳最小的节点，
     * 即第一个访问的节点，栈内大于节点u的节点出栈，都是同一个连通分量中的节点
     * 2、判断桥边：当前节点u访问未访问的邻接节点v，更新当前节点u的low[u]=min(low[u],low[v])，
     * 如果low[v]>dfn[u]，则邻接节点v只能通过访问节点u访问节点u的祖先节点，则节点u和节点v的边是桥边
     * <p>
     * 遍历到当前节点u，找当前节点u的邻接节点v，邻接节点v未访问，进行dfs，dfs结束后更新当前节点u的low[u]=min(low[u],low[v])，
     * 如果low[v]>dfn[u]，则邻接节点v只能通过访问节点u访问节点u的祖先节点，则节点u和节点v的边是桥边；
     * 邻接节点v已访问，更新当前节点u的low[u]=min(low[u],dfn[v])
     * 时间复杂度O(m+n)，空间复杂度O(m+n) (m=connections.size()，即图中边的个数)
     *
     * @param n
     * @param connections
     * @return
     */
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        //邻接表
        List<List<Integer>> edges = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            edges.add(new ArrayList<>());
        }

        for (int i = 0; i < connections.size(); i++) {
            int u = connections.get(i).get(0);
            int v = connections.get(i).get(1);

            edges.get(u).add(v);
            edges.get(v).add(u);
        }

        //节点第一次访问的时间戳，即节点在dfs的访问顺序，同时也作为节点访问数组
        int[] dfn = new int[n];
        //节点不通过当前节点到父节点的边能够访问到的祖先节点的最小dfn
        int[] low = new int[n];

        //dfn、low初始化为-1，表示节点未访问
        for (int i = 0; i < n; i++) {
            dfn[i] = -1;
            low[i] = -1;
        }

        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (dfn[i] == -1) {
                //初始化节点i的父节点为-1，表示节点i不存在父节点
                dfs(i, -1, dfn, low, edges, result);
            }
        }

        return result;
    }

    /**
     * Tarjan求桥边
     * 注意：本题图中不存在重复的连接，即两个节点之间最多只存在一条边，遍历当前节点时保存父节点，即可避免重复遍历
     *
     * @param u      当前节点u
     * @param parent 节点u的父节点，即节点u是从哪个节点遍历到的，无向图中不存在重复的连接，保存父节点，即可避免重复遍历
     * @param dfn    节点u第一次访问的时间戳，即节点在dfs的访问顺序，同时也作为节点访问数组
     * @param low    节点u不通过当前节点u到父节点的边能够访问到的祖先节点v的最小dfn[v]
     * @param edges  邻接表
     * @param result 存储桥边的结果集合
     */
    private void dfs(int u, int parent, int[] dfn, int[] low, List<List<Integer>> edges, List<List<Integer>> result) {
        dfn[u] = index;
        low[u] = index;
        index++;

        //遍历节点u的邻接节点v
        for (int v : edges.get(u)) {
            //无向图中不存在重复的连接，保存父节点，即可避免重复遍历
            if (v == parent) {
                continue;
            }

            //邻接节点v未访问
            if (dfn[v] == -1) {
                dfs(v, u, dfn, low, edges, result);
                //更新节点u不通过父节点parent能够访问到的祖先节点的最小dfn[u]，
                //因为节点v为节点u未访问的邻接节点，所以取low[u]和low[v]中的较小值
                low[u] = Math.min(low[u], low[v]);

                //邻接节点v只能通过节点u访问节点u的祖先节点，则节点u和节点v的边是桥边
                if (low[v] > dfn[u]) {
                    List<Integer> list = new ArrayList<>();
                    list.add(u);
                    list.add(v);
                    result.add(list);
                }
            } else {
                //邻接节点v已访问

                //更新节点u不通过父节点parent能够访问到的祖先节点的最小dfn[u]，
                //注意：这里是取low[u]和dfn[v]中的较小值
                low[u] = Math.min(low[u], dfn[v]);
            }
        }
    }
}
