package com.zhang.zhang;

/**
 * @Date 2022/6/21 8:32
 * @Author zsy
 * @Description 删除排序链表中的重复元素 II 类比Problem83、Problem92、Problem206
 * 给定一个已排序的链表的头 head ， 删除原始链表中所有重复数字的节点，只留下不同的数字 。
 * 返回 已排序的链表 。
 * <p>
 * 输入：head = [1,2,3,3,4,4,5]
 * 输出：[1,2,5]
 * <p>
 * 输入：head = [1,1,1,2,3]
 * 输出：[2,3]
 * <p>
 * 链表中节点数目在范围 [0, 300] 内
 * -100 <= Node.val <= 100
 * 题目数据保证链表已经按升序 排列
 */
public class Problem82 {
    public static void main(String[] args) {
        Problem82 problem82 = new Problem82();
        int[] data = {1, 2, 3, 3, 4, 4, 5};
        ListNode head = problem82.buildList(data);
//        head = problem82.deleteDuplicates(head);
        head = problem82.deleteDuplicates2(head);
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

        while (node != null && node.next != null) {
            //当前节点和当前节点的下一个节点相等时，找当前节点后面第一个和当前节点不同的节点
            if (node.val == node.next.val) {
                ListNode nextNode = node.next;

                while (nextNode != null && node.val == nextNode.val) {
                    nextNode = nextNode.next;
                }

                pre.next = nextNode;
                node = nextNode;
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

        //当前节点和下一个节点相等时，找当前节点后面第一个和当前节点不同的节点
        if (head.val == head.next.val) {
            ListNode node = head.next;

            while (node != null && head.val == node.val) {
                node = node.next;
            }

            head = deleteDuplicates(node);
        } else {
            //当前节点和下一个节点不相等，不更新头结点
            head.next = deleteDuplicates2(head.next);
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
