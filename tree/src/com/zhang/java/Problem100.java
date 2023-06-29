package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/9/1 11:15
 * @Author zsy
 * @Description 相同的树 类比Problem101、Problem226、Problem572、Problem1367、Problem951、Offer26、Offer27、Offer28
 * 给你两棵二叉树的根节点 p 和 q ，编写一个函数来检验这两棵树是否相同。
 * 如果两个树在结构上相同，并且节点具有相同的值，则认为它们是相同的。
 * <p>
 * 输入：p = [1,2,3], q = [1,2,3]
 * 输出：true
 * <p>
 * 输入：p = [1,2], q = [1,null,2]
 * 输出：false
 * <p>
 * 输入：p = [1,2,1], q = [1,1,2]
 * 输出：false
 * <p>
 * 两棵树上的节点数目都在范围 [0, 100] 内
 * -10^4 <= Node.val <= 10^4
 */
public class Problem100 {
    public static void main(String[] args) {
        Problem100 problem100 = new Problem100();
        String[] data1 = {"1", "2"};
        String[] data2 = {"1", "null", "2"};
        TreeNode node1 = problem100.buildTree(data1);
        TreeNode node2 = problem100.buildTree(data2);
        System.out.println(problem100.isSameTree(node1, node2));
        System.out.println(problem100.isSameTree2(node1, node2));
    }

    /**
     * dfs
     * 时间复杂度O(min(m,n))，空间复杂度O(min(m,n)) (m、n分别为两个树节点的个数)
     *
     * @param p
     * @param q
     * @return
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }

        if (p == null || q == null || p.val != q.val) {
            return false;
        }

        //递归判断p的左子树和q的左子树是否相同，p的右子树和q的右子树是否相同
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    /**
     * bfs
     * 时间复杂度O(min(m,n))，空间复杂度O(min(m,n)) (m、n分别为两个树节点的个数)
     *
     * @param p
     * @param q
     * @return
     */
    public boolean isSameTree2(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }

        if (p == null || q == null || p.val != q.val) {
            return false;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(p);
        queue.offer(q);

        while (!queue.isEmpty()) {
            TreeNode node1 = queue.poll();
            TreeNode node2 = queue.poll();

            if (node1 == null && node2 == null) {
                continue;
            }

            if (node1 == null || node2 == null || node1.val != node2.val) {
                return false;
            }

            queue.offer(node1.left);
            queue.offer(node2.left);
            queue.offer(node1.right);
            queue.offer(node2.right);
        }

        return true;
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
