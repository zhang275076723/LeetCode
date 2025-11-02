package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2025/11/1 18:04
 * @Author zsy
 * @Description 数字流的秩 线段树类比
 * 假设你正在读取一串整数。
 * 每隔一段时间，你希望能找出数字 x 的秩(小于或等于 x 的值的个数)。
 * 请实现数据结构和算法来支持这些操作，也就是说：
 * 实现 track(int x) 方法，每读入一个数字都会调用该方法；
 * 实现 getRankOfNumber(int x) 方法，返回小于或等于 x 的值的个数。
 * 注意：本题相对原题稍作改动
 * <p>
 * 输入：
 * ["StreamRank", "getRankOfNumber", "track", "getRankOfNumber"]
 * [[], [1], [0], [0]]
 * 输出：
 * [null,0,null,1]
 * <p>
 * x <= 50000
 * track 和 getRankOfNumber 方法的调用次数均不超过 2000 次
 */
public class Interview_10_10 {
    public static void main(String[] args) {
        StreamRank streamRank = new StreamRank();
        System.out.println(streamRank.getRankOfNumber(1));
        streamRank.track(0);
        System.out.println(streamRank.getRankOfNumber(0));
    }

    /**
     * 二分查找
     */
    static class StreamRank {
        private final List<Integer> list;

        public StreamRank() {
            list = new ArrayList<>();
        }

        public void track(int x) {
            if (list.isEmpty()) {
                list.add(x);
                return;
            }

            //list中大于x的第一个下标索引
            int index = -1;
            int left = 0;
            int right = list.size() - 1;
            int mid;

            //二分查找list中大于x的第一个下标索引
            while (left <= right) {
                mid = left + ((right - left) >> 1);

                if (list.get(mid) <= x) {
                    index = mid + 1;
                    left = mid + 1;
                } else {
                    index = mid;
                    right = mid - 1;
                }
            }

            list.add(index, x);
        }

        public int getRankOfNumber(int x) {
            if (list.isEmpty()) {
                return 0;
            }

            //list中小于等于x的最后一个下标索引
            int index = -1;
            int left = 0;
            int right = list.size() - 1;
            int mid;

            //二分查找list中小于等于x的最后一个下标索引
            while (left <= right) {
                mid = left + ((right - left) >> 1);

                if (list.get(mid) <= x) {
                    index = mid;
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }

            //加1才为小于等于x的个数
            return index + 1;
        }
    }

    /**
     * 线段树
     */
    static class StreamRank2 {
        //线段树
        private final SegmentTree segmentTree;

        public StreamRank2() {
            //元素的左右边界
            //左边界初始化为较小的数，如果初始化为int最小值，在求mid需要转化为long，否则会溢出
            segmentTree = new SegmentTree(Integer.MIN_VALUE / 2, 50000);
        }

        public void track(int x) {
            //元素x出现次数加1
            segmentTree.update(segmentTree.root, x, x, 1);
        }

        public int getRankOfNumber(int x) {
            //查询小于等于x的元素个数
            return segmentTree.query(segmentTree.root, Integer.MIN_VALUE / 2, x);
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
                if (node.leftBound > queryRight || node.rightBound < queryLeft) {
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
                if (node.leftBound > updateRight || node.rightBound < updateLeft) {
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
