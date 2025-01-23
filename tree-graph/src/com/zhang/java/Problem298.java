package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/1/26 08:33
 * @Author zsy
 * @Description 二叉树最长连续序列 类比Problem687 dfs类比Problem124、Problem250、Problem337、Problem543、Problem687、Problem968、Problem979、Problem1245、Problem1372、Problem1373、Problem2246、Problem2378、Problem2925
 * 给你一棵指定的二叉树的根节点 root ，请你计算其中 最长连续序列路径 的长度。
 * 最长连续序列路径 是依次递增 1 的路径。
 * 该路径，可以是从某个初始节点到树中任意节点，通过「父 - 子」关系连接而产生的任意路径。
 * 且必须从父节点到子节点，反过来是不可以的。
 * <p>
 * 输入：root = [1,null,3,2,4,null,null,null,5]
 * 输出：3
 * 解释：当中，最长连续序列是 3-4-5 ，所以返回结果为 3 。
 * <p>
 * 输入：root = [2,null,3,2,null,1]
 * 输出：2
 * 解释：当中，最长连续序列是 2-3 。注意，不是 3-2-1，所以返回 2 。
 * <p>
 * 树中节点的数目在范围 [1, 3 * 10^4] 内
 * -3 * 10^4 <= Node.val <= 3 * 10^4
 */
public class Problem298 {
    /**
     * 最长连续序列路径的长度
     */
    private int max = 0;

    public static void main(String[] args) {
        Problem298 problem298 = new Problem298();
        String[] data = {"1", "null", "3", "2", "4", "null", "null", "null", "5"};
//        String[] data = {"2", "null", "3", "2", "null", "1"};
        TreeNode root = problem298.buildTree(data);
        System.out.println(problem298.longestConsecutive(root));
    }

    /**
     * dfs
     * 计算当前节点左右子节点作为根节点的包含根节点的最长单侧连续序列路径长度，更新最长连续序列路径的长度，
     * 返回当前节点对父节点的包含根节点的最长单侧连续序列路径长度，用于计算当前节点父节点作为根节点的包含根节点的最长单侧连续序列路径长度
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int longestConsecutive(TreeNode root) {
        if (root == null) {
            return 0;
        }

        dfs(root);

        return max;
    }

    /**
     * 返回root作为根节点的包含根节点的最长单侧连续序列路径长度
     *
     * @param root
     * @return
     */
    private int dfs(TreeNode root) {
        //路径长度为节点的个数，则空节点返回0
        if (root == null) {
            return 0;
        }

        //当前节点左子节点作为根节点的包含根节点的最长单侧连续序列路径长度
        int leftMax = dfs(root.left);
        //当前节点右子节点作为根节点的包含根节点的最长单侧连续序列路径长度
        int rightMax = dfs(root.right);
        //root作为根节点向左子树的包含根节点的最长单侧连续序列路径长度
        int max1;
        //root作为根节点向右子树的包含根节点的最长单侧连续序列路径长度
        int max2;

        if (root.left != null) {
            if (root.val + 1 == root.left.val) {
                max1 = leftMax + 1;
            } else {
                max1 = 1;
            }
        } else {
            max1 = 1;
        }

        if (root.right != null) {
            if (root.val + 1 == root.right.val) {
                max2 = rightMax + 1;
            } else {
                max2 = 1;
            }
        } else {
            max2 = 1;
        }

        //更新最长连续序列路径的长度
        max = Math.max(max, Math.max(max1, max2));

        //返回当前节点对父节点的包含根节点的最长单侧连续序列路径长度，用于计算当前节点父节点作为根节点的包含根节点的最长单侧连续序列路径长度
        return Math.max(max1, max2);
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
