package com.zhang.zhang;

/**
 * @Date 2022/5/19 19:19
 * @Author zsy
 * @Description 给单链表加一 七牛云面试题 类比Problem2、Problem66、Problem67、Problem415、Problem445、Problem989
 * 用一个 非空 单链表来表示一个非负整数，然后将这个整数加一。
 * 你可以假设这个整数除了 0 本身，没有任何前导的 0。
 * 这个整数的各个数位按照 高位在链表头部、低位在链表尾部 的顺序排列。
 * <p>
 * 输入: [1,2,3]
 * 输出: [1,2,4]
 * <p>
 * 输入: head = [0]
 * 输出: [1]
 * <p>
 * 链表中的节点数在 [1, 100] 的范围内。
 * 0 <= Node.val <= 9
 * 由链表表示的数字不包含前导零，除了零本身。
 */
public class Problem369 {
    public static void main(String[] args) {
        Problem369 problem369 = new Problem369();
//        int[] data = {1, 9, 9};
        int[] data = {9, 9, 9};
        ListNode head = problem369.buildList(data);
        head = problem369.plusOne(head);
        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }
    }

    /**
     * 1、反转链表
     * 2、链表加1(如果最高位需要进位，则添加一个新的节点)
     * 3、最后再反转回来
     * 时间复杂度O(n)，空间复杂度O(1) (如果使用递归反转链表，空间复杂度为O(n))
     *
     * @param head
     * @return
     */
    public ListNode plusOne(ListNode head) {
        if (head == null) {
            return new ListNode(1);
        }

        //1、反转链表，便于末尾加一操作
        head = reverse(head);

        ListNode node = head;
        //当前节点的前驱节点，用于最高位进位时添加节点
        ListNode pre = head;
        node.val++;
        int carry = 0;

        //2、链表加1(如果最高位需要进位，则添加一个新的节点)
        while (node != null) {
            node.val = node.val + carry;
            carry = node.val / 10;
            node.val = node.val % 10;

            //当前位进位为0，则高位节点不需要修改
            if (carry == 0) {
                break;
            }

            pre = node;
            node = node.next;
        }

        //当前节点为空，则说明最高位有进位，创建新节点
        if (node == null) {
            pre.next = new ListNode(carry);
        }

        //3、反转回来，得到结果链表
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
