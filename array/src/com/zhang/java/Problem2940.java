package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2025/1/2 08:47
 * @Author zsy
 * @Description 找到 Alice 和 Bob 可以相遇的建筑 类比Problem1756 线段树类比
 * 给你一个下标从 0 开始的正整数数组 heights ，其中 heights[i] 表示第 i 栋建筑的高度。
 * 如果一个人在建筑 i ，且存在 i < j 的建筑 j 满足 heights[i] < heights[j] ，那么这个人可以移动到建筑 j 。
 * 给你另外一个数组 queries ，其中 queries[i] = [ai, bi] 。第 i 个查询中，Alice 在建筑 ai ，Bob 在建筑 bi 。
 * 请你能返回一个数组 ans ，其中 ans[i] 是第 i 个查询中，Alice 和 Bob 可以相遇的 最左边的建筑 。
 * 如果对于查询 i ，Alice 和 Bob 不能相遇，令 ans[i] 为 -1 。
 * <p>
 * 输入：heights = [6,4,8,5,2,7], queries = [[0,1],[0,3],[2,4],[3,4],[2,2]]
 * 输出：[2,5,-1,5,2]
 * 解释：第一个查询中，Alice 和 Bob 可以移动到建筑 2 ，因为 heights[0] < heights[2] 且 heights[1] < heights[2] 。
 * 第二个查询中，Alice 和 Bob 可以移动到建筑 5 ，因为 heights[0] < heights[5] 且 heights[3] < heights[5] 。
 * 第三个查询中，Alice 无法与 Bob 相遇，因为 Alice 不能移动到任何其他建筑。
 * 第四个查询中，Alice 和 Bob 可以移动到建筑 5 ，因为 heights[3] < heights[5] 且 heights[4] < heights[5] 。
 * 第五个查询中，Alice 和 Bob 已经在同一栋建筑中。
 * 对于 ans[i] != -1 ，ans[i] 是 Alice 和 Bob 可以相遇的建筑中最左边建筑的下标。
 * 对于 ans[i] == -1 ，不存在 Alice 和 Bob 可以相遇的建筑。
 * <p>
 * 输入：heights = [5,3,8,2,6,1,4,6], queries = [[0,7],[3,5],[5,2],[3,0],[1,6]]
 * 输出：[7,6,-1,4,6]
 * 解释：第一个查询中，Alice 可以直接移动到 Bob 的建筑，因为 heights[0] < heights[7] 。
 * 第二个查询中，Alice 和 Bob 可以移动到建筑 6 ，因为 heights[3] < heights[6] 且 heights[5] < heights[6] 。
 * 第三个查询中，Alice 无法与 Bob 相遇，因为 Bob 不能移动到任何其他建筑。
 * 第四个查询中，Alice 和 Bob 可以移动到建筑 4 ，因为 heights[3] < heights[4] 且 heights[0] < heights[4] 。
 * 第五个查询中，Alice 可以直接移动到 Bob 的建筑，因为 heights[1] < heights[6] 。
 * 对于 ans[i] != -1 ，ans[i] 是 Alice 和 Bob 可以相遇的建筑中最左边建筑的下标。
 * 对于 ans[i] == -1 ，不存在 Alice 和 Bob 可以相遇的建筑。
 * <p>
 * 1 <= heights.length <= 5 * 10^4
 * 1 <= heights[i] <= 10^9
 * 1 <= queries.length <= 5 * 10^4
 * queries[i] = [ai, bi]
 * 0 <= ai, bi <= heights.length - 1
 */
public class Problem2940 {
    public static void main(String[] args) {
        Problem2940 problem2940 = new Problem2940();
        int[] heights = {6, 4, 8, 5, 2, 7};
        int[][] queries = {{0, 1}, {0, 3}, {2, 4}, {3, 4}, {2, 2}};
        System.out.println(Arrays.toString(problem2940.leftmostBuildingQueries(heights, queries)));
        System.out.println(Arrays.toString(problem2940.leftmostBuildingQueries2(heights, queries)));
    }

    /**
     * 暴力 (超时)
     * 时间复杂度O(mn)，空间复杂度O(1) (n=height.length，m=queries.length)
     *
     * @param heights
     * @param queries
     * @return
     */
    public int[] leftmostBuildingQueries(int[] heights, int[][] queries) {
        int[] result = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int a = queries[i][0];
            int b = queries[i][1];

            //始终保持a<=b
            if (a > b) {
                int temp = a;
                a = b;
                b = temp;
            }

            //下标索引a和b相等，或者heights[a]小于heights[b]，则在下标索引b相遇
            if (a == b || heights[a] < heights[b]) {
                result[i] = b;
                continue;
            }

            //初始化为-1，表示不能相遇
            result[i] = -1;

            //[b+1,n-1]中第一个大于heights[a]的下标索引，即为可以相遇的最左边下标索引
            for (int j = b + 1; j < heights.length; j++) {
                if (heights[j] > heights[a]) {
                    result[i] = j;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * 二分查找+线段树
     * 对于查询queries[i]=[ai,bi](始终保持ai<=bi)，则在线段树中二分查找[bi+1,n-1]中第一个大于heights[ai]的下标索引
     * 时间复杂度O(nlogn+mlogn*logn)，空间复杂度O(n) (n=height.length，m=queries.length)
     *
     * @param heights
     * @param queries
     * @return
     */
    public int[] leftmostBuildingQueries2(int[] heights, int[][] queries) {
        int[] result = new int[queries.length];
        SegmentTree segmentTree = new SegmentTree(heights);

        for (int i = 0; i < queries.length; i++) {
            int a = queries[i][0];
            int b = queries[i][1];

            //始终保持a<=b
            if (a > b) {
                int temp = a;
                a = b;
                b = temp;
            }

            //下标索引a和b相等，或者heights[a]小于heights[b]，则在下标索引b相遇
            if (a == b || heights[a] < heights[b]) {
                result[i] = b;
                continue;
            }

            //区间[b+1,n-1]中不存在大于heights[a]的下标索引，则不能相遇
            if (segmentTree.query(segmentTree.root, b + 1, heights.length - 1) <= heights[a]) {
                result[i] = -1;
                continue;
            }

            int left = b + 1;
            int right = heights.length - 1;
            int mid;

            //二分查找区间[b+1,n-1]中第一个大于heights[a]的下标索引
            while (left < right) {
                mid = left + ((right - left) >> 1);

                //区间[left,mid]中不存在大于heights[a]的下标索引，则往右找，left=mid+1
                if (segmentTree.query(segmentTree.root, left, mid) <= heights[a]) {
                    left = mid + 1;
                } else {
                    //区间[left,mid]中存在大于heights[a]的下标索引，则往左找，right=mid
                    right = mid;
                }
            }

            result[i] = left;
        }

        return result;
    }

    /**
     * 线段树，动态开点
     */
    private static class SegmentTree {
        private final SegmentTreeNode root;

        public SegmentTree(int[] arr) {
            root = new SegmentTreeNode(0, arr.length - 1);

            for (int i = 0; i < arr.length; i++) {
                update(root, i, i, arr[i]);
            }
        }

        public int query(SegmentTreeNode node, int queryLeft, int queryRight) {
            if (queryLeft > node.rightBound || queryRight < node.leftBound) {
                return Integer.MIN_VALUE;
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
                node.leftNode.maxValue = node.leftNode.maxValue + node.lazyValue;
                node.rightNode.maxValue = node.rightNode.maxValue + node.lazyValue;
                node.leftNode.lazyValue = node.leftNode.lazyValue + node.lazyValue;
                node.rightNode.lazyValue = node.rightNode.lazyValue + node.lazyValue;

                node.lazyValue = 0;
            }

            int leftValue = query(node.leftNode, queryLeft, queryRight);
            int rightValue = query(node.rightNode, queryLeft, queryRight);

            return Math.max(leftValue, rightValue);
        }

        public void update(SegmentTreeNode node, int updateLeft, int updateRight, int value) {
            if (updateLeft > node.rightBound || updateRight < node.leftBound) {
                return;
            }

            if (updateLeft <= node.leftBound && node.rightBound <= updateRight) {
                node.maxValue = node.maxValue + value;
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
                node.leftNode.maxValue = node.leftNode.maxValue + node.lazyValue;
                node.rightNode.maxValue = node.rightNode.maxValue + node.lazyValue;
                node.leftNode.lazyValue = node.leftNode.lazyValue + node.lazyValue;
                node.rightNode.lazyValue = node.rightNode.lazyValue + node.lazyValue;

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
