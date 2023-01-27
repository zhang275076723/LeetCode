package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/3/23 10:03
 * @Author zsy
 * @Description 二叉搜索树与双向链表 二叉树和链表之间转化类比Problem114、Problem430 二叉搜索树类比Problem95、Problem96、Problem98、Problem99、Problem230、Offer33
 * 输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的循环双向链表。
 * 要求不能创建任何新的节点，只能调整树中节点指针的指向。
 * 我们希望将这个二叉搜索树转化为双向循环链表。链表中的每个节点都有一个前驱和后继指针。
 * 对于双向循环链表，第一个节点的前驱是最后一个节点，最后一个节点的后继是第一个节点。
 * 下图展示了上面的二叉搜索树转化成的链表。“head” 表示指向链表中有最小元素的节点。
 * 特别地，我们希望可以就地完成转换操作。
 * 当转化完成以后，树中节点的左指针需要指向前驱，树中节点的右指针需要指向后继。
 * 还需要返回链表中的第一个节点的指针。
 */
public class Offer36 {
    public static void main(String[] args) {
        Offer36 offer36 = new Offer36();
        String[] data = {"4", "2", "5", "1", "3"};
        Node root = offer36.buildTree(data);
//        Node head = offer36.treeToDoublyList(root);
        Node head = offer36.treeToDoublyList2(root);
    }

    /**
     * 中序遍历
     * 将节点保存在list集合中，再按照中序遍历顺序重新赋值list集合中节点的左右指针，构建循环双向链表
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public Node treeToDoublyList(Node root) {
        if (root == null) {
            return null;
        }

        List<Node> list = new ArrayList<>();
        Stack<Node> stack = new Stack<>();
        Node node = root;

        while (!stack.isEmpty() || node != null) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            node = stack.pop();
            list.add(node);
            node = node.right;
        }

        //双向链表头结点
        Node head = list.remove(0);
        Node pre = head;

        while (!list.isEmpty()) {
            node = list.remove(0);
            pre.right = node;
            node.left = pre;
            pre = node;
        }

        //连接尾结点的右指针和头结点的左指针，形成循环双向链表
        pre.right = head;
        head.left = pre;

        return head;
    }

    /**
     * 中序遍历
     * 在中序遍历过程中就修改节点的左右指针，将二叉搜索树转换为循环双向链表
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public Node treeToDoublyList2(Node root) {
        if (root == null) {
            return null;
        }

        Stack<Node> stack = new Stack<>();
        Node node = root;
        //头结点
        Node head = null;
        //当前节点的前驱节点
        Node pre = null;

        while (!stack.isEmpty() || node != null) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            node = stack.pop();

            //pre为空，则当前节点是头结点
            if (pre == null) {
                head = node;
            } else {
                //pre不为空，建立当前节点和前驱节点的双链表关系
                pre.right = node;
                node.left = pre;
            }

            //更新前驱节点
            pre = node;
            //更新当前节点
            node = node.right;
        }

        //连接首尾节点
        pre.right = head;
        head.left = pre;

        return head;
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
                String leftNodeValue = list.remove(0);
                if (!"null".equals(leftNodeValue)) {
                    Node leftNode = new Node(Integer.parseInt(leftNodeValue));
                    node.left = leftNode;
                    queue.offer(leftNode);
                }
            }
            if (!list.isEmpty()) {
                String rightNodeValue = list.remove(0);
                if (!"null".equals(rightNodeValue)) {
                    Node rightNode = new Node(Integer.parseInt(rightNodeValue));
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

        public Node(int val) {
            this.val = val;
        }
    }
}