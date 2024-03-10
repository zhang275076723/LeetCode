package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/12/11 09:20
 * @Author zsy
 * @Description 我的日程安排表 I 线段树类比Problem307、Problem308、Problem327、Problem370、Problem654、Problem715、Problem731、Problem732、Problem1094、Problem1109、Problem1893、Problem2407 二分搜索树类比Problem4、Problem230、Problem378、Problem440
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
                list.add(new int[]{start, end - 1});
                return true;
            }

            for (int[] arr : list) {
                //当前日程安排区间[start,end-1]和list集合中区间存在重叠，返回false
                if (!(end <= arr[0] || start > arr[1])) {
                    return false;
                }
            }

            //当前日程安排区间[start,end-1]和list集合中所有区间都不重叠，则将当前日程安排区间加入list集合，并返回true
            list.add(new int[]{start, end - 1});
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
     * 线段树，动态开点
     * 适用于：不知道数组长度的情况下，多次求区间元素之和、区间元素的最大值，区间元素的最小值，并且区间内元素多次修改的情况
     * 时间复杂度O(nlogC)，空间复杂度O(nlogC) (n=插入区间的个数，C=10^9，区间的最大值)
     */
    static class MyCalendar4 {
        //线段树
        private final SegmentTree segmentTree;

        public MyCalendar4() {
            //根节点区间为[0,10^9-1)，因为start、end中end是开区间
            segmentTree = new SegmentTree(0, (int) 1e9 - 1);
        }

        public boolean book(int start, int end) {
            //区间[start,end-1]元素出现次数的最大值为0，即当前区间[start,end-1]不存在重叠，
            //更新区间[start,end-1]节点出现次数的最大值加1，表示当前区间已经重叠，返回true
            if (segmentTree.query(segmentTree.root, start, end - 1) == 0) {
                segmentTree.update(segmentTree.root, start, end - 1, 1);
                return true;
            }

            //区间[start,end-1]元素出现次数的最大值大于0，则当前区间[start,end-1]已经存在重叠，返回false
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
