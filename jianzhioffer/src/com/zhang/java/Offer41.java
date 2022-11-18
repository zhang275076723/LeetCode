package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/3/25 19:30
 * @Author zsy
 * @Description 数据流中的中位数 类比Problem155、Problem225、Problem232、Problem716、Offer9、Offer30、Offer31、Offer59_2 同Problem295
 * 如何得到一个数据流中的中位数？
 * 如果从数据流中读出奇数个数值，那么中位数就是所有数值排序之后位于中间的数值。
 * 如果从数据流中读出偶数个数值，那么中位数就是所有数值排序之后中间两个数的平均值。
 * <p>
 * 设计一个支持以下两种操作的数据结构：
 * void addNum(int num) - 从数据流中添加一个整数到数据结构中。
 * double findMedian() - 返回目前所有元素的中位数。
 * <p>
 * 输入：
 * ["MedianFinder","addNum","addNum","findMedian","addNum","findMedian"]
 * [[],[1],[2],[],[3],[]]
 * 输出：[null,null,null,1.50000,null,2.00000]
 * <p>
 * 输入：
 * ["MedianFinder","addNum","findMedian","addNum","findMedian"]
 * [[],[2],[],[3],[]]
 * 输出：[null,null,2.00000,null,2.50000]
 * <p>
 * 最多会对 addNum、findMedian 进行 50000 次调用。
 */
public class Offer41 {
    public static void main(String[] args) {
        MedianFinder medianFinder = new MedianFinder();
        medianFinder.addNum(1);
        medianFinder.addNum(2);
        System.out.println(medianFinder.findMedian());
        medianFinder.addNum(3);
        System.out.println(medianFinder.findMedian());
    }

    /**
     * 两个堆
     * 大根堆，保存所有元素中较小的一半元素
     * 小根堆，保存所有元素中较大的一半元素
     */
    public static class MedianFinder {
        /**
         * 大根堆，保存所有元素中较小的一半元素
         */
        private final Queue<Integer> maxHeap;

        /**
         * 小根堆，保存所有元素中较大的一半元素
         */
        private final Queue<Integer> minHeap;

        public MedianFinder() {
            maxHeap = new PriorityQueue<>((o1, o2) -> o2 - o1);
            minHeap = new PriorityQueue<>((o1, o2) -> o1 - o2);
        }

        /**
         * 1、将元素放入大根堆，取出大根堆堆顶元素放入小根堆
         * 2、如果大根堆大小 < 小根堆大小，则将小根堆堆顶元素放入大根堆
         * 始终保持大根堆元素数量 >= 小根堆元素数量
         * 时间复杂度O(logn)，空间复杂度O(1)
         *
         * @param num
         */
        public void addNum(int num) {
            maxHeap.offer(num);
            minHeap.offer(maxHeap.poll());

            //当大根堆元素个数小于小根堆元素个数时，小根堆堆顶元素出栈，大根堆入栈，始终保证大根堆元素数量大于等于小根堆元素数量
            if (maxHeap.size() < minHeap.size()) {
                maxHeap.offer(minHeap.poll());
            }
        }

        /**
         * 时间复杂度O(1)，空间复杂度O(1)
         *
         * @return
         */
        public double findMedian() {
            //奇数
            if (maxHeap.size() != minHeap.size()) {
                return maxHeap.peek();
            } else {
                //偶数
                return (maxHeap.peek() + minHeap.peek()) / 2.0;
            }
        }
    }
}
