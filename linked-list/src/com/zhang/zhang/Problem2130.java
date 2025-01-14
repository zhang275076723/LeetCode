package com.zhang.zhang;

/**
 * @Date 2025/2/15 08:52
 * @Author zsy
 * @Description 链表最大孪生和 类比Problem143、Problem234、SortOddEvenList
 * 在一个大小为 n 且 n 为 偶数 的链表中，对于 0 <= i <= (n / 2) - 1 的 i ，
 * 第 i 个节点（下标从 0 开始）的孪生节点为第 (n-1-i) 个节点 。
 * 比方说，n = 4 那么节点 0 是节点 3 的孪生节点，节点 1 是节点 2 的孪生节点。
 * 这是长度为 n = 4 的链表中所有的孪生节点。
 * 孪生和 定义为一个节点和它孪生节点两者值之和。
 * 给你一个长度为偶数的链表的头节点 head ，请你返回链表的 最大孪生和 。
 * <p>
 * 输入：head = [5,4,2,1]
 * 输出：6
 * 解释：
 * 节点 0 和节点 1 分别是节点 3 和 2 的孪生节点。孪生和都为 6 。
 * 链表中没有其他孪生节点。
 * 所以，链表的最大孪生和是 6 。
 * <p>
 * 输入：head = [4,2,2,3]
 * 输出：7
 * 解释：
 * 链表中的孪生节点为：
 * - 节点 0 是节点 3 的孪生节点，孪生和为 4 + 3 = 7 。
 * - 节点 1 是节点 2 的孪生节点，孪生和为 2 + 2 = 4 。
 * 所以，最大孪生和为 max(7, 4) = 7 。
 * <p>
 * 输入：head = [1,100000]
 * 输出：100001
 * 解释：
 * 链表中只有一对孪生节点，孪生和为 1 + 100000 = 100001 。
 * <p>
 * 链表的节点数目是 [2, 10^5] 中的 偶数 。
 * 1 <= Node.val <= 10^5
 */
public class Problem2130 {
    public static void main(String[] args) {
        Problem2130 problem2130 = new Problem2130();
        int[] data = {4, 2, 2, 3};
        ListNode head = problem2130.buildList(data);
        System.out.println(problem2130.pairSum(head));
    }

    /**
     * 1、快慢指针找到中间节点，断开形成两个链表
     * 2、反转后半部分链表
     * 3、两个链表中相同位置的节点相加即为孪生和
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param head
     * @return
     */
    public int pairSum(ListNode head) {
        if (head == null) {
            return 0;
        }

        ListNode slow = head;
        ListNode fast = head;

        //1、快慢指针找到中间节点，断开形成两个链表
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        //后半部分链表头
        ListNode head2 = slow.next;
        //断开链表，分为两个链表
        slow.next = null;

        //2、反转后半部分链表
        head2 = reverse(head2);

        int max = Integer.MIN_VALUE;

        ListNode node1 = head;
        ListNode node2 = head2;

        //3、两个链表中相同位置的节点相加即为孪生和
        while (node1 != null && node2 != null) {
            max = Math.max(max, node1.val + node2.val);
            node1 = node1.next;
            node2 = node2.next;
        }

        return max;
    }

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
