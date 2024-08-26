package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2024/2/24 09:02
 * @Author zsy
 * @Description 区间加法 差分数组类比Problem253、Problem1094、Problem1109、Problem1893、Problem2528 线段树类比Problem218、Problem307、Problem308、Problem327、Problem654、Problem699、Problem715、Problem729、Problem731、Problem732、Problem933、Problem1094、Problem1109、Problem1893、Problem2407
 * 假设你有一个长度为 n 的数组，初始情况下所有的数字均为 0，你将会被给出 k 个更新的操作。
 * 其中，每个操作会被表示为一个三元组：[startIndex, endIndex, inc]，你需要将子数组 A[startIndex ... endIndex]
 * （包括 startIndex 和 endIndex）增加 inc。
 * 请你返回 k 次操作后的数组。
 * <p>
 * 输入: length = 5, updates = [[1,3,2],[2,4,3],[0,2,-2]]
 * 输出: [-2,0,3,5,3]
 * 解释：
 * 初始状态:[0,0,0,0,0]
 * 进行了操作 [1,3,2] 后的状态:[0,2,2,2,0]
 * 进行了操作 [2,4,3] 后的状态:[0,2,5,5,3]
 * 进行了操作 [0,2,-2] 后的状态:[-2,0,3,5,3]
 */
public class Problem370 {
    public static void main(String[] args) {
        Problem370 problem370 = new Problem370();
        int length = 5;
        int[][] updates = {{1, 3, 2}, {2, 4, 3}, {0, 2, -2}};
        System.out.println(Arrays.toString(problem370.getModifiedArray(length, updates)));
        System.out.println(Arrays.toString(problem370.getModifiedArray2(length, updates)));
        System.out.println(Arrays.toString(problem370.getModifiedArray3(length, updates)));
    }

    /**
     * 差分数组 (差分数组在O(1)进行区间修改，但区间查询需要O(n)；前缀数组在O(1)进行区间查询，但区间修改需要O(n)，两者互为逆操作)
     * diff[i] = arr[i] - arr[i-1]
     * arr[i] = diff[0] + diff[1] + ... + diff[i-1] + diff[i]
     * 区间[left,right]每个元素加上value，则diff[left]=diff[left]+value，diff[right+1]=diff[right+1]-value
     * 时间复杂度O(n+length)，空间复杂度O(1) (n=updates.length)
     *
     * @param length
     * @param updates
     * @return
     */
    public int[] getModifiedArray(int length, int[][] updates) {
        //差分数组
        int[] diff = new int[length];

        for (int i = 0; i < updates.length; i++) {
            int left = updates[i][0];
            int right = updates[i][1];
            int value = updates[i][2];

            diff[left] = diff[left] + value;

            if (right + 1 < length) {
                diff[right + 1] = diff[right + 1] - value;
            }
        }

        //差分数组累加，还原为结果数组
        for (int i = 1; i < length; i++) {
            diff[i] = diff[i] + diff[i - 1];
        }

        return diff;
    }

    /**
     * 线段树，用数组表示线段树 (线段树在O(logn)进行区间查询和修改)
     * 时间复杂度O(nlog(length)+length*log(length))，空间复杂度O(length) (n=updates.length)
     *
     * @param length
     * @param updates
     * @return
     */
    public int[] getModifiedArray2(int length, int[][] updates) {
        //线段树，用数组表示线段树
        SegmentTree segmentTree = new SegmentTree(length);
        int leftBound = 0;
        int rightBound = length - 1;

        for (int i = 0; i < updates.length; i++) {
            int updateLeft = updates[i][0];
            int updateRight = updates[i][1];
            int value = updates[i][2];

            segmentTree.update(0, leftBound, rightBound, updateLeft, updateRight, value);
        }

        //修改后的结果数组
        int[] arr = new int[length];

        for (int i = 0; i < length; i++) {
            arr[i] = segmentTree.query(0, leftBound, rightBound, i, i);
        }

        return arr;
    }

    /**
     * 线段树，动态开点 (线段树在O(logn)进行区间查询和修改)
     * 时间复杂度O(nlog(length)+length*log(length))，空间复杂度O(length) (n=updates.length)
     *
     * @param length
     * @param updates
     * @return
     */
    public int[] getModifiedArray3(int length, int[][] updates) {
        //线段树，动态开点
        SegmentTree2 segmentTree2 = new SegmentTree2(0, length - 1);

        for (int i = 0; i < updates.length; i++) {
            int updateLeft = updates[i][0];
            int updateRight = updates[i][1];
            int value = updates[i][2];

            segmentTree2.update(segmentTree2.root, updateLeft, updateRight, value);
        }

        //修改后的结果数组
        int[] arr = new int[length];

        for (int i = 0; i < length; i++) {
            arr[i] = segmentTree2.query(segmentTree2.root, i, i);
        }

        return arr;
    }

    /**
     * 线段树，用数组表示线段树
     */
    private static class SegmentTree {
        private final int[] valueArr;
        private final int[] lazyValueArr;

        public SegmentTree(int n) {
            valueArr = new int[4 * n];
            lazyValueArr = new int[4 * n];
        }

        public int query(int rootIndex, int left, int right, int queryLeft, int queryRight) {
            if (queryLeft > right || queryRight < left) {
                return 0;
            }

            if (queryLeft <= left && right <= queryRight) {
                return valueArr[rootIndex];
            }

            int mid = left + ((right - left) >> 1);
            int leftRootIndex = 2 * rootIndex + 1;
            int rightRootIndex = 2 * rootIndex + 2;

            if (lazyValueArr[rootIndex] != 0) {
                valueArr[leftRootIndex] = valueArr[leftRootIndex] + (mid - left + 1) * lazyValueArr[rootIndex];
                valueArr[rightRootIndex] = valueArr[rightRootIndex] + (right - mid) * lazyValueArr[rootIndex];
                lazyValueArr[leftRootIndex] = lazyValueArr[leftRootIndex] + lazyValueArr[rootIndex];
                lazyValueArr[rightRootIndex] = lazyValueArr[rightRootIndex] + lazyValueArr[rootIndex];

                lazyValueArr[rootIndex] = 0;
            }

            int leftValue = query(leftRootIndex, left, mid, queryLeft, queryRight);
            int rightValue = query(rightRootIndex, mid + 1, right, queryLeft, queryRight);

            return leftValue + rightValue;
        }

        public void update(int rootIndex, int left, int right, int updateLeft, int updateRight, int value) {
            if (updateLeft > right || updateRight < left) {
                return;
            }

            if (updateLeft <= left && right <= updateRight) {
                valueArr[rootIndex] = valueArr[rootIndex] + (right - left + 1) * value;
                lazyValueArr[rootIndex] = lazyValueArr[rootIndex] + value;
                return;
            }

            int mid = left + ((right - left) >> 1);
            int leftRootIndex = 2 * rootIndex + 1;
            int rightRootIndex = 2 * rootIndex + 2;

            if (lazyValueArr[rootIndex] != 0) {
                valueArr[leftRootIndex] = valueArr[leftRootIndex] + (mid - left + 1) * lazyValueArr[rootIndex];
                valueArr[rightRootIndex] = valueArr[rightRootIndex] + (right - mid) * lazyValueArr[rootIndex];
                lazyValueArr[leftRootIndex] = lazyValueArr[leftRootIndex] + lazyValueArr[rootIndex];
                lazyValueArr[rightRootIndex] = lazyValueArr[rightRootIndex] + lazyValueArr[rootIndex];

                lazyValueArr[rootIndex] = 0;
            }

            update(leftRootIndex, left, mid, updateLeft, updateRight, value);
            update(rightRootIndex, mid + 1, right, updateLeft, updateRight, value);

            valueArr[rootIndex] = valueArr[leftRootIndex] + valueArr[rightRootIndex];
        }
    }

    /**
     * 线段树，动态开点
     * 适用于不知道数组长度的情况下，进行区间查询和修改
     */
    private static class SegmentTree2 {
        private final SegmentTreeNode root;

        public SegmentTree2(int leftBound, int rightBound) {
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
