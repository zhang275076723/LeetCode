package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/9/1 9:19
 * @Author zsy
 * @Description 不同的二叉搜索树 II 类比Problem78、Problem96、Problem98、Problem99、Problem230、Offer33、Offer36
 * 给你一个整数 n ，请你生成并返回所有由 n 个节点组成且节点值从 1 到 n 互不相同的不同 二叉搜索树 。
 * 可以按 任意顺序 返回答案。
 * <p>
 * 输入：n = 3
 * 输出：[[1,null,2,null,3],[1,null,3,2],[2,1,3],[3,1,null,null,2],[3,2,null,1]]
 * <p>
 * 输入：n = 1
 * 输出：[[1]]
 * <p>
 * 1 <= n <= 8
 */
public class Problem95 {
    public static void main(String[] args) {
        Problem95 problem95 = new Problem95();
        List<TreeNode> roots = problem95.generateTrees(3);

        //每种情况进行中序遍历
        for (TreeNode root : roots) {
            List<Integer> list = new ArrayList<>();
            Stack<TreeNode> stack = new Stack<>();
            TreeNode node = root;

            while (!stack.isEmpty() || node != null) {
                while (node != null) {
                    stack.push(node);
                    node = node.left;
                }

                node = stack.pop();
                list.add(node.val);
                node = node.right;
            }

            System.out.println(list);
        }
    }

    /**
     * 回溯+剪枝
     * 时间复杂度O(n*Cn)，空间复杂度O(n*Cn) (Cn为第n个卡特兰数)
     *
     * @param n
     * @return
     */
    public List<TreeNode> generateTrees(int n) {
        if (n == 0) {
            return new ArrayList<>();
        }

        return backtrack(1, n);
    }

    private List<TreeNode> backtrack(int start, int end) {
        //没有节点，null放入集合中返回
        if (start > end) {
            List<TreeNode> list = new ArrayList<>();
            list.add(null);
            return list;
        }

        //只有一个节点，当前节点放入集合中返回
        if (start == end) {
            List<TreeNode> list = new ArrayList<>();
            list.add(new TreeNode(start));
            return list;
        }

        List<TreeNode> list = new ArrayList<>();

        //[start,end]选择一个数作为根节点
        for (int i = start; i <= end; i++) {
            //[start,i-1]作为左子树集合
            List<TreeNode> leftNodes = backtrack(start, i - 1);
            //[i+1,end]作为右子树集合
            List<TreeNode> rightNodes = backtrack(i + 1, end);

            for (TreeNode leftNode : leftNodes) {
                for (TreeNode rightNode : rightNodes) {
                    TreeNode root = new TreeNode(i);
                    root.left = leftNode;
                    root.right = rightNode;
                    list.add(root);
                }
            }
        }

        return list;
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
