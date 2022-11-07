package com.zhang.java;

/**
 * @Date 2022/4/15 10:10
 * @Author zsy
 * @Description 删除链表的倒数第 N 个结点 类比Problem237、Problem876、Offer18、Offer22
 * 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
 * <p>
 * 输入：head = [1,2,3,4,5], n = 2
 * 输出：[1,2,3,5]
 * <p>
 * 输入：head = [1], n = 1
 * 输出：[]
 * <p>
 * 输入：head = [1,2], n = 1
 * 输出：[1]
 * <p>
 * 链表中结点的数目为 sz
 * 1 <= sz <= 30
 * 0 <= Node.val <= 100
 * 1 <= n <= sz
 */
public class Problem19 {
    public static void main(String[] args) {
        Problem19 problem19 = new Problem19();
        int[] data = {1, 2, 3, 4, 5};
        ListNode head = problem19.buildList(data);
        ListNode node = problem19.removeNthFromEnd(head, 5);
        while (node != null) {
            System.out.println(node.val);
            node = node.next;
        }
    }

    /**
     * 双指针，快指针先走n步
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null || head.next == null) {
            return null;
        }

        //设置头结点，把第一个节点作为普通节点，方便处理
        ListNode hair = new ListNode(0, head);

        ListNode fast = hair;
        ListNode slow = hair;

        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }

        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }

        ListNode deleteNode = slow.next;
        slow.next = deleteNode.next;
        deleteNode.next = null;

        return hair.next;
    }

    public ListNode buildList(int[] data) {
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
}
