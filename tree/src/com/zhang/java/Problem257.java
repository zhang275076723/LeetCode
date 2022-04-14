package com.zhang.java;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Date 2022/3/20 17:46
 * @Author zsy
 * @Description 给你一个二叉树的根节点 root ，按任意顺序，返回所有从根节点到叶子节点的路径。
 * 叶子节点是指没有子节点的节点。
 * <p>
 * 输入：root = [1,2,3,null,5]
 * 输出：["1->2->5","1->3"]
 * <p>
 * 输入：root = [1]
 * 输出：["1"]
 */
public class Problem257 {
    public static void main(String[] args) {
        Problem257 problem257 = new Problem257();
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(5);
        node1.left = node2;
        node1.right = node3;
        node2.right = node4;
        System.out.println(problem257.binaryTreePaths(node1));
        System.out.println(problem257.binaryTreePaths2(node1));
    }

    /**
     * 回溯法，时间复杂度O(n^2)，空间复杂度O(n^2)
     *
     * @param root
     * @return
     */
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> result = new ArrayList<>();
        dfs(root, result, new StringBuilder());
        return result;
    }

    /**
     * 队列，一个队列存放节点，一个队列存放路径，时间复杂度O(n^2)，空间复杂度O(n^2)
     *
     * @param root
     * @return
     */
    public List<String> binaryTreePaths2(TreeNode root) {
        List<String> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> nodeQueue = new LinkedList<>();
        Queue<StringBuilder> pathQueue = new LinkedList<>();
        nodeQueue.add(root);
        pathQueue.add(new StringBuilder(Integer.toString(root.val)));

        while (!nodeQueue.isEmpty()) {
            TreeNode node = nodeQueue.remove();
            StringBuilder path = pathQueue.remove();

            if (node.left == null && node.right == null) {
                result.add(path.toString());
            } else {
                if (node.left != null) {
                    nodeQueue.add(node.left);
                    pathQueue.add(new StringBuilder(path).append("->").append(node.left.val));
                }
                if (node.right != null) {
                    nodeQueue.add(node.right);
                    pathQueue.add(new StringBuilder(path).append("->").append(node.right.val));
                }
            }
        }

        return result;
    }

    public void dfs(TreeNode root, List<String> result, StringBuilder path) {
        if (root == null) {
            return;
        }

        //记录当前长度，用于回溯删除
        int start = path.length();

        path.append(root.val);
        if (root.left == null && root.right == null) {
            result.add(path.toString());
        }
        path.append("->");

        dfs(root.left, result, path);
        dfs(root.right, result, path);

        path.delete(start, path.length());
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
