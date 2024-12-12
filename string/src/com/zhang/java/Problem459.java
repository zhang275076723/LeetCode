package com.zhang.java;

/**
 * @Date 2023/5/4 08:49
 * @Author zsy
 * @Description 重复的子字符串 类比Problem471 类比Problem1071 kmp类比Problem28、Problem214、Problem471、Problem686、Problem796、Problem1408
 * 给定一个非空的字符串 s ，检查是否可以通过由它的一个子串重复多次构成。
 * <p>
 * 输入: s = "abab"
 * 输出: true
 * 解释: 可由子串 "ab" 重复两次构成。
 * <p>
 * 输入: s = "aba"
 * 输出: false
 * <p>
 * 输入: s = "abcabcabcabc"
 * 输出: true
 * 解释: 可由子串 "abc" 重复四次构成。 (或子串 "abcabc" 重复两次构成。)
 * <p>
 * 1 <= s.length <= 10^4
 * s 由小写英文字母组成
 */
public class Problem459 {
    public static void main(String[] args) {
        Problem459 problem459 = new Problem459();
//        String s = "aba";
//        String s = "abcabcabcabc";
        String s = "aabaaba";
        System.out.println(problem459.repeatedSubstringPattern(s));
        System.out.println(problem459.repeatedSubstringPattern2(s));
        System.out.println(problem459.repeatedSubstringPattern3(s));
    }

    /**
     * 暴力
     * 如果s由子串s[0]-s[i]重复多次构成，则s[j] = s[j%(i+1)] (i < j < s.length())
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param s
     * @return
     */
    public boolean repeatedSubstringPattern(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }

        //子串s[0]-s[i]重复多次构成s，子串s[0]-s[i]至少重复1次，所以子串长度不能超过s的一半
        for (int i = 0; i < s.length() / 2; i++) {
            //s的长度不能整除子串s[0]-s[i]的长度，则子串s[0]-s[i]重复多次不能构成s，直接进行下次循环
            if (s.length() % (i + 1) != 0) {
                continue;
            }

            //子串s[0]-s[i]重复多次能否构成s的标志位
            boolean flag = true;

            for (int j = i + 1; j < s.length(); j++) {
                //s[j]不等于s[j%(i+1)]，则子串s[0]-s[i]重复多次不能构成s
                if (s.charAt(j) != s.charAt(j % (i + 1))) {
                    flag = false;
                    break;
                }
            }

            //子串s[0]-s[i]重复多次能构成s，返回true
            if (flag) {
                return true;
            }
        }

        //遍历结束都没有能构成s的子串，返回false
        return false;
    }

    /**
     * 模拟
     * 如果s由其子串重复多次构成，并且假设构成s的子串为x，则s可以写成xx...xx的形式，s拼接s，移除首尾字符得到的新字符串str，
     * 如果str中存在s，则s由重复的子字符串x构成，假设str中s第一次出现的下标索引为index，则重复的子字符串x为s[0]-s[index]
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public boolean repeatedSubstringPattern2(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }

        //s拼接s，再去除首尾字符，得到的新字符串str，如果str中存在s，则s由重复的子字符串构成
        String str = (s + s).substring(1, (s + s).length() - 1);

        return str.contains(s);
    }

    /**
     * kmp
     * 如果s由其子串重复多次构成，并且假设构成s的子串为x，则s可以写成xx...xx的形式，s拼接s，移除首尾字符得到的新字符串str，
     * 如果str中存在s，则s由重复的子字符串x构成，假设str中s第一次出现的下标索引为index，则重复的子字符串x为s[0]-s[index]
     * kmp判断str中是否包含s
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public boolean repeatedSubstringPattern3(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }

        //s拼接s，再去除首尾字符，得到的新字符串str，如果str中存在s，则s由重复的子字符串构成
        String str = (s + s).substring(1, (s + s).length() - 1);
        //next数组
        int[] next = getNext(s);
        //模式串指针
        int j = 0;

        for (int i = 0; i < str.length(); i++) {
            //当前字符不匹配，j指针通过next数组前移
            while (j > 0 && str.charAt(i) != s.charAt(j)) {
                j = next[j - 1];
            }

            //当前字符匹配，j指针后移
            if (str.charAt(i) == s.charAt(j)) {
                j++;
            }

            //str中包含s，则s由重复的子字符串构成，返回true
            if (j == s.length()) {
                return true;
            }
        }

        //str中不包含s，则s不能由重复的子字符串构成，返回false
        return false;
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
