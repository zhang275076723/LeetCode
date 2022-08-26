package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/3/22 20:19
 * @Author zsy
 * @Description 复杂链表的复制 同Problem138
 * 请实现 copyRandomList 函数，复制一个复杂链表。
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
 * <p>
 * -10000 <= Node.val <= 10000
 * Node.random 为空（null）或指向链表中的节点。
 * 节点数目不超过 1000 。
 */
public class Offer35 {
    public static void main(String[] args) {
        Offer35 offer35 = new Offer35();
        Node head = new Node(7);
        Node node2 = new Node(13);
        Node node3 = new Node(11);
        Node node4 = new Node(10);
        Node node5 = new Node(1);
        head.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node2.random = head;
        node3.random = node5;
        node4.random = node3;
        node5.random = head;
//        Node newHead = offer35.copyRandomList(head);
        Node newHead = offer35.copyRandomList2(head);
        while (newHead != null) {
            System.out.println(newHead.val);
            newHead = newHead.next;
        }
    }

    /**
     * 哈希表，建立原节点和拷贝节点的映射
     * 时间复杂度O(n)，空间复杂的O(n)
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
            Node tempNode = map.get(node);
            tempNode.next = map.get(node.next);
            tempNode.random = map.get(node.random);
            node = node.next;
        }

        return map.get(head);
    }

    /**
     * 节点拆分
     * 将链表中的节点拆分为两个相邻节点，设置next和random之后进行拆分，得到拷贝链表
     * 例如：A->B->C变为A->A'->B->B'->C->C'，再拆分获得A'->B'->C'
     * 时间复杂度O(n)，空间复杂的O(1)
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

        node = head;
        Node newHead = head.next;
        Node newNode = newHead;

        //将链表拆分为两个链表，得到拷贝链表
        while (newNode != null) {
            node.next = node.next.next;

            if (newNode.next != null) {
                newNode.next = newNode.next.next;
            }

            node = node.next;
            newNode = newNode.next;
        }

        return newHead;
    }

    private static class Node {
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
