package com.zhang.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date 2024/5/14 09:10
 * @Author zsy
 * @Description 单词替换 前缀树类比
 * 在英语中，我们有一个叫做 词根(root) 的概念，可以词根 后面 添加其他一些词组成另一个较长的单词——我们称这个词为 继承词 (successor)。
 * 例如，词根 help，跟随着 继承词 "ful"，可以形成新的单词 "helpful"。
 * 现在，给定一个由许多 词根 组成的词典 dictionary 和一个用空格分隔单词形成的句子 sentence。
 * 你需要将句子中的所有 继承词 用 词根 替换掉。
 * 如果 继承词 有许多可以形成它的 词根，则用 最短 的 词根 替换它。
 * 你需要输出替换之后的句子。
 * <p>
 * 输入：dictionary = ["cat","bat","rat"], sentence = "the cattle was rattled by the battery"
 * 输出："the cat was rat by the bat"
 * <p>
 * 输入：dictionary = ["a","b","c"], sentence = "aadsfasf absbs bbab cadsfafs"
 * 输出："a a b c"
 * <p>
 * 1 <= dictionary.length <= 1000
 * 1 <= dictionary[i].length <= 100
 * dictionary[i] 仅由小写字母组成。
 * 1 <= sentence.length <= 10^6
 * sentence 仅由小写字母和空格组成。
 * sentence 中单词的总量在范围 [1, 1000] 内。
 * sentence 中每个单词的长度在范围 [1, 1000] 内。
 * sentence 中单词之间由一个空格隔开。
 * sentence 没有前导或尾随空格。
 */
public class Problem648 {
    public static void main(String[] args) {
        Problem648 problem648 = new Problem648();
        List<String> dictionary = new ArrayList<String>() {{
            add("cat");
            add("bat");
            add("rat");
        }};
        String sentence = "the cattle was rattled by the battery";
        System.out.println(problem648.replaceWords(dictionary, sentence));
    }

    /**
     * 前缀树
     * 时间复杂度O(n*disctionary[i].length()+m)，空间复杂度O(n*disctionary[i].length()) (n=dictionary.size()，m=sentence.length())
     *
     * @param dictionary
     * @param sentence
     * @return
     */
    public String replaceWords(List<String> dictionary, String sentence) {
        Trie trie = new Trie();

        for (String word : dictionary) {
            trie.insert(word);
        }

        StringBuilder sb = new StringBuilder();
        //sentence中单词数组
        String[] wordArr = sentence.split(" ");

        //在前缀树中查询word的最短词根
        for (String word : wordArr) {
            sb.append(trie.search(word)).append(" ");
        }

        //删除末尾空格
        return sb.delete(sb.length() - 1, sb.length()).toString();
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
            }

            node.isEnd = true;
        }

        /**
         * 返回前缀树中word的最短词根，如果前缀树中没有word的词根，则返回word
         * 时间复杂度O(n)，空间复杂度O(n)
         *
         * @param word
         * @return
         */
        public String search(String word) {
            StringBuilder sb = new StringBuilder();
            TrieNode node = root;

            for (char c : word.toCharArray()) {
                //前缀树中没有当前字符c，则前缀树中没有word的词根，则返回word
                if (!node.children.containsKey(c)) {
                    return word;
                }

                node = node.children.get(c);
                sb.append(c);

                //前缀树中找到最短的词根，则返回sb.toString()
                if (node.isEnd) {
                    return sb.toString();
                }
            }

            //遍历结束，前缀树中没有word的词根，则返回word
            return word;
        }

        /**
         * 前缀树节点
         */
        private static class TrieNode {
            private final Map<Character, TrieNode> children;
            private boolean isEnd;

            public TrieNode() {
                children = new HashMap<>();
                isEnd = false;
            }
        }
    }
}
