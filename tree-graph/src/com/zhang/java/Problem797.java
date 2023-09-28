package com.zhang.java;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Date 2023/10/2 08:01
 * @Author zsy
 * @Description 所有可能的路径 图类比 回溯+剪枝类比
 * 给你一个有 n 个节点的 有向无环图（DAG），请你找出所有从节点 0 到节点 n-1 的路径并输出（不要求按特定顺序）
 * graph[i] 是一个从节点 i 可以访问的所有节点的列表（即从节点 i 到节点 graph[i][j]存在一条有向边）。
 * <p>
 * 输入：graph = [[1,2],[3],[3],[]]
 * 输出：[[0,1,3],[0,2,3]]
 * 解释：有两条路径 0 -> 1 -> 3 和 0 -> 2 -> 3
 * <p>
 * 输入：graph = [[4,3,1],[3,2,4],[3],[4],[]]
 * 输出：[[0,4],[0,3,4],[0,1,3,4],[0,1,2,3,4],[0,1,4]]
 * <p>
 * n == graph.length
 * 2 <= n <= 15
 * 0 <= graph[i][j] < n
 * graph[i][j] != i（即不存在自环）
 * graph[i] 中的所有元素 互不相同
 * 保证输入为 有向无环图（DAG）
 */
public class Problem797 {
    public static void main(String[] args) {
        Problem797 problem797 = new Problem797();
        int[][] graph = {{4, 3, 1}, {3, 2, 4}, {3}, {4}, {}};
        System.out.println(problem797.allPathsSourceTarget(graph));
        System.out.println(problem797.allPathsSourceTarget2(graph));
    }

    /**
     * dfs
     * 时间复杂度O(n*2^n)，空间复杂度O(n)
     * (共n个节点，每个节点有选或不选2种情况，共2^n条路径，每条路径最多n个节点，需要O(n)将当前路径拷贝到结果集合)
     *
     * @param graph
     * @return
     */
    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        List<List<Integer>> result = new ArrayList<>();

        dfs(0, graph.length - 1, graph, new ArrayList<>(), result);

        return result;
    }

    /**
     * bfs
     * 时间复杂度O(n*2^n)，空间复杂度O(n*2^n)
     * (共n个节点，每个节点有选或不选2种情况，共2^n条路径，每条路径最多n个节点，需要O(n)将当前路径拷贝到结果集合)
     * (2^n条路径，每条路径需要O(n)的空间，总共需要O(n*2^n)的空间)
     *
     * @param graph
     * @return
     */
    public List<List<Integer>> allPathsSourceTarget2(int[][] graph) {
        List<List<Integer>> result = new ArrayList<>();
        Queue<Pos> queue = new LinkedList<>();
        Pos pos = new Pos(0, 0);
        pos.path = new ArrayList<Integer>() {{
            add(0);
        }};
        queue.offer(pos);

        while (!queue.isEmpty()) {
            Pos curPos = queue.poll();

            if (curPos.v == graph.length - 1) {
                result.add(curPos.path);
                continue;
            }

            for (int i = 0; i < graph[curPos.v].length; i++) {
                Pos newPos = new Pos(curPos.u, graph[curPos.v][i]);
                newPos.path = new ArrayList<>(curPos.path);
                newPos.path.add(graph[curPos.v][i]);
                queue.offer(newPos);
            }
        }

        return result;
    }

    private void dfs(int u, int v, int[][] graph, List<Integer> list, List<List<Integer>> result) {
        if (u == v) {
            list.add(u);
            result.add(new ArrayList<>(list));
            list.remove(list.size() - 1);
            return;
        }

        list.add(u);

        for (int i = 0; i < graph[u].length; i++) {
            dfs(graph[u][i], v, graph, list, result);
        }

        list.remove(list.size() - 1);
    }

    /**
     * bfs节点
     */
    private static class Pos {
        private int u;
        private int v;
        private List<Integer> path;

        public Pos(int u, int v) {
            this.u = u;
            this.v = v;
        }
    }
}
