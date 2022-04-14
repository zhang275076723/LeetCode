package com.zhang.java;

/**
 * @Date 2022/3/18 19:13
 * @Author zsy
 * @Description 输入一个链表，输出该链表中倒数第k个节点。
 * 为了符合大多数人的习惯，本题从1开始计数，即链表的尾节点是倒数第1个节点。
 * 例如，一个链表有 6 个节点，从头节点开始，它们的值依次是 1、2、3、4、5、6。这个链表的倒数第 3 个节点是值为 4 的节点。
 * <p>
 * 给定一个链表: 1->2->3->4->5, 和 k = 2.
 * 返回链表 4->5.
 */
public class Offer22 {
    public static void main(String[] args) {
        Offer22 offer22 = new Offer22();
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);
        ListNode node6 = new ListNode(6);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;
        node6.next = null;
        ListNode node = offer22.getKthFromEnd(node1, 6);
        while (node != null) {
            System.out.println(node.val);
            node = node.next;
        }
    }

    /**
     * 快慢指针，时间复杂度O(n)，空间复杂的O(1)
     *
     * @param head
     * @param k
     * @return
     */
    public ListNode getKthFromEnd(ListNode head, int k) {
        ListNode fastNode = head;
        ListNode slowNode = head;

        //快指针先走k步
        while (fastNode != null && k > 0) {
            fastNode = fastNode.next;
            k--;
        }

        //当快指针为null时，慢指针即为倒数第k个节点
        while (fastNode != null) {
            fastNode = fastNode.next;
            slowNode = slowNode.next;
        }

        return slowNode;
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
