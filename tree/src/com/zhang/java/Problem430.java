package com.zhang.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @Date 2023/1/10 19:18
 * @Author zsy
 * @Description 扁平化多级双向链表 二叉树和链表之间转化类比Problem114、Problem897、Offer36
 * 你会得到一个双链表，其中包含的节点有一个下一个指针、一个前一个指针和一个额外的 子指针 。
 * 这个子指针可能指向一个单独的双向链表，也包含这些特殊的节点。
 * 这些子列表可以有一个或多个自己的子列表，以此类推，以生成如下面的示例所示的 多层数据结构 。
 * 给定链表的头节点 head ，将链表 扁平化 ，以便所有节点都出现在单层双链表中。
 * 让 curr 是一个带有子列表的节点。子列表中的节点应该出现在扁平化列表中的 curr 之后 和 curr.next 之前 。
 * 返回 扁平列表的 head 。列表中的节点必须将其 所有 子指针设置为 null 。
 * <p>
 * 输入：head = [1,2,3,4,5,6,null,null,null,7,8,9,10,null,null,11,12]
 * 输出：[1,2,3,7,8,11,12,9,10,4,5,6]
 * 解释：输入的多级列表如上图所示。
 * 扁平化后的链表如下图：
 * <p>
 * 输入：head = [1,2,null,3]
 * 输出：[1,3,2]
 * 解释：输入的多级列表如上图所示。
 * 扁平化后的链表如下图：
 * <p>
 * 输入：head = []
 * 输出：[]
 * 说明：输入中可能存在空列表。
 * <p>
 * 节点数目不超过 1000
 * 1 <= Node.val <= 10^5
 * <p>
 * 如何表示测试用例中的多级链表？
 * < 1---2---3---4---5---6--NULL
 * <         |
 * <         7---8---9---10--NULL
 * <             |
 * <             11--12--NULL
 * 序列化其中的每一级之后：
 * [1,2,3,4,5,6,null]
 * [7,8,9,10,null]
 * [11,12,null]
 * 为了将每一级都序列化到一起，我们需要每一级中添加值为 null 的元素，以表示没有节点连接到上一级的上级节点。
 * [1,2,3,4,5,6,null]
 * [null,null,7,8,9,10,null]
 * [null,11,12,null]
 * 合并所有序列化结果，并去除末尾的 null 。
 * [1,2,3,4,5,6,null,null,null,7,8,9,10,null,null,11,12]
 */
public class Problem430 {
    public static void main(String[] args) {
        Problem430 problem430 = new Problem430();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node7 = new Node(7);
        Node node8 = new Node(8);
        Node node9 = new Node(9);
        Node node10 = new Node(10);
        Node node11 = new Node(11);
        Node node12 = new Node(12);
        node1.next = node2;
        node2.prev = node1;
        node2.next = node3;
        node3.prev = node2;
        node3.next = node4;
        node4.prev = node3;
        node4.next = node5;
        node5.prev = node4;
        node5.next = node6;
        node6.prev = node5;
        node7.next = node8;
        node8.prev = node7;
        node8.next = node9;
        node9.prev = node8;
        node9.next = node10;
        node10.prev = node9;
        node11.next = node12;
        node12.prev = node11;
        node3.child = node7;
        node8.child = node11;

//        Node head = problem430.flatten(node1);
        Node head2 = problem430.flatten2(node1);
    }

    /**
     * 前序遍历
     * 根据前序遍历顺序，将节点放在集合中，再按照前序遍历顺序连接节点为链表
     * 注意：将节点的child指针置空
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param head
     * @return
     */
    public Node flatten(Node head) {
        if (head == null) {
            return null;
        }

        List<Node> list = new ArrayList<>();

//        preorder(head, list);
        preorder2(head, list);

        //头结点，即head节点
        Node node = list.remove(0);
        node.child = null;

        while (!list.isEmpty()) {
            //node节点的下一个节点
            Node nextNode = list.remove(0);
            node.next = nextNode;
            nextNode.prev = node;
            //nextNode的child指针置空
            nextNode.child = null;
            node = nextNode;
        }

        return head;
    }

    /**
     * 前序遍历
     * 将当前多级双向链表视为二叉树，child视为左指针，next视为右指针，
     * 根据前序遍历性质，在前序遍历过程中，左子树中最右下节点的下一个遍历到节点是右子树根节点，
     * 所以找当前节点左子树的最右下节点，将当前节点的右子树作为最右下节点的右子树，再将当前节点的左子树作为当前节点的右子树
     * 时间复杂度O(n)，空间复杂度O(1)
     * 例如：
     * <     1                1                1                1                1
     * <      \                \                \                \                \
     * <       2                2                2                2                2
     * <        \                \                \                \                \
     * <         3                3                3                3                3
     * <       /  \             /                   \                \                \
     * <      7    4     =>    7          =>         7       =>       7       =>       7
     * <       \    \           \                     \                \                \
     * <        8    5           8                     8                8                8
     * <      /  \    \        /  \                  /  \             /                   \
     * <    11    9    6     11    9               11    9          11                     11
     * <      \    \           \    \                \    \           \                     \
     * <       12   10          12   10               12   10          12                    12
     * <                              \                     \           \                     \
     * <                               4                     4           9                     9
     * <                                \                     \           \                     \
     * <                                 5                     5           10                    10
     * <                                  \                     \           \                     \
     * <                                   6                     6           4                     4
     * <                                                                      \                     \
     * <                                                                       5                     5
     * <                                                                        \                     \
     * <                                                                         6                     6
     *
     * @param head
     * @return
     */
    public Node flatten2(Node head) {
        if (head == null) {
            return null;
        }

        Node node = head;

        while (node != null) {
            //当前节点左子树的最右下节点
            Node mostRightNode = node.child;

            if (mostRightNode != null) {
                //找当前节点左子树的最右下节点
                while (mostRightNode.next != null) {
                    mostRightNode = mostRightNode.next;
                }


                //当前节点node的右节点的前驱指针prev指向当前节点左子树的最右下节点mostRightNode
                if (node.next != null) {
                    node.next.prev = mostRightNode;
                }
                //当前节点左子树的最右下节点mostRightNode的右指针next指向当前节点node的右子树next
                mostRightNode.next = node.next;
                //将当前节点node的左子树child作为右子树next
                node.next = node.child;
                //当前节点node的左节点的前驱指针prev指向当前节点node
                node.child.prev = node;
                //当前节点的左子树为空，即child指向null
                node.child = null;
            }

            node = node.next;
        }

        return head;
    }

    /**
     * 非递归前序遍历
     *
     * @param root
     * @param list
     */
    private void preorder(Node root, List<Node> list) {
        if (root == null) {
            return;
        }

        Stack<Node> stack = new Stack<>();
        Node node = root;

        while (!stack.isEmpty() || node != null) {
            while (node != null) {
                list.add(node);
                stack.push(node);
                node = node.child;
            }

            node = stack.pop();
            node = node.next;
        }
    }

    private void preorder2(Node root, List<Node> list) {
        if (root == null) {
            return;
        }

        Stack<Node> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            Node node = stack.pop();
            list.add(node);

            //先将右子节点入栈，再将左子节点入栈
            if (node.next != null) {
                stack.push(node.next);
            }
            if (node.child != null) {
                stack.push(node.child);
            }
        }
    }

    static class Node {
        public int val;
        public Node prev;
        public Node next;
        public Node child;

        Node() {

        }

        Node(int val) {
            this.val = val;
        }

        Node(int val, Node prev, Node next, Node child) {
            this.val = val;
            this.prev = prev;
            this.next = next;
            this.child = child;
        }
    }
}
