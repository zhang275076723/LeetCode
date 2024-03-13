package com.zhang.java;

/**
 * @Date 2022/12/13 10:53
 * @Author zsy
 * @Description 我的日程安排表 III 线段树类比Problem218、Problem307、Problem308、Problem327、Problem370、Problem654、Problem715、Problem729、Problem731、Problem1094、Problem1109、Problem1893、Problem2407
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
     * 线段树，动态开点
     * 适用于：不知道数组长度的情况下，多次求区间元素之和、区间元素的最大值，区间元素的最小值，并且区间内元素多次修改的情况
     * 时间复杂度O(nlogC)，空间复杂度O(nlogC) (n=插入区间的个数，C=10^9，区间的最大值)
     */
    static class MyCalendarThree {
        //线段树
        private final SegmentTree segmentTree;

        public MyCalendarThree() {
            //根节点区间为[0,10^9-1)，因为start、end中end是开区间
            segmentTree = new SegmentTree(0, (int) 1e9 - 1);
        }

        public int book(int start, int end) {
            //更新区间[start,end-1]元素出现次数的最大值加1，表示当前区间多一重重叠
            segmentTree.update(segmentTree.root, start, end - 1, 1);

            //线段树根节点root区间[0,10^9-1]元素出现次数的最大值，即最大的重叠次数
            return segmentTree.query(segmentTree.root, 0, (int) 1e9 - 1);
        }

        /**
         * 线段树，动态开点
         */
        private static class SegmentTree {
            //线段树根节点
            private final SegmentTreeNode root;

            public SegmentTree(int leftBound, int rightBound) {
                root = new SegmentTreeNode(leftBound, rightBound);
            }

            /**
             * 查询区间[queryLeft,queryRight]元素出现次数的最大值
             * 时间复杂度O(logC)，空间复杂度O(logC) (C=10^9，区间的最大值)
             *
             * @param node
             * @param queryLeft
             * @param queryRight
             * @return
             */
            public int query(SegmentTreeNode node, int queryLeft, int queryRight) {
                //要查询的区间[queryLeft,queryRight]不在当前节点表示的范围[node.leftBound,node.rightBound]之内，
                //直接返回0
                if (node.leftBound > queryRight || node.rightBound < queryLeft) {
                    return 0;
                }

                //要查询的区间[queryLeft,queryRight]包含当前节点表示的范围[node.leftBound,node.rightBound]，
                //直接返回当前节点表示区间元素出现次数的最大值value
                if (queryLeft <= node.leftBound && node.rightBound <= queryRight) {
                    return node.value;
                }

                //建议取mid都这样写，不要写成相加再除2，例如区间[-1,0]，按照相加再除2得到mid为0，按照这样得到mid为-1
                int mid = node.leftBound + ((node.rightBound - node.leftBound) >> 1);

                //当前节点左右子树为空，动态开点
                if (node.leftNode == null) {
                    node.leftNode = new SegmentTreeNode(node.leftBound, mid);
                }

                if (node.rightNode == null) {
                    node.rightNode = new SegmentTreeNode(mid + 1, node.rightBound);
                }

                //将当前节点懒标记值向下传递给左右子节点，更新左右子节点表示的区间元素出现次数的最大值、懒标记值，
                //并将当前节点的懒标记值置0
                if (node.lazyValue != 0) {
                    node.leftNode.value = node.leftNode.value + node.lazyValue;
                    node.rightNode.value = node.rightNode.value + node.lazyValue;
                    node.leftNode.lazyValue = node.leftNode.lazyValue + node.lazyValue;
                    node.rightNode.lazyValue = node.rightNode.lazyValue + node.lazyValue;

                    //将懒标记值传递给左右子节点之后，当前节点的懒标记值置为0
                    node.lazyValue = 0;
                }

                //左区间[node.leftBound,mid]元素出现次数的最大值
                int leftValue = query(node.leftNode, queryLeft, queryRight);
                //右区间[mid+1,node.rightBound]元素出现次数的最大值
                int rightValue = query(node.rightNode, queryLeft, queryRight);

                //返回左右子节点区间元素出现次数的最大值中较大的值，即为查询区间[queryLeft,queryRight]元素出现次数的最大值
                return Math.max(leftValue, rightValue);
            }

            /**
             * 更新区间[updateLeft,updateRight]元素出现次数的最大值加上value
             * 时间复杂度O(logC)，空间复杂度O(logC) (C=10^9，即区间的最大值)
             *
             * @param node
             * @param updateLeft
             * @param updateRight
             * @param value
             */
            public void update(SegmentTreeNode node, int updateLeft, int updateRight, int value) {
                //要修改的区间[updateLeft,updateRight]不在当前节点表示的范围[node.leftBound,node.rightBound]之内，
                //直接返回
                if (node.leftBound > updateRight || node.rightBound < updateLeft) {
                    return;
                }

                //要查询的区间[updateLeft,updateRight]包含当前节点表示的范围[node.leftBound,node.rightBound]，
                //修改当前节点表示区间元素出现次数的最大值、懒标记值
                if (updateLeft <= node.leftBound && node.rightBound <= updateRight) {
                    node.value = node.value + value;
                    node.lazyValue = node.lazyValue + value;
                    return;
                }

                //建议取mid都这样写，不要写成相加再除2，例如区间[-1,0]，按照相加再除2得到mid为0，按照这样得到mid为-1
                int mid = node.leftBound + ((node.rightBound - node.leftBound) >> 1);

                //当前节点左右子树为空，动态开点
                if (node.leftNode == null) {
                    node.leftNode = new SegmentTreeNode(node.leftBound, mid);
                }

                if (node.rightNode == null) {
                    node.rightNode = new SegmentTreeNode(mid + 1, node.rightBound);
                }

                //将当前节点懒标记值向下传递给左右子节点，更新左右子节点表示的区间元素出现次数的最大值、懒标记值，
                //并将当前节点的懒标记值置0
                if (node.lazyValue != 0) {
                    node.leftNode.value = node.leftNode.value + node.lazyValue;
                    node.rightNode.value = node.rightNode.value + node.lazyValue;
                    node.leftNode.lazyValue = node.leftNode.lazyValue + node.lazyValue;
                    node.rightNode.lazyValue = node.rightNode.lazyValue + node.lazyValue;

                    //将懒标记值传递给左右子节点之后，当前节点的懒标记值置为0
                    node.lazyValue = 0;
                }

                update(node.leftNode, updateLeft, updateRight, value);
                update(node.rightNode, updateLeft, updateRight, value);

                //更新当前节点表示区间元素出现次数的最大值，即为左右节点表示区间元素出现次数的最大值中较大的值
                node.value = Math.max(node.leftNode.value, node.rightNode.value);
            }

            /**
             * 线段树节点
             * 注意：节点存储的是区间元素出现次数的最大值value
             */
            private static class SegmentTreeNode {
                //当前节点表示区间元素出现次数的最大值
                private int value;
                //懒标记值，当前节点的所有子孙节点表示的区间需要向下传递的值
                private int lazyValue;
                //当前节点表示区间的左边界
                private int leftBound;
                //当前节点表示区间的右边界
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
