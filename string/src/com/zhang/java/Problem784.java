package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/10/2 08:20
 * @Author zsy
 * @Description 字母大小写全排列 类比Problem77、Problem216 全排列类比Problem46、Problem47、Problem60 回溯+剪枝类比
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
        System.out.println(problem784.letterCasePermutation3(s));
    }

    /**
     * 回溯+剪枝1
     * 时间复杂度O(n*2^n)，空间复杂度O(n) (每个位置有大写和小写2种情况，共2^n种情况，需要O(n)将每种情况添加到结果集合)
     *
     * @param s
     * @return
     */
    public List<String> letterCasePermutation(String s) {
        List<String> list = new ArrayList<>();

        backtrack(0, s, new StringBuilder(), list);

        return list;
    }

    /**
     * 回溯+剪枝2
     * 时间复杂度O(n*2^n)，空间复杂度O(n) (每个位置有大写和小写2种情况，共2^n种情况，需要O(n)将每种情况添加到结果集合)
     *
     * @param s
     * @return
     */
    public List<String> letterCasePermutation2(String s) {
        List<String> list = new ArrayList<>();

        backtrack2(0, s, new StringBuilder(s), list);

        return list;
    }

    /**
     * 二进制状态压缩
     * s的长度最大为12，使用长度为n的int整数的每一位表示s中字母的大小写，当前位为1，则当前字母转变为大写；当前位为0，则当前字母转变为小写
     * 时间复杂度O(n*2^n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public List<String> letterCasePermutation3(String s) {
        List<String> list = new ArrayList<>();
        //s中字母的个数
        int count = 0;

        for (char c : s.toCharArray()) {
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                count++;
            }
        }

        //s的长度最大为12，使用长度为n的int整数的每一位表示s中字母的大小写
        for (int i = 0; i < (1 << count); i++) {
            StringBuilder sb = new StringBuilder();
            //二进制状态i的下标索引
            int index = 0;

            for (int j = 0; j < s.length(); j++) {
                char c = s.charAt(j);

                //当前位为数字，直接拼接
                if (c >= '0' && c <= '9') {
                    sb.append(c);
                } else {
                    //二进制状态i中index位置为1，表示当前字母转变为大写
                    if (((i >>> index) & 1) == 1) {
                        if (c >= 'a' && c <= 'z') {
                            sb.append((char) (c - ('a' - 'A')));
                        } else {
                            sb.append(c);
                        }
                    } else {
                        //二进制状态i中index位置为0，表示当前字母转变为小写
                        if (c >= 'A' && c <= 'Z') {
                            sb.append((char) (c + ('a' - 'A')));
                        } else {
                            sb.append(c);
                        }
                    }
                }
            }

            list.add(sb.toString());
        }

        return list;
    }

    private void backtrack(int t, String s, StringBuilder sb, List<String> list) {
        //s遍历结束，才在list中添加sb
        if (t == sb.length()) {
            list.add(sb.toString());
            return;
        }

        char c = s.charAt(t);

        //不修改当前字母的大小写
        sb.append(c);
        backtrack(t + 1, s, sb, list);
        sb.delete(sb.length() - 1, sb.length());

        //修改当前字母小写变大写
        if (c >= 'a' && c <= 'z') {
            sb.append((char) (c - ('a' - 'A')));
            backtrack(t + 1, s, sb, list);
            sb.delete(sb.length() - 1, sb.length());
        } else if (c >= 'A' && c <= 'Z') {
            //修改当前字母大写变小写
            sb.append((char) (c + ('a' - 'A')));
            backtrack(t + 1, s, sb, list);
            sb.delete(sb.length() - 1, sb.length());
        }
    }

    private void backtrack2(int t, String s, StringBuilder sb, List<String> list) {
        //s遍历过程中，就在list中添加sb
        list.add(sb.toString());

        if (t == s.length()) {
            return;
        }

        for (int i = t; i < s.length(); i++) {
            char c = s.charAt(i);

            //修改当前字母小写变大写
            if (c >= 'a' && c <= 'z') {
                sb.setCharAt(i + 1, (char) (c - ('a' - 'A')));
                backtrack2(i + 1, s, sb, list);
                sb.setCharAt(i, c);
            } else if (c >= 'A' && c <= 'Z') {
                //修改当前字母大写变小写
                sb.setCharAt(i, (char) (c + ('a' - 'A')));
                backtrack2(i + 1, s, sb, list);
                sb.setCharAt(i, c);
            }
        }
    }
}
