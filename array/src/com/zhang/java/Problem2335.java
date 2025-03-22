package com.zhang.java;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Date 2025/5/3 08:09
 * @Author zsy
 * @Description 装满杯子需要的最短总时长 类比Problem1753、Problem1953
 * 现有一台饮水机，可以制备冷水、温水和热水。
 * 每秒钟，可以装满 2 杯 不同 类型的水或者 1 杯任意类型的水。
 * 给你一个下标从 0 开始、长度为 3 的整数数组 amount ，
 * 其中 amount[0]、amount[1] 和 amount[2] 分别表示需要装满冷水、温水和热水的杯子数量。
 * 返回装满所有杯子所需的 最少 秒数。
 * <p>
 * 输入：amount = [1,4,2]
 * 输出：4
 * 解释：下面给出一种方案：
 * 第 1 秒：装满一杯冷水和一杯温水。
 * 第 2 秒：装满一杯温水和一杯热水。
 * 第 3 秒：装满一杯温水和一杯热水。
 * 第 4 秒：装满一杯温水。
 * 可以证明最少需要 4 秒才能装满所有杯子。
 * <p>
 * 输入：amount = [5,4,4]
 * 输出：7
 * 解释：下面给出一种方案：
 * 第 1 秒：装满一杯冷水和一杯热水。
 * 第 2 秒：装满一杯冷水和一杯温水。
 * 第 3 秒：装满一杯冷水和一杯温水。
 * 第 4 秒：装满一杯温水和一杯热水。
 * 第 5 秒：装满一杯冷水和一杯热水。
 * 第 6 秒：装满一杯冷水和一杯温水。
 * 第 7 秒：装满一杯热水。
 * <p>
 * 输入：amount = [5,0,0]
 * 输出：5
 * 解释：每秒装满一杯冷水。
 * <p>
 * amount.length == 3
 * 0 <= amount[i] <= 100
 */
public class Problem2335 {
    public static void main(String[] args) {
        Problem2335 problem2335 = new Problem2335();
        int[] amount = {1, 4, 2};
        System.out.println(problem2335.fillCups(amount));
        System.out.println(problem2335.fillCups2(amount));
    }

    /**
     * 优先队列，大根堆
     * 时间复杂度O(max(amount[i])*log3)=O(300)=O(1)，空间复杂度O(3)=O(1)
     *
     * @param amount
     * @return
     */
    public int fillCups(int[] amount) {
        //大根堆，存放3种需要装满水的杯子的非空数量
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return b - a;
            }
        });

        for (int count : amount) {
            //大于0的杯子数量入堆
            if (count > 0) {
                priorityQueue.offer(count);
            }
        }

        int time = 0;

        while (priorityQueue.size() == 3) {
            int max = priorityQueue.poll();
            int mid = priorityQueue.poll();

            time++;

            if (max - 1 > 0) {
                priorityQueue.offer(max - 1);
            }
            if (mid - 1 > 0) {
                priorityQueue.offer(mid - 1);
            }
        }

        if (!priorityQueue.isEmpty()) {
            time = time + priorityQueue.poll();
        }

        return time;
    }

    /**
     * 贪心
     * amount[i]由小到大排序，从小到大的数量分别是a、b、c，
     * 1、a+b<=c，则每次用1个c匹配1个a或b，需要的时间为c
     * 2、a+b>c，则a和b匹配(a+b-c+1)/2次，此时a+b<=c，即转换为情况1，需要的时间为(a+b-c+1)/2+c=(a+b+c+1)/2
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @param amount
     * @return
     */
    public int fillCups2(int[] amount) {
        //amount[i]由小到大排序
        Arrays.sort(amount);

        if (amount[0] + amount[1] <= amount[2]) {
            return amount[2];
        } else {
            return (amount[0] + amount[1] + amount[2] + 1) / 2;
        }
    }
}
