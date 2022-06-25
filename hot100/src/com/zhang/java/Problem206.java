package com.zhang.java;

/**
 * @Date 2022/5/13 8:48
 * @Author zsy
 * @Description 反转链表 类比Problem92
 * 给你单链表的头节点 head ，请你反转链表，并返回反转后的链表。
 * <p>
 * 输入：head = [1,2,3,4,5]
 * 输出：[5,4,3,2,1]
 * <p>
 * 输入：head = [1,2]
 * 输出：[2,1]
 * <p>
 * 输入：head = []
 * 输出：[]
 * <p>
 * 链表中节点的数目范围是 [0, 5000]
 * -5000 <= Node.val <= 5000
 */
public class Problem206 {
    public static void main(String[] args) {
        Problem206 problem206 = new Problem206();
        int[] data = {1, 2, 3, 4, 5};
        ListNode head = problem206.buildLinkedList(data);
//        head = problem206.reverseList(head);
        head = problem206.reverseList2(head);
        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }
    }

    /**
     * 非递归反转链表
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode node = head;
        ListNode nextNode = node.next;
        ListNode preNode = null;
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
     * 时间复杂度O(n)，空间复杂度O(n)
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

    private ListNode buildLinkedList(int[] data) {
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

    private static class ListNode {
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
