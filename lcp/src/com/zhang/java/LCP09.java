package com.zhang.java;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2024/11/14 08:21
 * @Author zsy
 * @Description 最小跳跃次数 网易机试题 华为面试题 跳跃问题类比Problem45、Problem55、Problem403、Problem975、Problem1306、Problem1340、Problem1345、Problem1377、Problem1654、Problem1696、Problem1871、Problem2297、Problem2498、Problem2770 线段树类比
 * 为了给刷题的同学一些奖励，力扣团队引入了一个弹簧游戏机。
 * 游戏机由 N 个特殊弹簧排成一排，编号为 0 到 N-1。
 * 初始有一个小球在编号 0 的弹簧处。
 * 若小球在编号为 i 的弹簧处，通过按动弹簧，可以选择把小球向右弹射 jump[i] 的距离，或者向左弹射到任意左侧弹簧的位置。
 * 也就是说，在编号为 i 弹簧处按动弹簧，小球可以弹向 0 到 i-1 中任意弹簧或者 i+jump[i] 的弹簧
 * （若 i+jump[i]>=N ，则表示小球弹出了机器）。
 * 小球位于编号 0 处的弹簧时不能再向左弹。
 * 为了获得奖励，你需要将小球弹出机器。
 * 请求出最少需要按动多少次弹簧，可以将小球从编号 0 弹簧弹出整个机器，即向右越过编号 N-1 的弹簧。
 * <p>
 * 输入：jump = [2, 5, 1, 1, 1, 1]
 * 输出：3
 * 解释：小 Z 最少需要按动 3 次弹簧，小球依次到达的顺序为 0 -> 2 -> 1 -> 6，最终小球弹出了机器。
 * <p>
 * 1 <= jump.length <= 10^6
 * 1 <= jump[i] <= 10000
 */
public class LCP09 {
    public static void main(String[] args) {
        LCP09 lcp09 = new LCP09();
//        int[] jump = {2, 5, 1, 1, 1, 1};
//        int[] jump = {3, 7, 6, 1, 4, 3, 7, 8, 1, 2, 8, 5, 9, 8, 3, 2, 7, 5, 1, 1};
        int[] jump = {4, 6, 10, 8, 3, 5, 3, 5, 7, 8, 6, 10, 3, 7, 3, 10, 7, 10, 10, 9, 1, 4, 7, 4, 8, 6, 9, 8, 8, 2, 7, 2, 4, 5, 4, 3, 3, 2, 2, 2, 3, 4, 4, 1, 1, 5, 6, 8, 1, 2};
        System.out.println(lcp09.minJump(jump));
        System.out.println(lcp09.minJump2(jump));
    }

    /**
     * bfs
     * 核心思想：跳跃到下标索引i的最小跳跃次数为a，则跳跃到下标索引i之前的下标索引的最小跳跃次数小于等于a+1
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param jump
     * @return
     */
    public int minJump(int[] jump) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[jump.length];
        queue.offer(0);
        visited[0] = true;

        //bfs当前跳跃次数
        int step = 0;
        //当前已访问的最大下标索引，即已经得到maxIndex左边的最小跳跃次数，当下次往左跳时，只需要考虑maxIndex右边的下标索引
        int maxIndex = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                int index = queue.poll();

                //index往右跳能跳跃到大于等于jump.length的下标索引，则返回step+1
                if (index + jump[index] >= jump.length) {
                    return step + 1;
                }

                //index往右跳
                if (!visited[index + jump[index]]) {
                    queue.offer(index + jump[index]);
                    visited[index + jump[index]] = true;
                }

                //index往左跳
                //已经得到maxIndex左边的最小跳跃次数，即只需要考虑maxIndex右边的下标索引
                for (int j = maxIndex + 1; j < index; j++) {
                    if (visited[j]) {
                        continue;
                    }

                    queue.offer(j);
                    visited[j] = true;
                }

                //更新maxIndex
                maxIndex = Math.max(maxIndex, index);
            }

            step++;
        }

        //bfs遍历节点，即无法跳跃到大于等于jump.length的下标索引，则返回-1
        return -1;
    }

    /**
     * 线段树
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param jump
     * @return
     */
    public int minJump2(int[] jump) {
        SegmentTree segmentTree = new SegmentTree(0, jump.length - 1);
        //更新区间[0,0]的最小值为0，即跳跃到下标索引0的最小跳跃次数为0
        segmentTree.update(segmentTree.root, 0, 0, 0);

        //跳跃到大于等于jump.length的下标索引的最小跳跃次数
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < jump.length; i++) {
            //当前线段树中跳跃到下标索引i的最小跳跃次数
            int step1 = segmentTree.query(segmentTree.root, i, i);
            //当前线段树中跳跃到下标索引[i+1,jump.length-1]的最小跳跃次数，因为可以从当前下标索引往左跳到达下标索引i
            int step2 = segmentTree.query(segmentTree.root, i + 1, jump.length - 1);
            //跳跃到下标索引i的最小跳跃次数
            int step = Math.min(step1, step2 + 1);

            //更新min
            if (i + jump[i] >= jump.length) {
                min = Math.min(min, step + 1);
            } else {
                //跳跃到下标索引i+jump[i]的最小跳跃次数小于等于step+1
                segmentTree.update(segmentTree.root, i + jump[i], i + jump[i], step + 1);
            }
        }

        return min;
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
                //返回int最大值除以2，避免int溢出
                return Integer.MAX_VALUE / 2;
            }

            if (queryLeft <= node.leftBound && node.rightBound <= queryRight) {
                return node.minValue;
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
                node.leftNode.minValue = node.leftNode.minValue + node.lazyValue;
                node.rightNode.minValue = node.rightNode.minValue + node.lazyValue;
                node.leftNode.lazyValue = node.leftNode.lazyValue + node.lazyValue;
                node.rightNode.lazyValue = node.rightNode.lazyValue + node.lazyValue;

                node.lazyValue = 0;
            }

            int leftValue = query(node.leftNode, queryLeft, queryRight);
            int rightValue = query(node.rightNode, queryLeft, queryRight);

            return Math.min(leftValue, rightValue);
        }

        /**
         * 更新区间[updateLeft,updateRight]中大于value的节点值为value
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

            if (node.leftBound == node.rightBound) {
                node.minValue = Math.min(node.minValue, value);
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
                node.leftNode.minValue = node.leftNode.minValue + node.lazyValue;
                node.rightNode.minValue = node.rightNode.minValue + node.lazyValue;
                node.leftNode.lazyValue = node.leftNode.lazyValue + node.lazyValue;
                node.rightNode.lazyValue = node.rightNode.lazyValue + node.lazyValue;

                node.lazyValue = 0;
            }

            update(node.leftNode, updateLeft, updateRight, value);
            update(node.rightNode, updateLeft, updateRight, value);

            node.minValue = Math.min(node.leftNode.minValue, node.rightNode.minValue);
        }

        /**
         * 线段树节点
         */
        private static class SegmentTreeNode {
            //区间[leftBound,rightBound]元素出现的最小次数
            private int minValue;
            private int lazyValue;
            private int leftBound;
            private int rightBound;
            private SegmentTreeNode leftNode;
            private SegmentTreeNode rightNode;

            public SegmentTreeNode(int leftBound, int rightBound) {
                //初始化为int最大值除以2，避免int溢出
                minValue = Integer.MAX_VALUE / 2;
                this.leftBound = leftBound;
                this.rightBound = rightBound;
            }
        }
    }
}
