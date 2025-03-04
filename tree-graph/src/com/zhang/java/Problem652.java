package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/5/31 08:33
 * @Author zsy
 * @Description 寻找重复的子树 阿里面试题 华为机试题 类比Problem536、Problem606、Problem1948 类比Problem271、Problem297、Problem331、Problem449、Problem535、Problem820、Problem1948、Offer37
 * 给你一棵二叉树的根节点 root ，返回所有 重复的子树 。
 * 对于同一类的重复子树，你只需要返回其中任意 一棵 的根结点即可。
 * 如果两棵树具有 相同的结构 和 相同的结点值 ，则认为二者是 重复 的。
 * <p>
 * 输入：root = [1,2,3,4,null,2,4,null,null,4]
 * 输出：[[2,4],[4]]
 * <p>
 * 输入：root = [2,1,1]
 * 输出：[[1]]
 * <p>
 * 输入：root = [2,2,2,3,null,3,null]
 * 输出：[[2,3],[3]]
 * <p>
 * 树中的结点数在 [1, 5000] 范围内。
 * -200 <= Node.val <= 200
 */
public class Problem652 {
    public static void main(String[] args) {
        Problem652 problem652 = new Problem652();
        String[] data = {"1", "2", "3", "4", "null", "2", "4", "null", "null", "4"};
        TreeNode root = problem652.buildTree(data);
        System.out.println(problem652.findDuplicateSubtrees(root));
    }

    /**
     * 哈希表+dfs+序列化
     * 每个节点序列化为字符串，如果序列化字符串相同，则为相同子树；序列化字符串不相同，则为不同子树
     * 时间复杂度O(n^2)，空间复杂度O(n^2) (每个节点需要O(n)拼接序列化后的字符串，则所有节点需要O(n^2))
     *
     * @param root
     * @return
     */
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<TreeNode> list = new ArrayList<>();
        //key：当前节点为根节点的树前序遍历序列化的字符串，value：当前序列化的字符串出现的次数
        Map<String, Integer> map = new HashMap<>();

        serialize(root, list, map);

        return list;
    }

    private String serialize(TreeNode node, List<TreeNode> list, Map<String, Integer> map) {
        if (node == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        //当前节点和子节点之间用"()"
        sb.append(node.val).
                append('(').append(serialize(node.left, list, map)).append(')').
                append('(').append(serialize(node.right, list, map)).append(')');


        //当前节点前序遍历序列化的字符串
        String serializeStr = sb.toString();

        //serializeStr出现次数为2，则当前节点为重复的子树，加入list中
        map.put(serializeStr, map.getOrDefault(serializeStr, 0) + 1);

        if (map.get(serializeStr) == 2) {
            list.add(node);
        }

        return serializeStr;
    }

//    /**
//     * dfs前序遍历+序列化
//     * 每个节点序列化为字符串，如果序列化字符串相同，则为相同子树；序列化字符串不相同，则为不同子树
//     * 时间复杂度O(n^3)，空间复杂度O(n^2) (每个节点需要O(n)拼接序列化后的字符串，每个节点需要遍历到叶节点，即需要O(n^2)，则所有节点需要O(n^3))
//     *
//     * @param root
//     * @return
//     */
//    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
//        if (root == null) {
//            return new ArrayList<>();
//        }
//
//        List<TreeNode> list = new ArrayList<>();
//        //key：当前节点前序遍历序列化的字符串，value：key出现的次数
//        Map<String, Integer> map = new HashMap<>();
//
//        dfs(root, list, map);
//
//        return list;
//    }
//
//    private void dfs(TreeNode node, List<TreeNode> list, Map<String, Integer> map) {
//        if (node == null) {
//            return;
//        }
//
//        //当前节点前序遍历序列化的字符串
//        String serialize = serialize(node);
//
//        map.put(serialize, map.getOrDefault(serialize, 0) + 1);
//
//        //serialize出现次数为2，则当前节点为重复的子树，加入list中
//        if (map.get(serialize) == 2) {
//            list.add(node);
//        }
//
//        dfs(node.left, list, map);
//        dfs(node.right, list, map);
//    }
//
//    /**
//     * 前序遍历序列化的字符串
//     *
//     * @param node
//     * @return
//     */
//    private String serialize(TreeNode node) {
//        //空节点表示为"null"
//        if (node == null) {
//            return "null";
//        }
//
//        StringBuilder sb = new StringBuilder();
//
//        //不同节点之间用','分割
//        sb.append(node.val).append(',').append(serialize(node.left)).append(',').append(serialize(node.right));
//
//        return sb.toString();
//    }

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

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
