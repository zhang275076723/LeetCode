package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/5/23 08:22
 * @Author zsy
 * @Description 上下翻转二叉树 类比Problem206、Problem226
 * 给你一个二叉树的根节点 root ，请你将此二叉树上下翻转，并返回新的根节点。
 * 你可以按下面的步骤翻转一棵二叉树：
 * 1、原来的左子节点变成新的根节点
 * 2、原来的根节点变成新的右子节点
 * 3、原来的右子节点变成新的左子节点
 * 上面的步骤逐层进行。题目数据保证每个右节点都有一个同级节点（即共享同一父节点的左节点）且不存在子节点。
 * <p>
 * 输入：root = [1,2,3,4,5]
 * 输出：[4,5,2,null,null,3,1]
 * <p>
 * 输入：root = []
 * 输出：[]
 * <p>
 * 输入：root = [1]
 * 输出：[1]
 * <p>
 * 树中节点数目在范围 [0, 10] 内
 * 1 <= Node.val <= 10
 * 树中的每个右节点都有一个同级节点（即共享同一父节点的左节点）
 * 树中的每个右节点都没有子节点
 */
public class Problem156 {
    public static void main(String[] args) {
        Problem156 problem156 = new Problem156();
        String[] data = {"1", "2", "3", "4", "5"};
        TreeNode root = problem156.buildTree(data);
//        root = problem156.upsideDownBinaryTree(root);
        root = problem156.upsideDownBinaryTree2(root);
    }

    /**
     * 递归上下翻转二叉树
     * 根节点的左子节点作为新的根节点，根节点的右子节点作为左子节点的左子节点，根节点作为左子节点的右子节点
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public TreeNode upsideDownBinaryTree(TreeNode root) {
        if (root == null) {
            return null;
        }

        //当前节点的左右子树都为空，则当前节点就是上下翻转后的二叉树，直接返回
        if (root.left == null && root.right == null) {
            return root;
        }

        //上下翻转二叉树之后的新根节点
        TreeNode newRoot = upsideDownBinaryTree(root.left);

        //根节点的右子节点作为左子节点的左子节点，根节点作为左子节点的右子节点
        root.left.left = root.right;
        root.left.right = root;
        //根节点左右指针置为空
        root.left = null;
        root.right = null;

        return newRoot;
    }

    /**
     * 非递归上下翻转二叉树
     * 当前节点的兄弟节点作为当前节点的左子节点，当前节点的父节点作为当前节点的右子节点，
     * 直至当前节点为空，则当前节点的父节点为上下翻转二叉树之后的新根节点
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param root
     * @return
     */
    public TreeNode upsideDownBinaryTree2(TreeNode root) {
        if (root == null) {
            return null;
        }

        //当前节点的左右子树都为空，则当前节点就是上下翻转后的二叉树，直接返回
        if (root.left == null && root.right == null) {
            return root;
        }

        //当前遍历到的节点
        TreeNode node = root;
        //node节点的父节点
        TreeNode parent = null;
        //node节点的兄弟节点
        TreeNode sibling = null;

        while (node != null) {
            //node节点的左子节点
            TreeNode leftNode = node.left;

            //node的右子节点作为左子节点的左子节点，根节点作为左子节点的右子节点
            node.left = sibling;
            sibling = node.right;
            node.right = parent;
            parent = node;

            //更新当前节点，即沿着左子树往下
            node = leftNode;
        }

        return parent;
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

    private static class TreeNode {
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
