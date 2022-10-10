package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/10/7 09:50
 * @Author zsy
 * @Description 二叉搜索树中的插入操作 类比Problem450、Problem700
 * 给定二叉搜索树（BST）的根节点 root 和要插入树中的值 value ，将值插入二叉搜索树。
 * 返回插入后二叉搜索树的根节点。
 * 输入数据 保证 ，新值和原始二叉搜索树中的任意节点值都不同。
 * 注意，可能存在多种有效的插入方式，只要树在插入后仍保持为二叉搜索树即可。
 * 你可以返回 任意有效的结果 。
 * <p>
 * 输入：root = [4,2,7,1,3], val = 5
 * 输出：[4,2,7,1,3,5]
 * <p>
 * 输入：root = [40,20,60,10,30,50,70], val = 25
 * 输出：[40,20,60,10,30,50,70,null,null,25]
 * <p>
 * 输入：root = [4,2,7,1,3,null,null,null,null,null,null], val = 5
 * 输出：[4,2,7,1,3,5]
 * <p>
 * 树中的节点数将在 [0, 10^4]的范围内。
 * -10^8 <= Node.val <= 10^8
 * 所有值 Node.val 是 独一无二 的。
 * -10^8 <= val <= 10^8
 * 保证 val 在原始BST中不存在。
 */
public class Problem701 {
    public static void main(String[] args) {
        Problem701 problem701 = new Problem701();
        String[] data = {"4", "2", "7", "1", "3"};
        TreeNode root = problem701.buildTree(data);
        int val = 5;
//        root = problem701.insertIntoBST(root, val);
        root = problem701.insertIntoBST2(root, val);
    }

    /**
     * 递归
     * 找到要插入的位置进行插入(叶节点插入)
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @param val
     * @return
     */
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }

        if (root.val < val) {
            root.right = insertIntoBST(root.right, val);
        } else if (root.val > val) {
            root.left = insertIntoBST(root.left, val);
        }

        return root;
    }

    /**
     * 非递归
     * 找到要插入的位置进行插入(叶节点插入)
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param root
     * @param val
     * @return
     */
    public TreeNode insertIntoBST2(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }

        //要插入节点的父节点
        TreeNode parent = root;
        TreeNode node = root;

        while (node != null) {
            parent = node;

            //往右找
            if (node.val < val) {
                node = node.right;
            } else if (node.val > val) {
                //往左找
                node = node.left;
            }
        }

        //叶节点插入
        if (parent.val < val) {
            parent.right = new TreeNode(val);
        } else {
            parent.left = new TreeNode(val);
        }

        return root;
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
