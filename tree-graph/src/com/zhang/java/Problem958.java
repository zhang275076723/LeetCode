package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/7/10 9:31
 * @Author zsy
 * @Description 二叉树的完全性检验 完全二叉树类比Problem222、Problem919
 * 给定一个二叉树的 root ，确定它是否是一个 完全二叉树 。
 * 在一个 完全二叉树 中，除了最后一个关卡外，所有关卡都是完全被填满的，并且最后一个关卡中的所有节点都是尽可能靠左的。
 * 它可以包含 1 到 2h 节点之间的最后一级 h 。
 * <p>
 * 输入：root = [1,2,3,4,5,6]
 * 输出：true
 * 解释：最后一层前的每一层都是满的（即，结点值为 {1} 和 {2,3} 的两层），且最后一层中的所有结点（{4,5,6}）都尽可能地向左。
 * <p>
 * 输入：root = [1,2,3,4,5,null,7]
 * 输出：false
 * 解释：值为 7 的结点没有尽可能靠向左侧。
 * <p>
 * 树的结点数在范围  [1, 100] 内。
 * 1 <= Node.val <= 1000
 */
public class Problem958 {
    /**
     * dfs遍历树中最后一个节点的索引(索引从0开始，按照完全二叉树排序)
     */
    private int lastIndex = 0;

    public static void main(String[] args) {
        Problem958 problem958 = new Problem958();
        String[] data = {"1", "2", "3", "4", "5", "null", "7"};
        TreeNode root = problem958.buildTree(data);
        System.out.println(problem958.isCompleteTree(root));
        System.out.println(problem958.isCompleteTree2(root));
        System.out.println(problem958.isCompleteTree3(root));
    }

    /**
     * bfs
     * 当出现第一个null时，如果之后还有未遍历到的非null节点，说明不是完全二叉树
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public boolean isCompleteTree(TreeNode root) {
        if (root == null) {
            return true;
        }

        //遍历到第一个null的标志位，如果之后还有null节点，则说明不是
        boolean flag = false;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();

                //bfs遍历过程中，当第一次出现null节点时，设置标志位为true，
                //如果之后队列中所有节点都为null，则是完全二叉树；如果之后队列出现一个非null节点，则不是完全二叉树
                if (node == null) {
                    flag = true;
                    continue;
                }

                //标志位为true，则说明当前节点之前出现过null节点，不是完全二叉树，返回false
                if (flag) {
                    return false;
                }

                queue.offer(node.left);
                queue.offer(node.right);
            }
        }

        return true;
    }

    /**
     * bfs
     * 如果树中节点个数等于最后一个节点的索引加1(索引从0开始)，则是完全二叉树
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public boolean isCompleteTree2(TreeNode root) {
        if (root == null) {
            return true;
        }

        //树中最后一个节点的索引
        int lastIndex = 0;
        //树中节点的个数
        int count = 0;
        Queue<Pos> queue = new LinkedList<>();
        queue.offer(new Pos(root, 0));

        while (!queue.isEmpty()) {
            Pos pos = queue.poll();
            lastIndex = Math.max(lastIndex, pos.index);
            count++;

            if (pos.node.left != null) {
                queue.offer(new Pos(pos.node.left, pos.index * 2 + 1));
            }
            if (pos.node.right != null) {
                queue.offer(new Pos(pos.node.right, pos.index * 2 + 2));
            }
        }

        return count == lastIndex + 1;
    }

    /**
     * dfs
     * 如果树中节点个数等于最后一个节点的索引加1(索引从0开始)，则是完全二叉树
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public boolean isCompleteTree3(TreeNode root) {
        if (root == null) {
            return true;
        }

        //树中节点的个数
        int count = dfs(root, 0);

        return count == lastIndex + 1;
    }

    private int dfs(TreeNode root, int index) {
        if (root == null) {
            return 0;
        }

        lastIndex = Math.max(lastIndex, index);

        int leftCount = dfs(root.left, index * 2 + 1);
        int rightCount = dfs(root.right, index * 2 + 2);

        return leftCount + rightCount + 1;
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

    /**
     * bfs遍历节点，统计树大小和树节点索引
     */
    private static class Pos {
        TreeNode node;
        int index;

        Pos(TreeNode node, int index) {
            this.node = node;
            this.index = index;
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
