package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/5/14 9:58
 * @Author zsy
 * @Description 课程表 II 类比Problem207
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
     * dfs中拓扑排序数组下标索引
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
     * 时间复杂度O(m+n)，空间复杂度O(m+n)，m为课程数，n为先修课程的要求数
     * 拓扑排序：有向无环图所有顶点进行排序，使图中任意一对顶点u、v，边<u,v>在排序中u出现在v之前
     *
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        //使用集合存放临界表，存放每门课程修完后可以修的课程
        List<List<Integer>> edges = new ArrayList<>();
        //节点对应课程的访问数组，0-未访问，1-正在访问，2-已访问
        int[] visited = new int[numCourses];
        //拓扑排序数组
        int[] result = new int[numCourses];
        //因为dfs中拓扑排序数组下标索引相当于栈
        index = numCourses - 1;

        for (int i = 0; i < numCourses; i++) {
            edges.add(new ArrayList<>());
        }
        for (int i = 0; i < prerequisites.length; i++) {
            edges.get(prerequisites[i][1]).add(prerequisites[i][0]);
        }

        for (int i = 0; i < numCourses; i++) {
            //有环，说明不满足当前课程要先上的课程，返回false
            if (hasCircle) {
                return new int[0];
            }

            //当前节点未访问，从当前节点进行dfs
            if (visited[i] == 0) {
                dfs(i, visited, edges, result);
            }
        }

        return result;
    }

    /**
     * bfs，计算图的拓扑排序
     * 时间复杂度O(m+n)，空间复杂度O(m+n)，m为课程数，n为先修课程的要求数
     * 本质：找入度为0的节点，入度为0的节点在拓扑排序中一定排在前面
     * 拓扑排序：有向无环图所有顶点进行排序，使图中任意一对顶点u、v，边<u,v>在排序中u出现在v之前
     *
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public int[] findOrder2(int numCourses, int[][] prerequisites) {
        //使用集合存放临界表，存放每门课程修完后可以修的课程
        List<List<Integer>> edges = new ArrayList<>();
        //节点的入度
        int[] inDegree = new int[numCourses];

        for (int i = 0; i < numCourses; i++) {
            edges.add(new ArrayList<>());
        }
        for (int i = 0; i < prerequisites.length; i++) {
            edges.get(prerequisites[i][1]).add(prerequisites[i][0]);
            inDegree[prerequisites[i][0]]++;
        }

        //存放入度为0的队列
        Queue<Integer> queue = new LinkedList<>();
        //拓扑排序数组
        int[] result = new int[numCourses];
        //拓扑排序数组索引下标
        int index = 0;

        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            int u = queue.poll();
            result[index++] = u;

            //u的邻接顶点v
            for (int v : edges.get(u)) {
                //v的入度减1
                inDegree[v]--;
                if (inDegree[v] == 0) {
                    queue.offer(v);
                }
            }
        }

        //能够访问所有节点，表示没有环
        return index == numCourses ? result : new int[0];
    }

    private void dfs(int u, int[] visited, List<List<Integer>> edges, int[] result) {
        //当前节点正在访问
        visited[u] = 1;

        //u的邻接顶点v
        for (int v : edges.get(u)) {
            if (visited[v] == 0) {
                dfs(v, visited, edges, result);
            } else if (visited[v] == 1) {
                hasCircle = true;
                return;
            }
        }

        //当前节点已访问
        visited[u] = 2;
        //当前节点u入栈
        result[index--] = u;
    }
}
