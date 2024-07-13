package com.zhang.java;

import java.util.Stack;

/**
 * @Date 2022/3/13 19:38
 * @Author zsy
 * @Description 用两个栈实现一个队列 类比Problem155、Problem225、Problem232、Problem622、Problem641、Problem705、Problem706、Problem707、Problem716、Problem1381、Problem1670、Problem2296、Offer30、Offer59_2 同Problem232
 * 队列的声明如下，请实现它的两个函数 appendTail 和 deleteHead ，
 * 分别完成在队列尾部插入整数和在队列头部删除整数的功能。(若队列中没有元素，deleteHead操作返回 -1 )
 * <p>
 * 输入：
 * ["CQueue","appendTail","deleteHead","deleteHead"]
 * [[],[3],[],[]]
 * 输出：[null,null,3,-1]
 * <p>
 * 输入：
 * ["CQueue","deleteHead","appendTail","appendTail","deleteHead","deleteHead"]
 * [[],[],[5],[2],[],[]]
 * 输出：[null,-1,null,null,5,2]
 * <p>
 * 1 <= values <= 10000
 * 最多会对 appendTail、deleteHead 进行 10000 次调用
 */
public class Offer9 {
    public static void main(String[] args) {
        CQueue queue = new CQueue();
        queue.appendTail(3);
        System.out.println(queue.deleteHead());
        System.out.println(queue.deleteHead());
    }

    /**
     * 使用两个栈，一个出栈，一个入栈
     */
    public static class CQueue {
        //元素入栈存放的栈
        private final Stack<Integer> stack1;
        //stack1中元素出栈存放的栈
        private final Stack<Integer> stack2;

        public CQueue() {
            stack1 = new Stack<>();
            stack2 = new Stack<>();
        }

        /**
         * 入栈
         * 时间复杂度O(1)，空间复杂度O(1)
         *
         * @param value
         */
        public void appendTail(int value) {
            stack1.push(value);
        }

        /**
         * 出栈
         * 先从stack2中出栈，如果stack2为空，则将stack1中元素依次出栈，入栈到stack2中，再从stack2中出栈，如果都为空，返回-1
         * 时间复杂度O(1)，空间复杂度O(1)
         *
         * @return
         */
        public int deleteHead() {
            if (!stack2.isEmpty()) {
                return stack2.pop();
            }

            if (!stack1.isEmpty()) {
                while (!stack1.isEmpty()) {
                    stack2.push(stack1.pop());
                }
                return stack2.pop();
            }

            return -1;
        }
    }
}