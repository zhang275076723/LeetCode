package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/11/12 09:36
 * @Author zsy
 * @Description 最长回文串 回文类比Problem5、Problem9、Problem131、Problem132、Problem214、Problem234、Problem516、Problem647
 * 给定一个包含大写字母和小写字母的字符串 s ，返回 通过这些字母构造成的 最长的回文串 。
 * 在构造过程中，请注意 区分大小写 。比如 "Aa" 不能当做一个回文字符串。
 * <p>
 * 输入:s = "abccccdd"
 * 输出:7
 * 解释:
 * 我们可以构造的最长的回文串是"dccaccd", 它的长度是 7。
 * <p>
 * 输入:s = "a"
 * 输入:1
 * <p>
 * 1 <= s.length <= 2000
 * s 只由小写 和/或 大写英文字母组成
 */
public class Problem409 {
    public static void main(String[] args) {
        Problem409 problem409 = new Problem409();
        String s = "abccccdd";
        System.out.println(problem409.longestPalindrome(s));
    }

    /**
     * 哈希表
     * 统计字符串s中每个字符出现的次数，偶数字符出现的次数加上奇数字符出现次数减1，
     * 最后再根据字符是否出现了奇数次，觉得最后是否还需要加1，即得到最长回文串长度，
     * 时间复杂度O(n)，空间复杂度O(1) (最多有128种不同的字符)
     *
     * @param s
     * @return
     */
    public int longestPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        Map<Character, Integer> map = new HashMap<>();

        for (char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        //最长回文串的长度
        int len = 0;
        //字符是否出现了奇数次
        boolean oddSign = false;

        for (int count : map.values()) {
            if (count % 2 == 0) {
                len = len + count;
            } else {
                len = len + count - 1;
                oddSign = true;
            }
        }

        //字符出现了奇数次，则选一个奇数字符作为中间元素，长度要+1
        return oddSign ? len + 1 : len;
    }
}
