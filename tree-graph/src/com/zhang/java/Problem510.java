package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/1/16 08:46
 * @Author zsy
 * @Description 二叉搜索树中的中序后继 II 字节面试题 类比Problem235、Problem236、Problem270、Problem272、Problem285、Problem450、Problem700、Problem701、Offer68、Offer68_2
 * 给定一棵二叉搜索树和其中的一个节点 node ，找到该节点在树中的中序后继。
 * 如果节点没有中序后继，请返回 null 。
 * 一个节点 node 的中序后继是键值比 node.val 大所有的节点中键值最小的那个。
 * 你可以直接访问结点，但无法直接访问树。
 * 每个节点都会有其父节点的引用。
 * <p>
 * 输入：tree = [2,1,3], node = 1
 * 输出：2
 * 解析：1 的中序后继结点是 2 。注意节点和返回值都是 Node 类型的。
 * <p>
 * 输入：tree = [5,3,6,2,4,null,null,1], node = 6
 * 输出：null
 * 解析：该结点没有中序后继，因此返回 null 。
 * <p>
 * 输入：tree = [15,6,18,3,7,17,20,2,4,null,13,null,null,null,null,null,null,null,null,9], node = 15
 * 输出：17
 * <p>
 * 输入：tree = [15,6,18,3,7,17,20,2,4,null,13,null,null,null,null,null,null,null,null,9], node = 13
 * 输出：15
 * <p>
 * 输入：tree = [0], node = 0
 * 输出：null
 * <p>
 * 树中节点的数目在范围 [1, 10^4] 内。
 * -10^5 <= Node.val <= 10^5
 * 树中各结点的值均保证唯一。
 */
public class Problem510 {
    public static void main(String[] args) {
        Problem510 problem510 = new Problem510();
        String[] data = {"15", "6", "18", "3", "7", "17", "20", "2", "4", "null",
                "13", "null", "null", "null", "null", "null", "null", "null", "null", "9"};
        TreeNode root = problem510.buildTree(data);
        TreeNode node = root.left.right.right.left;
        TreeNode inorderNextNode = problem510.inorderSuccessor(node);
    }

    /**
     * 利用二叉搜索树性质
     * 1、当前节点存在右子树，则右子树的最左下节点为当前节点中序遍历的下一个节点
     * 2、当前节点不存在右子树，则当前节点沿着父节点往上找，直至temp节点父节点的左子节点等于temp节点时，temp节点的父节点为当前节点中序遍历的下一个节点
     * 时间复杂度O(h)，空间复杂度O(1) (h=logn，即树的高度)
     *
     * @param node
     * @return
     */
    public TreeNode inorderSuccessor(TreeNode node) {
        if (node == null) {
            return null;
        }

        //情况1：当前节点存在右子树，则右子树的最左下节点为当前节点中序遍历的下一个节点
        if (node.right != null) {
            //node右子树的最左下节点
            TreeNode mostLeftNode = node.right;

            while (mostLeftNode.left != null) {
                mostLeftNode = mostLeftNode.left;
            }

            return mostLeftNode;
        } else {
            //情况2：当前节点不存在右子树，则当前节点沿着父节点往上找，直至temp节点父节点的左子节点等于temp节点时，temp节点的父节点为当前节点中序遍历的下一个节点

            TreeNode temp = node;

            while (temp.parent != null && temp.parent.left != temp) {
                temp = temp.parent;
            }

            return temp.parent;
        }
    }

    /**
     * 在建树的过程中同时赋值当前节点的父节点
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param data
     * @return
     */
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
                    //父节点赋值
                    leftNode.parent = node;
                    queue.offer(leftNode);
                }
            }
            if (!list.isEmpty()) {
                String rightValue = list.remove(0);
                if (!"null".equals(rightValue)) {
                    TreeNode rightNode = new TreeNode(Integer.parseInt(rightValue));
                    node.right = rightNode;
                    //父节点赋值
                    rightNode.parent = node;
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
        //当前节点的父节点
        TreeNode parent;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right, TreeNode parent) {
            this.val = val;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
    }
}
