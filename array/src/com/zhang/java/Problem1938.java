package com.zhang.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Date 2025/4/13 08:16
 * @Author zsy
 * @Description 查询最大基因差 类比Problem421、Problem1707、Problem1803 前缀树类比
 * 给你一棵 n 个节点的有根树，节点编号从 0 到 n - 1 。
 * 每个节点的编号表示这个节点的 独一无二的基因值 （也就是说节点 x 的基因值为 x）。
 * 两个基因值的 基因差 是两者的 异或和 。给你整数数组 parents ，其中 parents[i] 是节点 i 的父节点。
 * 如果节点 x 是树的 根 ，那么 parents[x] == -1 。
 * 给你查询数组 queries ，其中 queries[i] = [nodei, vali] 。
 * 对于查询 i ，请你找到 vali 和 pi 的 最大基因差 ，其中 pi 是节点 nodei 到根之间的任意节点（包含 nodei 和根节点）。
 * 更正式的，你想要最大化 vali XOR pi 。
 * 请你返回数组 ans ，其中 ans[i] 是第 i 个查询的答案。
 * <p>
 * 输入：parents = [-1,0,1,1], queries = [[0,2],[3,2],[2,5]]
 * 输出：[2,3,7]
 * 解释：查询数组处理如下：
 * - [0,2]：最大基因差的对应节点为 0 ，基因差为 2 XOR 0 = 2 。
 * - [3,2]：最大基因差的对应节点为 1 ，基因差为 2 XOR 1 = 3 。
 * - [2,5]：最大基因差的对应节点为 2 ，基因差为 5 XOR 2 = 7 。
 * <p>
 * 输入：parents = [3,7,-1,2,0,7,0,2], queries = [[4,6],[1,15],[0,5]]
 * 输出：[6,14,7]
 * 解释：查询数组处理如下：
 * - [4,6]：最大基因差的对应节点为 0 ，基因差为 6 XOR 0 = 6 。
 * - [1,15]：最大基因差的对应节点为 1 ，基因差为 15 XOR 1 = 14 。
 * - [0,5]：最大基因差的对应节点为 2 ，基因差为 5 XOR 2 = 7 。
 * <p>
 * 2 <= parents.length <= 10^5
 * 对于每个 不是 根节点的 i ，有 0 <= parents[i] <= parents.length - 1 。
 * parents[root] == -1
 * 1 <= queries.length <= 3 * 10^4
 * 0 <= nodei <= parents.length - 1
 * 0 <= vali <= 2 * 10^5
 */
public class Problem1938 {
    public static void main(String[] args) {
        Problem1938 problem1938 = new Problem1938();
        int[] parents = {3, 7, -1, 2, 0, 7, 0, 2};
        int[][] queries = {{4, 6}, {1, 15}, {0, 5}};
        System.out.println(Arrays.toString(problem1938.maxGeneticDifference(parents, queries)));
    }

    /**
     * 前缀树+dfs
     * dfs遍历整个树，遍历到的当前节点加入前缀树，遍历结束当前节点从前缀树中删除，保证前缀树中只包含根节点到当前节点路径中的节点，
     * 如果遍历到的当前节点是要查询的节点，则前缀树中的节点都是根节点到当前节点路径中的节点
     * 时间复杂度O((n+m)*logC)=O(n+m)，空间复杂度O(n+m+n*logC)=O(n+m) (n=parents.length，m=queries.length) (C=max(n,queries[i][1]))
     *
     * @param parents
     * @param queries
     * @return
     */
    public int[] maxGeneticDifference(int[] parents, int[][] queries) {
        //树中节点的个数
        int n = parents.length;
        //根节点在parents中的下标索引
        int root = -1;
        //当前节点向子节点的邻接表
        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < n; i++) {
            if (parents[i] == -1) {
                root = i;
            } else {
                graph.get(parents[i]).add(i);
            }
        }

        //arr[0]：要查询的节点对应的val，arr[1]：要查询的节点在queries中的下标索引
        List<List<int[]>> queriesList = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            queriesList.add(new ArrayList<>());
        }

        for (int i = 0; i < queries.length; i++) {
            int node = queries[i][0];
            int val = queries[i][1];
            queriesList.get(node).add(new int[]{val, i});
        }

        int[] result = new int[queries.length];
        Trie trie = new Trie();

        //dfs遍历过程中保证前缀树中只包含根节点到当前节点路径中的节点
        dfs(root, graph, queriesList, trie, result);

        return result;
    }

    public void dfs(int u, List<List<Integer>> graph, List<List<int[]>> queriesList, Trie trie, int[] result) {
        trie.insert(u);

        //判断节点u是否是queries中要查询的节点
        for (int[] arr : queriesList.get(u)) {
            //要查询的节点对应的val
            int val = arr[0];
            //要查询的节点在queries中的下标索引
            int index = arr[1];
            //前缀树中只包含根节点到当前节点路径中的节点，通过前缀树得到前缀树中和val异或的最大值
            result[index] = trie.searchMaxXor(val);
        }

        for (int v : graph.get(u)) {
            dfs(v, graph, queriesList, trie, result);
        }

        trie.remove(u);
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
         * num二进制表示的每一位插入前缀树中
         * 时间复杂度O(logC)=O(1)，空间复杂度O(1) (C=max(num))
         *
         * @param num
         */
        public void insert(int num) {
            TrieNode node = root;
            node.count++;

            //num都为正数，不需要考虑最高位符号位
            for (int i = 30; i >= 0; i--) {
                int cur = (num >>> i) & 1;

                if (node.children[cur] == null) {
                    node.children[cur] = new TrieNode();
                }

                node = node.children[cur];
                node.count++;
            }

            node.isEnd = true;
        }

        /**
         * num二进制表示的每一位从前缀树中删除
         * 时间复杂度O(logC)=O(1)，空间复杂度O(1) (C=max(num))
         *
         * @param num
         */
        public void remove(int num) {
            TrieNode node = root;
            node.count--;

            //num都为正数，不需要考虑最高位符号位
            for (int i = 30; i >= 0; i--) {
                int cur = (num >>> i) & 1;
                node = node.children[cur];
                node.count--;
            }

            if (node.count == 0) {
                node.isEnd = false;
            }
        }

        /**
         * 查询前缀树中和num异或的最大值
         * 时间复杂度O(logC)，空间复杂度O(1) (C=max(num))
         *
         * @param num
         * @return
         */
        public int searchMaxXor(int num) {
            //前缀树中和num异或的最大值
            int xor = 0;
            TrieNode node = root;

            //num都为正数，不需要考虑最高位符号位
            for (int i = 30; i >= 0; i--) {
                int cur = (num >>> i) & 1;

                //前缀树中节点会删除，所以不能只判断子节点是否为空，还需要考虑子节点count
                if (node.children[cur ^ 1] != null && node.children[cur ^ 1].count > 0) {
                    node = node.children[cur ^ 1];
                    xor = (xor << 1) + 1;
                } else {
                    node = node.children[cur];
                    xor = xor << 1;
                }
            }

            return xor;
        }

        private static class TrieNode {
            private final TrieNode[] children;
            //前缀树中根节点到当前节点作为二进制前缀包含的元素个数
            private int count;
            private boolean isEnd;

            public TrieNode() {
                children = new TrieNode[2];
                count = 0;
                isEnd = false;
            }
        }
    }
}
