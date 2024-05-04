package com.zhang.java;


/**
 * @Date 2022/5/17 11:20
 * @Author zsy
 * @Description 回文链表 字节面试题 链表类比Problem143 回文类比Problem5、Problem9、Problem125、Problem131、Problem132、Problem214、Problem266、Problem267、Problem336、Problem409、Problem479、Problem516、Problem564、Problem647、Problem680、Problem866、Problem1147、Problem1177、Problem1312、Problem1328、Problem1332、Problem1400、Problem1457、Problem1542、Problem1616、Problem1930、Problem2002、Problem2108、Problem2131、Problem2217、Problem2384、Problem2396、Problem2484、Problem2490、Problem2663、Problem2697、Problem2791、Problem3035
 * 给你一个单链表的头节点 head ，请你判断该链表是否为回文链表。
 * 如果是，返回 true ；否则，返回 false 。
 * <p>
 * 输入：head = [1,2,2,1]
 * 输出：true
 * <p>
 * 输入：head = [1,2]
 * 输出：false
 * <p>
 * 链表中节点数目在范围[1, 10^5] 内
 * 0 <= Node.val <= 9
 */
public class Problem234 {
    public static void main(String[] args) {
        Problem234 problem234 = new Problem234();
        int[] data = {1, 2, 2, 1};
        ListNode head = problem234.buildList(data);
        System.out.println(problem234.isPalindrome(head));
    }

    /**
     * 快慢指针找到中间节点，断开链表并翻转后半部分链表，判断两个链表中节点是否相等，
     * 如果有节点不相等，则不是回文链表；如果两个链表中节点都相等，则是回文链表
     * 注意：在并发环境下，函数运行时需要锁定其他线程或进程对链表的访问，因为在函数执行过程中链表会被修改
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param head
     * @return
     */
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }

        ListNode slow = head;
        ListNode fast = head;

        //快慢指针找到中间节点
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        //后半部分链表头
        ListNode head2 = slow.next;
        //断开链表，分为两个链表
        slow.next = null;

        //后半部分链表反转
        head2 = reverse(head2);

        ListNode node1 = head;
        ListNode node2 = head2;

        //两个链表中节点依次比较，判断是否是回文链表
        while (node1 != null && node2 != null) {
            //两个链表中当前节点值不同，则不是回文链表，返回false
            if (node1.val != node2.val) {
                return false;
            } else {
                node1 = node1.next;
                node2 = node2.next;
            }
        }

        //链表遍历结束，则是回文链表，返回true
        return true;
    }

    /**
     * 非递归反转链表
     *
     * @param head
     * @return
     */
    private ListNode reverse(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode pre = null;
        ListNode node = head;
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

        int index = 0;
        ListNode head = new ListNode(data[index++]);
        ListNode node = head;
        while (index < data.length) {
            node.next = new ListNode(data[index++]);
            node = node.next;
        }
        return head;
    }

    private static class ListNode {
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
