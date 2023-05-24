package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/5/21 08:35
 * @Author zsy
 * @Description 二叉搜索子树的最大键值和 dfs类比Problem104、Problem110、Problem111、Problem124、Problem337、Problem543、Problem687
 * 给你一棵以 root 为根的 二叉树 ，请你返回 任意 二叉搜索子树的最大键值和。
 * 二叉搜索树的定义如下：
 * 任意节点的左子树中的键值都 小于 此节点的键值。
 * 任意节点的右子树中的键值都 大于 此节点的键值。
 * 任意节点的左子树和右子树都是二叉搜索树。
 * <p>
 * 输入：root = [1,4,3,2,4,2,5,null,null,null,null,null,null,4,6]
 * 输出：20
 * 解释：键值为 3 的子树是和最大的二叉搜索树。
 * <p>
 * 输入：root = [4,3,null,1,2]
 * 输出：2
 * 解释：键值为 2 的单节点子树是和最大的二叉搜索树。
 * <p>
 * 输入：root = [-4,-2,-5]
 * 输出：0
 * 解释：所有节点键值都为负数，和最大的二叉搜索树为空。
 * <p>
 * 输入：root = [2,1,3]
 * 输出：6
 * <p>
 * 输入：root = [5,4,8,3,null,6,3]
 * 输出：7
 * <p>
 * 每棵树有 1 到 40000 个节点。
 * 每个节点的键值在 [-4 * 10^4 , 4 * 10^4] 之间。
 */
public class Problem1373 {
    /**
     * dfs中二叉搜索树的最大键值和
     */
    private int sum = 0;

    public static void main(String[] args) {
        Problem1373 problem1373 = new Problem1373();
        String[] data = {"1", "4", "3", "2", "4", "2", "5", "null", "null", "null", "null", "null", "null", "4", "6"};
        TreeNode root = problem1373.buildTree(data);
        System.out.println(problem1373.maxSumBST(root));
    }

    /**
     * dfs
     * 计算每一个节点作为二叉搜索树根节点的最大键值和，更新二叉搜索树的最大键值和，
     * 并返回当前节点作为二叉搜索树根节点对父节点的最大键值和数组，用于当前节点父节点更新二叉搜索树的最大键值和
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int maxSumBST(TreeNode root) {
        if (root == null) {
            return 0;
        }

        dfs(root);

        return sum;
    }

    /**
     * 得到当前节点作为二叉搜索树根节点的最大键值和数组
     * arr[0]：当前节点作为二叉搜索树根节点的最大键值和，如果不是二叉搜索树，则最大键值和为0
     * arr[1]：当前树中节点的最大值，如果不是二叉搜索树，则为int最大值
     * arr[2]：当前树中节点的最小值，如果不是二叉搜索树，则为int最小值
     *
     * @param root
     * @return
     */
    private int[] dfs(TreeNode root) {
        //当前节点为空，则树中节点的最大值为int最小值，树中节点的最小值为int最大值，表示是一颗特殊的二叉搜索树
        if (root == null) {
            return new int[]{0, Integer.MIN_VALUE, Integer.MAX_VALUE};
        }

        //左子树作为二叉搜索树根节点的最大键值和数组
        int[] leftArr = dfs(root.left);
        //右子树作为二叉搜索树根节点的最大键值和数组
        int[] rightArr = dfs(root.right);

        //根节点的值大于左子树的最大值，并且根节点的值小于右子树的最小值，才是二叉搜索树，否则不是二叉搜索树，
        //赋值树中节点的最大值为int最大值，树中节点的最小值为int最小值，表示不是二叉搜索树
        if (!(root.val > leftArr[1] && root.val < rightArr[2])) {
            return new int[]{0, Integer.MAX_VALUE, Integer.MIN_VALUE};
        }

        //更新二叉搜索树的最大键值和
        sum = Math.max(sum, root.val + leftArr[0] + rightArr[0]);

        //返回当前节点作为二叉搜索树根节点对父节点的最大键值和数组，用于当前节点父节点更新二叉搜索树的最大键值和
        return new int[]{root.val + leftArr[0] + rightArr[0],
                Math.max(root.val, rightArr[1]),
                Math.min(root.val, leftArr[2])};
    }

    private TreeNode buildTree(String[] data) {
        if (data == null || data.length == 0) {
            return null;
        }

        List<String> list = new ArrayList<>(Arrays.asList(data));
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode root = new TreeNode(Integer.parseInt(list.remove(0)));
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (!list.isEmpty()) {
                String leftValue = list.remove(0);
                if (!"null".equals(leftValue)) {
                    TreeNode leftNode = new TreeNode(Integer.parseInt(leftValue));
                    node.left = leftNode;
                    queue.offer(leftNode);
                }
            }
            if (!list.isEmpty()) {
                String rightValue = list.remove(0);
                if (!"null".equals(rightValue)) {
                    TreeNode rightNode = new TreeNode(Integer.parseInt(rightValue));
                    node.right = rightNode;
                    queue.offer(rightNode);
                }
            }
        }

        return root;
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
