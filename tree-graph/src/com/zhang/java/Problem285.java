package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/1/16 08:27
 * @Author zsy
 * @Description 二叉搜索树中的中序后继 类比Problem235、Problem236、Problem270、Problem272、Problem450、Problem510、Problem700、Problem701、Offer68、Offer68_2
 * 给定一棵二叉搜索树和其中的一个节点 p ，找到该节点在树中的中序后继。
 * 如果节点没有中序后继，请返回 null 。
 * 节点 p 的后继是值比 p.val 大的节点中键值最小的节点。
 * <p>
 * 输入：root = [2,1,3], p = 1
 * 输出：2
 * 解释：这里 1 的中序后继是 2。请注意 p 和返回值都应是 TreeNode 类型。
 * <p>
 * 输入：root = [5,3,6,2,4,null,null,1], p = 6
 * 输出：null
 * 解释：因为给出的节点没有中序后继，所以答案就返回 null 了。
 * <p>
 * 树中节点的数目在范围 [1, 10^4] 内。
 * -10^5 <= Node.val <= 10^5
 * 树中各节点的值均保证唯一。
 */
public class Problem285 {
    public static void main(String[] args) {
        Problem285 problem285 = new Problem285();
        String[] data = {"5", "3", "6", "2", "4", "null", "null", "1"};
        TreeNode root = problem285.buildTree(data);
        TreeNode p = root.right;
        TreeNode inorderNextNode = problem285.inorderSuccessor(root, p);
    }

    /**
     * 利用二叉搜索树性质
     * 如果当前节点值小于p.val，则中序遍历节点p的下一个节点在当前节点右子树；否则，中序遍历节点p的下一个节点在当前节点或当前节点右子树
     * 时间复杂度O(h)，空间复杂度O(1) (h=logn，即树的高度)
     *
     * @param root
     * @param p
     * @return
     */
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        TreeNode inorderNextNode = null;
        TreeNode node = root;

        while (node != null) {
            //当前节点值小于p.val，则中序遍历节点p的下一个节点在当前节点右子树
            if (node.val < p.val) {
                node = node.right;
            } else {
                //当前节点值大于等于p.val，中序遍历节点p的下一个节点在当前节点或当前节点右子树
                inorderNextNode = node;
                node = node.left;
            }
        }

        return inorderNextNode;
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
