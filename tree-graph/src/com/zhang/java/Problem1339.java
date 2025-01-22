package com.zhang.java;

import java.util.*;

/**
 * @Date 2025/3/11 08:28
 * @Author zsy
 * @Description 分裂二叉树的最大乘积 类比Problem450、Problem1110、Problem2049
 * 给你一棵二叉树，它的根为 root 。请你删除 1 条边，使二叉树分裂成两棵子树，且它们子树和的乘积尽可能大。
 * 由于答案可能会很大，请你将结果对 10^9 + 7 取模后再返回。
 * <p>
 * 输入：root = [1,2,3,4,5,6]
 * 输出：110
 * 解释：删除红色的边，得到 2 棵子树，和分别为 11 和 10 。它们的乘积是 110 （11*10）
 * <p>
 * 输入：root = [1,null,2,3,4,null,null,5,6]
 * 输出：90
 * 解释：移除红色的边，得到 2 棵子树，和分别是 15 和 6 。它们的乘积为 90 （15*6）
 * <p>
 * 输入：root = [2,3,9,10,7,8,6,5,4,11,1]
 * 输出：1025
 * <p>
 * 输入：root = [1,1]
 * 输出：1
 * <p>
 * 每棵树最多有 50000 个节点，且至少有 2 个节点。
 * 每个节点的值在 [1, 10000] 之间。
 */
public class Problem1339 {
    private final int MOD = (int) 1e9 + 7;

    /**
     * 树中元素之和
     */
    private int sum = 0;

    /**
     * 最接近树中元素之和sum一半的子树元素之和
     */
    private int bestSum = 0;

    public static void main(String[] args) {
        Problem1339 problem1339 = new Problem1339();
        String[] data = {"1", "2", "3", "4", "5", "6"};
        TreeNode root = problem1339.buildTree(data);
        System.out.println(problem1339.maxProduct(root));
    }

    /**
     * dfs
     * 二叉树分裂成两棵子树的最大乘积=子树元素之和*(树中元素之和-子树元素之和*)
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int maxProduct(TreeNode root) {
        if (root == null) {
            return 0;
        }

        dfs(root);
        dfs2(root);

        //注意：最大乘积如果在dfs2中计算再取模，可能不是最大的，解决方法1可以使用long，
        //解决方法2根据均值不等式，最接近树中元素之和sum一半的子树元素之和bestSum，对应的两颗子树的乘积bestSum*(sum-bestSum)最大
        return (int) ((long) bestSum * (sum - bestSum) % MOD);
    }

    private void dfs(TreeNode root) {
        if (root == null) {
            return;
        }

        sum = sum + root.val;

        dfs(root.left);
        dfs(root.right);
    }

    private int dfs2(TreeNode root) {
        if (root == null) {
            return 0;
        }

        //当前节点为根节点的树中元素之和
        int curSum = root.val;

        curSum = curSum + dfs2(root.left);
        curSum = curSum + dfs2(root.right);

        //更新最接近树中元素之和sum一半的子树元素之和
        if (Math.abs(curSum - sum / 2) < Math.abs(bestSum - sum / 2)) {
            bestSum = curSum;
        }

        return curSum;
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
