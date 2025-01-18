package com.zhang.java;

import java.util.*;

/**
 * @Date 2025/3/7 08:52
 * @Author zsy
 * @Description 找到具有最小和的树的层数 类比Problem199、Problem513、Problem515、Problem637、Problem662、Problem993、Problem1161、Problem1302、Problem2583、Problem2641
 * 给定一棵二叉树的根 root，其中每个节点有一个值，返回树中 层和最小 的层数（如果相等，返回 最低 的层数）。
 * 注意 树的根节点在第一层，其它任何节点的层数是它到根节点的距离+1。
 * <p>
 * 输入：root = [50,6,2,30,80,7]
 * 输出：2
 * <p>
 * 输入：root = [36,17,10,null,null,24]
 * 输出：3
 * <p>
 * 输入：root = [5,null,5,null,5]
 * 输出：1
 * <p>
 * 树中节点数量的范围是 [1, 10^5]。
 * 1 <= Node.val <= 10^9
 */
public class Problem3157 {
    public static void main(String[] args) {
        Problem3157 problem3157 = new Problem3157();
        String[] data = {"50", "6", "2", "30", "80", "7"};
        TreeNode root = problem3157.buildTree(data);
        System.out.println(problem3157.minimumLevel(root));
        System.out.println(problem3157.minimumLevel2(root));
    }

    /**
     * dfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int minimumLevel(TreeNode root) {
        if (root == null) {
            return 0;
        }

        //存储每层元素和的集合
        List<Integer> list = new ArrayList<>();

        //根节点为第0层
        dfs(root, 0, list);

        //最小层内元素和的层数，当存在多个相等的最小层内元素和，则取最小的层数
        int index = 0;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) < list.get(index)) {
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
    public int minimumLevel2(TreeNode root) {
        if (root == null) {
            return 0;
        }

        //最小层内元素和
        int minSum = root.val;
        //最小层内元素和的层数，当存在多个相等的最小层内元素和，则取最小的层数
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

            if (curSum < minSum) {
                index = level;
                minSum = curSum;
            }

            level++;
        }

        return index;
    }

    private void dfs(TreeNode node, int level, List<Integer> list) {
        if (node == null) {
            return;
        }

        if (level == list.size()) {
            list.add(node.val);
        } else {
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
