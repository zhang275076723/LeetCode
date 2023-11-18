package com.zhang.java;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @Date 2023/11/23 08:31
 * @Author zsy
 * @Description 得到要求路径的最小带权子图 图中最短路径类比Problem399、Problem743、Problem787、Problem882、Problem1293、Problem1334、Problem1368、Problem1462、Problem1514、Problem1631、Problem1786、Problem1928、Problem1976、Problem2045、Problem2093、Problem2290、Problem2662、Dijkstra
 * 给你一个整数 n ，它表示一个 带权有向 图的节点数，节点编号为 0 到 n - 1 。
 * 同时给你一个二维整数数组 edges ，其中 edges[i] = [fromi, toi, weighti] ，
 * 表示从 fromi 到 toi 有一条边权为 weighti 的 有向 边。
 * 最后，给你三个 互不相同 的整数 src1 ，src2 和 dest ，表示图中三个不同的点。
 * 请你从图中选出一个 边权和最小 的子图，使得从 src1 和 src2 出发，在这个子图中，都 可以 到达 dest 。
 * 如果这样的子图不存在，请返回 -1 。
 * 子图 中的点和边都应该属于原图的一部分。
 * 子图的边权和定义为它所包含的所有边的权值之和。
 * <p>
 * 输入：n = 6, edges = [[0,2,2],[0,5,6],[1,0,3],[1,4,5],[2,1,1],[2,3,3],[2,3,4],[3,4,2],[4,5,1]], src1 = 0, src2 = 1, dest = 5
 * 输出：9
 * 解释：
 * 上图为输入的图。
 * 蓝色边为最优子图之一。
 * 子图 [[0,2,2],[1,4,5],[2,1,1],[4,5,1]]为最优解，该子图的权值为9
 * 注意，子图 [[1,0,3],[0,5,6]] 也能得到最优解，但不可能找到权重和比这些更少的子图。
 * <p>
 * 输入：n = 3, edges = [[0,1,1],[2,1,1]], src1 = 0, src2 = 1, dest = 2
 * 输出：-1
 * 解释：
 * 上图为输入的图。
 * 可以看到，不存在从节点 1 到节点 2 的路径，所以不存在任何子图满足所有限制。
 * <p>
 * 3 <= n <= 10^5
 * 0 <= edges.length <= 10^5
 * edges[i].length == 3
 * 0 <= fromi, toi, src1, src2, dest <= n - 1
 * fromi != toi
 * src1 ，src2 和 dest 两两不同。
 * 1 <= weight[i] <= 10^5
 */
public class Problem2203 {
    public static void main(String[] args) {
        Problem2203 problem2203 = new Problem2203();
//        int n = 6;
//        int[][] edges = {{0, 2, 2}, {0, 5, 6}, {1, 0, 3}, {1, 4, 5}, {2, 1, 1},
//                {2, 3, 3}, {2, 3, 4}, {3, 4, 2}, {4, 5, 1}};
//        int src1 = 0;
//        int src2 = 1;
//        int dest = 5;
        int n = 5;
        int[][] edges = {{4, 2, 20}, {4, 3, 46}, {0, 1, 15}, {0, 1, 43}, {0, 1, 32}, {3, 1, 13}};
        int src1 = 0;
        int src2 = 4;
        int dest = 1;
        System.out.println(problem2203.minimumWeight(n, edges, src1, src2, dest));
    }

    /**
     * 堆优化Dijkstra求节点src1、节点src2、节点dest到其他节点的最短路径长度
     * 节点src1和节点src2到节点dest的最短路径为'Y'型，枚举中间节点，得到节点src1到中间节点的最短路径长度distance1，
     * 节点src2到中间节点的最短路径长度distance2，节点dest到中间节点的最短路径长度distance3，
     * distance1+distance2+distance3的最小值，即为节点src1和节点src2到节点dest子图的最小权值
     * 时间复杂度O(mlogm+n)，空间复杂度O(m+n) (m=edges.length，即图中边的个数，n为图中节点的个数)
     * <    src1                  src2
     * <      \                    /
     * <       \                 /
     * <        \              /
     * <         A            B
     * <          \         /
     * <           \      /
     * <            \   /
     * <              C
     * <              |
     * <              |
     * <              |
     * <              D
     * <              |
     * <              |
     * <              |
     * <             dest
     * < 图中节点C即为使distance1+distance2+distance3最小的中间节点
     *
     * @param n
     * @param edges
     * @param src1
     * @param src2
     * @param dest
     * @return
     */
    public long minimumWeight(int n, int[][] edges, int src1, int src2, int dest) {
        //邻接表，有向图，arr[0]：当前节点，arr[1]：当前节点到邻接节点边的权值
        List<List<int[]>> graph1 = new ArrayList<>();
        //邻接表，graph1的反图，通过graph2得到节点dest到其他节点的最短路径长度
        List<List<int[]>> graph2 = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph1.add(new ArrayList<>());
            graph2.add(new ArrayList<>());
        }

        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            int weight = edges[i][2];

            graph1.get(u).add(new int[]{v, weight});
            graph2.get(v).add(new int[]{u, weight});
        }

        //节点src1到其他节点的最短路径长度数组
        long[] distance1 = dijkstra(src1, graph1);
        //节点src2到其他节点的最短路径长度数组
        long[] distance2 = dijkstra(src2, graph1);
        //节点dest到其他节点的最短路径长度数组
        long[] distance3 = dijkstra(dest, graph2);

        //节点src1和节点src2到节点dest子图的最小权值
        long result = Long.MAX_VALUE;

        //枚举中间节点，distance1+distance2+distance3的最小值，即为节点src1和节点src2到节点dest的最小权值子图
        for (int i = 0; i < n; i++) {
            //节点src1能够到达节点i，节点src2能够到达节点i，节点dst能够到达节点i，节点i才能作为中间节点
            if (distance1[i] != Long.MAX_VALUE && distance2[i] != Long.MAX_VALUE && distance3[i] != Long.MAX_VALUE) {
                result = Math.min(result, distance1[i] + distance2[i] + distance3[i]);
            }
        }

        //result仍然为Long.MAX_VALUE，则不存在节点src1和节点src2到节点dest子图，返回-1；否则，返回result
        return result == Long.MAX_VALUE ? -1 : result;
    }

    /**
     * 堆优化Dijkstra求节点u到其他节点的最短路径长度
     * 优先队列每次出队节点u到其他节点的路径长度中最短路径的节点v，节点v作为中间节点更新节点u到其他节点的最短路径长度
     * 时间复杂度O(mlogm)，空间复杂度O(n) (n为图中节点的个数，m为图中边的个数)
     *
     * @param u
     * @param edges
     * @return
     */
    private long[] dijkstra(int u, List<List<int[]>> edges) {
        //图中节点的个数
        int n = edges.size();
        //节点u到其他节点的最短路径长度数组
        long[] distance = new long[n];

        //distance数组初始化，初始化为int最大值表示节点u无法到达节点i
        for (int i = 0; i < n; i++) {
            distance[i] = Long.MAX_VALUE;
        }

        //初始化，节点u到节点u的最短路径长度为0
        distance[u] = 0;

        //小根堆，arr[0]：当前节点，arr[1]：节点u到当前节点的路径长度，注意：当前路径长度不一定是最短路径长度
        PriorityQueue<long[]> priorityQueue = new PriorityQueue<>(new Comparator<long[]>() {
            @Override
            public int compare(long[] arr1, long[] arr2) {
                //不能写成return (int) (arr1[1] - arr2[1]);，因为long相减再转为int有可能在int范围溢出
                return Long.compare(arr1[1], arr2[1]);
            }
        });

        //起始节点u入堆
        priorityQueue.offer(new long[]{u, 0});

        while (!priorityQueue.isEmpty()) {
            long[] arr = priorityQueue.poll();
            //当前节点v
            int v = (int) arr[0];
            //节点u到节点v的路径长度，注意：当前路径长度不一定是最短路径长度
            long curDistance = arr[1];

            //curDistance大于distance[v]，则当前节点v不能作为中间节点更新节点u到其他节点的最短路径长度，直接进行下次循环
            if (curDistance > distance[v]) {
                continue;
            }

            //遍历节点v的邻接节点w
            for (int[] arr2 : edges.get(v)) {
                //邻接节点w
                int w = arr2[0];
                //节点v到邻接节点w边的权值
                int weight = arr2[1];

                //节点v作为中间节点更新节点u到其他节点的最短路径长度，更新distance[w]，节点w入堆
                if (curDistance + weight < distance[w]) {
                    distance[w] = curDistance + weight;
                    priorityQueue.offer(new long[]{w, distance[w]});
                }
            }
        }

        return distance;
    }
}
