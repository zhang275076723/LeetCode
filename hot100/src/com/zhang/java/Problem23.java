package com.zhang.java;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Date 2022/4/16 10:12
 * @Author zsy
 * @Description 合并K个升序链表 字节面试题 类比Problem21、Problem23、Problem25、Problem148、Problem378
 * 给你一个链表数组，每个链表都已经按升序排列。
 * 请你将所有链表合并到一个升序链表中，返回合并后的链表。
 * <p>
 * 输入：lists = [[1,4,5],[1,3,4],[2,6]]
 * 输出：[1,1,2,3,4,4,5,6]
 * 解释：链表数组如下：
 * [
 * 1->4->5,
 * 1->3->4,
 * 2->6
 * ]
 * 将它们合并到一个有序链表中得到。
 * 1->1->2->3->4->4->5->6
 * <p>
 * 输入：lists = []
 * 输出：[]
 * <p>
 * 输入：lists = [[]]
 * 输出：[]
 * <p>
 * k == lists.length
 * 0 <= k <= 10^4
 * 0 <= lists[i].length <= 500
 * -10^4 <= lists[i][j] <= 10^4
 * lists[i] 按 升序 排列
 * lists[i].length 的总和不超过 10^4
 */
public class Problem23 {
    public static void main(String[] args) {
        Problem23 problem23 = new Problem23();
        int[][] data = {{1, 4, 5}, {1, 3, 4}, {2, 6}};
        ListNode[] lists = problem23.buildList(data);
//        ListNode head = problem23.mergeKLists(lists);
        ListNode head = problem23.mergeKLists2(lists);
//        ListNode head = problem23.mergeKLists3(lists);
        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }
    }

    /**
     * 暴力
     * 循环合并2个链表，直至K个链表合并为一个链表
     * 时间复杂度O((k^2)*n)，空间复杂度O(1) (k为链表个数，n为每个链表最长长度)
     *
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }

        ListNode head = null;

        for (ListNode list : lists) {
            head = mergeTwoLists(head, list);
        }

        return head;
    }

    /**
     * 分治合并
     * 时间复杂度O((Σi=1 i=logk)(k/2^i)(2^i)*n) = O(kn*logk)，空间复杂度O(logk) (k为链表个数，n为每个链表最长长度)
     *
     * @param lists
     * @return
     */
    public ListNode mergeKLists2(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }

        return mergeSort(lists, 0, lists.length - 1);
    }

    /**
     * 小根堆
     * 时间复杂度O(kn*logk)，空间复杂度O(k)
     * (k为链表个数，n为每个链表最长长度，一共nk个节点，每个节点插入和删除需要O(logk))
     *
     * @param lists
     * @return
     */
    public ListNode mergeKLists3(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }

        //优先队列，小根堆
        PriorityQueue<ListNode> priorityQueue = new PriorityQueue<>(new Comparator<ListNode>() {
            @Override
            public int compare(ListNode node1, ListNode node2) {
                return node1.val - node2.val;
            }
        });

        for (ListNode node : lists) {
            if (node != null) {
                priorityQueue.offer(node);
            }
        }

        //设置头指针，方便合并
        ListNode head = new ListNode();
        ListNode node = head;

        while (!priorityQueue.isEmpty()) {
            ListNode temp = priorityQueue.poll();
            node.next = temp;
            node = node.next;

            //如果当前节点的下一个节点不为空，则加入到优先队列中
            if (temp.next != null) {
                priorityQueue.offer(temp.next);
            }
        }

        return head.next;
    }

    private ListNode mergeSort(ListNode[] lists, int left, int right) {
        if (left >= right) {
            return lists[left];
        }

        // >> 优先级小于 + ，所以需要在使用 >> 的时候添加括号
        int mid = left + ((right - left) >> 1);

        ListNode head1 = mergeSort(lists, left, mid);
        ListNode head2 = mergeSort(lists, mid + 1, right);

        return mergeTwoLists(head1, head2);
    }

    /**
     * 非递归合并两个有序链表
     * 时间复杂度O(m+n)，空间复杂度O(1)
     *
     * @param list1
     * @param list2
     * @return
     */
    private ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }

        if (list2 == null) {
            return list1;
        }

        //设置头指针，方便合并
        ListNode head = new ListNode();
        ListNode node = head;

        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                node.next = list1;
                list1 = list1.next;
            } else {
                node.next = list2;
                list2 = list2.next;
            }

            node = node.next;
        }

        if (list1 == null) {
            node.next = list2;
        } else {
            node.next = list1;
        }

        return head.next;
    }

    public ListNode[] buildList(int[][] data) {
        if (data == null || data.length == 0) {
            return null;
        }

        ListNode[] lists = new ListNode[data.length];

        for (int i = 0; i < data.length; i++) {
            if (data[i].length == 0) {
                lists[i] = null;
            }

            ListNode head = new ListNode(data[i][0]);
            ListNode node = head;

            for (int j = 1; j < data[i].length; j++) {
                node.next = new ListNode(data[i][j]);
                node = node.next;
            }

            lists[i] = head;
        }

        return lists;
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
