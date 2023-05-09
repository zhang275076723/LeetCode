package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/4/12 08:35
 * @Author zsy
 * @Description 填充每个节点的下一个右侧节点指针 二叉树和链表之间的转换类比Problem114、Problem117、Problem426、Problem430、Problem897、Offer36
 * 给定一个 完美二叉树 ，其所有叶子节点都在同一层，每个父节点都有两个子节点。
 * 二叉树定义如下：
 * struct Node {
 * int val;
 * Node *left;
 * Node *right;
 * Node *next;
 * }
 * 填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 NULL。
 * 初始状态下，所有 next 指针都被设置为 NULL。
 * <p>
 * 输入：root = [1,2,3,4,5,6,7]
 * 输出：[1,#,2,3,#,4,5,6,7,#]
 * <            1 --> null
 * <        /      \
 * <       2   -->  3 --> null
 * <     /  \      /  \
 * <    4 -> 5 -> 6 -> 7 --> null
 * 解释：给定二叉树如图 A 所示，你的函数应该填充它的每个 next 指针，以指向其下一个右侧节点，如图 B 所示。
 * 序列化的输出按层序遍历排列，同一层节点由 next 指针连接，'#' 标志着每一层的结束。
 * <p>
 * 输入：root = []
 * 输出：[]
 * <p>
 * 树中节点的数量在 [0, 2^12 - 1] 范围内
 * -1000 <= node.val <= 1000
 */
public class Problem116 {
    public static void main(String[] args) {
        Problem116 problem116 = new Problem116();
        String[] data = {"1", "2", "3", "4", "5", "6", "7"};
        Node root = problem116.buildTree(data);
//        root = problem116.connect(root);
        root = problem116.connect2(root);
    }

    /**
     * bfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public Node connect(Node root) {
        if (root == null) {
            return null;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                Node node = queue.poll();
                //当前层中node不是末尾节点，则node的next指针指向当前层中的下一个节点
                if (i != size - 1) {
                    node.next = queue.peek();
                }
                //左右非空子节点入队
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }

        return root;
    }

    /**
     * 使用每层的next指针，根据当前层的next指针将当前层节点看成一个链表，
     * 通过当前层链表对下一层节点的next指针赋值，使之也成为一个链表
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param root
     * @return
     */
    public Node connect2(Node root) {
        if (root == null) {
            return null;
        }

        Node node = root;

        while (node != null) {
            //每层链表的头结点
            Node head = new Node();
            //每层链表的当前节点
            Node temp = head;

            //根据next指针遍历当前层链表节点，对下一层节点的next指针赋值
            while (node != null) {
                if (node.left != null) {
                    temp.next = node.left;
                    temp = temp.next;
                }
                if (node.right != null) {
                    temp.next = node.right;
                    temp = temp.next;
                }

                node = node.next;
            }

            //当前节点赋值为下一层链表的第一个节点
            node = head.next;
        }

        return root;
    }

    private Node buildTree(String[] data) {
        if (data == null || data.length == 0) {
            return null;
        }

        List<String> list = new ArrayList<>(Arrays.asList(data));
        Queue<Node> queue = new LinkedList<>();
        Node root = new Node(Integer.parseInt(list.remove(0)));
        queue.offer(root);

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (!list.isEmpty()) {
                String leftValue = list.remove(0);
                if (!"null".equals(leftValue)) {
                    Node leftNode = new Node(Integer.parseInt(leftValue));
                    node.left = leftNode;
                    queue.offer(leftNode);
                }
            }
            if (!list.isEmpty()) {
                String rightValue = list.remove(0);
                if (!"null".equals(rightValue)) {
                    Node rightNode = new Node(Integer.parseInt(rightValue));
                    node.right = rightNode;
                    queue.offer(rightNode);
                }
            }
        }

        return root;
    }

    static class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {
        }

        public Node(int val) {
            this.val = val;
        }

        public Node(int val, Node left, Node right, Node next) {
            this.val = val;
            this.left = left;
            this.right = right;
            this.next = next;
        }
    }
}
