package com.zhang.zhang;

/**
 * @Date 2025/2/14 08:17
 * @Author zsy
 * @Description 删除链表的中间节点 类比Problem143、Problem876
 * 给你一个链表的头节点 head 。
 * 删除 链表的 中间节点 ，并返回修改后的链表的头节点 head 。
 * 长度为 n 链表的中间节点是从头数起第 ⌊n / 2⌋ 个节点（下标从 0 开始），其中 ⌊x⌋ 表示小于或等于 x 的最大整数。
 * 对于 n = 1、2、3、4 和 5 的情况，中间节点的下标分别是 0、1、1、2 和 2 。
 * <p>
 * 输入：head = [1,3,4,7,1,2,6]
 * 输出：[1,3,4,1,2,6]
 * 解释：
 * 上图表示给出的链表。节点的下标分别标注在每个节点的下方。
 * 由于 n = 7 ，值为 7 的节点 3 是中间节点，用红色标注。
 * 返回结果为移除节点后的新链表。
 * <p>
 * 输入：head = [1,2,3,4]
 * 输出：[1,2,4]
 * 解释：
 * 上图表示给出的链表。
 * 对于 n = 4 ，值为 3 的节点 2 是中间节点，用红色标注。
 * <p>
 * 输入：head = [2,1]
 * 输出：[2]
 * 解释：
 * 上图表示给出的链表。
 * 对于 n = 2 ，值为 1 的节点 1 是中间节点，用红色标注。
 * 值为 2 的节点 0 是移除节点 1 后剩下的唯一一个节点。
 * <p>
 * 链表中节点的数目在范围 [1, 10^5] 内
 * 1 <= Node.val <= 10^5
 */
public class Problem2095 {
    public static void main(String[] args) {
        Problem2095 problem2095 = new Problem2095();
        int[] data = {1, 3, 4, 7, 1, 2, 6};
        ListNode head = problem2095.buildList(data);
        head = problem2095.deleteMiddle(head);
    }

    /**
     * 快慢指针
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param head
     * @return
     */
    public ListNode deleteMiddle(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }

        //设置头结点，方便处理特殊情况
        ListNode hair = new ListNode();
        hair.next = head;

        ListNode slow = head;
        ListNode fast = head;
        //slow节点的前驱节点
        ListNode preSlow = head;

        while (fast != null && fast.next != null) {
            preSlow = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        //slow即为要删除的节点
        preSlow.next = slow.next;

        return hair.next;
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
