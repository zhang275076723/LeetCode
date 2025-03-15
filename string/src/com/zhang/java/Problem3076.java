package com.zhang.java;

import java.util.*;

/**
 * @Date 2025/4/27 08:02
 * @Author zsy
 * @Description 数组中的最短非公共子字符串 字符串哈希类比Problem187、Problem1044、Problem1062、Problem1316、Problem1392、Problem1698、Problem3029、Problem3031、Problem3042、Problem3045 前缀树类比
 * 给你一个数组 arr ，数组中有 n 个 非空 字符串。
 * 请你求出一个长度为 n 的字符串数组 answer ，满足：
 * answer[i] 是 arr[i] 最短 的子字符串，且它不是 arr 中其他任何字符串的子字符串。
 * 如果有多个这样的子字符串存在，answer[i] 应该是它们中字典序最小的一个。
 * 如果不存在这样的子字符串，answer[i] 为空字符串。
 * 请你返回数组 answer 。
 * <p>
 * 输入：arr = ["cab","ad","bad","c"]
 * 输出：["ab","","ba",""]
 * 解释：求解过程如下：
 * - 对于字符串 "cab" ，最短没有在其他字符串中出现过的子字符串是 "ca" 或者 "ab" ，我们选择字典序更小的子字符串，也就是 "ab" 。
 * - 对于字符串 "ad" ，不存在没有在其他字符串中出现过的子字符串。
 * - 对于字符串 "bad" ，最短没有在其他字符串中出现过的子字符串是 "ba" 。
 * - 对于字符串 "c" ，不存在没有在其他字符串中出现过的子字符串。
 * <p>
 * 输入：arr = ["abc","bcd","abcd"]
 * 输出：["","","abcd"]
 * 解释：求解过程如下：
 * - 对于字符串 "abc" ，不存在没有在其他字符串中出现过的子字符串。
 * - 对于字符串 "bcd" ，不存在没有在其他字符串中出现过的子字符串。
 * - 对于字符串 "abcd" ，最短没有在其他字符串中出现过的子字符串是 "abcd" 。
 * <p>
 * n == arr.length
 * 2 <= n <= 100
 * 1 <= arr[i].length <= 20
 * arr[i] 只包含小写英文字母。
 */
public class Problem3076 {
    public static void main(String[] args) {
        Problem3076 problem3076 = new Problem3076();
        String[] arr = {"cab", "ad", "bad", "c"};
//        String[] arr = {"abc", "bcd", "abcd"};
        System.out.println(Arrays.toString(problem3076.shortestSubstrings(arr)));
        System.out.println(Arrays.toString(problem3076.shortestSubstrings2(arr)));
    }

    /**
     * 字符串哈希+哈希表
     * 时间复杂度O(n*m^2*(m+n))，空间复杂度O(n*m^2) (n=arr.length，m=arr[i].length())
     *
     * @param arr
     * @return
     */
    public String[] shortestSubstrings(String[] arr) {
        //arr[i]的最大长度
        int maxLen = 0;

        for (String str : arr) {
            maxLen = Math.max(maxLen, str.length());
        }

        //大质数，p进制
        int p = 131;
        List<List<Long>> hash = new ArrayList<>();
        List<Long> prime = new ArrayList<>();

        for (int i = 0; i < arr.length; i++) {
            hash.add(new ArrayList<>());
            //每个hash[i][0]初始化为p^0
            hash.get(i).add(0L);
        }

        prime.add(1L);

        for (int i = 1; i <= maxLen; i++) {
            //注意：不需要进行取模运算，产生溢出相当于自动对MOD取模
            prime.add(prime.get(i - 1) * p);
        }

        //key：arr[i]在arr的下标索引，value：arr[i]中每个子串的哈希值集合
        Map<Integer, Set<Long>> map = new HashMap<>();

        //计算arr[i]中每个子串的哈希值，并放入map中
        for (int i = 0; i < arr.length; i++) {
            String str = arr[i];

            for (int j = 0; j < str.length(); j++) {
                char c = str.charAt(j);
                hash.get(i).add(hash.get(i).get(j) * p + c);
            }

            map.put(i, new HashSet<>());

            //str长度为j的子串str[k]-str[k+j-1]
            for (int j = 1; j <= str.length(); j++) {
                for (int k = 0; k <= str.length() - j; k++) {
                    //str[k]-str[k+j-1]的哈希值
                    long subStrHash = hash.get(i).get(k + j) - hash.get(i).get(k) * prime.get(j);
                    map.get(i).add(subStrHash);
                }
            }
        }

        String[] result = new String[arr.length];

        for (int i = 0; i < arr.length; i++) {
            String str = arr[i];
            //初始化为""
            result[i] = "";

            //判断str长度为j的子串str[k]-str[k+j-1]是否不是arr其他字符串的子串
            for (int j = 1; j <= str.length(); j++) {
                //str长度为j的子串是否是str的最短子串，并且最短子串不是arr其他字符串的子串的标志位
                boolean flag1 = false;

                for (int k = 0; k <= str.length() - j; k++) {
                    String subStr = str.substring(k, k + j);
                    long subStrHash = hash.get(i).get(k + j) - hash.get(i).get(k) * prime.get(j);
                    //str长度为j的子串str[k]-str[k+j-1]是否是arr其他字符串的子串的标志位
                    boolean flag2 = false;

                    for (int l = 0; l < arr.length; l++) {
                        if (l == i) {
                            continue;
                        }

                        if (map.get(l).contains(subStrHash)) {
                            flag2 = true;
                            break;
                        }
                    }

                    //str长度为j的子串str[k]-str[k+j-1]不是arr其他字符串的子串，则更新result[i]
                    if (!flag2) {
                        if (result[i].length() == 0 || subStr.compareTo(result[i]) < 0) {
                            result[i] = subStr;
                        }

                        flag1 = true;
                    }
                }

                //找到str的最短子串，则不需要继续往更长的子串查找，直接跳出循环
                if (flag1) {
                    break;
                }
            }
        }

        return result;
    }

    /**
     * 前缀树
     * 时间复杂度O(n*m^3)，空间复杂度O(n*m^2) (n=arr.length，m=arr[i].length())
     *
     * @param arr
     * @return
     */
    public String[] shortestSubstrings2(String[] arr) {
        Trie trie = new Trie();

        for (int i = 0; i < arr.length; i++) {
            trie.insert(arr[i], i);
        }

        String[] result = new String[arr.length];

        for (int i = 0; i < arr.length; i++) {
            result[i] = trie.search(arr[i]);
        }

        return result;
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
         * word中所有子串加入前缀树中，并且记录每个word的子串在arr的下标索引index
         * 时间复杂度O(n^2)，空间复杂度O(1)
         *
         * @param word
         * @param index
         */
        public void insert(String word, int index) {
            for (int i = 0; i < word.length(); i++) {
                TrieNode node = root;

                for (int j = i; j < word.length(); j++) {
                    char c = word.charAt(j);

                    if (!node.children.containsKey(c)) {
                        node.children.put(c, new TrieNode());
                    }

                    node = node.children.get(c);
                    node.set.add(index);
                    node.isEnd = true;
                }
            }
        }

        /**
         * 查询前缀树中word的最短子串，并且最短子串不是arr其他字符串的子串
         * 时间复杂度O(n^3)，空间复杂度O(1)
         *
         * @param word
         */
        public String search(String word) {
            String result = "";

            //判断word长度为i的子串word[j]-word[j+i-1]是否不是arr其他字符串的子串
            for (int i = 1; i <= word.length(); i++) {
                //word长度为i的子串是否是word的最短子串，并且最短子串不是arr其他字符串的子串的标志位
                boolean flag = false;

                for (int j = 0; j <= word.length() - i; j++) {
                    TrieNode node = root;

                    for (int k = j; k <= j + i - 1; k++) {
                        char c = word.charAt(k);
                        //注意：node不需要非空判断，因为word中所有子都已经插入前缀树中
                        node = node.children.get(c);
                    }

                    //根节点到当前节点的字符串只是word的子串，并不是arr其他字符串的子串，则更新result
                    if (node.set.size() == 1) {
                        if (result.length() == 0 || word.substring(j, j + i).compareTo(result) < 0) {
                            result = word.substring(j, j + i);
                        }

                        flag = true;
                    }
                }

                //找到word的最短子串，则不需要继续往更长的子串查找，直接跳出循环
                if (flag) {
                    break;
                }
            }

            return result;
        }

        /**
         * 前缀树节点
         */
        private static class TrieNode {
            private final Map<Character, TrieNode> children;
            //根节点到当前节点的字符串是arr中哪些字符串的子串的下标索引集合
            private final Set<Integer> set;
            private boolean isEnd;

            public TrieNode() {
                children = new HashMap<>();
                set = new HashSet<>();
                isEnd = false;
            }
        }
    }
}
