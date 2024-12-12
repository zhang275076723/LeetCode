package com.zhang.java;

/**
 * @Date 2022/10/31 08:40
 * @Author zsy
 * @Description 找出字符串中第一个匹配项的下标 kmp类比Problem214、Problem459、Problem471、Problem686、Problem796、Problem1408
 * 给你两个字符串 haystack 和 needle ，请你在 haystack 字符串中找出 needle 字符串的第一个匹配项的下标（下标从 0 开始）。
 * 如果needle 不是 haystack 的一部分，则返回 -1 。
 * <p>
 * 输入：haystack = "sadbutsad", needle = "sad"
 * 输出：0
 * 解释："sad" 在下标 0 和 6 处匹配。
 * 第一个匹配项的下标是 0 ，所以返回 0 。
 * <p>
 * 输入：haystack = "leetcode", needle = "leeto"
 * 输出：-1
 * 解释："leeto" 没有在 "leetcode" 中出现，所以返回 -1 。
 * <p>
 * 1 <= haystack.length, needle.length <= 10^4
 * haystack 和 needle 仅由小写英文字符组成
 */
public class Problem28 {
    public static void main(String[] args) {
        Problem28 problem28 = new Problem28();
        String haystack = "leetcode";
        String needle = "leeto";
        System.out.println(problem28.strStr(haystack, needle));
    }

    /**
     * kmp
     * 主串指针不回退，模式串指针在不匹配时根据next数组回退
     * 时间复杂度O(m+n)，空间复杂度O(n) (m=haystack.length()，n=needle.length())
     *
     * @param haystack
     * @param needle
     * @return
     */
    public int strStr(String haystack, String needle) {
        if (needle == null || needle.length() == 0) {
            return -1;
        }

        //next数组
        int[] next = getNext(needle);
        //模式串指针
        int j = 0;

        for (int i = 0; i < haystack.length(); i++) {
            //当前字符不匹配，j指针通过next数组前移
            while (j > 0 && haystack.charAt(i) != needle.charAt(j)) {
                j = next[j - 1];
            }

            //当前字符匹配，j指针后移
            if (haystack.charAt(i) == needle.charAt(j)) {
                j++;
            }

            //j遍历到末尾，则匹配，返回主串匹配的第一个字符下标索引
            if (j == needle.length()) {
                return i - j + 1;
            }
        }

        //没有匹配，返回-1
        return -1;
    }

    /**
     * 获取字符串s的next数组
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param s
     * @return
     */
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
