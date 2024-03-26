package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/3/21 08:13
 * @Author zsy
 * @Description 元音拼写检查器 元音类比Problem345、Problem824、Problem1119、Problem1371、Problem1456
 * 在给定单词列表 wordlist 的情况下，我们希望实现一个拼写检查器，将查询单词转换为正确的单词。
 * 对于给定的查询单词 query，拼写检查器将会处理两类拼写错误：
 * 大小写：如果查询匹配单词列表中的某个单词（不区分大小写），则返回的正确单词与单词列表中的大小写相同。
 * 例如：wordlist = ["yellow"], query = "YellOw": correct = "yellow"
 * 例如：wordlist = ["Yellow"], query = "yellow": correct = "Yellow"
 * 例如：wordlist = ["yellow"], query = "yellow": correct = "yellow"
 * 元音错误：如果在将查询单词中的元音 ('a', 'e', 'i', 'o', 'u')  分别替换为任何元音后，
 * 能与单词列表中的单词匹配（不区分大小写），则返回的正确单词与单词列表中的匹配项大小写相同。
 * 例如：wordlist = ["YellOw"], query = "yollow": correct = "YellOw"
 * 例如：wordlist = ["YellOw"], query = "yeellow": correct = "" （无匹配项）
 * 例如：wordlist = ["YellOw"], query = "yllw": correct = "" （无匹配项）
 * 此外，拼写检查器还按照以下优先级规则操作：
 * 当查询完全匹配单词列表中的某个单词（区分大小写）时，应返回相同的单词。
 * 当查询匹配到大小写问题的单词时，您应该返回单词列表中的第一个这样的匹配项。
 * 当查询匹配到元音错误的单词时，您应该返回单词列表中的第一个这样的匹配项。
 * 如果该查询在单词列表中没有匹配项，则应返回空字符串。
 * 给出一些查询 queries，返回一个单词列表 answer，其中 answer[i] 是由查询 query = queries[i] 得到的正确单词。
 * <p>
 * 输入：wordlist = ["KiTe","kite","hare","Hare"], queries = ["kite","Kite","KiTe","Hare","HARE","Hear","hear","keti","keet","keto"]
 * 输出：["kite","KiTe","KiTe","Hare","hare","","","KiTe","","KiTe"]
 * <p>
 * 输入：wordlist = ["yellow"], queries = ["YellOw"]
 * 输出：["yellow"]
 * <p>
 * 1 <= wordlist.length, queries.length <= 5000
 * 1 <= wordlist[i].length, queries[i].length <= 7
 * wordlist[i] 和 queries[i] 只包含英文字母
 */
public class Problem966 {
    public static void main(String[] args) {
        Problem966 problem966 = new Problem966();
        String[] wordlist = {"KiTe", "kite", "hare", "Hare"};
        String[] queries = {"kite", "Kite", "KiTe", "Hare", "HARE", "Hear", "hear", "keti", "keet", "keto"};
        System.out.println(Arrays.toString(problem966.spellchecker(wordlist, queries)));
    }

    /**
     * 哈希表
     * 存储wordlist[i]转换为小写单词和wordlist[i]的映射，存储wordlist[i]转换为小写单词并且元音字母转换为'*'和wordlist[i]的映射
     * 先判断wordlist[i]能否匹配，再判断wordlist[i]转换为小写单词能否匹配，最后判断wordlist[i]转换为小写单词并且元音字母转换为'*'能否匹配
     * 时间复杂度O(mp+nq)，空间复杂度O(mp+nq) (m=wordlist.length，n=queries.length，p=wordlist[i].length()，q=queries[i].length())
     *
     * @param wordlist
     * @param queries
     * @return
     */
    public String[] spellchecker(String[] wordlist, String[] queries) {
        //存储wordlist中单词的集合
        Set<String> set = new HashSet<>();
        //key：wordlist[i]转换为小写单词，value：wordlist[i]
        Map<String, String> capitalMap = new HashMap<>();
        //key：wordlist[i]转换为小写单词并且元音字母转换为'*'，value：wordlist[i]
        Map<String, String> vowelMap = new HashMap<>();

        //wordlist[i]、wordlist[i]转换为小写单词、wordlist[i]转换为小写单词并且元音字母转换为'*'，分别加入set和map中
        for (int i = 0; i < wordlist.length; i++) {
            set.add(wordlist[i]);

            //wordlist[i]转换为小写单词
            String lowerWord = wordlist[i].toLowerCase();
            //capitalMap中不存在lowerWord，才加入map，保证查询时找到第一个匹配的单词
            if (!capitalMap.containsKey(lowerWord)) {
                capitalMap.put(lowerWord, wordlist[i]);
            }

            //wordlist[i]转换为小写单词并且元音字母转换为'*'
            StringBuilder vowelWord = new StringBuilder();

            for (char c : wordlist[i].toLowerCase().toCharArray()) {
                if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                    vowelWord.append('*');
                } else {
                    vowelWord.append(c);
                }
            }

            //capitalMap中不存在vowelWord，才加入map，保证查询时找到第一个匹配的单词
            if (!vowelMap.containsKey(vowelWord.toString())) {
                vowelMap.put(vowelWord.toString(), wordlist[i]);
            }
        }

        String[] result = new String[queries.length];

        for (int i = 0; i < queries.length; i++) {
            //当前要查询的单词
            String word = queries[i];

            //set中存在word，则word完全匹配，直接返回
            if (set.contains(word)) {
                result[i] = word;
                continue;
            }

            //word转换为小写单词
            String lowerWord = word.toLowerCase();

            //capitalMap中存在lowerWord，则word不区分大小写匹配，直接返回
            if (capitalMap.containsKey(lowerWord)) {
                result[i] = capitalMap.get(lowerWord);
                continue;
            }

            //word转换为小写单词并且元音字母转换为'*'
            StringBuilder vowelWord = new StringBuilder();

            for (char c : word.toLowerCase().toCharArray()) {
                if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                    vowelWord.append('*');
                } else {
                    vowelWord.append(c);
                }
            }

            //vowelMap中存在vowelWord，则word不区分大小写并且元音替换其他元音匹配，直接返回
            if (vowelMap.containsKey(vowelWord.toString())) {
                result[i] = vowelMap.get(vowelWord.toString());
                continue;
            }

            //word无法匹配，返回""
            result[i] = "";
        }

        return result;
    }
}
