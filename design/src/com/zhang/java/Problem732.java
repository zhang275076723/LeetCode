package com.zhang.java;

/**
 * @Date 2022/12/13 10:53
 * @Author zsy
 * @Description 我的日程安排表 III 线段树类比Problem307、Problem308、Problem729、Problem731 二分搜索树类比Problem4、Problem230、Problem378、Problem440
 * 当 k 个日程安排有一些时间上的交叉时（例如 k 个日程安排都在同一时间内），就会产生 k 次预订。
 * 给你一些日程安排 [start, end) ，请你在每个日程安排添加后，返回一个整数 k ，表示所有先前日程安排会产生的最大 k 次预订。
 * 实现一个 MyCalendarThree 类来存放你的日程安排，你可以一直添加新的日程安排。
 * MyCalendarThree() 初始化对象。
 * int book(int start, int end) 返回一个整数 k ，表示日历中存在的 k 次预订的最大值。
 * <p>
 * 输入：
 * ["MyCalendarThree", "book", "book", "book", "book", "book", "book"]
 * [[], [10, 20], [50, 60], [10, 40], [5, 15], [5, 10], [25, 55]]
 * 输出：
 * [null, 1, 1, 2, 3, 3, 3]
 * 解释：
 * MyCalendarThree myCalendarThree = new MyCalendarThree();
 * myCalendarThree.book(10, 20); // 返回 1 ，第一个日程安排可以预订并且不存在相交，所以最大 k 次预订是 1 次预订。
 * myCalendarThree.book(50, 60); // 返回 1 ，第二个日程安排可以预订并且不存在相交，所以最大 k 次预订是 1 次预订。
 * myCalendarThree.book(10, 40); // 返回 2 ，第三个日程安排 [10, 40) 与第一个日程安排相交，所以最大 k 次预订是 2 次预订。
 * myCalendarThree.book(5, 15); // 返回 3 ，剩下的日程安排的最大 k 次预订是 3 次预订。
 * myCalendarThree.book(5, 10); // 返回 3
 * myCalendarThree.book(25, 55); // 返回 3
 * <p>
 * 0 <= start < end <= 10^9
 * 每个测试用例，调用 book 函数最多不超过 400次
 */
public class Problem732 {
    public static void main(String[] args) {
        MyCalendarThree myCalendar = new MyCalendarThree();
        System.out.println(myCalendar.book(10, 20));
        System.out.println(myCalendar.book(50, 60));
        System.out.println(myCalendar.book(10, 40));
        System.out.println(myCalendar.book(5, 15));
        System.out.println(myCalendar.book(5, 10));
        System.out.println(myCalendar.book(25, 55));
    }

    /**
     * 线段树，动态开点，适用于：不知道数组长度的情况下，多次求区间元素之和、区间元素的最大值，并且区间内元素多次修改的情况
     * 时间复杂度O(nlogC)，空间复杂度O(nlogC) (n=插入区间的个数，C=10^9，区间的最大值)
     */
    static class MyCalendarThree {
        //线段树所能表示区间的最大范围[0,maxRight]
        private final int maxRight = (int) 1e9;
        //线段树
        private final SegmentTree segmentTree;

        public MyCalendarThree() {
            segmentTree = new SegmentTree();
        }

        public int book(int start, int end) {
            //更新区间[start,end)节点的最大值均加1，表示多了一重预订
            segmentTree.update(segmentTree.root, 0, maxRight, start, end - 1, 1);

            //根节点root表示的区间[0,maxRight]元素的最大值，即存在的最大预订次数
            return segmentTree.root.maxValue;
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
