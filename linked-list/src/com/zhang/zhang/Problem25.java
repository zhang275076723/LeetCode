package com.zhang.zhang;


/**
 * @Date 2022/4/18 14:39
 * @Author zsy
 * @Description K 个一组翻转链表 字节面试题 类比Problem21、Problem23、Problem24、Problem92、Problem206
 * 给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。
 * k 是一个正整数，它的值小于或等于链表的长度。
 * 如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
 * <p>
 * 你可以设计一个只使用常数额外空间的算法来解决此问题吗？
 * 你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。
 * <p>
 * 输入：head = [1,2,3,4,5], k = 2
 * 输出：[2,1,4,3,5]
 * <p>
 * 输入：head = [1,2,3,4,5], k = 3
 * 输出：[3,2,1,4,5]
 * <p>
 * 输入：head = [1,2,3,4,5], k = 1
 * 输出：[1,2,3,4,5]
 * <p>
 * 输入：head = [1], k = 1
 * 输出：[1]
 * <p>
 * 列表中节点的数量在范围 sz 内
 * 1 <= sz <= 5000
 * 0 <= Node.val <= 1000
 * 1 <= k <= sz
 */
public class Problem25 {
    public static void main(String[] args) {
        Problem25 problem25 = new Problem25();
        int[] data = {1, 2, 3, 4, 5};
        int k = 2;
        ListNode head = problem25.buildLinkedList(data);
        head = problem25.reverseKGroup(head, k);
        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }
    }

    /**
     * 模拟，找到每次要反转的k个节点，断开链表，反转，再重新连接回原链表
     * 需要额外的指针保存子链表的第一个节点、最后一个节点、子链表第一个节点的前一个节点、子链表最后一个节点的后一个节点
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param head
     * @param k
     * @return
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || head.next == null || k == 1) {
            return head;
        }

        //使用头结点，方便返回
        ListNode hair = new ListNode();
        hair.next = head;

        //遍历的当前节点
        ListNode node = head;
        //要反转子链表第一个节点的前一个节点
        ListNode pre = hair;
        //要反转子链表的最后一个节点的后一个节点
        ListNode next;
        //要反转子链表第一个节点
        ListNode first = head;
        //要反转子链表的最后一个节点
        ListNode last;

        while (node != null) {
            //k个一组进行反转，找到当前组最后一个节点
            for (int i = 1; i < k; i++) {
                node = node.next;

                //最后一组不够k个，不能进行反转，直接返回
                if (node == null) {
                    return hair.next;
                }
            }

            //更新指针
            last = node;
            next = last.next;

            //断开子链表
            last.next = null;

            //反转子链表
            reverse(first);

            //子链表连接回原链表
            pre.next = last;
            first.next = next;

            //更新指针
            pre = first;
            node = next;
            first = next;
        }

        return hair.next;
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

    private ListNode buildLinkedList(int[] data) {
        if (data == null || data.length == 0) {
            return null;
        }

        //不能使用Arrays.asList(data)，因为需要传入引用类型才能转换为list，
        //如果传入基本数据类型，则会将数组对象作为引用放入list中
        ListNode head = new ListNode(data[0]);
        ListNode node = head;

        for (int i = 1; i < data.length; i++) {
            node.next = new ListNode(data[i]);
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
