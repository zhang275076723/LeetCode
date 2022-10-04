package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/9/15 8:25
 * @Author zsy
 * @Description 根据前序和后序遍历构造二叉树 类比Problem105、Problem106、Problem1008、Offer7、Offer33
 * 给定两个整数数组，preorder 和 postorder ，
 * 其中 preorder 是一个具有 无重复 值的二叉树的前序遍历，postorder 是同一棵树的后序遍历，重构并返回二叉树。
 * 如果存在多个答案，您可以返回其中 任何 一个。
 * <p>
 * 输入：preorder = [1,2,4,5,3,6,7], postorder = [4,5,2,6,7,3,1]
 * 输出：[1,2,3,4,5,6,7]
 * <p>
 * 输入: preorder = [1], postorder = [1]
 * 输出: [1]
 * <p>
 * 1 <= preorder.length <= 30
 * 1 <= preorder[i] <= preorder.length
 * preorder 中所有值都 不同
 * postorder.length == preorder.length
 * 1 <= postorder[i] <= postorder.length
 * postorder 中所有值都 不同
 * 保证 preorder 和 postorder 是同一棵二叉树的前序遍历和后序遍历
 */
public class Problem889 {
    public static void main(String[] args) {
        Problem889 problem889 = new Problem889();
        int[] preorder = {1, 2, 4, 5, 3, 6, 7};
        int[] postorder = {4, 5, 2, 6, 7, 3, 1};
        TreeNode root = problem889.constructFromPrePost(preorder, postorder);
    }

    /**
     * 分治
     * 通过前序遍历数组中第一个元素确定根节点，前序遍历数组中第二个元素在后序遍历数组中的位置确定左子树数组和右子树数组长度，
     * 递归确定左子树节点和右子树节点
     * 时间复杂度O(n)，空间复杂度O(n) (哈希表需要O(n)的空间，栈的深度平均为O(logn)，最差为O(n))
     * 注意：前序遍历和后序遍历是无法确定唯一一个二叉树，这里假定优先往左子树插入
     *
     * @param preorder
     * @param postorder
     * @return
     */
    public TreeNode constructFromPrePost(int[] preorder, int[] postorder) {
        if (preorder.length == 0) {
            return null;
        }

        //在O(1)确定前序遍历数组中元素在后序遍历数组中的索引下标
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < postorder.length; i++) {
            map.put(postorder[i], i);
        }

        return buildTree(0, preorder.length - 1,
                0, postorder.length - 1,
                preorder, postorder, map);
    }

    private TreeNode buildTree(int leftPreorder, int rightPreorder, int leftPostorder, int rightPostOrder,
                               int[] preorder, int[] postorder, Map<Integer, Integer> map) {
        if (leftPreorder > rightPreorder) {
            return null;
        }

        if (leftPreorder == rightPreorder) {
            return new TreeNode(preorder[leftPreorder]);
        }

        //后序遍历数组中左子树根节点索引下标
        int leftPostorderRootIndex = map.get(preorder[leftPreorder + 1]);
        //左子树长度
        int leftLength = leftPostorderRootIndex - leftPostorder + 1;

        TreeNode root = new TreeNode(preorder[leftPreorder]);

        root.left = buildTree(leftPreorder + 1, leftPreorder + leftLength,
                leftPostorder, leftPostorderRootIndex,
                preorder, postorder, map);
        root.right = buildTree(leftPreorder + leftLength + 1, rightPreorder,
                leftPostorderRootIndex + 1, rightPostOrder - 1,
                preorder, postorder, map);

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
