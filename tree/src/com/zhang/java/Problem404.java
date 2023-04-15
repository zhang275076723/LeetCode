package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/4/15 08:18
 * @Author zsy
 * @Description 左叶子之和 类比Problem513
 * 给定二叉树的根节点 root ，返回所有左叶子之和。
 * <p>
 * 输入: root = [3,9,20,null,null,15,7]
 * 输出: 24
 * 解释: 在这个二叉树中，有两个左叶子，分别是 9 和 15，所以返回 24
 * <p>
 * 输入: root = [1]
 * 输出: 0
 * <p>
 * 节点数在 [1, 1000] 范围内
 * -1000 <= Node.val <= 1000
 */
public class Problem404 {
    public static void main(String[] args) {
        Problem404 problem404 = new Problem404();
        String[] data = {"3", "9", "20", "null", "null", "15", "7"};
        TreeNode root = problem404.buildTree(data);
        System.out.println(problem404.sumOfLeftLeaves(root));
        System.out.println(problem404.sumOfLeftLeaves2(root));
    }

    /**
     * dfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int sumOfLeftLeaves(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return dfs(root, root);
    }

    /**
     * bfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int sumOfLeftLeaves2(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int count = 0;
        Queue<Pos> queue = new LinkedList<>();
        queue.offer(new Pos(root, root));

        while (!queue.isEmpty()) {
            Pos pos = queue.poll();
            //当前节点为叶节点，并且是左叶子结点，则左叶子节点值相加
            if (pos.node.left == null && pos.node.right == null && pos.pre.left == pos.node) {
                count = count + pos.node.val;
            }
            //左右子树入队
            if (pos.node.left != null) {
                queue.offer(new Pos(pos.node.left, pos.node));
            }
            if (pos.node.right != null) {
                queue.offer(new Pos(pos.node.right, pos.node));
            }
        }

        return count;
    }

    /**
     * @param root
     * @param pre  root节点的前驱节点
     * @return
     */
    private int dfs(TreeNode root, TreeNode pre) {
        if (root == null) {
            return 0;
        }

        int count = 0;

        //当前节点为叶节点，并且是左叶子结点，则左叶子节点值相加
        if (root.left == null && root.right == null && pre.left == root) {
            count = count + root.val;
        }

        count = count + dfs(root.left, root);
        count = count + dfs(root.right, root);

        return count;
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
        //当期节点
        TreeNode node;
        //bfs遍历过程中当前节点的前驱节点
        TreeNode pre;

        Pos(TreeNode node, TreeNode pre) {
            this.node = node;
            this.pre = pre;
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
