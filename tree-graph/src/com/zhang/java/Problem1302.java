package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/1/28 08:13
 * @Author zsy
 * @Description 层数最深叶子节点的和 类比Problem199、Problem513、Problem515、Problem637、Problem662、Problem993、Problem1161、Problem2583、Problem2641
 * 给你一棵二叉树的根节点 root ，请你返回 层数最深的叶子节点的和 。
 * <p>
 * 输入：root = [1,2,3,4,5,null,6,7,null,null,null,null,8]
 * 输出：15
 * <p>
 * 输入：root = [6,7,8,2,7,1,3,9,null,1,4,null,null,null,5]
 * 输出：19
 * <p>
 * 树中节点数目在范围 [1, 10^4] 之间。
 * 1 <= Node.val <= 100
 */
public class Problem1302 {
    /**
     * dfs最大层节点之和
     */
    private int sum = 0;

    /**
     * dfs最大层数，初始化为-1，表示空树
     */
    private int maxLevel = -1;

    public static void main(String[] args) {
        Problem1302 problem1302 = new Problem1302();
        String[] data = {"1", "2", "3", "4", "5", "null", "6", "7", "null", "null", "null", "null", "8"};
        TreeNode root = problem1302.buildTree(data);
        System.out.println(problem1302.deepestLeavesSum(root));
        System.out.println(problem1302.deepestLeavesSum2(root));
    }

    /**
     * dfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int deepestLeavesSum(TreeNode root) {
        if (root == null) {
            return 0;
        }

        //根节点为第0层
        dfs(root, 0);

        return sum;
    }

    /**
     * bfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int deepestLeavesSum2(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int sum = 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            //当前层节点之和
            int curSum = 0;

            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                curSum = curSum + node.val;

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

            sum = curSum;
        }

        return sum;
    }

    private void dfs(TreeNode node, int level) {
        if (node == null) {
            return;
        }

        //当前层大于最大层，更新dfs的最大层数，计算最大层节点之和
        if (level > maxLevel) {
            sum = node.val;
            maxLevel = level;
        } else if (level == maxLevel) {
            //当前层等于最大层，计算最大层节点之和
            sum = sum + node.val;
        }

        dfs(node.left, level + 1);
        dfs(node.right, level + 1);
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
