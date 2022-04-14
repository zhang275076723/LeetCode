package com.zhang.java;


import java.util.Stack;

/**
 * @Date 2022/3/21 18:57
 * @Author zsy
 * @Description 输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历结果。
 * 如果是则返回 true，否则返回 false。假设输入的数组的任意两个数字都互不相同。
 * <p>
 * 输入: [1,6,3,2,5]
 * 输出: false
 * <p>
 * 输入: [1,3,2,6,5]
 * 输出: true
 */
public class Offer33 {
    public static void main(String[] args) {
        Offer33 offer33 = new Offer33();
//        int[] postorder = {1, 6, 3, 2, 5};
        int[] postorder = {1, 3, 2, 6, 5};
        System.out.println(offer33.verifyPostorder(postorder));
        System.out.println(offer33.verifyPostorder2(postorder));
    }

    public boolean verifyPostorder(int[] postorder) {
        if (postorder == null || postorder.length < 2) {
            return true;
        }
        return recursion(postorder, 0, postorder.length - 1);
    }

    /**
     * @param postorder 后序遍历数组
     * @param left      当前数组起始索引
     * @param right     当前数组末尾索引
     */
    public boolean recursion(int[] postorder, int left, int right) {
        if (left >= right) {
            return true;
        }

        //当前根节点
        int rootVal = postorder[right];
        //中序遍历中当前根节点右子树数组的起始索引
        int rightIndex = left;

        while (postorder[rightIndex] < rootVal) {
            rightIndex++;
        }

        for (int i = rightIndex; i < right; i++) {
            if (postorder[i] < rootVal) {
                return false;
            }
        }

        return recursion(postorder, left, rightIndex - 1) &&
                recursion(postorder, rightIndex, right - 1);
    }

    /**
     * 单调栈（未理解）
     * 后序遍历的逆序是：根，右子树，左子树
     * arr[i] < arr[i+1]，则arr[i+1]是arr[i]的右子节点
     * arr[i] > arr[i+1]，则arr[i+1]是arr[0]到arr[i]中某个节点的左子节点，并且这个值是arr[0]到arr[i]中的最小值
     *
     * @param postorder
     * @return
     */
    public boolean verifyPostorder2(int[] postorder) {
        //单调递增栈
        Stack<Integer> stack = new Stack<>();
        int parent = Integer.MAX_VALUE;

        for (int i = postorder.length - 1; i >= 0; i--) {
            int val = postorder[i];
            //栈顶元素 > val 时，val是栈中最小元素的左子节点
            while (!stack.empty() && stack.peek() > val) {
                parent = stack.pop();
            }
            //val是左子树的值，如果大于parent，则不是二叉搜索树
            if (val > parent) {
                return false;
            }
            //栈顶元素 < val，val是栈顶元素的右子节点，val入栈
            stack.push(val);
        }

        return true;
    }

}
