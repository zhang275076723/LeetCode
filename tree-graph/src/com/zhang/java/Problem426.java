package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/5/8 09:51
 * @Author zsy
 * @Description 将二叉搜索树转化为排序的双向链表 二叉树和链表之间的转换类比Problem114、Problem116、Problem117、Problem430、Problem897 同Offer36
 * 将一个 二叉搜索树 就地转化为一个 已排序的双向循环链表 。
 * 对于双向循环列表，你可以将左右孩子指针作为双向循环链表的前驱和后继指针，
 * 第一个节点的前驱是最后一个节点，最后一个节点的后继是第一个节点。
 * 特别地，我们希望可以 就地 完成转换操作。
 * 当转化完成以后，树中节点的左指针需要指向前驱，树中节点的右指针需要指向后继。
 * 还需要返回链表中最小元素的指针。
 * <p>
 * 输入：root = [4,2,5,1,3]
 * 输出：[1,2,3,4,5]
 * 解释：下图显示了转化后的二叉搜索树，实线表示后继关系，虚线表示前驱关系。
 * <p>
 * 输入：root = [2,1,3]
 * 输出：[1,2,3]
 * <p>
 * 输入：root = []
 * 输出：[]
 * 解释：输入是空树，所以输出也是空链表。
 * <p>
 * 输入：root = [1]
 * 输出：[1]
 * <p>
 * -1000 <= Node.val <= 1000
 * Node.left.val < Node.val < Node.right.val
 * Node.val 的所有值都是独一无二的
 * 0 <= Number of Nodes <= 2000
 */
public class Problem426 {
    public static void main(String[] args) {
        Problem426 problem426 = new Problem426();
        String[] data = {"4", "2", "5", "1", "3"};
        Node root = problem426.buildTree(data);
//        Node head = problem426.treeToDoublyList(root);
        Node head = problem426.treeToDoublyList2(root);
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

        //双向循环链表的头结点
        Node head = list.remove(0);
        Node pre = head;

        while (!list.isEmpty()) {
            node = list.remove(0);
            pre.right = node;
            node.left = pre;
            pre = node;
        }

        //连接首尾节点，构成双向循环链表
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

        //头结点
        Node head = null;
        //中序遍历过程中当前节点的前驱节点
        Node pre = null;
        Stack<Node> stack = new Stack<>();
        Node node = root;

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

        //连接首尾节点，构成双向循环链表
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

        public Node(int val) {
            this.val = val;
        }
    }
}
