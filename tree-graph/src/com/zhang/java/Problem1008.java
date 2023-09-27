package com.zhang.java;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/9/15 10:09
 * @Author zsy
 * @Description 前序遍历构造二叉搜索树 分治法类比Problem95、Problem105、Problem106、Problem108、Problem109、Problem255、Problem395、Problem449、Problem617、Problem654、Problem889、Offer7、Offer33
 * 给定一个整数数组，它表示BST(即 二叉搜索树 )的 先序遍历 ，构造树并返回其根。
 * 保证 对于给定的测试用例，总是有可能找到具有给定需求的二叉搜索树。
 * 二叉搜索树 是一棵二叉树，其中每个节点，
 * Node.left 的任何后代的值 严格小于 Node.val , Node.right 的任何后代的值 严格大于 Node.val。
 * 二叉树的 前序遍历 首先显示节点的值，然后遍历Node.left，最后遍历Node.right。
 * <p>
 * 输入：preorder = [8,5,1,7,10,12]
 * 输出：[8,5,10,1,7,null,12]
 * <p>
 * 输入: preorder = [1,3]
 * 输出: [1,null,3]
 * <p>
 * 1 <= preorder.length <= 100
 * 1 <= preorder[i] <= 10^8
 * preorder 中的值 互不相同
 */
public class Problem1008 {
    public static void main(String[] args) {
        Problem1008 problem1008 = new Problem1008();
        int[] preorder = {8, 5, 1, 7, 10, 12};
//        TreeNode root = problem1008.bstFromPreorder(preorder);
        TreeNode root = problem1008.bstFromPreorder2(preorder);
    }

    /**
     * 分治法
     * 二叉搜索树的中序遍历为顺序，对前序遍历数组由小到大排序得到中序遍历数组，由前序遍历数组和中序遍历数组构造二叉树，
     * 前序遍历数组中第一个元素为当前根节点，将前序遍历数组和中序遍历数组分为左子树数组和右子树数组，
     * 递归对左子树数组和右子树数组建立二叉树
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     * (排序时间复杂度为O(nlogn)，排序辅助数组需要O(n)空间，构造二叉树时间为O(n)，哈希表需要O(n)空间，递归栈的深度平均为O(logn)，最差为O(n))
     *
     * @param preorder
     * @return
     */
    public TreeNode bstFromPreorder(int[] preorder) {
        if (preorder == null || preorder.length == 0) {
            return null;
        }

        //中序遍历数组
        int[] inorder = Arrays.copyOfRange(preorder, 0, preorder.length);

        mergeSort(inorder, 0, inorder.length - 1, new int[inorder.length]);

        //在O(1)确定前序遍历数组中元素在中序遍历数组中索引
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }

        return buildTree(0, preorder.length - 1,
                0, inorder.length - 1,
                preorder, inorder, map);
    }

    /**
     * 分治法
     * 利用二叉搜索树性质，前序遍历数组第一个元素作为根节点，第一个节点之后到第一个比根节点值大的节点之前，都为左子树数组，
     * 之后都为右子树数组，递归对左子树数组和右子树数组建立二叉搜索树
     * 时间复杂度O(n)，空间复杂度O(n) (最差时间复杂度O(n^2))
     *
     * @param preorder
     * @return
     */
    public TreeNode bstFromPreorder2(int[] preorder) {
        if (preorder == null || preorder.length == 0) {
            return null;
        }

        return buildTree(preorder, 0, preorder.length - 1);
    }

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

        //根节点，前序遍历数组中第一个元素即为根节点
        TreeNode root = new TreeNode(preorder[preorderLeft]);

        root.left = buildTree(preorderLeft + 1, preorderLeft + leftLength,
                inorderLeft, inorderRootIndex - 1,
                preorder, inorder, map);
        root.right = buildTree(preorderLeft + leftLength + 1, preorderRight,
                inorderRootIndex + 1, inorderRight,
                preorder, inorder, map);

        return root;
    }

    private TreeNode buildTree(int[] preorder, int left, int right) {
        if (left > right) {
            return null;
        }

        if (left == right) {
            return new TreeNode(preorder[left]);
        }

        //右子树根节点下标索引，前序遍历数组中第一个比根节点preorder[left]大的节点
        int rightRootIndex = left + 1;

        //找第一个比根节点值大的元素索引
        while (rightRootIndex <= right && preorder[rightRootIndex] < preorder[left]) {
            rightRootIndex++;
        }

        //根节点，前序遍历数组中第一个元素即为根节点
        TreeNode root = new TreeNode(preorder[left]);

        root.left = buildTree(preorder, left + 1, rightRootIndex - 1);
        root.right = buildTree(preorder, rightRootIndex, right);

        return root;
    }

    private void mergeSort(int[] nums, int left, int right, int[] tempArr) {
        if (left < right) {
            int mid = left + ((right - left) >> 1);
            mergeSort(nums, left, mid, tempArr);
            mergeSort(nums, mid + 1, right, tempArr);
            merge(nums, left, mid, right, tempArr);
        }
    }

    private void merge(int[] nums, int left, int mid, int right, int[] tempArr) {
        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (nums[i] < nums[j]) {
                tempArr[k] = nums[i];
                i++;
            } else {
                tempArr[k] = nums[j];
                j++;
            }
            k++;
        }

        while (i <= mid) {
            tempArr[k] = nums[i];
            i++;
            k++;
        }

        while (j <= right) {
            tempArr[k] = nums[j];
            j++;
            k++;
        }

        for (k = left; k <= right; k++) {
            nums[k] = tempArr[k];
        }
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
