package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/1/24 09:55
 * @Author zsy
 * @Description 克隆图 节点复制类比Problem138、Offer35 图类比Problem207、Problem210、Problem329、Problem399
 * 给你无向 连通 图中一个节点的引用，请你返回该图的 深拷贝（克隆）。
 * 图中的每个节点都包含它的值 val（int） 和其邻居的列表（list[Node]）。
 * <p>
 * 测试用例格式：
 * 简单起见，每个节点的值都和它的索引相同。
 * 例如，第一个节点值为 1（val = 1），第二个节点值为 2（val = 2），以此类推。
 * 该图在测试用例中使用邻接列表表示。
 * 邻接列表 是用于表示有限图的无序列表的集合。每个列表都描述了图中节点的邻居集。
 * 给定节点将始终是图中的第一个节点（值为 1）。你必须将 给定节点的拷贝 作为对克隆图的引用返回。
 * <p>
 * 输入：adjList = [[2,4],[1,3],[2,4],[1,3]]
 * 输出：[[2,4],[1,3],[2,4],[1,3]]
 * 解释：
 * 图中有 4 个节点。
 * 节点 1 的值是 1，它有两个邻居：节点 2 和 4 。
 * 节点 2 的值是 2，它有两个邻居：节点 1 和 3 。
 * 节点 3 的值是 3，它有两个邻居：节点 2 和 4 。
 * 节点 4 的值是 4，它有两个邻居：节点 1 和 3 。
 * <p>
 * 输入：adjList = [[]]
 * 输出：[[]]
 * 解释：输入包含一个空列表。该图仅仅只有一个值为 1 的节点，它没有任何邻居。
 * <p>
 * 输入：adjList = []
 * 输出：[]
 * 解释：这个图是空的，它不含任何节点。
 * <p>
 * 输入：adjList = [[2],[1]]
 * 输出：[[2],[1]]
 * <p>
 * 节点数不超过 100 。
 * 每个节点值 Node.val 都是唯一的，1 <= Node.val <= 100。
 * 无向图是一个简单图，这意味着图中没有重复的边，也没有自环。
 * 由于图是无向的，如果节点 p 是节点 q 的邻居，那么节点 q 也必须是节点 p 的邻居。
 * 图是连通图，你可以从给定节点访问到所有节点。
 */
public class Problem133 {
    public static void main(String[] args) {
        Problem133 problem133 = new Problem133();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        node1.neighbors.add(node2);
        node1.neighbors.add(node4);
        node2.neighbors.add(node1);
        node2.neighbors.add(node3);
        node3.neighbors.add(node2);
        node3.neighbors.add(node4);
        node4.neighbors.add(node1);
        node4.neighbors.add(node3);

        Node copyNode = problem133.cloneGraph(node1);
//        Node copyNode2 = problem133.cloneGraph2(node1);
    }

    /**
     * dfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param node
     * @return
     */
    public Node cloneGraph(Node node) {
        if (node == null) {
            return null;
        }

        //节点和新节点之间的映射map
        Map<Node, Node> map = new HashMap<>();

        return dfs(node, map);
    }

    /**
     * bfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param node
     * @return
     */
    public Node cloneGraph2(Node node) {
        if (node == null) {
            return null;
        }

        //节点和新节点之间的映射map
        Map<Node, Node> map = new HashMap<>();
        Node copyNode = new Node(node.val);
        map.put(node, copyNode);

        Queue<Node> queue = new LinkedList<>();
        queue.offer(node);

        while (!queue.isEmpty()) {
            Node tempNode = queue.poll();
            //当前节点的复制节点
            Node tempCopyNode = map.get(tempNode);

            //遍历当前节点tempNode的neighbors节点，对tempCopyNode的neighbors进行赋值
            for (Node neighborNode : tempNode.neighbors) {
                //neighborNode的映射节点不在map中，map中添加节点映射，并将neighborNode入队
                if (!map.containsKey(neighborNode)) {
                    map.put(neighborNode, new Node(neighborNode.val));
                    queue.offer(neighborNode);
                }

                Node copyNeighborNode = map.get(neighborNode);
                tempCopyNode.neighbors.add(copyNeighborNode);
            }
        }

        return copyNode;
    }

    private Node dfs(Node node, Map<Node, Node> map) {
        //当前节点的拷贝节点存在于map中，直接返回当前节点的复制节点
        if (map.containsKey(node)) {
            return map.get(node);
        }

        //当前节点的拷贝节点，加入到map中
        Node copyNode = new Node(node.val);
        map.put(node, copyNode);

        //遍历当前节点node的neighbors节点，对copyNode的neighbors进行赋值
        for (Node neighborNode : node.neighbors) {
            Node copyNeighborNode = dfs(neighborNode, map);
            copyNode.neighbors.add(copyNeighborNode);
        }

        return copyNode;
    }

    static class Node {
        public int val;
        public List<Node> neighbors;

        public Node() {
            val = 0;
            neighbors = new ArrayList<>();
        }

        public Node(int val) {
            this.val = val;
            neighbors = new ArrayList<>();
        }

        public Node(int val, ArrayList<Node> neighbors) {
            this.val = val;
            this.neighbors = neighbors;
        }
    }
}
