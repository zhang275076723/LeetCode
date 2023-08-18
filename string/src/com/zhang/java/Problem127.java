package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/2/12 08:10
 * @Author zsy
 * @Description 单词接龙 双向bfs类比Problem126、Problem433、Problem752、Problem1345
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
//        //hit->hot->dot->dog->cog，转换序列长度为5
//        List<String> wordList = new ArrayList<>(Arrays.asList("hot", "dot", "dog", "lot", "log", "cog"));
        String beginWord = "leet";
        String endWord = "code";
        //leet->lest->lost->lose->lode->code，转换序列长度为6
        List<String> wordList = new ArrayList<>(Arrays.asList("lest", "leet", "lose", "code", "lode", "robe", "lost"));
        System.out.println(problem127.ladderLength(beginWord, endWord, wordList));
        System.out.println(problem127.ladderLength2(beginWord, endWord, wordList));
    }

    /**
     * bfs (bfs确保最先得到最短转换序列中的单词个数)
     * bfs每次往外扩一层，将当前层中所有单词通过wordList变化能够得到的单词全部加入队列中，直至遍历到endWord，
     * 或全部遍历完都没有找到endWord，返回-1
     * 时间复杂度O(m^2*n)，空间复杂度O(mn) (m=beginWord.length()=endWord.length()，n=wordList.size())
     *
     * @param beginWord
     * @param endWord
     * @param wordList
     * @return
     */
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        //wordList中不包含endWord，则beginWord无法转换为endWord，返回0
        if (!wordList.contains(endWord)) {
            return 0;
        }

        Queue<String> queue = new LinkedList<>();
        //访问集合，存储当前已经访问到的单词
        Set<String> visitedSet = new HashSet<>();
        //有效单词集合，存储wordList中单词，O(1)判断当前单词是否是wordList中的单词
        Set<String> wordSet = new HashSet<>(wordList);
        queue.offer(beginWord);
        visitedSet.add(beginWord);

        //bfs向外扩展的次数，beginWord转换为endWord的最少次数
        int count = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            //每次往外扩一层，将本次所有能够转换的word加入队列中
            for (int i = 0; i < size; i++) {
                //当前单词
                String word = queue.poll();

                //当前单词已经转换为了endWord，则找到了beginWord转换为endWord的最少次数，直接返回count+1，
                //因为要返回beginWord转换为endWord的最少序列长度，所以返回count+1
                if (endWord.equals(word)) {
                    return count + 1;
                }

                //word转换为char数组，便于某一位的变化
                char[] wordArr = word.toCharArray();

                //变化word中的第j位
                for (int j = 0; j < wordArr.length; j++) {
                    //word中的第j位
                    char cur = wordArr[j];

                    //变化word中的第j位为k
                    for (char k = 'a'; k <= 'z'; k++) {
                        wordArr[j] = k;
                        //变化word中的第j位为k得到的单词
                        String newWord = new String(wordArr);

                        //newWord已经访问过，或者newWord不是wordSet中的单词，直接进行下次循环
                        if (visitedSet.contains(newWord) || !wordSet.contains(newWord)) {
                            continue;
                        }

                        //newWord加入队列，并且设置newWord已访问
                        queue.offer(newWord);
                        visitedSet.add(newWord);
                    }

                    //word中的第j位复原，用于第j+1位变化
                    wordArr[j] = cur;
                }
            }

            //count加1，表示bfs每次往外扩一层
            count++;
        }

        //bfs结束也没有到达endWord，即beginWord无法转换为endWord，返回0
        return 0;
    }

    /**
     * 双向bfs (bfs确保最先得到最短转换序列中的单词个数)
     * 从beginWord和endWord同时开始bfs，bfs每次往外扩一层，将当前队列当前层中所有单词通过wordList变化能够得到的单词全部加入另一个队列中，
     * 直至一个队列中包含了另一个队列中的单词，即双向bfs相交，或者全部遍历完都没有找到endWord，返回-1
     * 注意：双向bfs优先遍历两个队列中较少的队列，因为较少的队列，扩展一层得到的元素少，能够加快查询速度
     * 时间复杂度O(m^2*n)，空间复杂度O(mn) (m=beginWord.length()=endWord.length()，n=wordList.size())
     *
     * @param beginWord
     * @param endWord
     * @param wordList
     * @return
     */
    public int ladderLength2(String beginWord, String endWord, List<String> wordList) {
        //wordList中不包含endWord，则beginWord无法转换为endWord，返回0
        if (!wordList.contains(endWord)) {
            return 0;
        }

        //从前往后遍历的队列，即从beginWord开始遍历
        Queue<String> queue1 = new LinkedList<>();
        //从后往前遍历的队列，即从endWord开始遍历
        Queue<String> queue2 = new LinkedList<>();
        //从前往后遍历的访问集合，存储queue1已经访问到的单词
        Set<String> visitedSet1 = new HashSet<>();
        //从后往前遍历的访问集合，存储queue2已经访问到的单词
        Set<String> visitedSet2 = new HashSet<>();
        //有效单词集合，存储wordList中单词，O(1)判断当前单词是否是wordList中的单词
        Set<String> wordSet = new HashSet<>(wordList);
        queue1.offer(beginWord);
        queue2.offer(endWord);
        //注意：双向bfs，必须先将首尾节点在对应的set中设置为已访问，不能每次出队元素的时候再标记节点已访问
        visitedSet1.add(beginWord);
        visitedSet2.add(endWord);

        //双向bfs向外扩展的次数，两个队列相交，即beginWord转换为endWord的最少次数
        int count = 0;

        while (!queue1.isEmpty() && !queue2.isEmpty()) {
            //双向bfs优先遍历两个队列中较少的队列，因为较少的队列，扩展一层得到的元素少，能够加快查询速度
            if (queue1.size() > queue2.size()) {
                Queue<String> tempQueue = queue1;
                queue1 = queue2;
                queue2 = tempQueue;
                Set<String> tempSet = visitedSet1;
                visitedSet1 = visitedSet2;
                visitedSet2 = tempSet;
            }

            int size = queue1.size();

            for (int i = 0; i < size; i++) {
                //当前单词
                String word = queue1.poll();

                //word已经存在visitedSet2中，即双向bfs相交，则找到了beginWord转换为endWord的最少次数，直接返回count+1，
                //因为要返回beginWord转换为endWord的最少序列长度，所以返回count+1
                if (visitedSet2.contains(word)) {
                    return count + 1;
                }

                //word转换为char数组，便于某一位的变化
                char[] wordArr = word.toCharArray();

                //变化word中的第j位
                for (int j = 0; j < wordArr.length; j++) {
                    //word中的第j位
                    char cur = wordArr[j];

                    //变化word中的第j位为k
                    for (char k = 'a'; k <= 'z'; k++) {
                        wordArr[j] = k;
                        //变化word中的第j位为k得到的单词
                        String newWord = new String(wordArr);

                        //newWord已经访问过，或者newWord不是wordSet中的单词，直接进行下次循环
                        if (visitedSet1.contains(newWord) || !wordSet.contains(newWord)) {
                            continue;
                        }

                        //newWord加入队列queue1，并且设置newWord已访问
                        queue1.offer(newWord);
                        visitedSet1.add(newWord);
                    }

                    //word中的第j位复原，用于第j+1位变化
                    wordArr[j] = cur;
                }
            }

            //count加1，表示bfs每次往外扩一层
            count++;
        }

        //bfs结束也没有到达endWord，即beginWord无法转换为endWord，返回0
        return 0;
    }
}
