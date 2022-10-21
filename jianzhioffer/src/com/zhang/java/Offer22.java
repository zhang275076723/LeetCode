package com.zhang.java;

/**
 * @Date 2022/3/18 19:13
 * @Author zsy
 * @Description 链表中倒数第k个节点
 * 输入一个链表，输出该链表中倒数第k个节点。
 * 为了符合大多数人的习惯，本题从1开始计数，即链表的尾节点是倒数第1个节点。
 * 例如，一个链表有 6 个节点，从头节点开始，它们的值依次是 1、2、3、4、5、6。这个链表的倒数第 3 个节点是值为 4 的节点。
 * <p>
 * 给定一个链表: 1->2->3->4->5, 和 k = 2.
 * 返回链表 4->5.
 */
public class Offer22 {
    public static void main(String[] args) {
        Offer22 offer22 = new Offer22();
        int[] data = {1, 2, 3, 4, 5, 6};
        ListNode head = offer22.buildList(data);
        head = offer22.getKthFromEnd(head, 6);
        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }
    }

    /**
     * 双指针
     * 时间复杂度O(n)，空间复杂的O(1)
     *
     * @param head
     * @param k
     * @return
     */
    public ListNode getKthFromEnd(ListNode head, int k) {
        ListNode fast = head;
        ListNode slow = head;

        //快指针先走k步
        for (int i = 0; i < k; i++) {
            fast = fast.next;
        }

        //当快指针为null时，慢指针即为倒数第k个节点
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        return slow;
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

        ListNode(int x) {
            val = x;
        }
    }
}
