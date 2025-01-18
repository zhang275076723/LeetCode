package com.zhang.java;

import java.util.*;

/**
 * @Date 2025/3/6 08:03
 * @Author zsy
 * @Description 二叉树中的第 K 大层和 类比Problem199、Problem513、Problem515、Problem637、Problem662、Problem993、Problem1161、Problem1302、Problem2641
 * 给你一棵二叉树的根节点 root 和一个正整数 k 。
 * 树中的 层和 是指 同一层 上节点值的总和。
 * 返回树中第 k 大的层和（不一定不同）。
 * 如果树少于 k 层，则返回 -1 。
 * 注意，如果两个节点与根节点的距离相同，则认为它们在同一层。
 * <p>
 * 输入：root = [5,8,9,2,1,3,7,4,6], k = 2
 * 输出：13
 * 解释：树中每一层的层和分别是：
 * - Level 1: 5
 * - Level 2: 8 + 9 = 17
 * - Level 3: 2 + 1 + 3 + 7 = 13
 * - Level 4: 4 + 6 = 10
 * 第 2 大的层和等于 13 。
 * <p>
 * 输入：root = [1,2,null,3], k = 1
 * 输出：3
 * 解释：最大的层和是 3 。
 * <p>
 * 树中的节点数为 n
 * 2 <= n <= 10^5
 * 1 <= Node.val <= 10^6
 * 1 <= k <= n
 */
public class Problem2583 {
    public static void main(String[] args) {
        Problem2583 problem2583 = new Problem2583();
//        String[] data = {"5", "8", "9", "2", "1", "3", "7", "4", "6"};
//        int k = 2;
        String[] data = { "3", "2", "1"};
        int k = 2;
        TreeNode root = problem2583.buildTree(data);
        System.out.println(problem2583.kthLargestLevelSum(root, k));
        System.out.println(problem2583.kthLargestLevelSum2(root, k));
    }

    /**
     * dfs
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param root
     * @param k
     * @return
     */
    public long kthLargestLevelSum(TreeNode root, int k) {
        if (root == null) {
            return -1;
        }

        //存储每层元素和的集合
        List<Long> list = new ArrayList<>();

        //根节点为第0层
        dfs(root, 0, list);

        //不存在第k大层元素和，返回-1
        if (k > list.size()) {
            return -1;
        }

        //由小到大排序
        list.sort(new Comparator<Long>() {
            @Override
            public int compare(Long a, Long b) {
                return Long.compare(a, b);
            }
        });

        return list.get(list.size() - k);
    }

    /**
     * bfs
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param root
     * @param k
     * @return
     */
    public long kthLargestLevelSum2(TreeNode root, int k) {
        if (root == null) {
            return 0;
        }

        //存储每层元素和的集合
        List<Long> list = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            //当前层元素和
            long sum = 0;

            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                sum = sum + node.val;

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

            list.add(sum);
        }

        //不存在第k大层元素和，返回-1
        if (k > list.size()) {
            return -1;
        }

        //由小到大排序
        list.sort(new Comparator<Long>() {
            @Override
            public int compare(Long a, Long b) {
                return Long.compare(a, b);
            }
        });

        return list.get(list.size() - k);
    }

    private void dfs(TreeNode node, int level, List<Long> list) {
        if (node == null) {
            return;
        }

        if (level == list.size()) {
            list.add((long) node.val);
        } else {
            list.set(level, list.get(level) + node.val);
        }

        dfs(node.left, level + 1, list);
        dfs(node.right, level + 1, list);
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
