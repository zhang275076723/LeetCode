package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/10/7 09:24
 * @Author zsy
 * @Description 二叉搜索树中的搜索 类比Problem235、Problem236、Problem270、Problem272、Problem285、Problem450、Problem510、Problem701、Offer68、Offer68_2
 * 给定二叉搜索树（BST）的根节点 root 和一个整数值 val。
 * 你需要在 BST 中找到节点值等于 val 的节点。
 * 返回以该节点为根的子树。 如果节点不存在，则返回 null 。
 * <p>
 * 输入：root = [4,2,7,1,3], val = 2
 * 输出：[2,1,3]
 * <p>
 * 输入：root = [4,2,7,1,3], val = 5
 * 输出：[]
 * <p>
 * 数中节点数在 [1, 5000] 范围内
 * 1 <= Node.val <= 10^7
 * root 是二叉搜索树
 * 1 <= val <= 10^7
 */
public class Problem700 {
    public static void main(String[] args) {
        Problem700 problem700 = new Problem700();
        String[] data = {"4", "2", "7", "1", "3"};
        TreeNode root = problem700.buildTree(data);
        int val = 2;
//        TreeNode node = problem700.searchBST(root, val);
        TreeNode node = problem700.searchBST(root, val);
    }

    /**
     * 递归
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @param val
     * @return
     */
    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null) {
            return null;
        }

        //往右找
        if (root.val < val) {
            return searchBST(root.right, val);
        } else if (root.val > val) {
            //往左找
            return searchBST(root.left, val);
        } else {
            //找到
            return root;
        }
    }

    /**
     * 非递归
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param root
     * @param val
     * @return
     */
    public TreeNode searchBST2(TreeNode root, int val) {
        if (root == null) {
            return null;
        }

        TreeNode node = root;

        while (node != null) {
            //往右找
            if (node.val < val) {
                node = node.right;
            } else if (node.val > val) {
                //往左找
                node = node.left;
            } else {
                //找到
                return node;
            }
        }

        return null;
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
