package com.zhang.zhang;

import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2023/6/28 10:04
 * @Author zsy
 * @Description 链表组件
 * 给定链表头结点 head，该链表上的每个结点都有一个 唯一的整型值 。同时给定列表 nums，该列表是上述链表中整型值的一个子集。
 * 返回列表 nums 中组件的个数，这里对组件的定义为：链表中一段最长连续结点的值（该值必须在列表 nums 中）构成的集合。
 * <p>
 * 输入: head = [0,1,2,3], nums = [0,1,3]
 * 输出: 2
 * 解释: 链表中,0 和 1 是相连接的，且 nums 中不包含 2，所以 [0, 1] 是 nums 的一个组件，同理 [3] 也是一个组件，故返回 2。
 * <p>
 * 输入: head = [0,1,2,3,4], nums = [0,3,1,4]
 * 输出: 2
 * 解释: 链表中，0 和 1 是相连接的，3 和 4 是相连接的，所以 [0, 1] 和 [3, 4] 是两个组件，故返回 2。
 * <p>
 * 链表中节点数为n
 * 1 <= n <= 10^4
 * 0 <= Node.val < n
 * Node.val 中所有值 不同
 * 1 <= nums.length <= n
 * 0 <= nums[i] < n
 * nums 中所有值 不同
 */
public class Problem817 {
    public static void main(String[] args) {
        Problem817 problem817 = new Problem817();
//        int[] data = {0, 1, 2, 3};
//        int[] nums = {0, 1, 3};
        int[] data = {0, 1, 2};
        int[] nums = {0, 2};
        ListNode head = problem817.buildList(data);
        System.out.println(problem817.numComponents(head, nums));
    }

    /**
     * 哈希表
     * 时间复杂度O(n)，空间复杂度O(m) (n：链表长度，m：数组长度)
     *
     * @param head
     * @param nums
     * @return
     */
    public int numComponents(ListNode head, int[] nums) {
        if (head == null) {
            return 0;
        }

        Set<Integer> set = new HashSet<>();

        //nums数组中元素存放到哈希集合中
        for (int num : nums) {
            set.add(num);
        }

        //链表中nums数组组件的个数
        int count = 0;
        ListNode node = head;

        while (node != null) {
            if (set.contains(node.val)) {
                while (node != null && set.contains(node.val)) {
                    node = node.next;
                }
                count++;
            } else {
                node = node.next;
            }
        }

        return count;
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
