package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/6/26 8:15
 * @Author zsy
 * @Description 二叉树的前序遍历 类比Problem94、Problem145
 * 给你二叉树的根节点 root ，返回它节点值的 前序 遍历。
 * <p>
 * 输入：root = [1,null,2,3]
 * 输出：[1,2,3]
 * <p>
 * 输入：root = []
 * 输出：[]
 * <p>
 * 输入：root = [1]
 * 输出：[1]
 * <p>
 * 输入：root = [1,2]
 * 输出：[1,2]
 * <p>
 * 输入：root = [1,null,2]
 * 输出：[1,2]
 * <p>
 * 树中节点数目在范围 [0, 100] 内
 * -100 <= Node.val <= 100
 */
public class Problem144 {
    public static void main(String[] args) {
        Problem144 problem144 = new Problem144();
        String[] data = {"1", "null", "2", "3"};
        TreeNode root = problem144.buildTree(data);
        System.out.println(problem144.preorderTraversal(root));
        System.out.println(problem144.preorderTraversal2(root));
    }

    /**
     * 递归先序遍历
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<Integer> list = new ArrayList<>();

        preOrder(root, list);

        return list;
    }

    /**
     * 非递归先序遍历
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public List<Integer> preorderTraversal2(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<Integer> list = new ArrayList<>();
        Deque<TreeNode> stack = new LinkedList<>();
        stack.offerLast(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pollLast();
            list.add(node.val);

            //先将右子树节点压入栈中，再压入左子树节点
            if (node.right != null) {
                stack.offerLast(node.right);
            }
            if (node.left != null) {
                stack.offerLast(node.left);
            }
        }

        return list;
    }

    private void preOrder(TreeNode root, List<Integer> list) {
        if (root == null) {
            return;
        }

        list.add(root.val);

        preOrder(root.left, list);
        preOrder(root.right, list);
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
