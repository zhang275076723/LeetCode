package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/1/26 08:09
 * @Author zsy
 * @Description 二叉树剪枝 类比Problem82、Problem83、Problem104、Problem700、Problem701、Problem814
 * 给你二叉树的根结点 root ，此外树的每个结点的值要么是 0 ，要么是 1 。
 * 返回移除了所有不包含 1 的子树的原二叉树。
 * 节点 node 的子树为 node 本身加上所有 node 的后代。
 * <p>
 * 输入：root = [1,null,0,0,1]
 * 输出：[1,null,0,null,1]
 * 解释：
 * 只有红色节点满足条件“所有不包含 1 的子树”。 右图为返回的答案。
 * <p>
 * 输入：root = [1,0,1,0,0,0,1]
 * 输出：[1,null,1,null,1]
 * <p>
 * 输入：root = [1,1,0,1,1,0,1,0]
 * 输出：[1,1,0,1,1,null,1]
 * <p>
 * 树中节点的数目在范围 [1, 200] 内
 * Node.val 为 0 或 1
 */
public class Problem814 {
    public static void main(String[] args) {
        Problem814 problem814 = new Problem814();
        String[] data = {"1", "0", "1", "0", "0", "0", "1"};
        TreeNode root = problem814.buildTree(data);
        root = problem814.pruneTree(root);
    }
    /**
     * dfs，递归
     * 核心思想：递归要抓住递归函数的功能，不要在意递归函数的具体实现
     * pruneTree()用于删除root中所有不包含1的子树，分别对root左右子树调用pruneTree()，
     * 此时root左右子树就是所有不包含1的子树的树，如果此时root左右子树为null，并且root为0，则以root为根节点的树不包含1，返回null；
     * 否则，返回root
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public TreeNode pruneTree(TreeNode root) {
        if (root == null) {
            return null;
        }

        root.left = pruneTree(root.left);
        root.right = pruneTree(root.right);

        //root左右子树为null，并且root为0，则以root为根节点的树不包含1，返回null
        if (root.left == null && root.right == null && root.val == 0) {
            return null;
        } else {
            return root;
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
