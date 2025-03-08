package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/5/24 08:41
 * @Author zsy
 * @Description 字符串中的加粗单词 同Problem616 类比Problem616、Problem1065 前缀树类比 区间类比
 * 给定一个关键词集合 words 和一个字符串 s，将所有 s 中出现的关键词 words[i] 加粗。
 * 所有在标签 <b> 和 </b> 中的字母都会加粗。
 * 加粗后返回 s 。
 * 返回的字符串需要使用尽可能少的标签，当然标签应形成有效的组合。
 * <p>
 * 输入: words = ["ab","bc"], s = "aabcd"
 * 输出: "a<b>abc</b>d"
 * 解释: 注意返回 "a<b>a<b>b</b>c</b>d" 会使用更多的标签，因此是错误的。
 * <p>
 * 输入: words = ["ab","cb"], s = "aabcd"
 * 输出: "a<b>ab</b>cd"
 * <p>
 * 1 <= s.length <= 500
 * 0 <= words.length <= 50
 * 1 <= words[i].length <= 10
 * s 和 words[i] 由小写英文字母组成
 */
public class Problem758 {
    public static void main(String[] args) {
        Problem758 problem758 = new Problem758();
        String[] words = {"ab", "bc"};
        String s = "aabcd";
        System.out.println(problem758.boldWords(words, s));
    }

    /**
     * 前缀树+合并区间
     * 1、words中字符串加入前缀树中
     * 2、遍历s，得到s中需要进行合并的区间集合
     * 3、重叠区间、相邻区间、区间左右边界相差1的区间进行合并
     * 4、合并后的区间添加粗体标签
     * 时间复杂度O(mp+n^2)，空间复杂度O(mp+n) (n=s.length()，m=words.length，p=words[i].length())
     *
     * @param words
     * @param s
     * @return
     */
    public String boldWords(String[] words, String s) {
        Trie trie = new Trie();

        //1、words中字符串加入前缀树中
        for (String word : words) {
            trie.insert(word);
        }

        //s中可以加粗的区间集合
        List<int[]> list = new ArrayList<>();

        //2、遍历s，得到s中需要进行合并的区间集合
        //注意：此时list中区间已经有序
        trie.search(s, list);

        //3、重叠区间、相邻区间、区间左右边界相差1的区间进行合并

        //list中合并区间之后的区间集合
        List<int[]> list2 = new ArrayList<>();

        int start = list.get(0)[0];
        int end = list.get(0)[1];

        for (int i = 1; i < list.size(); i++) {
            int[] arr = list.get(i);

            if (end + 1 < arr[0]) {
                list2.add(new int[]{start, end});
                start = arr[0];
                end = arr[1];
            } else {
                end = Math.max(end, arr[1]);
            }
        }

        //末尾区间加入list2
        list2.add(new int[]{start, end});

        //4、list2中合并后的区间添加粗体标签

        StringBuilder sb = new StringBuilder();
        //list2中当前遍历到的区间右边界+1，即得到两个添加粗体标签的字符串之间的不需要添加粗体标签的字符串
        int index = 0;

        for (int i = 0; i < list2.size(); i++) {
            int[] arr = list2.get(i);

            //s[index]-s[arr[0]-1]为不需要添加粗体标签的字符串
            if (index < arr[0]) {
                sb.append(s.substring(index, arr[0]));
            }

            //s[arr[0]]-s[arr[1]]为需要添加粗体标签的字符串
            sb.append("<b>");
            sb.append(s.substring(arr[0], arr[1] + 1));
            sb.append("</b>");

            //更新index
            index = arr[1] + 1;
        }

        //s[index]-s[s.length()-1]为不需要添加粗体标签的字符串
        if (index != s.length()) {
            sb.append(s.substring(index, s.length()));
        }

        return sb.toString();
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
         * 遍历s，得到s中需要进行合并的区间集合
         * 时间复杂度O(n^2)，空间复杂度O(1)
         *
         * @param s
         * @param list
         */
        public void search(String s, List<int[]> list) {
            for (int i = 0; i < s.length(); i++) {
                TrieNode node = root;

                for (int j = i; j < s.length(); j++) {
                    char c = s.charAt(j);
                    node = node.children.get(c);

                    //node不存在子节点c，则从s[j]往后找不到加粗区间，直接跳出循环
                    if (node == null) {
                        break;
                    }

                    //s[i]-s[j]是可以加粗的区间，加入list中
                    if (node.isEnd) {
                        list.add(new int[]{i, j});
                    }
                }
            }
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
