package com.zhang.java;

/**
 * @Date 2023/9/5 08:32
 * @Author zsy
 * @Description 最长递增子序列 II 类比Problem300、Problem673 线段树类比Problem307、Problem308、Problem327、Problem370、Problem654、Problem715、Problem729、Problem731、Problem732、Problem1109、Problem1893 子序列和子数组类比Problem53、Problem115、Problem152、Problem209、Problem300、Problem325、Problem392、Problem491、Problem516、Problem525、Problem560、Problem581、Problem659、Problem673、Problem674、Problem718、Problem862、Problem1143、Offer42、Offer57_2
 * 给你一个整数数组 nums 和一个整数 k 。
 * 找到 nums 中满足以下要求的最长子序列：
 * 子序列 严格递增
 * 子序列中相邻元素的差值 不超过 k 。
 * 请你返回满足上述要求的 最长子序列 的长度。
 * 子序列 是从一个数组中删除部分元素后，剩余元素不改变顺序得到的数组。
 * <p>
 * 输入：nums = [4,2,1,4,3,4,5,8,15], k = 3
 * 输出：5
 * 解释：
 * 满足要求的最长子序列是 [1,3,4,5,8] 。
 * 子序列长度为 5 ，所以我们返回 5 。
 * 注意子序列 [1,3,4,5,8,15] 不满足要求，因为 15 - 8 = 7 大于 3 。
 * <p>
 * 输入：nums = [7,4,5,1,8,12,4,7], k = 5
 * 输出：4
 * 解释：
 * 满足要求的最长子序列是 [4,5,8,12] 。
 * 子序列长度为 4 ，所以我们返回 4 。
 * <p>
 * 输入：nums = [1,5], k = 1
 * 输出：1
 * 解释：
 * 满足要求的最长子序列是 [1] 。
 * 子序列长度为 1 ，所以我们返回 1 。
 * <p>
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i], k <= 10^5
 */
public class Problem2407 {
    public static void main(String[] args) {
        Problem2407 problem2407 = new Problem2407();
        int[] nums = {4, 2, 1, 4, 3, 4, 5, 8, 15};
        int k = 3;
//        int[] nums = {1, 100, 500, 100000, 100000};
//        int k = 100000;
        System.out.println(problem2407.lengthOfLIS(nums, k));
        System.out.println(problem2407.lengthOfLIS2(nums, k));
        System.out.println(problem2407.lengthOfLIS3(nums, k));
    }

    /**
     * 动态规划
     * dp[i]：以nums[i]结尾的最长递增子序列的长度，保证最长子序列中相邻元素的差值不超过k
     * dp[i] = max(dp[j]+1) (0 <= j < i，nums[j] < nums[i]，nums[i] - nums[j] <= k)
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param nums
     * @param k
     * @return
     */
    public int lengthOfLIS(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int[] dp = new int[nums.length];
        //dp初始化
        dp[0] = 1;
        //最长递增子序列的长度，保证最长子序列中相邻元素的差值不超过k
        int maxLen = 1;

        for (int i = 1; i < nums.length; i++) {
            dp[i] = 1;

            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i] && nums[i] - nums[j] <= k) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }

            maxLen = Math.max(maxLen, dp[i]);
        }

        return maxLen;
    }

    /**
     * 动态规划+线段树(用数组表示线段树)
     * dp[i]：以nums[i]结尾的最长递增子序列的长度，保证最长子序列中相邻元素的差值不超过k
     * dp[i] = max(dp[j]+1) (0 <= j < i，nums[j] < nums[i]，nums[i] - nums[j] <= k)
     * 通过线段树O(logn)获取nums[i]对应[nums[i]-k,nums[i]-1]中dp的最大值
     * 时间复杂度O(nlogm)，空间复杂度O(m) (n：数组长度，m：数组最大值)
     *
     * @param nums
     * @param k
     * @return
     */
    public int lengthOfLIS2(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        //最长递增子序列的长度，保证最长子序列中相邻元素的差值不超过k
        int maxLen = 1;
        //线段树左边界为1，因为nums数组元素范围为[1,10^5]
        int leftBound = 1;
        //线段树右边界为10^5，因为nums数组元素范围为[1,10^5]
        int rightBound = (int) 1e5;
        //线段树(用数组表示线段树)
        SegmentTree segmentTree = new SegmentTree(rightBound - leftBound + 1);

        for (int i = 0; i < nums.length; i++) {
            //nums[i]查询的左边界
            int queryLeft = Math.max(1, nums[i] - k);
            //nums[i]查询的右边界
            int queryRight = nums[i] - 1;
            //nums[i]对应[nums[i]-k,nums[i]-1]中dp的最大值
            int max = segmentTree.query(0, leftBound, rightBound, queryLeft, queryRight);
            //更新maxLen
            maxLen = Math.max(maxLen, max + 1);
            //更新区间[nums[i],nums[i]]值为max+1
            segmentTree.update(0, leftBound, rightBound, nums[i], max + 1);
        }

        return maxLen;
    }

    /**
     * 动态规划+线段树(动态开点)
     * dp[i]：以nums[i]结尾的最长递增子序列的长度，保证最长子序列中相邻元素的差值不超过k
     * dp[i] = max(dp[j]+1) (0 <= j < i，nums[j] < nums[i]，nums[i] - nums[j] <= k)
     * 通过线段树O(logn)获取nums[i]对应[nums[i]-k,nums[i]-1]中dp的最大值
     * 时间复杂度O(nlogm)，空间复杂度O(m) (n：数组长度，m：数组最大值)
     *
     * @param nums
     * @param k
     * @return
     */
    public int lengthOfLIS3(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        //最长递增子序列的长度，保证最长子序列中相邻元素的差值不超过k
        int maxLen = 1;
        //线段树左边界为1，因为nums数组元素范围为[1,10^5]
        int leftBound = 1;
        //线段树右边界为10^5，因为nums数组元素范围为[1,10^5]
        int rightBound = (int) 1e5;
        //线段树(动态开点)
        SegmentTree2 segmentTree = new SegmentTree2(leftBound, rightBound);

        for (int i = 0; i < nums.length; i++) {
            int queryLeft = Math.max(1, nums[i] - k);
            int queryRight = nums[i] - 1;
            int max = segmentTree.query(segmentTree.root, queryLeft, queryRight);
            maxLen = Math.max(maxLen, max + 1);
            segmentTree.update(segmentTree.root, nums[i], max + 1);
        }

        return maxLen;
    }

    /**
     * 线段树，用数组表示线段树
     */
    private static class SegmentTree {
        //区间最大值数组
        private final int[] maxValueArr;
        //懒标记数组，所有子孙节点需要加上的值
        private final int[] lazyValueArr;

        public SegmentTree(int len) {
            maxValueArr = new int[len * 4];
            lazyValueArr = new int[len * 4];
        }

        public int query(int rootIndex, int left, int right, int queryLeft, int queryRight) {
            //区间[left,right]不在要查询的区间[queryLeft,queryRight]，返回0
            if (left > queryRight || right < queryLeft) {
                return 0;
            }

            if (queryLeft <= left && right <= queryRight) {
                return maxValueArr[rootIndex];
            }

            int mid = left + ((right - left) >> 1);
            int leftRootIndex = rootIndex * 2 + 1;
            int rightRootIndex = rootIndex * 2 + 2;

            if (lazyValueArr[rootIndex] != 0) {
                maxValueArr[leftRootIndex] = maxValueArr[leftRootIndex] + lazyValueArr[rootIndex];
                maxValueArr[rightRootIndex] = maxValueArr[rightRootIndex] + lazyValueArr[rootIndex];
                lazyValueArr[leftRootIndex] = lazyValueArr[leftRootIndex] + lazyValueArr[rootIndex];
                lazyValueArr[rightRootIndex] = lazyValueArr[rightRootIndex] + lazyValueArr[rootIndex];

                lazyValueArr[rootIndex] = 0;
            }

            int leftValue = query(leftRootIndex, left, mid, queryLeft, queryRight);
            int rightValue = query(rightRootIndex, mid + 1, right, queryLeft, queryRight);

            return Math.max(leftValue, rightValue);
        }

        /**
         * 更新区间[updateIndex,updateIndex]节点值为value
         *
         * @param rootIndex
         * @param left
         * @param right
         * @param updateIndex
         * @param value
         */
        public void update(int rootIndex, int left, int right, int updateIndex, int value) {
            if (left > updateIndex || right < updateIndex) {
                return;
            }

            if (left == right) {
                maxValueArr[rootIndex] = value;
                return;
            }

            int mid = left + ((right - left) >> 1);
            int leftRootIndex = rootIndex * 2 + 1;
            int rightRootIndex = rootIndex * 2 + 2;

            if (lazyValueArr[rootIndex] != 0) {
                maxValueArr[leftRootIndex] = maxValueArr[leftRootIndex] + lazyValueArr[rootIndex];
                maxValueArr[rightRootIndex] = maxValueArr[rightRootIndex] + lazyValueArr[rootIndex];
                lazyValueArr[leftRootIndex] = lazyValueArr[leftRootIndex] + lazyValueArr[rootIndex];
                lazyValueArr[rightRootIndex] = lazyValueArr[rightRootIndex] + lazyValueArr[rootIndex];

                lazyValueArr[rootIndex] = 0;
            }

            update(leftRootIndex, left, mid, updateIndex, value);
            update(rightRootIndex, mid + 1, right, updateIndex, value);

            maxValueArr[rootIndex] = Math.max(maxValueArr[leftRootIndex], maxValueArr[rightRootIndex]);
        }
    }

    /**
     * 线段树，动态开点
     */
    private static class SegmentTree2 {
        private final SegmentTreeNode root;

        public SegmentTree2(int leftBound, int rightBound) {
            root = new SegmentTreeNode(leftBound, rightBound);
        }

        public int query(SegmentTreeNode node, int queryLeft, int queryRight) {
            //区间[node.leftBound,node.rightBound]不在要查询的区间[queryLeft,queryRight]，返回0
            if (node.leftBound > queryRight || node.rightBound < queryLeft) {
                return 0;
            }

            if (queryLeft <= node.leftBound && node.rightBound <= queryRight) {
                return node.maxValue;
            }

            int mid = node.leftBound + ((node.rightBound - node.leftBound) >> 1);

            if (node.leftNode == null) {
                node.leftNode = new SegmentTreeNode(node.leftBound, mid);
            }

            if (node.rightNode == null) {
                node.rightNode = new SegmentTreeNode(mid + 1, node.rightBound);
            }

            if (node.lazyValue != 0) {
                node.leftNode.maxValue = node.leftNode.maxValue + node.lazyValue;
                node.rightNode.maxValue = node.rightNode.maxValue + node.lazyValue;
                node.leftNode.lazyValue = node.leftNode.lazyValue + node.lazyValue;
                node.rightNode.lazyValue = node.rightNode.lazyValue + node.lazyValue;

                node.lazyValue = 0;
            }

            int leftValue = query(node.leftNode, queryLeft, queryRight);
            int rightValue = query(node.rightNode, queryLeft, queryRight);

            //返回左右子树中的较大值
            return Math.max(leftValue, rightValue);
        }

        /**
         * 更新区间[updateIndex,updateIndex]节点值为value
         *
         * @param node
         * @param updateIndex
         * @param value
         */
        public void update(SegmentTreeNode node, int updateIndex, int value) {
            if (node.leftBound > updateIndex || node.rightBound < updateIndex) {
                return;
            }

            if (node.leftBound == node.rightBound) {
                node.maxValue = value;
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
                node.leftNode.maxValue = node.leftNode.maxValue + node.lazyValue;
                node.rightNode.maxValue = node.rightNode.maxValue + node.lazyValue;
                node.leftNode.lazyValue = node.leftNode.lazyValue + node.lazyValue;
                node.rightNode.lazyValue = node.rightNode.lazyValue + node.lazyValue;

                node.lazyValue = 0;
            }

            update(node.leftNode, updateIndex, value);
            update(node.rightNode, updateIndex, value);

            //更新node.maxValue为左右子树中的较大值
            node.maxValue = Math.max(node.leftNode.maxValue, node.rightNode.maxValue);
        }

        private static class SegmentTreeNode {
            private int maxValue;
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
