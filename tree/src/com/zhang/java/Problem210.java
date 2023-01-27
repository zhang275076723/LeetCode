package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/5/14 9:58
 * @Author zsy
 * @Description 课程表 II 拓扑排序类比Problem207、Problem329 图类比Problem133、Problem207、Problem329、Problem399
 * 现在你总共有 numCourses 门课需要选，记为 0 到 numCourses - 1。
 * 给你一个数组 prerequisites ，其中 prerequisites[i] = [ai, bi] ，表示在选修课程 ai 前 必须 先选修 bi 。
 * 例如，想要学习课程 0 ，你需要先完成课程 1 ，我们用一个匹配来表示：[0,1] 。
 * 返回你为了学完所有课程所安排的学习顺序。可能会有多个正确的顺序，你只要返回 任意一种 就可以了。
 * 如果不可能完成所有课程，返回 一个空数组 。
 * <p>
 * 输入：numCourses = 2, prerequisites = [[1,0]]
 * 输出：[0,1]
 * 解释：总共有 2 门课程。要学习课程 1，你需要先完成课程 0。因此，正确的课程顺序为 [0,1] 。
 * <p>
 * 输入：numCourses = 4, prerequisites = [[1,0],[2,0],[3,1],[3,2]]
 * 输出：[0,2,1,3]
 * 解释：总共有 4 门课程。要学习课程 3，你应该先完成课程 1 和课程 2。并且课程 1 和课程 2 都应该排在课程 0 之后。
 * 因此，一个正确的课程顺序是 [0,1,2,3] 。另一个正确的排序是 [0,2,1,3] 。
 * <p>
 * 输入：numCourses = 1, prerequisites = []
 * 输出：[0]
 * <p>
 * 1 <= numCourses <= 2000
 * 0 <= prerequisites.length <= numCourses * (numCourses - 1)
 * prerequisites[i].length == 2
 * 0 <= ai, bi < numCourses
 * ai != bi
 * 所有[ai, bi] 互不相同
 */
public class Problem210 {
    /**
     * dfs中是否有环，是否可以完成所有课程
     */
    private boolean hasCircle = false;

    /**
     * 拓扑排序数组指针，dfs需要倒序
     */
    private int index;

    public static void main(String[] args) {
        Problem210 problem210 = new Problem210();
        int numCourses = 6;
        int[][] prerequisites = {{3, 0}, {3, 1}, {4, 1}, {4, 2}, {5, 3}, {5, 4}};
        System.out.println(Arrays.toString(problem210.findOrder(numCourses, prerequisites)));
        System.out.println(Arrays.toString(problem210.findOrder2(numCourses, prerequisites)));
    }

    /**
     * dfs，判断图中是否有拓扑排序
     * 本质：找出度为0的节点，出度为0的节点在拓扑排序中一定排在后面
     * 拓扑排序：有向无环图所有顶点进行排序，使图中任意一对顶点u、v，边<u,v>在排序中u出现在v之前
     * 时间复杂度O(m+n)，空间复杂度O(m+n)，m为课程数，n为先修课程的要求数
     *
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        //集合表示的邻接矩阵
        List<List<Integer>> edges = new ArrayList<>();
        //访问数组，0-未访问，1-正在访问，2-已访问
        int[] visited = new int[numCourses];

        for (int i = 0; i < numCourses; i++) {
            edges.add(new ArrayList<>());
        }

        for (int i = 0; i < prerequisites.length; i++) {
            edges.get(prerequisites[i][1]).add(prerequisites[i][0]);
        }

        //拓扑排序数组
        int[] result = new int[numCourses];
        //拓扑排序数组指针，dfs需要倒序
        index = numCourses - 1;

        for (int i = 0; i < numCourses; i++) {
            //从未访问的顶点开始dfs
            if (visited[i] == 0) {
                dfs(i, result, edges, visited);
            }

            //有环说明不存在拓扑排序，返回空数组
            if (hasCircle) {
                return new int[0];
            }
        }

        return result;
    }

    /**
     * bfs，计算图的拓扑排序
     * 本质：找入度为0的节点，入度为0的节点在拓扑排序中一定排在前面
     * 拓扑排序：有向无环图所有顶点进行排序，使图中任意一对顶点u、v，边<u,v>在排序中u出现在v之前
     * 时间复杂度O(m+n)，空间复杂度O(m+n)，m为课程数，n为先修课程的要求数
     *
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public int[] findOrder2(int numCourses, int[][] prerequisites) {
        //集合表示的邻接矩阵
        List<List<Integer>> edges = new ArrayList<>();
        //入度数组
        int[] inDegree = new int[numCourses];
        //拓扑排序数组
        int[] result = new int[numCourses];
        //拓扑排序数组指针，bfs正序
        int index = 0;

        for (int i = 0; i < numCourses; i++) {
            edges.add(new ArrayList<>());
        }

        for (int i = 0; i < prerequisites.length; i++) {
            edges.get(prerequisites[i][1]).add(prerequisites[i][0]);
            inDegree[prerequisites[i][0]]++;
        }

        //存放入度为0的队列
        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            int u = queue.poll();
            result[index] = u;
            index++;

            //遍历u的邻接顶点v
            for (int v : edges.get(u)) {
                inDegree[v]--;

                //邻接顶点v的入度为0，则入队
                if (inDegree[v] == 0) {
                    queue.offer(v);
                }
            }
        }

        //能够访问到所有的入度为0的顶点，则存在拓扑排序
        return index == numCourses ? result : new int[0];
    }

    private void dfs(int u, int[] result, List<List<Integer>> edges, int[] visited) {
        //当前顶点u正在访问
        visited[u] = 1;

        //u的邻接顶点v
        for (int v : edges.get(u)) {
            if (hasCircle) {
                return;
            }

            //邻接顶点v没有访问
            if (visited[v] == 0) {
                dfs(v, result, edges, visited);
            } else if (visited[v] == 1) {
                //邻接顶点v正在访问，说明有环，不存在拓扑排序
                hasCircle = true;
                return;
            }
        }

        //当前顶点u已经访问
        visited[u] = 2;

        //拓扑排序数组赋值
        result[index] = u;
        index--;
    }
}
