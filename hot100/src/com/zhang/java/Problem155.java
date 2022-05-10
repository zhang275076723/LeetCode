package com.zhang.java;

import java.util.Stack;

/**
 * @Date 2022/5/10 9:33
 * @Author zsy
 * @Description 最小栈
 * 设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
 * 实现 MinStack 类:
 * MinStack() 初始化堆栈对象。
 * void push(int val) 将元素val推入堆栈。
 * void pop() 删除堆栈顶部的元素。
 * int top() 获取堆栈顶部的元素。
 * int getMin() 获取堆栈中的最小元素。
 * <p>
 * 输入：
 * ["MinStack","push","push","push","getMin","pop","top","getMin"]
 * [[],[-2],[0],[-3],[],[],[],[]]
 * 输出：
 * [null,null,null,null,-3,null,0,-2]
 * 解释：
 * MinStack minStack = new MinStack();
 * minStack.push(-2);
 * minStack.push(0);
 * minStack.push(-3);
 * minStack.getMin();   --> 返回 -3.
 * minStack.pop();
 * minStack.top();      --> 返回 0.
 * minStack.getMin();   --> 返回 -2.
 * <p>
 * -2^31 <= val <= 2^31 - 1
 * pop、top 和 getMin 操作总是在 非空栈 上调用
 * push, pop, top, and getMin最多被调用 3 * 10^4 次
 */
public class Problem155 {
    public static void main(String[] args) {
//        MinStack minStack = new MinStack();
//        MinStack2 minStack = new MinStack2();
        MinStack3 minStack = new MinStack3();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        System.out.println(minStack.getMin());
        minStack.pop();
        System.out.println(minStack.top());
        System.out.println(minStack.getMin());
    }

    /**
     * 使用两个栈，一个栈保存所有元素，另一个栈在栈顶保存当前栈的最小值元素
     * 时间复杂度O(1)，空间复杂度O(n)
     */
    private static class MinStack {
        //保存所有元素的栈
        private Stack<Integer> stack;

        //栈顶保存当前栈的最小值元素的栈
        private Stack<Integer> minStack;

        public MinStack() {
            stack = new Stack<>();
            minStack = new Stack<>();
        }

        public void push(int val) {
            stack.push(val);
            if (minStack.isEmpty() || minStack.peek() >= val) {
                minStack.push(val);
            }
        }

        public void pop() {
            int x = stack.pop();
            if (minStack.peek() >= x) {
                minStack.pop();
            }
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return minStack.peek();
        }
    }

    /**
     * 使用链表，每个节点都存储当前节点的值和当前节点到尾节点的最小值
     * 时间复杂度O(1)，空间复杂度O(n)
     */
    private static class MinStack2 {
        //头结点
        private Node head;

        public MinStack2() {
            head = new Node(Integer.MAX_VALUE, Integer.MAX_VALUE, null);
        }

        public void push(int val) {
            head = new Node(val, Math.min(val, head.min), head);
        }

        public void pop() {
            Node newHead = head.next;
            head.next = null;
            head = newHead;
        }

        public int top() {
            return head.val;
        }

        public int getMin() {
            return head.min;
        }

        private static class Node {
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

    /**
     * 只使用一个栈，不使用额外的栈存储当前栈的最小元素
     * 时间复杂度O(1)，空间复杂度O(1)
     */
    private static class MinStack3 {
        //保存当前元素和最小元素差值的栈
        //因为int类型差值可能会溢出，所以使用long
        private Stack<Long> stack;

        //当前最小元素
        private int min;

        public MinStack3() {
            stack = new Stack<>();
        }

        /**
         * 入栈时，栈中保存当前元素和最小元素的差值
         *
         * @param val
         */
        public void push(int val) {
            if (stack.isEmpty()) {
                stack.push(0L);
                min = val;
            } else {
                //如果val是最小的数，这里可能越界，所以用Long来保存
                stack.push((long) val - min);
                min = Math.min(min, val);
            }
        }

        /**
         * 1、如果差值diff>=0，则说明要出栈的值大于等于当前min，返回min+diff；
         * 2、如果差值diff<0，则说明当前要出栈的值为min，返回min，更新min=min-diff
         */
        public void pop() {
            //元素和最小元素的差值
            long diff = stack.pop();
            if (diff >= 0) {
//                return min + diff;
            } else {
//                int result = min;
                min = (int) (min - diff);
//                return result;
            }
        }

        public int top() {
            //元素和最小元素的差值
            long diff = stack.peek();
            if (diff >= 0) {
                return (int) (min + diff);
            } else {
                return min;
            }
        }

        public int getMin() {
            return min;
        }
    }
}
