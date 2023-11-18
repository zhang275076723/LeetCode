package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/11/21 08:33
 * @Author zsy
 * @Description 前往目标的最小代价 图中最短路径类比Problem399、Problem743、Problem787、Problem882、Problem1293、Problem1334、Problem1368、Problem1462、Problem1514、Problem1631、Problem1786、Problem1928、Problem1976、Problem2045、Problem2203、Problem2290、Problem2093、Dijkstra
 * 给你一个数组 start ，其中 start = [startX, startY] 表示你的初始位置位于二维空间上的 (startX, startY) 。
 * 另给你一个数组 target ，其中 target = [targetX, targetY] 表示你的目标位置 (targetX, targetY) 。
 * 从位置 (x1, y1) 到空间中任一其他位置 (x2, y2) 的代价是 |x2 - x1| + |y2 - y1| 。
 * 给你一个二维数组 specialRoads ，表示空间中存在的一些特殊路径。
 * 其中 specialRoads[i] = [x1i, y1i, x2i, y2i, costi] 表示第 i 条特殊路径可以从 (x1i, y1i) 到 (x2i, y2i) ，但成本等于 costi 。
 * 你可以使用每条特殊路径任意次数。
 * 返回从 (startX, startY) 到 (targetX, targetY) 所需的最小代价。
 * <p>
 * 输入：start = [1,1], target = [4,5], specialRoads = [[1,2,3,3,2],[3,4,4,5,1]]
 * 输出：5
 * 解释：从 (1,1) 到 (4,5) 的最优路径如下：
 * - (1,1) -> (1,2) ，移动的代价是 |1 - 1| + |2 - 1| = 1 。
 * - (1,2) -> (3,3) ，移动使用第一条特殊路径，代价是 2 。
 * - (3,3) -> (3,4) ，移动的代价是 |3 - 3| + |4 - 3| = 1.
 * - (3,4) -> (4,5) ，移动使用第二条特殊路径，代价是 1 。
 * 总代价是 1 + 2 + 1 + 1 = 5 。
 * 可以证明无法以小于 5 的代价完成从 (1,1) 到 (4,5) 。
 * <p>
 * 输入：start = [3,2], target = [5,7], specialRoads = [[3,2,3,4,4],[3,3,5,5,5],[3,4,5,6,6]]
 * 输出：7
 * 解释：最优路径是不使用任何特殊路径，直接以 |5 - 3| + |7 - 2| = 7 的代价从初始位置到达目标位置。
 * <p>
 * start.length == target.length == 2
 * 1 <= startX <= targetX <= 10^5
 * 1 <= startY <= targetY <= 10^5
 * 1 <= specialRoads.length <= 200
 * specialRoads[i].length == 5
 * startX <= x1i, x2i <= targetX
 * startY <= y1i, y2i <= targetY
 * 1 <= costi <= 10^5
 */
public class Problem2662 {
    public static void main(String[] args) {
        Problem2662 problem2662 = new Problem2662();
//        int[] start = {1, 1};
//        int[] target = {4, 5};
//        int[][] specialRoads = {{1, 2, 3, 3, 2}, {3, 4, 4, 5, 1}};
        int[] start = {3, 2};
        int[] target = {5, 7};
        int[][] specialRoads = {{3, 2, 3, 4, 4}, {3, 3, 5, 5, 5}, {3, 4, 5, 6, 6}};
        System.out.println(problem2662.minimumCost(start, target, specialRoads));
        System.out.println(problem2662.minimumCost2(start, target, specialRoads));
    }

    /**
     * Dijkstra求节点(startX,startY)到节点(targetX,targetY)的最短路径长度
     * 难点：将二维节点转换为一维节点存储，保证不同的二维节点不会映射到同一个一维节点，
     * 节点都在int范围内，横坐标左移32位加上纵坐标，得到long范围内唯一的一维节点
     * 时间复杂度O(n^2)，空间复杂度O(n) (n=specialRoads.length，2n+2为图中节点的个数，图为O(n)个节点的完全图)
     *
     * @param start
     * @param target
     * @param specialRoads
     * @return
     */
    public int minimumCost(int[] start, int[] target, int[][] specialRoads) {
        //二维节点转换为一维节点存储的集合，使用long存储，保证不同的二维节点不会映射到同一个一维节点
        Set<Long> vertexSet = new HashSet<>();

        //起始节点和目标节点转换为一维节点加入vertexSet
        vertexSet.add(convert(start[0], start[1]));
        vertexSet.add(convert(target[0], target[1]));

        //特殊路径中的节点转换为一维节点加入vertexSet
        for (int i = 0; i < specialRoads.length; i++) {
            int x1 = specialRoads[i][0];
            int y1 = specialRoads[i][1];
            int x2 = specialRoads[i][2];
            int y2 = specialRoads[i][3];

            vertexSet.add(convert(x1, y1));
            vertexSet.add(convert(x2, y2));
        }

        //邻接表，key：当前一维节点，value：当前节点的邻接节点数组集合，arr[0]：当前节点的邻接节点，arr[1]：当前节点到邻接节点边的长度
        Map<Long, List<long[]>> edges = new HashMap<>();

        //建图，每个节点和其他节点都有边，即图为完全图
        for (long u : vertexSet) {
            for (long v : vertexSet) {
                if (u != v) {
                    if (!edges.containsKey(u)) {
                        edges.put(u, new ArrayList<>());
                    }
                    if (!edges.containsKey(v)) {
                        edges.put(v, new ArrayList<>());
                    }

                    //一维节点u转换为二维节点(x1,y1)
                    int x1 = (int) (u >>> 32);
                    int y1 = (int) (u & Integer.MAX_VALUE);
                    //一维节点v转换为二维节点(x2,y2)
                    int x2 = (int) (v >>> 32);
                    int y2 = (int) (v & Integer.MAX_VALUE);

                    //(x1,y1)和(x2,y2)之间边的长度
                    int weight = Math.abs(x1 - x2) + Math.abs(y1 - y2);

                    edges.get(u).add(new long[]{v, weight});
                    edges.get(v).add(new long[]{u, weight});
                }
            }
        }

        //特殊路径的边也加入图中
        for (int i = 0; i < specialRoads.length; i++) {
            int x1 = specialRoads[i][0];
            int y1 = specialRoads[i][1];
            int x2 = specialRoads[i][2];
            int y2 = specialRoads[i][3];

            long u = convert(x1, y1);
            long v = convert(x2, y2);

            if (!edges.containsKey(u)) {
                edges.put(u, new ArrayList<>());
            }

            edges.get(u).add(new long[]{v, specialRoads[i][4]});
        }

        //一维起始节点到其他一维节点的最短路径长度map
        Map<Long, Integer> distanceMap = new HashMap<>();
        //一维节点访问集合
        Set<Long> visitedSet = new HashSet<>();

        //distanceMap初始化，初始化为int最大值表示一维起始节点无法到达一维节点u
        for (long u : vertexSet) {
            distanceMap.put(u, Integer.MAX_VALUE);
        }

        //初始化，一维起始节点到一维起始节点的最短路径长度为0
        distanceMap.put(convert(start[0], start[1]), 0);

        //每次从未访问节点中选择距离一维起始节点最短路径长度的节点u，节点u作为中间节点更新一维起始节点到其他节点的最短路径长度
        for (int i = 0; i < edges.size(); i++) {
            //初始化distanceMap中未访问节点中选择距离一维起始节点最短路径长度的一维节点u
            long u = -1;

            //未访问节点中选择距离一维起始节点最短路径长度的一维节点u
            for (long v : vertexSet) {
                if (!visitedSet.contains(v) && (u == -1 || distanceMap.get(v) < distanceMap.get(u))) {
                    u = v;
                }
            }

            //设置节点u已访问，表示已经得到一维起始节点到节点u的最短路径长度
            visitedSet.add(u);

            //已经得到一维起始节点到一维目标节点的最短路径长度，直接返回distanceMap.get(u)
            if (u == convert(target[0], target[1])) {
                return distanceMap.get(u);
            }

            //一维节点u作为中间节点更新一维起始节点到其他节点的最短路径长度
            for (long[] arr : edges.get(u)) {
                long v = arr[0];
                int weight = (int) arr[1];

                if (!visitedSet.contains(v) && distanceMap.get(u) + weight < distanceMap.get(v)) {
                    distanceMap.put(v, distanceMap.get(u) + weight);
                }
            }
        }

        //遍历结束，不用返回-1，因为完全图肯定存在一维起始节点到一维目标节点的最短路径长度
        return distanceMap.get(convert(target[0], target[1]));
    }

    /**
     * 堆优化Dijkstra求节点(startX,startY)到节点(targetX,targetY)的最短路径长度
     * 难点：将二维节点转换为一维节点存储，保证不同的二维节点不会映射到同一个一维节点，
     * 节点都在int范围内，横坐标左移32位加上纵坐标，得到long范围内唯一的一维节点
     * 时间复杂度O(n^2*logn)，空间复杂度O(n) (n=specialRoads.length，2n+2为图中节点的个数，图为O(n)个节点的完全图)
     * (堆优化Dijkstra的时间复杂度O(mlogm)，其中m为图中边的个数，完全图边的个数O(n^2)，所以时间复杂度O(n^2*logn))
     *
     * @param start
     * @param target
     * @param specialRoads
     * @return
     */
    public int minimumCost2(int[] start, int[] target, int[][] specialRoads) {
        //二维节点转换为一维节点存储的集合，使用long存储，保证不同的二维节点不会映射到同一个一维节点
        Set<Long> vertexSet = new HashSet<>();

        //起始节点和目标节点转换为一维节点加入vertexSet
        vertexSet.add(convert(start[0], start[1]));
        vertexSet.add(convert(target[0], target[1]));

        //特殊路径中的节点转换为一维节点加入vertexSet
        for (int i = 0; i < specialRoads.length; i++) {
            int x1 = specialRoads[i][0];
            int y1 = specialRoads[i][1];
            int x2 = specialRoads[i][2];
            int y2 = specialRoads[i][3];

            vertexSet.add(convert(x1, y1));
            vertexSet.add(convert(x2, y2));
        }

        //邻接表，key：当前一维节点，value：当前节点的邻接节点数组集合，arr[0]：当前节点的邻接节点，arr[1]：当前节点到邻接节点边的长度
        Map<Long, List<long[]>> edges = new HashMap<>();

        //建图，每个节点和其他节点都有边，即图为完全图
        for (long u : vertexSet) {
            for (long v : vertexSet) {
                if (u != v) {
                    if (!edges.containsKey(u)) {
                        edges.put(u, new ArrayList<>());
                    }
                    if (!edges.containsKey(v)) {
                        edges.put(v, new ArrayList<>());
                    }

                    //一维节点u转换为二维节点(x1,y1)
                    int x1 = (int) (u >>> 32);
                    int y1 = (int) (u & Integer.MAX_VALUE);
                    //一维节点v转换为二维节点(x2,y2)
                    int x2 = (int) (v >>> 32);
                    int y2 = (int) (v & Integer.MAX_VALUE);

                    //(x1,y1)和(x2,y2)之间边的长度
                    int weight = Math.abs(x1 - x2) + Math.abs(y1 - y2);

                    edges.get(u).add(new long[]{v, weight});
                    edges.get(v).add(new long[]{u, weight});
                }
            }
        }

        //特殊路径的边也加入图中
        for (int i = 0; i < specialRoads.length; i++) {
            int x1 = specialRoads[i][0];
            int y1 = specialRoads[i][1];
            int x2 = specialRoads[i][2];
            int y2 = specialRoads[i][3];

            long u = convert(x1, y1);
            long v = convert(x2, y2);

            if (!edges.containsKey(u)) {
                edges.put(u, new ArrayList<>());
            }

            edges.get(u).add(new long[]{v, specialRoads[i][4]});
        }

        //一维起始节点到其他一维节点的最短路径长度map
        Map<Long, Integer> distanceMap = new HashMap<>();

        //distanceMap初始化，初始化为int最大值表示一维起始节点无法到达一维节点u
        for (long u : vertexSet) {
            distanceMap.put(u, Integer.MAX_VALUE);
        }

        //初始化，一维起始节点到一维起始节点的最短路径长度为0
        distanceMap.put(convert(start[0], start[1]), 0);

        //小根堆，arr[0]：当前一维节点，arr[1]：一维起始节点到当前一维节点的路径长度
        PriorityQueue<long[]> priorityQueue = new PriorityQueue<>(new Comparator<long[]>() {
            @Override
            public int compare(long[] arr1, long[] arr2) {
                //arr[1]是int类型转换为long的，所以相减之后再转换为int不会溢出，也就不必要写为Long.compare(a, b);
                return (int) (arr1[1] - arr2[1]);
            }
        });

        //一维起始节点入堆
        priorityQueue.offer(new long[]{convert(start[0], start[1]), 0});

        while (!priorityQueue.isEmpty()) {
            long[] arr = priorityQueue.poll();
            //当前一维节点u
            long u = arr[0];
            //一维起始节点到一维节点u的路径长度
            int curDistance = (int) arr[1];

            //curDistance大于distanceMap.get(u)，则当前一维节点u不能作为中间节点更新一维起始节点到其他节点的最短路径长度，直接进行下次循环
            if (curDistance > distanceMap.get(u)) {
                continue;
            }

            //小根堆保证第一次访问到一维目标节点，则得到一维起始节点到一维目标节点的最短路径长度，直接返回curDistance
            if (u == convert(target[0], target[1])) {
                return curDistance;
            }

            //遍历一维节点u的邻接节点v
            for (long[] arr2 : edges.get(u)) {
                //邻接节点v
                long v = arr2[0];
                //一维节点u到邻接节点v边的长度
                int weight = (int) arr2[1];

                //一维节点u作为中间节点更新一维起始节点到其他节点的最短路径长度，更新distanceMap.get(v)，一维节点v入堆
                if (curDistance + weight < distanceMap.get(v)) {
                    distanceMap.put(v, curDistance + weight);
                    priorityQueue.offer(new long[]{v, distanceMap.get(v)});
                }
            }
        }

        //遍历结束，不用返回-1，因为完全图肯定存在一维起始节点到一维目标节点的最短路径长度
        return distanceMap.get(convert(target[0], target[1]));
    }

    /**
     * 二维节点转换为一维节点，保证不同的二维节点不会映射到同一个一维节点，
     * 节点都在int范围内，横坐标左移32位加上纵坐标，得到long范围内唯一的一维节点
     *
     * @return
     */
    private long convert(int x, int y) {
        return ((long) x << 32) | y;
    }
}
