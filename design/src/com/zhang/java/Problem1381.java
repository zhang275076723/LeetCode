package com.zhang.java;

/**
 * @Date 2024/9/6 09:02
 * @Author zsy
 * @Description 设计一个支持增量操作的栈 星环科技面试题 类比Problem155、Problem225、Problem232、Problem622、Problem641、Problem705、Problem706、Problem707、Problem716、Problem895、Problem1172、Problem1670、Offer9、Offer30、Offer59_2
 * 请你设计一个支持对其元素进行增量操作的栈。
 * 实现自定义栈类 CustomStack ：
 * CustomStack(int maxSize)：用 maxSize 初始化对象，maxSize 是栈中最多能容纳的元素数量。
 * void push(int x)：如果栈还未增长到 maxSize ，就将 x 添加到栈顶。
 * int pop()：弹出栈顶元素，并返回栈顶的值，或栈为空时返回 -1 。
 * void inc(int k, int val)：栈底的 k 个元素的值都增加 val 。如果栈中元素总数小于 k ，则栈中的所有元素都增加 val 。
 * <p>
 * 输入：
 * ["CustomStack","push","push","pop","push","push","push","increment","increment","pop","pop","pop","pop"]
 * [[3],[1],[2],[],[2],[3],[4],[5,100],[2,100],[],[],[],[]]
 * 输出：
 * [null,null,null,2,null,null,null,null,null,103,202,201,-1]
 * 解释：
 * CustomStack stk = new CustomStack(3); // 栈是空的 []
 * stk.push(1);                          // 栈变为 [1]
 * stk.push(2);                          // 栈变为 [1, 2]
 * stk.pop();                            // 返回 2 --> 返回栈顶值 2，栈变为 [1]
 * stk.push(2);                          // 栈变为 [1, 2]
 * stk.push(3);                          // 栈变为 [1, 2, 3]
 * stk.push(4);                          // 栈仍然是 [1, 2, 3]，不能添加其他元素使栈大小变为 4
 * stk.increment(5, 100);                // 栈变为 [101, 102, 103]
 * stk.increment(2, 100);                // 栈变为 [201, 202, 103]
 * stk.pop();                            // 返回 103 --> 返回栈顶值 103，栈变为 [201, 202]
 * stk.pop();                            // 返回 202 --> 返回栈顶值 202，栈变为 [201]
 * stk.pop();                            // 返回 201 --> 返回栈顶值 201，栈变为 []
 * stk.pop();                            // 返回 -1 --> 栈为空，返回 -1
 * <p>
 * 1 <= maxSize, x, k <= 1000
 * 0 <= val <= 100
 * 每种方法 increment，push 以及 pop 分别最多调用 1000 次
 */
public class Problem1381 {
    public static void main(String[] args) {
        // 栈是空的 []
        CustomStack stk = new CustomStack(3);
        // 栈变为 [1]
        stk.push(1);
        // 栈变为 [1, 2]
        stk.push(2);
        // 返回 2 --> 返回栈顶值 2，栈变为 [1]
        System.out.println(stk.pop());
        // 栈变为 [1, 2]
        stk.push(2);
        // 栈变为 [1, 2, 3]
        stk.push(3);
        // 栈仍然是 [1, 2, 3]，不能添加其他元素使栈大小变为 4
        stk.push(4);
        // 栈变为 [101, 102, 103]
        stk.increment(5, 100);
        // 栈变为 [201, 202, 103]
        stk.increment(2, 100);
        // 返回 103 --> 返回栈顶值 103，栈变为 [201, 202]
        System.out.println(stk.pop());
        // 返回 202 --> 返回栈顶值 202，栈变为 [201]
        System.out.println(stk.pop());
        // 返回 201 --> 返回栈顶值 201，栈变为 []
        System.out.println(stk.pop());
        // 返回 -1 --> 栈为空，返回 -1
        System.out.println(stk.pop());
    }

    /**
     * 数组模拟栈+差分思想
     * 时间复杂度O(1)，空间复杂度O(maxSize)
     */
    static class CustomStack {
        //栈数组
        private final int[] stack;
        //addArr[i]：stack[0]-stack[i]需要添加的值
        private final int[] addArr;
        //栈顶指针
        private int top;

        public CustomStack(int maxSize) {
            stack = new int[maxSize];
            addArr = new int[maxSize];
            top = 0;
        }

        public void push(int x) {
            //栈满，则x无法入栈
            if (top == stack.length) {
                return;
            }

            stack[top] = x;
            top++;
        }

        public int pop() {
            //栈空，则栈顶元素无法出栈，直接返回-1
            if (top == 0) {
                return -1;
            }

            top--;
            int result = stack[top] + addArr[top];

            //栈顶元素出栈，更新addArr[top-1]
            if (top - 1 >= 0) {
                addArr[top - 1] = addArr[top - 1] + addArr[top];
            }

            //栈顶元素出栈，addArr[top]已经添加到addArr[top-1]中，addArr[top]赋值为0，用于之后调用increment()时更新
            addArr[top] = 0;

            return result;
        }

        public void increment(int k, int val) {
            //栈空，则栈中元素不需要加val，直接返回
            if (top == 0) {
                return;
            }

            //k大于栈中元素，则栈中所有元素都加val
            if (k > top) {
                addArr[top - 1] = addArr[top - 1] + val;
            } else {
                //栈底k个元素加val
                addArr[k - 1] = addArr[k - 1] + val;
            }
        }
    }
}
