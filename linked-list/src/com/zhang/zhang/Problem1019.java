package com.zhang.zhang;

import java.util.Arrays;
import java.util.Stack;

/**
 * @Date 2023/4/13 09:07
 * @Author zsy
 * @Description 链表中的下一个更大节点 单调栈类比Problem42、Problem84、Problem255、Problem316、Problem321、Problem402、Problem456、Problem496、Problem503、Problem739、Problem907、Problem1856、Problem2104、Offer33、DoubleStackSort
 * 给定一个长度为 n 的链表 head
 * 对于列表中的每个节点，查找下一个 更大节点 的值。
 * 也就是说，对于每个节点，找到它旁边的第一个节点的值，这个节点的值 严格大于 它的值。
 * 返回一个整数数组 answer ，其中 answer[i] 是第 i 个节点( 从1开始 )的下一个更大的节点的值。
 * 如果第 i 个节点没有下一个更大的节点，设置 answer[i] = 0 。
 * <p>
 * 输入：head = [2,1,5]
 * 输出：[5,5,0]
 * <p>
 * 输入：head = [2,7,4,3,5]
 * 输出：[7,0,5,5,0]
 * <p>
 * 链表中节点数为 n
 * 1 <= n <= 10^4
 * 1 <= Node.val <= 10^9
 */
public class Problem1019 {
    public static void main(String[] args) {
        Problem1019 problem1019 = new Problem1019();
        int[] data = {2, 7, 4, 3, 5};
        ListNode head = problem1019.buildList(data);
        System.out.println(Arrays.toString(problem1019.nextLargerNodes(head)));
    }

    /**
     * 单调栈
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param head
     * @return
     */
    public int[] nextLargerNodes(ListNode head) {
        if (head == null) {
            return null;
        }

        //链表长度
        int len = 0;
        ListNode node = head;

        while (node != null) {
            len++;
            node = node.next;
        }

        int[] result = new int[len];
        //单调递减栈，arr[0]：当前节点值，arr[1]：节点下标索引
        Stack<int[]> stack = new Stack<>();
        node = head;
        //数组下标索引
        int index = 0;

        while (node != null) {
            while (!stack.isEmpty() && stack.peek()[0] < node.val) {
                int[] arr = stack.pop();
                result[arr[1]] = node.val;
            }

            stack.push(new int[]{node.val, index});
            node = node.next;
            index++;
        }

        return result;
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
