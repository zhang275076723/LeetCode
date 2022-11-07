package com.zhang.zhang;

/**
 * @Date 2022/11/7 09:45
 * @Author zsy
 * @Description 移除链表元素
 * 给你一个链表的头节点 head 和一个整数 val ，请你删除链表中所有满足 Node.val == val 的节点，并返回 新的头节点 。
 * <p>
 * 输入：head = [1,2,6,3,4,5,6], val = 6
 * 输出：[1,2,3,4,5]
 * <p>
 * 输入：head = [], val = 1
 * 输出：[]
 * <p>
 * 输入：head = [7,7,7,7], val = 7
 * 输出：[]
 * <p>
 * 列表中的节点数目在范围 [0, 10^4] 内
 * 1 <= Node.val <= 50
 * 0 <= val <= 50
 */
public class Problem203 {
    public static void main(String[] args) {
        Problem203 problem203 = new Problem203();
        int[] data = {1, 2, 6, 3, 4, 5, 6};
        ListNode head = problem203.buildList(data);
        int val = 6;
        head = problem203.removeElements(head, val);
    }

    /**
     * 保存当前节点的前一个节点，用于删除当前节点
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param head
     * @param val
     * @return
     */
    public ListNode removeElements(ListNode head, int val) {
        if (head == null) {
            return null;
        }

        //设置头结点，方便第一个节点的删除
        ListNode hair = new ListNode(Integer.MAX_VALUE);
        hair.next = head;

        ListNode node = hair;

        while (node.next != null) {
            if (node.next.val == val) {
                node.next = node.next.next;
            } else {
                node = node.next;
            }
        }

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
