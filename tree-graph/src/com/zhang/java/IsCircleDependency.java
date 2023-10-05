package com.zhang.java;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2023/5/17 08:44
 * @Author zsy
 * @Description 检测循环依赖 字节面试题 拓扑排序类比Problem207、Problem210、Problem310、Problem329
 * 现有n个编译项，编号为0 ~ n-1。给定一个二维数组，表示编译项之间有依赖关系。
 * 如[0, 1]表示1依赖于0。
 * 若存在循环依赖则返回空；不存在依赖则返回可行的编译顺序。
 * <p>
 * 输入：[[0,2],[1,2],[2,3],[2,4]]
 * 输出：[0,1,2,3,4]，或[1,0,2,4,3]，拓扑排序不唯一
 * <p>
 * 输入：[[0,1],[1,2],[2,1]]
 * 输出：[]
 * 解释：1到2
 */
public class IsCircleDependency {
    /**
     * dfs中是否有环，是否可以完成所有课程
     */
    private boolean hasCircle = false;

    /**
     * 拓扑排序数组指针，dfs需要倒序
     */
    private int index;

    public static void main(String[] args) {
        IsCircleDependency isCircleDependency = new IsCircleDependency();
        int n = 5;
        int[][] dependency = {{0, 2}, {1, 2}, {2, 3}, {2, 4}};
        System.out.println(Arrays.toString(isCircleDependency.topology(n, dependency)));
        System.out.println(Arrays.toString(isCircleDependency.topology2(n, dependency)));
    }

    /**
     * dfs拓扑排序
     * 核心思想：找出度为0的节点，出度为0的节点在拓扑排序中排在后面
     * 拓扑排序：有向无环图所有顶点进行排序，图中u到v的边在排序中u出现在v之前
     * 对未访问的节点dfs，标记未访问的节点为0，正在访问的节点为1，已经访问的节点为2，如果当前节点访问标记为1，
     * 则说明图中存在环，不存在拓扑排序，当前节点访问结束，标记当前节点访问标记为2
     * 时间复杂度O(m+n)，空间复杂度O(m^2) (m为顶点数，n为依赖数组的要求数，如果使用集合替代临界矩阵，空间复杂度O(m+n))
     *
     * @param n
     * @param dependency
     * @return
     */
    public int[] topology(int n, int[][] dependency) {
        //邻接矩阵
        int[][] edges = new int[n][n];
        //访问数组，0-未访问，1-正在访问，2-已访问
        int[] visited = new int[n];

        for (int i = 0; i < dependency.length; i++) {
            edges[dependency[i][0]][dependency[i][1]] = 1;
        }

        //拓扑排序数组
        int[] result = new int[n];
        //拓扑排序数组指针，dfs需要倒序
        index = n - 1;

        for (int i = 0; i < n; i++) {
            //有环说明不存在拓扑排序，返回空数组
            if (hasCircle) {
                return new int[0];
            }

            //从未访问的顶点开始dfs
            if (visited[i] == 0) {
                dfs(i, result, edges, visited);
            }
        }

        return result;
    }

    /**
     * bfs拓扑排序
     * 核心思想：找入度为0的节点，入度为0的节点在拓扑排序中排在前面
     * 拓扑排序：有向无环图所有顶点进行排序，图中u到v的边在排序中u出现在v之前
     * 图中入度为0的节点入队，队列中节点出队，当前节点指向的节点的入度减1，如果存在新的入度为0的节点，则新节点入队，
     * 判断是否能够遍历到所有的节点，如果能遍历到所有的节点，则存在拓扑排序；否则不存在拓扑排序
     * 时间复杂度O(m+n)，空间复杂度O(m^2) (m为顶点数，n为依赖数组的要求数，如果使用集合替代临界矩阵，空间复杂度O(m+n))
     *
     * @param n
     * @param dependency
     * @return
     */
    public int[] topology2(int n, int[][] dependency) {
        //邻接矩阵
        int[][] edges = new int[n][n];
        //入度数组
        int[] inDegree = new int[n];
        //拓扑排序数组
        int[] result = new int[n];
        //拓扑排序数组指针，bfs正序
        int index = 0;

        for (int i = 0; i < dependency.length; i++) {
            edges[dependency[i][0]][dependency[i][1]] = 1;
            inDegree[dependency[i][1]]++;
        }

        //存放入度为0的队列
        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            int u = queue.poll();
            result[index] = u;
            index++;

            //遍历u的邻接顶点v
            for (int v = 0; v < n; v++) {
                if (edges[u][v] != 0) {
                    inDegree[v]--;

                    //邻接顶点v的入度为0，则入队
                    if (inDegree[v] == 0) {
                        queue.offer(v);
                    }
                }
            }
        }

        //能遍历到所有的节点，则存在拓扑排序；否则不存在拓扑排序
        return index == n ? result : new int[0];
    }

    private void dfs(int u, int[] result, int[][] edges, int[] visited) {
        if (hasCircle) {
            return;
        }

        //当前顶点u正在访问
        visited[u] = 1;

        //遍历u的邻接顶点v
        for (int v = 0; v < edges.length; v++) {
            if (hasCircle) {
                return;
            }

            //找u未被访问的邻接顶点v
            if (edges[u][v] != 0) {
                //邻接顶点v没有访问
                if (visited[v] == 0) {
                    dfs(v, result, edges, visited);
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

        //拓扑排序数组赋值
        result[index] = u;
        index--;
        //当前顶点u已经访问
        visited[u] = 2;
    }
}
