package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/4/15 08:18
 * @Author zsy
 * @Description 左叶子之和 类比Problem129、Problem1448
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
     * 左叶子：当前节点是叶节点，并且当前节点存在父节点，当前节点是父节点的左子节点
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int sumOfLeftLeaves(TreeNode root) {
        if (root == null) {
            return 0;
        }

        //根节点的父节点假定为root，不能为null，避免空指针异常
        return dfs(root, root);
    }

    /**
     * bfs
     * 左叶子：当前节点是叶节点，并且当前节点存在父节点，当前节点是父节点的左子节点
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int sumOfLeftLeaves2(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int sum = 0;
        Queue<Pos> queue = new LinkedList<>();
        //根节点的前驱节点为root，避免pos.pre.left空指针异常
        queue.offer(new Pos(root, root));

        while (!queue.isEmpty()) {
            Pos pos = queue.poll();

            //当前节点为叶节点，并且是左叶子结点，则左叶子节点值相加
            if (pos.node.left == null && pos.node.right == null && pos.pre.left == pos.node) {
                sum = sum + pos.node.val;
            }

            if (pos.node.left != null) {
                queue.offer(new Pos(pos.node.left, pos.node));
            }
            if (pos.node.right != null) {
                queue.offer(new Pos(pos.node.right, pos.node));
            }
        }

        return sum;
    }

    /**
     * @param root
     * @param pre  root节点的父节点
     * @return
     */
    private int dfs(TreeNode root, TreeNode pre) {
        if (root == null) {
            return 0;
        }

        int sum = 0;

        //当前节点为叶节点，并且是左叶子结点，则左叶子节点值相加
        if (root.left == null && root.right == null && pre.left == root) {
            sum = sum + root.val;
        }

        sum = sum + dfs(root.left, root);
        sum = sum + dfs(root.right, root);

        return sum;
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
        //当前节点
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
