package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/3/20 10:45
 * @Author zsy
 * @Description 二叉树的镜像 类比Problem100、Problem101、Problem572、Offer26、Offer28、Offer55、Offer55_2 同Problem226
 * 请完成一个函数，输入一个二叉树，该函数输出它的镜像
 * <p>
 * 输入：root = [4,2,7,1,3,6,9]
 * 输出：[4,7,2,9,6,3,1]
 * <p>
 * 0 <= 节点个数 <= 1000
 */
public class Offer27 {
    public static void main(String[] args) {
        Offer27 offer27 = new Offer27();
        String[] data = {"4", "2", "7", "1", "3", "6", "9"};
        TreeNode root = offer27.buildTree(data);
        root = offer27.mirrorTree(root);
//      root = offer27.mirrorTree2(root);
//        root = offer27.mirrorTree3(root);
        offer27.traversal(root);
    }


    /**
     * dfs递归
     * dfs遍历所有节点，交换当前节点的左右子节点
     * 时间复杂度O(n)，平均空间复杂度O(logn)，最差空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public TreeNode mirrorTree(TreeNode root) {
        if (root == null) {
            return null;
        }

        TreeNode tempNode = root.left;
        root.left = root.right;
        root.right = tempNode;

        mirrorTree(root.left);
        mirrorTree(root.right);

        return root;
    }

    /**
     * dfs非递归
     * 时间复杂度O(n)，平均空间复杂度O(logn)，最差空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public TreeNode mirrorTree2(TreeNode root) {
        if (root == null) {
            return null;
        }

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.empty()) {
            TreeNode node = stack.pop();

            TreeNode tempNode = node.left;
            node.left = node.right;
            node.right = tempNode;

            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }

        return root;
    }

    /**
     * dfs，层序遍历，自上而下交换
     * 时间复杂度O(n)，平均空间复杂度O(logn)，最差空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public TreeNode mirrorTree3(TreeNode root) {
        if (root == null) {
            return null;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            TreeNode tempNode = node.left;
            node.left = node.right;
            node.right = tempNode;

            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }

        return root;
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

    public void traversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.remove();
            list.add(node.val);
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }

        System.out.println(list);
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
