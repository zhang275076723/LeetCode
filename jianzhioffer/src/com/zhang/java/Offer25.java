package com.zhang.java;

/**
 * @Date 2022/3/19 11:19
 * @Author zsy
 * @Description 输入两个递增排序的链表，合并这两个链表并使新链表中的节点仍然是递增排序的
 * <p>
 * 输入：1->2->4, 1->3->4
 * 输出：1->1->2->3->4->4
 */
public class Offer25 {
    public static void main(String[] args) {
        Offer25 offer25 = new Offer25();
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(4);
        ListNode node4 = new ListNode(1);
        ListNode node5 = new ListNode(3);
        ListNode node6 = new ListNode(4);
        node1.next = node2;
        node2.next = node3;
        node3.next = null;
        node4.next = node5;
        node5.next = node6;
        node6.next = null;
//        ListNode node = offer25.mergeTwoLists(node1, node4);
        ListNode node = offer25.mergeTwoLists2(node1, node4);
        while (node != null) {
            System.out.println(node.val);
            node = node.next;
        }
    }

    /**
     * 非递归链表合并，时间复杂度O(m+n)，空间复杂的O(1)
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }

        //设置头结点，方便链表合并
        ListNode head = new ListNode(Integer.MIN_VALUE);
        ListNode node = head;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                node.next = l1;
                l1 = l1.next;
            } else {
                node.next = l2;
                l2 = l2.next;
            }
            node = node.next;
        }
        if (l1 == null) {
            node.next = l2;
        } else {
            node.next = l1;
        }

        return head.next;
    }

    /**
     * 递归链表合并，时间复杂度O(m+n)，空间复杂的O(m+n)
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }

        if (l1.val < l2.val) {
            l1.next = mergeTwoLists2(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoLists2(l1, l2.next);
            return l2;
        }
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
