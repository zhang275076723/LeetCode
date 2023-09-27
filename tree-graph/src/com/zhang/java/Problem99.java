package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/8/16 10:16
 * @Author zsy
 * @Description 恢复二叉搜索树 二叉搜索树类比Problem95、Problem96、Problem98、Problem230、Problem501、Problem530、Problem538、Offer33、Offer36
 * 给你二叉搜索树的根节点 root ，该树中的 恰好 两个节点的值被错误地交换。
 * 请在不改变其结构的情况下，恢复这棵树 。
 * <p>
 * 输入：root = [1,3,null,null,2]
 * 输出：[3,1,null,null,2]
 * 解释：3 不能是 1 的左孩子，因为 3 > 1 。交换 1 和 3 使二叉搜索树有效。
 * <p>
 * 输入：root = [3,1,4,null,null,2]
 * 输出：[2,1,4,null,null,3]
 * 解释：2 不能在 3 的右子树中，因为 2 < 3 。交换 2 和 3 使二叉搜索树有效。
 * <p>
 * 树上节点的数目在范围 [2, 1000] 内
 * -2^31 <= Node.val <= 2^31 - 1
 */
public class Problem99 {
    /**
     * 递归中序遍历中当前节点的前驱节点
     */
    private TreeNode pre = null;

    /**
     * 递归中序遍历中要交换的前一个节点
     */
    private TreeNode node1 = null;

    /**
     * 递归中序遍历中要交换的后一个节点
     */
    private TreeNode node2 = null;

    public static void main(String[] args) {
        Problem99 problem99 = new Problem99();
        String[] data = {"3", "1", "4", "null", "null", "2"};
        TreeNode root = problem99.buildTree(data);
        problem99.recoverTree(root);
        problem99.recoverTree2(root);
    }

    /**
     * 递归中序遍历
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     */
    public void recoverTree(TreeNode root) {
        if (root == null) {
            return;
        }

        inorder(root);

        //找到要交换的两个节点，进行交换，node1指向第一个节点，node2指向最后一个节点
        int temp = node1.val;
        node1.val = node2.val;
        node2.val = temp;
    }

    /**
     * 非递归中序遍历
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     */
    public void recoverTree2(TreeNode root) {
        if (root == null) {
            return;
        }

        Stack<TreeNode> stack = new Stack<>();
        //中序遍历当前节点
        TreeNode node = root;
        //中序遍历当前节点的前一个节点
        TreeNode pre = null;
        //中序遍历中要交换的前一个节点
        TreeNode node1 = null;
        //中序遍历中要交换的后一个节点
        TreeNode node2 = null;

        while (!stack.isEmpty() || node != null) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            node = stack.pop();

            //当前节点前驱节点的值大于等于当前节点的值，则当前的两个节点需要调整
            if (pre != null && pre.val >= node.val) {
                //当要交换的第一个节点为空时，为node1赋值
                if (node1 == null) {
                    node1 = pre;
                }

                node2 = node;
            }

            //更新前驱节点
            pre = node;
            //更新当前节点
            node = node.right;
        }

        //找到要交换的两个节点，进行交换，node1指向第一个节点，node2指向最后一个节点
        int temp = node1.val;
        node1.val = node2.val;
        node2.val = temp;
    }

    private void inorder(TreeNode root) {
        if (root == null) {
            return;
        }

        inorder(root.left);

        //当前节点前驱节点的值大于等于当前节点的值，则当前的两个节点需要调整
        if (pre != null && pre.val >= root.val) {
            //当要交换的第一个节点为空时，为node1赋值
            if (node1 == null) {
                node1 = pre;
            }

            node2 = root;
        }

        //更新前驱节点
        pre = root;

        inorder(root.right);
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
