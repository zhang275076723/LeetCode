package com.zhang.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date 2024/5/22 09:03
 * @Author zsy
 * @Description 单词方块 前缀树类比 回溯+剪枝类比
 * 给定一个单词集合 words （没有重复），找出并返回其中所有的 单词方块 。
 * words 中的同一个单词可以被 多次 使用。你可以按 任意顺序 返回答案。
 * 一个单词序列形成了一个有效的 单词方块 的意思是指从第 k 行和第 k 列  0 <= k < max(numRows, numColumns) 来看都是相同的字符串。
 * 例如，单词序列 ["ball","area","lead","lady"] 形成了一个单词方块，因为每个单词从水平方向看和从竖直方向看都是相同的。
 * <p>
 * 输入：words = ["area","lead","wall","lady","ball"]
 * 输出: [["ball","area","lead","lady"],
 * ["wall","area","lead","lady"]]
 * 解释：
 * 输出包含两个单词方块，输出的顺序不重要，只需要保证每个单词方块内的单词顺序正确即可。
 * <p>
 * 输入：words = ["abat","baba","atan","atal"]
 * 输出：[["baba","abat","baba","atal"],
 * ["baba","abat","baba","atan"]]
 * 解释：
 * 输出包含两个单词方块，输出的顺序不重要，只需要保证每个单词方块内的单词顺序正确即可。
 * <p>
 * 1 <= words.length <= 1000
 * 1 <= words[i].length <= 4
 * words[i] 长度相同
 * words[i] 只由小写英文字母组成
 * words[i] 都 各不相同
 */
public class Problem425 {
    public static void main(String[] args) {
        Problem425 problem425 = new Problem425();
        String[] words = {"area", "lead", "wall", "lady", "ball"};
        System.out.println(problem425.wordSquares(words));
    }

    /**
     * 前缀树+回溯+剪枝
     * words中单词加入前缀树中，每次单词方块中加入新的一行单词，需要根据之前单词方块中已经添加的单词，得到新的一行单词前缀，
     * 通过单词前缀在前缀树中查询新的一行单词
     * 时间复杂度O(mn+m*n!)，空间复杂度O(mn) (n=words.length，m=words[i].length())
     * <p>
     * b a l l
     * a r e a
     * l e a d
     * l a d y
     * 例如：遍历到第2行单词时，第2行第0列字符必须为第0行第2列字符'l'，第2行第1列字符必须为第1行第2列字符'e'，
     * 则第2行单词前缀必须为"le"，需要通过前缀树中查询前缀为"le"的单词
     *
     * @param words
     * @return
     */
    public List<List<String>> wordSquares(String[] words) {
        Trie trie = new Trie();

        //words中单词加入前缀树中
        for (int i = 0; i < words.length; i++) {
            trie.insert(words[i], i);
        }

        List<List<String>> result = new ArrayList<>();

        for (String word : words) {
            List<String> list = new ArrayList<>();
            list.add(word);

            backtrack(trie, words, list, result);

            list.remove(list.size() - 1);
        }

        return result;
    }

    private void backtrack(Trie trie, String[] words, List<String> list, List<List<String>> result) {
        if (list.size() == words[0].length()) {
            result.add(new ArrayList<>(list));
            return;
        }

        int index = list.size();
        StringBuilder sb = new StringBuilder();

        //每次单词方块中加入新的一行单词，需要根据之前单词方块中已经添加的单词，得到新的一行单词前缀，
        //即word[index]构成新的一行单词的前缀
        for (String word : list) {
            sb.append(word.charAt(index));
        }

        //通过单词前缀在前缀树中查询新的一行单词
        List<Integer> prefixList = trie.search(sb.toString());

        for (int i = 0; i < prefixList.size(); i++) {
            //新的一行单词
            String word = words[prefixList.get(i)];
            list.add(word);

            backtrack(trie, words, list, result);

            list.remove(list.size() - 1);
        }
    }

    /**
     * 前缀树
     */
    private static class Trie {
        private final TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        public void insert(String word, int index) {
            TrieNode node = root;

            for (char c : word.toCharArray()) {
                if (!node.children.containsKey(c)) {
                    node.children.put(c, new TrieNode());
                }

                node = node.children.get(c);
                node.list.add(index);
            }

            node.isEnd = true;
        }

        /**
         * 查询前缀树中前缀为prefix的words中字符串下标索引集合
         * 时间复杂度O(n)，空间复杂度O(1)
         *
         * @param prefix
         * @return
         */
        public List<Integer> search(String prefix) {
            TrieNode node = root;

            for (char c : prefix.toCharArray()) {
                node = node.children.get(c);

                //不存在当前节点，则前缀树中不存在prefix，返回空集合
                if (node == null) {
                    return new ArrayList<>();
                }
            }

            return node.list;
        }

        /**
         * 前缀树节点
         */
        private static class TrieNode {
            private final Map<Character, TrieNode> children;
            //根节点到当前节点构成的字符串是words中前缀的下标索引
            private final List<Integer> list;
            private boolean isEnd;

            public TrieNode() {
                children = new HashMap<>();
                list = new ArrayList<>();
                isEnd = false;
            }
        }
    }
}
