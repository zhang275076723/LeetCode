package com.zhang.java;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2023/10/29 08:04
 * @Author zsy
 * @Description 访问所有节点的最短路径 状态压缩类比Problem187、Problem294、Problem464、Problem473、Problem526、Problem638、Problem698、Problem1723、Problem1908、Problem2305 bfs类比Problem499、Problem505、Problem1129、Problem1293、Problem1368、Problem1631、Problem2045、Problem2290
 * 存在一个由 n 个节点组成的无向连通图，图中的节点按从 0 到 n - 1 编号。
 * 给你一个数组 graph 表示这个图。其中，graph[i] 是一个列表，由所有与节点 i 直接相连的节点组成。
 * 返回能够访问所有节点的最短路径的长度。
 * 你可以在任一节点开始和停止，也可以多次重访节点，并且可以重用边。
 * <p>
 * 输入：graph = [[1,2,3],[0],[0],[0]]
 * 输出：4
 * 解释：一种可能的路径为 [1,0,2,0,3]
 * <p>
 * 输入：graph = [[1],[0,2,4],[1,3,4],[2],[1,2]]
 * 输出：4
 * 解释：一种可能的路径为 [0,1,4,2,3]
 * <p>
 * n == graph.length
 * 1 <= n <= 12
 * 0 <= graph[i].length < n
 * graph[i] 不包含 i
 * 如果 graph[a] 包含 b ，那么 graph[b] 也包含 a
 * 输入的图总是连通图
 */
public class Problem847 {
    public static void main(String[] args) {
        Problem847 problem847 = new Problem847();
        int[][] graph = {{1, 2, 3}, {0}, {0}, {0}};
//        int[][] graph = {{1}, {0, 2, 4}, {1, 3, 4}, {2}, {1, 2}};
        System.out.println(problem847.shortestPathLength(graph));
    }

    /**
     * bfs+二进制状态压缩
     * visited[i][j]：遍历到节点i，此时所有节点的二进制访问状态j是否被访问
     * 某个节点对应的二进制访问状态第i位(低位到高位，最低位为第0位)为1，则表示节点i已访问；第i位为0，则表示节点i未访问
     * n个节点共2^n种不同节点的访问状态，不使用二进制状态压缩，每种状态需要O(n)存储，每个节点需要O(n*2^n)，共需要O(n^2*2^n)；
     * 使用二进制状态压缩，每种状态需要O(1)存储，每个节点需要O(2^n)，共需要O(n*2^n)
     * 时间复杂度O(n^2*2^n)，空间复杂度O(n*2^n)
     * (图的bfs时间复杂度O(m+n)，m为图中边的个数，n为图中节点的个数，但本题图中边的个数为n^2，图中节点的个数为访问状态的个数，即为2^n)
     *
     * @param graph
     * @return
     */
    public int shortestPathLength(int[][] graph) {
        //图中节点的个数
        int n = graph.length;
        //二进制状态压缩访问数组，避免重复访问，即节点u到节点v，节点v又到节点u的情况
        //visited[i][j]：遍历到节点i，此时所有节点的二进制访问状态j是否被访问
        boolean[][] visited = new boolean[n][1 << n];
        //arr[0]：当前节点，arr[1]：遍历到节点arr[0]，此时所有节点的二进制访问状态
        Queue<int[]> queue = new LinkedList<>();

        //每个节点都作为起始节点bfs
        for (int i = 0; i < n; i++) {
            //访问状态的第i位为1，表示节点i已访问
            queue.offer(new int[]{i, 1 << i});
        }

        //访问所有节点的最短路径长度
        int distance = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            //每次往外扩一层，将本次节点能够访问到的节点加入队列中
            for (int i = 0; i < size; i++) {
                int[] arr = queue.poll();
                int u = arr[0];
                int curState = arr[1];

                //所有节点的二进制访问状态的所有位都为1，即遍历到节点u时所有节点都已访问，则找到了访问所有节点的最短路径长度
                if (curState == (1 << n) - 1) {
                    return distance;
                }

                //遍历到节点u，此时所有节点的二进制访问状态curState已经访问，则直接进行下次循环
                if (visited[u][curState]) {
                    continue;
                }

                //遍历到节点u，访问状态curState置为true，表示为已访问
                visited[u][curState] = true;

                //节点u的邻接节点v
                for (int v : graph[u]) {
                    //节点v对应的二进制访问状态第v位为1，表示节点v已访问
                    queue.offer(new int[]{v, curState | (1 << v)});
                }
            }

            //distance加1，表示bfs每次往外扩一层
            distance++;
        }

        //bfs结束没有遍历到所有节点，则图中节点不连通，返回-1
        return -1;
    }
}
