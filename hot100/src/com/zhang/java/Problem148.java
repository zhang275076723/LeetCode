package com.zhang.java;

/**
 * @Date 2022/5/9 9:20
 * @Author zsy
 * @Description 排序链表 链表类比Problem23、Problem143、Problem147 归并排序类比Problem23、Problem315、Problem327、Problem493、Offer51
 * 给你链表的头结点 head ，请将其按 升序 排列并返回 排序后的链表 。
 * <p>
 * 输入：head = [4,2,1,3]
 * 输出：[1,2,3,4]
 * <p>
 * 输入：head = [-1,5,3,4,0]
 * 输出：[-1,0,3,4,5]
 * <p>
 * 输入：head = []
 * 输出：[]
 * <p>
 * 链表中节点的数目在范围 [0, 5 * 10^4] 内
 * -10^5 <= Node.val <= 10^5
 */
public class Problem148 {
    public static void main(String[] args) {
        Problem148 problem148 = new Problem148();
        int[] data = {-1, 5, 3, 4, 0};
        ListNode head = problem148.buildLinkedList(data);

        head = problem148.sortList(head);
//        head = problem148.sortList2(head);

        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }
    }

    /**
     * 递归归并排序，自顶向下
     * 快慢指针找到链表中间节点，断开链表分为两个链表，进行递归合并
     * 时间复杂度O(nlogn)，空间复杂度O(logn)
     *
     * @param head
     * @return
     */
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        return mergeSort(head);
    }

    /**
     * 非递归归并排序，自底向上
     * 每次合并1、2、4、8...个节点，直至链表完全有序
     * 时间复杂度O(nlogn)，空间复杂度O(1)
     *
     * @param head
     * @return
     */
    public ListNode sortList2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        //链表总长度
        int length = 0;
        ListNode node = head;
        while (node != null) {
            length++;
            node = node.next;
        }

        //设置链表头结点，方便操作
        ListNode hair = new ListNode(0);
        hair.next = head;

        //每次要合并的节点个数
        int mergeLength;
        for (mergeLength = 1; mergeLength < length; mergeLength = mergeLength << 1) {
            //当前头结点
            ListNode curHead = hair.next;
            //当前尾节点
            ListNode curTail = hair;

            while (curHead != null) {
                //第一个链表头结点
                ListNode head1 = curHead;
                //第二个链表头结点
                ListNode head2 = cut(head1, mergeLength);
                //更新当前头结点
                curHead = cut(head2, mergeLength);
                //两个链表合并，并使当前尾结点始终指向链表的尾
                curTail.next = merge(head1, head2);
                while (curTail.next != null) {
                    curTail = curTail.next;
                }
            }
        }

        return hair.next;
    }

    /**
     * 链表归并排序
     *
     * @param head
     * @return
     */
    private ListNode mergeSort(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        //快慢指针找中间节点
        ListNode fast = head;
        ListNode slow = head;
        //slow指针的前驱指针，用于找中间节点
        ListNode pre = null;

        while (fast != null && fast.next != null) {
            pre = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        //后半部分链表头
        ListNode head2;

        //根据fast指针是否为空，得到后半部分链表，并断开链表，分为两个链表
        if (fast == null) {
            head2 = slow;
            pre.next = null;
        } else {
            head2 = slow.next;
            slow.next = null;
        }

        //前半部分链表归并排序
        head = mergeSort(head);
        //后半部分链表归并排序
        head2 = mergeSort(head2);

        //合并链表
        return merge(head, head2);
    }

    /**
     * 合并两个链表
     *
     * @param head1
     * @param head2
     * @return
     */
    private ListNode merge(ListNode head1, ListNode head2) {
        if (head1 == null) {
            return head2;
        }

        if (head2 == null) {
            return head1;
        }

        //设置链表头结点，方便操作
        ListNode head = new ListNode();
        ListNode node = head;
        ListNode node1 = head1;
        ListNode node2 = head2;

        while (node1 != null && node2 != null) {
            if (node1.val < node2.val) {
                node.next = node1;
                node1 = node1.next;
                node = node.next;
            } else {
                node.next = node2;
                node2 = node2.next;
                node = node.next;
            }
        }

        if (node1 != null) {
            node.next = node1;
        } else {
            node.next = node2;
        }

        return head.next;
    }

    /**
     * 链表去除前n个节点，返回后半部分的链表头节点
     *
     * @param head
     * @param n
     * @return
     */
    private ListNode cut(ListNode head, int n) {
        ListNode pre = head;
        while (n > 0 && head != null) {
            n--;
            pre = head;
            head = head.next;
        }

        if (head == null) {
            return null;
        }

        pre.next = null;
        return head;
    }

    /**
     * 建立链表
     *
     * @param data
     * @return
     */
    private ListNode buildLinkedList(int[] data) {
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
