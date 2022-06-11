package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/6/11 9:27
 * @Author zsy
 * @Description 把二叉搜索树转换为累加树
 * 给出二叉 搜索 树的根节点，该树的节点值各不相同，请你将其转换为累加树（Greater Sum Tree），
 * 使每个节点 node 的新值等于原树中大于或等于 node.val 的值之和。
 * 提醒一下，二叉搜索树满足下列约束条件：
 * 节点的左子树仅包含键 小于 节点键的节点。
 * 节点的右子树仅包含键 大于 节点键的节点。
 * 左右子树也必须是二叉搜索树。
 * <p>
 * 输入：[4,1,6,0,2,5,7,null,null,null,3,null,null,null,8]
 * 输出：[30,36,21,36,35,26,15,null,null,null,33,null,null,null,8]
 * <p>
 * 输入：root = [0,null,1]
 * 输出：[1,null,1]
 * <p>
 * 输入：root = [1,0,2]
 * 输出：[3,3,2]
 * <p>
 * 输入：root = [3,2,4,1]
 * 输出：[7,9,4,10]
 * <p>
 * 树中的节点数介于 0 和 10^4 之间。
 * 每个节点的值介于 -10^4 和 10^4 之间。
 * 树中的所有值 互不相同 。
 * 给定的树为二叉搜索树。
 */
public class Problem538 {
    /**
     * 先遍历右子树，再遍历根节点，最后遍历左子树的累加值
     */
    private int preSum;

    public static void main(String[] args) {
        Problem538 problem538 = new Problem538();
        String[] data = {"4", "1", "6", "0", "2", "5", "7", "null", "null", "null", "3", "null", "null", "null", "8"};
        TreeNode root = problem538.buildTree(data);
//        root = problem538.convertBST(root);
//        root = problem538.convertBST2(root);
        root = problem538.convertBST3(root);
        problem538.traversal(root);
    }

    /**
     * 反序中序遍历，使用全局遍历，先遍历右子树，再遍历根节点，最后遍历左子树
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public TreeNode convertBST(TreeNode root) {
        if (root == null) {
            return null;
        }

        convertBST(root.right);
        root.val = root.val + preSum;
        preSum = root.val;
        convertBST(root.left);

        return root;
    }

    /**
     * 反序中序遍历，不使用全局遍历，先遍历右子树，再遍历根节点，最后遍历左子树
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public TreeNode convertBST2(TreeNode root) {
        if (root == null) {
            return null;
        }

        reverseInorder(root, 0);

        return root;
    }

    /**
     * 非递归反序中序遍历
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public TreeNode convertBST3(TreeNode root) {
        if (root == null) {
            return null;
        }

        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        int preSum = 0;

        while (!stack.isEmpty() || node != null) {
            while (node != null) {
                stack.push(node);
                node = node.right;
            }

            node = stack.pop();
            node.val = node.val + preSum;
            preSum = node.val;
            node = node.left;
        }

        return root;
    }

    private int reverseInorder(TreeNode root, int preSum) {
        if (root == null) {
            return preSum;
        }

        int value = reverseInorder(root.right, preSum);
        root.val = root.val + value;
        value = reverseInorder(root.left, root.val);

        return value;
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

    private void traversal(TreeNode root) {
        if (root == null) {
            return;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node != null) {
                System.out.println(node.val);
                queue.offer(node.left);
                queue.offer(node.right);
            }
        }
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
