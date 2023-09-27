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

        return dfs(root, 0, targetSum);
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

            //当前节点为叶节点，判断路径和是否等于targetSum
            if (pos.node.left == null && pos.node.right == null) {
                if (pos.sum == targetSum) {
                    return true;
                }

                continue;
            }

            if (pos.node.left != null) {
                queue.offer(new Pos(pos.node.left, pos.sum + pos.node.left.val));
            }

            if (pos.node.right != null) {
                queue.offer(new Pos(pos.node.right, pos.sum + pos.node.right.val));
            }
        }

        return false;
    }

    private boolean dfs(TreeNode root, int sum, int targetSum) {
        if (root == null) {
            return false;
        }

        sum = sum + root.val;

        //当前节点为叶节点，判断路径和是否等于targetSum
        if (root.left == null && root.right == null) {
            return sum == targetSum;
        }

        //继续往左往右找路径总和为targetSum的路径
        return dfs(root.left, sum, targetSum) || dfs(root.right, sum, targetSum);
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
        int sum;

        Pos(TreeNode node, int sum) {
            this.node = node;
            this.sum = sum;
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
