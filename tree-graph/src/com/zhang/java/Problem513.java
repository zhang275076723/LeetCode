package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/4/15 08:25
 * @Author zsy
 * @Description 找树左下角的值 类比Problem404 类比Problem199、Problem515、Problem637、Problem662、Problem1161、Problem1302
 * 给定一个二叉树的 根节点 root，请找出该二叉树的 最底层 最左边 节点的值。
 * 假设二叉树中至少有一个节点。
 * <p>
 * 输入: root = [2,1,3]
 * 输出: 1
 * <p>
 * 输入: [1,2,3,4,null,5,6,null,null,7]
 * 输出: 7
 * <p>
 * 二叉树的节点个数的范围是 [1,10^4]
 * -2^31 <= Node.val <= 2^31 - 1
 */
public class Problem513 {
    /**
     * dfs树最左下叶节点的值
     */
    private int mostLeftValue;

    /**
     * dfs树的最大层数，初始化为-1，表示空树
     */
    private int maxLevel = -1;

    public static void main(String[] args) {
        Problem513 problem513 = new Problem513();
        String[] data = {"1", "2", "3", "4", "null", "5", "6", "null", "null", "7"};
        TreeNode root = problem513.buildTree(data);
        System.out.println(problem513.findBottomLeftValue(root));
        System.out.println(problem513.findBottomLeftValue2(root));
    }

    /**
     * bfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int findBottomLeftValue(TreeNode root) {
        if (root == null) {
            return 0;
        }

        //最左下叶节点的值，初始化为根节点的值
        int mostLeftValue = root.val;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            //当前层节点的个数
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();

                //每次保存当前层中第一个节点，则bfs遍历完之后就能得到最左下叶节点的值
                if (i == 0) {
                    mostLeftValue = node.val;
                }

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }

        return mostLeftValue;
    }

    /**
     * dfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int findBottomLeftValue2(TreeNode root) {
        if (root == null) {
            return 0;
        }

        //最左下叶节点的值，初始化为根节点的值
        mostLeftValue = root.val;

        //根节点为第0层
        dfs(root, 0);

        return mostLeftValue;
    }

    /**
     * @param root
     * @param level 当前层数(从0层开始)
     */
    private void dfs(TreeNode root, int level) {
        if (root == null) {
            return;
        }

        //当前遍历到层数大于最大层数，则访问到新的一层，更新最左节点值和最大层数
        if (maxLevel < level) {
            mostLeftValue = root.val;
            maxLevel = level;
        }

        //先遍历左子树再遍历右子树，保证当前层最先访问到最左边节点
        dfs(root.left, level + 1);
        dfs(root.right, level + 1);
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
