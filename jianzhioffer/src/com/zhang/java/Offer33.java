package com.zhang.java;


import java.util.Stack;

/**
 * @Date 2022/3/21 18:57
 * @Author zsy
 * @Description 二叉搜索树的后序遍历序列 分治法类比Problem95、Problem105、Problem106、Problem108、Problem109、Problem449、Problem889、Problem1008、Offer7
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
     * 单调栈 (不理解)
     * 后序遍历：左右根， 逆序后序遍历：根右左
     * 单调递增栈存储当前节点的父节点，逆序遍历后序遍历数组，
     * 1、stack.peek() < postorder[i]，则postorder[i]是stack.peek()的右子节点，postorder[i]入栈
     * 判断postorder[i]是否小于parent，如果不小于，则不是后序遍历，返回false
     * 2、stack.peek() < postorder[i]，则postorder[i]是栈中某个节点的左子节点，栈中元素出栈，赋值给当前parent，
     * 判断postorder[i]是否小于parent，如果不小于，则不是后序遍历，返回false
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
        int parent = Integer.MAX_VALUE;

        for (int i = postorder.length - 1; i >= 0; i--) {
            int value = postorder[i];

            //栈顶元素 > value 时，value是栈中最小元素的左子节点
            while (!stack.empty() && stack.peek() > value) {
                parent = stack.pop();
            }

            //value是左子树的值，如果大于parent，则不是二叉搜索树
            if (value > parent) {
                return false;
            }

            //栈顶元素 < value，value是栈顶元素的右子节点，value入栈
            stack.push(value);
        }

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

        //找第一个大于根节点的右子树节点下标索引rightIndex
        while (rightIndex < right && postorder[rightIndex] < postorder[right]) {
            rightIndex++;
        }

        //后序遍历数组postorder中[rightIndex,right-1]为右子树的节点，如果存在小于根节点的值，
        //则当前后序遍历数组不是二叉搜索树的后序遍历结果，返回false
        for (int i = rightIndex; i < right; i++) {
            if (postorder[i] < postorder[right]) {
                return false;
            }
        }

        //递归判断左子数组和右子数组是否是二叉搜索树的后序遍历结果
        return dfs(postorder, left, rightIndex - 1) && dfs(postorder, rightIndex, right - 1);
    }
}
