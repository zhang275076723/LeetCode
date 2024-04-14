package com.zhang.java;

/**
 * @Date 2024/4/13 08:21
 * @Author zsy
 * @Description 回环句
 * 句子 是由单个空格分隔的一组单词，且不含前导或尾随空格。
 * 例如，"Hello World"、"HELLO"、"hello world hello world" 都是符合要求的句子。
 * 单词 仅 由大写和小写英文字母组成。且大写和小写字母会视作不同字符。
 * 如果句子满足下述全部条件，则认为它是一个 回环句 ：
 * 单词的最后一个字符和下一个单词的第一个字符相等。
 * 最后一个单词的最后一个字符和第一个单词的第一个字符相等。
 * 例如，"leetcode exercises sound delightful"、"eetcode"、"leetcode eats soul" 都是回环句。
 * 然而，"Leetcode is cool"、"happy Leetcode"、"Leetcode" 和 "I like Leetcode" 都 不 是回环句。
 * 给你一个字符串 sentence ，请你判断它是不是一个回环句。
 * 如果是，返回 true ；否则，返回 false 。
 * <p>
 * 输入：sentence = "leetcode exercises sound delightful"
 * 输出：true
 * 解释：句子中的单词是 ["leetcode", "exercises", "sound", "delightful"] 。
 * - leetcode 的最后一个字符和 exercises 的第一个字符相等。
 * - exercises 的最后一个字符和 sound 的第一个字符相等。
 * - sound 的最后一个字符和 delightful 的第一个字符相等。
 * - delightful 的最后一个字符和 leetcode 的第一个字符相等。
 * 这个句子是回环句。
 * <p>
 * 输入：sentence = "eetcode"
 * 输出：true
 * 解释：句子中的单词是 ["eetcode"] 。
 * - eetcode 的最后一个字符和 eetcode 的第一个字符相等。
 * 这个句子是回环句。
 * <p>
 * 输入：sentence = "Leetcode is cool"
 * 输出：false
 * 解释：句子中的单词是 ["Leetcode", "is", "cool"] 。
 * - Leetcode 的最后一个字符和 is 的第一个字符 不 相等。
 * 这个句子 不 是回环句。
 * <p>
 * 1 <= sentence.length <= 500
 * sentence 仅由大小写英文字母和空格组成
 * sentence 中的单词由单个空格进行分隔
 * 不含任何前导或尾随空格
 */
public class Problem2490 {
    public static void main(String[] args) {
        Problem2490 problem2490 = new Problem2490();
        String sentence = "leetcode exercises sound delightful";
        System.out.println(problem2490.isCircularSentence(sentence));
    }

    /**
     * 模拟
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param sentence
     * @return
     */
    public boolean isCircularSentence(String sentence) {
        int index = 0;

        //找第一个单词的最后一个字符
        while (index < sentence.length() && sentence.charAt(index) != ' ') {
            index++;
        }

        //当前单词前一个单词的最后一个字符
        char c1 = sentence.charAt(index - 1);
        index++;

        while (index < sentence.length()) {
            //当前单词的首字符
            char c2 = sentence.charAt(index);

            if (c1 != c2) {
                return false;
            }

            while (index < sentence.length() && sentence.charAt(index) != ' ') {
                index++;
            }

            //更新当前单词前一个单词的最后一个字符
            c1 = sentence.charAt(index - 1);
            index++;
        }

        //第一个单词的首字符和最后一个单词的尾字符不等，则不是回环句，返回false
        if (sentence.charAt(0) != c1) {
            return false;
        }

        //遍历结束，则是回环句，返回true
        return true;
    }
}
