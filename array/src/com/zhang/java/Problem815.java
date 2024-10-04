package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/12/31 08:29
 * @Author zsy
 * @Description 公交路线 阿里机试题 类比Problem1654、Problem2059、LCP09 双向bfs类比Problem126、Problem127、Problem433、Problem752、Problem1345、Problem2059
 * 给你一个数组 routes ，表示一系列公交线路，其中每个 routes[i] 表示一条公交线路，第 i 辆公交车将会在上面循环行驶。
 * 例如，路线 routes[0] = [1, 5, 7] 表示第 0 辆公交车会一直按序列 1 -> 5 -> 7 -> 1 -> 5 -> 7 -> 1 -> ... 这样的车站路线行驶。
 * 现在从 source 车站出发（初始时不在公交车上），要前往 target 车站。
 * 期间仅可乘坐公交车。
 * 求出 最少乘坐的公交车数量 。
 * 如果不可能到达终点车站，返回 -1 。
 * <p>
 * 输入：routes = [[1,2,7],[3,6,7]], source = 1, target = 6
 * 输出：2
 * 解释：最优策略是先乘坐第一辆公交车到达车站 7 , 然后换乘第二辆公交车到车站 6 。
 * <p>
 * 输入：routes = [[7,12],[4,5,15],[6],[15,19],[9,12,13]], source = 15, target = 12
 * 输出：-1
 * <p>
 * 1 <= routes.length <= 500.
 * 1 <= routes[i].length <= 10^5
 * routes[i] 中的所有值 互不相同
 * sum(routes[i].length) <= 10^5
 * 0 <= routes[i][j] < 10^6
 * 0 <= source, target < 10^6
 */
public class Problem815 {
    public static void main(String[] args) {
        Problem815 problem815 = new Problem815();
        int[][] routes = {{1, 2, 7}, {3, 6, 7}};
        int source = 1;
        int target = 6;
//        int[][] routes = {{7, 12}, {4, 5, 15}, {6}, {15, 19}, {9, 12, 13}};
//        int source = 12;
//        int target = 15;
        System.out.println(problem815.numBusesToDestination(routes, source, target));
        System.out.println(problem815.numBusesToDestination2(routes, source, target));
    }

    /**
     * bfs
     * 注意：bfs队列只能存储公交车，不能存储车站，因为一个公交车对应多个车站，如果存储车站，则会超时
     * 时间复杂度O(sum(routes[i].length)+mn)，空间复杂度O(mn) (n=routes.length，即公交车个数，m=车站个数)
     *
     * @param routes
     * @param source
     * @param target
     * @return
     */
    public int numBusesToDestination(int[][] routes, int source, int target) {
        if (source == target) {
            return 0;
        }

        //key：当前车站，value：经过当前车站的公交车集合
        Map<Integer, Set<Integer>> stationBusMap = new HashMap<>();
        //存储公交车的队列
        //注意：bfs队列只能存储公交车，不能存储车站，因为一个公交车对应多个车站，如果存储车站，则会超时
        Queue<Integer> queue = new LinkedList<>();
        //公交车访问集合
        Set<Integer> visitedSet = new HashSet<>();

        for (int i = 0; i < routes.length; i++) {
            //当前公交车
            int curBus = i;

            //当前公交车经过的车站curStation
            for (int curStation : routes[curBus]) {
                //当前公交车经过source车站，则当前公交车curBus入队
                if (curStation == source) {
                    queue.offer(curBus);
                    visitedSet.add(curBus);
                }

                if (!stationBusMap.containsKey(curStation)) {
                    stationBusMap.put(curStation, new HashSet<>());
                }

                stationBusMap.get(curStation).add(curBus);
            }
        }

        //从source车站到target车站的最小乘坐公交车数量
        int count = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                //当前公交车
                int curBus = queue.poll();

                //当前公交车经过的车站curStation对应未访问的公交车入队
                for (int curStation : routes[curBus]) {
                    //找到了从source车站到target车站的最小乘坐公交车数量，返回count+1
                    if (curStation == target) {
                        return count + 1;
                    }

                    //当前公交车经过的车站对应的公交车集合
                    Set<Integer> busSet = stationBusMap.get(curStation);

                    for (int nextBus : busSet) {
                        if (!visitedSet.contains(nextBus)) {
                            queue.offer(nextBus);
                            visitedSet.add(nextBus);
                        }
                    }
                }
            }

            //bfs每次往外扩一层，最小乘坐公交车数量加1
            count++;
        }

        //bfs遍历结束，则source车站无法到target车站，返回-1
        return -1;
    }

    /**
     * 双向bfs
     * 注意：bfs队列只能存储公交车，不能存储车站，因为一个公交车对应多个车站，如果存储车站，则会超时
     * 从source和target同时开始bfs，bfs每次往外扩一层，将当前队列当前层中所有车站得到的下一个车站加入当前队列中，
     * 直至一个队列中包含了另一个队列中的车站，即双向bfs相交，或者全部遍历完都没有找到target，返回-1
     * 注意：双向bfs优先遍历两个队列中较少的队列，因为较少的队列，扩展一层得到的元素少，能够加快查询速度
     * 时间复杂度O(sum(routes[i].length)+mn)，空间复杂度O(mn) (n=routes.length，即公交车个数，m=车站个数)
     *
     * @param routes
     * @param source
     * @param target
     * @return
     */
    public int numBusesToDestination2(int[][] routes, int source, int target) {
        if (source == target) {
            return 0;
        }

        //key：当前车站，value：经过当前车站的公交车集合
        Map<Integer, Set<Integer>> stationBusMap = new HashMap<>();
        //存储公交车的队列
        //注意：bfs队列只能存储公交车，不能存储车站，因为一个公交车对应多个车站，如果存储车站，则会超时
        Queue<Integer> queue1 = new LinkedList<>();
        Queue<Integer> queue2 = new LinkedList<>();
        //公交车访问集合
        Set<Integer> visitedSet1 = new HashSet<>();
        Set<Integer> visitedSet2 = new HashSet<>();

        for (int i = 0; i < routes.length; i++) {
            //当前公交车
            int curBus = i;

            //当前公交车经过的车站curStation
            for (int curStation : routes[curBus]) {
                //当前公交车经过source车站，则当前公交车curBus入队queue1
                if (curStation == source) {
                    queue1.offer(curBus);
                    visitedSet1.add(curBus);
                }

                //当前公交车经过target车站，则当前公交车curBus入队queue2
                if (curStation == target) {
                    queue2.offer(curBus);
                    visitedSet2.add(curBus);
                }

                if (!stationBusMap.containsKey(curStation)) {
                    stationBusMap.put(curStation, new HashSet<>());
                }

                stationBusMap.get(curStation).add(curBus);
            }
        }

        //从source车站到target车站的最小乘坐公交车数量
        int count = 0;

        while (!queue1.isEmpty() && !queue2.isEmpty()) {
            //双向bfs优先遍历两个队列中较少的队列，因为较少的队列，扩展一层得到的元素少，能够加快查询速度
            if (queue1.size() > queue2.size()) {
                Queue<Integer> tempQueue = queue1;
                queue1 = queue2;
                queue2 = tempQueue;
                Set<Integer> tempSet = visitedSet1;
                visitedSet1 = visitedSet2;
                visitedSet2 = tempSet;
            }

            int size = queue1.size();

            for (int i = 0; i < size; i++) {
                //当前公交车
                int curBus = queue1.poll();

                //visitedSet2中存在curBus，即双向bfs相交，则找到了从source车站到target车站的最小乘坐公交车数量，返回count+1
                if (visitedSet2.contains(curBus)) {
                    return count + 1;
                }

                //当前公交车经过的车站curStation对应未访问的公交车入队
                for (int curStation : routes[curBus]) {
                    //当前公交车经过的车站对应的公交车集合
                    Set<Integer> busSet = stationBusMap.get(curStation);

                    for (int nextBus : busSet) {
                        if (!visitedSet1.contains(nextBus)) {
                            queue1.offer(nextBus);
                            visitedSet1.add(nextBus);
                        }
                    }
                }
            }

            //bfs每次往外扩一层，最小乘坐公交车数量加1
            count++;
        }

        //bfs遍历结束，则source车站无法到target车站，返回-1
        return -1;
    }
}
