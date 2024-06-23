package com.zhang.java;

/**
 * @Date 2024/6/23 09:34
 * @Author zsy
 * @Description 最大单词长度乘积 状态压缩类比
 * 给你一个字符串数组 words ，找出并返回 length(words[i]) * length(words[j]) 的最大值，并且这两个单词不含有公共字母。
 * 如果不存在这样的两个单词，返回 0 。
 * <p>
 * 输入：words = ["abcw","baz","foo","bar","xtfn","abcdef"]
 * 输出：16
 * 解释：这两个单词为 "abcw", "xtfn"。
 * <p>
 * 输入：words = ["a","ab","abc","d","cd","bcd","abcd"]
 * 输出：4
 * 解释：这两个单词为 "ab", "cd"。
 * <p>
 * 输入：words = ["a","aa","aaa","aaaa"]
 * 输出：0
 * 解释：不存在这样的两个单词。
 * <p>
 * 2 <= words.length <= 1000
 * 1 <= words[i].length <= 1000
 * words[i] 仅包含小写字母
 */
public class Problem318 {
    public static void main(String[] args) {
        Problem318 problem318 = new Problem318();
        String[] words = {"abcw", "baz", "foo", "bar", "xtfn", "abcdef"};
        System.out.println(problem318.maxProduct(words));
    }

    /**
     * 二进制状态压缩
     * words[i]只包含26个小写字母，则words[i]可以压缩为长度为26的int数字存储，
     * 两个压缩后的单词状态与运算为0，则这两个单词不含有公共字母
     * 时间复杂度O(mn+n^2)，空间复杂度O(n) (n=words.length，m=words[i].length())
     *
     * @param words
     * @return
     */
    public int maxProduct(String[] words) {
        int[] state = new int[words.length];

        for (int i = 0; i < words.length; i++) {
            for (char c : words[i].toCharArray()) {
                state[i] = state[i] | (1 << (c - 'a'));
            }
        }

        int max = 0;

        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < words.length; j++) {
                //两个压缩后的单词状态与运算为0，则这两个单词不含有公共字母
                if ((state[i] & state[j]) == 0) {
                    max = Math.max(max, words[i].length() * words[j].length());
                }
            }
        }

        return max;
    }
}
