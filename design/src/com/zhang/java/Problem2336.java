package com.zhang.java;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * @Date 2025/3/22 08:19
 * @Author zsy
 * @Description 无限集中的最小数字 类比Problem379、Problem1500、Problem1845、Problem2254
 * 现有一个包含所有正整数的集合 [1, 2, 3, 4, 5, ...] 。
 * 实现 SmallestInfiniteSet 类：
 * SmallestInfiniteSet() 初始化 SmallestInfiniteSet 对象以包含 所有 正整数。
 * int popSmallest() 移除 并返回该无限集中的最小整数。
 * void addBack(int num) 如果正整数 num 不 存在于无限集中，则将一个 num 添加 到该无限集中。
 * <p>
 * 输入
 * ["SmallestInfiniteSet", "addBack", "popSmallest", "popSmallest", "popSmallest", "addBack", "popSmallest", "popSmallest", "popSmallest"]
 * [[], [2], [], [], [], [1], [], [], []]
 * 输出
 * [null, null, 1, 2, 3, null, 1, 4, 5]
 * 解释
 * SmallestInfiniteSet smallestInfiniteSet = new SmallestInfiniteSet();
 * smallestInfiniteSet.addBack(2);    // 2 已经在集合中，所以不做任何变更。
 * smallestInfiniteSet.popSmallest(); // 返回 1 ，因为 1 是最小的整数，并将其从集合中移除。
 * smallestInfiniteSet.popSmallest(); // 返回 2 ，并将其从集合中移除。
 * smallestInfiniteSet.popSmallest(); // 返回 3 ，并将其从集合中移除。
 * smallestInfiniteSet.addBack(1);    // 将 1 添加到该集合中。
 * smallestInfiniteSet.popSmallest(); // 返回 1 ，因为 1 在上一步中被添加到集合中，
 * // 且 1 是最小的整数，并将其从集合中移除。
 * smallestInfiniteSet.popSmallest(); // 返回 4 ，并将其从集合中移除。
 * smallestInfiniteSet.popSmallest(); // 返回 5 ，并将其从集合中移除。
 * <p>
 * 1 <= num <= 1000
 * 最多调用 popSmallest 和 addBack 方法 共计 1000 次
 */
public class Problem2336 {
    public static void main(String[] args) {
        SmallestInfiniteSet smallestInfiniteSet = new SmallestInfiniteSet();
        // 2 已经在集合中，所以不做任何变更。
        smallestInfiniteSet.addBack(2);
        // 返回 1 ，因为 1 是最小的整数，并将其从集合中移除。
        System.out.println(smallestInfiniteSet.popSmallest());
        // 返回 2 ，并将其从集合中移除。
        System.out.println(smallestInfiniteSet.popSmallest());
        // 返回 3 ，并将其从集合中移除。
        System.out.println(smallestInfiniteSet.popSmallest());
        // 将 1 添加到该集合中。
        smallestInfiniteSet.addBack(1);
        // 返回 1 ，因为 1 在上一步中被添加到集合中，
        // 且 1 是最小的整数，并将其从集合中移除。
        System.out.println(smallestInfiniteSet.popSmallest());
        // 返回 4 ，并将其从集合中移除。
        System.out.println(smallestInfiniteSet.popSmallest());
        // 返回 5 ，并将其从集合中移除。
        System.out.println(smallestInfiniteSet.popSmallest());
    }

    /**
     * 优先队列，小根堆
     */
    static class SmallestInfiniteSet {
        //优先队列，小根堆，存放可以重复移除的整数
        //小根堆不为空，则优先移除小根堆堆顶的整数
        //注意：不能单独使用小根堆，因为有可能同一个相同的整数多次入小根堆，导致元素重复，需要额外使用set记录小根堆中的元素
        private final PriorityQueue<Integer> priorityQueue;
        //存储小根堆中元素的哈希集合，避免小根堆中存储相同的元素
        private final Set<Integer> set;
        //下一个要移除的最小整数
        private int nextNum;

        public SmallestInfiniteSet() {
            priorityQueue = new PriorityQueue<>(new Comparator<Integer>() {
                @Override
                public int compare(Integer a, Integer b) {
                    return a - b;
                }
            });
            set = new HashSet<>();
            nextNum = 1;
        }

        public int popSmallest() {
            if (!priorityQueue.isEmpty()) {
                int result = priorityQueue.poll();
                set.remove(result);
                return result;
            }

            int result = nextNum;
            nextNum++;
            return result;
        }

        public void addBack(int num) {
            //num存在于无限集中，则不需要添加
            if (num >= nextNum || set.contains(num)) {
                return;
            }

            priorityQueue.offer(num);
            set.add(num);
        }
    }
}
