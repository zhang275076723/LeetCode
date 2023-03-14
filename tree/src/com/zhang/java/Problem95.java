package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/9/1 9:19
 * @Author zsy
 * @Description 不同的二叉搜索树 II 二叉搜索树类比Problem96、Problem98、Problem99、Problem230、Offer33、Offer36 分治法类比Problem105、Problem106、Problem108、Problem109、Problem449、Problem889、Problem1008、Offer7、Offer33
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
     * 分治法
     * 确定1-n中哪个节点作为根节点，递归得到左子树集合和右子树集合，分别赋值为当前根节点的左右子树
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

    private List<TreeNode> backtrack(int left, int right) {
        //没有节点，null放入集合中返回
        if (left > right) {
            List<TreeNode> list = new ArrayList<>();
            list.add(null);
            return list;
        }

        //只有一个节点，当前节点放入集合中返回
        if (left == right) {
            List<TreeNode> list = new ArrayList<>();
            list.add(new TreeNode(left));
            return list;
        }

        List<TreeNode> list = new ArrayList<>();

        //[start,end]选择一个数作为根节点
        for (int i = left; i <= right; i++) {
            //[start,i-1]作为左子树集合
            List<TreeNode> leftNodes = backtrack(left, i - 1);
            //[i+1,end]作为右子树集合
            List<TreeNode> rightNodes = backtrack(i + 1, right);

            for (TreeNode leftNode : leftNodes) {
                for (TreeNode rightNode : rightNodes) {
                    //i作为根节点
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
