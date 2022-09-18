package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/3/13 15:25
 * @Author zsy
 * @Description 重建二叉树 类比Problem106、Problem889、Problem1008、Offer33 同Problem105
 * 输入某二叉树的前序遍历和中序遍历的结果，请构建该二叉树并返回其根节点
 * 假设输入的前序遍历和中序遍历的结果中都不含重复的数字
 * <p>
 * Input: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
 * Output: [3,9,20,null,null,15,7]
 * <p>
 * Input: preorder = [-1], inorder = [-1]
 * Output: [-1]
 * <p>
 * 0 <= 节点个数 <= 5000
 */
public class Offer7 {
    public static void main(String[] args) {
        Offer7 offer7 = new Offer7();
        int[] preorder = {3, 9, 20, 15, 7};
        int[] inorder = {9, 3, 15, 20, 7};
        TreeNode root = offer7.buildTree(preorder, inorder);
        offer7.levelTraversal(root);
    }

    /**
     * 分治
     * 1、通过【前序遍历列表】确定【根节点 (root)】和【中序遍历列表】中的【根节点索引 (inorderRootIndex)】
     * 2、将【前序遍历列表】的节点分割成【左节点的前序遍历列表】和【右节点的前序遍历列表】
     * 2、将【中序遍历列表】的节点分割成【左节点的中序遍历列表】和【右节点的中序遍历列表】
     * 3、递归寻找【左分支节点】中的【根节点】和 【右分支节点】中的【根节点】
     * 时间复杂度O(n)，空间复杂度O(n) (哈希表需要O(n)的空间，栈的深度平均为O(logn)，最差为O(n))
     *
     * @param preorder
     * @param inorder
     * @return
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder.length == 0) {
            return null;
        }

        //key：节点值，value：节点在中序遍历数组的索引下标，在O(1)时间，定位中序遍历数组中根节点的索引
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }

        return buildTree(0, preorder.length - 1, 0, inorder.length - 1, preorder, inorder, map);
    }

    /**
     * @param preorderLeft  前序遍历左指针
     * @param preorderRight 前序遍历右指针
     * @param inorderLeft   中序遍历左指针
     * @param inorderRight  中序遍历右指针
     * @param preorder      前序遍历数组
     * @param inorder       中序遍历数组
     * @param map           中序遍历数组哈希，在O(1)时间找到中序遍历数组中的根节点索引
     * @return
     */
    private TreeNode buildTree(int preorderLeft, int preorderRight, int inorderLeft, int inorderRight,
                               int[] preorder, int[] inorder, Map<Integer, Integer> map) {
        if (preorderLeft > preorderRight) {
            return null;
        }

        if (preorderLeft == preorderRight) {
            return new TreeNode(preorder[preorderLeft]);
        }

        //中序遍历数组中根节点索引
        int inorderRootIndex = map.get(preorder[preorderLeft]);
        //左子树长度
        int leftLength = inorderRootIndex - inorderLeft;

        TreeNode root = new TreeNode(preorder[preorderLeft]);

        root.left = buildTree(preorderLeft + 1, preorderLeft + leftLength, inorderLeft, inorderRootIndex - 1,
                preorder, inorder, map);
        root.right = buildTree(preorderLeft + leftLength + 1, preorderRight, inorderRootIndex + 1, inorderRight,
                preorder, inorder, map);

        return root;
    }

    private void levelTraversal(TreeNode root) {
        if (root == null) {
            return;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            System.out.println(node.val);
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
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


