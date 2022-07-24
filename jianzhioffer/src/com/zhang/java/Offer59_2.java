package com.zhang.java;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2022/4/6 11:17
 * @Author zsy
 * @Description 队列的最大值 类比Problem155、Problem232、Offer9、Offer30
 * 请定义一个队列并实现函数 max_value 得到队列里的最大值，
 * 要求函数 max_value、push_back 和 pop_front 的均摊时间复杂度都是O(1)。
 * 若队列为空，pop_front 和 max_value 需要返回 -1
 * <p>
 * 输入:
 * ["MaxQueue","push_back","push_back","max_value","pop_front","max_value"]
 * [[],[1],[2],[],[],[]]
 * 输出: [null,null,null,2,1,2]
 * <p>
 * 输入:
 * ["MaxQueue","pop_front","max_value"]
 * [[],[],[]]
 * 输出: [null,-1,-1]
 * <p>
 * 1 <= push_back,pop_front,max_value的总操作数 <= 10000
 * 1 <= value <= 10^5
 */
public class Offer59_2 {
    public static void main(String[] args) {
        MaxQueue maxQueue = new MaxQueue();
        maxQueue.push_back(1);
        maxQueue.push_back(2);
        System.out.println(maxQueue.max_value());
        System.out.println(maxQueue.pop_front());
        System.out.println(maxQueue.max_value());
    }

    /**
     * 使用两个队列，一个队列存放元素，另一个队列在队首存放当前队列的最大值元素，作为单调递减栈
     * 均摊时间复杂度O(1)，空间复杂度O(n)
     */
    public static class MaxQueue {
        //存放元素的队列
        private Queue<Integer> queue;

        //存放当前队列的最大值元素，作为单调递减栈
        private Deque<Integer> maxQueue;

        public MaxQueue() {
            queue = new LinkedList<>();
            maxQueue = new LinkedList<>();
        }

        public int max_value() {
            return maxQueue.isEmpty() ? -1 : maxQueue.peekFirst();
        }

        public void push_back(int value) {
            queue.offer(value);

            //不满足单调递减队列，则出队
            while (!maxQueue.isEmpty() && value > maxQueue.peekLast()) {
                maxQueue.pollLast();
            }

            maxQueue.offerLast(value);
        }

        public int pop_front() {
            if (!queue.isEmpty()) {
                int value = queue.poll();

                if (value == maxQueue.peekFirst()) {
                    maxQueue.pollFirst();
                }

                return value;
            }

            return -1;
        }
    }
}
