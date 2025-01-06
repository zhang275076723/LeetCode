package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2024/3/4 08:23
 * @Author zsy
 * @Description 掉落的方块 类比Problem42、Problem218、Problem407 线段树类比Problem218、Problem307、Problem308、Problem327、Problem370、Problem654、Problem715、Problem729、Problem731、Problem732、Problem933、Problem1094、Problem1109、Problem1893、Problem2407
 * 在二维平面上的 x 轴上，放置着一些方块。
 * 给你一个二维整数数组 positions ，其中 positions[i] = [lefti, sideLengthi] 表示：
 * 第 i 个方块边长为 sideLengthi ，其左侧边与 x 轴上坐标点 lefti 对齐。
 * 每个方块都从一个比目前所有的落地方块更高的高度掉落而下。
 * 方块沿 y 轴负方向下落，直到着陆到 另一个正方形的顶边 或者是 x 轴上 。
 * 一个方块仅仅是擦过另一个方块的左侧边或右侧边不算着陆。
 * 一旦着陆，它就会固定在原地，无法移动。
 * 在每个方块掉落后，你必须记录目前所有已经落稳的 方块堆叠的最高高度 。
 * 返回一个整数数组 ans ，其中 ans[i] 表示在第 i 块方块掉落后堆叠的最高高度。
 * <p>
 * 输入：positions = [[1,2],[2,3],[6,1]]
 * 输出：[2,5,5]
 * 解释：
 * 第 1 个方块掉落后，最高的堆叠由方块 1 组成，堆叠的最高高度为 2 。
 * 第 2 个方块掉落后，最高的堆叠由方块 1 和 2 组成，堆叠的最高高度为 5 。
 * 第 3 个方块掉落后，最高的堆叠仍然由方块 1 和 2 组成，堆叠的最高高度为 5 。
 * 因此，返回 [2, 5, 5] 作为答案。
 * <p>
 * 输入：positions = [[100,100],[200,100]]
 * 输出：[100,100]
 * 解释：
 * 第 1 个方块掉落后，最高的堆叠由方块 1 组成，堆叠的最高高度为 100 。
 * 第 2 个方块掉落后，最高的堆叠可以由方块 1 组成也可以由方块 2 组成，堆叠的最高高度为 100 。
 * 因此，返回 [100, 100] 作为答案。
 * 注意，方块 2 擦过方块 1 的右侧边，但不会算作在方块 1 上着陆。
 * <p>
 * 1 <= positions.length <= 1000
 * 1 <= lefti <= 10^8
 * 1 <= sideLengthi <= 10^6
 */
public class Problem699 {
    public static void main(String[] args) {
        Problem699 problem699 = new Problem699();
        int[][] positions = {{1, 2}, {2, 3}, {6, 1}};
        System.out.println(problem699.fallingSquares(positions));
        System.out.println(problem699.fallingSquares2(positions));
    }

    /**
     * 暴力
     * 核心思想：找当前方块掉落之前当前方块区间的最大高度，最大高度加上当前方块高度作为当前区间的高度
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param positions
     * @return
     */
    public List<Integer> fallingSquares(int[][] positions) {
        List<Integer> list = new ArrayList<>();
        //遍历到当前方块之前已经遍历过的方块区间list集合
        //arr[0]：区间左边界，arr[1]：区间右边界，arr[2]：区间高度
        List<int[]> listArr = new ArrayList<>();
        //当前方块掉落后所有区间的最大高度，用于求result
        int maxHeight = 0;

        for (int i = 0; i < positions.length; i++) {
            //当前方块左边界
            int left = positions[i][0];
            //当前方块右边界
            int right = positions[i][0] + positions[i][1];
            //当前方块的高度
            int height = positions[i][1];
            //当前方块掉落后的最大高度
            int curMaxHeight = positions[i][1];

            for (int j = 0; j < listArr.size(); j++) {
                //当前方块之前的区间
                int[] arr = listArr.get(j);

                //当前方块和区间不相交，则直接进行下次循环
                if (arr[0] >= right || arr[1] <= left) {
                    continue;
                }

                curMaxHeight = Math.max(curMaxHeight, arr[2] + height);
            }

            //当前方块区间加入listArr
            listArr.add(new int[]{left, right, curMaxHeight});
            //更新maxHeight
            maxHeight = Math.max(maxHeight, curMaxHeight);
            //当前方块掉落后所有区间的最大高度maxHeight加入list
            list.add(maxHeight);
        }

        return list;
    }

    /**
     * 线段树，动态开点
     * 核心思想：找当前方块掉落之前当前方块区间的最大高度，最大高度加上当前方块高度作为当前区间的高度
     * 注意：线段树查询和更新时，右边界要减1，保证当前方块和其他区间边界相邻情况下不相交
     * 时间复杂度O(nlogm)，空间复杂度O(m) (n=positions.length，m=max(positions[i][0]+positions[i][1])-min(positions[i][0]))
     *
     * @param positions
     * @return
     */
    public List<Integer> fallingSquares2(int[][] positions) {
        //线段树所表示区间的左边界
        int leftBound = Integer.MAX_VALUE;
        //线段树所表示区间的右边界
        int rightBound = Integer.MIN_VALUE;

        for (int i = 0; i < positions.length; i++) {
            leftBound = Math.min(leftBound, positions[i][0]);
            rightBound = Math.max(rightBound, positions[i][0] + positions[i][1]);
        }

        //线段树，动态开点
        SegmentTree segmentTree = new SegmentTree(leftBound, rightBound);
        List<Integer> list = new ArrayList<>();
        //当前方块掉落后所有区间的最大高度，用于求result
        int maxHeight = 0;

        for (int i = 0; i < positions.length; i++) {
            //当前方块左边界
            int left = positions[i][0];
            //当前方块右边界
            int right = positions[i][0] + positions[i][1];
            //当前方块的高度
            int height = positions[i][1];
            //当前方块掉落后的最大高度
            //注意：右边界要减1，保证当前方块和其他区间边界相邻情况下不相交
            int curMaxHeight = segmentTree.query(segmentTree.root, left, right - 1) + height;
            //更新区间[left,right]的最大值为curMaxHeight
            //注意和218题中线段树update()的区别
            //注意：右边界要减1，保证当前方块和其他区间边界相邻情况下不相交
            segmentTree.update(segmentTree.root, left, right - 1, curMaxHeight);
            //更新maxHeight
            maxHeight = Math.max(maxHeight, curMaxHeight);
            //当前方块掉落后所有区间的最大高度maxHeight加入list
            list.add(maxHeight);
        }

        return list;
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
                //注意和218题中线段树update()的区别
                node.leftNode.maxValue = node.lazyValue;
                node.rightNode.maxValue = node.lazyValue;
                node.leftNode.lazyValue = node.lazyValue;
                node.rightNode.lazyValue = node.lazyValue;

                node.lazyValue = 0;
            }

            int leftValue = query(node.leftNode, queryLeft, queryRight);
            int rightValue = query(node.rightNode, queryLeft, queryRight);

            return Math.max(leftValue, rightValue);
        }

        /**
         * 更新区间[updateLeft,updateRight]的最大值为value
         * 注意和218题中线段树update()的区别
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
                //注意和218题中线段树update()的区别
                node.maxValue = value;
                node.lazyValue = value;
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
                //注意和218题中线段树update()的区别
                node.leftNode.maxValue = node.lazyValue;
                node.rightNode.maxValue = node.lazyValue;
                node.leftNode.lazyValue = node.lazyValue;
                node.rightNode.lazyValue = node.lazyValue;

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
