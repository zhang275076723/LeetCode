package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2022/3/13 14:56
 * @Author zsy
 * @Description 输入一个链表的头节点，从尾到头反过来返回每个节点的值（用数组返回）
 * 输入：head = [1,3,2,4]
 * 输出：[4,2,3,1]
 */
public class Offer6 {
    public static void main(String[] args) {
        Offer6 offer6 = new Offer6();
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(3);
        ListNode node3 = new ListNode(2);
        ListNode node4 = new ListNode(4);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = null;
        System.out.println(Arrays.toString(offer6.reversePrint(node1)));
    }

    /**
     * 非递归
     *
     * @param head
     * @return
     */
    public int[] reversePrint(ListNode head) {
        ListNode node = head;
        int len = 0;
        int[] arr;

        while (node != null) {
            len++;
            node = node.next;
        }

        arr = new int[len];
        node = head;

        for (int i = len - 1; i >= 0; i--) {
            arr[i] = node.val;
            node = node.next;
        }
        return arr;
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }
}

