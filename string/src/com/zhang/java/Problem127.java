package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/2/12 08:10
 * @Author zsy
 * @Description 单词接龙 双向bfs类比Problem126、Problem1345
 * 字典 wordList 中从单词 beginWord 和 endWord 的 转换序列 是一个按下述规格形成的序列 beginWord -> s1 -> s2 -> ... -> sk：
 * 每一对相邻的单词只差一个字母。
 * 对于 1 <= i <= k 时，每个 si 都在 wordList 中。
 * 注意， beginWord 不需要在 wordList 中。
 * sk == endWord
 * 给你两个单词 beginWord 和 endWord 和一个字典 wordList ，返回 从 beginWord 到 endWord 的 最短转换序列 中的 单词数目 。
 * 如果不存在这样的转换序列，返回 0 。
 * <p>
 * 输入：beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
 * 输出：5
 * 解释：一个最短转换序列是 "hit" -> "hot" -> "dot" -> "dog" -> "cog", 返回它的长度 5。
 * <p>
 * 输入：beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]
 * 输出：0
 * 解释：endWord "cog" 不在字典中，所以无法进行转换。
 * <p>
 * 1 <= beginWord.length <= 10
 * endWord.length == beginWord.length
 * 1 <= wordList.length <= 5000
 * wordList[i].length == beginWord.length
 * beginWord、endWord 和 wordList[i] 由小写英文字母组成
 * beginWord != endWord
 * wordList 中的所有字符串 互不相同
 */
public class Problem127 {
    public static void main(String[] args) {
        Problem127 problem127 = new Problem127();
//        String beginWord = "hit";
//        String endWord = "cog";
//        List<String> wordList = new ArrayList<>(Arrays.asList("hot", "dot", "dog", "lot", "log", "cog"));
        String beginWord = "leet";
        String endWord = "code";
        List<String> wordList = new ArrayList<>(Arrays.asList("lest", "leet", "lose", "code", "lode", "robe", "lost"));
        //leet->lest->lost->lose->lode->code，转换序列长度为6
        System.out.println(problem127.ladderLength(beginWord, endWord, wordList));
        System.out.println(problem127.ladderLength2(beginWord, endWord, wordList));
    }
    /**
     * bfs (bfs确保最先得到最短转换序列中的单词个数)
     * bfs每次往外扩一层，将本次word能够转换的单词全部加入队列中，直至遍历到endWord
     * 时间复杂度O(m*n^2)，空间复杂度O(mn)
     * (n=wordList.size(), m=beginWord.length()=endWord.length()=wordList[i].length())
     *
     * @param beginWord
     * @param endWord
     * @param wordList
     * @return
     */
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        //wordList中不包含endWord，beginWord无法转换为endWord，返回0
        if (!wordList.contains(endWord)) {
            return 0;
        }

        Queue<String> queue = new LinkedList<>();
        //存储queue遍历到单词，作为访问标志
        Set<String> set = new HashSet<>();
        queue.offer(beginWord);
        set.add(beginWord);

        //bfs向外扩展的次数，beginWord转换为endWord的最短转换序列中的单词个数
        int count = 0;

        while (!queue.isEmpty()) {
            //count加1，表示bfs每次往外扩一层
            count++;
            int size = queue.size();

            //每次往外扩一层，将本次所有能够转换的word加入队列中
            for (int i = 0; i < size; i++) {
                String word = queue.poll();

                //遍历wordList，找word能够转换为的newWord
                for (String newWord : wordList) {
                    //newWord已经在set中，则newWord已被遍历，直接进行下次循环
                    if (set.contains(newWord)) {
                        continue;
                    }

                    //word能够转换为newWord，则将newWord加入队列，并加入set集合中
                    if (canConvert(word, newWord)) {
                        //newWord和endWord相等，则说明beginWord能够转换为endWord，返回count+1
                        if (newWord.equals(endWord)) {
                            return count + 1;
                        }

                        queue.offer(newWord);
                        set.add(newWord);
                    }
                }
            }
        }

        //beginWord无法转换为endWord，返回0
        return 0;
    }

    /**
     * 双向bfs+优化 (bfs确保最先得到最短转换序列中的单词个数)
     * 从beginWord和endWord同时开始bfs，bfs每次往外扩一层，将本次word能够转换的单词全部加入队列中，
     * 直至其中一个队列遍历到了另一个队列已经遍历过的单词，则找到了最短转换序列中的单词个数
     * 优化一：优先遍历两个队列中存储单词较少的队列，能够加快查询速度
     * 优化二：对于本次word，替换word中的某个字符，如果替换后的单词是wordList中的单词，则加入队列
     * 时间复杂度O(m^2*n)，空间复杂度O(mn)
     * (n=wordList.size(), m=beginWord.length()=endWord.length()=wordList[i].length())
     *
     * @param beginWord
     * @param endWord
     * @param wordList
     * @return
     */
    public int ladderLength2(String beginWord, String endWord, List<String> wordList) {
        //wordList中不包含endWord，beginWord无法转换为endWord，返回0
        if (!wordList.contains(endWord)) {
            return 0;
        }

        //从前往后存储queue1遍历到单词，作为访问标志，即先存储beginWord
        Set<String> set1 = new HashSet<>();
        //从后往前存储queue2遍历到单词，作为访问标志，即先存储endWord
        Set<String> set2 = new HashSet<>();
        //存储wordList中单词，用于判断当前单词某个字符进行替换之后是否在wordSet中，是否能够进行转换
        Set<String> wordSet = new HashSet<>(wordList);
        //注意：双向bfs，必须先将首尾节点在对应的set中设置为已访问，不能每次出队元素的时候再标记节点已访问
        set1.add(beginWord);
        set2.add(endWord);

        //从前往后遍历的队列，即从beginWord开始遍历
        Queue<String> queue1 = new LinkedList<>();
        //从后往前遍历的队列，即从endWord开始遍历
        Queue<String> queue2 = new LinkedList<>();
        queue1.offer(beginWord);
        queue2.offer(endWord);

        //双向bfs向外扩展的次数，得到beginWord转换为endWord的最短转换序列中的单词个数
        int count = 0;

        while (!queue1.isEmpty() && !queue2.isEmpty()) {
            //优化：优先遍历两个队列中存储单词较少的队列，能够加快查询速度
            //从前往后遍历的queue1中单词数量大于从后往前遍历的queue2中单词数量，两个队列和两个set交换，
            //queue1是两个队列中存储单词较少的队列，每次只遍历queue1
            if (queue1.size() > queue2.size()) {
                Queue<String> tempQueue = queue1;
                queue1 = queue2;
                queue2 = tempQueue;
                Set<String> tempSet = set1;
                set1 = set2;
                set2 = tempSet;
            }

            //count加1，表示bfs每次往外扩一层
            count++;
            //记录存储单词较少的队列中单词的数量
            int size = queue1.size();

            //queue1是两个队列中存储单词较少的队列
            for (int i = 0; i < size; i++) {
                //当前单词
                String word = queue1.poll();
                //当前单词word转换为char数组，便于字符替换
                char[] wordArr = word.toCharArray();

                //替换wordArr中某个字符，如果替换后的单词在wordSet中，则加入队列
                for (int j = 0; j < wordArr.length; j++) {
                    //保存之前的wordArr[j]，用于字符复原
                    char originChar = wordArr[j];

                    //wordArr[j]替换为字符k，判断替换之后的单词是否是wordList中单词
                    for (char k = 'a'; k <= 'z'; k++) {
                        wordArr[j] = k;
                        //替换wordArr[j]之后的单词
                        String newWord = new String(wordArr);

                        //newWord不是wordSet中单词，直接进行下次循环
                        if (!wordSet.contains(newWord)) {
                            continue;
                        }

                        //newWord已经在set1中，则newWord已被遍历，直接进行下次循环
                        if (set1.contains(newWord)) {
                            continue;
                        }

                        //newWord已经在set2中，双向dfs已经相交，则已经找到beginWord转换为endWord的最短转换序列中的单词个数
                        if (set2.contains(newWord)) {
                            return count + 1;
                        }

                        //newWord加入队列queue1，并在set1中标记为已访问
                        queue1.offer(newWord);
                        set1.add(newWord);
                    }

                    //wordArr[j]恢复为之前的字符，用于下个位置的字符替换
                    wordArr[j] = originChar;
                }
            }
        }

        //beginWord无法转换为endWord，返回0
        return 0;
    }

    /**
     * str1能否转换为str2
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param str1
     * @param str2
     * @return
     */
    private boolean canConvert(String str1, String str2) {
        if (str1.length() != str2.length()) {
            return false;
        }

        //str1和str2的不同字符次数
        int diff = 0;

        for (int i = 0; i < str1.length(); i++) {
            char c1 = str1.charAt(i);
            char c2 = str2.charAt(i);

            if (c1 != c2) {
                diff++;
            }

            //不同字符次数超过1个，则不能转换，直接返回false
            if (diff > 1) {
                return false;
            }
        }

        return diff == 1;
    }
}
