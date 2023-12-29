package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/4/2 16:28
 * @Author zsy
 * @Description 二叉搜索树的第k大节点 类比Problem173、Problem230、Problem538、Problem653、Offer68、Offer68_2
 * 给定一棵二叉搜索树，请找出其中第 k 大的节点的值。
 * <p>
 * 输入: root = [3,1,4,null,2], k = 1
 * 输出: 4
 * <p>
 * 输入: root = [5,3,6,2,4,null,null,1], k = 3
 * 输出: 4
 * <p>
 * 1 ≤ k ≤ 二叉搜索树元素个数
 */
public class Offer54 {
    /**
     * 递归逆序中序遍历中目前所遍历到的节点数量
     */
    private int count = 0;

    /**
     * 递归逆序中序遍历中二叉搜索树的第k大节点值
     */
    private int maxKValue;

    public static void main(String[] args) {
        Offer54 offer54 = new Offer54();
        String[] data = {"5", "3", "6", "2", "4", "null", "null", "1"};
        TreeNode root = offer54.buildTree(data);
        System.out.println(offer54.kthLargest(root, 3));
        System.out.println(offer54.kthLargest2(root, 3));
    }

    /**
     * 递归逆序中序遍历
     * 先遍历右子树，再遍历根节点，最后遍历左子树
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @param k
     * @return
     */
    public int kthLargest(TreeNode root, int k) {
        if (root == null) {
            return -1;
        }

        reverseInorder(root, k);

        return maxKValue;
    }

    /**
     * 非递归逆序中序遍历
     * 先遍历右子树，再遍历根节点，最后遍历左子树
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @param k
     * @return
     */
    public int kthLargest2(TreeNode root, int k) {
        if (root == null) {
            return -1;
        }

        int count = 0;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;

        while (!stack.isEmpty() || node != null) {
            while (node != null) {
                stack.push(node);
                node = node.right;
            }

            node = stack.pop();
            count++;

            if (count == k) {
                return node.val;
            }

            node = node.left;
        }

        //没有找到第k大节点，返回-1
        return -1;
    }

    private void reverseInorder(TreeNode root, int k) {
        if (root == null) {
            return;
        }

        //已经找到第k大节点，直接返回
        if (count == k) {
            return;
        }

        reverseInorder(root.right, k);

        count++;

        if (count == k) {
            maxKValue = root.val;
            return;
        }

        reverseInorder(root.left, k);
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
