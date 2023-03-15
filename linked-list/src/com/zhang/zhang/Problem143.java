package com.zhang.zhang;


/**
 * @Date 2022/4/20 17:24
 * @Author zsy
 * @Description 重排链表 字节面试题 类比Problem61、Problem92、Problem147、Problem148、Problem206、Problem234、Problem328
 * 给定一个单链表 L 的头节点 head ，单链表 L 表示为：L0 → L1 → … → Ln - 1 → Ln
 * 请将其重新排列后变为：L0 → Ln → L1 → Ln - 1 → L2 → Ln - 2 → …
 * 不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
 * <p>
 * 输入：head = [1,2,3,4]
 * 输出：[1,4,2,3]
 * <p>
 * 输入：head = [1,2,3,4,5]
 * 输出：[1,5,2,4,3]
 * <p>
 * 链表的长度范围为 [1, 5*10^4]
 * 1 <= node.val <= 1000
 */
public class Problem143 {
    public static void main(String[] args) {
        Problem143 problem143 = new Problem143();
        int[] data = {1, 2, 3, 4, 5};
        ListNode head = problem143.buildList(data);
        problem143.reorderList(head);
        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }
    }

    /**
     * 快慢指针找到中间位置，断开形成两个链表，反转后半部分链表，合并前半边链表和后半边链表
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param head
     */
    public void reorderList(ListNode head) {
        if (head == null || head.next == null) {
            return;
        }

        //快慢指针找中间位置
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        //后半部分链表头
        ListNode head2 = slow.next;
        //断开链表，分为两个链表
        slow.next = null;

        //后半边链表反转
        head2 = reverse(head2);

        //前半部分链表和后半部分链表合并
        ListNode node1 = head;
        ListNode node2 = head2;

        while (node1 != null && node2 != null) {
            ListNode nextNode1 = node1.next;
            ListNode nextNode2 = node2.next;
            node1.next = node2;
            node2.next = nextNode1;
            node1 = nextNode1;
            node2 = nextNode2;
        }
    }

    /**
     * 非递归反转链表
     *
     * @param head
     * @return
     */
    private ListNode reverse(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode pre = null;
        ListNode node = head;
        ListNode next = node.next;

        while (next != null) {
            node.next = pre;
            pre = node;
            node = next;
            next = next.next;
        }

        node.next = pre;

        return node;
    }

    /**
     * 递归反转链表
     *
     * @param head
     * @return
     */
    private ListNode reverse2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode node = reverse2(head.next);

        head.next.next = head;
        head.next = null;

        return node;
    }

    private ListNode buildList(int[] data) {
        if (data == null || data.length == 0) {
            return null;
        }

        //不能使用Arrays.asList(data)，因为需要传入引用类型才能转换为list，
        //如果传入基本数据类型，则会将数组对象作为引用放入list中
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
