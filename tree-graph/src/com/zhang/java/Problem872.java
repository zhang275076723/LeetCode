package com.zhang.java;

import java.util.*;

/**
 * @Date 2025/2/26 08:42
 * @Author zsy
 * @Description 叶子相似的树
 * 请考虑一棵二叉树上所有的叶子，这些叶子的值按从左到右的顺序排列形成一个 叶值序列 。
 * 举个例子，如上图所示，给定一棵叶值序列为 (6, 7, 4, 9, 8) 的树。
 * 如果有两棵二叉树的叶值序列是相同，那么我们就认为它们是 叶相似 的。
 * 如果给定的两个根结点分别为 root1 和 root2 的树是叶相似的，则返回 true；否则返回 false 。
 * <p>
 * 输入：root1 = [3,5,1,6,2,9,8,null,null,7,4], root2 = [3,5,1,6,7,4,2,null,null,null,null,null,null,9,8]
 * 输出：true
 * <p>
 * 输入：root1 = [1,2,3], root2 = [1,3,2]
 * 输出：false
 * <p>
 * 给定的两棵树结点数在 [1, 200] 范围内
 * 给定的两棵树上的值在 [0, 200] 范围内
 */
public class Problem872 {
    public static void main(String[] args) {
        Problem872 problem872 = new Problem872();
        String[] data1 = {"3", "5", "1", "6", "2", "9", "8", "null", "null", "7", "4"};
        String[] data2 = {"3", "5", "1", "6", "7", "4", "2", "null", "null", "null", "null", "null", "null", "9", "8"};
        TreeNode root1 = problem872.buildTree(data1);
        TreeNode root2 = problem872.buildTree(data2);
        System.out.println(problem872.leafSimilar(root1, root2));
        System.out.println(problem872.leafSimilar2(root1, root2));
    }

    /**
     * 递归中序遍历
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root1
     * @param root2
     * @return
     */
    public boolean leafSimilar(TreeNode root1, TreeNode root2) {
        //按照中序遍历保存root1中叶节点的集合
        List<Integer> list1 = new ArrayList<>();
        //按照中序遍历保存root2中叶节点的集合
        List<Integer> list2 = new ArrayList<>();

        inorder(root1, list1);
        inorder(root2, list2);

        if (list1.size() != list2.size()) {
            return false;
        }

        for (int i = 0; i < list1.size(); i++) {
            //注意：Integer对象之间的比较不能使用==，只能使用equals()
            if (!list1.get(i).equals(list2.get(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * 非递归中序遍历
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root1
     * @param root2
     * @return
     */
    public boolean leafSimilar2(TreeNode root1, TreeNode root2) {
        //按照中序遍历保存root1中叶节点的集合
        List<Integer> list1 = new ArrayList<>();
        //按照中序遍历保存root2中叶节点的集合
        List<Integer> list2 = new ArrayList<>();

        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root1;

        while (!stack.isEmpty() || node != null) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            node = stack.pop();

            if (node.left == null && node.right == null) {
                list1.add(node.val);
            }

            node = node.right;
        }

        stack = new Stack<>();
        node = root2;

        while (!stack.isEmpty() || node != null) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            node = stack.pop();

            if (node.left == null && node.right == null) {
                list2.add(node.val);
            }

            node = node.right;
        }

        if (list1.size() != list2.size()) {
            return false;
        }

        for (int i = 0; i < list1.size(); i++) {
            //注意：Integer对象之间的比较不能使用==，只能使用equals()
            if (!list1.get(i).equals(list2.get(i))) {
                return false;
            }
        }

        return true;
    }

    private void inorder(TreeNode node, List<Integer> list) {
        if (node == null) {
            return;
        }

        if (node.left == null && node.right == null) {
            list.add(node.val);
            return;
        }

        inorder(node.left, list);
        inorder(node.right, list);
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
