package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/3/22 20:19
 * @Author zsy
 * @Description 请实现 copyRandomList 函数，复制一个复杂链表。
 * 在复杂链表中，每个节点除了有一个 next 指针指向下一个节点，还有一个 random 指针指向链表中的任意节点或者 null。
 * <p>
 * 输入：head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
 * 输出：[[7,null],[13,0],[11,4],[10,2],[1,0]]
 * <p>
 * 输入：head = [[1,1],[2,1]]
 * 输出：[[1,1],[2,1]]
 * <p>
 * 输入：head = [[3,null],[3,0],[3,null]]
 * 输出：[[3,null],[3,0],[3,null]]
 * <p>
 * 输入：head = []
 * 输出：[]
 * 解释：给定的链表为空（空指针），因此返回 null。
 */
public class Offer35 {
    public static void main(String[] args) {
        Offer35 offer35 = new Offer35();
        Node node1 = new Node(7);
        Node node2 = new Node(13);
        Node node3 = new Node(11);
        Node node4 = new Node(10);
        Node node5 = new Node(1);
        node1.next = node2;
        node2.next = node3;
        node2.random = node1;
        node3.next = node4;
        node3.random = node5;
        node4.next = node5;
        node4.random = node3;
        node5.random = node1;
//        Node node = offer35.copyRandomList(node1);
        Node node = offer35.copyRandomList2(node1);
        while (node != null) {
            System.out.println(node.val);
            node = node.next;
        }
    }

    /**
     * 哈希表，建立原节点和拷贝节点的映射，时间复杂度O(n)，空间复杂的O(n)
     *
     * @param head
     * @return
     */
    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }

        Map<Node, Node> map = new HashMap<>();
        Node node = head;
        //创建新节点放入map
        while (node != null) {
            map.put(node, new Node(node.val));
            node = node.next;
        }

        node = head;
        //设置每个新节点的next和random
        while (node != null) {
            map.get(node).next = map.get(node.next);
            map.get(node).random = map.get(node.random);
            node = node.next;
        }

        return map.get(head);
    }

    /**
     * 节点拆分，时间复杂度O(n)，空间复杂的O(1) 注意：空间复杂度指额外的空间开销，新建的n个节点是结果，不算在空间复杂度中
     * 将链表中的节点拆分为两个相邻节点，设置next和random之后进行拆分，得到拷贝链表
     * 例如：A->B->C变为A->A'->B->B'->C->C'，再拆分获得A'->B'->C'
     *
     * @param head
     * @return
     */
    public Node copyRandomList2(Node head) {
        if (head == null) {
            return null;
        }

        Node node = head;
        //链表每个节点复制为2个相同节点
        while (node != null) {
            Node tempNode = new Node(node.val);
            tempNode.next = node.next;
            node.next = tempNode;
            node = node.next.next;
        }

        node = head;
        //设置每个新节点的random
        while (node != null) {
            if (node.random != null) {
                node.next.random = node.random.next;
            } else {
                node.next.random = null;
            }
            node = node.next.next;
        }

        Node newHead = head.next;
        node = head;
        //将链表拆分为两个链表，得到拷贝链表
        while (node != null) {
            Node newNode = node.next;
            if (node.next.next != null) {
                node.next = node.next.next;
                newNode.next = newNode.next.next;
            } else {
                node.next = null;
            }
            node = node.next;
        }

        return newHead;
    }

    static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }
}
