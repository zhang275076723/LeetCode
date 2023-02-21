package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/5/1 11:29
 * @Author zsy
 * @Description 二叉树的最大深度 dfs类比Problem110、Problem111、Problem124、Problem337、Problem543、Problem687 同Offer55
 * 给定一个二叉树，找出其最大深度。
 * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
 * 说明: 叶子节点是指没有子节点的节点。
 * <p>
 * 给定二叉树 [3,9,20,null,null,15,7]，返回它的最大深度 3 。
 */
public class Problem104 {
    public static void main(String[] args) {
        Problem104 problem104 = new Problem104();
        String[] data = {"3", "9", "20", "null", "null", "15", "7"};
        TreeNode root = problem104.buildTree(data);
        System.out.println(problem104.maxDepth(root));
        System.out.println(problem104.maxDepth2(root));
        System.out.println(problem104.maxDepth3(root));
        System.out.println(problem104.maxDepth4(root));
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

            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();

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

    /**
     * 非递归dfs和bfs节点
     */
    private static class Pos {
        TreeNode node;
        int depth;

        Pos(TreeNode node, int depth) {
            this.node = node;
            this.depth = depth;
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
