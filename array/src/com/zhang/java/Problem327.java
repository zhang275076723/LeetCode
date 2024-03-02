package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/9/25 8:34
 * @Author zsy
 * @Description 区间和的个数 阿里机试题 归并排序类比Problem23、Problem148、Problem315、Problem493、Offer51、CalculateSmallSum 前缀和类比Problem209、Problem325、Problem437、Problem523、Problem525、Problem560、Problem862、Problem974、Problem1171、Problem1856、Problem1871、Offer57_2 线段树类比Problem307、Problem308、Problem654、Problem715、Problem729、Problem731、Problem732、Problem2407
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
        System.out.println(problem327.countRangeSum(nums, lower, upper));
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
     * 前缀和+归并排序
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
        //前缀和数组，preSum[i]：nums[0]-nums[i-1]之和
        //使用long避免int溢出
        long[] preSum = new long[nums.length + 1];

        for (int i = 1; i < preSum.length; i++) {
            preSum[i] = preSum[i - 1] + nums[i - 1];
        }

        return mergeSort(preSum, 0, preSum.length - 1, new long[preSum.length], lower, upper);
    }

    /**
     * 线段树+数据离散化 (将范围较大的前缀和，离散化到较小的范围之内)
     * 对于每个preSum[i]，都找之前的preSum[j]，满足lower<=preSum[i]-preSum[j]<=upper (0 < j < i)，
     * 可变形为preSum[i]-upper <= preSum[j] <= preSum[i]-lower，
     * 在线段树中找[preSum[i]-upper,preSum[i]-lower]区间范围内值的个数，即为区间和在[lower,upper]的个数
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param nums
     * @param lower
     * @param upper
     * @return
     */
    public int countRangeSum3(int[] nums, int lower, int upper) {
        //前缀和数组，preSum[i]：nums[0]-nums[i-1]之和
        //使用long避免int溢出
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

        //set数据由小到大排序，便于离散化到较小的数据范围之内
        list.sort(new Comparator<Long>() {
            @Override
            public int compare(Long a, Long b) {
                //不能写成return (int) (a - b);，因为long相减再转为int有可能在int范围溢出
                return Long.compare(a, b);
            }
        });

        //数据离散化map，将较大的值映射数据到[0,list.size()-1]，减小线段树的规模
        Map<Long, Integer> map = new HashMap<>();

        for (int i = 0; i < list.size(); i++) {
            map.put(list.get(i), i);
        }

        //线段树+数据离散化
        SegmentTree segmentTree = new SegmentTree(list.size());
        //离散化之后线段树所的左边界
        int leftBound = 0;
        //离散化之后线段树所的右边界
        int rightBound = list.size() - 1;

        int count = 0;

        //离散化之后的preSum[i]找离散化之后的区间[preSum[i]-upper,preSum[i]-lower]表示元素出现次数之和
        for (int i = 0; i < preSum.length; i++) {
            //map离散化之后的要查询区间的左右边界
            int queryLeft = map.get(preSum[i] - upper);
            int queryRight = map.get(preSum[i] - lower);
            //map离散化之后的更新区间左右边界
            int updateIndex = map.get(preSum[i]);

            count = count + segmentTree.query(0, leftBound, rightBound, queryLeft, queryRight);
            //离散化之后的区间[preSum[i],preSum[i]]表示元素出现次数加1
            segmentTree.update(0, leftBound, rightBound, updateIndex, updateIndex, 1);
        }

        return count;
    }

    /**
     * 线段树+动态开点
     * 对于每个preSum[i]，都找之前的preSum[j]，满足lower<=preSum[i]-preSum[j]<=upper (0 < j < i)，
     * 可变形为preSum[i]-upper <= preSum[j] <= preSum[i]-lower，
     * 在线段树中找[preSum[i]-upper,preSum[i]-lower]区间范围内值的个数，即为区间和在[lower,upper]的个数
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param nums
     * @param lower
     * @param upper
     * @return
     */
    public int countRangeSum4(int[] nums, int lower, int upper) {
        //前缀和数组，preSum[i]：nums[0]-nums[i-1]之和
        //使用long避免int溢出
        long[] preSum = new long[nums.length + 1];

        for (int i = 1; i < preSum.length; i++) {
            preSum[i] = preSum[i - 1] + nums[i - 1];
        }

        //线段树所表示区间的左边界
        long leftBound = Long.MAX_VALUE;
        //线段树所表示区间的右边界
        long rightBound = Long.MIN_VALUE;

        //得到线段树所表示区间的左边界和右边界
        for (int i = 0; i < preSum.length; i++) {
            long queryLeft = preSum[i] - upper;
            long queryRight = preSum[i] - lower;
            leftBound = Math.min(leftBound, Math.min(preSum[i], queryLeft));
            rightBound = Math.max(rightBound, Math.max(preSum[i], queryRight));
        }

        int count = 0;

        //线段树+动态开点
        SegmentTree2 segmentTree = new SegmentTree2(leftBound, rightBound);

        //preSum[i]找区间[preSum[i]-upper,preSum[i]-lower]表示元素出现次数之和
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
        if (left >= right) {
            return 0;
        }

        int count = 0;
        int mid = left + ((right - left) >> 1);

        count = count + mergeSort(preSum, left, mid, tempArr, lower, upper);
        count = count + mergeSort(preSum, mid + 1, right, tempArr, lower, upper);
        count = count + merge(preSum, left, mid, right, tempArr, lower, upper);

        return count;
    }

    private int merge(long[] preSum, int left, int mid, int right, long[] tempArr, int lower, int upper) {
        int count = 0;
        int i = left;
        int j = mid + 1;
        int k = left;
        //preSum[i]对应的区间左边界leftBound，满足preSum[leftBound]-preSum[i]>=lower
        int leftBound = mid + 1;
        //preSum[i]对应的区间右边界rightBound，满足preSum[rightBound]-preSum[i]<upper
        int rightBound = mid + 1;

        //查找每个preSum[i]满足要求的区间[leftBound,rightBound)，即区间内每个元素m都满足lower<=preSum[m]-preSum[i]<=upper
        while (i <= mid) {
            //preSum[i]对应的区间左边界leftBound
            while (leftBound <= right && preSum[leftBound] - preSum[i] < lower) {
                leftBound++;
            }

            //找preSum[i]对应的区间右边界rightBound
            while (rightBound <= right && preSum[rightBound] - preSum[i] <= upper) {
                rightBound++;
            }

            //区间[leftBound,rightBound)内每个元素m都满足lower<=preSum[m]-preSum[i]<=upper
            count = count + rightBound - leftBound;
            //i指针右移
            i++;
        }

        //i重新赋值，preSum数组中[left,mid]和[mid+1，right]进行合并
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
        //线段树数组，类似堆排序数组，用数组表示树，valueArr[i]：离散化后的元素值i出现的次数
        private final int[] valueArr;

        //懒标记数组，类似堆排序数组，用数组表示树，当前节点的所有子孙节点需要加上的值
        private final int[] lazyValueArr;

        public SegmentTree(int len) {
            //长度至少要开4n，确保包含了所有的区间
            valueArr = new int[4 * len];
            lazyValueArr = new int[4 * len];
        }

        /**
         * 查询区间[queryLeft,queryRight]元素出现次数之和
         * 时间复杂度O(logn)，空间复杂度O(logn)
         *
         * @param rootIndex  当前节点在线段树数组中的下标索引
         * @param left       当前节点表示的区间左边界
         * @param right      当前节点表示的区间右边界
         * @param queryLeft  要查询的区间左边界
         * @param queryRight 要查询的区间右边界
         * @return
         */
        public int query(int rootIndex, int left, int right, int queryLeft, int queryRight) {
            //要查询区间[queryLeft,queryRight]不在当前节点表示的范围[left,right]之内，直接返回0
            if (right < queryLeft || left > queryRight) {
                return 0;
            }

            //要查询区间[queryLeft,queryRight]包含当前节点表示的范围[left,right]，直接返回当前节点表示区间元素出现次数之和
            if (queryLeft <= left && right <= queryRight) {
                return valueArr[rootIndex];
            }

            //建议取mid都这样写，不要写成相加再除2，例如区间[-1,0]，按照相加再除2得到mid为0，按照这样得到mid为-1
            int mid = left + ((right - left) >> 1);
            //左子节点在valueArr、lazyValueArr的下标索引
            int leftRootIndex = rootIndex * 2 + 1;
            //右子节点在valueArr、lazyValueArr的下标索引
            int rightRootIndex = rootIndex * 2 + 2;

            //将当前节点懒标记值向下传递给左右子节点，更新左右子节点表示的区间元素出现次数之和、懒标记值，
            //并将当前节点的懒标记值置0
            if (lazyValueArr[rootIndex] != 0) {
                valueArr[leftRootIndex] = valueArr[leftRootIndex] + (mid - left + 1) * lazyValueArr[rootIndex];
                valueArr[rightRootIndex] = valueArr[rightRootIndex] + (right - mid) * lazyValueArr[rootIndex];
                lazyValueArr[leftRootIndex] = lazyValueArr[leftRootIndex] + lazyValueArr[rootIndex];
                lazyValueArr[rightRootIndex] = lazyValueArr[rightRootIndex] + lazyValueArr[rootIndex];

                //将懒标记值传递给左右子节点之后，当前节点的懒标记值置为0
                lazyValueArr[rootIndex] = 0;
            }

            //左区间[left,mid]元素出现次数之和
            int leftValue = query(leftRootIndex, left, mid, queryLeft, queryRight);
            //右区间[mid,right+1]元素出现次数之和
            int rightValue = query(rightRootIndex, mid + 1, right, queryLeft, queryRight);

            //返回左右子节点区间元素出现次数之和相加，即为查询区间[queryLeft,queryRight]元素出现次数之和
            return leftValue + rightValue;
        }

        /**
         * 更新区间[updateLeft,updateRight]元素出现次数加上value
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
            if (right < updateLeft || left > updateRight) {
                return;
            }

            //要修改的区间[updateLeft,updateRight]包含当前节点表示的范围[left,right]，
            //修改当前节点表示区间元素出现次数之和、懒标记值
            if (updateLeft <= left && right <= updateRight) {
                valueArr[rootIndex] = valueArr[rootIndex] + (right - left + 1) * value;
                lazyValueArr[rootIndex] = lazyValueArr[rootIndex] + value;
                return;
            }

            //建议取mid都这样写，不要写成相加再除2，例如区间[-1,0]，按照相加再除2得到mid为0，按照这样得到mid为-1
            int mid = left + ((right - left) >> 1);
            //左子节点在valueArr、lazyValueArr的下标索引
            int leftRootIndex = rootIndex * 2 + 1;
            //右子节点在valueArr、lazyValueArr的下标索引
            int rightRootIndex = rootIndex * 2 + 2;

            //将当前节点懒标记值向下传递给左右子节点，更新左右子节点表示的区间元素出现次数之和、懒标记值，
            //并将当前节点的懒标记值置0
            if (lazyValueArr[rootIndex] != 0) {
                valueArr[leftRootIndex] = valueArr[leftRootIndex] + (mid - left + 1) * lazyValueArr[rootIndex];
                valueArr[rightRootIndex] = valueArr[rightRootIndex] + (right - mid) * lazyValueArr[rootIndex];
                lazyValueArr[leftRootIndex] = lazyValueArr[leftRootIndex] + lazyValueArr[rootIndex];
                lazyValueArr[rightRootIndex] = lazyValueArr[rightRootIndex] + lazyValueArr[rootIndex];

                //将懒标记值传递给左右子节点之后，当前节点的懒标记值置为0
                lazyValueArr[rootIndex] = 0;
            }

            update(leftRootIndex, left, mid, updateLeft, updateRight, value);
            update(rightRootIndex, mid + 1, right, updateLeft, updateRight, value);

            //更新当前节点表示的区间元素出现次数之和，即为左右节点表示区间元素出现次数之和相加
            valueArr[rootIndex] = valueArr[leftRootIndex] + valueArr[rightRootIndex];
        }
    }

    /**
     * 线段树，动态开点
     * 适用于：不知道数组长度的情况下，多次求区间元素之和、区间元素的最大值，区间元素的最小值，并且区间内元素多次修改的情况
     */
    private static class SegmentTree2 {
        //线段树根节点
        private final SegmentTreeNode root;

        public SegmentTree2(long leftBound, long rightBound) {
            root = new SegmentTreeNode(leftBound, rightBound);
        }

        /**
         * 查询区间[queryLeft,queryRight]元素出现次数之和
         * 时间复杂度O(logn)，空间复杂度O(logn)
         *
         * @param node
         * @param queryLeft
         * @param queryRight
         * @return
         */
        public int query(SegmentTreeNode node, long queryLeft, long queryRight) {
            //要查询的区间[queryLeft,queryRight]不在当前节点表示的范围[node.leftBound,node.rightBound]之内，
            //直接返回0
            if (node.leftBound > queryRight || node.rightBound < queryLeft) {
                return 0;
            }

            //要查询的区间[queryLeft,queryRight]包含当前节点表示的范围[node.leftBound,node.rightBound]，
            //直接返回当前节点表示区间元素出现次数之和value
            if (queryLeft <= node.leftBound && node.rightBound <= queryRight) {
                return node.value;
            }

            //建议取mid都这样写，不要写成相加再除2，例如区间[-1,0]，按照相加再除2得到mid为0，按照这样得到mid为-1
            long mid = node.leftBound + ((node.rightBound - node.leftBound) >> 1);

            //当前节点左右子树为空，动态开点
            if (node.leftNode == null) {
                node.leftNode = new SegmentTreeNode(node.leftBound, mid);
            }

            if (node.rightNode == null) {
                node.rightNode = new SegmentTreeNode(mid + 1, node.rightBound);
            }

            //将当前节点懒标记值向下传递给左右子节点，更新左右子节点表示的区间元素出现次数之和、懒标记值，
            //并将当前节点的懒标记值置0
            if (node.lazyValue != 0) {
                node.leftNode.value = node.leftNode.value + (int) (mid - node.leftBound + 1) * node.lazyValue;
                node.rightNode.value = node.rightNode.value + (int) (node.rightBound - mid) * node.lazyValue;
                node.leftNode.lazyValue = node.leftNode.lazyValue + node.lazyValue;
                node.rightNode.lazyValue = node.rightNode.lazyValue + node.lazyValue;

                //将懒标记值传递给左右子节点之后，当前节点的懒标记值置为0
                node.lazyValue = 0;
            }

            //左区间[node.leftBound,mid]元素出现次数之和
            int leftValue = query(node.leftNode, queryLeft, queryRight);
            //右区间[mid+1,node.rightBound]元素出现次数之和
            int rightValue = query(node.rightNode, queryLeft, queryRight);

            //返回左右子节点区间元素出现次数之和相加，即为查询区间[queryLeft,queryRight]元素出现次数之和
            return leftValue + rightValue;
        }

        /**
         * 更新区间[updateLeft,updateRight]元素出现次数加上value
         * 时间复杂度O(logn)，空间复杂度O(logn)
         *
         * @param node
         * @param updateLeft
         * @param updateRight
         * @param value
         */
        public void update(SegmentTreeNode node, long updateLeft, long updateRight, int value) {
            //要修改的区间[updateLeft,updateRight]不在当前节点表示的范围[node.leftBound,node.rightBound]之内，
            //直接返回
            if (node.rightBound < updateLeft || node.leftBound > updateRight) {
                return;
            }

            //要查询的区间[updateLeft,updateRight]包含当前节点表示的范围[node.leftBound,node.rightBound]，
            //修改当前节点表示区间元素出现次数之和、懒标记值
            if (updateLeft <= node.leftBound && node.rightBound <= updateRight) {
                node.value = node.value + (int) (node.rightBound - node.leftBound + 1) * value;
                node.lazyValue = node.lazyValue + value;
                return;
            }

            //建议取mid都这样写，不要写成相加再除2，例如区间[-1,0]，按照相加再除2得到mid为0，按照这样得到mid为-1
            long mid = node.leftBound + ((node.rightBound - node.leftBound) >> 1);

            //当前节点左右子树为空，动态开点
            if (node.leftNode == null) {
                node.leftNode = new SegmentTreeNode(node.leftBound, mid);
            }

            if (node.rightNode == null) {
                node.rightNode = new SegmentTreeNode(mid + 1, node.rightBound);
            }

            //将当前节点懒标记值向下传递给左右子节点，更新左右子节点表示的区间元素出现次数之和、懒标记值，
            //并将当前节点的懒标记值置0
            if (node.lazyValue != 0) {
                node.leftNode.value = node.leftNode.value + (int) (mid - node.leftBound + 1) * node.lazyValue;
                node.rightNode.value = node.rightNode.value + (int) (node.rightBound - mid) * node.lazyValue;
                node.leftNode.lazyValue = node.leftNode.lazyValue + node.lazyValue;
                node.rightNode.lazyValue = node.rightNode.lazyValue + node.lazyValue;

                //将懒标记值传递给左右子节点之后，当前节点的懒标记值置为0
                node.lazyValue = 0;
            }

            update(node.leftNode, updateLeft, updateRight, value);
            update(node.rightNode, updateLeft, updateRight, value);

            //更新当前节点表示区间元素出现次数之和，即为左右节点表示区间元素出现次数之和相加
            node.value = node.leftNode.value + node.rightNode.value;
        }

        /**
         * 线段树节点
         * 注意：节点存储的是区间[leftBound,rightBound]元素出现次数之和value
         */
        private static class SegmentTreeNode {
            //当前节点表示区间元素出现次数之和
            private int value;
            //懒标记值，当前节点的所有子孙节点需要加上的值
            private int lazyValue;
            //当前节点表示区间的左边界
            //使用long，避免int溢出
            private long leftBound;
            //当前节点表示区间的右边界
            //使用long，避免int溢出
            private long rightBound;
            private SegmentTreeNode leftNode;
            private SegmentTreeNode rightNode;

            public SegmentTreeNode(long leftBound, long rightBound) {
                this.leftBound = leftBound;
                this.rightBound = rightBound;
            }
        }
    }
}
