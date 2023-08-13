package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/5/15 08:23
 * @Author zsy
 * @Description 中序遍历的下一个节点 字节面试题 类比Problem235、Problem236、Problem450、Problem700、Problem701、Problem998、Offer68、Offer68_2
 * 给定二叉树其中的一个结点，请找出其中序遍历顺序的下一个结点并且返回。
 * 注意，树中的结点不仅包含左右子结点，而且包含指向父结点的指针。
 * <p>
 * 输入：root = [1,2,3,4,5,null,6,7,null,8,9], node = 9
 * 输出：1
 * <p>
 * 输入：root = [5], node = 5
 * 输出：null
 */
public class InorderNextNode {
    public static void main(String[] args) {
        InorderNextNode inorderNextNode = new InorderNextNode();
        String[] data = {"1", "2", "3", "4", "5", "null", "6", "7", "null", "8", "9"};
        TreeNode root = inorderNextNode.buildTree(data);
        TreeNode node = root.left.right.right;
        TreeNode nextNode = inorderNextNode.inorderNextNode(node);
    }

    /**
     * 通过当前节点和父节点之间的关系，找中序遍历的下一个节点
     * 1、当前节点的右子树不为空，则中序遍历当前节点的下一个节点为右子树的最左下节点
     * 2.1、当前节点的右子树为空，当前节点是父节点的左子节点，则中序遍历当前节点的下一个节点为当前节点的父节点
     * 2.2、当前节点的右子树为空，当前节点不是父节点的左子节点，沿着父节点往上找，
     * 当找到一个节点的父节点的左子节点为该节点时，则中序遍历当前节点的下一个节点为找到的该节点的父节点
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @param node
     * @return
     */
    public TreeNode inorderNextNode(TreeNode node) {
        if (node == null) {
            return null;
        }

        //情况1，当前节点的右子树不为空，则中序遍历当前节点的下一个节点为右子树的最左下节点
        if (node.right != null) {
            //node右子树的最左下节点
            TreeNode mostLeftNode = node.right;

            while (mostLeftNode.left != null) {
                mostLeftNode = mostLeftNode.left;
            }

            return mostLeftNode;
        } else {
            //情况2.1，当前节点的右子树为空，当前节点是父节点的左子节点，则中序遍历当前节点的下一个节点为当前节点的父节点
            if (node.parent != null && node.parent.left == node) {
                return node.parent;
            }

            //情况2.2，当前节点的右子树为空，当前节点不是父节点的左子节点，沿着父节点往上找，
            //当找到一个节点的父节点的左子节点为该节点时，则中序遍历当前节点的下一个节点为找到的该节点的父节点
            TreeNode temp = node;

            while (temp.parent != null && temp.parent.left != temp) {
                temp = temp.parent;
            }

            return temp.parent;
        }
    }

    /**
     * 建树，并在建树过程中对父节点赋值
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param data
     * @return
     */
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
                    //父节点赋值
                    leftNode.parent = node;
                    queue.offer(leftNode);
                }
            }
            if (!list.isEmpty()) {
                String rightValue = list.remove(0);
                if (!"null".equals(rightValue)) {
                    TreeNode rightNode = new TreeNode(Integer.parseInt(rightValue));
                    node.right = rightNode;
                    //父节点赋值
                    rightNode.parent = node;
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
        //当前节点的父节点
        TreeNode parent;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right, TreeNode parent) {
            this.val = val;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
    }
}
