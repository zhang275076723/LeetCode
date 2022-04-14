package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/4/3 9:56
 * @Author zsy
 * @Description 输入一棵二叉树的根节点，求该树的深度。
 * 从根节点到叶节点依次经过的节点（含根、叶节点）形成树的一条路径，最长路径的长度为树的深度。
 * <p>
 * 给定二叉树 [3,9,20,null,null,15,7]，返回它的最大深度 3 。
 */
public class Offer55 {
    public static void main(String[] args) {
        Offer55 offer55 = new Offer55();
        String[] data = {"3", "9", "20", "null", "null", "15", "7"};
        TreeNode root = offer55.buildTree(data);
        System.out.println(offer55.maxDepth(root));
        System.out.println(offer55.maxDepth2(root));
        System.out.println(offer55.maxDepth3(root));
    }

    /**
     * 递归，深度优先求树的深度，时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        return Math.max(leftDepth, rightDepth) + 1;
    }

    /**
     * 非递归，深度优先求树的深度，时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int maxDepth2(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Stack<Pair> stack = new Stack<>();
        stack.push(new Pair(root, 1));
        int depth = 0;

        while (!stack.isEmpty()) {
            Pair pair = stack.pop();
            depth = Math.max(pair.depth, depth);
            if (pair.node.left != null) {
                stack.push(new Pair(pair.node.left, pair.depth + 1));
            }
            if (pair.node.right != null) {
                stack.push(new Pair(pair.node.right, pair.depth + 1));
            }
        }

        return depth;
    }

    /**
     * 非递归，层次遍历求树的深度，时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int maxDepth3(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int depth = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            depth++;
        }

        return depth;
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

    /**
     * 用于非递归，深度优先求树的深度
     */
    public static class Pair {
        TreeNode node;
        int depth;

        Pair(TreeNode node, int depth) {
            this.node = node;
            this.depth = depth;
        }
    }
}
