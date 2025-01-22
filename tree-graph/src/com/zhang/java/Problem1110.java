package com.zhang.java;

import java.util.*;

/**
 * @Date 2025/3/9 08:01
 * @Author zsy
 * @Description 删点成林 类比Problem450、Problem1339、Problem2049
 * 给出二叉树的根节点 root，树上每个节点都有一个不同的值。
 * 如果节点值在 to_delete 中出现，我们就把该节点从树上删去，最后得到一个森林（一些不相交的树构成的集合）。
 * 返回森林中的每棵树。你可以按任意顺序组织答案。
 * <p>
 * 输入：root = [1,2,3,4,5,6,7], to_delete = [3,5]
 * 输出：[[1,2,null,4],[6],[7]]
 * <p>
 * 输入：root = [1,2,4,null,3], to_delete = [3]
 * 输出：[[1,2,4]]
 * <p>
 * 树中的节点数最大为 1000。
 * 每个节点都有一个介于 1 到 1000 之间的值，且各不相同。
 * to_delete.length <= 1000
 * to_delete 包含一些从 1 到 1000、各不相同的值。
 */
public class Problem1110 {
    public static void main(String[] args) {
        Problem1110 problem1110 = new Problem1110();
        String[] data = {"1", "2", "3", "4", "5", "6", "7"};
        TreeNode root = problem1110.buildTree(data);
        int[] to_delete = {3, 5};
        List<TreeNode> list = problem1110.delNodes(root, to_delete);
    }

    /**
     * dfs (后续遍历)
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @param to_delete
     * @return
     */
    public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<TreeNode> list = new ArrayList<>();
        Set<Integer> set = new HashSet<>();

        for (int num : to_delete) {
            set.add(num);
        }

        //不删除根节点，则根节点加入list
        if (!set.contains(root.val)) {
            list.add(root);
        }

        dfs(root, list, set);

        return list;
    }

    /**
     * 返回node为根节点的树删除set中节点后的根节点
     *
     * @param node
     * @param list
     * @param set
     * @return
     */
    private TreeNode dfs(TreeNode node, List<TreeNode> list, Set<Integer> set) {
        if (node == null) {
            return null;
        }

        node.left = dfs(node.left, list, set);
        node.right = dfs(node.right, list, set);

        //当前节点不是要删除的节点，则返回当前节点
        if (!set.contains(node.val)) {
            return node;
        }

        //要删除的节点的左子节点非空，则左子节点为新的根节点，加入list
        if (node.left != null) {
            list.add(node.left);
        }

        //要删除的节点的右子节点非空，则右子节点为新的根节点，加入list
        if (node.right != null) {
            list.add(node.right);
        }

        //当前节点为要删除的节点，则返回空节点
        return null;
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
