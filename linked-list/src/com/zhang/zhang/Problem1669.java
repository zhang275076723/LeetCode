package com.zhang.zhang;

/**
 * @Date 2023/4/14 08:47
 * @Author zsy
 * @Description 合并两个链表 类比Problem21、Offer25
 * 给你两个链表 list1 和 list2 ，它们包含的元素分别为 n 个和 m 个。
 * 请你将 list1 中下标从 a 到 b 的全部节点都删除，并将list2 接在被删除节点的位置。
 * 下图中蓝色边和节点展示了操作后的结果：
 * 请你返回结果链表的头指针。
 * <p>
 * 输入：list1 = [0,1,2,3,4,5], a = 3, b = 4, list2 = [1000000,1000001,1000002]
 * 输出：[0,1,2,1000000,1000001,1000002,5]
 * 解释：我们删除 list1 中下标为 3 和 4 的两个节点，并将 list2 接在该位置。上图中蓝色的边和节点为答案链表。
 * <p>
 * 输入：list1 = [0,1,2,3,4,5,6], a = 2, b = 5, list2 = [1000000,1000001,1000002,1000003,1000004]
 * 输出：[0,1,1000000,1000001,1000002,1000003,1000004,6]
 * 解释：上图中蓝色的边和节点为答案链表。
 * <p>
 * 3 <= list1.length <= 10^4
 * 1 <= a <= b < list1.length - 1
 * 1 <= list2.length <= 10^4
 */
public class Problem1669 {
    public static void main(String[] args) {
        Problem1669 problem1669 = new Problem1669();
        int[] data1 = {0, 1, 2, 3, 4, 5, 6};
        int[] data2 = {1000000, 1000001, 1000002, 1000003, 1000004};
        int a = 2;
        int b = 5;
        ListNode list1 = problem1669.buildList(data1);
        ListNode list2 = problem1669.buildList(data2);
        ListNode head = problem1669.mergeInBetween(list1, a, b, list2);
    }

    /**
     * 找到list1要断开链表的位置，连接上另一个链表list2
     * 时间复杂度O(m+n)，空间复杂度O(1)
     *
     * @param list1
     * @param a
     * @param b
     * @param list2
     * @return
     */
    public ListNode mergeInBetween(ListNode list1, int a, int b, ListNode list2) {
        ListNode head = new ListNode();
        head.next = list1;
        //要断开链表的位置a的前一个节点
        ListNode node1 = head;
        //要断开链表的位置b的下一个节点
        ListNode node2 = head;

        for (int i = 0; i < a; i++) {
            node1 = node1.next;
            node2 = node2.next;
        }

        node2 = node2.next;

        for (int i = 0; i < b - a; i++) {
            node2 = node2.next;
        }

        node2 = node2.next;

        //list2的末尾节点
        ListNode node3 = list2;

        while (node3.next != null) {
            node3 = node3.next;
        }

        //链表list1连接另一个链表list2
        node1.next = list2;
        node3.next = node2;

        return head.next;
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
