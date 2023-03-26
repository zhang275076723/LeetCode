package com.zhang.zhang;

/**
 * @Date 2022/9/2 7:20
 * @Author zsy
 * @Description 旋转链表 旋转问题类比Problem186、Problem189、Offer58_2 类比Problem143
 * 给你一个链表的头节点 head ，旋转链表，将链表每个节点向右移动 k 个位置。
 * <p>
 * 输入：head = [1,2,3,4,5], k = 2
 * 输出：[4,5,1,2,3]
 * <p>
 * 输入：head = [0,1,2], k = 4
 * 输出：[2,0,1]
 * <p>
 * 链表中节点的数目在范围 [0, 500] 内
 * -100 <= Node.val <= 100
 * 0 <= k <= 2 * 10^9
 */
public class Problem61 {
    public static void main(String[] args) {
        Problem61 problem61 = new Problem61();
        int[] data = {1, 2, 3, 4, 5};
        int k = 2;
        ListNode head = problem61.buildList(data);
        head = problem61.rotateRight(head, k);
    }

    /**
     * 两次遍历
     * 第一次遍历，得到链表长度，确定要移动的步数k=k%len (因为k可能为较大的数，所以必须得到链表长度，取模，把k限制在len范围之内)
     * 第二次遍历，得到要断开链表的位置，将链表断开，再重新连接
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param head
     * @param k
     * @return
     */
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null || k == 0) {
            return head;
        }

        //链表长度
        int len = 0;
        ListNode node = head;

        while (node != null) {
            len++;
            node = node.next;
        }

        k = k % len;

        if (k == 0) {
            return head;
        }

        ListNode fast = head;
        ListNode slow = head;

        //fast指针移动k步
        for (int i = 0; i < k; i++) {
            fast = fast.next;
        }

        //快慢指针找断开链表的位置
        while (fast.next != null) {
            slow = slow.next;
            fast = fast.next;
        }

        ListNode newHead = slow.next;

        //断开链表
        slow.next = null;
        //两个链表重新连接
        fast.next = head;

        return newHead;
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
