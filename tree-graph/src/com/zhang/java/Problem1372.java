package com.zhang.java;

import java.util.*;

/**
 * @Date 2025/3/3 08:11
 * @Author zsy
 * @Description 二叉树中的最长交错路径 dfs类比Problem124、Problem298、Problem337、Problem543、Problem687、Problem968、Problem979、Problem1245、Problem1373、Problem2246、Problem2378
 * 给你一棵以 root 为根的二叉树，二叉树中的交错路径定义如下：
 * 选择二叉树中 任意 节点和一个方向（左或者右）。
 * 如果前进方向为右，那么移动到当前节点的的右子节点，否则移动到它的左子节点。
 * 改变前进方向：左变右或者右变左。
 * 重复第二步和第三步，直到你在树中无法继续移动。
 * 交错路径的长度定义为：访问过的节点数目 - 1（单个节点的路径长度为 0 ）。
 * 请你返回给定树中最长 交错路径 的长度。
 * <p>
 * 输入：root = [1,null,1,1,1,null,null,1,1,null,1,null,null,null,1,null,1]
 * 输出：3
 * 解释：蓝色节点为树中最长交错路径（右 -> 左 -> 右）。
 * <p>
 * 输入：root = [1,1,1,null,1,null,null,1,1,null,1]
 * 输出：4
 * 解释：蓝色节点为树中最长交错路径（左 -> 右 -> 左 -> 右）。
 * <p>
 * 输入：root = [1]
 * 输出：0
 * <p>
 * 每棵树最多有 50000 个节点。
 * 每个节点的值在 [1, 100] 之间。
 */
public class Problem1372 {
    /**
     * 二叉树的最长交错路径
     */
    private int max = 0;

    public static void main(String[] args) {
        Problem1372 problem1372 = new Problem1372();
        String[] data = {"1", "null", "1", "1", "1", "null", "null", "1", "1", "null", "1",
                "null", "null", "null", "1", "null", "1"};
        TreeNode root = problem1372.buildTree(data);
        System.out.println(problem1372.longestZigZag(root));
    }

    /**
     * dfs
     * 计算当前节点左右子节点作为根节点向左右子树移动得到的最长交错路径，更新二叉树的最长交错路径，
     * 返回当前节点对父节点的最长交错路径，用于计算当前节点父节点作为根节点的最长交错路径
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int longestZigZag(TreeNode root) {
        if (root == null) {
            return 0;
        }

        //根节点没有父节点，假设根节点的父节点向左子树移动到根节点
        dfs(root, true);

        return max;
    }

    /**
     * 当前节点作为根节点是否向左子树移动得到的最长交错路径
     *
     * @param node
     * @param isLeft
     * @return
     */
    private int dfs(TreeNode node, boolean isLeft) {
        if (node == null) {
            return 0;
        }

        //当前节点作为根节点向左子树移动得到的最长交错路径
        int leftMax = dfs(node.left, true);
        //当前节点作为根节点向右子树移动得到的最长交错路径
        int rightMax = dfs(node.right, false);

        //更新二叉树的最长交错路径
        max = Math.max(max, Math.max(leftMax, rightMax));

        //当前节点的父节点向左子树移动到当前节点，则当前节点只能向右子树移动
        if (isLeft) {
            return rightMax + 1;
        } else {
            //当前节点的父节点向右子树移动到当前节点，则当前节点只能向左子树移动
            return leftMax + 1;
        }
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
