package com.zhang.java;

/**
 * @Date 2022/9/17 11:42
 * @Author zsy
 * @Description 最短回文串 网易机试题 美团机试题 华为面试题 字节面试题 类比Problem5、Problem9、Problem131、Problem234、Problem516、Problem647
 * 给定一个字符串 s，你可以通过在字符串前面添加字符将其转换为回文串。
 * 找到并返回可以用这种方式转换的最短回文串。
 * <p>
 * 输入：s = "aacecaaa"
 * 输出："aaacecaaa"
 * <p>
 * 输入：s = "abcd"
 * 输出："dcbabcd"
 * <p>
 * 0 <= s.length <= 5 * 10^4
 * s 仅由小写英文字母组成
 */
public class Problem214 {
    public static void main(String[] args) {
        Problem214 problem214 = new Problem214();
        String s = "aabba";
        System.out.println(problem214.shortestPalindrome(s));
        System.out.println(problem214.shortestPalindrome2(s));
    }

    /**
     * 暴力
     * 找以s[0]开始的最长回文串，反转s剩下的字符串再拼接s，得到最短回文串
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param s
     * @return
     */
    public String shortestPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }

        //从后往前找s[0]开头的最长回文串
        for (int i = s.length() - 1; i >= 0; i--) {
            int left = 0;
            int right = i;

            while (left <= right && s.charAt(left) == s.charAt(right)) {
                left++;
                right--;
            }

            //s[0]-s[i]构成回文串
            if (left > right) {
                //反转s[i+1]-s[s.length()-1]，拼接上s，得到最短回文串
                return new StringBuilder().append(s.substring(i + 1, s.length())).reverse().append(s).toString();
            }
        }

        return null;
    }

    /**
     * kmp
     * 通过kmp的next数组确定以s[0]开始的最长回文串，
     * 主串为s的逆序，模式串为s，当主串遍历结束时，模式串匹配到的前j个字符，即为以s[0]开始的最长回文串
     * 找以s[0]开始的最长回文串，反转s剩下的字符串再拼接s，得到最短回文串
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public String shortestPalindrome2(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }

        String p = new StringBuilder(s).reverse().toString();
        int[] next = getNext(s);
        //主串p指针
        int i;
        //模式串s指针
        int j = 0;

        //通过kmp，得到reverse(s)和s的匹配结果，即以s[0]开始的最长回文串，s[0]-s[j]
        for (i = 0; i < p.length(); i++) {
            while (j > 0 && p.charAt(i) != s.charAt(j)) {
                j = next[j - 1];
            }

            if (p.charAt(i) == s.charAt(j)) {
                j++;
            }
        }

        //反转s[j]-s[s.length()-1]，拼接上s，得到最短回文串
        return new StringBuilder().append(s.substring(j, s.length())).reverse().append(s).toString();
    }

    /**
     * kmp算法
     * 主串指针不回退，模式串指针根据next数组回退
     * 时间复杂度O(m+n)，空间复杂度O(n) (m=s.length()，n=p.length())
     *
     * @param s
     * @param p
     * @return
     */
    private int kmpSearch(String s, String p) {
        //next数组
        int[] next = getNext(p);
        //字符串s指针，不回退
        int i;
        //字符串p指针，回退
        int j = 0;

        for (i = 0; i < s.length(); i++) {
            //当s[i]和p[j]不相等时，j指针前移
            while (j > 0 && s.charAt(i) != p.charAt(j)) {
                j = next[j - 1];
            }

            //当前s[i]和p[j]匹配，j后移
            if (s.charAt(i) == p.charAt(j)) {
                j++;
            }

            //j遍历到末尾，则匹配，返回字符串s匹配的第一个字符索引
            if (j == p.length()) {
                return i - j + 1;
            }
        }

        //没有匹配，返回-1
        return -1;
    }

    /**
     * 得到字符串p的next数组
     * 当指向字符串p的指针j与主串指针i所指的元素不相同，即匹配失败时，字符串p的指针j重新指向next[j-1]
     *
     * @param p
     * @return
     */
    private int[] getNext(String p) {
        int[] next = new int[p.length()];
        //当前字符串索引
        int i;
        //当前相同的最长前缀和后缀长度
        int j = 0;

        for (i = 1; i < p.length(); i++) {
            //当p[i]和p[j]不相等时，j指针前移
            while (j > 0 && p.charAt(i) != p.charAt(j)) {
                j = next[j - 1];
            }

            //当前前缀相等，j后移
            if (p.charAt(i) == p.charAt(j)) {
                j++;
            }

            next[i] = j;
        }

        return next;
    }
}