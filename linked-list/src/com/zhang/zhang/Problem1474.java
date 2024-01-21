package com.zhang.zhang;

/**
 * @Date 2024/1/21 08:48
 * @Author zsy
 * @Description 删除链表 M 个节点之后的 N 个节点 类比Problem541
 * 给定链表 head 和两个整数 m 和 n. 遍历该链表并按照如下方式删除节点:
 * 开始时以头节点作为当前节点.
 * 保留以当前节点开始的前 m 个节点.
 * 删除接下来的 n 个节点.
 * 重复步骤 2 和 3, 直到到达链表结尾.
 * 在删除了指定结点之后, 返回修改过后的链表的头节点.
 * <p>
 * 输入: head = [1,2,3,4,5,6,7,8,9,10,11,12,13], m = 2, n = 3
 * 输出: [1,2,6,7,11,12]
 * 解析: 保留前(m = 2)个结点,  也就是以黑色节点表示的从链表头结点开始的结点(1 ->2).
 * 删除接下来的(n = 3)个结点(3 -> 4 -> 5), 在图中以红色结点表示.
 * 继续相同的操作, 直到链表的末尾.
 * 返回删除结点之后的链表的头结点.
 * <p>
 * 输入: head = [1,2,3,4,5,6,7,8,9,10,11], m = 1, n = 3
 * 输出: [1,5,9]
 * 解析: 返回删除结点之后的链表的头结点.
 * <p>
 * 输入: head = [1,2,3,4,5,6,7,8,9,10,11], m = 3, n = 1
 * 输出: [1,2,3,5,6,7,9,10,11]
 * <p>
 * 输入: head = [9,3,7,7,9,10,8,2], m = 1, n = 2
 * 输出: [9,7,8]
 * <p>
 * 链表中节点数目在范围 [1, 10^4] 内
 * 1 <= Node.val <= 10^6
 * 1 <= m, n <= 1000
 */
public class Problem1474 {
    public static void main(String[] args) {
        Problem1474 problem1474 = new Problem1474();
        int[] data = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        ListNode head = problem1474.buildList(data);
        int m = 2;
        int n = 3;
        head = problem1474.deleteNodes(head, m, n);
    }

    /**
     * 模拟
     * 时间复杂度O(n)，空间复杂度O(1) (n：链表head的长度)
     *
     * @param head
     * @param m
     * @param n
     * @return
     */
    public ListNode deleteNodes(ListNode head, int m, int n) {
        if (head == null) {
            return null;
        }

        //头结点
        ListNode hair = new ListNode();
        hair.next = head;
        ListNode node = head;

        while (node != null) {
            //保留前m个节点的最后一个节点
            ListNode pre = node;

            //保留前m个节点
            for (int i = 0; i < m; i++) {
                pre = node;

                //已经遍历到链表末尾，则直接返回链表头
                if (node == null) {
                    return hair.next;
                }

                node = node.next;
            }

            //删除接下来n个节点
            for (int i = 0; i < n; i++) {
                //已经遍历到链表末尾，则pre直接指向null，返回链表头
                if (node == null) {
                    pre.next = null;
                    return hair.next;
                }

                node = node.next;
            }

            pre.next = node;
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
