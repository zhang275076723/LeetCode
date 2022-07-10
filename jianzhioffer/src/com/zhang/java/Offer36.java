package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/3/23 10:03
 * @Author zsy
 * @Description 二叉搜索树与双向链表
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
    /**
     * treeToDoublyList2中，当前节点的前驱节点
     */
    private Node pre;

    /**
     * treeToDoublyList2中，双向链表的头结点
     */
    private Node head;

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
     * 时间复杂度O(n)，空间复杂度O(n)
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

            //第一个节点的前驱是最后一个节点
            if (i == 0) {
                preNode = nodeList.get(nodeList.size() - 1);
            } else {
                preNode = nodeList.get(i - 1);
            }

            //最后一个节点的后继是第一个节点
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
     * 时间复杂度O(n)，空间复杂度O(n)
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
        head.left = pre;
        pre.right = head;

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

        //pre为空，则说明是第一个节点，即头结点
        if (pre == null) {
            head = root;
        } else {
            //pre不为空，则说明是中间的节点，pre保存当前节点的上一个节点
            pre.right = root;
            root.left = pre;
        }
        //更新pre
        pre = root;

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