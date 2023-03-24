package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/3/24 08:46
 * @Author zsy
 * @Description N 叉树的前序遍历 类比Problem94、Problem144、Problem145、Problem429、Problem590
 * 给定一个 n 叉树的根节点 root ，返回 其节点值的 前序遍历 。
 * n 叉树 在输入中按层序遍历进行序列化表示，每组子节点由空值 null 分隔（请参见示例）。
 * <p>
 * 输入：root = [1,null,3,2,4,null,5,6]
 * 输出：[1,3,5,6,2,4]
 * <p>
 * 输入：root = [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,null,13,null,null,14]
 * 输出：[1,2,3,6,7,11,14,4,8,12,5,9,13,10]
 * <p>
 * 节点总数在范围 [0, 10^4]内
 * 0 <= Node.val <= 10^4
 * n 叉树的高度小于或等于 1000
 */
public class Problem589 {
    public static void main(String[] args) {
        Problem589 problem589 = new Problem589();
        String[] data = {"1", "null", "2", "3", "4", "5", "null", "null",
                "6", "7", "null", "8", "null", "9", "10", "null", "null",
                "11", "null", "12", "null", "13", "null", "null", "14"};
        Node root = problem589.buildTree(data);
        System.out.println(problem589.preorder(root));
        System.out.println(problem589.preorder2(root));
    }

    /**
     * 递归前序遍历
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public List<Integer> preorder(Node root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<Integer> list = new ArrayList<>();

        preorder(root, list);

        return list;
    }

    /**
     * 非递归前序遍历
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public List<Integer> preorder2(Node root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<Integer> list = new ArrayList<>();
        Stack<Node> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            Node node = stack.pop();
            list.add(node.val);

            //当前节点node的子节点按照从右到左的顺序压入栈中
            for (int i = node.children.size() - 1; i >= 0; i--) {
                stack.push(node.children.get(i));
            }
        }

        return list;
    }

    private void preorder(Node root, List<Integer> list) {
        if (root == null) {
            return;
        }

        list.add(root.val);

        for (Node childNode : root.children) {
            preorder(childNode, list);
        }
    }

    /**
     * N叉树建树
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @return
     */
    private Node buildTree(String[] data) {
        if (data == null || data.length == 0) {
            return null;
        }

        List<String> list = new ArrayList<>(Arrays.asList(data));
        Queue<Node> queue = new LinkedList<>();
        Node root = new Node(Integer.parseInt(list.remove(0)));
        //移除根节点值后面的null
        list.remove(0);
        queue.offer(root);

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            List<Node> children = new ArrayList<>();

            while (!list.isEmpty()) {
                String nodeValue = list.remove(0);
                //当前值为null，表明当前节点的子节点已经赋值完毕，直接跳出循环
                if ("null".equals(nodeValue)) {
                    break;
                }
                Node childNode = new Node(Integer.parseInt(nodeValue));
                children.add(childNode);
                queue.offer(childNode);
            }

            node.children = children;
        }

        return root;
    }

    static class Node {
        public int val;
        public List<Node> children;

        public Node() {
        }

        public Node(int val) {
            this.val = val;
        }

        public Node(int val, List<Node> children) {
            this.val = val;
            this.children = children;
        }
    }
}
