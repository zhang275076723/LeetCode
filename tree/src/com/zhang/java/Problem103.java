package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/3/15 11:58
 * @Author zsy
 * @Description 二叉树的锯齿形层序遍历 类比Problem102、Offer32_3
 * 给你二叉树的根节点 root ，返回其节点值的锯齿形层序遍历 。
 * （即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。
 * <p>
 * 输入：root = [3,9,20,null,null,15,7]
 * 输出：[[3],[20,9],[15,7]]
 * <p>
 * 输入：root = [1]
 * 输出：[[1]]
 * <p>
 * 输入：root = []
 * 输出：[]
 */
public class Problem103 {
    public static void main(String[] args) {
        Problem103 problem103 = new Problem103();
        TreeNode node1 = new TreeNode(3);
        TreeNode node2 = new TreeNode(9);
        TreeNode node3 = new TreeNode(20);
        TreeNode node4 = new TreeNode(15);
        TreeNode node5 = new TreeNode(7);
        node1.left = node2;
        node1.right = node3;
        node3.left = node4;
        node3.right = node5;
        List<List<Integer>> list = problem103.zigzagLevelOrder(node1);
        System.out.println(list);
        List<List<Integer>> list2 = problem103.zigzagLevelOrder2(node1);
        System.out.println(list2);
    }

    /**
     * 使用一个队列存放元素 + 反转标志
     *
     * @param root
     * @return
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<List<Integer>> result = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        //用于每行元素的正序或逆序
        boolean reverse = false;
        queue.add(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> list = new ArrayList<>();
            while (size > 0) {
                TreeNode node = queue.remove();
                size--;
                list.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            if (reverse) {
                Collections.reverse(list);
            }
            reverse = !reverse;
            result.add(list);
        }
        return result;
    }

    /**
     * 使用两个队列存放元素 + LinkedList
     *
     * @param root
     * @return
     */
    public List<List<Integer>> zigzagLevelOrder2(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<List<Integer>> result = new ArrayList<>();
        //从左到右
        Queue<TreeNode> queue1 = new LinkedList<>();
        //从右到左
        Queue<TreeNode> queue2 = new LinkedList<>();
        queue1.add(root);

        while (!queue1.isEmpty() || !queue2.isEmpty()) {
            LinkedList<Integer> list = new LinkedList<>();
            if (!queue1.isEmpty()) {
                while (!queue1.isEmpty()) {
                    TreeNode node = queue1.remove();
                    //queue1从左到右
                    list.addLast(node.val);
                    if (node.left != null) {
                        queue2.add(node.left);
                    }
                    if (node.right != null) {
                        queue2.add(node.right);
                    }
                }
            } else {
                while (!queue2.isEmpty()) {
                    TreeNode node = queue2.remove();
                    //queue2从右到左
                    list.addFirst(node.val);
                    if (node.left != null) {
                        queue1.add(node.left);
                    }
                    if (node.right != null) {
                        queue1.add(node.right);
                    }
                }
            }

            result.add(list);
        }
        return result;
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

