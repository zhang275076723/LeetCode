package com.zhang.zhang;

/**
 * @Date 2022/12/9 10:38
 * @Author zsy
 * @Description 分隔链表
 * 给你一个链表的头节点 head 和一个特定值 x ，请你对链表进行分隔，使得所有 小于 x 的节点都出现在 大于或等于 x 的节点之前。
 * 你应当 保留 两个分区中每个节点的初始相对位置。
 * <p>
 * 输入：head = [1,4,3,2,5,2], x = 3
 * 输出：[1,2,2,4,3,5]
 * <p>
 * 输入：head = [2,1], x = 2
 * 输出：[1,2]
 * <p>
 * 链表中节点的数目在范围 [0, 200] 内
 * -100 <= Node.val <= 100
 * -200 <= x <= 200
 */
public class Problem86 {
    public static void main(String[] args) {
        Problem86 problem86 = new Problem86();
        int[] data = {1, 4, 3, 2, 5, 2};
        int x = 3;
        ListNode head = problem86.buildList(data);
        head = problem86.partition(head, x);
    }

    /**
     * 使用两个链表
     * 一个链表存储小于x的节点，另一个链表存储大于等于x的节点，最后再将两个链表合并，得到最终链表
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param head
     * @param x
     * @return
     */
    public ListNode partition(ListNode head, int x) {
        if (head == null) {
            return null;
        }

        //小于x的链表，设置头结点
        ListNode head1 = new ListNode();
        //大于等于x的链表，设置头结点
        ListNode head2 = new ListNode();
        ListNode node1 = head1;
        ListNode node2 = head2;
        ListNode node = head;

        while (node != null) {
            if (node.val < x) {
                node1.next = node;
                node1 = node1.next;
                node = node.next;
            } else {
                node2.next = node;
                node2 = node2.next;
                node = node.next;
            }
        }

        //大于等于x的链表末尾指向null，避免指向之前链表其他节点
        node2.next = null;

        node1.next = head2.next;

        return head1.next;
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
