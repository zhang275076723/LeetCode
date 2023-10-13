package com.zhang.java;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2022/5/13 9:06
 * @Author zsy
 * @Description 课程表 华为机试题 课程表类比Problem210、Problem630、Problem1462 拓扑排序类比Problem210、Problem269、Problem310、Problem329、Problem444、Problem802、Problem1136、Problem1203、Problem1462、Problem1786、Problem1976、Problem2127、Problem2192、Problem2360、IsCircleDependency 图类比Problem133、Problem210、Problem261、Problem269、Problem277、Problem310、Problem323、Problem329、Problem332、Problem399、Problem444、Problem574、Problem684、Problem685、Problem753、Problem765、Problem785、Problem797、Problem834、Problem863
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
     * dfs拓扑排序图中是否有环标志位
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
     * dfs拓扑排序
     * 核心思想：拓扑排序能够保证如果存在节点u到节点v的边，则节点u在拓扑排序中出现在节点v之前
     * 拓扑排序：有向无环图所有节点进行排序，图中节点u到节点v的边在排序中节点u出现在节点v之前
     * 对未访问的节点dfs，标记未访问的节点为0，正在访问的节点为1，已经访问的节点为2，如果当前节点访问标记为1，
     * 则说明图中存在环，不存在拓扑排序，当前节点访问结束，标记当前节点访问标记为2
     * 时间复杂度O(numCourses+m)，空间复杂度O(numCourses^2)
     * (m为先修课程的要求数，即图中边的个数，如果使用邻接表，空间复杂度O(numCourses+m))
     *
     * @param prerequisites
     * @return
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        //邻接矩阵
        int[][] edges = new int[numCourses][numCourses];
        //访问数组，0-未访问，1-正在访问，2-已访问
        int[] visited = new int[numCourses];

        for (int i = 0; i < prerequisites.length; i++) {
            edges[prerequisites[i][1]][prerequisites[i][0]] = 1;
        }

        for (int i = 0; i < numCourses; i++) {
            //从未访问的节点开始dfs
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
     * bfs拓扑排序
     * 核心思想：拓扑排序能够保证如果存在节点u到节点v的边，则节点u在拓扑排序中出现在节点v之前
     * 拓扑排序：有向无环图所有节点进行排序，图中节点u到节点v的边在排序中节点u出现在节点v之前
     * 图中入度为0的节点入队，队列中节点出队，当前节点的邻接节点的入度减1，邻接节点入度为0的节点入队，
     * 遍历结束判断是否能够遍历到所有的节点，如果能遍历到所有的节点，则存在拓扑排序；否则不存在拓扑排序
     * 时间复杂度O(numCourses+m)，空间复杂度O(numCourses^2)
     * (m为先修课程的要求数，即图中边的个数，如果使用邻接表，空间复杂度O(numCourses+m))
     *
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public boolean canFinish2(int numCourses, int[][] prerequisites) {
        //邻接矩阵
        int[][] edges = new int[numCourses][numCourses];
        //入度数组
        int[] inDegree = new int[numCourses];

        for (int i = 0; i < prerequisites.length; i++) {
            edges[prerequisites[i][1]][prerequisites[i][0]] = 1;
            inDegree[prerequisites[i][0]]++;
        }

        //存放入度为0节点的队列
        Queue<Integer> queue = new LinkedList<>();
        //bfs能够遍历到的入度为0节点个数
        int count = 0;

        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            int u = queue.poll();
            count++;

            //遍历节点u的邻接节点v
            for (int v = 0; v < edges[0].length; v++) {
                if (edges[u][v] != 0) {
                    //v入度减1
                    inDegree[v]--;

                    //邻接节点v的入度为0，则入队
                    if (inDegree[v] == 0) {
                        queue.offer(v);
                    }
                }
            }
        }

        //能遍历到所有的节点，则存在拓扑排序；否则不存在拓扑排序
        return count == numCourses;
    }

    private void dfs(int u, int[][] edges, int[] visited) {
        //已经存在环，或者当前节点u已经访问，直接返回
        if (hasCircle || visited[u] == 2) {
            return;
        }

        //当前节点u正在访问，说明有环，不存在拓扑排序，直接返回
        if (visited[u] == 1) {
            hasCircle = true;
            return;
        }

        //当前节点u正在访问
        visited[u] = 1;

        //遍历节点u的邻接节点v
        for (int v = 0; v < edges.length; v++) {
            if (edges[u][v] != 0) {
                dfs(v, edges, visited);
            }

            if (hasCircle) {
                return;
            }
        }

        //当前节点u已经访问
        visited[u] = 2;
    }
}
