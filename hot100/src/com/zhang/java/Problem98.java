package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/4/30 11:39
 * @Author zsy
 * @Description 验证二叉搜索树
 * 给你一个二叉树的根节点 root ，判断其是否是一个有效的二叉搜索树。
 * 有效 二叉搜索树定义如下：
 * 节点的左子树只包含 小于 当前节点的数。
 * 节点的右子树只包含 大于 当前节点的数。
 * 所有左子树和右子树自身必须也是二叉搜索树。
 * <p>
 * 输入：root = [2,1,3]
 * 输出：true
 * <p>
 * 输入：root = [5,1,4,null,null,3,6]
 * 输出：false
 * 解释：根节点的值是 5 ，但是右子节点的值是 4 。
 * <p>
 * 树中节点数目范围在[1, 10^4] 内
 * -2^31 <= Node.val <= 2^31 - 1
 */
public class Problem98 {
    //中序遍历中当前节点的上一个节点值，使用long是因为节点值的范围在int，
    //且能取到最小的int，所以要使用long取到比最小的int更小的值作为初始值
    private long preNodeValue = Long.MIN_VALUE;

    public static void main(String[] args) {
        Problem98 problem98 = new Problem98();
//        String[] data = {"5", "1", "4", "null", "null", "3", "6"};
        String[] data = {"5", "4", "6", "null", "null", "3", "7"};
        TreeNode root = problem98.buildTree(data);

        System.out.println(problem98.isValidBST(root));
        System.out.println(problem98.isValidBST2(root));
    }

    /**
     * 递归中序遍历，时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }

        return inorder(root);
    }

    /**
     * 非递归中序遍历，时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public boolean isValidBST2(TreeNode root) {
        if (root == null) {
            return true;
        }

        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        //中序遍历中当前节点的上一个节点值，使用long是因为节点值的范围在int，
        //且能取到最小的int，所以要使用long取到比最小的int更小的值作为初始值
        long preNodeValue = Long.MIN_VALUE;

        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            node = stack.pop();
            //不满足二叉搜索树要求，直接返回false
            if (node.val <= preNodeValue) {
                return false;
            }
            preNodeValue = node.val;
            node = node.right;
        }

        return true;
    }

    private boolean inorder(TreeNode root) {
        if (root == null) {
            return true;
        }

        //遍历左子树，左子树节点的值都应小于当前节点的值
        boolean leftFlag = inorder(root.left);
        if (!leftFlag) {
            return false;
        }

        //当前节点的值应大于当前节点之前节点的值
        if (root.val <= preNodeValue) {
            return false;
        }
        preNodeValue = root.val;

        //遍历右子树，右子树节点的值都应大于当前节点的值
        boolean rightFlag = inorder(root.right);

        return rightFlag;
    }

    /**
     * 建树
     *
     * @param data
     * @return
     */
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
                String leftNodeValue = list.remove(0);
                if (!"null".equals(leftNodeValue)) {
                    TreeNode leftNode = new TreeNode(Integer.parseInt(leftNodeValue));
                    node.left = leftNode;
                    queue.offer(leftNode);
                }
            }
            if (!list.isEmpty()) {
                String rightNodeValue = list.remove(0);
                if (!"null".equals(rightNodeValue)) {
                    TreeNode rightNode = new TreeNode(Integer.parseInt(rightNodeValue));
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
