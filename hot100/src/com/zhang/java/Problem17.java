package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/4/15 9:42
 * @Author zsy
 * @Description 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。
 * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
 * 输入：digits = "23"
 * 输出：["ad","ae","af","bd","be","bf","cd","ce","cf"]
 * <p>
 * 输入：digits = ""
 * 输出：[]
 * <p>
 * 输入：digits = "2"
 * 输出：["a","b","c"]
 * <p>
 * 0 <= digits.length <= 4
 * digits[i] 是范围 ['2', '9'] 的一个数字。
 */
public class Problem17 {
    public static void main(String[] args) {
        Problem17 problem17 = new Problem17();
        String digits = "23";
        System.out.println(problem17.letterCombinations(digits));
    }

    /**
     * 回溯，时间复杂度O(3^n)-O(4^n)，空间复杂度O(n)
     *
     * @param digits
     * @return
     */
    public List<String> letterCombinations(String digits) {
        if (digits == null || digits.length() == 0) {
            return new ArrayList<>();
        }

        //数字对应的字母
        String[] numString = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        List<String> result = new ArrayList<>();

        backtrack(0, digits, new StringBuilder(), result, numString);

        return result;
    }

    /**
     * @param t         当前第几个数字
     * @param digits    数字字符串
     * @param sb        结果拼接字符串
     * @param result    返回结果集
     * @param numString 数字对应字符串
     */
    public void backtrack(int t, String digits, StringBuilder sb, List<String> result, String[] numString) {
        if (t == digits.length()) {
            result.add(sb.toString());
            return;
        }

        String str = numString[digits.charAt(t) - '0'];
        for (int i = 0; i < str.length(); i++) {
            sb.append(str.charAt(i));
            backtrack(t + 1, digits, sb, result, numString);
            sb.delete(sb.length() - 1, sb.length());
        }
    }
}
