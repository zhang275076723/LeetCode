package com.zhang.zhang;

/**
 * @Date 2022/5/19 19:19
 * @Author zsy
 * @Description 给单链表加一 类比Problem2、Problem66 七牛云面试题
 * 用一个 非空 单链表来表示一个非负整数，然后将这个整数加一。
 * 你可以假设这个整数除了 0 本身，没有任何前导的 0。
 * 这个整数的各个数位按照 高位在链表头部、低位在链表尾部 的顺序排列。
 * <p>
 * 输入: [1,2,3]
 * 输出: [1,2,4]
 */
public class Problem369 {
    public static void main(String[] args) {
        Problem369 problem369 = new Problem369();
//        int[] data = {1, 2, 3};
        int[] data = {9, 9, 9};
        ListNode head = problem369.buildList(data);
        head = problem369.plusOne(head);
        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }
    }

    /**
     * 先反转链表，再链表加1(如果最高位需要进位，则添加一个新的节点)，最后再反转回来
     * 时间复杂度O(n)，空间复杂度O(1) (如果使用递归反转链表，空间复杂度为O(n))
     *
     * @param head
     * @return
     */
    public ListNode plusOne(ListNode head) {
        if (head == null) {
            return new ListNode(1);
        }

        head = reverse(head);

        ListNode node = head;
        node.val++;

        while (node.val == 10) {
            //当前位置为0
            node.val = 0;

            if (node.next != null) {
                node.next.val++;
            } else {
                //添加尾结点
                node.next = new ListNode(1);
            }

            node = node.next;
        }

        return reverse(head);
    }

    /**
     * 递归反转链表
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param head
     * @return
     */
    private ListNode reverse(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode node = reverse(head.next);

        head.next.next = head;
        head.next = null;

        return node;
    }

    /**
     * 非递归反转链表
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param head
     * @return
     */
    private ListNode reverse2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode pre = null;
        ListNode node = head;
        ListNode next = head.next;

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

        //不能使用Arrays.asList(data)，因为需要传入引用类型的data才能转换为list，
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
