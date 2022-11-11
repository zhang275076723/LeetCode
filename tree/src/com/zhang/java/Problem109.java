package com.zhang.java;

/**
 * @Date 2022/11/11 12:00
 * @Author zsy
 * @Description 有序链表转换二叉搜索树 类比Problem108
 * 给定一个单链表的头节点 head ，其中的元素 按升序排序 ，将其转换为高度平衡的二叉搜索树。
 * 本题中，一个高度平衡二叉树是指一个二叉树每个节点 的左右两个子树的高度差不超过 1。
 * <p>
 * 输入: head = [-10,-3,0,5,9]
 * 输出: [0,-3,9,-10,null,5]
 * 解释: 一个可能的答案是[0，-3,9，-10,null,5]，它表示所示的高度平衡的二叉搜索树。
 * <p>
 * 输入: head = []
 * 输出: []
 * <p>
 * head 中的节点数在[0, 2 * 10^4] 范围内
 * -10^5 <= Node.val <= 10^5
 */
public class Problem109 {
    public static void main(String[] args) {
        Problem109 problem109 = new Problem109();
        int[] nums = {-10, -3, 0, 5, 9};
        ListNode head = problem109.buildList(nums);
        TreeNode root = problem109.sortedListToBST(head);
    }

    /**
     * 分治法
     * 时间复杂度O(nlogn)，空间复杂度O(logn)
     *
     * @param head
     * @return
     */
    public TreeNode sortedListToBST(ListNode head) {
        if (head == null) {
            return null;
        }

        return buildTree(head);
    }

    private TreeNode buildTree(ListNode head) {
        if (head == null) {
            return null;
        }

        if (head.next == null) {
            return new TreeNode(head.val);
        }

        //slow节点的前驱节点
        ListNode pre = head;
        ListNode slow = head;
        ListNode fast = head;

        //双指针找中间节点作为根节点
        while (fast != null && fast.next != null) {
            pre = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        //前半部分链表断开
        pre.next = null;
        //后半部分链表头
        ListNode newHead = slow.next;
        //后半部分链表断开
        slow.next = null;

        TreeNode root = new TreeNode(slow.val);
        root.left = buildTree(head);
        root.right = buildTree(newHead);

        return root;
    }

    private ListNode buildList(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        ListNode head = new ListNode(nums[0]);
        ListNode node = head;

        for (int i = 1; i < nums.length; i++) {
            node.next = new ListNode(nums[i]);
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
