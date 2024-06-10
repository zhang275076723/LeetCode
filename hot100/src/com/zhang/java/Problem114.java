package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/5/2 9:42
 * @Author zsy
 * @Description 二叉树展开为链表 二叉树和链表之间的转换类比Problem116、Problem117、Problem426、Problem430、Problem897、Offer36
 * 给你二叉树的根结点 root ，请你将它展开为一个单链表：
 * 展开后的单链表应该同样使用 TreeNode ，其中 right 子指针指向链表中下一个结点，而左子指针始终为 null 。
 * 展开后的单链表应该与二叉树 先序遍历 顺序相同。
 * 你可以使用原地算法（O(1) 额外空间）展开这棵树吗？
 * <p>
 * 输入：root = [1,2,5,3,4,null,6]
 * 输出：[1,null,2,null,3,null,4,null,5,null,6]
 * <p>
 * 输入：root = []
 * 输出：[]
 * <p>
 * 输入：root = [0]
 * 输出：[0]
 * <p>
 * 树中结点数在范围 [0, 2000] 内
 * -100 <= Node.val <= 100
 */
public class Problem114 {
    public static void main(String[] args) {
        Problem114 problem114 = new Problem114();
        String[] data = {"1", "2", "5", "3", "4", "null", "6"};
        TreeNode root = problem114.buildTree(data);
//        problem114.flatten(root);
        problem114.flatten2(root);
    }

    /**
     * 前序遍历
     * 根据前序遍历顺序，将节点放在集合中，再按照前序遍历顺序连接节点为链表
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     */
    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }

        List<TreeNode> list = new ArrayList<>();

        preorder(root, list);
//        preorder2(root, list);

        //头结点，即root节点
        TreeNode node = list.remove(0);

        while (!list.isEmpty()) {
            //node节点的下一个节点
            TreeNode nextNode = list.remove(0);
            node.left = null;
            node.right = nextNode;
            node = nextNode;
        }
    }

    /**
     * 前序遍历
     * 根据前序遍历性质，在前序遍历过程中，左子树中最右下节点的下一个遍历到节点是右子树根节点，
     * 所以找当前节点左子树的最右下节点，将当前节点的右子树作为最右下节点的右子树，再将当前节点的左子树作为当前节点的右子树
     * 时间复杂度O(n)，空间复杂度O(1)
     * 例如：
     * <     1                1        1            1            1
     * <    / \              /          \            \            \
     * <   2   5     =>     2     =>     2     =>     2     =>     2
     * <  / \   \          / \          / \          /              \
     * < 3   4   6        3   4        3   4        3                3
     * <                       \            \        \                \
     * <                        5            5        4                4
     * <                         \            \        \                \
     * <                          6            6        5                5
     * <                                                 \                \
     * <                                                  6                6
     *
     * @param root
     */
    public void flatten2(TreeNode root) {
        if (root == null) {
            return;
        }

        TreeNode node = root;

        while (node != null) {
            //当前节点左子树的最右下节点
            TreeNode mostRightNode = node.left;

            if (mostRightNode != null) {
                //找当前节点左子树的最右下节点
                while (mostRightNode.right != null) {
                    mostRightNode = mostRightNode.right;
                }

                //当前节点左子树的最右下节点的右指针指向当前节点的右子树
                mostRightNode.right = node.right;
                //将当前节点的左子树作为右子树
                node.right = node.left;
                //当前节点的左子树为空
                node.left = null;
            }

            node = node.right;
        }
    }

    /**
     * 非递归前序遍历
     *
     * @param root
     * @param list
     */
    private void preorder(TreeNode root, List<TreeNode> list) {
        if (root == null) {
            return;
        }

        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;

        while (!stack.isEmpty() || node != null) {
            while (node != null) {
                list.add(node);
                stack.push(node);
                node = node.left;
            }

            node = stack.pop();
            node = node.right;
        }
    }

    /**
     * 非递归前序遍历
     *
     * @param root
     * @param list
     */
    private void preorder2(TreeNode root, List<TreeNode> list) {
        if (root == null) {
            return;
        }

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            list.add(node);

            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
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
