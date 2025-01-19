package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/5/21 08:35
 * @Author zsy
 * @Description 二叉搜索子树的最大键值和 dfs类比Problem124、Problem298、Problem337、Problem543、Problem687、Problem968、Problem979、Problem1245、Problem1372、Problem2246、Problem2378
 * 给你一棵以 root 为根的 二叉树 ，请你返回 任意 二叉搜索子树的最大键值和。
 * 二叉搜索树的定义如下：
 * 任意节点的左子树中的键值都 小于 此节点的键值。
 * 任意节点的右子树中的键值都 大于 此节点的键值。
 * 任意节点的左子树和右子树都是二叉搜索树。
 * <p>
 * 输入：root = [1,4,3,2,4,2,5,null,null,null,null,null,null,4,6]
 * 输出：20
 * 解释：键值为 3 的子树是和最大的二叉搜索树。
 * <p>
 * 输入：root = [4,3,null,1,2]
 * 输出：2
 * 解释：键值为 2 的单节点子树是和最大的二叉搜索树。
 * <p>
 * 输入：root = [-4,-2,-5]
 * 输出：0
 * 解释：所有节点键值都为负数，和最大的二叉搜索树为空。
 * <p>
 * 输入：root = [2,1,3]
 * 输出：6
 * <p>
 * 输入：root = [5,4,8,3,null,6,3]
 * 输出：7
 * <p>
 * 每棵树有 1 到 40000 个节点。
 * 每个节点的键值在 [-4 * 10^4 , 4 * 10^4] 之间。
 */
public class Problem1373 {
    /**
     * 二叉搜索子树的最大键值和
     * 注意：不能初始化为int最小值，因为空子树也是二叉搜索树，其键值和为0
     */
    private int sum = 0;

    public static void main(String[] args) {
        Problem1373 problem1373 = new Problem1373();
//        String[] data = {"1", "4", "3", "2", "4", "2", "5", "null", "null", "null", "null", "null", "null", "4", "6"};
        String[] data = {"5", "4", "8", "3", "null", "6", "3"};
        TreeNode root = problem1373.buildTree(data);
        System.out.println(problem1373.maxSumBST(root));
    }

    /**
     * dfs
     * 计算当前节点左右子节点作为二叉搜索树根节点的最大键值和数组，更新二叉搜索子树的最大键值和，
     * 返回当前节点作为二叉搜索树根节点对父节点的最大键值和数组，用于计算以当前节点父节点作为二叉搜索树根节点的最大键值和数组
     * arr[0]：当前二叉搜索树的最大键值和，如果当前节点作为根节点的二叉树不是二叉搜索树，则为-1
     * arr[1]：当前二叉搜索树中节点的最大值，如果当前节点作为根节点的二叉树不是二叉搜索树，则为int最大值
     * arr[2]：当前二叉搜索树中节点的最小值，如果当前节点作为根节点的二叉树不是二叉搜索树，则为int最小值
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int maxSumBST(TreeNode root) {
        if (root == null) {
            return 0;
        }

        dfs(root);

        return sum;
    }

    /**
     * 返回当前节点作为二叉搜索树根节点的最大键值和数组
     * arr[0]：当前二叉搜索树的最大键值和，如果当前节点作为根节点的二叉树不是二叉搜索树，则为-1
     * arr[1]：当前二叉搜索树中节点的最大值，如果当前节点作为根节点的二叉树不是二叉搜索树，则为int最大值
     * arr[2]：当前二叉搜索树中节点的最小值，如果当前节点作为根节点的二叉树不是二叉搜索树，则为int最小值
     *
     * @param root
     * @return
     */
    private int[] dfs(TreeNode root) {
        //空子树是特殊的二叉搜索树，赋值键值和为0，树中节点的最大值为int最小值，树中节点的最小值为int最大值
        if (root == null) {
            return new int[]{0, Integer.MIN_VALUE, Integer.MAX_VALUE};
        }

        //左子节点作为二叉搜索树根节点的最大键值和数组
        int[] leftArr = dfs(root.left);
        //右子节点作为二叉搜索树根节点的最大键值和数组
        int[] rightArr = dfs(root.right);

        //根节点的值大于左子树的最大值，并且根节点的值小于右子树的最小值，则是二叉搜索树；
        //否则不是二叉搜索树，赋值键值和为-1，树中节点的最大值为int最大值，树中节点的最小值为int最小值
        if (!(root.val > leftArr[1] && root.val < rightArr[2])) {
            return new int[]{-1, Integer.MAX_VALUE, Integer.MIN_VALUE};
        }

        //更新二叉搜索子树的最大键值和
        sum = Math.max(sum, root.val + leftArr[0] + rightArr[0]);

        //返回当前节点作为二叉搜索树根节点对父节点的最大键值和数组，用于计算以当前节点父节点作为二叉搜索树根节点的最大键值和数组
        return new int[]{root.val + leftArr[0] + rightArr[0],
                Math.max(root.val, rightArr[1]), Math.min(root.val, leftArr[2])};
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
