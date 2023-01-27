package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/9/25 8:34
 * @Author zsy
 * @Description 区间和的个数 阿里笔试题 归并排序类比Problem23、Problem148、Problem315、Problem493、Offer51 线段树类比Problem307、Problem308、Problem729、Problem731、Problem732
 * 给你一个整数数组 nums 以及两个整数 lower 和 upper 。
 * 求数组中，值位于范围 [lower, upper] （包含 lower 和 upper）之内的 区间和的个数 。
 * 区间和 S(i, j) 表示在 nums 中，位置从 i 到 j 的元素之和，包含 i 和 j (i ≤ j)。
 * <p>
 * 输入：nums = [-2,5,-1], lower = -2, upper = 2
 * 输出：3
 * 解释：存在三个区间：[0,0]、[2,2] 和 [0,2] ，对应的区间和分别是：-2 、-1 、2 。
 * <p>
 * 输入：nums = [0], lower = 0, upper = 0
 * 输出：1
 * <p>
 * 1 <= nums.length <= 10^5
 * -2^31 <= nums[i] <= 2^31 - 1
 * -10^5 <= lower <= upper <= 10^5
 * 题目数据保证答案是一个 32 位 的整数
 */
public class Problem327 {
    public static void main(String[] args) {
        Problem327 problem327 = new Problem327();
//        int[] nums = {-1, 1, 7, 2, -11, 7, 2, 1};
//        int lower = 0;
//        int upper = 4;
        int[] nums = {-2, 5, -1};
        int lower = -2;
        int upper = 2;
//        System.out.println(problem327.countRangeSum(nums, lower, upper));
        System.out.println(problem327.countRangeSum2(nums, lower, upper));
        System.out.println(problem327.countRangeSum3(nums, lower, upper));
        System.out.println(problem327.countRangeSum4(nums, lower, upper));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param nums
     * @param lower
     * @param upper
     * @return
     */
    public int countRangeSum(int[] nums, int lower, int upper) {
        int count = 0;

        for (int i = 0; i < nums.length; i++) {
            //使用long，避免int溢出
            long sum = 0;

            for (int j = i; j < nums.length; j++) {
                sum = sum + nums[j];

                if (lower <= sum && sum <= upper) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * 归并排序+前缀和
     * 前缀和数组进行归并排序，求在[lower,upper]区间范围内的前缀和之差，前缀和排序只影响统计的先后顺序，不影响最终结果，
     * 在合并时，如果左区间preSum[i]，右区间的左指针元素preSum[l]和右指针元素preSum[r]，
     * 满足preSum[l]-preSum[i]>=lower && preSum[r-1]-preSum[i]<=upper，则[l,r)都满足区间和在[lower,upper]之间
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param nums
     * @param lower
     * @param upper
     * @return
     */
    public int countRangeSum2(int[] nums, int lower, int upper) {
        //前缀和数组使用long，避免int溢出
        long[] preSum = new long[nums.length + 1];

        for (int i = 1; i < preSum.length; i++) {
            preSum[i] = preSum[i - 1] + nums[i - 1];
        }

        return mergeSort(preSum, 0, preSum.length - 1, new long[preSum.length], lower, upper);
    }

    /**
     * 权值线段树+数据离散化 (将范围较大的前缀和，离散化到较小的范围之内)
     * 对于每个preSum[i]，都找之前的preSum[j]，满足lower<=preSum[i]-preSum[j]<=upper (0 < j < i)，
     * 可变形为preSum[i]-upper <= preSum[j] <= preSum[i]-lower，
     * 在线段树中找[preSum[i]-upper,preSum[i]-lower]区间范围内值的个数，
     * 即为区间和在[lower,upper]的个数
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param nums
     * @param lower
     * @param upper
     * @return
     */
    public int countRangeSum3(int[] nums, int lower, int upper) {
        //前缀和数组使用long，避免int溢出
        long[] preSum = new long[nums.length + 1];

        for (int i = 1; i < preSum.length; i++) {
            preSum[i] = preSum[i - 1] + nums[i - 1];
        }

        Set<Long> set = new HashSet<>();

        //统计不同数的个数，用于数据离散化
        for (int i = 0; i < preSum.length; i++) {
            set.add(preSum[i]);
            set.add(preSum[i] - lower);
            set.add(preSum[i] - upper);
        }

        List<Long> list = new ArrayList<>(set);

        //set数据由小到大排序
        list.sort(new Comparator<Long>() {
            @Override
            public int compare(Long a, Long b) {
                long result = a - b;

                if (result < 0) {
                    return -1;
                } else if (result > 0) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        //数据离散化map，映射数据到[0,list.size()-1]
        Map<Long, Integer> map = new HashMap<>();

        for (int i = 0; i < list.size(); i++) {
            map.put(list.get(i), i);
        }

        int count = 0;

        //权值线段树+数据离散化
        SegmentTree segmentTree = new SegmentTree(list.size());
        //区间的最大右边界
        int maxRight = list.size() - 1;

        //离散化之后的preSum[i]找离散化之后的[preSum[i]-upper,preSum[i]-lower]区间范围内值的个数，即preSum[i]
        for (int i = 0; i < preSum.length; i++) {
            //map离散化之后的查询区间左右边界
            int queryLeft = map.get(preSum[i] - upper);
            int queryRight = map.get(preSum[i] - lower);
            //map离散化之后的更新区间左右边界
            int updateIndex = map.get(preSum[i]);

            count = count + segmentTree.query(0, 0, maxRight, queryLeft, queryRight);
            //离散化之后的preSum[i]加入到线段树中，离散化之后的[preSum[i],preSum[i]]区间对应数值出现的次数加1
            segmentTree.update(0, 0, maxRight, updateIndex, updateIndex, 1);
        }

        return count;
    }

    /**
     * 权值线段树+动态开点
     * 对于每个preSum[i]，都找之前的preSum[j]，满足lower<=preSum[i]-preSum[j]<=upper (0 < j < i)，
     * 可变形为preSum[i]-upper <= preSum[j] <= preSum[i]-lower，
     * 在线段树中找[preSum[i]-upper,preSum[i]-lower]区间范围内值的个数，
     * 即为区间和在[lower,upper]的个数
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param nums
     * @param lower
     * @param upper
     * @return
     */
    public int countRangeSum4(int[] nums, int lower, int upper) {
        //前缀和数组使用long，避免int溢出
        long[] preSum = new long[nums.length + 1];

        for (int i = 1; i < preSum.length; i++) {
            preSum[i] = preSum[i - 1] + nums[i - 1];
        }

        //区间左边界
        long leftBound = Long.MAX_VALUE;
        //区间右边界
        long rightBound = Long.MIN_VALUE;

        for (int i = 0; i < preSum.length; i++) {
            long queryLeft = preSum[i] - upper;
            long queryRight = preSum[i] - lower;
            leftBound = Math.min(leftBound, Math.min(preSum[i], Math.min(queryLeft, queryRight)));
            rightBound = Math.max(rightBound, Math.max(preSum[i], Math.max(queryLeft, queryRight)));
        }

        int count = 0;

        //权值线段树+动态开点
        SegmentTree2 segmentTree = new SegmentTree2(leftBound, rightBound);

        //preSum[i]找[preSum[i]-upper,preSum[i]-lower]区间范围内值的个数
        for (int i = 0; i < preSum.length; i++) {
            long queryLeft = preSum[i] - upper;
            long queryRight = preSum[i] - lower;

            count = count + segmentTree.query(segmentTree.root, queryLeft, queryRight);
            //preSum[i]加入到线段树中，[preSum[i],preSum[i]]区间对应数值出现的次数加1
            segmentTree.update(segmentTree.root, preSum[i], preSum[i], 1);
        }

        return count;
    }

    private int mergeSort(long[] preSum, int left, int right, long[] tempArr, int lower, int upper) {
        if (left < right) {
            int count = 0;
            int mid = left + ((right - left) >> 1);
            count = count + mergeSort(preSum, left, mid, tempArr, lower, upper);
            count = count + mergeSort(preSum, mid + 1, right, tempArr, lower, upper);
            count = count + merge(preSum, left, mid, right, tempArr, lower, upper);
            return count;
        }

        return 0;
    }

    private int merge(long[] preSum, int left, int mid, int right, long[] tempArr, int lower, int upper) {
        int count = 0;
        int i = left;
        int j = mid + 1;
        int k = left;
        int l = mid + 1;
        int r = mid + 1;

        //查找每个左区间preSum[i]满足要求的右区间preSum[l]和preSum[r]，
        //即[l,r)都满足区间preSum[r-1]-preSum[i]和preSum[l]-preSum[i]在[lower,upper]之间
        while (i <= mid) {
            //找右半部分的左区间l
            while (l <= right && preSum[l] - preSum[i] < lower) {
                l++;
            }

            //找右半部分的右区间r
            while (r <= right && preSum[r] - preSum[i] <= upper) {
                r++;
            }

            //preSum[r-1]-preSum[i]和preSum[l]-preSum[i]之间的[l,r)都满足区间和在[lower,upper]之间
            count = count + r - l;
            //i指针右移
            i++;
        }

        i = left;

        while (i <= mid && j <= right) {
            if (preSum[i] < preSum[j]) {
                tempArr[k] = preSum[i];
                i++;
                k++;
            } else {
                tempArr[k] = preSum[j];
                j++;
                k++;
            }
        }

        while (i <= mid) {
            tempArr[k] = preSum[i];
            i++;
            k++;
        }

        while (j <= right) {
            tempArr[k] = preSum[j];
            j++;
            k++;
        }

        for (k = left; k <= right; k++) {
            preSum[k] = tempArr[k];
        }

        return count;
    }

    /**
     * 线段树，用数组表示线段树
     */
    private static class SegmentTree {
        //线段树数组，类似堆排序数组，用数组表示树
        private final int[] segmentTreeArr;

        //懒标记数组，类似堆排序数组，用数组表示树，当前节点的所有子孙节点表示的区间需要向下传递的值
        private final int[] lazyValueArr;

        SegmentTree(int len) {
            //长度至少要开4n，确保包含了所有的区间
            segmentTreeArr = new int[4 * len];
            lazyValueArr = new int[4 * len];
        }

        /**
         * 查询区间[queryLeft,queryRight]元素之和
         * 时间复杂度O(logn)，空间复杂度O(logn)
         *
         * @param rootIndex  当前节点在线段树数组中的下标索引
         * @param left       当前节点表示的区间左边界
         * @param right      当前节点表示的区间右边界
         * @param queryLeft  要查询的区间左边界
         * @param queryRight 要查询的区间右边界
         * @return
         */
        private int query(int rootIndex, int left, int right, int queryLeft, int queryRight) {
            if (right < queryLeft || left > queryRight) {
                return 0;
            }

            if (queryLeft <= left && right <= queryRight) {
                return segmentTreeArr[rootIndex];
            }

            int mid = left + ((right - left) >> 1);
            int leftRootIndex = rootIndex * 2 + 1;
            int rightRootIndex = rootIndex * 2 + 2;

            if (lazyValueArr[rootIndex] != 0) {
                segmentTreeArr[leftRootIndex] = segmentTreeArr[leftRootIndex] + (mid - left + 1) * lazyValueArr[rootIndex];
                segmentTreeArr[rightRootIndex] = segmentTreeArr[rightRootIndex] + (right - mid) * lazyValueArr[rootIndex];
                lazyValueArr[leftRootIndex] = lazyValueArr[leftRootIndex] + lazyValueArr[rootIndex];
                lazyValueArr[rightRootIndex] = lazyValueArr[rightRootIndex] + lazyValueArr[rootIndex];

                lazyValueArr[rootIndex] = 0;
            }

            int leftValue = query(leftRootIndex, left, mid, queryLeft, queryRight);
            int rightValue = query(rightRootIndex, mid + 1, right, queryLeft, queryRight);

            return leftValue + rightValue;
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
        private void update(int rootIndex, int left, int right, int updateLeft, int updateRight, int value) {
            if (right < updateLeft || left > updateRight) {
                return;
            }

            if (updateLeft <= left && right <= updateRight) {
                segmentTreeArr[rootIndex] = segmentTreeArr[rootIndex] + (right - left + 1) * value;
                lazyValueArr[rootIndex] = lazyValueArr[rootIndex] + value;
                return;
            }

            int mid = left + ((right - left) >> 1);
            int leftRootIndex = rootIndex * 2 + 1;
            int rightRootIndex = rootIndex * 2 + 2;

            if (lazyValueArr[rootIndex] != 0) {
                segmentTreeArr[leftRootIndex] = segmentTreeArr[leftRootIndex] + (mid - left + 1) * lazyValueArr[rootIndex];
                segmentTreeArr[rightRootIndex] = segmentTreeArr[rightRootIndex] + (right - mid) * lazyValueArr[rootIndex];
                lazyValueArr[leftRootIndex] = lazyValueArr[leftRootIndex] + lazyValueArr[rootIndex];
                lazyValueArr[rightRootIndex] = lazyValueArr[rightRootIndex] + lazyValueArr[rootIndex];

                lazyValueArr[rootIndex] = 0;
            }

            update(leftRootIndex, left, mid, updateLeft, updateRight, value);
            update(rightRootIndex, mid + 1, right, updateLeft, updateRight, value);

            segmentTreeArr[rootIndex] = segmentTreeArr[leftRootIndex] + segmentTreeArr[rightRootIndex];
        }
    }

    /**
     * 权值线段树，动态开点
     */
    private static class SegmentTree2 {
        //线段树根节点
        private final SegmentTreeNode root;

        SegmentTree2(long leftBound, long rightBound) {
            root = new SegmentTreeNode(leftBound, rightBound);
        }

        /**
         * 查询区间[queryLeft,queryRight]元素之和
         * 时间复杂度O(logn)，空间复杂度O(logn)
         *
         * @param node
         * @param queryLeft
         * @param queryRight
         * @return
         */
        private int query(SegmentTreeNode node, long queryLeft, long queryRight) {
            if (node.leftBound > queryRight || node.rightBound < queryLeft) {
                return 0;
            }

            if (queryLeft <= node.leftBound && node.rightBound <= queryRight) {
                return node.value;
            }

            long mid = node.leftBound + ((node.rightBound - node.leftBound) >> 1);

            //当前节点左右子树为空，动态开点
            if (node.leftNode == null) {
                node.leftNode = new SegmentTreeNode(node.leftBound, mid);
            }

            if (node.rightNode == null) {
                node.rightNode = new SegmentTreeNode(mid + 1, node.rightBound);
            }

            if (node.lazyValue != 0) {
                node.leftNode.value = node.leftNode.value + (int) (mid - node.leftBound + 1) * node.lazyValue;
                node.rightNode.value = node.rightNode.value + (int) (node.rightBound - mid) * node.lazyValue;
                node.leftNode.lazyValue = node.leftNode.lazyValue + node.lazyValue;
                node.rightNode.lazyValue = node.rightNode.lazyValue + node.lazyValue;

                node.lazyValue = 0;
            }

            int leftValue = query(node.leftNode, queryLeft, queryRight);
            int rightValue = query(node.rightNode, queryLeft, queryRight);

            return leftValue + rightValue;
        }

        /**
         * 更新区间[updateLeft,updateRight]节点值加上value
         * 时间复杂度O(logn)，空间复杂度O(logn)
         *
         * @param node
         * @param updateLeft
         * @param updateRight
         * @param value
         */
        private void update(SegmentTreeNode node, long updateLeft, long updateRight, int value) {
            if (node.rightBound < updateLeft || node.leftBound > updateRight) {
                return;
            }

            if (updateLeft <= node.leftBound && node.rightBound <= updateRight) {
                node.value = node.value + (int) (node.rightBound - node.leftBound + 1) * value;
                node.lazyValue = node.lazyValue + value;
                return;
            }

            long mid = node.leftBound + ((node.rightBound - node.leftBound) >> 1);

            //当前节点左右子树为空，动态开点
            if (node.leftNode == null) {
                node.leftNode = new SegmentTreeNode(node.leftBound, mid);
            }

            if (node.rightNode == null) {
                node.rightNode = new SegmentTreeNode(mid + 1, node.rightBound);
            }

            if (node.lazyValue != 0) {
                node.leftNode.value = node.leftNode.value + (int) (mid - node.leftBound + 1) * node.lazyValue;
                node.rightNode.value = node.rightNode.value + (int) (node.rightBound - mid) * node.lazyValue;
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
         * 注意：节点存储的是对应数值出现的次数
         */
        private static class SegmentTreeNode {
            //当前节点对应数值出现的次数，对应数值即为当前节点为叶节点，leftBound等于rightBound，数值为leftBound
            int value;
            //当前节点表示区间的左边界，使用long，避免int溢出
            long leftBound;
            //当前节点表示区间的右边界，使用long，避免int溢出
            long rightBound;
            //懒标记值，当前节点的所有子孙节点表示的区间需要向下传递的值
            int lazyValue;
            SegmentTreeNode leftNode;
            SegmentTreeNode rightNode;

            SegmentTreeNode(long leftBound, long rightBound) {
                this.leftBound = leftBound;
                this.rightBound = rightBound;
            }
        }
    }
}
