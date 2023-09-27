package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/4/14 08:36
 * @Author zsy
 * @Description 二叉树中的链表 类比Problem100、Problem101、Problem226、Problem572、Problem951、Offer26、Offer27、Offer28
 * 给你一棵以 root 为根的二叉树和一个 head 为第一个节点的链表。
 * 如果在二叉树中，存在一条一直向下的路径，且每个点的数值恰好一一对应以 head 为首的链表中每个节点的值，
 * 那么请你返回 True ，否则返回 False 。
 * 一直向下的路径的意思是：从树中某个节点开始，一直连续向下的路径。
 * <p>
 * 输入：head = [4,2,8], root = [1,4,4,null,2,2,null,1,null,6,8,null,null,null,null,1,3]
 * 输出：true
 * 解释：树中蓝色的节点构成了与链表对应的子路径。
 * <p>
 * 输入：head = [1,4,2,6], root = [1,4,4,null,2,2,null,1,null,6,8,null,null,null,null,1,3]
 * 输出：true
 * <p>
 * 输入：head = [1,4,2,6,8], root = [1,4,4,null,2,2,null,1,null,6,8,null,null,null,null,1,3]
 * 输出：false
 * 解释：二叉树中不存在一一对应链表的路径。
 * <p>
 * 二叉树和链表中的每个节点的值都满足 1 <= node.val <= 100 。
 * 链表包含的节点数目在 1 到 100 之间。
 * 二叉树包含的节点数目在 1 到 2500 之间。
 */
public class Problem1367 {
    public static void main(String[] args) {
        Problem1367 problem1367 = new Problem1367();
        int[] dataList = {4, 2, 8};
        String[] dataTree = {"1", "4", "4", "null", "2", "2", "null", "1",
                "null", "6", "8", "null", "null", "null", "null", "1", "3"};
        ListNode head = problem1367.buildList(dataList);
        TreeNode root = problem1367.buildTree(dataTree);
        System.out.println(problem1367.isSubPath(head, root));
    }

    /**
     * dfs
     * 时间复杂度O(mn)，空间复杂度O(max(m,n)) (m：head链表长度，n：root树节点个数)
     *
     * @param head
     * @param root
     * @return
     */
    public boolean isSubPath(ListNode head, TreeNode root) {
        //head为空，则head是root的路径
        if (head == null) {
            return true;
        }

        //root为空，则head不是root的路径
        if (root == null) {
            return false;
        }

        //判断head是否是root的路径，或者head是否是root左子树的路径，或者head是否是root右子树的路径
        return contains(head, root) || isSubPath(head, root.left) || isSubPath(head, root.right);
    }

    /**
     * 判断head是否是root的路径
     *
     * @param head
     * @param root
     * @return
     */
    private boolean contains(ListNode head, TreeNode root) {
        //head为空，则head是root的路径
        if (head == null) {
            return true;
        }

        //root为空，则head不是root的路径
        if (root == null) {
            return false;
        }

        //head节点值和root节点值不同，则说明head不是root的路径
        if (head.val != root.val) {
            return false;
        }

        //判断head的下一个节点是否是root左子树的路径，或者head的下一个节点是否是root右子树的路径
        return contains(head.next, root.left) || contains(head.next, root.right);
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

    private ListNode buildList(int[] data) {
        if (data == null || data.length == 0) {
            return null;
        }

        ListNode head = new ListNode(data[0]);
        ListNode node = head;

        for (int i = 1; i < data.length; i++) {
            node.next = new ListNode(data[i]);
            node = node.next;
        }

        return head;
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
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
