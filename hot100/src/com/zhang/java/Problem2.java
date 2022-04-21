package com.zhang.java;

/**
 * @Date 2022/4/11 8:02
 * @Author zsy
 * @Description 给你两个 非空 的链表，表示两个非负的整数。
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
        ListNode l1 = new ListNode(9);
        ListNode l2 = new ListNode(9);
        l1.next = new ListNode(9);
        l1.next.next = new ListNode(9);
        l1.next.next.next = new ListNode(9);
        l1.next.next.next.next = new ListNode(9);
        l1.next.next.next.next.next = new ListNode(9);
        l1.next.next.next.next.next.next = new ListNode(9);
        l2.next = new ListNode(9);
        l2.next.next = new ListNode(9);
        l2.next.next.next = new ListNode(9);
        ListNode node = problem2.addTwoNumbers(l1, l2);
        int result = 0;
        int factor = 1;
        while (node != null) {
            result = result + node.val * factor;
            node = node.next;
            factor = factor * 10;
        }
        System.out.println(result);
    }

    /**
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        //找到较长的链表作为主链表
        ListNode head;
        ListNode head1 = l1;
        ListNode head2 = l2;
        while (head1 != null && head2 != null) {
            head1 = head1.next;
            head2 = head2.next;
        }
        if (head1 != null) {
            head = l1;
        } else {
            head = l2;
        }

        //当前位的进位
        int carry = 0;
        head1 = l1;
        head2 = l2;
        ListNode headNode = head;
        ListNode headPreNode = null;

        //两个链表公共长度部分相加
        while (head1 != null && head2 != null) {
            //当前位的值
            int temp = head1.val + head2.val + carry;
            if (temp >= 10) {
                headNode.val = temp - 10;
                carry = 1;
            } else {
                headNode.val = temp;
                carry = 0;
            }

            headPreNode = headNode;
            headNode = headNode.next;
            head1 = head1.next;
            head2 = head2.next;
        }

        //较长链表处理
        while (carry == 1 && headNode != null) {
            //当前位的值
            int temp = headNode.val + carry;
            if (temp >= 10) {
                headNode.val = temp - 10;
            } else {
                headNode.val = temp;
                carry = 0;
                break;
            }

            headPreNode = headNode;
            headNode = headNode.next;
        }

        //最后一位进位处理
        if (carry == 1) {
            headPreNode.next = new ListNode(carry);
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
