package com.zhang.java;

/**
 * @Date 2024/3/1 08:59
 * @Author zsy
 * @Description 检查是否区域内所有整数都被覆盖 差分数组类比Problem253、Problem370、Problem1094、Problem1109 线段树类比Problem218、Problem307、Problem308、Problem327、Problem370、Problem654、Problem699、Problem715、Problem729、Problem731、Problem732、Problem933、Problem1094、Problem1109、Problem2407
 * 给你一个二维整数数组 ranges 和两个整数 left 和 right 。
 * 每个 ranges[i] = [starti, endi] 表示一个从 starti 到 endi 的 闭区间 。
 * 如果闭区间 [left, right] 内每个整数都被 ranges 中 至少一个 区间覆盖，那么请你返回 true ，否则返回 false 。
 * 已知区间 ranges[i] = [starti, endi] ，如果整数 x 满足 starti <= x <= endi ，那么我们称整数x 被覆盖了。
 * <p>
 * 输入：ranges = [[1,2],[3,4],[5,6]], left = 2, right = 5
 * 输出：true
 * 解释：2 到 5 的每个整数都被覆盖了：
 * - 2 被第一个区间覆盖。
 * - 3 和 4 被第二个区间覆盖。
 * - 5 被第三个区间覆盖。
 * <p>
 * 输入：ranges = [[1,10],[10,20]], left = 21, right = 21
 * 输出：false
 * 解释：21 没有被任何一个区间覆盖。
 * <p>
 * 1 <= ranges.length <= 50
 * 1 <= starti <= endi <= 50
 * 1 <= left <= right <= 50
 */
public class Problem1893 {
    public static void main(String[] args) {
        Problem1893 problem1893 = new Problem1893();
        int[][] ranges = {{1, 2}, {3, 4}, {5, 6}};
        int left = 2;
        int right = 5;
        System.out.println(problem1893.isCovered(ranges, left, right));
        System.out.println(problem1893.isCovered2(ranges, left, right));
        System.out.println(problem1893.isCovered3(ranges, left, right));
        System.out.println(problem1893.isCovered4(ranges, left, right));
    }

    /**
     * 暴力
     * 时间复杂度O(mn)，空间复杂度O(m) (n=ranges.length，m=max(right,ranges[i][1]))
     *
     * @param ranges
     * @param left
     * @param right
     * @return
     */
    public boolean isCovered(int[][] ranges, int left, int right) {
        //区间的最大右边界
        int m = right;

        for (int i = 0; i < ranges.length; i++) {
            m = Math.max(m, ranges[i][1]);
        }

        //元素是否被覆盖数组
        boolean[] arr = new boolean[m + 1];

        for (int i = 0; i < ranges.length; i++) {
            for (int j = ranges[i][0]; j <= ranges[i][1]; j++) {
                arr[j] = true;
            }
        }

        for (int i = left; i <= right; i++) {
            //[left,right]中只要有一个元素未被覆盖，返回false
            if (!arr[i]) {
                return false;
            }
        }

        //[left,right]中元素都被覆盖，返回true
        return true;
    }

    /**
     * 差分数组 (差分数组在O(1)进行区间修改，但区间查询需要O(n)；前缀数组在O(1)进行区间查询，但区间修改需要O(n)，两者互为逆操作)
     * diff[i] = arr[i] - arr[i-1]
     * arr[i] = diff[0] + diff[1] + ... + diff[i-1] + diff[i]
     * 区间[left,right]每个元素加上value，则diff[left]=diff[left]+value，diff[right]=diff[right]-value
     * 时间复杂度O(n+m)，空间复杂度O(m) (n=ranges.length，m=max(right,ranges[i][1]))
     *
     * @param ranges
     * @param left
     * @param right
     * @return
     */
    public boolean isCovered2(int[][] ranges, int left, int right) {
        //区间的最大右边界
        int m = right;

        for (int i = 0; i < ranges.length; i++) {
            m = Math.max(m, ranges[i][1]);
        }

        //差分数组
        int[] diff = new int[m + 1];

        for (int i = 0; i < ranges.length; i++) {
            diff[ranges[i][0]]++;

            if (ranges[i][1] + 1 <= m) {
                diff[ranges[i][1] + 1]--;
            }
        }

        //差分数组累加，还原为结果数组
        for (int i = 1; i <= m; i++) {
            diff[i] = diff[i] + diff[i - 1];
        }

        for (int i = left; i <= right; i++) {
            //[left,right]中只要有一个元素未被覆盖，返回false
            if (diff[i] <= 0) {
                return false;
            }
        }

        //[left,right]中元素都被覆盖，返回true
        return true;
    }

    /**
     * 线段树，用数组表示线段树 (线段树在O(logn)进行区间查询和修改)
     * 时间复杂度O(nlogm+(right-left)logm)，空间复杂度O(m) (n=ranges.length，m=max(right,ranges[i][1]))
     *
     * @param ranges
     * @param left
     * @param right
     * @return
     */
    public boolean isCovered3(int[][] ranges, int left, int right) {
        //区间的最大右边界
        int m = right;

        for (int i = 0; i < ranges.length; i++) {
            m = Math.max(m, ranges[i][1]);
        }

        //线段树，用数组表示线段树
        SegmentTree segmentTree = new SegmentTree(m + 1);
        int leftBound = 0;
        int rightBound = m;

        for (int i = 0; i < ranges.length; i++) {
            int updateLeft = ranges[i][0];
            int updateRight = ranges[i][1];

            segmentTree.update(0, leftBound, rightBound, updateLeft, updateRight, 1);
        }

        for (int i = left; i <= right; i++) {
            //[left,right]中只要有一个元素未被覆盖，返回false
            if (segmentTree.query(0, leftBound, rightBound, i, i) <= 0) {
                return false;
            }
        }

        //[left,right]中元素都被覆盖，返回true
        return true;
    }

    /**
     * 线段树，动态开点 (线段树在O(logn)进行区间查询和修改)
     * 时间复杂度O(nlogm+(right-left)logm)，空间复杂度O(m) (n=ranges.length，m=max(right,ranges[i][1]))
     *
     * @param ranges
     * @param left
     * @param right
     * @return
     */
    public boolean isCovered4(int[][] ranges, int left, int right) {
        //区间的最大右边界
        int m = right;

        for (int i = 0; i < ranges.length; i++) {
            m = Math.max(m, ranges[i][1]);
        }

        //线段树，动态开点
        SegmentTree2 segmentTree2 = new SegmentTree2(0, m);

        for (int i = 0; i < ranges.length; i++) {
            int updateLeft = ranges[i][0];
            int updateRight = ranges[i][1];

            segmentTree2.update(segmentTree2.root, updateLeft, updateRight, 1);
        }

        for (int i = left; i <= right; i++) {
            //[left,right]中只要有一个元素未被覆盖，返回false
            if (segmentTree2.query(segmentTree2.root, i, i) <= 0) {
                return false;
            }
        }

        //[left,right]中元素都被覆盖，返回true
        return true;
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
