package com.zhang.java;


import java.util.*;

/**
 * @Date 2022/3/22 18:13
 * @Author zsy
 * @Description 二叉树中和为某一值的路径 保存父节点类比Problem126、Problem863 同Problem113
 * 给你二叉树的根节点 root 和一个整数目标和 targetSum ，
 * 找出所有 从根节点到叶子节点 路径总和等于给定目标和的路径。叶子节点是指没有子节点的节点。
 * 注意：节点val值可能为负，targetSum可能为负
 * <p>
 * 输入：root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
 * 输出：[[5,4,11,2],[5,8,4,5]]
 * <p>
 * 输入：root = [1,2,3], targetSum = 5
 * 输出：[]
 * <p>
 * 输入：root = [1,2], targetSum = 0
 * 输出：[]
 * <p>
 * 树中节点总数在范围 [0, 5000] 内
 * -1000 <= Node.val <= 1000
 * -1000 <= targetSum <= 1000
 */
public class Offer34 {
    public static void main(String[] args) {
        Offer34 offer34 = new Offer34();
        String[] data = {"5", "4", "8", "11", "null", "13", "4", "7", "2", "null", "null", "5", "1"};
        TreeNode root = offer34.buildTree(data);
        System.out.println(offer34.pathSum(root, 22));
        System.out.println(offer34.pathSum2(root, 22));
    }

    /**
     * dfs
     * 时间复杂度O(n^2)，空间复杂度O(n) (将满足条件的路径复制到结果集合需要O(n))
     *
     * @param root
     * @param targetSum
     * @return
     */
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<List<Integer>> result = new ArrayList<>();

        dfs(root, 0, targetSum, new ArrayList<>(), result);

        return result;
    }

    /**
     * bfs
     * 使用哈希表存储当前节点的父节点，用于路径复原
     * 时间复杂度O(n^2)，空间复杂度O(n) (将满足条件的路径复制到结果集合需要O(n))
     *
     * @param root
     * @param targetSum
     * @return
     */
    public List<List<Integer>> pathSum2(TreeNode root, int targetSum) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<List<Integer>> result = new ArrayList<>();
        Queue<Pos> queue = new LinkedList<>();
        queue.offer(new Pos(root, root.val));

        //存放当前节点的父节点，用于路径复原
        Map<TreeNode, TreeNode> map = new HashMap<>();
        map.put(root, null);

        while (!queue.isEmpty()) {
            Pos pos = queue.poll();

            //当前节点为叶节点，路径和等于targetSum，将路径加入结果集合
            if (pos.node.left == null && pos.node.right == null && pos.sum == targetSum) {
                result.add(getPath(pos.node, map));
            }

            if (pos.node.left != null) {
                map.put(pos.node.left, pos.node);
                queue.offer(new Pos(pos.node.left, pos.sum + pos.node.left.val));
            }

            if (pos.node.right != null) {
                map.put(pos.node.right, pos.node);
                queue.offer(new Pos(pos.node.right, pos.sum + pos.node.right.val));
            }
        }

        return result;
    }

    private void dfs(TreeNode root, int sum, int targetSum, List<Integer> path, List<List<Integer>> result) {
        if (root == null) {
            return;
        }

        sum = sum + root.val;
        path.add(root.val);

        //当前节点为叶节点，判断路径和是否等于targetSum
        if (root.left == null && root.right == null) {
            if (sum == targetSum) {
                //将路径和等于targetSum的路径复制到结果集合需要O(n)
                result.add(new ArrayList<>(path));
            }

            path.remove(path.size() - 1);
            return;
        }

        dfs(root.left, sum, targetSum, path, result);
        dfs(root.right, sum, targetSum, path, result);

        path.remove(path.size() - 1);
    }

    /**
     * 根据父节点map，获取根节点到当前节点的路径集合
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param node
     * @param map
     * @return
     */
    private List<Integer> getPath(TreeNode node, Map<TreeNode, TreeNode> map) {
        //因为是从叶节点往根节点找，所以需要首添加
        LinkedList<Integer> list = new LinkedList<>();

        while (node != null) {
            //首添加
            list.addFirst(node.val);
            node = map.get(node);
        }

        return list;
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

    /**
     * bfs节点
     */
    public static class Pos {
        TreeNode node;
        //根节点到当前节点的路径和
        int sum;

        Pos(TreeNode node, int sum) {
            this.node = node;
            this.sum = sum;
        }
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }
}
