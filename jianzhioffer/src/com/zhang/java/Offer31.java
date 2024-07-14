package com.zhang.java;

import java.util.Stack;

/**
 * @Date 2022/3/21 15:34
 * @Author zsy
 * @Description 栈的压入、弹出序列 类比Problem1441 栈类比Problem20、Problem71、Problem150、Problem224、Problem227、Problem331、Problem341、Problem394、Problem678、Problem856、Problem946、Problem1003、Problem1047、Problem1096、CharacterToInteger 同Problem946
 * 输入两个整数序列，第一个序列表示栈的压入顺序，请判断第二个序列是否为该栈的弹出顺序。
 * 假设压入栈的所有数字均不相等。
 * 例如，序列 {1,2,3,4,5} 是某栈的压栈序列，序列 {4,5,3,2,1} 是该压栈序列对应的一个弹出序列，
 * 但 {4,3,5,1,2} 就不可能是该压栈序列的弹出序列。
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
 * <p>
 * 0 <= pushed.length == popped.length <= 1000
 * 0 <= pushed[i], popped[i] < 1000
 * pushed 是 popped 的排列。
 */
public class Offer31 {
    public static void main(String[] args) {
        Offer31 offer31 = new Offer31();
        int[] pushed = {1, 2, 3, 4, 5};
        int[] popped = {4, 5, 3, 2, 1};
        System.out.println(offer31.validateStackSequences(pushed, popped));
    }

    /**
     * 栈
     * 每次入栈pushed[i]，判断栈顶元素是否和popped[j]相同，如果相同，则栈顶元素出栈，
     * 遍历结束，如果栈为空，或者j指向popped数组末尾，则popped是pushed的出栈顺序
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param pushed
     * @param popped
     * @return
     */
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        Stack<Integer> stack = new Stack<>();
        //popped数组指针
        int j = 0;

        for (int i = 0; i < pushed.length; i++) {
            stack.push(pushed[i]);

            //栈顶元素和popped[j]相等，则说明入栈和出栈顺序匹配，栈顶元素出栈，popped指针j后移
            while (!stack.isEmpty() && stack.peek() == popped[j]) {
                stack.pop();
                j++;
            }
        }

//        //stack为空，则说明全部匹配，返回true
//        return stack.isEmpty();

        //j指向popped数组末尾，则说明全部匹配，返回true
        return j == popped.length;
    }
}
