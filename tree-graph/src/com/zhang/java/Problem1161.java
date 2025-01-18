package com.zhang.java;

import java.util.*;

/**
 * @Date 2025/3/2 08:27
 * @Author zsy
 * @Description 最大层内元素和 类比Problem199、Problem513、Problem515、Problem637、Problem662、Problem993、Problem1302、Problem2583、Problem2641、Problem3157
 * 给你一个二叉树的根节点 root。
 * 设根节点位于二叉树的第 1 层，而根节点的子节点位于第 2 层，依此类推。
 * 请返回层内元素之和 最大 的那几层（可能只有一层）的层号，并返回其中 最小 的那个。
 * <p>
 * 输入：root = [1,7,0,7,-8,null,null]
 * 输出：2
 * 解释：
 * 第 1 层各元素之和为 1，
 * 第 2 层各元素之和为 7 + 0 = 7，
 * 第 3 层各元素之和为 7 + -8 = -1，
 * 所以我们返回第 2 层的层号，它的层内元素之和最大。
 * <p>
 * 输入：root = [989,null,10250,98693,-89388,null,null,null,-32127]
 * 输出：2
 * <p>
 * 树中的节点数在 [1, 10^4]范围内
 * -10^5 <= Node.val <= 10^5
 */
public class Problem1161 {
    public static void main(String[] args) {
        Problem1161 problem1161 = new Problem1161();
        String[] data = {"1", "7", "0", "7", "-8", "null", "null"};
        TreeNode root = problem1161.buildTree(data);
        System.out.println(problem1161.maxLevelSum(root));
        System.out.println(problem1161.maxLevelSum2(root));
    }

    /**
     * dfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int maxLevelSum(TreeNode root) {
        if (root == null) {
            return 0;
        }

        //存储每层元素和的集合
        List<Integer> list = new ArrayList<>();

        //根节点为第0层
        dfs(root, 0, list);

        //最大层内元素和的层数，当存在多个相等的最大层内元素和，则取最小的层数
        int index = 0;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) > list.get(index)) {
                index = i;
            }
        }

        //层数从1开始
        return index + 1;
    }

    /**
     * bfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int maxLevelSum2(TreeNode root) {
        if (root == null) {
            return 0;
        }

        //最大层内元素和
        int maxSum = root.val;
        //最大层内元素和的层数，当存在多个相等的最大层内元素和，则取最小的层数
        int index = 1;
        //bfs当前遍历到的层数
        int level = 1;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            //当前层元素和
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

            if (curSum > maxSum) {
                index = level;
                maxSum = curSum;
            }

            level++;
        }

        return index;
    }

    private void dfs(TreeNode node, int level, List<Integer> list) {
        if (node == null) {
            return;
        }

        //当前节点是第level层第一个元素
        if (level == list.size()) {
            list.add(node.val);
        } else {
            //当前节点不是第level层第一个元素
            list.set(level, list.get(level) + node.val);
        }

        dfs(node.left, level + 1, list);
        dfs(node.right, level + 1, list);
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
