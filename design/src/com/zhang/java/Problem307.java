package com.zhang.java;

/**
 * @Date 2022/11/13 11:38
 * @Author zsy
 * @Description 区域和检索 - 数组可修改 类比Problem303、Problem304、Problem308 线段树类比Problem308、Problem327、Problem654、Problem729、Problem731、Problem732 二分搜索树类比Problem4、Problem230、Problem378、Problem440
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
//        NumArray numArray = new NumArray(nums);
        NumArray2 numArray = new NumArray2(nums);
        //9
        System.out.println(numArray.sumRange(0, 2));
        numArray.update(1, 2);
        //8
        System.out.println(numArray.sumRange(0, 2));
    }

    /**
     * 线段树，用数组表示线段树
     * 适用于：O(logn)时间内单点修改、区间修改、区间查询(区间元素之和、区间元素的最大值、区间元素的最小值)
     */
    static class NumArray {
        //线段树
        private final SegmentTree segmentTree;
        //线段树表示区间的最大右边界
        private final int maxRight;

        public NumArray(int[] nums) {
            segmentTree = new SegmentTree(nums);
            maxRight = nums.length - 1;
        }

        public void update(int index, int val) {
            segmentTree.update(0, 0, maxRight, index, val);
        }

        public int sumRange(int left, int right) {
            return segmentTree.querySumValue(0, 0, maxRight, left, right);
        }

        /**
         * 线段树
         * 适用于：多次求区间元素之和、区间元素的最大值、区间元素的最小值，并且区间内元素多次修改的情况
         */
        private static class SegmentTree {
            /**
             * 区间元素之和数组，类似堆排序数组，用数组表示树
             * sumValueArr[0]为nums[0]-nums[nums.length-1]的区间元素之和，
             * 左子节点sumValueArr[1]为nums[0]-nums[(nums.length-1)/2]的区间元素之和，
             * 右子节点sumValueArr[2]为nums[(nums.length-1)/2+1]-nums[nums.length-1]的区间元素之和
             */
            private final int[] sumValueArr;

            /**
             * 区间元素的最大值数组，类似堆排序数组，用数组表示树
             * maxValueArr[0]为nums[0]-nums[nums.length-1]的区间元素的最大值，
             * 左子节点maxValueArr[1]为nums[0]-nums[(nums.length-1)/2]的区间元素的最大值，
             * 右子节点maxValueArr[2]为nums[(nums.length-1)/2+1]-nums[nums.length-1]的区间元素的最大值
             */
            private final int[] maxValueArr;

            /**
             * 区间元素的最小值数组，类似堆排序数组，用数组表示树
             * minValueArr[0]为nums[0]-nums[nums.length-1]的区间元素的最小值，
             * 左子节点minValueArr[1]为nums[0]-nums[(nums.length-1)/2]的区间元素的最小值，
             * 右子节点minValueArr[2]为nums[(nums.length-1)/2+1]-nums[nums.length-1]的区间元素的最小值
             */
            private final int[] minValueArr;

            /**
             * 懒标记数组，表示当前线段树节点的所有子孙节点表示的区间需要向下传递的值
             * lazyValueArr[0]为sumValueArr[0]和maxValueArr[0]的所有子孙节点
             * sumValueArr[1]、sumValueArr[2]、maxValueArr[1]、maxValueArr[1]...，需要加上的值
             */
            private final int[] lazyValueArr;

            public SegmentTree(int[] nums) {
                //区间元素之和数组长度至少为4n，确保区间元素之和数组能够覆盖nums中所有区间
                sumValueArr = new int[nums.length * 4];
                //区间元素的最大值数组长度至少为4n，确保区间元素的最大值数组能够覆盖nums中所有区间
                maxValueArr = new int[nums.length * 4];
                //区间元素的最小值数组长度至少为4n，确保区间元素的最小值数组能够覆盖nums中所有区间
                minValueArr = new int[nums.length * 4];
                //懒标记数组长度至少为4n，确保懒标记数组能够覆盖nums中所有区间
                lazyValueArr = new int[nums.length * 4];

                //建立线段树
                buildSegmentTree(nums, 0, 0, nums.length - 1);
            }

            /**
             * 建立线段树
             * 时间复杂度O(n)，空间复杂度O(logn)
             *
             * @param nums      要建立线段树的数组
             * @param rootIndex 当前节点在线段树数组中的下标索引
             * @param left      当前节点在nums数组中表示区间的左边界
             * @param right     当前节点在nums数组中表示区间的右边界
             */
            private void buildSegmentTree(int[] nums, int rootIndex, int left, int right) {
                //当前区间[left,right]长度为1，直接更新当前节点区间元素之和、区间元素的最大值、区间元素的最小值
                if (left == right) {
                    sumValueArr[rootIndex] = nums[left];
                    maxValueArr[rootIndex] = nums[left];
                    minValueArr[rootIndex] = nums[left];
                    return;
                }

                //建议取mid都这样写，不要写成相加再除2，例如区间[-1,0]，按照相加再除2得到mid为0，按照这样得到mid为-1
                int mid = left + ((right - left) >> 1);
                //左子树根节点在sumValueArr、maxValueArr、minValueArr中的下标索引
                int leftRootIndex = rootIndex * 2 + 1;
                //右子树根节点在sumValueArr、maxValueArr、minValueArr中的下标索引
                int rightRootIndex = rootIndex * 2 + 2;

                buildSegmentTree(nums, leftRootIndex, left, mid);
                buildSegmentTree(nums, rightRootIndex, mid + 1, right);

                //更新当前节点区间元素之和、区间元素的最大值、区间元素的最小值
                sumValueArr[rootIndex] = sumValueArr[leftRootIndex] + sumValueArr[rightRootIndex];
                maxValueArr[rootIndex] = Math.max(maxValueArr[leftRootIndex], maxValueArr[rightRootIndex]);
                minValueArr[rootIndex] = Math.min(minValueArr[leftRootIndex], minValueArr[rightRootIndex]);
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
            public int querySumValue(int rootIndex, int left, int right, int queryLeft, int queryRight) {
                //要查询区间[queryLeft,queryRight]不在当前节点表示的范围[left,right]之内，直接返回0
                if (left > queryRight || right < queryLeft) {
                    return 0;
                }

                //要查询区间[queryLeft,queryRight]包含当前节点表示的范围[left,right]，直接返回当前节点表示区间元素之和
                if (queryLeft <= left && right <= queryRight) {
                    return sumValueArr[rootIndex];
                }

                //建议取mid都这样写，不要写成相加再除2，例如区间[-1,0]，按照相加再除2得到mid为0，按照这样得到mid为-1
                int mid = left + ((right - left) >> 1);
                //左子树根节点在sumValueArr、maxValueArr、minValueArr中的下标索引
                int leftRootIndex = rootIndex * 2 + 1;
                //右子树根节点在sumValueArr、maxValueArr、minValueArr中的下标索引
                int rightRootIndex = rootIndex * 2 + 2;

                //将当前节点懒标记值向下传递给左右子节点，更新左右子节点表示的区间元素之和、区间元素的最大值、
                //区间元素的最小值、懒标记值，并将当前节点的懒标记值置0
                if (lazyValueArr[rootIndex] != 0) {
                    sumValueArr[leftRootIndex] = sumValueArr[leftRootIndex] + (mid - left + 1) * lazyValueArr[rootIndex];
                    sumValueArr[rightRootIndex] = sumValueArr[rightRootIndex] + (right - mid) * lazyValueArr[rootIndex];
                    maxValueArr[leftRootIndex] = maxValueArr[leftRootIndex] + lazyValueArr[rootIndex];
                    maxValueArr[rightRootIndex] = maxValueArr[rightRootIndex] + lazyValueArr[rootIndex];
                    minValueArr[leftRootIndex] = minValueArr[leftRootIndex] + lazyValueArr[rootIndex];
                    minValueArr[rightRootIndex] = minValueArr[rightRootIndex] + lazyValueArr[rootIndex];
                    lazyValueArr[leftRootIndex] = lazyValueArr[leftRootIndex] + lazyValueArr[rootIndex];
                    lazyValueArr[rightRootIndex] = lazyValueArr[rightRootIndex] + lazyValueArr[rootIndex];

                    //将懒标记值传递给左右子节点之后，当前节点的懒标记值置为0
                    lazyValueArr[rootIndex] = 0;
                }

                //左区间[left,mid]元素之和
                int leftValue = querySumValue(leftRootIndex, left, mid, queryLeft, queryRight);
                //右区间[mid+1,right]元素之和
                int rightValue = querySumValue(rightRootIndex, mid + 1, right, queryLeft, queryRight);

                //返回左右子节点区间元素之和相加，即为查询区间[queryLeft,queryRight]元素之和
                return leftValue + rightValue;
            }

            /**
             * 查询区间[queryLeft,queryRight]元素的最大值
             * 时间复杂度O(logn)，空间复杂度O(logn)
             *
             * @param rootIndex
             * @param left
             * @param right
             * @param queryLeft
             * @param queryRight
             * @return
             */
            public int queryMaxValue(int rootIndex, int left, int right, int queryLeft, int queryRight) {
                //要查询区间[queryLeft,queryRight]不在当前节点表示的范围[left,right]之内，直接返回int范围的最小值
                if (left > queryRight || right < queryLeft) {
                    return Integer.MIN_VALUE;
                }

                //要查询区间[queryLeft,queryRight]包含当前节点表示的范围[left,right]，直接返回当前节点表示区间元素的最大值
                if (queryLeft <= left && right <= queryRight) {
                    return maxValueArr[rootIndex];
                }

                //建议取mid都这样写，不要写成相加再除2，例如区间[-1,0]，按照相加再除2得到mid为0，按照这样得到mid为-1
                int mid = left + ((right - left) >> 1);
                //左子树根节点在sumValueArr、maxValueArr、minValueArr中的下标索引
                int leftRootIndex = rootIndex * 2 + 1;
                //右子树根节点在sumValueArr、maxValueArr、minValueArr中的下标索引
                int rightRootIndex = rootIndex * 2 + 2;

                //将当前节点懒标记值向下传递给左右子节点，更新左右子节点表示的区间元素之和、区间元素的最大值、
                //区间元素的最小值、懒标记值，并将当前节点的懒标记值置0
                if (lazyValueArr[rootIndex] != 0) {
                    sumValueArr[leftRootIndex] = sumValueArr[leftRootIndex] + (mid - left + 1) * lazyValueArr[rootIndex];
                    sumValueArr[rightRootIndex] = sumValueArr[rightRootIndex] + (right - mid) * lazyValueArr[rootIndex];
                    maxValueArr[leftRootIndex] = maxValueArr[leftRootIndex] + lazyValueArr[rootIndex];
                    maxValueArr[rightRootIndex] = maxValueArr[rightRootIndex] + lazyValueArr[rootIndex];
                    minValueArr[leftRootIndex] = minValueArr[leftRootIndex] + lazyValueArr[rootIndex];
                    minValueArr[rightRootIndex] = minValueArr[rightRootIndex] + lazyValueArr[rootIndex];
                    lazyValueArr[leftRootIndex] = lazyValueArr[leftRootIndex] + lazyValueArr[rootIndex];
                    lazyValueArr[rightRootIndex] = lazyValueArr[rightRootIndex] + lazyValueArr[rootIndex];

                    //将懒标记值传递给左右子节点之后，当前节点的懒标记值置为0
                    lazyValueArr[rootIndex] = 0;
                }

                //左区间[left,mid]元素的最大值
                int leftValue = queryMaxValue(leftRootIndex, left, mid, queryLeft, queryRight);
                //右区间[mid+1,right]元素的最大值
                int rightValue = queryMaxValue(rightRootIndex, mid + 1, right, queryLeft, queryRight);

                //返回左右子节点区间元素的最大值中较大的值，即为查询区间[queryLeft,queryRight]元素的最大值
                return Math.max(leftValue, rightValue);
            }

            /**
             * 查询区间[queryLeft,queryRight]元素的最小值
             * 时间复杂度O(logn)，空间复杂度O(logn)
             *
             * @param rootIndex
             * @param left
             * @param right
             * @param queryLeft
             * @param queryRight
             * @return
             */
            public int queryMinValue(int rootIndex, int left, int right, int queryLeft, int queryRight) {
                //要查询区间[queryLeft,queryRight]不在当前节点表示的范围[left,right]之内，直接返回int范围的最大值
                if (left > queryRight || right < queryLeft) {
                    return Integer.MAX_VALUE;
                }

                //要查询区间[queryLeft,queryRight]包含当前节点表示的范围[left,right]，直接返回当前节点表示区间元素的最小值
                if (queryLeft <= left && right <= queryRight) {
                    return minValueArr[rootIndex];
                }

                //建议取mid都这样写，不要写成相加再除2，例如区间[-1,0]，按照相加再除2得到mid为0，按照这样得到mid为-1
                int mid = left + ((right - left) >> 1);
                //左子树根节点在sumValueArr、maxValueArr、minValueArr中的下标索引
                int leftRootIndex = rootIndex * 2 + 1;
                //右子树根节点在sumValueArr、maxValueArr、minValueArr中的下标索引
                int rightRootIndex = rootIndex * 2 + 2;

                //将当前节点懒标记值向下传递给左右子节点，更新左右子节点表示的区间元素之和、区间元素的最大值、
                //区间元素的最小值、懒标记值，并将当前节点的懒标记值置0
                if (lazyValueArr[rootIndex] != 0) {
                    sumValueArr[leftRootIndex] = sumValueArr[leftRootIndex] + (mid - left + 1) * lazyValueArr[rootIndex];
                    sumValueArr[rightRootIndex] = sumValueArr[rightRootIndex] + (right - mid) * lazyValueArr[rootIndex];
                    maxValueArr[leftRootIndex] = maxValueArr[leftRootIndex] + lazyValueArr[rootIndex];
                    maxValueArr[rightRootIndex] = maxValueArr[rightRootIndex] + lazyValueArr[rootIndex];
                    minValueArr[leftRootIndex] = minValueArr[leftRootIndex] + lazyValueArr[rootIndex];
                    minValueArr[rightRootIndex] = minValueArr[rightRootIndex] + lazyValueArr[rootIndex];
                    lazyValueArr[leftRootIndex] = lazyValueArr[leftRootIndex] + lazyValueArr[rootIndex];
                    lazyValueArr[rightRootIndex] = lazyValueArr[rightRootIndex] + lazyValueArr[rootIndex];

                    //将懒标记值传递给左右子节点之后，当前节点的懒标记值置为0
                    lazyValueArr[rootIndex] = 0;
                }

                //左区间[left,mid]元素的最小值
                int leftValue = queryMinValue(leftRootIndex, left, mid, queryLeft, queryRight);
                //右区间[mid+1,right]元素的最小值
                int rightValue = queryMinValue(rightRootIndex, mid + 1, right, queryLeft, queryRight);

                //返回左右子节点区间元素的最小值中较小的值，即为查询区间[queryLeft,queryRight]元素的最小值
                return Math.min(leftValue, rightValue);
            }

            /**
             * 更新区间[updateLeft,updateRight]节点值加上value
             * 时间复杂度O(logn)，空间复杂度O(logn)
             *
             * @param rootIndex
             * @param left
             * @param right
             * @param updateLeft
             * @param updateRight
             * @param value
             */
            public void update(int rootIndex, int left, int right, int updateLeft, int updateRight, int value) {
                //要修改的区间[updateLeft,updateRight]不在当前节点表示的范围[left,right]之内，直接返回
                if (left > updateRight || right < updateLeft) {
                    return;
                }

                //要修改的区间[updateLeft,updateRight]包含当前节点表示的范围[left,right]，
                //修改当前节点表示区间元素之和、区间元素的最大值、区间元素的最小值、懒标记值
                if (updateLeft <= left && right <= updateRight) {
                    sumValueArr[rootIndex] = sumValueArr[rootIndex] + (right - left + 1) * value;
                    maxValueArr[rootIndex] = maxValueArr[rootIndex] + value;
                    minValueArr[rootIndex] = minValueArr[rootIndex] + value;
                    lazyValueArr[rootIndex] = lazyValueArr[rootIndex] + value;
                    return;
                }

                //建议取mid都这样写，不要写成相加再除2，例如区间[-1,0]，按照相加再除2得到mid为0，按照这样得到mid为-1
                int mid = left + ((right - left) >> 1);
                //左子树根节点在sumValueArr、maxValueArr、minValueArr中的下标索引
                int leftRootIndex = rootIndex * 2 + 1;
                //右子树根节点在sumValueArr、maxValueArr、minValueArr中的下标索引
                int rightRootIndex = rootIndex * 2 + 2;

                //将当前节点懒标记值向下传递给左右子节点，更新左右子节点表示的区间元素之和、区间元素的最大值、
                //区间元素的最小值、懒标记值，并将当前节点的懒标记值置0
                if (lazyValueArr[rootIndex] != 0) {
                    sumValueArr[leftRootIndex] = sumValueArr[leftRootIndex] + (mid - left + 1) * lazyValueArr[rootIndex];
                    sumValueArr[rightRootIndex] = sumValueArr[rightRootIndex] + (right - mid) * lazyValueArr[rootIndex];
                    maxValueArr[leftRootIndex] = maxValueArr[leftRootIndex] + lazyValueArr[rootIndex];
                    maxValueArr[rightRootIndex] = maxValueArr[rightRootIndex] + lazyValueArr[rootIndex];
                    minValueArr[leftRootIndex] = minValueArr[leftRootIndex] + lazyValueArr[rootIndex];
                    minValueArr[rightRootIndex] = minValueArr[rightRootIndex] + lazyValueArr[rootIndex];
                    lazyValueArr[leftRootIndex] = lazyValueArr[leftRootIndex] + lazyValueArr[rootIndex];
                    lazyValueArr[rightRootIndex] = lazyValueArr[rightRootIndex] + lazyValueArr[rootIndex];

                    //将懒标记值传递给左右子节点之后，当前节点的懒标记值置为0
                    lazyValueArr[rootIndex] = 0;
                }

                update(leftRootIndex, left, mid, updateLeft, updateRight, value);
                update(rightRootIndex, mid + 1, right, updateLeft, updateRight, value);

                //更新当前节点表示的区间元素之和，即为左右节点表示区间元素之和相加
                sumValueArr[rootIndex] = sumValueArr[leftRootIndex] + sumValueArr[rightRootIndex];
                //更新当前节点表示的区间元素的最大值，即为左右节点表示区间元素的最大值中较大的值
                maxValueArr[rootIndex] = Math.max(maxValueArr[leftRootIndex], maxValueArr[rightRootIndex]);
                //更新当前节点表示的区间元素的最小值，即为左右节点表示区间元素的最小值中较小的值
                minValueArr[rootIndex] = Math.min(minValueArr[leftRootIndex], minValueArr[rightRootIndex]);
            }

            /**
             * 更新区间[updateIndex,updateIndex]节点值为value
             * 时间复杂度O(logn)，空间复杂度O(logn)
             *
             * @param rootIndex
             * @param left
             * @param right
             * @param updateIndex
             * @param value
             */
            public void update(int rootIndex, int left, int right, int updateIndex, int value) {
                //要修改的区间[updateIndex,updateIndex]不在当前节点表示的范围[left,right]之内，直接返回
                if (left > updateIndex || right < updateIndex) {
                    return;
                }

                //找到要修改的区间[updateIndex,updateIndex]，
                //修改当前节点表示区间元素之和、区间元素的最大值、区间元素的最小值
                if (left == right) {
                    sumValueArr[rootIndex] = value;
                    maxValueArr[rootIndex] = value;
                    minValueArr[rootIndex] = value;
                    return;
                }

                //建议取mid都这样写，不要写成相加再除2，例如区间[-1,0]，按照相加再除2得到mid为0，按照这样得到mid为-1
                int mid = left + ((right - left) >> 1);
                //左子树根节点在sumValueArr、maxValueArr、minValueArr中的下标索引
                int leftRootIndex = rootIndex * 2 + 1;
                //右子树根节点在sumValueArr、maxValueArr、minValueArr中的下标索引
                int rightRootIndex = rootIndex * 2 + 2;

                //将当前节点懒标记值向下传递给左右子节点，更新左右子节点表示的区间元素之和、区间元素的最大值、
                //区间元素的最小值、懒标记值，并将当前节点的懒标记值置0
                if (lazyValueArr[rootIndex] != 0) {
                    sumValueArr[leftRootIndex] = sumValueArr[leftRootIndex] + (mid - left + 1) * lazyValueArr[rootIndex];
                    sumValueArr[rightRootIndex] = sumValueArr[rightRootIndex] + (right - mid) * lazyValueArr[rootIndex];
                    maxValueArr[leftRootIndex] = maxValueArr[leftRootIndex] + lazyValueArr[rootIndex];
                    maxValueArr[rightRootIndex] = maxValueArr[rightRootIndex] + lazyValueArr[rootIndex];
                    minValueArr[leftRootIndex] = minValueArr[leftRootIndex] + lazyValueArr[rootIndex];
                    minValueArr[rightRootIndex] = minValueArr[rightRootIndex] + lazyValueArr[rootIndex];
                    lazyValueArr[leftRootIndex] = lazyValueArr[leftRootIndex] + lazyValueArr[rootIndex];
                    lazyValueArr[rightRootIndex] = lazyValueArr[rightRootIndex] + lazyValueArr[rootIndex];

                    //将懒标记值传递给左右子节点之后，当前节点的懒标记值置为0
                    lazyValueArr[rootIndex] = 0;
                }

                update(leftRootIndex, left, mid, updateIndex, value);
                update(rightRootIndex, mid + 1, right, updateIndex, value);

                //更新当前节点表示的区间元素之和，即为左右节点表示区间元素之和相加
                sumValueArr[rootIndex] = sumValueArr[leftRootIndex] + sumValueArr[rightRootIndex];
                //更新当前节点表示的区间元素的最大值，即为左右节点表示区间元素的最大值中较大的值
                maxValueArr[rootIndex] = Math.max(maxValueArr[leftRootIndex], maxValueArr[rightRootIndex]);
                //更新当前节点表示的区间元素的最小值，即为左右节点表示区间元素的最小值中较小的值
                minValueArr[rootIndex] = Math.min(minValueArr[leftRootIndex], minValueArr[rightRootIndex]);
            }
        }
    }

    /**
     * 线段树，动态开点
     * 适用于：不知道数组长度的情况下，多次求区间元素之和、区间元素的最大值，区间元素的最小值，并且区间内元素多次修改的情况
     */
    static class NumArray2 {
        //线段树
        private final SegmentTree segmentTree;

        public NumArray2(int[] nums) {
            segmentTree = new SegmentTree(nums);
        }

        public void update(int index, int val) {
            segmentTree.update(segmentTree.root, index, val);
        }

        public int sumRange(int left, int right) {
            return segmentTree.querySumValue(segmentTree.root, left, right);
        }

        /**
         * 线段树，动态开点
         */
        private static class SegmentTree {
            //线段树根节点
            private final SegmentTreeNode root;

            public SegmentTree(int[] nums) {
                root = new SegmentTreeNode(0, nums.length - 1);

                //建立线段树
                for (int i = 0; i < nums.length; i++) {
                    update(root, i, nums[i]);
                }
            }

            /**
             * 查询区间[queryLeft,queryRight]元素之和
             * 时间复杂度O(logn)，空间复杂度O(logn) (n=数组的长度)
             *
             * @param node
             * @param queryLeft
             * @param queryRight
             * @return
             */
            public int querySumValue(SegmentTreeNode node, int queryLeft, int queryRight) {
                //要查询的区间[queryLeft,queryRight]不在当前节点表示的范围[node.leftBound,node.rightBound]之内，
                //直接返回0
                if (node.leftBound > queryRight || node.rightBound < queryLeft) {
                    return 0;
                }

                //要查询的区间[queryLeft,queryRight]包含当前节点表示的范围[node.leftBound,node.rightBound]，
                //直接返回当前节点表示区间元素之和sumValue
                if (queryLeft <= node.leftBound && node.rightBound <= queryRight) {
                    return node.sumValue;
                }

                //建议取mid都这样写，不要写成相加再除2，例如区间[-1,0]，按照相加再除2得到mid为0，按照这样得到mid为-1
                int mid = node.leftBound + ((node.rightBound - node.leftBound) >> 1);

                //当前节点左右子树为空，动态开点
                if (node.leftNode == null) {
                    node.leftNode = new SegmentTreeNode(node.leftBound, mid);
                }

                if (node.rightNode == null) {
                    node.rightNode = new SegmentTreeNode(mid + 1, node.rightBound);
                }

                //将当前节点懒标记值向下传递给左右子节点，更新左右子节点表示的区间元素之和、区间元素的最大值、
                //区间元素的最小值、懒标记值，并将当前节点的懒标记值置0
                if (node.lazyValue != 0) {
                    node.leftNode.sumValue = node.leftNode.sumValue + (mid - node.leftBound + 1) * node.lazyValue;
                    node.rightNode.sumValue = node.rightNode.sumValue + (node.rightBound - mid) * node.lazyValue;
                    node.leftNode.maxValue = node.leftNode.maxValue + node.lazyValue;
                    node.rightNode.maxValue = node.rightNode.maxValue + node.lazyValue;
                    node.leftNode.minValue = node.leftNode.minValue + node.lazyValue;
                    node.rightNode.minValue = node.rightNode.minValue + node.lazyValue;
                    node.leftNode.lazyValue = node.leftNode.lazyValue + node.lazyValue;
                    node.rightNode.lazyValue = node.rightNode.lazyValue + node.lazyValue;

                    //将懒标记值传递给左右子节点之后，当前节点的懒标记值置为0
                    node.lazyValue = 0;
                }

                //左区间[node.leftBound,mid]元素之和
                int leftValue = querySumValue(node.leftNode, queryLeft, queryRight);
                //右区间[mid+1,node.rightBound]元素之和
                int rightValue = querySumValue(node.rightNode, queryLeft, queryRight);

                //返回左右子节点区间元素之和相加，即为查询区间[queryLeft,queryRight]元素之和
                return leftValue + rightValue;
            }

            /**
             * 查询区间[queryLeft,queryRight]元素的最大值
             * 时间复杂度O(logn)，空间复杂度O(logn) (n=数组的长度)
             *
             * @param node
             * @param queryLeft
             * @param queryRight
             * @return
             */
            public int queryMaxValue(SegmentTreeNode node, int queryLeft, int queryRight) {
                //要查询的区间[queryLeft,queryRight]不在当前节点表示的范围[node.leftBound,node.rightBound]之内，
                //直接返回int范围的最小值，因为数组nums元素取值范围在[-100,100]
                if (node.leftBound > queryRight || node.rightBound < queryLeft) {
                    return Integer.MIN_VALUE;
                }

                //要查询的区间[queryLeft,queryRight]包含当前节点表示的范围[node.leftBound,node.rightBound]，
                //直接返回当前节点表示区间元素的最大值maxValue
                if (queryLeft <= node.leftBound && node.rightBound <= queryRight) {
                    return node.maxValue;
                }

                //建议取mid都这样写，不要写成相加再除2，例如区间[-1,0]，按照相加再除2得到mid为0，按照这样得到mid为-1
                int mid = node.leftBound + ((node.rightBound - node.leftBound) >> 1);

                //当前节点左右子树为空，动态开点
                if (node.leftNode == null) {
                    node.leftNode = new SegmentTreeNode(node.leftBound, mid);
                }

                if (node.rightNode == null) {
                    node.rightNode = new SegmentTreeNode(mid + 1, node.rightBound);
                }

                //将当前节点懒标记值向下传递给左右子节点，更新左右子节点表示的区间元素之和、区间元素的最大值、
                //区间元素的最小值、懒标记值，并将当前节点的懒标记值置0
                if (node.lazyValue != 0) {
                    node.leftNode.sumValue = node.leftNode.sumValue + (mid - node.leftBound + 1) * node.lazyValue;
                    node.rightNode.sumValue = node.rightNode.sumValue + (node.rightBound - mid) * node.lazyValue;
                    node.leftNode.maxValue = node.leftNode.maxValue + node.lazyValue;
                    node.rightNode.maxValue = node.rightNode.maxValue + node.lazyValue;
                    node.leftNode.minValue = node.leftNode.minValue + node.lazyValue;
                    node.rightNode.minValue = node.rightNode.minValue + node.lazyValue;
                    node.leftNode.lazyValue = node.leftNode.lazyValue + node.lazyValue;
                    node.rightNode.lazyValue = node.rightNode.lazyValue + node.lazyValue;

                    //将懒标记值传递给左右子节点之后，当前节点的懒标记值置为0
                    node.lazyValue = 0;
                }

                //左区间[node.leftBound,mid]元素的最大值
                int leftValue = queryMaxValue(node.leftNode, queryLeft, queryRight);
                //右区间[mid+1,node.rightBound]元素的最大值
                int rightValue = queryMaxValue(node.rightNode, queryLeft, queryRight);

                //返回左右子节点区间元素的最大值中较大的值，即为查询区间[queryLeft,queryRight]元素的最大值
                return Math.max(leftValue, rightValue);
            }

            /**
             * 查询区间[queryLeft,queryRight]元素的最小值
             * 时间复杂度O(logn)，空间复杂度O(logn) (n=数组的长度)
             *
             * @param node
             * @param queryLeft
             * @param queryRight
             * @return
             */
            public int queryMinValue(SegmentTreeNode node, int queryLeft, int queryRight) {
                //要查询的区间[queryLeft,queryRight]不在当前节点表示的范围[node.leftBound,node.rightBound]之内，
                //直接返回int范围的最小值，因为数组nums元素取值范围在[-100,100]
                if (node.leftBound > queryRight || node.rightBound < queryLeft) {
                    return Integer.MAX_VALUE;
                }

                //要查询的区间[queryLeft,queryRight]包含当前节点表示的范围[node.leftBound,node.rightBound]，
                //直接返回当前节点表示区间元素的最小值minValue
                if (queryLeft <= node.leftBound && node.rightBound <= queryRight) {
                    return node.minValue;
                }

                //建议取mid都这样写，不要写成相加再除2，例如区间[-1,0]，按照相加再除2得到mid为0，按照这样得到mid为-1
                int mid = node.leftBound + ((node.rightBound - node.leftBound) >> 1);

                //当前节点左右子树为空，动态开点
                if (node.leftNode == null) {
                    node.leftNode = new SegmentTreeNode(node.leftBound, mid);
                }

                if (node.rightNode == null) {
                    node.rightNode = new SegmentTreeNode(mid + 1, node.rightBound);
                }

                //将当前节点懒标记值向下传递给左右子节点，更新左右子节点表示的区间元素之和、区间元素的最大值、
                //区间元素的最小值、懒标记值，并将当前节点的懒标记值置0
                if (node.lazyValue != 0) {
                    node.leftNode.sumValue = node.leftNode.sumValue + (mid - node.leftBound + 1) * node.lazyValue;
                    node.rightNode.sumValue = node.rightNode.sumValue + (node.rightBound - mid) * node.lazyValue;
                    node.leftNode.maxValue = node.leftNode.maxValue + node.lazyValue;
                    node.rightNode.maxValue = node.rightNode.maxValue + node.lazyValue;
                    node.leftNode.minValue = node.leftNode.minValue + node.lazyValue;
                    node.rightNode.minValue = node.rightNode.minValue + node.lazyValue;
                    node.leftNode.lazyValue = node.leftNode.lazyValue + node.lazyValue;
                    node.rightNode.lazyValue = node.rightNode.lazyValue + node.lazyValue;

                    //将懒标记值传递给左右子节点之后，当前节点的懒标记值置为0
                    node.lazyValue = 0;
                }

                //左区间[node.leftBound,mid]元素的最小值
                int leftValue = queryMinValue(node.leftNode, queryLeft, queryRight);
                //右区间[mid+1,node.rightBound]元素的最小值
                int rightValue = queryMinValue(node.rightNode, queryLeft, queryRight);

                //返回左右子节点区间元素的最小值中较小的值，即为查询区间[queryLeft,queryRight]元素的最小值
                return Math.min(leftValue, rightValue);
            }

            /**
             * 更新区间[updateLeft,updateRight]节点值加上value
             * 时间复杂度O(logn)，空间复杂度O(logn) (n=数组的长度)
             *
             * @param node
             * @param updateLeft
             * @param updateRight
             * @param value
             */
            public void update(SegmentTreeNode node, int updateLeft, int updateRight, int value) {
                //要修改的区间[updateLeft,updateRight]不在当前节点表示的范围[node.leftBound,node.rightBound]之内，
                //直接返回
                if (node.leftBound > updateRight || node.rightBound < updateLeft) {
                    return;
                }

                //要查询的区间[updateLeft,updateRight]包含当前节点表示的范围[node.leftBound,node.rightBound]，
                //修改当前节点表示区间元素之和、区间元素的最大值、区间元素的最小值、懒标记值
                if (updateLeft <= node.leftBound && node.rightBound <= updateRight) {
                    node.sumValue = node.sumValue + (node.rightBound - node.leftBound + 1) * value;
                    node.maxValue = node.maxValue + value;
                    node.minValue = node.minValue + value;
                    node.lazyValue = node.lazyValue + value;
                    return;
                }

                //建议取mid都这样写，不要写成相加再除2，例如区间[-1,0]，按照相加再除2得到mid为0，按照这样得到mid为-1
                int mid = node.leftBound + ((node.rightBound - node.leftBound) >> 1);

                //当前节点左右子树为空，动态开点
                if (node.leftNode == null) {
                    node.leftNode = new SegmentTreeNode(node.leftBound, mid);
                }

                if (node.rightNode == null) {
                    node.rightNode = new SegmentTreeNode(mid + 1, node.rightBound);
                }

                //将当前节点懒标记值向下传递给左右子节点，更新左右子节点表示的区间元素之和、区间元素的最大值、
                //区间元素的最小值、懒标记值，并将当前节点的懒标记值置0
                if (node.lazyValue != 0) {
                    node.leftNode.sumValue = node.leftNode.sumValue + (mid - node.leftBound + 1) * node.lazyValue;
                    node.rightNode.sumValue = node.rightNode.sumValue + (node.rightBound - mid) * node.lazyValue;
                    node.leftNode.maxValue = node.leftNode.maxValue + node.lazyValue;
                    node.rightNode.maxValue = node.rightNode.maxValue + node.lazyValue;
                    node.leftNode.minValue = node.leftNode.minValue + node.lazyValue;
                    node.rightNode.minValue = node.rightNode.minValue + node.lazyValue;
                    node.leftNode.lazyValue = node.leftNode.lazyValue + node.lazyValue;
                    node.rightNode.lazyValue = node.rightNode.lazyValue + node.lazyValue;

                    //将懒标记值传递给左右子节点之后，当前节点的懒标记值置为0
                    node.lazyValue = 0;
                }

                update(node.leftNode, updateLeft, updateRight, value);
                update(node.rightNode, updateLeft, updateRight, value);

                //更新当前节点表示的区间元素之和，即为左右节点表示区间元素之和相加
                node.sumValue = node.leftNode.sumValue + node.rightNode.sumValue;
                //更新当前节点表示的区间元素的最大值，即为左右节点表示区间元素的最大值中较大的值
                node.maxValue = Math.max(node.leftNode.maxValue, node.rightNode.maxValue);
                //更新当前节点表示的区间元素的最小值，即为左右节点表示区间元素的最小值中较小的值
                node.minValue = Math.min(node.leftNode.minValue, node.rightNode.minValue);
            }

            /**
             * 更新区间[updateIndex,updateIndex]节点值为value
             * 时间复杂度O(logn)，空间复杂度O(logn) (n=数组的长度)
             *
             * @param node
             * @param updateIndex
             * @param value
             */
            public void update(SegmentTreeNode node, int updateIndex, int value) {
                //要修改的区间[updateIndex,updateIndex]不在当前节点表示的范围[node.leftBound,node.rightBound]之内，
                //直接返回
                if (node.leftBound > updateIndex || node.rightBound < updateIndex) {
                    return;
                }

                //找到要修改的区间[updateIndex,updateIndex]，
                //修改当前节点表示区间元素之和、区间元素的最大值、区间元素的最小值
                if (node.leftBound == node.rightBound) {
                    node.sumValue = value;
                    node.maxValue = value;
                    node.minValue = value;
                    return;
                }

                //建议取mid都这样写，不要写成相加再除2，例如区间[-1,0]，按照相加再除2得到mid为0，按照这样得到mid为-1
                int mid = node.leftBound + ((node.rightBound - node.leftBound) >> 1);

                //当前节点左右子树为空，动态开点
                if (node.leftNode == null) {
                    node.leftNode = new SegmentTreeNode(node.leftBound, mid);
                }

                if (node.rightNode == null) {
                    node.rightNode = new SegmentTreeNode(mid + 1, node.rightBound);
                }

                //将当前节点懒标记值向下传递给左右子节点，更新左右子节点表示的区间元素之和、区间元素的最大值、
                //区间元素的最小值、懒标记值，并将当前节点的懒标记值置0
                if (node.lazyValue != 0) {
                    node.leftNode.sumValue = node.leftNode.sumValue + (mid - node.leftBound + 1) * node.lazyValue;
                    node.rightNode.sumValue = node.rightNode.sumValue + (node.rightBound - mid) * node.lazyValue;
                    node.leftNode.maxValue = node.leftNode.maxValue + node.lazyValue;
                    node.rightNode.maxValue = node.rightNode.maxValue + node.lazyValue;
                    node.leftNode.minValue = node.leftNode.minValue + node.lazyValue;
                    node.rightNode.minValue = node.rightNode.minValue + node.lazyValue;
                    node.leftNode.lazyValue = node.leftNode.lazyValue + node.lazyValue;
                    node.rightNode.lazyValue = node.rightNode.lazyValue + node.lazyValue;

                    //将懒标记值传递给左右子节点之后，当前节点的懒标记值置为0
                    node.lazyValue = 0;
                }

                update(node.leftNode, updateIndex, value);
                update(node.rightNode, updateIndex, value);

                //更新当前节点表示的区间元素之和
                node.sumValue = node.leftNode.sumValue + node.rightNode.sumValue;
                //更新当前节点表示的区间元素的最大值，即为左右节点表示区间元素的最大值中较大的值
                node.maxValue = Math.max(node.leftNode.maxValue, node.rightNode.maxValue);
                //更新当前节点表示的区间元素的最小值，即为左右节点表示区间元素的最小值中较小的值
                node.minValue = Math.min(node.leftNode.minValue, node.rightNode.minValue);
            }

            /**
             * 线段树节点
             * 注意：节点存储的是区间元素之和sumValue，区间元素的最大值maxValue，区间元素的最小值minValue
             */
            private static class SegmentTreeNode {
                //当前节点表示区间元素之和
                private int sumValue;
                //当前节点表示区间元素的最大值
                private int maxValue;
                //当前节点表示区间元素的最小值
                private int minValue;
                //懒标记值，当前节点的所有子孙节点表示的区间需要向下传递的值
                private int lazyValue;
                //当前节点表示区间的左边界
                private int leftBound;
                //当前节点表示区间的右边界
                private int rightBound;
                private SegmentTreeNode leftNode;
                private SegmentTreeNode rightNode;

                public SegmentTreeNode(int leftBound, int rightBound) {
                    maxValue = Integer.MIN_VALUE;
                    minValue = Integer.MAX_VALUE;
                    this.leftBound = leftBound;
                    this.rightBound = rightBound;
                }
            }
        }
    }
}
