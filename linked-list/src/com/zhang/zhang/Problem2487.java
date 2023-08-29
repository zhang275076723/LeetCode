package com.zhang.zhang;

import java.util.Stack;

/**
 * @Date 2023/8/13 08:25
 * @Author zsy
 * @Description 从链表中移除节点 类比Problem82、Problem83、Problem92、Problem206 单调栈类比Problem42、Problem84、Problem255、Problem316、Problem321、Problem402、Problem456、Problem496、Problem503、Problem654、Problem739、Problem795、Problem907、Problem1019、Problem1856、Problem2104、Problem2454、Offer33、DoubleStackSort
 * 给你一个链表的头节点 head 。
 * 对于列表中的每个节点 node ，如果其右侧存在一个具有 严格更大 值的节点，则移除 node 。
 * 返回修改后链表的头节点 head 。
 * <p>
 * 输入：head = [5,2,13,3,8]
 * 输出：[13,8]
 * 解释：需要移除的节点是 5 ，2 和 3 。
 * - 节点 13 在节点 5 右侧。
 * - 节点 13 在节点 2 右侧。
 * - 节点 8 在节点 3 右侧。
 * <p>
 * 输入：head = [1,1,1,1]
 * 输出：[1,1,1,1]
 * 解释：每个节点的值都是 1 ，所以没有需要移除的节点。
 * <p>
 * 给定列表中的节点数目在范围 [1, 10^5] 内
 * 1 <= Node.val <= 10^5
 */
public class Problem2487 {
    public static void main(String[] args) {
        Problem2487 problem2487 = new Problem2487();
        int[] nums = {5, 2, 13, 3, 8};
        ListNode head = problem2487.buildList(nums);
        head = problem2487.removeNodes(head);
//        head = problem2487.removeNodes2(head);
//        head = problem2487.removeNodes3(head);
    }

    /**
     * 单调栈
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param head
     * @return
     */
    public ListNode removeNodes(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        //头结点，初始化为int最大值，避免头结点作为栈顶节点时不满足单调递减栈，造成头结点出栈的情况
        ListNode hair = new ListNode(Integer.MAX_VALUE);
        hair.next = head;
        //单调递减栈
        Stack<ListNode> stack = new Stack<>();
        ListNode node = hair;

        while (node != null) {
            while (!stack.isEmpty() && stack.peek().val < node.val) {
                stack.pop();
            }

            //栈为空，即头结点直接入栈
            if (stack.isEmpty()) {
                stack.push(node);
                node = node.next;
            } else {
                //栈非空，栈顶元素指向当前节点
                stack.peek().next = node;
                stack.push(node);
                node = node.next;
            }
        }

        return hair.next;
    }

    /**
     * 递归
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param head
     * @return
     */
    public ListNode removeNodes2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        //得到以head.next为头结点，满足要求的链表
        ListNode node = removeNodes2(head.next);

        //head.val小于node.val，则head后面存在比head值大的节点，head节点不能添加，返回node
        if (head.val < node.val) {
            return node;
        } else {
            //head.val大于等于node.val，则head节点满足要求，返回head
            head.next = node;
            return head;
        }
    }

    /**
     * 非递归
     * 1、反转链表
     * 2、遍历反转之后的链表，如果当前节点的下一个节点小于当前节点，则当前节点的下一个节点不满足要求，删除当前节点的下一个节点
     * 3、重新反转链表
     * 时间复杂度O(n)，空间复杂度O(n) (递归反转链表的空间复杂度O(n))
     *
     * @param head
     * @return
     */
    public ListNode removeNodes3(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        //1、反转链表
        head = reverse(head);

        ListNode node = head;

        //2、遍历反转之后的链表，如果当前节点的下一个节点小于当前节点，则当前节点的下一个节点不满足要求，删除当前节点的下一个节点
        while (node.next != null) {
            if (node.next.val < node.val) {
                node.next = node.next.next;
            } else {
                node = node.next;
            }
        }

        //3、重新反转链表
        return reverse(head);
    }

    private ListNode reverse(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode node = reverse(head.next);

        head.next.next = head;
        head.next = null;

        return node;
    }

    private ListNode reverse2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode node = head;
        ListNode next = head.next;
        ListNode pre = null;

        while (next != null) {
            node.next = pre;
            pre = node;
            node = next;
            next = next.next;
        }

        node.next = pre;

        return node;
    }

    private ListNode buildList(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        ListNode head = new ListNode(nums[0]);
        ListNode node = head;

        for (int i = 1; i < nums.length; i++) {
            node.next = new ListNode(nums[i]);
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
