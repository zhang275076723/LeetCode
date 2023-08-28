package com.zhang.zhang;

/**
 * @Date 2023/4/14 08:11
 * @Author zsy
 * @Description 二进制链表转整数 位运算类比Problem29、Problem190、Problem191、Problem201、Problem231、Problem271、Problem326、Problem342、Problem371、Problem405、Problem461、Problem477、Problem898、Offer15、Offer64、Offer65、IpToInt
 * 给你一个单链表的引用结点 head。链表中每个结点的值不是 0 就是 1。已知此链表是一个整数数字的二进制表示形式。
 * 请你返回该链表所表示数字的 十进制值 。
 * <p>
 * 输入：head = [1,0,1]
 * 输出：5
 * 解释：二进制数 (101) 转化为十进制数 (5)
 * <p>
 * 输入：head = [0]
 * 输出：0
 * <p>
 * 输入：head = [1]
 * 输出：1
 * <p>
 * 输入：head = [1,0,0,1,0,0,1,1,1,0,0,0,0,0,0]
 * 输出：18880
 * <p>
 * 输入：head = [0,0]
 * 输出：0
 * <p>
 * 链表不为空。
 * 链表的结点总数不超过 30。
 * 每个结点的值不是 0 就是 1。
 */
public class Problem1290 {
    public static void main(String[] args) {
        Problem1290 problem1290 = new Problem1290();
        int[] data = {1, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0};
        ListNode head = problem1290.buildList(data);
        System.out.println(problem1290.getDecimalValue(head));
        System.out.println(problem1290.getBinaryValue(18880));
    }

    /**
     * 模拟
     * 从左往右遍历，二进制数，每次右移一位表示乘2
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param head
     * @return
     */
    public int getDecimalValue(ListNode head) {
        if (head == null) {
            return 0;
        }

        ListNode node = head;
        int value = 0;

        while (node != null) {
            //二进制数，每次右移一位表示乘2
            value = (value << 1) + node.val;
            node = node.next;
        }

        return value;
    }

    /**
     * 十进制数转化为二进制数
     * 十进制数右移1位，相当于除以2，直至当前数为0，拼接所有的余数，即为二进制数
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param value
     * @return
     */
    public String getBinaryValue(int value) {
        if (value == 0) {
            return "0";
        }

        StringBuilder sb = new StringBuilder();

        while (value != 0) {
            sb.append(value & 1);
            value = value >>> 1;
        }

        //因为sb尾添加，所以需要反转字符串得到二进制数
        return sb.reverse().toString();
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
