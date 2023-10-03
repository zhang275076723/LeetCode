package com.zhang.java;


import java.util.Stack;

/**
 * @Date 2022/3/21 18:57
 * @Author zsy
 * @Description 二叉搜索树的后序遍历序列 分治法类比Problem95、Problem105、Problem106、Problem108、Problem109、Problem241、Problem255、Problem395、Problem449、Problem617、Problem654、Problem889、Problem1008、Offer7 单调栈类比Problem42、Problem84、Problem255、Problem316、Problem321、Problem402、Problem456、Problem496、Problem503、Problem654、Problem739、Problem795、Problem907、Problem1019、Problem1856、Problem2104、Problem2454、Problem2487、DoubleStackSort
 * 输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历结果。
 * 如果是则返回 true，否则返回 false。假设输入的数组的任意两个数字都互不相同。
 * <p>
 * 参考以下这颗二叉搜索树：
 * <      5
 * <     / \
 * <    2   6
 * <   / \
 * <  1   3
 * <p>
 * 输入: [1,6,3,2,5]
 * 输出: false
 * <p>
 * 输入: [1,3,2,6,5]
 * 输出: true
 * <p>
 * 数组长度 <= 1000
 */
public class Offer33 {
    public static void main(String[] args) {
        Offer33 offer33 = new Offer33();
//        int[] postorder = {1, 6, 3, 2, 5};
        int[] postorder = {1, 3, 2, 6, 5};
        System.out.println(offer33.verifyPostorder(postorder));
        System.out.println(offer33.verifyPostorder2(postorder));
    }

    /**
     * 分治法
     * 二叉搜索树后序遍历数组中最后一个元素为当前根节点，从前往后遍历后序遍历数组，找第一个大于根节点的右子树节点下标索引，
     * 将后序遍历数组分为左子树数组和右子树数组，递归判断左子树数组和右子树数组是否是二叉搜索树的后序遍历结果
     * 时间复杂度O(n)，空间复杂度O(n) (最差时间复杂度O(n^2))
     *
     * @param postorder
     * @return
     */
    public boolean verifyPostorder(int[] postorder) {
        if (postorder == null || postorder.length <= 1) {
            return true;
        }

        return dfs(postorder, 0, postorder.length - 1);
    }

    /**
     * 单调栈
     * 后序遍历：左右根，逆序后序遍历：根右左
     * 逆序遍历postorder，如果当前节点的值大于父节点的值，则postorder不是二叉搜索树的后序遍历结果，返回false，
     * 如果当前节点的值不满足单调递增栈，则当前遍历到了左子树，栈中节点出栈，更新父节点
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param postorder
     * @return
     */
    public boolean verifyPostorder2(int[] postorder) {
        if (postorder == null || postorder.length <= 1) {
            return true;
        }

        //单调递增栈
        Stack<Integer> stack = new Stack<>();
        //当前遍历到节点的父节点，初始化根节点的父节点为int最大值，因为从后往前遍历postorder，所以父节点的值大于当前节点的值
        int parent = Integer.MAX_VALUE;

        for (int i = postorder.length - 1; i >= 0; i--) {
            //当前节点的值大于父节点的值，则不是二叉搜索树，返回false
            if (postorder[i] > parent) {
                return false;
            }

            //不满足单调递增栈，则当前遍历到了左子树，栈中节点出栈，更新父节点
            while (!stack.empty() && postorder[stack.peek()] > postorder[i]) {
                parent = postorder[stack.pop()];
            }

            //当前节点入栈，继续遍历右子树节点
            stack.push(i);
        }

        //遍历结束，则是二叉搜索树，返回true
        return true;
    }

    /**
     * 判断后序遍历数组postorder中[left,right]是否是二叉搜索树的后序遍历结果
     *
     * @param postorder 后序遍历数组
     * @param left      当前数组起始索引
     * @param right     当前数组末尾索引
     */
    public boolean dfs(int[] postorder, int left, int right) {
        //当前区间为空，或只有一个元素，返回true，即当前后序遍历数组是二叉搜索树的后序遍历结果
        if (left >= right) {
            return true;
        }

        //后序遍历数组postorder中第一个大于根节点的右子树节点下标索引，即[rightIndex,right-1]都比根节点postorder[right]值要大
        int rightIndex = left;

        //从左往右遍历找第一个大于根节点的右子树节点下标索引rightIndex
        while (rightIndex < right && postorder[rightIndex] < postorder[right]) {
            rightIndex++;
        }

        //后序遍历数组postorder中[rightIndex,right-1]为右子树的节点，如果存在小于根节点的值，
        //则后序遍历数组不是二叉搜索树的后序遍历结果，返回false
        for (int i = rightIndex; i < right; i++) {
            if (postorder[i] < postorder[right]) {
                return false;
            }
        }

        //递归判断左子数组和右子数组是否是二叉搜索树的后序遍历结果
        return dfs(postorder, left, rightIndex - 1) && dfs(postorder, rightIndex, right - 1);
    }
}
