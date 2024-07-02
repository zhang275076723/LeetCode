package com.zhang.java;

/**
 * @Date 2022/9/17 11:42
 * @Author zsy
 * @Description 最短回文串 网易机试题 美团机试题 华为面试题 字节面试题 kmp类比Problem28、Problem459、Problem686、Problem796、Problem1408 回文类比Problem5、Problem9、Problem125、Problem131、Problem132、Problem234、Problem266、Problem267、Problem336、Problem409、Problem479、Problem516、Problem564、Problem647、Problem680、Problem866、Problem1147、Problem1177、Problem1312、Problem1328、Problem1332、Problem1400、Problem1457、Problem1542、Problem1616、Problem1930、Problem2002、Problem2108、Problem2131、Problem2217、Problem2384、Problem2396、Problem2484、Problem2490、Problem2663、Problem2697、Problem2791、Problem3035
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
     * 从s[0]开始找最长回文串，反转s中不是回文串的剩下字符串，再拼接s，得到最短回文串
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param s
     * @return
     */
    public String shortestPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }

        //从后往前找从s[0]开头的最长回文串
        for (int i = s.length() - 1; i >= 0; i--) {
            int left = 0;
            int right = i;
            //s[0]-s[i]是否是回文串标志
            boolean flag = true;

            while (left <= right) {
                if (s.charAt(left) != s.charAt(right)) {
                    flag = false;
                    break;
                } else {
                    left++;
                    right--;
                }
            }

            //s[0]-s[i]是回文串
            if (flag) {
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

        //主串p、模式串s
        String p = new StringBuilder(s).reverse().toString();
        int[] next = getNext(s);
        //主串p指针
        int i;
        //模式串s指针
        int j = 0;

        //通过kmp，得到reverse(s)和s匹配的，以s[0]开始的最长回文串，s[0]-s[j-1]
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
     * 主串指针不回退，模式串指针在不匹配时根据next数组回退
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
            //当s[i]和p[j]不相等时，j指针通过next数组前移
            while (j > 0 && s.charAt(i) != p.charAt(j)) {
                j = next[j - 1];
            }

            //当前s[i]和p[j]匹配，j指针后移
            if (s.charAt(i) == p.charAt(j)) {
                j++;
            }

            //j遍历到末尾，则匹配，返回字符串s匹配的第一个字符下标索引
            if (j == p.length()) {
                return i - j + 1;
            }
        }

        //没有匹配，返回-1
        return -1;
    }

    /**
     * 获取字符串s的next数组
     * 当指向字符串s的指针j与主串指针i所指的元素不相同，即匹配失败时，字符串s的指针j重新指向next[j-1]
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param s
     * @return
     */
    private int[] getNext(String s) {
        int[] next = new int[s.length()];
        //当前字符串索引
        int i;
        //当前字符串相同的最长前缀和最长后缀的公共长度
        int j = 0;

        //注意：i从1开始遍历，因为s[0]-s[0]不存在公共前缀和后缀
        for (i = 1; i < s.length(); i++) {
            //当s[i]和s[j]不相等时，j指针右移
            while (j > 0 && s.charAt(i) != s.charAt(j)) {
                j = next[j - 1];
            }

            //当前前缀相等，j指针后移
            if (s.charAt(i) == s.charAt(j)) {
                j++;
            }

            next[i] = j;
        }

        return next;
    }
}
