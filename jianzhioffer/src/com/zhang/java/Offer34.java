package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

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
        TreeNode node1 = new TreeNode(5);
        TreeNode node2 = new TreeNode(4);
        TreeNode node3 = new TreeNode(8);
        TreeNode node4 = new TreeNode(11);
        TreeNode node5 = new TreeNode(13);
        TreeNode node6 = new TreeNode(4);
        TreeNode node7 = new TreeNode(7);
        TreeNode node8 = new TreeNode(2);
        TreeNode node9 = new TreeNode(5);
        TreeNode node10 = new TreeNode(1);
        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        node3.left = node5;
        node3.right = node6;
        node4.left = node7;
        node4.right = node8;
        node6.left = node9;
        node6.right = node10;
        System.out.println(offer34.pathSum(node1, 22));
    }

    public List<List<Integer>> pathSum(TreeNode root, int target) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<List<Integer>> result = new ArrayList<>();
        dfs(root, target, root.val, new ArrayList<>(), result);
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
        //满足条件，将path加入result集合中
        if (root.left == null && root.right == null && curSum == target) {
            path.add(root.val);
            result.add(new ArrayList<>(path));
            path.remove(path.size() - 1);
            return;
        }

        path.add(root.val);
        if (root.left != null) {
            dfs(root.left, target, curSum + root.left.val, path, result);
        }
        if (root.right != null) {
            dfs(root.right, target, curSum + root.right.val, path, result);
        }
        path.remove(path.size() - 1);
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
