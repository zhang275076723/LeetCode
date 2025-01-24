package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/2/21 09:23
 * @Author zsy
 * @Description 最长同值路径 类比Problem298 dfs类比Problem124、Problem250、Problem298、Problem337、Problem543、Problem968、Problem979、Problem1245、Problem1372、Problem1373、Problem2246、Problem2378、Problem2925、Problem2973
 * 给定一个二叉树的 root ，返回 最长的路径的长度 ，这个路径中的 每个节点具有相同值 。
 * 这条路径可以经过也可以不经过根节点。
 * 两个节点之间的路径长度 由它们之间的边数表示。
 * <p>
 * 输入：root = [5,4,5,1,1,5]
 * 输出：2
 * <p>
 * 输入：root = [1,4,5,4,4,5]
 * 输出：2
 * <p>
 * 树的节点数的范围是 [0, 10^4]
 * -1000 <= Node.val <= 1000
 * 树的深度将不超过 1000
 */
public class Problem687 {
    /**
     * 二叉树的最长同值路径长度
     */
    private int max = 0;

    public static void main(String[] args) {
        Problem687 problem687 = new Problem687();
        String[] data = {"5", "4", "5", "1", "1", "5"};
        TreeNode root = problem687.buildTree(data);
        System.out.println(problem687.longestUnivaluePath(root));
    }

    /**
     * dfs
     * 计算当前节点左右子节点作为根节点的包含根节点的最长单侧同值路径长度，更新二叉树的最长同值路径长度，
     * 返回当前节点对父节点的包含根节点的最长单侧同值路径长度，用于计算当前节点父节点作为根节点的包含根节点的最长单侧同值路径长度
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int longestUnivaluePath(TreeNode root) {
        if (root == null) {
            return 0;
        }

        dfs(root);

        return max;
    }

    /**
     * 返回root作为根节点的包含根节点的最长单侧同值路径长度
     *
     * @param root
     * @return
     */
    private int dfs(TreeNode root) {
        //路径长度为节点的边数，则空节点返回-1
        if (root == null) {
            return -1;
        }

        //当前节点左子节点作为根节点的包含根节点的最长单侧同值路径长度
        int leftMax = dfs(root.left);
        //当前节点右子节点作为根节点的包含根节点的最长单侧同值路径长度
        int rightMax = dfs(root.right);
        //root作为根节点向左子树的包含根节点的最长单侧同值路径长度
        int max1;
        //root作为根节点向右子树的包含根节点的最长单侧同值路径长度
        int max2;

        if (root.left != null && root.left.val == root.val) {
            max1 = leftMax + 1;
        } else {
            max1 = 0;
        }

        if (root.right != null && root.right.val == root.val) {
            max2 = rightMax + 1;
        } else {
            max2 = 0;
        }

        //更新二叉树的最长同值路径长度
        max = Math.max(max, max1 + max2);

        //返回当前节点对父节点的包含根节点的最长单侧同值路径长度，用于计算当前节点父节点作为根节点的包含根节点的最长单侧同值路径长度
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
