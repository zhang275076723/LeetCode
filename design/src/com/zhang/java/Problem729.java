package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/12/11 09:20
 * @Author zsy
 * @Description 我的日程安排表 I 线段树类比Problem307、Problem308、Problem731、Problem732 二分搜索树类比Problem4、Problem230、Problem378、Problem440
 * 实现一个 MyCalendar 类来存放你的日程安排。如果要添加的日程安排不会造成 重复预订 ，则可以存储这个新的日程安排。
 * 当两个日程安排有一些时间上的交叉时（例如两个日程安排都在同一时间内），就会产生 重复预订 。
 * 日程可以用一对整数 start 和 end 表示，这里的时间是半开区间，即 [start, end), 实数 x 的范围为， start <= x < end 。
 * 实现 MyCalendar 类：
 * MyCalendar() 初始化日历对象。
 * boolean book(int start, int end) 如果可以将日程安排成功添加到日历中而不会导致重复预订，返回 true 。
 * 否则，返回 false 并且不要将该日程安排添加到日历中。
 * <p>
 * 输入：
 * ["MyCalendar", "book", "book", "book"]
 * [[], [10, 20], [15, 25], [20, 30]]
 * 输出：
 * [null, true, false, true]
 * 解释：
 * MyCalendar myCalendar = new MyCalendar();
 * myCalendar.book(10, 20); // return True
 * myCalendar.book(15, 25); // return False ，这个日程安排不能添加到日历中，因为时间 15 已经被另一个日程安排预订了。
 * myCalendar.book(20, 30); // return True ，这个日程安排可以添加到日历中，因为第一个日程安排预订的每个时间都小于 20 ，且不包含时间 20 。
 * <p>
 * 0 <= start < end <= 10^9
 * 每个测试用例，调用 book 方法的次数最多不超过 1000 次。
 */
public class Problem729 {
    public static void main(String[] args) {
//        MyCalendar myCalendar = new MyCalendar();
//        MyCalendar2 myCalendar = new MyCalendar2();
//        MyCalendar3 myCalendar = new MyCalendar3();
        MyCalendar4 myCalendar = new MyCalendar4();
        System.out.println(myCalendar.book(10, 20));
        System.out.println(myCalendar.book(15, 25));
        System.out.println(myCalendar.book(20, 30));
    }

    /**
     * 暴力
     * 将每个日程安排区间存储到集合中，遍历集合中日程安排区间是否和当前日程安排区间重叠
     * 时间复杂度O(n^2)，空间复杂度O(n)
     */
    static class MyCalendar {
        private final List<int[]> list;

        public MyCalendar() {
            list = new ArrayList<>();
        }

        public boolean book(int start, int end) {
            if (list.isEmpty()) {
                list.add(new int[]{start, end});
                return true;
            }

            for (int[] arr : list) {
                //当前日程安排区间[start,end)和list集合中区间存在重叠
                if (!(end <= arr[0] || arr[1] <= start)) {
                    return false;
                }
            }

            //当前日程安排区间和list集合中所有区间都不重叠，则将当前日程安排区间加入list集合
            list.add(new int[]{start, end});

            return true;
        }
    }

    /**
     * 二分查找
     * 将每个日程安排区间存储到集合中，二分查找集合中日程安排区间第一个右边界大于start的区间，是否和当前日程安排区间重叠
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     */
    static class MyCalendar2 {
        //二分查找保证集合按照区间左边界是有序的
        private final List<int[]> list;

        public MyCalendar2() {
            list = new ArrayList<>();
        }

        public boolean book(int start, int end) {
            if (list.isEmpty()) {
                list.add(new int[]{start, end});
                return true;
            }

            int left = 0;
            int right = list.size() - 1;
            int mid;
            //list中第一个右边界大于start的区间
            int[] target = null;
            //target在list集合中的下标索引
            int index = -1;

            while (left <= right) {
                mid = left + ((right - left) >> 1);

                if (list.get(mid)[1] <= start) {
                    left = mid + 1;
                } else {
                    target = list.get(mid);
                    index = mid;
                    right = mid - 1;
                }
            }

            //当前日程安排区间比集合中所有日程安排区间都要大，没有重叠
            if (target == null) {
                list.add(new int[]{start, end});
                return true;
            }

            //当前日程安排区间与target不重叠，则可以添加
            if (end <= target[0]) {
                //在索引下标mid添加，保证左区间按照由小到大排序
                list.add(index, new int[]{start, end});
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 二分搜索树
     * 当前日程安排区间和当前节点区间重叠，则不能添加，返回false
     * 当前日程安排区间在当前节点区间左边，则往左边找；当前日程安排区间在当前节点区间右边，则往右边找，
     * 直至当前节点为空节点，则可以添加，创建节点添加到树中，返回true
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     */
    static class MyCalendar3 {
        private TreeNode root;

        public MyCalendar3() {
        }

        public boolean book(int start, int end) {
            if (root == null) {
                root = new TreeNode(start, end);
            }

            TreeNode node = root;

            while (true) {
                //当前日程安排区间再当前节点区间左边，往左边找
                if (end <= node.start) {
                    //当前节点左子树为空，说明当前日程安排区间可以插入树中，返回true
                    if (node.leftNode == null) {
                        node.leftNode = new TreeNode(start, end);
                        return true;
                    } else {
                        node = node.leftNode;
                    }
                } else if (start >= node.end) {
                    //当前日程安排区间再当前节点区间右边，往右边找

                    //当前节点右子树为空，说明当前日程安排区间可以插入树中，返回true
                    if (node.rightNode == null) {
                        node.rightNode = new TreeNode(start, end);
                        return true;
                    } else {
                        node = node.rightNode;
                    }
                } else {
                    //当前区间和当前节点区间重叠
                    return false;
                }
            }
        }

        private static class TreeNode {
            int start;
            int end;
            TreeNode leftNode;
            TreeNode rightNode;

            TreeNode() {
            }

            TreeNode(int start, int end) {
                this.start = start;
                this.end = end;
            }
        }
    }

    /**
     * 线段树，动态开点，适用于：不知道数组长度的情况下，多次求区间元素之和、区间元素的最大值，并且区间内元素多次修改的情况
     * 时间复杂度O(nlogC)，空间复杂度O(nlogC) (n=插入区间的个数，C=10^9，区间的最大值)
     */
    static class MyCalendar4 {
        //线段树所能表示区间的最大范围[0,maxRight]
        private final int maxRight = (int) 1e9;
        //线段树
        private final SegmentTree segmentTree;

        public MyCalendar4() {
            segmentTree = new SegmentTree();
        }

        public boolean book(int start, int end) {
            //区间[start,end)元素之和为0，即当前区间[start,end)可以插入，更新区间[start,end)节点值加上1，表示当前区间已被预定
            if (segmentTree.querySumValue(segmentTree.root, 0, maxRight, start, end - 1) == 0) {
                segmentTree.update(segmentTree.root, 0, maxRight, start, end - 1, 1);
                return true;
            }

            //区间[start,end)元素之和不为0，即当前区间不能[start,end)预定，返回false
            return false;
        }

        /**
         * 线段树，动态开点
         * 注意：线段树的update()区间长度超过1是区间节点值加上value，update()区间长度为1是更新区间节点值为value
         */
        private static class SegmentTree {
            //线段树根节点
            private final SegmentTreeNode root;

            SegmentTree() {
                root = new SegmentTreeNode();
            }

            /**
             * 查询区间[queryLeft,queryRight]元素之和
             * 时间复杂度O(logC)，空间复杂度O(logC) (C=10^9，区间的最大值)
             *
             * @param node
             * @param left
             * @param right
             * @param queryLeft
             * @param queryRight
             * @return
             */
            private int querySumValue(SegmentTreeNode node, int left, int right, int queryLeft, int queryRight) {
                //要查询区间[queryLeft,queryRight]不在当前节点表示的范围[left,right]之内，直接返回0
                if (left > queryRight || right < queryLeft) {
                    return 0;
                }

                //要查询区间[queryLeft,queryRight]包含当前节点表示的范围[left,right]，直接返回当前节点表示区间元素之和sumValue
                if (queryLeft <= left && right <= queryRight) {
                    return node.sumValue;
                }

                //当前节点左右子树为空，动态开点
                if (node.leftNode == null) {
                    node.leftNode = new SegmentTreeNode();
                }

                if (node.rightNode == null) {
                    node.rightNode = new SegmentTreeNode();
                }

                int mid = left + ((right - left) >> 1);

                //将当前节点懒标记值向下传递给左右子节点，更新左右子节点表示的区间元素之和、区间元素的最大值、懒标记值，并将当前节点的懒标记值置0
                if (node.lazyValue != 0) {
                    node.leftNode.sumValue = node.leftNode.sumValue + node.lazyValue * (mid - left + 1);
                    node.rightNode.sumValue = node.rightNode.sumValue + node.lazyValue * (right - mid);
                    node.leftNode.maxValue = node.leftNode.maxValue + node.lazyValue;
                    node.rightNode.maxValue = node.rightNode.maxValue + node.lazyValue;
                    node.leftNode.lazyValue = node.leftNode.lazyValue + node.lazyValue;
                    node.rightNode.lazyValue = node.rightNode.lazyValue + node.lazyValue;

                    //将懒标记值传递给左右子节点之后，当前节点的懒标记值置为0
                    node.lazyValue = 0;
                }

                int leftValue = querySumValue(node.leftNode, left, mid, queryLeft, queryRight);
                int rightValue = querySumValue(node.rightNode, mid + 1, right, queryLeft, queryRight);

                //返回左右子节点区间元素之和相加，即为查询区间[queryLeft,queryRight]元素之和
                return leftValue + rightValue;
            }

            /**
             * 查询区间[queryLeft,queryRight]元素的最大值
             * 时间复杂度O(logC)，空间复杂度O(logC) (C=10^9，区间的最大值)
             *
             * @param node
             * @param left
             * @param right
             * @param queryLeft
             * @param queryRight
             * @return
             */
            private int queryMaxValue(SegmentTreeNode node, int left, int right, int queryLeft, int queryRight) {
                //要查询区间[queryLeft,queryRight]不在当前节点表示的范围[left,right]之内，直接返回0
                if (left > queryRight || right < queryLeft) {
                    return 0;
                }

                //要查询区间[queryLeft,queryRight]包含当前节点表示的范围[left,right]，直接返回当前节点表示区间元素的最大值maxValue
                if (queryLeft <= left && right <= queryRight) {
                    return node.maxValue;
                }

                //当前节点左右子树为空，动态开点
                if (node.leftNode == null) {
                    node.leftNode = new SegmentTreeNode();
                }

                if (node.rightNode == null) {
                    node.rightNode = new SegmentTreeNode();
                }

                int mid = left + ((right - left) >> 1);

                //将当前节点懒标记值向下传递给左右子节点，更新左右子节点表示的区间元素之和、区间元素的最大值、懒标记值，并将当前节点的懒标记值置0
                if (node.lazyValue != 0) {
                    node.leftNode.sumValue = node.leftNode.sumValue + node.lazyValue * (mid - left + 1);
                    node.rightNode.sumValue = node.rightNode.sumValue + node.lazyValue * (right - mid);
                    node.leftNode.maxValue = node.leftNode.maxValue + node.lazyValue;
                    node.rightNode.maxValue = node.rightNode.maxValue + node.lazyValue;
                    node.leftNode.lazyValue = node.leftNode.lazyValue + node.lazyValue;
                    node.rightNode.lazyValue = node.rightNode.lazyValue + node.lazyValue;

                    //将懒标记值传递给左右子节点之后，当前节点的懒标记值置为0
                    node.lazyValue = 0;
                }

                int leftValue = queryMaxValue(node.leftNode, left, mid, queryLeft, queryRight);
                int rightValue = queryMaxValue(node.rightNode, mid + 1, right, queryLeft, queryRight);

                //返回左右子节点区间元素的最大值中较大的值，即为查询区间[queryLeft,queryRight]元素的最大值
                return Math.max(leftValue, rightValue);
            }

            /**
             * 更新区间[updateLeft,updateRight]节点值加上value
             * 时间复杂度O(logC)，空间复杂度O(logC) (C=10^9，即区间的最大值)
             *
             * @param node
             * @param left
             * @param right
             * @param updateLeft
             * @param updateRight
             * @param value
             */
            private void update(SegmentTreeNode node, int left, int right, int updateLeft, int updateRight, int value) {
                //要修改的区间[updateLeft,updateRight]不在当前节点表示的范围[left,right]之内，直接返回
                if (left > updateRight || right < updateLeft) {
                    return;
                }

                //要修改的区间[updateLeft,updateRight]包含当前节点表示的范围[left,right]，修改当前节点表示区间元素之和、区间元素的最大值、懒标记值
                if (updateLeft <= left && right <= updateRight) {
                    node.sumValue = node.sumValue + value * (right - left + 1);
                    node.maxValue = node.maxValue + value;
                    node.lazyValue = node.lazyValue + value;
                    return;
                }

                //当前节点左右子树为空，动态开点
                if (node.leftNode == null) {
                    node.leftNode = new SegmentTreeNode();
                }

                if (node.rightNode == null) {
                    node.rightNode = new SegmentTreeNode();
                }

                int mid = left + ((right - left) >> 1);

                //将当前节点懒标记值向下传递给左右子节点，更新左右子节点表示的区间元素之和、区间元素的最大值、懒标记值，并将当前节点的懒标记值置0
                if (node.lazyValue != 0) {
                    node.leftNode.sumValue = node.leftNode.sumValue + node.lazyValue * (mid - left + 1);
                    node.rightNode.sumValue = node.rightNode.sumValue + node.lazyValue * (right - mid);
                    node.leftNode.maxValue = node.leftNode.maxValue + node.lazyValue;
                    node.rightNode.maxValue = node.rightNode.maxValue + node.lazyValue;
                    node.leftNode.lazyValue = node.leftNode.lazyValue + node.lazyValue;
                    node.rightNode.lazyValue = node.rightNode.lazyValue + node.lazyValue;

                    //将懒标记值传递给左右子节点之后，当前节点的懒标记值置为0
                    node.lazyValue = 0;
                }

                update(node.leftNode, left, mid, updateLeft, updateRight, value);
                update(node.rightNode, mid + 1, right, updateLeft, updateRight, value);

                //更新当前节点表示的区间元素之和
                node.sumValue = node.leftNode.sumValue + node.rightNode.sumValue;
                //更新当前节点表示的区间元素的最大值，即为左右节点表示区间元素的最大值中较大的值
                node.maxValue = Math.max(node.leftNode.maxValue,node.rightNode.maxValue);
            }

            /**
             * 更新区间[updateIndex,updateIndex]节点值为value
             * 时间复杂度O(logC)，空间复杂度O(logC) (C=10^9，即区间的最大值)
             *
             * @param node
             * @param left
             * @param right
             * @param updateIndex
             * @param value
             */
            private void update(SegmentTreeNode node, int left, int right, int updateIndex, int value) {
                //要修改的区间[updateIndex,updateIndex]不在当前节点表示的范围[left,right]之内，直接返回
                if (!(left <= updateIndex && updateIndex <= right)) {
                    return;
                }

                //找到要修改的区间[updateIndex,updateIndex]，进行修改
                if (left == right) {
                    node.sumValue = value;
                    node.maxValue = value;
                    return;
                }

                //当前节点左右子树为空，动态开点
                if (node.leftNode == null) {
                    node.leftNode = new SegmentTreeNode();
                }

                if (node.rightNode == null) {
                    node.rightNode = new SegmentTreeNode();
                }

                int mid = left + ((right - left) >> 1);

                //将当前节点懒标记值向下传递给左右子节点，更新左右子节点表示的区间元素之和、区间元素的最大值、懒标记值，并将当前节点的懒标记值置0
                if (node.lazyValue != 0) {
                    node.leftNode.sumValue = node.leftNode.sumValue + node.lazyValue * (mid - left + 1);
                    node.rightNode.sumValue = node.rightNode.sumValue + node.lazyValue * (right - mid);
                    node.leftNode.maxValue = node.leftNode.maxValue + node.lazyValue;
                    node.rightNode.maxValue = node.rightNode.maxValue + node.lazyValue;
                    node.leftNode.lazyValue = node.leftNode.lazyValue + node.lazyValue;
                    node.rightNode.lazyValue = node.rightNode.lazyValue + node.lazyValue;

                    //将懒标记值传递给左右子节点之后，当前节点的懒标记值置为0
                    node.lazyValue = 0;
                }

                update(node.leftNode, left, mid, updateIndex, value);
                update(node.rightNode, mid + 1, right, updateIndex, value);

                //更新当前节点表示的区间元素之和
                node.sumValue = node.leftNode.sumValue + node.rightNode.sumValue;
                //更新当前节点表示的区间元素的最大值，即为左右节点表示区间元素的最大值中较大的值
                node.maxValue = Math.max(node.leftNode.maxValue, node.rightNode.maxValue);
            }

            /**
             * 线段树节点
             * 注意：节点存储的是区间元素之和sumValue，和区间元素的最大值maxValue
             */
            private static class SegmentTreeNode {
                //当前节点表示区间元素之和
                int sumValue;
                //当前节点表示区间元素的最大值
                int maxValue;
                //懒标记值，当前节点的所有子孙节点表示的区间需要向下传递的值
                int lazyValue;
                SegmentTreeNode leftNode;
                SegmentTreeNode rightNode;
            }
        }
    }
}
