package com.zhang.java;

import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2022/5/5 11:20
 * @Author zsy
 * @Description 环形链表 类比Problem142、Problem160、Problem202、Problem457、Problem565、Offer52
 * 给你一个链表的头节点 head ，判断链表中是否有环。
 * 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。
 * 为了表示给定链表中的环，评测系统内部使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。
 * 注意：pos 不作为参数进行传递 。仅仅是为了标识链表的实际情况。
 * 如果链表中存在环 ，则返回 true 。 否则，返回 false 。
 * <p>
 * 输入：head = [3,2,0,-4], pos = 1
 * 输出：true
 * 解释：链表中有一个环，其尾部连接到第二个节点。
 * <p>
 * 输入：head = [1,2], pos = 0
 * 输出：true
 * 解释：链表中有一个环，其尾部连接到第一个节点。
 * <p>
 * 输入：head = [1], pos = -1
 * 输出：false
 * 解释：链表中没有环。
 * <p>
 * 链表中节点的数目范围是 [0, 10^4]
 * -10^5 <= Node.val <= 10^5
 * pos 为 -1 或者链表中的一个 有效索引 。
 */
public class Problem141 {
    public static void main(String[] args) {
        Problem141 problem141 = new Problem141();
        ListNode node1 = new ListNode(3);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(0);
        ListNode node4 = new ListNode(-4);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node2;
        System.out.println(problem141.hasCycle(node1));
        System.out.println(problem141.hasCycle2(node1));
    }

    /**
     * 哈希表
     * 将节点放入哈希表中，判断当前节点是否在哈希表中
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }

        Set<ListNode> set = new HashSet<>();
        ListNode node = head;

        while (node != null) {
            //当前节点在set中，则存在环，返回true
            if (set.contains(node)) {
                return true;
            }

            set.add(node);
            node = node.next;
        }

        //遍历结束，则不存在环，返回false
        return false;
    }

    /**
     * 快慢指针
     * 快指针一次移动2步，慢指针一次移动1步，如果两指针相遇则说明有环
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param head
     * @return
     */
    public boolean hasCycle2(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }

        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            //快慢指针相遇，则存在环，返回true
            if (slow == fast) {
                return true;
            }
        }

        //遍历结束，则不存在环，返回false
        return false;
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }
}
