package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/10/2 08:20
 * @Author zsy
 * @Description 字母大小写全排列 回溯+剪枝类比
 * 给定一个字符串 s ，通过将字符串 s 中的每个字母转变大小写，我们可以获得一个新的字符串。
 * 返回 所有可能得到的字符串集合 。
 * 以 任意顺序 返回输出。
 * <p>
 * 输入：s = "a1b2"
 * 输出：["a1b2", "a1B2", "A1b2", "A1B2"]
 * <p>
 * 输入: s = "3z4"
 * 输出: ["3z4","3Z4"]
 * <p>
 * 1 <= s.length <= 12
 * s 由小写英文字母、大写英文字母和数字组成
 */
public class Problem784 {
    public static void main(String[] args) {
        Problem784 problem784 = new Problem784();
        String s = "a1b2";
        System.out.println(problem784.letterCasePermutation(s));
        System.out.println(problem784.letterCasePermutation2(s));
    }

    /**
     * 回溯+剪枝
     * 时间复杂度O(n*2^n)，空间复杂度O(n) (每个位置有大写和小写2种情况，共2^n种情况，需要O(n)将每种情况添加到结果集合)
     *
     * @param s
     * @return
     */
    public List<String> letterCasePermutation(String s) {
        List<String> list = new ArrayList<>();

        backtrack(0, new StringBuilder(s), list);

        return list;
    }

    /**
     * 模拟
     * 每遍历到s的一个字符，如果是字母就将结果集合list中每个字符串修改当前字母的大小写，作为新的字符串重新添加回结果集合list
     * 时间复杂度O(n*2^n)，空间复杂度O(1) (每个位置有大写和小写2种情况，共2^n种情况，需要O(n)将每种情况添加到结果集合)
     *
     * @param s
     * @return
     */
    public List<String> letterCasePermutation2(String s) {
        List<String> list = new ArrayList<>();
        list.add(s);

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            //修改当前字母小写变大写
            if ('a' <= c && c <= 'z') {
                int size = list.size();

                //list中每个字符串修改当前字母的大小写，作为新的字符串重新添加回结果集合list
                for (int j = 0; j < size; j++) {
                    String s2 = list.get(j);
                    s2 = s2.substring(0, i) + (char) (c - ('a' - 'A')) + s.substring(i + 1, s.length());
                    list.add(s2);
                }
            } else if ('A' <= c && c <= 'Z') {
                int size = list.size();

                //list中每个字符串修改当前字母的大小写，作为新的字符串重新添加回结果集合list
                for (int j = 0; j < size; j++) {
                    String s2 = list.get(j);
                    s2 = s2.substring(0, i) + (char) (c + ('a' - 'A')) + s.substring(i + 1, s.length());
                    list.add(s2);
                }
            }
        }

        return list;
    }

    private void backtrack(int t, StringBuilder sb, List<String> list) {
        if (t == sb.length()) {
            list.add(sb.toString());
            return;
        }

        char c = sb.charAt(t);

        //不修改当前字母的大小写
        backtrack(t + 1, sb, list);

        //修改当前字母小写变大写
        if ('a' <= c && c <= 'z') {
            sb.setCharAt(t, (char) (c - ('a' - 'A')));
            backtrack(t + 1, sb, list);
            sb.setCharAt(t, c);
        } else if ('A' <= c && c <= 'Z') {
            //修改当前字母大写变小写
            sb.setCharAt(t, (char) (c + ('a' - 'A')));
            backtrack(t + 1, sb, list);
            sb.setCharAt(t, c);
        }
    }
}
