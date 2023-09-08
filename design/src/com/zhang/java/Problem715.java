package com.zhang.java;

/**
 * @Date 2023/9/3 09:46
 * @Author zsy
 * @Description Range 模块 线段树类比Problem307、Problem308、Problem327、Problem654、Problem729、Problem731、Problem732、Problem2407
 * Range模块是跟踪数字范围的模块。设计一个数据结构来跟踪表示为 半开区间 的范围并查询它们。
 * 半开区间 [left, right) 表示所有 left <= x < right 的实数 x 。
 * 实现 RangeModule 类:
 * RangeModule() 初始化数据结构的对象。
 * void addRange(int left, int right) 添加 半开区间 [left, right)，跟踪该区间中的每个实数。
 * 添加与当前跟踪的数字部分重叠的区间时，应当添加在区间 [left, right) 中尚未跟踪的任何数字到该区间中。
 * boolean queryRange(int left, int right) 只有在当前正在跟踪区间 [left, right) 中的每一个实数时，
 * 才返回 true ，否则返回 false 。
 * void removeRange(int left, int right) 停止跟踪 半开区间 [left, right) 中当前正在跟踪的每个实数。
 * <p>
 * 输入
 * ["RangeModule", "addRange", "removeRange", "queryRange", "queryRange", "queryRange"]
 * [[], [10, 20], [14, 16], [10, 14], [13, 15], [16, 17]]
 * 输出
 * [null, null, null, true, false, true]
 * 解释
 * RangeModule rangeModule = new RangeModule();
 * rangeModule.addRange(10, 20);
 * rangeModule.removeRange(14, 16);
 * rangeModule.queryRange(10, 14); 返回 true （区间 [10, 14) 中的每个数都正在被跟踪）
 * rangeModule.queryRange(13, 15); 返回 false（未跟踪区间 [13, 15) 中像 14, 14.03, 14.17 这样的数字）
 * rangeModule.queryRange(16, 17); 返回 true （尽管执行了删除操作，区间 [16, 17) 中的数字 16 仍然会被跟踪）
 * <p>
 * 1 <= left < right <= 10^9
 * 在单个测试用例中，对 addRange、queryRange 和 removeRange 的调用总数不超过 10^4 次
 */
public class Problem715 {
    public static void main(String[] args) {
        //注意：最大右边界为10^9，导致区间和数组和懒标记数组长度int溢出，所以不适用
//        RangeModule rangeModule = new RangeModule();
        RangeModule2 rangeModule = new RangeModule2();
        rangeModule.addRange(10, 20);
        rangeModule.removeRange(14, 16);
        //返回 true （区间 [10, 14) 中的每个数都正在被跟踪）
        System.out.println(rangeModule.queryRange(10, 14));
        //返回 false（未跟踪区间 [13, 15) 中像 14, 14.03, 14.17 这样的数字）
        System.out.println(rangeModule.queryRange(13, 15));
        //返回 true （尽管执行了删除操作，区间 [16, 17) 中的数字 16 仍然会被跟踪）
        System.out.println(rangeModule.queryRange(16, 17));
    }

    /**
     * 线段树，用数组表示线段树
     * 注意：最大右边界为10^9，导致区间和数组和懒标记数组长度int溢出，所以不适用
     */
    static class RangeModule {
        private final SegmentTree segmentTree;
        private final int leftBound = 1;
        private final int rightBound = (int) 1e9;

        public RangeModule() {
            segmentTree = new SegmentTree(rightBound - leftBound + 1);
        }

        public void addRange(int left, int right) {
            //区间[left,right-1]值都设置为1
            segmentTree.update(0, leftBound, rightBound, left, right - 1, true);
        }

        public boolean queryRange(int left, int right) {
            //判断区间[left,right-1]值是否都为1
            return segmentTree.query(0, leftBound, rightBound, left, right - 1) == right - left;
        }

        public void removeRange(int left, int right) {
            //区间[left,right-1]值都设置为0
            segmentTree.update(0, leftBound, rightBound, left, right - 1, false);
        }

        private static class SegmentTree {
            //区间元素之和数组，每个元素只能为1或0
            private final int[] sumValueArr;
            //懒标记数组，每个懒标记只能为1或-1，1表示所有子孙节点值均为1，-1表示所有子孙节点值均为0
            private final int[] lazyValueArr;

            public SegmentTree(int len) {
                sumValueArr = new int[4 * len];
                lazyValueArr = new int[4 * len];
            }

            public int query(int rootIndex, int left, int right, int queryLeft, int queryRight) {
                if (queryLeft > right || queryRight < left) {
                    return 0;
                }

                if (queryLeft <= left && right <= queryRight) {
                    return sumValueArr[rootIndex];
                }

                int mid = left + ((right - left) >> 1);
                int leftRootIndex = rootIndex * 2 + 1;
                int rightRootIndex = rootIndex * 2 + 2;

                if (lazyValueArr[rootIndex] != 0) {
                    //懒标记为1，则所有子孙节点值均为1，并且所有子孙节点懒标记为1
                    if (lazyValueArr[rootIndex] == 1) {
                        sumValueArr[leftRootIndex] = mid - left + 1;
                        sumValueArr[rightRootIndex] = right - mid;
                        lazyValueArr[leftRootIndex] = 1;
                        lazyValueArr[rightRootIndex] = 1;

                        lazyValueArr[rootIndex] = 0;
                    } else if (lazyValueArr[rootIndex] == -1) {
                        //懒标记为-1，则所有子孙节点值均为0，并且所有子孙节点懒标记为-1
                        sumValueArr[leftRootIndex] = 0;
                        sumValueArr[rightRootIndex] = 0;
                        lazyValueArr[leftRootIndex] = -1;
                        lazyValueArr[rightRootIndex] = -1;

                        lazyValueArr[rootIndex] = 0;
                    }
                }

                int leftValue = query(leftRootIndex, left, mid, queryLeft, queryRight);
                int rightValue = query(rightRootIndex, mid + 1, right, queryLeft, queryRight);

                return leftValue + rightValue;
            }

            /**
             * 根据flag更新区间[updateLeft,updateRight]中每个元素的值为1或0，
             * 如果flag为true，更新区间中每个元素为1；如果flag为false，更新区间中每个元素为0
             *
             * @param rootIndex
             * @param left
             * @param right
             * @param updateLeft
             * @param updateRight
             * @param flag
             */
            public void update(int rootIndex, int left, int right, int updateLeft, int updateRight, boolean flag) {
                if (updateLeft > right || updateRight < left) {
                    return;
                }

                if (updateLeft <= left && right <= updateRight) {
                    //flag为true，则所有子孙节点值均为1，并且所有子孙节点懒标记为1
                    if (flag) {
                        sumValueArr[rootIndex] = right - left + 1;
                        lazyValueArr[rootIndex] = 1;
                    } else {
                        //flag为false，则所有子孙节点值均为0，并且所有子孙节点懒标记为-1
                        sumValueArr[rootIndex] = 0;
                        lazyValueArr[rootIndex] = -1;
                    }

                    return;
                }

                int mid = left + ((right - left) >> 1);
                int leftRootIndex = rootIndex * 2 + 1;
                int rightRootIndex = rootIndex * 2 + 2;

                if (lazyValueArr[rootIndex] != 0) {
                    //懒标记为1，则所有子孙节点值均为1，并且所有子孙节点懒标记为1
                    if (lazyValueArr[rootIndex] == 1) {
                        sumValueArr[leftRootIndex] = mid - left + 1;
                        sumValueArr[rightRootIndex] = right - mid;
                        lazyValueArr[leftRootIndex] = 1;
                        lazyValueArr[rightRootIndex] = 1;

                        lazyValueArr[rootIndex] = 0;
                    } else if (lazyValueArr[rootIndex] == -1) {
                        //懒标记为-1，则所有子孙节点值均为0，并且所有子孙节点懒标记为-1
                        sumValueArr[leftRootIndex] = 0;
                        sumValueArr[rightRootIndex] = 0;
                        lazyValueArr[leftRootIndex] = -1;
                        lazyValueArr[rightRootIndex] = -1;

                        lazyValueArr[rootIndex] = 0;
                    }
                }

                update(leftRootIndex, left, mid, updateLeft, updateRight, flag);
                update(rightRootIndex, mid + 1, right, updateLeft, updateRight, flag);

                sumValueArr[rootIndex] = sumValueArr[leftRootIndex] + sumValueArr[rightRootIndex];
            }
        }
    }

    /**
     * 线段树，动态开点
     * 适用于：不知道数组长度的情况
     */
    static class RangeModule2 {
        private final SegmentTree segmentTree;
        private final int leftBound = 1;
        private final int rightBound = (int) 1e9;

        public RangeModule2() {
            segmentTree = new SegmentTree(leftBound, rightBound);
        }

        public void addRange(int left, int right) {
            //区间[left,right-1]值都设置为1
            segmentTree.update(segmentTree.root, left, right - 1, true);
        }

        public boolean queryRange(int left, int right) {
            //判断区间[left,right-1]值是否都为1
            return segmentTree.query(segmentTree.root, left, right - 1) == right - left;
        }

        public void removeRange(int left, int right) {
            //区间[left,right-1]值都设置为0
            segmentTree.update(segmentTree.root, left, right - 1, false);
        }

        private static class SegmentTree {
            private final SegmentTreeNode root;

            public SegmentTree(int leftBound, int rightBound) {
                root = new SegmentTreeNode(leftBound, rightBound);
            }

            public int query(SegmentTreeNode node, int queryLeft, int queryRight) {
                if (node.leftBound > queryRight || node.rightBound < queryLeft) {
                    return 0;
                }

                if (queryLeft <= node.leftBound && node.rightBound <= queryRight) {
                    return node.sumValue;
                }

                int mid = node.leftBound + ((node.rightBound - node.leftBound) >> 1);

                if (node.leftNode == null) {
                    node.leftNode = new SegmentTreeNode(node.leftBound, mid);
                }

                if (node.rightNode == null) {
                    node.rightNode = new SegmentTreeNode(mid + 1, node.rightBound);
                }

                if (node.lazyValue != 0) {
                    //懒标记为1，则所有子孙节点值均为1，并且所有子孙节点懒标记为1
                    if (node.lazyValue == 1) {
                        node.leftNode.sumValue = mid - node.leftBound + 1;
                        node.rightNode.sumValue = node.rightBound - mid;
                        node.leftNode.lazyValue = 1;
                        node.rightNode.lazyValue = 1;

                        node.lazyValue = 0;
                    } else if (node.lazyValue == -1) {
                        //懒标记为-1，则所有子孙节点值均为0，并且所有子孙节点懒标记为-1
                        node.leftNode.sumValue = 0;
                        node.rightNode.sumValue = 0;
                        node.leftNode.lazyValue = -1;
                        node.rightNode.lazyValue = -1;

                        node.lazyValue = 0;
                    }
                }

                int leftValue = query(node.leftNode, queryLeft, queryRight);
                int rightValue = query(node.rightNode, queryLeft, queryRight);

                return leftValue + rightValue;
            }

            /**
             * 根据flag更新区间[updateLeft,updateRight]中每个元素的值为1或0，
             * 如果flag为true，更新区间中每个元素为1；如果flag为false，更新区间中每个元素为0
             *
             * @param node
             * @param updateLeft
             * @param updateRight
             * @param flag
             */
            public void update(SegmentTreeNode node, int updateLeft, int updateRight, boolean flag) {
                if (node.leftBound > updateRight || node.rightBound < updateLeft) {
                    return;
                }

                if (updateLeft <= node.leftBound && node.rightBound <= updateRight) {
                    //flag为true，则所有子孙节点值均为1，并且所有子孙节点懒标记为1
                    if (flag) {
                        node.sumValue = node.rightBound - node.leftBound + 1;
                        node.lazyValue = 1;
                    } else {
                        //flag为false，则所有子孙节点值均为0，并且所有子孙节点懒标记为-1
                        node.sumValue = 0;
                        node.lazyValue = -1;
                    }

                    return;
                }

                int mid = node.leftBound + ((node.rightBound - node.leftBound) >> 1);

                if (node.leftNode == null) {
                    node.leftNode = new SegmentTreeNode(node.leftBound, mid);
                }

                if (node.rightNode == null) {
                    node.rightNode = new SegmentTreeNode(mid + 1, node.rightBound);
                }

                if (node.lazyValue != 0) {
                    //懒标记为1，则所有子孙节点值均为1，并且所有子孙节点懒标记为1
                    if (node.lazyValue == 1) {
                        node.leftNode.sumValue = mid - node.leftBound + 1;
                        node.rightNode.sumValue = node.rightBound - mid;
                        node.leftNode.lazyValue = 1;
                        node.rightNode.lazyValue = 1;

                        node.lazyValue = 0;
                    } else if (node.lazyValue == -1) {
                        //懒标记为-1，则所有子孙节点值均为0，并且所有子孙节点懒标记为-1
                        node.leftNode.sumValue = 0;
                        node.rightNode.sumValue = 0;
                        node.leftNode.lazyValue = -1;
                        node.rightNode.lazyValue = -1;

                        node.lazyValue = 0;
                    }
                }

                update(node.leftNode, updateLeft, updateRight, flag);
                update(node.rightNode, updateLeft, updateRight, flag);

                node.sumValue = node.leftNode.sumValue + node.rightNode.sumValue;
            }

            private static class SegmentTreeNode {
                //区间[leftBound,rightBound]元素之和，每个元素只能为1或0
                private int sumValue;
                //懒标记，每个懒标记只能为1或-1，1表示所有子孙节点值均为1，-1表示所有子孙节点值均为0
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
}
