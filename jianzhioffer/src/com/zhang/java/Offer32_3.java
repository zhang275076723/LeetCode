package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/3/21 17:34
 * @Author zsy
 * @Description 从上到下打印二叉树 III 类比Problem103、Offer32、Offer32_2
 * 请实现一个函数按照之字形顺序打印二叉树，
 * 即第一行按照从左到右的顺序打印，第二层按照从右到左的顺序打印，第三行再按照从左到右的顺序打印，其他行以此类推。
 * <p>
 * 给定二叉树: [3, 9, 20, null, null, 15, 7]
 * 返回其层次遍历结果：[[3], [20,9], [15,7]]
 */
public class Offer32_3 {
    public static void main(String[] args) {
        Offer32_3 offer32_3 = new Offer32_3();
        TreeNode node1 = new TreeNode(3);
        TreeNode node2 = new TreeNode(9);
        TreeNode node3 = new TreeNode(20);
        TreeNode node4 = new TreeNode(15);
        TreeNode node5 = new TreeNode(7);
        node1.left = node2;
        node1.right = node3;
        node3.left = node4;
        node3.right = node5;
        System.out.println(offer32_3.levelOrder(node1));
        System.out.println(offer32_3.levelOrder2(node1));
        System.out.println(offer32_3.levelOrder3(node1));
    }

    /**
     * 使用一个队列 + 记录队列长度的变量 + LinkedList
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<List<Integer>> result = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            LinkedList<Integer> list = new LinkedList<>();
            int size = queue.size();

            while (size > 0) {
                TreeNode node = queue.remove();
                size--;

                //判断每行的插入顺序
                if (result.size() % 2 == 0) {
                    list.addLast(node.val);
                } else {
                    list.addFirst(node.val);
                }

                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }

            result.add(list);
        }

        return result;
    }

    /**
     * 两个队列 + LinkedList
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder2(TreeNode root) {
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

    /**
     * 递归
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder3(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<List<Integer>> result = new ArrayList<>();
        recursion(root, 0, result);
        return result;
    }

    /**
     * @param root   当前根节点
     * @param level  树的层数
     * @param result 结果集合
     */
    public void recursion(TreeNode root, int level, List<List<Integer>> result) {
        if (root == null) {
            return;
        }

        if (result.size() <= level) {
            result.add(new ArrayList<>());
        }

        List<Integer> list = result.get(level);
        //如果是偶数层，则尾插
        if (level % 2 == 0) {
            list.add(root.val);
        } else {
            //如果是奇数层，则头插
            list.add(0, root.val);
        }
        recursion(root.left, level + 1, result);
        recursion(root.right, level + 1, result);
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
