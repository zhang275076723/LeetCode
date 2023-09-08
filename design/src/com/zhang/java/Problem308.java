package com.zhang.java;

/**
 * @Date 2022/12/14 11:14
 * @Author zsy
 * @Description 二维区域和检索 - 可变 类比Problem303、Problem304、Problem307 线段树类比Problem307、Problem327、Problem654、Problem715、Problem729、Problem731、Problem732、Problem2407
 * 给你一个二维矩阵 matrix ，你需要处理下面两种类型的若干次查询：
 * 更新：更新 matrix 中某个单元的值。
 * 求和：计算矩阵 matrix 中某一矩形区域元素的 和 ，该区域由 左上角 (row1, col1) 和 右下角 (row2, col2) 界定。
 * 实现 NumMatrix 类：
 * NumMatrix(int[][] matrix) 用整数矩阵 matrix 初始化对象。
 * void update(int row, int col, int val) 更新 matrix[row][col] 的值到 val 。
 * int sumRegion(int row1, int col1, int row2, int col2) 返回矩阵 matrix 中指定矩形区域元素的 和 ，
 * 该区域由 左上角 (row1, col1) 和 右下角 (row2, col2) 界定。
 * <p>
 * 输入:
 * [“NumMatrix”, “sumRegion”, “update”, “sumRegion”]
 * [[[[3,0,1,4,2],[5,6,3,2,1],[1,2,0,1,5],[4,1,0,1,7],[1,0,3,0,5]]],[2,1,4,3],[3,2,2],[2,1,4,3]]
 * 输出:
 * [null, 8, null, 10]
 * 解释:
 * NumMatrix numMatrix = new NumMatrix([[3,0,1,4,2],[5,6,3,2,1],[1,2,0,1,5],[4,1,0,1,7],[1,0,3,0,5]]);
 * numMatrix.sumRegion(2, 1, 4, 3); // 返回 8 (即, 左侧红色矩形的和)
 * numMatrix.update(3, 2, 2); // 矩阵从左图变为右图
 * numMatrix.sumRegion(2, 1, 4, 3); // 返回 10 (即，右侧红色矩形的和)
 * <p>
 * m == matrix.length
 * n == matrix[i].length
 * 1 <= m, n <= 200
 * -10^5 <= matrix[i][j] <= 10^5
 * 0 <= row < m
 * 0 <= col < n
 * -10^5 <= val <= 10^5
 * 0 <= row1 <= row2 < m
 * 0 <= col1 <= col2 < n
 * 最多调用 10^4 次 sumRegion 和 update 方法
 */
public class Problem308 {
    public static void main(String[] args) {
        int[][] matrix = {
                {3, 0, 1, 4, 2},
                {5, 6, 3, 2, 1},
                {1, 2, 0, 1, 5},
                {4, 1, 0, 1, 7},
                {1, 0, 3, 0, 5}
        };
//        NumMatrix numMatrix = new NumMatrix(matrix);
        NumMatrix2 numMatrix = new NumMatrix2(matrix);
        //8
        System.out.println(numMatrix.sumRegion(2, 1, 4, 3));
        numMatrix.update(3, 2, 2);
        //10
        System.out.println(numMatrix.sumRegion(2, 1, 4, 3));
    }

    /**
     * 二维线段树，用数组表示线段树
     * 适用于：多次求矩阵元素之和、矩阵元素的最大值、矩阵元素的最小值，并且矩阵内元素多次修改的情况
     */
    static class NumMatrix {
        //线段树
        private final SegmentTree segmentTree;
        private final int maxRowRight;
        private final int maxColRight;

        public NumMatrix(int[][] matrix) {
            segmentTree = new SegmentTree(matrix);
            maxRowRight = matrix.length - 1;
            maxColRight = matrix[0].length - 1;
        }

        public void update(int row, int col, int val) {
            segmentTree.update(0, 0, 0, maxRowRight, 0, maxColRight, row, col, val);
        }

        public int sumRegion(int row1, int col1, int row2, int col2) {
            return segmentTree.query(0, 0, 0, maxRowRight, 0, maxColRight, row1, row2, col1, col2);
        }

        /**
         * 线段树套线段树，一维数组是一颗线段树，每一个一维节点也是一颗线段树，
         * 每次都是先通过行找到一维线段树上的节点，再对当前一维线段树节点通过列找到二维线段树上的节点
         */
        private static class SegmentTree {
            /**
             * 二维矩阵元素之和数组，类似堆排序数组，用数组表示树
             * valueArr一维是一颗线段树，每一个一维节点也是一颗线段树
             * 每次都是先通过行找到一维线段树上的节点，再对当前一维线段树节点通过列找到二维线段树上的节点
             * valueArr[i]为叶节点，则valueArr[i][j]为matrix某一行的线段树
             * valueArr[i]为非叶节点，则valueArr[i][j]为左右子树线段树相应值相加
             */
            private final int[][] valueArr;

            /**
             * 二维懒标记数组
             */
            private final int[][] lazyValueArr;

            public SegmentTree(int[][] matrix) {
                //4倍
                valueArr = new int[matrix.length * 4][matrix[0].length * 4];
                lazyValueArr = new int[matrix.length * 4][matrix[0].length * 4];

                buildSegmentTree(matrix, 0, 0, 0, matrix.length - 1, 0, matrix[0].length - 1);
            }

            private void buildSegmentTree(int[][] matrix, int rowRootIndex, int colRootIndex,
                                          int rowLeft, int rowRight, int colLeft, int colRight) {
                if (rowLeft == rowRight) {
                    subBuildSegmentTree(matrix, rowRootIndex, colRootIndex, rowLeft, colLeft, colRight);
                    return;
                }

                int rowMid = rowLeft + ((rowRight - rowLeft) >> 1);
                int rowLeftRootIndex = rowRootIndex * 2 + 1;
                int rowRightRootIndex = rowRootIndex * 2 + 2;

                buildSegmentTree(matrix, rowLeftRootIndex, colRootIndex, rowLeft, rowMid, colLeft, colRight);
                buildSegmentTree(matrix, rowRightRootIndex, colRootIndex, rowMid + 1, rowRight, colLeft, colRight);

                for (int j = 0; j < valueArr[0].length; j++) {
                    valueArr[rowRootIndex][j] = valueArr[rowLeftRootIndex][j] + valueArr[rowRightRootIndex][j];
                }
            }

            private void subBuildSegmentTree(int[][] matrix, int rowRootIndex, int colRootIndex,
                                             int rowIndex, int colLeft, int colRight) {
                if (colLeft == colRight) {
                    valueArr[rowRootIndex][colRootIndex] = matrix[rowIndex][colLeft];
                    return;
                }

                int colMid = colLeft + ((colRight - colLeft) >> 1);
                int colLeftRootIndex = colRootIndex * 2 + 1;
                int colRightRootIndex = colRootIndex * 2 + 2;

                subBuildSegmentTree(matrix, rowRootIndex, colLeftRootIndex, rowIndex, colLeft, colMid);
                subBuildSegmentTree(matrix, rowRootIndex, colRightRootIndex, rowIndex, colMid + 1, colRight);

                valueArr[rowRootIndex][colRootIndex] = valueArr[rowRootIndex][colLeftRootIndex] + valueArr[rowRootIndex][colRightRootIndex];
            }

            public int query(int rowRootIndex, int colRootIndex, int rowLeft, int rowRight, int colLeft, int colRight,
                             int queryRowLeft, int queryRowRight, int queryColLeft, int queryColRight) {
                if (rowLeft > queryRowRight || rowRight < queryRowLeft) {
                    return 0;
                }

                if (queryRowLeft <= rowLeft && rowRight <= queryRowRight) {
                    return subQuery(rowRootIndex, colRootIndex, colLeft, colRight, queryColLeft, queryColRight);
                }

                int rowMid = rowLeft + ((rowRight - rowLeft) >> 1);
                int rowLeftRootIndex = rowRootIndex * 2 + 1;
                int rowRightRootIndex = rowRootIndex * 2 + 2;

                int leftValue = query(rowLeftRootIndex, colRootIndex, rowLeft, rowMid, colLeft, colRight, queryRowLeft, queryRowRight, queryColLeft, queryColRight);
                int rightValue = query(rowRightRootIndex, colRootIndex, rowMid + 1, rowRight, colLeft, colRight, queryRowLeft, queryRowRight, queryColLeft, queryColRight);

                return leftValue + rightValue;
            }

            private int subQuery(int rowRootIndex, int colRootIndex, int colLeft, int colRight,
                                 int queryColLeft, int queryColRight) {
                if (colLeft > queryColRight || colRight < queryColLeft) {
                    return 0;
                }

                if (queryColLeft <= colLeft && colRight <= queryColRight) {
                    return valueArr[rowRootIndex][colRootIndex];
                }

                int colMid = colLeft + ((colRight - colLeft) >> 1);
                int colLeftRootIndex = colRootIndex * 2 + 1;
                int colRightRootIndex = colRootIndex * 2 + 2;

                if (lazyValueArr[rowRootIndex][colRootIndex] != 0) {
                    valueArr[rowRootIndex][colLeftRootIndex] = valueArr[rowRootIndex][colLeftRootIndex] + (colMid - colLeft + 1) * lazyValueArr[rowRootIndex][colRootIndex];
                    valueArr[rowRootIndex][colRightRootIndex] = valueArr[rowRootIndex][colRightRootIndex] + (colRight - colMid) * lazyValueArr[rowRootIndex][colRootIndex];
                    lazyValueArr[rowRootIndex][colLeftRootIndex] = lazyValueArr[rowRootIndex][colLeftRootIndex] + lazyValueArr[rowRootIndex][colRootIndex];
                    lazyValueArr[rowRootIndex][colRightRootIndex] = lazyValueArr[rowRootIndex][colRightRootIndex] + lazyValueArr[rowRootIndex][colRootIndex];

                    lazyValueArr[rowRootIndex][colRootIndex] = 0;
                }

                int leftValue = subQuery(rowRootIndex, colLeftRootIndex, colLeft, colMid, queryColLeft, queryColRight);
                int rightValue = subQuery(rowRootIndex, colRightRootIndex, colMid + 1, colRight, queryColLeft, queryColRight);

                return leftValue + rightValue;
            }

            public void update(int rowRootIndex, int colRootIndex, int rowLeft, int rowRight, int colLeft, int colRight,
                               int updateRowLeft, int updateRowRight, int updateColLeft, int updateColRight, int value) {
                if (rowLeft > updateRowRight || rowRight < updateRowLeft) {
                    return;
                }

                if (updateRowLeft <= rowLeft && rowRight <= updateRowRight) {
                    subUpdate(rowRootIndex, colRootIndex, colLeft, colRight, updateColLeft, updateColRight, value);
                    return;
                }

                int rowMid = rowLeft + ((rowRight - rowLeft) >> 1);
                int rowLeftRootIndex = rowRootIndex * 2 + 1;
                int rowRightRootIndex = rowRootIndex * 2 + 2;

                update(rowLeftRootIndex, colRootIndex, rowLeft, rowMid, colLeft, colRight, updateRowLeft, updateRowRight, updateColLeft, updateColRight, value);
                update(rowRightRootIndex, colRootIndex, rowMid + 1, rowRight, colLeft, colRight, updateRowLeft, updateRowRight, updateColLeft, updateColRight, value);

                for (int j = 0; j < valueArr[0].length; j++) {
                    valueArr[rowRootIndex][j] = valueArr[rowLeftRootIndex][j] + valueArr[rowRightRootIndex][j];
                }
            }

            private void subUpdate(int rowRootIndex, int colRootIndex, int colLeft, int colRight,
                                   int updateColLeft, int updateColRight, int value) {
                if (colLeft > updateColRight || colRight < updateColLeft) {
                    return;
                }

                if (updateColLeft <= colLeft && colRight <= updateColRight) {
                    valueArr[rowRootIndex][colRootIndex] = valueArr[rowRootIndex][colRootIndex] + (colRight - colLeft + 1) * value;
                    lazyValueArr[rowRootIndex][colRootIndex] = lazyValueArr[rowRootIndex][colRootIndex] + value;
                    return;
                }

                int colMid = colLeft + ((colRight - colLeft) >> 1);
                int colLeftRootIndex = colRootIndex * 2 + 1;
                int colRightRootIndex = colRootIndex * 2 + 2;

                if (lazyValueArr[rowRootIndex][colRootIndex] != 0) {
                    valueArr[rowRootIndex][colLeftRootIndex] = valueArr[rowRootIndex][colLeftRootIndex] + (colMid - colLeft + 1) * lazyValueArr[rowRootIndex][colRootIndex];
                    valueArr[rowRootIndex][colRightRootIndex] = valueArr[rowRootIndex][colRightRootIndex] + (colRight - colMid) * lazyValueArr[rowRootIndex][colRootIndex];
                    lazyValueArr[rowRootIndex][colLeftRootIndex] = lazyValueArr[rowRootIndex][colLeftRootIndex] + lazyValueArr[rowRootIndex][colRootIndex];
                    lazyValueArr[rowRootIndex][colRightRootIndex] = lazyValueArr[rowRootIndex][colRightRootIndex] + lazyValueArr[rowRootIndex][colRootIndex];

                    lazyValueArr[rowRootIndex][colRootIndex] = 0;
                }

                subUpdate(rowRootIndex, colLeftRootIndex, colLeft, colMid, updateColLeft, updateColRight, value);
                subUpdate(rowRootIndex, colRightRootIndex, colMid + 1, colRight, updateColLeft, updateColRight, value);

                valueArr[rowRootIndex][colRootIndex] = valueArr[rowRootIndex][colLeftRootIndex] + valueArr[rowRootIndex][colRightRootIndex];
            }

            public void update(int rowRootIndex, int colRootIndex, int rowLeft, int rowRight, int colLeft, int colRight,
                               int updateRowIndex, int updateColIndex, int value) {
                if (rowLeft > updateRowIndex || rowRight < updateRowIndex) {
                    return;
                }

                if (rowLeft == rowRight) {
                    subUpdate(rowRootIndex, colRootIndex, colLeft, colRight, updateColIndex, value);
                    return;
                }

                int rowMid = rowLeft + ((rowRight - rowLeft) >> 1);
                int rowLeftRootIndex = rowRootIndex * 2 + 1;
                int rowRightRootIndex = rowRootIndex * 2 + 2;

                update(rowLeftRootIndex, colRootIndex, rowLeft, rowMid, colLeft, colRight, updateRowIndex, updateColIndex, value);
                update(rowRightRootIndex, colRootIndex, rowMid + 1, rowRight, colLeft, colRight, updateRowIndex, updateColIndex, value);

                for (int j = 0; j < valueArr[0].length; j++) {
                    valueArr[rowRootIndex][j] = valueArr[rowLeftRootIndex][j] + valueArr[rowRightRootIndex][j];
                }
            }

            private void subUpdate(int rowRootIndex, int colRootIndex, int colLeft, int colRight,
                                   int updateColIndex, int value) {
                if (colLeft > updateColIndex || colRight < updateColIndex) {
                    return;
                }

                if (colLeft == colRight) {
                    valueArr[rowRootIndex][colRootIndex] = value;
                    return;
                }

                int colMid = colLeft + ((colRight - colLeft) >> 1);
                int colLeftRootIndex = colRootIndex * 2 + 1;
                int colRightRootIndex = colRootIndex * 2 + 2;

                if (lazyValueArr[rowRootIndex][colRootIndex] != 0) {
                    valueArr[rowRootIndex][colLeftRootIndex] = valueArr[rowRootIndex][colLeftRootIndex] + (colMid - colLeft + 1) * lazyValueArr[rowRootIndex][colRootIndex];
                    valueArr[rowRootIndex][colRightRootIndex] = valueArr[rowRootIndex][colRightRootIndex] + (colRight - colMid) * lazyValueArr[rowRootIndex][colRootIndex];
                    lazyValueArr[rowRootIndex][colLeftRootIndex] = lazyValueArr[rowRootIndex][colLeftRootIndex] + lazyValueArr[rowRootIndex][colRootIndex];
                    lazyValueArr[rowRootIndex][colRightRootIndex] = lazyValueArr[rowRootIndex][colRightRootIndex] + lazyValueArr[rowRootIndex][colRootIndex];

                    lazyValueArr[rowRootIndex][colRootIndex] = 0;
                }

                subUpdate(rowRootIndex, colLeftRootIndex, colLeft, colMid, updateColIndex, value);
                subUpdate(rowRootIndex, colRightRootIndex, colMid + 1, colRight, updateColIndex, value);

                valueArr[rowRootIndex][colRootIndex] = valueArr[rowRootIndex][colLeftRootIndex] + valueArr[rowRootIndex][colRightRootIndex];
            }
        }
    }

    /**
     * 线段树，动态开点
     * 适用于：不知道矩阵长度的情况下，多次求矩阵元素之和、矩阵元素的最大值、矩阵元素的最小值，并且矩阵内元素多次修改的情况
     */
    static class NumMatrix2 {
        //线段树
        private final SegmentTree segmentTree;

        public NumMatrix2(int[][] matrix) {
            segmentTree = new SegmentTree(matrix);
        }

        public void update(int row, int col, int val) {
            segmentTree.update(segmentTree.root, row, col, val);
        }

        public int sumRegion(int row1, int col1, int row2, int col2) {
            return segmentTree.query(segmentTree.root, row1, row2, col1, col2);
        }

        /**
         * 四分法，每个节点表示矩阵分为四个子矩阵
         */
        private static class SegmentTree {
            private final SegmentTreeNode root;

            public SegmentTree(int[][] matrix) {
                root = new SegmentTreeNode(0, matrix.length - 1, 0, matrix[0].length - 1);

                //建建二维线段树
                for (int i = 0; i < matrix.length; i++) {
                    for (int j = 0; j < matrix[0].length; j++) {
                        update(root, i, j, matrix[i][j]);
                    }
                }
            }

            public int query(SegmentTreeNode node, int queryRowLeft, int queryRowRight, int queryColLeft, int queryColRight) {
                if (node.rowLeftBound > queryRowRight || node.rowRightBound < queryRowLeft ||
                        node.colLeftBound > queryColRight || node.colRightBound < queryColLeft) {
                    return 0;
                }

                if (queryRowLeft <= node.rowLeftBound && node.rowRightBound <= queryRowRight &&
                        queryColLeft <= node.colLeftBound && node.colRightBound <= queryColRight) {
                    return node.value;
                }

                int rowMid = node.rowLeftBound + ((node.rowRightBound - node.rowLeftBound) >> 1);
                int colMid = node.colLeftBound + ((node.colRightBound - node.colLeftBound) >> 1);

                if (node.node1 == null) {
                    node.node1 = new SegmentTreeNode(node.rowLeftBound, rowMid, colMid + 1, node.colRightBound);
                }

                if (node.node2 == null) {
                    node.node2 = new SegmentTreeNode(node.rowLeftBound, rowMid, node.colLeftBound, colMid);
                }

                if (node.node3 == null) {
                    node.node3 = new SegmentTreeNode(rowMid + 1, node.rowRightBound, node.colLeftBound, colMid);
                }

                if (node.node4 == null) {
                    node.node4 = new SegmentTreeNode(rowMid + 1, node.rowRightBound, colMid + 1, node.colRightBound);
                }

                if (node.lazyValue != 0) {
                    node.node1.value = node.node1.value + (rowMid - node.rowLeftBound + 1) * (node.colRightBound - colMid) * node.lazyValue;
                    node.node2.value = node.node2.value + (rowMid - node.rowLeftBound + 1) * (colMid - node.colLeftBound + 1) * node.lazyValue;
                    node.node3.value = node.node3.value + (node.rowRightBound - rowMid) * (colMid - node.colLeftBound + 1) * node.lazyValue;
                    node.node4.value = node.node4.value + (node.rowRightBound - rowMid) * (node.colRightBound - colMid) * node.lazyValue;
                    node.node1.lazyValue = node.node1.lazyValue + node.lazyValue;
                    node.node2.lazyValue = node.node2.lazyValue + node.lazyValue;
                    node.node3.lazyValue = node.node3.lazyValue + node.lazyValue;
                    node.node4.lazyValue = node.node4.lazyValue + node.lazyValue;

                    node.lazyValue = 0;
                }

                int value1 = query(node.node1, queryRowLeft, queryRowRight, queryColLeft, queryColRight);
                int value2 = query(node.node2, queryRowLeft, queryRowRight, queryColLeft, queryColRight);
                int value3 = query(node.node3, queryRowLeft, queryRowRight, queryColLeft, queryColRight);
                int value4 = query(node.node4, queryRowLeft, queryRowRight, queryColLeft, queryColRight);

                return value1 + value2 + value3 + value4;
            }

            public void update(SegmentTreeNode node, int updateRowLeft, int updateRowRight, int updateColLeft, int updateColRight, int value) {
                if (node.rowLeftBound > updateRowRight || node.rowRightBound < updateRowLeft ||
                        node.colLeftBound > updateColRight || node.colRightBound < updateColLeft) {
                    return;
                }

                if (updateRowLeft <= node.rowLeftBound && node.rowRightBound <= updateRowRight &&
                        updateColLeft <= node.colLeftBound && node.colRightBound <= updateColRight) {
                    node.value = node.value + (node.rowRightBound - node.rowLeftBound + 1) * (node.colRightBound - node.colLeftBound + 1) * value;
                    node.lazyValue = node.lazyValue + value;
                    return;
                }

                int rowMid = node.rowLeftBound + ((node.rowRightBound - node.rowLeftBound) >> 1);
                int colMid = node.colLeftBound + ((node.colRightBound - node.colLeftBound) >> 1);

                if (node.node1 == null) {
                    node.node1 = new SegmentTreeNode(node.rowLeftBound, rowMid, colMid + 1, node.colRightBound);
                }

                if (node.node2 == null) {
                    node.node2 = new SegmentTreeNode(node.rowLeftBound, rowMid, node.colLeftBound, colMid);
                }

                if (node.node3 == null) {
                    node.node3 = new SegmentTreeNode(rowMid + 1, node.rowRightBound, node.colLeftBound, colMid);
                }

                if (node.node4 == null) {
                    node.node4 = new SegmentTreeNode(rowMid + 1, node.rowRightBound, colMid + 1, node.colRightBound);
                }

                if (node.lazyValue != 0) {
                    node.node1.value = node.node1.value + (rowMid - node.rowLeftBound + 1) * (node.colRightBound - colMid) * node.lazyValue;
                    node.node2.value = node.node2.value + (rowMid - node.rowLeftBound + 1) * (colMid - node.colLeftBound + 1) * node.lazyValue;
                    node.node3.value = node.node3.value + (node.rowRightBound - rowMid) * (colMid - node.colLeftBound + 1) * node.lazyValue;
                    node.node4.value = node.node4.value + (node.rowRightBound - rowMid) * (node.colRightBound - colMid) * node.lazyValue;
                    node.node1.lazyValue = node.node1.lazyValue + node.lazyValue;
                    node.node2.lazyValue = node.node2.lazyValue + node.lazyValue;
                    node.node3.lazyValue = node.node3.lazyValue + node.lazyValue;
                    node.node4.lazyValue = node.node4.lazyValue + node.lazyValue;

                    node.lazyValue = 0;
                }

                update(node.node1, updateRowLeft, updateRowRight, updateColLeft, updateColRight, value);
                update(node.node2, updateRowLeft, updateRowRight, updateColLeft, updateColRight, value);
                update(node.node3, updateRowLeft, updateRowRight, updateColLeft, updateColRight, value);
                update(node.node4, updateRowLeft, updateRowRight, updateColLeft, updateColRight, value);

                node.value = node.node1.value + node.node2.value + node.node3.value + node.node4.value;
            }

            public void update(SegmentTreeNode node, int updateRowIndex, int updateColIndex, int value) {
                if (node.rowLeftBound > updateRowIndex || node.rowRightBound < updateRowIndex ||
                        node.colLeftBound > updateColIndex || node.colRightBound < updateColIndex) {
                    return;
                }

                if (node.rowLeftBound == node.rowRightBound && node.colLeftBound == node.colRightBound) {
                    node.value = value;
                    return;
                }

                int rowMid = node.rowLeftBound + ((node.rowRightBound - node.rowLeftBound) >> 1);
                int colMid = node.colLeftBound + ((node.colRightBound - node.colLeftBound) >> 1);

                if (node.node1 == null) {
                    node.node1 = new SegmentTreeNode(node.rowLeftBound, rowMid, colMid + 1, node.colRightBound);
                }

                if (node.node2 == null) {
                    node.node2 = new SegmentTreeNode(node.rowLeftBound, rowMid, node.colLeftBound, colMid);
                }

                if (node.node3 == null) {
                    node.node3 = new SegmentTreeNode(rowMid + 1, node.rowRightBound, node.colLeftBound, colMid);
                }

                if (node.node4 == null) {
                    node.node4 = new SegmentTreeNode(rowMid + 1, node.rowRightBound, colMid + 1, node.colRightBound);
                }

                if (node.lazyValue != 0) {
                    node.node1.value = node.node1.value + (rowMid - node.rowLeftBound + 1) * (node.colRightBound - colMid) * node.lazyValue;
                    node.node2.value = node.node2.value + (rowMid - node.rowLeftBound + 1) * (colMid - node.colLeftBound + 1) * node.lazyValue;
                    node.node3.value = node.node3.value + (node.rowRightBound - rowMid) * (colMid - node.colLeftBound + 1) * node.lazyValue;
                    node.node4.value = node.node4.value + (node.rowRightBound - rowMid) * (node.colRightBound - colMid) * node.lazyValue;
                    node.node1.lazyValue = node.node1.lazyValue + node.lazyValue;
                    node.node2.lazyValue = node.node2.lazyValue + node.lazyValue;
                    node.node3.lazyValue = node.node3.lazyValue + node.lazyValue;
                    node.node4.lazyValue = node.node4.lazyValue + node.lazyValue;

                    node.lazyValue = 0;
                }

                update(node.node1, updateRowIndex, updateColIndex, value);
                update(node.node2, updateRowIndex, updateColIndex, value);
                update(node.node3, updateRowIndex, updateColIndex, value);
                update(node.node4, updateRowIndex, updateColIndex, value);

                node.value = node.node1.value + node.node2.value + node.node3.value + node.node4.value;
            }

            private static class SegmentTreeNode {
                //当前节点表示矩阵元素之和
                private int value;
                //懒标记值，当前节点的所有子孙节点表示的矩阵需要向下传递的值
                private int lazyValue;
                //当前节点表示矩阵的下标索引
                private int rowLeftBound;
                private int rowRightBound;
                private int colLeftBound;
                private int colRightBound;
                //当前节点表示矩阵分为四个子矩阵，分别为第一象限、第二象限、第三象限、第四象限
                private SegmentTreeNode node1;
                private SegmentTreeNode node2;
                private SegmentTreeNode node3;
                private SegmentTreeNode node4;

                public SegmentTreeNode(int rowLeftBound, int rowRightBound, int colLeftBound, int colRightBound) {
                    this.rowLeftBound = rowLeftBound;
                    this.rowRightBound = rowRightBound;
                    this.colLeftBound = colLeftBound;
                    this.colRightBound = colRightBound;
                }
            }
        }
    }
}
