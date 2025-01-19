package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/5/20 09:18
 * @Author zsy
 * @Description 搜索推荐系统 字节面试题 类比Problem71、Problem588、Problem609、Problem642、Problem1166、Problem1233、Problem1500、Problem1948 前缀树类比 优先队列类比
 * 给你一个产品数组 products 和一个字符串 searchWord ，products  数组中每个产品都是一个字符串。
 * 请你设计一个推荐系统，在依次输入单词 searchWord 的每一个字母后，推荐 products 数组中前缀与 searchWord 相同的最多三个产品。
 * 如果前缀相同的可推荐产品超过三个，请按字典序返回最小的三个。
 * 请你以二维列表的形式，返回在输入 searchWord 每个字母后相应的推荐产品的列表。
 * <p>
 * 输入：products = ["mobile","mouse","moneypot","monitor","mousepad"], searchWord = "mouse"
 * 输出：[
 * ["mobile","moneypot","monitor"],
 * ["mobile","moneypot","monitor"],
 * ["mouse","mousepad"],
 * ["mouse","mousepad"],
 * ["mouse","mousepad"]
 * ]
 * 解释：按字典序排序后的产品列表是 ["mobile","moneypot","monitor","mouse","mousepad"]
 * 输入 m 和 mo，由于所有产品的前缀都相同，所以系统返回字典序最小的三个产品 ["mobile","moneypot","monitor"]
 * 输入 mou， mous 和 mouse 后系统都返回 ["mouse","mousepad"]
 * <p>
 * 输入：products = ["havana"], searchWord = "havana"
 * 输出：[["havana"],["havana"],["havana"],["havana"],["havana"],["havana"]]
 * <p>
 * 输入：products = ["bags","baggage","banner","box","cloths"], searchWord = "bags"
 * 输出：[["baggage","bags","banner"],["baggage","bags","banner"],["baggage","bags"],["bags"]]
 * <p>
 * 输入：products = ["havana"], searchWord = "tatiana"
 * 输出：[[],[],[],[],[],[],[]]
 * <p>
 * 1 <= products.length <= 1000
 * 1 <= Σ products[i].length <= 2 * 10^4
 * products[i] 中所有的字符都是小写英文字母。
 * 1 <= searchWord.length <= 1000
 * searchWord 中所有字符都是小写英文字母。
 */
public class Problem1268 {
    public static void main(String[] args) {
        Problem1268 problem1268 = new Problem1268();
        String[] products = {"mobile", "mouse", "moneypot", "monitor", "mousepad"};
        String searchWord = "mouse";
        System.out.println(problem1268.suggestedProducts(products, searchWord));
    }

    /**
     * 前缀树+优先队列，大根堆
     * products中字符串插入前缀树中，在插入过程中，前缀树节点的大根堆存储字典序最小的3个前缀是根节点到当前节点的字符串
     * 时间复杂度O(mn)，空间复杂度O(mn) (n=products.length，m=products[i].length())
     *
     * @param products
     * @param searchWord
     * @return
     */
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        Trie trie = new Trie();

        for (String product : products) {
            trie.insert(product);
        }

        return trie.startsWith(searchWord);
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
         * word插入前缀树中，在插入过程中，前缀树节点的大根堆存储字典序最小的3个前缀是根节点到当前节点的字符串
         * 时间复杂度O(n)，空间复杂度O(1)
         *
         * @param word
         */
        public void insert(String word) {
            TrieNode node = root;

            for (char c : word.toCharArray()) {
                if (!node.children.containsKey(c)) {
                    node.children.put(c, new TrieNode());
                }

                node = node.children.get(c);
                //word加入当前节点大根堆中，作为products中前缀是根节点到当前节点的字符串
                node.priorityQueue.offer(word);

                //当前节点大根堆中元素个数大于3，则堆顶元素不是字典序最小的3个前缀字符串，堆顶元素出堆
                if (node.priorityQueue.size() > 3) {
                    node.priorityQueue.poll();
                }
            }

            node.isEnd = true;
        }

        /**
         * 返回products中字典序最小的3个前缀是word[0]、word[0]-word[1]、...、word[0]-word[word.length()-1]的字符串集合
         * 时间复杂度O(n)，空间复杂度O(1)
         *
         * @param word
         * @return
         */
        public List<List<String>> startsWith(String word) {
            List<List<String>> result = new ArrayList<>();
            TrieNode node = root;
            //当前节点是否为空标志位
            boolean flag = false;

            for (char c : word.toCharArray()) {
                //当前节点为空，或者当前节点不存在子节点c，则products中不存在字典序最小的前缀是根节点到当前节点的字符串，
                //result中加入空集合，直接进行下次循环
                if (flag || !node.children.containsKey(c)) {
                    flag = true;
                    result.add(new ArrayList<>());
                    continue;
                }

                node = node.children.get(c);
                //注意：需要首添加，所以使用的是LinkedList
                LinkedList<String> list = new LinkedList<>();

                while (!node.priorityQueue.isEmpty()) {
                    //因为是大根堆，所以堆顶元素不是字典序最小的，需要首添加保证list中是字典序最小的三个字符串
                    list.addFirst(node.priorityQueue.poll());
                }

                result.add(list);
            }

            return result;
        }

        /**
         * 前缀树节点
         */
        private static class TrieNode {
            private final Map<Character, TrieNode> children;
            //优先队列，大根堆，存储products中字典序最小的3个前缀是根节点到当前节点的字符串
            private final PriorityQueue<String> priorityQueue;
            private boolean isEnd;

            public TrieNode() {
                children = new HashMap<>();
                //大根堆
                priorityQueue = new PriorityQueue<>(new Comparator<String>() {
                    @Override
                    public int compare(String str1, String str2) {
                        return str2.compareTo(str1);
                    }
                });
                isEnd = false;
            }
        }
    }
}
