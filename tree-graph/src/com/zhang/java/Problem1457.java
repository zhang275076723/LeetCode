package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/3/14 08:53
 * @Author zsy
 * @Description 二叉树中的伪回文路径 类比Problem1177、Problem1371、Problem1542、Problem1915、Problem2791 状态压缩类比 回文类比
 * 给你一棵二叉树，每个节点的值为 1 到 9 。
 * 我们称二叉树中的一条路径是 「伪回文」的，当它满足：路径经过的所有节点值的排列中，存在一个回文序列。
 * 请你返回从根到叶子节点的所有路径中 伪回文 路径的数目。
 * <p>
 * 输入：root = [2,3,1,3,1,null,1]
 * 输出：2
 * 解释：上图为给定的二叉树。总共有 3 条从根到叶子的路径：红色路径 [2,3,3] ，绿色路径 [2,1,1] 和路径 [2,3,1] 。
 * 在这些路径中，只有红色和绿色的路径是伪回文路径，因为红色路径 [2,3,3] 存在回文排列 [3,2,3] ，绿色路径 [2,1,1] 存在回文排列 [1,2,1] 。
 * <p>
 * 输入：root = [2,1,1,1,3,null,null,null,null,null,1]
 * 输出：1
 * 解释：上图为给定二叉树。总共有 3 条从根到叶子的路径：绿色路径 [2,1,1] ，路径 [2,1,3,1] 和路径 [2,1] 。
 * 这些路径中只有绿色路径是伪回文路径，因为 [2,1,1] 存在回文排列 [1,2,1] 。
 * <p>
 * 输入：root = [9]
 * 输出：1
 * <p>
 * 给定二叉树的节点数目在范围 [1, 10^5] 内
 * 1 <= Node.val <= 9
 */
public class Problem1457 {
    public static void main(String[] args) {
        Problem1457 problem1457 = new Problem1457();
        String[] data = {"2", "1", "1", "1", "3", "null", "null", "null", "null", "null", "1"};
        TreeNode root = problem1457.buildTree(data);
        System.out.println(problem1457.pseudoPalindromicPaths(root));
        System.out.println(problem1457.pseudoPalindromicPaths2(root));
    }

    /**
     * dfs+模拟
     * 根节点到叶节点的路径中出现次数为奇数的数字个数小于等于1，则当前路径为伪回文路径
     * 时间复杂度O(n)，空间复杂度O(n) (栈的最大深度为O(n))
     *
     * @param root
     * @return
     */
    public int pseudoPalindromicPaths(TreeNode root) {
        if (root == null) {
            return 0;
        }

        //当前路径中节点数字出现的次数数组，数字只包含1-9
        int[] countArr = new int[10];

        return dfs(root, countArr);
    }

    /**
     * dfs+二进制状态压缩
     * 根节点到叶节点的路径中出现次数为奇数的数字个数小于等于1，则当前路径为伪回文路径
     * 一共1-9，共9个数字，使用int替代1-9的统计数组，数字1作为二进制表示的从右往左第0位
     * 时间复杂度O(n)，空间复杂度O(n) (栈的最大深度为O(n))
     * <p>
     * 例如：根节点到当前叶节点的路径path=10010，则表示数字2、5出现出现奇数次，数字1、3、4出现偶数次(因为数字从1开始)
     *
     * @param root
     * @return
     */
    public int pseudoPalindromicPaths2(TreeNode root) {
        if (root == null) {
            return 0;
        }

        //path：根节点到当前节点路径中数字出现的奇偶次数二进制表示的数
        return dfs(root, 0);
    }

    private int dfs(TreeNode node, int[] countArr) {
        if (node == null) {
            return 0;
        }

        countArr[node.val]++;

        if (node.left == null && node.right == null) {
            //当前路径中出现次数为奇数的数字个数
            int oddCount = 0;

            //统计当前路径中出现次数为奇数的数字个数
            for (int i = 1; i <= 9; i++) {
                if (countArr[i] % 2 == 1) {
                    oddCount++;

                    //当前路径中出现次数为奇数的数字个数大于1，则当前路径重新排列后无法构成回文，返回0
                    if (oddCount > 1) {
                        countArr[node.val]--;
                        return 0;
                    }
                }
            }

            countArr[node.val]--;
            //当前路径重新排列后能够构成回文，返回1
            return 1;
        }

        //左子树路径中重新排列后构成回文的个数
        int leftCount = dfs(node.left, countArr);
        //右子树路径中重新排列后构成回文的个数
        int rightCount = dfs(node.right, countArr);

        countArr[node.val]--;
        return leftCount + rightCount;
    }

    private int dfs(TreeNode node, int path) {
        if (node == null) {
            return 0;
        }

        //(1<<(node.val-1))：当前数字存储在二进制表示的从右往左第node.val-1位(因为数字从1开始)
        //注意：异或操作可以立刻得到当前数字在当前位的奇偶次数
        path = path ^ (1 << (node.val - 1));

        if (node.left == null && node.right == null) {
            //当前路径中出现次数为奇数的数字个数
            int oddCount = 0;
            int value = path;

            //统计path二进制表示的数中出现次数为奇数的数字个数
            while (value != 0) {
                oddCount = oddCount + (value & 1);
                value = value >>> 1;

                //当前路径中出现次数为奇数的数字个数大于1，则当前路径重新排列后无法构成回文，返回0
                if (oddCount > 1) {
                    return 0;
                }
            }

            //当前路径重新排列后能够构成回文，返回1
            return 1;
        }

        //左子树路径中重新排列后构成回文的个数
        int leftCount = dfs(node.left, path);
        //右子树路径中重新排列后构成回文的个数
        int rightCount = dfs(node.right, path);

        return leftCount + rightCount;
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
