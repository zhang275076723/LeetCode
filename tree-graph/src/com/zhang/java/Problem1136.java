package com.zhang.java;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Date 2023/10/23 08:09
 * @Author zsy
 * @Description 并行课程 拓扑排序类比 图类比
 * 给你一个整数 n ，表示编号从 1 到 n 的 n 门课程。
 * 另给你一个数组 relations ，其中 relations[i] = [prevCoursei, nextCoursei] ，
 * 表示课程 prevCoursei 和课程 nextCoursei 之间存在先修关系：课程 prevCoursei 必须在 nextCoursei 之前修读完成。
 * 在一个学期内，你可以学习 任意数量 的课程，但前提是你已经在上一学期修读完待学习课程的所有先修课程。
 * 请你返回学完全部课程所需的 最少 学期数。
 * 如果没有办法做到学完全部这些课程的话，就返回 -1。
 * <p>
 * 输入：n = 3, relations = [[1,3],[2,3]]
 * 输出：2
 * 解释：上图表示课程之间的关系图：
 * 在第一学期，可以修读课程 1 和 2 。
 * 在第二学期，可以修读课程 3 。
 * <p>
 * 输入：n = 3, relations = [[1,2],[2,3],[3,1]]
 * 输出：-1
 * 解释：没有课程可以学习，因为它们互为先修课程。
 * <p>
 * 1 <= n <= 5000
 * 1 <= relations.length <= 5000
 * relations[i].length == 2
 * 1 <= prevCoursei, nextCoursei <= n
 * prevCoursei != nextCoursei
 * 所有 [prevCoursei, nextCoursei] 互不相同
 */
public class Problem1136 {
    public static void main(String[] args) {
        Problem1136 problem1136 = new Problem1136();
        int n = 8;
        int[][] relations = {{0, 3}, {1, 3}, {1, 4}, {2, 4}, {3, 5}, {4, 5}, {6, 5}, {7, 5}};
        System.out.println(problem1136.minimumSemesters(n, relations));
    }

    /**
     * bfs拓扑排序
     * 时间复杂度O(m+n)，空间复杂度O(m+n) (m=relations.length，如果使用邻接矩阵，空间复杂度O(n^2))
     *
     * @param n
     * @param relations
     * @return
     */
    public int minimumSemesters(int n, int[][] relations) {
        //邻接表
        List<List<Integer>> edges = new ArrayList<>(n);
        //入度数组
        int[] inDegree = new int[n];

        for (int i = 0; i < n; i++) {
            edges.add(new ArrayList<>());
        }

        for (int i = 0; i < relations.length; i++) {
            int u = relations[i][0];
            int v = relations[i][1];

            edges.get(u).add(v);
            inDegree[v]++;
        }

        //存放入度为0节点的队列
        Queue<Integer> queue = new LinkedList<>();
        //bfs能够遍历到的入度为0节点个数
        int count = 0;

        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        //bfs去掉最外层节点的次数，学完全部课程所需的最少学期数
        int semesterCount = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            //每次去掉最外层节点，即入度为0的节点
            for (int i = 0; i < size; i++) {
                int u = queue.poll();
                count++;

                for (int v : edges.get(u)) {
                    inDegree[v]--;

                    if (inDegree[v] == 0) {
                        queue.offer(v);
                    }
                }
            }

            //semesterCount加1，表示bfs每次去掉最外层节点，即入度为0的节点
            semesterCount++;
        }

        //能遍历到所有的节点，则存在拓扑排序，返回学完全部课程所需的最少学期数semesterCount；否则不存在拓扑排序，返回-1
        return count == n ? semesterCount : -1;
    }
}
