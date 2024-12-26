package com.zhang.java;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2024/3/5 08:24
 * @Author zsy
 * @Description 最近的请求次数 类比Problem362 线段树类比Problem218、Problem307、Problem308、Problem327、Problem370、Problem654、Problem699、Problem715、Problem729、Problem731、Problem732、Problem1094、Problem1109、Problem1893、Problem2407
 * 写一个 RecentCounter 类来计算特定时间范围内最近的请求。
 * 请你实现 RecentCounter 类：
 * RecentCounter() 初始化计数器，请求数为 0 。
 * int ping(int t) 在时间 t 添加一个新请求，其中 t 表示以毫秒为单位的某个时间，并返回过去 3000 毫秒内发生的所有请求数（包括新请求）。
 * 确切地说，返回在 [t-3000, t] 内发生的请求数。
 * 保证 每次对 ping 的调用都使用比之前更大的 t 值。
 * <p>
 * 输入：
 * ["RecentCounter", "ping", "ping", "ping", "ping"]
 * [[], [1], [100], [3001], [3002]]
 * 输出：
 * [null, 1, 2, 3, 3]
 * 解释：
 * RecentCounter recentCounter = new RecentCounter();
 * recentCounter.ping(1);     // requests = [1]，范围是 [-2999,1]，返回 1
 * recentCounter.ping(100);   // requests = [1, 100]，范围是 [-2900,100]，返回 2
 * recentCounter.ping(3001);  // requests = [1, 100, 3001]，范围是 [1,3001]，返回 3
 * recentCounter.ping(3002);  // requests = [1, 100, 3001, 3002]，范围是 [2,3002]，返回 3
 * <p>
 * 1 <= t <= 10^9
 * 保证每次对 ping 调用所使用的 t 值都 严格递增
 * 至多调用 ping 方法 10^4 次
 */
public class Problem933 {
    public static void main(String[] args) {
//        RecentCounter recentCounter = new RecentCounter();
        RecentCounter2 recentCounter = new RecentCounter2();
        // requests = [1]，范围是 [-2999,1]，返回 1
        System.out.println(recentCounter.ping(1));
        // requests = [1, 100]，范围是 [-2900,100]，返回 2
        System.out.println(recentCounter.ping(100));
        // requests = [1, 100, 3001]，范围是 [1,3001]，返回 3
        System.out.println(recentCounter.ping(3001));
        // requests = [1, 100, 3001, 3002]，范围是 [2,3002]，返回 3
        System.out.println(recentCounter.ping(3002));
    }

    /**
     * 队列
     */
    static class RecentCounter {
        //存储[t-3000,t]的时间队列
        private final Queue<Integer> queue;

        public RecentCounter() {
            queue = new LinkedList<>();
        }

        /**
         * 当前时间t入队，队列中小于t-3000的时间出队，保证队列中存储[t-3000,t]的时间
         * 时间复杂度O(1)，空间复杂度O(1)
         *
         * @param t
         * @return
         */
        public int ping(int t) {
            queue.offer(t);

            //队列中小于t-3000的时间出队，保证队列中存储[t-3000,t]的时间
            while (queue.peek() < t - 3000) {
                queue.poll();
            }

            return queue.size();
        }
    }

    /**
     * 线段树，动态开点
     */
    static class RecentCounter2 {
        //线段树，动态开点
        private final SegmentTree segmentTree;

        public RecentCounter2() {
            segmentTree = new SegmentTree(1, (int) 1e9);
        }

        /**
         * 时间复杂度O(logm)，空间复杂度O(logm) (m为区间的范围，m=10^9)
         *
         * @param t
         * @return
         */
        public int ping(int t) {
            //区间[t,t]元素出现的次数之和加1
            segmentTree.update(segmentTree.root, t, t, 1);

            //返回区间[t-3000,t]元素出现的次数之和
            return segmentTree.query(segmentTree.root, t - 3000, t);
        }

        /**
         * 线段树，动态开点
         */
        private static class SegmentTree {
            private final SegmentTreeNode root;

            public SegmentTree(int leftBound, int rightBound) {
                root = new SegmentTreeNode(leftBound, rightBound);
            }

            public int query(SegmentTreeNode node, int queryLeft, int queryRight) {
                if (queryLeft > node.rightBound || queryRight < node.leftBound) {
                    return 0;
                }

                if (queryLeft <= node.leftBound && node.rightBound <= queryRight) {
                    return node.value;
                }

                int mid = node.leftBound + ((node.rightBound - node.leftBound) >> 1);

                //动态开点
                if (node.leftNode == null) {
                    node.leftNode = new SegmentTreeNode(node.leftBound, mid);
                }

                //动态开点
                if (node.rightNode == null) {
                    node.rightNode = new SegmentTreeNode(mid + 1, node.rightBound);
                }

                if (node.lazyValue != 0) {
                    node.leftNode.value = node.leftNode.value + (mid - node.leftBound + 1) * node.lazyValue;
                    node.rightNode.value = node.rightNode.value + (node.rightBound - mid) * node.lazyValue;
                    node.leftNode.lazyValue = node.leftNode.lazyValue + node.lazyValue;
                    node.rightNode.lazyValue = node.rightNode.lazyValue + node.lazyValue;

                    node.lazyValue = 0;
                }

                int leftValue = query(node.leftNode, queryLeft, queryRight);
                int rightValue = query(node.rightNode, queryLeft, queryRight);

                return leftValue + rightValue;
            }

            public void update(SegmentTreeNode node, int updateLeft, int updateRight, int value) {
                if (updateLeft > node.rightBound || updateRight < node.leftBound) {
                    return;
                }

                if (updateLeft <= node.leftBound && node.rightBound <= updateRight) {
                    node.value = node.value + (node.rightBound - node.leftBound + 1) * value;
                    node.lazyValue = node.lazyValue + value;
                    return;
                }

                int mid = node.leftBound + ((node.rightBound - node.leftBound) >> 1);

                //动态开点
                if (node.leftNode == null) {
                    node.leftNode = new SegmentTreeNode(node.leftBound, mid);
                }

                //动态开点
                if (node.rightNode == null) {
                    node.rightNode = new SegmentTreeNode(mid + 1, node.rightBound);
                }

                if (node.lazyValue != 0) {
                    node.leftNode.value = node.leftNode.value + (mid - node.leftBound + 1) * node.lazyValue;
                    node.rightNode.value = node.rightNode.value + (node.rightBound - mid) * node.lazyValue;
                    node.leftNode.lazyValue = node.leftNode.lazyValue + node.lazyValue;
                    node.rightNode.lazyValue = node.rightNode.lazyValue + node.lazyValue;

                    node.lazyValue = 0;
                }

                update(node.leftNode, updateLeft, updateRight, value);
                update(node.rightNode, updateLeft, updateRight, value);

                node.value = node.leftNode.value + node.rightNode.value;
            }

            /**
             * 线段树节点
             */
            private static class SegmentTreeNode {
                //区间[leftBound,rightBound]元素出现的次数之和
                private int value;
                private int lazyValue;
                private int leftBound;
                private int rightBound;
                private SegmentTreeNode leftNode;
                private SegmentTreeNode rightNode;

                public SegmentTreeNode(int leftBound, int rightBound) {
                    this.leftBound = leftBound;
                    this.rightBound = rightBound;
                }
            }
        }
    }
}
