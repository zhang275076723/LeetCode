package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/8/12 08:39
 * @Author zsy
 * @Description 最大二叉树 类比Problem998 分治法类比Problem95、Problem105、Problem106、Problem108、Problem109、Problem255、Problem395、Problem449、Problem617、Problem889、Problem1008、Offer7、Offer33 线段树类比Problem307、Problem308、Problem327、Problem729、Problem731、Problem732 单调栈类比Problem42、Problem84、Problem255、Problem316、Problem321、Problem402、Problem456、Problem496、Problem503、Problem739、Problem907、Problem1019、Problem1856、Problem2104、Problem2454、Problem2487、Offer33、DoubleStackSort
 * 给定一个不重复的整数数组 nums 。
 * 最大二叉树 可以用下面的算法从 nums 递归地构建:
 * 创建一个根节点，其值为 nums 中的最大值。
 * 递归地在最大值 左边 的 子数组前缀上 构建左子树。
 * 递归地在最大值 右边 的 子数组后缀上 构建右子树。
 * 返回 nums 构建的 最大二叉树 。
 * <p>
 * 输入：nums = [3,2,1,6,0,5]
 * 输出：[6,3,5,null,2,0,null,null,1]
 * 解释：递归调用如下所示：
 * - [3,2,1,6,0,5] 中的最大值是 6 ，左边部分是 [3,2,1] ，右边部分是 [0,5] 。
 * - [3,2,1] 中的最大值是 3 ，左边部分是 [] ，右边部分是 [2,1] 。
 * - 空数组，无子节点。
 * - [2,1] 中的最大值是 2 ，左边部分是 [] ，右边部分是 [1] 。
 * - 空数组，无子节点。
 * - 只有一个元素，所以子节点是一个值为 1 的节点。
 * - [0,5] 中的最大值是 5 ，左边部分是 [0] ，右边部分是 [] 。
 * - 只有一个元素，所以子节点是一个值为 0 的节点。
 * - 空数组，无子节点。
 * <p>
 * 输入：nums = [3,2,1]
 * 输出：[3,null,2,null,1]
 * <p>
 * 1 <= nums.length <= 1000
 * 0 <= nums[i] <= 1000
 * nums 中的所有整数 互不相同
 */
public class Problem654 {
    public static void main(String[] args) {
        Problem654 problem654 = new Problem654();
        int[] nums = {3, 2, 1, 6, 0, 5};
        TreeNode root = problem654.constructMaximumBinaryTree(nums);
        TreeNode root2 = problem654.constructMaximumBinaryTree2(nums);
        TreeNode root3 = problem654.constructMaximumBinaryTree3(nums);
        TreeNode root4 = problem654.constructMaximumBinaryTree4(nums);
    }

    /**
     * 分治法
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        return dfs(nums, 0, nums.length - 1);
    }

    /**
     * 分治法+线段树(数组表示线段树)
     * 通过线段树O(logn)得到区间最大值
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public TreeNode constructMaximumBinaryTree2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        //线段树，O(logn)得到区间最大值
        SegmentTree segmentTree = new SegmentTree(nums);
        //哈希表，O(1)得到区间最大值对应的nums数组下标索引
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }

        return dfs(nums, 0, nums.length - 1, segmentTree, map);
    }

    /**
     * 分治法+线段树(动态开点)
     * 通过线段树O(logn)得到区间最大值
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public TreeNode constructMaximumBinaryTree3(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        //线段树，O(logn)得到区间最大值
        SegmentTree2 segmentTree = new SegmentTree2(nums);
        //哈希表，O(1)得到区间最大值对应的nums数组下标索引
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }

        return dfs(nums, 0, nums.length - 1, segmentTree, map);
    }

    /**
     * 单调栈
     * 单调递减栈存放nums表示的树中节点
     * 当前元素小于栈顶元素，栈顶元素节点的右子节点指向当前元素节点；
     * 当前元素大于栈顶元素，栈顶元素出栈，当前元素节点的左子节点指向出栈元素节点
     * 时间复杂度O(n)，空间复杂度O(n)
     * <p>
     * 例如：nums = [3,2,1,6,0,5]
     * <   3       3      3            6        6           6   5           6
     * <            \      \          /        / \         / \ /         /    \
     * <      =>     2  =>  2  =>   3   =>    3   0  =>   3   0    =>   3      5
     * <                     \       \        \           \             \    /
     * <                      1       2        2           2             2  0
     * <                               \        \           \             \
     * <                                1        1           1             1
     * <
     * <
     * <                  1
     * <          2       2                     0            5             5
     * <   3      3       3           6         6            6             6 (栈中元素)
     *
     * @param nums
     * @return
     */
    public TreeNode constructMaximumBinaryTree4(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        //单调递减栈，这里使用双向队列作为栈，是因为要返回根节点，栈底节点即为根节点
        Deque<TreeNode> stack = new LinkedList<>();

        for (int i = 0; i < nums.length; i++) {
            TreeNode node = new TreeNode(nums[i]);

            //不满足单调递减栈，栈顶元素出栈，当前元素节点的左子节点指向出栈元素节点
            while (!stack.isEmpty() && stack.peekLast().val < node.val) {
                node.left = stack.pollLast();
            }

            //栈不为空，满足单调递减栈，即当前元素小于栈顶元素，栈顶元素节点的右子节点指向当前元素节点
            if (!stack.isEmpty()) {
                stack.peekLast().right = node;
            }

            //当前节点入栈
            stack.offerLast(node);
        }

        //栈底节点即为根节点
        return stack.peekFirst();
    }

    private TreeNode dfs(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }

        if (left == right) {
            return new TreeNode(nums[left]);
        }

        //nums[left]-nums[right]最大值下标索引
        int maxIndex = left;

        for (int i = left; i <= right; i++) {
            if (nums[maxIndex] < nums[i]) {
                maxIndex = i;
            }
        }

        TreeNode root = new TreeNode(nums[maxIndex]);

        root.left = dfs(nums, left, maxIndex - 1);
        root.right = dfs(nums, maxIndex + 1, right);

        return root;
    }

    private TreeNode dfs(int[] nums, int left, int right, SegmentTree segmentTree, Map<Integer, Integer> map) {
        if (left > right) {
            return null;
        }

        if (left == right) {
            return new TreeNode(nums[left]);
        }

        //通过线段树，得到nums[left]-nums[right]的最大值
        int maxValue = segmentTree.query(0, 0, nums.length - 1, left, right);
        //通过map，得到maxValue在nums数组的下标索引
        int index = map.get(maxValue);

        TreeNode root = new TreeNode(maxValue);

        root.left = dfs(nums, left, index - 1, segmentTree, map);
        root.right = dfs(nums, index + 1, right, segmentTree, map);

        return root;
    }

    private TreeNode dfs(int[] nums, int left, int right, SegmentTree2 segmentTree, Map<Integer, Integer> map) {
        if (left > right) {
            return null;
        }

        if (left == right) {
            return new TreeNode(nums[left]);
        }

        //通过线段树，得到nums[left]-nums[right]的最大值
        int maxValue = segmentTree.query(segmentTree.root, left, right);
        //通过map，得到maxValue在nums数组的下标索引
        int index = map.get(maxValue);

        TreeNode root = new TreeNode(maxValue);

        root.left = dfs(nums, left, index - 1, segmentTree, map);
        root.right = dfs(nums, index + 1, right, segmentTree, map);

        return root;
    }

    /**
     * 线段树，用数组表示线段树
     */
    private static class SegmentTree {
        //区间元素的最大值数组
        private final int[] valueArr;
        //懒标记数组，当前节点的所有子孙节点需要加上的值
        private final int[] lazyValueArr;

        public SegmentTree(int[] nums) {
            //长度至少要开4n，确保包含了所有的区间
            valueArr = new int[4 * nums.length];
            lazyValueArr = new int[4 * nums.length];

            buildSegmentTree(nums, 0, 0, nums.length - 1);
        }

        private void buildSegmentTree(int[] nums, int rootIndex, int left, int right) {
            if (left == right) {
                valueArr[rootIndex] = nums[left];
                return;
            }

            int mid = left + ((right - left) >> 1);
            int leftRootIndex = rootIndex * 2 + 1;
            int rightRootIndex = rootIndex * 2 + 2;

            buildSegmentTree(nums, leftRootIndex, left, mid);
            buildSegmentTree(nums, rightRootIndex, mid + 1, right);

            //valueArr[rootIndex]为左右子树中的较大值
            valueArr[rootIndex] = Math.max(valueArr[leftRootIndex], valueArr[rightRootIndex]);
        }

        public int query(int rootIndex, int left, int right, int queryLeft, int queryRight) {
            //区间[left,right]不在要查询的区间[queryLeft,queryRight]，返回int最小值
            if (left > queryRight || right < queryLeft) {
                return Integer.MIN_VALUE;
            }

            if (queryLeft <= left && right <= queryRight) {
                return valueArr[rootIndex];
            }

            int mid = left + ((right - left) >> 1);
            int leftRootIndex = rootIndex * 2 + 1;
            int rightRootIndex = rootIndex * 2 + 2;

            if (lazyValueArr[rootIndex] != 0) {
                valueArr[leftRootIndex] = valueArr[leftRootIndex] + lazyValueArr[rootIndex];
                valueArr[rightRootIndex] = valueArr[rightRootIndex] + lazyValueArr[rootIndex];
                lazyValueArr[leftRootIndex] = valueArr[leftRootIndex] + lazyValueArr[rootIndex];
                lazyValueArr[rightRootIndex] = valueArr[rightRootIndex] + lazyValueArr[rootIndex];

                lazyValueArr[rootIndex] = 0;
            }

            int leftValue = query(leftRootIndex, left, mid, queryLeft, queryRight);
            int rightValue = query(rightRootIndex, mid + 1, right, queryLeft, queryRight);

            //返回左右子树中的较大值
            return Math.max(leftValue, rightValue);
        }

        public void update(int rootIndex, int left, int right, int updateLeft, int updateRight, int value) {
            if (left > updateRight || right < updateLeft) {
                return;
            }

            if (updateLeft <= left && right <= updateRight) {
                valueArr[rootIndex] = valueArr[rootIndex] + value;
                lazyValueArr[rootIndex] = lazyValueArr[rootIndex] + value;
                return;
            }

            int mid = left + ((right - left) >> 1);
            int leftRootIndex = rootIndex * 2 + 1;
            int rightRootIndex = rootIndex * 2 + 2;

            if (lazyValueArr[rootIndex] != 0) {
                valueArr[leftRootIndex] = valueArr[leftRootIndex] + lazyValueArr[rootIndex];
                valueArr[rightRootIndex] = valueArr[rightRootIndex] + lazyValueArr[rootIndex];
                lazyValueArr[leftRootIndex] = lazyValueArr[leftRootIndex] + lazyValueArr[rootIndex];
                lazyValueArr[rightRootIndex] = lazyValueArr[rightRootIndex] + lazyValueArr[rootIndex];

                lazyValueArr[rootIndex] = 0;
            }

            update(leftRootIndex, left, mid, updateLeft, updateRight, value);
            update(rightRootIndex, mid + 1, right, updateLeft, updateRight, value);

            //更新valueArr[rootIndex]为左右子树中的较大值
            valueArr[rootIndex] = Math.max(valueArr[leftRootIndex], valueArr[rightRootIndex]);
        }
    }

    /**
     * 线段树，动态开点
     */
    private static class SegmentTree2 {
        private final SegmentTreeNode root;

        public SegmentTree2(int[] nums) {
            root = new SegmentTreeNode(0, nums.length - 1);

            for (int i = 0; i < nums.length; i++) {
                update(root, i, nums[i]);
            }
        }

        public int query(SegmentTreeNode node, int queryLeft, int queryRight) {
            //区间[node.leftBound,node.rightBound]不在要查询的区间[queryLeft,queryRight]，返回int最小值
            if (node.leftBound > queryRight || node.rightBound < queryLeft) {
                return Integer.MIN_VALUE;
            }

            if (queryLeft <= node.leftBound && node.rightBound <= queryRight) {
                return node.value;
            }

            int mid = node.leftBound + ((node.rightBound - node.leftBound) >> 1);

            if (node.leftNode == null) {
                node.leftNode = new SegmentTreeNode(node.leftBound, mid);
            }

            if (node.rightNode == null) {
                node.rightNode = new SegmentTreeNode(mid + 1, node.rightBound);
            }

            if (node.lazyValue != 0) {
                node.leftNode.value = node.leftNode.value + node.lazyValue;
                node.rightNode.value = node.rightNode.value + node.lazyValue;
                node.leftNode.lazyValue = node.leftNode.lazyValue + node.lazyValue;
                node.rightNode.lazyValue = node.rightNode.lazyValue + node.lazyValue;

                node.lazyValue = 0;
            }

            int leftValue = query(node.leftNode, queryLeft, queryRight);
            int rightValue = query(node.rightNode, queryLeft, queryRight);

            //返回左右子树中的较大值
            return Math.max(leftValue, rightValue);
        }

        public void update(SegmentTreeNode node, int updateLeft, int updateRight, int value) {
            if (node.leftBound > updateRight || node.rightBound < updateLeft) {
                return;
            }

            if (updateLeft <= node.leftBound && node.rightBound <= updateRight) {
                node.value = node.value + value;
                node.lazyValue = node.lazyValue + value;
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
                node.leftNode.value = node.leftNode.value + node.lazyValue;
                node.rightNode.value = node.rightNode.value + node.lazyValue;
                node.leftNode.lazyValue = node.leftNode.lazyValue + node.lazyValue;
                node.rightNode.lazyValue = node.rightNode.lazyValue + node.lazyValue;

                node.lazyValue = 0;
            }

            update(node.leftNode, updateLeft, updateRight);
            update(node.rightNode, updateLeft, updateRight);

            //更新node.value为左右子树中的较大值
            node.value = Math.max(node.leftNode.value, node.rightNode.value);
        }

        public void update(SegmentTreeNode node, int updateIndex, int value) {
            if (node.leftBound > updateIndex || node.rightBound < updateIndex) {
                return;
            }

            if (node.leftBound == node.rightBound) {
                node.value = value;
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
                node.leftNode.value = node.leftNode.value + node.lazyValue;
                node.rightNode.value = node.rightNode.value + node.lazyValue;
                node.leftNode.lazyValue = node.leftNode.lazyValue + node.lazyValue;
                node.rightNode.lazyValue = node.rightNode.lazyValue + node.lazyValue;

                node.lazyValue = 0;
            }

            update(node.leftNode, updateIndex, value);
            update(node.rightNode, updateIndex, value);

            //更新node.value为左右子树中的较大值
            node.value = Math.max(node.leftNode.value, node.rightNode.value);
        }

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

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
