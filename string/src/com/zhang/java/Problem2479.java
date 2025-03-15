package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2025/4/28 08:47
 * @Author zsy
 * @Description 两个不重叠子树的最大异或值 类比Problem421、Problem1707、Problem1803、Problem1938 前缀树类比
 * 有一个无向树，有 n 个节点，节点标记为从 0 到 n - 1。
 * 给定整数 n 和一个长度为 n - 1 的 2 维整数数组 edges，其中 edges[i] = [ai, bi] 表示在树中的节点 ai 和 bi 之间有一条边。
 * 树的根节点是标记为 0 的节点。
 * 每个节点都有一个相关联的 值。给定一个长度为 n 的数组 values，其中 values[i] 是第 i 个节点的 值。
 * 选择任意两个 不重叠 的子树。你的 分数 是这些子树中值的和的逐位异或。
 * 返回你能达到的最大分数。如果不可能找到两个不重叠的子树，则返回 0。
 * 注意：
 * 节点的 子树 是由该节点及其所有子节点组成的树。
 * 如果两个子树不共享 任何公共 节点，则它们是 不重叠 的。
 * <p>
 * 输入: n = 6, edges = [[0,1],[0,2],[1,3],[1,4],[2,5]], values = [2,8,3,6,2,5]
 * 输出: 24
 * 解释: 节点 1 的子树的和值为 16，而节点 2 的子树的和值为 8，因此选择这些节点将得到 16 XOR 8 = 24 的分数。
 * 可以证明，这是我们能得到的最大可能分数。
 * <p>
 * 输入: n = 3, edges = [[0,1],[1,2]], values = [4,6,1]
 * 输出: 0
 * 解释: 不可能选择两个不重叠的子树，所以我们只返回 0。
 * <p>
 * 2 <= n <= 5 * 10^4
 * edges.length == n - 1
 * 0 <= ai, bi < n
 * values.length == n
 * 1 <= values[i] <= 10^9
 * 保证 edges 代表一个有效的树。
 */
public class Problem2479 {
    private long maxXorResult = 0;

    public static void main(String[] args) {
        Problem2479 problem2479 = new Problem2479();
        int n = 6;
        int[][] edges = {{0, 1}, {0, 2}, {1, 3}, {1, 4}, {2, 5}};
        int[] values = {2, 8, 3, 6, 2, 5};
        System.out.println(problem2479.maxXor(n, edges, values));
    }

    /**
     * 前缀树+dfs
     * dfs得到每个节点作为根节点的树中节点值之和，dfs遍历当前节点和已经加入前缀树的节点进行异或运算，
     * 当前节点遍历结束，当前节点加入前缀树，保证前缀树的节点作为根节点的树和当前dfs遍历到的节点作为根节点的树不重叠
     * 时间复杂度O(nlogC)=O(n)，空间复杂度O(nlogC)=O(n) (C=sum(values[i]))
     *
     * @param n
     * @param edges
     * @param values
     * @return
     */
    public long maxXor(int n, int[][] edges, int[] values) {
        //邻接表，无向图
        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < edges.length; i++) {
            graph.get(edges[i][0]).add(edges[i][1]);
            graph.get(edges[i][1]).add(edges[i][0]);
        }

        //每个节点作为根节点的树中节点值之和数组
        long[] sumArr = new long[n];

        dfs(0, -1, graph, sumArr, values);

        Trie trie = new Trie();

        //dfs遍历过程中保证前缀树的节点作为根节点的树和当前dfs遍历到的节点作为根节点的树不重叠
        dfs(0, -1, graph, trie, sumArr);

        return maxXorResult;
    }

    private long dfs(int u, int parent, List<List<Integer>> graph, long[] sumArr, int[] values) {
        sumArr[u] = values[u];

        for (int v : graph.get(u)) {
            if (v == parent) {
                continue;
            }

            sumArr[u] = sumArr[u] + dfs(v, u, graph, sumArr, values);
        }

        return sumArr[u];
    }

    private void dfs(int u, int parent, List<List<Integer>> graph, Trie trie, long[] sumArr) {
        //前缀树的节点作为根节点的树和当前节点作为根节点的树不重叠，
        //通过前缀树得到当前节点作为根节点的树和前缀树的节点作为根节点的树异或的最大值，来更新maxXorResult
        maxXorResult = Math.max(maxXorResult, trie.searchMaxXor(sumArr[u]));

        for (int v : graph.get(u)) {
            if (v == parent) {
                continue;
            }

            dfs(v, u, graph, trie, sumArr);
        }

        //当前节点遍历结束，当前节点加入前缀树，保证前缀树的节点作为根节点的树和当前dfs遍历到的节点作为根节点的树不重叠
        trie.insert(sumArr[u]);
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
         * 时间复杂度O(log(num))=O(1)，空间复杂度O(1)
         *
         * @param num
         */
        public void insert(long num) {
            TrieNode node = root;

            //num都为正数，不需要考虑最高位符号位
            for (int i = 62; i >= 0; i--) {
                //num当前位的值
                int cur = (int) ((num >>> i) & 1);

                if (node.children[cur] == null) {
                    node.children[cur] = new TrieNode();
                }

                node = node.children[cur];
            }

            node.isEnd = true;
        }

        /**
         * 查询前缀树中和num异或的最大值
         * 时间复杂度O(log(num))，空间复杂度O(1)
         *
         * @param num
         * @return
         */
        public long searchMaxXor(long num) {
            long xor = 0;
            TrieNode node = root;

            //num都为正数，不需要考虑最高位符号位
            for (int i = 62; i >= 0; i--) {
                //num当前位的值
                int cur = (int) ((num >>> i) & 1);

                //当前节点为空，则说明前缀树为空，不存在不重叠的前缀中节点对应的子树和当前节点对应的子树，无法进行异或运算，直接返回0
                if (node == null) {
                    return 0;
                }

                //当前节点存在cur的异或值cur^1，则xor当前位为1
                if (node.children[cur ^ 1] != null) {
                    node = node.children[cur ^ 1];
                    xor = (xor << 1) + 1;
                } else {
                    //当前节点存在和cur的相同值cur，则xor当前位为0
                    node = node.children[cur];
                    xor = xor << 1;
                }
            }

            return xor;
        }

        /**
         * 前缀树节点
         */
        private static class TrieNode {
            private final TrieNode[] children;
            private boolean isEnd;

            public TrieNode() {
                children = new TrieNode[2];
                isEnd = false;
            }
        }
    }
}
