package com.zhang.java;

import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2022/5/6 9:33
 * @Author zsy
 * @Description 环形链表 II 类比Problem141
 * 给定一个链表的头节点 head ，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。
 * 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。
 * 为了表示给定链表中的环，评测系统内部使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。
 * 如果 pos 是 -1，则在该链表中没有环。
 * 注意：pos 不作为参数进行传递，仅仅是为了标识链表的实际情况。
 * 不允许修改 链表。
 * <p>
 * 输入：head = [3,2,0,-4], pos = 1
 * 输出：返回索引为 1 的链表节点
 * 解释：链表中有一个环，其尾部连接到第二个节点。
 * <p>
 * 输入：head = [1,2], pos = 0
 * 输出：返回索引为 0 的链表节点
 * 解释：链表中有一个环，其尾部连接到第一个节点。
 * <p>
 * 输入：head = [1], pos = -1
 * 输出：返回 null
 * 解释：链表中没有环。
 * <p>
 * 链表中节点的数目范围在范围 [0, 10^4] 内
 * -10^5 <= Node.val <= 10^5
 * pos 的值为 -1 或者链表中的一个有效索引
 */
public class Problem142 {
    public static void main(String[] args) {
        Problem142 problem142 = new Problem142();
        ListNode node1 = new ListNode(3);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(0);
        ListNode node4 = new ListNode(-4);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node2;
        System.out.println(problem142.detectCycle(node1).val);
        System.out.println(problem142.detectCycle2(node1).val);
    }

    /**
     * 哈希表
     * 将节点放入哈希表中，判断当前节点是否在哈希表中
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param head
     * @return
     */
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }

        Set<ListNode> set = new HashSet<>();

        while (head != null) {
            if (set.contains(head)) {
                return head;
            }
            set.add(head);
            head = head.next;
        }

        return null;
    }

    /**
     * 双指针
     * 快指针一次移动2步，慢指针一次移动1步，如果两指针相遇则说明有环
     * 时间复杂度O(n)，空间复杂度O(1)
     * <p>
     * 3 -> 2 -> 0 -> -4
     * |    ^         |
     * |   丨--------丨
     * a：链表头到链表环的第一个节点的个数(不包含环的第一个节点)
     * b：链表环的节点个数
     * f：快指针走的步数
     * s：慢指针走的步数
     * n：快指针比慢指针多走的几个链表环，
     * 当快慢指针相遇时：
     * f = s + nb(当快慢指针相遇时，快指针比慢指针多走n个链表环的长度)
     * f = 2s(快指针一次走2步，慢指针一次走1步，快指针所走步数是慢指针的2倍)
     * 两者联立，得到：f = 2nb, s = nb(快慢指针分别走了2n个、n个链表环的长度)
     * 从头结点到链表环入口节点需要走a+nb，
     * 当快慢指针相遇时，快指针指向链表头，慢指针不变，两指针每次走1步，当两者重新相遇时，
     * 快指针走了a步，慢指针走了a步，即慢指针共走a+nb，找到了链表环的入口节点
     *
     * @param head
     * @return
     */
    public ListNode detectCycle2(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }

        ListNode fast = head;
        ListNode slow = head;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;

            //快慢指针相遇，说明有环
            if (fast == slow) {
                //快指针重新指向链表头，快慢指针每次走1步，当快慢指针再次相遇时，慢指针指向链表中环的头结点
                fast = head;
                while (fast != slow) {
                    fast = fast.next;
                    slow = slow.next;
                }
                return slow;
            }
        }

        return null;
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
