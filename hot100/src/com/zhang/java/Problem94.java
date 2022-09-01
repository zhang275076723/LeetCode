package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/4/29 15:57
 * @Author zsy
 * @Description 二叉树的中序遍历 类比Problem144、Problem145 字节面试题
 * 给定一个二叉树的根节点 root ，返回 它的 中序 遍历 。
 * <p>
 * 输入：root = [1,null,2,3]
 * 输出：[1,3,2]
 * <p>
 * 输入：root = []
 * 输出：[]
 * <p>
 * 输入：root = [1]
 * 输出：[1]
 * <p>
 * 树中节点数目在范围 [0, 100] 内
 * -100 <= Node.val <= 100
 */
public class Problem94 {
    public static void main(String[] args) {
        Problem94 problem94 = new Problem94();
        String[] data = {"1", "null", "2", "3"};
        TreeNode root = problem94.buildTree(data);
        System.out.println(problem94.inorderTraversal(root));
        System.out.println(problem94.inorderTraversal2(root));
    }

    /**
     * 递归中序遍历
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<Integer> result = new ArrayList<>();

        inorder(root, result);

        return result;
    }

    /**
     * 非递归中序遍历
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal2(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode node = root;

        while (!stack.isEmpty() || node != null) {
            while (node != null) {
                stack.offerLast(node);
                node = node.left;
            }

            node = stack.pollLast();
            result.add(node.val);
            node = node.right;
        }

        return result;
    }

    private void inorder(TreeNode root, List<Integer> result) {
        if (root == null) {
            return;
        }

        inorder(root.left, result);
        result.add(root.val);
        inorder(root.right, result);
    }

    /**
     * 建树
     *
     * @param data
     * @return
     */
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
                String leftNodeValue = list.remove(0);
                if (!"null".equals(leftNodeValue)) {
                    TreeNode leftNode = new TreeNode(Integer.parseInt(leftNodeValue));
                    node.left = leftNode;
                    queue.offer(leftNode);
                }
            }
            if (!list.isEmpty()) {
                String rightNodeValue = list.remove(0);
                if (!"null".equals(rightNodeValue)) {
                    TreeNode rightNode = new TreeNode(Integer.parseInt(rightNodeValue));
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
