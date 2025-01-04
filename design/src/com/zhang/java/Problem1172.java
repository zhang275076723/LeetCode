package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/10/7 08:52
 * @Author zsy
 * @Description 餐盘栈 延迟删除类比Problem480、Problem855、Problem2034、Problem2349、Problem2353 类比Problem155、Problem225、Problem232、Problem622、Problem641、Problem705、Problem706、Problem707、Problem716、Problem895、Problem1381、Problem1670、Offer9、Offer30、Offer59_2
 * 我们把无限数量 ∞ 的栈排成一行，按从左到右的次序从 0 开始编号。
 * 每个栈的的最大容量 capacity 都相同。
 * 实现一个叫「餐盘」的类 DinnerPlates：
 * DinnerPlates(int capacity) - 给出栈的最大容量 capacity。
 * void push(int val) - 将给出的正整数 val 推入 从左往右第一个 没有满的栈。
 * int pop() - 返回 从右往左第一个 非空栈顶部的值，并将其从栈中删除；如果所有的栈都是空的，请返回 -1。
 * int popAtStack(int index) - 返回编号 index 的栈顶部的值，并将其从栈中删除；如果编号 index 的栈是空的，请返回 -1。
 * <p>
 * 输入：
 * ["DinnerPlates","push","push","push","push","push","popAtStack","push","push","popAtStack","popAtStack","pop","pop","pop","pop","pop"]
 * [[2],[1],[2],[3],[4],[5],[0],[20],[21],[0],[2],[],[],[],[],[]]
 * 输出：
 * [null,null,null,null,null,null,2,null,null,20,21,5,4,3,1,-1]
 * 解释：
 * DinnerPlates D = DinnerPlates(2);  // 初始化，栈最大容量 capacity = 2
 * D.push(1);
 * D.push(2);
 * D.push(3);
 * D.push(4);
 * D.push(5);         // 栈的现状为：    2  4
 * <                                    1  3  5
 * <                                   ﹈ ﹈ ﹈
 * D.popAtStack(0);   // 返回 2。栈的现状为：     4
 * <                                         1  3  5
 * <                                        ﹈ ﹈ ﹈
 * D.push(20);        // 栈的现状为：  20  4
 * <                                  1  3  5
 * <                                 ﹈ ﹈ ﹈
 * D.push(21);        // 栈的现状为：  20  4 21
 * <                                  1  3  5
 * <                                 ﹈ ﹈ ﹈
 * D.popAtStack(0);   // 返回 20。栈的现状为：      4 21
 * <                                           1  3  5
 * <                                          ﹈ ﹈ ﹈
 * D.popAtStack(2);   // 返回 21。栈的现状为：      4
 * <                                           1  3  5
 * <                                          ﹈ ﹈ ﹈
 * D.pop()            // 返回 5。栈的现状为：       4
 * <                                           1  3
 * <                                          ﹈ ﹈
 * D.pop()            // 返回 4。栈的现状为：   1  3
 * <                                         ﹈ ﹈
 * D.pop()            // 返回 3。栈的现状为：   1
 * <                                         ﹈
 * D.pop()            // 返回 1。现在没有栈。
 * D.pop()            // 返回 -1。仍然没有栈。
 * <p>
 * 1 <= capacity <= 20000
 * 1 <= val <= 20000
 * 0 <= index <= 100000
 * 最多会对 push，pop，和 popAtStack 进行 200000 次调用。
 */
public class Problem1172 {
    public static void main(String[] args) {
        // 初始化，栈最大容量 capacity = 2
        DinnerPlates D = new DinnerPlates(2);
        D.push(1);
        D.push(2);
        D.push(3);
        D.push(4);
        // 栈的现状为：    2  4
        //                1  3  5
        //                ﹈ ﹈ ﹈
        D.push(5);
        // 返回 2。栈的现状为：       4
        //                       1  3  5
        //                       ﹈ ﹈ ﹈
        System.out.println(D.popAtStack(0));
        // 栈的现状为：  20 4
        //              1  3  5
        //              ﹈ ﹈ ﹈
        D.push(20);
        // 栈的现状为：  20 4  21
        //              1  3  5
        //              ﹈ ﹈ ﹈
        D.push(21);
        // 返回 20。栈的现状为：   4 21
        //                    1  3  5
        //                    ﹈ ﹈ ﹈
        System.out.println(D.popAtStack(0));
        // 返回 21。栈的现状为：       4
        //                        1  3  5
        //                        ﹈ ﹈ ﹈
        System.out.println(D.popAtStack(2));
        //// 返回 5。栈的现状为：         4
        //                           1  3
        //                           ﹈ ﹈
        System.out.println(D.pop());
        // 返回 4。栈的现状为：    1  3
        //                       ﹈ ﹈
        System.out.println(D.pop());
        // 返回 3。栈的现状为：    1
        //                       ﹈
        System.out.println(D.pop());
        // 返回 1。现在没有栈。
        System.out.println(D.pop());
        // 返回 -1。仍然没有栈。
        System.out.println(D.pop());

//        DinnerPlates D = new DinnerPlates(1);
//        D.push(1);
//        D.push(2);
//        System.out.println(D.popAtStack(1));
//        System.out.println(D.pop());
//        D.push(1);
//        D.push(2);
//        System.out.println(D.pop());
//        System.out.println(D.pop());
    }

    /**
     * 栈+优先队列，小根堆+延迟删除
     */
    static class DinnerPlates {
        //存储栈的list集合
        private final List<Stack<Integer>> stackList;
        //优先队列，小根堆，存储stackList中未满栈的下标索引
        private final PriorityQueue<Integer> priorityQueue;
        //每个栈的最大容量
        private final int capacity;

        public DinnerPlates(int capacity) {
            stackList = new ArrayList<>();
            priorityQueue = new PriorityQueue<>(new Comparator<Integer>() {
                @Override
                public int compare(Integer a, Integer b) {
                    return a - b;
                }
            });
            this.capacity = capacity;
        }

        public void push(int val) {
            //避免小根堆中未满栈不在stackList中的情况，导致stackList.get()空指针异常，无法获取未满栈，则小根堆置空，所有栈都是未满栈
            if (!priorityQueue.isEmpty() && stackList.size() <= priorityQueue.peek()) {
                priorityQueue.clear();
            }

            //小根堆未空，则不存在未满栈，新建一个栈，val入新建栈
            if (priorityQueue.isEmpty()) {
                Stack<Integer> stack = new Stack<>();
                stack.push(val);
                stackList.add(stack);

                //新建栈未满，则加入小根堆中
                if (stack.size() < capacity) {
                    priorityQueue.offer(stackList.size() - 1);
                }

                return;
            }

            //下标索引最小的未满栈
            Stack<Integer> stack = stackList.get(priorityQueue.peek());
            stack.push(val);

            //当前栈已满，则当前栈出堆
            if (stack.size() == capacity) {
                priorityQueue.poll();
            }
        }

        public int pop() {
            //所有栈都未空，则返回-1
            if (stackList.isEmpty()) {
                return -1;
            }

            Stack<Integer> stack = stackList.get(stackList.size() - 1);

            //最右边的栈出栈一个元素后未满，则加入小根堆
            if (stack.size() == capacity) {
                priorityQueue.offer(stackList.size() - 1);
            }

            int result = stack.pop();

            //延迟删除，stackList末尾栈为空，则从stackList中删除
            while (!stackList.isEmpty() && stackList.get(stackList.size() - 1).isEmpty()) {
                stackList.remove(stackList.size() - 1);
            }

            return result;
        }

        public int popAtStack(int index) {
            //index不合法，或者stackList中下标索引未index的栈为空，则返回-1
            if (index < 0 || index >= stackList.size() || stackList.get(index).isEmpty()) {
                return -1;
            }

            Stack<Integer> stack = stackList.get(index);

            //stackList中下标索引未index的栈出栈一个元素后未满，则加入小根堆
            if (stack.size() == capacity) {
                priorityQueue.offer(index);
            }

            int result = stack.pop();

            //延迟删除，stackList末尾栈为空，则从stackList中删除
            while (!stackList.isEmpty() && stackList.get(stackList.size() - 1).isEmpty()) {
                stackList.remove(stackList.size() - 1);
            }

            return result;
        }
    }
}
