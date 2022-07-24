package com.zhang.java;

/**
 * @Date 2022/4/15 12:22
 * @Author zsy
 * @Description 合并两个有序链表 同Offer25 类比Problem24、Problem92、Problem206
 * 将两个升序链表合并为一个新的 升序 链表并返回。
 * 新链表是通过拼接给定的两个链表的所有节点组成的。
 * <p>
 * 输入：l1 = [1,2,4], l2 = [1,3,4]
 * 输出：[1,1,2,3,4,4]
 * <p>
 * 输入：l1 = [], l2 = []
 * 输出：[]
 * <p>
 * 输入：l1 = [], l2 = [0]
 * 输出：[0]
 * <p>
 * 两个链表的节点数目范围是 [0, 50]
 * -100 <= Node.val <= 100
 * l1 和 l2 均按 非递减顺序 排列
 */
public class Problem21 {
    public static void main(String[] args) {
        Problem21 problem21 = new Problem21();
        int[] nums1 = {1, 2, 4};
        int[] nums2 = {1, 3, 4};
        ListNode list1 = problem21.buildList(nums1);
        ListNode list2 = problem21.buildList(nums2);
//        ListNode head = problem021.mergeTwoLists(list1, list2);
        ListNode head = problem21.mergeTwoLists2(list1, list2);
        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }
    }

    /**
     * 非递归合并两个有序链表
     * 时间复杂度O(m+n)，空间复杂度O(1)
     *
     * @param list1
     * @param list2
     * @return
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }

        //设置头指针，方便合并
        ListNode head = new ListNode();
        ListNode node = head;

        //合并
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                node.next = list1;
                list1 = list1.next;
            } else {
                node.next = list2;
                list2 = list2.next;
            }
            node = node.next;
        }

        if (list1 == null) {
            node.next = list2;
        } else {
            node.next = list1;
        }

        return head.next;
    }

    /**
     * 递归合并两个有序链表
     * 时间复杂度O(m+n)，空间复杂度O(m+n)
     *
     * @param list1
     * @param list2
     * @return
     */
    public ListNode mergeTwoLists2(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }

        if (list1.val < list2.val) {
            list1.next = mergeTwoLists2(list1.next, list2);
            return list1;
        } else {
            list2.next = mergeTwoLists2(list1, list2.next);
            return list2;
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
