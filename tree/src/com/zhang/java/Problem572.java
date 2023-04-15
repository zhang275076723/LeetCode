package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/4/9 10:42
 * @Author zsy
 * @Description 另一棵树的子树 类比Problem100、Problem101、Problem1367、Offer26、Offer27、Offer28
 * 给你两棵二叉树 root 和 subRoot 。
 * 检验 root 中是否包含和 subRoot 具有相同结构和节点值的子树。
 * 如果存在，返回 true ；否则，返回 false 。
 * 二叉树 tree 的一棵子树包括 tree 的某个节点和这个节点的所有后代节点。
 * tree 也可以看做它自身的一棵子树。
 * <p>
 * 输入：root = [3,4,5,1,2], subRoot = [4,1,2]
 * 输出：true
 * <p>
 * 输入：root = [3,4,5,1,2,null,null,null,null,0], subRoot = [4,1,2]
 * 输出：false
 * <p>
 * root 树上的节点数量范围是 [1, 2000]
 * subRoot 树上的节点数量范围是 [1, 1000]
 * -10^4 <= root.val <= 10^4
 * -10^4 <= subRoot.val <= 10^4
 */
public class Problem572 {
    public static void main(String[] args) {
        Problem572 problem572 = new Problem572();
        String[] data = {"3", "4", "5", "1", "2", "null", "null", "null", "null", "0"};
        String[] subData = {"4", "1", "2"};
        TreeNode root = problem572.buildTree(data);
        TreeNode subRoot = problem572.buildTree(subData);
        System.out.println(problem572.isSubtree(root, subRoot));
    }

    /**
     * dfs
     * 时间复杂度O(mn)，空间复杂度O(max(m,n)) (m：root树节点个数，n：subRoot树节点个数)
     *
     * @param root
     * @param subRoot
     * @return
     */
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        //空树不是另一棵树的子树
        if (root == null || subRoot == null) {
            return false;
        }

        //判断subRoot是否是root的子树，或者subRoot是否是root左子树的子树，或者subRoot是否是root右子树的子树
        return contains(root, subRoot) || isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
    }

    /**
     * 判断subRoot是否是root的子树
     *
     * @param root
     * @param subRoot
     * @return
     */
    private boolean contains(TreeNode root, TreeNode subRoot) {
        //root为空，subRoot为空，则说明B树是A树的子树
        if (root == null && subRoot == null) {
            return true;
        }

        //root和subRoot只有一个树为为空树，或者root节点值和subRoot节点值不同，则说明B树不是A树的子树
        if (root == null || subRoot == null || root.val != subRoot.val) {
            return false;
        }

        //判断subRoot的左子树是否是root左子树的子树，并且判断subRoot的右子树是否是root右子树的子树
        return contains(root.left, subRoot.left) && contains(root.right, subRoot.right);
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
