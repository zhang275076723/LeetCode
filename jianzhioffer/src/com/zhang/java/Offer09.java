package com.zhang.java;

import java.util.Stack;

/**
 * @Date 2022/3/13 19:38
 * @Author zsy
 * @Description 用两个栈实现一个队列。队列的声明如下，请实现它的两个函数 appendTail 和 deleteHead ，
 * 分别完成在队列尾部插入整数和在队列头部删除整数的功能。(若队列中没有元素，deleteHead操作返回 -1 )
 */
public class Offer09 {
    public static void main(String[] args) {

    }

    /**
     * 使用两个栈，一个出栈，一个入栈
     */
    public static class CQueue {
        private Stack<Integer> stack1;
        private Stack<Integer> stack2;

        public CQueue() {
            stack1 = new Stack<>();
            stack2 = new Stack<>();
        }

        public void appendTail(int value) {
            stack1.push(value);
        }

        public int deleteHead() {
            if (!stack2.empty()) {
                return stack2.pop();
            }
            if (!stack1.empty()) {
                while (!stack1.empty()) {
                    stack2.push(stack1.pop());
                }
                return stack2.pop();
            }
            return -1;
        }
    }
}