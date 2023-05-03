package com.zhang.java;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * @Date 2022/8/19 9:23
 * @Author zsy
 * @Description 从中序与后序遍历序列构造二叉树 分治法类比Problem95、Problem105、Problem108、Problem109、Problem255、Problem449、Problem889、Problem1008、Offer7、Offer33
 * 给定两个整数数组 inorder 和 postorder ，
 * 其中 inorder 是二叉树的中序遍历，postorder 是同一棵树的后序遍历，请你构造并返回这颗 二叉树 。
 * <p>
 * 输入：inorder = [9,3,15,20,7], postorder = [9,15,7,20,3]
 * 输出：[3,9,20,null,null,15,7]
 * <p>
 * 输入：inorder = [-1], postorder = [-1]
 * 输出：[-1]
 * <p>
 * 1 <= inorder.length <= 3000
 * postorder.length == inorder.length
 * -3000 <= inorder[i], postorder[i] <= 3000
 * inorder 和 postorder 都由 不同 的值组成
 * postorder 中每一个值都在 inorder 中
 * inorder 保证是树的中序遍历
 * postorder 保证是树的后序遍历
 */
public class Problem106 {
    public static void main(String[] args) {
        Problem106 problem106 = new Problem106();
        int[] inorder = {9, 3, 15, 20, 7};
        int[] postorder = {9, 15, 7, 20, 3};
        TreeNode root = problem106.buildTree(inorder, postorder);
        problem106.levelTraversal(root);
    }

    /**
     * 分治法
     * 后序遍历数组中最后一个元素为当前根节点，将中序遍历数组和后序遍历数组分为左子树数组和右子树数组，
     * 递归对左子树数组和右子树数组建立二叉树
     * 时间复杂度O(n)，空间复杂度O(n) (哈希表需要O(n)空间，栈的深度平均为O(logn)，最差为O(n))
     *
     * @param inorder
     * @param postorder
     * @return
     */
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        if (inorder.length == 0) {
            return null;
        }

        //在O(1)确定后序遍历数组中节点在中序遍历数组中的下标索引
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }

        return buildTree(0, inorder.length - 1,
                0, postorder.length - 1,
                inorder, postorder, map);
    }

    private TreeNode buildTree(int inorderLeft, int inorderRight, int postorderLeft, int postorderRight,
                               int[] inorder, int[] postorder, Map<Integer, Integer> map) {
        if (inorderLeft > inorderRight) {
            return null;
        }

        if (inorderLeft == inorderRight) {
            return new TreeNode(inorder[inorderLeft]);
        }

        //中序遍历数组根节点索引
        int inorderRootIndex = map.get(postorder[postorderRight]);
        //左子树长度
        int leftLength = inorderRootIndex - inorderLeft;

        //根节点，后序遍历数组中最后一个元素即为根节点
        TreeNode root = new TreeNode(postorder[postorderRight]);

        root.left = buildTree(inorderLeft, inorderRootIndex - 1,
                postorderLeft, postorderLeft + leftLength - 1,
                inorder, postorder, map);
        root.right = buildTree(inorderRootIndex + 1, inorderRight,
                postorderLeft + leftLength, postorderRight - 1,
                inorder, postorder, map);

        return root;
    }

    /**
     * 层次遍历输出
     *
     * @param root
     */
    private void levelTraversal(TreeNode root) {
        if (root == null) {
            return;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            System.out.println(node.val);

            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
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
