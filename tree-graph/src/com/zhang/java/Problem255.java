package com.zhang.java;

import java.util.Stack;

/**
 * @Date 2023/5/3 08:05
 * @Author zsy
 * @Description 验证前序遍历序列二叉搜索树 分治法类比Problem95、Problem105、Problem106、Problem108、Problem109、Problem241、Problem395、Problem449、Problem617、Problem654、Problem889、Problem1008、Offer7、Offer33 单调栈类比Problem42、Problem84、Problem316、Problem321、Problem402、Problem456、Problem496、Problem503、Problem654、Problem739、Problem795、Problem907、Problem1019、Problem1856、Problem2104、Problem2454、Problem2487、Offer33、DoubleStackSort
 * 给定一个整数数组，你需要验证它是否是一个二叉搜索树正确的先序遍历序列。
 * 你可以假定该序列中的数都是不相同的。
 * <p>
 * 参考以下这颗二叉搜索树：
 * <      5
 * <     / \
 * <    2   6
 * <   / \
 * <  1   3
 * 输入: preorder = [5,2,1,3,6]
 * 输出: true
 * <p>
 * 输入: preorder = [5,2,6,1,3]
 * 输出: false
 * <p>
 * 1 <= preorder.length <= 10^4
 * 1 <= preorder[i] <= 10^4
 * preorder 中 无重复元素
 */
public class Problem255 {
    public static void main(String[] args) {
        Problem255 problem255 = new Problem255();
        int[] preorder = {5, 2, 1, 3, 6};
//        int[] preorder = {5, 2, 6, 1, 3};
        System.out.println(problem255.verifyPreorder(preorder));
        System.out.println(problem255.verifyPreorder2(preorder));
    }

    /**
     * 分治法
     * 二叉搜索树前序遍历数组中第一个元素为当前根节点，从前往后遍历前序遍历数组，找第一个大于根节点的右子树节点下标索引，
     * 将前序遍历数组分为左子树数组和右子树数组，递归判断左子树数组和右子树数组是否是二叉搜索树的前序遍历结果
     * 时间复杂度O(n)，空间复杂度O(n) (最差时间复杂度O(n^2))
     *
     * @param preorder
     * @return
     */
    public boolean verifyPreorder(int[] preorder) {
        if (preorder == null || preorder.length <= 1) {
            return true;
        }

        return dfs(preorder, 0, preorder.length - 1);
    }

    /**
     * 单调栈
     * 前序遍历：根左右
     * 顺序遍历preorder，如果当前节点的值小于父节点的值，则preorder不是二叉搜索树的前序遍历结果，返回false，
     * 如果当前节点的值不满足单调递减栈，则当前遍历到了右子树，栈中节点出栈，更新父节点
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param preorder
     * @return
     */
    public boolean verifyPreorder2(int[] preorder) {
        if (preorder == null || preorder.length <= 1) {
            return true;
        }

        //单调递减栈，表示当前遍历到的是左子树，因为左子树的值小于根节点的值
        Stack<Integer> stack = new Stack<>();
        //当前遍历到节点的父节点，初始化根节点的父节点为int最小值，因为从前往后遍历preorder，所以父节点的值小于当前节点的值
        int parent = Integer.MIN_VALUE;

        for (int i = 0; i < preorder.length; i++) {
            //当前节点的值小于父节点的值，则不是二叉搜索树，返回false
            if (preorder[i] < parent) {
                return false;
            }

            //不满足单调递减栈，则当前遍历到了右子树，栈中节点出栈，更新父节点
            while (!stack.isEmpty() && preorder[stack.peek()] < preorder[i]) {
                parent = preorder[stack.pop()];
            }

            //当前节点入栈，继续遍历左子树节点
            stack.push(i);
        }

        //遍历结束，则是二叉搜索树，返回true
        return true;
    }

    /**
     * 判断前序遍历数组preorder中[left,right]是否是二叉搜索树的前序遍历结果
     *
     * @param preorder
     * @param left
     * @param right
     * @return
     */
    private boolean dfs(int[] preorder, int left, int right) {
        if (left >= right) {
            return true;
        }

        //右子树根节点下标索引，即为preorder从左往右遍历中第一个大于preorder[left]的下标索引
        int rightRootIndex = left + 1;

        //从左往右遍历找第一个大于根节点的右子树根节点下标索引rightRootIndex
        while (rightRootIndex < right && preorder[left] > preorder[rightRootIndex]) {
            rightRootIndex++;
        }

        //前序遍历数组preorder中[rightRootIndex,right]为右子树的节点，如果存在小于根节点的值，
        //则前序遍历数组不是二叉搜索树的前序遍历结果，返回false
        for (int i = rightRootIndex; i <= right; i++) {
            if (preorder[left] > preorder[i]) {
                return false;
            }
        }

        //递归判断左子数组和右子数组是否是二叉搜索树的前序遍历结果
        return dfs(preorder, left + 1, rightRootIndex - 1) && dfs(preorder, rightRootIndex, right);
    }
}
