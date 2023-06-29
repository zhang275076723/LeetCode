package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/6/29 08:50
 * @Author zsy
 * @Description 在每个树行中找最大值 类比Problem513、Problem637、Problem662
 * 给定一棵二叉树的根节点 root ，请找出该二叉树中每一层的最大值。
 * <p>
 * 输入: root = [1,3,2,5,3,null,9]
 * 输出: [1,3,9]
 * <p>
 * 输入: root = [1,2,3]
 * 输出: [1,3]
 * <p>
 * 二叉树的节点个数的范围是 [0,10^4]
 * -2^31 <= Node.val <= 2^31 - 1
 */
public class Problem515 {
    public static void main(String[] args) {
        Problem515 problem515 = new Problem515();
        String[] data = {"1", "3", "2", "5", "3", "null", "9"};
        TreeNode root = problem515.buildTree(data);
        System.out.println(problem515.largestValues(root));
        System.out.println(problem515.largestValues2(root));
    }

    /**
     * bfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public List<Integer> largestValues(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<Integer> list = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            //二叉树当前层的最大值
            int max = queue.peek().val;

            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                max = Math.max(max, node.val);

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

            list.add(max);
        }

        return list;
    }

    /**
     * dfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public List<Integer> largestValues2(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<Integer> list = new ArrayList<>();

        //根节点为第0层
        dfs(root, 0, list);

        return list;
    }

    private void dfs(TreeNode root, int level, List<Integer> list) {
        if (root == null) {
            return;
        }

        //当前节点是当前层遍历到的第一个节点，当前节点作为当前层的最大节点
        if (list.size() == level) {
            list.add(root.val);
        } else {
            //当前节点的值大于list中保存的当前层中的最大值，更新当前层的最大值
            if (list.get(level) < root.val) {
                list.set(level, root.val);
            }
        }

        dfs(root.left, level + 1, list);
        dfs(root.right, level + 1, list);
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
