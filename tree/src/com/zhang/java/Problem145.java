package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/6/26 9:16
 * @Author zsy
 * @Description 二叉树的后序遍历 类比Problem94、Problem144、Offer33
 * 给你一棵二叉树的根节点 root ，返回其节点值的 后序遍历 。
 * <p>
 * 输入：root = [1,null,2,3]
 * 输出：[3,2,1]
 * <p>
 * 输入：root = []
 * 输出：[]
 * <p>
 * 输入：root = [1]
 * 输出：[1]
 * <p>
 * 树中节点的数目在范围 [0, 100] 内
 * -100 <= Node.val <= 100
 */
public class Problem145 {
    public static void main(String[] args) {
        Problem145 problem145 = new Problem145();
        String[] data = {"1", "null", "2", "3"};
        TreeNode root = problem145.buildTree(data);
        System.out.println(problem145.postorderTraversal(root));
        System.out.println(problem145.postorderTraversal2(root));
    }

    /**
     * 递归后序遍历
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<Integer> list = new ArrayList<>();

        postOrder(root, list);

        return list;
    }

    /**
     * 非递归后序遍历
     * 将前序遍历(根左右)，转为根右左，反转为左右根，即为后序遍历结果
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public List<Integer> postorderTraversal2(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        //使用LinkedList便于首添加，首添加时间复杂度O(1)
        LinkedList<Integer> list = new LinkedList<>();
        Deque<TreeNode> stack = new LinkedList<>();
        stack.offerLast(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pollLast();

            //首添加到结果集合
            list.addFirst(node.val);

            //先将左子树节点压入栈中
            if (node.left != null) {
                stack.offerLast(node.left);
            }

            //再将右子树节点压入栈中
            if (node.right != null) {
                stack.offerLast(node.right);
            }
        }

        return list;
    }

    private void postOrder(TreeNode root, List<Integer> list) {
        if (root == null) {
            return;
        }

        postOrder(root.left, list);
        postOrder(root.right, list);

        list.add(root.val);
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
