package com.zhang.java;

import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2022/4/1 10:12
 * @Author zsy
 * @Description 输入两个链表，找出它们的第一个公共节点。
 * 如果两个链表没有交点，返回 null.
 * 在返回结果后，两个链表仍须保持原有的结构。
 * 可假定整个链表结构中没有循环。
 * 程序尽量满足 O(n) 时间复杂度，且仅用 O(1) 内存。
 * <p>
 * 输入：intersectVal = 8, listA = [4,1,8,4,5], listB = [5,0,1,8,4,5], skipA = 2, skipB = 3
 * 输出：Reference of the node with value = 8
 * 输入解释：相交节点的值为 8 （注意，如果两个列表相交则不能为 0）。
 * 从各自的表头开始算起，链表 A 为 [4,1,8,4,5]，链表 B 为 [5,0,1,8,4,5]。
 * 在 A 中，相交节点前有 2 个节点；在 B 中，相交节点前有 3 个节点。
 * <p>
 * 输入：intersectVal = 2, listA = [0,9,1,2,4], listB = [3,2,4], skipA = 3, skipB = 1
 * 输出：Reference of the node with value = 2
 * 输入解释：相交节点的值为 2 （注意，如果两个列表相交则不能为 0）。
 * 从各自的表头开始算起，链表 A 为 [0,9,1,2,4]，链表 B 为 [3,2,4]。
 * 在 A 中，相交节点前有 3 个节点；在 B 中，相交节点前有 1 个节点。
 * <p>
 * 输入：intersectVal = 0, listA = [2,6,4], listB = [1,5], skipA = 3, skipB = 2
 * 输出：null
 * 输入解释：从各自的表头开始算起，链表 A 为 [2,6,4]，链表 B 为 [1,5]。
 * 由于这两个链表不相交，所以 intersectVal 必须为 0，而 skipA 和 skipB 可以是任意值。
 * 解释：这两个链表不相交，因此返回 null。
 */
public class Offer52 {
    public static void main(String[] args) {
        Offer52 offer52 = new Offer52();
        ListNode headA = new ListNode(4);
        ListNode headB = new ListNode(5);
        headA.next = new ListNode(1);
        headA.next.next = new ListNode(8);
        headA.next.next.next = new ListNode(4);
        headA.next.next.next.next = new ListNode(5);
        headB.next = new ListNode(0);
        headB.next.next = new ListNode(1);
        headB.next.next.next = headA.next.next;
//        ListNode intersectionNode = offer52.getIntersectionNode(headA, headB);
        ListNode intersectionNode = offer52.getIntersectionNode2(headA, headB);
//        ListNode intersectionNode = offer52.getIntersectionNode3(headA, headB);
        while (intersectionNode != null) {
            System.out.println(intersectionNode.val);
            intersectionNode = intersectionNode.next;
        }
    }

    /**
     * 自己的双指针，时间复杂度O(m+n)，空间复杂度O(1)
     *
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }

        int lenA = 0;
        int lenB = 0;
        ListNode nodeA = headA;
        ListNode nodeB = headB;
        while (nodeA != null) {
            lenA++;
            nodeA = nodeA.next;
        }
        while (nodeB != null) {
            lenB++;
            nodeB = nodeB.next;
        }
        nodeA = headA;
        nodeB = headB;

        //让两个链表起始位置对齐，即到尾节点长度相等
        if (lenA < lenB) {
            for (int i = lenB - lenA; i > 0; i--) {
                nodeB = nodeB.next;
            }
        } else {
            for (int i = lenA - lenB; i > 0; i--) {
                nodeA = nodeA.next;
            }
        }

        while (nodeA != null) {
            if (nodeA == nodeB) {
                return nodeA;
            }
            nodeA = nodeA.next;
            nodeB = nodeB.next;
        }

        return null;
    }

    /**
     * 答案的双指针，时间复杂度O(m+n)，空间复杂度O(1)
     * 链表A先遍历A，再遍历B；链表B先遍历B，再遍历A
     * 如果在遍历过程中两个指针节点相等，则返回当前指针
     *
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode2(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }

        ListNode nodeA = headA;
        ListNode nodeB = headB;
        while (nodeA != nodeB) {
            if (nodeA != null) {
                nodeA = nodeA.next;
            } else {
                nodeA = headB;
            }

            if (nodeB != null) {
                nodeB = nodeB.next;
            } else {
                nodeB = headA;
            }
        }
        return nodeA;
    }

    /**
     * 哈希表，时间复杂度O(m+n)，空间复杂度O(m)
     *
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode3(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }

        Set<ListNode> set = new HashSet<>();
        ListNode tempNode = headA;
        while (tempNode != null) {
            //把A链表中节点放入哈希表
            set.add(tempNode);
            tempNode = tempNode.next;
        }

        tempNode = headB;
        while (tempNode != null) {
            //如果B链表中节点与哈希表中节点相同，则表明有公共节点
            if (set.contains(tempNode)) {
                return tempNode;
            }
            tempNode = tempNode.next;
        }

        return null;
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }
}
