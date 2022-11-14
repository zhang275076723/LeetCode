package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/11/14 11:49
 * @Author zsy
 * @Description 完全二叉树的节点个数 类比Problem958
 * 给你一棵 完全二叉树 的根节点 root ，求出该树的节点个数。
 * 完全二叉树 的定义如下：在完全二叉树中，除了最底层节点可能没填满外，其余每层节点数都达到最大值，
 * 并且最下面一层的节点都集中在该层最左边的若干位置。若最底层为第 h 层，则该层包含 1~ 2^h 个节点。
 * <p>
 * 输入：root = [1,2,3,4,5,6]
 * 输出：6
 * <p>
 * 输入：root = []
 * 输出：0
 * <p>
 * 输入：root = [1]
 * 输出：1
 * <p>
 * 树中节点的数目范围是[0, 5 * 10^4]
 * 0 <= Node.val <= 5 * 10^4
 * 题目数据保证输入的树是 完全二叉树
 */
public class Problem222 {
    public static void main(String[] args) {
        Problem222 problem222 = new Problem222();
        String[] data = {"1", "2", "3", "4", "5", "6"};
        TreeNode root = problem222.buildTree(data);
        System.out.println(problem222.countNodes(root));
        System.out.println(problem222.countNodes2(root));
    }

    /**
     * 层次遍历
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int count = 0;

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            count++;

            if (node.left != null) {
                queue.offer(node.left);
            }

            if (node.right != null) {
                queue.offer(node.right);
            }
        }

        return count;
    }

    /**
     * 完全二叉树的性质：除了最底层，其它层都是满二叉树；空节点只会存在于最底层的右边，左边都是非空节点
     * 如果一棵树是满二叉树，则节点个数为2^h-1 (h：树的高度)
     * 根据当前节点的左子树和右子树的高度，判断哪颗子树是满二叉树
     * 时间复杂度O((logn)^2)，空间复杂度O(logn) (T(n) = T(n/2) + logn)
     *
     * @param root
     * @return
     */
    public int countNodes2(TreeNode root) {
        if (root == null) {
            return 0;
        }

        //左子树高度
        int leftHeight = 0;
        //右子树高度
        int rightHeight = 0;
        TreeNode leftNode = root.left;
        TreeNode rightNode = root.right;

        while (leftNode != null) {
            leftHeight++;
            leftNode = leftNode.left;
        }

        while (rightNode != null) {
            rightHeight++;
            rightNode = rightNode.left;
        }

        //左子树高度等于右子树高度，说明左子树是满二叉树，左子树和根节点个数为2^leftHeight，再加上右子树节点个数，即为树的节点个数
        if (leftHeight == rightHeight) {
            return (1 << leftHeight) + countNodes2(root.right);
        } else {
            //左子树高度等于右子树高度不相等，即相差1，说明右子树是满二叉树，右子树和根节点个数为2^rightHeight，再加上左子树节点个数，即为树的节点个数
            return (1 << rightHeight) + countNodes2(root.left);
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
