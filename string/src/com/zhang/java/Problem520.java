package com.zhang.java;

/**
 * @Date 2024/1/3 08:28
 * @Author zsy
 * @Description 检测大写字母 类比Problem65、Offer20
 * 我们定义，在以下情况时，单词的大写用法是正确的：
 * 全部字母都是大写，比如 "USA" 。
 * 单词中所有字母都不是大写，比如 "leetcode" 。
 * 如果单词不只含有一个字母，只有首字母大写， 比如 "Google" 。
 * 给你一个字符串 word 。如果大写用法正确，返回 true ；否则，返回 false 。
 * <p>
 * 输入：word = "USA"
 * 输出：true
 * <p>
 * 输入：word = "FlaG"
 * 输出：false
 * <p>
 * 1 <= word.length <= 100
 * word 由小写和大写英文字母组成
 */
public class Problem520 {
    public static void main(String[] args) {
        Problem520 problem520 = new Problem520();
        String word = "FlaG";
        System.out.println(problem520.detectCapitalUse(word));
    }

    /**
     * 模拟
     * 如果第一个字母为大写，则后面所有字母要么都是大写，要么都是小写，即从第二个字母开始之后字母大小写都相同
     * 如果第一个字母为小写，则后面所有字母都要是小写
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param word
     * @return
     */
    public boolean detectCapitalUse(String word) {
        if (word.length() == 1) {
            return true;
        }

        //第一个字母
        char c1 = word.charAt(0);

        //如果第一个字母为大写，则后面所有字母要么都是大写，要么都是小写，即从第二个字母开始之后字母大小写都相同
        if (c1 >= 'A' && c1 <= 'Z') {
            for (int i = 2; i < word.length(); i++) {
                //当前字母
                char c = word.charAt(i);
                //前一个字母
                char c2 = word.charAt(i - 1);

                if (((c >= 'a' && c <= 'z') && (c2 >= 'A' && c2 <= 'Z')) ||
                        ((c >= 'A' && c <= 'Z') && (c2 >= 'a' && c2 <= 'z'))) {
                    return false;
                }
            }
        } else {
            //如果第一个字母为小写，则后面所有字母都要是小写
            for (int i = 1; i < word.length(); i++) {
                char c = word.charAt(i);
                if (!(c >= 'a' && c <= 'z')) {
                    return false;
                }
            }
        }

        //遍历结束，则单词大小写合法，返回true
        return true;
    }
}
