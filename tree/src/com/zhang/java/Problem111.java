package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/9/13 15:44
 * @Author zsy
 * @Description 二叉树的最小深度 类比Problem104、Problem110、Problem124、Problem543
 * 给定一个二叉树，找出其最小深度。
 * 最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
 * 说明：叶子节点是指没有子节点的节点。
 * <p>
 * 输入：root = [3,9,20,null,null,15,7]
 * 输出：2
 * <p>
 * 输入：root = [2,null,3,null,4,null,5,null,6]
 * 输出：5
 * <p>
 * 树中节点数的范围在 [0, 10^5] 内
 * -1000 <= Node.val <= 1000
 */
public class Problem111 {
    public static void main(String[] args) {
        Problem111 problem111 = new Problem111();
        String[] data = {"2", "null", "3", "null", "4", "null", "5", "null", "6"};
        TreeNode root = problem111.buildTree(data);
        System.out.println(problem111.minDepth(root));
        System.out.println(problem111.minDepth2(root));
    }

    /**
     * dfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int minDepth(TreeNode root) {
        //空节点的最小深度为0
        if (root == null) {
            return 0;
        }

        //只有一个节点的最小深度为1
        if (root.left == null && root.right == null) {
            return 1;
        }

        //左节点为空
        if (root.left == null) {
            return minDepth(root.right) + 1;
        }

        //右节点为空
        if (root.right == null) {
            return minDepth(root.left) + 1;
        }

        int leftDepth = minDepth(root.left);
        int rightDepth = minDepth(root.right);

        //左右节点都不为空，取左右子树的最小高度+1
        return Math.min(leftDepth, rightDepth) + 1;
    }

    /**
     * bfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int minDepth2(TreeNode root) {
        //空节点的最小深度为0
        if (root == null) {
            return 0;
        }

        //只有一个节点的最小深度为1
        if (root.left == null && root.right == null) {
            return 1;
        }

        int depth = Integer.MAX_VALUE;
        Queue<Pos> queue = new LinkedList<>();
        queue.offer(new Pos(root, 1));

        while (!queue.isEmpty()) {
            Pos pos = queue.poll();

            //当前节点左右子树都为空，即找到一个叶节点，更新最小深度
            if (pos.node.left == null && pos.node.right == null) {
                depth = Math.min(depth, pos.depth);
                continue;
            }

            if (pos.node.left != null) {
                queue.offer(new Pos(pos.node.left, pos.depth + 1));
            }

            if (pos.node.right != null) {
                queue.offer(new Pos(pos.node.right, pos.depth + 1));
            }
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

    /**
     * bfs节点
     */
    private static class Pos {
        TreeNode node;
        int depth;

        Pos(TreeNode node, int depth) {
            this.node = node;
            this.depth = depth;
        }
    }
}
