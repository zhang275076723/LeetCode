package com.zhang.java;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Date 2024/1/12 08:06
 * @Author zsy
 * @Description 最低加油次数 百度机试题 类比Problem134、Problem774 优先队列类比
 * 汽车从起点出发驶向目的地，该目的地位于出发位置东面 target 英里处。
 * 沿途有加油站，用数组 stations 表示。
 * 其中 stations[i] = [positioni, fueli] 表示第 i 个加油站位于出发位置东面 positioni 英里处，并且有 fueli 升汽油。
 * 假设汽车油箱的容量是无限的，其中最初有 startFuel 升燃料。它每行驶 1 英里就会用掉 1 升汽油。
 * 当汽车到达加油站时，它可能停下来加油，将所有汽油从加油站转移到汽车中。
 * 为了到达目的地，汽车所必要的最低加油次数是多少？如果无法到达目的地，则返回 -1 。
 * 注意：如果汽车到达加油站时剩余燃料为 0，它仍然可以在那里加油。如果汽车到达目的地时剩余燃料为 0，仍然认为它已经到达目的地。
 * <p>
 * 输入：target = 1, startFuel = 1, stations = []
 * 输出：0
 * 解释：可以在不加油的情况下到达目的地。
 * <p>
 * 输入：target = 100, startFuel = 1, stations = [[10,100]]
 * 输出：-1
 * 解释：无法抵达目的地，甚至无法到达第一个加油站。
 * <p>
 * 输入：target = 100, startFuel = 10, stations = [[10,60],[20,30],[30,30],[60,40]]
 * 输出：2
 * 解释：
 * 出发时有 10 升燃料。
 * 开车来到距起点 10 英里处的加油站，消耗 10 升燃料。将汽油从 0 升加到 60 升。
 * 然后，从 10 英里处的加油站开到 60 英里处的加油站（消耗 50 升燃料），
 * 并将汽油从 10 升加到 50 升。然后开车抵达目的地。
 * 沿途在两个加油站停靠，所以返回 2 。
 * <p>
 * 1 <= target, startFuel <= 10^9
 * 0 <= stations.length <= 500
 * 1 <= positioni < positioni+1 < target
 * 1 <= fueli < 10^9
 */
public class Problem871 {
    public static void main(String[] args) {
        Problem871 problem871 = new Problem871();
        int target = 100;
        int startFuel = 10;
        int[][] stations = {{10, 60}, {20, 30}, {30, 30}, {60, 40}};
        System.out.println(problem871.minRefuelStops(target, startFuel, stations));
    }

    /**
     * 优先队列，大根堆
     * 起点距离加油站的距离stations[i][0]已经按照由小到大排序，所以不需要进行排序，
     * 将能够访问到的加油站汽油stations[i][1]加入大根堆，此时并不急于加油，而是当汽车剩余油量不能到达下一个加油站时，
     * 大根堆堆顶元素出堆，即选择当前能够到达的加油站中stations[i][1]最大的加油站给汽车加油，则到达终点的加油次数即为最少加油次数
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param target
     * @param startFuel
     * @param stations
     * @return
     */
    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        //初始汽车剩余油量大于等于target，则不需要加油，返回0
        if (startFuel >= target) {
            return 0;
        }

        //优先队列，大根堆，存放能够访问到的加油站汽油stations[i][1]
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return b - a;
            }
        });

        int n = stations.length;
        //最少加油次数
        int count = 0;
        //当前汽车剩余油量
        int remainFuel = startFuel;
        //汽车经过的前一个加油站距离起点的位置
        int preStation = 0;

        //stations已经按照stations[i][0]由小到大排序，则不需要再排序
        for (int i = 0; i < n; i++) {
            //汽车从上一个加油站到当前加油站剩余油量
            remainFuel = remainFuel - (stations[i][0] - preStation);

            //当前汽车剩余油量小于0，则大根堆堆顶元素出堆，即选择当前能够到达的加油站中stations[i][1]最大的加油站给汽车加油
            while (remainFuel < 0 && !priorityQueue.isEmpty()) {
                remainFuel = remainFuel + priorityQueue.poll();
                count++;
            }

            //加油之后汽车剩余油量仍小于0，即不能到达target，返回-1
            if (remainFuel < 0) {
                return -1;
            }

            //更新汽车经过的最后一个加油站距离起点的位置
            preStation = stations[i][0];
            //当前加油站汽油stations[i][1]加入大根堆
            priorityQueue.offer(stations[i][1]);
        }

        //汽车从最后一个加油站到target
        remainFuel = remainFuel - (target - preStation);

        //当前汽车剩余油量小于0，则大根堆堆顶加油站汽油出堆，给汽车加油
        while (remainFuel < 0 && !priorityQueue.isEmpty()) {
            remainFuel = remainFuel + priorityQueue.poll();
            count++;
        }

        //加油之后汽车剩余油量仍小于0，即不能到达target，返回-1
        if (remainFuel < 0) {
            return -1;
        }

        return count;
    }
}
