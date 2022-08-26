package com.zhang.java;

/**
 * @Date 2022/5/9 9:20
 * @Author zsy
 * @Description 排序链表 类比Problem23、Problem143、Problem147
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
 * 链表中节点的数目在范围 [0, 5 * 104] 内
 * -10^5 <= Node.val <= 10^5
 */
public class Problem148 {
    public static void main(String[] args) {
        Problem148 problem148 = new Problem148();
        int[] data = {-1, 5, 3, 4, 0};
        ListNode head = problem148.buildLinkedList(data);

//        head = problem148.sortList(head);
//        head = problem148.sortList2(head);
        head = problem148.sortList3(head);

        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }
    }

    /**
     * 递归归并排序，自顶向下
     * 利用快慢指针，找到中间链表节点，断链分为两个链表，然后递归进行合并
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
     * 快排
     * 时间复杂度O(nlogn)，空间复杂度O(logn)
     *
     * @param head
     * @return
     */
    public ListNode sortList3(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        quickSort(head, null);
        return head;
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

        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        //断开链表，分为两个链表
        ListNode tempNode = slow.next;
        slow.next = null;

        //前半部分链表归并排序
        ListNode head1 = mergeSort(head);
        //后半部分链表归并排序
        ListNode head2 = mergeSort(tempNode);

        //合并链表
        return merge(head1, head2);
    }

    /**
     * 合并两个链表
     *
     * @param head1
     * @param head2
     * @return
     */
    private ListNode merge(ListNode head1, ListNode head2) {
        //设置链表头结点，方便操作
        ListNode head = new ListNode();
        ListNode node = head;

        while (head1 != null && head2 != null) {
            if (head1.val < head2.val) {
                node.next = head1;
                head1 = head1.next;
            } else {
                node.next = head2;
                head2 = head2.next;
            }
            node = node.next;
        }

        if (head1 != null) {
            node.next = head1;
        }
        if (head2 != null) {
            node.next = head2;
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
     * 快排合并链表
     * [head, tail)
     *
     * @param head
     * @param tail
     */
    private void quickSort(ListNode head, ListNode tail) {
        if (head == tail) {
            return;
        }

        ListNode pivot = partition(head, tail);
        quickSort(head, pivot);
        quickSort(pivot.next, tail);
    }

    /**
     * 快排合并链表的一次划分，从左到右将遍历，将节点划分为小于pivot的元素，pivot元素，大于pivot的元素
     * 而常用的快排的划分方式，是使用头尾指针，直至两指针相遇
     *
     * @param head
     * @param tail
     * @return
     */
    private ListNode partition(ListNode head, ListNode tail) {
        //左指针
        ListNode pivot = head;
        //右指针
        ListNode curNode = head.next;
        int tempValue = head.val;

        while (curNode != tail) {
            if (curNode.val < tempValue) {
                pivot.val = curNode.val;
                curNode.val = pivot.next.val;
                pivot = pivot.next;
            }
            curNode = curNode.next;
        }

        pivot.val = tempValue;
        return pivot;
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

    public class ListNode {
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
