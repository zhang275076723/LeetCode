package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/3/24 09:55
 * @Author zsy
 * @Description N 叉树的最大深度 类比Problem104、Problem110、Problem111、Problem559 n叉树类比Problem429、Problem589、Problem590
 * 给定一个 N 叉树，找到其最大深度。
 * 最大深度是指从根节点到最远叶子节点的最长路径上的节点总数。
 * N 叉树输入按层序遍历序列化表示，每组子节点由空值分隔（请参见示例）。
 * <p>
 * 输入：root = [1,null,3,2,4,null,5,6]
 * 输出：3
 * <p>
 * 输入：root = [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,null,13,null,null,14]
 * 输出：5
 * <p>
 * 树的深度不会超过 1000 。
 * 树的节点数目位于 [0, 10^4] 之间。
 */
public class Problem559 {
    public static void main(String[] args) {
        Problem559 problem559 = new Problem559();
        String[] data = {"1", "null", "2", "3", "4", "5", "null", "null",
                "6", "7", "null", "8", "null", "9", "10", "null", "null",
                "11", "null", "12", "null", "13", "null", "null", "14"};
        Node root = problem559.buildTree(data);
        System.out.println(problem559.maxDepth(root));
        System.out.println(problem559.maxDepth2(root));
    }

    /**
     * dfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int maxDepth(Node root) {
        if (root == null) {
            return 0;
        }

        int maxDepth = 0;

        for (Node childNode : root.children) {
            maxDepth = Math.max(maxDepth, maxDepth(childNode));
        }

        return maxDepth + 1;
    }

    /**
     * bfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int maxDepth2(Node root) {
        if (root == null) {
            return 0;
        }

        int maxDepth = 0;
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                Node node = queue.poll();

                for (Node childNode : node.children) {
                    queue.offer(childNode);
                }
            }

            //当前层遍历完，树的深度加1
            maxDepth++;
        }

        return maxDepth;
    }

    /**
     * N叉树建树
     * 注意：每个节点子节点的元素都是以null结尾，除了最后一个节点
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
            node.children = children;

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
