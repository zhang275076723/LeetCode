package com.zhang.java;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2022/3/13 15:25
 * @Author zsy
 * @Description 输入某二叉树的前序遍历和中序遍历的结果，请构建该二叉树并返回其根节点
 * 假设输入的前序遍历和中序遍历的结果中都不含重复的数字
 * <p>
 * Input: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
 * Output: [3,9,20,null,null,15,7]
 */
public class Offer07 {
    public static void main(String[] args) {
        Offer07 offer07 = new Offer07();
        int[] preorder = {3, 9, 20, 15, 7};
        int[] inorder = {9, 3, 15, 20, 7};
        TreeNode treeNode = offer07.buildTree(preorder, inorder);

        //层次遍历
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(treeNode);
        while (!queue.isEmpty()) {
            TreeNode node = queue.remove();
            System.out.println(node.val);
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
    }

    /**
     * 1、通过【前序遍历列表】确定【根节点 (root)】
     * 2、将【中序遍历列表】的节点分割成【左分支节点】和【右分支节点】
     * 3、递归寻找【左分支节点】中的【根节点 (left child)】和 【右分支节点】中的【根节点 (right child)】
     *
     * @param preorder
     * @param inorder
     * @return
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        //递归出口
        if (preorder.length == 0) {
            return null;
        }

        //中序遍历中根节点的索引
        int rootIndex = 0;
        int rootVal = preorder[0];
        int len = inorder.length;
        //建立当前根节点
        TreeNode root = new TreeNode(rootVal);

        //找到rootIndex
        for (int i = 0; i < len; i++) {
            if (inorder[i] == rootVal) {
                rootIndex = i;
                break;
            }
        }

        int[] leftPreorder = Arrays.copyOfRange(preorder, 1, rootIndex + 1);
        int[] rightPreorder = Arrays.copyOfRange(preorder, rootIndex + 1, len);
        int[] leftInorder = Arrays.copyOfRange(inorder, 0, rootIndex);
        int[] rightInorder = Arrays.copyOfRange(inorder, rootIndex + 1, len);
        root.left = buildTree(leftPreorder, leftInorder);
        root.right = buildTree(rightPreorder, rightInorder);

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


