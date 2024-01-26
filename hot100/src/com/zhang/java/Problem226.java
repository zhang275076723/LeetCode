package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/5/16 8:46
 * @Author zsy
 * @Description 翻转二叉树 类比Problem156、Problem206、Problem998 类比Problem100、Problem101、Problem572、Problem951、Problem1367、Offer26、Offer27、Offer28 同Offer27
 * 给你一棵二叉树的根节点 root ，翻转这棵二叉树，并返回其根节点。
 * <p>
 * 输入：root = [4,2,7,1,3,6,9]
 * 输出：[4,7,2,9,6,3,1]
 * <p>
 * 输入：root = [2,1,3]
 * 输出：[2,3,1]
 * <p>
 * 输入：root = []
 * 输出：[]
 * <p>
 * 树中节点数目范围在 [0, 100] 内
 * -100 <= Node.val <= 100
 */
public class Problem226 {
    public static void main(String[] args) {
        Problem226 problem226 = new Problem226();
        int[] data = {4, 2, 7, 1, 3, 6, 9};
        TreeNode root = problem226.buildTree(data);
        problem226.traversal(root);
        System.out.println();
//        root = problem226.invertTree(root);
//        root = problem226.invertTree2(root);
        root = problem226.invertTree3(root);
        problem226.traversal(root);
    }

    /**
     * dfs递归
     * dfs遍历所有节点，交换当前节点的左右子节点
     * 时间复杂度O(n)，平均空间复杂度O(logn)，最坏空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }

        //翻转当前节点的左右子节点
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;

        invertTree(root.left);
        invertTree(root.right);

        return root;
    }

    /**
     * dfs非递归
     * 时间复杂度O(n)，平均空间复杂度O(logn)，最差空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public TreeNode invertTree2(TreeNode root) {
        if (root == null) {
            return null;
        }

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.empty()) {
            TreeNode node = stack.pop();

            //翻转当前节点的左右子节点
            TreeNode tempNode = node.left;
            node.left = node.right;
            node.right = tempNode;

            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }

        return root;
    }

    /**
     * bfs，层序遍历
     * 时间复杂度O(n)，平均空间复杂度O(logn)，最坏空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public TreeNode invertTree3(TreeNode root) {
        if (root == null) {
            return null;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();

            //翻转当前节点的左右子节点
            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;

            if (node.left != null) {
                queue.offer(node.left);
            }

            if (node.right != null) {
                queue.offer(node.right);
            }
        }

        return root;
    }

    private TreeNode buildTree(int[] data) {
        if (data == null || data.length == 0) {
            return null;
        }

        //data数组的索引
        int index = 0;
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode root = new TreeNode(data[index++]);
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();

            if (index < data.length) {
                TreeNode leftNode = new TreeNode(data[index++]);
                node.left = leftNode;
                queue.offer(leftNode);
            }
            if (index < data.length) {
                TreeNode rightNode = new TreeNode(data[index++]);
                node.right = rightNode;
                queue.offer(rightNode);
            }
        }

        return root;
    }

    private void traversal(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            System.out.println(node.val);
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
    }

    private static class TreeNode {
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
