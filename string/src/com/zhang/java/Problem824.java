package com.zhang.java;

import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2024/3/20 08:25
 * @Author zsy
 * @Description 山羊拉丁文 元音类比Problem345、Problem966、Problem1119、Problem1371、Problem1456、Problem1704、Problem1839
 * 给你一个由若干单词组成的句子 sentence ，单词间由空格分隔。每个单词仅由大写和小写英文字母组成。
 * 请你将句子转换为 “山羊拉丁文（Goat Latin）”（一种类似于 猪拉丁文 - Pig Latin 的虚构语言）。
 * 山羊拉丁文的规则如下：
 * 如果单词以元音开头（'a', 'e', 'i', 'o', 'u'），在单词后添加"ma"。
 * 例如，单词 "apple" 变为 "applema" 。
 * 如果单词以辅音字母开头（即，非元音字母），移除第一个字符并将它放到末尾，之后再添加"ma"。
 * 例如，单词 "goat" 变为 "oatgma" 。
 * 根据单词在句子中的索引，在单词最后添加与索引相同数量的字母'a'，索引从 1 开始。
 * 例如，在第一个单词后添加 "a" ，在第二个单词后添加 "aa" ，以此类推。
 * 返回将 sentence 转换为山羊拉丁文后的句子。
 * <p>
 * 输入：sentence = "I speak Goat Latin"
 * 输出："Imaa peaksmaaa oatGmaaaa atinLmaaaaa"
 * <p>
 * 输入：sentence = "The quick brown fox jumped over the lazy dog"
 * 输出："heTmaa uickqmaaa rownbmaaaa oxfmaaaaa umpedjmaaaaaa overmaaaaaaa hetmaaaaaaaa azylmaaaaaaaaa ogdmaaaaaaaaaa"
 * <p>
 * 1 <= sentence.length <= 150
 * sentence 由英文字母和空格组成
 * sentence 不含前导或尾随空格
 * sentence 中的所有单词由单个空格分隔
 */
public class Problem824 {
    public static void main(String[] args) {
        Problem824 problem824 = new Problem824();
        String sentence = "I speak Goat Latin";
        System.out.println(problem824.toGoatLatin(sentence));
    }

    /**
     * 模拟
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param sentence
     * @return
     */
    public String toGoatLatin(String sentence) {
        StringBuilder sb = new StringBuilder();
        //当前单词左下标索引
        int left = 0;
        //当前单词右下标索引
        int right = 0;
        //当前单词的下标索引，下标索引从1开始
        int index = 1;

        //大写和小写共10个元音字符的集合
        Set<Character> vowelSet = new HashSet<Character>() {{
            add('a');
            add('e');
            add('i');
            add('o');
            add('u');
            add('A');
            add('E');
            add('I');
            add('O');
            add('U');
        }};

        while (right < sentence.length()) {
            while (right < sentence.length() && sentence.charAt(right) != ' ') {
                right++;
            }

            //sentence[left]-sentence[right-1]以元音字母开头，单词后添加"ma"
            if (vowelSet.contains(sentence.charAt(left))) {
                sb.append(sentence.substring(left, right)).append("ma");
            } else {
                //sentence[left]-sentence[right-1]不以元音字母开头，移除第一个字符放到末尾，单词后添加"ma"
                sb.append(sentence.substring(left + 1, right)).append(sentence.charAt(left)).append("ma");
            }

            //拼接index个a
            for (int i = 0; i < index; i++) {
                sb.append('a');
            }

            //单词之间拼接空格
            sb.append(' ');

            //跳过单词中的空格
            while (right < sentence.length() && sentence.charAt(right) == ' ') {
                right++;
            }

            left = right;
            index++;
        }

        //移除末尾空格
        return sb.delete(sb.length() - 1, sb.length()).toString();
    }
}
