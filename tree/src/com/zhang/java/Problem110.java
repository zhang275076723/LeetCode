package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/6/27 8:25
 * @Author zsy
 * @Description 平衡二叉树 同Offer55_2
 * 给定一个二叉树，判断它是否是高度平衡的二叉树。
 * 本题中，一棵高度平衡二叉树定义为：
 * 一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1 。
 * <p>
 * 输入：root = [3,9,20,null,null,15,7]
 * 输出：true
 * <p>
 * 输入：root = [1,2,2,3,3,null,null,4,4]
 * 输出：false
 * <p>
 * 输入：root = []
 * 输出：true
 * <p>
 * 树中的节点数在范围 [0, 5000] 内
 * -10^4 <= Node.val <= 10^4
 */
public class Problem110 {
    /**
     * 当前二叉树是否是平衡二叉树
     */
    private boolean isBalanced = true;

    public static void main(String[] args) {
        Problem110 problem110 = new Problem110();
        String[] data = {"3", "9", "20", "null", "null", "15", "7"};
        TreeNode root = problem110.buildTree(data);
        System.out.println(problem110.isBalanced(root));
    }

    /**
     * dfs
     * 判断每一个节点的左右子树高度之差是否大于1
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }

        nodeDepth(root);

        return isBalanced;
    }

    private int nodeDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        //当前节点子树已经不是平衡二叉树，直接剪枝返回
        if (!isBalanced) {
            return -1;
        }

        int leftHeight = nodeDepth(root.left);
        int rightHeight = nodeDepth(root.right);

        if (Math.abs(leftHeight - rightHeight) > 1) {
            isBalanced = false;
        }

        return Math.max(leftHeight, rightHeight) + 1;
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
