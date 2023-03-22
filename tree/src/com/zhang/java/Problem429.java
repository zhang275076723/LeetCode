package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/3/23 08:12
 * @Author zsy
 * @Description N 叉树的层序遍历 类比Problem102、Problem103、Problem107
 * 给定一个 N 叉树，返回其节点值的层序遍历。（即从左到右，逐层遍历）。
 * 树的序列化输入是用层序遍历，每组子节点都由 null 值分隔（参见示例）。
 * <p>
 * 输入：root = [1,null,3,2,4,null,5,6]
 * 输出：[[1],[3,2,4],[5,6]]
 * <p>
 * 输入：root = [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,null,13,null,null,14]
 * 输出：[[1],[2,3,4,5],[6,7,8,9,10],[11,12,13],[14]]
 * <p>
 * 树的高度不会超过 1000
 * 树的节点总数在 [0, 10^4] 之间
 */
public class Problem429 {
    public static void main(String[] args) {
        Problem429 problem429 = new Problem429();
        String[] data = {"1", "null", "2", "3", "4", "5", "null", "null",
                "6", "7", "null", "8", "null", "9", "10", "null", "null",
                "11", "null", "12", "null", "13", "null", "null", "14"};
        Node root = problem429.buildTree(data);
        System.out.println(problem429.levelOrder(root));
    }

    /**
     * bfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder(Node root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<List<Integer>> result = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            List<Integer> list = new ArrayList<>();
            //当前层元素的个数
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                Node node = queue.poll();
                list.add(node.val);
                List<Node> childrenNodeList = node.children;

                for (Node childrenNode : childrenNodeList) {
                    queue.offer(childrenNode);
                }
            }

            result.add(list);
        }

        return result;
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
            List<Node> childrenNodeList = new ArrayList<>();

            while (!list.isEmpty()) {
                String childrenValue = list.remove(0);
                //当前值为null，表明当前节点的子节点已经赋值完毕，直接跳出循环
                if ("null".equals(childrenValue)) {
                    break;
                }
                Node childrenNode = new Node(Integer.parseInt(childrenValue));
                childrenNodeList.add(childrenNode);
                queue.offer(childrenNode);
            }

            node.children = childrenNodeList;
        }

        return root;
    }

    /**
     * N叉树节点
     */
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
