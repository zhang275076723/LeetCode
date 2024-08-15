package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/11/2 08:00
 * @Author zsy
 * @Description Prim求最小生成树 最小生成树类比Problem778、Problem1135、Problem1168、Problem1489、Problem1584、Problem1631
 */
public class Prim {
    public static void main(String[] args) {
        Prim prim = new Prim();
        int[][] edges = {
                {0, -1, 1, 2, -1, 3, -1},
                {-1, 0, 4, 8, 7, -1, -1},
                {1, 4, 0, 5, -1, -1, -1},
                {2, 8, 5, 0, -1, -1, -1},
                {-1, 7, -1, -1, 0, -1, 6},
                {3, -1, -1, -1, -1, 0, 9},
                {-1, -1, -1, -1, 6, 9, 0}
        };
//        List<int[]> list = prim.getMinSpanTree(edges);
        List<int[]> list = prim.getMinSpanTree2(edges);
        for (int[] arr : list) {
            System.out.println(Arrays.toString(arr));
        }
    }

    /**
     * Prim求最小生成树
     * 选择一个节点作为起始节点，加入已访问节点集合，更新未访问节点到已访问节点集合的最短路径长度，
     * 从未访问节点中选择距离已访问节点集合最短路径长度的节点u，节点u作为中间节点更新未访问节点到已访问节点集合的最短路径长度，
     * 节点u和已访问节点集合中某个节点构成的最小生成树边，当前边加入结果集合；
     * 不存在未访问节点中距离已访问节点集合最短路径长度的节点，则无法形成最小生成树
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param edges
     * @return
     */
    public List<int[]> getMinSpanTree(int[][] edges) {
        //图中节点的个数
        int n = edges.length;
        //未访问节点到已访问节点集合的最短路径长度数组
        int[] distance = new int[n];
        //未访问节点到已访问节点集合是通过哪个节点得到的最短路径长度数组，
        //通过vertexArr得到当前节点和已访问节点集合某个节点构成的最小生成树边
        int[] vertexArr = new int[n];
        //节点访问数组，visited[u]为true，表示已经得到节点u到已访问节点集合的最短路径长度
        boolean[] visited = new boolean[n];

        //distance数组初始化，初始化为int最大值表示节点i无法到达已访问节点集合
        //vertexArr数组初始化，初始化为-1表示节点i无法到达已访问节点集合
        for (int i = 0; i < n; i++) {
            distance[i] = Integer.MAX_VALUE;
            vertexArr[i] = -1;
        }

        //节点0作为起始节点
        visited[0] = true;

        //初始化，节点0加入已访问节点集合，更新其他节点到已访问节点集合的最短路径长度
        for (int i = 0; i < n; i++) {
            if (edges[0][i] != -1) {
                distance[i] = edges[0][i];
                //节点i到已访问节点集合是通过节点0得到的最短路径长度
                vertexArr[i] = 0;
            }
        }

        //存储最小生成树边的集合
        List<int[]> list = new ArrayList<>();

        //n个节点的最小生成树只需要遍历n-1次，得到n-1条边
        for (int i = 0; i < n - 1; i++) {
            //初始化distance数组中未访问节点中选择距离已访问节点集合最短路径长度的节点u
            int u = -1;

            //未访问节点中选择距离已访问节点集合最短路径长度的节点u
            for (int j = 0; j < n; j++) {
                if (!visited[j] && (u == -1 || distance[j] < distance[u])) {
                    u = j;
                }
            }

            //不存在未访问节点中距离已访问节点集合最短路径长度的节点，则无法形成最小生成树，直接返回
            if (distance[u] == Integer.MAX_VALUE) {
                return new ArrayList<>();
            }

            //设置节点u已访问，表示节点u和vertexArr[u]构成的最小生成树边
            visited[u] = true;
            //节点u和vertexArr[u]构成的最小生成树边，加入结果集合
            list.add(new int[]{u, vertexArr[u]});

            //节点u作为中间节点更新未访问节点到已访问节点集合的最短路径长度
            for (int j = 0; j < n; j++) {
                if (!visited[j] && edges[u][j] != -1 && edges[u][j] < distance[j]) {
                    distance[j] = edges[u][j];
                    //节点j到已访问节点集合是通过节点u得到的最短路径长度
                    vertexArr[j] = u;
                }
            }
        }

        return list;
    }

    /**
     * 堆优化Prim求最小生成树
     * 选择一个节点作为起始节点，加入已访问节点集合，更新未访问节点到已访问节点集合的最短路径长度，
     * 通过优先队列找未访问节点中距离已访问节点集合最短路径长度的节点u，节点u作为中间节点更新未访问节点到已访问节点集合的最短路径长度，
     * 节点u和已访问节点集合中某个节点构成的最小生成树边，当前边加入结果集合；
     * 不存在未访问节点中距离已访问节点集合最短路径长度的节点，则无法形成最小生成树
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param edges
     * @return
     */
    public List<int[]> getMinSpanTree2(int[][] edges) {
        //图中节点的个数
        int n = edges.length;
        //未访问节点到已访问节点集合的最短路径长度数组
        int[] distance = new int[n];
        //未访问节点到已访问节点集合是通过哪个节点得到的最短路径长度数组，
        //通过vertexArr得到当前节点和已访问节点集合某个节点构成的最小生成树边
        int[] vertexArr = new int[n];
        //节点访问数组，visited[u]为true，表示已经得到节点u到已访问节点集合的最短路径长度
        boolean[] visited = new boolean[n];

        //distance数组初始化，初始化为int最大值表示节点i无法到达已访问节点集合
        //vertexArr数组初始化，初始化为-1表示节点i无法到达已访问节点集合
        for (int i = 0; i < n; i++) {
            distance[i] = Integer.MAX_VALUE;
            vertexArr[i] = -1;
        }

        //节点0作为起始节点
        visited[0] = true;

        //小根堆，arr[0]：当前节点，arr[i]：当前节点到已访问节点集合的路径长度
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                return arr1[1] - arr2[1];
            }
        });

        //节点0作为起始节点，和节点0相连的边入堆
        for (int i = 0; i < n; i++) {
            if (edges[0][i] != -1) {
                distance[i] = edges[0][i];
                //节点i到已访问节点集合是通过节点0得到的最短路径长度
                vertexArr[i] = 0;
                priorityQueue.offer(new int[]{i, distance[i]});
            }
        }

        //存储最小生成树边的集合
        List<int[]> list = new ArrayList<>();

        while (!priorityQueue.isEmpty()) {
            int[] arr = priorityQueue.poll();
            //通过优先队列找未访问节点到已访问节点集合的最短路径长度的节点u
            int u = arr[0];

            if (visited[u]) {
                continue;
            }

            //设置节点u已访问，表示节点u和vertexArr[u]构成的最小生成树边
            visited[u] = true;
            //节点u和vertexArr[u]构成的最小生成树边，加入结果集合
            list.add(new int[]{u, vertexArr[u]});

            //节点u作为中间节点更新未访问节点到已访问节点集合的最短路径长度
            for (int i = 0; i < n; i++) {
                if (!visited[i] && edges[u][i] != -1 && edges[u][i] < distance[i]) {
                    distance[i] = edges[u][i];
                    //节点i到已访问节点集合是通过节点u得到的最短路径长度
                    vertexArr[i] = u;
                    //节点i入堆，用于下一次找未访问节点到已访问节点集合的最短路径长度的节点u
                    priorityQueue.offer(new int[]{i, distance[i]});
                }
            }
        }

        //n个节点的最小生成树需要n-1条边，没有n-1条边，则不存在最小生成树
        return list.size() == n - 1 ? list : new ArrayList<>();
    }
}
