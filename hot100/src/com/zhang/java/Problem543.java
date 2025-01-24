package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/6/12 9:43
 * @Author zsy
 * @Description 二叉树的直径 类比Problem1245、Problem2246 dfs类比Problem124、Problem250、Problem298、Problem337、Problem687、Problem968、Problem979、Problem1245、Problem1372、Problem1373、Problem2246、Problem2378、Problem2925、Problem2973
 * 给你一棵二叉树的根节点，返回该树的 直径 。
 * 二叉树的 直径 是指树中任意两个节点之间最长路径的 长度 。
 * 这条路径可能经过也可能不经过根节点 root 。
 * 两节点之间路径的 长度 由它们之间边数表示。
 * <p>
 * 输入：root = [1,2,3,4,5]
 * 输出：3
 * 解释：3 ，取路径 [4,2,1,3] 或 [5,2,1,3] 的长度。
 * <p>
 * 输入：root = [1,2]
 * 输出：1
 * <p>
 * 树中节点数目在范围 [1, 10^4] 内
 * -100 <= Node.val <= 100
 */
public class Problem543 {
    /**
     * 二叉树的直径
     */
    private int diameter = 0;

    public static void main(String[] args) {
        Problem543 problem543 = new Problem543();
        String[] data = {"1", "2", "3", "4", "5"};
        TreeNode root = problem543.buildTree(data);
        System.out.println(problem543.diameterOfBinaryTree(root));
    }

    /**
     * dfs
     * 计算当前节点左右子节点作为根节点的最大单侧路径长度，更新二叉树的直径，
     * 返回当前节点对父节点的最大单侧路径长度，用于计算当前节点父节点作为根节点的最大单侧路径长度
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }

        dfs(root);

        return diameter;
    }

    /**
     * 返回root作为根节点的最大单侧路径长度
     *
     * @param root
     * @return
     */
    private int dfs(TreeNode root) {
        //路径长度为节点的边数，则空节点返回-1
        if (root == null) {
            return -1;
        }

        //当前节点左子节点作为根节点的最大单侧路径长度
        int leftMax = dfs(root.left);
        //当前节点右子节点作为根节点的最大单侧路径长度
        int rightMax = dfs(root.right);
        //root作为根节点向左子树的最大单侧路径长度
        int max1 = leftMax + 1;
        //root作为根节点向右子树的最大单侧路径长度
        int max2 = rightMax + 1;

        //更新二叉树的直径
        diameter = Math.max(diameter, max1 + max2);

        //返回当前节点对父节点的最大单侧路径长度，用于计算当前节点父节点作为根节点的最大单侧路径长度
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
