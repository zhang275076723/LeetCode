package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/5/3 9:03
 * @Author zsy
 * @Description 二叉树中的最大路径和 字节面试题 dfs类比Problem250、Problem298、Problem337、Problem543、Problem687、Problem968、Problem979、Problem1245、Problem1372、Problem1373、Problem2246、Problem2378
 * 路径 被定义为一条从树中任意节点出发，沿父节点-子节点连接，达到任意节点的序列。
 * 同一个节点在一条路径序列中 至多出现一次 。
 * 该路径 至少包含一个 节点，且不一定经过根节点。
 * 路径和 是路径中各节点值的总和。
 * 给你一个二叉树的根节点 root ，返回其 最大路径和 。
 * <p>
 * 输入：root = [1,2,3]
 * 输出：6
 * 解释：最优路径是 2 -> 1 -> 3 ，路径和为 2 + 1 + 3 = 6
 * <p>
 * 输入：root = [-10,9,20,null,null,15,7]
 * 输出：42
 * 解释：最优路径是 15 -> 20 -> 7 ，路径和为 15 + 20 + 7 = 42
 * <p>
 * 树中节点数目范围是 [1, 3 * 10^4]
 * -1000 <= Node.val <= 1000
 */
public class Problem124 {
    /**
     * 二叉树的最大路径和
     */
    private int max = Integer.MIN_VALUE;

    public static void main(String[] args) {
        Problem124 problem124 = new Problem124();
        String[] data = {"-10", "9", "20", "null", "null", "15", "7"};
        TreeNode root = problem124.buildTree(data);
        System.out.println(problem124.maxPathSum(root));
    }

    /**
     * dfs
     * 计算当前节点左右子节点作为根节点的最大单侧路径和，更新二叉树的最大路径和，
     * 返回当前节点对父节点的最大单侧路径和，用于计算当前节点父节点作为根节点的最大单侧路径和
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int maxPathSum(TreeNode root) {
        if (root == null) {
            return 0;
        }

        dfs(root);

        return max;
    }

    /**
     * 返回root作为根节点的最大单侧路径和
     *
     * @param root
     * @return
     */
    private int dfs(TreeNode root) {
        if (root == null) {
            return 0;
        }

        //当前节点左子节点作为根节点的最大单侧路径和
        //注意：因为节点值存在负数，所以最大路径和至少为0，即不选当前路径
        int leftMax = Math.max(dfs(root.left), 0);
        //当前节点右子节点作为根节点的最大单侧路径和
        //注意：因为节点值存在负数，所以最大路径和至少为0，即不选当前路径
        int rightMax = Math.max(dfs(root.right), 0);

        //更新二叉树的最大路径和
        max = Math.max(max, root.val + leftMax + rightMax);

        //返回当前节点对父节点的最大单侧路径和，用于计算当前节点父节点作为根节点的最大单侧路径和
        return Math.max(leftMax, rightMax) + root.val;
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
