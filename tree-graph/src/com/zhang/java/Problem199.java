package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/6/20 16:00
 * @Author zsy
 * @Description 二叉树的右视图 字节面试题 类比Problem513、Problem515、Problem637、Problem662、Problem1302
 * 给定一个二叉树的 根节点 root，想象自己站在它的右侧，按照从顶部到底部的顺序，返回从右侧所能看到的节点值。
 * <p>
 * 输入: [1,2,3,null,5,null,4]
 * 输出: [1,3,4]
 * <p>
 * 输入: [1,null,3]
 * 输出: [1,3]
 * <p>
 * 输入: []
 * 输出: []
 * <p>
 * 二叉树的节点个数的范围是 [0,100]
 * -100 <= Node.val <= 100
 */
public class Problem199 {
    public static void main(String[] args) {
        Problem199 problem199 = new Problem199();
        String[] data = {"1", "2", "3", "null", "5", "null", "4"};
        TreeNode root = problem199.buildTree(data);
        System.out.println(problem199.rightSideView(root));
        System.out.println(problem199.rightSideView2(root));
    }

    /**
     * bfs
     * 每次保存每层的最后一个节点值
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public List<Integer> rightSideView(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<Integer> list = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();

                //如果是左视图，则是当前层中第一个元素，即i==0的情况
                if (i == size - 1) {
                    list.add(node.val);
                }

                if (node.left != null) {
                    queue.offer(node.left);
                }

                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }

        return list;
    }

    /**
     * dfs
     * 逆序前序遍历，先遍历根节点，再遍历右子树，最后遍历左子树，保证每行首次都访问最右边节点
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public List<Integer> rightSideView2(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<Integer> list = new ArrayList<>();

        //根节点为第0层
        dfs(root, list, 0);

        return list;
    }

    /**
     * @param root  当前节点
     * @param list  结果集合
     * @param depth 当前节点的层数(从0层开始)
     */
    private void dfs(TreeNode root, List<Integer> list, int level) {
        if (root == null) {
            return;
        }

        //当前节点的层数等于list大小，则当前节点是当前层的右视图节点，加入list中
        if (level == list.size()) {
            list.add(root.val);
        }

        //右视图(逆序前序遍历)，先遍历右子树，再遍历左子树；如果是左视图(前序遍历)，则先遍历左子树，再遍历右子树
        dfs(root.right, list, level + 1);
        dfs(root.left, list, level + 1);
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
