package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/4/2 16:28
 * @Author zsy
 * @Description 给定一棵二叉搜索树，请找出其中第 k 大的节点的值。
 * <p>
 * 输入: root = [3,1,4,null,2], k = 1
 * 输出: 4
 * <p>
 * 输入: root = [5,3,6,2,4,null,null,1], k = 3
 * 输出: 4
 */
public class Offer54 {
    private int count;
    private int maxKValue;

    public static void main(String[] args) {
        Offer54 offer54 = new Offer54();
        String[] data = {"5", "3", "6", "2", "4", "null", "null", "1"};
        TreeNode root = offer54.buildTree(data);
        System.out.println(offer54.kthLargest(root, 3));
    }

    /**
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @param k
     * @return
     */
    public int kthLargest(TreeNode root, int k) {
        order(root, k);
        return maxKValue;
    }

    /**
     * 先遍历右子树，再遍历根节点，最后遍历左子树
     */
    public void order(TreeNode root, int k) {
        if (root != null) {
            order(root.right, k);
            count++;
            if (count == k) {
                maxKValue = root.val;
                return;
            }
            if (count > k) {
                return;
            }
            order(root.left, k);
        }
    }

    /**
     * 层次遍历建树
     *
     * @param data
     * @return
     */
    public TreeNode buildTree(String[] data) {
        List<String> list = new ArrayList<>(Arrays.asList(data));
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode root = new TreeNode(Integer.parseInt(list.remove(0)));
        queue.add(root);

        while (list.size() >= 2) {
            TreeNode node = queue.remove();
            String leftValue = list.remove(0);
            String rightValue = list.remove(0);
            if (!"null".equals(leftValue)) {
                TreeNode leftNode = new TreeNode(Integer.parseInt(leftValue));
                node.left = leftNode;
                queue.add(leftNode);
            }
            if (!"null".equals(rightValue)) {
                TreeNode rightNode = new TreeNode(Integer.parseInt(rightValue));
                node.right = rightNode;
                queue.add(rightNode);
            }
        }

        //list集合只剩一个元素的处理
        if (list.size() == 1 && !"null".equals(list.get(0))) {
            TreeNode node = queue.remove();
            String leftValue = list.remove(0);
            TreeNode leftNode = new TreeNode(Integer.parseInt(leftValue));
            node.left = leftNode;
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
