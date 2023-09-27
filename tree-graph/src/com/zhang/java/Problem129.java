package com.zhang.java;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2022/6/23 8:53
 * @Author zsy
 * @Description 求根节点到叶节点数字之和
 * 给你一个二叉树的根节点 root ，树中每个节点都存放有一个 0 到 9 之间的数字。
 * 每条从根节点到叶节点的路径都代表一个数字：
 * 例如，从根节点到叶节点的路径 1 -> 2 -> 3 表示数字 123 。
 * 计算从根节点到叶节点生成的 所有数字之和 。
 * 叶节点 是指没有子节点的节点。
 * <p>
 * 输入：root = [1,2,3]
 * 输出：25
 * 解释：
 * 从根到叶子节点路径 1->2 代表数字 12
 * 从根到叶子节点路径 1->3 代表数字 13
 * 因此，数字总和 = 12 + 13 = 25
 * <p>
 * 输入：root = [4,9,0,5,1]
 * 输出：1026
 * 解释：
 * 从根到叶子节点路径 4->9->5 代表数字 495
 * 从根到叶子节点路径 4->9->1 代表数字 491
 * 从根到叶子节点路径 4->0 代表数字 40
 * 因此，数字总和 = 495 + 491 + 40 = 1026
 * <p>
 * 树中节点的数目在范围 [1, 1000] 内
 * 0 <= Node.val <= 9
 * 树的深度不超过 10
 */
public class Problem129 {
    private int result = 0;

    public static void main(String[] args) {
        Problem129 problem129 = new Problem129();
        int[] data = {4, 9, 0, 5, 1};
        TreeNode root = problem129.buildTree(data);
        System.out.println(problem129.sumNumbers(root));
        System.out.println(problem129.sumNumbers2(root));
    }

    /**
     * dfs，前序遍历
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int sumNumbers(TreeNode root) {
        if (root == null) {
            return 0;
        }

        preorder(root, 0);

        return result;
    }

    /**
     * bfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int sumNumbers2(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Queue<Pos> queue = new LinkedList<>();
        queue.offer(new Pos(root, 0));

        int result = 0;

        while (!queue.isEmpty()) {
            Pos pos = queue.poll();

            if (pos.node.left == null && pos.node.right == null) {
                result = result + pos.value * 10 + pos.node.val;
            }

            if (pos.node.left != null) {
                queue.offer(new Pos(pos.node.left, pos.value * 10 + pos.node.val));
            }

            if (pos.node.right != null) {
                queue.offer(new Pos(pos.node.right, pos.value * 10 + pos.node.val));
            }
        }

        return result;
    }

    private void preorder(TreeNode root, int value) {
        if (root.left == null && root.right == null) {
            value = value * 10 + root.val;
            result = result + value;
            return;
        }

        value = value * 10 + root.val;

        if (root.left != null) {
            preorder(root.left, value);
        }

        if (root.right != null) {
            preorder(root.right, value);
        }
    }

    private TreeNode buildTree(int[] data) {
        if (data == null || data.length == 0) {
            return null;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode root = new TreeNode(data[0]);
        queue.offer(root);

        int index = 1;

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (index < data.length) {
                TreeNode leftNode = new TreeNode(data[index]);
                index++;
                node.left = leftNode;
                queue.offer(leftNode);
            }
            if (index < data.length) {
                TreeNode rightNode = new TreeNode(data[index]);
                index++;
                node.right = rightNode;
                queue.offer(rightNode);
            }
        }

        return root;
    }

    /**
     * bfs节点
     */
    private static class Pos {
        TreeNode node;
        int value;

        Pos(TreeNode node, int value) {
            this.node = node;
            this.value = value;
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
