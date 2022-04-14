package com.zhang.java;

/**
 * @Date 2022/3/18 16:15
 * @Author zsy
 * @Description 给定单向链表的头指针和一个要删除确定在链表中的节点，定义一个函数在O(1)时间内删除该节点。
 */
public class Offer18_2 {
    public static void main(String[] args) {
        Offer18_2 Offer18_2 = new Offer18_2();
        ListNode node1 = new Offer18_2.ListNode(4);
        ListNode node2 = new Offer18_2.ListNode(5);
        ListNode node3 = new Offer18_2.ListNode(1);
        ListNode node4 = new Offer18_2.ListNode(9);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = null;
        ListNode head = Offer18_2.deleteNode(node1, node2);
        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }
    }

    /**
     * 将要删除的节点的下一个节点赋值给当前节点，删除下一个节点
     *
     * @param head
     * @param node
     * @return
     */
    public ListNode deleteNode(ListNode head, ListNode node) {
        if (head == null || node == null) {
            return head;
        }
        if (head == node) {
            return head.next;
        }

        //如果要删除的节点是最后一个节点，则必须遍历，时间复杂度O(n)
        if (node.next == null) {
            ListNode preNode = head;
            while (preNode.next != node) {
                preNode = preNode.next;
            }
            preNode.next = null;
        } else {
            ListNode nextNode = node.next;
            node.val = nextNode.val;
            node.next = nextNode.next;
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
