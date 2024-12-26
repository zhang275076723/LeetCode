package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/9/23 09:00
 * @Author zsy
 * @Description 设计数字容器系统 延迟删除类比Problem480、Problem1172、Problem2034、Problem2353 有序集合类比
 * 设计一个数字容器系统，可以实现以下功能：
 * 在系统中给定下标处 插入 或者 替换 一个数字。
 * 返回 系统中给定数字的最小下标。
 * 请你实现一个 NumberContainers 类：
 * NumberContainers() 初始化数字容器系统。
 * void change(int index, int number) 在下标 index 处填入 number 。
 * 如果该下标 index 处已经有数字了，那么用 number 替换该数字。
 * int find(int number) 返回给定数字 number 在系统中的最小下标。如果系统中没有 number ，那么返回 -1 。
 * <p>
 * 输入：
 * ["NumberContainers", "find", "change", "change", "change", "change", "find", "change", "find"]
 * [[], [10], [2, 10], [1, 10], [3, 10], [5, 10], [10], [1, 20], [10]]
 * 输出：
 * [null, -1, null, null, null, null, 1, null, 2]
 * 解释：
 * NumberContainers nc = new NumberContainers();
 * nc.find(10); // 没有数字 10 ，所以返回 -1 。
 * nc.change(2, 10); // 容器中下标为 2 处填入数字 10 。
 * nc.change(1, 10); // 容器中下标为 1 处填入数字 10 。
 * nc.change(3, 10); // 容器中下标为 3 处填入数字 10 。
 * nc.change(5, 10); // 容器中下标为 5 处填入数字 10 。
 * nc.find(10); // 数字 10 所在的下标为 1 ，2 ，3 和 5 。因为最小下标为 1 ，所以返回 1 。
 * nc.change(1, 20); // 容器中下标为 1 处填入数字 20 。注意，下标 1 处之前为 10 ，现在被替换为 20 。
 * nc.find(10); // 数字 10 所在下标为 2 ，3 和 5 。最小下标为 2 ，所以返回 2 。
 * <p>
 * 1 <= index, number <= 10^9
 * 调用 change 和 find 的 总次数 不超过 10^5 次。
 */
public class Problem2349 {
    public static void main(String[] args) {
//        NumberContainers nc = new NumberContainers();
        NumberContainers2 nc = new NumberContainers2();
        // 没有数字 10 ，所以返回 -1 。
        System.out.println(nc.find(10));
        // 容器中下标为 2 处填入数字 10 。
        nc.change(2, 10);
        // 容器中下标为 1 处填入数字 10 。
        nc.change(1, 10);
        // 容器中下标为 3 处填入数字 10 。
        nc.change(3, 10);
        // 容器中下标为 5 处填入数字 10 。
        nc.change(5, 10);
        // 数字 10 所在的下标为 1 ，2 ，3 和 5 。因为最小下标为 1 ，所以返回 1 。
        System.out.println(nc.find(10));
        // 容器中下标为 1 处填入数字 20 。注意，下标 1 处之前为 10 ，现在被替换为 20 。
        nc.change(1, 20);
        // 数字 10 所在下标为 2 ，3 和 5 。最小下标为 2 ，所以返回 2 。
        System.out.println(nc.find(10));

//        NumberContainers nc = new NumberContainers();
//        nc.change(1, 10);
//        System.out.println(nc.find(10));
//        nc.change(1, 20);
//        System.out.println(nc.find(10));
//        System.out.println(nc.find(20));
//        System.out.println(nc.find(30));
    }

    /**
     * 哈希表+有序集合
     */
    static class NumberContainers {
        //key：下标索引，value：当前下标索引对应的值
        private final Map<Integer, Integer> indexMap;
        //key：当前值，value：按照下标索引由小到大排序的相同值的有序集合
        private final Map<Integer, TreeSet<Integer>> treeSetMap;

        public NumberContainers() {
            indexMap = new HashMap<>();
            treeSetMap = new HashMap<>();
        }

        public void change(int index, int number) {
            if (!indexMap.containsKey(index)) {
                indexMap.put(index, number);

                if (!treeSetMap.containsKey(number)) {
                    treeSetMap.put(number, new TreeSet<>(new Comparator<Integer>() {
                        @Override
                        public int compare(Integer a, Integer b) {
                            //相同值的下标索引由小到大排序
                            return a - b;
                        }
                    }));
                }

                //number所在的有序集合中加入index
                TreeSet<Integer> treeSet = treeSetMap.get(number);
                treeSet.add(index);

                return;
            }

            //下标索引index未修改之前的值
            int originNumber = indexMap.get(index);
            //更新下标索引index的值为number
            indexMap.put(index, number);
            //originNumber所在的有序集合
            TreeSet<Integer> originTreeSet = treeSetMap.get(originNumber);
            //originTreeSet中删除index
            originTreeSet.remove(index);

            if (!treeSetMap.containsKey(number)) {
                treeSetMap.put(number, new TreeSet<>(new Comparator<Integer>() {
                    @Override
                    public int compare(Integer a, Integer b) {
                        //相同值的下标索引由小到大排序
                        return a - b;
                    }
                }));
            }

            //number所在的有序集合中加入index
            TreeSet<Integer> treeSet = treeSetMap.get(number);
            treeSet.add(index);

            //originNumber所在的有序集合为空，则treeSetMap中删除originNumber
            if (originTreeSet.isEmpty()) {
                treeSetMap.remove(originNumber);
            }
        }

        public int find(int number) {
            //不存在number，返回-1
            if (!treeSetMap.containsKey(number)) {
                return -1;
            }

            //返回number所在的有序集合中第一个下标索引，即为值为number的最小下标索引
            return treeSetMap.get(number).first();
        }
    }

    /**
     * 哈希表+优先队列，小根堆+延迟删除
     * 因为小根堆只能移除堆顶元素，所以对于非堆顶元素的删除使用延迟删除，在更新index时，
     * index之前的值originNumber所在小根堆中并不删除index，而是查找originNumber的最小下标索引时，
     * 如果堆顶元素下标索引在indexMap中对应元素不等于originNumber，则堆顶元素下标索引发生了修改，删除堆顶元素
     */
    static class NumberContainers2 {
        //key：下标索引，value：当前下标索引对应的值
        private final Map<Integer, Integer> indexMap;
        //key：当前值，value：存储相同值的下标索引的小根堆
        private final Map<Integer, PriorityQueue<Integer>> priorityQueueMap;

        public NumberContainers2() {
            indexMap = new HashMap<>();
            priorityQueueMap = new HashMap<>();
        }

        public void change(int index, int number) {
            indexMap.put(index, number);

            if (!priorityQueueMap.containsKey(number)) {
                priorityQueueMap.put(number, new PriorityQueue<>(new Comparator<Integer>() {
                    @Override
                    public int compare(Integer a, Integer b) {
                        //小根堆
                        return a - b;
                    }
                }));
            }

            //number所在的小根堆中加入index
            //注意：originNumber所在小根堆中并不删除index，而是在查找number时延迟删除
            PriorityQueue<Integer> priorityQueue = priorityQueueMap.get(number);
            priorityQueue.offer(index);
        }

        public int find(int number) {
            //不存在number，返回-1
            if (!priorityQueueMap.containsKey(number)) {
                return -1;
            }

            //number所在的小根堆
            PriorityQueue<Integer> priorityQueue = priorityQueueMap.get(number);

            //堆顶元素下标索引在indexMap中对应元素不等于number，则堆顶元素下标索引发生了修改，删除堆顶元素
            while (!priorityQueue.isEmpty() && indexMap.get(priorityQueue.peek()) != number) {
                priorityQueue.poll();
            }

            //number所在的小根堆为空，则priorityQueueMap中删除number
            if (priorityQueue.isEmpty()) {
                priorityQueueMap.remove(number);
            }

            //number所在的小根堆为空，则不存在number，返回-1；否则，返回number所在的小根堆堆顶元素，即为number的最小下标索引
            return priorityQueue.isEmpty() ? -1 : priorityQueue.peek();
        }
    }
}
