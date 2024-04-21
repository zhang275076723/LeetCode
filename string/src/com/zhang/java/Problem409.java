package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/11/12 09:36
 * @Author zsy
 * @Description 最长回文串 类比Problem767 类比Problem266、Problem267、Problem2131、Problem2384、Problem3035 回文类比Problem5、Problem9、Problem125、Problem131、Problem132、Problem214、Problem234、Problem266、Problem267、Problem336、Problem479、Problem516、Problem647、Problem680、Problem866、Problem1147、Problem1177、Problem1312、Problem1328、Problem1332、Problem1400
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
     * 统计字符串s中每个字符出现的次数，
     * 最长回文串长度包括出现次数为偶数的字符，和最多只能有一个出现次数为奇数的字符，其他出现次数为奇数的字符个数减1
     * 时间复杂度O(n)，空间复杂度O(1) (只包含大小写字母，共26*2种情况)
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
        int length = 0;
        //map中字符出现次数为奇数的标志位
        boolean oddFlag = false;

        //遍历每个字符出现的次数
        for (int count : map.values()) {
            //当前字符出现次数为偶数，则都可以统计为回文串
            if (count % 2 == 0) {
                length = length + count;
            } else {
                //当前字符出现次数为奇数，则需要判断是否是第一次出现次数为奇数，如果是则当前字符出现次数都可以统计为回文串，
                //加上当前字符出现次数，如果不是第一次出现，则加上当前字符出现次数减1
                if (oddFlag) {
                    length = length + count - 1;
                } else {
                    length = length + count;
                    //设置已经出现了次数为奇数的字符
                    oddFlag = true;
                }
            }
        }

        return length;
    }
}
