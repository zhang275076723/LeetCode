package com.zhang.java;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @Date 2024/10/6 08:27
 * @Author zsy
 * @Description 最大频率栈 阿里面试题 字节面试题 快手面试题 华为面试题 类比Problem146、Problem432、Problem460、Problem1797 类比Problem155、Problem225、Problem232、Problem622、Problem641、Problem705、Problem706、Problem707、Problem716、Problem1172、Problem1381、Problem1670、Offer9、Offer30、Offer59_2
 * 设计一个类似堆栈的数据结构，将元素推入堆栈，并从堆栈中弹出出现频率最高的元素。
 * 实现 FreqStack 类:
 * FreqStack() 构造一个空的堆栈。
 * void push(int val) 将一个整数 val 压入栈顶。
 * int pop() 删除并返回堆栈中出现频率最高的元素。
 * 如果出现频率最高的元素不只一个，则移除并返回最接近栈顶的元素。
 * <p>
 * 输入：
 * ["FreqStack","push","push","push","push","push","push","pop","pop","pop","pop"],
 * [[],[5],[7],[5],[7],[4],[5],[],[],[],[]]
 * 输出：[null,null,null,null,null,null,null,5,7,5,4]
 * 解释：
 * FreqStack = new FreqStack();
 * freqStack.push (5);//堆栈为 [5]
 * freqStack.push (7);//堆栈是 [5,7]
 * freqStack.push (5);//堆栈是 [5,7,5]
 * freqStack.push (7);//堆栈是 [5,7,5,7]
 * freqStack.push (4);//堆栈是 [5,7,5,7,4]
 * freqStack.push (5);//堆栈是 [5,7,5,7,4,5]
 * freqStack.pop ();//返回 5 ，因为 5 出现频率最高。堆栈变成 [5,7,5,7,4]。
 * freqStack.pop ();//返回 7 ，因为 5 和 7 出现频率最高，但7最接近顶部。堆栈变成 [5,7,5,4]。
 * freqStack.pop ();//返回 5 ，因为 5 出现频率最高。堆栈变成 [5,7,4]。
 * freqStack.pop ();//返回 4 ，因为 4, 5 和 7 出现频率最高，但 4 是最接近顶部的。堆栈变成 [5,7]。
 * <p>
 * 0 <= val <= 10^9
 * push 和 pop 的操作数不大于 2 * 10^4。
 * 输入保证在调用 pop 之前堆栈中至少有一个元素。
 */
public class Problem895 {
    public static void main(String[] args) {
        FreqStack freqStack = new FreqStack();
        //堆栈为 [5]
        freqStack.push(5);
        //堆栈是 [5,7]
        freqStack.push(7);
        //堆栈是 [5,7,5]
        freqStack.push(5);
        //堆栈是 [5,7,5,7]
        freqStack.push(7);
        //堆栈是 [5,7,5,7,4]
        freqStack.push(4);
        //堆栈是 [5,7,5,7,4,5]
        freqStack.push(5);
        //返回 5 ，因为 5 出现频率最高。堆栈变成 [5,7,5,7,4]。
        System.out.println(freqStack.pop());
        //返回 7 ，因为 5 和 7 出现频率最高，但7最接近顶部。堆栈变成 [5,7,5,4]。
        System.out.println(freqStack.pop());
        //返回 5 ，因为 5 出现频率最高。堆栈变成 [5,7,4]。
        System.out.println(freqStack.pop());
        //返回 4 ，因为 4, 5 和 7 出现频率最高，但 4 是最接近顶部的。堆栈变成 [5,7]。
        System.out.println(freqStack.pop());
    }

    /**
     * 哈希表+分段频率栈+最大频率次数计数器 (lfu)
     * 根据频率将元素放到不同的栈中，每次出栈最大频率对应的栈中的栈顶元素即为出现频率最高且最接近栈顶的元素
     */
    static class FreqStack {
        //key：节点值，value：key出现的频率次数
        private final Map<Integer, Integer> frequencyMap;
        //key：频率次数，value：频率次数为key的栈
        private final Map<Integer, Stack<Integer>> stackMap;
        //元素的最大频率次数
        private int maxFrequency;

        public FreqStack() {
            frequencyMap = new HashMap<>();
            stackMap = new HashMap<>();
            maxFrequency = 0;
        }

        public void push(int val) {
            //更新val的出现次数
            frequencyMap.put(val, frequencyMap.getOrDefault(val, 0) + 1);

            //不存在出现次数为frequencyMap.get(val)的栈
            if (!stackMap.containsKey(frequencyMap.get(val))) {
                stackMap.put(frequencyMap.get(val), new Stack<>());
            }

            //val加入出现次数为frequencyMap.get(val)的栈
            stackMap.get(frequencyMap.get(val)).push(val);
            //更新maxFrequency
            maxFrequency = Math.max(maxFrequency, frequencyMap.get(val));
        }

        public int pop() {
            //栈中没有元素，返回-1
            if (maxFrequency == 0) {
                return -1;
            }

            //最大频率对应的栈
            Stack<Integer> stack = stackMap.get(maxFrequency);
            //每次出栈最大频率对应的栈中的栈顶元素即为出现频率最高且最接近栈顶的元素
            int result = stack.pop();
            //更新result的出现次数
            frequencyMap.put(result, frequencyMap.get(result) - 1);

            //result的出现次数为0，则从frequencyMap中删除
            if (frequencyMap.get(result) == 0) {
                frequencyMap.remove(result);
            }

            //最大频率对应的栈为空，则更新maxFrequency
            if (stackMap.get(maxFrequency).isEmpty()) {
                stackMap.remove(maxFrequency);
                maxFrequency--;
            }

            return result;
        }
    }
}
