package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/7/4 8:12
 * @Author zsy
 * @Description 二叉树最大宽度 字节面试题
 * 给定一个二叉树，编写一个函数来获取这个树的最大宽度。
 * 树的宽度是所有层中的最大宽度。这个二叉树与满二叉树（full binary tree）结构相同，但一些节点为空。
 * 每一层的宽度被定义为两个端点（该层最左和最右的非空节点，两端点间的null节点也计入长度）之间的长度。
 * 题目数据保证答案将会在  32 位 带符号整数范围内。
 * <p>
 * 输入：root = [1,3,2,5,3,null,9]
 * <          1
 * <         / \
 * <        3   2
 * <       / \   \
 * <      5   3   9
 * 输出: 4
 * 解释: 最大值出现在树的第 3 层，宽度为 4 (5,3,null,9)。
 * <p>
 * 输入：root = [1,3,2,5,null,null,9,6,null,7]
 * <         1
 * <        / \
 * <       3   2
 * <      /     \
 * <     5       9
 * <    /       /
 * <   6       7
 * 输出：7
 * 解释：最大宽度出现在树的第 4 层，宽度为 7 (6,null,null,null,null,null,7) 。
 * <p>
 * 输入：root = [1,3,2,5]
 * <         1
 * <        / \
 * <       3   2
 * <      /
 * <     5
 * 输出：2
 * 解释：最大宽度出现在树的第 2 层，宽度为 2 (3,2) 。
 * <p>
 * 树中节点的数目范围是 [1, 3000]
 * -100 <= Node.val <= 100
 */
public class Problem662 {
    /**
     * dfs的最大宽度
     */
    private int maxWidth = 0;

    public static void main(String[] args) {
        Problem662 problem662 = new Problem662();
        String[] data = {"1", "3", "2", "5", "null", "null", "9", "6", "null", "7"};
        TreeNode root = problem662.buildTree(data);
        System.out.println(problem662.widthOfBinaryTree(root));
        System.out.println(problem662.widthOfBinaryTree2(root));
    }

    /**
     * bfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int widthOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Queue<Pos> queue = new LinkedList<>();
        queue.offer(new Pos(root, 0));
        //当前只有一个根节点时，最大宽度为1
        int maxWidth = 1;

        while (!queue.isEmpty()) {
            //当前层的节点个数
            int size = queue.size();
            //当前层中第一个节点的下标索引
            int left = 0;
            //当前层中最后一个节点的下标索引
            int right = 0;

            //遍历当前层中节点
            for (int i = 0; i < size; i++) {
                Pos pos = queue.poll();

                //当前层中第一个节点
                if (i == 0) {
                    left = pos.index;
                }
                //当前层中最后一个节点
                if (i == size - 1) {
                    right = pos.index;
                }

                if (pos.node.left != null) {
                    queue.offer(new Pos(pos.node.left, pos.index * 2 + 1));
                }
                if (pos.node.right != null) {
                    queue.offer(new Pos(pos.node.right, pos.index * 2 + 2));
                }
            }

            maxWidth = Math.max(maxWidth, right - left + 1);
        }

        return maxWidth;
    }

    /**
     * dfs
     * 每层中第一个访问到的节点即为当前层最左边节点，记录每层最左边节点的下标索引，
     * 计算每层中节点和最左边节点的距离，得到最大宽度
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int widthOfBinaryTree2(TreeNode root) {
        if (root == null) {
            return 0;
        }

        //根节点为第0层，下标索引为0
        dfs(root, new ArrayList<>(), 0, 0);

        return maxWidth;
    }

    /**
     * @param root  当前节点
     * @param list  存放每层最左边节点下标索引的list集合，list.get(2)：第二层最左边节点的下标索引(从0层开始)
     * @param level 当前层数(从0层开始)
     * @param index 当前节点的下标索引(从0开始)
     */
    private void dfs(TreeNode root, List<Integer> list, int level, int index) {
        if (root == null) {
            return;
        }

        //每层中第一个访问到的节点即为当前层最左边节点，将每层最左边节点的下标索引加入list中
        if (level == list.size()) {
            list.add(index);
        }

        //更新最大宽度
        maxWidth = Math.max(maxWidth, index - list.get(level) + 1);

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
        //当前节点在树中的索引下标(索引从0开始)
        int index;

        Pos(TreeNode node, int index) {
            this.node = node;
            this.index = index;
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
