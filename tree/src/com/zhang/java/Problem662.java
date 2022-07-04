package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/7/4 8:12
 * @Author zsy
 * @Description 二叉树最大宽度 字节面试题
 * 给定一个二叉树，编写一个函数来获取这个树的最大宽度。
 * 树的宽度是所有层中的最大宽度。这个二叉树与满二叉树（full binary tree）结构相同，但一些节点为空。
 * 每一层的宽度被定义为两个端点（该层最左和最右的非空节点，两端点间的null节点也计入长度）之间的长度。
 * 输入:
 * <          1
 * <         / \
 * <        3   2
 * <       / \   \
 * <      5   3   9
 * 输出: 4
 * 解释: 最大值出现在树的第 3 层，宽度为 4 (5,3,null,9)。
 * <p>
 * 输入:
 * <         1
 * <        /
 * <       3
 * <      / \
 * <     5   3
 * 输出: 2
 * 解释: 最大值出现在树的第 3 层，宽度为 2 (5,3)。
 * <p>
 * 输入:
 * <         1
 * <        / \
 * <       3   2
 * <      /
 * <     5
 * 输出: 2
 * 解释: 最大值出现在树的第 2 层，宽度为 2 (3,2)。
 * <p>
 * 输入:
 * <         1
 * <        / \
 * <       3   2
 * <      /     \
 * <     5       9
 * <    /         \
 * <   6           7
 * 输出: 8
 * 解释: 最大值出现在树的第 4 层，宽度为 8 (6,null,null,null,null,null,null,7)。
 * <p>
 * 注意: 答案在32位有符号整数的表示范围内。
 */
public class Problem662 {
    /**
     * dfs的最大宽度
     */
    private int maxWidth = 0;

    public static void main(String[] args) {
        Problem662 problem662 = new Problem662();
        String[] data = {"1", "3", "2", "5", "null", "null", "9", "6", "null", "null", "null", "null", "null", "null", "7"};
        TreeNode root = problem662.buildTree(data);
        System.out.println(problem662.widthOfBinaryTree(root));
        System.out.println(problem662.widthOfBinaryTree2(root));
    }

    /**
     * bfs，层序遍历
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int widthOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        //存放节点在树中的索引下标
        List<Integer> list = new ArrayList<>();
        queue.offer(root);
        list.add(0);

        //当前只有一个根节点时，最大宽度为1
        int maxWidth = 1;

        while (!queue.isEmpty()) {
            //当前层的节点数量
            int size = queue.size();

            //遍历当前层
            while (size > 0) {
                TreeNode node = queue.poll();
                int index = list.remove(0);

                if (node.left != null) {
                    queue.offer(node.left);
                    list.add(2 * index + 1);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                    list.add(2 * index + 2);
                }

                size--;
            }

            //更新最大宽度
            if (list.size() >= 2) {
                maxWidth = Math.max(maxWidth, list.get(list.size() - 1) - list.get(0) + 1);
            }
        }

        return maxWidth;
    }

    /**
     * dfs，记录每层的开始索引，将每层节点分别减去每层的开始索引，得到最大宽度
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int widthOfBinaryTree2(TreeNode root) {
        if (root == null) {
            return 0;
        }

        dfs(root, new ArrayList<>(), 1, 0);

        return maxWidth;
    }

    /**
     * @param root  当前节点
     * @param list  每层最左边节点的索引下标
     * @param level 当前层数
     * @param index 当前节点的索引下标
     */
    private void dfs(TreeNode root, List<Integer> list, int level, int index) {
        if (root == null) {
            return;
        }

        //当前层数大于每层最左边节点的索引下标集合，说明当前节点是该层第一个节点
        if (level > list.size()) {
            list.add(index);
        }

        //对于每个节点，更新最大宽度
        maxWidth = Math.max(maxWidth, index - list.get(level - 1) + 1);

        dfs(root.left, list, level + 1, 2 * index + 1);
        dfs(root.right, list, level + 1, 2 * index + 2);
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
                if (node != null) {
                    if (!"null".equals(leftValue)) {
                        TreeNode leftNode = new TreeNode(Integer.parseInt(leftValue));
                        node.left = leftNode;
                        queue.offer(leftNode);
                    } else {
                        queue.offer(null);
                    }
                }
            }
            if (!list.isEmpty()) {
                String rightValue = list.remove(0);
                if (node != null) {
                    if (!"null".equals(rightValue)) {
                        TreeNode rightNode = new TreeNode(Integer.parseInt(rightValue));
                        node.right = rightNode;
                        queue.offer(rightNode);
                    } else {
                        queue.offer(null);
                    }
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
