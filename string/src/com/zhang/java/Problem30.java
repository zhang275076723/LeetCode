package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/2/6 11:42
 * @Author zsy
 * @Description 串联所有单词的子串 滑动窗口类比Problem3、Problem76、Problem209、Problem219、Problem220、Problem239、Problem340、Problem438、Problem485、Problem487、Problem532、Problem567、Problem713、Problem1004、Offer48、Offer57_2、Offer59
 * 给定一个字符串 s 和一个字符串数组 words。 words 中所有字符串 长度相同。
 * s 中的 串联子串 是指一个包含 words 中所有字符串以任意顺序排列连接起来的子串。
 * 例如，如果 words = ["ab","cd","ef"]，
 * 那么 "abcdef"， "abefcd"，"cdabef"， "cdefab"，"efabcd"，和 "efcdab" 都是串联子串。
 * "acdbef" 不是串联子串，因为他不是任何 words 排列的连接。
 * 返回所有串联字串在 s 中的开始索引。
 * 你可以以 任意顺序 返回答案。
 * <p>
 * 输入：s = "barfoothefoobarman", words = ["foo","bar"]
 * 输出：[0,9]
 * 解释：因为 words.length == 2 同时 words[i].length == 3，连接的子字符串的长度必须为 6。
 * 子串 "barfoo" 开始位置是 0。它是 words 中以 ["bar","foo"] 顺序排列的连接。
 * 子串 "foobar" 开始位置是 9。它是 words 中以 ["foo","bar"] 顺序排列的连接。
 * 输出顺序无关紧要。返回 [9,0] 也是可以的。
 * <p>
 * 输入：s = "wordgoodgoodgoodbestword", words = ["word","good","best","word"]
 * 输出：[]
 * 解释：因为 words.length == 4 并且 words[i].length == 4，所以串联子串的长度必须为 16。
 * s 中没有子串长度为 16 并且等于 words 的任何顺序排列的连接。
 * 所以我们返回一个空数组。
 * <p>
 * 输入：s = "barfoofoobarthefoobarman", words = ["bar","foo","the"]
 * 输出：[6,9,12]
 * 解释：因为 words.length == 3 并且 words[i].length == 3，所以串联子串的长度必须为 9。
 * 子串 "foobarthe" 开始位置是 6。它是 words 中以 ["foo","bar","the"] 顺序排列的连接。
 * 子串 "barthefoo" 开始位置是 9。它是 words 中以 ["bar","the","foo"] 顺序排列的连接。
 * 子串 "thefoobar" 开始位置是 12。它是 words 中以 ["the","foo","bar"] 顺序排列的连接。
 * <p>
 * 1 <= s.length <= 10^4
 * 1 <= words.length <= 5000
 * 1 <= words[i].length <= 30
 * words[i] 和 s 由小写英文字母组成
 */
public class Problem30 {
    public static void main(String[] args) {
        Problem30 problem30 = new Problem30();
//        String s = "barfoothefoobarman";
//        String[] words = {"foo", "bar"};
        String s = "barfoofoobarthefoobarman";
        String[] words = {"bar", "foo", "the"};
        System.out.println(problem30.findSubstring(s, words));
        System.out.println(problem30.findSubstring2(s, words));
    }

    /**
     * 暴力
     * wordsMap存放words中单词出现次数，遍历字符串s，将s中每个单词放入sMap中，
     * 判断wordsMap和sMap中单词出现次数是否相等，如果相等，则从s[i]开始的字符串是words的串联子串
     * 时间复杂度O(s.length()*wordCont*wordLen)，空间复杂度O(wordCont*wordLen)
     * (wordCont：words中单词数量，wordLen：words中每个单词长度)
     *
     * @param s
     * @param words
     * @return
     */
    public List<Integer> findSubstring(String s, String[] words) {
        if (s == null || s.length() == 0 || words == null || words.length == 0) {
            return new ArrayList<>();
        }

        //words中单词数量
        int wordCount = words.length;
        //words中每个单词长度
        int wordLen = words[0].length();

        //words中字符串的总长度超过s的长度，则不存在s的串联子串，直接返回
        if (s.length() < wordCount * wordLen) {
            return new ArrayList<>();
        }

        Map<String, Integer> wordsMap = new HashMap<>();

        for (String word : words) {
            wordsMap.put(word, wordsMap.getOrDefault(word, 0) + 1);
        }

        List<Integer> list = new ArrayList<>();

        for (int i = 0; i <= s.length() - wordCount * wordLen; i++) {
            Map<String, Integer> sMap = new HashMap<>();

            //从s[i]开始，每个单词长度wordLen，加入到sMap中，再和wordsMap比较是否相等
            for (int j = i; j < i + wordCount * wordLen; j = j + wordLen) {
                String word = s.substring(j, j + wordLen);
                //当前单词word不在wordsMap中，则不可能是words的串联子串，不需要继续往后找单词加入sMap中，直接跳出循环
                if (!wordsMap.containsKey(word)) {
                    break;
                }
                sMap.put(word, sMap.getOrDefault(word, 0) + 1);
            }

            //sMap和wordsMap中包含的字符串和对应出现次数相等，则从s[i]开始的字符串是words的串联子串
            if (isEqual(sMap, wordsMap)) {
                list.add(i);
            }
        }

        return list;
    }

    /**
     * 滑动窗口，双指针
     * 根据words中单词长度，确定s遍历的起始下标索引为[0-words[0].length()-1]，
     * 从起始下标索引开始，将右指针所指单词单词加入sMap中，右指针右移，当滑动窗口的大小words中单词总长度时，
     * 判断s[left]-s[right-1]中单词，即sMap是否是words的串联子串，如果是，则将左指针下标索引加入结果集合；
     * 如果不是，则左指针所指单词从sMap删除的单词，左指针右移
     * 时间复杂度O(s.length()*wordLen)，空间复杂度O(wordCont*wordLen)
     * (wordCont：words中单词数量，wordLen：words中每个单词长度)
     *
     * @param s
     * @param words
     * @return
     */
    public List<Integer> findSubstring2(String s, String[] words) {
        if (s == null || s.length() == 0 || words == null || words.length == 0) {
            return new ArrayList<>();
        }

        //words中单词数量
        int wordCont = words.length;
        //words中每个单词长度
        int wordLen = words[0].length();

        //words中字符串的总长度超过s的长度，则不存在s的串联子串，直接返回
        if (s.length() < wordCont * wordLen) {
            return new ArrayList<>();
        }

        Map<String, Integer> wordsMap = new HashMap<>();

        for (String word : words) {
            wordsMap.put(word, wordsMap.getOrDefault(word, 0) + 1);
        }

        List<Integer> list = new ArrayList<>();

        //起始索引为s[0]-s[wordLen-1]，从起始索引开始滑动窗口
        for (int i = 0; i < wordLen; i++) {
            Map<String, Integer> sMap = new HashMap<>();
            int left = i;
            int right = i;

            while (right + wordLen <= s.length()) {
                //右指针要添加到sMap的单词
                String word = s.substring(right, right + wordLen);
                sMap.put(word, sMap.getOrDefault(word, 0) + 1);
                //右指针右移
                right = right + wordLen;

                //当前窗口大小满足要求，判断s[left]-s[right-1]是否是words的串联子串
                if (right - left == wordCont * wordLen) {
                    if (isEqual(sMap, wordsMap)) {
                        //left加入结果集合
                        list.add(left);
                    }

                    //左指针要从sMap删除的单词
                    String leftWord = s.substring(left, left + wordLen);
                    sMap.put(leftWord, sMap.get(leftWord) - 1);
                    //左指针右移
                    left = left + wordLen;
                }
            }
        }

        return list;
    }

    /**
     * sMap和wordsMap中包含的字符串及出现次数是否相等
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @param sMap
     * @param wordsMap
     * @return
     */
    private boolean isEqual(Map<String, Integer> sMap, Map<String, Integer> wordsMap) {
        //只能遍历wordsMap中的Entry，因为sMap中有可能存在key，但对应value为0的情况
        for (Map.Entry<String, Integer> entry : wordsMap.entrySet()) {
            //Integer和Integer之间比较会自动装箱，只能使用equals()，不能使用==，==比较的是地址是否相等
            if (!sMap.containsKey(entry.getKey()) || !sMap.get(entry.getKey()).equals(entry.getValue())) {
                return false;
            }
        }
        return true;
    }
}
