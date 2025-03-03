package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/5/23 09:18
 * @Author zsy
 * @Description 设计搜索自动补全系统 类比Problem71、Problem588、Problem609、Problem1166、Problem1233、Problem1268、Problem1500、Problem1948 前缀树类比 优先队列类比
 * 为搜索引擎设计一个搜索自动补全系统。用户会输入一条语句（最少包含一个字母，以特殊字符 '#' 结尾）。
 * 给定一个字符串数组 sentences 和一个整数数组 times ，长度都为 n ，
 * 其中 sentences[i] 是之前输入的句子， times[i] 是该句子输入的相应次数。
 * 对于除 ‘#’ 以外的每个输入字符，返回前 3 个历史热门句子，这些句子的前缀与已经输入的句子的部分相同。
 * 下面是详细规则：
 * 一条句子的热度定义为历史上用户输入这个句子的总次数。
 * 返回前 3 的句子需要按照热度从高到低排序（第一个是最热门的）。
 * 如果有多条热度相同的句子，请按照 ASCII 码的顺序输出（ASCII 码越小排名越前）。
 * 如果满足条件的句子个数少于 3 ，将它们全部输出。
 * 如果输入了特殊字符，意味着句子结束了，请返回一个空集合。
 * 实现 AutocompleteSystem 类：
 * AutocompleteSystem(String[] sentences, int[] times): 使用数组sentences 和 times 对对象进行初始化。
 * List<String> input(char c) 表示用户输入了字符 c 。
 * 如果 c == '#' ，则返回空数组 [] ，并将输入的语句存储在系统中。
 * 返回前 3 个历史热门句子，这些句子的前缀与已经输入的句子的部分相同。
 * 如果少于 3 个匹配项，则全部返回。
 * <p>
 * 输入：
 * ["AutocompleteSystem", "input", "input", "input", "input"]
 * [[["i love you", "island", "iroman", "i love leetcode"], [5, 3, 2, 2]], ["i"], [" "], ["a"], ["#"]]
 * 输出：
 * [null, ["i love you", "island", "i love leetcode"], ["i love you", "i love leetcode"], [], []]
 * 解释：
 * AutocompleteSystem obj = new AutocompleteSystem(["i love you", "island", "iroman", "i love leetcode"], [5, 3, 2, 2]);
 * obj.input("i"); // return ["i love you", "island", "i love leetcode"]. There are four sentences that have prefix "i". Among them, "ironman" and "i love leetcode" have same hot degree. Since ' ' has ASCII code 32 and 'r' has ASCII code 114, "i love leetcode" should be in front of "ironman". Also we only need to output top 3 hot sentences, so "ironman" will be ignored.
 * obj.input(" "); // return ["i love you", "i love leetcode"]. There are only two sentences that have prefix "i ".
 * obj.input("a"); // return []. There are no sentences that have prefix "i a".
 * obj.input("#"); // return []. The user finished the input, the sentence "i a" should be saved as a historical sentence in system. And the following input will be counted as a new search.
 * <p>
 * n == sentences.length
 * n == times.length
 * 1 <= n <= 100
 * 1 <= sentences[i].length <= 100
 * 1 <= times[i] <= 50
 * c 是小写英文字母， '#', 或空格 ' '
 * 每个被测试的句子将是一个以字符 '#' 结尾的字符 c 序列。
 * 每个被测试的句子的长度范围为 [1,200]
 * 每个输入句子中的单词用单个空格隔开。
 * input 最多被调用 5000 次
 */
public class Problem642 {
    public static void main(String[] args) {
        String[] sentences = {"i love you", "island", "iroman", "i love leetcode"};
        int[] times = {5, 3, 2, 2};
        AutocompleteSystem autocompleteSystem = new AutocompleteSystem(sentences, times);
        // return ["i love you", "island", "i love leetcode"].
        // There are four sentences that have prefix "i".
        // Among them, "ironman" and "i love leetcode" have same hot degree.
        // Since ' ' has ASCII code 32 and 'r' has ASCII code 114, "i love leetcode" should be in front of "ironman".
        // Also we only need to output top 3 hot sentences, so "ironman" will be ignored.
        System.out.println(autocompleteSystem.input('i'));
        // return ["i love you", "i love leetcode"]. There are only two sentences that have prefix "i ".
        System.out.println(autocompleteSystem.input(' '));
        // return []. There are no sentences that have prefix "i a".
        System.out.println(autocompleteSystem.input('a'));
        // return []. The user finished the input, the sentence "i a" should be saved as a historical sentence in system.
        // And the following input will be counted as a new search.
        System.out.println(autocompleteSystem.input('#'));
    }

    /**
     * 前缀树+dfs+优先队列，小根堆
     */
    static class AutocompleteSystem {
        //前缀树
        private final Trie trie;
        //当前输入的字符串
        private StringBuilder sb;

        public AutocompleteSystem(String[] sentences, int[] times) {
            trie = new Trie();
            sb = new StringBuilder();

            for (int i = 0; i < sentences.length; i++) {
                trie.insert(sentences[i], times[i]);
            }
        }

        public List<String> input(char c) {
            //当前字符为'#'，则返回空集合，并将输入的字符串sb加入前缀树中
            if (c == '#') {
                trie.insert(sb.toString(), 1);
                //sb重新赋值，用于下一个输入的字符串
                sb = new StringBuilder();
                return new ArrayList<>();
            }

            //当前输入的字符c加入sb中
            sb.append(c);
            //前缀树中查询当前输入的字符串，得到末尾字符对应的前缀树节点
            Trie.TrieNode node = trie.search(sb.toString());

            //前缀树中不存在当前输入的字符串，则返回空集合
            if (node == null) {
                return new ArrayList<>();
            }

            //小根堆，先按照节点表示的字符串出现的次数由小到大排序，再按照节点表示的字符串字典序由大到小排序
            PriorityQueue<Trie.TrieNode> priorityQueue = new PriorityQueue<>(new Comparator<Trie.TrieNode>() {
                @Override
                public int compare(Trie.TrieNode node1, Trie.TrieNode node2) {
                    if (node1.count != node2.count) {
                        return node1.count - node2.count;
                    } else {
                        return node2.str.compareTo(node1.str);
                    }
                }
            });

            //从node节点开始dfs找前缀树中当前节点的所有子节点，即找到所有前缀为当前输入的字符串的字符串
            dfs(node, priorityQueue);

            //注意：需要首添加，所以使用的是LinkedList
            LinkedList<String> list = new LinkedList<>();

            while (!priorityQueue.isEmpty()) {
                list.addFirst(priorityQueue.poll().str);
            }

            return list;
        }

        private void dfs(Trie.TrieNode node, PriorityQueue<Trie.TrieNode> priorityQueue) {
            if (node == null) {
                return;
            }

            //根节点到当前节点表示的字符串的前缀是当前输入的字符串，则当前节点加入小根堆
            if (node.isEnd) {
                priorityQueue.offer(node);

                if (priorityQueue.size() > 3) {
                    priorityQueue.poll();
                }
            }

            for (Trie.TrieNode nextNode : node.children.values()) {
                dfs(nextNode, priorityQueue);
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

            /**
             * word加入前缀树中，word出现次数增加time次
             * 时间复杂度O(n)，空间复杂度O(1)
             *
             * @param word
             * @param time
             */
            public void insert(String word, int time) {
                TrieNode node = root;

                for (char c : word.toCharArray()) {
                    if (!node.children.containsKey(c)) {
                        node.children.put(c, new TrieNode());
                    }

                    node = node.children.get(c);
                }

                node.str = word;
                node.count = node.count + time;
                node.isEnd = true;
            }

            /**
             * 返回前缀树中prefix的末尾前缀树节点
             * 时间复杂度O(n)，空间复杂度O(1)
             *
             * @param prefix
             * @return
             */
            public TrieNode search(String prefix) {
                TrieNode node = root;

                for (char c : prefix.toCharArray()) {
                    node = node.children.get(c);

                    //前缀树中不存在当前节点c，直接返回null
                    if (node == null) {
                        return null;
                    }
                }

                return node;
            }

            /**
             * 前缀树节点
             */
            private static class TrieNode {
                private final Map<Character, TrieNode> children;
                //根节点到当前节点表示的字符串
                private String str;
                //根节点到当前节点表示的字符串出现的次数
                private int count;
                private boolean isEnd;

                public TrieNode() {
                    children = new HashMap<>();
                    str = "";
                    count = 0;
                    isEnd = false;
                }
            }
        }
    }
}
