package com.zhang.java;

/**
 * @Date 2024/1/11 08:25
 * @Author zsy
 * @Description 有效单词缩写 类比Problem320、Problem527 双指针类比
 * 字符串可以用 缩写 进行表示，缩写 的方法是将任意数量的 不相邻 的子字符串替换为相应子串的长度。
 * 例如，字符串 "substitution" 可以缩写为（不止这几种方法）：
 * "s10n" ("s ubstitutio n")
 * "sub4u4" ("sub stit u tion")
 * "12" ("substitution")
 * "su3i1u2on" ("su bst i t u ti on")
 * "substitution" (没有替换子字符串)
 * 下列是不合法的缩写：
 * "s55n" ("s ubsti tutio n"，两处缩写相邻)
 * "s010n" (缩写存在前导零)
 * "s0ubstitution" (缩写是一个空字符串)
 * 给你一个字符串单词 word 和一个缩写 abbr ，判断这个缩写是否可以是给定单词的缩写。
 * 子字符串是字符串中连续的非空字符序列。
 * <p>
 * 输入：word = "internationalization", abbr = "i12iz4n"
 * 输出：true
 * 解释：单词 "internationalization" 可以缩写为 "i12iz4n" ("i nternational iz atio n") 。
 * <p>
 * 输入：word = "apple", abbr = "a2e"
 * 输出：false
 * 解释：单词 "apple" 无法缩写为 "a2e" 。
 * <p>
 * 1 <= word.length <= 20
 * word 仅由小写英文字母组成
 * 1 <= abbr.length <= 10
 * abbr 由小写英文字母和数字组成
 * abbr 中的所有数字均符合 32-bit 整数范围
 */
public class Problem408 {
    public static void main(String[] args) {
        Problem408 problem408 = new Problem408();
        String word = "internationalization";
        String abbr = "i12iz4n";
        System.out.println(problem408.validWordAbbreviation(word, abbr));
    }

    /**
     * 双指针
     * 指针i、j分别指向word和abbr当前遍历到的下标索引，如果j遍历到数字num，则判断num能否和word[i]-word[i+num-1]匹配
     * 时间复杂度O(m+n)，空间复杂度O(1) (m=word.length()，n=abbr.length)
     *
     * @param word
     * @param abbr
     * @return
     */
    public boolean validWordAbbreviation(String word, String abbr) {
        int m = word.length();
        int n = abbr.length();

        //word当前遍历到的下标索引
        int i = 0;
        //abbr当前遍历到的下标索引
        int j = 0;

        while (i < m && j < n) {
            char c1 = word.charAt(i);
            char c2 = abbr.charAt(j);

            //word[i]和abbr[j]相等，则i和j都右移一位
            if (c1 == c2) {
                i++;
                j++;
            } else {
                //word[i]和abbr[j]不相等

                //abbr[j]不是数字，word[i]和abbr[j]表示的字母不相等，则不是有效单词缩写，返回false
                if (c2 < '0' || c2 > '9') {
                    return false;
                }

                //有前导0，或缩写空字符串，则不是有效单词缩写，返回false
                if (c2 == '0') {
                    return false;
                }

                //j当前遍历到的数字
                int num = 0;

                while (j < n && abbr.charAt(j) >= '0' && abbr.charAt(j) <= '9') {
                    num = num * 10 + abbr.charAt(j) - '0';
                    j++;
                }

                //判断num能否和word[i]-word[i+num-1]匹配
                i = i + num;

                //i超过word的范围，则不是有效单词缩写，返回false
                if (i > m) {
                    return false;
                }
            }
        }

        //i和j都遍历结束才是有效单词缩写
        return i == m && j == n;
    }
}
