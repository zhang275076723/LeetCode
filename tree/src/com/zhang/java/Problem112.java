package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/6/28 16:01
 * @Author zsy
 * @Description 路径总和 类比Problem113、Problem257、Problem437
 * 给你二叉树的根节点 root 和一个表示目标和的整数 targetSum 。
 * 判断该树中是否存在 根节点到叶子节点 的路径，这条路径上所有节点值相加等于目标和 targetSum 。
 * 如果存在，返回 true ；否则，返回 false 。
 * 叶子节点 是指没有子节点的节点。
 * <p>
 * 输入：root = [5,4,8,11,null,13,4,7,2,null,null,null,1], targetSum = 22
 * 输出：true
 * 解释：等于目标和的根节点到叶节点路径如上图所示。
 * <p>
 * 输入：root = [1,2,3], targetSum = 5
 * 输出：false
 * 解释：树中存在两条根节点到叶子节点的路径：
 * (1 --> 2): 和为 3
 * (1 --> 3): 和为 4
 * 不存在 sum = 5 的根节点到叶子节点的路径。
 * <p>
 * 输入：root = [], targetSum = 0
 * 输出：false
 * 解释：由于树是空的，所以不存在根节点到叶子节点的路径。
 * <p>
 * 树中节点的数目在范围 [0, 5000] 内
 * -1000 <= Node.val <= 1000
 * -1000 <= targetSum <= 1000
 */
public class Problem112 {
    private boolean flag = false;

    public static void main(String[] args) {
        Problem112 problem112 = new Problem112();
        String[] data = {"5", "4", "8", "11", "null", "13", "4", "7", "2", "null", "null", "null", "1"};
        TreeNode root = problem112.buildTree(data);
        System.out.println(problem112.hasPathSum(root, 22));
        System.out.println(problem112.hasPathSum2(root, 22));
    }

    /**
     * dfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @param targetSum
     * @return
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }

        dfs(root, targetSum, 0);

        return flag;
    }

    /**
     * bfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @param targetSum
     * @return
     */
    public boolean hasPathSum2(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }

        Queue<Pos> queue = new LinkedList<>();
        queue.offer(new Pos(root, root.val));

        while (!queue.isEmpty()) {
            Pos pos = queue.poll();

            if (pos.node.left == null && pos.node.right == null && pos.pathSum == targetSum) {
                return true;
            }
            if (pos.node.left != null) {
                queue.offer(new Pos(pos.node.left, pos.pathSum + pos.node.left.val));
            }
            if (pos.node.right != null) {
                queue.offer(new Pos(pos.node.right, pos.pathSum + pos.node.right.val));
            }
        }

        return false;
    }

    private void dfs(TreeNode root, int targetSum, int curSum) {
        if (root == null) {
            return;
        }

        //已经找到根节点到叶子节点的路径和为targetSum的路径，则返回
        if (flag) {
            return;
        }

        curSum = curSum + root.val;

        //到达叶节点，且路径和为targetSum
        if (root.left == null && root.right == null && curSum == targetSum) {
            flag = true;
            return;
        }

        dfs(root.left, targetSum, curSum);
        dfs(root.right, targetSum, curSum);
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
    private static class Pos {
        TreeNode node;
        int pathSum;

        Pos(TreeNode node, int pathSum) {
            this.node = node;
            this.pathSum = pathSum;
        }
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
