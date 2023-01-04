package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/12/12 09:44
 * @Author zsy
 * @Description 我的日程安排表 II 线段树类比Problem307、Problem308、Problem729、Problem732 二分搜索树类比Problem230、Problem378、Problem440
 * 实现一个 MyCalendar 类来存放你的日程安排。如果要添加的时间内不会导致三重预订时，则可以存储这个新的日程安排。
 * MyCalendar 有一个 book(int start, int end)方法。它意味着在 start 到 end 时间内增加一个日程安排，
 * 注意，这里的时间是半开区间，即 [start, end), 实数 x 的范围为， start <= x < end。
 * 当三个日程安排有一些时间上的交叉时（例如三个日程安排都在同一时间内），就会产生三重预订。
 * 每次调用 MyCalendar.book方法时，如果可以将日程安排成功添加到日历中而不会导致三重预订，返回 true。
 * 否则，返回 false 并且不要将该日程安排添加到日历中。
 * 请按照以下步骤调用MyCalendar 类: MyCalendar cal = new MyCalendar(); MyCalendar.book(start, end)
 * <p>
 * MyCalendar();
 * MyCalendar.book(10, 20); // returns true
 * MyCalendar.book(50, 60); // returns true
 * MyCalendar.book(10, 40); // returns true
 * MyCalendar.book(5, 15); // returns false
 * MyCalendar.book(5, 10); // returns true
 * MyCalendar.book(25, 55); // returns true
 * 解释：
 * 前两个日程安排可以添加至日历中。 第三个日程安排会导致双重预订，但可以添加至日历中。
 * 第四个日程安排活动（5,15）不能添加至日历中，因为它会导致三重预订。
 * 第五个日程安排（5,10）可以添加至日历中，因为它未使用已经双重预订的时间10。
 * 第六个日程安排（25,55）可以添加至日历中，因为时间 [25,40] 将和第三个日程安排双重预订；
 * 时间 [40,50] 将单独预订，时间 [50,55）将和第二个日程安排双重预订。
 * <p>
 * 每个测试用例，调用 MyCalendar.book 函数最多不超过 1000次。
 * 调用函数 MyCalendar.book(start, end)时， start 和 end 的取值范围为 [0, 10^9]。
 */
public class Problem731 {
    public static void main(String[] args) {
//        MyCalendarTwo myCalendar = new MyCalendarTwo();
        MyCalendarTwo2 myCalendar = new MyCalendarTwo2();
        System.out.println(myCalendar.book(10, 20));
        System.out.println(myCalendar.book(50, 60));
        System.out.println(myCalendar.book(10, 40));
        System.out.println(myCalendar.book(5, 15));
        System.out.println(myCalendar.book(5, 10));
        System.out.println(myCalendar.book(25, 55));
    }

    /**
     * 暴力
     * 当前区间和第二个存放区间的集合比较，如果存在重叠部分，则导致三重预订，返回false；
     * 如果不存在重叠部分，当前区间再和第一个存放区间的集合比较，将存在重叠的区间部分添加到第二个存放区间的集合中，
     * 将当前区间添加到第一个存放区间的集合中，返回true
     * 时间复杂度O(n^2)，空间复杂度O(n)
     */
    static class MyCalendarTwo {
        //存放日程安排区间的集合，不存在重叠部分的区间存放到list1
        private final List<int[]> list1;
        //存放日程安排区间的集合，存在重叠部分的区间存放到list2
        private final List<int[]> list2;

        public MyCalendarTwo() {
            list1 = new ArrayList<>();
            list2 = new ArrayList<>();
        }

        public boolean book(int start, int end) {
            if (list1.isEmpty()) {
                list1.add(new int[]{start, end});
                return true;
            }

            //当前日程安排区间[start,end)先和list2中区间比较
            for (int[] arr : list2) {
                //当前日程安排区间[start,end)和list2集合中区间存在重叠部分，即存在三重预订
                if (!(arr[0] >= end || arr[1] <= start)) {
                    return false;
                }
            }

            //当前日程安排区间[start,end)再和list1中区间比较
            for (int[] arr : list1) {
                //当前日程安排区间[start,end)和list1集合中区间存在重叠部分，将重叠部分区间添加到list2集合中，表示存在二重重叠
                if (!(arr[0] >= end || arr[1] <= start)) {
                    list2.add(new int[]{Math.max(arr[0], start), Math.min(arr[1], end)});
                }
            }

            //当前日程安排区间[start,end)添加到list1集合中，表示当前区间已经存在一次重叠
            list1.add(new int[]{start, end});

            return true;
        }
    }

    /**
     * 线段树，动态开点，适用于：不知道数组长度的情况下，多次求区间元素之和、区间元素的最大值，并且区间内元素多次修改的情况
     * 时间复杂度O(nlogC)，空间复杂度O(nlogC) (n=插入区间的个数，C=10^9，区间的最大值)
     */
    static class MyCalendarTwo2 {
        //线段树所能表示区间的最大范围[0,maxRight]
        private final int rightMax = (int) 1e9;
        //线段树
        private final SegmentTree segmentTree;

        public MyCalendarTwo2() {
            segmentTree = new SegmentTree();
        }

        public boolean book(int start, int end) {
            //当前区间[start,end)的最大值小于2，即当前区间[start,end)不存在三重预订，可以预定
            if (segmentTree.queryMaxValue(segmentTree.root, 0, rightMax, start, end - 1) < 2) {
                //更新区间[start,end)节点的最大值均加上1，表示多了一重预订
                segmentTree.update(segmentTree.root, 0, rightMax, start, end - 1, 1);
                return true;
            }

            //存在三重预订，即区间[start,end)的最大值大于等于2，不能预定，直接返回false
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
             * 时间复杂度O(logC)，空间复杂度O(logC) (C=10^9，区间的最大值)
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
                node.maxValue = Math.max(node.leftNode.maxValue, node.rightNode.maxValue);
            }

            /**
             * 更新区间[updateIndex,updateIndex]节点值为value
             * 时间复杂度O(logC)，空间复杂度O(logC) (C=10^9，区间的最大值)
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
