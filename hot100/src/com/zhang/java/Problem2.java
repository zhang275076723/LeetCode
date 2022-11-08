package com.zhang.java;

/**
 * @Date 2022/4/11 8:02
 * @Author zsy
 * @Description 两数相加 类比Problem66、Problem67、Problem369、Problem415
 * 给你两个 非空 的链表，表示两个非负的整数。
 * 它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
 * 请你将两个数相加，并以相同形式返回一个表示和的链表。
 * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 * <p>
 * 输入：l1 = [2,4,3], l2 = [5,6,4]
 * 输出：[7,0,8]
 * 解释：342 + 465 = 807.
 * <p>
 * 输入：l1 = [0], l2 = [0]
 * 输出：[0]
 * <p>
 * 输入：l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
 * 输出：[8,9,9,9,0,0,0,1]
 * <p>
 * 每个链表中的节点数在范围 [1, 100] 内
 * 0 <= Node.val <= 9
 * 题目数据保证列表表示的数字不含前导零
 */
public class Problem2 {
    public static void main(String[] args) {
        Problem2 problem2 = new Problem2();
        int[] data1 = {2, 4, 3};
        int[] data2 = {5, 6, 4};
        ListNode l1 = problem2.buildList(data1);
        ListNode l2 = problem2.buildList(data2);
        ListNode node = problem2.addTwoNumbers(l1, l2);
    }

    /**
     * 模拟
     * 模拟过程类似归并排序
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }

        if (l2 == null) {
            return l1;
        }

        //设置头结点，方便处理
        ListNode head = new ListNode();
        ListNode node = head;
        //进位
        int carry = 0;
        //当前位之和
        int sum;

        while (l1 != null && l2 != null) {
            sum = l1.val + l2.val + carry;

            if (sum >= 10) {
                sum = sum - 10;
                carry = 1;
            } else {
                carry = 0;
            }

            node.next = new ListNode(sum);
            node = node.next;
            l1 = l1.next;
            l2 = l2.next;
        }

        while (l1 != null) {
            sum = l1.val + carry;

            if (sum >= 10) {
                sum = sum - 10;
                carry = 1;
            } else {
                carry = 0;
            }

            node.next = new ListNode(sum);
            node = node.next;
            l1 = l1.next;
        }

        while (l2 != null) {
            sum = l2.val + carry;

            if (sum >= 10) {
                sum = sum - 10;
                carry = 1;
            } else {
                carry = 0;
            }

            node.next = new ListNode(sum);
            node = node.next;
            l2 = l2.next;
        }

        //最后一位进位处理
        if (carry != 0) {
            node.next = new ListNode(carry);
        }

        return head.next;
    }

    private ListNode buildList(int[] data) {
        if (data == null || data.length == 0) {
            return null;
        }

        //不能使用Arrays.asList(data)，因为需要传入引用类型的data才能转换为list，
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
