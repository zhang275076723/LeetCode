package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/7/5 09:23
 * @Author zsy
 * @Description 数据流中的移动平均值 类比Problem295、Problem480、Problem703、Offer41
 * 给定一个整数数据流和一个窗口大小，根据该滑动窗口的大小，计算其所有整数的移动平均值。
 * 实现 MovingAverage 类：
 * MovingAverage(int size) 用窗口大小 size 初始化对象。
 * double next(int val) 成员函数 next 每次调用的时候都会往滑动窗口增加一个整数，
 * 请计算并返回数据流中最后 size 个值的移动平均值，即滑动窗口里所有数字的平均值。
 * <p>
 * 输入：
 * inputs = ["MovingAverage", "next", "next", "next", "next"]
 * inputs = [[3], [1], [10], [3], [5]]
 * 输出：
 * [null, 1.0, 5.5, 4.66667, 6.0]
 * 解释：
 * MovingAverage movingAverage = new MovingAverage(3);
 * movingAverage.next(1);  // 返回 1.0 = 1 / 1
 * movingAverage.next(10); // 返回 5.5 = (1 + 10) / 2
 * movingAverage.next(3);  // 返回 4.66667 = (1 + 10 + 3) / 3
 * movingAverage.next(5);  // 返回 6.0 = (10 + 3 + 5) / 3
 * <p>
 * 1 <= size <= 1000
 * -10^5 <= val <= 10^5
 * 最多调用 next 方法 10^4 次
 */
public class Problem346 {
    public static void main(String[] args) {
        MovingAverage movingAverage = new MovingAverage(3);
        // 返回 1.0 = 1 / 1
        System.out.println(movingAverage.next(1));
        // 返回 5.5 = (1 + 10) / 2
        System.out.println(movingAverage.next(10));
        // 返回 4.66667 = (1 + 10 + 3) / 3
        System.out.println(movingAverage.next(3));
        // 返回 6.0 = (10 + 3 + 5) / 3
        System.out.println(movingAverage.next(5));
    }

    /**
     * 队列
     * 时间复杂度O(1)，空间复杂度O(size)
     */
    static class MovingAverage {
        //存放滑动窗口中元素的队列
        private final Queue<Integer> queue;
        //滑动窗口大小
        private final int capacity;
        //滑动窗口元素之和
        private int sum;

        public MovingAverage(int size) {
            queue = new LinkedList<>();
            capacity = size;
        }

        public double next(int val) {
            //队列大小小于滑动窗口大小，当前元素入队，sum加上当前元素
            if (queue.size() < capacity) {
                sum = sum + val;
                queue.offer(val);
                return (double) sum / queue.size();
            } else {
                //队列大小等于滑动窗口大小，队首元素出队，当前元素入队，sum减去队首元素，加上当前元素
                sum = sum - queue.poll();
                sum = sum + val;
                queue.offer(val);
                return (double) sum / queue.size();
            }
        }
    }
}
