package com.zhang.java;

/**
 * @Date 2022/3/19 11:19
 * @Author zsy
 * @Description 合并两个排序的链表 类比Offer24 同Problem21
 * 输入两个递增排序的链表，合并这两个链表并使新链表中的节点仍然是递增排序的
 * <p>
 * 输入：1->2->4, 1->3->4
 * 输出：1->1->2->3->4->4
 * <p>
 * 0 <= 链表长度 <= 1000
 */
public class Offer25 {
    public static void main(String[] args) {
        Offer25 offer25 = new Offer25();
        int[] data1 = {1, 2, 4};
        int[] data2 = {1, 3, 4};
        ListNode head1 = offer25.buildList(data1);
        ListNode head2 = offer25.buildList(data2);
//        ListNode head = offer25.mergeTwoLists(head1, head2);
        ListNode head = offer25.mergeTwoLists2(head1, head2);
        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }
    }

    /**
     * 非递归链表合并
     * 时间复杂度O(m+n)，空间复杂的O(1)
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
        ListNode head = new ListNode(-1);
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
     * 递归链表合并
     * 时间复杂度O(m+n)，空间复杂的O(m+n)
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

    private ListNode buildList(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        ListNode head = new ListNode(nums[0]);
        ListNode node = head;

        for (int i = 1; i < nums.length; i++) {
            node.next = new ListNode(nums[i]);
            node = node.next;
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
