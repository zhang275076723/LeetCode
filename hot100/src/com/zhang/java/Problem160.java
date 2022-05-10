package com.zhang.java;

import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2022/5/10 10:41
 * @Author zsy
 * @Description 相交链表
 * 给你两个单链表的头节点 headA 和 headB ，请你找出并返回两个单链表相交的起始节点。
 * 如果两个链表不存在相交节点，返回 null 。
 * 注意，函数返回结果后，链表必须 保持其原始结构 。
 * <p>
 * intersectVal - 相交的起始节点的值。如果不存在相交节点，这一值为 0
 * listA - 第一个链表
 * listB - 第二个链表
 * skipA - 在 listA 中（从头节点开始）跳到交叉节点的节点数
 * skipB - 在 listB 中（从头节点开始）跳到交叉节点的节点数
 * <p>
 * 输入：intersectVal = 8, listA = [4,1,8,4,5], listB = [5,6,1,8,4,5], skipA = 2, skipB = 3
 * 输出：Intersected at '8'
 * 解释：相交节点的值为 8 （注意，如果两个链表相交则不能为 0）。
 * 从各自的表头开始算起，链表 A 为 [4,1,8,4,5]，链表 B 为 [5,6,1,8,4,5]。
 * 在 A 中，相交节点前有 2 个节点；在 B 中，相交节点前有 3 个节点。
 * <p>
 * 输入：intersectVal = 2, listA = [1,9,1,2,4], listB = [3,2,4], skipA = 3, skipB = 1
 * 输出：Intersected at '2'
 * 解释：相交节点的值为 2 （注意，如果两个链表相交则不能为 0）。
 * 从各自的表头开始算起，链表 A 为 [1,9,1,2,4]，链表 B 为 [3,2,4]。
 * 在 A 中，相交节点前有 3 个节点；在 B 中，相交节点前有 1 个节点。
 * <p>
 * 输入：intersectVal = 0, listA = [2,6,4], listB = [1,5], skipA = 3, skipB = 2
 * 输出：null
 * 解释：从各自的表头开始算起，链表 A 为 [2,6,4]，链表 B 为 [1,5]。
 * 由于这两个链表不相交，所以 intersectVal 必须为 0，而 skipA 和 skipB 可以是任意值。
 * 这两个链表不相交，因此返回 null 。
 * <p>
 * listA 中节点数目为 m
 * listB 中节点数目为 n
 * 1 <= m, n <= 3 * 10^4
 * 1 <= Node.val <= 10^5
 * 0 <= skipA <= m
 * 0 <= skipB <= n
 * 如果 listA 和 listB 没有交点，intersectVal 为 0
 * 如果 listA 和 listB 有交点，intersectVal == listA[skipA] == listB[skipB]
 */
public class Problem160 {
    public static void main(String[] args) {
        Problem160 problem160 = new Problem160();
        ListNode headA = new ListNode(4);
        ListNode headB = new ListNode(5);
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(8);
        ListNode node3 = new ListNode(4);
        ListNode node4 = new ListNode(5);
        ListNode node5 = new ListNode(6);
        headA.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        headB.next = node5;
        node5.next = node1;
//        ListNode node = problem160.getIntersectionNode(headA, headB);
//        ListNode node = problem160.getIntersectionNode2(headA, headB);
        ListNode node = problem160.getIntersectionNode3(headA, headB);
        while (node != null) {
            System.out.println(node.val);
            node = node.next;
        }
    }

    /**
     * 哈希表
     * 时间复杂度O(m+n)，空间复杂度O(m)
     *
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }

        Set<ListNode> set = new HashSet<>();

        while (headA != null) {
            set.add(headA);
            headA = headA.next;
        }

        while (headB != null) {
            if (set.contains(headB)) {
                return headB;
            }
            headB = headB.next;
        }

        return null;
    }

    /**
     * 双指针，遍历链表找到两个链表的长度，长链表指针往后移动x步，保证两链表长度相同，再次遍历，判断是否相交
     * 时间复杂度O(m+n)，空间复杂度O(1)
     *
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode2(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }

        int lenA = 0;
        int lenB = 0;
        ListNode nodeA = headA;
        ListNode nodeB = headB;
        while (nodeA != null && nodeB != null) {
            lenA++;
            lenB++;
            nodeA = nodeA.next;
            nodeB = nodeB.next;
        }
        while (nodeA != null) {
            lenA++;
            nodeA = nodeA.next;
        }
        while (nodeB != null) {
            lenB++;
            nodeB = nodeB.next;
        }

        //长链表往后移动lenB-lenA步，保证两链表长度相同
        if (lenA < lenB) {
            for (int i = 0; i < lenB - lenA; i++) {
                headB = headB.next;
            }
        } else {
            for (int i = 0; i < lenA - lenB; i++) {
                headA = headA.next;
            }
        }

        while (headA != null) {
            if (headA == headB) {
                return headA;
            }
            headA = headA.next;
            headB = headB.next;
        }

        return null;
    }

    /**
     * 答案的双指针
     * 链表A先遍历A，再遍历B，链表B先遍历B，再遍历A，如果在遍历过程中两个指针节点相等，则返回当前指针，即为相交节点
     * 时间复杂度O(m+n)，空间复杂度O(1)
     *
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode3(ListNode headA, ListNode headB) {
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

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }
}
