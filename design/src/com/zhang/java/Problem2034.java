package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/11/22 08:49
 * @Author zsy
 * @Description 股票价格波动 延迟删除类比Problem480、Problem1172、Problem2349、Problem2353 股票类比Problem121、Problem122、Problem123、Problem188、Problem309、Problem714、Problem901、Problem2110、Problem2291、Offer63 有序集合类比
 * 给你一支股票价格的数据流。
 * 数据流中每一条记录包含一个 时间戳 和该时间点股票对应的 价格 。
 * 不巧的是，由于股票市场内在的波动性，股票价格记录可能不是按时间顺序到来的。
 * 某些情况下，有的记录可能是错的。如果两个有相同时间戳的记录出现在数据流中，
 * 前一条记录视为错误记录，后出现的记录 更正 前一条错误的记录。
 * 请你设计一个算法，实现：
 * 更新 股票在某一时间戳的股票价格，如果有之前同一时间戳的价格，这一操作将 更正 之前的错误价格。
 * 找到当前记录里 最新股票价格 。最新股票价格 定义为时间戳最晚的股票价格。
 * 找到当前记录里股票的 最高价格 。
 * 找到当前记录里股票的 最低价格 。
 * 请你实现 StockPrice 类：
 * StockPrice() 初始化对象，当前无股票价格记录。
 * void update(int timestamp, int price) 在时间点 timestamp 更新股票价格为 price 。
 * int current() 返回股票 最新价格 。
 * int maximum() 返回股票 最高价格 。
 * int minimum() 返回股票 最低价格 。
 * <p>
 * 输入：
 * ["StockPrice", "update", "update", "current", "maximum", "update", "maximum", "update", "minimum"]
 * [[], [1, 10], [2, 5], [], [], [1, 3], [], [4, 2], []]
 * 输出：
 * [null, null, null, 5, 10, null, 5, null, 2]
 * 解释：
 * StockPrice stockPrice = new StockPrice();
 * stockPrice.update(1, 10); // 时间戳为 [1] ，对应的股票价格为 [10] 。
 * stockPrice.update(2, 5);  // 时间戳为 [1,2] ，对应的股票价格为 [10,5] 。
 * stockPrice.current();     // 返回 5 ，最新时间戳为 2 ，对应价格为 5 。
 * stockPrice.maximum();     // 返回 10 ，最高价格的时间戳为 1 ，价格为 10 。
 * stockPrice.update(1, 3);  // 之前时间戳为 1 的价格错误，价格更新为 3 。
 * <                         // 时间戳为 [1,2] ，对应股票价格为 [3,5] 。
 * stockPrice.maximum();     // 返回 5 ，更正后最高价格为 5 。
 * stockPrice.update(4, 2);  // 时间戳为 [1,2,4] ，对应价格为 [3,5,2] 。
 * stockPrice.minimum();     // 返回 2 ，最低价格时间戳为 4 ，价格为 2 。
 * <p>
 * 1 <= timestamp, price <= 10^9
 * update，current，maximum 和 minimum 总 调用次数不超过 10^5 。
 * current，maximum 和 minimum 被调用时，update 操作 至少 已经被调用过 一次 。
 */
public class Problem2034 {
    public static void main(String[] args) {
        StockPrice stockPrice = new StockPrice();
//        StockPrice2 stockPrice = new StockPrice2();
        // 时间戳为 [1] ，对应的股票价格为 [10] 。
        stockPrice.update(1, 10);
        // 时间戳为 [1,2] ，对应的股票价格为 [10,5] 。
        stockPrice.update(2, 5);
        // 返回 5 ，最新时间戳为 2 ，对应价格为 5 。
        System.out.println(stockPrice.current());
        // 返回 10 ，最高价格的时间戳为 1 ，价格为 10 。
        System.out.println(stockPrice.maximum());
        // 之前时间戳为 1 的价格错误，价格更新为 3 。
        // 时间戳为 [1,2] ，对应股票价格为 [3,5] 。
        stockPrice.update(1, 3);
        // 返回 5 ，更正后最高价格为 5 。
        System.out.println(stockPrice.maximum());
        // 时间戳为 [1,2,4] ，对应价格为 [3,5,2] 。
        stockPrice.update(4, 2);
        // 返回 2 ，最低价格时间戳为 4 ，价格为 2 。
        System.out.println(stockPrice.minimum());
    }

    /**
     * 哈希表+有序集合
     */
    static class StockPrice {
        //key：时间戳，value：arr[0]：时间戳，arr[1]：当前时间戳对应的股票价格
        private final Map<Integer, int[]> map;
        //有序集合，按照arr[1]股票价格由小到大存储
        //注意：有序集合中key唯一并且不能修改，如果要修改，则需要先删除，再添加
        private final TreeSet<int[]> treeSet;
        //当前股票的最大时间戳，用于current()获取最新股票价格
        private int maxTimestamp;

        public StockPrice() {
            map = new HashMap<>();
            treeSet = new TreeSet<>(new Comparator<int[]>() {
                @Override
                public int compare(int[] arr1, int[] arr2) {
                    if (arr1[1] != arr2[1]) {
                        return arr1[1] - arr2[1];
                    } else {
                        return arr1[0] - arr2[0];
                    }
                }
            });
            maxTimestamp = 0;
        }

        public void update(int timestamp, int price) {
            if (!map.containsKey(timestamp)) {
                maxTimestamp = Math.max(maxTimestamp, timestamp);
                int[] arr = new int[]{timestamp, price};
                map.put(timestamp, arr);
                treeSet.add(arr);

                return;
            }

            int[] arr = map.get(timestamp);
            //注意：有序集合中修改需要先删除，再添加
            treeSet.remove(arr);
            arr[1] = price;
            treeSet.add(arr);
        }

        public int current() {
            if (map.isEmpty()) {
                return -1;
            }

            return map.get(maxTimestamp)[1];
        }

        public int maximum() {
            if (treeSet.isEmpty()) {
                return -1;
            }

            //last()：有序集合中最后一个区间
            return treeSet.last()[1];
        }

        public int minimum() {
            if (treeSet.isEmpty()) {
                return -1;
            }

            //first()：有序集合中第一个区间
            return treeSet.first()[1];
        }
    }

    /**
     * 哈希表+优先队列，大根堆，小根堆+延迟删除
     * 因为大根堆、小根堆只能移除堆顶元素，所以对于非堆顶元素的删除使用延迟删除，在更新当前时间戳对应的股票价格时，
     * 并不删除大根堆、小根堆中当前时间戳之前对应的股票价格，而是查找大根堆、小根堆的最大、最小股票价格时，
     * 如果堆顶时间戳对应的股票价格不等于map中当前时间戳对应的股票价格，则堆顶时间戳对应的股票价格发生了修改，删除堆顶元素
     */
    static class StockPrice2 {
        //key：时间戳，value：arr[0]：时间戳，arr[1]：当前时间戳对应的股票价格
        private final Map<Integer, int[]> map;
        //优先队列，大根堆，arr[0]：时间戳，arr[1]：当前时间戳对应的股票价格
        private final PriorityQueue<int[]> maxPriorityQueue;
        //优先队列，小根堆，arr[0]：时间戳，arr[1]：当前时间戳对应的股票价格
        private final PriorityQueue<int[]> minPriorityQueue;
        //当前股票的最大时间戳，用于current()获取最新股票价格
        private int maxTimestamp;

        public StockPrice2() {
            map = new HashMap<>();
            maxPriorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
                @Override
                public int compare(int[] arr1, int[] arr2) {
                    return arr2[1] - arr1[1];
                }
            });
            minPriorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
                @Override
                public int compare(int[] arr1, int[] arr2) {
                    return arr1[1] - arr2[1];
                }
            });
            maxTimestamp = 0;
        }

        public void update(int timestamp, int price) {
            maxTimestamp = Math.max(maxTimestamp, timestamp);
            int[] arr = new int[]{timestamp, price};
            map.put(timestamp, arr);
            maxPriorityQueue.offer(arr);
            minPriorityQueue.offer(arr);
        }

        public int current() {
            if (map.isEmpty()) {
                return -1;
            }

            return map.get(maxTimestamp)[1];
        }

        public int maximum() {
            if (maxPriorityQueue.isEmpty()) {
                return -1;
            }

            //大根堆堆顶时间戳对应的股票价格不等于map中当前时间戳对应的股票价格，则大根堆堆顶时间戳对应的股票价格发生了修改，删除大根堆堆顶元素
            while (!maxPriorityQueue.isEmpty() && maxPriorityQueue.peek()[1] != map.get(maxPriorityQueue.peek()[0])[1]) {
                maxPriorityQueue.poll();
            }

            //大根堆为空，则不存在最大股票价格，返回-1；否则，返回大根堆堆顶股票价格，即为最大股票价格
            return maxPriorityQueue.peek()[1];
        }

        public int minimum() {
            if (minPriorityQueue.isEmpty()) {
                return -1;
            }

            //小根堆堆顶时间戳对应的股票价格不等于map中当前时间戳对应的股票价格，则小根堆堆顶时间戳对应的股票价格发生了修改，删除小根堆堆顶元素
            while (!minPriorityQueue.isEmpty() && minPriorityQueue.peek()[1] != map.get(minPriorityQueue.peek()[0])[1]) {
                minPriorityQueue.poll();
            }

            //小根堆为空，则不存在最小股票价格，返回-1；否则，返回小根堆堆顶股票价格，即为最小股票价格
            return minPriorityQueue.peek()[1];
        }
    }
}
