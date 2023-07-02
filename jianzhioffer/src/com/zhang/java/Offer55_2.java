package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/4/3 10:43
 * @Author zsy
 * @Description 平衡二叉树 类比Offer26、Offer27、Offer28、Offer55 同Problem110
 * 输入一棵二叉树的根节点，判断该树是不是平衡二叉树。
 * 如果某二叉树中任意节点的左右子树的深度相差不超过1，那么它就是一棵平衡二叉树。
 * <p>
 * 给定二叉树 [3,9,20,null,null,15,7]，返回 true 。
 * <p>
 * 给定二叉树 [1,2,2,3,3,null,null,4,4]，返回 false 。
 * <p>
 * 0 <= 树的结点个数 <= 10000
 */
public class Offer55_2 {
    /**
     * 当前二叉树是否是平衡二叉树
     */
    private boolean isBalanced = true;

    public static void main(String[] args) {
        Offer55_2 offer55_2 = new Offer55_2();
        String[] data = {"1", "2", "2", "3", "3", "null", "null", "4", "4"};
        TreeNode root = offer55_2.buildTree(data);
        System.out.println(offer55_2.isBalanced(root));
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

        getHeight(root);

        return isBalanced;
    }

    private int getHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }

        //当前已经不是平衡二叉树，直接返回，相当于剪枝
        if (!isBalanced) {
            return -1;
        }

        int leftHeight = getHeight(root.left);
        int rightHeight = getHeight(root.right);

        //当前节点左右子树高度之差超过1，则不是平衡二叉树
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

        TreeNode(int x) {
            val = x;
        }
    }
}
