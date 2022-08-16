package com.zhang.java;


/**
 * @Date 2022/5/17 11:20
 * @Author zsy
 * @Description 回文链表 类比Problem5、Problem131、Problem674
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
     * 快慢指针找到中间节点，并翻转前半部分链表，得到的两个链表进行比较，判断是否是回文链表
     * 在并发环境下，函数运行时需要锁定其他线程或进程对链表的访问，因为在函数执行过程中链表会被修改
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
        ListNode pre = null;
        ListNode cur = slow;

        //找中间节点，并翻转前半部分链表
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            //反转链表
            cur.next = pre;
            pre = cur;
            cur = slow;
        }

        //前半部分链表处理，断开链表
        if (fast.next == null) {
            slow = slow.next;
        } else {
            slow = slow.next;
            cur.next = pre;
            pre = cur;
        }

        //两链表进行比较，判断是否是回文链表
        while (pre != null && slow != null) {
            if (pre.val != slow.val) {
                return false;
            }

            pre = pre.next;
            slow = slow.next;
        }

        return true;
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
