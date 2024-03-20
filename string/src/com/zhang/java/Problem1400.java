package com.zhang.java;

/**
 * @Date 2024/3/13 08:28
 * @Author zsy
 * @Description 构造 K 个回文字符串 回文类比Problem5、Problem9、Problem125、Problem131、Problem132、Problem214、Problem234、Problem266、Problem267、Problem336、Problem409、Problem479、Problem516、Problem647、Problem680、Problem866、Problem1147、Problem1177、Problem1312、Problem1328、Problem1332
 * 给你一个字符串 s 和一个整数 k 。
 * 请你用 s 字符串中 所有字符 构造 k 个非空 回文串 。
 * 如果你可以用 s 中所有字符构造 k 个回文字符串，那么请你返回 True ，否则返回 False 。
 * <p>
 * 输入：s = "annabelle", k = 2
 * 输出：true
 * 解释：可以用 s 中所有字符构造 2 个回文字符串。
 * 一些可行的构造方案包括："anna" + "elble"，"anbna" + "elle"，"anellena" + "b"
 * <p>
 * 输入：s = "leetcode", k = 3
 * 输出：false
 * 解释：无法用 s 中所有字符构造 3 个回文串。
 * <p>
 * 输入：s = "true", k = 4
 * 输出：true
 * 解释：唯一可行的方案是让 s 中每个字符单独构成一个字符串。
 * <p>
 * 输入：s = "yzyzyzyzyzyzyzy", k = 2
 * 输出：true
 * 解释：你只需要将所有的 z 放在一个字符串中，所有的 y 放在另一个字符串中。那么两个字符串都是回文串。
 * <p>
 * 输入：s = "cr", k = 7
 * 输出：false
 * 解释：我们没有足够的字符去构造 7 个回文串。
 * <p>
 * 1 <= s.length <= 10^5
 * s 中所有字符都是小写英文字母
 * 1 <= k <= 10^5
 */
public class Problem1400 {
    public static void main(String[] args) {
        Problem1400 problem1400 = new Problem1400();
        String s = "leetcode";
        int k = 3;
        System.out.println(problem1400.canConstruct(s, k));
    }

    /**
     * 模拟
     * 统计s中每个字符出现的次数，如果出现次数为奇数的字符个数小于等于k，并且s的长度大于等于k，则s可以构成k个回文串
     * 时间复杂度O(n+|Σ|)，空间复杂度O(|Σ|) (|Σ|=26，只包含小写字母)
     *
     * @param s
     * @param k
     * @return
     */
    public boolean canConstruct(String s, int k) {
        //s的长度小于k，则s不能构成k个回文串，返回false
        if (s.length() < k) {
            return false;
        }

        int[] countArr = new int[26];

        for (char c : s.toCharArray()) {
            countArr[c - 'a']++;
        }

        //s中出现次数为奇数的字符个数
        int oddCount = 0;

        for (int i = 0; i < 26; i++) {
            if (countArr[i] % 2 == 1) {
                oddCount++;
            }
        }

        //出现次数为奇数的字符个数小于等于k，并且s的长度大于等于k，则s可以构成k个回文串，返回true
        return oddCount <= k;
    }
}
