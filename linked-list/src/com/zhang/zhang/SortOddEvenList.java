package com.zhang.zhang;


/**
 * @Date 2023/5/9 08:55
 * @Author zsy
 * @Description 排序奇升偶降链表 字节面试题 类比Problem21、Problem143、Problem206、Problem328
 * 给定一个奇数位升序，偶数位降序的链表，将其重新排序，实现链表从小到大。
 * <p>
 * 输入: 1->8->3->6->5->4->7->2->NULL
 * 输出: 1->2->3->4->5->6->7->8->NULL
 */
public class SortOddEvenList {
    public static void main(String[] args) {
        SortOddEvenList sortOddEvenList = new SortOddEvenList();
        int[] data = {1, 8, 3, 6, 5, 4, 7, 2};
        ListNode head = sortOddEvenList.buildList(data);
        head = sortOddEvenList.sortOddEvenList(head);
    }

    /**
     * 1、拆分奇偶链表
     * 2、反转偶链表
     * 3、合并两个有序链表
     * 时间复杂度O(n)，空间复杂度O(n) (递归反转链表的空间复杂度为O(n))
     *
     * @param head
     * @return
     */
    public ListNode sortOddEvenList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        //偶链表头
        ListNode evenHead = head.next;
        ListNode node = head;

        //1、拆分奇偶链表
        while (node.next != null && node.next.next != null) {
            ListNode next = node.next;
            node.next = next.next;
            next.next = next.next.next;
            node = node.next;
        }

        //奇数链表末尾节点指向null
        node.next = null;

        //2、反转偶链表
        evenHead = reverse(evenHead);

        //3、合并两个链表
        ListNode node1 = head;
        ListNode node2 = evenHead;
        ListNode newHead = new ListNode();
        node = newHead;

        while (node1 != null && node2 != null) {
            if (node1.val < node2.val) {
                node.next = node1;
                node1 = node1.next;
                node = node.next;
            } else {
                node.next = node2;
                node2 = node2.next;
                node = node.next;
            }
        }

        if (node1 != null) {
            node.next = node1;
        } else {
            node.next = node2;
        }

        return newHead.next;
    }

    private ListNode reverse(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode node = reverse(head.next);

        head.next.next = head;
        head.next = null;

        return node;
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
}
