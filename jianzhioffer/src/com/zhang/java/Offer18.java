package com.zhang.java;

/**
 * @Date 2022/3/18 15:17
 * @Author zsy
 * @Description 删除链表的节点 类比Problem19、Problem237、Offer22 同Problem203
 * 给定单向链表的头指针和一个要删除的节点的值，定义一个函数删除该节点。
 * 返回删除后的链表的头节点。链表中节点的值互不相同
 * <p>
 * 输入: head = [4,5,1,9], val = 5
 * 输出: [4,1,9]
 * 解释: 给定你链表中值为5的第二个节点，那么在调用了你的函数之后，该链表应变为 4 -> 1 -> 9.
 * <p>
 * 输入: head = [4,5,1,9], val = 1
 * 输出: [4,5,9]
 * 解释: 给定你链表中值为1的第三个节点，那么在调用了你的函数之后，该链表应变为 4 -> 5 -> 9.
 * <p>
 * 题目保证链表中节点的值互不相同
 * 若使用 C 或 C++ 语言，你不需要 free 或 delete 被删除的节点
 */
public class Offer18 {
    public static void main(String[] args) {
        Offer18 offer18 = new Offer18();
        ListNode node1 = new ListNode(4);
        ListNode node2 = new ListNode(5);
        ListNode node3 = new ListNode(1);
        ListNode node4 = new ListNode(9);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = null;
        ListNode head = offer18.deleteNode(node1, 5);
//        ListNode head = offer18.deleteNode2(node1, 5);
        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }
    }

    /**
     * 非递归删除某一节点
     * 保存当前节点的前一个节点，用于删除当前节点
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param head
     * @param val
     * @return
     */
    public ListNode deleteNode(ListNode head, int val) {
        if (head == null) {
            return null;
        }

        //设置头结点，方便处理
        ListNode hair = new ListNode();
        hair.next = head;

        ListNode node = hair;

        while (node.next != null) {
            //找到要删除的节点
            if (node.next.val == val) {
                node.next = node.next.next;
                break;
            }

            node = node.next;
        }

        return hair.next;
    }

    /**
     * 递归删除某一节点
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param head
     * @param val
     * @return
     */
    public ListNode deleteNode2(ListNode head, int val) {
        if (head == null) {
            return null;
        }

        if (head.val == val) {
            return head.next;
        }

        head.next = deleteNode2(head.next, val);

        return head;
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int x) {
            val = x;
        }
    }
}
