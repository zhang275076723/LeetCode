package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/3/20 15:30
 * @Author zsy
 * @Description 对称的二叉树 类比Problem100、Problem101、Problem226、Problem572、Problem951、Problem1367、Offer26、Offer27、Offer55、Offer55_2 同Problem101
 * 请实现一个函数，用来判断一棵二叉树是不是对称的。如果一棵二叉树和它的镜像一样，那么它是对称的。
 * <p>
 * 输入：root = [1,2,2,3,4,4,3]
 * 输出：true
 * <p>
 * 输入：root = [1,2,2,null,3,null,3]
 * 输出：false
 * <p>
 * 0 <= 节点个数 <= 1000
 */
public class Offer28 {
    public static void main(String[] args) {
        Offer28 offer28 = new Offer28();
        String[] data = {"1", "2", "2", "null", "3", "null", "3"};
        TreeNode root = offer28.buildTree(data);
        System.out.println(offer28.isSymmetric(root));
//        System.out.println(offer28.isSymmetric2(root));
    }

    /**
     * dfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }

        return dfs(root.left, root.right);
    }

    /**
     * bfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public boolean isSymmetric2(TreeNode root) {
        if (root == null) {
            return true;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root.left);
        queue.offer(root.right);

        while (!queue.isEmpty()) {
            TreeNode leftNode = queue.poll();
            TreeNode rightNode = queue.poll();

            //当前节点左右子树都为空，则对称，继续下次循环
            if (leftNode == null && rightNode == null) {
                continue;
            }

            //当前节点只有一个子树为空或左右节点值不一样，则不对称，返回false
            if (leftNode == null || rightNode == null || leftNode.val != rightNode.val) {
                return false;
            }

            //按照对称的顺序添加子节点
            queue.offer(leftNode.left);
            queue.offer(rightNode.right);
            queue.offer(leftNode.right);
            queue.offer(rightNode.left);
        }

        return true;
    }

    /**
     * 判断以node1为根节点的树和以node2为根节点的树是否对称
     *
     * @param node1
     * @param node2
     * @return
     */
    private boolean dfs(TreeNode node1, TreeNode node2) {
        //node1和node2都为空，则是对称二叉树，返回true
        if (node1 == null && node2 == null) {
            return true;
        }

        //node1和node2只有一个不为空，或者节点值不相等，则不是对称二叉树，返回false
        if (node1 == null || node2 == null || node1.val != node2.val) {
            return false;
        }

        //递归判断node1的左子树和node2的右子树是否是对称二叉树，node1的右子树和node2的左子树是否是对称二叉树
        return dfs(node1.left, node2.right) && dfs(node1.right, node2.left);
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

        TreeNode(int x) {
            val = x;
        }
    }
}
