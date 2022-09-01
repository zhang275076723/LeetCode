package com.zhang.java;


import java.util.Stack;

/**
 * @Date 2022/3/21 18:57
 * @Author zsy
 * @Description 二叉搜索树的后序遍历序列 类比Problem98、Problem105、Problem106、Problem145、Offer7、Offer36
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
     * 递归判断后序遍历数组是否是二叉搜索树
     * 后序遍历：左右根
     * 找到根节点，为后序遍历数组的最后一个元素，从左往右遍历数组找到第一个比根节点值大的元素到倒数第二个元素为根的右子树，
     * 右子树节点值都要比根节点值大，再递归判断左子树和右子树是否是后序遍历二叉搜索树
     * 时间复杂度O(n^2)，空间复杂度O(n)
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
     * 判断postorder中[left,right]是否是二叉搜索树的后序遍历
     *
     * @param postorder 后序遍历数组
     * @param left      当前数组起始索引
     * @param right     当前数组末尾索引
     */
    public boolean dfs(int[] postorder, int left, int right) {
        if (left >= right) {
            return true;
        }

        //postorder数组中第一个比根节点值大的元素索引，该节点为右子树中的某一个节点
        int index = left;

        //找到第一个比根节点值大的元素索引
        while (postorder[index] < postorder[right]) {
            index++;
        }

        //根节点右边所有值都要比根节点值要大
        for (int i = index + 1; i < right; i++) {
            if (postorder[i] < postorder[right]) {
                return false;
            }
        }

        //递归判断左子树和右子树是否是后序遍历二叉搜索树
        return dfs(postorder, left, index - 1) && dfs(postorder, index, right - 1);
    }

}
