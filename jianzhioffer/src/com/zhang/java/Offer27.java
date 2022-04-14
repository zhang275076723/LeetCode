package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/3/20 10:45
 * @Author zsy
 * @Description 请完成一个函数，输入一个二叉树，该函数输出它的镜像
 * <p>
 * 输入：root = [4,2,7,1,3,6,9]
 * 输出：[4,7,2,9,6,3,1]
 */
public class Offer27 {
    public static void main(String[] args) {
        Offer27 offer27 = new Offer27();
        TreeNode node1 = new TreeNode(4);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(7);
        TreeNode node4 = new TreeNode(1);
        TreeNode node5 = new TreeNode(3);
        TreeNode node6 = new TreeNode(6);
        TreeNode node7 = new TreeNode(9);
        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        node2.right = node5;
        node3.left = node6;
        node3.right = node7;
        offer27.traversal(node1);
//        TreeNode node = offer27.mirrorTree(node1);
//        TreeNode node = offer27.mirrorTree2(node1);
        TreeNode node = offer27.mirrorTree3(node1);
        offer27.traversal(node1);
    }


    /**
     * 递归，自下而上交换，时间复杂度O(n)，空间复杂的O(n)
     *
     * @param root
     * @return
     */
    public TreeNode mirrorTree(TreeNode root) {
        if (root == null) {
            return null;
        }

        TreeNode leftNode = mirrorTree(root.left);
        TreeNode rightNode = mirrorTree(root.right);
        root.left = rightNode;
        root.right = leftNode;

        return root;
    }

    /**
     * 栈，自上而下交换，时间复杂度O(n)，空间复杂的O(n)
     *
     * @param root
     * @return
     */
    public TreeNode mirrorTree2(TreeNode root) {
        if (root == null) {
            return null;
        }

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.empty()) {
            TreeNode node = stack.pop();
            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
            TreeNode tempNode = node.left;
            node.left = node.right;
            node.right = tempNode;
        }

        return root;
    }

    /**
     * 队列，自上而下交换，时间复杂度O(n)，空间复杂的O(n)
     *
     * @param root
     * @return
     */
    public TreeNode mirrorTree3(TreeNode root) {
        if (root == null) {
            return null;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.remove();
            TreeNode tempNode = node.left;
            node.left = node.right;
            node.right = tempNode;
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }

        return root;
    }

    public void traversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.remove();
            list.add(node.val);
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
        System.out.println(list);
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
