package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/6/15 10:05
 * @Author zsy
 * @Description 二叉树的层序遍历 类比Problem103、Problem107、Problem199、Offer32、Offer32_3 同Offer32_2
 * 给你二叉树的根节点 root ，返回其节点值的 层序遍历。（即逐层地，从左到右访问所有节点）。
 * 输入：root = [3,9,20,null,null,15,7]
 * 输出：[[3],[9,20],[15,7]]
 * <p>
 * 输入：root = [1]
 * 输出：[[1]]
 * <p>
 * 输入：root = []
 * 输出：[]
 * <p>
 * 树中节点数目在范围 [0, 2000] 内
 * -1000 <= Node.val <= 1000
 */
public class Problem102 {
    public static void main(String[] args) {
        Problem102 problem102 = new Problem102();
        String[] data = {"3", "9", "20", "null", "null", "15", "7"};
        TreeNode root = problem102.buildTree(data);
        System.out.println(problem102.levelOrder(root));
        System.out.println(problem102.levelOrder2(root));
        System.out.println(problem102.levelOrder3(root));
    }

    /**
     * bfs，使用size统计树每行元素的个数
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<List<Integer>> result = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            List<Integer> list = new ArrayList<>();
            int size = queue.size();

            while (size > 0) {
                TreeNode node = queue.poll();
                size--;
                list.add(node.val);

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

            result.add(list);
        }

        return result;
    }

    /**
     * bfs，使用两个队列
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder2(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<List<Integer>> result = new ArrayList<>();
        Queue<TreeNode> queue1 = new LinkedList<>();
        Queue<TreeNode> queue2 = new LinkedList<>();
        queue1.offer(root);

        while (!queue1.isEmpty() || !queue2.isEmpty()) {
            List<Integer> list = new ArrayList<>();

            if (!queue1.isEmpty()) {
                while (!queue1.isEmpty()) {
                    TreeNode node = queue1.poll();
                    list.add(node.val);

                    if (node.left != null) {
                        queue2.offer(node.left);
                    }
                    if (node.right != null) {
                        queue2.offer(node.right);
                    }
                }
            } else {
                while (!queue2.isEmpty()) {
                    TreeNode node = queue2.poll();
                    list.add(node.val);

                    if (node.left != null) {
                        queue1.offer(node.left);
                    }
                    if (node.right != null) {
                        queue1.offer(node.right);
                    }
                }
            }

            result.add(list);
        }

        return result;
    }

    /**
     * dfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder3(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<List<Integer>> result = new ArrayList<>();

        dfs(root, 0, result);

        return result;
    }

    private void dfs(TreeNode root, int level, List<List<Integer>> result) {
        if (root == null) {
            return;
        }

        //每行第一次访问，添加list集合
        if (result.size() <= level) {
            result.add(new ArrayList<>());
        }

        List<Integer> list = result.get(level);
        list.add(root.val);

        dfs(root.left, level + 1, result);
        dfs(root.right, level + 1, result);
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
