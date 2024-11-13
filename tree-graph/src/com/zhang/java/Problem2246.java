package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2025/1/28 08:40
 * @Author zsy
 * @Description 相邻字符不同的最长路径 类比Problem543、Problem1245 dfs类比Problem104、Problem110、Problem111、Problem124、Problem298、Problem337、Problem543、Problem687、Problem968、Problem979、Problem1245、Problem1373、Problem2378
 * 给你一棵 树（即一个连通、无向、无环图），根节点是节点 0 ，这棵树由编号从 0 到 n - 1 的 n 个节点组成。
 * 用下标从 0 开始、长度为 n 的数组 parent 来表示这棵树，其中 parent[i] 是节点 i 的父节点，
 * 由于节点 0 是根节点，所以 parent[0] == -1 。
 * 另给你一个字符串 s ，长度也是 n ，其中 s[i] 表示分配给节点 i 的字符。
 * 请你找出路径上任意一对相邻节点都没有分配到相同字符的 最长路径 ，并返回该路径的长度。
 * <p>
 * 输入：parent = [-1,0,0,1,1,2], s = "abacbe"
 * 输出：3
 * 解释：任意一对相邻节点字符都不同的最长路径是：0 -> 1 -> 3 。该路径的长度是 3 ，所以返回 3 。
 * 可以证明不存在满足上述条件且比 3 更长的路径。
 * <p>
 * <p>
 * 输入：parent = [-1,0,0,0], s = "aabc"
 * 输出：3
 * 解释：任意一对相邻节点字符都不同的最长路径是：2 -> 0 -> 3 。该路径的长度为 3 ，所以返回 3 。
 * <p>
 * n == parent.length == s.length
 * 1 <= n <= 10^5
 * 对所有 i >= 1 ，0 <= parent[i] <= n - 1 均成立
 * parent[0] == -1
 * parent 表示一棵有效的树
 * s 仅由小写英文字母组成
 */
public class Problem2246 {
    /**
     * 树中相邻字符不同的最长路径长度
     */
    private int max = 0;

    public static void main(String[] args) {
        Problem2246 problem2246 = new Problem2246();
        int[] parent = {-1, 0, 0, 1, 1, 2};
        String s = "abacbe";
        System.out.println(problem2246.longestPath(parent, s));
    }

    /**
     * dfs
     * 计算当前节点子节点作为路径起点的相邻字符不同的最大单侧路径长度，更新树中相邻字符不同的最长路径长度，
     * 返回当前节点对父节点的相邻字符不同的最大单侧路径长度，用于计算以当前节点父节点作为根节点的相邻字符不同的最大单侧路径长度
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param parent
     * @param s
     * @return
     */
    public int longestPath(int[] parent, String s) {
        //图中节点的个数
        int n = parent.length;
        //邻接表，有向图
        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        //i从1开始遍历，不需要考虑节点0的父节点
        for (int i = 1; i < n; i++) {
            graph.get(parent[i]).add(i);
        }

        dfs(0, graph, s.toCharArray());

        //注意：要加1，这里返回的是节点个数，而不是真正意义上的路径长度
        return max + 1;
    }

    private int dfs(int u, List<List<Integer>> graph, char[] arr) {
        //节点v对父节点u的相邻字符不同的最大单侧路径长度
        int maxVLen = 0;

        for (int v : graph.get(u)) {
            //节点v对父节点u的相邻字符不同的单侧路径长度
            int vLen = dfs(v, graph, arr);

            //相邻字符不同时，才能更新max和maxVLen
            if (arr[u] != arr[v]) {
                //更新树中相邻字符不同的最长路径长度
                max = Math.max(max, maxVLen + vLen);
                //更新maxVLen
                maxVLen = Math.max(maxVLen, vLen);
            }
        }

        //返回当前节点u对父节点的相邻字符不同的最大单侧路径长度，用于计算以当前节点父节点作为根节点的相邻字符不同的最大单侧路径长度
        return maxVLen + 1;
    }
}
