package com.zhang.zhang;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/7/9 8:42
 * @Author zsy
 * @Description 复制带随机指针的链表 同Offer35
 * 给你一个长度为 n 的链表，每个节点包含一个额外增加的随机指针 random ，该指针可以指向链表中的任何节点或空节点。
 * 构造这个链表的 深拷贝。
 * 深拷贝应该正好由 n 个 全新 节点组成，其中每个新节点的值都设为其对应的原节点的值。
 * 新节点的 next 指针和 random 指针也都应指向复制链表中的新节点，并使原链表和复制链表中的这些指针能够表示相同的链表状态。
 * 复制链表中的指针都不应指向原链表中的节点 。
 * 例如，如果原链表中有 X 和 Y 两个节点，其中 X.random --> Y 。
 * 那么在复制链表中对应的两个节点 x 和 y ，同样有 x.random --> y 。
 * 返回复制链表的头节点。
 * 用一个由 n 个节点组成的链表来表示输入/输出中的链表。每个节点用一个 [val, random_index] 表示：
 * val：一个表示 Node.val 的整数。
 * random_index：随机指针指向的节点索引（范围从 0 到 n-1）；如果不指向任何节点，则为  null 。
 * 你的代码 只 接受原链表的头节点 head 作为传入参数。
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
 * 0 <= n <= 1000
 * -10^4 <= Node.val <= 10^4
 * Node.random 为 null 或指向链表中的节点。
 */
public class Problem138 {
    public static void main(String[] args) {
        Problem138 problem138 = new Problem138();
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
//        Node newHead = problem138.copyRandomList(head);
        Node newHead = problem138.copyRandomList2(head);
    }

    /**
     * 哈希表
     * 使用哈希表存储原节点和新建节点之间的映射关系
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param head
     * @return
     */
    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }

        //建立节点和新节点之间的映射
        Map<Node, Node> map = new HashMap<>();
        Node node = head;

        while (node != null) {
            map.put(node, new Node(node.val));
            node = node.next;
        }

        node = head;

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
     * 将原节点拆分为原节点和新建节点，然后再将链表分开，得到新节点链表
     * 例如：A->B->C变为A->A'->B->B'->C->C'，再拆分得到A'->B'->C'
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param head
     * @return
     */
    public Node copyRandomList2(Node head) {
        if (head == null) {
            return null;
        }

        Node node = head;

        //复制新节点
        while (node != null) {
            Node tempNode = new Node(node.val);
            tempNode.next = node.next;
            node.next = tempNode;
            node = node.next.next;
        }

        node = head;

        //修改新节点的random指针
        while (node != null) {
            if (node.random == null) {
                node.next.random = null;
            } else {
                node.next.random = node.random.next;
            }
            node = node.next.next;
        }

        node = head;
        Node newHead = node.next;

        //断开原链表，得到新节点链表
        while (node != null) {
            Node newNode = node.next;
            node.next = node.next.next;

            if (newNode.next != null) {
                newNode.next = newNode.next.next;
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
