package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/3/20 17:46
 * @Author zsy
 * @Description 二叉树的所有路径 类比Problem112、Problem113、Problem437
 * 给你一个二叉树的根节点 root ，按任意顺序，返回所有从根节点到叶子节点的路径。
 * 叶子节点是指没有子节点的节点。
 * <p>
 * 输入：root = [1,2,3,null,5]
 * 输出：["1->2->5","1->3"]
 * <p>
 * 输入：root = [1]
 * 输出：["1"]
 * <p>
 * 树中节点的数目在范围 [1, 100] 内
 * -100 <= Node.val <= 100
 */
public class Problem257 {
    public static void main(String[] args) {
        Problem257 problem257 = new Problem257();
        String[] data = {"37", "-34", "-48", "null", "-100", "-100", "48",
                "null", "null", "null", "null", "-54", "null", "-71", "-22", "null", "null", "null", "8"};
        TreeNode root = problem257.buildTree(data);
        System.out.println(problem257.binaryTreePaths(root));
        System.out.println(problem257.binaryTreePaths2(root));
    }

    /**
     * dfs
     * 时间复杂度O(n^2)，空间复杂度O(n^2)
     * (每个节点访问一次，每次需要O(n)复制到结果集合中，最坏递归栈深度O(n)，每层需要O(n)存储路径)
     *
     * @param root
     * @return
     */
    public List<String> binaryTreePaths(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<String> result = new ArrayList<>();

        dfs(root, new StringBuilder(), result);

        return result;
    }

    /**
     * bfs
     * 时间复杂度O(n^2)，空间复杂度O(n^2)
     * (每个节点访问一次，每次需要O(n)复制到结果集合中，最坏队列长度O(n)，队列中每个节点需要O(n)存储路径)
     *
     * @param root
     * @return
     */
    public List<String> binaryTreePaths2(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<String> result = new ArrayList<>();
        Queue<Pos> queue = new LinkedList<>();
        queue.offer(new Pos(root, root.val + ""));

        while (!queue.isEmpty()) {
            Pos pos = queue.poll();

            //当前节点为叶节点，路径加入结果集合
            if (pos.node.left == null && pos.node.right == null) {
                result.add(pos.path);
                continue;
            }

            if (pos.node.left != null) {
                queue.offer(new Pos(pos.node.left, pos.path + "->" + pos.node.left.val));
            }

            if (pos.node.right != null) {
                queue.offer(new Pos(pos.node.right, pos.path + "->" + pos.node.right.val));
            }
        }

        return result;
    }

    private void dfs(TreeNode node, StringBuilder path, List<String> result) {
        //当前节点为叶节点，路径加入结果集合
        if (node.left == null && node.right == null) {
            //节点的值可能不止一位，所以需要记录原始长度，用于回溯删除
            int start = path.length();
            path.append(node.val);
            //当前路径复制到结果集合需要O(n)
            result.add(path.toString());
            path.delete(start, path.length());
            return;
        }

        //记录当前长度，用于回溯删除
        int start = path.length();
        path.append(node.val).append("->");

        if (node.left != null) {
            dfs(node.left, path, result);
        }

        if (node.right != null) {
            dfs(node.right, path, result);
        }

        path.delete(start, path.length());
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
        TreeNode node;
        String path;

        Pos(TreeNode node, String path) {
            this.node = node;
            this.path = path;
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
