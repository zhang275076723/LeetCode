package com.zhang.java;

import java.util.Stack;

/**
 * @Date 2022/5/10 9:33
 * @Author zsy
 * @Description 最小栈 类比Problem225、Problem232、Problem295、Problem716、Offer9、Offer31、Offer41、Offer59_2 同Offer30
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
     * 双栈
     * 一个栈保存所有元素，另一个栈在栈顶保存当前栈的最小值元素，作为单调递减栈
     * 均摊时间复杂度O(1)，空间复杂度O(n)
     */
    static class MinStack {
        //保存元素的栈
        private final Stack<Integer> stack;

        //保存当前最小元素的栈，单调递减栈
        private final Stack<Integer> minStack;

        public MinStack() {
            stack = new Stack<>();
            minStack = new Stack<>();
        }

        public void push(int x) {
            stack.push(x);

            //minStack为空，或minStack栈顶元素大于等于当前元素x，x入minStack栈(必须是大于等于)
            if (minStack.isEmpty() || minStack.peek() >= x) {
                minStack.push(x);
            }
        }

        public void pop() {
            if (stack.isEmpty()) {
                return;
            }

            int x = stack.pop();

            if (minStack.peek() == x) {
                minStack.pop();
            }
        }

        public int top() {
            if (stack.isEmpty()) {
                return -1;
            }

            return stack.peek();
        }

        public int getMin() {
            if (minStack.isEmpty()) {
                return -1;
            }

            return minStack.peek();
        }
    }

    /**
     * 使用链表，每个节点都存储当前节点的值和当前节点到尾节点的最小值
     * 头插法，每次将新节点放在链表头
     * 时间复杂度O(1)，空间复杂度O(n)
     */
    static class MinStack2 {
        //头结点
        private Node head;

        public MinStack2() {
            head = new Node();
            head.min = Integer.MAX_VALUE;
        }

        public void push(int value) {
            Node node = new Node();
            node.value = value;
            node.min = Math.min(value, head.min);
            node.next = head;
            head = node;
        }

        public void pop() {
            if (head.next == null) {
                return;
            }

            Node node = head;
            head = head.next;
            node.next = null;
        }

        public int top() {
            if (head.next == null) {
                return -1;
            }

            return head.value;
        }

        public int getMin() {
            if (head.next == null) {
                return -1;
            }

            return head.min;
        }

        private static class Node {
            //当前节点值
            private int value;
            //当前节点和之后节点的最小值
            private int min;
            private Node next;
        }
    }

    /**
     * 只使用一个栈，不使用额外的栈存储当前栈的最小元素
     * 时间复杂度O(1)，空间复杂度O(1)
     */
    static class MinStack3 {
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
