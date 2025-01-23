package com.zhang.java;

import java.util.*;

/**
 * @Date 2025/3/8 08:21
 * @Author zsy
 * @Description 统计同值子树 dfs类比Problem124、Problem298、Problem337、Problem543、Problem687、Problem968、Problem979、Problem1245、Problem1372、Problem1373、Problem2246、Problem2378、Problem2925
 * 给定一个二叉树，统计该二叉树数值相同的子树个数。
 * 同值子树是指该子树的所有节点都拥有相同的数值。
 * <p>
 * 输入: root = [5,1,5,5,5,null,5]
 * 输出: 4
 * 解释：
 * <              5
 * <             / \
 * <            1   5
 * <           / \   \
 * <          5   5   5
 * 同值子树：叶节点5、叶节点5、叶节点5、路径5-5
 */
public class Problem250 {
    /**
     * 二叉树数值相同的子树个数
     */
    private int count = 0;

    public static void main(String[] args) {
        Problem250 problem250 = new Problem250();
        String[] data = {"5", "1", "5", "5", "5", "null", "5"};
        TreeNode root = problem250.buildTree(data);
        System.out.println(problem250.countUnivalSubtrees(root));
    }

    /**
     * dfs
     * 判断当前节点左右子节点作为根节点的树中节点值是否都相同，更新二叉树数值相同的子树个数
     * 返回当前节点作为根节点的树中节点值是否都相同，用于判断当前节点父节点作为根节点的树中节点值是否都相同
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int countUnivalSubtrees(TreeNode root) {
        if (root == null) {
            return 0;
        }

        dfs(root);

        return count;
    }

    /**
     * 返回root作为根节点的树中节点值是否都相同
     *
     * @param root
     * @return
     */
    private boolean dfs(TreeNode root) {
        if (root == null) {
            return true;
        }

        //当前节点左子节点作为根节点的树中节点值是否都相同标志位
        boolean leftFlag = dfs(root.left);
        //当前节点右子节点作为根节点的树中节点值是否都相同标志位
        boolean rightFlag = dfs(root.left);
        //root作为根节点向左子树的树中节点值是否都相同标志位
        boolean flag1;
        //root作为根节点向右子树的树中节点值是否都相同标志位
        boolean flag2;

        if (root.left == null) {
            flag1 = true;
        } else {
            if (leftFlag && root.left.val == root.val) {
                flag1 = true;
            } else {
                flag1 = false;
            }
        }

        if (root.right == null) {
            flag2 = true;
        } else {
            if (rightFlag && root.right.val == root.val) {
                flag2 = true;
            } else {
                flag2 = false;
            }
        }

        //更新二叉树数值相同的子树个数
        if (flag1 && flag2) {
            count++;
        }

        //返回当前节点作为根节点的树中节点值是否都相同，用于判断当前节点父节点作为根节点的树中节点值是否都相同
        return flag1 && flag2;
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
