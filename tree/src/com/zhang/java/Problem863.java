package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/4/15 08:58
 * @Author zsy
 * @Description 二叉树中所有距离为 K 的结点 字节面试题 保存父节点类比Problem113、Problem126、Offer34 剪枝类比Problem236、Offer68_2 图类比Problem133、Problem207、Problem210、Problem329、Problem399、Problem785
 * 给定一个二叉树（具有根结点 root）， 一个目标结点 target ，和一个整数值 k 。
 * 返回到目标结点 target 距离为 k 的所有结点的值的列表。
 * 答案可以以 任何顺序 返回。
 * <p>
 * 输入：root = [3,5,1,6,2,0,8,null,null,7,4], target = 5, k = 2
 * 输出：[7,4,1]
 * 解释：所求结点为与目标结点（值为 5）距离为 2 的结点，值分别为 7，4，以及 1
 * <p>
 * 输入: root = [1], target = 1, k = 3
 * 输出: []
 * <p>
 * 节点数在 [1, 500] 范围内
 * 0 <= Node.val <= 500
 * Node.val 中所有值 不同
 * 目标结点 target 是树上的结点。
 * 0 <= k <= 1000
 */
public class Problem863 {
    public static void main(String[] args) {
        Problem863 problem863 = new Problem863();
        String[] data = {"3", "5", "1", "6", "2", "0", "8", "null", "null", "7", "4"};
        TreeNode root = problem863.buildTree(data);
        TreeNode target = root.left;
        int k = 2;
        System.out.println(problem863.distanceK(root, target, k));
        System.out.println(problem863.distanceK2(root, target, k));
    }

    /**
     * dfs
     * 哈希表保存当前节点和父节点之间的映射关系，通过哈希表可以实现从当前节点向父节点查找，
     * 从target节点往子节点和父节点找距离为k的节点，使用前驱节点，用于剪枝，避免重复查找
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @param target
     * @param k
     * @return
     */
    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<Integer> list = new ArrayList<>();
        //保存当前节点和父节点之间的映射关系，通过哈希表可以实现从当前节点向父节点路径查找，key：当前节点，value：父节点
        Map<TreeNode, TreeNode> map = new HashMap<>();
        //初始化，root的父节点为空
        map.put(root, null);

        //得到每个节点的父节点map
        buildMap(root, map);

        //从target节点开始往子节点和父节点找距离target节点为k的节点值加入list中
        dfs(target, null, 0, k, map, list);

        return list;
    }

    /**
     * 树转换为图，通过dfs或bfs找距离target节点为k的节点
     * 时间复杂度O(n)，空间复杂度O(n)
     *    0
     *   1
     *    2
     *     3
     *      4
     * @param root
     * @param target
     * @param k
     * @return
     */
    public List<Integer> distanceK2(TreeNode root, TreeNode target, int k) {
        //邻接表表示的图，key：当前节点，value：当前节点的邻接节点集合
        Map<TreeNode, List<TreeNode>> edges = new HashMap<>();
        List<Integer> list = new ArrayList<>();

        //通过树建立邻接表形表的有向图
        buildGraph(root, edges);

//        //图的dfs查找
//        dfs(target, 0, k, new HashSet<>(), edges, list);

        //图的bfs查找
        bfs(target, k, edges, list);

        return list;
    }

    /**
     * 得到每个节点的父节点map，用于从当前节点向父节点路径查找
     *
     * @param root
     * @param map
     */
    private void buildMap(TreeNode root, Map<TreeNode, TreeNode> map) {
        if (root == null) {
            return;
        }

        //左子节点不为空，则左子节点的父节点为当前节点，加入map中
        if (root.left != null) {
            map.put(root.left, root);
        }

        //右子节点不为空，则右子节点的父节点为当前节点，加入map中
        if (root.right != null) {
            map.put(root.right, root);
        }

        buildMap(root.right, map);
        buildMap(root.left, map);
    }

    /**
     * 从root节点开始往子节点和父节点找距离root节点为k的节点值加入list中
     *
     * @param root
     * @param pre      当前遍历到root节点的前驱节点，用于剪枝，避免重复查找
     * @param distance
     * @param k
     * @param map
     * @param list
     */
    private void dfs(TreeNode root, TreeNode pre, int distance, int k, Map<TreeNode, TreeNode> map, List<Integer> list) {
        if (root == null) {
            return;
        }

        //找到距离target节点为k的节点值，加入list集合中
        if (distance == k) {
            list.add(root.val);
        }

        //root父节点不为前驱节点pre，则说明root到root父节点这条路径没有被遍历，可以往父节点找，用于剪枝，避免重复查找
        if (map.get(root) != pre) {
            dfs(map.get(root), root, distance + 1, k, map, list);
        }

        //root左子节点不为前驱节点pre，则说明root到root左子树这条路径没有被遍历，可以往左子树找，用于剪枝，避免重复查找
        if (root.left != pre) {
            dfs(root.left, root, distance + 1, k, map, list);
        }

        //root右子节点不为前驱节点pre，则说明root到root右子树这条路径没有被遍历，可以往右子树找，用于剪枝，避免重复查找
        if (root.right != pre) {
            dfs(root.right, root, distance + 1, k, map, list);
        }
    }

    /**
     * 图的dfs，从target节点开始找距离target节点为k的节点值加入list中
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param node
     * @param distance
     * @param k
     * @param visited
     * @param edges
     * @param list
     */
    private void dfs(TreeNode node, int distance, int k, Set<TreeNode> visited,
                     Map<TreeNode, List<TreeNode>> edges, List<Integer> list) {
        if (node == null) {
            return;
        }

        if (distance == k) {
            list.add(node.val);
        }

        //设置当前节点已访问
        visited.add(node);

        //遍历当前节点没有访问过的邻接节点
        for (TreeNode nextNode : edges.get(node)) {
            if (visited.contains(nextNode)) {
                continue;
            }

            dfs(nextNode, distance + 1, k, visited, edges, list);
        }

        //设置当前节点未访问
        visited.remove(node);
    }

    /**
     * 图的bfs，从target节点开始找距离target节点为k的节点值加入list中
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param target
     * @param k
     * @param edges
     * @param list
     */
    private void bfs(TreeNode target, int k, Map<TreeNode, List<TreeNode>> edges, List<Integer> list) {
        if (target == null) {
            return;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        //节点的访问集合
        Set<TreeNode> visited = new HashSet<>();
        queue.offer(target);
        visited.add(target);
        //bfs扩展的次数
        int count = 0;

        while (!queue.isEmpty()) {
            //当前层元素的个数
            int size = queue.size();

            //k等于bfs扩展的次数，则已经找到了距离target节点为k的节点，加入list集合中
            if (k == count) {
                while (!queue.isEmpty()) {
                    TreeNode node = queue.poll();
                    list.add(node.val);
                }
                return;
            }

            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();

                //遍历当前节点没有访问过的邻接节点，加入队列
                for (TreeNode nextNode : edges.get(node)) {
                    if (visited.contains(nextNode)) {
                        continue;
                    }

                    queue.offer(nextNode);
                    //设置当前节点已访问
                    visited.add(nextNode);
                }
            }

            //bfs扩展的次数加1
            count++;
        }
    }

    /**
     * 通过树建立邻接表形表的有向图，key：当前节点，value：当前节点的邻接节点集合
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @param edges
     */
    private void buildGraph(TreeNode root, Map<TreeNode, List<TreeNode>> edges) {
        if (root == null) {
            return;
        }

        //邻接表中没有当前节点，则创建当前节点和当前节点的邻接节点集合
        if (!edges.containsKey(root)) {
            edges.put(root, new ArrayList<>());
        }

        //左子节点不为空，当前节点和左子节点则存在边，加入邻接表中
        if (root.left != null) {
            //邻接表中没有左子节点，则创建左子节点和左子节点的邻接节点集合
            if (!edges.containsKey(root.left)) {
                edges.put(root.left, new ArrayList<>());
            }

            //当前节点到左子节点的边加入邻接表
            List<TreeNode> list = edges.get(root);
            list.add(root.left);
            //左子节点到当前节点的边加入邻接表
            List<TreeNode> leftNodeList = edges.get(root.left);
            leftNodeList.add(root);
        }

        //右子节点不为空，当前节点和右子节点则存在边，加入邻接表中
        if (root.right != null) {
            //邻接表中没有右子节点，则创建右子节点和右子节点的邻接节点集合
            if (!edges.containsKey(root.right)) {
                edges.put(root.right, new ArrayList<>());
            }

            //当前节点到右子节点的边加入邻接表
            List<TreeNode> list = edges.get(root);
            list.add(root.right);
            //右子节点到当前节点的边加入邻接表
            List<TreeNode> rightNodeList = edges.get(root.right);
            rightNodeList.add(root);
        }

        buildGraph(root.left, edges);
        buildGraph(root.right, edges);
    }

    private TreeNode buildTree(String[] data) {
        if (data == null || data.length == 0) {
            return null;
        }

        List<String> list = new ArrayList<>(Arrays.asList(data));
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode root = new TreeNode(Integer.parseInt(list.remove(0)));
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (!list.isEmpty()) {
                String leftValue = list.remove(0);
                if (!"null".equals(leftValue)) {
                    TreeNode leftNode = new TreeNode(Integer.parseInt(leftValue));
                    node.left = leftNode;
                    queue.offer(leftNode);
                }
            }
            if (!list.isEmpty()) {
                String rightValue = list.remove(0);
                if (!"null".equals(rightValue)) {
                    TreeNode rightNode = new TreeNode(Integer.parseInt(rightValue));
                    node.right = rightNode;
                    queue.offer(rightNode);
                }
            }
        }

        return root;
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
