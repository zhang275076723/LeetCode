package com.zhang.java;

import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2025/4/17 08:47
 * @Author zsy
 * @Description 不同的循环子字符串 类比Problem459 字符串哈希类比Problem187、Problem1044、Problem1392、Problem1698、Problem3029、Problem3031、Problem3042、Problem3045、Problem3076
 * 给你一个字符串 text ，请你返回满足下述条件的 不同 非空子字符串的数目：
 * 可以写成某个字符串与其自身相连接的形式（即，可以写为 a + a，其中 a 是某个字符串）。
 * 例如，abcabc 就是 abc 和它自身连接形成的。
 * <p>
 * 输入：text = "abcabcabc"
 * 输出：3
 * 解释：3 个子字符串分别为 "abcabc"，"bcabca" 和 "cabcab" 。
 * <p>
 * 输入：text = "leetcodeleetcode"
 * 输出：2
 * 解释：2 个子字符串为 "ee" 和 "leetcodeleetcode" 。
 * <p>
 * 1 <= text.length <= 2000
 * text 只包含小写英文字母。
 */
public class Problem1316 {
    public static void main(String[] args) {
        Problem1316 problem1316 = new Problem1316();
        String text = "abcabcabc";
        System.out.println(problem1316.distinctEchoSubstrings(text));
        System.out.println(problem1316.distinctEchoSubstrings2(text));
    }

    /**
     * 暴力
     * 时间复杂度O(n^3)，空间复杂度O(n^2)
     *
     * @param text
     * @return
     */
    public int distinctEchoSubstrings(String text) {
        //text中循环字符串的个数
        //注意：循环字符串只能由2个相同的子字符串构成，不能由多个子字符串构成
        int count = 0;
        Set<String> set = new HashSet<>();

        //判断s[i]-s[j]是否是循环字符串
        for (int i = 0; i < text.length(); i++) {
            for (int j = i; j < text.length(); j++) {
                //s[i]-s[j]
                String str = text.substring(i, j + 1);
                //str1的前一半字符串
                String subStr1 = str.substring(0, str.length() / 2);
                //str1的后一半字符串
                String subStr2 = str.substring(str.length() / 2, str.length());

                if (subStr1.equals(subStr2)) {
                    if (!set.contains(str)) {
                        count++;
                    }

                    set.add(str);
                }
            }
        }

        return count;
    }

    /**
     * 字符串哈希
     * hash[i]：s[0]-s[i-1]的哈希值
     * prime[i]：p^i的值
     * hash[j+1]-hash[i]*prime[j-i+1]：s[i]-s[j]的哈希值
     * 核心思想：将字符串看成P进制数，再对MOD取余，作为当前字符串的哈希值，只要两个字符串哈希值相等，则认为两个字符串相等
     * 一般取P为较大的质数，P=131或P=13331或P=131313，此时产生的哈希冲突低；
     * 一般取MOD=2^63(long类型最大值+1)，在计算时不处理溢出问题，产生溢出相当于自动对MOD取余；
     * 如果产生哈希冲突，则使用双哈希来减少冲突
     * 时间复杂度O(n^2)，空间复杂度O(n^2)
     *
     * @param text
     * @return
     */
    public int distinctEchoSubstrings2(String text) {
        //大质数，p进制
        int p = 131;
        long[] hash = new long[text.length() + 1];
        long[] prime = new long[text.length() + 1];

        //p^0初始化
        prime[0] = 1;

        for (int i = 1; i <= text.length(); i++) {
            char c = text.charAt(i - 1);
            //注意：不需要进行取模运算，产生溢出相当于自动对MOD取模
            hash[i] = hash[i - 1] * p + c;
            prime[i] = prime[i - 1] * p;
        }

        //存储text中子字符串的哈希值
        Set<Long> set = new HashSet<>();

        //判断s[i]-s[j]是否是循环字符串
        for (int i = 0; i < text.length(); i++) {
            for (int j = i; j < text.length(); j++) {
                //s[i]-s[j]的哈希值
                long h1 = hash[j + 1] - hash[i] * (prime[j - i + 1]);
                //s[i]-s[j]的前一半s[i]-s[(i+j-1)/2]的哈希值
                long h2 = hash[(i + j + 1) / 2] - hash[i] * prime[(j - i + 1) / 2];
                //s[i]-s[j]的后一半s[(i+j+1)/2]-s[j]的哈希值
                long h3 = hash[j + 1] - hash[(i + j + 1) / 2] * prime[(j - i + 1) / 2];

                if (h2 == h3) {
                    set.add(h1);
                }
            }
        }

        return set.size();
    }
}
