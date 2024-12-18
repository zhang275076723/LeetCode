package com.zhang.java;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Date 2022/10/27 09:07
 * @Author zsy
 * @Description 数据流的中位数 类比Problem346、Problem480、Problem703、Problem1670、Offer41 同Offer41
 * 中位数是有序列表中间的数。如果列表长度是偶数，中位数则是中间两个数的平均值。
 * 例如 arr = [2,3,4] 的中位数是 3 。
 * 例如 arr = [2,3] 的中位数是 (2 + 3) / 2 = 2.5 。
 * 实现 MedianFinder 类:
 * MedianFinder() 初始化 MedianFinder 对象。
 * void addNum(int num) 将数据流中的整数 num 添加到数据结构中。
 * double findMedian() 返回到目前为止所有元素的中位数。与实际答案相差 10^-5 以内的答案将被接受。
 * <p>
 * 输入
 * ["MedianFinder", "addNum", "addNum", "findMedian", "addNum", "findMedian"]
 * [[], [1], [2], [], [3], []]
 * 输出
 * [null, null, null, 1.5, null, 2.0]
 * 解释
 * MedianFinder medianFinder = new MedianFinder();
 * medianFinder.addNum(1);    // arr = [1]
 * medianFinder.addNum(2);    // arr = [1, 2]
 * medianFinder.findMedian(); // 返回 1.5 ((1 + 2) / 2)
 * medianFinder.addNum(3);    // arr[1, 2, 3]
 * medianFinder.findMedian(); // return 2.0
 * <p>
 * -10^5 <= num <= 10^5
 * 在调用 findMedian 之前，数据结构中至少有一个元素
 * 最多 5 * 10^4 次调用 addNum 和 findMedian
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
     * 大根堆+小根堆(对顶堆)
     * 大根堆，维护所有元素中较小的一半
     * 小根堆，维护所有元素中较大的一半
     * 1、大根堆为空，或者当前元素小于大根堆堆顶元素，则当前元素入大根堆，此时如果大根堆元素数量大于小根堆元素数量加1，
     * 则大根堆堆顶元素出堆，入小根堆；
     * 2、否则，当前元素入小根堆，此时如果大根堆元素数量小于小根堆元素数量，则小根堆堆顶元素出堆，入大根堆
     * 注意：始终保持大根堆元素数量 >= 小根堆元素数量
     * <p>
     * 进阶1：如果数据都在[0,100]之间，则通过计数排序统计每个元素出现的次数，使用双指针维护中位数
     * 进阶2：如果数据99%在[0,100]之间，则仍可以通过计数排序统计每个元素出现的次数，使用双指针维护中位数；
     * 对于超过[0,100]的数据，使用额外的数组存储，当中位数不在[0,100]之间时，对于大于100的数组暴力查询即可
     */
    static class MedianFinder {
        //大根堆，维护所有元素中较小的一半
        private final PriorityQueue<Integer> maxPriorityQueue;
        //小根堆，维护所有元素中较大的一半
        private final PriorityQueue<Integer> minPriorityQueue;

        public MedianFinder() {
            maxPriorityQueue = new PriorityQueue<>(new Comparator<Integer>() {
                @Override
                public int compare(Integer a, Integer b) {
                    return b - a;
                }
            });

            minPriorityQueue = new PriorityQueue<>(new Comparator<Integer>() {
                @Override
                public int compare(Integer a, Integer b) {
                    return a - b;
                }
            });
        }

        /**
         * 1、大根堆为空，或者当前元素小于大根堆堆顶元素，则当前元素入大根堆，此时如果大根堆元素数量大于小根堆元素数量加1，
         * 则大根堆堆顶元素出堆，入小根堆；
         * 2、否则，当前元素入小根堆，此时如果大根堆元素数量小于小根堆元素数量，则小根堆堆顶元素出堆，入大根堆
         * 注意：始终保持大根堆元素数量 >= 小根堆元素数量
         * 时间复杂度O(logn)，空间复杂度O(1)
         *
         * @param num
         */
        public void addNum(int num) {
            //注意：必须是小于等于，不能是小于，因为始终保持大根堆元素数量大于等于小根堆元素数量
            if (maxPriorityQueue.isEmpty() || num <= maxPriorityQueue.peek()) {
                maxPriorityQueue.offer(num);

                if (maxPriorityQueue.size() > minPriorityQueue.size() + 1) {
                    minPriorityQueue.offer(maxPriorityQueue.poll());
                }
            } else {
                minPriorityQueue.offer(num);

                if (maxPriorityQueue.size() < minPriorityQueue.size()) {
                    maxPriorityQueue.offer(minPriorityQueue.poll());
                }
            }
        }

        public double findMedian() {
            if (maxPriorityQueue.size() == minPriorityQueue.size()) {
                return (maxPriorityQueue.peek() + minPriorityQueue.peek()) / 2.0;
            } else {
                return maxPriorityQueue.peek();
            }
        }
    }
}
