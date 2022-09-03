package com.zhang.zhang;

/**
 * @Date 2022/9/3 8:11
 * @Author zsy
 * @Description 删除链表中的节点 类比Offer18
 * 有一个单链表的 head，我们想删除它其中的一个节点 node。
 * 给你一个需要删除的节点 node 。
 * 你将 无法访问 第一个节点 head。
 * 链表的所有值都是 唯一的，并且保证给定的节点 node 不是链表中的最后一个节点。
 * 删除给定的节点。注意，删除节点并不是指从内存中删除它。这里的意思是：
 * 给定节点的值不应该存在于链表中。
 * 链表中的节点数应该减少 1。
 * node 前面的所有值顺序相同。
 * node 后面的所有值顺序相同。
 * <p>
 * 输入：head = [4,5,1,9], node = 5
 * 输出：[4,1,9]
 * 解释：指定链表中值为 5 的第二个节点，那么在调用了你的函数之后，该链表应变为 4 -> 1 -> 9
 * <p>
 * 输入：head = [4,5,1,9], node = 1
 * 输出：[4,5,9]
 * 解释：指定链表中值为 1 的第三个节点，那么在调用了你的函数之后，该链表应变为 4 -> 5 -> 9
 * <p>
 * 链表中节点的数目范围是 [2, 1000]
 * -1000 <= Node.val <= 1000
 * 链表中每个节点的值都是 唯一 的
 * 需要删除的节点 node 是 链表中的节点 ，且 不是末尾节点
 */
public class Problem237 {
    public static void main(String[] args) {
        Problem237 problem237 = new Problem237();
        int[] data = {4, 5, 1, 9};
        ListNode head = problem237.buildList(data);
        ListNode node = head.next;
        problem237.deleteNode(node);
    }

    /**
     * 因为node不是尾节点，所以将node的下一个节点赋值到node中，删除node的下一个节点
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @param node
     */
    public void deleteNode(ListNode node) {
        ListNode next = node.next;
        node.val = next.val;
        node.next = next.next;
        next.next = null;
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

        ListNode(int x) {
            val = x;
        }
    }
}
