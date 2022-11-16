package com.zhang.zhang;

/**
 * @Date 2022/11/15 08:22 类比Problem2、Problem66、Problem67、Problem369、Problem415
 * @Author zsy
 * @Description 两数相加 II
 * 给你两个 非空 链表来代表两个非负整数。数字最高位位于链表开始位置。
 * 它们的每个节点只存储一位数字。将这两数相加会返回一个新的链表。
 * 你可以假设除了数字 0 之外，这两个数字都不会以零开头。
 * <p>
 * 输入：l1 = [7,2,4,3], l2 = [5,6,4]
 * 输出：[7,8,0,7]
 * <p>
 * 输入：l1 = [2,4,3], l2 = [5,6,4]
 * 输出：[8,0,7]
 * <p>
 * 输入：l1 = [0], l2 = [0]
 * 输出：[0]
 * <p>
 * 链表的长度范围为 [1, 100]
 * 0 <= node.val <= 9
 * 输入数据保证链表代表的数字无前导 0
 */
public class Problem445 {
    public static void main(String[] args) {
        Problem445 problem445 = new Problem445();
        int[] data1 = {7, 2, 4, 3};
        int[] data2 = {5, 6, 4};
        ListNode l1 = problem445.buildList(data1);
        ListNode l2 = problem445.buildList(data2);
        ListNode head = problem445.addTwoNumbers(l1, l2);
    }

    /**
     * 模拟
     * 反转两个链表，再逐一相加
     * 时间复杂度O(n)，空间复杂度O(1) (非递归反转的空间复杂度O(1)，递归反转的空间复杂度O(n))
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

        //翻转链表，便于相加操作
        ListNode head1 = reverse(l1);
        ListNode head2 = reverse(l2);

        ListNode node1 = head1;
        ListNode node2 = head2;
        //当前位的进位
        int carry = 0;
        //当前位之和
        int sum;

        while (node1 != null && node2 != null) {
            sum = node1.val + node2.val + carry;

            if (sum > 9) {
                carry = sum / 10;
                node.next = new ListNode(sum % 10);
            } else {
                carry = 0;
                node.next = new ListNode(sum);
            }

            node1 = node1.next;
            node2 = node2.next;
            node = node.next;
        }

        while (node1 != null) {
            sum = node1.val + carry;

            if (sum > 9) {
                carry = sum / 10;
                node.next = new ListNode(sum % 10);
            } else {
                carry = 0;
                node.next = new ListNode(sum);
            }

            node1 = node1.next;
            node = node.next;
        }

        while (node2 != null) {
            sum = node2.val + carry;

            if (sum > 9) {
                carry = sum / 10;
                node.next = new ListNode(sum % 10);
            } else {
                carry = 0;
                node.next = new ListNode(sum);
            }

            node2 = node2.next;
            node = node.next;
        }

        //最高位进位处理
        if (carry != 0) {
            node.next = new ListNode(carry);
        }

        //之前反转了链表，所以需要再次反转，得到结果链表
        return reverse(head.next);
    }

    /**
     * 非递归反转链表
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param head
     * @return
     */
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

    /**
     * 递归反转链表
     * 时间复杂度O(n)，空间复杂度O(n)
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
