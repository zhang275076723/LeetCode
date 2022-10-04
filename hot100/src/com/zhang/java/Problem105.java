package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/5/1 11:57
 * @Author zsy
 * @Description 从前序与中序遍历序列构造二叉树 类比Problem106、Problem889、Problem1008、Offer33 同Offer7
 * 给定两个整数数组 preorder 和 inorder ，
 * 其中 preorder 是二叉树的先序遍历， inorder 是同一棵树的中序遍历，请构造二叉树并返回其根节点。
 * <p>
 * 输入: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
 * 输出: [3,9,20,null,null,15,7]
 * <p>
 * 输入: preorder = [-1], inorder = [-1]
 * 输出: [-1]
 * <p>
 * 1 <= preorder.length <= 3000
 * inorder.length == preorder.length
 * -3000 <= preorder[i], inorder[i] <= 3000
 * preorder 和 inorder 均 无重复 元素
 * inorder 均出现在 preorder
 * preorder 保证 为二叉树的前序遍历序列
 * inorder 保证 为二叉树的中序遍历序列
 */
public class Problem105 {
    public static void main(String[] args) {
        Problem105 problem105 = new Problem105();
        int[] preorder = {3, 9, 20, 15, 7};
        int[] inorder = {9, 3, 15, 20, 7};
        TreeNode root = problem105.buildTree(preorder, inorder);
        problem105.levelTraversal(root);
    }

    /**
     * 分治
     * 1、通过【前序遍历列表】确定【根节点 (root)】和【中序遍历列表】中的【根节点索引 (inorderRootIndex)】
     * 2、将【前序遍历列表】的节点分割成【左节点的前序遍历列表】和【右节点的前序遍历列表】
     * 2、将【中序遍历列表】的节点分割成【左节点的中序遍历列表】和【右节点的中序遍历列表】
     * 3、递归寻找【左分支节点】中的【根节点】和 【右分支节点】中的【根节点】
     * 时间复杂度O(n)，空间复杂度O(n) (哈希表需要O(n)的空间，栈的深度平均为O(logn)，最差为O(n))
     *
     * @param preorder
     * @param inorder
     * @return
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder.length == 0) {
            return null;
        }

        //key：节点值，value：节点在中序遍历数组的索引下标，在O(1)确定前序遍历数组中元素在中序遍历数组中索引
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }

        return buildTree(0, preorder.length - 1,
                0, inorder.length - 1,
                preorder, inorder, map);
    }

    /**
     * @param leftPreorder  前序遍历左指针
     * @param rightPreorder 前序遍历右指针
     * @param leftInorder   中序遍历左指针
     * @param rightInorder  中序遍历右指针
     * @param preorder      前序遍历数组
     * @param inorder       中序遍历数组
     * @param map           中序遍历数组哈希，在O(1)时间找到中序遍历数组中的根节点索引
     * @return
     */
    private TreeNode buildTree(int leftPreorder, int rightPreorder, int leftInorder, int rightInorder,
                               int[] preorder, int[] inorder, Map<Integer, Integer> map) {
        if (leftPreorder > rightPreorder) {
            return null;
        }

        if (leftPreorder == rightPreorder) {
            return new TreeNode(preorder[leftPreorder]);
        }

        //中序遍历数组中根节点索引
        int inorderRootIndex = map.get(preorder[leftPreorder]);
        //左子树长度
        int leftLength = inorderRootIndex - leftInorder;

        TreeNode root = new TreeNode(preorder[leftPreorder]);

        root.left = buildTree(leftPreorder + 1, leftPreorder + leftLength,
                leftInorder, inorderRootIndex - 1,
                preorder, inorder, map);
        root.right = buildTree(leftPreorder + leftLength + 1, rightPreorder,
                inorderRootIndex + 1, rightInorder,
                preorder, inorder, map);

        return root;
    }

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
