package com.zhang.java;

/**
 * @Date 2023/5/4 08:04
 * @Author zsy
 * @Description 至少有 K 个重复字符的最长子串 分治法类比Problem95、Problem105、Problem106、Problem108、Problem109、Problem255、Problem449、Problem617、Problem654、Problem889、Problem1008、Offer7、Offer33
 * 给你一个字符串 s 和一个整数 k ，请你找出 s 中的最长子串，要求该子串中的每一字符出现次数都不少于 k 。
 * 返回这一子串的长度。
 * <p>
 * 输入：s = "aaabb", k = 3
 * 输出：3
 * 解释：最长子串为 "aaa" ，其中 'a' 重复了 3 次。
 * <p>
 * 输入：s = "ababbc", k = 2
 * 输出：5
 * 解释：最长子串为 "ababb" ，其中 'a' 重复了 2 次， 'b' 重复了 3 次。
 * <p>
 * 1 <= s.length <= 10^4
 * s 仅由小写英文字母组成
 * 1 <= k <= 10^5
 */
public class Problem395 {
    public static void main(String[] args) {
        Problem395 problem395 = new Problem395();
        String s = "ababbc";
        int k = 2;
        System.out.println(problem395.longestSubstring(s, k));
    }

    /**
     * 哈希表+分治法
     * 统计字符串中每个字符出现的次数，如果字符串中每个字符出现次数都大于等于k，则当前字符串满足要求，直接返回当前字符串长度；
     * 如果字符串中存在字符出现次数小于k，则根据当前字符c将字符串分割为左右两个字符串，继续对这两个字符串进行判断
     * 时间复杂度O(n*|Σ|)，空间复杂度O(n^2) (因为s只包含小写字母，所以|Σ|=26)
     *
     * @param s
     * @param k
     * @return
     */
    public int longestSubstring(String s, int k) {
        if (s == null || s.length() == 0 || k == 0 || s.length() < k) {
            return 0;
        }

        //字符串中每个字符出现次数都大于等于1，则直接返回字符串的长度
        if (k == 1) {
            return s.length();
        }

        return dfs(0, s.length() - 1, s, k);
    }

    private int dfs(int left, int right, String s, int k) {
        //当前字符串长度小于等于1，则不存在字符串中每个字符出现次数都大于等于k(之前已经考虑过k为1的情况)，返回0
        if (left >= right) {
            return 0;
        }

        //当前字符串长度小于k，则不存在字符串中每个字符出现次数都大于等于k，返回0
        if (right - left + 1 < k) {
            return 0;
        }

        //字符串中每个字符出现的次数
        int[] count = new int[26];

        //统计字符串中每个字符出现的次数
        for (int i = left; i <= right; i++) {
            char c = s.charAt(i);
            count[c - 'a']++;
        }

        for (int i = left; i <= right; i++) {
            char c = s.charAt(i);
            //当前字符出现次数小于k，则将s[left]-s[right]分割为s[left]-s[i-1]和s[i+1]-s[right]，继续往左右两边判断
            if (count[c - 'a'] < k) {
                return Math.max(dfs(left, i - 1, s, k), dfs(i + 1, right, s, k));
            }
        }

        //字符串中每个字符出现的次数都大于等于k，则直接返回字符串的长度
        return right - left + 1;
    }
}
