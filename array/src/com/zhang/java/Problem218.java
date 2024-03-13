package com.zhang.java;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @Date 2024/3/3 09:09
 * @Author zsy
 * @Description 天际线问题 类比Problem42、Problem407 线段树类比Problem307、Problem308、Problem327、Problem370、Problem654、Problem715、Problem729、Problem731、Problem732、Problem1094、Problem1109、Problem1893、Problem2407 优先队列类比
 * 城市的 天际线 是从远处观看该城市中所有建筑物形成的轮廓的外部轮廓。
 * 给你所有建筑物的位置和高度，请返回 由这些建筑物形成的 天际线 。
 * 每个建筑物的几何信息由数组 buildings 表示，其中三元组 buildings[i] = [lefti, righti, heighti] 表示：
 * lefti 是第 i 座建筑物左边缘的 x 坐标。
 * righti 是第 i 座建筑物右边缘的 x 坐标。
 * heighti 是第 i 座建筑物的高度。
 * 你可以假设所有的建筑都是完美的长方形，在高度为 0 的绝对平坦的表面上。
 * 天际线 应该表示为由 “关键点” 组成的列表，格式 [[x1,y1],[x2,y2],...] ，并按 x 坐标 进行 排序 。
 * 关键点是水平线段的左端点。列表中最后一个点是最右侧建筑物的终点，y 坐标始终为 0 ，仅用于标记天际线的终点。
 * 此外，任何两个相邻建筑物之间的地面都应被视为天际线轮廓的一部分。
 * 注意：输出天际线中不得有连续的相同高度的水平线。
 * 例如 [...[2 3], [4 5], [7 5], [11 5], [12 7]...] 是不正确的答案；
 * 三条高度为 5 的线应该在最终输出中合并为一个：[...[2 3], [4 5], [12 7], ...]
 * <p>
 * 输入：buildings = [[2,9,10],[3,7,15],[5,12,12],[15,20,10],[19,24,8]]
 * 输出：[[2,10],[3,15],[7,12],[12,0],[15,10],[20,8],[24,0]]
 * 解释：
 * 图 A 显示输入的所有建筑物的位置和高度，
 * 图 B 显示由这些建筑物形成的天际线。图 B 中的红点表示输出列表中的关键点。
 * <p>
 * 输入：buildings = [[0,2,3],[2,5,3]]
 * 输出：[[0,3],[5,0]]
 * <p>
 * 1 <= buildings.length <= 10^4
 * 0 <= lefti < righti <= 2^31 - 1
 * 1 <= heighti <= 2^31 - 1
 * buildings 按 lefti 非递减排序
 */
public class Problem218 {
    public static void main(String[] args) {
        Problem218 problem218 = new Problem218();
        int[][] buildings = {{2, 9, 10}, {3, 7, 15}, {5, 12, 12}, {15, 20, 10}, {19, 24, 8}};
        System.out.println(problem218.getSkyline(buildings));
        System.out.println(problem218.getSkyline2(buildings));
        System.out.println(problem218.getSkyline3(buildings));
    }

    /**
     * 暴力
     * 核心思想：找包含当前左右边界的横坐标的所有建筑中的最大高度作为当前横坐标的纵坐标
     * buildings[i]左右边界的横坐标由小到大排序，遍历排序后的左右边界横坐标，找包含当前横坐标的所有建筑中的最大高度作为纵坐标，
     * 如果最大高度和上一次遍历的最大高度不同，即保证题目要求输出天际线中不得有连续的相同高度的水平线，则构成关键点
     * 注意：如果当前横坐标是某个建筑的右边界，则当前建筑不包含当前横坐标
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param buildings
     * @return
     */
    public List<List<Integer>> getSkyline(int[][] buildings) {
        //存储buildings[i]左右边界的横坐标的list集合
        List<Integer> indexList = new ArrayList<>();

        for (int i = 0; i < buildings.length; i++) {
            indexList.add(buildings[i][0]);
            indexList.add(buildings[i][1]);
        }

        //横坐标由小到大排序
        indexList.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return a - b;
            }
        });

        List<List<Integer>> result = new ArrayList<>();
        //上一次遍历的最大高度
        int lastMaxHeight = 0;

        for (int i = 0; i < indexList.size(); i++) {
            //当前左右边界的横坐标
            int index = indexList.get(i);
            //包含当前横坐标的所有建筑中的最大高度
            int curMaxHeight = 0;

            for (int j = 0; j < buildings.length; j++) {
                //注意：如果当前横坐标是某个建筑的右边界，则当前建筑不包含当前横坐标
                if (buildings[j][0] <= index && index < buildings[j][1]) {
                    curMaxHeight = Math.max(curMaxHeight, buildings[j][2]);
                }
            }

            //最大高度和上一次遍历的最大高度不同，即保证题目要求输出天际线中不得有连续的相同高度的水平线，则构成关键点
            if (curMaxHeight != lastMaxHeight) {
                List<Integer> list = new ArrayList<>();
                list.add(index);
                list.add(curMaxHeight);
                result.add(list);

                //更新lastMaxHeight
                lastMaxHeight = curMaxHeight;
            }
        }

        return result;
    }

    /**
     * 线段树(动态开点)
     * 核心思想：找包含当前左右边界的横坐标的所有建筑中的最大高度作为当前横坐标的纵坐标
     * 通过线段树O(logn)获取包含当前横坐标的所有建筑中的最大高度
     * buildings[i]左右边界的横坐标由小到大排序，遍历排序后的左右边界横坐标，找包含当前横坐标的所有建筑中的最大高度作为纵坐标，
     * 如果最大高度和上一次遍历的最大高度不同，即保证题目要求输出天际线中不得有连续的相同高度的水平线，则构成关键点
     * 注意：线段树中当前建筑的高度不包含当前建筑的右边界
     * 时间复杂度O(nlogn+nlogm)，空间复杂度O(m+n) (n=buildings.length，m=max(buildings[i][1]))
     *
     * @param buildings
     * @return
     */
    public List<List<Integer>> getSkyline2(int[][] buildings) {
        //存储buildings[i]左右边界的横坐标的list集合
        List<Integer> indexList = new ArrayList<>();
        //线段树所表示区间的左边界
        int leftBound = Integer.MAX_VALUE;
        //线段树所表示区间的右边界
        int rightBound = Integer.MIN_VALUE;

        for (int i = 0; i < buildings.length; i++) {
            indexList.add(buildings[i][0]);
            indexList.add(buildings[i][1]);
            leftBound = Math.min(leftBound, buildings[i][0]);
            rightBound = Math.max(rightBound, buildings[i][1]);
        }

        //横坐标由小到大排序
        indexList.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return a - b;
            }
        });

        //线段树，动态开点
        SegmentTree segmentTree = new SegmentTree(leftBound, rightBound);

        for (int i = 0; i < buildings.length; i++) {
            //注意：线段树中当前建筑的高度不包含当前建筑的右边界
            segmentTree.update(segmentTree.root, buildings[i][0], buildings[i][1] - 1, buildings[i][2]);
        }

        List<List<Integer>> result = new ArrayList<>();
        //上一次遍历的最大高度
        int lastMaxHeight = 0;

        for (int i = 0; i < indexList.size(); i++) {
            //当前左右边界的横坐标
            int index = indexList.get(i);
            //当前横坐标的最大高度
            int curMaxHeight = segmentTree.query(segmentTree.root, index, index);

            //最大高度和上一次遍历的最大高度不同，即保证题目要求输出天际线中不得有连续的相同高度的水平线，则构成关键点
            if (curMaxHeight != lastMaxHeight) {
                List<Integer> list = new ArrayList<>();
                list.add(index);
                list.add(curMaxHeight);
                result.add(list);

                //更新lastMaxHeight
                lastMaxHeight = curMaxHeight;
            }
        }

        return result;
    }

    /**
     * 优先队列，大根堆
     * 核心思想：找包含当前左右边界的横坐标的所有建筑中的最大高度作为当前横坐标的纵坐标
     * buildings[i]左右边界的横坐标由小到大排序，遍历排序后的左右边界横坐标，当前横坐标大于等于建筑左边界的建筑入堆，
     * 并且当前横坐标大于等于堆顶建筑右边界的建筑出堆，保证堆顶即为当前横坐标的最大高度，
     * 如果最大高度和上一次遍历的最大高度不同，即保证题目要求输出天际线中不得有连续的相同高度的水平线，则构成关键点
     * 注意：如果当前横坐标是某个建筑的右边界，则当前建筑不包含当前横坐标
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param buildings
     * @return
     */
    public List<List<Integer>> getSkyline3(int[][] buildings) {
        //存储buildings[i]左右边界的横坐标的list集合
        List<Integer> indexList = new ArrayList<>();

        for (int i = 0; i < buildings.length; i++) {
            indexList.add(buildings[i][0]);
            indexList.add(buildings[i][1]);
        }

        //横坐标由小到大排序
        indexList.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return a - b;
            }
        });

        List<List<Integer>> result = new ArrayList<>();
        //大根堆，arr[0]：建筑的右边界，arr[1]：建筑的高度
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                //先按照建筑的高度由大到小排序，再按照建筑的右边界由小到大排序
                if (arr1[1] != arr2[1]) {
                    return arr2[1] - arr1[1];
                } else {
                    return arr1[0] - arr2[0];
                }
            }
        });
        //buildings当前遍历到的下标索引
        int j = 0;
        //上一次遍历的最大高度
        int lastMaxHeight = 0;

        for (int i = 0; i < indexList.size(); i++) {
            //当前左右边界的横坐标
            int index = indexList.get(i);

            //当前横坐标大于等于建筑左边界的建筑入堆
            while (j < buildings.length && buildings[j][0] <= index) {
                priorityQueue.offer(new int[]{buildings[j][1], buildings[j][2]});
                j++;
            }

            //当前横坐标大于等于堆顶建筑右边界的建筑出堆，保证堆顶即为当前横坐标的最大高度
            //注意：如果当前横坐标是某个建筑的右边界，则当前建筑不包含当前横坐标，所以index是大于等于，而不是大于
            while (!priorityQueue.isEmpty() && priorityQueue.peek()[0] <= index) {
                priorityQueue.poll();
            }

            //当前横坐标的最大高度
            int curMaxHeight;

            //大根堆为空，则当前横坐标的最大高度为0
            if (priorityQueue.isEmpty()) {
                curMaxHeight = 0;
            } else {
                //大根堆不为空，则当前横坐标的最大高度为堆顶元素的高度
                curMaxHeight = priorityQueue.peek()[1];
            }

            //最大高度和上一次遍历的最大高度不同，即保证题目要求输出天际线中不得有连续的相同高度的水平线，则构成关键点
            if (curMaxHeight != lastMaxHeight) {
                List<Integer> list = new ArrayList<>();
                list.add(index);
                list.add(curMaxHeight);
                result.add(list);

                //更新lastMaxHeight
                lastMaxHeight = curMaxHeight;
            }
        }

        return result;
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
                return node.maxValue;
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
                //注意：lazyValue为当前节点的所有子孙节点表示区间的最大高度
                node.leftNode.maxValue = Math.max(node.leftNode.maxValue, node.lazyValue);
                node.rightNode.maxValue = Math.max(node.rightNode.maxValue, node.lazyValue);
                node.leftNode.lazyValue = Math.max(node.leftNode.lazyValue, node.lazyValue);
                node.rightNode.lazyValue = Math.max(node.rightNode.lazyValue, node.lazyValue);

                node.lazyValue = 0;
            }

            int leftValue = query(node.leftNode, queryLeft, queryRight);
            int rightValue = query(node.rightNode, queryLeft, queryRight);

            return Math.max(leftValue, rightValue);
        }

        /**
         * 更新区间[updateLeft,updateRight]所有最大值小于value的区间的最大值更新为value
         * 时间复杂度O(logn)，空间复杂度O(logn)
         *
         * @param node
         * @param updateLeft
         * @param updateRight
         * @param value
         */
        public void update(SegmentTreeNode node, int updateLeft, int updateRight, int value) {
            if (updateLeft > node.rightBound || updateRight < node.leftBound) {
                return;
            }

            if (updateLeft <= node.leftBound && node.rightBound <= updateRight) {
                node.maxValue = Math.max(node.maxValue, value);
                node.lazyValue = Math.max(node.lazyValue, value);
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
                //注意：lazyValue为当前节点的所有子孙节点表示区间的最大高度
                node.leftNode.maxValue = Math.max(node.leftNode.maxValue, node.lazyValue);
                node.rightNode.maxValue = Math.max(node.rightNode.maxValue, node.lazyValue);
                node.leftNode.lazyValue = Math.max(node.leftNode.lazyValue, node.lazyValue);
                node.rightNode.lazyValue = Math.max(node.rightNode.lazyValue, node.lazyValue);

                node.lazyValue = 0;
            }

            update(node.leftNode, updateLeft, updateRight, value);
            update(node.rightNode, updateLeft, updateRight, value);

            node.maxValue = Math.max(node.leftNode.maxValue, node.rightNode.maxValue);
        }

        /**
         * 线段树节点
         */
        private static class SegmentTreeNode {
            //区间[leftBound,rightBound]的最大值
            private int maxValue;
            //当前节点的所有子孙节点表示区间的最大值
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
