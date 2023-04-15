package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/4/15 08:58
 * @Author zsy
 * @Description 二叉树中所有距离为 K 的结点 字节面试题 保存父节点类比Problem113、Problem126、Offer34
 * 给定一个二叉树（具有根结点 root）， 一个目标结点 target ，和一个整数值 k 。
 * 返回到目标结点 target 距离为 k 的所有结点的值的列表。 答案可以以 任何顺序 返回。
 * <p>
 * 输入：root = [3,5,1,6,2,0,8,null,null,7,4], target = 5, k = 2
 * 输出：[7,4,1]
 * 解释：所求结点为与目标结点（值为 5）距离为 2 的结点，值分别为 7，4，以及 1
 * <p>
 * 输入: root = [1], target = 1, k = 3
 * 输出: []
 * <p>
 * 节点数在 [1, 500] 范围内
 * 0 <= Node.val <= 500
 * Node.val 中所有值 不同
 * 目标结点 target 是树上的结点。
 * 0 <= k <= 1000
 */
public class Problem863 {
    public static void main(String[] args) {
        Problem863 problem863 = new Problem863();
        String[] data = {"3", "5", "1", "6", "2", "0", "8", "null", "null", "7", "4"};
        TreeNode root = problem863.buildTree(data);
        TreeNode target = root.left;
        int k = 2;
        System.out.println(problem863.distanceK(root, target, k));
    }

    /**
     * dfs
     * 哈希表保存当前节点和父节点之间的映射关系，通过哈希表可以实现从当前节点向父节点查找，
     * 从target节点往子节点和父节点找距离为k的节点，使用前驱节点避免重复查找
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @param target
     * @param k
     * @return
     */
    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<Integer> list = new ArrayList<>();
        //存放当前节点的父节点，用于路径复原
        Map<TreeNode, TreeNode> map = new HashMap<>();
        //初始化，root的父节点为空
        map.put(root, null);

        //得到每个节点的父节点map
        buildMap(root, map);

        //从target节点开始往子节点和父节点找距离target节点为k的节点值加入list中
        dfs(target, null, 0, k, map, list);

        return list;
    }

    /**
     * 得到每个节点的父节点map
     *
     * @param root
     * @param map
     */
    private void buildMap(TreeNode root, Map<TreeNode, TreeNode> map) {
        if (root == null) {
            return;
        }

        if (root.left != null) {
            map.put(root.left, root);
            buildMap(root.left, map);
        }

        if (root.right != null) {
            map.put(root.right, root);
            buildMap(root.right, map);
        }
    }

    /**
     * 从root节点开始往子节点和父节点找距离root节点为k的节点值加入list中
     *
     * @param root
     * @param pre      当前遍历到root节点的前驱节点，避免重复查找
     * @param distance
     * @param k
     * @param map
     * @param list
     */
    private void dfs(TreeNode root, TreeNode pre, int distance, int k, Map<TreeNode, TreeNode> map, List<Integer> list) {
        if (root == null) {
            return;
        }

        //找到距离target节点为k的节点值，加入list集合中
        if (distance == k) {
            list.add(root.val);
        }

        //root父节点不为前驱节点pre，则说明root到root父节点这条路径没有被遍历，可以往父节点找，避免重复查找
        if (map.get(root) != pre) {
            dfs(map.get(root), root, distance + 1, k, map, list);
        }

        //root左子节点不为前驱节点pre，则说明root到root左子树这条路径没有被遍历，可以往左子树找，避免重复查找
        if (root.left != pre) {
            dfs(root.left, root, distance + 1, k, map, list);
        }

        //root右子节点不为前驱节点pre，则说明root到root右子树这条路径没有被遍历，可以往右子树找，避免重复查找
        if (root.right != pre) {
            dfs(root.right, root, distance + 1, k, map, list);
        }
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
