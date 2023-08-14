package com.zhang.java;

import java.util.Stack;

/**
 * @Date 2023/5/17 09:24
 * @Author zsy
 * @Description 双栈排序 字节面试题 美团面试题 猿辅导面试题 单调栈类比Problem42、Problem84、Problem255、Problem316、Problem321、Problem402、Problem456、Problem496、Problem503、Problem654、Problem739、Problem907、Problem1019、Problem1856、Problem2104、Problem2487、Offer33
 * 给定一个乱序的栈，允许额外使用一个栈来辅助操作，设计算法将其升序排列。
 * <p>
 * 输入：[4, 2, 1, 3]
 * 输出：[1, 2, 3, 4]
 */
public class DoubleStackSort {
    public static void main(String[] args) {
        DoubleStackSort doubleStackSort = new DoubleStackSort();
        Stack<Integer> stack = new Stack<>();
        stack.push(4);
        stack.push(2);
        stack.push(1);
        stack.push(3);
        System.out.println(doubleStackSort.sort(stack));
    }

    /**
     * 单调栈
     * 单调递增栈保存原始栈中元素，当不满足单调递增栈时，原始栈中当前元素出栈，保存当前元素，
     * 单调栈中大于当前元素的元素依次出栈，入原始栈，直到当前元素满足单调栈时，当前元素入单调栈中
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param stack
     */
    public Stack<Integer> sort(Stack<Integer> stack) {
        //单调递增栈
        Stack<Integer> resultStack = new Stack<>();

        while (!stack.isEmpty()) {
            //原始栈顶元素
            int value = stack.pop();

            //不满足单调递增栈，单调栈中大于value的元素依次出栈，入原始栈
            while (!resultStack.isEmpty() && resultStack.peek() > value) {
                stack.push(resultStack.pop());
            }

            resultStack.push(value);
        }

        return resultStack;
    }
}
