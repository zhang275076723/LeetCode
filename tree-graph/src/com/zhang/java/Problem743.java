package com.zhang.java;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @Date 2023/11/15 08:05
 * @Author zsy
 * @Description 网络延迟时间 华为机试题 快手面试题 图中最短路径类比Problem399、Problem787、Problem882、Problem1334、Problem1368、Problem1462、Problem1514、Problem1786、Problem1928、Problem1976、Dijkstra
 * 有 n 个网络节点，标记为 1 到 n。
 * 给你一个列表 times，表示信号经过 有向 边的传递时间。
 * times[i] = (ui, vi, wi)，其中 ui 是源节点，vi 是目标节点， wi 是一个信号从源节点传递到目标节点的时间。
 * 现在，从某个节点 K 发出一个信号。需要多久才能使所有节点都收到信号？
 * 如果不能使所有节点收到信号，返回 -1 。
 * <p>
 * 输入：times = [[2,1,1],[2,3,1],[3,4,1]], n = 4, k = 2
 * 输出：2
 * <p>
 * 输入：times = [[1,2,1]], n = 2, k = 1
 * 输出：1
 * <p>
 * 输入：times = [[1,2,1]], n = 2, k = 2
 * 输出：-1
 * <p>
 * 1 <= k <= n <= 100
 * 1 <= times.length <= 6000
 * times[i].length == 3
 * 1 <= ui, vi <= n
 * ui != vi
 * 0 <= wi <= 100
 * 所有 (ui, vi) 对都 互不相同（即，不含重复边）
 */
public class Problem743 {
    public static void main(String[] args) {
        Problem743 problem743 = new Problem743();
        int[][] times = {{2, 1, 1}, {2, 3, 1}, {3, 4, 1}};
        int n = 4;
        int k = 2;
        System.out.println(problem743.networkDelayTime(times, n, k));
        System.out.println(problem743.networkDelayTime2(times, n, k));
        System.out.println(problem743.networkDelayTime3(times, n, k));
    }

    /**
     * Dijkstra求节点k-1(每个节点都减1，节点从1开始变为从0开始)到其他节点的最短路径长度
     * 每次从未访问节点中选择距离节点k-1最短路径长度的节点u，节点u作为中间节点更新节点k-1到其他节点的最短路径长度
     * 时间复杂度O(n^2)，空间复杂度O(m+n) (m=times.length，即图中边的个数，n为图中节点的个数)
     *
     * @param times
     * @param n
     * @param k
     * @return
     */
    public int networkDelayTime(int[][] times, int n, int k) {
        //邻接表，arr[0]：当前节点的邻接节点，arr[1]：当前节点到邻接节点边的权值
        List<List<int[]>> edges = new ArrayList<>();

        //注意：节点不是从0而是从1开始，对每个节点都减1，节点从1开始变为从0开始
        for (int i = 0; i < n; i++) {
            edges.add(new ArrayList<>());
        }

        for (int i = 0; i < times.length; i++) {
            //节点u和节点v减1，节点从1开始变为从0开始
            int u = times[i][0] - 1;
            int v = times[i][1] - 1;
            int weight = times[i][2];

            edges.get(u).add(new int[]{v, weight});
        }

        //节点k-1到其他节点的最短路径长度数组
        int[] distance = new int[n];
        //节点访问数组，visited[u]为true，表示已经得到节点k-1到节点u的最短路径长度
        boolean[] visited = new boolean[n];

        //distance数组初始化，初始化为int最大值表示节点k-1无法到达节点i
        for (int i = 0; i < n; i++) {
            distance[i] = Integer.MAX_VALUE;
        }

        //初始化，节点k-1到节点k-1的最短路径长度为0
        distance[k - 1] = 0;

        //每次从未访问节点中选择距离节点k-1最短路径长度的节点u，节点u作为中间节点更新节点k-1到其他节点的最短路径长度
        for (int i = 0; i < n; i++) {
            //初始化distance数组中未访问节点中选择距离节点k-1最短路径长度的节点u
            int u = -1;

            //未访问节点中选择距离节点k-1最短路径长度的节点u
            for (int j = 0; j < n; j++) {
                if (!visited[j] && (u == -1 || distance[j] < distance[u])) {
                    u = j;
                }
            }

            //设置节点u已访问，表示已经得到节点k-1到节点u的最短路径长度
            visited[u] = true;

            //节点u作为中间节点更新节点k-1到其他节点的最短路径长度
            for (int[] arr : edges.get(u)) {
                int v = arr[0];
                int weight = arr[1];

                if (!visited[v]) {
                    distance[v] = Math.min(distance[v], distance[u] + weight);
                }
            }
        }

        //节点k-1到其他节点的最短路径长度中的最大值
        int max = 0;

        for (int i = 0; i < n; i++) {
            max = Math.max(max, distance[i]);
        }

        //max等于Integer.MAX_VALUE，则说明节点k-1有无法到达的节点，返回-1；否则，返回max
        return max == Integer.MAX_VALUE ? -1 : max;
    }

    /**
     * 堆优化Dijkstra求节点k-1(每个节点都减1，节点从1开始变为从0开始)到其他节点的最短路径长度
     * 优先队列每次出队节点k-1到其他节点的路径长度中最短路径的节点u，节点u作为中间节点更新节点k-1到其他节点的最短路径长度
     * 时间复杂度O(mlogm)，空间复杂度O(m+n) (m=times.length，即图中边的个数，n为图中节点的个数)
     *
     * @param times
     * @param n
     * @param k
     * @return
     */
    public int networkDelayTime2(int[][] times, int n, int k) {
        //邻接表，arr[0]：当前节点的邻接节点，arr[1]：当前节点到邻接节点边的权值
        List<List<int[]>> edges = new ArrayList<>();

        //注意：节点不是从0而是从1开始，对每个节点都减1，节点从1开始变为从0开始
        for (int i = 0; i < n; i++) {
            edges.add(new ArrayList<>());
        }

        for (int i = 0; i < times.length; i++) {
            //节点u和节点v减1，节点从1开始变为从0开始
            int u = times[i][0] - 1;
            int v = times[i][1] - 1;
            int weight = times[i][2];

            edges.get(u).add(new int[]{v, weight});
        }

        //节点k-1到其他节点的最短路径长度数组
        int[] distance = new int[n];

        //distance数组初始化，初始化为int最大值表示节点k-1无法到达节点i
        for (int i = 0; i < n; i++) {
            distance[i] = Integer.MAX_VALUE;
        }

        //初始化，节点k-1到节点k-1的最短路径长度为0
        distance[k - 1] = 0;

        //小根堆，arr[0]：当前节点，arr[1]：节点k-1到当前节点的路径长度
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                return arr1[1] - arr2[1];
            }
        });

        //起始节点k-1入堆
        priorityQueue.offer(new int[]{k - 1, 0});

        while (!priorityQueue.isEmpty()) {
            int[] arr = priorityQueue.poll();
            //当前节点u
            int u = arr[0];
            //节点k-1到节点u的路径长度
            int curDistance = arr[1];

            //curDistance大于distance[u]，则当前节点u不能作为中间节点更新节点k-1到其他节点的最短路径长度，直接进行下次循环
            if (curDistance > distance[u]) {
                continue;
            }

            //遍历节点u的邻接节点v
            for (int[] arr2 : edges.get(u)) {
                //节点u的邻接节点v
                int v = arr2[0];
                //节点u到节点v边的权值
                int weight = arr2[1];

                //节点u作为中间节点更新节点k-1到其他节点的最短路径长度，更新distance[v]，节点v入堆
                if (curDistance + weight < distance[v]) {
                    distance[v] = curDistance + weight;
                    priorityQueue.offer(new int[]{v, distance[v]});
                }
            }
        }

        //节点k-1到其他节点的最短路径长度中的最大值
        int max = 0;

        for (int i = 0; i < n; i++) {
            max = Math.max(max, distance[i]);
        }

        //max等于Integer.MAX_VALUE，则说明节点k-1有无法到达的节点，返回-1；否则，返回max
        return max == Integer.MAX_VALUE ? -1 : max;
    }

    /**
     * Floyd，计算任意两个节点之间的最短路径
     * 注意：Floyd可以处理带负权值的图，而Dijkstra不能处理带负权值的图
     * 时间复杂度O(n^3)，空间复杂度O(n^2) (邻接矩阵需要O(n^2))
     *
     * @param times
     * @param n
     * @param k
     * @return
     */
    public int networkDelayTime3(int[][] times, int n, int k) {
        //邻接矩阵
        int[][] edges = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    edges[i][j] = 0;
                } else {
                    //注意：只能初始化不存在边的两个节点的距离为int最大值，不能初始化为-1，因为floyd可以求带负权值的边
                    edges[i][j] = Integer.MAX_VALUE;
                }
            }
        }

        for (int i = 0; i < times.length; i++) {
            //节点u和节点v减1，节点从1开始变为从0开始
            int u = times[i][0] - 1;
            int v = times[i][1] - 1;
            int weight = times[i][2];

            edges[u][v] = weight;
        }

        for (int m = 0; m < n; m++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    //节点i到节点k和节点k到节点j都存在路径时，才计算节点i到节点j的最短路径长度
                    if (edges[i][m] != Integer.MAX_VALUE && edges[m][j] != Integer.MAX_VALUE) {
                        edges[i][j] = Math.min(edges[i][j], edges[i][m] + edges[m][j]);
                    }
                }
            }
        }

        //节点k-1到其他节点的最短路径长度中的最大值
        int max = 0;

        for (int i = 0; i < n; i++) {
            max = Math.max(max, edges[k - 1][i]);
        }

        //max等于Integer.MAX_VALUE，则说明节点k-1有无法到达的节点，返回-1；否则，返回max
        return max == Integer.MAX_VALUE ? -1 : max;
    }
}
