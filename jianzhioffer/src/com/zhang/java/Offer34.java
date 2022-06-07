package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/3/22 18:13
 * @Author zsy
 * @Description 给你二叉树的根节点 root 和一个整数目标和 targetSum ，
 * 找出所有 从根节点到叶子节点 路径总和等于给定目标和的路径。叶子节点是指没有子节点的节点。
 * 注意：节点val值可能为负，targetSum可能为负
 * <p>
 * 输入：root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
 * 输出：[[5,4,11,2],[5,8,4,5]]
 * <p>
 * 输入：root = [1,2,3], targetSum = 5
 * 输出：[]
 * <p>
 * 输入：root = [1,2], targetSum = 0
 * 输出：[]
 */
public class Offer34 {
    public static void main(String[] args) {
        Offer34 offer34 = new Offer34();
        String[] data = {"5", "4", "8", "11", "null", "13", "4", "7", "2", "null", "null", "5", "1"};
        TreeNode root = offer34.buildTree(data);
        System.out.println(offer34.pathSum(root, 22));
    }

    /**
     * dfs
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param root
     * @param target
     * @return
     */
    public List<List<Integer>> pathSum(TreeNode root, int target) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<List<Integer>> result = new ArrayList<>();
        dfs(root, target, 0, new ArrayList<>(), result);
        return result;
    }

    /**
     * 回溯，因为可能存在负数的情况，所以不能限界剪枝
     *
     * @param root   当前根节点
     * @param target 目标路径和
     * @param curSum 当前路径和
     * @param path   当前路径
     * @param result 满足要求的所有路径
     */
    public void dfs(TreeNode root, int target, int curSum, List<Integer> path, List<List<Integer>> result) {
        //当前节点为叶节点，并且路径之和等于target，将path加入result集合中
        if (root.left == null && root.right == null && curSum + root.val == target) {
            path.add(root.val);
            result.add(new ArrayList<>(path));
            path.remove(path.size() - 1);
            return;
        }

        path.add(root.val);
        if (root.left != null) {
            dfs(root.left, target, curSum + root.val, path, result);
        }
        if (root.right != null) {
            dfs(root.right, target, curSum + root.val, path, result);
        }
        path.remove(path.size() - 1);
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
                if (!list.isEmpty()) {
                    String rightValue = list.remove(0);
                    if (!"null".equals(rightValue)) {
                        TreeNode rightNode = new TreeNode(Integer.parseInt(rightValue));
                        node.right = rightNode;
                        queue.offer(rightNode);
                    }
                }
            }
        }

        return root;
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }
}
