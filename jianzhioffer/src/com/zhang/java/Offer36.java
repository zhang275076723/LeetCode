package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/3/23 10:03
 * @Author zsy
 * @Description 输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的循环双向链表。
 * 要求不能创建任何新的节点，只能调整树中节点指针的指向。
 */
public class Offer36 {
    //用于treeToDoublyList2
    Node preNode;
    Node head;

    public static void main(String[] args) {
        Offer36 offer36 = new Offer36();
        Node node1 = new Node(4);
        Node node2 = new Node(2);
        Node node3 = new Node(5);
        Node node4 = new Node(1);
        Node node5 = new Node(3);
        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        node2.right = node5;
//        Node head = offer36.treeToDoublyList(node1);
        Node head = offer36.treeToDoublyList2(node1);
        if (head == null) {
            return;
        } else if (head.right == head) {
            System.out.println(head.val);
        } else {
            Node node = head;
            while (node.right != head) {
                System.out.println(node.val);
                node = node.right;
            }
            System.out.println(node.val);
        }
    }

    /**
     * 中序遍历，将节点保存在集合中，再重新赋值左右指针，构建双向链表
     *
     * @param root
     * @return
     */
    public Node treeToDoublyList(Node root) {
        if (root == null) {
            return null;
        }

        List<Node> nodeList = new ArrayList<>();
        inorder(root, nodeList);
        Node node;
        Node preNode;
        Node nextNode;
        //从中序遍历集合调整左右指针，构建双向链表
        for (int i = 0; i < nodeList.size(); i++) {
            node = nodeList.get(i);
            if (i == 0) {
                preNode = nodeList.get(nodeList.size() - 1);
            } else {
                preNode = nodeList.get(i - 1);
            }
            if (i == nodeList.size() - 1) {
                nextNode = nodeList.get(0);
            } else {
                nextNode = nodeList.get(i + 1);
            }
            node.left = preNode;
            node.right = nextNode;
        }
        return nodeList.get(0);
    }

    /**
     * 递归，中序遍历，使用两个指针preNode和head，构建双向链表
     *
     * @param root
     * @return
     */
    public Node treeToDoublyList2(Node root) {
        if (root == null) {
            return null;
        }

        inorder2(root);
        //连接首尾指针，构成双向链表
        head.left = preNode;
        preNode.right = head;
        return head;
    }

    public void inorder(Node root, List<Node> nodeList) {
        if (root == null) {
            return;
        }

        inorder(root.left, nodeList);
        nodeList.add(root);
        inorder(root.right, nodeList);
    }

    public void inorder2(Node root) {
        if (root == null) {
            return;
        }

        inorder2(root.left);
        if (preNode == null) {
            //找到head
            head = root;
        } else {
            //设置preNode
            preNode.right = root;
            root.left = preNode;
        }
        preNode = root;
        inorder2(root.right);
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