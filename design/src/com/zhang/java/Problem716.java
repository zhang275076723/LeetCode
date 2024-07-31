package com.zhang.java;

import java.util.Stack;

/**
 * @Date 2022/10/27 09:39
 * @Author zsy
 * @Description 最大栈 类比Problem155、Problem225、Problem232、Problem622、Problem641、Problem705、Problem706、Problem707、Problem895、Problem1172、Problem1381、Problem1670、Offer9、Offer30、Offer59_2
 * 设计一个最大栈数据结构，既支持栈操作，又支持查找栈中最大元素。
 * 实现 MaxStack 类：
 * MaxStack() 初始化栈对象；
 * void push(int x) 将元素 x 压入栈中；
 * int pop() ：移除栈顶元素并返回这个元素；
 * int top() ：返回栈顶元素，无需移除；
 * int peekMax() ：检索并返回栈中最大元素，无需移除；
 * int popMax() ：检索并返回栈中最大元素，并将其移除。如果有多个最大元素，只要移除 最靠近栈顶 的那个。
 * <p>
 * MaxStack stk = new MaxStack();
 * stk.push(5);   // [5] - 5 既是栈顶元素，也是最大元素
 * stk.push(1);   // [5, 1] - 栈顶元素是 1，最大元素是 5
 * stk.push(5);   // [5, 1, 5] - 5 既是栈顶元素，也是最大元素
 * stk.top();     // 返回 5，[5, 1, 5] - 栈没有改变
 * stk.popMax();  // 返回 5，[5, 1] - 栈发生改变，栈顶元素不再是最大元素
 * stk.top();     // 返回 1，[5, 1] - 栈没有改变
 * stk.peekMax(); // 返回 5，[5, 1] - 栈没有改变
 * stk.pop();     // 返回 1，[5] - 此操作后，5 既是栈顶元素，也是最大元素
 * stk.top();     // 返回 5，[5] - 栈没有改变
 * <p>
 * -10^7 <= x <= 10^7;
 * 最多调用 10^4 次 push、pop、top、peekMax和popMax;
 * 调用 pop、top、peekMax 或 popMax 时，栈中至少存在一个元素。
 */
public class Problem716 {
    public static void main(String[] args) {
        MaxStack maxStack = new MaxStack();
        maxStack.push(5);
        maxStack.push(1);
        maxStack.push(5);
        System.out.println(maxStack.top());
        System.out.println(maxStack.popMax());
        System.out.println(maxStack.top());
        System.out.println(maxStack.peekMax());
        System.out.println(maxStack.pop());
        System.out.println(maxStack.top());
    }

    /**
     * 双栈
     * 一个栈保存元素，另一个栈作为单调递增栈，保存当前最大元素
     */
    static class MaxStack {
        //保存元素的栈
        private final Stack<Integer> stack;

        //保存当前最大元素的栈，单调递增栈
        private final Stack<Integer> maxStack;

        public MaxStack() {
            stack = new Stack<>();
            maxStack = new Stack<>();
        }

        public void push(int x) {
            stack.push(x);

            //maxStack为空，或maxStack栈顶元素小于等于当前元素x，x入maxStack栈(必须是小于等于)
            if (maxStack.isEmpty() || maxStack.peek() <= x) {
                maxStack.push(x);
            }
        }

        public int pop() {
            if (stack.isEmpty()) {
                return -1;
            }

            int x = stack.pop();

            if (maxStack.peek() == x) {
                maxStack.pop();
            }

            return x;
        }

        public int top() {
            if (stack.isEmpty()) {
                return -1;
            }

            return stack.peek();
        }

        public int peekMax() {
            if (maxStack.isEmpty()) {
                return -1;
            }

            return maxStack.peek();
        }

        public int popMax() {
            if (maxStack.isEmpty()) {
                return -1;
            }

            int max = maxStack.pop();

            //临时栈中保存stack栈中最大元素上面的元素
            Stack<Integer> tempStack = new Stack<>();

            while (stack.peek() != max) {
                tempStack.push(stack.pop());
            }

            //找到栈中最大元素，出栈
            stack.pop();

            //临时栈中元素再重新入栈
            while (!tempStack.isEmpty()) {
                stack.push(tempStack.pop());
            }

            return max;
        }
    }
}
