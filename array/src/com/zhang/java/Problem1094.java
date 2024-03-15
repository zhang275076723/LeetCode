package com.zhang.java;

/**
 * @Date 2024/3/2 09:07
 * @Author zsy
 * @Description 拼车 差分数组类比Problem253、Problem370、Problem1109、Problem1893 线段树类比Problem218、Problem307、Problem308、Problem327、Problem370、Problem654、Problem699、Problem715、Problem729、Problem731、Problem732、Problem933、Problem1109、Problem1893、Problem2407
 * 车上最初有 capacity 个空座位。车 只能 向一个方向行驶（也就是说，不允许掉头或改变方向）
 * 给定整数 capacity 和一个数组 trips ,  trip[i] = [numPassengersi, fromi, toi] 表示第 i 次旅行有 numPassengersi 乘客，
 * 接他们和放他们的位置分别是 fromi 和 toi 。
 * 这些位置是从汽车的初始位置向东的公里数。
 * 当且仅当你可以在所有给定的行程中接送所有乘客时，返回 true，否则请返回 false。
 * <p>
 * 输入：trips = [[2,1,5],[3,3,7]], capacity = 4
 * 输出：false
 * <p>
 * 输入：trips = [[2,1,5],[3,3,7]], capacity = 5
 * 输出：true
 * <p>
 * 1 <= trips.length <= 1000
 * trips[i].length == 3
 * 1 <= numPassengersi <= 100
 * 0 <= fromi < toi <= 1000
 * 1 <= capacity <= 10^5
 */
public class Problem1094 {
    public static void main(String[] args) {
        Problem1094 problem1094 = new Problem1094();
        int[][] trips = {{2, 1, 5}, {3, 3, 7}};
        int capacity = 4;
        System.out.println(problem1094.carPooling(trips, capacity));
        System.out.println(problem1094.carPooling2(trips, capacity));
        System.out.println(problem1094.carPooling3(trips, capacity));
        System.out.println(problem1094.carPooling4(trips, capacity));
    }

    /**
     * 暴力
     * 时间复杂度O(mn)，空间复杂度O(m) (n=trips.length，m=max(trips[i][2]))
     *
     * @param trips
     * @param capacity
     * @return
     */
    public boolean carPooling(int[][] trips, int capacity) {
        //汽车能到达的最远距离
        int m = trips[0][2];

        for (int i = 0; i < trips.length; i++) {
            m = Math.max(m, trips[i][2]);
        }

        //当前距离乘车的乘客数量数组
        int[] arr = new int[m];

        for (int i = 0; i < trips.length; i++) {
            int passenger = trips[i][0];
            int from = trips[i][1];
            //放这批乘客的距离要减1，因为放这批乘客时接其他乘客不冲突
            int to = trips[i][2] - 1;

            for (int j = from; j <= to; j++) {
                arr[j] = arr[j] + passenger;

                //当前距离乘车的乘客数量大于capacity，则无法乘车，返回false
                if (arr[j] > capacity) {
                    return false;
                }
            }
        }

        //遍历结束，则可以乘车，返回true
        return true;
    }

    /**
     * 差分数组 (差分数组在O(1)进行区间修改，但区间查询需要O(n)；前缀数组在O(1)进行区间查询，但区间修改需要O(n)，两者互为逆操作)
     * diff[i] = arr[i] - arr[i-1]
     * arr[i] = diff[0] + diff[1] + ... + diff[i-1] + diff[i]
     * 区间[left,right]每个元素加上value，则diff[left]=diff[left]+value，diff[right]=diff[right]-value
     * 时间复杂度O(m+n)，空间复杂度O(m) (n=trips.length，m=max(trips[i][2]))
     *
     * @param trips
     * @param capacity
     * @return
     */
    public boolean carPooling2(int[][] trips, int capacity) {
        //汽车能到达的最远距离
        int m = trips[0][2];

        for (int i = 0; i < trips.length; i++) {
            m = Math.max(m, trips[i][2]);
        }

        //差分数组
        int[] diff = new int[m];

        for (int i = 0; i < trips.length; i++) {
            int passenger = trips[i][0];
            int from = trips[i][1];
            //放这批乘客的距离要减1，因为放这批乘客时接其他乘客不冲突
            int to = trips[i][2] - 1;

            diff[from] = diff[from] + passenger;

            if (to + 1 < m) {
                diff[to + 1] = diff[to + 1] - passenger;
            }
        }

        //差分数组累加，还原为结果数组
        for (int i = 1; i < m; i++) {
            diff[i] = diff[i] + diff[i - 1];
        }

        for (int i = 0; i < m; i++) {
            //当前距离乘车的乘客数量大于capacity，则无法乘车，返回false
            if (diff[i] > capacity) {
                return false;
            }
        }

        //遍历结束，则可以乘车，返回true
        return true;
    }

    /**
     * 线段树，用数组表示线段树 (线段树在O(logn)进行区间查询和修改)
     * 时间复杂度O(nlogm+mlogm)，空间复杂度O(m) (n=trips.length，m=max(trips[i][2]))
     *
     * @param trips
     * @param capacity
     * @return
     */
    public boolean carPooling3(int[][] trips, int capacity) {
        //汽车能到达的最远距离
        int m = trips[0][2];

        for (int i = 0; i < trips.length; i++) {
            m = Math.max(m, trips[i][2]);
        }

        //线段树，用数组表示线段树
        SegmentTree segmentTree = new SegmentTree(m);
        int leftBound = 0;
        int rightBound = m - 1;

        for (int i = 0; i < trips.length; i++) {
            int passenger = trips[i][0];
            int updateLeft = trips[i][1];
            //更新区间右边界要减1，因为放这批乘客时接其他乘客不冲突
            int updateRight = trips[i][2] - 1;

            segmentTree.update(0, leftBound, rightBound, updateLeft, updateRight, passenger);
        }

        for (int i = 0; i < m; i++) {
            //当前距离乘车的乘客数量大于capacity，则无法乘车，返回false
            if (segmentTree.query(0, leftBound, rightBound, i, i) > capacity) {
                return false;
            }
        }

        //遍历结束，则可以乘车，返回true
        return true;
    }

    /**
     * 线段树，动态开点 (线段树在O(logn)进行区间查询和修改)
     * 时间复杂度O(nlogm+mlogm)，空间复杂度O(m) (n=trips.length，m=max(trips[i][2]))
     *
     * @param trips
     * @param capacity
     * @return
     */
    public boolean carPooling4(int[][] trips, int capacity) {
        //汽车能到达的最远距离
        int m = trips[0][2];

        for (int i = 0; i < trips.length; i++) {
            m = Math.max(m, trips[i][2]);
        }

        //线段树，动态开点
        SegmentTree2 segmentTree2 = new SegmentTree2(0, m - 1);

        for (int i = 0; i < trips.length; i++) {
            int passenger = trips[i][0];
            int updateLeft = trips[i][1];
            //更新区间右边界要减1，因为放这批乘客时接其他乘客不冲突
            int updateRight = trips[i][2] - 1;

            segmentTree2.update(segmentTree2.root, updateLeft, updateRight, passenger);
        }

        for (int i = 0; i < m; i++) {
            //当前距离乘车的乘客数量大于capacity，则无法乘车，返回false
            if (segmentTree2.query(segmentTree2.root, i, i) > capacity) {
                return false;
            }
        }

        //遍历结束，则可以乘车，返回true
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
