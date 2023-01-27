package com.zhang.zhang;

/**
 * @Date 2022/6/21 11:42
 * @Author zsy
 * @Description 删除排序链表中的重复元素 类比Problem82
 * 给定一个已排序的链表的头 head ， 删除所有重复的元素，使每个元素只出现一次 。
 * 返回 已排序的链表 。
 * <p>
 * 输入：head = [1,1,2]
 * 输出：[1,2]
 * <p>
 * 输入：head = [1,1,2,3,3]
 * 输出：[1,2,3]
 * <p>
 * 链表中节点数目在范围 [0, 300] 内
 * -100 <= Node.val <= 100
 * 题目数据保证链表已经按升序 排列
 */
public class Problem83 {
    public static void main(String[] args) {
        Problem83 problem83 = new Problem83();
        int[] data = {1, 1, 2, 3, 3};
        ListNode head = problem83.buildList(data);
//        head = problem83.deleteDuplicates(head);
        head = problem83.deleteDuplicates2(head);
        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }
    }

    /**
     * 非递归删除重复元素
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param head
     * @return
     */
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        //设置头结点，便于第一个节点的删除
        ListNode hair = new ListNode(Integer.MIN_VALUE);
        hair.next = head;

        ListNode pre = hair;
        ListNode node = head;

        while (node != null) {
            if (pre.val == node.val) {
                pre.next = node.next;
                node = node.next;
            } else {
                pre = node;
                node = node.next;
            }
        }

        return hair.next;
    }

    /**
     * 递归删除重复元素
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param head
     * @return
     */
    public ListNode deleteDuplicates2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        //当前节点和下一个节点相等，更新头结点
        if (head.val == head.next.val) {
            head = deleteDuplicates(head.next);
        } else {
            //当前节点和下一个节点不相等，不更新头结点
            head.next = deleteDuplicates(head.next);
        }

        return head;
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
