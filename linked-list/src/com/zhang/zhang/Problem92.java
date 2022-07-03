package com.zhang.zhang;

/**
 * @Date 2022/6/25 9:31
 * @Author zsy
 * @Description 反转链表 II 类比Problem24、Problem206 百度面试题
 * 给你单链表的头指针 head 和两个整数 left 和 right ，其中 left <= right 。
 * 请你反转从位置 left 到位置 right 的链表节点，返回 反转后的链表 。
 * <p>
 * 输入：head = [1,2,3,4,5], left = 2, right = 4
 * 输出：[1,4,3,2,5]
 * <p>
 * 输入：head = [5], left = 1, right = 1
 * 输出：[5]
 * <p>
 * 链表中节点数目为 n
 * 1 <= n <= 500
 * -500 <= Node.val <= 500
 * 1 <= left <= right <= n
 */
public class Problem92 {
    public static void main(String[] args) {
        Problem92 problem92 = new Problem92();
        int[] data = {1, 2, 3, 4, 5};
        ListNode head = problem92.buildList(data);
//        head = problem92.reverseBetween(head, 2, 4);
        head = problem92.reverseBetween2(head, 2, 4);
        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }
    }

    /**
     * 找到要反转的部分，进行反转，然后连接回原链表
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param head
     * @param left
     * @param right
     * @return
     */
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (head == null || head.next == null) {
            return head;
        }

        //设置头结点，使原先头结点成为普通节点，避免特殊情况的考虑
        ListNode hair = new ListNode(0);
        hair.next = head;

        //要反转的第一个节点的前节点
        ListNode pre = hair;
        for (int i = 1; i < left; i++) {
            pre = pre.next;
        }

        //要反转的第一个节点
        ListNode first = pre.next;
        //要反转的最后一个节点
        ListNode last = first;

        for (int i = left; i < right; i++) {
            last = last.next;
        }

        //要反转的最后一个节点的后节点
        ListNode next = last.next;
        //反转的最后一个节点指向null
        last.next = null;

        //反转部分链表连接
        pre.next = reverse(first);
        first.next = next;

        return hair.next;
    }

    /**
     * 一次遍历，头插法
     * 将要反转部分的每一个节点采用头插法，插入到反转部分的第一个节点位置
     * 例如：1->2->3->4->5->6 left=2, right=5
     * 1->2->(3)->4->5->6
     * 1->3->2->(4)->5->6
     * 1->4->3->2->(5)->6
     * 1->5->4->3->2->6
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param head
     * @param left
     * @param right
     * @return
     */
    public ListNode reverseBetween2(ListNode head, int left, int right) {
        if (head == null || head.next == null) {
            return head;
        }

        //设置头结点，使原先头结点成为普通节点，避免特殊情况的考虑
        ListNode hair = new ListNode(0);
        hair.next = head;

        //要反转的第一个节点的前节点
        ListNode pre = hair;
        for (int i = 1; i < left; i++) {
            pre = pre.next;
        }

        //要反转的第一个节点
        ListNode first = pre.next;
        //当前要插入反转部分第一个节点位置的节点
        ListNode node = first.next;
        //node的下一个节点
        ListNode next;

        //将node插入到反转部分的第一个节点位置
        for (int i = left; i < right; i++) {
            next = node.next;

            node.next = pre.next;
            pre.next = node;
            first.next = next;

            node = next;
        }

        return hair.next;
    }

    private ListNode reverse(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode node = head;
        ListNode pre = null;
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
