package com.zhang.java;

/**
 * @Date 2023/5/4 08:24
 * @Author zsy
 * @Description 重复叠加字符串匹配 kmp类比Problem28、Problem214、Problem459、Problem796 旋转问题类比Problem61、Problem186、Problem189、Problem459、Problem796、Offer58_2
 * 给定两个字符串 a 和 b，寻找重复叠加字符串 a 的最小次数，使得字符串 b 成为叠加后的字符串 a 的子串，如果不存在则返回 -1。
 * 注意：字符串 "abc" 重复叠加 0 次是 ""，重复叠加 1 次是 "abc"，重复叠加 2 次是 "abcabc"。
 * <p>
 * 输入：a = "abcd", b = "cdabcdab"
 * 输出：3
 * 解释：a 重复叠加三遍后为 "abcdabcdabcd", 此时 b 是其子串。
 * <p>
 * 输入：a = "a", b = "aa"
 * 输出：2
 * <p>
 * 输入：a = "a", b = "a"
 * 输出：1
 * <p>
 * 输入：a = "abc", b = "wxyz"
 * 输出：-1
 * <p>
 * 1 <= a.length <= 10^4
 * 1 <= b.length <= 10^4
 * a 和 b 由小写英文字母组成
 */
public class Problem686 {
    public static void main(String[] args) {
        Problem686 problem686 = new Problem686();
        String a = "aabaa";
        String b = "aaab";
        System.out.println(problem686.repeatedStringMatch(a, b));
    }

    /**
     * kmp
     * 当字符串a的长度大于等于字符串b的长度时，字符串a最多重复2次，kmp判断得到的新字符串中是否存在字符串b；
     * 当字符串a的长度小于字符串b的长度时，字符串a最多重复b.length()/a.length()+2次，kmp判断得到的新字符串中是否存在字符串b。
     * 即字符串a最多重复b.length()/a.length()+2次，字符串a先拼接b.length()/a.length()次，再判断拼接之后是否包含字符串b
     * 时间复杂度O(m+n)，空间复杂度O(n) (m=a.length(),n=b.length())
     *
     * @param a
     * @param b
     * @return
     */
    public int repeatedStringMatch(String a, String b) {
        //字符串a最多重复的次数为count+2
        int count = b.length() / a.length();
        //next数组
        int[] next = getNext(b);
        //字符串a重复多次得到的新字符串sb，多次字符串拼接不要使用String拼接，会超时
        StringBuilder sb = new StringBuilder();

        //sb先拼接count次a，保证sb的长度接近b的长度，再判断sb中是否存在字符串b，不需要拼接一个a就判断一次，提升性能
        for (int i = 0; i < count; i++) {
            sb.append(a);
        }

        //sb拼接count次a之后，最多再拼接2次a
        for (int i = 0; i <= 2; i++) {
            //模式串b指针
            int n = 0;

            //主串sb指针
            for (int m = 0; m < sb.length(); m++) {
                //当前字符不匹配，n指针通过next数组前移
                while (n > 0 && sb.charAt(m) != b.charAt(n)) {
                    n = next[n - 1];
                }

                //当前字符匹配，n指针后移
                if (sb.charAt(m) == b.charAt(n)) {
                    n++;
                }

                //sb中存在字符串b，返回字符串a最小重复次数count+i
                if (n == b.length()) {
                    return count + i;
                }
            }

            sb.append(a);
        }

        //字符串a最多重复count+2次，b也不是其子串，返回-1
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
