package com.zhang.java;

/**
 * @Date 2025/4/12 08:33
 * @Author zsy
 * @Description 二进制字符串前缀一致的次数 类比Problem769 线段树类比
 * 给你一个长度为 n 、下标从 1 开始的二进制字符串，所有位最开始都是 0 。
 * 我们会按步翻转该二进制字符串的所有位（即，将 0 变为 1）。
 * 给你一个下标从 1 开始的整数数组 flips ，其中 flips[i] 表示对应下标 i 的位将会在第 i 步翻转。
 * 二进制字符串 前缀一致 需满足：在第 i 步之后，在 闭 区间 [1, i] 内的所有位都是 1 ，而其他位都是 0 。
 * 返回二进制字符串在翻转过程中 前缀一致 的次数。
 * <p>
 * 输入：flips = [3,2,4,1,5]
 * 输出：2
 * 解释：二进制字符串最开始是 "00000" 。
 * 执行第 1 步：字符串变为 "00100" ，不属于前缀一致的情况。
 * 执行第 2 步：字符串变为 "01100" ，不属于前缀一致的情况。
 * 执行第 3 步：字符串变为 "01110" ，不属于前缀一致的情况。
 * 执行第 4 步：字符串变为 "11110" ，属于前缀一致的情况。
 * 执行第 5 步：字符串变为 "11111" ，属于前缀一致的情况。
 * 在翻转过程中，前缀一致的次数为 2 ，所以返回 2 。
 * <p>
 * 输入：flips = [4,1,2,3]
 * 输出：1
 * 解释：二进制字符串最开始是 "0000" 。
 * 执行第 1 步：字符串变为 "0001" ，不属于前缀一致的情况。
 * 执行第 2 步：字符串变为 "1001" ，不属于前缀一致的情况。
 * 执行第 3 步：字符串变为 "1101" ，不属于前缀一致的情况。
 * 执行第 4 步：字符串变为 "1111" ，属于前缀一致的情况。
 * 在翻转过程中，前缀一致的次数为 1 ，所以返回 1 。
 * <p>
 * n == flips.length
 * 1 <= n <= 5 * 10^4
 * flips 是范围 [1, n] 中所有整数构成的一个排列
 */
public class Problem1375 {
    public static void main(String[] args) {
        Problem1375 problem1375 = new Problem1375();
        int[] flips = {3, 2, 4, 1, 5};
        System.out.println(problem1375.numTimesAllBlue(flips));
        System.out.println(problem1375.numTimesAllBlue2(flips));
    }

    /**
     * 线段树，动态开点
     * flips为1-n的排列，即1-n每个元素只出现一次，每次将当前flips[i]作为1加入线段树，
     * 如果线段树中1到i+1元素之和为i+1，则满足区间[1,i+1]都为1，而其他位都为0
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param flips
     * @return
     */
    public int numTimesAllBlue(int[] flips) {
        SegmentTree segmentTree = new SegmentTree(1, flips.length);
        int count = 0;

        for (int i = 0; i < flips.length; i++) {
            //每次将当前flips[i]作为1加入线段树
            segmentTree.update(segmentTree.root, flips[i], flips[i], 1);

            //如果线段树中1到i+1元素之和为i+1，则满足区间[1,i+1]都为1，而其他位都为0
            if (segmentTree.query(segmentTree.root, 1, i + 1) == i + 1) {
                count++;
            }
        }

        return count;
    }

    /**
     * 贪心
     * 从左往右遍历，记录当前遍历到元素的最大值max，如果max等于当前下标索引i+1，则满足区间[1,i+1]都为1，而其他位都为0
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param flips
     * @return
     */
    public int numTimesAllBlue2(int[] flips) {
        //当前遍历到元素的最大值，即flips[0]-flips[i]的最大值
        int max = 0;
        int count = 0;

        for (int i = 0; i < flips.length; i++) {
            max = Math.max(max, flips[i]);

            //max等于当前下标索引i+1，则满足区间[1,i+1]都为1，而其他位都为0
            if (max == i + 1) {
                count++;
            }
        }

        return count;
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
            if (updateLeft > node.rightBound || updateRight < node.leftBound) {
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
