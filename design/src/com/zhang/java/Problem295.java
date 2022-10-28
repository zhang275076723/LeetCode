package com.zhang.java;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @Date 2022/10/27 09:07
 * @Author zsy
 * @Description 数据流的中位数 类比Problem155、Problem232、Problem295、Problem716、Offer9、Offer30、Offer59_2 同Offer41
 * 中位数是有序列表中间的数。如果列表长度是偶数，中位数则是中间两个数的平均值。
 * 例如，
 * [2,3,4] 的中位数是 3
 * [2,3] 的中位数是 (2 + 3) / 2 = 2.5
 * 设计一个支持以下两种操作的数据结构：
 * void addNum(int num) - 从数据流中添加一个整数到数据结构中。
 * double findMedian() - 返回目前所有元素的中位数。
 * <p>
 * addNum(1)
 * addNum(2)
 * findMedian() -> 1.5
 * addNum(3)
 * findMedian() -> 2
 * <p>
 * 如果数据流中所有整数都在 0 到 100 范围内，你将如何优化你的算法？
 * 如果数据流中 99% 的整数都在 0 到 100 范围内，你将如何优化你的算法？
 */
public class Problem295 {
    public static void main(String[] args) {
        MedianFinder medianFinder = new MedianFinder();
        medianFinder.addNum(1);
        medianFinder.addNum(2);
        System.out.println(medianFinder.findMedian());
        medianFinder.addNum(3);
        System.out.println(medianFinder.findMedian());
    }

    /**
     * 大根堆+小根堆
     * 每次先将元素放入
     */
    static class MedianFinder {
        /**
         * 大根堆
         */
        private final Queue<Integer> maxQueue;

        /**
         * 小根堆
         */
        private final Queue<Integer> minQueue;

        public MedianFinder() {
            maxQueue = new PriorityQueue<>(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o2 - o1;
                }
            });

            minQueue = new PriorityQueue<>(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o1 - o2;
                }
            });
        }

        public void addNum(int num) {
            maxQueue.offer(num);
            minQueue.offer(maxQueue.poll());

            //当大根堆元素个数小于小根堆元素个数时，小根堆堆顶元素出栈，大根堆入栈
            if (maxQueue.size() < minQueue.size()) {
                maxQueue.offer(minQueue.poll());
            }
        }

        public double findMedian() {
            if (maxQueue.size() == minQueue.size()) {
                return (maxQueue.peek() + minQueue.peek()) / 2.0;
            } else {
                return maxQueue.peek();
            }
        }
    }
}
