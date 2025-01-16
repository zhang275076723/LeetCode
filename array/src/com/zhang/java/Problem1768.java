package com.zhang.java;

/**
 * @Date 2025/2/23 08:18
 * @Author zsy
 * @Description 交替合并字符串 类比Problem165 双指针类比
 * 给你两个字符串 word1 和 word2 。
 * 请你从 word1 开始，通过交替添加字母来合并字符串。
 * 如果一个字符串比另一个字符串长，就将多出来的字母追加到合并后字符串的末尾。
 * 返回 合并后的字符串 。
 * <p>
 * 输入：word1 = "abc", word2 = "pqr"
 * 输出："apbqcr"
 * 解释：字符串合并情况如下所示：
 * word1：  a   b   c
 * word2：    p   q   r
 * 合并后：  a p b q c r
 * <p>
 * 输入：word1 = "ab", word2 = "pqrs"
 * 输出："apbqrs"
 * 解释：注意，word2 比 word1 长，"rs" 需要追加到合并后字符串的末尾。
 * word1：  a   b
 * word2：    p   q   r   s
 * 合并后：  a p b q   r   s
 * <p>
 * 输入：word1 = "abcd", word2 = "pq"
 * 输出："apbqcd"
 * 解释：注意，word1 比 word2 长，"cd" 需要追加到合并后字符串的末尾。
 * word1：  a   b   c   d
 * word2：    p   q
 * 合并后：  a p b q c   d
 * <p>
 * 1 <= word1.length, word2.length <= 100
 * word1 和 word2 由小写英文字母组成
 */
public class Problem1768 {
    public static void main(String[] args) {
        Problem1768 problem1768 = new Problem1768();
        String word1 = "abcd";
        String word2 = "pq";
        System.out.println(problem1768.mergeAlternately(word1, word2));
    }

    /**
     * 双指针
     * 时间复杂度O(m+n)，空间复杂度O(m+n)
     *
     * @param word1
     * @param word2
     * @return
     */
    public String mergeAlternately(String word1, String word2) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        int j = 0;

        while (i < word1.length() && j < word2.length()) {
            if (i <= j) {
                sb.append(word1.charAt(i));
                i++;
            } else {
                sb.append(word2.charAt(j));
                j++;
            }
        }

        if (i < word1.length()) {
            sb.append(word1.substring(i));
        } else {
            sb.append(word2.substring(j));
        }

        return sb.toString();
    }
}
