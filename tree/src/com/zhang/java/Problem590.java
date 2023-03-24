package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/3/24 09:26
 * @Author zsy
 * @Description N 叉树的后序遍历 类比Problem94、Problem144、Problem145、Problem429、Problem589
 * 给定一个 n 叉树的根节点 root ，返回 其节点值的 后序遍历 。
 * n 叉树 在输入中按层序遍历进行序列化表示，每组子节点由空值 null 分隔（请参见示例）。
 * <p>
 * 输入：root = [1,null,3,2,4,null,5,6]
 * 输出：[5,6,3,2,4,1]
 * <p>
 * 输入：root = [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,null,13,null,null,14]
 * 输出：[2,6,14,11,7,3,12,8,4,13,9,10,5,1]
 * <p>
 * 节点总数在范围 [0, 10^4] 内
 * 0 <= Node.val <= 10^4
 * n 叉树的高度小于或等于 1000
 */
public class Problem590 {
    public static void main(String[] args) {
        Problem590 problem590 = new Problem590();
        String[] data = {"1", "null", "2", "3", "4", "5", "null", "null",
                "6", "7", "null", "8", "null", "9", "10", "null", "null",
                "11", "null", "12", "null", "13", "null", "null", "14"};
        Node root = problem590.buildTree(data);
        System.out.println(problem590.postorder(root));
        System.out.println(problem590.postorder2(root));
    }

    /**
     * 递归后序遍历
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public List<Integer> postorder(Node root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<Integer> list = new ArrayList<>();

        postorder(root, list);

        return list;
    }

    /**
     * 非递归后序遍历
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public List<Integer> postorder2(Node root) {
        if (root == null) {
            return new ArrayList<>();
        }

        //使用LinkedList便于首添加，首添加时间复杂度O(1)
        LinkedList<Integer> list = new LinkedList<>();
        Stack<Node> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            Node node = stack.pop();
            //首添加
            list.addFirst(node.val);

            //当前节点node的子节点按照从左到右的顺序压入栈中，出栈时的顺序为从右到左，list首添加，符合后序遍历特点
            for (Node childNode : node.children) {
                stack.push(childNode);
            }
        }

        return list;
    }

    private void postorder(Node root, List<Integer> list) {
        if (root == null) {
            return;
        }

        for (Node childNode : root.children) {
            postorder(childNode, list);
        }

        list.add(root.val);
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
