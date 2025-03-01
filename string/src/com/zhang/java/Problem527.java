package com.zhang.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date 2024/5/15 09:08
 * @Author zsy
 * @Description 单词缩写 类比Problem320、Problem408 类比Problem1804 前缀树类比
 * 给你一个字符串数组 words ，该数组由 互不相同 的若干字符串组成，请你找出并返回每个单词的 最小缩写 。
 * 生成缩写的规则如下：
 * 初始缩写由起始字母+省略字母的数量+结尾字母组成。
 * 若存在冲突，亦即多于一个单词有同样的缩写，则使用更长的前缀代替首字母，直到从单词到缩写的映射唯一。
 * 换而言之，最终的缩写必须只能映射到一个单词。
 * 若缩写并不比原单词更短，则保留原样。
 * <p>
 * 输入: words = ["like", "god", "internal", "me", "internet", "interval", "intension", "face", "intrusion"]
 * 输出: ["l2e","god","internal","me","i6t","interval","inte4n","f2e","intr4n"]
 * <p>
 * 输入：words = ["aa","aaa"]
 * 输出：["aa","aaa"]
 * <p>
 * 1 <= words.length <= 400
 * 2 <= words[i].length <= 400
 * words[i] 由小写英文字母组成
 * words 中的所有字符串 互不相同
 */
public class Problem527 {
    public static void main(String[] args) {
        Problem527 problem527 = new Problem527();
        List<String> words = new ArrayList<String>() {{
            add("like");
            add("god");
            add("internal");
            add("me");
            add("internet");
            add("interval");
            add("intension");
            add("face");
            add("intrusion");
        }};
        System.out.println(problem527.wordsAbbreviation(words));
    }

    /**
     * 哈希表+前缀树
     * 核心思想：只有首字符+尾字符+长度都相同的单词才插入同一颗前缀树中进行缩写
     * words中单词根据words[i]的首字符+尾字符+长度分组，加入不同的前缀树中，words[i]全部加入前缀树后，再次对word[i]进行查询，
     * 如果words[i]在前缀树中节点出现次数count为1，则找到了words[i]只出现1次的最短前缀；如果没有找到，则words[i]不能缩写
     * 时间复杂度O(n*words[i].length())，空间复杂度O(n*words[i].length()) (n=words.size())
     *
     * @param words
     * @return
     */
    public List<String> wordsAbbreviation(List<String> words) {
        //key：word的首字符+尾字符+长度组成的字符串，value：当前前缀树
        Map<String, Trie> trieMap = new HashMap<>();

        for (String word : words) {
            //word长度小于等于3，则不能缩写，直接进行下次循环
            if (word.length() <= 3) {
                continue;
            }

            //word的首字符+尾字符+长度组成的字符串
            String key = word.charAt(0) + word.charAt(word.length() - 1) + (word.length() + "");

            if (!trieMap.containsKey(key)) {
                trieMap.put(key, new Trie());
            }

            Trie trie = trieMap.get(key);
            //word加入对应的前缀树中
            trie.insert(word);
        }

        List<String> list = new ArrayList<>();

        for (String word : words) {
            //word长度小于等于3，则不能缩写，word直接加入list中
            if (word.length() <= 3) {
                list.add(word);
                continue;
            }

            //word的首字符+尾字符+长度组成的字符串
            String key = word.charAt(0) + word.charAt(word.length() - 1) + (word.length() + "");
            //word对应的前缀树
            Trie trie = trieMap.get(key);
            //word在前缀树中节点出现次数count为1的最短前缀下标索引
            int index = trie.search(word);

            //word缩写之后的长度小于word的长度，则word缩写加入list中
            if (index + 3 < word.length()) {
                list.add(word.substring(0, index + 1) + (word.length() - index - 2) + word.substring(word.length() - 1));
            } else {
                //word缩写之后的长度大于等于word的长度，则不能缩写，word直接加入list中
                list.add(word);
            }
        }

        return list;
    }

    /**
     * 前缀树
     */
    private static class Trie {
        private final TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        public void insert(String word) {
            TrieNode node = root;

            for (char c : word.toCharArray()) {
                if (!node.children.containsKey(c)) {
                    node.children.put(c, new TrieNode());
                }

                node = node.children.get(c);
                node.count++;
            }

            node.isEnd = true;
        }

        /**
         * 返回word在前缀树中节点出现次数count为1的最短前缀下标索引
         * 时间复杂度O(n)，空间复杂度O(1)
         *
         * @param word
         * @return
         */
        public int search(String word) {
            TrieNode node = root;
            int index = -1;

            for (char c : word.toCharArray()) {
                node = node.children.get(c);
                index++;

                //找到word在前缀树中节点出现次数count为1的最短前缀下标索引，返回index
                if (node.count == 1) {
                    return index;
                }
            }

            //遍历结束，则前缀树中只有word一个单词，返回index
            return index;
        }

        /**
         * 前缀树节点
         */
        private static class TrieNode {
            private final Map<Character, TrieNode> children;
            //前缀树中根节点到当前节点作为前缀的字符串个数
            private int count;
            private boolean isEnd;

            public TrieNode() {
                children = new HashMap<>();
                count = 0;
                isEnd = false;
            }
        }
    }
}
