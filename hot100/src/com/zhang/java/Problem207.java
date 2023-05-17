package com.zhang.java;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Date 2022/5/13 9:06
 * @Author zsy
 * @Description 课程表 华为机试题 拓扑排序类比Problem210、Problem329、IsCircleDependency 图类比Problem133、Problem210、Problem329、Problem399、Problem785、Problem863
 * 你这个学期必须选修 numCourses 门课程，记为 0 到 numCourses - 1 。
 * 在选修某些课程之前需要一些先修课程。
 * 先修课程按数组 prerequisites 给出，其中 prerequisites[i] = [ai, bi] ，
 * 表示如果要学习课程 ai 则 必须 先学习课程 bi 。
 * 例如，先修课程对 [0, 1] 表示：想要学习课程 0 ，你需要先完成课程 1 。
 * 请你判断是否可能完成所有课程的学习？如果可以，返回 true ；否则，返回 false 。
 * <p>
 * 输入：numCourses = 2, prerequisites = [[1,0]]
 * 输出：true
 * 解释：总共有 2 门课程。学习课程 1 之前，你需要完成课程 0 。这是可能的。
 * <p>
 * 输入：numCourses = 2, prerequisites = [[1,0],[0,1]]
 * 输出：false
 * 解释：总共有 2 门课程。学习课程 1 之前，你需要先完成 课程 0 ；并且学习课程 0 之前，你还应先完成课程 1 。这是不可能的。
 * <p>
 * 1 <= numCourses <= 10^5
 * 0 <= prerequisites.length <= 5000
 * prerequisites[i].length == 2
 * 0 <= ai, bi < numCourses
 * prerequisites[i] 中的所有课程对 互不相同
 */
public class Problem207 {
    /**
     * dfs中是否有环，是否可以完成所有课程
     */
    private boolean hasCircle = false;

    public static void main(String[] args) {
        Problem207 problem207 = new Problem207();
        int numCourses = 6;
        int[][] prerequisites = {{3, 0}, {3, 1}, {4, 1}, {4, 2}, {5, 3}, {5, 4}};
        System.out.println(problem207.canFinish(numCourses, prerequisites));
        System.out.println(problem207.canFinish2(numCourses, prerequisites));
    }

    /**
     * dfs，判断图中是否有拓扑排序
     * 本质：找出度为0的节点，出度为0的节点在拓扑排序中一定排在后面
     * 拓扑排序：有向无环图所有顶点进行排序，使图中任意一对顶点u、v，边<u,v>在排序中u出现在v之前
     * 时间复杂度O(m+n)，空间复杂度O(m^2) (m为课程数，n为先修课程的要求数，如果使用集合替代临界矩阵，空间复杂度O(m+n))
     *
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        //邻接矩阵，可以使用list集合替代邻接矩阵
        int[][] edges = new int[numCourses][numCourses];
        //访问数组，0-未访问，1-正在访问，2-已访问
        int[] visited = new int[numCourses];

        for (int i = 0; i < prerequisites.length; i++) {
            edges[prerequisites[i][1]][prerequisites[i][0]] = 1;
        }

        for (int i = 0; i < numCourses; i++) {
            //从未访问的顶点开始dfs
            if (visited[i] == 0) {
                dfs(i, edges, visited);
            }

            //有环说明不存在拓扑排序，返回false
            if (hasCircle) {
                return false;
            }
        }

        return true;
    }

    /**
     * bfs，判断图中是否有拓扑排序
     * 本质：找入度为0的节点，入度为0的节点在拓扑排序中一定排在前面
     * 拓扑排序：有向无环图所有顶点进行排序，使图中任意一对顶点u、v，边<u,v>在排序中u出现在v之前
     * 时间复杂度O(m+n)，空间复杂度O(m^2) (m为课程数，n为先修课程的要求数，如果使用集合替代临界矩阵，空间复杂度O(m+n))
     *
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public boolean canFinish2(int numCourses, int[][] prerequisites) {
        //邻接矩阵，可以使用list集合替代邻接矩阵
        int[][] edges = new int[numCourses][numCourses];
        //入度数组
        int[] inDegree = new int[numCourses];

        for (int i = 0; i < prerequisites.length; i++) {
            edges[prerequisites[i][1]][prerequisites[i][0]] = 1;
            inDegree[prerequisites[i][0]]++;
        }

        //存放入度为0的队列
        Queue<Integer> queue = new LinkedList<>();
        //统计能够访问到的入度为0顶点个数
        int count = 0;

        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            int u = queue.poll();
            count++;

            //遍历u的邻接顶点v
            for (int v = 0; v < edges[0].length; v++) {
                if (edges[u][v] != 0) {
                    //v入度减1
                    inDegree[v]--;

                    //邻接顶点v的入度为0，则入队
                    if (inDegree[v] == 0) {
                        queue.offer(v);
                    }
                }
            }
        }

        //判断是否能够访问到所有的顶点
        return count == numCourses;
    }

    private void dfs(int u, int[][] edges, int[] visited) {
        if (hasCircle) {
            return;
        }

        //当前顶点u正在访问
        visited[u] = 1;

        //遍历u的邻接顶点v
        for (int v = 0; v < edges[0].length; v++) {
            if (hasCircle) {
                return;
            }

            //找u未被访问的邻接顶点v
            if (edges[u][v] != 0) {
                //邻接顶点v没有访问
                if (visited[v] == 0) {
                    dfs(v, edges, visited);
                } else if (visited[v] == 1) {
                    //邻接顶点v正在访问，说明有环，不存在拓扑排序
                    hasCircle = true;
                    return;
                } else if (visited[v] == 2) {
                    //邻接顶点v已经访问过，直接进行下次循环
                    continue;
                }
            }
        }

        //当前顶点u已经访问
        visited[u] = 2;
    }
}
