package com.zhang.java;

/**
 * @Date 2024/11/11 08:12
 * @Author zsy
 * @Description 达到末尾下标所需的最大跳跃次数 跳跃问题类比Problem45、Problem55、Problem403、Problem975、Problem1306、Problem1340、Problem1345、Problem1377、Problem1654、Problem1696、Problem1871、Problem2297、Problem2498、LCP09 记忆化搜索类比 线段树类比
 * 给你一个下标从 0 开始、由 n 个整数组成的数组 nums 和一个整数 target 。
 * 你的初始位置在下标 0 。
 * 在一步操作中，你可以从下标 i 跳跃到任意满足下述条件的下标 j ：
 * 0 <= i < j < n
 * -target <= nums[j] - nums[i] <= target
 * 返回到达下标 n - 1 处所需的 最大跳跃次数 。
 * 如果无法到达下标 n - 1 ，返回 -1 。
 * <p>
 * 输入：nums = [1,3,6,4,1,2], target = 2
 * 输出：3
 * 解释：要想以最大跳跃次数从下标 0 到下标 n - 1 ，可以按下述跳跃序列执行操作：
 * - 从下标 0 跳跃到下标 1 。
 * - 从下标 1 跳跃到下标 3 。
 * - 从下标 3 跳跃到下标 5 。
 * 可以证明，从 0 到 n - 1 的所有方案中，不存在比 3 步更长的跳跃序列。因此，答案是 3 。
 * <p>
 * 输入：nums = [1,3,6,4,1,2], target = 3
 * 输出：5
 * 解释：要想以最大跳跃次数从下标 0 到下标 n - 1 ，可以按下述跳跃序列执行操作：
 * - 从下标 0 跳跃到下标 1 。
 * - 从下标 1 跳跃到下标 2 。
 * - 从下标 2 跳跃到下标 3 。
 * - 从下标 3 跳跃到下标 4 。
 * - 从下标 4 跳跃到下标 5 。
 * 可以证明，从 0 到 n - 1 的所有方案中，不存在比 5 步更长的跳跃序列。因此，答案是 5 。
 * <p>
 * 输入：nums = [1,3,6,4,1,2], target = 0
 * 输出：-1
 * 解释：可以证明不存在从 0 到 n - 1 的跳跃序列。因此，答案是 -1 。
 * <p>
 * 2 <= nums.length == n <= 1000
 * -10^9 <= nums[i] <= 10^9
 * 0 <= target <= 2 * 10^9
 */
public class Problem2770 {
    public static void main(String[] args) {
        Problem2770 problem2770 = new Problem2770();
//        int[] nums = {1, 3, 6, 4, 1, 2};
//        int target = 2;
        int[] nums = {758043978, 79060681, 785252849, 287889790, -983845055, 224430896, -477101480};
        int target = 1769097904;
        System.out.println(problem2770.maximumJumps(nums, target));
        System.out.println(problem2770.maximumJumps2(nums, target));
        System.out.println(problem2770.maximumJumps3(nums, target));
    }

    /**
     * 动态规划
     * dp[i]：跳跃到下标索引i的最大跳跃次数
     * dp[i] = max(dp[j] + 1) (0 <= j < i，|nums[i]-nums[j]| <= target)
     * 如果是求最小跳跃次数，则使用bfs，同1345题
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param nums
     * @param target
     * @return
     */
    public int maximumJumps(int[] nums, int target) {
        int[] dp = new int[nums.length];
        //dp初始化
        dp[0] = 0;

        //dp初始化，初始化为int最小值表示无法跳跃到下标索引i
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Integer.MIN_VALUE;
        }

        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                //nums[i]和nums[j]差的绝对值小于等于target，并且能够跳跃到下标索引j，则更新dp[i]
                //使用long，避免相减int溢出
                if (Math.abs((long) nums[i] - nums[j]) <= target && dp[j] != Integer.MIN_VALUE) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        return dp[nums.length - 1] == Integer.MIN_VALUE ? -1 : dp[nums.length - 1];
    }

    /**
     * 递归+记忆化搜索
     * dp[i]：跳跃到下标索引i的最大跳跃次数
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param nums
     * @param target
     * @return
     */
    public int maximumJumps2(int[] nums, int target) {
        int[] dp = new int[nums.length];
        //dp初始化
        dp[0] = 0;

        //dp初始化，初始化为int最小值表示无法跳跃到下标索引i
        //注意：如果初始化-1，当dp[i]记忆化搜索后仍为-1时，之后每次对i进行记忆化搜索都需要遍历一遍dfs，即记忆化搜索失效，
        //即要保证初始化和经过记忆化搜索后的dp[i]不同
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Integer.MIN_VALUE;
        }

        //注意：经过记忆化搜索后dp[n-1]有可能不为int最小值，但仍小于0的数，都认为下标索引n-1不能到达，返回-1
        return dfs(nums.length - 1, nums, target, dp) < 0 ? -1 : dp[nums.length - 1];
    }

    /**
     * 线段树
     * 时间复杂度O(nlogC)，空间复杂度O(logC) (C=6*10^9，即区间的范围)
     *
     * @param nums
     * @param target
     * @return
     */
    public int maximumJumps3(int[] nums, int target) {
        //区间的范围为[-3*10^9,3*10^9]
        SegmentTree segmentTree = new SegmentTree((long) -3e9, (long) 3e9);
        //更新区间[nums[0],nums[0]]的最大值为0，即跳跃到下标索引0的最大跳跃次数为0
        segmentTree.update(segmentTree.root, nums[0], 0);

        //查询nums[i]之前存在前缀树中区间[nums[i]-target,nums[0]+target]的最大值，即跳跃到区间[nums[i]-target,nums[0]+target]下标索引的最大跳跃次数,
        //更新区间[nums[i],nums[i]]的最大值，即为跳跃到下标索引i的最大跳跃次数
        for (int i = 1; i < nums.length; i++) {
            //使用long，避免相减int溢出
            long queryLeft = (long) nums[i] - target;
            long queryRight = (long) nums[i] + target;
            int max = segmentTree.query(segmentTree.root, queryLeft, queryRight);
            segmentTree.update(segmentTree.root, nums[i], max + 1);
        }

        //跳跃到下标索引n-1的最大跳跃次数
        int result = segmentTree.query(segmentTree.root, nums[nums.length - 1], nums[nums.length - 1]);

        //result小于0，则认为下标索引n-1不能到达，返回-1
        return result < 0 ? -1 : result;
    }

    private int dfs(int t, int[] nums, int target, int[] dp) {
        //下标索引t经过dfs后，直接返回dp[t]
        if (dp[t] != Integer.MIN_VALUE) {
            return dp[t];
        }

        for (int i = 0; i < t; i++) {
            //nums[i]和nums[t]差的绝对值小于等于target，则更新dp[t]
            //使用long，避免相减int溢出
            if (Math.abs((long) nums[i] - nums[t]) <= target) {
                dp[t] = Math.max(dp[t], dfs(i, nums, target, dp) + 1);
            }
        }

        //注意：经过dfs后，如果下标索引t不能到达，则dp[t]不能仍为int最小值，要和没有经过dfs的初始化进行区分，
        //即为大于int最小值，但仍小于0的数
        return dp[t];
    }

    /**
     * 线段树，动态开点
     */
    private static class SegmentTree {
        private final SegmentTreeNode root;

        public SegmentTree(long leftBound, long rightBound) {
            root = new SegmentTreeNode(leftBound, rightBound);
        }

        public int query(SegmentTreeNode node, long queryLeft, long queryRight) {
            if (queryLeft > node.rightBound || queryRight < node.leftBound) {
                return Integer.MIN_VALUE;
            }

            if (queryLeft <= node.leftBound && node.rightBound <= queryRight) {
                return node.maxValue;
            }

            long mid = node.leftBound + ((node.rightBound - node.leftBound) >> 1);

            //动态开点
            if (node.leftNode == null) {
                node.leftNode = new SegmentTreeNode(node.leftBound, mid);
            }

            //动态开点
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

            return Math.max(leftValue, rightValue);
        }

        /**
         * 更新区间[updateIndex,updateIndex]中小于value的节点值为value
         *
         * @param node
         * @param updateIndex
         * @param value
         */
        public void update(SegmentTreeNode node, long updateIndex, int value) {
            if (updateIndex > node.rightBound || updateIndex < node.leftBound) {
                return;
            }

            if (node.leftBound == node.rightBound) {
                node.maxValue = Math.max(node.maxValue, value);
                return;
            }

            long mid = node.leftBound + ((node.rightBound - node.leftBound) >> 1);

            //动态开点
            if (node.leftNode == null) {
                node.leftNode = new SegmentTreeNode(node.leftBound, mid);
            }

            //动态开点
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

            node.maxValue = Math.max(node.leftNode.maxValue, node.rightNode.maxValue);
        }

        /**
         * 线段树节点
         */
        private static class SegmentTreeNode {
            //区间[leftBound,rightBound]元素出现的最大次数
            private int maxValue;
            private int lazyValue;
            //使用long，避免int溢出
            private long leftBound;
            //使用long，避免int溢出
            private long rightBound;
            private SegmentTreeNode leftNode;
            private SegmentTreeNode rightNode;

            public SegmentTreeNode(long leftBound, long rightBound) {
                maxValue = Integer.MIN_VALUE;
                this.leftBound = leftBound;
                this.rightBound = rightBound;
            }
        }
    }
}
