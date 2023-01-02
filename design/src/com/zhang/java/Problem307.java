package com.zhang.java;

/**
 * @Date 2022/11/13 11:38
 * @Author zsy
 * @Description 区域和检索 - 数组可修改 类比Problem303、Problem304、Problem308 线段树类比Problem308、Problem729、Problem731、Problem732 二分搜索树类比Problem230、Problem440
 * 给你一个数组 nums ，请你完成两类查询。
 * 1、其中一类查询要求 更新 数组 nums 下标对应的值
 * 2、另一类查询要求返回数组 nums 中索引 left 和索引 right 之间（ 包含 ）的nums元素的 和 ，其中 left <= right
 * 实现 NumArray 类：
 * NumArray(int[] nums) 用整数数组 nums 初始化对象
 * void update(int index, int val) 将 nums[index] 的值 更新 为 val
 * int sumRange(int left, int right) 返回数组 nums 中索引 left 和索引 right 之间（ 包含 ）的nums元素的 和
 * （即，nums[left] + nums[left + 1], ..., nums[right]）
 * <p>
 * 输入：
 * ["NumArray", "sumRange", "update", "sumRange"]
 * [[[1, 3, 5]], [0, 2], [1, 2], [0, 2]]
 * 输出：
 * [null, 9, null, 8]
 * 解释：
 * NumArray numArray = new NumArray([1, 3, 5]);
 * numArray.sumRange(0, 2); // 返回 1 + 3 + 5 = 9
 * numArray.update(1, 2);   // nums = [1,2,5]
 * numArray.sumRange(0, 2); // 返回 1 + 2 + 5 = 8
 * <p>
 * 1 <= nums.length <= 3 * 10^4
 * -100 <= nums[i] <= 100
 * 0 <= index < nums.length
 * -100 <= val <= 100
 * 0 <= left <= right < nums.length
 * 调用 update 和 sumRange 方法次数不大于 3 * 10^4
 */
public class Problem307 {
    public static void main(String[] args) {
        int[] nums = {1, 3, 5};
        NumArray numArray = new NumArray(nums);
//        NumArray2 numArray = new NumArray2(nums);
        System.out.println(numArray.sumRange(0, 2));
        numArray.update(1, 2);
        System.out.println(numArray.sumRange(0, 2));
    }

    /**
     * 线段树，用数组表示线段树，适用于：多次求区间和、区间最大值，并且区间内元素多次修改的情况
     */
    static class NumArray {
        //需要线段化的数组
        private final int[] nums;
        //线段树
        private final SegmentTree segmentTree;

        public NumArray(int[] nums) {
            this.nums = nums;
            segmentTree = new SegmentTree(nums);
        }

        public void update(int index, int val) {
            nums[index] = val;
            segmentTree.update(0, 0, nums.length - 1, index, index, val);
        }

        public int sumRange(int left, int right) {
            return segmentTree.query(0, 0, nums.length - 1, left, right);
        }

        /**
         * 线段树，适用于：多次求区间和、区间最大值，并且区间内元素多次修改的情况
         */
        private static class SegmentTree {
            /**
             * 线段树数组，每个节点表示区间内元素之和，类似堆排序数组，用数组表示树
             * segmentTreeArr[0]为nums[0]-nums[nums.length-1]的区间和，
             * 左子树segmentTreeArr[1]为nums[0]-nums[(nums.length-1)/2]的区间和，
             * 左子树segmentTreeArr[2]为nums[(nums.length-1)/2+1]-nums[nums.length-1]的区间和
             */
            private final int[] segmentTreeArr;

            SegmentTree(int[] nums) {
                //线段树数组大小至少为4n，确保线段树数组能够覆盖nums中所有区间元素
                segmentTreeArr = new int[nums.length * 4];

                //建立线段树
                buildSegmentTree(nums, 0, 0, nums.length - 1);
            }

            /**
             * 建立线段树
             * 时间复杂度O(n)，空间复杂度O(logn)
             *
             * @param nums      要建立线段树的数组
             * @param rootIndex 当前节点在线段树数组中的下标索引
             * @param left      当前区间在nums数组中的左边界
             * @param right     当前区间在nums数组中的右边界
             */
            private int buildSegmentTree(int[] nums, int rootIndex, int left, int right) {
                if (left == right) {
                    segmentTreeArr[rootIndex] = nums[left];
                    return segmentTreeArr[rootIndex];
                }

                int mid = left + ((right - left) >> 1);
                //左子树根节点
                int leftRootIndex = rootIndex * 2 + 1;
                //右子树根节点
                int rightRootIndex = rootIndex * 2 + 2;

                int leftValue = buildSegmentTree(nums, leftRootIndex, left, mid);
                int rightValue = buildSegmentTree(nums, rightRootIndex, mid + 1, right);

                segmentTreeArr[rootIndex] = leftValue + rightValue;

                return segmentTreeArr[rootIndex];
            }

            /**
             * 查询区间[queryLeft,queryRight]元素之和
             * 时间复杂度O(logn)，空间复杂度O(logn)
             *
             * @param rootIndex
             * @param left
             * @param right
             * @param queryLeft
             * @param queryRight
             * @return
             */
            private int query(int rootIndex, int left, int right, int queryLeft, int queryRight) {
                //要查询的区间[queryLeft,queryRight]不在当前线段树节点表示的范围[left,right]之内，直接返回0
                if (queryRight < left || queryLeft > right) {
                    return 0;
                }

                //要查询的区间[queryLeft,queryRight]包含当前线段树节点表示的范围[left,right]，直接返回当前线段树节点的值
                if (queryLeft <= left && right <= queryRight) {
                    return segmentTreeArr[rootIndex];
                }

                int mid = left + ((right - left) >> 1);
                //左子树根节点
                int leftRootIndex = rootIndex * 2 + 1;
                //右子树根节点
                int rightRootIndex = rootIndex * 2 + 2;

                int leftValue = query(leftRootIndex, left, mid, queryLeft, queryRight);
                int rightValue = query(rightRootIndex, mid + 1, right, queryLeft, queryRight);

                //左节点区间和右节点区间元素之和，即为当前节点区间元素之和
                return leftValue + rightValue;
            }

            /**
             * 更新区间[updateLeft,updateRight]节点值为value
             * 时间复杂度O(logn)，空间复杂度O(logn)
             *
             * @param rootIndex
             * @param left
             * @param right
             * @param updateLeft
             * @param updateRight
             * @param value
             */
            private void update(int rootIndex, int left, int right, int updateLeft, int updateRight, int value) {
                //要修改的区间[updateLeft,updateRight]不在当前线段树节点表示的范围[left,right]之内，直接返回
                if (updateRight < left || updateLeft > right) {
                    return;
                }

                //要修改的区间[updateLeft,updateRight]包含当前线段树节点表示的范围[left,right]，修改当前线段树节点表示区间元素之和，
                //并继续更新当前线段树节点的子节点
                if (updateLeft <= left && right <= updateRight) {
                    segmentTreeArr[rootIndex] = value * (right - left + 1);

                    //当前段树节点表示的范围[left,right]区间长度为1，则直接返回，不需要继续往下修改
                    if (left == right) {
                        return;
                    }

                    int mid = left + ((right - left) >> 1);
                    //左子树根节点
                    int leftRootIndex = rootIndex * 2 + 1;
                    //右子树根节点
                    int rightRootIndex = rootIndex * 2 + 2;

                    //更新当前线段树节点的子节点
                    update(leftRootIndex, left, mid, updateLeft, updateRight, value);
                    update(rightRootIndex, mid + 1, right, updateLeft, updateRight, value);

                    return;
                }

                int mid = left + ((right - left) >> 1);
                //左子树根节点
                int leftRootIndex = rootIndex * 2 + 1;
                //右子树根节点
                int rightRootIndex = rootIndex * 2 + 2;

                update(leftRootIndex, left, mid, updateLeft, updateRight, value);
                update(rightRootIndex, mid + 1, right, updateLeft, updateRight, value);

                //更新当前线段树节点表示的区间元素之和
                segmentTreeArr[rootIndex] = segmentTreeArr[leftRootIndex] + segmentTreeArr[rightRootIndex];
            }
        }
    }

    /**
     * 线段树，动态开点，适用于：不知道数组长度的情况下，多次求区间和、区间最大值，并且区间内元素多次修改的情况
     */
    static class NumArray2 {
        //线段树所能表示区间的最大范围[0,maxRight]
        private final int maxRight;
        private final int[] nums;
        //线段树
        private final SegmentTree segmentTree;

        public NumArray2(int[] nums) {
            maxRight = nums.length - 1;
            this.nums = nums;
            segmentTree = new SegmentTree();

            //动态开点，建立线段树
            for (int i = 0; i < nums.length; i++) {
                segmentTree.update(segmentTree.root, 0, maxRight, i, i, nums[i]);
            }
        }

        public void update(int index, int val) {
            nums[index] = val;
            segmentTree.update(segmentTree.root, 0, maxRight, index, index, val);
        }

        public int sumRange(int left, int right) {
            return segmentTree.query(segmentTree.root, 0, maxRight, left, right);
        }

        /**
         * 线段树，动态开点
         * 注意：线段树节点存储的是区间元素之和sumValue，线段树的update()是更新区间节点值为value，而不是区间节点值加上value
         */
        private static class SegmentTree {
            //线段树根节点
            private final SegmentTreeNode root;

            SegmentTree() {
                root = new SegmentTreeNode();
            }

            /**
             * 查询区间[queryLeft,queryRight]元素之和
             * 时间复杂度O(logn)，空间复杂度O(logn) (n=数组的长度)
             *
             * @param node
             * @param left
             * @param right
             * @param queryLeft
             * @param queryRight
             * @return
             */
            private int query(SegmentTreeNode node, int left, int right, int queryLeft, int queryRight) {
                //要查询区间[queryLeft,queryRight]不在当前节点表示的范围[left,right]之内，直接返回0
                if (left > queryRight || right < queryLeft) {
                    return 0;
                }

                //要查询区间[queryLeft,queryRight]包含当前节点表示的范围[left,right]，直接返回当前节点表示区间元素之和sumValue
                if (queryLeft <= left && right <= queryRight) {
                    return node.sumValue;
                }

                //当前节点左右子树为空，动态开点
                if (node.leftNode == null) {
                    node.leftNode = new SegmentTreeNode();
                }

                if (node.rightNode == null) {
                    node.rightNode = new SegmentTreeNode();
                }

                int mid = left + ((right - left) >> 1);

                //将当前节点懒标记值向下传递给左右子节点，更新左右子节点表示的区间元素之和、懒标记值，并将当前节点的懒标记值置0
                if (node.lazyValue != 0) {
                    node.leftNode.sumValue = node.leftNode.sumValue + node.lazyValue * (mid - left + 1);
                    node.rightNode.sumValue = node.rightNode.sumValue + node.lazyValue * (right - mid);
                    node.leftNode.lazyValue = node.lazyValue;
                    node.rightNode.lazyValue = node.lazyValue;

                    //将懒标记值传递给左右子节点之后，当前节点的懒标记值置为0
                    node.lazyValue = 0;
                }

                int leftValue = query(node.leftNode, left, mid, queryLeft, queryRight);
                int rightValue = query(node.rightNode, mid + 1, right, queryLeft, queryRight);

                return leftValue + rightValue;
            }

            /**
             * 更新区间[updateLeft,updateRight]节点值为value
             * 时间复杂度O(logn)，空间复杂度O(logn) (n=数组的长度)
             *
             * @param node
             * @param left
             * @param right
             * @param updateLeft
             * @param updateRight
             * @param value
             */
            private void update(SegmentTreeNode node, int left, int right, int updateLeft, int updateRight, int value) {
                //要修改的区间[updateLeft,updateRight]不在当前节点表示的范围[left,right]之内，直接返回
                if (left > updateRight || right < updateLeft) {
                    return;
                }

                //要修改的区间[updateLeft,updateRight]包含当前节点表示的范围[left,right]，修改当前节点表示区间元素之和、懒标记值
                if (updateLeft <= left && right <= updateRight) {
                    node.sumValue = value * (right - left + 1);
                    node.lazyValue = value;
                    return;
                }

                //当前节点左右子树为空，动态开点
                if (node.leftNode == null) {
                    node.leftNode = new SegmentTreeNode();
                }

                if (node.rightNode == null) {
                    node.rightNode = new SegmentTreeNode();
                }

                int mid = left + ((right - left) >> 1);

                //将当前节点懒标记值向下传递给左右子节点，更新左右子节点表示的区间元素之和、懒标记值，并将当前节点的懒标记值置0
                if (node.lazyValue != 0) {
                    node.leftNode.sumValue = node.leftNode.sumValue + node.lazyValue * (mid - left + 1);
                    node.rightNode.sumValue = node.rightNode.sumValue + node.lazyValue * (right - mid);
                    node.leftNode.lazyValue = node.lazyValue;
                    node.rightNode.lazyValue = node.lazyValue;

                    //将懒标记值传递给左右子节点之后，当前节点的懒标记值置为0
                    node.lazyValue = 0;
                }

                update(node.leftNode, left, mid, updateLeft, updateRight, value);
                update(node.rightNode, mid + 1, right, updateLeft, updateRight, value);

                //更新当前节点表示的区间元素之和
                node.sumValue = node.leftNode.sumValue + node.rightNode.sumValue;
            }

            /**
             * 线段树节点
             * 注意：节点存储的是区间元素之和sumValue
             */
            private static class SegmentTreeNode {
                //当前节点表示区间元素之和
                int sumValue;
                //懒标记值，当前节点的所有子孙节点表示的区间需要向下传递的值
                int lazyValue;
                SegmentTreeNode leftNode;
                SegmentTreeNode rightNode;
            }
        }
    }
}
