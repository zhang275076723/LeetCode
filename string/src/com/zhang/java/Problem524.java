package com.zhang.java;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @Date 2024/1/20 09:08
 * @Author zsy
 * @Description 通过删除字母匹配到字典里最长单词 类比Problem392、Problem522、Problem792、Problem1023 双指针类比
 * 给你一个字符串 s 和一个字符串数组 dictionary ，找出并返回 dictionary 中最长的字符串，该字符串可以通过删除 s 中的某些字符得到。
 * 如果答案不止一个，返回长度最长且字母序最小的字符串。
 * 如果答案不存在，则返回空字符串。
 * <p>
 * 输入：s = "abpcplea", dictionary = ["ale","apple","monkey","plea"]
 * 输出："apple"
 * <p>
 * 输入：s = "abpcplea", dictionary = ["a","b","c"]
 * 输出："a"
 * <p>
 * 1 <= s.length <= 1000
 * 1 <= dictionary.length <= 1000
 * 1 <= dictionary[i].length <= 1000
 * s 和 dictionary[i] 仅由小写英文字母组成
 */
public class Problem524 {
    public static void main(String[] args) {
        Problem524 problem524 = new Problem524();
        String s = "abpcplea";
        List<String> dictionary = new ArrayList<String>() {{
            add("ale");
            add("apple");
            add("monkey");
            add("plea");
        }};
        System.out.println(problem524.findLongestWord(s, dictionary));
        System.out.println(problem524.findLongestWord2(s, dictionary));
    }

    /**
     * 排序+双指针
     * dictionary中单词先按照长度由大到小排序，再按照字典序由小到大排序，
     * 遍历dictionary，如果当前单词dictionary[i]是s的子序列，则dictionary[i]是满足要求的长度最长且字母序最小的字符串
     * 时间复杂度O(m*t*logm+mn)，空间复杂度O(logm) (n=s.length()，m=dictionary.size()，t=dictionary[i]的平均长度)
     *
     * @param s
     * @param dictionary
     * @return
     */
    public String findLongestWord(String s, List<String> dictionary) {
        //dictionary中单词先按照长度由大到小排序，再按照字典序由小到大排序
        dictionary.sort(new Comparator<String>() {
            @Override
            public int compare(String str1, String str2) {
                if (str1.length() != str2.length()) {
                    return str2.length() - str1.length();
                } else {
                    return str1.compareTo(str2);
                }
            }
        });

        for (String word : dictionary) {
            //word长度大于s长度，则word不是s的子序列，直接进行下次循环
            if (word.length() > s.length()) {
                continue;
            }

            //word的下标索引
            int index = 0;

            for (int i = 0; i < s.length(); i++) {
                //word[index]和s[i]相等，index后移
                if (word.charAt(index) == s.charAt(i)) {
                    index++;
                }

                //index已经遍历完，则word是s的子序列，返回word
                if (index == word.length()) {
                    return word;
                }
            }
        }

        //遍历完dictionary，都没有dictionary[i]是s的子序列，返回空字符串
        return "";
    }

    /**
     * 动态规划
     * dp[i][j]：从s[i]开始往后字符'a'+j第一次出现的下标索引
     * dp[i][j] = i          (s[i] == 'a'+j)
     * dp[i][j] = dp[i+1][j] (s[i] != 'a'+j)
     * 时间复杂度O(n*|Σ|+m*t)=O(n+m*t)，空间复杂度O(n*|Σ|)=O(n)
     * (n=s.length()，m=dictionary.size()，t=dictionary[i]的平均长度，|Σ|=26，只包含小写字母)
     *
     * @param s
     * @param dictionary
     * @return
     */
    public String findLongestWord2(String s, List<String> dictionary) {
        //只包含小写字母，所以数组二维长度为26
        int[][] dp = new int[s.length() + 1][26];

        //dp初始化
        for (int j = 0; j < 26; j++) {
            //-1表示""中不存在字符'a'+j
            dp[s.length()][j] = -1;
        }

        for (int i = s.length() - 1; i >= 0; i--) {
            for (int j = 0; j < 26; j++) {
                //s[i]等于字符'a'+j，则从s[i]开始往后字符'a'+j第一次出现的下标索引为i
                if (s.charAt(i) == 'a' + j) {
                    dp[i][j] = i;
                } else {
                    //s[i]不等于字符'a'+j，则从s[i]开始往后字符'a'+j第一次出现的下标索引为dp[i+1][j]
                    dp[i][j] = dp[i + 1][j];
                }
            }
        }

        //结果字符串，初始化为空串
        String result = "";

        for (String word : dictionary) {
            //word是否是s的子序列的标志位
            boolean flag = true;
            //s的下标索引
            int index = 0;

            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);

                //从s[index]开始往后不存在字符c，则flag置为false，直接跳出循环
                if (dp[index][c - 'a'] == -1) {
                    flag = false;
                    break;
                } else {
                    //从s[index]开始往后存在字符c，更新index为从s[index]开始往后字符c第一次出现的下标索引dp[index][c-'a']+1
                    index = dp[index][c - 'a'] + 1;
                }
            }

            if (flag) {
                //word长度大于result长度，或者word长度等于result长度，并且word字典序小于result字典序，则更新result为word
                if (word.length() > result.length() || (word.length() == result.length() && word.compareTo(result) < 0)) {
                    result = word;
                }
            }
        }

        return result;
    }
}
