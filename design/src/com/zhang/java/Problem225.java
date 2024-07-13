package com.zhang.java;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2022/10/27 09:01
 * @Author zsy
 * @Description 用队列实现栈 字节面试题 拼多多面试题 类比Problem155、Problem232、Problem622、Problem641、Problem705、Problem706、Problem707、Problem716、Problem1381、Problem1670、Problem2296、Offer9、Offer30、Offer59_2
 * 请你仅使用两个队列实现一个后入先出（LIFO）的栈，并支持普通栈的全部四种操作（push、top、pop 和 empty）。
 * 实现 MyStack 类：
 * void push(int x) 将元素 x 压入栈顶。
 * int pop() 移除并返回栈顶元素。
 * int top() 返回栈顶元素。
 * boolean empty() 如果栈是空的，返回 true ；否则，返回 false 。
 * <p>
 * 注意：
 * 你只能使用队列的基本操作 —— 也就是 push to back、peek/pop from front、size 和 is empty 这些操作。
 * 你所使用的语言也许不支持队列。 你可以使用 list （列表）或者 deque（双端队列）来模拟一个队列 , 只要是标准的队列操作即可。
 * <p>
 * 输入：
 * ["MyStack", "push", "push", "top", "pop", "empty"]
 * [[], [1], [2], [], [], []]
 * 输出：
 * [null, null, null, 2, 2, false]
 * 解释：
 * MyStack myStack = new MyStack();
 * myStack.push(1);
 * myStack.push(2);
 * myStack.top(); // 返回 2
 * myStack.pop(); // 返回 2
 * myStack.empty(); // 返回 False
 * <p>
 * 1 <= x <= 9
 * 最多调用100 次 push、pop、top 和 empty
 * 每次调用 pop 和 top 都保证栈不为空
 */
public class Problem225 {
    public static void main(String[] args) {
//        MyStack myStack = new MyStack();
        MyStack2 myStack = new MyStack2();
        myStack.push(1);
        myStack.push(2);
        System.out.println(myStack.top());
        System.out.println(myStack.pop());
        System.out.println(myStack.empty());
    }

    /**
     * 两个队列实现栈
     * queue2始终为空，每次新元素入queue2中，再将queue1中元素依次出队，入queue2中，在交换queue1和queue2
     */
    static class MyStack {
        private Queue<Integer> queue1;
        private Queue<Integer> queue2;

        public MyStack() {
            queue1 = new LinkedList<>();
            queue2 = new LinkedList<>();
        }

        public void push(int x) {
            queue2.offer(x);

            while (!queue1.isEmpty()) {
                queue2.offer(queue1.poll());
            }

            //交换queue1和queue2，始终保持queue2为空
            Queue<Integer> temp = queue1;
            queue1 = queue2;
            queue2 = temp;
        }

        public int pop() {
            if (queue1.isEmpty()) {
                return -1;
            }

            return queue1.poll();
        }

        public int top() {
            if (queue1.isEmpty()) {
                return -1;
            }

            return queue1.peek();
        }

        public boolean empty() {
            return queue1.isEmpty();
        }
    }

    /**
     * 一个队列实现栈
     * 每次新元素入队之前需要记录当前队列中元素个数，新元素入队之后，再将队列中之前元素依次出队再入队
     */
    static class MyStack2 {
        private final Queue<Integer> queue;

        public MyStack2() {
            queue = new LinkedList<>();
        }

        public void push(int x) {
            //记录当前队列元素个数，重新出队再入队，用于模拟栈
            int size = queue.size();
            queue.offer(x);

            //x入队之前的元素依次出队再入队
            for (int i = 0; i < size; i++) {
                queue.offer(queue.poll());
            }
        }

        public int pop() {
            if (queue.isEmpty()) {
                return -1;
            }

            return queue.poll();
        }

        public int top() {
            if (queue.isEmpty()) {
                return -1;
            }

            return queue.peek();
        }

        public boolean empty() {
            return queue.isEmpty();
        }
    }
}
