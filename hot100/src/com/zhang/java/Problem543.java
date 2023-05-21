package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/6/12 9:43
 * @Author zsy
 * @Description 二叉树的直径 dfs类比Problem104、Problem110、Problem111、Problem124、Problem337、Problem687、Problem1373
 * 给定一棵二叉树，你需要计算它的直径长度。一棵二叉树的直径长度是任意两个结点路径长度中的最大值。
 * 这条路径可能穿过也可能不穿过根结点。
 * <p>
 * 给定二叉树
 * <        1
 * <      / \
 * <     2   3
 * <    / \
 * <   4   5
 * 返回 3, 它的长度是路径 [4,2,1,3] 或者 [5,2,1,3]。
 * <p>
 * 注意：两结点之间的路径长度是以它们之间边的数目表示。
 */
public class Problem543 {
    /**
     * 二叉树的直径长度
     */
    private int diameter = 0;

    public static void main(String[] args) {
        Problem543 problem543 = new Problem543();
        String[] data = {"1", "2", "3", "4", "5"};
        TreeNode root = problem543.buildTree(data);
        System.out.println(problem543.diameterOfBinaryTree(root));
    }

    /**
     * dfs
     * 计算每一个节点的路径长度，更新二叉树的直径，并返回当前节点对父节点的路径长度，用于父节点更新二叉树的直径
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }

        dfs(root);

        return diameter;
    }

    private int dfs(TreeNode root) {
        if (root == null) {
            return 0;
        }

        //当前节点左子树的路径长度
        int leftMax = dfs(root.left);
        //当前节点右子树的路径长度
        int rightMax = dfs(root.right);

        //更新二叉树的直径长度
        diameter = Math.max(diameter, leftMax + rightMax);

        //返回当前节点对父节点的路径长度
        return Math.max(leftMax, rightMax) + 1;
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
