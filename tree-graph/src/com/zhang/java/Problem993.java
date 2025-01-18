package com.zhang.java;

import java.util.*;

/**
 * @Date 2025/3/4 08:00
 * @Author zsy
 * @Description 二叉树的堂兄弟节点 类比Problem2641 类比Problem199、Problem513、Problem515、Problem637、Problem662、Problem1161、Problem1302、Problem2641
 * 在二叉树中，根节点位于深度 0 处，每个深度为 k 的节点的子节点位于深度 k+1 处。
 * 如果二叉树的两个节点深度相同，但 父节点不同 ，则它们是一对堂兄弟节点。
 * 我们给出了具有唯一值的二叉树的根节点 root ，以及树中两个不同节点的值 x 和 y 。
 * 只有与值 x 和 y 对应的节点是堂兄弟节点时，才返回 true 。否则，返回 false。
 * <p>
 * 输入：root = [1,2,3,4], x = 4, y = 3
 * 输出：false
 * <p>
 * 输入：root = [1,2,3,null,4,null,5], x = 5, y = 4
 * 输出：true
 * <p>
 * 输入：root = [1,2,3,null,4], x = 2, y = 3
 * 输出：false
 * <p>
 * 二叉树的节点数介于 2 到 100 之间。
 * 每个节点的值都是唯一的、范围为 1 到 100 的整数。
 */
public class Problem993 {
    /**
     * 节点x的层数
     */
    private int xLevel = -1;

    /**
     * 节点y的层数
     */
    private int yLevel = -1;

    /**
     * 节点x的父节点
     */
    private TreeNode xParent = null;

    /**
     * 节点y的父节点
     */
    private TreeNode yParent = null;

    public static void main(String[] args) {
        Problem993 problem993 = new Problem993();
        String[] data = {"1", "2", "3", "null", "4", "null", "5"};
        int x = 5;
        int y = 4;
        TreeNode root = problem993.buildTree(data);
        System.out.println(problem993.isCousins(root, x, y));
        System.out.println(problem993.isCousins2(root, x, y));
    }

    /**
     * dfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @param x
     * @param y
     * @return
     */
    public boolean isCousins(TreeNode root, int x, int y) {
        if (root == null) {
            return false;
        }

        dfs(root, null, 0, x, y);

        return xParent != yParent && xLevel == yLevel;
    }

    /**
     * bfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @param x
     * @param y
     * @return
     */
    public boolean isCousins2(TreeNode root, int x, int y) {
        if (root == null) {
            return false;
        }

        //节点x的层数
        int xLevel = -1;
        //节yx的层数
        int yLevel = -1;
        //节点x的父节点
        TreeNode xParent = null;
        //节点y的父节点
        TreeNode yParent = null;
        //dfs当前的层数
        int level = 0;

        Queue<Pos> queue = new LinkedList<>();
        queue.offer(new Pos(root, null));

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                Pos pos = queue.poll();

                if (pos.node.val == x) {
                    xLevel = level;
                    xParent = pos.parent;
                } else if (pos.node.val == y) {
                    yLevel = level;
                    yParent = pos.parent;
                }

                if (pos.node.left != null) {
                    queue.offer(new Pos(pos.node.left, pos.node));
                }
                if (pos.node.right != null) {
                    queue.offer(new Pos(pos.node.right, pos.node));
                }
            }

            level++;
        }

        return xParent != yParent && xLevel == yLevel;
    }

    private void dfs(TreeNode node, TreeNode parent, int level, int x, int y) {
        if (node == null) {
            return;
        }

        //已经找到节点x和节点y，直接返回
        if (xLevel != -1 && yLevel != -1) {
            return;
        }

        if (node.val == x) {
            xLevel = level;
            xParent = parent;
        } else if (node.val == y) {
            yLevel = level;
            yParent = parent;
        }

        dfs(node.left, node, level + 1, x, y);
        dfs(node.right, node, level + 1, x, y);
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
     * bfs节点
     */
    private static class Pos {
        private TreeNode node;
        private TreeNode parent;

        public Pos(TreeNode node, TreeNode parent) {
            this.node = node;
            this.parent = parent;
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
