package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2024/2/26 08:35
 * @Author zsy
 * @Description 航班预订统计 华为面试题 差分数组类比Problem253、Problem370、Problem1094、Problem1893 线段树类比Problem218、Problem307、Problem308、Problem327、Problem370、Problem654、Problem699、Problem715、Problem729、Problem731、Problem732、Problem933、Problem1094、Problem1893、Problem2407
 * 这里有 n 个航班，它们分别从 1 到 n 进行编号。
 * 有一份航班预订表 bookings ，表中第 i 条预订记录 bookings[i] = [firsti, lasti, seatsi]
 * 意味着在从 firsti 到 lasti （包含 firsti 和 lasti ）的 每个航班 上预订了 seatsi 个座位。
 * 请你返回一个长度为 n 的数组 answer，里面的元素是每个航班预定的座位总数。
 * <p>
 * 输入：bookings = [[1,2,10],[2,3,20],[2,5,25]], n = 5
 * 输出：[10,55,45,25,25]
 * 解释：
 * 航班编号        1   2   3   4   5
 * 预订记录 1 ：   10  10
 * 预订记录 2 ：       20  20
 * 预订记录 3 ：       25  25  25  25
 * 总座位数：      10  55  45  25  25
 * 因此，answer = [10,55,45,25,25]
 * <p>
 * 输入：bookings = [[1,2,10],[2,2,15]], n = 2
 * 输出：[10,25]
 * 解释：
 * 航班编号        1   2
 * 预订记录 1 ：   10  10
 * 预订记录 2 ：       15
 * 总座位数：      10  25
 * 因此，answer = [10,25]
 * <p>
 * 1 <= n <= 2 * 10^4
 * 1 <= bookings.length <= 2 * 10^4
 * bookings[i].length == 3
 * 1 <= firsti <= lasti <= n
 * 1 <= seatsi <= 10^4
 */
public class Problem1109 {
    public static void main(String[] args) {
        Problem1109 problem1109 = new Problem1109();
        int[][] bookings = {{1, 2, 10}, {2, 3, 20}, {2, 5, 25}};
        int n = 5;
        System.out.println(Arrays.toString(problem1109.corpFlightBookings(bookings, n)));
        System.out.println(Arrays.toString(problem1109.corpFlightBookings2(bookings, n)));
        System.out.println(Arrays.toString(problem1109.corpFlightBookings3(bookings, n)));
    }

    /**
     * 差分数组 (差分数组在O(1)进行区间修改，但区间查询需要O(n)；前缀数组在O(1)进行区间查询，但区间修改需要O(n)，两者互为逆操作)
     * diff[i] = arr[i] - arr[i-1]
     * arr[i] = diff[0] + diff[1] + ... + diff[i-1] + diff[i]
     * 区间[left,right]每个元素加上value，则diff[left]=diff[left]+value，diff[right+1]=diff[right+1]-value
     * 时间复杂度O(m+n)，空间复杂度O(1) (m=bookings.length)
     *
     * @param bookings
     * @param n
     * @return
     */
    public int[] corpFlightBookings(int[][] bookings, int n) {
        //差分数组
        int[] diff = new int[n];

        for (int i = 0; i < bookings.length; i++) {
            //因为是从1开始编号，减1变为从0开始编号
            int left = bookings[i][0] - 1;
            int right = bookings[i][1] - 1;
            int value = bookings[i][2];

            diff[left] = diff[left] + value;

            if (right + 1 < n) {
                diff[right + 1] = diff[right + 1] - value;
            }
        }

        //差分数组累加，还原为结果数组
        for (int i = 1; i < n; i++) {
            diff[i] = diff[i] + diff[i - 1];
        }

        return diff;
    }

    /**
     * 线段树，用数组表示线段树 (线段树在O(logn)进行区间查询和修改)
     * 时间复杂度O(mlogn+nlogn)，空间复杂度O(n) (m=bookings.length)
     *
     * @param bookings
     * @param n
     * @return
     */
    public int[] corpFlightBookings2(int[][] bookings, int n) {
        //线段树，用数组表示线段树
        SegmentTree segmentTree = new SegmentTree(n);
        int leftBound = 0;
        int rightBound = n - 1;

        for (int i = 0; i < bookings.length; i++) {
            //因为是从1开始编号，减1变为从0开始编号
            int updateLeft = bookings[i][0] - 1;
            int updateRight = bookings[i][1] - 1;
            int value = bookings[i][2];

            segmentTree.update(0, leftBound, rightBound, updateLeft, updateRight, value);
        }

        //修改后的结果数组
        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = segmentTree.query(0, leftBound, rightBound, i, i);
        }

        return arr;
    }

    /**
     * 线段树，动态开点 (线段树在O(logn)进行区间查询和修改)
     * 时间复杂度O(mlogn+nlogn)，空间复杂度O(n) (m=bookings.length)
     *
     * @param bookings
     * @param n
     * @return
     */
    public int[] corpFlightBookings3(int[][] bookings, int n) {
        //线段树，动态开点
        SegmentTree2 segmentTree2 = new SegmentTree2(0, n - 1);

        for (int i = 0; i < bookings.length; i++) {
            //因为是从1开始编号，减1变为从0开始编号
            int updateLeft = bookings[i][0] - 1;
            int updateRight = bookings[i][1] - 1;
            int value = bookings[i][2];

            segmentTree2.update(segmentTree2.root, updateLeft, updateRight, value);
        }

        //修改后的结果数组
        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
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
