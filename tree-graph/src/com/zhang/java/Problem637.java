package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/6/29 10:32
 * @Author zsy
 * @Description 二叉树的层平均值 类比Problem199、Problem513、Problem515、Problem662、Problem1161、Problem1302
 * 给定一个非空二叉树的根节点 root , 以数组的形式返回每一层节点的平均值。与实际答案相差 10^-5 以内的答案可以被接受。
 * <p>
 * 输入：root = [3,9,20,null,null,15,7]
 * 输出：[3.00000,14.50000,11.00000]
 * 解释：第 0 层的平均值为 3,第 1 层的平均值为 14.5,第 2 层的平均值为 11 。
 * 因此返回 [3, 14.5, 11] 。
 * <p>
 * 输入：root = [3,9,20,15,7]
 * 输出：[3.00000,14.50000,11.00000]
 * <p>
 * 树中节点数量在 [1, 10^4] 范围内
 * -2^31 <= Node.val <= 2^31 - 1
 */
public class Problem637 {
    public static void main(String[] args) {
        Problem637 problem637 = new Problem637();
        String[] data = {"3", "9", "20", "null", "null", "15", "7"};
        TreeNode root = problem637.buildTree(data);
        System.out.println(problem637.averageOfLevels(root));
        System.out.println(problem637.averageOfLevels2(root));
    }

    /**
     * bfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public List<Double> averageOfLevels(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<Double> list = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            //当前层节点值之和，使用double避免相加int溢出
            double sum = 0;

            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                sum = sum + node.val;

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

            list.add(sum / size);
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
    public List<Double> averageOfLevels2(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<Double> result = new ArrayList<>();
        //保存每层节点值之和，使用double避免相加int溢出
        List<Double> sumList = new ArrayList<>();
        //保存每层节点个数
        List<Integer> countList = new ArrayList<>();

        //根节点为第0层
        dfs(root, 0, sumList, countList);

        for (int i = 0; i < sumList.size(); i++) {
            result.add(sumList.get(i) / countList.get(i));
        }

        return result;
    }

    private void dfs(TreeNode root, int level, List<Double> sumList, List<Integer> countList) {
        if (root == null) {
            return;
        }

        //当前节点是当前层遍历到的第一个节点
        if (sumList.size() == level) {
            sumList.add((double) root.val);
            countList.add(1);
        } else if (sumList.size() > level) {
            //当前节点不是当前层遍历到的第一个节点，更新sumList和countList
            sumList.set(level, sumList.get(level) + root.val);
            countList.set(level, countList.get(level) + 1);
        }

        dfs(root.left, level + 1, sumList, countList);
        dfs(root.right, level + 1, sumList, countList);
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
