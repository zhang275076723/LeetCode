package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/3/19 19:44
 * @Author zsy
 * @Description 树的子结构 字节面试题
 * 输入两棵二叉树A和B，判断B是不是A的子结构。(约定空树不是任意一个树的子结构)
 * B是A的子结构，即A中有出现和B相同的结构和节点值。
 * <p>
 * 输入：A = [1,2,3], B = [3,1]
 * 输出：false
 * <p>
 * 输入：A = [3,4,5,1,2], B = [4,1]
 * 输出：true
 * <p>
 * 0 <= 节点个数 <= 10000
 */
public class Offer26 {
    public static void main(String[] args) {
        Offer26 offer26 = new Offer26();
        String[] data1 = {"3", "4", "5", "1", "2"};
        String[] data2 = {"4", "1"};
        TreeNode root1 = offer26.buildTree(data1);
        TreeNode root2 = offer26.buildTree(data2);
        System.out.println(offer26.isSubStructure(root1, root2));
    }

    /**
     * dfs判断B是否是当前根节点的子节点
     * 时间复杂度O(mn)，空间复杂度O(m) (m：A树节点个数，n:B树节点个数)
     *
     * @param A
     * @param B
     * @return
     */
    public boolean isSubStructure(TreeNode A, TreeNode B) {
        //空树不是任意一个树的子结构
        if (A == null || B == null) {
            return false;
        }

        //判断B树是否是A树的子结构，或者B树是否是A树左子树的子结构，或者树是否是A树右子树的子结构
        return contain(A, B) || isSubStructure(A.left, B) || isSubStructure(A.right, B);
    }

    /**
     * 判断B为根节点的树是否是A为根节点的树的子结构
     *
     * @param A
     * @param B
     * @return
     */
    public boolean contain(TreeNode A, TreeNode B) {
        //A树为空，B树为空，则说明B树是A树的子结构
        if (A == null && B == null) {
            return true;
        }

        //A树为空，B树不为空，则说明B树不是A树的子结构
        if (A == null) {
            return false;
        }

        //A树不为空，B树为空，则说明B树是A树的子结构
        if (B == null) {
            return true;
        }

        //A节点值和B节点值不相同，则说明B树不是A树的子结构
        if (A.val != B.val) {
            return false;
        }

        //判断B树的左子树是否是A树的左子树的子结构，并且判断B树的右子树是否是A树的右子树的子结构
        return contain(A.left, B.left) && contain(A.right, B.right);
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

        TreeNode(int x) {
            val = x;
        }
    }
}
