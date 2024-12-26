package com.zhang.java;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * @Date 2024/10/13 08:58
 * @Author zsy
 * @Description 敲击计数器 类比Problem933
 * 设计一个敲击计数器，使它可以统计在过去 5 分钟内被敲击次数。（即过去 300 秒）
 * 您的系统应该接受一个时间戳参数 timestamp (单位为 秒 )，并且您可以假定对系统的调用是按时间顺序进行的(即 timestamp 是单调递增的)。
 * 几次撞击可能同时发生。
 * 实现 HitCounter 类:
 * HitCounter() 初始化命中计数器系统。
 * void hit(int timestamp) 记录在 timestamp ( 单位为秒 )发生的一次命中。在同一个 timestamp 中可能会出现几个点击。
 * int getHits(int timestamp) 返回 timestamp 在过去 5 分钟内(即过去 300 秒)的命中次数。
 * 进阶: 如果每秒的敲击次数是一个很大的数字，你的计数器可以应对吗？
 * <p>
 * 输入：
 * ["HitCounter", "hit", "hit", "hit", "getHits", "hit", "getHits", "getHits"]
 * [[], [1], [2], [3], [4], [300], [300], [301]]
 * 输出：
 * [null, null, null, null, 3, null, 4, 3]
 * 解释：
 * HitCounter counter = new HitCounter();
 * counter.hit(1);// 在时刻 1 敲击一次。
 * counter.hit(2);// 在时刻 2 敲击一次。
 * counter.hit(3);// 在时刻 3 敲击一次。
 * counter.getHits(4);// 在时刻 4 统计过去 5 分钟内的敲击次数, 函数返回 3 。
 * counter.hit(300);// 在时刻 300 敲击一次。
 * counter.getHits(300); // 在时刻 300 统计过去 5 分钟内的敲击次数，函数返回 4 。
 * counter.getHits(301); // 在时刻 301 统计过去 5 分钟内的敲击次数，函数返回 3 。
 * <p>
 * 1 <= timestamp <= 2 * 10^9
 * 所有对系统的调用都是按时间顺序进行的（即 timestamp 是单调递增的）
 * hit and getHits 最多被调用 300 次
 */
public class Problem362 {
    public static void main(String[] args) {
//        HitCounter counter = new HitCounter();
        HitCounter2 counter = new HitCounter2();
        // 在时刻 1 敲击一次。
        counter.hit(1);
        // 在时刻 2 敲击一次。
        counter.hit(2);
        // 在时刻 3 敲击一次。
        counter.hit(3);
        // 在时刻 4 统计过去 5 分钟内的敲击次数, 函数返回 3 。
        System.out.println(counter.getHits(4));
        // 在时刻 300 敲击一次。
        counter.hit(300);
        // 在时刻 300 统计过去 5 分钟内的敲击次数，函数返回 4 。
        System.out.println(counter.getHits(300));
        // 在时刻 301 统计过去 5 分钟内的敲击次数，函数返回 3 。
        System.out.println(counter.getHits(301));
    }

    /**
     * 队列
     */
    static class HitCounter {
        //队列存储最近300s内的敲击时间
        private final Queue<Integer> queue;

        public HitCounter() {
            queue = new LinkedList<>();
        }

        public void hit(int timestamp) {
            queue.offer(timestamp);

            //队列只存储[timestamp-300+1,timestamp]内的敲击时间
            while (!queue.isEmpty() && queue.peek() < timestamp - 300 + 1) {
                queue.poll();
            }
        }

        public int getHits(int timestamp) {
            //队列只存储[timestamp-300+1,timestamp]内的敲击时间
            while (!queue.isEmpty() && queue.peek() < timestamp - 300 + 1) {
                queue.poll();
            }

            return queue.size();
        }
    }

    /**
     * 队列+哈希表
     * 进阶: 如果每秒的敲击次数是一个很大的数字，你的计数器可以应对吗？
     * 队列中不存储最近300s内的每次敲击，而是存储最近300s内每秒敲击的次数数组
     */
    static class HitCounter2 {
        //队列存储最近300s内的每秒敲击时间，即敲击时间不重复
        private final Queue<Integer> queue;
        //key：敲击时间，value：当前敲击时间的敲击次数
        private final Map<Integer, Integer> map;
        //队列中最近300s内的敲击次数
        private int size;

        public HitCounter2() {
            queue = new LinkedList<>();
            map = new HashMap<>();
            size = 0;
        }

        public void hit(int timestamp) {
            if (!map.containsKey(timestamp)) {
                queue.offer(timestamp);
            }

            map.put(timestamp, map.getOrDefault(timestamp, 0) + 1);
            size++;

            //队列只存储[timestamp-300+1,timestamp]内的敲击时间
            while (!queue.isEmpty() && queue.peek() < timestamp - 300 + 1) {
                int curTimestamp = queue.poll();
                int count = map.remove(curTimestamp);
                size = size - count;
            }
        }

        public int getHits(int timestamp) {
            //队列只存储[timestamp-300+1,timestamp]内的敲击时间
            while (!queue.isEmpty() && queue.peek() < timestamp - 300 + 1) {
                int curTimestamp = queue.poll();
                int count = map.remove(curTimestamp);
                size = size - count;
            }

            return size;
        }
    }
}
