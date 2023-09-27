package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/11/14 11:49
 * @Author zsy
 * @Description 完全二叉树的节点个数 快手面试题 完全二叉树类比Problem919、Problem958
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
        System.out.println(problem222.countNodes3(root));
        System.out.println(problem222.countNodes4(root));
    }

    /**
     * dfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }

        //左子树节点个数
        int leftCount = countNodes(root.left);
        //右子树节点个数
        int rightCount = countNodes(root.right);

        return leftCount + rightCount + 1;
    }

    /**
     * bfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int countNodes2(TreeNode root) {
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
     * 利用完全二叉树性质，非递归实现
     * 判断根节点左右子树的高度是否相等，如果相等，则左子树是满二叉树，如果不等，则右子树是满二叉树
     * 时间复杂度O((logn)^2)，空间复杂度O(1) (T(n) = T(n/2) + logn)
     *
     * @param root
     * @return
     */
    public int countNodes3(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int count = 0;
        TreeNode node = root;

        while (node != null) {
            TreeNode leftNode = node.left;
            TreeNode rightNode = node.right;
            //左子树高度
            int leftHeight = 0;
            //右子树高度
            int rightHeight = 0;

            //计算左子树高度
            while (leftNode != null) {
                leftHeight++;
                leftNode = leftNode.left;
            }

            //计算右子树高度
            while (rightNode != null) {
                rightHeight++;
                rightNode = rightNode.left;
            }

            //左子树高度等于右子树高度，则左子树是满二叉树，左子树和根节点个数为2^leftHeight，继续找右子树的节点个数
            if (leftHeight == rightHeight) {
                count = count + (1 << leftHeight);
                node = node.right;
            } else {
                //左子树高度等于右子树高度不相等，即相差1，则右子树是满二叉树，右子树和根节点个数为2^rightHeight，继续找左子树的节点个数
                count = count + (1 << rightHeight);
                node = node.left;
            }
        }

        return count;
    }

    /**
     * 利用完全二叉树性质，递归实现
     * 判断根节点左右子树的高度是否相等，如果相等，则左子树是满二叉树，如果不等，则右子树是满二叉树
     * 时间复杂度O((logn)^2)，空间复杂度O(logn) (T(n) = T(n/2) + logn)
     *
     * @param root
     * @return
     */
    public int countNodes4(TreeNode root) {
        if (root == null) {
            return 0;
        }

        TreeNode leftNode = root.left;
        TreeNode rightNode = root.right;
        //左子树高度
        int leftHeight = 0;
        //右子树高度
        int rightHeight = 0;

        //计算左子树高度
        while (leftNode != null) {
            leftHeight++;
            leftNode = leftNode.left;
        }

        //计算右子树高度
        while (rightNode != null) {
            rightHeight++;
            rightNode = rightNode.left;
        }

        //左子树高度等于右子树高度，则左子树是满二叉树，左子树和根节点个数为2^leftHeight，继续找右子树的节点个数
        if (leftHeight == rightHeight) {
            return (1 << leftHeight) + countNodes4(root.right);
        } else {
            //左子树高度等于右子树高度不相等，即相差1，则右子树是满二叉树，右子树和根节点个数为2^rightHeight，继续找左子树的节点个数
            return (1 << rightHeight) + countNodes4(root.left);
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
