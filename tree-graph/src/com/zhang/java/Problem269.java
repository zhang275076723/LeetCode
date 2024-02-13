package com.zhang.java;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * @Date 2023/10/13 08:22
 * @Author zsy
 * @Description 火星词典 拓扑排序类比
 * 现有一种使用英语字母的火星语言，这门语言的字母顺序对你来说是未知的。
 * 给你一个来自这种外星语言字典的字符串列表 words ，words 中的字符串已经 按这门新语言的字母顺序进行了排序 。
 * 如果这种说法是错误的，并且给出的 words 不能对应任何字母的顺序，则返回 "" 。
 * 否则，返回一个按新语言规则的 字典递增顺序 排序的独特字符串。
 * 如果有多个解决方案，则返回其中 任意一个 。
 * <p>
 * 输入：words = ["wrt","wrf","er","ett","rftt"]
 * 输出："wertf"
 * <p>
 * 输入：words = ["z","x"]
 * 输出："zx"
 * <p>
 * 输入：words = ["z","x","z"]
 * 输出：""
 * 解释：不存在合法字母顺序，因此返回 "" 。
 * <p>
 * 1 <= words.length <= 100
 * 1 <= words[i].length <= 100
 * words[i] 仅由小写英文字母组成
 */
public class Problem269 {
    /**
     * dfs拓扑排序图中是否有环标志位
     */
    private boolean hasCircle = false;

    public static void main(String[] args) {
        Problem269 problem269 = new Problem269();
        String[] words = {"wrt", "wrf", "er", "ett", "rftt"};
        System.out.println(problem269.alienOrder(words));
        System.out.println(problem269.alienOrder2(words));
    }

    /**
     * dfs拓扑排序
     * 解释：我们熟悉的字母顺序为abcdefg...，这里新字母顺序，例如"wrt"和"wrf"，则存在t在f之前的新字母顺序
     * 遍历words中相邻单词，如果当前单词和前一个单词相同下标索引对应的字符不相同，
     * 则前一个单词下标索引对应字符的新字母顺序在当前单词下标索引对应字符的新字母顺序之前，即存在前一个单词字符指向当前单词字符的边，
     * 如果当前单词和前一个单词相同下标索引对应的字符都相同，但前一个单词长度大于当前单词长度，则不是新字母顺序，返回""
     * 对未访问的节点dfs，标记未访问的节点为0，正在访问的节点为1，已经访问的节点为2，如果当前节点访问标记为1，
     * 则说明图中存在环，不存在拓扑排序，当前节点访问结束，标记当前节点访问标记为2
     * 时间复杂度O(mn+C^2)，空间复杂度O(C^2) (n=words.length，m=max(words[i].length())，C=26，最多有C个节点，最多有C^2条边)
     *
     * @param words
     * @return
     */
    public String alienOrder(String[] words) {
        //邻接矩阵，words中只包含小写字母
        int[][] edges = new int[26][26];
        //哪些小写字母是图中节点的集合
        Set<Character> set = new HashSet<>();
        //前一个单词
        String preWord = words[0];

        for (int i = 1; i < words.length; i++) {
            String curWord = words[i];
            int j = 0;
            int k = 0;

            while (j < preWord.length() && k < curWord.length()) {
                //当前单词和前一个单词相同下标索引对应的字符不相同，
                //则前一个单词下标索引对应字符的新字母顺序在当前单词下标索引对应字符的新字母顺序之前，
                //即存在前一个单词字符preWord[j]指向当前单词字符curWord[k]的边
                if (preWord.charAt(j) != curWord.charAt(k)) {
                    edges[preWord.charAt(j) - 'a'][curWord.charAt(k) - 'a'] = 1;
                    set.add(preWord.charAt(j));
                    set.add(curWord.charAt(k));
                    break;
                }

                j++;
                k++;
            }

            //当前单词和前一个单词相同下标索引对应的字符都相同，但前一个单词长度大于当前单词长度，则不是新字母顺序，返回""
            if (j < preWord.length() && k == curWord.length()) {
                return "";
            }

            preWord = curWord;
        }

        //访问数组，0-未访问，1-正在访问，2-已访问
        int[] visited = new int[26];
        //dfs需要首添加
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 26; i++) {
            //从未访问的节点开始dfs
            if (set.contains((char) (i + 'a')) && visited[i] == 0) {
                dfs(i, edges, visited, sb);
            }

            //有环说明不存在拓扑排序，返回""
            if (hasCircle) {
                return "";
            }
        }

        return sb.toString();
    }

    /**
     * bfs拓扑排序
     * 解释：我们熟悉的字母顺序为abcdefg...，这里新字母顺序，例如"wrt"和"wrf"，则存在t在f之前的新字母顺序
     * 遍历words中相邻单词，如果当前单词和前一个单词相同下标索引对应的字符不相同，
     * 则前一个单词下标索引对应字符的新字母顺序在当前单词下标索引对应字符的新字母顺序之前，即存在前一个单词字符指向当前单词字符的边，
     * 如果当前单词和前一个单词相同下标索引对应的字符都相同，但前一个单词长度大于当前单词长度，则不是新字母顺序，返回""
     * 图中入度为0的节点入队，队列中节点出队，当前节点的邻接节点的入度减1，邻接节点入度为0的节点入队，
     * 遍历结束判断是否能够遍历到所有的节点，如果能遍历到所有的节点，则存在拓扑排序，返回拓扑排序路径；否则不存在拓扑排序，返回""
     * 时间复杂度O(mn+C+C^2)，空间复杂度O(C^2) (n=words.length，m=max(words[i].length())，C=26，最多有C个节点，最多有C^2条边)
     *
     * @param words
     * @return
     */
    public String alienOrder2(String[] words) {
        //邻接矩阵，words中只包含小写字母
        int[][] edges = new int[26][26];
        //图中节点的集合
        Set<Character> set = new HashSet<>();
        //前一个单词
        String preWord = words[0];

        for (int i = 1; i < words.length; i++) {
            String curWord = words[i];
            int j = 0;
            int k = 0;

            while (j < preWord.length() && k < curWord.length()) {
                //当前单词和前一个单词相同下标索引对应的字符不相同，
                //则前一个单词下标索引对应字符的新字母顺序在当前单词下标索引对应字符的新字母顺序之前，
                //即存在前一个单词字符preWord[j]指向当前单词字符curWord[k]的边
                if (preWord.charAt(j) != curWord.charAt(k)) {
                    edges[preWord.charAt(j) - 'a'][curWord.charAt(k) - 'a'] = 1;
                    set.add(preWord.charAt(j));
                    set.add(curWord.charAt(k));
                    break;
                }

                j++;
                k++;
            }

            //当前单词和前一个单词相同下标索引对应的字符都相同，但前一个单词长度大于当前单词长度，则不是新字母顺序，返回""
            if (j < preWord.length() && k == curWord.length()) {
                return "";
            }

            preWord = curWord;
        }

        //入度数组
        int[] inDegree = new int[26];

        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++) {
                if (edges[i][j] == 1) {
                    inDegree[j]++;
                }
            }
        }

        Queue<Character> queue = new LinkedList<>();

        for (int i = 0; i < 26; i++) {
            //入度为0的节点入队
            if (set.contains((char) (i + 'a')) && inDegree[i] == 0) {
                queue.offer((char) (i + 'a'));
            }
        }

        StringBuilder sb = new StringBuilder();

        while (!queue.isEmpty()) {
            char c = queue.poll();
            sb.append(c);

            for (int j = 0; j < 26; j++) {
                //节点c邻接顶点入度减1，入度为0的节点入队
                if (set.contains((char) (j + 'a')) && edges[c - 'a'][j] == 1) {
                    inDegree[j]--;
                    if (inDegree[j] == 0) {
                        queue.offer((char) (j + 'a'));
                    }
                }
            }
        }

        //能遍历到所有的节点，则存在拓扑排序；否则不存在拓扑排序
        return sb.length() == set.size() ? sb.toString() : "";
    }

    private void dfs(int i, int[][] edges, int[] visited, StringBuilder sb) {
        if (hasCircle) {
            return;
        }

        //当前节点正在访问
        visited[i] = 1;

        for (int j = 0; j < 26; j++) {
            if (hasCircle) {
                return;
            }

            //对当前节点的邻接节点进行dfs
            if (edges[i][j] == 1) {
                if (visited[j] == 0) {
                    dfs(j, edges, visited, sb);
                } else if (visited[j] == 1) {
                    //邻接节点正在访问，说明有环，不存在拓扑排序
                    hasCircle = true;
                    return;
                } else if (visited[j] == 2) {
                    continue;
                }
            }
        }

        //dfs需要首添加
        sb.insert(0, (char) (i + 'a'));
        //当前节点已经访问
        visited[i] = 2;
    }
}
