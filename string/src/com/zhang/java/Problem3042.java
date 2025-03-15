package com.zhang.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date 2025/4/23 08:48
 * @Author zsy
 * @Description 统计前后缀下标对 I 同Problem3045 字符串哈希类比Problem187、Problem1044、Problem1062、Problem1316、Problem1392、Problem1698、Problem3029、Problem3031、Problem3045、Problem3076 前缀树类比
 * 给你一个下标从 0 开始的字符串数组 words 。
 * 定义一个 布尔 函数 isPrefixAndSuffix ，它接受两个字符串参数 str1 和 str2 ：
 * 当 str1 同时是 str2 的前缀（prefix）和后缀（suffix）时，isPrefixAndSuffix(str1, str2) 返回 true，否则返回 false。
 * 例如，isPrefixAndSuffix("aba", "ababa") 返回 true，因为 "aba" 既是 "ababa" 的前缀，也是 "ababa" 的后缀，
 * 但是 isPrefixAndSuffix("abc", "abcd") 返回 false。
 * 以整数形式，返回满足 i < j 且 isPrefixAndSuffix(words[i], words[j]) 为 true 的下标对 (i, j) 的 数量 。
 * <p>
 * 输入：words = ["a","aba","ababa","aa"]
 * 输出：4
 * 解释：在本示例中，计数的下标对包括：
 * i = 0 且 j = 1 ，因为 isPrefixAndSuffix("a", "aba") 为 true 。
 * i = 0 且 j = 2 ，因为 isPrefixAndSuffix("a", "ababa") 为 true 。
 * i = 0 且 j = 3 ，因为 isPrefixAndSuffix("a", "aa") 为 true 。
 * i = 1 且 j = 2 ，因为 isPrefixAndSuffix("aba", "ababa") 为 true 。
 * 因此，答案是 4 。
 * <p>
 * 输入：words = ["pa","papa","ma","mama"]
 * 输出：2
 * 解释：在本示例中，计数的下标对包括：
 * i = 0 且 j = 1 ，因为 isPrefixAndSuffix("pa", "papa") 为 true 。
 * i = 2 且 j = 3 ，因为 isPrefixAndSuffix("ma", "mama") 为 true 。
 * 因此，答案是 2 。
 * <p>
 * 输入：words = ["abab","ab"]
 * 输出：0
 * 解释：在本示例中，唯一有效的下标对是 i = 0 且 j = 1 ，但是 isPrefixAndSuffix("abab", "ab") 为 false 。
 * 因此，答案是 0 。
 * <p>
 * 1 <= words.length <= 50
 * 1 <= words[i].length <= 10
 * words[i] 仅由小写英文字母组成。
 */
public class Problem3042 {
    public static void main(String[] args) {
        Problem3042 problem3042 = new Problem3042();
//        String[] words = {"a", "aba", "ababa", "aa"};
        String[] words = {"a", "abb"};
        System.out.println(problem3042.countPrefixSuffixPairs(words));
        System.out.println(problem3042.countPrefixSuffixPairs2(words));
    }

    /**
     * 字符串哈希
     * hash[i]：s[0]-s[i-1]的哈希值
     * prime[i]：p^i的值
     * hash[j+1]-hash[i]*prime[j-i+1]：s[i]-s[j]的哈希值
     * 核心思想：将字符串看成P进制数，再对MOD取余，作为当前字符串的哈希值，只要两个字符串哈希值相等，则认为两个字符串相等
     * 一般取P为较大的质数，P=131或P=13331或P=131313，此时产生的哈希冲突低；
     * 一般取MOD=2^63(long类型最大值+1)，在计算时不处理溢出问题，产生溢出相当于自动对MOD取余；
     * 如果产生哈希冲突，则使用双哈希来减少冲突
     * 时间复杂度O(mn+n^2)，空间复杂度O(mn) (n=words.length，m=words[i].length())
     *
     * @param words
     * @return
     */
    public int countPrefixSuffixPairs(String[] words) {
        //words[i]的最大长度
        int maxLen = 0;

        for (String word : words) {
            maxLen = Math.max(maxLen, word.length());
        }

        //大质数，p进制
        int p = 131;
        List<List<Long>> hash = new ArrayList<>();
        List<Long> prime = new ArrayList<>();

        for (int i = 0; i < words.length; i++) {
            hash.add(new ArrayList<>());
            //每个hash[i][0]初始化为p^0
            hash.get(i).add(0L);
        }

        prime.add(1L);

        for (int i = 1; i <= maxLen; i++) {
            //注意：不需要进行取模运算，产生溢出相当于自动对MOD取模
            prime.add(prime.get(i - 1) * p);
        }

        for (int i = 0; i < words.length; i++) {
            String word = words[i];

            for (int j = 0; j < word.length(); j++) {
                char c = word.charAt(j);
                hash.get(i).add(hash.get(i).get(j) * p + c);
            }
        }

        int count = 0;

        //判断words[i]是否是words[j]的前缀和后缀
        for (int i = 0; i < words.length - 1; i++) {
            //words[i][0]-words[i][words[i].length()-1]的哈希值
            long hash1 = hash.get(i).get(words[i].length()) - hash.get(i).get(0) * prime.get(words[i].length());

            for (int j = i + 1; j < words.length; j++) {
                //words[i]的长度小于words[j]的长度，则words[i]不是words[j]的前缀和后缀，直接进行下次循环
                if (words[i].length() > words[j].length()) {
                    continue;
                }

                //words[j][0]-words[j][words[i].length()-1]的哈希值
                long hash2 = hash.get(j).get(words[i].length()) - hash.get(j).get(0) * prime.get(words[i].length());
                //words[j][words[j].length()-words[i].length()]-words[j][words[j].length()-1]的哈希值
                long hash3 = hash.get(j).get(words[j].length()) - hash.get(j).get(words[j].length() - words[i].length()) * prime.get(words[i].length());

                if (hash1 == hash2 && hash1 == hash3) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * 前缀树
     * 前缀树中节点存储当前遍历到的前缀字符word[i]和与之对应的后缀字符word[n-1-i]2个字符构成的值
     * 时间复杂度O(mn)，空间复杂度O(mn) (n=words.length，m=words[i].length())
     *
     * @param words
     * @return
     */
    public int countPrefixSuffixPairs2(String[] words) {
        int count = 0;
        Trie trie = new Trie();

        for (String word : words) {
            //注意：先查询前缀树中满足word的前缀和后缀单词，再将word加入前缀树
            count = count + trie.search(word);
            trie.insert(word);
        }

        return count;
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

            for (int i = 0; i < word.length(); i++) {
                char c1 = word.charAt(i);
                char c2 = word.charAt(word.length() - 1 - i);
                //word[i]和word[n-1-i]2个字符构成的值作为key
                int key = (c1 - 'a') * 26 + (c2 - 'a');

                if (!node.children.containsKey(key)) {
                    node.children.put(key, new TrieNode());
                }

                node = node.children.get(key);
            }

            node.count++;
            node.isEnd = true;
        }

        private int search(String word) {
            int count = 0;
            TrieNode node = root;

            for (int i = 0; i < word.length(); i++) {
                char c1 = word.charAt(i);
                char c2 = word.charAt(word.length() - 1 - i);
                //word[i]和word[n-1-i]2个字符构成的值作为key
                int key = (c1 - 'a') * 26 + (c2 - 'a');

                if (!node.children.containsKey(key)) {
                    break;
                }

                node = node.children.get(key);

                //根节点到当前节点的字符串是word的前缀和后缀
                if (node.isEnd) {
                    count = count + node.count;
                }
            }

            return count;
        }

        /**
         * 前缀树节点
         */
        private static class TrieNode {
            //注意：节点存储当前遍历到的前缀字符word[i]和与之对应的后缀字符word[n-1-i]2个字符构成的值
            private final Map<Integer, TrieNode> children;
            //前缀树中根节点到当前节点作为words中单词的前缀和后缀的字符串个数
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
