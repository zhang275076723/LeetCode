package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/6/28 17:11
 * @Author zsy
 * @Description 路径总和 II 类比Problem112、Problem257、Problem437 同Offer34
 * 给你二叉树的根节点 root 和一个整数目标和 targetSum ，找出所有 从根节点到叶子节点 路径总和等于给定目标和的路径。
 * 叶子节点 是指没有子节点的节点。
 * <p>
 * 输入：root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
 * 输出：[[5,4,11,2],[5,8,4,5]]
 * <p>
 * 输入：root = [1,2,3], targetSum = 5
 * 输出：[]
 * <p>
 * 输入：root = [1,2], targetSum = 0
 * 输出：[]
 * <p>
 * 树中节点总数在范围 [0, 5000] 内
 * -1000 <= Node.val <= 1000
 * -1000 <= targetSum <= 1000
 */
public class Problem113 {
    public static void main(String[] args) {
        Problem113 problem113 = new Problem113();
        String[] data = {"5", "4", "8", "11", "null", "13", "4", "7", "2", "null", "null", "5", "1"};
        TreeNode root = problem113.buildTree(data);
        System.out.println(problem113.pathSum(root, 22));
        System.out.println(problem113.pathSum2(root, 22));
    }

    /**
     * dfs
     * 时间复杂度O(n^2)，空间复杂度O(n) (将满足条件的路径复制到结果集合需要O(n))
     *
     * @param root
     * @param targetSum
     * @return
     */
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<List<Integer>> result = new ArrayList<>();

        dfs(root, targetSum, 0, new ArrayList<>(), result);

        return result;
    }

    /**
     * bfs，使用哈希表存储当前节点的父节点，用于路径复原
     * 时间复杂度O(n^2)，空间复杂度O(n) (将满足条件的路径复制到结果集合需要O(n))
     *
     * @param root
     * @param targetSum
     * @return
     */
    public List<List<Integer>> pathSum2(TreeNode root, int targetSum) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<List<Integer>> result = new ArrayList<>();
        Queue<Pos> queue = new LinkedList<>();
        //存放当前节点的父节点，用于路径复原
        Map<TreeNode, TreeNode> map = new HashMap<>();
        queue.offer(new Pos(root, root.val));
        map.put(root, null);

        while (!queue.isEmpty()) {
            Pos pos = queue.poll();

            if (pos.node.left == null && pos.node.right == null && pos.pathSum == targetSum) {
                result.add(getPath(pos.node, map));
            }

            if (pos.node.left != null) {
                map.put(pos.node.left, pos.node);
                queue.offer(new Pos(pos.node.left, pos.pathSum + pos.node.left.val));
            }
            if (pos.node.right != null) {
                map.put(pos.node.right, pos.node);
                queue.offer(new Pos(pos.node.right, pos.pathSum + pos.node.right.val));
            }
        }

        return result;
    }

    private void dfs(TreeNode root, int targetSum, int curSum, List<Integer> path, List<List<Integer>> result) {
        if (root == null) {
            return;
        }

        curSum = curSum + root.val;
        path.add(root.val);

        if (root.left == null && root.right == null && curSum == targetSum) {
            //将满足条件的路径复制到结果集合需要O(n)
            result.add(new ArrayList<>(path));
            path.remove(path.size() - 1);
            return;
        }

        dfs(root.left, targetSum, curSum, path, result);
        dfs(root.right, targetSum, curSum, path, result);

        path.remove(path.size() - 1);
    }

    private List<Integer> getPath(TreeNode node, Map<TreeNode, TreeNode> map) {
        List<Integer> list = new ArrayList<>();

        while (node != null) {
            list.add(node.val);
            node = map.get(node);
        }

        //因为是从叶节点找根节点，所以需要反转
        Collections.reverse(list);

        return list;
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
    public static class Pos {
        TreeNode node;

        /**
         * 根节点到当前节点的路径和
         */
        int pathSum;

        Pos(TreeNode node, int pathSum) {
            this.node = node;
            this.pathSum = pathSum;
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
