package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/3/21 16:41
 * @Author zsy
 * @Description 上到下打印二叉树 II 类比Problem103、Problem107、Offer32、Offer32_3 同Problem102
 * 从上到下按层打印二叉树，同一层的节点按从左到右的顺序打印，每一层打印到一行。
 * <p>
 * 给定二叉树: [3, 9, 20, null, null, 15, 7]
 * 返回其层次遍历结果：[[3], [9,20], [15,7]]
 */
public class Offer32_2 {
    public static void main(String[] args) {
        Offer32_2 offer32_2 = new Offer32_2();
        String[] data = {"3", "9", "20", "null", "null", "15", "7"};
        TreeNode root = offer32_2.buildTree(data);
        System.out.println(offer32_2.levelOrder(root));
        System.out.println(offer32_2.levelOrder2(root));
        System.out.println(offer32_2.levelOrder3(root));
    }

    /**
     * 使用size记录数每行元素的个数
     *  时间复杂度O(n)，空间复杂度O(n)
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
            int size = queue.size();
            List<Integer> list = new ArrayList<>();

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
     * 使用两个队列
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

        backtrack(root, 0, result);

        return result;
    }

    /**
     * @param root   当前根节点
     * @param level  当前树的层数
     * @param result 结果集合
     */
    public void backtrack(TreeNode root, int level, List<List<Integer>> result) {
        if (root == null) {
            return;
        }

        if (result.size() <= level) {
            result.add(new ArrayList<>());
        }

        List<Integer> list = result.get(level);
        list.add(root.val);

        backtrack(root.left, level + 1, result);
        backtrack(root.right, level + 1, result);
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
