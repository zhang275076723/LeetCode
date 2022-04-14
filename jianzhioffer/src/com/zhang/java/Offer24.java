package com.zhang.java;

/**
 * @Date 2022/3/19 10:43
 * @Author zsy
 * @Description 定义一个函数，输入一个链表的头节点，反转该链表并输出反转后链表的头节点
 * <p>
 * 输入: 1->2->3->4->5->NULL
 * 输出: 5->4->3->2->1->NULL
 */
public class Offer24 {
    public static void main(String[] args) {
        Offer24 offer24 = new Offer24();
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = null;
//        ListNode node = offer24.reverseList(node1);
        ListNode node = offer24.reverseList2(node1);
        while (node != null) {
            System.out.println(node.val);
            node = node.next;
        }
    }

    /**
     * 非递归反转链表
     *
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode preNode = null;
        ListNode node = head;
        ListNode nextNode = node.next;
        while (nextNode != null) {
            node.next = preNode;
            preNode = node;
            node = nextNode;
            nextNode = nextNode.next;
        }
        node.next = preNode;
        return node;
    }

    /**
     * 递归反转链表
     *
     * @param head
     * @return
     */
    public ListNode reverseList2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode node = reverseList2(head.next);
        head.next.next = head;
        head.next = null;
        return node;
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
