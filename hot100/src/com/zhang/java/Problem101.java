package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/5/1 9:58
 * @Author zsy
 * @Description 对称二叉树 类比Problem100、Problem226、Problem572、Problem951、Problem1367、Offer26、Offer27、Offer28 同Offer28
 * 给你一个二叉树的根节点 root ， 检查它是否轴对称。
 * <p>
 * 输入：root = [1,2,2,3,4,4,3]
 * 输出：true
 * <p>
 * 输入：root = [1,2,2,null,3,null,3]
 * 输出：false
 * <p>
 * 树中节点数目在范围 [1, 1000] 内
 * -100 <= Node.val <= 100
 */
public class Problem101 {
    public static void main(String[] args) {
        Problem101 problem101 = new Problem101();
        String[] data = {"1", "2", "2", "3", "4", "4", "3"};
        TreeNode root = problem101.buildTree(data);
        System.out.println(problem101.isSymmetric(root));
        System.out.println(problem101.isSymmetric2(root));
    }

    /**
     * dfs
     * 时间复杂度O(n)，平均空间复杂度O(logn)，最坏空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }

        return dfs(root.left, root.right);
    }

    /**
     * bfs
     * 使用队列，判断每一层是否对称
     * 时间复杂度O(n)，平均空间复杂度O(logn)，最坏空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public boolean isSymmetric2(TreeNode root) {
        if (root == null) {
            return true;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root.left);
        queue.offer(root.right);

        while (!queue.isEmpty()) {
            TreeNode node1 = queue.poll();
            TreeNode node2 = queue.poll();

            //当前节点左右子树都为空，则对称，继续下次循环
            if (node1 == null && node2 == null) {
                continue;
            }

            //当前节点只有一个子树为空或左右节点值不一样，则不对称，返回false
            if (node1 == null || node2 == null || node1.val != node2.val) {
                return false;
            }

            //按照对称的顺序添加子节点
            queue.add(node1.left);
            queue.add(node2.right);
            queue.add(node1.right);
            queue.add(node2.left);
        }

        return true;
    }

    private boolean dfs(TreeNode node1, TreeNode node2) {
        //node1和node2都为空，则是对称二叉树，返回true
        if (node1 == null && node2 == null) {
            return true;
        }

        //node1和node2只有一个不为空，或者节点值不相等，则不是对称二叉树，返回false
        if (node1 == null || node2 == null || node1.val != node2.val) {
            return false;
        }

        //递归判断node1的左子树和node2的右子树是否是对称二叉树，node1的右子树和node2的左子树是否是对称二叉树
        return dfs(node1.left, node2.right) && dfs(node1.right, node2.left);
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
