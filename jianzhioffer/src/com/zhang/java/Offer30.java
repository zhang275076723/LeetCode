package com.zhang.java;


import java.util.Stack;

/**
 * @Date 2022/3/20 16:41
 * @Author zsy
 * @Description 定义栈的数据结构，请在该类型中实现一个能够得到栈的最小元素的 min 函数在该栈中，
 * 调用 min、push 及 pop 的时间复杂度都是 O(1)。
 * <p>
 * MinStack minStack = new MinStack();
 * minStack.push(-2);
 * minStack.push(0);
 * minStack.push(-3);
 * minStack.min();   --> 返回 -3.
 * minStack.pop();
 * minStack.top();      --> 返回 0.
 * minStack.min();   --> 返回 -2.
 */
public class Offer30 {
    public static void main(String[] args) {
        MinStack minStack = new MinStack();
//        MinStack2 minStack = new MinStack2();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        System.out.println(minStack.min());
        minStack.pop();
        System.out.println(minStack.top());
        System.out.println(minStack.min());
    }

    /**
     * 使用链表，每个节点都存储当前节点到尾节点的最小值
     */
    public static class MinStack {
        Node head;

        public MinStack() {
            head = new Node(Integer.MAX_VALUE, Integer.MAX_VALUE, null);
        }

        public void push(int x) {
            head = new Node(x, Math.min(x, head.min), head);
        }

        public void pop() {
            head = head.next;
        }

        public int top() {
            return head.val;
        }

        public int min() {
            return head.min;
        }
    }

    /**
     * 使用两个栈，一个栈保存所有元素，一个栈保存当前栈的最小值元素
     */
    public static class MinStack2 {
        //保存所有元素
        Stack<Integer> stack1;
        //保存当前栈的最小值元素
        Stack<Integer> stack2;

        public MinStack2() {
            stack1 = new Stack<>();
            stack2 = new Stack<>();
        }

        public void push(int x) {
            stack1.push(x);
            if (stack2.empty() || x <= stack2.peek()) {
                stack2.push(x);
            }
        }

        public void pop() {
            if (stack1.peek() > stack2.peek()) {
                stack1.pop();
            } else {
                stack1.pop();
                stack2.pop();
            }
        }

        public int top() {
            return stack1.peek();
        }

        public int min() {
            return stack2.peek();
        }
    }

    public static class Node {
        int val;
        int min;
        Node next;

        Node(int val, int min, Node next) {
            this.val = val;
            this.min = min;
            this.next = next;
        }
    }
}
