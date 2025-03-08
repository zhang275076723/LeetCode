package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/5/21 08:59
 * @Author zsy
 * @Description 给字符串添加加粗标签 同Problem758 类比Problem758、Problem1065 前缀树类比 区间类比
 * 给定字符串 s 和字符串数组 words。
 * 对于 s 内部的子字符串，若其存在于 words 数组中，则通过添加闭合的粗体标签 <b> 和 </b> 进行加粗标记。
 * 如果两个这样的子字符串重叠，你应该仅使用一对闭合的粗体标签将它们包围起来。
 * 如果被粗体标签包围的两个子字符串是连续的，你应该将它们合并。
 * 返回添加加粗标签后的字符串 s 。
 * <p>
 * 输入： s = "abcxyz123", words = ["abc","123"]
 * 输出："<b>abc</b>xyz<b>123</b>"
 * 解释：两个单词字符串是 s 的子字符串，如下所示: "abcxyz123"。
 * 我们在每个子字符串之前添加<b>，在每个子字符串之后添加</b>。
 * <p>
 * 输入：s = "aaabbb", words = ["aa","b"]
 * 输出："<b>aaabbb</b>"
 * 解释：
 * "aa"作为子字符串出现了两次: "aaabbb" 和 "aaabbb"。
 * "b"作为子字符串出现了三次: "aaabbb"、"aaabbb" 和 "aaabbb"。
 * 我们在每个子字符串之前添加<b>，在每个子字符串之后添加</b>: "<b>a<b>a</b>a</b><b>b</b><b>b</b><b>b</b>"。
 * 由于前两个<b>重叠，把它们合并得到: "<b>aaa</b><b>b</b><b>b</b><b>b</b>"。
 * 由于现在这四个<b>是连续的，把它们合并得到: "<b>aaabbb</b>"。
 * <p>
 * 1 <= s.length <= 1000
 * 0 <= words.length <= 100
 * 1 <= words[i].length <= 1000
 * s 和 words[i] 由英文字母和数字组成
 * words 中的所有值 互不相同
 */
public class Problem616 {
    public static void main(String[] args) {
        Problem616 problem616 = new Problem616();
//        String s = "abcxyz123";
//        String[] words = {"abc", "123"};
        String s = "aaabbb";
        String[] words = {"aa", "b"};
        System.out.println(problem616.addBoldTag(s, words));
    }

    /**
     * 前缀树+合并区间
     * 1、words中字符串加入前缀树中
     * 2、遍历s，得到s中需要进行合并的区间集合
     * 3、重叠区间、相邻区间、区间左右边界相差1的区间进行合并
     * 4、合并后的区间添加粗体标签
     * 时间复杂度O(mp+n^2)，空间复杂度O(mp+n) (n=s.length()，m=words.length，p=words[i].length())
     *
     * @param s
     * @param words
     * @return
     */
    public String addBoldTag(String s, String[] words) {
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
