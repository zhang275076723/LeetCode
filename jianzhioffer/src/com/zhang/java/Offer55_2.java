package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/4/3 10:43
 * @Author zsy
 * @Description 平衡二叉树 同Problem110
 * 输入一棵二叉树的根节点，判断该树是不是平衡二叉树。
 * 如果某二叉树中任意节点的左右子树的深度相差不超过1，那么它就是一棵平衡二叉树。
 * <p>
 * 给定二叉树 [3,9,20,null,null,15,7]，返回 true 。
 * <p>
 * 给定二叉树 [1,2,2,3,3,null,null,4,4]，返回 false 。
 */
public class Offer55_2 {
    private boolean isBalanced = true;

    public static void main(String[] args) {
        Offer55_2 offer55_2 = new Offer55_2();
        String[] data = {"1", "2", "2", "3", "3", "null", "null", "4", "4"};
        TreeNode root = offer55_2.buildTree(data);
        System.out.println(offer55_2.isBalanced(root));
    }

    /**
     * 递归，深度优先判断是否是平衡二叉树
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public boolean isBalanced(TreeNode root) {
        nodeDepth(root);

        return isBalanced;
    }

    public int nodeDepth(TreeNode root) {
        //如果isBalanced为false，则直接剪枝返回
        if (!isBalanced) {
            return -1;
        }

        if (root == null) {
            return 0;
        }

        int leftNodeDepth = nodeDepth(root.left);
        int rightNodeDepth = nodeDepth(root.right);
        if (Math.abs(leftNodeDepth - rightNodeDepth) > 1) {
            isBalanced = false;
        }

        return Math.max(leftNodeDepth, rightNodeDepth) + 1;
    }

    /**
     * 层次遍历建树
     *
     * @param data
     * @return
     */
    public TreeNode buildTree(String[] data) {
        List<String> list = new ArrayList<>(Arrays.asList(data));
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode root = new TreeNode(Integer.parseInt(list.remove(0)));
        queue.add(root);

        while (list.size() >= 2) {
            TreeNode node = queue.remove();
            String leftValue = list.remove(0);
            String rightValue = list.remove(0);
            if (!"null".equals(leftValue)) {
                TreeNode leftNode = new TreeNode(Integer.parseInt(leftValue));
                node.left = leftNode;
                queue.add(leftNode);
            }
            if (!"null".equals(rightValue)) {
                TreeNode rightNode = new TreeNode(Integer.parseInt(rightValue));
                node.right = rightNode;
                queue.add(rightNode);
            }
        }

        //list集合只剩一个元素的处理
        if (list.size() == 1 && !"null".equals(list.get(0))) {
            TreeNode node = queue.remove();
            String leftValue = list.remove(0);
            TreeNode leftNode = new TreeNode(Integer.parseInt(leftValue));
            node.left = leftNode;
        }

        return root;
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
