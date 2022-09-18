package com.zhang.zhang;

/**
 * @Date 2022/7/3 8:51
 * @Author zsy
 * @Description 两两交换链表中的节点 类比Problem21、Problem23、Problem25、Problem92、Problem206
 * 给你一个链表，两两交换其中相邻的节点，并返回交换后链表的头节点。
 * 你必须在不修改节点内部的值的情况下完成本题（即，只能进行节点交换）。
 * <p>
 * 输入：head = [1,2,3,4]
 * 输出：[2,1,4,3]
 * <p>
 * 输入：head = []
 * 输出：[]
 * <p>
 * 输入：head = [1]
 * 输出：[1]
 * <p>
 * 链表中节点的数目在范围 [0, 100] 内
 * 0 <= Node.val <= 100
 */
public class Problem24 {
    public static void main(String[] args) {
        Problem24 problem24 = new Problem24();
        int[] data = {1, 2, 3, 4};
        ListNode head = problem24.buildList(data);
//        head = problem24.swapPairs(head);
        head = problem24.swapPairs2(head);
        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }
    }

    /**
     * 非递归两两交换
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param head
     * @return
     */
    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        //设置头结点，方便处理
        ListNode hair = new ListNode();
        hair.next = head;

        ListNode pre = hair;
        ListNode node = head;

        while (node != null && node.next != null) {
            pre.next = node.next;
            node.next = node.next.next;
            pre.next.next = node;

            pre = node;
            node = node.next;
        }

        return hair.next;
    }

    /**
     * 递归两两交换
     * 当前节点指向之后的两两反转的节点，当前节点的下一个节点指向当前节点，当前节点的下一个节点作为头结点返回
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param head
     * @return
     */
    public ListNode swapPairs2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        //head节点的下一个节点
        ListNode next = head.next;
        //当前节点后面已经反转好的链表头
        ListNode newHead = swapPairs(head.next.next);

        //head和node节点交换，并连接后面链表
        next.next = head;
        head.next = newHead;

        return next;
    }

    private ListNode buildList(int[] data) {
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
