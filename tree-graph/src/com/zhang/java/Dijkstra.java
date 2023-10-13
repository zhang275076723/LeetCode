package com.zhang.java;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Date 2023/2/12 18:22
 * @Author zsy
 * @Description Dijkstra求单元最短路径 图中最短路径类比Problem399、Problem1462、Problem1786、Problem1976
 */
public class Dijkstra {
    public static void main(String[] args) {
        Dijkstra dijkstra = new Dijkstra();
        int[][] edges = {
                {0, 12, -1, -1, -1, 16, 14},
                {12, 0, 10, -1, -1, 7, -1},
                {-1, 10, 0, 3, 5, 6, -1},
                {-1, -1, 3, 0, 4, -1, -1},
                {-1, -1, 5, 4, 0, 2, 8},
                {16, 7, 6, -1, 2, 0, 9},
                {14, -1, -1, -1, 8, 9, 0}
        };
        int u = 3;
        System.out.println(Arrays.toString(dijkstra.getClosestPath(u, edges)));
        System.out.println(Arrays.toString(dijkstra.getClosestPath2(u, edges)));
    }

    /**
     * dijkstra求节点u到其他节点的最短路径长度
     * 注意：不适合权值为负的图
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param u
     * @param edges
     * @return
     */
    public int[] getClosestPath(int u, int[][] edges) {
        //节点u到其他节点的最短路径长度数组
        int[] distance = new int[edges.length];
        //节点访问数组，visited[v]为true，表示已经得到节点u到节点v的最短路径长度
        boolean[] visited = new boolean[edges.length];

        //distance数组初始化，初始化为int最大值表示节点u无法到达节点i
        for (int i = 0; i < edges.length; i++) {
            distance[i] = Integer.MAX_VALUE;
        }

        //节点u到节点u的最短路径长度为0
        distance[u] = 0;

        //每次从还未得到节点u到其他节点的最短路径长度节点中选择最短路径长度节点v，
        //节点u通过节点v作为中间节点更新节点u能够达到其他节点的最短路径长度
        for (int i = 0; i < edges.length; i++) {
            //初始化distance数组中本次选择的最短路径长度节点v
            int v = -1;

            //从还未得到节点u到其他节点的最短路径节点中选择最短路径节点v
            for (int j = 0; j < edges.length; j++) {
                if (!visited[j] && (v == -1 || distance[j] < distance[v])) {
                    v = j;
                }
            }

            //节点v已访问，表示已经得到节点u到节点v的最短路径长度
            visited[v] = true;

            //节点u通过节点v作为中间节点更新节点u能够达到其他节点的最短路径长度
            for (int j = 0; j < edges.length; j++) {
                if (!visited[j] && edges[v][j] != -1) {
                    distance[j] = Math.min(distance[j], distance[v] + edges[v][j]);
                }
            }
        }

        return distance;
    }

    /**
     * 堆优化dijkstra求节点u到其他节点的最短路径长度
     * 通过优先队列找节点u到未访问节点中最短路径长度的节点
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param u
     * @param edges
     * @return
     */
    public int[] getClosestPath2(int u, int[][] edges) {
        //节点u到其他节点的最短路径长度数组
        int[] distance = new int[edges.length];
        //节点访问数组，visited[v]为true，表示已经得到节点u到节点v的最短路径长度
        boolean[] visited = new boolean[edges.length];

        //distance数组初始化，初始化为int最大值表示节点u无法到达节点i
        for (int i = 0; i < edges.length; i++) {
            distance[i] = Integer.MAX_VALUE;
        }

        //节点u到节点u的最短路径长度为0
        distance[u] = 0;

        //小根堆，arr[0]：当前节点，arr[i]：节点u到当前节点的路径长度
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                return arr1[1] - arr2[1];
            }
        });

        //起始节点u入堆
        priorityQueue.offer(new int[]{u, 0});

        while (!priorityQueue.isEmpty()) {
            int[] arr = priorityQueue.poll();
            //节点u到小根堆中节点的最短路径长度节点v
            int v = arr[0];

            if (visited[v]) {
                continue;
            }

            visited[v] = true;

            //节点u通过节点v作为中间节点更新节点u能够达到其他节点的最短路径长度
            for (int i = 0; i < edges.length; i++) {
                if (!visited[i] && edges[v][i] != -1) {
                    distance[i] = Math.min(distance[i], distance[v] + edges[v][i]);
                    priorityQueue.offer(new int[]{i, distance[i]});
                }
            }
        }

        return distance;
    }
}
