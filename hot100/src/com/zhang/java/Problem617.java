package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/6/15 8:28
 * @Author zsy
 * @Description 合并二叉树 类比Offer68_2
 * 给你两棵二叉树： root1 和 root2 。
 * 想象一下，当你将其中一棵覆盖到另一棵之上时，两棵树上的一些节点将会重叠（而另一些不会）。
 * 你需要将这两棵树合并成一棵新二叉树。
 * 合并的规则是：如果两个节点重叠，那么将这两个节点的值相加作为合并后节点的新值；
 * 否则，不为 null 的节点将直接作为新二叉树的节点。
 * 返回合并后的二叉树。
 * 注意: 合并过程必须从两个树的根节点开始。
 * <p>
 * 输入：root1 = [1,3,2,5], root2 = [2,1,3,null,4,null,7]
 * 输出：[3,4,5,5,4,null,7]
 * <p>
 * 输入：root1 = [1], root2 = [1,2]
 * 输出：[2,2]
 * <p>
 * 两棵树中的节点数目在范围 [0, 2000] 内
 * -10^4 <= Node.val <= 10^4
 */
public class Problem617 {
    public static void main(String[] args) {
        Problem617 problem617 = new Problem617();
//        String[] data1 = {"1", "3", "2", "5"};
//        String[] data2 = {"2", "1", "3", "null", "4", "null", "7"};
        String[] data1 = {"1",};
        String[] data2 = {"2", "1"};
        TreeNode root1 = problem617.buildTree(data1);
        TreeNode root2 = problem617.buildTree(data2);
//        TreeNode root = problem617.mergeTrees(root1, root2);
        TreeNode root = problem617.mergeTrees2(root1, root2);
    }

    /**
     * 前序遍历，不修改原二叉树
     * 时间复杂度O(min(m,n))，空间复杂度O(min(m,n)) (m,n分别为二叉树的节点个数)
     *
     * @param root1
     * @param root2
     * @return
     */
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) {
            return null;
        }

        if (root1 == null) {
            return root2;
        }

        if (root2 == null) {
            return root1;
        }

        TreeNode root = new TreeNode(root1.val + root2.val);

        root.left = mergeTrees(root1.left, root2.left);
        root.right = mergeTrees(root1.right, root2.right);

        return root;
    }

    /**
     * 层序遍历，返回的树中会有root1中的节点
     * 时间复杂度O(min(m,n))，空间复杂度O(min(m,n))
     *
     * @param root1
     * @param root2
     * @return
     */
    public TreeNode mergeTrees2(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) {
            return null;
        }

        if (root1 == null) {
            return root2;
        }

        if (root2 == null) {
            return root1;
        }

        //存放合并之后的节点
        Queue<TreeNode> queue = new LinkedList<>();
        //存放root1节点
        Queue<TreeNode> queue1 = new LinkedList<>();
        //存放root2节点
        Queue<TreeNode> queue2 = new LinkedList<>();

        TreeNode root = new TreeNode(root1.val + root2.val);
        queue.offer(root);
        queue1.offer(root1);
        queue2.offer(root2);

        while (!queue1.isEmpty() && !queue2.isEmpty()) {
            TreeNode node = queue.poll();
            TreeNode node1 = queue1.poll();
            TreeNode node2 = queue2.poll();

            if (node1.left != null && node2.left != null) {
                TreeNode leftNode = new TreeNode(node1.left.val + node2.left.val);
                node.left = leftNode;
                queue.offer(leftNode);
                queue1.offer(node1.left);
                queue2.offer(node2.left);
            } else if (node1.left != null) {
                node.left = node1.left;
            } else if (node2.left != null) {
                node.left = node2.left;
            }

            if (node1.right != null && node2.right != null) {
                TreeNode rightNode = new TreeNode(node1.right.val + node2.right.val);
                node.right = rightNode;
                queue.offer(rightNode);
                queue1.offer(node1.right);
                queue2.offer(node2.right);
            } else if (node1.right != null) {
                node.right = node1.right;
            } else if (node2.right != null) {
                node.right = node2.right;
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
