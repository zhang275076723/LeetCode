package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/1/29 08:27
 * @Author zsy
 * @Description 统计二叉树中好节点的数目 类比Problem129、Problem404
 * 给你一棵根为 root 的二叉树，请你返回二叉树中好节点的数目。
 * 「好节点」X 定义为：从根到该节点 X 所经过的节点中，没有任何节点的值大于 X 的值。
 * <p>
 * 输入：root = [3,1,4,3,null,1,5]
 * 输出：4
 * 解释：图中蓝色节点为好节点。
 * 根节点 (3) 永远是个好节点。
 * 节点 4 -> (3,4) 是路径中的最大值。
 * 节点 5 -> (3,4,5) 是路径中的最大值。
 * 节点 3 -> (3,1,3) 是路径中的最大值。
 * <p>
 * 输入：root = [3,3,null,4,2]
 * 输出：3
 * 解释：节点 2 -> (3, 3, 2) 不是好节点，因为 "3" 比它大。
 * <p>
 * 输入：root = [1]
 * 输出：1
 * 解释：根节点是好节点。
 * <p>
 * 二叉树中节点数目范围是 [1, 10^5] 。
 * 每个节点权值的范围是 [-10^4, 10^4] 。
 */
public class Problem1448 {
    public static void main(String[] args) {
        Problem1448 problem1448 = new Problem1448();
        String[] data = {"3", "1", "4", "3", "null", "1", "5"};
        TreeNode root = problem1448.buildTree(data);
        System.out.println(problem1448.goodNodes(root));
        System.out.println(problem1448.goodNodes2(root));
    }

    /**
     * dfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int goodNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }

        //初始化遍历到根节点root之前的最大值为int最小值
        return dfs(root, Integer.MIN_VALUE);
    }

    /**
     * bfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int goodNodes2(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int count = 0;
        Queue<Pos> queue = new LinkedList<>();
        queue.offer(new Pos(root, Integer.MIN_VALUE));

        while (!queue.isEmpty()) {
            Pos pos = queue.poll();

            if (pos.node.val >= pos.max) {
                count++;
            }

            if (pos.node.left != null) {
                queue.offer(new Pos(pos.node.left, Math.max(pos.max, pos.node.val)));
            }
            if (pos.node.right != null) {
                queue.offer(new Pos(pos.node.right, Math.max(pos.max, pos.node.val)));
            }
        }

        return count;
    }

    /**
     * @param node
     * @param max  遍历到当前节点node之前路径节点的最大值
     * @return
     */
    private int dfs(TreeNode node, int max) {
        if (node == null) {
            return 0;
        }

        int count = 0;

        if (node.val >= max) {
            count++;
        }

        count = count + dfs(node.left, Math.max(max, node.val));
        count = count + dfs(node.right, Math.max(max, node.val));

        return count;
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
        //遍历到当前节点node之前路径节点的最大值
        int max;

        Pos(TreeNode node, int max) {
            this.node = node;
            this.max = max;
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
