package com.zhang.java;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @Date 2022/3/21 15:34
 * @Author zsy
 * @Description 输入两个整数序列，第一个序列表示栈的压入顺序，请判断第二个序列是否为该栈的弹出顺序。
 * 假设压入栈的所有数字均不相等。
 * 例如，序列 {1,2,3,4,5} 是某栈的压栈序列，序列 {4,5,3,2,1} 是该压栈序列对应的一个弹出序列，但 {4,3,5,1,2} 就不可能是该压栈序列的弹出序列。
 * <p>
 * 输入：pushed = [1,2,3,4,5], popped = [4,5,3,2,1]
 * 输出：true
 * 解释：我们可以按以下顺序执行：
 * push(1), push(2), push(3), push(4), pop() -> 4,
 * push(5), pop() -> 5, pop() -> 3, pop() -> 2, pop() -> 1
 * <p>
 * 输入：pushed = [1,2,3,4,5], popped = [4,3,5,1,2]
 * 输出：false
 * 解释：1 不能在 2 之前弹出。
 */
public class Offer31 {
    public static void main(String[] args) {
        Offer31 offer31 = new Offer31();
        int[] pushed = {1, 2, 3, 4, 5};
        int[] popped = {4, 5, 3, 2, 1};
        System.out.println(offer31.validateStackSequences(pushed, popped));
    }

    public boolean validateStackSequences(int[] pushed, int[] popped) {
        if ((pushed == null && popped == null) || (pushed.length == 0 && popped.length == 0)) {
            return true;
        }
        if (pushed.length != popped.length) {
            return false;
        }

        Stack<Integer> stack = new Stack<>();
        int pushedIndex = 0;
        int poppedIndex = 0;

        while (pushedIndex != pushed.length && poppedIndex != popped.length) {
            if (pushed[pushedIndex] == popped[poppedIndex]) {
                pushedIndex++;
                poppedIndex++;
            } else if (stack.empty() || stack.peek() != popped[poppedIndex]) {
                stack.push(pushed[pushedIndex]);
                pushedIndex++;
            } else {
                stack.pop();
                poppedIndex++;
            }
        }

        while (poppedIndex != popped.length) {
            if (stack.peek() == popped[poppedIndex]) {
                stack.pop();
                poppedIndex++;
            } else {
                return false;
            }
        }

        return true;
    }
}
