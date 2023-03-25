package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/3/25 08:45
 * @Author zsy
 * @Description 递增顺序搜索树 二叉树和链表之间转化类比Problem114、Problem430、Offer36
 * 给你一棵二叉搜索树的 root ，请你 按中序遍历 将其重新排列为一棵递增顺序搜索树，
 * 使树中最左边的节点成为树的根节点，并且每个节点没有左子节点，只有一个右子节点。
 * <p>
 * 输入：root = [5,3,6,2,4,null,8,1,null,null,null,7,9]
 * 输出：[1,null,2,null,3,null,4,null,5,null,6,null,7,null,8,null,9]
 * <p>
 * 输入：root = [5,1,7]
 * 输出：[1,null,5,null,7]
 * <p>
 * 树中节点数的取值范围是 [1, 100]
 * 0 <= Node.val <= 1000
 */
public class Problem897 {
    public static void main(String[] args) {
        Problem897 problem897 = new Problem897();
        String[] data = {"5", "3", "6", "2", "4", "null", "8", "1", "null", "null", "null", "7", "9"};
        TreeNode root = problem897.buildTree(data);
//        TreeNode head = problem897.increasingBST(root);
        TreeNode head2 = problem897.increasingBST2(root);
    }

    /**
     * 中序遍历
     * 根据中序遍历顺序，将节点放在集合中，再按照中序遍历顺序连接节点为链表
     * 注意：将节点的left指针置空
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public TreeNode increasingBST(TreeNode root) {
        if (root == null) {
            return null;
        }

        List<TreeNode> list = new ArrayList<>();

        inorder(root, list);

        //头结点
        TreeNode head = list.remove(0);
        TreeNode node = head;

        while (!list.isEmpty()) {
            TreeNode nextNode = list.remove(0);
            node.right = nextNode;
            //nextNode的left指针置空
            nextNode.left = null;
            node = nextNode;
        }

        return head;
    }

    /**
     * 中序遍历
     * 在中序遍历过程中就修改节点的左右指针，将二叉搜索树转换为单链表
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public TreeNode increasingBST2(TreeNode root) {
        if (root == null) {
            return null;
        }

        //头结点
        TreeNode head = null;
        //中序遍历过程中当前节点的前驱节点
        TreeNode pre = null;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;

        while (!stack.isEmpty() || node != null) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            node = stack.pop();

            if (head == null) {
                head = node;
            } else {
                //前驱节点的right指针指向当前节点
                pre.right = node;
                //当前节点的left指针置空
                node.left = null;
            }

            pre = node;
            node = node.right;
        }

        return head;
    }

    /**
     * 非递归中序遍历
     *
     * @param root
     * @param list
     */
    private void inorder(TreeNode root, List<TreeNode> list) {
        if (root == null) {
            return;
        }

        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;

        while (!stack.isEmpty() || node != null) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            node = stack.pop();
            list.add(node);
            node = node.right;
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
