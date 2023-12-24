package com.zhang.java;

import java.util.Stack;

/**
 * @Date 2023/12/24 08:24
 * @Author zsy
 * @Description 验证栈序列 美团机试题 栈类比Problem20、Problem71、Problem150、Problem224、Problem227、Problem331、Problem341、Problem394、Problem678、Problem856、Problem1003、Problem1047、Problem1096、Offer31、CharacterToInteger 同Offer31
 * 给定 pushed 和 popped 两个序列，每个序列中的 值都不重复，
 * 只有当它们可能是在最初空栈上进行的推入 push 和弹出 pop 操作序列的结果时，返回 true；否则，返回 false 。
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
 * 1 <= pushed.length <= 1000
 * 0 <= pushed[i] <= 1000
 * pushed 的所有元素 互不相同
 * popped.length == pushed.length
 * popped 是 pushed 的一个排列
 */
public class Problem946 {
    public static void main(String[] args) {
        Problem946 problem946 = new Problem946();
//        int[] pushed = {1, 2, 3, 4, 5};
//        int[] popped = {4, 5, 3, 2, 1};
        int[] pushed = {1, 2, 3, 4, 5};
        int[] popped = {4, 3, 5, 1, 2};
        System.out.println(problem946.validateStackSequences(pushed, popped));
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
