package com.zhang.java;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @Date 2022/11/17 10:51
 * @Author zsy
 * @Description 删除字符串中的所有相邻重复项 栈类比Problem20、Problem71、Problem150、Problem224、Problem227、Problem331、Problem341、Problem394、Problem678、Problem856、Problem946、Problem1003、Problem1096、Offer31、CharacterToInteger
 * 给出由小写字母组成的字符串 S，重复项删除操作会选择两个相邻且相同的字母，并删除它们。
 * 在 S 上反复执行重复项删除操作，直到无法继续删除。
 * 在完成所有重复项删除操作后返回最终的字符串。答案保证唯一。
 * <p>
 * 输入："abbaca"
 * 输出："ca"
 * 解释：
 * 例如，在 "abbaca" 中，我们可以删除 "bb" 由于两字母相邻且相同，这是此时唯一可以执行删除操作的重复项。
 * 之后我们得到字符串 "aaca"，其中又只有 "aa" 可以执行重复项删除操作，所以最后的字符串为 "ca"。
 * <p>
 * 1 <= S.length <= 20000
 * S 仅由小写英文字母组成。
 */
public class Problem1047 {
    public static void main(String[] args) {
        Problem1047 problem1047 = new Problem1047();
        String s = "abbaca";
        System.out.println(problem1047.removeDuplicates(s));
    }

    /**
     * 栈
     * 遍历字符串s，当前元素和栈顶元素相等时，即存在相邻重复元素，栈顶元素出队
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public String removeDuplicates(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }

        Deque<Character> stack = new LinkedList<>();

        for (char c : s.toCharArray()) {
            //栈顶元素和当前元素c相等，即存在相邻重复元素，栈顶元素出栈
            if (!stack.isEmpty() && stack.peekLast() == c) {
                stack.pollLast();
            } else {
                //否则，当前元素c入栈
                stack.offerLast(c);
            }
        }

        StringBuilder sb = new StringBuilder();

        while (!stack.isEmpty()) {
            sb.append(stack.pollFirst());
        }

        return sb.toString();
    }
}
