package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/12/12 09:44
 * @Author zsy
 * @Description 我的日程安排表 II 线段树类比Problem307、Problem308、Problem327、Problem370、Problem654、Problem715、Problem729、Problem732、Problem1094、Problem1109、Problem1893、Problem2407 二分搜索树类比Problem4、Problem230、Problem378、Problem440
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
        //存放日程安排区间的集合，不存在重叠部分的区间存放到list1，作为二重重叠判断
        private final List<int[]> list1;
        //存放日程安排区间的集合，存在重叠部分的区间存放到list2，作为三重重叠判断
        private final List<int[]> list2;

        public MyCalendarTwo() {
            list1 = new ArrayList<>();
            list2 = new ArrayList<>();
        }

        public boolean book(int start, int end) {
            if (list1.isEmpty()) {
                list1.add(new int[]{start, end - 1});
                return true;
            }

            //当前日程安排区间[start,end-1]先和list2中区间比较，判断是否存在三重重叠
            for (int[] arr : list2) {
                //当前日程安排区间[start,end-1]和list2集合中区间存在重叠部分，即存在三重重叠
                if (!(end <= arr[0] || start > arr[1])) {
                    return false;
                }
            }

            //当不存在三重重叠时，当前日程安排区间[start,end-1]再和list1中区间比较，将重叠区间加入list2集合中，用于三重重叠判断
            for (int[] arr : list1) {
                //当前日程安排区间[start,end-1]和list1集合中区间存在重叠部分，将重叠区间加入list2集合中，用于三重重叠判断
                if (!(end <= arr[0] || start > arr[1])) {
                    list2.add(new int[]{Math.max(start, arr[0]), Math.min(end - 1, arr[1])});
                }
            }

            //当前日程安排区间[start,end-1]添加到list1集合中，表示当前区间已经存在二重重叠
            list1.add(new int[]{start, end - 1});

            return true;
        }
    }

    /**
     * 线段树，动态开点
     * 适用于：不知道数组长度的情况下，多次求区间元素之和、区间元素的最大值，区间元素的最小值，并且区间内元素多次修改的情况
     * 时间复杂度O(nlogC)，空间复杂度O(nlogC) (n=插入区间的个数，C=10^9，区间的最大值)
     */
    static class MyCalendarTwo2 {
        //线段树
        private final SegmentTree segmentTree;

        public MyCalendarTwo2() {
            //根节点区间为[0,10^9-1)，因为start、end中end是开区间
            segmentTree = new SegmentTree(0, (int) 1e9 - 1);
        }

        public boolean book(int start, int end) {
            //区间[start,end-1]元素出现次数的最大值小于等于1，即当前区间[start,end-1]只存在二重重叠，
            //更新区间[start,end-1]元素出现次数的最大值加1，表示当前区间多一重重叠，返回true
            if (segmentTree.query(segmentTree.root, start, end - 1) <= 1) {
                //更新区间[start,end-1]元素出现次数的最大值加上1，表示当前区间多一重重叠
                segmentTree.update(segmentTree.root, start, end - 1, 1);
                return true;
            }

            //区间[start,end-1]元素出现次数的最大值大于1，即当前区间[start,end-1]已经存在三重重叠，返回false
            return false;
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
