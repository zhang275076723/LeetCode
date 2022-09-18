package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/4/3 9:56
 * @Author zsy
 * @Description 二叉树的深度 类比Offer55_2 同Problem104
 * 输入一棵二叉树的根节点，求该树的深度。
 * 从根节点到叶节点依次经过的节点（含根、叶节点）形成树的一条路径，最长路径的长度为树的深度。
 * <p>
 * 给定二叉树 [3,9,20,null,null,15,7]，返回它的最大深度 3 。
 * <p>
 * 节点总数 <= 10000
 */
public class Offer55 {
    public static void main(String[] args) {
        Offer55 offer55 = new Offer55();
        String[] data = {"3", "9", "20", "null", "null", "15", "7"};
        TreeNode root = offer55.buildTree(data);
        System.out.println(offer55.maxDepth(root));
        System.out.println(offer55.maxDepth2(root));
        System.out.println(offer55.maxDepth3(root));
        System.out.println(offer55.maxDepth4(root));
    }

    /**
     * dfs，递归前序遍历
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);

        return Math.max(leftDepth, rightDepth) + 1;
    }

    /**
     * dfs，非递归前序遍历
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int maxDepth2(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int depth = 0;
        Deque<Pos> stack = new LinkedList<>();
        stack.push(new Pos(root, 1));

        while (!stack.isEmpty()) {
            Pos pos = stack.pop();
            depth = Math.max(depth, pos.depth);

            if (pos.node.right != null) {
                stack.push(new Pos(pos.node.right, pos.depth + 1));
            }
            if (pos.node.left != null) {
                stack.push(new Pos(pos.node.left, pos.depth + 1));
            }
        }

        return depth;
    }

    /**
     * bfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int maxDepth3(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int depth = 1;
        Queue<Pos> queue = new LinkedList<>();
        queue.offer(new Pos(root, 1));

        while (!queue.isEmpty()) {
            Pos pos = queue.poll();
            depth = Math.max(depth, pos.depth);

            if (pos.node.left != null) {
                queue.offer(new Pos(pos.node.left, pos.depth + 1));
            }

            if (pos.node.right != null) {
                queue.offer(new Pos(pos.node.right, pos.depth + 1));
            }
        }

        return depth;
    }

    /**
     * bfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int maxDepth4(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int depth = 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();

            while (size > 0) {
                TreeNode node = queue.poll();
                size--;

                if (node.left != null) {
                    queue.offer(node.left);
                }

                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

            depth++;
        }

        return depth;
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
                String leftNodeValue = list.remove(0);
                if (!"null".equals(leftNodeValue)) {
                    TreeNode leftNode = new TreeNode(Integer.parseInt(leftNodeValue));
                    node.left = leftNode;
                    queue.offer(leftNode);
                }
            }
            if (!list.isEmpty()) {
                String rightNodeValue = list.remove(0);
                if (!"null".equals(rightNodeValue)) {
                    TreeNode rightNode = new TreeNode(Integer.parseInt(rightNodeValue));
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

        TreeNode(int x) {
            val = x;
        }
    }

    /**
     * 非递归dfs和bfs节点
     */
    public static class Pos {
        TreeNode node;
        int depth;

        Pos(TreeNode node, int depth) {
            this.node = node;
            this.depth = depth;
        }
    }
}
