package com.zhang.zhang;

/**
 * @Date 2022/11/7 09:57
 * @Author zsy
 * @Description 奇偶链表 字节面试题 类比Problem143
 * 给定单链表的头节点 head ，将所有索引为奇数的节点和索引为偶数的节点分别组合在一起，然后返回重新排序的列表。
 * 第一个节点的索引被认为是 奇数 ， 第二个节点的索引为 偶数 ，以此类推。
 * 请注意，偶数组和奇数组内部的相对顺序应该与输入时保持一致。
 * 你必须在 O(1) 的额外空间复杂度和 O(n) 的时间复杂度下解决这个问题。
 * <p>
 * 输入: head = [1,2,3,4,5]
 * 输出: [1,3,5,2,4]
 * <p>
 * 输入: head = [2,1,3,5,6,4,7]
 * 输出: [2,3,6,7,1,5,4]
 * <p>
 * n == 链表中的节点数
 * 0 <= n <= 10^4
 * -10^6 <= Node.val <= 10^6
 */
public class Problem328 {
    public static void main(String[] args) {
        Problem328 problem328 = new Problem328();
        int[] data = {1, 2, 3, 4};
        ListNode head = problem328.buildList(data);
        head = problem328.oddEvenList(head);
    }

    /**
     * 奇数链表和偶数链表分开，奇数链表后面重新连接偶数链表
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param head
     * @return
     */
    public ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return head;
        }

        //奇数链表头
        ListNode oddHead = head;
        //偶数链表头
        ListNode evenHead = head.next;
        ListNode node = head;

        //断开奇偶链表
        while (node.next != null && node.next.next != null) {
            ListNode next = node.next;
            node.next = next.next;
            next.next = next.next.next;
            node = node.next;
        }

        //奇数链表后面链接偶数链表
        node.next = evenHead;

        return oddHead;
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
