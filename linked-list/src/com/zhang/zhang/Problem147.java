package com.zhang.zhang;

/**
 * @Date 2022/8/26 8:22
 * @Author zsy
 * @Description 对链表进行插入排序 类比Problem143、Problem148
 * 给定单个链表的头 head ，使用 插入排序 对链表进行排序，并返回 排序后链表的头 。
 * 插入排序 算法的步骤:
 * 插入排序是迭代的，每次只移动一个元素，直到所有元素可以形成一个有序的输出列表。
 * 每次迭代中，插入排序只从输入数据中移除一个待排序的元素，找到它在序列中适当的位置，并将其插入。
 * 重复直到所有输入数据插入完为止。
 * <p>
 * 输入: head = [4,2,1,3]
 * 输出: [1,2,3,4]
 * <p>
 * 输入: head = [-1,5,3,4,0]
 * 输出: [-1,0,3,4,5]
 * <p>
 * 列表中的节点数在 [1, 5000]范围内
 * -5000 <= Node.val <= 5000
 */
public class Problem147 {
    public static void main(String[] args) {
        Problem147 problem147 = new Problem147();
        int[] nums = {-1, 5, 3, 4, 0};
        ListNode head = problem147.buildList(nums);
        head = problem147.insertionSortList(head);
    }

    /**
     * 插入排序的链表形式
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param head
     * @return
     */
    public ListNode insertionSortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        //设置头结点，方便处理
        ListNode hair = new ListNode();
        hair.next = head;

        //node的前驱节点
        ListNode pre = head;
        ListNode node = head.next;

        while (node != null) {
            //当前节点node的值大于等于前驱节点pre的值，插在当前链表末尾
            if (pre.val <= node.val) {
                pre = node;
                node = node.next;
            } else {
                //当前节点node的值小于前驱节点pre的值，插在当前链表非末尾

                //当前节点node要插入的位置的前一个节点
                ListNode temp = hair;

                //找到要插入的位置
                while (temp.next.val < node.val) {
                    temp = temp.next;
                }

                //当前节点从链表中移除
                pre.next = node.next;
                //当前节点插入链表相应位置
                node.next = temp.next;
                temp.next = node;
                //更新node，指向下一个节点
                node = pre.next;
            }
        }

        return hair.next;
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
