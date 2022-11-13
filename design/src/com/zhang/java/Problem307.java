package com.zhang.java;

/**
 * @Date 2022/11/13 11:38
 * @Author zsy
 * @Description 区域和检索 - 数组可修改 类比Problem303 线段树类比Problem230、Problem440
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
        System.out.println(numArray.sumRange(0, 2));
        numArray.update(1, 2);
        System.out.println(numArray.sumRange(0, 2));
    }

    /**
     * 线段树，用于多次修改某一位置元素值和多次求区间和
     */
    static class NumArray {
        private final int[] nums;

        private final SegmentTree segmentTree;

        public NumArray(int[] nums) {
            this.nums = nums;
            segmentTree = new SegmentTree(nums);
        }

        public void update(int index, int val) {
            segmentTree.update(nums, 0, 0, nums.length - 1, index, val);
        }

        public int sumRange(int left, int right) {
            return segmentTree.query(0, 0, nums.length - 1, left, right);
        }

        /**
         * 线段树，用于多次修改某一位置元素值和多次求区间和
         */
        static class SegmentTree {
            /**
             * 线段树数组，类似堆排序数组，用数组表示树，表示每个节点的区间和
             */
            private final int[] segmentTreeArr;

            SegmentTree(int[] nums) {
                //确保线段树数组能够覆盖nums中元素的区间
                segmentTreeArr = new int[nums.length * 4];

                buildSegmentTree(nums, segmentTreeArr, 0, 0, nums.length - 1);
            }

            /**
             * 建立线段树
             * 时间复杂度O(n)，空间复杂度O(logn)
             *
             * @param nums           要建立线段树的数组
             * @param segmentTreeArr 线段树数组
             * @param rootIndex      当前节点在线段树数组中的下标索引
             * @param left           当前区间在nums数组中的左边界
             * @param right          当前区间在nums数组中的右边界
             */
            private void buildSegmentTree(int[] nums, int[] segmentTreeArr, int rootIndex, int left, int right) {
                if (left == right) {
                    segmentTreeArr[rootIndex] = nums[left];
                    return;
                }

                int mid = left + ((right - left) >> 1);
                int leftRootIndex = rootIndex * 2 + 1;
                int rightRootIndex = rootIndex * 2 + 2;

                buildSegmentTree(nums, segmentTreeArr, leftRootIndex, left, mid);
                buildSegmentTree(nums, segmentTreeArr, rightRootIndex, mid + 1, right);

                segmentTreeArr[rootIndex] = segmentTreeArr[leftRootIndex] + segmentTreeArr[rightRootIndex];
            }

            /**
             * 查询区间[queryLeft,queryRight]的数组元素之和
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
                //要查询的区间不在当前线段树[left,right]区间之内，直接返回0
                if (queryRight < left || queryLeft > right) {
                    return 0;
                }

                //要查询的区间包含当前线段树[left,right]区间，直接返回segmentTreeArr[rootIndex]
                if (queryLeft <= left && queryRight >= right) {
                    return segmentTreeArr[rootIndex];
                }

                int mid = left + ((right - left) >> 1);
                int leftRootIndex = rootIndex * 2 + 1;
                int rightRootIndex = rootIndex * 2 + 2;

                int leftValue = query(leftRootIndex, left, mid, queryLeft, queryRight);
                int rightValue = query(rightRootIndex, mid + 1, right, queryLeft, queryRight);

                return leftValue + rightValue;
            }

            /**
             * 更新线段树数组中的值，nums[index]修改为value，导致nums[index]在线段树数组中的叶节点到根节点的值都进行修改
             * 时间复杂度O(logn)，空间复杂度O(logn)
             *
             * @param nums
             * @param rootIndex
             * @param left
             * @param right
             * @param index
             * @param value
             */
            private void update(int[] nums, int rootIndex, int left, int right, int index, int value) {
                //要修改的nums[index]不在当前区间[left,right]，直接返回
                if (!(left <= index && index <= right)) {
                    return;
                }

                //找到线段树数值中的叶节点，进行修改
                if (left == right) {
                    nums[index] = value;
                    segmentTreeArr[rootIndex] = value;
                    return;
                }

                int mid = left + ((right - left) >> 1);
                int leftRootIndex = rootIndex * 2 + 1;
                int rightRootIndex = rootIndex * 2 + 2;

                update(nums, leftRootIndex, left, mid, index, value);
                update(nums, rightRootIndex, mid + 1, right, index, value);

                segmentTreeArr[rootIndex] = segmentTreeArr[leftRootIndex] + segmentTreeArr[rightRootIndex];
            }
        }
    }
}
