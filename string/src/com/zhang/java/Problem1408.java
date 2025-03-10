package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2024/8/1 08:12
 * @Author zsy
 * @Description 数组中的字符串匹配 kmp类比Problem28、Problem214、Problem459、Problem471、Problem686、Problem796、Problem1392
 * 给你一个字符串数组 words ，数组中的每个字符串都可以看作是一个单词。
 * 请你按 任意 顺序返回 words 中是其他单词的子字符串的所有单词。
 * 如果你可以删除 words[j] 最左侧和/或最右侧的若干字符得到 words[i] ，那么字符串 words[i] 就是 words[j] 的一个子字符串。
 * <p>
 * 输入：words = ["mass","as","hero","superhero"]
 * 输出：["as","hero"]
 * 解释："as" 是 "mass" 的子字符串，"hero" 是 "superhero" 的子字符串。
 * ["hero","as"] 也是有效的答案。
 * <p>
 * 输入：words = ["leetcode","et","code"]
 * 输出：["et","code"]
 * 解释："et" 和 "code" 都是 "leetcode" 的子字符串。
 * <p>
 * 输入：words = ["blue","green","bu"]
 * 输出：[]
 * <p>
 * 1 <= words.length <= 100
 * 1 <= words[i].length <= 30
 * words[i] 仅包含小写英文字母。
 * 题目数据 保证 每个 words[i] 都是独一无二的。
 */
public class Problem1408 {
    public static void main(String[] args) {
        Problem1408 problem1408 = new Problem1408();
        String[] words = {"mass", "as", "hero", "superhero"};
        System.out.println(problem1408.stringMatching(words));
    }

    /**
     * kmp
     * 时间复杂度O(n^2*m)，空间复杂度O(m) (n=words.length，m=words[i].length())
     *
     * @param words
     * @return
     */
    public List<String> stringMatching(String[] words) {
        List<String> list = new ArrayList<>();

        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < words.length; j++) {
                //words[i]是words[j]的子字符串，则words[i]加入list中
                //注意kmpSearch中words[i]和words[j]的先后顺序
                if (i != j && kmpSearch(words[j], words[i])) {
                    list.add(words[i]);
                    break;
                }
            }
        }

        return list;
    }

    /**
     * kmp查询模式串p是否是主串s的子串
     * 时间复杂度O(m+n)，空间复杂度O(n) (m=s.length()，n=p.length())
     *
     * @param s
     * @param p
     * @return
     */
    private boolean kmpSearch(String s, String p) {
        int[] next = getNext(p);
        int j = 0;

        for (int i = 0; i < s.length(); i++) {
            while (j > 0 && s.charAt(i) != p.charAt(j)) {
                j = next[j - 1];
            }

            if (s.charAt(i) == p.charAt(j)) {
                j++;
            }

            //j遍历到末尾，则p是s的子串，返回true
            if (j == p.length()) {
                return true;
            }
        }

        //遍历结束，则p不是s的子串，返回false
        return false;
    }

    private int[] getNext(String s) {
        int[] next = new int[s.length()];
        int j = 0;

        //注意：i从1开始遍历，因为s[0]-s[0]不存在公共前缀和后缀
        for (int i = 1; i < s.length(); i++) {
            while (j > 0 && s.charAt(i) != s.charAt(j)) {
                j = next[j - 1];
            }

            if (s.charAt(i) == s.charAt(j)) {
                j++;
            }

            next[i] = j;
        }

        return next;
    }
}
