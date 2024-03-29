package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2022/3/13 14:56
 * @Author zsy
 * @Description 从尾到头打印链表
 * 输入一个链表的头节点，从尾到头反过来返回每个节点的值（用数组返回）
 * <p>
 * 输入：head = [1,3,2,4]
 * 输出：[4,2,3,1]
 * <p>
 * 0 <= 链表长度 <= 10000
 */
public class Offer6 {
    public static void main(String[] args) {
        Offer6 offer6 = new Offer6();
        int[] data = {1, 3, 2, 4};
        ListNode head = offer6.buildList(data);
        System.out.println(Arrays.toString(offer6.reversePrint(head)));
        System.out.println(Arrays.toString(offer6.reversePrint2(head)));
    }

    /**
     * 先计算链表长度，再遍历链表，每次得到的值是数组末尾元素
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param head
     * @return
     */
    public int[] reversePrint(ListNode head) {
        if (head == null) {
            return new int[0];
        }

        ListNode node = head;
        int length = 0;

        while (node != null) {
            length++;
            node = node.next;
        }

        int[] result = new int[length];
        node = head;

        for (int i = length - 1; i >= 0; i--) {
            result[i] = node.val;
            node = node.next;
        }

        return result;
    }

    /**
     * 非递归反转链表，再顺序遍历反转之后的链表
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param head
     * @return
     */
    public int[] reversePrint2(ListNode head) {
        if (head == null) {
            return new int[0];
        }

        if (head.next == null) {
            return new int[]{head.val};
        }

        int length = 1;
        ListNode node = head;
        ListNode pre = null;
        ListNode next = head.next;

        //反转链表
        while (node.next != null) {
            next = node.next;
            node.next = pre;
            pre = node;
            node = next;
            length++;
        }

        node.next = pre;

        int[] result = new int[length];

        for (int i = 0; i < length; i++) {
            result[i] = node.val;
            node = node.next;
        }

        return result;
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

        ListNode(int val) {
            this.val = val;
        }
    }
}

