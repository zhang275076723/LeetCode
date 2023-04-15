package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/4/15 08:41
 * @Author zsy
 * @Description 二叉搜索树的最小绝对差 类比Problem98、Problem99、Problem501、Problem538、Offer36
 * 给你一个二叉搜索树的根节点 root ，返回 树中任意两不同节点值之间的最小差值 。
 * 差值是一个正数，其数值等于两值之差的绝对值。
 * <p>
 * 输入：root = [4,2,6,1,3]
 * 输出：1
 * <p>
 * 输入：root = [1,0,48,null,null,12,49]
 * 输出：1
 * <p>
 * 树中节点的数目范围是 [2, 10^4]
 * 0 <= Node.val <= 10^5
 */
public class Problem530 {
    /**
     * 递归中序遍历中任意两不同节点值之间的最小差值
     */
    private int minDiff = Integer.MAX_VALUE;

    /**
     * 递归中序遍历中当前节点的前驱节点
     */
    private TreeNode pre = null;

    public static void main(String[] args) {
        Problem530 problem530 = new Problem530();
        String[] data = {"1", "0", "48", "null", "null", "12", "49"};
        TreeNode root = problem530.buildTree(data);
        System.out.println(problem530.getMinimumDifference(root));
        System.out.println(problem530.getMinimumDifference2(root));
    }

    /**
     * 非递归中序遍历
     * 二叉搜索树的中序遍历有序，按照中序遍历的顺序，计算相邻两个节点的最小差值
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int getMinimumDifference(TreeNode root) {
        if (root == null) {
            return 0;
        }

        //任意两不同节点值之间的最小差值
        int minDiff = Integer.MAX_VALUE;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        //中序遍历过程中当前节点的前驱节点
        TreeNode pre = null;

        while (!stack.isEmpty() || node != null) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            node = stack.pop();
            //前驱节点pre不为空，更新minDiff
            if (pre != null) {
                minDiff = Math.min(minDiff, node.val - pre.val);
            }
            //更新前驱节点pre
            pre = node;
            node = node.right;
        }

        return minDiff;
    }

    /**
     * 递归中序遍历
     * 二叉搜索树的中序遍历有序，按照中序遍历的顺序，计算相邻两个节点的最小差值
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int getMinimumDifference2(TreeNode root) {
        if (root == null) {
            return 0;
        }

        inorder(root);

        return minDiff;
    }

    private void inorder(TreeNode root) {
        if (root == null) {
            return;
        }

        inorder(root.left);

        //前驱节点pre不为空，更新minDiff
        if (pre != null) {
            minDiff = Math.min(minDiff, root.val - pre.val);
        }

        //更新前驱节点pre
        pre = root;

        inorder(root.right);
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
