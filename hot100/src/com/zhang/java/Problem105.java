package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/5/1 11:57
 * @Author zsy
 * @Description 给定两个整数数组 preorder 和 inorder ，
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
     * 时间复杂度O(n)，空间复杂度O(n)，哈希表需要O(n)的空间
     * 1、通过【前序遍历列表】确定【根节点 (root)】和【中序遍历列表】中的【根节点索引 (inorderRootIndex)】
     * 2、将【前序遍历列表】的节点分割成【左节点的前序遍历列表】和【右节点的前序遍历列表】
     * 2、将【中序遍历列表】的节点分割成【左节点的中序遍历列表】和【右节点的中序遍历列表】
     * 3、递归寻找【左分支节点】中的【根节点】和 【右分支节点】中的【根节点】
     *
     * @param preorder
     * @param inorder
     * @return
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int len = preorder.length;
        if (len == 0) {
            return null;
        }

        //使用hash在O(1)时间，定位中序遍历数组中根节点的索引
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < len; i++) {
            map.put(inorder[i], i);
        }

        return buildTree(preorder, inorder, map,
                0, len - 1, 0, len - 1);
    }

    /**
     * @param preorder      前序遍历数组
     * @param inorder       中序遍历数组
     * @param map           中序遍历数组哈希，在O(1)时间找到中序遍历数组中的根节点索引
     * @param preorderLeft  前序遍历左指针
     * @param preorderRight 前序遍历右指针
     * @param inorderLeft   中序遍历左指针
     * @param inorderRight  中序遍历右指针
     * @return
     */
    private TreeNode buildTree(int[] preorder, int[] inorder, Map<Integer, Integer> map,
                               int preorderLeft, int preorderRight, int inorderLeft, int inorderRight) {
        //当前数组的长度
        int len1 = preorderRight - preorderLeft + 1;
        int len2 = inorderRight - inorderLeft + 1;
        if (len1 <= 0 || len2 <= 0) {
            return null;
        }

        int rootValue = preorder[preorderLeft];
        TreeNode root = new TreeNode(rootValue);
        //中序遍历根节点索引
        int inorderRootIndex = map.get(rootValue);
        //左子树数组长度
        int leftLen = inorderRootIndex - inorderLeft;

        root.left = buildTree(preorder, inorder, map,
                preorderLeft + 1, preorderLeft + leftLen,
                inorderLeft, inorderRootIndex - 1);
        root.right = buildTree(preorder, inorder, map,
                preorderLeft + leftLen + 1, preorderRight,
                inorderRootIndex + 1, inorderRight);

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

    public class TreeNode {
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
