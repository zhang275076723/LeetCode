package com.zhang.java;

/**
 * @Date 2022/3/18 15:17
 * @Author zsy
 * @Description 删除链表的节点
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
//        ListNode head = offer18.deleteNode3(node1, node2);
        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }
    }

    /**
     * 非递归删除某一节点
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
        if (head.val == val) {
            return head.next;
        }

        ListNode preNode = head;
        ListNode node = head.next;

        while (node != null) {
            if (node.val == val) {
                preNode.next = node.next;
                break;
            }
            preNode = node;
            node = node.next;
        }

        return head;
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

    /**
     * 将要删除的节点的下一个节点赋值给当前节点，删除下一个节点
     * 时间复杂度O(1)，空间复杂度O(1) (要删除的节点不是最后一个节点)
     * 时间复杂度O(n)，空间复杂度O(1) (要删除的节点是最后一个节点)
     *
     * @param head
     * @param node
     * @return
     */
    public ListNode deleteNode3(ListNode head, ListNode node) {
        if (head == null || node == null) {
            return head;
        }
        if (head == node) {
            return head.next;
        }

        //要删除的节点是最后一个节点，则必须遍历，时间复杂度O(n)
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
