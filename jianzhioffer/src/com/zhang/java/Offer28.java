package com.zhang.java;

/**
 * @Date 2022/3/20 15:30
 * @Author zsy
 * @Description 对称的二叉树 类比Problem101
 * 请实现一个函数，用来判断一棵二叉树是不是对称的。如果一棵二叉树和它的镜像一样，那么它是对称的。
 * <p>
 * 输入：root = [1,2,2,3,4,4,3]
 * 输出：true
 * <p>
 * 输入：root = [1,2,2,null,3,null,3]
 * 输出：false
 */
public class Offer28 {
    public static void main(String[] args) {
        Offer28 offer28 = new Offer28();
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(2);
        TreeNode node4 = new TreeNode(3);
        TreeNode node5 = new TreeNode(4);
        TreeNode node6 = new TreeNode(4);
        TreeNode node7 = new TreeNode(3);
        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        node2.right = node5;
        node3.left = node6;
        node3.right = node7;
        System.out.println(offer28.isSymmetric(node1));
    }

    /**
     * 递归，时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }

        return recursion(root.left, root.right);
    }

    /**
     * 判断以leftNode为根节点的树和以rightNode为根节点的树是否对称
     *
     * @param leftNode
     * @param rightNode
     * @return
     */
    public boolean recursion(TreeNode leftNode, TreeNode rightNode) {
        if (leftNode == null && rightNode == null) {
            return true;
        }
        if (leftNode == null || rightNode == null) {
            return false;
        }
        if (leftNode.val == rightNode.val) {
            return recursion(leftNode.left, rightNode.right) && recursion(leftNode.right, rightNode.left);
        }
        return false;
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
